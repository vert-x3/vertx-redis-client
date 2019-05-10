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
package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.List;

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
  static Redis createClient(Vertx vertx, SocketAddress address) {
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
}
