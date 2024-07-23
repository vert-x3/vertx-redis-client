package io.vertx.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisReplication implements TestRule {
  private final GenericContainer<?> container = new FixedHostPortGenericContainer<>("quay.io/ladicek/redis-replication")
    .withFixedExposedPort(7000, 7000)
    .withFixedExposedPort(7001, 7001)
    .withFixedExposedPort(7002, 7002)
    .waitingFor(Wait.forLogMessage(".*MASTER <-> REPLICA sync: Finished with success.*", 2));

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

  private String getRedisUri(int portNumber) {
    return "redis://" + container.getHost() + ":" + container.getMappedPort(portNumber);
  }
}
