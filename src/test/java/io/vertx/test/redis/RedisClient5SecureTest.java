package io.vertx.test.redis;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.impl.types.ErrorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.UUID;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisClient5SecureTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Rule
  public final GenericContainer<?> redis = new GenericContainer<>("redis:5")
    .withExposedPorts(6379);

  private Redis client;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    final Redis setupClient = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString("redis://" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort()));

    setupClient
      .send(cmd(CONFIG).arg("SET").arg("requirepass").arg("foobar"), onConfigSet -> {
        should.assertTrue(onConfigSet.succeeded());
        // disconnect this client and create a new one
        setupClient.close();

        rule.vertx()
          .runOnContext(v -> {
            client = Redis.createClient(
              rule.vertx(),
              new RedisOptions().setConnectionString("redis://:foobar@" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort()));

            before.complete();
          });
      });
  }

  @After
  public void after() {
    client.close();
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Test(timeout = 10_000L)
  public void testBasicInterop(TestContext should) {
    final Async test = should.async();
    final String nonexisting = makeKey();

    client.send(cmd(GET).arg(nonexisting), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNull(reply0.result());
      test.complete();
    });
  }
}
