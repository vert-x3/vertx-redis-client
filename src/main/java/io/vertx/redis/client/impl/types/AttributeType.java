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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A Redis MULTI response can represent a List/Set/Map type.
 */
public final class AttributeType implements Multi {

  public static AttributeType create(long length) {
    return new AttributeType(new Response[(int) length * 2]);
  }

  private final Map<String, Response> map;
  private final Response[] replies;
  // mutable temporary state
  private int count;
  private String key;

  private AttributeType(Response[] replies) {
    this.replies = replies;
    this.count = 0;
    this.map = new HashMap<>();
  }

  @Override
  public ResponseType type() {
    return ResponseType.ATTRIBUTE;
  }

  public void add(Response reply) {
    if (count % 2 == 0) {
      key = reply.toString();
    } else {
      if (key != null) {
        map.put(key, reply);
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
    return map.get(key);
  }

  @Override
  public boolean containsKey(String key) {
    return map.containsKey(key);
  }

  @Override
  public Set<String> getKeys() {
    return map.keySet();
  }

  @Override
  public int size() {
    return replies.length;
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public boolean isMap() {
    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    sb.append('{');
    boolean more = false;
    for (Map.Entry<String, Response> kv : map.entrySet()) {
      if (more) {
        sb.append(", ");
      }

      String key = kv.getKey();

      if (key == null) {
        sb.append("null");
      } else {
        sb.append(key);
      }

      sb.append(':');

      Response value = kv.getValue();

      if (value == null) {
        sb.append("null");
      } else {
        sb.append(value);
      }

      more = true;
    }
    sb.append('}');

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

        idx += 2;

        return value;
      }
    };
  }
}
