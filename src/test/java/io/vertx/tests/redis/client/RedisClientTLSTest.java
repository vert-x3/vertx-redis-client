package io.vertx.tests.redis.client;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.KeyPairAndCertificate;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
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

  @ClassRule
  public static final RedisStandalone redis = RedisStandalone.builder()
    .enableTls(serverKey)
    .enableMutualTls(clientKey)
    .build();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testConnection(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setNetClientOptions(createNetClientOptions())
        .setConnectionString("rediss://" + redis.getHost() + ":" + redis.getPort()));

    client.connect()
      .onComplete(should.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.PING))
          .onComplete(should.asyncAssertSuccess(ignored -> {
            conn.close();
            test.complete();
          }));
      }));
  }

  @Test
  public void testInvalidConnection(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setNetClientOptions(createNetClientOptions())
        // in this test, Redis requires SSL and doesn't accept plain text connections;
        // the connection string has wrong scheme
        .setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort()));

    client.connect()
      .onComplete(should.asyncAssertFailure(ignored -> {
        test.complete();
      }));
  }
}
