package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.redis.impl.RedisPoolImpl;

@VertxGen
public interface RedisPool extends RedisConnection {

  static RedisPool create(Vertx vertx) {
    return create(vertx, new RedisPoolOptions());
  }

  static RedisPool create(Vertx vertx, RedisPoolOptions options) {
    return new RedisPoolImpl(vertx, options);
  }

  static RedisPool createShared(Vertx vertx) {
    return createShared(vertx, new RedisPoolOptions());
  }

  static RedisPool createShared(Vertx vertx, RedisPoolOptions options) {
    return new RedisPoolImpl(vertx, options, "shared");
  }
}
