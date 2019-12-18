package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.ResponseType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.Command.*;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private RedisConnection pub;
  private RedisConnection sub;

  @Before
  public void setUp(TestContext should) {
    final Async setUp = should.async();

    Redis.createClient(rule.vertx(), "redis://127.0.0.1:7006")
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());
        pub = onCreate.result();
        pub.exceptionHandler(should::fail);

        Redis.createClient(rule.vertx(), "redis://127.0.0.1:7006")
          .connect(onCreate2 -> {
            should.assertTrue(onCreate2.succeeded());
            sub = onCreate2.result();
            sub.exceptionHandler(should::fail);

            setUp.complete();
          });
      });
  }

  @After
  public void tearDown() {
    pub.close();
    sub.close();
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    final AtomicInteger psubscribeCnt = new AtomicInteger(0);
    final AtomicInteger pmessageCnt = new AtomicInteger(0);

    sub.handler(message -> {
      System.out.println(message);
      should.assertTrue(message.type() == ResponseType.MULTI);

      if (message.get(0).toString().equals("message")) {
        should.assertEquals(3, message.size());
        pmessageCnt.incrementAndGet();
      }
      if (message.get(0).toString().equals("subscribe")) {
        should.assertEquals(3, message.size());
        psubscribeCnt.incrementAndGet();
      }
      if (psubscribeCnt.get() + pmessageCnt.get() == 2) {
        test.complete();
      }
    });

    sub.send(cmd(SUBSCRIBE).arg("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> {
        pub.send(cmd(PUBLISH).arg("mychannel").arg(123456), publish -> {
          should.assertTrue(publish.succeeded());
          should.assertNotNull(publish.result());
        });
      });
    });
  }

  @Test
  public void simpleMultiplePsubTest(TestContext should) {
    final Async test = should.async();

    final AtomicInteger psubscribeCnt = new AtomicInteger(0);
    final AtomicInteger pmessageCnt = new AtomicInteger(0);

    sub.handler(message -> {
      System.out.println(message);
      should.assertTrue(message.type() == ResponseType.MULTI);

      if (message.get(0).toString().equals("pmessage")) {
        should.assertEquals(4, message.size());
        pmessageCnt.incrementAndGet();
      }
      if (message.get(0).toString().equals("psubscribe")) {
        should.assertEquals(3, message.size());
        psubscribeCnt.incrementAndGet();
      }
      if (psubscribeCnt.get() + pmessageCnt.get() == 12) {
        test.complete();
      }
    });

    String REDIS_KEYSPACE_EVENT_CHANNEL_SET = "__keyspace@0__:";

    HashSet<String> patterns = new HashSet<String>() {{
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "A*");
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "B*");
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "C*");
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "D*");
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "E*");
      add(REDIS_KEYSPACE_EVENT_CHANNEL_SET + "F*");
    }};

    Request psub_request = cmd(PSUBSCRIBE);
    // Add all patterns to subscribe to
    patterns.stream().forEach(psub_request::arg);

    sub.send(psub_request, subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> {
        patterns.stream().forEach(p -> {
          pub.send(cmd(PUBLISH).arg(p).arg(System.nanoTime()), publish -> {
            should.assertTrue(publish.succeeded());
            should.assertNotNull(publish.result());
          });
        });
      });
    });
  }
}
