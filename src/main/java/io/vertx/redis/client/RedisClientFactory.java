package io.vertx.redis.client;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.internal.CloseFuture;
import io.vertx.core.internal.ContextInternal;
import io.vertx.core.internal.VertxInternal;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.impl.CloseableRedis;

import java.util.function.Supplier;

class RedisClientFactory {

  static final String SHARED_REDIS_KEY = "__vertx.shared.redis";

  @FunctionalInterface
  interface ClientFactory<OPTS extends RedisConnectOptions> {
    Redis create(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                 Supplier<Future<OPTS>> connectOptions, TracingPolicy tracingPolicy,
                 CloseFuture closeFuture);
  }

  static <OPTS extends RedisConnectOptions> Redis create(
      Vertx vertx,
      NetClientOptions tcpOptions,
      PoolOptions poolOptions,
      Supplier<Future<OPTS>> connectOptions,
      TracingPolicy tracingPolicy,
      ClientFactory<OPTS> factory) {

    VertxInternal vx = (VertxInternal) vertx;
    CloseFuture closeFuture = new CloseFuture();
    Redis delegate;
    if (poolOptions.isShared()) {
      delegate = vx.createSharedResource(SHARED_REDIS_KEY, poolOptions.getName(), closeFuture,
        cf -> factory.create(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy, cf));
    } else {
      delegate = factory.create(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy, closeFuture);
    }
    Redis client = new CloseableRedis(vx, closeFuture, delegate);
    ContextInternal ctx = vx.getContext();
    if (ctx != null) {
      ctx.addCloseHook(closeFuture);
    } else {
      vx.addCloseHook(closeFuture);
    }
    return client;
  }
}
