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
import io.vertx.redis.Redis;
import io.vertx.redis.RedisConnection;

/**
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public class RedisImpl extends AbstractRedisClient implements Redis {

  public RedisImpl(Vertx vertx, SocketAddress socketAddress, NetClientOptions netClientOptions) {
    super(vertx, socketAddress, netClientOptions);
  }

  @Override
  public Redis open(Handler<AsyncResult<RedisConnection>> onOpen) {
    // wrap a netClient for the connection
    final NetClient client = createNetClient();
    client.connect(endpoints.get(0), connect -> {
      if (connect.failed()) {
        client.close();
        onOpen.handle(Future.failedFuture(connect.cause()));
        return;
      }
      // ready
      onOpen.handle(
        Future.succeededFuture(
          new RedisConnectionImpl(context, client, connect.result(), endpoints.get(0))));
    });

    return this;
  }

  @Override
  public void close() {
  }
}
