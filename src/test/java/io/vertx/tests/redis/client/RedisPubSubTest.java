package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.EventBusHandler;
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

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.PSUBSCRIBE;
import static io.vertx.redis.client.Command.PUBLISH;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;


  @Before
  public void before(TestContext test) throws Exception {
    Async async = test.async();
    RedisOptions options = new RedisOptions().setConnectionString(redis.getRedisUri());

    redisPublish = Redis.createClient(rule.vertx(), options);
    redisPublish.connect().onComplete(test.asyncAssertSuccess(connectPub -> {
      pubConn = connectPub;

      redisSubscribe = Redis.createClient(rule.vertx(), options);
      redisSubscribe.connect().onComplete(test.asyncAssertSuccess(connectSub -> {
        subConn = connectSub;

        async.complete();
      }));
    }));
  }

  @After
  public void after() throws Exception {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void publishSubscribe_withHandler(TestContext test) {
    Async async = test.async(2);

    rule.vertx().eventBus().consumer("io.vertx.redis.news", msg -> async.countDown());

    subConn.handler(EventBusHandler.create(rule.vertx()));
    subConn.send(Request.cmd(Command.SUBSCRIBE).arg("news")).onComplete(test.asyncAssertSuccess(result -> {
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo"));
    }));
  }

  @Test
  public void publishPSubscribe_withHandler(TestContext test) {
    Async async = test.async(2);

    rule.vertx().eventBus().consumer("io.vertx.redis.new*", msg -> async.countDown());

    subConn.handler(EventBusHandler.create(rule.vertx()));
    subConn.send(Request.cmd(Command.PSUBSCRIBE).arg("new*")).onComplete(test.asyncAssertSuccess(result -> {
      pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo"));
    }));
  }

  @Test
  public void publishSubscribe_naive(TestContext test) {
    Async async = test.async();

    AtomicInteger subscribeCnt = new AtomicInteger(0);
    AtomicInteger messageCnt = new AtomicInteger(0);

    subConn.handler(message -> {
      switch (message.type()) {
        case PUSH:
        case MULTI:
          if (message.get(0).toString().equals("message")) {
            test.assertEquals(3, message.size());
            messageCnt.incrementAndGet();
          }
          if (message.get(0).toString().equals("subscribe")) {
            test.assertEquals(3, message.size());
            subscribeCnt.incrementAndGet();
          }
          if (subscribeCnt.get() + messageCnt.get() == 2) {
            async.complete();
          }
          break;
        default:
          test.fail();
      }
    });

    subConn.send(cmd(SUBSCRIBE).arg("mychannel")).onComplete(test.asyncAssertSuccess(subscribe -> {
      pubConn.send(cmd(PUBLISH).arg("mychannel").arg(123456))
        .onComplete(test.asyncAssertSuccess(test::assertNotNull));
    }));
  }

  @Test
  public void publishPSubscribe_naive(TestContext test) {
    Async async = test.async();

    AtomicInteger psubscribeCnt = new AtomicInteger(0);
    AtomicInteger pmessageCnt = new AtomicInteger(0);

    subConn.handler(message -> {
      switch (message.type()) {
        case PUSH:
        case MULTI:
          if (message.get(0).toString().equals("pmessage")) {
            test.assertEquals(4, message.size());
            pmessageCnt.incrementAndGet();
          }
          if (message.get(0).toString().equals("psubscribe")) {
            test.assertEquals(3, message.size());
            psubscribeCnt.incrementAndGet();
          }
          if (psubscribeCnt.get() + pmessageCnt.get() == 12) {
            async.complete();
          }
          break;
        default:
          test.fail();
      }
    });

    Set<String> patterns = Set.of("A*", "B*", "C*", "D*", "E*", "F*");

    Request psub = cmd(PSUBSCRIBE);
    patterns.forEach(psub::arg);

    subConn.send(psub).onComplete(test.asyncAssertSuccess(subscribe -> {
      patterns.forEach(p -> {
        pubConn.send(cmd(PUBLISH).arg(p).arg(System.nanoTime()))
          .onComplete(test.asyncAssertSuccess(test::assertNotNull));
      });
    }));
  }
}
