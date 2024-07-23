package io.vertx.test.redis;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.test.TestUtils.randomKey;

@RunWith(VertxUnitRunner.class)
public class RedisClient6SecureFailedTest {

  @ClassRule
  public static final RedisStandalone redis = RedisStandalone.builder().setVersion("6.2").setPassword("foobar").build();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis client;

  @Before
  public void before() {
    client = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString("redis://:foobared@" + redis.getHost() + ":" + redis.getPort()));
  }

  @After
  public void after() {
    client.close();
  }

  @Test
  public void testBasicInterop(TestContext should) {
    final Async test = should.async();
    final String nonexisting = randomKey();

    client.send(cmd(GET).arg(nonexisting), reply -> {
      should.assertTrue(reply.failed());
      should.assertTrue(((ErrorType) reply.cause()).is("WRONGPASS"));
      test.complete();
    });
  }
}
