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

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A Redis MULTI response can represent a List/Set/Map type.
 * <p>
 * Not thread safe.
 */
public final class MultiType implements Multi {
  // shorthand aliases for better readability
  private static final Function<Response, String> AS_STRING = Response::toString;
  private static final Function<Response, Buffer> AS_BIN = Response::toBuffer;

  public static final MultiType EMPTY_MULTI = new MultiType(0, false);
  public static final MultiType EMPTY_MAP = new MultiType(0, true);

  public static MultiType create(long length, boolean asMap) {
    return new MultiType((int) length, asMap);
  }

  // elements of this Multi are stored in "multi" array, and corresponding
  // maps are built on demand
  private final Response[] multi;
  private final boolean isMap;
  private Map<String, Response> strMap;
  private Map<Buffer, Response> binMap;
  // mutable temporary state
  private int count = 0;

  private MultiType(int size, boolean asMap) {
    this.isMap = asMap;
    // if asMap==true, then the "size" is the number of key-value pairs,
    // so we need to allocate double size for the array
    this.multi = new Response[asMap ? size * 2 : size];
  }

  private MultiType(Response key, Response value) {
    this.isMap = true;
    this.multi = new Response[]{key, value};
  }

  @Override
  public ResponseType type() {
    return ResponseType.MULTI;
  }

  @Override
  public void add(Response reply) {
    this.multi[this.count] = reply;
    count++;
  }

  public boolean complete() {
    return count == multi.length;
  }

  @Override
  public Response get(int index) {
    if (isMap) {
      throw new RuntimeException("Multi is a Map");
    }
    return multi[index];
  }

  @Override
  public @Nullable Response get(String key) {
    if (isMap) {
      buildStrMapIfNeeded();
      return strMap.get(key);
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(key, AS_STRING);
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public boolean containsKey(String key) {
    if (isMap) {
      buildStrMapIfNeeded();
      return strMap.containsKey(key);
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(key, AS_STRING) != null;
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Set<String> getKeys() {
    if (isMap) {
      buildStrMapIfNeeded();
      return strMap.keySet();
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return getKeysOfMultiArray(AS_STRING);
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Response get(Buffer binKey) {
    if (isMap) {
      buildBinMapIfNeeded();
      return binMap.get(binKey);
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(binKey, AS_BIN);
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public boolean containsKey(Buffer binKey) {
    if (isMap) {
      buildBinMapIfNeeded();
      return binMap.containsKey(binKey);
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(binKey, AS_BIN) != null;
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Set<Buffer> getBinaryKeys() {
    if (isMap) {
      buildBinMapIfNeeded();
      return binMap.keySet();
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return getKeysOfMultiArray(AS_BIN);
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public int size() {
    return multi.length;
  }

  @Override
  public boolean isArray() {
    return !isMap;
  }

  @Override
  public boolean isMap() {
    return isMap;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    if (!isMap) {
      sb.append('[');
      boolean more = false;
      for (Response r : multi) {
        if (more) {
          sb.append(", ");
        }

        if (r == null) {
          sb.append("null");
        } else {
          sb.append(r);
        }
        more = true;
      }
      sb.append(']');
    } else {
      // if binMap is built, then the caller interprets keys as binary
      if (binMap != null) {
        // encode buffers in base64 ready for usage in JSON
        mapToString(sb, binMap, Buffer::toJson);
      } else {
        buildStrMapIfNeeded();
        mapToString(sb, strMap, Function.identity());
      }
    }

    return sb.toString();
  }

  @Override
  public Iterator<Response> iterator() {
    if (!isMap) {
      return new Iterator<Response>() {
        private int idx = 0;

        @Override
        public boolean hasNext() {
          return idx < multi.length;
        }

        @Override
        public Response next() {
          return multi[idx++];
        }
      };
    } else {
      return new Iterator<Response>() {
        private final Iterator<Response> it = Arrays.stream(multi).iterator();

        @Override
        public boolean hasNext() {
          return it.hasNext();
        }

        @Override
        public Response next() {
          // wrap the kv into a single multi response
          return new MultiType(it.next(), it.next());
        }
      };
    }
  }

  private boolean canInterpretMultiArrayAsMap() {
    // if the size is even, we assume we can handle it as Map
    return multi.length % 2 == 0;
  }

  private <K> Set<K> getKeysOfMultiArray(Function<Response, K> converter) {
    Set<K> convertedKeys = new LinkedHashSet<>();
    // if the size is even, we assume we can handle it as Map
    for (int i = 0; i < multi.length; i += 2) {
      switch (multi[i].type()) {
        case BULK:
        case SIMPLE:
          convertedKeys.add(converter.apply(multi[i]));
          break;
      }
    }
    return convertedKeys;
  }

  private <K> Response findInMultiArray(K key, Function<Response, K> converter) {
    for (int i = 0; i < multi.length; i += 2) {
      K respInKeyFormat = converter.apply(multi[i]);
      if (key.equals(respInKeyFormat)) {
        return multi[i + 1];
      }
    }
    // not found
    return null;
  }

  private void buildStrMapIfNeeded() {
    if (!isMap || strMap != null) {
      return;
    }
    // redis tracks the order of entries in hashes, for some use-cases it may
    // be important to preserve it, so we use LinkedHashMap
    strMap = new LinkedHashMap<>(multi.length / 2, 1.0f);
    for (int i = 0; i < multi.length; i += 2) {
      // Response.toString() triggers the conversion of the response to
      // UTF-8 String
      strMap.put(multi[i].toString(), multi[i + 1]);
    }
  }

  private void buildBinMapIfNeeded() {
    if (!isMap || binMap != null) {
      return;
    }
    // redis tracks the order of entries in hashes, for some use-cases it may
    // be important to preserve it, so we use LinkedHashMap
    binMap = new LinkedHashMap<>(multi.length / 2, 1.0f);
    for (int i = 0; i < multi.length; i += 2) {
      // Response.toBuffer() triggers the conversion of the response to Buffer
      binMap.put(multi[i].toBuffer(), multi[i + 1]);
    }
  }

  private <T> void mapToString(StringBuilder sb, Map<T, Response> mapToUse,
    Function<T, String> keyToString) {
    sb.append('{');
    boolean more = false;
    for (Map.Entry<T, Response> kv : mapToUse.entrySet()) {
      if (more) {
        sb.append(", ");
      }

      sb.append(keyToString.apply(kv.getKey()));
      sb.append(": ");
      sb.append(kv.getValue());
      more = true;
    }
    sb.append('}');
  }
}
