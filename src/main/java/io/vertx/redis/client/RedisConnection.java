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
import io.vertx.core.streams.ReadStream;

import java.util.List;

/**
 * A simple Redis client.
 */
@VertxGen
public interface RedisConnection extends ReadStream<Response> {

  /**
   * Set an exception handler on the read stream.
   *
   * @param handler  the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection exceptionHandler(Handler<Throwable> handler);

  /**
   * Set a data handler. As data is read, the handler will be called with the data.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection handler(Handler<Response> handler);

  /**
   * Pause the {@code ReadStream}, it sets the buffer in {@code fetch} mode and clears the actual demand.
   * <p>
   * While it's paused, no data will be sent to the data {@code handler}.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection pause();

  /**
   * Resume reading, and sets the buffer in {@code flowing} mode.
   * <p/>
   * If the {@code ReadStream} has been paused, reading will recommence on it.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection resume();

  /**
   * Fetch the specified {@code amount} of elements. If the {@code ReadStream} has been paused, reading will
   * recommence with the specified {@code amount} of items, otherwise the specified {@code amount} will
   * be added to the current stream demand.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection fetch(long amount);

  /**
   * Set an end handler. Once the stream has ended, and there is no more data to be read, this handler will be called.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisConnection endHandler(@Nullable Handler<Void> endHandler);


  /**
   * Send the given command to the redis server or cluster.
   * @param command the command to send
   * @param onSend the asynchronous result handler.
   * @return fluent self.
   */
  @Fluent
  RedisConnection send(Request command, Handler<AsyncResult<@Nullable Response>> onSend);

  /**
   * Send the given command to the redis server or cluster.
   * @param command the command to send
   * @return a future with the result of the operation
   */
  default Future<@Nullable Response> send(Request command) {
    final Promise<@Nullable Response> promise = Promise.promise();
    send(command, promise);
    return promise.future();
  }

  /**
   * Sends a list of commands in a single IO operation, this prevents any inter twinning to happen from other
   * client users.
   *
   * @param commands list of command to send
   * @param onSend the asynchronous result handler.
   * @return fluent self.
   */
  @Fluent
  RedisConnection batch(List<Request> commands, Handler<AsyncResult<List<@Nullable Response>>> onSend);

  /**
   * Sends a list of commands in a single IO operation, this prevents any inter twinning to happen from other
   * client users.
   *
   * @param commands list of command to send
   * @return a future with the result of the operation
   */
  default Future<List<@Nullable Response>> batch(List<Request> commands) {
    final Promise<List<@Nullable Response>> promise = Promise.promise();
    batch(commands, promise);
    return promise.future();
  }

  /**
   * Closes the connection or returns to the pool.
   */
  void close();

  /**
   * Flag to notify if the pending message queue (commands in transit) is full.
   *
   * When the pending message queue is full and a new send command is issued it will result in a exception to be thrown.
   * Checking this flag before sending can allow the application to wait before sending the next message.
   *
   * @return true is queue is full.
   */
  boolean pendingQueueFull();
}
