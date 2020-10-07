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
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.NumberType;
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

public class RedisClusterClient implements Redis {

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    RedisClusterConnection.addReducer(command, fn);
  }

  public static void addUnSupportedCommand(Command command, String error) {
    RedisClusterConnection.addUnSupportedCommand(command, error);
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
      NumberType.create(list.stream().mapToLong(Response::toLong).sum()));

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
      NumberType.create(list.stream().mapToLong(Response::toLong).sum()));

    Arrays.asList(ASKING, AUTH, BGREWRITEAOF, BGSAVE, CLIENT, COMMAND, CONFIG,
      DEBUG, DISCARD, HOST, INFO, LASTSAVE, LATENCY, LOLWUT, MEMORY, MODULE, MONITOR, PFDEBUG, PFSELFTEST,
      PING, READONLY, READWRITE, REPLCONF, REPLICAOF, ROLE, SAVE, SCAN, SCRIPT, SELECT, SHUTDOWN, SLAVEOF, SLOWLOG, SWAPDB,
      SYNC, SENTINEL).forEach(command -> addUnSupportedCommand(command, null));

    addUnSupportedCommand(FLUSHALL, "RedisClusterClient does not handle command FLUSHALL, use FLUSHDB");

    addMasterOnlyCommand(WAIT);
  }

  private final Vertx vertx;
  private final Context context;
  private final RedisConnectionManager connectionManager;
  private final RedisOptions options;

  public RedisClusterClient(Vertx vertx, RedisOptions options) {
    this.vertx = vertx;
    this.context = vertx.getOrCreateContext();
    this.options = options;
    // validate options
    if (options.getMaxPoolWaiting() < options.getMaxPoolSize()) {
      throw new IllegalStateException("Invalid options: maxPoolWaiting < maxPoolSize");
      // we can't validate the max pool size yet as we need to know the slots first
      // the remaining validation will happen at the connect time
    }
    this.connectionManager = new RedisConnectionManager(vertx, options);
    this.connectionManager.start();
  }

  @Override
  public Redis connect(Handler<AsyncResult<RedisConnection>> onConnect) {
    // attempt to load the slots from the first good endpoint
    connect(options.getEndpoints(), 0, onConnect);
    return this;
  }

  private void connect(List<String> endpoints, int index, Handler<AsyncResult<RedisConnection>> onConnect) {
    if (index >= endpoints.size()) {
      // stop condition
      onConnect.handle(Future.failedFuture("Cannot connect to any of the provided endpoints"));
      return;
    }

    connectionManager.getConnection(context, endpoints.get(index), RedisReplicas.NEVER != options.getUseReplicas() ? cmd(READONLY) : null, getConnection -> {
      if (getConnection.failed()) {
        // failed try with the next endpoint
        connect(endpoints, index + 1, onConnect);
        return;
      }

      final RedisConnection conn = getConnection.result();

      // fetch slots from the cluster immediately to ensure slots are correct
      getSlots(endpoints.get(index), conn, getSlots -> {
        if (getSlots.failed()) {
          // the slots command failed.
          conn.close();
          // try with the next one
          connect(endpoints, index + 1, onConnect);
          return;
        }

        // slots are loaded (this connection isn't needed anymore)
        conn.close();
        // create a cluster connection
        final Slots slots = getSlots.result();
        final AtomicBoolean failed = new AtomicBoolean(false);
        final AtomicInteger counter = new AtomicInteger();
        final Map<String, RedisConnection> connections = new HashMap<>();

        // validate if the pool config is valid
        if (options.getMaxPoolSize() < slots.size()) {
          // this isn't a valid setup, the connection pool will not accommodate all the required connections
          onConnect.handle(Future.failedFuture("RedisOptions maxPoolSize < Cluster size(" + slots.size() + "): The pool is not able to hold all required connections!"));
          return;
        }

        for (String endpoint : slots.endpoints()) {
          connectionManager.getConnection(context, endpoint, RedisReplicas.NEVER != options.getUseReplicas() ? cmd(READONLY) : null, getClusterConnection -> {
            if (getClusterConnection.failed()) {
              // failed try with the next endpoint
              failed.set(true);
            } else {
              // there can be concurrent access to the connection map
              // since this is a one time operation we can pay the penalty of
              // synchronizing on each write (hopefully is only a few writes)
              synchronized (connections) {
                connections.put(endpoint, getClusterConnection.result());
              }
            }

            if (counter.incrementAndGet() == slots.endpoints().length) {
              // end condition
              if (failed.get()) {
                // cleanup

                // during an error we lock the map because we will change it
                // probably this isn't an issue as no more write should happen anyway
                synchronized (connections) {
                  connections.forEach((key, value) -> {
                    if (value != null) {
                      value.close();
                    }
                  });
                }
                // return
                onConnect.handle(Future.failedFuture("Failed to connect to all nodes of the cluster"));
              } else {
                onConnect.handle(Future.succeededFuture(new RedisClusterConnection(vertx, options, slots, connections)));
              }
            }
          });
        }
      });
    });
  }

  @Override
  public void close() {
    connectionManager.close();
  }

  private void getSlots(String endpoint, RedisConnection conn, Handler<AsyncResult<Slots>> onGetSlots) {

    conn.send(cmd(CLUSTER).arg("SLOTS"), send -> {
      if (send.failed()) {
        // failed to load the slots from this connection
        onGetSlots.handle(Future.failedFuture(send.cause()));
        return;
      }

      final Response reply = send.result();

      if (reply.size() == 0) {
        // no slots available we can't really proceed
        onGetSlots.handle(Future.failedFuture("SLOTS No slots available in the cluster."));
        return;
      }

      onGetSlots.handle(Future.succeededFuture(new Slots(endpoint, reply)));
    });
  }
}
