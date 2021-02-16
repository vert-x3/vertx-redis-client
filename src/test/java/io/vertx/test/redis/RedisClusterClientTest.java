package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class RedisClusterClientTest {

  @Parameterized.Parameters
  public static Iterable<String[]> data() {
    return Arrays.asList(
      new String[]{"redis:5", "abcd"},
      new String[]{"redis:5", null},
      new String[]{"redis:6", "abcd"},
      new String[]{"redis:6", null});
  }

  public final Network network = Network.newNetwork();

  public final GenericContainer<?> redis7000;
  public final GenericContainer<?> redis7001;
  public final GenericContainer<?> redis7002;
  public final GenericContainer<?> redis7003;
  public final GenericContainer<?> redis7004;
  public final GenericContainer<?> redis7005;

  public GenericContainer<?> redisCli;

  private final String password;

  public RedisClusterClientTest(String image, String password) {

    System.out.println(image);
    System.out.println(password);

    this.password = password;
    String cmd = "redis-server --port 6379 --cluster-enabled yes --cluster-config-file nodes.conf --cluster-node-timeout 5000 --appendonly yes" + (password == null ? "" : " --requirepass " + password);

    redis7000 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    redis7001 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    redis7002 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    redis7003 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    redis7004 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    redis7005 = new GenericContainer<>(image)
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand(cmd);

    StringBuilder stringBuilder = new StringBuilder();
    for (GenericContainer<?> container : Arrays.asList(redis7000, redis7001, redis7002, redis7003, redis7004, redis7005)) {
      container
        .start();

      container
        .getContainerInfo()
        .getNetworkSettings()
        .getNetworks()
        .values()
        .stream()
        .findFirst()
        .ifPresent(net -> stringBuilder.append(net.getIpAddress()).append(":").append("6379").append(" "));
    }

    String connectionString = stringBuilder.substring(0, stringBuilder.length() - 1);
    String cmdline = String.format("redis-cli --cluster create %s --cluster-replicas 1 --cluster-yes%s", connectionString, password == null ? "" : " -a " + password);
    System.out.println(cmdline);

    redisCli = new GenericContainer<>(image)
      .withNetwork(network).waitingFor(
        Wait.forLogMessage(".*OK.*", 2)
      )
      .withCommand(cmdline);

    redisCli.start();
  }

  private Vertx vertx;
  private Redis client;

  @Before
  public void before() {
    vertx = Vertx.vertx();
    client = Redis.createClient(
      vertx, new RedisOptions()
        .setType(RedisClientType.CLUSTER)
        .addConnectionString(
          password != null ?
            "redis://:" + password + "@" + redis7000.getContainerIpAddress() + ":" + redis7000.getFirstMappedPort() :
            "redis://" + redis7000.getContainerIpAddress() + ":" + redis7000.getFirstMappedPort()));
  }

  @After
  public void after() {
    try {
      client.close();
    } catch (RuntimeException e) {
    }
    for (GenericContainer<?> container : Arrays.asList(redis7000, redis7001, redis7002, redis7003, redis7004, redis7005, redisCli)) {
      try {
        container.close();
      } catch (RuntimeException e) {
      }
    }
    try {
      vertx.close();
    } catch (RuntimeException e) {
    }
  }

  @Test(timeout = 10_000L)
  public void testConnect() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);

    client.connect(onConnect -> {
      if (onConnect.failed()) {
        fail(onConnect.cause().getMessage());
      } else {
        assertTrue(onConnect.succeeded());
        latch.countDown();
      }
    });

    latch.await();
  }
}
