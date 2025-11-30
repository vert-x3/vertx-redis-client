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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * A Redis MULTI response can represent a List/Set/Map type.
 */
public final class MultiType implements Multi {
  // shorthand aliases for better readability
  private static final Function<Response, String> AS_STRING = Response::toString;
  private static final Function<Response, Buffer> AS_BIN = Response::toBuffer;

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
  private final Map<StrKey, Response> strMap;
  private final Response[] multi;
  private Map<BinKey, Response> binMap; // evaluated when binary keys are used
  // the expected size
  private final int size;
  // mutable temporary state
  private int count;
  private StrKey key;

  private MultiType(int size, boolean asMap) {
    if (asMap) {
      this.multi = null;
      // redis tracks the order of entries in hashes, for some use-cases it may
      // be important to preserve it, so we use LinkedHashMap
      this.strMap = new LinkedHashMap<>(size, 1.0f);
    } else {
      this.multi = new Response[size];
      this.strMap = null;
    }
    this.size = size;
    this.count = 0;
  }

  private MultiType(WrappedResp wrappedKey, Response value) {
    this.strMap = null;
    this.multi = new Response[]{wrappedKey.rawResp(), value};
    this.size = 2;
  }

  @Override
  public ResponseType type() {
    return ResponseType.MULTI;
  }

