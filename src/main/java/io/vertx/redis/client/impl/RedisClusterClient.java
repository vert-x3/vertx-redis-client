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
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.NumberType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

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

  public RedisClusterClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisOptions>> mutableOptions) {
    super(vertx, options, mutableOptions);
    // validate options
    if (options.getMaxPoolWaiting() < options.getMaxPoolSize()) {
      throw new IllegalStateException("Invalid options: maxPoolWaiting < maxPoolSize");
      // we can't validate the max pool size yet as we need to know the slots first
      // the remaining validation will happen at the connect time
    }
  }

  @Override
  public Future<RedisConnection> connect() {
    return optionsSupplier.get().flatMap(options -> {
      final Promise<RedisConnection> promise = vertx.promise();
      // attempt to load the slots from the first good endpoint
      connect(options.getEndpoints(), 0, promise);
      return promise.future();
    });
  }

  private void connect(List<String> endpoints, int index, Handler<AsyncResult<RedisConnection>> onConnect) {
    optionsSupplier.get().flatMap(options -> {

      if (index >= endpoints.size()) {
        // stop condition
        onConnect.handle(Future.failedFuture("Cannot connect to any of the provided endpoints"));
        return Future.succeededFuture();
      }

      return connectionManager.getConnection(endpoints.get(index), RedisReplicas.NEVER != options.getUseReplicas() ? cmd(READONLY) : null)
        .onFailure(err -> {
          // failed try with the next endpoint
          connect(endpoints, index + 1, onConnect);
        })
        .onSuccess(conn -> {
          // fetch slots from the cluster immediately to ensure slots are correct
          getSlots(endpoints.get(index), conn, getSlots -> {
            if (getSlots.failed()) {
              // the slots command failed.
              conn.close().onFailure(LOG::warn);
              // try with the next one
              connect(endpoints, index + 1, onConnect);
              return;
            }

            // slots are loaded (this connection isn't needed anymore)
            conn.close().onFailure(LOG::warn);
            // create a cluster connection
            final Slots slots = getSlots.result();
            final AtomicBoolean failed = new AtomicBoolean(false);
            final AtomicInteger counter = new AtomicInteger();
            final Map<String, RedisConnection> connections = new HashMap<>();

            // validate if the pool config is valid
            final int totalUniqueEndpoints = slots.endpoints().length;
            if (this.options.getMaxPoolSize() < totalUniqueEndpoints) {
              // this isn't a valid setup, the connection pool will not accommodate all the required connections
              onConnect.handle(Future.failedFuture("RedisOptions maxPoolSize < Cluster size(" + totalUniqueEndpoints + "): The pool is not able to hold all required connections!"));
              return;
            }

            for (String endpoint : slots.endpoints()) {
              connectionManager.getConnection(endpoint, RedisReplicas.NEVER != this.options.getUseReplicas() ? cmd(READONLY) : null)
                .onFailure(err -> {
                  // failed try with the next endpoint
                  failed.set(true);
                  connectionComplete(counter, slots, connections, failed, onConnect);
                })
                .onSuccess(cconn -> {
                  // there can be concurrent access to the connection map
                  // since this is a one time operation we can pay the penalty of
                  // synchronizing on each write (hopefully is only a few writes)
                  synchronized (connections) {
                    connections.put(endpoint, cconn);
                  }
                  connectionComplete(counter, slots, connections, failed, onConnect);
                });
            }
          });
        });
    });
  }

  private void connectionComplete(AtomicInteger counter, Slots slots, Map<String, RedisConnection> connections, AtomicBoolean failed, Handler<AsyncResult<RedisConnection>> onConnect) {
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
        onConnect.handle(Future.failedFuture("Failed to connect to all nodes of the cluster"));
      } else {
        onConnect.handle(Future.succeededFuture(new RedisClusterConnection(vertx, options, optionsSupplier, slots, connections)));
      }
    }
  }

  private void getSlots(String endpoint, RedisConnection conn, Handler<AsyncResult<Slots>> onGetSlots) {

    conn.send(cmd(CLUSTER).arg("SLOTS"), send -> {
      if (send.failed()) {
        // failed to load the slots from this connection
        onGetSlots.handle(Future.failedFuture(send.cause()));
        return;
      }

      final Response reply = send.result();

      if (reply == null || reply.size() == 0) {
        // no slots available we can't really proceed
        onGetSlots.handle(Future.failedFuture("SLOTS No slots available in the cluster."));
        return;
      }

      onGetSlots.handle(Future.succeededFuture(new Slots(endpoint, reply)));
    });
  }
}
