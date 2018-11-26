package io.vertx.redis.client.impl;

import io.vertx.redis.client.Response;

public interface ParserHandler {

  void handle(Response response);

  void fatal(Throwable t);

  void fail(Throwable t);
}
