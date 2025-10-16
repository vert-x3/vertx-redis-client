package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.RedisTopology;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisReplication;
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
public class RedisReplicationTest {

  @Container
  public static final RedisReplication redis = new RedisReplication();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testGetClient_discoverTopologyFromMaster_replicasNever(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.NEVER)
          .addConnectionString(redis.getRedisMasterUri())
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
  public void testGetClient_discoverTopologyFromMaster_replicasAlways(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
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
  public void testGetClient_discoverTopologyFromReplicas_replicasNever(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.NEVER)
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void testGetClient_discoverTopologyFromReplicas_replicasAlways(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
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
  public void testGetClient_staticTopology_replicasNever(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
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
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("role:master"));
        test.completeNow();
      }));
  }

  @Test
  public void testGetClient_staticTopology_replicasAlways(VertxTestContext test) {
    Redis.createClient(
        context.vertx(),
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
      .onComplete(test.succeeding(info -> {
        assertTrue(info.toString().contains("role:slave"));
        test.completeNow();
      }));
  }

  @Test
  public void preservesContext_discoverTopology(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions()
      .setType(RedisClientType.REPLICATION)
      .addConnectionString(redis.getRedisMasterUri()));

    PreservesContext.sendWithoutConnect(client, test);
    PreservesContext.batchWithoutConnect(client, test);
    PreservesContext.connect(client, test);
    PreservesContext.connectThenSend(client, test);
    PreservesContext.connectThenBatch(client, test);
  }

  @Test
  public void preservesContext_staticTopology(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions()
      .setType(RedisClientType.REPLICATION)
      .setTopology(RedisTopology.STATIC)
      .addConnectionString(redis.getRedisMasterUri())
      .addConnectionString(redis.getRedisReplica0Uri())
      .addConnectionString(redis.getRedisReplica1Uri()));

    PreservesContext.sendWithoutConnect(client, test);
    PreservesContext.batchWithoutConnect(client, test);
    PreservesContext.connect(client, test);
    PreservesContext.connectThenSend(client, test);
    PreservesContext.connectThenBatch(client, test);
  }

  @Test
  public void testWriteToMasterReadFromReplica_discoverTopology(VertxTestContext test) {
    final String key = randomKey();

    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("foobar"))
          .compose(ignored -> retryUntilSuccess(context.vertx(), () -> {
            return conn.send(Request.cmd(Command.GET).arg(key));
          }, 10))
          .onComplete(test.succeeding(result -> {
            assertEquals("foobar", result.toString());
            test.completeNow();
          }));
      }));
  }

  @Test
  public void testWriteToMasterReadFromReplica_staticTopology(VertxTestContext test) {
    final String key = randomKey();

    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.REPLICATION)
          .setTopology(RedisTopology.STATIC)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisMasterUri())
          .addConnectionString(redis.getRedisReplica0Uri())
          .addConnectionString(redis.getRedisReplica1Uri())
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("foobar"))
          .compose(ignored -> retryUntilSuccess(context.vertx(), () -> {
            return conn.send(Request.cmd(Command.GET).arg(key));
          }, 10))
          .onComplete(test.succeeding(result -> {
            assertEquals("foobar", result.toString());
            test.completeNow();
          }));
      }));
  }
}
