package io.vertx.redis.client.impl;

import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisSentinelConnectOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Exactly one instance of this class exists for each instance of {@link RedisSentinelClient}.
 */
class SharedSentinelTopology {
  private static final Logger LOG = LoggerFactory.getLogger(SharedSentinelTopology.class);

  private final Vertx vertx;
  private final Supplier<Future<RedisSentinelConnectOptions>> connectOptions;
  private final RedisConnectionManager connectionManager;

  private final AtomicReference<Future<SentinelTopology>> topology = new AtomicReference<>();

  SharedSentinelTopology(Vertx vertx, Supplier<Future<RedisSentinelConnectOptions>> connectOptions, RedisConnectionManager connectionManager) {
    this.vertx = vertx;
    this.connectOptions = connectOptions;
    this.connectionManager = connectionManager;
  }

  Future<SentinelTopology> get() {
    while (true) {
      Future<SentinelTopology> topology = this.topology.get();
      if (topology != null) {
        return topology;
      }

      Promise<SentinelTopology> promise = Promise.promise();
      Future<SentinelTopology> future = promise.future();
      if (this.topology.compareAndSet(null, future)) {
        LOG.debug("Obtaining sentinel topology");
        // attempt to load the topology from the first good endpoint
        connectOptions.get()
          .onSuccess(opts -> getTopology(opts, 0, ConcurrentHashMap.newKeySet(), promise))
          .onFailure(promise::fail);
        return future;
      }
    }
  }

  private void getTopology(RedisSentinelConnectOptions connectOptions, int index, Set<Throwable> failures, Completable<SentinelTopology> onGotTopology) {
    List<String> endpoints = connectOptions.getEndpoints();
    if (index >= endpoints.size()) {
      // stop condition
      StringBuilder message = new StringBuilder("Cannot connect to any of the provided endpoints");
      for (Throwable failure : failures) {
        message.append("\n- ").append(failure);
      }
      onGotTopology.fail(new RedisConnectException(message.toString()));
      scheduleInvalidation(connectOptions);
      return;
    }

    RedisURI uri = new RedisURI(endpoints.get(index));
    connectionManager.getConnection(uri.baseUri(), null)
      .onFailure(err -> {
        // try with the next endpoint
        failures.add(err);
        getTopology(connectOptions, index + 1, failures, onGotTopology);
      })
      .onSuccess(conn -> {
        getTopology(conn, uri, connectOptions.getMasterName()).onComplete(result -> {
          // the connection is not needed anymore, regardless of success or failure
          // (on success, we just finish, on failure, we'll try another endpoint)
          conn.close().onFailure(LOG::warn);

          if (result.failed()) {
            // one of the `SENTINEL ...` commands failed, try with next endpoint
            failures.add(result.cause());
            getTopology(connectOptions, index + 1, failures, onGotTopology);
          } else {
            SentinelTopology topology = result.result();
            onGotTopology.succeed(topology);
            scheduleInvalidation(connectOptions);
          }
        });
      });
  }

  private Future<SentinelTopology> getTopology(RedisConnection conn, RedisURI uri, String masterName) {
    return conn
      .batch(List.of(
        Request.cmd(Command.SENTINEL).arg("SENTINELS").arg(masterName),
        Request.cmd(Command.SENTINEL).arg("GET-MASTER-ADDR-BY-NAME").arg(masterName),
        Request.cmd(Command.SENTINEL).arg("SLAVES").arg(masterName)))
      .compose(replies -> {
        if (replies == null || replies.size() != 3) {
          return Future.failedFuture("Could not obtain sentinel topology");
        }

        List<RedisURI> sentinels = new ArrayList<>();
        // `SENTINEL SENTINELS <master set>` doesn't return the "current" sentinel we're connected to
        sentinels.add(uri);
        String failure = parseUris(uri, replies.get(0), sentinels, "SENTINELS " + masterName);
        if (failure != null) {
          return Future.failedFuture(failure);
        }

        RedisURI master;
        Response masterResponse = replies.get(1);
        if (masterResponse == null) {
          return Future.failedFuture("Failed to get SENTINEL GET-MASTER-ADDR-BY-NAME " + masterName);
        } else {
          String ip = masterResponse.get(0).toString();
          Integer port = masterResponse.get(1).toInteger();
          master = new RedisURI(uri, ip.contains(":") ? "[" + ip + "]" : ip, port);
        }

        List<RedisURI> replicas = new ArrayList<>();
        failure = parseUris(uri, replies.get(2), replicas, "REPLICAS " + masterName);
        if (failure != null) {
          return Future.failedFuture(failure);
        }

        return Future.succeededFuture(
          new SentinelTopology(sentinels.toArray(RedisURI[]::new), master, replicas.toArray(RedisURI[]::new)));
      });
  }

  // `uri` and `hostsResponse` are input
  // `uris` is output
  // `command` is used only to construct an error message, if necessary
  // returns a `String` describing an error, or `null` if successful
  private static String parseUris(RedisURI uri, Response hostsResponse, List<RedisURI> uris, String command) {
    if (hostsResponse == null) {
      return "Failed to get SENTINEL " + command;
    }

    for (int i = 0; i < hostsResponse.size(); i++) {
      Response hostResponse = hostsResponse.get(i);
      if (hostResponse.size() % 2 != 0) {
        return "Corrupted response " + i + " from SENTINEL " + command;
      } else {
        String ip = null;
        int port = 6379;

        if (hostResponse.containsKey("ip")) {
          ip = hostResponse.get("ip").toString();
        }
        if (hostResponse.containsKey("port")) {
          port = hostResponse.get("port").toInteger();
        }

        if (ip == null) {
          return "No IP found in response " + i + " from SENTINEL " + command;
        } else {
          String host = ip.contains(":") ? "[" + ip + "]" : ip;
          uris.add(new RedisURI(uri, host, port));
        }
      }
    }

    return null;
  }

  void invalidate() {
    topology.set(null);
  }

  private void scheduleInvalidation(RedisSentinelConnectOptions connectOptions) {
    vertx.setTimer(connectOptions.getTopologyCacheTTL(), ignored -> invalidate());
  }
}
