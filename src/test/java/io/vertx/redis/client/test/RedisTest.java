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
import static io.vertx.redis.client.Request.*;

@RunWith(VertxUnitRunner.class)
public class RedisTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void emptyStringTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.exceptionHandler(should::fail);

        redis.send(Request.cmd(Command.SET).arg(UUID.randomUUID().toString()).arg(""), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("OK", send.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void simpleSelectTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().addEndpoint("redis://localhost:7006/0"))
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void batchTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.batch(Arrays.asList(
          cmd(MULTI),
          cmd(SET).arg("a").arg(3),
          cmd(LPOP).arg("a"),
          cmd(EXEC)
        ), batch -> {
          should.assertTrue(batch.succeeded());
          test.complete();
        });
      });
  }

  @Test
  public void simpleTestAPI(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7006")
      .connect(create -> {
        should.assertTrue(create.succeeded());

        RedisAPI redis = RedisAPI.api(create.result());

        redis.set(Arrays.asList("key1", "value1"), set -> {
          should.assertTrue(set.succeeded());
          should.assertNotNull(set.result());

          should.assertEquals("OK", set.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void simpleStream(TestContext should) {

    final AtomicInteger cnt = new AtomicInteger(100);
    final Async test = should.async();
    final Vertx vertx = rule.vertx();

    Redis.createClient(vertx, "redis://localhost:7006")
      .connect(create -> {
        should.assertTrue(create.succeeded());

        RedisAPI redis = RedisAPI.api(create.result());

        IntStream.range(0, 100).forEach(i -> {
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
      });
  }
  }
