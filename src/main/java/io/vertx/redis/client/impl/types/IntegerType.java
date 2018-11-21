package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public final class IntegerType implements Response {

  public static Response create(Long value) {
    return new IntegerType(value);
  }

  private final Long value;

  private IntegerType(long value) {
    this.value = value;
  }

  @Override
  public long getLong() {
    return value;
  }

  @Override
  public int getInteger() {
    return value.intValue();
  }

  @Override
  public short getShort() {
    return value.shortValue();
  }

  @Override
  public byte getByte() {
    return value.byteValue();
  }

  @Override
  public boolean getBoolean() {
    return value == 1L;
  }
}
