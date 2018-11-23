package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.RedisOptions;

@FunctionalInterface
public interface Resolver {

  void resolve(Vertx vertx, SocketAddress endpoint, RedisOptions parameter, Handler<AsyncResult<SocketAddress>> callback);
}
