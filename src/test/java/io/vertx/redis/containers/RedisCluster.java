package io.vertx.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.io.File;

public class RedisCluster implements TestRule {
  private final ComposeContainer compose = new ComposeContainer(new File("src/test/resources/redis-cluster.yaml"))
    .withExposedService("redis-node0", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"))
    .withExposedService("redis-node1", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"))
    .withExposedService("redis-node2", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"))
    .withExposedService("redis-node3", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"))
    .withExposedService("redis-node4", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"))
    .withExposedService("redis-node5", 6379, new LogMessageWaitStrategy().withRegEx(".*Cluster state changed: ok.*"));

  @Override
  public Statement apply(Statement base, Description description) {
    return compose.apply(base, description);
  }

  public String getRedisNode0Uri() {
    return getRedisUri("redis-node0");
  }

  public String getRedisNode1Uri() {
    return getRedisUri("redis-node1");
  }

  public String getRedisNode2Uri() {
    return getRedisUri("redis-node2");
  }

  public String getRedisNode3Uri() {
    return getRedisUri("redis-node3");
  }

  public String getRedisNode4Uri() {
    return getRedisUri("redis-node4");
  }

  public String getRedisNode5Uri() {
    return getRedisUri("redis-node5");
  }

  private String getRedisUri(String node) {
    return "redis://" + compose.getServiceHost(node, 6379) + ":" + compose.getServicePort(node, 6379);
  }
}
