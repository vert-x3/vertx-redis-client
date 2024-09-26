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

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(VertxUnitRunner.class)
public class RedisClientPubSubTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  private Vertx vertx;

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;
  private MessageConsumer<Object> consumer;


  @Before
  public void before() throws Exception {
    vertx = Vertx.vertx();
    redisPublish = Redis.createClient(vertx, new RedisOptions().setConnectionString(redis.getRedisUri()));
    pubConn = redisPublish.connect().await(20, TimeUnit.SECONDS);
    redisSubscribe = Redis.createClient(vertx, new RedisOptions().setConnectionString(redis.getRedisUri()));
    subConn = redisSubscribe.connect().await(20, TimeUnit.SECONDS);
  }

  @After
  public void after() throws Exception {
    redisPublish.close().await(20, TimeUnit.SECONDS);
    redisSubscribe.close().await(20, TimeUnit.SECONDS);
    vertx.close().await(20, TimeUnit.SECONDS);
  }

  @Test
  public void testPublishSubscribe(TestContext should) throws Exception {
    Async test = should.async();
    consumer = vertx.eventBus().consumer("io.vertx.redis.news", msg -> test.complete());
    subConn.send(Request.cmd(Command.SUBSCRIBE).arg("news")).await(20, TimeUnit.SECONDS);
    publishMsg(Request.cmd(Command.PUBLISH).arg("news").arg("foo"), 5);
  }

  @Test
  public void testPublishPSubscribe(TestContext should) throws Exception {
    final Async test = should.async();
    consumer = vertx.eventBus().consumer("io.vertx.redis.new*", msg -> test.complete());
    subConn.send(Request.cmd(Command.PSUBSCRIBE).arg("new*")).await(20, TimeUnit.SECONDS);
    publishMsg(Request.cmd(Command.PUBLISH).arg("news").arg("foo"), 5);
  }

  private void publishMsg(Request req, int retries) throws Exception {
    while (true) {
      assertTrue(retries > 0);
      Integer num = pubConn
        .send(req)
        .await(20, TimeUnit.SECONDS)
        .toInteger();
      if (num > 0) {
        // Racy publish, no subscriber yet
        break;
      }
    }
  }
}
