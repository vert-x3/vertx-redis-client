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

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.client.impl.CachingRedisClient;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisReplicationClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.List;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis {

  /**
   * Create a new redis client using the default client options.
   *
   * @param vertx the vertx instance
   * @return the client
   */
  static Redis createClient(Vertx vertx) {
    return createClient(vertx, new RedisOptions());
  }

  /**
   * Create a new redis client using the default client options. Does not support rediss (redis over ssl scheme) for now.
   *
   * @param connectionString a string URI following the scheme: redis://[username:password@][host][:port][/database]
   * @param vertx            the vertx instance
   * @return the client
   * @see <a href="https://www.iana.org/assignments/uri-schemes/prov/redis">Redis scheme on iana.org</a>
   */
  static Redis createClient(Vertx vertx, String connectionString) {
    return createClient(vertx, new RedisOptions().setConnectionString(connectionString));
  }

  /**
   * Create a new redis client using the given client options.
   *
   * @param vertx   the vertx instance
   * @param options the user provided options
   * @return the client
   */
  static Redis createClient(Vertx vertx, RedisOptions options) {
    final Redis clientImpl;

    switch (options.getType()) {
      case STANDALONE:
        clientImpl = new RedisClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), new RedisStandaloneConnectOptions(options), options.getTracingPolicy());
        break;
      case SENTINEL:
        clientImpl = new RedisSentinelClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), new RedisSentinelConnectOptions(options), options.getTracingPolicy());
        break;
      case CLUSTER:
        clientImpl = new RedisClusterClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), new RedisClusterConnectOptions(options), options.getTracingPolicy());
        break;
      case REPLICATION:
        clientImpl = new RedisReplicationClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), new RedisClusterConnectOptions(options), options.getTracingPolicy());
        break;
      default:
        throw new IllegalStateException("Unknown Redis Client type: " + options.getType());
    }

    if (options.getCacheEnabled()) {
      return new CachingRedisClient(vertx, clientImpl, options.getCachingOptions());
    }

    return clientImpl;
  }

  /**
   * Connects to the redis server.
   *
   * @return a future with the result of the operation
   */
  Future<RedisConnection> connect();

  /**
   * Closes the client and terminates any connection.
   */
  void close();

  /**
   * Send the given command to the redis server or cluster.
   *
   * @param command the command to send
   * @return a future with the result of the operation
   */
  Future<@Nullable Response> send(Request command);

  /**
   * Sends a list of commands in a single IO operation, this prevents any inter twinning to happen from other
   * client users.
   *
   * @param commands list of command to send
   * @return a future with the result of the operation
   */
  Future<List<@Nullable Response>> batch(List<Request> commands);
}
