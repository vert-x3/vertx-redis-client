package io.vertx.redis.client.test;

import io.vertx.core.*;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisTest {

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:7")
    .withExposedPorts(6379);

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
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

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
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

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort() + "/0"))
      .connect().onComplete(create -> {
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

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
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
  public void batchEmptyTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.batch(Collections.emptyList(), batch -> {
          should.assertTrue(batch.succeeded());
          test.complete();
        });
      });
  }

  @Test
  public void simpleTestAPI(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
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

    Redis.createClient(vertx, new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        RedisAPI redis = RedisAPI.api(create.result());

        IntStream.range(0, 100).forEach(i -> vertx.setTimer(1, timerid -> {
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
      });
  }

  @Test(timeout = 10_000L)
  public void simpleFullQueue(TestContext should) {
    final Async test = should.async();
    final Vertx vertx = rule.vertx();

    RedisOptions options = new RedisOptions()
      .setMaxWaitingHandlers(10)
      .addConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort());

    Redis.createClient(rule.vertx(), options)
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();
        final RedisAPI redisApi = RedisAPI.api(redis);

        AtomicInteger cnt = new AtomicInteger();
        List<Future> futures = new ArrayList<>();
        IntStream.range(0, 100).forEach(i -> {
          Promise<Response> p = Promise.promise();
          vertx.setTimer(1, timerid -> redisApi.set(Arrays.asList("foo-" + i, "bar")).onComplete(p));
          futures.add(p.future());
        });

        CompositeFuture.all(futures)
          .onFailure(f -> {
            should.assertEquals("Redis waiting Queue is full", f.getMessage());
            test.complete();
          })
          .onSuccess(r -> {
            if (cnt.get() == 100) {
              should.fail("Should not succeed!");
            }
          });
      });
  }

  @Test
  public void testZAdd(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();
        RedisAPI api = RedisAPI.api(redis);

        api.zadd(Arrays.asList("anykey", "1598248800", "member1", "1598248700", "member2"), zadd -> {
          should.assertTrue(zadd.succeeded());
          api.zrange(Arrays.asList("anykey", "0", "-1", "WITHSCORES"), zrange -> {
            should.assertTrue(zrange.succeeded());
            System.out.println(zrange);
            test.complete();
          });
        });
      });
  }

  @Test
  public void buggyHmget(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();

        redis.send(cmd(HMGET).arg("nonExistingKey").arg("nonExistingValue1").arg("nonExistingValue2"), send -> {
          should.assertTrue(send.succeeded());
          test.complete();
        });
      });
  }

  @Test
  public void buggyIterator(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        final RedisConnection redis = create.result();
        final String key = UUID.randomUUID().toString();

        redis.send(cmd(ZADD).arg(key).arg("1.0").arg("testValue1").arg("2.0").arg("testValue2"), zadd -> {
          should.assertTrue(zadd.succeeded());
          redis.send(cmd(ZRANGEBYSCORE).arg(key).arg("0.0").arg("3.0").arg("WITHSCORES"), zrangebyscore -> {
            should.assertTrue(zrangebyscore.succeeded());

            Response response = zrangebyscore.result();

            should.assertEquals(ResponseType.MULTI, response.type());
            //verify that keys are absent
            should.assertEquals(0, response.getKeys().size());

            //collect all the values (buggy)
            List<String> buggyList = new ArrayList<>();
            for (Response r : response) {
              buggyList.add(r.toString());
            }
            //verify that first value is present
            should.assertEquals("[testValue1, 1.0]", buggyList.get(0));
            should.assertEquals("[testValue2, 2.0]", buggyList.get(1));
            should.assertEquals(2, buggyList.size());

            //collect all the values (correct)
            List<String> correctList = new ArrayList<>();
            for (int i = 0; i < response.size(); i++) {
              correctList.add(response.get(i).toString());
            }
            //verify both values are present
            should.assertEquals("[testValue1, 1.0]", correctList.get(0));
            should.assertEquals("[testValue2, 2.0]", correctList.get(1));
            should.assertEquals(2, correctList.size());
            test.complete();
          });
        });
      });
  }

  @Test
  public void testBatch2(TestContext should) {
    final Async test = should.async();

    List<Request> requests = Arrays.asList(
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")),
      Request.cmd(Command.create("OOPS")));

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()))
      .batch(requests)
      .onFailure(err -> {
        should.assertTrue(err.getMessage().contains("ERR [0]"));
        should.assertTrue(err.getMessage().contains("ERR [1]"));
        should.assertTrue(err.getMessage().contains("ERR [2]"));
        should.assertTrue(err.getMessage().contains("ERR [3]"));
        test.complete();
      })
      .onSuccess(responses -> should.fail("Commands are wrong"));
  }
}
