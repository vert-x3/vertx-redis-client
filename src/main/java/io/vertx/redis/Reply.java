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

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A Reply from the REDIS server.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Reply {

  /**
   * Get the status of the reply.
   * @return string value.
   */
  @Nullable
  String status();

  /**
   * If the reply is a multibulk it will encode it as a JSON array.
   * @return json
   */
  @Nullable
  JsonArray asJsonArray();

  /**
   * If the reply is a multibulk it will encode it as a JSON object.
   * @return json
   */
  @Nullable
  JsonObject asJsonObject();

  /**
   * If the reply is a redis integer or bulk it will encode it as a long.
   * @return long
   */
  @Nullable
  Long asLong();

  /**
   * If the reply is a redis integer or bulk it will encode it as a int.
   * @return int
   */
  @Nullable
  Integer asInteger();

  /**
   * If the reply is a redis integer or bulk or bulk it will encode it as a float.
   * @return float
   */
  @Nullable
  Float asFloat();

  /**
   * If the reply is a redis integer or bulk or bulk it will encode it as a double.
   * @return double
   */
  @Nullable
  Double asDouble();

  /**
   * If the reply is a not a multibulk it will encode it as a String.
   * @return string
   */
  @Nullable
  String asString();

  /**
   * If the reply is a not a multibulk it will encode it as a String using the provided character encoding.
   * @return string
   */
  @Nullable
  String asString(String encoding);

  /**
   * If the reply is a not a multibulk it return the original buffer.
   * @return buffer
   */
  @Nullable
  Buffer asBuffer();

  /**
   * Is this reply a multibulk?
   * @return boolean
   */
  boolean isMulti();

  /**
   * Returns the size of the multibulk
   * @return json
   */
  int size();

  /**
   * Returns reply at index pos in the multibulk
   * @return json
   */
  Reply get(int pos);
}
