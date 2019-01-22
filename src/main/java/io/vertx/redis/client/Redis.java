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
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisSentinelClient;

import java.util.List;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis extends ReadStream<Response> {

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClient(Vertx vertx, SocketAddress address, Handler<AsyncResult<Redis>> onCreate) {
    createClient(vertx, new RedisOptions().setEndpoint(address), onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisClient.create(vertx, options.getEndpoint(), options, onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createSentinelClient(Vertx vertx, SocketAddress address, Handler<AsyncResult<Redis>> onCreate) {
    createSentinelClient(vertx, new RedisOptions().setEndpoint(address).setRole(RedisRole.MASTER).setMasterName("mymaster"), onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createSentinelClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisSentinelClient.create(vertx, options, onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClusterClient(Vertx vertx, SocketAddress address, Handler<AsyncResult<Redis>> onCreate) {
    createClusterClient(vertx, new RedisOptions().setEndpoint(address).setUseSlave(RedisSlaves.NEVER), onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClusterClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisClusterClient.create(vertx, options, onCreate);
  }

  /**
   * Set an exception handler on the read stream.
   *
   * @param handler  the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  Redis exceptionHandler(Handler<Throwable> handler);

  /**
   * Set a data handler. As data is read, the handler will be called with the data.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis handler(Handler<Response> handler);

  /**
   * Pause the {@code ReadStream}, it sets the buffer in {@code fetch} mode and clears the actual demand.
   * <p>
   * While it's paused, no data will be sent to the data {@code handler}.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis pause();

  /**
   * Resume reading, and sets the buffer in {@code flowing} mode.
   * <p/>
   * If the {@code ReadStream} has been paused, reading will recommence on it.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis resume();

  /**
   * Fetch the specified {@code amount} of elements. If the {@code ReadStream} has been paused, reading will
   * recommence with the specified {@code amount} of items, otherwise the specified {@code amount} will
   * be added to the current stream demand.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis fetch(long amount);

  /**
   * Set an end handler. Once the stream has ended, and there is no more data to be read, this handler will be called.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis endHandler(@Nullable Handler<Void> endHandler);


  @Fluent
  Redis send(Request command, Handler<AsyncResult<@Nullable Response>> onSend);

  @Fluent
  Redis batch(List<Request> commands, Handler<AsyncResult<List<@Nullable Response>>> handler);

  /**
   * Returns the address associated with this client.
   * @return the address.
   */
  SocketAddress socketAddress();

  void close();
}