  @Override
  public void add(Response reply) {
    // if this Multi was created as a Map
    if (strMap != null) {
      if (count % 2 == 0) {
        if (reply == null || reply.type() == null) {
          throw new IllegalArgumentException("Map key is NULL or untyped");
        }

        switch (reply.type()) {
          case BULK:
          case SIMPLE:
            key = new StrKey(reply);
            break;
          default:
            throw new IllegalArgumentException("Map key is not BULK or SIMPLE");
        }
      } else {
        if (key != null) {
          strMap.put(key, reply);
        }
        // clear the key
        key = null;
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
  public @Nullable Response get(String key) {
    if (strMap != null) {
      return strMap.get(new StrKey(key));
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(key, AS_STRING);
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public boolean containsKey(String key) {
    if (strMap != null) {
      return strMap.containsKey(new StrKey(key));
    }
    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(key, AS_STRING) != null;
    }
    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Set<String> getKeys() {
    if (strMap != null) {
      Set<String> strKeys = new LinkedHashSet<>();
      for (StrKey k : strMap.keySet()) {
        strKeys.add(k.asStr());
      }
      return strKeys;
    }

    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return getKeysOfMultiArray(AS_STRING);
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Response get(Buffer binKey) {
    if (strMap != null) {
      buildBinMapIfNeeded();
      return binMap.get(new BinKey(binKey));
    }

    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(binKey, AS_BIN);
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public boolean containsKey(Buffer binKey) {
    if (strMap != null) {
      buildBinMapIfNeeded();
      return binMap.containsKey(new BinKey(binKey));
    }

    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return findInMultiArray(binKey, AS_BIN) != null;
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public Set<Buffer> getBinaryKeys() {
    if (strMap != null) {
      buildBinMapIfNeeded();
      Set<Buffer> binKeys = new LinkedHashSet<>();
      for (BinKey k : binMap.keySet()) {
        binKeys.add(k.asBin());
      }
      return binKeys;
    }

    if (canInterpretMultiArrayAsMap()) {
      // fallback (emulate old behavior)
      return getKeysOfMultiArray(AS_BIN);
    }

    throw new RuntimeException("Number of key is not even can't handle as Map");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isArray() {
    return multi != null;
  }

  @Override
  public boolean isMap() {
    return strMap != null;
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
          sb.append(r);
        }
        more = true;
      }
      sb.append(']');
    }

    if (strMap != null) {
      sb.append('{');
      boolean more = false;
      // if binMap is built, then the caller interprets keys as binary
      Map<? extends WrappedResp, Response> mapToUse =
        Objects.requireNonNullElse(binMap, strMap);
      for (Map.Entry<? extends WrappedResp, Response> kv : mapToUse.entrySet()) {
        if (more) {
          sb.append(", ");
        }

        sb.append(kv.getKey());
        sb.append(": ");
        sb.append(kv.getValue());
        more = true;
      }
      sb.append('}');
    }

    return sb.toString();
  }

  @Override
  public Iterator<Response> iterator() {
    if (multi != null) {
      return new Iterator<Response>() {
        private int idx = 0;

        @Override
        public boolean hasNext() {
          return idx < size;
        }

        @Override
        public Response next() {
          return multi[idx++];
        }
      };
    }

    if (strMap != null) {
      return new Iterator<Response>() {
        private final Iterator<Map.Entry<StrKey, Response>> it = strMap.entrySet().iterator();

        @Override
        public boolean hasNext() {
          return it.hasNext();
        }

        @Override
        public Response next() {
          // wrap the kv into a single multi response
          final Map.Entry<StrKey, Response> kv = it.next();
          return new MultiType(kv.getKey(), kv.getValue());
        }
      };
    }

    throw new UnsupportedOperationException("Cannot iterator over NULL");
  }

  private boolean canInterpretMultiArrayAsMap() {
    // if the size is even we assume we can handle it as Map
    return multi != null && multi.length % 2 == 0;
  }

  private <K> Set<K> getKeysOfMultiArray(Function<Response, K> converter) {
    Set<K> convertedKeys = new LinkedHashSet<K>();
    // if the size is even we assume we can handle it as Map
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

  private void buildBinMapIfNeeded() {
    if (binMap != null) {
      return;
    }
    binMap = new LinkedHashMap<>(strMap.size(), 1.0f);
    for (Map.Entry<StrKey, Response> e : strMap.entrySet()) {
      binMap.put(new BinKey(e.getKey().rawResp()), e.getValue());
    }
  }

  private abstract static class WrappedResp {
    private final Response rawResp;

    WrappedResp(Response rawResp) {
      this.rawResp = rawResp;
    }

    Response rawResp() {
      return rawResp;
    }
  }

  /**
   * Wrapper over {@link Response} that allows using it as a Map key,
   * and allows comparison (equals/hashCode) with both other {@link StrKey}
   * instances and with {@link String} directly. Needed to support lookups
   * by String keys provided by callers.
   */
  private static class StrKey extends WrappedResp {
    private final String asStr;

    StrKey(Response rawResp) {
      super(rawResp);
      this.asStr = rawResp.toString();
    }

    StrKey(String strKey) {
      super(null);
      this.asStr = strKey;
    }

    String asStr() {
      return asStr;
    }

    @Override
    public int hashCode() {
      return asStr.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o == null) {
        return false;
      }
      if (o.getClass() == String.class) {
        return asStr.equals(o);
      } else if (o.getClass() == StrKey.class) {
        return asStr.equals(((StrKey) o).asStr);
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return asStr;
    }
  }

  /**
   * Wrapper over {@link Response} that allows using it as a Map key,
   * and allows comparison (equals/hashCode) with both other {@link BinKey}
   * instances and with {@link Buffer} directly. Needed to support lookups
   * by binary keys ({@link Buffer}) provided by callers.
   */
  private static class BinKey extends WrappedResp {
    private final Buffer asBin;

    BinKey(Response rawResp) {
      super(rawResp);
      this.asBin = rawResp.toBuffer();
    }

    BinKey(Buffer binKey) {
      super(null);
      this.asBin = binKey;
    }

    Buffer asBin() {
      return asBin;
    }

    @Override
    public int hashCode() {
      return asBin.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o == null) {
        return false;
      }
      if (o instanceof Buffer) {
        return asBin.equals(o);
      } else if (o.getClass() == BinKey.class) {
        return asBin.equals(((BinKey) o).asBin);
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return asBin.toJson(); // to base64 in JSON standard
    }
  }
}
