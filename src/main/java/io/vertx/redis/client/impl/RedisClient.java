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

import io.vertx.core.*;
import io.vertx.core.net.NetClientOptions;
import io.vertx.redis.client.*;

public class RedisClient extends BaseRedisClient implements Redis {

  private final String defaultAddress;

  public RedisClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, RedisConnectOptions connectOptions) {
    super(vertx, tcpOptions, poolOptions, connectOptions);
    this.defaultAddress = connectOptions.getEndpoint();
  }

  @Override
  public Future<RedisConnection> connect() {
    return connectionManager.getConnection(defaultAddress, null);
  }
}
