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
import io.vertx.redis.client.*;

public class RedisClient implements Redis {

  private final ConnectionManager connectionManager;
  private final String defaultAddress;

  public RedisClient(Vertx vertx, RedisOptions options) {
    this.connectionManager = new ConnectionManager(vertx, options);
    this.defaultAddress = options.getEndpoint();
  }

  @Override
  public Redis connect(Handler<AsyncResult<RedisConnection>> onConnect) {
    connectionManager.getConnection(defaultAddress, null, onConnect);
    return this;
  }

  @Override
  public void close() {
    connectionManager.close();
  }
}
