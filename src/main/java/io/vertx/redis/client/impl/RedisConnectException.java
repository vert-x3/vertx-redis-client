package io.vertx.redis.client.impl;

import java.net.ConnectException;

/**
 * Used to signal connection failure in certain Redis clients that don't propagate underlying
 * exceptions directly, but use a custom logic. Intentionally a subclass of {@code ConnectException},
 * so that users of Redis clients can handle connection failures uniformly.
 */
class RedisConnectException extends ConnectException {
  RedisConnectException(String msg) {
    super(msg);
  }

  @Override
  public Throwable fillInStackTrace() {
    return this;
  }
}
