/*
 * Copyright 2018 Red Hat, Inc.
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
package io.vertx.redis.client;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.impl.RequestImpl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Builder for Redis requests that will be encoded according to the RESP protocol.
 * RESP was introduced in Redis 1.2 and became the standard way for talking with the Redis server in Redis 2.0.
 * <p>
 * Redis <a href="https://redis.io/topics/protocol">protocol documentation</a> states:
 *
 * <blockquote>
 * Clients send commands to a Redis server as a RESP Array of Bulk Strings.
 * </blockquote>
 * <p>
 * So all non String/Bulk types will be encoded to Bulk for convenience.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Request {

  /**
   * Creates a new request. Requests can be reused to avoid GC.
   *
   * @param command the command to use
   * @return a new request instance
   */
  static Request cmd(Command command) {
    return new RequestImpl(command);
  }

  /**
   * Creates a new request only with simple types of arguments: {@link Number}, {@link Boolean},
   * {@link String}, {@code byte[]} or {@link Buffer}. Other kinds of arguments throw an exception.
   * <p>
   * This factory reduces the GC pressure as it allocates the arguments array with the right size.
   *
   * @param command the command to use
   * @param args    the fixed list of arguments
   * @return a new request instance
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static Request cmd(Command command, Object... args) {
    return new RequestImpl(command, args);
  }

  /**
   * Adds a byte array
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  Request arg(byte[] arg);

  /**
   * Adds a String argument using UTF8 character encoding
   *
   * @return self
   */
  @Fluent
  default Request arg(String arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    }
    return arg(arg.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Adds a String using a specific character encoding argument
   *
   * @return self
   */
  @Fluent
  default Request arg(String arg, String enc) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    }
    return arg(arg.getBytes(Charset.forName(enc)));
  }

  /**
   * Adds a String key argument
   *
   * @return self
   */
  @Fluent
  Request arg(Buffer arg);

  /**
   * Adds a long encoded to string
   *
   * @return self
   */
  @Fluent
  Request arg(long arg);

  /**
   * Adds a int encoded to string
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(int arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a short encoded to string
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(short arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a byte encoded to string
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(byte arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a float encoded to string
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(float arg) {
    arg(Float.toString(arg));
    return this;
  }

  /**
   * Adds a double encoded to string
   *
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(double arg) {
    arg(Double.toString(arg));
    return this;
  }

  /**
   * Adds a boolean encoded to string
   *
   * @return self
   */
  @Fluent
  Request arg(boolean arg);

  /**
   * Adds a JsonObject argument, the encoding will serialize the json as key0, value0, key1, value1, ... keyN, valueN.
   * This is a non-optimized serialization and will just use the string encoding of the values for non buffers.
   *
   * @return self
   * @throws IllegalArgumentException when values are {@code null} or of type JsonArray or JsonObject
   */
  @Fluent
  default Request arg(JsonObject arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    } else {
      for (Map.Entry<String, Object> kv : arg) {
        arg(kv.getKey());
        Object val = kv.getValue();

        if (val == null) {
          throw new IllegalArgumentException("Null argument not allowed");
        } else if (val instanceof Buffer) {
          arg((Buffer) val);
        } else if (val instanceof JsonArray || val instanceof JsonObject) {
          throw new IllegalArgumentException("Graph objects are not allowed as values, only simple types");
        } else {
          arg(val.toString());
        }
      }
    }
    return this;
  }

  /**
   * Adds a JsonArray argument, the encoding will serialize the json as value0, value1, ... valueN.
   * This is a non-optimized serialization and will just use the string encoding of the values for non buffers.
   *
   * @return self
   * @throws IllegalArgumentException when values are {@code null} or of type JsonArray or JsonObject
   */
  @Fluent
  default Request arg(JsonArray arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    } else {
      for (Object el : arg) {
        if (el == null) {
          throw new IllegalArgumentException("Null argument not allowed");
        } else if (el instanceof Buffer) {
          arg((Buffer) el);
        } else if (el instanceof JsonArray || el instanceof JsonObject) {
          throw new IllegalArgumentException("Graph objects are not allowed as elements, only simple types");
        } else {
          arg(el.toString());
        }
      }
    }
    return this;
  }

  /**
   * Get the Command that is to be used by this request.
   *
   * @return the command.
   */
  Command command();
}
