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
package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;

/**
 * Define the response types that the client can receive from REDIS.
 */
@VertxGen
public enum ResponseType {
  /**
   * C String simple String.
   */
  SIMPLE,

  /**
   * C String simple String representing an error.
   */
  ERROR,

  /**
   * boolean value.
   */
  BOOLEAN,

  /**
   * numeric value.
   */
  NUMBER,

  /**
   * byte array value.
   */
  BULK,

  /**
   * List of multiple bulk responses.
   */
  MULTI
}
