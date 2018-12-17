package io.vertx.redis.client.impl;

import io.vertx.core.Future;
import io.vertx.redis.client.*;

public class RedisAPIImpl implements RedisAPI {

  private final Redis client;

  public RedisAPIImpl(Redis client) {
    this.client = client;
  }

  @Override
  public Future<Response> send(Command cmd, Object... args) {
    final Future<Response> future = Future.future();
    final Request req = Request.cmd(cmd);

    if (args != null) {
      for (Object o : args) {
        if (o == null) {
          req.nullArg();
        } else {
          req.arg(o.toString());
        }
      }
    }

    client.send(req, future.completer());
    return future;
  }
}
