package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MultiType implements Response {

  public static final MultiType EMPTY = new MultiType(new Response[0]);

  public static Response create(int length) {
    return new MultiType(length);
  }
  public static Response create(Response[] replies) {
    return new MultiType(replies);
  }

  private final Response[] replies;
  private int count;

  private MultiType(int length) {
    this.replies = new Response[length];
    this.count = 0;
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
    return replies.length;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    sb.append('[');
    boolean first = true;
    for (Response r : replies) {
      if (first) {
        sb.append(", ");
        first = false;
      }

      if (r == null) {
        sb.append("null");
      } else {
        sb.append(r.toString());
      }
    }
    sb.append(']');

    return sb.toString();
  }

  @Override
  public Iterator<Response> iterator() {
    return new Iterator<Response>() {
      private int idx = 0;
      @Override
      public boolean hasNext() {
        return idx < replies.length;
      }

      @Override
      public Response next() {
        return replies[idx++];
      }
    };
  }

  @Override
  public Map<String, Response> toMap() {
    if (replies.length == 0) {
      return Collections.emptyMap();
    }

    final Map<String, Response> kv = new HashMap<>();
    for (int i = 0; i < replies.length; i+=2) {
      kv.put(replies[i].toString(), replies[i+1]);
    }

    return kv;
  }
}
