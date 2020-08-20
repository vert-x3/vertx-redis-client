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

import java.util.Iterator;

public final class PushType implements Multi {

  public static PushType create(long length) {
    return new PushType(new Response[(int) length]);
  }

  private final Response[] replies;
  // mutable temporary state
  private int count;

  private PushType(Response[] replies) {
    this.replies = replies;
    this.count = 0;
  }

  @Override
  public ResponseType type() {
    return ResponseType.PUSH;
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
