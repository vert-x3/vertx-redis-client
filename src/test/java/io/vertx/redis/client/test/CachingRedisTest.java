package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.CachingRedis;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

@RunWith(VertxUnitRunner.class)
public class CachingRedisTest {

  @ClassRule
  public static final GenericContainer<?> container = new GenericContainer<>("redis:7")
    .withExposedPorts(6379);

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testCacheWrapping(TestContext should) {
    Redis redis = Redis.createClient(rule.vertx(), new RedisOptions()
      .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort())
      .setCacheEnabled(true));

    should.assertTrue(redis instanceof CachingRedis);
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions()
        .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort())
        .setCacheEnabled(true))
      .send(Request.cmd(Command.PING)).onComplete(send -> {
        should.assertTrue(send.succeeded());
        should.assertNotNull(send.result());
        should.assertEquals("PONG", send.result().toString());
        test.complete();
      });
  }

  @Test
  public void preservesContext(TestContext should) {
    Redis redis = Redis.createClient(rule.vertx(), new RedisOptions()
      .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort())
      .setCacheEnabled(true));

    PreservesContext.sendWithoutConnect(redis, should);
    PreservesContext.batchWithoutConnect(redis, should);
    PreservesContext.connect(redis, should);
    PreservesContext.connectThenSend(redis, should);
    PreservesContext.connectThenBatch(redis, should);
  }
}
