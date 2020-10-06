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
 * When should Redis replica nodes be used for queries.
 */
@VertxGen
public enum RedisReplicas {

  /**
   * Never use REPLICA, queries are always run on a MASTER node.
   */
  NEVER,

  /**
   * Queries can be randomly run on both MASTER and REPLICA nodes.
   */
  SHARE,

  /**
   * Queries are always run on REPLICA nodes (never on MASTER node).
   */
  ALWAYS
}
