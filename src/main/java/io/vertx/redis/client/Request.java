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
import io.vertx.redis.client.impl.RequestImpl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Builder for REDIS requests that will be encoded according to the RESP protocol was introduced in Redis 1.2.
 * Which became the standard way for talking with the Redis server in Redis 2.0.
 *
 * Redis <a href="https://redis.io/topics/protocol">protocol documentation</a> states:
 *
 * <blockquote>
 *     Clients send commands to a Redis server as a RESP Array of Bulk Strings.
 * </blockquote>
 *
 * So all non String/Bulk types will be encoded to Bulk for convenience.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Request {

  static Request cmd(Command commandEnum) {
    return new RequestImpl(commandEnum);
  }

  /**
   * Adds a byte array
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  Request arg(byte[] arg);

  /**
   * Adds a String argument using UTF8 character encoding
   * @return self
   */
  @Fluent
  default Request arg(String arg) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Adds a String using a specific character encoding argument
   * @return self
   */
  @Fluent
  default Request arg(String arg, String enc) {
    if (arg == null) {
      return nullArg();
    }
    return arg(arg.getBytes(Charset.forName(enc)));
  }

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  Request arg(Buffer arg);

  /**
   * Adds a long encoded to string
   * @return self
   */
  @Fluent
  Request arg(long arg);

  /**
   * Adds a int encoded to string
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
   * @return self
   */
  @Fluent
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Request arg(byte arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a boolean encoded to string
   * @return self
   */
  @Fluent
  default Request arg(boolean arg) {
    arg(arg ? 1L : 0L);
    return this;
  }

  /**
   * Adds a NULL encoded string
   * @return self
   */
  @Fluent
  Request nullArg();

  Command command();
}
