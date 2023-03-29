package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.Collections;
import java.util.List;

public abstract class BaseRedisClient implements Redis {

  private static final Logger LOG = LoggerFactory.getLogger(BaseRedisClient.class);

  protected final VertxInternal vertx;
  protected final RedisConnectionManager connectionManager;

  public BaseRedisClient(Vertx vertx, RedisOptions options) {
    this.vertx = (VertxInternal) vertx;
    this.connectionManager = new RedisConnectionManager(this.vertx, options);
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
      return vertx.getOrCreateContext().failedFuture("PubSub command in connection-less mode not allowed");
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
      return vertx.getOrCreateContext().succeededFuture(Collections.emptyList());
    } else {
      for (Request req : requests) {
        final CommandImpl cmd = (CommandImpl) req.command();
        if (cmd.isPubSub()) {
          // mixing pubSub cannot be used on a one-shot operation
          return Future.failedFuture("PubSub command in connection-less batch not allowed");
        }
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
