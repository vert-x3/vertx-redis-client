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
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.client.RedisImpl;

/**
 * A non opinionated Redis Client.
 *
 * This is a simple Redis client that provides the required protocol codec for REDIS.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Redis extends RedisConnection {

  /**
   * Creates an instance of the Client.
   *
   * @param vertx vertx instance
   * @param socketAddress the socket address
   * @return a instance of the connector.
   */
  static Redis create(Vertx vertx, SocketAddress socketAddress) {
    return new RedisImpl(
      vertx,
      socketAddress,
      // by default redis works with TCP_NO_DELAY
      new NetClientOptions().setTcpNoDelay(true));
  }

  /**
   * Creates an instance of the Client.
   *
   * @param vertx vertx instance
   * @param socketAddress the socket address
   * @param options the net client options to be used
   * @return a instance of the connector.
   */
  static Redis create(Vertx vertx, SocketAddress socketAddress, NetClientOptions options) {
    return new RedisImpl(vertx, socketAddress, options);
  }

  /**
   * Opens a connection to the redis server.
   *
   * @param handler a handler to be called once the connection is open or on error.
   * @return self
   */
  @Fluent
  Redis connect(Handler<AsyncResult<Void>> handler);
}
