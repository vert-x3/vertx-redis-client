package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.nio.charset.StandardCharsets;

@RunWith(VertxUnitRunner.class)
public class RedisReplicationTest {

  @ClassRule
  public static final GenericContainer<?> redis = new FixedHostPortGenericContainer<>("grokzen/redis-cluster:6.2.0")
    .withEnv("IP", "0.0.0.0")
    .withEnv("STANDALONE", "true")
    .withEnv("SENTINEL", "true")
    .withExposedPorts(7000, 7001, 7002, 7003, 7004, 7005, 7006, 7007, 5000, 5001, 5002)
    // cluster ports (7000-7005) 6x (master+replica) 3 nodes
    .withFixedExposedPort(7000, 7000)
    .withFixedExposedPort(7001, 7001)
    .withFixedExposedPort(7002, 7002)
    .withFixedExposedPort(7003, 7003)
    .withFixedExposedPort(7004, 7004)
    .withFixedExposedPort(7005, 7005)
    // standalone ports (7006-7007) 2x
    .withFixedExposedPort(7006, 7006)
    .withFixedExposedPort(7007, 7007)
    // sentinel ports (5000-5002) 3x (match the cluster master nodes)
    .withFixedExposedPort(5000, 5000)
    .withFixedExposedPort(5001, 5001)
    .withFixedExposedPort(5002, 5002);

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setType(RedisClientType.REPLICATION)
        .addConnectionString("redis://localhost:7000")
        .setMaxPoolSize(4)
        .setMaxPoolWaiting(16))
      .connect(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.INFO), info -> {
            should.assertTrue(info.succeeded());
            should.assertTrue(info.result().toString().contains("tcp_port:7000"));
            test.complete();
          });
      });
  }

  @Test
  public void testGetClientToReplica(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7000")
      .send(Request.cmd(Command.INFO).arg("REPLICATION"))
      .onFailure(should::fail)
      .onSuccess(res -> {
        String[] lines = res.toString(StandardCharsets.ISO_8859_1).split("\r\n");
        for (String line : lines) {
          if (line.startsWith("slave0")) {
            String[] parts = line.split(",");
            for (String part : parts) {
              if (part.startsWith("port=")) {
                String port = part.substring(5);

                // real start of the test
                Redis.createClient(
                  rule.vertx(),
                  new RedisOptions()
                    .setType(RedisClientType.REPLICATION)
                    .setUseReplicas(RedisReplicas.NEVER)
                    .addConnectionString("redis://localhost:" + port)
                    .setMaxPoolSize(4)
                    .setMaxPoolWaiting(16))
                  .connect(onCreate -> {
                    // get a connection to the master node
                    should.assertTrue(onCreate.succeeded());
                    // query the info
                    onCreate.result()
                      .send(Request.cmd(Command.INFO), info -> {
                        should.assertTrue(info.succeeded());
                        // even though we list the replica node, the main connection happens to the master
                        should.assertTrue(info.result().toString().contains("tcp_port:7000"));
                        test.complete();
                      });
                  });
              }
            }
          }
        }
      });
  }

  @Test
  public void testGetClientToReplicaUseReplicasAlways(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), "redis://localhost:7000")
      .send(Request.cmd(Command.INFO).arg("REPLICATION"))
      .onFailure(should::fail)
      .onSuccess(res -> {
        String[] lines = res.toString(StandardCharsets.ISO_8859_1).split("\r\n");
        for (String line : lines) {
          if (line.startsWith("slave0")) {
            String[] parts = line.split(",");
            for (String part : parts) {
              if (part.startsWith("port=")) {
                String port = part.substring(5);

                // real start of the test
                Redis.createClient(
                  rule.vertx(),
                  new RedisOptions()
                    .setType(RedisClientType.REPLICATION)
                    .setUseReplicas(RedisReplicas.ALWAYS)
                    .addConnectionString("redis://localhost:" + port)
                    .setMaxPoolSize(4)
                    .setMaxPoolWaiting(16))
                  .connect(onCreate -> {
                    // get a connection to the master node
                    should.assertTrue(onCreate.succeeded());
                    // query the info
                    onCreate.result()
                      .send(Request.cmd(Command.INFO), info -> {
                        should.assertTrue(info.succeeded());
                        // we force read commands to go to replicas
                        should.assertTrue(info.result().toString().contains("tcp_port:" + port));
                        test.complete();
                      });
                  });
              }
            }
          }
        }
      });
  }
}
