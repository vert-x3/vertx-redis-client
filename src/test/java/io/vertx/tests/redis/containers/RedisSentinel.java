package io.vertx.tests.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisSentinel implements TestRule {
  private final GenericContainer<?> container = new FixedHostPortGenericContainer<>("quay.io/ladicek/redis-replication")
    .withEnv("SENTINEL", "true")
    .withFixedExposedPort(7000, 7000)
    .withFixedExposedPort(7001, 7001)
    .withFixedExposedPort(7002, 7002)
    .withFixedExposedPort(5000, 5000)
    .withFixedExposedPort(5001, 5001)
    .withFixedExposedPort(5002, 5002)
    .waitingFor(Wait.forLogMessage(".*\\+slave.*", 6));

  @Override
  public Statement apply(Statement base, Description description) {
    return container.apply(base, description);
  }

  public String getRedisMasterUri() {
    return getRedisUri(7000);
  }

  public String getRedisReplica0Uri() {
    return getRedisUri(7001);
  }

  public String getRedisReplica1Uri() {
    return getRedisUri(7002);
  }

  public String getRedisSentinel0Uri() {
    return getRedisUri(5000);
  }

  public String getRedisSentinel1Uri() {
    return getRedisUri(5001);
  }

  public String getRedisSentinel2Uri() {
    return getRedisUri(5002);
  }

  private String getRedisUri(int portNumber) {
    return "redis://" + container.getHost() + ":" + container.getMappedPort(portNumber);
  }
}
