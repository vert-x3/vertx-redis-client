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

  public static final MultiType EMPTY_MULTI = new MultiType(0, false);
  public static final MultiType EMPTY_MAP = new MultiType(0, true);

  public static MultiType create(long length, boolean asMap) {
    if (asMap) {
      return new MultiType((int) length * 2, true);
    } else {
      return new MultiType((int) length, false);
    }
  }

  // only one of these will be not null
  private final Map<String, Response> map;
  private final Response[] multi;
  // the expected size
  private final int size;
  // mutable temporary state
  private int count;
  private String key;

  private MultiType(int size, boolean asMap) {
    if (asMap) {
      this.multi = null;
      this.map = new HashMap<>(size, 1.0f);
    } else {
      this.multi = new Response[size];
      this.map = null;
    }
    this.size = size;
    this.count = 0;
  }

  @Override
  public ResponseType type() {
    return ResponseType.MULTI;
  }

  @Override
  public void add(Response reply) {
    // if this Multi was created as a Map
    if (map != null) {
      if (count % 2 == 0) {
        switch (reply.type()) {
          case BULK:
          case SIMPLE:
            key = reply.toString();
            break;
          default:
            throw new IllegalArgumentException("Map key is not BULK or SIMPLE");
        }
      } else {
        if (key != null) {
          map.put(key, reply);
        }
      }
    }
    // if this Multi was created as a Collection
    if (multi != null) {
      this.multi[this.count] = reply;
    }
    // increment the counter
    count++;
  }

  public boolean complete() {
    return count == size;
  }

  @Override
  public Response get(int index) {
    if (multi != null) {
      return multi[index];
    }
    throw new RuntimeException("Multi is a Map");
  }

  @Override
  public Response get(String key) {
    if (map != null) {
      return map.get(key);
    }

    if (multi != null) {
      // fallback (emulate old behavior)
      if (multi.length % 2 == 0) {
        // if the size is even we assume we can handle it as Map
        for (int i = 0; i < multi.length; i+=2) {
          if (key.equals(multi[i].toString())) {
            return multi[i+1];
          }
        }
        // not found
        return null;
      }
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public boolean containsKey(String key) {
    if (map != null) {
      return map.containsKey(key);
    }

    if (multi != null) {
      // fallback (emulate old behavior)
      if (multi.length % 2 == 0) {
        // if the size is even we assume we can handle it as Map
        for (int i = 0; i < multi.length; i+=2) {
          if (key.equals(multi[i].toString())) {
            return true;
          }
        }
        // not found
        return false;
      }
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Set<String> getKeys() {
    if (map != null) {
      return map.keySet();
    }

    if (multi != null) {
      // fallback (emulate old behavior)
      if (multi.length % 2 == 0) {
        final Set<String> keys = new HashSet<>();
        // if the size is even we assume we can handle it as Map
        for (int i = 0; i < multi.length; i+=2) {
          switch (multi[i].type()) {
            case BULK:
            case SIMPLE:
              keys.add(multi[i].toString());
              break;
          }
        }

        return keys;
      }
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    if (multi != null) {
      sb.append('[');
      boolean more = false;
      for (Response r : multi) {
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
    }

    if (map != null) {
      sb.append('{');
      boolean more = false;
      for (Map.Entry<String, Response> kv : map.entrySet()) {
        if (more) {
          sb.append(", ");
        }

        sb.append(kv.getKey());
        sb.append(": ");
        sb.append(kv.getValue().toString());
        more = true;
      }
      sb.append('}');
    }

    return sb.toString();
  }

  @Override
  public Iterator<Response> iterator() {
    return new Iterator<Response>() {
      private int idx = 0;

      @Override
      public boolean hasNext() {
        if (map != null) {
          return false;
        }
        if (multi != null) {
          return idx < size;
        }
        return false;
      }

      @Override
      public Response next() {
        if (multi != null) {
          return multi[idx++];
        }
        throw new NoSuchElementException();
      }
    };
  }
}
