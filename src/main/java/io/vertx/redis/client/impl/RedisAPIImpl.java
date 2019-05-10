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
import io.vertx.redis.RedisConnection;
import io.vertx.redis.client.*;

public class RedisAPIImpl implements RedisAPI {

  private final RedisConnection client;

  public RedisAPIImpl(RedisConnection client) {
    this.client = client;
  }

  @Override
  public Future<Response> send(Command cmd, Object... args) {
    final Future<Response> future = Future.future();
    final Request req = Request.cmd(cmd);

    if (args != null) {
      for (Object o : args) {
        if (o == null) {
          req.nullArg();
        } else {
          req.arg(o.toString());
        }
      }
    }

    client.send(req, future);
    return future;
  }
}
