/*
 * Copyright 2015 Red Hat, Inc.
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
package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.redis.client.Request.cmd;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test relies on a REJSON capable Redis server.
 */
@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientREJSONTest {

  @Container
  public static final GenericContainer<?> container = new GenericContainer<>("redislabs/rejson:2.4.7")
    .withExposedPorts(6379);

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testJsonSetAndGet(VertxTestContext test) {
    final Redis client = Redis.createClient(
      context.vertx(),
      "redis://" + container.getHost() + ":" + container.getFirstMappedPort());

    client
      .send(cmd(Command.create("JSON.SET")).arg("foo").arg(".").arg("\"bar\""))
      .compose(response -> {
        // OK
        return client.send(cmd(Command.create("JSON.GET")).arg("foo"));
      })
      .compose(response -> {
        assertEquals("\"bar\"", response.toString());
        return client.send(cmd(Command.create("JSON.TYPE")).arg("foo").arg("."));
      })
      .compose(response -> {
        assertEquals("string", response.toString());
        return Future.succeededFuture();
      })
      .onComplete(test.succeedingThenComplete());
  }
}
