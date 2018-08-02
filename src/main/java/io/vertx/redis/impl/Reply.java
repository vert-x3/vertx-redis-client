/**
 * Copyright 2015 Red Hat, Inc.
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
package io.vertx.redis.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public final class Reply implements io.vertx.redis.Reply {

  private final byte type;
  private final Buffer buffer;
  private final Reply[] multi;

  public Reply(byte type, Buffer buffer) {
    this.type = type;
    this.buffer = buffer;
    this.multi = null;
  }

  public Reply(byte type, int size) {
    this.type = type;
    this.buffer = null;
    this.multi = new Reply[size];
  }

  void set(int pos, Reply reply) {
    if (multi != null) {
      multi[pos] = reply;
    } else {
      throw new IndexOutOfBoundsException("Position: " + pos);
    }
  }

  public boolean is(byte b) {
    return type == b;
  }

  public boolean is(char b) {
    return type == (byte) b;
  }

  /**
   * Return the type of instance of this Reply. Useful to avoid checks against instanceof
   *
   * @return enum
   */
  byte type() {
    return type;
  }

  public Reply[] multi() {
    return multi;
  }

  @SuppressWarnings("unchecked")
  public <T> T asType(final Class<T> type, final String encoding) throws ClassCastException {

    if (type == String.class) {
      if (buffer == null) {
        return null;
      }
      if (encoding != null) {
        return (T) buffer.toString(encoding);
      } else {
        return (T) buffer.toString();
      }
    }

    if (type == Long.class) {
      if (buffer == null) {
        return null;
      }
      return (T) Long.valueOf(buffer.toString());
    }

    if (type == Void.class) {
      if (buffer == null) {
        return null;
      }
      return null;
    }

    if (type == Buffer.class) {
      if (buffer == null) {
        return null;
      }
      return (T) buffer;
    }

    if (type == JsonArray.class) {
      if (multi == null) {
        return null;
      }
      final JsonArray json = new JsonArray();

      for (Reply r : multi) {
        Object elem;
        switch (r.type()) {
          case '+':
          case '$':   // Bulk
            elem = r.asType(String.class, encoding);
            break;
          case ':':   // Integer
            elem = r.asType(Long.class, encoding);
            break;
          case '*':   // Multi
            elem = r.asType(JsonArray.class, encoding);
            break;
          default:
            throw new RuntimeException("Unknown sub message type in multi: " + r.type());
        }
        if (elem == null) {
          json.addNull();
        } else {
          json.add(elem);
        }
      }

      return (T) json;
    }

    if (type == JsonObject.class) {
      if (multi == null) {
        return null;
      }
      final JsonObject json = new JsonObject();

      for (int i = 0; i < multi.length; i += 2) {
        if (multi[i].type() != '$') {
          throw new RuntimeException("Expected String as key type in multi: " + multi[i].type());
        }

        Reply brKey = multi[i];
        Reply brValue = multi[i + 1];

        String k = brKey.asType(String.class, encoding);

        switch (brValue.type()) {
          case '$':   // Bulk
            json.put((k == null ? "k" + (i / 2) : k), brValue.asType(String.class, encoding));
            break;
          case ':':   // Integer
            json.put((k == null ? "k" + (i / 2) : k), brValue.asType(Long.class, encoding));
            break;
          case '*':   // Multi
            json.put((k == null ? "k" + (i / 2) : k), brValue.asType(JsonArray.class, encoding));
            break;
          default:
            throw new RuntimeException("Unknown sub message type in multi: " + multi[i + 1].type());
        }

      }
      return (T) json;
    }

    return null;
  }

  public <T> T asType(Class<T> type) throws ClassCastException {
    return asType(type, "UTF-8");
  }

  @Override
  public String status() {
    if ((type == '+' || type == '-') && buffer != null) {
      return buffer.toString();
    }
    return null;
  }

  @Override
  public JsonArray asJsonArray() {
    if (type == '*' && multi != null) {
      final JsonArray array = new JsonArray();

      for (Reply r : multi) {
        Object elem;
        switch (r.type()) {
          case '+':
          case '-':
            elem = r.status();
            break;
          case '$':   // Bulk
            elem = r.asString();
            break;
          case ':':   // Integer
            elem = r.asLong();
            break;
          case '*':   // Multi
            elem = r.asJsonArray();
            break;
          default:
            throw new RuntimeException("Unknown sub message type in multi: " + r.type());
        }
        if (elem == null) {
          array.addNull();
        } else {
          array.add(elem);
        }
      }

      return array;
    }

    return null;
  }

  @Override
  public JsonObject asJsonObject() {
    if (type == '*' && multi != null) {
      final JsonObject json = new JsonObject();

      for (int i = 0; i < multi.length; i += 2) {
        if (multi[i].type() != '$') {
          throw new RuntimeException("Expected String as key type in multi: " + multi[i].type());
        }

        Reply brKey = multi[i];
        Reply brValue = multi[i + 1];

        String k = brKey.asString();

        switch (brValue.type()) {
          case '+':   // Status
          case '-':   // Status
            json.put((k == null ? "k" + (i / 2) : k), brValue.status());
            break;
          case '$':   // Bulk
            json.put((k == null ? "k" + (i / 2) : k), brValue.asString());
            break;
          case ':':   // Integer
            json.put((k == null ? "k" + (i / 2) : k), brValue.asLong());
            break;
          case '*':   // Multi
            json.put((k == null ? "k" + (i / 2) : k), brValue.asJsonArray());
            break;
          default:
            throw new RuntimeException("Unknown sub message type in multi: " + multi[i + 1].type());
        }
      }

      return json;
    }

    return null;
  }

  @Override
  public Long asLong() {
    if ((type == '$' || type == ':') && buffer != null) {
      return Long.parseLong(buffer.toString());
    }
    return null;
  }

  @Override
  public Integer asInteger() {
    if ((type == '$' || type == ':') && buffer != null) {
      return Integer.parseInt(buffer.toString());
    }
    return null;
  }

  @Override
  public Float asFloat() {
    if ((type == '$' || type == ':') && buffer != null) {
      return Float.parseFloat(buffer.toString());
    }
    return null;
  }

  @Override
  public Double asDouble() {
    if ((type == '$' || type == ':') && buffer != null) {
      return Double.parseDouble(buffer.toString());
    }
    return null;
  }

  @Override
  public String asString() {
    if ((type == '$' || type == '+' || type == '-' || type == ':') && buffer != null) {
      return buffer.toString();
    }
    return null;
  }

  @Override
  public String asString(String encoding) {
    if ((type == '$' || type == '+' || type == '-' || type == ':') && buffer != null) {
      return buffer.toString(encoding);
    }
    return null;
  }

  @Override
  public Buffer asBuffer() {
    return buffer;
  }

  @Override
  public boolean isMulti() {
    return multi != null;
  }

  @Override
  public int size() {
    if (multi != null) {
      return multi.length;
    }

    return 0;
  }

  @Override
  public Reply get(int pos) {
    if (multi != null) {
      return multi[pos];
    }

    throw new IndexOutOfBoundsException("Position: " + pos);
  }

  @Override
  public String toString() {
    if (multi != null) {
      return "["+ multi.length + "]";
    }
    return asString();
  }
}
