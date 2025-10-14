package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisSentinel;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;

@RunWith(VertxUnitRunner.class)
public class RedisSentinelTest {

  @ClassRule
  public static final RedisSentinel redis = new RedisSentinel();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
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
      .onComplete(should.asyncAssertSuccess(info -> {
        should.assertTrue(info.toString().contains("role:master"));
        test.complete();
      }));
  }

  @Test
  public void testGetClientToMasterWithDB(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
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
      .onComplete(should.asyncAssertSuccess(info -> {
        should.assertTrue(info.toString().contains("db=5"));
        test.complete();
      }));
  }

  @Test
  public void testGetClientToReplica(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
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
      .onComplete(should.asyncAssertSuccess(info -> {
        should.assertTrue(info.toString().contains("role:slave"));
        test.complete();
      }));
  }

  @Test
  public void testGetClientToSentinel(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
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
      .onComplete(should.asyncAssertSuccess(info -> {
        should.assertTrue(info.toString().contains("redis_mode:sentinel"));
        should.assertTrue(info.toString().contains("sentinel_masters:1"));
        test.complete();
      }));
  }

  @Test
  public void preservesContext(TestContext should) {
    Redis client = Redis.createClient(rule.vertx(), new RedisOptions()
      .setType(RedisClientType.SENTINEL)
      .addConnectionString(redis.getRedisSentinel0Uri())
      .addConnectionString(redis.getRedisSentinel1Uri())
      .addConnectionString(redis.getRedisSentinel2Uri())
      .setRole(RedisRole.MASTER));

    PreservesContext.sendWithoutConnect(client, should);
    PreservesContext.batchWithoutConnect(client, should);
    PreservesContext.connect(client, should);
    PreservesContext.connectThenSend(client, should);
    PreservesContext.connectThenBatch(client, should);
  }
}
