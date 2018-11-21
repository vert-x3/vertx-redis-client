package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public final class ArrayType implements Response {

  public static Response create(Response[] replies) {
    return new ArrayType(replies);
  }

  private final Response[] replies;

  private ArrayType(Response[] replies) {
    this.replies = replies;
  }

  @Override
  public Response get(int index) {
    return replies[index];
  }

  @Override
  public int size() {
    return replies.length;
  }
}
