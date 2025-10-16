package io.vertx.tests.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.EventBusHandler;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.tests.redis.client.TestUtils.executeWhenConditionSatisfied;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisPubSubClusterTest {

  @Container
  public static final RedisCluster redis = new RedisCluster();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @BeforeEach
  public void before(VertxTestContext test) {
    RedisOptions options = new RedisOptions().setConnectionString(redis.getRedisNode0Uri());

    redisPublish = Redis.createClient(context.vertx(), options);
    redisPublish.connect().onComplete(test.succeeding(connectPub -> {
      pubConn = connectPub;

      redisSubscribe = Redis.createClient(context.vertx(), options);
      redisSubscribe.connect().onComplete(test.succeeding(connectSub -> {
        subConn = connectSub;

        test.completeNow();
      }));
    }));
  }

  @AfterEach
  public void after() {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void testSubscribeMultipleTimes(VertxTestContext test) {
    final int N = 10;
    final String channel = "chan1";

    Checkpoint checkpoint = test.checkpoint(N);
    for (int i = 0; i < N; i++) {
      context.vertx().eventBus().consumer(channel, msg -> {
        checkpoint.flag();
      });
    }

    AtomicInteger subs = new AtomicInteger();
    context.vertx().eventBus().<JsonObject>consumer("io.vertx.redis." + channel, msg -> {
      if ("subscribe".equals(msg.body().getString("type"))) {
        subs.incrementAndGet();
      }
      if ("message".equals(msg.body().getString("type"))) {
        context.vertx().eventBus().publish(channel, msg.body());
      }
    });

    subConn.handler(EventBusHandler.create(context.vertx()));
    subUnsub(channel, N, test.checkpoint(N));

    executeWhenConditionSatisfied(context.vertx(), () -> subs.get() == 10, () -> {
      pubConn.send(Request.cmd(Command.PUBLISH).arg(channel).arg("hello")).onComplete(test.succeeding(publish -> {
        assertEquals(1, publish.toInteger());
      }));
    });
  }

  private void subUnsub(String channel, int attempts, Checkpoint testSub) {
    subConn.send(Request.cmd(Command.UNSUBSCRIBE).arg(channel)).onComplete(unsub -> {
      subConn.send(Request.cmd(Command.SUBSCRIBE).arg(channel)).onComplete(sub -> {
        testSub.flag();
        if (attempts > 1) {
          subUnsub(channel, attempts - 1, testSub);
        }
      });
    });
  }
}
