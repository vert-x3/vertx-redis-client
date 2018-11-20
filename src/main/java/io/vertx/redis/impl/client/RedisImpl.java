/*
 * Copyright 2018 Red Hat, Inc.
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
package io.vertx.redis.impl.client;

import io.vertx.core.*;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.Redis;
import io.vertx.redis.RedisCommand;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.Reply;

/**
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public class RedisImpl extends AbstractRedisClient implements Redis {

  public RedisImpl(Vertx vertx, SocketAddress socketAddress, NetClientOptions netClientOptions) {
    super(vertx, socketAddress, netClientOptions);
  }

  // state
  private RedisConnectionImpl connection;
  // temp holders while conn isn't available
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Reply> onMessage;

  @Override
  public Redis connect(Handler<AsyncResult<Void>> onConnect) {
    // wrap a netClient for the connection
    final NetClient client = createNetClient();
    client.connect(endpoint(), clientConnect -> {
      if (clientConnect.failed()) {
        // connection failed
        client.close();
        // reset state
        connection = null;
        onConnect.handle(Future.failedFuture(clientConnect.cause()));
        return;
      }

      // socket connection succeeded
      connection = new RedisConnectionImpl(context, client, clientConnect.result(), endpoint());
      connection
        .handler(onMessage)
        .endHandler(onEnd)
        .exceptionHandler(onException);

      onConnect.handle(Future.succeededFuture());
    });

    return this;
  }

  @Override
  public void close() {
    connection.close();
    // reset connection state
    connection = null;
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    if (connection == null) {
      onException = handler;
    } else {
      connection.exceptionHandler(handler);
    }
    return this;
  }

  @Override
  public RedisConnection endHandler(Handler<Void> handler) {
    if (connection == null) {
      onEnd = handler;
    } else {
      connection.endHandler(handler);
    }
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Reply> handler) {
    if (connection == null) {
      onMessage = handler;
    } else {
      connection.handler(handler);
    }
    return this;
  }

  @Override
  public RedisConnection pause() {
    connection.pause();
    return this;
  }

  @Override
  public RedisConnection resume() {
    connection.resume();
    return this;
  }

  @Override
  public ReadStream<Reply> fetch(long amount) {
    connection.fetch(amount);
    return this;
  }

  @Override
  public RedisConnection send(RedisCommand command, Handler<AsyncResult<Reply>> handler) {
//    // connection can be null if the connection cycle isn't finished
//    if (connection == null) {
//      // avoid keeping state which could lead to undefined behavior or OOM
//      handler.handle(Future.failedFuture("Redis Connection isn't ready."));
//    } else {
      connection.send(command, handler);
//    }

    return this;
  }

  @Override
  public SocketAddress address() {
    return connection.address();
  }
}
