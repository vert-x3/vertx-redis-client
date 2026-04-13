package io.vertx.tests.redis.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.lifecycle.Startable;

public class ValkeyContainer implements Startable {
  private final GenericContainer<?> container;

  public ValkeyContainer() {
    this.container = new GenericContainer<>("valkey/valkey:9.0.3")
      .withExposedPorts(6379)
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Ready to accept connections.*"));
  }

  @Override
  public void start() {
    ContainerUtil.start(container);
  }

  @Override
  public void stop() {
    ContainerUtil.stop(container);
  }

  public String getHost() {
    return container.getHost();
  }

  public int getPort() {
    return container.getMappedPort(6379);
  }

  public String getValkeyUri() {
    return "valkey://" + getHost() + ":" + getPort();
  }
}
