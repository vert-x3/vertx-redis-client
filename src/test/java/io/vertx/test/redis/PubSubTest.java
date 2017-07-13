/**
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

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PubSubTest extends AbstractRedisClientBase {

  @Test
  public void testPubSub() {
    final String message = makeKey();

    // register a handler for the incoming message
    vertx.eventBus().consumer("io.vertx.redis.ch1", (Message<JsonObject> msg) -> {
      JsonObject value = msg.body().getJsonObject("value");
      assertEquals("ch1", value.getString("channel"));
      assertEquals(message, value.getString("message"));
      testComplete();
    });

    // on sub address subscribe to channel ch1
    redis.subscribe("ch1", subscribe -> {
      assertTrue(subscribe.succeeded());

      assertEquals("subscribe", subscribe.result().getValue(0));
      assertEquals("ch1", subscribe.result().getValue(1));
      assertEquals(1l, subscribe.result().getValue(2));

      // on pub address publish a message
      redis.publish("ch1", message, res -> {
        assertTrue(res.succeeded());
        assertEquals(Long.valueOf(1l), res.result());
      });
    });
    await();
  }

  @Test
  public void testPubSubPattern() {

    final String worldNews = "hello world";
    final String technologyNews = "hello vertx";
    final List<JsonObject> inbox = new ArrayList<>();

    // register a handler for all incoming messages
    vertx.eventBus().consumer("io.vertx.redis.news.*", (Message<JsonObject> msg) -> {
      inbox.add(msg.body().getJsonObject("value"));

      if (inbox.size() == 2) {
        if (
          (worldNews.equals(inbox.get(0).getString("message")) && technologyNews.equals(inbox.get(1).getString("message"))) ||
            (worldNews.equals(inbox.get(1).getString("message")) && technologyNews.equals(inbox.get(0).getString("message")))) {
          testComplete();
        }
      }
    });

    // on sub address subscribe to channels news.*
    redis.psubscribe("news.*", subscribe -> {
      assertTrue(subscribe.succeeded());

      assertEquals("psubscribe", subscribe.result().getValue(0));
      assertEquals("news.*", subscribe.result().getValue(1));
      assertEquals(1l, subscribe.result().getValue(2));

      // on pub address publish a message to news.wold
      redis.publish("news.world", worldNews, r0 -> {
        assertTrue(r0.succeeded());
        assertEquals(1l, r0.result().longValue());
      });

      // on pub address publish a message to news.wold
      redis.publish("news.technology", technologyNews, r0 -> {
        assertTrue(r0.succeeded());
        assertEquals(1l, r0.result().longValue());
      });
    });

    await();
  }

  @Test
  public void testLateJoin() {
    final String message = makeKey();
    final AtomicInteger cnt = new AtomicInteger(0);

    // register a handler for the incoming message
    vertx.eventBus().consumer("io.vertx.redis.ch2", (Message<JsonObject> msg) -> {
      JsonObject value = msg.body().getJsonObject("value");
      assertEquals("ch2", value.getString("channel"));
      assertEquals(message, value.getString("message"));
      if (cnt.incrementAndGet() == 2) {
        testComplete();
      }
    });

    // on sub address subscribe to channel ch2
    redis.subscribe("ch2", subscribe -> {
      assertTrue(subscribe.succeeded());

      assertEquals("subscribe", subscribe.result().getValue(0));
      assertEquals("ch2", subscribe.result().getValue(1));
      assertEquals(1l, subscribe.result().getValue(2));

      // deploy a new sub
      RedisClient redis2 = RedisClient.create(vertx, getConfig());

      // on sub address subscribe to channel ch2
      redis2.subscribe("ch2", subscribe2 -> {
        assertTrue(subscribe2.succeeded());

        assertEquals("subscribe", subscribe2.result().getValue(0));
        assertEquals("ch2", subscribe2.result().getValue(1));
        assertEquals(1l, subscribe2.result().getValue(2));

        // on pub address publish a message
        redis2.publish("ch2", message, r0 -> {
          assertTrue(r0.succeeded());
          assertEquals(2l, r0.result().longValue());
        });
      });
    });

    await();
  }

  @Test
  public void testReconnect() {
    final String message = makeKey();
    final AtomicInteger cnt = new AtomicInteger(0);

    // register a handler for the incoming message
    vertx.eventBus().consumer("io.vertx.redis.ch3", (Message<JsonObject> msg) -> {
      JsonObject value = msg.body().getJsonObject("value");
      assertEquals("ch3", value.getString("channel"));
      assertEquals(message, value.getString("message"));
      if (cnt.incrementAndGet() == 2) {
        testComplete();
      }
    });

    // on sub address subscribe to channel ch2
    redis.subscribe("ch3", subscribe -> {
      assertTrue(subscribe.succeeded());

      assertEquals("subscribe", subscribe.result().getValue(0));
      assertEquals("ch3", subscribe.result().getValue(1));
      assertEquals(1l, subscribe.result().getValue(2));

      redis.publish("ch3", message, r0 -> {
        assertTrue(r0.succeeded());

        try {
          stopRedis();
          // we need some time to let the process really terminate
          vertx.setTimer(500, v -> {
            try {
              startRedis();
              // we need some time to let the process really start
              vertx.setTimer(500, v1 -> {
                redis.ping(v2 -> {
                  assertTrue(v2.succeeded());
                  // we need some time to let the client reconnect and complete the handshake
                  vertx.setTimer(500, v3 -> {
                    redis.publish("ch3", message, r1 -> {
                      assertTrue(r1.succeeded());
                    });
                  });
                });
              });
            } catch (Exception e) {
              fail(e);
            }
          });
        } catch (Exception e) {
          fail(e);
        }
      });
    });

    await();
  }
}
