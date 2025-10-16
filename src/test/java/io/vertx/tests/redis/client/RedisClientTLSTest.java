package io.vertx.tests.redis.client;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.KeyPairAndCertificate;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientTLSTest {

  private static final KeyPairAndCertificate serverKey = KeyPairAndCertificate.generateSelfSigned("redis");

  private static final KeyPairAndCertificate clientKey = KeyPairAndCertificate.generateSelfSigned("test");

  private static NetClientOptions createNetClientOptions() {
    return new NetClientOptions()
      .setKeyCertOptions(new JksOptions().setValue(Buffer.buffer(clientKey.privateKeyAsJKS())).setPassword(""))
      .setTrustOptions(new JksOptions().setValue(Buffer.buffer(serverKey.certificateAsJKS())))
      .setTcpKeepAlive(true)
      .setTcpNoDelay(true)
      // we cannot know the hostname, and hostname verification is not important for this test
      .setHostnameVerificationAlgorithm("");
  }

  @Container
  public static final RedisStandalone redis = RedisStandalone.builder()
    .enableTls(serverKey)
    .enableMutualTls(clientKey)
    .build();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testConnection(VertxTestContext test) {
    Redis client = Redis.createClient(
      context.vertx(),
      new RedisOptions()
        .setNetClientOptions(createNetClientOptions())
        .setConnectionString("rediss://" + redis.getHost() + ":" + redis.getPort()));

    client.connect()
      .onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.PING))
          .onComplete(test.succeeding(ignored -> {
            conn.close().onComplete(test.succeedingThenComplete());
          }));
      }));
  }

  @Test
  public void testInvalidConnection(VertxTestContext test) {
    Redis client = Redis.createClient(
      context.vertx(),
      new RedisOptions()
        .setNetClientOptions(createNetClientOptions())
        // in this test, Redis requires SSL and doesn't accept plain text connections;
        // the connection string has wrong scheme
        .setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort()));

    client.connect().onComplete(test.failingThenComplete());
  }
}
