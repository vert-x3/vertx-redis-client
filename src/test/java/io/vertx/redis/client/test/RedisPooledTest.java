package io.vertx.redis.client.test;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.PooledRedisConnection;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisPooledTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> container = new GenericContainer<>("redis:6.0.6")
    .withExposedPorts(6379);

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .send(Request.cmd(Command.PING), send -> {
        should.assertTrue(send.succeeded());
        should.assertNotNull(send.result());

        should.assertEquals("PONG", send.result().toString());
        test.complete();
      });
  }

  @Test
  public void emptyStringTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .send(Request.cmd(Command.SET).arg(UUID.randomUUID().toString()).arg(""), send -> {
        should.assertTrue(send.succeeded());
        should.assertNotNull(send.result());

        should.assertEquals("OK", send.result().toString());
        test.complete();
      });
  }

  @Test
  public void simpleSelectTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort() + "/0")
      .send(Request.cmd(Command.PING), send -> {
        should.assertTrue(send.succeeded());
        should.assertNotNull(send.result());

        should.assertEquals("PONG", send.result().toString());
        test.complete();
      });
  }

  @Test
  public void batchTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .batch(Arrays.asList(
        cmd(MULTI),
        cmd(SET).arg("a").arg(3),
        cmd(LPOP).arg("a"),
        cmd(EXEC)
      ), batch -> {
        should.assertTrue(batch.succeeded());
        test.complete();
      });
  }

  @Test
  public void batchEmptyTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .batch(Collections.emptyList(), batch -> {
        should.assertTrue(batch.succeeded());
        test.complete();
      });
  }

  @Test
  public void simpleTestAPI(TestContext should) {
    final Async test = should.async();

    RedisAPI redis = RedisAPI.api(Redis.createClient(rule.vertx(), "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort()));

    redis.set(Arrays.asList("key1", "value1"), set -> {
      should.assertTrue(set.succeeded());
      should.assertNotNull(set.result());

      should.assertEquals("OK", set.result().toString());
      test.complete();
    });
  }

  @Test
  public void simpleStream(TestContext should) {

    final AtomicInteger cnt = new AtomicInteger(5);
    final Async test = should.async();
    final Vertx vertx = rule.vertx();

    RedisAPI redis = RedisAPI.api(Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setMaxPoolWaiting(10)
        .addConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())));

    IntStream.range(0, 5).forEach(i -> vertx.setTimer(1, timerid -> {

      redis.set(Arrays.asList("foo", "bar"), res -> {
      });

      // EXPECTED NULL
      redis.get("redis_test", res -> {
        if (res.failed()) {
          should.fail(res.cause());
        } else {
          should.assertNull(res.result());
        }
        if (cnt.decrementAndGet() == 0) {
          test.complete();
        }
      });
    }));
  }

  @Test(timeout = 30_000L)
  public void acquireConnectionsTest(TestContext should) {
    final Vertx vertx = rule.vertx();
    final Async test = should.async();

    Redis client = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
        .setMaxPoolSize(10)
        .setMaxPoolWaiting(10));

    final AtomicInteger counter = new AtomicInteger();
    final AtomicBoolean closing = new AtomicBoolean(false);
    final AtomicInteger pending = new AtomicInteger();

    // this test asserts that the pools behaves as expected it shall return 10 new connections
    // and will fail on the 21st call as the 10 waiting slots are taken

    vertx.setPeriodic(250, event -> {
      counter.incrementAndGet();
      client.connect(event1 -> {
        if (event1.succeeded()) {
          should.assertTrue(counter.get() <= 10);
        } else {
          should.assertTrue(counter.get() == 21);
          if (closing.compareAndSet(false, true)) {
            vertx.cancelTimer(event);
            client.close();
          } else {
            int _pending = pending.incrementAndGet();
            should.assertTrue(_pending <= 10);
            if (_pending == 10) {
              test.complete();
            }
          }
        }
      });
    });
  }

  @Test
  public void testLeakOfSubscriptions(TestContext should) {
    final Async test = should.async();

    final String connectionString = "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort();
    final Vertx vertx = rule.vertx();

    Redis redis = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString(connectionString)
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1));

    redis
      .connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        conn
          .send(Request.cmd(SUBSCRIBE).arg("news"))
          .onFailure(should::fail)
          .onSuccess(msg -> {
            conn.close();
            // escape the current ctx to avoid inlining
            vertx.setTimer(5L, v -> {
              redis
                .connect()
                .onFailure(should::fail)
                .onSuccess(conn2 -> {
                  should.assertNotEquals(((PooledRedisConnection) conn).actual(), ((PooledRedisConnection) conn2).actual());
                  conn2
                    .send(Request.cmd(GET).arg("foo"))
                    .onFailure(should::fail)
                    .onSuccess(msg2 -> {
                      System.out.println(msg2);
                      test.complete();
                    });
                });
            });
          });
      });
  }

  @Test
  public void testReuse(TestContext should) {
    final Async test = should.async();

    final String connectionString = "redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort();
    final Vertx vertx = rule.vertx();

    Redis redis = Redis.createClient(
      vertx,
      new RedisOptions()
        .addConnectionString(connectionString)
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1));

    redis
      .connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        conn
          .send(Request.cmd(INFO))
          .onFailure(should::fail)
          .onSuccess(msg -> {
            conn.close();
            // escape the current ctx to avoid inlining
            vertx.setTimer(5L, v -> {
              redis
                .connect()
                .onFailure(should::fail)
                .onSuccess(conn2 -> {
                  should.assertEquals(((PooledRedisConnection) conn).actual(), ((PooledRedisConnection) conn2).actual());
                  conn2
                    .send(Request.cmd(GET).arg("foo"))
                    .onFailure(should::fail)
                    .onSuccess(msg2 -> test.complete());
                });
            });
          });
      });
  }

  @Test
  public void simpleTestRecycle(TestContext should) {
    final Async test = should.async();

    final Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1)
        .setPoolCleanerInterval(1000)
        .setPoolRecycleTimeout(1000));

    client.connect()
      .onSuccess(conn -> {
        long conn1Id = ((PooledRedisConnection) conn).actual().hashCode();
        conn.close();

        rule.vertx()
          .setTimer(2000L, t -> {
            client.connect()
              .onSuccess(conn2 -> {
                long conn2Id = ((PooledRedisConnection) conn2).actual().hashCode();
                should.assertNotEquals(conn1Id, conn2Id);
                conn2.close();
                test.complete();
              });
          });
      });
  }

  @Test
  public void simpleTestReuse(TestContext should) {
    final Async test = should.async();

    final Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
        .setMaxPoolSize(1)
        .setMaxPoolWaiting(1)
        .setPoolCleanerInterval(1000)
        .setPoolRecycleTimeout(1000));

    client.connect()
      .onSuccess(conn -> {
        long conn1Id = ((PooledRedisConnection) conn).actual().hashCode();
        conn.close();

        rule.vertx()
          .setTimer(100L, t -> {
            client.connect()
              .onSuccess(conn2 -> {
                long conn2Id = ((PooledRedisConnection) conn2).actual().hashCode();
                should.assertEquals(conn1Id, conn2Id);
                conn2.close();
                test.complete();
              });
          });
      });
  }
}
