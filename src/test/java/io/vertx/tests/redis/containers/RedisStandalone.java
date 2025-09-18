package io.vertx.tests.redis.containers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.images.builder.Transferable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RedisStandalone implements TestRule {
  public static class Builder {
    private String version;
    private String password;

    private boolean tlsEnabled;
    private KeyPairAndCertificate serverKey;

    private boolean mutualTlsEnabled;
    private KeyPairAndCertificate clientKey;

    private Builder() {
    }

    public Builder setVersion(String version) {
      this.version = Objects.requireNonNull(version);
      return this;
    }

    public Builder setPassword(String password) {
      this.password = Objects.requireNonNull(password);
      return this;
    }

    public Builder enableTls(KeyPairAndCertificate serverKey) {
      this.tlsEnabled = true;
      this.serverKey = Objects.requireNonNull(serverKey);
      return this;
    }

    public Builder enableMutualTls(KeyPairAndCertificate clientKey) {
      assert tlsEnabled;
      this.mutualTlsEnabled = true;
      this.clientKey = Objects.requireNonNull(clientKey);
      return this;
    }

    public RedisStandalone build() {
      return new RedisStandalone(this);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  // ---

  private final GenericContainer<?> container;

  public RedisStandalone() {
    this(builder());
  }

  private RedisStandalone(Builder builder) {
    String image = "docker.io/bitnamilegacy/redis:" + (builder.version != null ? builder.version : "7.2");

    Map<String, String> env = new HashMap<>();
    if (builder.password != null) {
      env.put("REDIS_PASSWORD", builder.password);
    } else {
      env.put("ALLOW_EMPTY_PASSWORD", "yes");
    }

    if (builder.tlsEnabled) {
      env.put("REDIS_TLS_ENABLED", "yes");
      env.put("REDIS_TLS_KEY_FILE", "/certs/redis.key");
      env.put("REDIS_TLS_CERT_FILE", "/certs/redis.crt");

      if (builder.mutualTlsEnabled) {
        env.put("REDIS_TLS_CA_FILE", "/certs/client.crt");
      } else {
        env.put("REDIS_TLS_AUTH_CLIENTS", "no");
      }
    }

    this.container = new GenericContainer<>(image)
      .withEnv(env)
      .withExposedPorts(6379)
      .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Ready to accept connections.*"));

    if (builder.tlsEnabled) {
      container.withCopyToContainer(Transferable.of(builder.serverKey.privateKeyAsPEM()), "/certs/redis.key");
      container.withCopyToContainer(Transferable.of(builder.serverKey.certificateAsPEM()), "/certs/redis.crt");
    }
    if (builder.mutualTlsEnabled) {
      container.withCopyToContainer(Transferable.of(builder.clientKey.certificateAsPEM()), "/certs/client.crt");
    }
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return container.apply(base, description);
  }

  public String getHost() {
    return container.getHost();
  }

  public int getPort() {
    return container.getMappedPort(6379);
  }

  public String getRedisUri() {
    return "redis://" + getHost() + ":" + getPort();
  }
}
