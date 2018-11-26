package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

public final class IntegerType implements Response {

  public static Response create(Long value) {
    return new IntegerType(value);
  }

  private final Long value;

  private IntegerType(long value) {
    this.value = value;
  }

  @Override
  public ResponseType type() {
    return ResponseType.INTEGER;
  }

  @Override
  public Long toLong() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
