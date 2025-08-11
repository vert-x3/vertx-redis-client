package io.vertx.tests.redis.client;

import io.vertx.core.json.JsonObject;
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
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.tests.redis.client.TestUtils.executeWhenConditionSatisfied;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubClusterTest {

  @ClassRule
  public static final RedisCluster redis = new RedisCluster();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @Before
  public void before(TestContext should) {
    Async test = should.async();
    RedisOptions options = new RedisOptions().setConnectionString(redis.getRedisNode0Uri());

    redisPublish = Redis.createClient(rule.vertx(), options);
    redisPublish.connect().onComplete(should.asyncAssertSuccess(connectPub -> {
        pubConn = connectPub;

        redisSubscribe = Redis.createClient(rule.vertx(), options);
        redisSubscribe.connect().onComplete(should.asyncAssertSuccess(connectSub -> {
            subConn = connectSub;

            test.complete();
          }));
      }));
  }

  @After
  public void after() {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void testSubscribeMultipleTimes(TestContext should) {
    final int N = 10;
    final String channel = "chan1";

    final Async test = should.async(N);
    for (int i = 0; i < N; i++) {
      rule.vertx().eventBus().consumer(channel, msg -> {
        test.countDown();
      });
    }

    AtomicInteger subs = new AtomicInteger();
    rule.vertx().eventBus().<JsonObject>consumer("io.vertx.redis." + channel, msg -> {
      if ("subscribe".equals(msg.body().getString("type"))) {
        subs.incrementAndGet();
      }
      if ("message".equals(msg.body().getString("type"))) {
        rule.vertx().eventBus().publish(channel, msg.body());
      }
    });

    subConn.handler(EventBusHandler.create(rule.vertx()));
    subUnsub(channel, N, should.async(N));

    executeWhenConditionSatisfied(rule.vertx(), () -> subs.get() == 10, () -> {
      pubConn.send(Request.cmd(Command.PUBLISH).arg(channel).arg("hello")).onComplete(should.asyncAssertSuccess(publish -> {
        should.assertEquals(1, publish.toInteger());
      }));
    });
  }

  private void subUnsub(String channel, int attempts, Async testSub) {
    subConn.send(Request.cmd(Command.UNSUBSCRIBE).arg(channel)).onComplete(unsub -> {
      subConn.send(Request.cmd(Command.SUBSCRIBE).arg(channel)).onComplete(sub -> {
        testSub.countDown();
        if (attempts > 1) {
          subUnsub(channel, attempts - 1, testSub);
        }
      });
    });
  }
}
