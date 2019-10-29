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

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.RedisTransaction;
import io.vertx.redis.op.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server unless
 * the <code>host</code> or <code>port</code> system property is specified. In this case the
 * test assumes an external database will be used.
 */
@RunWith(VertxUnitRunner.class)
public class RedisClientPubSubTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private RedisClient redisPublish;
  private RedisClient redisSubscribe;

  @Before
  public void before() {
    redisPublish = RedisClient.create(rule.vertx(), new RedisOptions().setPort(7006));
    redisSubscribe = RedisClient.create(rule.vertx(), new RedisOptions().setPort(7006));
  }

  @After
  public void after(TestContext should) {
    redisPublish.close(null);
    redisSubscribe.close(null);
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Test
  public void testPublishSubscribe(TestContext should) {
    final Async test = should.async();
    String key = makeKey();
    redisSubscribe.subscribe(key, reply -> {
      should.assertTrue(reply.succeeded());
      rule.vertx().eventBus().consumer("io.vertx.redis." + key, msg -> {
        test.complete();
      });
      redisPublish.publish(key, "foo", preply -> {
        should.assertTrue(preply.succeeded());
      });
    });
  }
}
