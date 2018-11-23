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
 * This class also defines the methods <pre>key</pre> which act just like an argument but allow
 * clients such as cluster aware clients to compute the right server slot when sending the request.
 *
 * Users can optionally mark that a request is <pre>readOnly</pre> in order to hint the underlying
 * client which connection to use.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Request {

  static Request create(String command) {
    return new RequestImpl(command);
  }

  static Request cmd(Command commandEnum) {
    return new RequestImpl(commandEnum);
  }

  /**
   * Adds a byte array
   * @return self
   */
  @Fluent
  @GenIgnore
  Request arg(byte[] arg);

  /**
   * Adds a String argument using UTF8 character encoding
   * @return self
   */
  @Fluent
  Request arg(String arg);

  /**
   * Adds a String using a specific character encoding argument
   * @return self
   */
  @Fluent
  Request arg(String arg, String enc);

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  Request arg(Buffer arg);

  /**
   * Adds a String key argument using UTF8 character encoding
   * @return self
   */
  @Fluent
  Request key(String key);

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  @GenIgnore
  Request key(byte[] key);

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  Request key(Buffer key);

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
  @GenIgnore
  default Request arg(int arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a short encoded to string
   * @return self
   */
  @Fluent
  @GenIgnore
  default Request arg(short arg) {
    arg((long) arg);
    return this;
  }

  /**
   * Adds a byte encoded to string
   * @return self
   */
  @Fluent
  @GenIgnore
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

  /**
   * Returns the key hash as a slot id, or -1 if not key is present.
   * @return slot
   */
  int getKeySlot();

  @Fluent
  Request setReadOnly(boolean readOnly);

  boolean isReadOnly();
}
