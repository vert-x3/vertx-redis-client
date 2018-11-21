package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public final class NullType implements Response {

  private static final NullType NULL = new NullType();

  public static Response create() {
    return NULL;
  }

  private NullType() {
  }

  @Override
  public boolean isNull() {
    return true;
  }
}
