/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.NumberType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

public class RedisClusterClient extends BaseRedisClient implements Redis {

  private static final Logger LOG = LoggerFactory.getLogger(RedisClusterClient.class);

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    RedisClusterConnection.addReducer(command, fn);
  }

  public static void addMasterOnlyCommand(Command command) {
    RedisClusterConnection.addMasterOnlyCommand(command);
  }

  static {
    // provided reducers

    addReducer(MSET, list ->
      // Simple string reply: always OK since MSET can't fail.
      SimpleStringType.OK);

    addReducer(DEL, list ->
      NumberType.create(list.stream()
        .mapToLong(el -> {
          Long l = el.toLong();
          if (l == null) {
            return 0L;
          } else {
            return l;
          }
        }).sum()));

    addReducer(MGET, list -> {
      int total = 0;
      for (Response resp : list) {
        total += resp.size();
      }

      MultiType multi = MultiType.create(total, false);
      for (Response resp : list) {
        for (Response child : resp) {
          multi.add(child);
        }
      }

      return multi;
    });

    addReducer(KEYS, list -> {
      int total = 0;
      for (Response resp : list) {
        total += resp.size();
      }

      MultiType multi = MultiType.create(total, false);
      for (Response resp : list) {
        for (Response child : resp) {
          multi.add(child);
        }
      }

      return multi;
    });

    addReducer(FLUSHDB, list ->
      // Simple string reply: always OK since FLUSHDB can't fail.
      SimpleStringType.OK);

    addReducer(DBSIZE, list ->
      // Sum of key numbers on all Key Slots
      NumberType.create(list.stream()
        .mapToLong(el -> {
          Long l = el.toLong();
          if (l == null) {
            return 0L;
          } else {
            return l;
          }
        }).sum()));

    addMasterOnlyCommand(WAIT);

    addMasterOnlyCommand(SUBSCRIBE);
    addMasterOnlyCommand(PSUBSCRIBE);
    addReducer(UNSUBSCRIBE, list -> SimpleStringType.OK);
    addReducer(PUNSUBSCRIBE, list -> SimpleStringType.OK);
  }

  private final RedisClusterConnectOptions connectOptions;
  private final PoolOptions poolOptions;

  private final AtomicReference<Future<Slots>> slots = new AtomicReference<>();

  public RedisClusterClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, RedisClusterConnectOptions connectOptions, TracingPolicy tracingPolicy) {
    super(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
    this.connectOptions = connectOptions;
    this.poolOptions = poolOptions;
    // validate options
    if (poolOptions.getMaxWaiting() < poolOptions.getMaxSize()) {
      throw new IllegalStateException("Invalid options: maxWaiting < maxSize");
      // we can't validate the max pool size yet as we need to know the slots first
      // the remaining validation will happen at the connect time
    }
  }

  @Override
  public Future<RedisConnection> connect() {
    final Promise<RedisConnection> promise = vertx.promise();
    getSlots(vertx.getOrCreateContext())
      .onSuccess(slots -> connect(slots, promise))
      .onFailure(promise::fail);
    return promise.future();
  }

  private void connect(Slots slots, Handler<AsyncResult<RedisConnection>> onConnected) {
    // validate if the pool config is valid
    final int totalUniqueEndpoints = slots.endpoints().length;
    if (poolOptions.getMaxSize() < totalUniqueEndpoints) {
      // this isn't a valid setup, the connection pool will not accommodate all the required connections
      onConnected.handle(Future.failedFuture("RedisOptions maxPoolSize < Cluster size(" + totalUniqueEndpoints + "): The pool is not able to hold all required connections!"));
      return;
    }

    // create a cluster connection
    final AtomicBoolean failed = new AtomicBoolean(false);
    final AtomicInteger counter = new AtomicInteger();
    final Map<String, PooledRedisConnection> connections = new HashMap<>();

    for (String endpoint : slots.endpoints()) {
      connectionManager.getConnection(endpoint, RedisReplicas.NEVER !=  connectOptions.getUseReplicas() ? cmd(READONLY) : null)
        .onFailure(err -> {
          // failed try with the next endpoint
          failed.set(true);
          connectionComplete(counter, slots, connections, failed, onConnected);
        })
        .onSuccess(cconn -> {
          // there can be concurrent access to the connection map
          // since this is a one time operation we can pay the penalty of
          // synchronizing on each write (hopefully is only a few writes)
          synchronized (connections) {
            connections.put(endpoint, cconn);
          }
          connectionComplete(counter, slots, connections, failed, onConnected);
        });
    }
  }

  private void connectionComplete(AtomicInteger counter, Slots slots, Map<String, PooledRedisConnection> connections,
      AtomicBoolean failed, Handler<AsyncResult<RedisConnection>> onConnected) {
    if (counter.incrementAndGet() == slots.endpoints().length) {
      // end condition
      if (failed.get()) {
        // cleanup

        // during an error we lock the map because we will change it
        // probably this isn't an issue as no more write should happen anyway
        synchronized (connections) {
          for (RedisConnection value : connections.values()) {
            if (value != null) {
              value.close().onFailure(LOG::warn);
            }
          }
        }
        // return
        onConnected.handle(Future.failedFuture("Failed to connect to all nodes of the cluster"));
      } else {
        onConnected.handle(Future.succeededFuture(new RedisClusterConnection(vertx, connectOptions, slots,
          () -> this.slots.set(null), connections)));
      }
    }
  }

  private Future<Slots> getSlots(ContextInternal context) {
    while (true) {
      Future<Slots> slots = this.slots.get();
      if (slots != null) {
        return slots;
      }

      Promise<Slots> promise = context.promise();
      Future<Slots> future = promise.future();
      if (this.slots.compareAndSet(null, future)) {
        LOG.debug("Obtaining hash slot assignment");
        // attempt to load the slots from the first good endpoint
        getSlots(connectOptions.getEndpoints(), 0, promise);
        return future;
      }
    }
  }

  private void getSlots(List<String> endpoints, int index, Handler<AsyncResult<Slots>> onGotSlots) {
    if (index >= endpoints.size()) {
      // stop condition
      onGotSlots.handle(Future.failedFuture("Cannot connect to any of the provided endpoints"));
      return;
    }

    connectionManager.getConnection(endpoints.get(index), RedisReplicas.NEVER != connectOptions.getUseReplicas() ? cmd(READONLY) : null)
      .onFailure(err -> {
        // try with the next endpoint
        getSlots(endpoints, index + 1, onGotSlots);
      })
      .onSuccess(conn -> {
        getSlots(endpoints.get(index), conn).onComplete(result -> {
          // the connection is not needed anymore, regardless of success or failure
          // (on success, we just finish, on failure, we'll try another endpoint)
          conn.close().onFailure(LOG::warn);

          if (result.failed()) {
            // the slots command failed, try with next endpoint
            getSlots(endpoints, index + 1, onGotSlots);
          } else {
            Slots slots = result.result();
            onGotSlots.handle(Future.succeededFuture(slots));
            vertx.setTimer(connectOptions.getHashSlotCacheTTL(), ignored -> this.slots.set(null));
          }
        });
      });
  }

  private Future<Slots> getSlots(String endpoint, RedisConnection conn) {
    return conn
      .send(cmd(CLUSTER).arg("SLOTS"))
      .compose(reply -> {
        if (reply == null || reply.size() == 0) {
          // no slots available we can't really proceed
          return Future.failedFuture("SLOTS No slots available in the cluster.");
        }

        return Future.succeededFuture(new Slots(endpoint, reply));
      });
  }
}
