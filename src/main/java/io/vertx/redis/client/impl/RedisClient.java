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

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.PoolOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisStandaloneConnectOptions;

import java.util.function.Supplier;

public class RedisClient extends BaseRedisClient<RedisStandaloneConnectOptions> implements Redis {

  public RedisClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, Supplier<Future<RedisStandaloneConnectOptions>> connectOptions, TracingPolicy tracingPolicy) {
    super(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
  }

  @Override
  public Future<RedisConnection> connect() {
    // so that the caller is called back on its original context
    Promise<RedisConnection> promise = vertx.promise();
    connectOptions.get()
      .compose(opts -> connectionManager.getConnection(opts.getEndpoint(), null))
      .onComplete(promise);
    return promise.future();
  }
}
