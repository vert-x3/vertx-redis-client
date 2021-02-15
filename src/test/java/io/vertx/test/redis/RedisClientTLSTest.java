package io.vertx.test.redis;

import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.Pump;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

@RunWith(VertxUnitRunner.class)
public class RedisClientTLSTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:6")
    .withExposedPorts(6379);

  @Before
  public void test(TestContext should) {
    // redis server (OSS) does not include TLS out of the box
    // users are expected to serve it behind a TLS tunnel
    // this test fakes it by creating a tunnel to the final open redis server
    NetServerOptions options = new NetServerOptions()
      .setSsl(true)
      .setKeyStoreOptions(
        new JksOptions()
          .setPath("server-keystore.jks")
          .setPassword("wibble"));

    rule.vertx()
      .createNetServer(options)
      .connectHandler(sockA -> {
        // client A is connected, open a socket to the redis server
        rule.vertx()
          .createNetClient()
          .connect(redis.getFirstMappedPort(), redis.getContainerIpAddress(), onConnect -> {
            if (onConnect.failed()) {
              should.fail(onConnect.cause());
            } else {
              NetSocket sockB = onConnect.result();
              // pump
              Pump.pump(sockA, sockB).start();
              Pump.pump(sockB, sockA).start();
            }
          });
      }).listen(1234);
  }

  @Test
  public void testConnectionStringUpgrade(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setTrustAll(true))
        .setConnectionString("rediss://localhost:1234"));

    client.connect(onConnect -> {
      if (onConnect.failed()) {
        should.fail(onConnect.cause());
      } else {
        RedisConnection conn = onConnect.result();
        conn.send(Request.cmd(Command.INFO), onSend -> {
          if (onSend.failed()) {
            should.fail(onSend.cause());
          } else {
            test.complete();
          }
        });
      }
    });
  }

  @Test
  public void testConnectionOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true))
        .setConnectionString("rediss://localhost:1234"));

    client.connect(onConnect -> {
      if (onConnect.failed()) {
        should.fail(onConnect.cause());
      } else {
        RedisConnection conn = onConnect.result();
        conn.send(Request.cmd(Command.INFO), onSend -> {
          if (onSend.failed()) {
            should.fail(onSend.cause());
          } else {
            test.complete();
          }
        });
      }
    });
  }

  @Test
  public void testInvalidOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true))
        // pool is SSL but connection string isn't
        .setConnectionString("redis://localhost:1234"));

    client.connect(onConnect -> {
      if (onConnect.failed()) {
        test.complete();
      } else {
        should.fail();
      }
    });
  }
}
