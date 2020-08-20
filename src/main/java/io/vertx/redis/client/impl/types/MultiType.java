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

import java.util.*;

/**
 * A Redis MULTI response can represent a List/Set/Map type.
 */
public final class MultiType implements Multi {

  public static final MultiType EMPTY_MULTI = new MultiType(new Response[0], false);
  public static final MultiType EMPTY_MAP = new MultiType(new Response[0], true);

  public static MultiType create(long length, boolean asMap) {
    if (asMap) {
      return new MultiType(new Response[(int) length * 2], true);
    } else {
      return new MultiType(new Response[(int) length], length % 2 == 0);
    }
  }

  private final Map<String, Response> map;
  private final Response[] replies;
  private final boolean asMap;
  // mutable temporary state
  private int count;
  private String key;

  private MultiType(Response[] replies, boolean asMap) {
    this.replies = replies;
    this.count = 0;
    this.asMap = asMap;
    this.map = asMap ? new HashMap<>() : Collections.emptyMap();
  }

  @Override
  public ResponseType type() {
    return ResponseType.MULTI;
  }

  public void add(Response reply) {
    if (asMap) {
      if (count % 2 == 0) {
        switch (reply.type()) {
          case BULK:
          case SIMPLE:
            key = reply.toString();
            break;
          default:
            key = null;
        }
      } else {
        if (key != null) {
          map.put(key, reply);
        }
      }
    }
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
    if (asMap) {
      return map.get(key);
    }
    throw new RuntimeException("Number of key is not even");
  }

  @Override
  public Set<String> getKeys() {
    if (asMap) {
      return map.keySet();
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
        final Response value = replies[idx];

        if (asMap) {
          idx += 2;
        } else {
          idx++;
        }

        return value;
      }
    };
  }
}
