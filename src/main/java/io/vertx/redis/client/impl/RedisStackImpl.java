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
package io.vertx.redis.client.impl;

import io.vertx.core.Future;
import io.vertx.redis.client.*;

public class RedisStackImpl implements RedisStack {

  private final Redis redis;
  private final RedisConnection connection;

  public RedisStackImpl(RedisConnection connection) {
    this.connection = connection;
    this.redis = null;
  }

  public RedisStackImpl(Redis redis) {
    this.connection = null;
    this.redis = redis;
  }

  @Override
  public Future<Response> send(Request req) {
    if (redis != null) {
      // operating in pooled mode
      return redis.send(req);
    } else if (connection != null) {
      // operating on connection mode
      return connection.send(req);
    }

    return Future.failedFuture("Invalid state: no pool or connection available");
  }

  @Override
  public Future<Void> close() {
    if (redis != null) {
      // operating in pooled mode
      try {
        redis.close();
        return Future.succeededFuture();
      } catch (RuntimeException e) {
        return Future.failedFuture(e);
      }
    } else if (connection != null) {
      // operating on connection mode
      return connection.close();
    }

    return Future.failedFuture("Invalid state: no pool or connection available");
  }
}
