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
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;
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
    return createClient(vertx, options, () -> Future.succeededFuture(new RedisOptions(options)));
  }

  /**
   * Create a new redis client using the given client options.
   *
   * @param vertx the vertx instance
   * @param options the user provided options
   * @param optionsSupplier the user provided options, which can be changed dynamically.
   *                        Values like type cannot be changed.
   * @return the client
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  static Redis createClient(Vertx vertx, RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier) {
    switch (options.getType()) {
      case STANDALONE:
        return new RedisClient(vertx, options, optionsSupplier);
      case SENTINEL:
        return new RedisSentinelClient(vertx, options, optionsSupplier);
      case CLUSTER:
        return new RedisClusterClient(vertx, options, optionsSupplier);
      case REPLICATION:
        return new RedisReplicationClient(vertx, options, optionsSupplier);
      default:
        throw new IllegalStateException("Unknown Redis Client type: " + options.getType());
    }
  }

  /**
   * Connects to the redis server.
   * @param handler the async result handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  default Redis connect(Handler<AsyncResult<RedisConnection>> handler) {
    connect().onComplete(handler);
    return this;
  }

  /**
   * Connects to the redis server.
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
