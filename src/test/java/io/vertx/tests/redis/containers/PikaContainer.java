package io.vertx.tests.redis.containers;

import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.lifecycle.Startable;

import java.util.Objects;

public class PikaContainer implements Startable {

  public static class Builder {
    private String version;
    private String password;
    private String configFile;

    private Builder() {
    }

    /**
     * Set the Pika version. Available versions include:
     * <ul>
     * <li>{@code v3.5.6}</li>
     * <li>{@code v4.0.2}</li>
     * </ul>
     *
     * @param version the Pika version tag
     * @return this builder
     */
    public Builder setVersion(String version) {
      this.version = Objects.requireNonNull(version);
      return this;
    }

    /**
     * Set the password that should be present in {@link #getPikaUri()}.
     * Note that this <em>does not</em> change the password present in
     * {@link #setConfigFile(String)} -- they need to match!
     *
     * @param password the password
     * @return this builder
     */
    public Builder setPassword(String password) {
      this.password = Objects.requireNonNull(password);
      return this;
    }

    /**
     * Set a custom configuration file to be mounted in the container.
     * The file should be available in the classpath (e.g., in src/test/resources).
     *
     * @param configFile the config file name (e.g., "pika.conf", "pika4.conf")
     * @return this builder
     */
    public Builder setConfigFile(String configFile) {
      this.configFile = Objects.requireNonNull(configFile);
      return this;
    }

    public PikaContainer build() {
      return new PikaContainer(this);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  // ---

  private final GenericContainer<?> container;
  private final String password;

  /**
   * Creates a PikaContainer with default settings (version v3.5.6, no password).
   */
  public PikaContainer() {
    this(builder());
  }

  private PikaContainer(Builder builder) {
    String image = "pikadb/pika:" + (builder.version != null ? builder.version : "v3.5.6");
    GenericContainer<?> container = new GenericContainer<>(image)
      .withExposedPorts(9221)
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Server started.*"));

    // Mount custom config file if specified
    if (builder.configFile != null) {
      container.withClasspathResourceMapping(
        builder.configFile,
        "/pika/conf/pika.conf",
        BindMode.READ_ONLY
      );
    }

    this.container = container;
    this.password = builder.password;
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
    return container.getMappedPort(9221);
  }

  /**
   * Get the Pika URI. If a password was set via the builder, it will be included in the URI.
   *
   * @return the connection URI
   */
  public String getPikaUri() {
    if (password != null) {
      return "redis://:" + password + "@" + getHost() + ":" + getPort();
    }
    return "redis://" + getHost() + ":" + getPort();
  }
}
