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
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.*;

import java.util.function.Supplier;

public class RedisClient extends BaseRedisClient implements Redis {

  public RedisClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                     Supplier<Future<RedisConnectOptions>> connectOptions, TracingPolicy tracingPolicy) {
    super(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
  }

  @Override
  public Future<RedisConnection> connect() {
    // so that the caller is called back on its original context
    Promise<RedisConnection> promise = vertx.promise();
    connectOptions.get()
      .flatMap(options -> connectionManager.getConnection(options.getEndpoint(), null))
      .onComplete((Promise) promise);
    return promise.future();
  }
}
