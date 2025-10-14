package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisSentinel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisSentinelTest {

  @Container
  public static final RedisSentinel redis = new RedisSentinel();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri())
          .addConnectionString(redis.getRedisSentinel1Uri())
          .addConnectionString(redis.getRedisSentinel2Uri())
          .setRole(RedisRole.MASTER)
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect()
      .compose(conn -> conn.send(Request.cmd(Command.INFO)))
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("role:master"));
        test.completeNow();
      }));
  }

  @Test
  public void testGetClientToMasterWithDB(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri() + "/5")
          .addConnectionString(redis.getRedisSentinel1Uri() + "/5")
          .addConnectionString(redis.getRedisSentinel2Uri() + "/5")
          .setRole(RedisRole.MASTER)
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect()
      .compose(conn -> conn.send(Request.cmd(Command.CLIENT).arg("INFO")))
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("db=5"));
        test.completeNow();
      }));
  }

  @Test
  public void testGetClientToReplica(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri())
          .addConnectionString(redis.getRedisSentinel1Uri())
          .addConnectionString(redis.getRedisSentinel2Uri())
          .setRole(RedisRole.REPLICA)
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect()
      .compose(conn -> conn.send(Request.cmd(Command.INFO)))
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("role:slave"));
        test.completeNow();
      }));
  }

  @Test
  public void testGetClientToSentinel(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString(redis.getRedisSentinel0Uri())
          .addConnectionString(redis.getRedisSentinel1Uri())
          .addConnectionString(redis.getRedisSentinel2Uri())
          .setRole(RedisRole.SENTINEL)
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect()
      .compose(conn -> conn.send(Request.cmd(Command.INFO)))
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("redis_mode:sentinel"));
        assertTrue(info.toString().contains("sentinel_masters:1"));
        test.completeNow();
      }));
  }

  @Test
  public void preservesContext(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions()
      .setType(RedisClientType.SENTINEL)
      .addConnectionString(redis.getRedisSentinel0Uri())
      .addConnectionString(redis.getRedisSentinel1Uri())
      .addConnectionString(redis.getRedisSentinel2Uri())
      .setRole(RedisRole.MASTER));

    PreservesContext.sendWithoutConnect(client, test);
    PreservesContext.batchWithoutConnect(client, test);
    PreservesContext.connect(client, test);
    PreservesContext.connectThenSend(client, test);
    PreservesContext.connectThenBatch(client, test);
  }
}
