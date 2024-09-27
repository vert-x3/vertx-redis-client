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

import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.PoolOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisReplicationConnectOptions;
import io.vertx.redis.client.RedisTopology;
import io.vertx.redis.client.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static io.vertx.redis.client.Command.INFO;
import static io.vertx.redis.client.Command.WAIT;
import static io.vertx.redis.client.Request.cmd;

public class RedisReplicationClient extends BaseRedisClient<RedisReplicationConnectOptions> implements Redis {

  private static final Logger LOG = LoggerFactory.getLogger(RedisReplicationClient.class);

  public static void addMasterOnlyCommand(Command command) {
    RedisReplicationConnection.addMasterOnlyCommand(command);
  }

  static class Node {
    private final RedisURI parent;
    final String ip;
    final int port;
    final boolean online;

    Node(RedisURI parent, String raw) {
      this.parent = parent;
      String[] parts = raw.split(",");
      String _ip = null;
      String _port = "6379";
      String _state = null;

      for (String part : parts) {
        int idx = part.indexOf('=');
        String key, value;
        if (idx != -1) {
          key = part.substring(0, idx);
          value = part.substring(idx + 1);
        } else {
          key = part;
          value = null;
        }

        switch (key) {
          case "ip":
            _ip = value;
            break;
          case "port":
            _port = value;
            break;
          case "state":
            _state = value;
            break;
        }
      }

      this.ip = _ip;
      this.port = Integer.parseInt(_port);
      this.online = "online".equals(_state);
    }

    String endpoint() {
      return parent.protocol() + "://" + parent.userinfo() + ip + ":" + port;
    }
  }

  static {
    addMasterOnlyCommand(WAIT);
  }

