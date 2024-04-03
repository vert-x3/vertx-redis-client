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
package io.vertx.redis.client.impl;

import io.vertx.core.impl.VertxInternal;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.metrics.ClientMetrics;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.RedisConnection;

public interface RedisConnectionInternal extends RedisConnection {

  boolean isValid();

  void forceClose();

  /**
   * Returns {@code true} is this connection can be reset. This means that the connection didn't enter PubSub mode.
   */
  boolean reset();

  /**
   * Returns whether this connection is "tainted". A connection is called tainted if it changes the default state,
   * for example, when a connection enters pub sub mode, or specific features are activated such as changing a database
   * or different authentication is used.
   */
  boolean isTainted();

  VertxInternal vertx();

  /**
   * Returns the {@linkplain RedisURI URI} of the Redis server to which this connection is connected.
   */
  RedisURI uri();

  /**
   * Returns the {@linkplain ClientMetrics client metrics} for this connection.
   */
  ClientMetrics metrics();

  /**
   * Returns the {@linkplain TracingPolicy tracing policy} configured for this connection.
   */
  TracingPolicy tracingPolicy();

  /**
   * Returns the {@linkplain SocketAddress remote address} of the Redis server to which this connection is connected.
   */
  SocketAddress remoteAddress();
}
