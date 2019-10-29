package io.vertx.redis.client.test;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisPooledTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
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

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
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

    Redis.createClient(rule.vertx(), new RedisOptions().addEndpoint("redis://localhost:7006/0"))
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

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
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
  public void simpleTestAPI(TestContext should) {
    final Async test = should.async();

    RedisAPI redis = RedisAPI.api(Redis.createClient(rule.vertx(), "redis://localhost:7006"));

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
        .setMaxPoolWaiting(8)
        .setEndpoint("redis://localhost:7006")));

    IntStream.range(0, 5).forEach(i -> {
      vertx.setTimer(1, timerid -> {
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
      });
    });
  }
}
