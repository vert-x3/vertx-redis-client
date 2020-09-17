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
 * Define what kind of behavior is expected from the client.
 */
@VertxGen
public enum RedisClientType {

  /**
   * The client should work in single server mode (the default).
   */
  STANDALONE,

  /**
   * The client should work in sentinel mode. When this mode is active
   * use the {@link RedisRole} to define which role to get the client
   * connection to.
   */
  SENTINEL,

  /**
   * The client should work in cluster mode. When this mode is active
   * use the {@link RedisReplicas} to define when replica nodes can be
   * used for read only queries.
   */
  CLUSTER
}
