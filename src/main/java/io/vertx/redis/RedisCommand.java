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
package io.vertx.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.impl.client.RedisCommandImpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Encodes arguments in a REDIS way
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface RedisCommand {

  static RedisCommand create(String command) {
    return new RedisCommandImpl(command);
  }

  static RedisCommand cmd(RedisCommandEnum commandEnum) {
    return new RedisCommandImpl(commandEnum);
  }

  /**
   * Adds a null argument
   * @return self
   */
  @Fluent
  RedisCommand nullArg();

  /**
   * Adds a byte array
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand arg(byte[] arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(Unpooled.wrappedBuffer(arg));
  }

  /**
   * Adds a String argument using UTF8 character encoding
   * @return self
   */
  @Fluent
  default RedisCommand arg(String arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Adds a String key argument using UTF8 character encoding
   * @return self
   */
  @Fluent
  default RedisCommand key(String key) {
    return key(key.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand key(byte[] key) {
    return key(Unpooled.wrappedBuffer(key));
  }

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  default RedisCommand key(Buffer key) {
    return key(key.getByteBuf());
  }

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  @GenIgnore
  RedisCommand key(ByteBuf key);

  /**
   * Adds a String using a specific character encoding argument
   * @return self
   */
  @Fluent
  default RedisCommand arg(String arg, String enc) throws UnsupportedEncodingException {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getBytes(enc));
  }

  /**
   * Adds a String using a specific charset argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand arg(String arg, Charset charset) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getBytes(charset));
  }

  /**
   * Adds a Buffer argument
   * @return self
   */
  @Fluent
  default RedisCommand arg(Buffer arg) {
    return arg(arg.getByteBuf());
  }

  /**
   * Adds a Buffer argument
   * @return self
   */
  @Fluent
  @GenIgnore
  RedisCommand arg(ByteBuf arg);


  /**
   * Adds a Integer argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand arg(Integer arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.longValue());
  }

  /**
   * Adds a Long argument
   * @return self
   */
  @Fluent
  RedisCommand arg(Long arg);

  /**
   * Adds a List argument
   * @return self
   */
  @Fluent
  @GenIgnore
  RedisCommand arg(List arg);

  /**
   * Adds a JSON array argument
   * @return self
   */
  @Fluent
  default RedisCommand arg(JsonArray arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getList());
  }

  /**
   * Adds a Float argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand arg(Float arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(Float.toString(arg));
  }

  /**
   * Adds a Double argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default RedisCommand arg(Double arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(Double.toString(arg));
  }

  /**
   * Retuns the current number of arguments
   * @return size
   */
  int size();

  /**
   * Returns the arguments encoded as a buffer using REDIS format
   * @return self
   */
  @GenIgnore
  ByteBuf toByteBuf();

  /**
   * Returns the key hash as a slot id, or -1 if not key is present.
   * @return slot
   */
  int getKeySlot();

  @Fluent
  RedisCommand setReadOnly(boolean readOnly);

  boolean isReadOnly();
}
