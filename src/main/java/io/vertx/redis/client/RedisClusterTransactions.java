/*
 * Copyright 2024 Red Hat, Inc.
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
 * Define how Redis transactions are handled in a clustered Redis client.
 * <p>
 * This is only meaningful in case of a {@linkplain RedisClientType#CLUSTER cluster} Redis client.
 */
@VertxGen
public enum RedisClusterTransactions {
  /**
   * Redis transactions are not supported in the cluster. Transaction
   * commands lead to an error.
   */
  DISABLED,

  /**
   * Redis transactions are supported in the cluster, but only when they
   * target a single node. The {@code MULTI} command is queued and is only
   * issued when the next command is executed. This next command binds
   * the connection to the corresponding node of the Redis cluster (so it should
   * have keys, otherwise the target node is random). All subsequent commands
   * are targeted to that node. If some of the subsequent commands have a key
   * that belongs to another node, the command fails on the server side.
   * <p>
   * If {@code WATCH} is used before {@code MULTI}, its key(s) determine to which
   * node the connection is bound and the subsequent {@code MULTI} is not queued.
   * If {@code WATCH} keys belong to multiple nodes, the command fails on the client side.
   */
  SINGLE_NODE,
}
