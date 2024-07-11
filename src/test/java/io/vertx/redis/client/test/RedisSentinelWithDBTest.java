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

@RunWith(VertxUnitRunner.class)
public class RedisSentinelWithDBTest {

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
    .withFixedExposedPort(5002, 5002)
    // workaround for new version of the Docker image that doesn't use the built `redis-sentinel` binary correctly
    .withCommand("/bin/bash", "-c", "sed -i -e 's|redis-sentinel|/redis/src/redis-sentinel|g' /docker-entrypoint.sh && exec /docker-entrypoint.sh redis-cluster");

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
        rule.vertx(),
        new RedisOptions()
          .setType(RedisClientType.SENTINEL)
          .addConnectionString("redis://localhost:5000/0")
          .addConnectionString("redis://localhost:5001/0")
          .addConnectionString("redis://localhost:5002/0")
          .setMasterName("sentinel7000")
          .setRole(RedisRole.MASTER)
          .setMaxPoolSize(4)
          .setMaxPoolWaiting(16))
      .connect().onComplete(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.CLIENT).arg("LIST")).onComplete(info -> {
            should.assertTrue(info.succeeded());
            System.out.println(info.result());
            test.complete();
          });
      });
  }
}
