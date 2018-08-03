package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.net.SocketAddress;

@FunctionalInterface
public interface Resolver {

  void resolve(SocketAddress endpoint, String parameter, Handler<AsyncResult<SocketAddress>> callback);
}
