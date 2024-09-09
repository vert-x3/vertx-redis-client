package io.vertx.tests.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.HGETALL;
import static io.vertx.redis.client.Command.HSET;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;

@RunWith(VertxUnitRunner.class)
public class RedisClient5Test {

  @ClassRule
  public static final RedisStandalone redis = RedisStandalone.builder().setVersion("5.0").build();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis client;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    client = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString(redis.getRedisUri()));

    client.connect().onComplete(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      before.complete();
    });
  }

  @After
  public void after() {
    client.close();
  }

  @Test(timeout = 10_000L)
  public void testBasicInterop(TestContext should) {
    final Async test = should.async();
    final String nonexisting = randomKey();
    final String mykey = randomKey();

    client.send(cmd(GET).arg(nonexisting)).onComplete(reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNull(reply0.result());

      client.send(cmd(SET).arg(mykey).arg("Hello")).onComplete(reply1 -> {
        should.assertTrue(reply1.succeeded());
        client.send(cmd(GET).arg(mykey)).onComplete(reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().toString());
          test.complete();
        });
      });
    });
  }

  @Test(timeout = 10_000L)
  public void testJson(TestContext should) {
    final Async test = should.async();
    final String mykey = randomKey();

    JsonObject json = new JsonObject()
      .put("key", "value");

    client.send(cmd(HSET).arg(mykey).arg(json))
      .compose(ignored -> client.send(cmd(HGETALL).arg(mykey)))
      .onComplete(should.asyncAssertSuccess(value -> {
        should.assertEquals("value", value.get("key").toString());
        test.complete();
      }));
  }
}
