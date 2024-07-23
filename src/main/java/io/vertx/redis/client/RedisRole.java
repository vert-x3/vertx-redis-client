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
 * The client role; that is, to which kind of node should the connection be established.
 * <p>
 * This is only meaningful in case of a {@linkplain RedisClientType#SENTINEL sentinel} Redis client.
 * </p>
 */
@VertxGen
public enum RedisRole {
  /**
   * Use a MASTER node connection.
   */
  MASTER,

  /**
   * Use a REPLICA node connection.
   */
  REPLICA,

  /**
   * Use a SENTINEL node connection.
   */
  SENTINEL
}
