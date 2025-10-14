package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisClusterConnectOptions;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Exactly one instance of this class exists for each instance of {@link RedisClusterClient}
 * and is also shared between all {@link RedisClusterConnection}s obtained from that client.
 */
class SharedSlots {
  private static final Logger LOG = LoggerFactory.getLogger(SharedSlots.class);

  private final Vertx vertx;
  private final RedisClusterConnectOptions connectOptions;
  private final RedisConnectionManager connectionManager;

  private final AtomicReference<Future<Slots>> slots = new AtomicReference<>();

  SharedSlots(Vertx vertx, RedisClusterConnectOptions connectOptions, RedisConnectionManager connectionManager) {
    this.vertx = vertx;
    this.connectOptions = connectOptions;
    this.connectionManager = connectionManager;
  }

  Future<Slots> get() {
    while (true) {
      Future<Slots> slots = this.slots.get();
      if (slots != null) {
        return slots;
      }

      Promise<Slots> promise = Promise.promise();
      Future<Slots> future = promise.future();
      if (this.slots.compareAndSet(null, future)) {
        LOG.debug("Obtaining hash slot assignment");
        // attempt to load the slots from the first good endpoint
        getSlots(connectOptions.getEndpoints(), 0, ConcurrentHashMap.newKeySet(), promise);
        return future;
      }
    }
  }

  private void getSlots(List<String> endpoints, int index, Set<Throwable> failures, Handler<AsyncResult<Slots>> onGotSlots) {
    if (index >= endpoints.size()) {
      // stop condition
      StringBuilder message = new StringBuilder("Cannot connect to any of the provided endpoints");
      for (Throwable failure : failures) {
        message.append("\n- ").append(failure);
      }
      onGotSlots.handle(Future.failedFuture(new RedisConnectException(message.toString())));
      scheduleInvalidation();
      return;
    }

    connectionManager.getConnection(endpoints.get(index), RedisReplicas.NEVER != connectOptions.getUseReplicas() ? Request.cmd(Command.READONLY) : null)
      .onFailure(err -> {
        // try with the next endpoint
        failures.add(err);
        getSlots(endpoints, index + 1, failures, onGotSlots);
      })
      .onSuccess(conn -> {
        getSlots(endpoints.get(index), conn).onComplete(result -> {
          // the connection is not needed anymore, regardless of success or failure
          // (on success, we just finish, on failure, we'll try another endpoint)
          conn.close().onFailure(LOG::warn);

          if (result.failed()) {
            // the slots command failed, try with next endpoint
            failures.add(result.cause());
            getSlots(endpoints, index + 1, failures, onGotSlots);
          } else {
            Slots slots = result.result();
            onGotSlots.handle(Future.succeededFuture(slots));
            scheduleInvalidation();
          }
        });
      });
  }

  private Future<Slots> getSlots(String endpoint, RedisConnection conn) {
    return conn
      .send(Request.cmd(Command.CLUSTER).arg("SLOTS"))
      .compose(reply -> {
        if (reply == null || reply.size() == 0) {
          // no slots available we can't really proceed
          return Future.failedFuture("CLUSTER SLOTS No slots available in the cluster.");
        }

        Slots result;
        try {
          result = new Slots(endpoint, reply);
        } catch (Exception e) {
          return Future.failedFuture("CLUSTER SLOTS response invalid: " + e);
        }
        return Future.succeededFuture(result);
      });
  }

  void invalidate() {
    slots.set(null);
  }

  void scheduleInvalidation() {
    vertx.setTimer(connectOptions.getTopologyCacheTTL(), ignored -> invalidate());
  }
}
