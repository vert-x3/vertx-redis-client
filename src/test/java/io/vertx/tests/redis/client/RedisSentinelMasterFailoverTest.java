package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisSentinel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisSentinelMasterFailoverTest {
  @Container
  public static final RedisSentinel redis = new RedisSentinel();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void test(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri())
          .addConnectionString(redis.getRedisSentinel1Uri())
          .addConnectionString(redis.getRedisSentinel2Uri())
          .setRole(RedisRole.MASTER)
          .setAutoFailover(true))
      .connect()
      .onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.SET).arg("key").arg("value"))
          .compose(ignored -> conn.send(Request.cmd(Command.SHUTDOWN)))
          .onComplete(test.failing(ignored -> { // connection closed
            retryUntilSuccess(context.vertx(), () -> conn.send(Request.cmd(Command.GET).arg("key")), 50)
              .onComplete(test.succeeding(response -> {
                assertEquals("value", response.toString());
                test.completeNow();
              }));
          }));
      }));
  }
}
