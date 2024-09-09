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
import io.vertx.redis.client.RedisTopology;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisReplication;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;

@RunWith(VertxUnitRunner.class)
public class RedisReplicationTest {

  @ClassRule
  public static final RedisReplication redis = new RedisReplication();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClient_discoverTopologyFromMaster_replicasNever(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.NEVER)
          .addConnectionString(redis.getRedisMasterUri())
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
  public void testGetClient_discoverTopologyFromMaster_replicasAlways(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
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
  public void testGetClient_discoverTopologyFromReplicas_replicasNever(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.NEVER)
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void testGetClient_discoverTopologyFromReplicas_replicasAlways(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void testGetClient_staticTopology_replicasNever(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.NEVER)
          .setTopology(RedisTopology.STATIC)
          .addConnectionString(redis.getRedisMasterUri())
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void testGetClient_staticTopology_replicasAlways(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .setTopology(RedisTopology.STATIC)
          .addConnectionString(redis.getRedisMasterUri())
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void preservesContext_discoverTopology(TestContext should) {
    Redis client = Redis.createClient(rule.vertx(), new RedisOptions()
      .setType(RedisClientType.REPLICATION)
      .addConnectionString(redis.getRedisMasterUri()));

    PreservesContext.sendWithoutConnect(client, should);
    PreservesContext.batchWithoutConnect(client, should);
    PreservesContext.connect(client, should);
    PreservesContext.connectThenSend(client, should);
    PreservesContext.connectThenBatch(client, should);
  }

  @Test
  public void preservesContext_staticTopology(TestContext should) {
    Redis client = Redis.createClient(rule.vertx(), new RedisOptions()
      .setType(RedisClientType.REPLICATION)
      .setTopology(RedisTopology.STATIC)
      .addConnectionString(redis.getRedisMasterUri())
      .addConnectionString(redis.getRedisReplica0Uri())
      .addConnectionString(redis.getRedisReplica1Uri()));

    PreservesContext.sendWithoutConnect(client, should);
    PreservesContext.batchWithoutConnect(client, should);
    PreservesContext.connect(client, should);
    PreservesContext.connectThenSend(client, should);
    PreservesContext.connectThenBatch(client, should);
  }

  @Test
  public void testWriteToMasterReadFromReplica_discoverTopology(TestContext should) {
    final Async test = should.async();
    final String key = randomKey();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect().onComplete(should.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("foobar"))
          .compose(ignored -> retryUntilSuccess(rule.vertx(), () -> {
            return conn.send(Request.cmd(Command.GET).arg(key));
          }, 10))
          .onComplete(should.asyncAssertSuccess(result -> {
            should.assertEquals("foobar", result.toString());
            test.complete();
          }));
      }));
  }

  @Test
  public void testWriteToMasterReadFromReplica_staticTopology(TestContext should) {
    final Async test = should.async();
    final String key = randomKey();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setTopology(RedisTopology.STATIC)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect().onComplete(should.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("foobar"))
          .compose(ignored -> retryUntilSuccess(rule.vertx(), () -> {
            return conn.send(Request.cmd(Command.GET).arg(key));
          }, 10))
          .onComplete(should.asyncAssertSuccess(result -> {
            should.assertEquals("foobar", result.toString());
            test.complete();
          }));
      }));
  }
}
