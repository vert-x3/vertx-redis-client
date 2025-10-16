package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.vertx.redis.client.Command.EXEC;
import static io.vertx.redis.client.Command.HMGET;
import static io.vertx.redis.client.Command.LPOP;
import static io.vertx.redis.client.Command.MULTI;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Command.ZADD;
import static io.vertx.redis.client.Command.ZRANGEBYSCORE;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void simpleTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(ex -> {
        });

        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
          assertNotNull(send);
          assertEquals("PONG", send.toString());
          test.completeNow();
        }));
      }));
  }

  @Test
  public void emptyStringTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(test::failNow);

        conn.send(Request.cmd(Command.SET).arg(randomKey()).arg("")).onComplete(test.succeeding(send -> {
          assertNotNull(send);
          assertEquals("OK", send.toString());
          test.completeNow();
        }));
      }));
  }

  @Test
  public void simpleSelectTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri() + "/0"))
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(ex -> {
        });

        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
          assertNotNull(send);
          assertEquals("PONG", send.toString());
          test.completeNow();
        }));
      }));
  }

  @Test
  public void batchTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.batch(Arrays.asList(
          cmd(MULTI),
          cmd(SET).arg("a").arg(3),
          cmd(LPOP).arg("a"),
          cmd(EXEC)
        )).onComplete(test.succeedingThenComplete());
      }));
  }

  @Test
  public void batchEmptyTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.batch(List.of()).onComplete(test.succeedingThenComplete());
      }));
  }

  @Test
  public void simpleTestAPI(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        RedisAPI redis = RedisAPI.api(conn);

        redis.set(Arrays.asList("key1", "value1")).onComplete(test.succeeding(set -> {
          assertNotNull(set);
          assertEquals("OK", set.toString());
          test.completeNow();
        }));
      }));
  }

  @Test
  public void simpleStream(VertxTestContext test) {
    final Vertx vertx = context.vertx();

    Checkpoint checkpoint = test.checkpoint(100);

    Redis.createClient(vertx, new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        RedisAPI redis = RedisAPI.api(conn);

        IntStream.range(0, 100).forEach(i -> vertx.setTimer(1, timerid -> {
          redis.set(Arrays.asList("foo", "bar"));

          // EXPECTED NULL
          redis.get("redis_test").onComplete(test.succeeding(res -> {
            assertNull(res);
            checkpoint.flag();
          }));
        }));
      }));
  }

  @Test
  public void simpleFullQueue(VertxTestContext test) {
    final Vertx vertx = context.vertx();

    RedisOptions options = new RedisOptions()
      .setMaxWaitingHandlers(10)
      .addConnectionString(redis.getRedisUri());

    Redis.createClient(context.vertx(), options)
      .connect().onComplete(test.succeeding(conn -> {
        final RedisAPI redisApi = RedisAPI.api(conn);

        AtomicInteger cnt = new AtomicInteger();
        List<Future<Response>> futures = new ArrayList<>();
        IntStream.range(0, 100).forEach(i -> {
          Promise<Response> p = Promise.promise();
          vertx.setTimer(1, timerid -> redisApi.set(Arrays.asList("foo-" + i, "bar")).onComplete(p));
          futures.add(p.future());
        });

        Future.all(futures)
          .onFailure(f -> {
            test.verify(() -> {
              assertEquals("Redis waiting queue is full", f.getMessage());
            });
            test.completeNow();
          })
          .onSuccess(r -> {
            if (cnt.get() == 100) {
              test.failNow("Should not succeed!");
            }
          });
      }));
  }

  @Test
  public void testZAdd(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        RedisAPI api = RedisAPI.api(conn);

        api
          .zadd(Arrays.asList("anykey", "1598248800", "member1", "1598248700", "member2"))
          .onComplete(test.succeeding(zadd -> {
            api.zrange(Arrays.asList("anykey", "0", "-1", "WITHSCORES")).onComplete(test.succeedingThenComplete());
          }));
      }));
  }

  @Test
  public void buggyHmget(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.send(cmd(HMGET).arg("nonExistingKey").arg("nonExistingValue1").arg("nonExistingValue2"))
          .onComplete(test.succeedingThenComplete());
      }));
  }

  @Test
  public void buggyIterator(VertxTestContext test) {
    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .connect().onComplete(test.succeeding(conn -> {
        final String key = randomKey();

        conn.send(cmd(ZADD).arg(key).arg("1.0").arg("testValue1").arg("2.0").arg("testValue2")).onComplete(test.succeeding(zadd -> {
          conn.send(cmd(ZRANGEBYSCORE).arg(key).arg("0.0").arg("3.0").arg("WITHSCORES")).onComplete(test.succeeding(zrangebyscore -> {
            assertEquals(ResponseType.MULTI, zrangebyscore.type());
            //verify that keys are absent
            assertEquals(0, zrangebyscore.getKeys().size());

            //collect all the values (buggy)
            List<String> buggyList = new ArrayList<>();
            for (Response r : zrangebyscore) {
              buggyList.add(r.toString());
            }
            //verify that first value is present
            assertEquals("[testValue1, 1.0]", buggyList.get(0));
            assertEquals("[testValue2, 2.0]", buggyList.get(1));
            assertEquals(2, buggyList.size());

            //collect all the values (correct)
            List<String> correctList = new ArrayList<>();
            for (int i = 0; i < zrangebyscore.size(); i++) {
              correctList.add(zrangebyscore.get(i).toString());
            }
            //verify both values are present
            assertEquals("[testValue1, 1.0]", correctList.get(0));
            assertEquals("[testValue2, 2.0]", correctList.get(1));
            assertEquals(2, correctList.size());
            test.completeNow();
          }));
        }));
      }));
  }

  @Test
  public void testBatch2(VertxTestContext test) {
    List<Request> requests = Arrays.asList(
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")));

    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()))
      .batch(requests)
      .onComplete(test.failing(err -> {
        assertTrue(err.getMessage().contains("ERR [0]"));
        assertTrue(err.getMessage().contains("ERR [1]"));
        assertTrue(err.getMessage().contains("ERR [2]"));
        assertTrue(err.getMessage().contains("ERR [3]"));
        test.completeNow();
      }));
  }

  @Test
  public void preservesContext(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));

    PreservesContext.sendWithoutConnect(client, test);
    PreservesContext.batchWithoutConnect(client, test);
    PreservesContext.connect(client, test);
    PreservesContext.connectThenSend(client, test);
    PreservesContext.connectThenBatch(client, test);
  }
}
