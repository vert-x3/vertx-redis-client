package io.vertx.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.io.File;

public class RedisReplication implements TestRule {
  private final ComposeContainer compose = new ComposeContainer(new File("src/test/resources/redis-replication.yaml"))
    .withExposedService("redis-master", 6379, new LogMessageWaitStrategy().withRegEx(".*Ready to accept connections.*"))
    .withExposedService("redis-replica0", 6379, new LogMessageWaitStrategy().withRegEx(".*MASTER <-> REPLICA sync: Finished with success.*"))
    .withExposedService("redis-replica1", 6379, new LogMessageWaitStrategy().withRegEx(".*MASTER <-> REPLICA sync: Finished with success.*"));

  @Override
  public Statement apply(Statement base, Description description) {
    return compose.apply(base, description);
  }

  public String getRedisMasterUri() {
    return getRedisUri("redis-master");
  }

  public String getRedisReplica0Uri() {
    return getRedisUri("redis-replica0");
  }

  public String getRedisReplica1Uri() {
    return getRedisUri("redis-replica1");
  }

  private String getRedisUri(String node) {
    return "redis://" + compose.getServiceHost(node, 6379) + ":" + compose.getServicePort(node, 6379);
  }
}
