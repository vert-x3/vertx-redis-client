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
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.List;
import java.util.Random;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

public class RedisSentinelClient implements Redis {

  // We don't need to be secure, we just want so simple
  // randomization to avoid picking the same replica all the time
  private static final Random RANDOM = new Random();

  private static class Pair<L, R> {
    final L left;
    final R right;

    Pair(L left, R right) {
      this.left = left;
      this.right = right;
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(RedisSentinelClient.class);

  private final Context context;
  private final RedisConnectionManager connectionManager;
  private final RedisOptions options;

  private RedisConnection sentinel;

  public RedisSentinelClient(Vertx vertx, RedisOptions options) {
    this.context = vertx.getOrCreateContext();
    this.options = options;
    // validate options
    if (options.getMaxPoolSize() < 2) {
      throw new IllegalStateException("Invalid options: maxPoolSize must be at least 2");
    }
    if (options.getMaxPoolWaiting() < options.getMaxPoolSize()) {
      throw new IllegalStateException("Invalid options: maxPoolWaiting < maxPoolSize");
    }
    // sentinel (HA) requires 2 connections, one to watch for sentinel events and the connection itself
    this.connectionManager = new RedisConnectionManager(vertx, options);

    this.connectionManager.start();
  }

  @Override
  public void close() {
    this.connectionManager.close();
  }

  @Override
  public Redis connect(Handler<AsyncResult<RedisConnection>> onCreate) {
    // sentinel (HA) requires 2 connections, one to watch for sentinel events and the connection itself
    createConnectionInternal(options, options.getRole(), createConnection -> {
      if (createConnection.failed()) {
        onCreate.handle(Future.failedFuture(createConnection.cause()));
        return;
      }

      final RedisConnection conn = createConnection.result();

      createConnectionInternal(options, RedisRole.SENTINEL, create -> {
        if (create.failed()) {
          LOG.error("Redis PUB/SUB wrap failed.", create.cause());
          return;
        }

        sentinel = create.result();

        sentinel
          .handler(msg -> {
            if (msg.type() == ResponseType.MULTI) {
              if ("MESSAGE".equalsIgnoreCase(msg.get(0).toString())) {
                // we don't care about the payload
                if (conn != null) {
                  ((RedisStandaloneConnection) conn).fail(ErrorType.create("SWITCH-MASTER Received +switch-master message from Redis Sentinel."));
                } else {
                  LOG.warn("Received +switch-master message from Redis Sentinel.");
                }
              }
            }
          });

        sentinel.send(cmd(SUBSCRIBE).arg("+switch-master"), send -> {
          if (send.failed()) {
            onCreate.handle(Future.failedFuture(send.cause()));
          } else {
            // both connections ready
            onCreate.handle(Future.succeededFuture(new RedisSentinelConnection(conn, sentinel)));
          }
        });

        sentinel.exceptionHandler(t -> {
          if (conn != null) {
            ((RedisStandaloneConnection) conn).fail(t);
          } else {
            LOG.error("Unhandled exception in Sentinel PUBSUB", t);
          }
        });
      });
    });

    return this;
  }

  private void createConnectionInternal(RedisOptions options, RedisRole role, Handler<AsyncResult<RedisConnection>> onCreate) {

    final Handler<AsyncResult<String>> createAndConnect = resolve -> {
      if (resolve.failed()) {
        onCreate.handle(Future.failedFuture(resolve.cause()));
        return;
      }
      // wrap a new client
      if (role == RedisRole.SENTINEL) {// sentinel cannot select
        final RedisURI uri = new RedisURI(resolve.result());
        connectionManager.getConnection(context, getSentinelEndpoint(uri), null, onCreate);
      } else {
        connectionManager.getConnection(context, resolve.result(), null, onCreate);
      }
    };

    switch (role) {
      case SENTINEL:
        resolveClient(this::isSentinelOk, options, createAndConnect);
        break;

      case MASTER:
        resolveClient(this::getMasterFromEndpoint, options, createAndConnect);
        break;

      case REPLICA:
        resolveClient(this::getReplicaFromEndpoint, options, createAndConnect);
    }
  }

  /**
   * We use the algorithm from http://redis.io/topics/sentinel-clients
   * to get a sentinel client and then do 'stuff' with it
   */
  private static void resolveClient(final Resolver checkEndpointFn, final RedisOptions options, final Handler<AsyncResult<String>> callback) {
    // Because finding the master is going to be an async list we will terminate
    // when we find one then use promises...
    iterate(0, checkEndpointFn, options, iterate -> {
      if (iterate.failed()) {
        callback.handle(Future.failedFuture(iterate.cause()));
      } else {
        final Pair<Integer, String> found = iterate.result();
        // This is the endpoint that has responded so stick it on the top of
        // the list
        final List<String> endpoints = options.getEndpoints();
        String endpoint = endpoints.get(found.left);
        endpoints.set(found.left, endpoints.get(0));
        endpoints.set(0, endpoint);
        // now return the right address
        callback.handle(Future.succeededFuture(found.right));
      }
    });
  }

  private static void iterate(final int idx, final Resolver checkEndpointFn, final RedisOptions argument, final Handler<AsyncResult<Pair<Integer, String>>> resultHandler) {
    // stop condition
    final List<String> endpoints = argument.getEndpoints();

    if (idx >= endpoints.size()) {
      resultHandler.handle(Future.failedFuture("No more endpoints in chain."));
      return;
    }

    // attempt to perform operation
    checkEndpointFn.resolve(endpoints.get(idx), argument, res -> {
      if (res.succeeded()) {
        resultHandler.handle(Future.succeededFuture(new Pair<>(idx, res.result())));
      } else {
        // try again with next endpoint
        iterate(idx + 1, checkEndpointFn, argument, resultHandler);
      }
    });
  }

  // begin endpoint check methods

  private void isSentinelOk(String endpoint, RedisOptions argument, Handler<AsyncResult<String>> handler) {
    // we can't use the endpoint as is, it should not contain a database selection,
    // but can contain authentication
    final RedisURI uri = new RedisURI(endpoint);

    connectionManager.getConnection(context, getSentinelEndpoint(uri), null, onCreate -> {
      if (onCreate.failed()) {
        handler.handle(Future.failedFuture(onCreate.cause()));
        return;
      }

      final RedisConnection conn = onCreate.result();

      // Send a command just to check we have a working node
      conn.send(cmd(PING), ping -> {
        if (ping.failed()) {
          handler.handle(Future.failedFuture(ping.cause()));
        } else {
          handler.handle(Future.succeededFuture(endpoint));
        }
        // connection is not needed anymore
        conn.close();
      });
    });
  }

  private void getMasterFromEndpoint(String endpoint, RedisOptions options, Handler<AsyncResult<String>> handler) {
    // we can't use the endpoint as is, it should not contain a database selection,
    // but can contain authentication
    final RedisURI uri = new RedisURI(endpoint);
    connectionManager.getConnection(context, getSentinelEndpoint(uri), null, onCreate -> {
      if (onCreate.failed()) {
        handler.handle(Future.failedFuture(onCreate.cause()));
        return;
      }

      final RedisConnection conn = onCreate.result();
      final String masterName = options.getMasterName();

      // Send a command just to check we have a working node
      conn.send(cmd(SENTINEL).arg("GET-MASTER-ADDR-BY-NAME").arg(masterName), getMasterAddrByName -> {
        if (getMasterAddrByName.failed()) {
          handler.handle(Future.failedFuture(getMasterAddrByName.cause()));
        } else {
          // Test the response
          final Response response = getMasterAddrByName.result();
          final String host = response.get(0).toString().contains(":") ? "[" + response.get(0).toString() + "]" : response.get(0).toString();
          handler.handle(
            Future.succeededFuture(uri.protocol() + "://" + uri.userinfo() + host + ":" + response.get(1).toInteger()));
        }
        // we don't need this connection anymore
        conn.close();
      });
    });
  }

  private void getReplicaFromEndpoint(String endpoint, RedisOptions options, Handler<AsyncResult<String>> handler) {
    // we can't use the endpoint as is, it should not contain a database selection,
    // but can contain authentication
    final RedisURI uri = new RedisURI(endpoint);
    connectionManager.getConnection(context, getSentinelEndpoint(uri), null, onCreate -> {
      if (onCreate.failed()) {
        handler.handle(Future.failedFuture(onCreate.cause()));
        return;
      }

      final RedisConnection conn = onCreate.result();
      final String masterName = options.getMasterName();

      // Send a command just to check we have a working node
      conn.send(cmd(SENTINEL).arg("SLAVES").arg(masterName), sentinelReplicas -> {
        if (sentinelReplicas.failed()) {
          handler.handle(Future.failedFuture(sentinelReplicas.cause()));
        } else {
          final Response response = sentinelReplicas.result();

          // Test the response
          if (response.size() == 0) {
            handler.handle(Future.failedFuture("No replicas linked to the master: " + masterName));
          } else {
            Response replicaInfoArr = response.get(RANDOM.nextInt(response.size()));
            if ((replicaInfoArr.size() % 2) > 0) {
              handler.handle(Future.failedFuture("Corrupted response from the sentinel"));
            } else {
              int port = 6379;
              String ip = null;

              if (replicaInfoArr.containsKey("port")) {
                port = replicaInfoArr.get("port").toInteger();
              }

              if (replicaInfoArr.containsKey("ip")) {
                ip = replicaInfoArr.get("ip").toString();
              }

              if (ip == null) {
                handler.handle(Future.failedFuture("No IP found for a REPLICA node!"));
              } else {
                final String host = ip.contains(":") ? "[" + ip + "]" : ip;

                handler.handle(Future.succeededFuture(uri.protocol() + "://" + uri.userinfo() + host + ":" + port));
              }
            }
          }
        }
        // connection is not needed anymore
        conn.close();
      });
    });
  }

  private String getSentinelEndpoint(RedisURI uri) {
    StringBuilder sb = new StringBuilder();

    if (uri.unix()) {
      sb.append("unix://");
      sb.append(uri.socketAddress().path());
    } else {
      sb.append("redis");
      if (uri.ssl()) {
        sb.append('s');
      }
      sb.append("://");
      sb.append(uri.userinfo());
      sb.append(uri.socketAddress().host());
      sb.append(':');
      sb.append(uri.socketAddress().port());
    }

    return sb.toString();
  }
}
