package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

public final class SimpleStringType implements Response {

  public static Response create(String message) {
    return new SimpleStringType(message);
  }

  private final String message;

  private SimpleStringType(String message) {
    this.message = message;
  }

  @Override
  public ResponseType type() {
    return ResponseType.SIMPLE;
  }

  @Override
  public String string() {
    return message;
  }

  @Override
  public String toString() {
    return message;
  }
}
