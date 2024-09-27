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
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisReplicationClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.List;
import java.util.function.Supplier;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis {

  /**
   * Create a new Redis client using the default client options.
   *
   * @param vertx the vertx instance
   * @return the client
   */
  static Redis createClient(Vertx vertx) {
    return createClient(vertx, new RedisOptions());
  }

  /**
   * Create a new Redis client using the default client options. Does not support rediss (redis over ssl scheme) for now.
   *
   * @param connectionString a string URI following the scheme: redis://[username:password@][host][:port][/database]
   * @param vertx the vertx instance
   * @return the client
   * @see <a href="https://www.iana.org/assignments/uri-schemes/prov/redis">Redis scheme on iana.org</a>
   */
  static Redis createClient(Vertx vertx, String connectionString) {
    return createClient(vertx, new RedisOptions().setConnectionString(connectionString));
  }

  /**
   * Create a new Redis client using the given client options.
   *
   * @param vertx the Vert.x instance
   * @param options the user provided options
   * @return the client
   */
  static Redis createClient(Vertx vertx, RedisOptions options) {
    switch (options.getType()) {
      case STANDALONE:
        return createStandaloneClient(vertx, options, new ConstantSupplier<>(Future.succeededFuture(new RedisStandaloneConnectOptions(options))));
      case SENTINEL:
        return createSentinelClient(vertx, options, new ConstantSupplier<>(Future.succeededFuture(new RedisSentinelConnectOptions(options))));
      case CLUSTER:
        return createClusterClient(vertx, options, new ConstantSupplier<>(Future.succeededFuture(new RedisClusterConnectOptions(options))));
      case REPLICATION:
        return createReplicationClient(vertx, options, new ConstantSupplier<>(Future.succeededFuture(new RedisReplicationConnectOptions(options))));
      default:
        throw new IllegalStateException("Unknown Redis Client type: " + options.getType());
    }
  }

  /**
   * Creates a new standalone Redis client. The {@code options} are used to obtain {@link RedisOptions#getType()},
   * {@link RedisOptions#getNetClientOptions()}, {@link RedisOptions#getPoolOptions()}
   * and {@link RedisOptions#getTracingPolicy()}. The {@code connectOptions} are queried for every
   * connection attempt.
   * <p>
   * If {@code options.getType() != RedisClientType.STANDALONE}, an exception is thrown.
   *
   * @param vertx the Vert.x instance
   * @param options the static options
   * @param connectOptions supplier of the dynamic options
   * @return the standalone client
   */
  static Redis createStandaloneClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisStandaloneConnectOptions>> connectOptions) {
    if (options.getType() != RedisClientType.STANDALONE) {
      throw new IllegalArgumentException("RedisOptions.getType() != RedisClientType.STANDALONE");
    }
    return new RedisClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), connectOptions, options.getTracingPolicy());
  }

  /**
   * Creates a new replication Redis client. The {@code options} are used to obtain {@link RedisOptions#getType()},
   * {@link RedisOptions#getNetClientOptions()}, {@link RedisOptions#getPoolOptions()}
   * and {@link RedisOptions#getTracingPolicy()}. The {@code connectOptions} are queried for every
   * connection attempt.
   * <p>
   * If {@code options.getType() != RedisClientType.REPLICATION}, an exception is thrown.
   *
   * @param vertx the Vert.x instance
   * @param options the static options
   * @param connectOptions supplier of the dynamic options
   * @return the replication client
   */
  static Redis createReplicationClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisReplicationConnectOptions>> connectOptions) {
    if (options.getType() != RedisClientType.REPLICATION) {
      throw new IllegalArgumentException("RedisOptions.getType() != RedisClientType.REPLICATION");
    }
    return new RedisReplicationClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), connectOptions, options.getTracingPolicy());
  }

  /**
   * Creates a new sentinel Redis client. The {@code options} are used to obtain {@link RedisOptions#getType()},
   * {@link RedisOptions#getNetClientOptions()}, {@link RedisOptions#getPoolOptions()}
   * and {@link RedisOptions#getTracingPolicy()}. The {@code connectOptions} are queried for every
   * connection attempt.
   * <p>
   * If {@code options.getType() != RedisClientType.SENTINEL}, an exception is thrown.
   *
   * @param vertx the Vert.x instance
   * @param options the static options
   * @param connectOptions supplier of the dynamic options
   * @return the sentinel client
   */
  static Redis createSentinelClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisSentinelConnectOptions>> connectOptions) {
    if (options.getType() != RedisClientType.SENTINEL) {
      throw new IllegalArgumentException("RedisOptions.getType() != RedisClientType.SENTINEL");
    }
    return new RedisSentinelClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), connectOptions, options.getTracingPolicy());
  }

  /**
   * Creates a new cluster Redis client. The {@code options} are used to obtain {@link RedisOptions#getType()},
   * {@link RedisOptions#getNetClientOptions()}, {@link RedisOptions#getPoolOptions()}
   * and {@link RedisOptions#getTracingPolicy()}. The {@code connectOptions} are queried for every
   * connection attempt.
   * <p>
   * If {@code options.getType() != RedisClientType.CLUSTER}, an exception is thrown.
   *
   * @param vertx the Vert.x instance
   * @param options the static options
   * @param connectOptions supplier of the dynamic options
   * @return the cluster client
   */
  static Redis createClusterClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisClusterConnectOptions>> connectOptions) {
    if (options.getType() != RedisClientType.CLUSTER) {
      throw new IllegalArgumentException("RedisOptions.getType() != RedisClientType.CLUSTER");
    }
    return new RedisClusterClient(vertx, options.getNetClientOptions(), options.getPoolOptions(), connectOptions, options.getTracingPolicy());
  }

  /**
   * Connects to the Redis server.
   *
   * @return a future with the result of the operation
   */
  Future<RedisConnection> connect();

  /**
   * Closes the client and terminates any connection.
   */
  Future<Void> close();

  /**
   * Send the given command to the Redis server or cluster.
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
