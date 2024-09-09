package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.PSUBSCRIBE;
import static io.vertx.redis.client.Command.PUBLISH;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisPubSub2Test {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private RedisConnection pub;
  private RedisConnection sub;

  @Before
  public void setUp(TestContext should) {
    final Async setUp = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(onCreate -> {
        should.assertTrue(onCreate.succeeded());
        pub = onCreate.result();
        pub.exceptionHandler(should::fail);

        Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
          .connect().onComplete(onCreate2 -> {
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
      switch (message.type()) {
        case PUSH:
        case MULTI:
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
          break;
        default:
          should.fail();
      }
    });

    sub.send(cmd(SUBSCRIBE).arg("mychannel")).onComplete(subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> pub.send(cmd(PUBLISH).arg("mychannel").arg(123456)).onComplete(publish -> {
        should.assertTrue(publish.succeeded());
        should.assertNotNull(publish.result());
      }));
    });
  }

  @Test
  public void simpleMultiplePsubTest(TestContext should) {
    final Async test = should.async();

    final AtomicInteger psubscribeCnt = new AtomicInteger(0);
    final AtomicInteger pmessageCnt = new AtomicInteger(0);

    sub.handler(message -> {
      System.out.println(message);
      switch (message.type()) {
        case PUSH:
        case MULTI:
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
          break;
        default:
          should.fail();
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
    patterns.forEach(psub_request::arg);

    sub.send(psub_request).onComplete(subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> patterns.forEach(p -> pub.send(cmd(PUBLISH).arg(p).arg(System.nanoTime())).onComplete(publish -> {
        should.assertTrue(publish.succeeded());
        should.assertNotNull(publish.result());
      })));
    });
  }
}
