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
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisClientPubSubTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @Before
  public void before(TestContext should) {
    Async test = should.async();
    redisPublish = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    redisPublish
      .connect().onComplete(connectPub -> {
        should.assertTrue(connectPub.succeeded());

        pubConn = connectPub.result();

        redisSubscribe = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
        redisSubscribe
          .connect().onComplete(connectSub -> {
            should.assertTrue(connectSub.succeeded());

            subConn = connectSub.result();
            test.complete();
          });
      });
  }

  @After
  public void after() {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void testPublishSubscribe(TestContext should) {
    final Async test = should.async();
    subConn.send(Request.cmd(Command.SUBSCRIBE).arg("news")).onComplete(reply -> {
      should.assertTrue(reply.succeeded());
      rule.vertx().eventBus().consumer("io.vertx.redis.news", msg -> test.complete());
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo")).onComplete(preply -> should.assertTrue(preply.succeeded()));
    });
  }

  @Test
  public void testPublishPSubscribe(TestContext should) {
    final Async test = should.async();
    subConn.send(Request.cmd(Command.PSUBSCRIBE).arg("new*")).onComplete(reply -> {
      should.assertTrue(reply.succeeded());
      rule.vertx().eventBus().consumer("io.vertx.redis.new*", msg -> test.complete());
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo")).onComplete(preply -> should.assertTrue(preply.succeeded()));
    });
  }
}
