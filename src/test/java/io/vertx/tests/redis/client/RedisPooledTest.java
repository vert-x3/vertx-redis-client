package io.vertx.tests.redis.client;

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
import io.vertx.redis.client.impl.PooledRedisConnection;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.vertx.redis.client.Command.EXEC;
import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.INFO;
import static io.vertx.redis.client.Command.LPOP;
import static io.vertx.redis.client.Command.MULTI;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisPooledTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void simpleTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), redis.getRedisUri())
      .send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
        assertNotNull(send);

        assertEquals("PONG", send.toString());
        test.completeNow();
      }));
  }

  @Test
  public void emptyStringTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), redis.getRedisUri())
      .send(Request.cmd(Command.SET).arg(randomKey()).arg("")).onComplete(test.succeeding(send -> {
        assertNotNull(send);
        assertEquals("OK", send.toString());
        test.completeNow();
      }));
  }

  @Test
  public void simpleSelectTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), redis.getRedisUri() + "/0")
      .send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
        assertNotNull(send);
        assertEquals("PONG", send.toString());
        test.completeNow();
      }));
  }

  @Test
  public void batchTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), redis.getRedisUri())
      .batch(Arrays.asList(
        cmd(MULTI),
        cmd(SET).arg("a").arg(3),
        cmd(LPOP).arg("a"),
        cmd(EXEC)
      )).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void batchEmptyTest(VertxTestContext test) {
    Redis.createClient(context.vertx(), redis.getRedisUri())
      .batch(Collections.emptyList()).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void simpleTestAPI(VertxTestContext test) {
    RedisAPI api = RedisAPI.api(Redis.createClient(context.vertx(), redis.getRedisUri()));

    api.set(Arrays.asList("key1", "value1")).onComplete(test.succeeding(set -> {
      assertEquals("OK", set.toString());
      test.completeNow();
    }));
  }

  @Test
  public void simpleStream(VertxTestContext test) {

    final Vertx vertx = context.vertx();

    RedisAPI api = RedisAPI.api(Redis.createClient(
      context.vertx(),
      new RedisOptions()
        .setMaxPoolWaiting(10)
        .addConnectionString(redis.getRedisUri())));

    Checkpoint checkpoint = test.checkpoint(5);

    IntStream.range(0, 5).forEach(i -> vertx.setTimer(1, timerid -> {

      api.set(Arrays.asList("foo", "bar"));

      // EXPECTED NULL
      api.get("redis_test").onComplete(test.succeeding(res -> {
        assertNull(res);
        checkpoint.flag();
      }));
    }));
  }

  @Test
  public void acquireConnectionsTest(VertxTestContext test) {
    final Vertx vertx = context.vertx();

    Redis client = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString(redis.getRedisUri())
        .setMaxPoolSize(10)
        .setMaxPoolWaiting(10));

    final AtomicInteger counter = new AtomicInteger();
    final AtomicBoolean closing = new AtomicBoolean(false);
    final AtomicInteger pending = new AtomicInteger();

    // this test asserts that the pool behaves as expected: it shall return 10 new connections
    // and will fail on the 21st call as the 10 waiting slots are taken

    vertx.setPeriodic(250, event -> {
      counter.incrementAndGet();
      client.connect().onComplete(event1 -> {
        if (event1.succeeded()) {
          assertTrue(counter.get() <= 10);
        } else {
          assertEquals(21, counter.get());
          if (closing.compareAndSet(false, true)) {
            vertx.cancelTimer(event);
            client.close();
          } else {
            int _pending = pending.incrementAndGet();
            assertTrue(_pending <= 10);
            if (_pending == 10) {
              test.completeNow();
            }
          }
        }
      });
    });
  }

  @Test
  public void testLeakOfSubscriptions(VertxTestContext test) {
    final Vertx vertx = context.vertx();

    Redis client = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString(redis.getRedisUri())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1));

    client
      .connect()
      .onComplete(test.succeeding(conn -> {
        conn
          .send(Request.cmd(SUBSCRIBE).arg("news"))
          .onComplete(test.succeeding(msg -> {
            conn.close();
            // escape the current ctx to avoid inlining
            vertx.setTimer(5L, v -> {
              client
                .connect()
                .onComplete(test.succeeding(conn2 -> {
                  assertNotEquals(((PooledRedisConnection) conn).actual(), ((PooledRedisConnection) conn2).actual());
                  conn2
                    .send(Request.cmd(GET).arg("foo"))
                    .onComplete(test.succeedingThenComplete());
                }));
            });
          }));
      }));
  }

  @Test
  public void testReuse(VertxTestContext test) {
    final Vertx vertx = context.vertx();

    Redis client = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString(redis.getRedisUri())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1));

    client
      .connect()
      .onComplete(test.succeeding(conn -> {
        conn
          .send(Request.cmd(INFO))
          .onComplete(test.succeeding(msg -> {
            conn.close();
            // escape the current ctx to avoid inlining
            vertx.setTimer(5L, v -> {
              client
                .connect()
                .onComplete(test.succeeding(conn2 -> {
                  assertEquals(((PooledRedisConnection) conn).actual(), ((PooledRedisConnection) conn2).actual());
                  conn2
                    .send(Request.cmd(GET).arg("foo"))
                    .onComplete(test.succeedingThenComplete());
                }));
            });
          }));
      }));
  }

  @Test
  public void simpleTestRecycle(VertxTestContext test) {
    final Redis client = Redis.createClient(
      context.vertx(),
      new RedisOptions()
        .setConnectionString(redis.getRedisUri())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1)
        .setPoolCleanerInterval(1000)
        .setPoolRecycleTimeout(1000));

    client.connect()
      .onComplete(test.succeeding(conn -> {
        long conn1Id = ((PooledRedisConnection) conn).actual().hashCode();
        conn.close();

        context.vertx()
          .setTimer(2000L, t -> {
            client.connect()
              .onComplete(test.succeeding(conn2 -> {
                long conn2Id = ((PooledRedisConnection) conn2).actual().hashCode();
                assertNotEquals(conn1Id, conn2Id);
                conn2.close();
                test.completeNow();
              }));
          });
      }));
  }

  @Test
  public void simpleTestReuse(VertxTestContext test) {
    final Redis client = Redis.createClient(
      context.vertx(),
      new RedisOptions()
        .setConnectionString(redis.getRedisUri())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1)
        .setPoolCleanerInterval(1000)
        .setPoolRecycleTimeout(1000));

    client.connect()
      .onComplete(test.succeeding(conn -> {
        long conn1Id = ((PooledRedisConnection) conn).actual().hashCode();
        conn.close();

        context.vertx()
          .setTimer(100L, t -> {
            client.connect()
              .onComplete(test.succeeding(conn2 -> {
                long conn2Id = ((PooledRedisConnection) conn2).actual().hashCode();
                assertEquals(conn1Id, conn2Id);
                conn2.close();
                test.completeNow();
              }));
          });
      }));
  }
}
