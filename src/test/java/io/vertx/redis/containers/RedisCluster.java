package io.vertx.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisCluster implements TestRule {
  private final GenericContainer<?> container = new FixedHostPortGenericContainer<>("quay.io/ladicek/redis-cluster")
    .withFixedExposedPort(7000, 7000)
    .withFixedExposedPort(7001, 7001)
    .withFixedExposedPort(7002, 7002)
    .withFixedExposedPort(7003, 7003)
    .withFixedExposedPort(7004, 7004)
    .withFixedExposedPort(7005, 7005)
    .waitingFor(Wait.forLogMessage(".*Cluster state changed: ok.*", 6));

  @Override
  public Statement apply(Statement base, Description description) {
    return container.apply(base, description);
  }

  public String getRedisNode0Uri() {
    return getRedisUri(7000);
  }

  public String getRedisNode1Uri() {
    return getRedisUri(7001);
  }

  public String getRedisNode2Uri() {
    return getRedisUri(7002);
  }

  public String getRedisNode3Uri() {
    return getRedisUri(7003);
  }

  public String getRedisNode4Uri() {
    return getRedisUri(7004);
  }

  public String getRedisNode5Uri() {
    return getRedisUri(7005);
  }

  private String getRedisUri(int portNumber) {
    return "redis://" + container.getHost() + ":" + container.getMappedPort(portNumber);
  }
}
