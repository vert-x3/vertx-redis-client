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
package io.vertx.test.redis;

import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import static io.vertx.redis.client.Request.cmd;

/**
 * This test relies on a REJSON capable Redis server.
 */
@RunWith(VertxUnitRunner.class)
public class RedisClientREJSONTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> container = new GenericContainer<>("redislabs/rejson:2.4.7")
    .withExposedPorts(6379);

  @Test(timeout = 30_000L)
  public void testJsonSetAndGet(TestContext should) {
    final Async test = should.async();

    final Redis client = Redis.createClient(
      rule.vertx(),
      "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort());

    client
      .send(cmd(Command.create("JSON.SET")).arg("foo").arg(".").arg("\"bar\""))
      .compose(response -> {
        // OK
        return client.send(cmd(Command.create("JSON.GET")).arg("foo"));
      })
      .compose(response -> {
        should.assertEquals("\"bar\"", response.toString());
        return client.send(cmd(Command.create("JSON.TYPE")).arg("foo").arg("."));
      })
      .compose(response -> {
        should.assertEquals("string", response.toString());
        return Future.succeededFuture();
      })
      .onFailure(should::fail)
      .onSuccess(v -> {
        test.complete();
      });
  }
}
