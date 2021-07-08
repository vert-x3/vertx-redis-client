package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;

@RunWith(VertxUnitRunner.class)
public class RedisReplicationTest {

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
