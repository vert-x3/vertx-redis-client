package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.Command.*;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:7")
    .withExposedPorts(6379);

  private RedisConnection pub;
  private RedisConnection sub;

  @Before
  public void setUp(TestContext should) {
    final Async setUp = should.async();

    Redis.createClient(rule.vertx(), "redis://" + redis.getHost() + ":" + redis.getFirstMappedPort())
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());
        pub = onCreate.result();
        pub.exceptionHandler(should::fail);

        Redis.createClient(rule.vertx(), "redis://" + redis.getHost() + ":" + redis.getFirstMappedPort())
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

    sub.send(cmd(SUBSCRIBE).arg("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> pub.send(cmd(PUBLISH).arg("mychannel").arg(123456), publish -> {
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

    sub.send(psub_request, subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> patterns.forEach(p -> pub.send(cmd(PUBLISH).arg(p).arg(System.nanoTime()), publish -> {
          should.assertTrue(publish.succeeded());
          should.assertNotNull(publish.result());
        })));
    });
  }
}
