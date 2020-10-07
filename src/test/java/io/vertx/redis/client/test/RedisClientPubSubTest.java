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
package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
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
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @Before
  public void before(TestContext should) {
    Async test = should.async();
    redisPublish = Redis.createClient(rule.vertx(), "redis://localhost:7006")
      .connect(connectPub -> {
        should.assertTrue(connectPub.succeeded());

        pubConn = connectPub.result();

        redisSubscribe = Redis.createClient(rule.vertx(), "redis://localhost:7006")
          .connect(connectSub -> {
            should.assertTrue(connectSub.succeeded());

            subConn = connectSub.result();
            test.complete();
          });
      });
  }

  @After
  public void after(TestContext should) {
    redisPublish.close();
    redisSubscribe.close();
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Test
  public void testPublishSubscribe(TestContext should) {
    final Async test = should.async();
    pubConn.send(Request.cmd(Command.SUBSCRIBE).arg("news"), reply -> {
      should.assertTrue(reply.succeeded());
      rule.vertx().eventBus().consumer("io.vertx.redis.news", msg -> test.complete());
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo"), preply -> should.assertTrue(preply.succeeded()));
    });
  }

  @Test
  public void testPublishPSubscribe(TestContext should) {
    final Async test = should.async();
    subConn.send(Request.cmd(Command.PSUBSCRIBE).arg("new*"), reply -> {
      should.assertTrue(reply.succeeded());
      rule.vertx().eventBus().consumer("io.vertx.redis.new*", msg -> test.complete());
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo"), preply -> should.assertTrue(preply.succeeded()));
    });
  }
}
