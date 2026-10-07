package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.internal.CloseFuture;
import io.vertx.core.internal.ContextInternal;
import io.vertx.core.internal.VertxInternal;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;

public class CloseableRedis implements Redis {

  private final VertxInternal vertx;
  private final CloseFuture closeFuture;
  private final Redis delegate;

  public CloseableRedis(VertxInternal vertx, CloseFuture closeFuture, Redis delegate) {
    this.vertx = vertx;
    this.closeFuture = closeFuture;
    this.delegate = delegate;
  }

  public Redis delegate() {
    return delegate;
  }

  @Override
  public Future<RedisConnection> connect() {
    return delegate.connect();
  }

  @Override
  public Future<Void> close() {
    ContextInternal closingCtx = vertx.getOrCreateContext();
    Promise<Void> promise = closingCtx.promise();
    closeFuture.close(promise);
    return promise.future();
  }

  @Override
  public Future<@Nullable Response> send(Request command) {
    return delegate.send(command);
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    return delegate.batch(commands);
  }
}
