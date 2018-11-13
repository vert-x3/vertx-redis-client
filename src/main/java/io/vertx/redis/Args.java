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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.impl.ArgsImpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Encodes arguments in a REDIS way
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Args {

  static Args args() {
    return new ArgsImpl();
  }

  @GenIgnore
  static Args args(Object... args) {
    return new ArgsImpl(args);
  }

  /**
   * Adds a null argument
   * @return self
   */
  @Fluent
  Args addNull();

  /**
   * Adds a byte array
   * @return self
   */
  @Fluent
  @GenIgnore
  default Args add(byte[] arg) {
    if (arg == null) {
      return addNull();
    }
    return add(Buffer.buffer(arg));
  }

  /**
   * Adds a String argument
   * @return self
   */
  @Fluent
  default Args add(String arg) {
    if (arg == null) {
      return addNull();
    }
    return add(arg.getBytes());
  }

  /**
   * Adds a String key argument
   * @return self
   */
  @Fluent
  Args key(String key);

  /**
   * Adds a String using a specific character encoding argument
   * @return self
   */
  @Fluent
  default Args add(String arg, String enc) throws UnsupportedEncodingException {
    if (arg == null) {
      return addNull();
    }
    return add(arg.getBytes(enc));
  }

  /**
   * Adds a String using a specific charset argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default Args add(String arg, Charset charset) {
    if (arg == null) {
      return addNull();
    }
    return add(arg.getBytes(charset));
  }

  /**
   * Adds a Buffer argument
   * @return self
   */
  @Fluent
  Args add(Buffer arg);


  /**
   * Adds a Integer argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default Args add(Integer arg) {
    if (arg == null) {
      return addNull();
    }
    return add(arg.longValue());
  }

  /**
   * Adds a Long argument
   * @return self
   */
  @Fluent
  Args add(Long arg);

  /**
   * Adds a List argument
   * @return self
   */
  @Fluent
  @GenIgnore
  Args add(List arg);

  /**
   * Adds a JSON array argument
   * @return self
   */
  @Fluent
  default Args add(JsonArray arg) {
    if (arg == null) {
      return addNull();
    }
    return add(arg.getList());
  }

  /**
   * Adds a Float argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default Args add(Float arg) {
    if (arg == null) {
      return addNull();
    }
    return add(Float.toString(arg));
  }

  /**
   * Adds a Double argument
   * @return self
   */
  @Fluent
  @GenIgnore
  default Args add(Double arg) {
    if (arg == null) {
      return addNull();
    }
    return add(Double.toString(arg));
  }

  /**
   * Retuns the current number of arguments
   * @return size
   */
  int size();

  /**
   * Returns the the arguments encoded as a buffer using REDIS format
   * @return self
   */
  Buffer toBuffer();

  /**
   * Returns the the argument marked as key if any or null
   * @return self
   */
  String getKey();
}
