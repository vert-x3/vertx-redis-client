package io.vertx.tests.redis.client;

import io.vertx.core.VertxOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
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

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @ClassRule
  public static final RedisCluster redis = new RedisCluster();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext(new VertxOptions().setEventLoopPoolSize(1));

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @Before
  public void before(TestContext should) {
    Async test = should.async();
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisNode0Uri())
      .setType(RedisClientType.CLUSTER);
    redisPublish = Redis.createClient(rule.vertx(), options);
    redisPublish
      .connect().onComplete(connectPub -> {
        should.assertTrue(connectPub.succeeded());

        pubConn = connectPub.result();

        redisSubscribe = Redis.createClient(rule.vertx(), options);
        redisSubscribe
          .connect().onComplete(connectSub -> {
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

  @Test
  public void testSubscribeMultipleTimes(TestContext should) {
    int N = 10;
    String CHAN = "chan1";
    final Async test = should.async(N);
    for (int i = 0; i < N; i++)
      rule.vertx().eventBus().consumer(CHAN, msg -> {
        test.countDown();
      });
    rule.vertx().eventBus().consumer("io.vertx.redis." + CHAN, msg -> {
      System.out.println("received redis msg " + msg.body());
      rule.vertx().eventBus().publish(CHAN, msg.body());
    });
    subUnsub(CHAN, N, should.async(N));
    rule.vertx().setTimer(1000, id ->
      pubConn.send(Request.cmd(Command.PUBLISH).arg(CHAN).arg("hello")).onComplete(preply -> should.assertTrue(preply.succeeded())));
  }

  private void subUnsub(String channel, int attempts, Async testSub) {
    subConn.send(Request.cmd(Command.UNSUBSCRIBE).arg(channel)).onComplete(unreply -> {
      subConn.send(Request.cmd(Command.SUBSCRIBE).arg(channel)).onComplete(reply -> {
        testSub.countDown();
        if (attempts > 1) {
          subUnsub(channel, attempts - 1, testSub);
        }
      });
    });
  }
}
