/*
 *  Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.redis.impl.pool;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.ConnectionPoolTooBusyException;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.shareddata.Shareable;
import io.vertx.redis.Redis;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.RedisPoolOptions;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages a pool of connection.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public abstract class AsyncConnectionPool {

  private static final String POOL_MAP_NAME = "__vertx.redis.pool.store";

  private static class PoolStore implements Shareable {
    private int poolSize = 0;
    private final Deque<RedisConnection> availableConnections = new ArrayDeque<>();
    private final Deque<Handler<AsyncResult<RedisConnection>>> waiters = new ArrayDeque<>();
    private final Map<RedisConnection, Long> timers = new HashMap<>();
  }

  private static final Logger logger = LoggerFactory.getLogger(AsyncConnectionPool.class);

  protected final Vertx vertx;
  private final RedisPoolOptions options;

  private final PoolStore store;

  public AsyncConnectionPool(Vertx vertx, RedisPoolOptions redisPoolOptions) {
    this(vertx, redisPoolOptions, UUID.randomUUID().toString());
  }

  public AsyncConnectionPool(Vertx vertx, RedisPoolOptions redisPoolOptions, String poolName) {
    this.vertx = vertx;
    this.options = redisPoolOptions;
    store = vertx.sharedData().<String, PoolStore>getLocalMap(POOL_MAP_NAME).computeIfAbsent(poolName, k -> new PoolStore());
  }

  public int getPoolSize() {
    synchronized (store) {
      return store.poolSize;
    }
  }

  private void createConnection(Handler<AsyncResult<RedisConnection>> handler) {
    synchronized (store) {
      store.poolSize += 1;
      createAndConnect(new Handler<AsyncResult<RedisConnection>>() {
        int retries = 0;

        @Override
        public void handle(AsyncResult<RedisConnection> connectionResult) {
          if (connectionResult.succeeded()) {
            handler.handle(connectionResult);
          } else if (options.getMaxConnectionRetries() < 0 || retries < options.getMaxConnectionRetries()) {
            retries++;
            logger.debug("Error creating connection. Waiting " + options.getConnectionRetryDelay() + " ms for retry " +
              retries + (options.getMaxConnectionRetries() >= 0 ? " of " + options.getMaxConnectionRetries() : ""));
            vertx.setTimer(options.getConnectionRetryDelay(), timerId ->
              createAndConnect(this) // Try to connect again using this handler
            );
          } else {
            synchronized (store) {
              store.poolSize -= 1;
            }
            notifyWaitersAboutAvailableConnection();
            handler.handle(connectionResult);
          }
        }
      });
    }
  }

  private void createAndConnect(Handler<AsyncResult<RedisConnection>> handler) {
    final Redis client;

    switch (options.getType()) {
      case STANDALONE:
        client = RedisClient.create(vertx, options);
        break;
      case SENTINEL:
        client = RedisSentinelClient.create(vertx, options);
        break;
      case CLUSTER:
        client = RedisClusterClient.create(vertx, options);
        break;
      default:
        logger.info("unknown redis client type: " + options.getType());
        handler.handle(Future.failedFuture("unknown redis client type: " + options.getType()));
        return;
    }


    try {
      client.connect(ar -> {
        if (ar.failed()) {
          logger.info("failed to create connection", ar.cause());
          handler.handle(Future.failedFuture(ar.cause()));
        } else {
          handler.handle(Future.succeededFuture(ar.result()));
        }
      });
    } catch (Throwable e) {
      logger.info("creating a connection went wrong", e);
      handler.handle(Future.failedFuture(e));
    }
  }

  private void waitForAvailableConnection(Handler<AsyncResult<RedisConnection>> handler) {
    synchronized (store) {
      if (canAddWaiter()) {
        store.waiters.add(handler);
        return;
      }

      handler.handle(Future.failedFuture(new ConnectionPoolTooBusyException("Connection pool reached max wait queue size of " + options.getMaxQueueSize())));
    }
  }

  private boolean canAddWaiter() {
    synchronized (store) {
      return options.getMaxQueueSize() < 0 || store.waiters.size() < options.getMaxQueueSize();
    }
  }

  private void createOrWaitForAvailableConnection(Handler<AsyncResult<RedisConnection>> handler) {
    synchronized (store) {
      if (store.poolSize < options.getMaxPoolSize()) {
        createConnection(handler);
      } else {
        waitForAvailableConnection(handler);
      }
    }
  }

  public void take(Handler<AsyncResult<RedisConnection>> handler) {
    synchronized (store) {
      RedisConnection connection = store.availableConnections.poll();
      if (connection == null) {
        createOrWaitForAvailableConnection(handler);
      } else {
        Long timerId = store.timers.remove(connection);
        if (timerId != null) {
          vertx.cancelTimer(timerId);
        }
        if (connection.isConnected()) {
          // Do connection test if connection test timeout is configured
          if (options.getConnectionTestTimeout() > 0) {
            AtomicBoolean testCompleted = new AtomicBoolean(false);
            long timer = vertx.setTimer(options.getConnectionTestTimeout(), ignored -> {
              // check if the test request has completed or not, if not, try it again and drop the current connection
              if (testCompleted.compareAndSet(false, true)) {
                logger.info("connection test timeout");
                connection.close(); // drop the connection if it's still alive
                synchronized (store) {
                  store.poolSize -= 1;
                }

                take(handler);
              }
            });
            connection.send(Request.cmd(Command.PING), ping -> {
              if (ping.failed()) {
                logger.info("connection test failed", ping.cause());
                connection.close(); // try to close the connection
                synchronized (store) {
                  store.poolSize -= 1;
                }

                take(handler);
              } else {
                // connection is good, however, need to check if the test query has timeout or not
                // if timeout is not fired yet, then we will cleanup the timeout timer and return
                // the connection, otherwise, we will skip this event, as timeout timer already
                // drop the connection and retry
                if (testCompleted.compareAndSet(false, true)) {
                  // cleanup the timer
                  if (this.options.getConnectionTestTimeout() > 0) {
                    vertx.cancelTimer(timer);
                  }

                  handler.handle(Future.succeededFuture(connection));
                }

              }
            });
          } else {
            // No test connection timeout is configured, return the connection directly
            handler.handle(Future.succeededFuture(connection));
          }
        } else {
          synchronized (store) {
            store.poolSize -= 1;
          }
          take(handler);
        }
      }
    }
  }

  private void notifyWaitersAboutAvailableConnection() {
    synchronized (store) {
      Handler<AsyncResult<RedisConnection>> handler = store.waiters.poll();
      if (handler != null) {
        take(handler);
      }
    }
  }

  public void expire(RedisConnection connection) {
    synchronized (store) {
      connection.close();
      store.availableConnections.remove(connection);
      store.poolSize -= 1;
    }
  }

  public void giveBack(RedisConnection connection) {
    synchronized (store) {
      if (connection.isConnected()) {
        store.availableConnections.add(connection);
        if (options.getConnectionReleaseDelay() > 0) {
          Long timerId = vertx.setTimer(options.getConnectionReleaseDelay(), res -> expire(connection));
          store.timers.put(connection, timerId);
        }
      } else {
        store.poolSize -= 1;
      }
      notifyWaitersAboutAvailableConnection();
    }
  }

  public void close() {
    synchronized (store) {
      for (long id : store.timers.values()) {
        vertx.cancelTimer(id);
      }
      store.timers.clear();
      store.availableConnections.forEach(RedisConnection::close);
    }
  }

  public void close(Handler<AsyncResult<Void>> handler) {
    close();
    if (handler != null) {
      handler.handle(Future.succeededFuture());
    }
  }
}
