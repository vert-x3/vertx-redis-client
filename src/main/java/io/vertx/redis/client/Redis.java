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
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisReplicationClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis {

  /**
   * Create a new redis client using the default client options.
   * @param vertx the vertx instance
   * @return the client
   */
  static Redis createClient(Vertx vertx) {
    return createClient(vertx, new RedisOptions());
  }

  /**
   * Create a new redis client using the default client options. Does not support rediss (redis over ssl scheme) for now.
   * @param connectionString a string URI following the scheme: redis://[username:password@][host][:port][/database]
   * @param vertx the vertx instance
   * @return the client
   * @see <a href="https://www.iana.org/assignments/uri-schemes/prov/redis">Redis scheme on iana.org</a>
   */
  static Redis createClient(Vertx vertx, String connectionString) {
    return createClient(vertx, new RedisOptions().setConnectionString(connectionString));
  }

  /**
   * Create a new redis client using the given client options.
   * @param vertx the vertx instance
   * @param options the user provided options
   * @return the client
   */
  static Redis createClient(Vertx vertx, RedisOptions options) {
    switch (options.getType()) {
      case STANDALONE:
        return new RedisClient(vertx, options.getNetClientOptions(), options.getPoolOptions(),
          () -> Future.succeededFuture(new RedisStandaloneConnectOptions(options)), options.getTracingPolicy());
      case SENTINEL:
        return new RedisSentinelClient(vertx, options.getNetClientOptions(), options.getPoolOptions(),
          () -> Future.succeededFuture(new RedisSentinelConnectOptions(options)), options.getTracingPolicy());
      case CLUSTER:
        return new RedisClusterClient(vertx, options.getNetClientOptions(), options.getPoolOptions(),
          () -> Future.succeededFuture(new RedisClusterConnectOptions(options)), options.getTracingPolicy());
      case REPLICATION:
        return new RedisReplicationClient(vertx, options.getNetClientOptions(), options.getPoolOptions(),
          () -> Future.succeededFuture(new RedisClusterConnectOptions(options)), options.getTracingPolicy());
      default:
        throw new IllegalStateException("Unknown Redis Client type: " + options.getType());
    }
  }

  static Redis createStandaloneClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                                            Supplier<Future<RedisStandaloneConnectOptions>> connectionOptionsSupplier,
                                            TracingPolicy tracingPolicy) {
    return new RedisClient(vertx, tcpOptions,poolOptions, connectionOptionsSupplier,tracingPolicy);
  }

  static Redis createSentinelClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                                            Supplier<Future<RedisSentinelConnectOptions>> connectionOptionsSupplier,
                                            TracingPolicy tracingPolicy) {
    return new RedisSentinelClient(vertx, tcpOptions,poolOptions, connectionOptionsSupplier,tracingPolicy);
  }

  static Redis createClusterClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                                    Supplier<Future<RedisClusterConnectOptions>> connectionOptionsSupplier,
                                    TracingPolicy tracingPolicy) {
    return new RedisClusterClient(vertx, tcpOptions,poolOptions, connectionOptionsSupplier,tracingPolicy);
  }

  static Redis createReplicationClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions,
                                   Supplier<Future<RedisClusterConnectOptions>> connectionOptionsSupplier,
                                   TracingPolicy tracingPolicy) {
    return new RedisReplicationClient(vertx, tcpOptions,poolOptions, connectionOptionsSupplier,tracingPolicy);
  }

  /**
   * Connects to the redis server.
   *
   * @param handler  the async result handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  default Redis connect(Handler<AsyncResult<RedisConnection>> handler) {
    connect().onComplete(handler);
    return this;
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
   * @param command the command to send
   * @param onSend the asynchronous result handler.
   * @return fluent self.
   */
  @Fluent
  default Redis send(Request command, Handler<AsyncResult<@Nullable Response>> onSend) {
    send(command).onComplete(onSend);
    return this;
  }

  /**
   * Send the given command to the redis server or cluster.
   * @param command the command to send
   * @return a future with the result of the operation
   */
  Future<@Nullable Response> send(Request command);

  /**
   * Sends a list of commands in a single IO operation, this prevents any inter twinning to happen from other
   * client users.
   *
   * @param commands list of command to send
   * @param onSend the asynchronous result handler.
   * @return fluent self.
   */
  @Fluent
  default Redis batch(List<Request> commands, Handler<AsyncResult<List<@Nullable Response>>> onSend) {
    batch(commands).onComplete(onSend);
    return this;
  }

  /**
   * Sends a list of commands in a single IO operation, this prevents any inter twinning to happen from other
   * client users.
   *
   * @param commands list of command to send
   * @return a future with the result of the operation
   */
  Future<List<@Nullable Response>> batch(List<Request> commands);
}
