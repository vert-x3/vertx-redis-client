package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.PSUBSCRIBE;
import static io.vertx.redis.client.Command.PUBLISH;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.test.TestUtils.executeWhenConditionSatisfied;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

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

    final AtomicInteger subscribeCnt = new AtomicInteger(0);
    final AtomicInteger messageCnt = new AtomicInteger(0);

    sub.handler(message -> {
      System.out.println(message);
      switch (message.type()) {
        case PUSH:
        case MULTI:
          if (message.get(0).toString().equals("message")) {
            should.assertEquals(3, message.size());
            messageCnt.incrementAndGet();
          }
          if (message.get(0).toString().equals("subscribe")) {
            should.assertEquals(3, message.size());
            subscribeCnt.incrementAndGet();
          }
          if (subscribeCnt.get() + messageCnt.get() == 2) {
            test.complete();
          }
          break;
        default:
          should.fail();
      }
    });

    sub.send(cmd(SUBSCRIBE).arg("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      executeWhenConditionSatisfied(rule.vertx(), () -> subscribeCnt.get() == 1, () -> {
        pub.send(cmd(PUBLISH).arg("mychannel").arg(123456), publish -> {
          should.assertTrue(publish.succeeded());
          should.assertEquals(1, publish.result().toInteger());
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

    List<String> patterns = Arrays.asList("A*", "B*", "C*", "D*", "E*", "F*");
    List<String> matchingChannels = Arrays.asList("A", "B1", "Co", "DDD", "E234", "F");

    Request psub = cmd(PSUBSCRIBE);
    patterns.forEach(psub::arg);

    sub.send(psub, subscribe -> {
      should.assertTrue(subscribe.succeeded());

      executeWhenConditionSatisfied(rule.vertx(), () -> psubscribeCnt.get() == 6, () -> {
        for (String ch : matchingChannels) {
          pub.send(cmd(PUBLISH).arg(ch).arg(System.nanoTime()), publish -> {
            should.assertTrue(publish.succeeded());
            should.assertEquals(1, publish.result().toInteger());
          });
        }
      });
    });
  }
}
