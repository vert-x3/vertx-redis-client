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

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

public class RedisReplicationClient extends BaseRedisClient implements Redis {

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

  public RedisReplicationClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier) {
    super(vertx, options, optionsSupplier);
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
      // make a copy as we may need to mutate the list during discovery
      final List<String> endpoints = new LinkedList<>(options.getEndpoints());
      // attempt to discover the topology from the first good endpoint
      connect(endpoints, 0, promise);
      return promise.future();
    });
  }

  private void connect(List<String> endpoints, int index, Handler<AsyncResult<RedisConnection>> onConnect) {
    if (index >= endpoints.size()) {
      // stop condition
      onConnect.handle(Future.failedFuture("Cannot connect to any of the provided endpoints"));
      return;
    }

    connectionManager.getConnection(endpoints.get(index), null)
      .onFailure(err -> {
        // failed try with the next endpoint
        connect(endpoints, index + 1, onConnect);
      })
      .onSuccess(conn -> {
        // fetch slots from the cluster immediately to ensure slots are correct
        getNodes(conn, endpoints, index, getNodes -> {
          if (getNodes.failed()) {
            // the slots command failed.
            conn.close();
            // try with the next one
            connect(endpoints, index + 1, onConnect);
            return;
          }

          // create a cluster connection
          final List<Node> nodes = getNodes.result();
          final AtomicInteger counter = new AtomicInteger();
          final List<RedisConnection> connections = new ArrayList<>();

          // validate if the pool config is valid
          final int totalUniqueEndpoints = nodes.size();
          if (options.getMaxPoolSize() < totalUniqueEndpoints) {
            // this isn't a valid setup, the connection pool will not accommodate all the required connections
            onConnect.handle(Future.failedFuture("RedisOptions maxPoolSize < Cluster size(" + totalUniqueEndpoints + "): The pool is not able to hold all required connections!"));
            return;
          }

          for (Node node : nodes) {
            if (!node.online) {
              LOG.info("Skipping offline node: " + node.ip);
              if (counter.incrementAndGet() == nodes.size()) {
                onConnect.handle(Future.succeededFuture(
                  new RedisReplicationConnection(vertx, optionsSupplier, conn, connections))
                );
              }
              continue;
            }

            connectionManager.getConnection(node.endpoint(), RedisReplicas.NEVER != options.getUseReplicas() ? cmd(READONLY) : null)
              .onFailure(err -> {
                // failed try with the next endpoint
                LOG.warn("Skipping failed node: " + node.ip, err);
                if (counter.incrementAndGet() == nodes.size()) {
                  onConnect.handle(Future.succeededFuture(
                    new RedisReplicationConnection(vertx, optionsSupplier, conn, connections))
                  );
                }
              })
              .onSuccess(cconn -> {
                // there can be concurrent access to the connection map
                // since this is a one time operation we can pay the penalty of
                // synchronizing on each write (hopefully is only a few writes)
                synchronized (connections) {
                  connections.add(cconn);
                }
                if (counter.incrementAndGet() == nodes.size()) {
                  onConnect.handle(Future.succeededFuture(
                    new RedisReplicationConnection(vertx, optionsSupplier, conn, connections))
                  );
                }
              });
          }
        });
      });
  }

  private void getNodes(RedisConnection conn, List<String> endpoints, int index, Handler<AsyncResult<List<Node>>> onGetSlots) {

    conn.send(cmd(INFO).arg("REPLICATION"), send -> {
      if (send.failed()) {
        // failed to load the slots from this connection
        onGetSlots.handle(Future.failedFuture(send.cause()));
        return;
      }

      final Map<String, String> reply = parseInfo(send.result());

      if (reply.size() == 0) {
        // no slots available we can't really proceed
        onGetSlots.handle(Future.failedFuture("INFO REPLICATION No config available in the node."));
        return;
      }

      // inherit protocol config from the current connection
      final RedisURI uri = new RedisURI(endpoints.get(index));

      switch (reply.get("role")) {
        case "master":
          try {
            List<Node> nodes = new ArrayList<>();
            int totalNodes = Integer.parseInt(reply.get("connected_slaves"));
            for (int i = 0; i < totalNodes; i++) {
              nodes.add(new Node(uri, reply.get("slave" + i)));
            }
            onGetSlots.handle(Future.succeededFuture(nodes));
            return;
          } catch (RuntimeException e) {
            onGetSlots.handle(Future.failedFuture(e));
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
            onGetSlots.handle(Future.failedFuture("Connected to replica, retrying with master"));
            return;
          } catch (RuntimeException e) {
            onGetSlots.handle(Future.failedFuture(e));
            return;
          }
        default:
          onGetSlots.handle(Future.failedFuture("INFO REPLICATION invalid role: " + reply.get("role")));
          break;
      }
    });
  }

  private Map<String, String> parseInfo(Response response) {
    if (response == null) {
      return Collections.emptyMap();
    }

    String text = response.toString(StandardCharsets.ISO_8859_1);
    if (text == null || text.length() == 0) {
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
