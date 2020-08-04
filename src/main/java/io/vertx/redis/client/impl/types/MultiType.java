/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MultiType implements Response {

  public static final MultiType EMPTY = new MultiType(new Response[0]);

  public static MultiType create(int length) {
    return new MultiType(length);
  }

  public static MultiType create(Response[] replies) {
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
  public Response get(String key) {
    if (replies.length % 2 == 0) {
      for (int i = 0; i < replies.length; i += 2) {
        if (key.equals(replies[i].toString())) {
          return replies[i + 1];
        }
      }
    }
    throw new RuntimeException("Number of key is not even");
  }

  @Override
  public Set<String> getKeys() {
    if (replies.length % 2 == 0) {
      final Set<String> keys = new HashSet<>();
      for (int i = 0; i < replies.length; i += 2) {
        keys.add(replies[i].toString());
      }

      return keys;
    }
    throw new RuntimeException("Number of key is not even");
  }

  @Override
  public int size() {
    return replies.length;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    sb.append('[');
    boolean more = false;
    for (Response r : replies) {
      if (more) {
        sb.append(", ");
      }

      if (r == null) {
        sb.append("null");
      } else {
        sb.append(r.toString());
      }
      more = true;
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
}