  public RedisReplicationClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, Supplier<Future<RedisReplicationConnectOptions>> connectOptions, TracingPolicy tracingPolicy) {
    super(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
    // validate options
    if (poolOptions.getMaxWaiting() < poolOptions.getMaxSize()) {
      throw new IllegalStateException("Invalid options: maxWaiting < maxSize");
    }
  }

  @Override
  public Future<RedisConnection> connect() {
    final Promise<RedisConnection> promise = vertx.promise();
    connectOptions.get()
      .onSuccess(opts -> doConnect(opts, promise))
      .onFailure(promise::fail);
    return promise.future();
  }

  private void doConnect(RedisReplicationConnectOptions connectOptions, Completable<RedisConnection> promise) {
    List<String> endpoints = connectOptions.getEndpoints();
    if (connectOptions.getTopology() == RedisTopology.DISCOVER) {
      // discover the topology from the first good endpoint
      // we make a copy of the list as we sometimes need to modify it during discovery
      connectWithDiscoverTopology(new LinkedList<>(endpoints), 0, ConcurrentHashMap.newKeySet(), connectOptions, promise);
    } else {
      // statically defined topology, first is master, rest are replicas
      connectWithStaticTopology(endpoints.get(0), endpoints.subList(1, endpoints.size()), connectOptions, promise);
    }
  }

  private void connectWithDiscoverTopology(List<String> endpoints, int index, Set<Throwable> failures, RedisReplicationConnectOptions connectOptions, Completable<RedisConnection> onConnect) {
    if (index >= endpoints.size()) {
      // stop condition
      StringBuilder message = new StringBuilder("Cannot connect to any of the provided endpoints");
      for (Throwable failure : failures) {
        message.append("\n- ").append(failure);
      }
      onConnect.fail(new RedisConnectException(message.toString()));
      return;
    }

    connectionManager.getConnection(endpoints.get(index), null)
      .onFailure(err -> {
        // failed try with the next endpoint
        failures.add(err);
        connectWithDiscoverTopology(endpoints, index + 1, failures, connectOptions, onConnect);
      })
      .onSuccess(conn -> {
        // fetch slots from the cluster immediately to ensure slots are correct
        getReplicas(conn, endpoints, index, (replicas, err) -> {
          if (err != null) {
            // the slots command failed.
            conn.close();
            // try with the next one
            failures.add(err);
            connectWithDiscoverTopology(endpoints, index + 1, failures, connectOptions, onConnect);
            return;
          }

          // create a cluster connection
          final AtomicInteger counter = new AtomicInteger();
          final List<PooledRedisConnection> replicaConnections = new ArrayList<>();

          for (Node replica : replicas) {
            if (!replica.online) {
              LOG.info("Skipping offline replica: " + replica.ip + ":" + replica.port);
              if (counter.incrementAndGet() == replicas.size()) {
                onConnect.succeed(new RedisReplicationConnection(vertx, connectOptions, conn, replicaConnections));
              }
              continue;
            }

            // we don't send `READONLY` setup to replica nodes, because that's a cluster-only command
            connectionManager.getConnection(replica.endpoint(), null)
              .onFailure(error -> {
                // failed try with the next endpoint
                LOG.warn("Skipping failed replica: " + replica.ip + ":" + replica.port, error);
                if (counter.incrementAndGet() == replicas.size()) {
                  onConnect.succeed(new RedisReplicationConnection(vertx, connectOptions, conn, replicaConnections));
                }
              })
              .onSuccess(replicaConnection -> {
                // there can be concurrent access to the connection map
                // since this is a one time operation we can pay the penalty of
                // synchronizing on each write (hopefully is only a few writes)
                synchronized (replicaConnections) {
                  replicaConnections.add(replicaConnection);
                }
                if (counter.incrementAndGet() == replicas.size()) {
                  onConnect.succeed(new RedisReplicationConnection(vertx, connectOptions, conn, replicaConnections));
                }
              });
          }
        });
      });
  }

  private void getReplicas(RedisConnection conn, List<String> endpoints, int index, Completable<List<Node>> onGetReplicas) {

    conn.send(cmd(INFO).arg("REPLICATION"))
      .onFailure(onGetReplicas::fail)
      .onSuccess(info -> {

        final Map<String, String> reply = parseInfo(info);

        if (reply.isEmpty()) {
          // no slots available we can't really proceed
          onGetReplicas.fail("INFO REPLICATION No config available in the node.");
          return;
        }

        // inherit protocol config from the current connection
        final RedisURI uri = new RedisURI(endpoints.get(index));

        switch (reply.get("role")) {
          case "master":
            try {
              int totalNodes = Integer.parseInt(reply.get("connected_slaves"));
              List<Node> nodes = new ArrayList<>(totalNodes);
              for (int i = 0; i < totalNodes; i++) {
                nodes.add(new Node(uri, reply.get("slave" + i)));
              }
              onGetReplicas.succeed(nodes);
              return;
            } catch (RuntimeException e) {
              onGetReplicas.fail(e);
              return;
            }
          case "slave":
            // extract the master info and if present add it to the current endpoints
            try {
              String masterHost = reply.get("master_host");
              String masterPort = reply.get("master_port");
              // push it to the list
              endpoints.add(index + 1, uri.protocol() + "://" + uri.userinfo() + masterHost + ":" + masterPort);
              // so it will be run on the next try
              onGetReplicas.fail("Connected to replica, retrying with master");
              return;
            } catch (RuntimeException e) {
              onGetReplicas.fail(e);
              return;
            }
          default:
            onGetReplicas.fail("INFO REPLICATION invalid role: " + reply.get("role"));
            break;
        }
      });
  }

  private void connectWithStaticTopology(String master, List<String> replicas, RedisReplicationConnectOptions connectOptions, Completable<RedisConnection> promise) {
    connectionManager.getConnection(master, null)
      .onFailure(error -> {
        promise.fail(new RedisConnectException("Cannot connect to statically configured master: " + master + "\n- " + error));
      })
      .onSuccess(masterConnection -> {
        AtomicInteger counter = new AtomicInteger();
        List<PooledRedisConnection> replicaConnections = new ArrayList<>();

        for (String replica : replicas) {
          // we don't send `READONLY` setup to replica nodes, because that's a cluster-only command
          connectionManager.getConnection(replica, null)
            .onFailure(err -> {
              LOG.warn("Skipping failed replica: " + replica, err);
              if (counter.incrementAndGet() == replicas.size()) {
                promise.succeed(new RedisReplicationConnection(vertx, connectOptions, masterConnection, replicaConnections));
              }
            })
            .onSuccess(replicaConnection -> {
              // there can be concurrent access to the connection map
              // since this is a one time operation we can pay the penalty of
              // synchronizing on each write (hopefully is only a few writes)
              synchronized (replicaConnections) {
                replicaConnections.add(replicaConnection);
              }
              if (counter.incrementAndGet() == replicas.size()) {
                promise.succeed(new RedisReplicationConnection(vertx, connectOptions, masterConnection, replicaConnections));
              }
            });
        }
      });
  }

  private Map<String, String> parseInfo(Response response) {
    if (response == null) {
      return Collections.emptyMap();
    }

    String text = response.toString(StandardCharsets.ISO_8859_1);
    if (text == null || text.isEmpty()) {
      return Collections.emptyMap();
    }

    String[] lines = text.split("\r\n");
    Map<String, String> info = new HashMap<>();
    for (String line : lines) {
      int idx = line.indexOf(':');
      if (idx != -1) {
        info.put(line.substring(0, idx), line.substring(idx + 1));
      } else {
        info.put(line, null);
      }
    }
    return info;
  }
}
