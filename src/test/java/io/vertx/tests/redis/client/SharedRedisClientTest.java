package io.vertx.tests.redis.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.*;
import io.vertx.tests.redis.containers.RedisCluster;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static io.vertx.tests.redis.client.TestUtils.getRedisConnectionManager;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class SharedRedisClientTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @Container
  public static final RedisCluster cluster = new RedisCluster();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private RedisOptions standaloneOptions(String poolName) {
    return new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setShared(true)
      .setPoolName(poolName)
      .setMaxPoolWaiting(1000);
  }

  @Test
  public void testUseSamePool(VertxTestContext test) {
    int maxSize = 8;
    int instances = maxSize * 4;
    CountDownLatch responseReceived = test.checkpoint().asLatch(instances);

    context.vertx().deployVerticle(() -> new AbstractVerticle() {
      Redis redis;

      @Override
      public void start() {
        redis = Redis.createClient(vertx, standaloneOptions("shared-redis-client-test").setMaxPoolSize(maxSize));

        vertx.timer(500)
          .compose(ignored -> redis.send(Request.cmd(Command.INFO).arg("clients")))
          .onComplete(test.succeeding(resp -> {
            Optional<String> connectedClients = Arrays.stream(resp.toString().split("\n"))
              .filter(line -> line.startsWith("connected_clients:"))
              .findFirst();
            if (connectedClients.isPresent()) {
              int clients = Integer.parseInt(connectedClients.get().split(":")[1].trim());
              assertTrue(clients >= 0);
              assertTrue(clients <= maxSize);
              responseReceived.countDown();
            } else {
              test.failNow("connected_clients field not found in INFO clients reply");
            }
          }));
      }
    }, new DeploymentOptions().setInstances(instances)).onFailure(test::failNow);
  }

  @Test
  public void testSharedClientsUseSameConnectionManager(VertxTestContext test) {
    Redis client1 = Redis.createClient(context.vertx(), standaloneOptions("shared-pool"));
    Redis client2 = Redis.createClient(context.vertx(), standaloneOptions("shared-pool"));

    assertSame(getRedisConnectionManager(client1), getRedisConnectionManager(client2));
    Future.all(client1.close(), client2.close()).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testNonSharedClientsUseDifferentConnectionManagers(VertxTestContext test) {
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setPoolName("non-shared-pool");

    Redis client1 = Redis.createClient(context.vertx(), options);
    Redis client2 = Redis.createClient(context.vertx(), options);

    assertNotSame(getRedisConnectionManager(client1), getRedisConnectionManager(client2));
    Future.all(client1.close(), client2.close()).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testExplicitCloseReleasesLeaseOnly(VertxTestContext test) {
    Redis client1 = Redis.createClient(context.vertx(), standaloneOptions("lease-release-pool"));
    Redis client2 = Redis.createClient(context.vertx(), standaloneOptions("lease-release-pool"));

    client1.close()
      .compose(ignored -> client2.send(Request.cmd(Command.PING)))
      .compose(resp -> {
        assertEquals("PONG", resp.toString());
        return client2.close();
      })
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testUseSamePoolClusterClient(VertxTestContext test) {
    int maxSize = 8;
    int instances = maxSize * 6;
    CountDownLatch responseReceived = test.checkpoint().asLatch(instances);

    context.vertx().deployVerticle(() -> new AbstractVerticle() {
      Redis redis;

      @Override
      public void start() {
        redis = Redis.createClient(vertx, new RedisOptions()
          .setType(RedisClientType.CLUSTER)
          .setShared(true)
          .setMaxPoolSize(maxSize)
          .setMaxPoolWaiting(maxSize * 10)
          .setConnectionString(cluster.getRedisNode0Uri()));
        vertx.timer(500)
          .compose(ignored -> redis.send(Request.cmd(Command.CLIENT).arg("list")))
          .onComplete(test.succeeding(resp -> {
            long connectedClients = Arrays.stream(resp.toString().split("\n"))
              .filter(line -> line.contains("user=default") && !line.contains("cmd=replconf") && !line.contains("cmd=cluster|slots"))
              .count();
            assertTrue(connectedClients > 0);
            assertTrue(connectedClients <= maxSize);
            responseReceived.countDown();
          }));
      }
    }, new DeploymentOptions().setInstances(instances)).onFailure(test::failNow);
  }
}
