package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public final class SimpleStringType implements Response {

  public static Response create(String message) {
    return new SimpleStringType(message);
  }

  private final String message;

  private SimpleStringType(String message) {
    this.message = message;
  }

  public String getString() {
    return message;
  }
}
