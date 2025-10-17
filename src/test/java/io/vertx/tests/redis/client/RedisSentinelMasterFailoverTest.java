package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisSentinel;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;

@RunWith(VertxUnitRunner.class)
public class RedisSentinelMasterFailoverTest {
  @ClassRule
  public static final RedisSentinel redis = new RedisSentinel();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void test(TestContext test) {
    Async async = test.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri())
          .addConnectionString(redis.getRedisSentinel1Uri())
          .addConnectionString(redis.getRedisSentinel2Uri())
          .setRole(RedisRole.MASTER)
          .setAutoFailover(true))
      .connect()
      .onComplete(test.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.SET).arg("key").arg("value"))
          .compose(ignored -> {
            // shutdown current master, promote one replica out of two (the other replica remains)
            return conn.send(Request.cmd(Command.SHUTDOWN));
          })
          .transform((value, error) -> {
            test.assertNull(value);
            test.assertNotNull(error); // connection closed
            return retryUntilSuccess(rule.vertx(), () -> conn.send(Request.cmd(Command.GET).arg("key")), 50);
          })
          .compose(response -> {
            test.assertEquals("value", response.toString());
            // shutdown current master, promote the remaining replica (no other replica exists)
            return conn.send(Request.cmd(Command.SHUTDOWN));
          })
          .transform((value, error) -> {
            test.assertNull(value);
            test.assertNotNull(error); // connection closed
            return retryUntilSuccess(rule.vertx(), () -> conn.send(Request.cmd(Command.GET).arg("key")), 50);
          })
          .onComplete(test.asyncAssertSuccess(response -> {
            test.assertEquals("value", response.toString());
            async.complete();
          }));
      }));
  }
}
