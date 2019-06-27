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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis {

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   *
   * This connection will use the default options which are connect
   * to a standalone server on the default port on "localhost".
   */
  static Redis createClient(Vertx vertx, String address) {
    return createClient(vertx, new RedisOptions().setEndpoint(address));
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static Redis createClient(Vertx vertx, RedisOptions options) {
    switch (options.getType()) {
      case STANDALONE:
        return RedisClient.create(vertx, options);
      case SENTINEL:
        return RedisSentinelClient.create(vertx, options);
      case CLUSTER:
        return RedisClusterClient.create(vertx, options);
      default:
        throw new IllegalStateException("Unknown Redis Client type: " + options.getType());
    }
  }

  /**
   * Connects to the redis server.
   *
   * @param handler  the async result handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis connect(Handler<AsyncResult<RedisConnection>> handler);

  /**
   * Connects to the redis server.
   *
   * @return a future with the result of the operation
   */
  default Future<RedisConnection> connect() {
    final Promise<RedisConnection> promise = Promise.promise();
    connect(promise);
    return promise.future();
  }
}
