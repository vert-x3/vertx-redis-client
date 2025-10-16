package io.vertx.tests.redis.client;

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
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.PSUBSCRIBE;
import static io.vertx.redis.client.Command.PUBLISH;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.executeWhenConditionSatisfied;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisPubSubTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;


  @BeforeEach
  public void before(VertxTestContext test) throws Exception {
    RedisOptions options = new RedisOptions().setConnectionString(redis.getRedisUri());

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
  public void after() throws Exception {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void publishSubscribe_withHandler(VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint(2);
    AtomicInteger counter = new AtomicInteger(0);

    context.vertx().eventBus().consumer("io.vertx.redis.news", msg -> {
      checkpoint.flag();
      counter.incrementAndGet();
    });

    subConn.handler(EventBusHandler.create(context.vertx()));
    subConn.send(Request.cmd(Command.SUBSCRIBE).arg("news")).onComplete(test.succeeding(subscribe -> {
      executeWhenConditionSatisfied(context.vertx(), () -> counter.get() == 1, () -> {
        pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo")).onComplete(test.succeeding(publish -> {
          assertEquals(1, publish.toInteger());
        }));
      });
    }));
  }

  @Test
  public void publishPSubscribe_withHandler(VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint(2);
    AtomicInteger counter = new AtomicInteger(0);

    context.vertx().eventBus().consumer("io.vertx.redis.new*", msg -> {
      checkpoint.flag();
      counter.incrementAndGet();
    });

    subConn.handler(EventBusHandler.create(context.vertx()));
    subConn.send(Request.cmd(Command.PSUBSCRIBE).arg("new*")).onComplete(test.succeeding(subscribe -> {
      executeWhenConditionSatisfied(context.vertx(), () -> counter.get() == 1, () -> {
        pubConn.send(Request.cmd(Command.PUBLISH).arg("news").arg("foo")).onComplete(test.succeeding(publish -> {
          assertEquals(1, publish.toInteger());
        }));
      });
    }));
  }

  @Test
  public void publishSubscribe_naive(VertxTestContext test) {
    AtomicInteger subscribeCnt = new AtomicInteger(0);
    AtomicInteger messageCnt = new AtomicInteger(0);

    subConn.handler(message -> {
      switch (message.type()) {
        case PUSH:
        case MULTI:
          if (message.get(0).toString().equals("message")) {
            assertEquals(3, message.size());
            messageCnt.incrementAndGet();
          }
          if (message.get(0).toString().equals("subscribe")) {
            assertEquals(3, message.size());
            subscribeCnt.incrementAndGet();
          }
          if (subscribeCnt.get() + messageCnt.get() == 2) {
            test.completeNow();
          }
          break;
        default:
          test.failNow("Unknown message type: " + message.type());
      }
    });

    subConn.send(cmd(SUBSCRIBE).arg("mychannel")).onComplete(test.succeeding(subscribe -> {
      executeWhenConditionSatisfied(context.vertx(), () -> subscribeCnt.get() == 1, () -> {
        pubConn.send(cmd(PUBLISH).arg("mychannel").arg(123456)).onComplete(test.succeeding(publish -> {
          assertEquals(1, publish.toInteger());
        }));
      });
    }));
  }

  @Test
  public void publishPSubscribe_naive(VertxTestContext test) {
    AtomicInteger psubscribeCnt = new AtomicInteger(0);
    AtomicInteger pmessageCnt = new AtomicInteger(0);

    subConn.handler(message -> {
      switch (message.type()) {
        case PUSH:
        case MULTI:
          if (message.get(0).toString().equals("pmessage")) {
            assertEquals(4, message.size());
            pmessageCnt.incrementAndGet();
          }
          if (message.get(0).toString().equals("psubscribe")) {
            assertEquals(3, message.size());
            psubscribeCnt.incrementAndGet();
          }
          if (psubscribeCnt.get() + pmessageCnt.get() == 12) {
            test.completeNow();
          }
          break;
        default:
          test.failNow("Unknown message type: " + message.type());
      }
    });

    List<String> patterns = List.of("A*", "B*", "C*", "D*", "E*", "F*");
    List<String> matchingChannels = List.of("A", "B1", "Co", "DDD", "E234", "F");

    Request psub = cmd(PSUBSCRIBE);
    patterns.forEach(psub::arg);

    subConn.send(psub).onComplete(test.succeeding(subscribe -> {
      executeWhenConditionSatisfied(context.vertx(), () -> psubscribeCnt.get() == 6, () -> {
        for (String ch : matchingChannels) {
          pubConn.send(cmd(PUBLISH).arg(ch).arg(System.nanoTime())).onComplete(test.succeeding(publish -> {
            assertEquals(1, publish.toInteger());
          }));
        }
      });
    }));
  }
}
