package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.*;

import java.util.Collections;
import java.util.List;

public abstract class BaseRedisClient implements Redis {

  private static final Logger LOG = LoggerFactory.getLogger(BaseRedisClient.class);

  protected final VertxInternal vertx;
  protected final RedisConnectionManager connectionManager;

  public BaseRedisClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, RedisConnectOptions connectOptions, TracingPolicy tracingPolicy) {
    this.vertx = (VertxInternal) vertx;
    this.connectionManager = new RedisConnectionManager(this.vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
    this.connectionManager.start();
  }

  @Override
  public void close() {
    this.connectionManager.close();
  }

  @Override
  public Future<@Nullable Response> send(Request request) {
    final CommandImpl cmd = (CommandImpl) request.command();
    if (cmd.isPubSub()) {
      // mixing pubSub cannot be used on a one-shot operation
      return Future.failedFuture("PubSub command in connection-less mode not allowed");
    } else if (cmd.isTransactional()) {
      return vertx.getOrCreateContext().failedFuture("Transactional command in connection-less mode not allowed");
    }

    return connect()
      .compose(conn ->
        conn.send(request)
          // regardless of the result, return the connection to the pool
          .eventually(e ->
            conn.close()
              .onFailure(LOG::warn)));
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> requests) {
    if (requests.isEmpty()) {
      LOG.debug("Empty batch");
      return Future.succeededFuture(Collections.emptyList());
    } else {
      for (Request req : requests) {
        final CommandImpl cmd = (CommandImpl) req.command();
        if (cmd.isPubSub()) {
          // mixing pubSub cannot be used on a one-shot operation
          return Future.failedFuture("PubSub command in connection-less batch not allowed");
        }
        // someone might expect that for symmetry with `send()`, we'll also check the commands here
        // and fail if any of them is transactional, but that would be wrong -- a batch is always
        // executed on a single connection and can therefore contain the whole transaction
      }

      return connect()
        .compose(conn ->
          conn.batch(requests)
            // regardless of the result, return the connection to the pool
            .eventually(e ->
                conn.close().onFailure(LOG::warn)));
    }
  }
}
