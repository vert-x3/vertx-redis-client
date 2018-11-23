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
  public long int64() {
    return value;
  }

  @Override
  public int int32() {
    return value.intValue();
  }

  @Override
  public short int16() {
    return value.shortValue();
  }

  @Override
  public byte int8() {
    return value.byteValue();
  }

  @Override
  public boolean bool() {
    return value == 1L;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
