package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

public final class MultiType implements Response {

  public static final MultiType NULL = new MultiType(-1);
  public static final MultiType EMPTY = new MultiType(0);

  public static Response create(int length) {
    return new MultiType(length);
  }
  public static Response create(Response[] replies) {
    return new MultiType(replies);
  }

  private final Response[] replies;
  private int count;

  private MultiType(int length) {
    if (length == -1) {
      this.replies = null;
      this.count = -1;
    } else {
      this.replies = new Response[length];
      this.count = 0;
    }
  }

  private MultiType(Response[] replies) {
    this.replies = replies;
    this.count = replies.length;
  }

  @Override
  public ResponseType type() {
    return ResponseType.MULTI;
  }

  public void add(Response reply) {
    this.replies[this.count++] = reply;
  }

  public boolean complete() {
    return count == replies.length;
  }

  @Override
  public Response get(int index) {
    return replies[index];
  }

  @Override
  public int size() {
    if (replies == null) {
      return -1;
    }
    return replies.length;
  }

  @Override
  public boolean isNull() {
    return replies == null;
  }

  @Override
  public String toString() {
    return "[" + replies + "]";
  }

}
