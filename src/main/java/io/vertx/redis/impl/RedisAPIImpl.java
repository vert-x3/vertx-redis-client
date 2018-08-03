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
package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.redis.Args;
import io.vertx.redis.Redis;
import io.vertx.redis.RedisAPI;
import io.vertx.redis.Reply;

/**
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public class RedisAPIImpl implements RedisAPI {

  private final Redis delegate;


  public RedisAPIImpl(Redis client) {
    delegate = client;
  }

  @Override
  public Redis open(Handler<AsyncResult<Void>> handler) {
    delegate.open(handler);
    return this;
  }

  @Override
  public void close() {
    delegate.close();
  }

  @Override
  public Redis exceptionHandler(Handler<Throwable> handler) {
    delegate.exceptionHandler(handler);
    return this;
  }

  @Override
  public Redis closeHandler(Handler<Void> handler) {
    delegate.closeHandler(handler);
    return this;
  }

  @Override
  public Redis handler(Handler<Reply> handler) {
    delegate.handler(handler);
    return this;
  }

  @Override
  public Redis send(String command, Args args, Handler<AsyncResult<Reply>> handler) {
    delegate.send(command, args, handler);
    return this;
  }
}
