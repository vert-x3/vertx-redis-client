package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.streams.Pump;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(VertxUnitRunner.class)
public class RedisClientTLSTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:6.2.1")
    .withExposedPorts(6379);

  private static final Vertx proxyVertx = Vertx.vertx();
  private static NetServer server;

  @BeforeClass
  public static void beforeAll(TestContext should) {
    final Async test = should.async();

    // redis server (OSS) does not include TLS out of the box
    // users are expected to serve it behind a TLS tunnel
    // this test fakes it by creating a tunnel to the final open redis server
    NetServerOptions options = new NetServerOptions()
      .setSsl(true)
      .setKeyStoreOptions(
        new JksOptions()
          .setPath("server-keystore.jks")
          .setPassword("wibble"));

    proxyVertx
      .createNetServer(options)
      .connectHandler(sockA -> {
        // client A is connected, open a socket to the redis server
        proxyVertx
          .createNetClient()
          .connect(redis.getFirstMappedPort(), redis.getContainerIpAddress())
          .onFailure(err -> {
            System.out.println("PROXY CLIENT ERR" + err);
            should.fail(err);
          })
          .onSuccess(sockB -> {
            // pump
            Pump.pump(sockA, sockB).start();
            Pump.pump(sockB, sockA).start();
          });
      }).listen(0)
      .onFailure(should::fail)
      .onSuccess(server -> {
        RedisClientTLSTest.server = server;
        test.complete();
      });
  }

  @AfterClass
  public static void shutdown(TestContext should) {
    final Async test = should.async();

    server.close()
      .onFailure(should::fail)
      .onSuccess(v -> test.complete());
  }

  @Test(timeout = 30_000L)
  public void testConnectionStringUpgrade(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setTrustAll(true))
        .setConnectionString("rediss://localhost:" + server.actualPort()));

    final AtomicBoolean started = new AtomicBoolean(false);

    client.connect()
      .onFailure(err -> {
        System.out.println("REDIS CLIENT (CONNECT) ERR" + err);
        if (!started.get()) {
          should.fail(err);
        }
      })
      .onSuccess(conn -> {
        started.set(true);
        conn.send(Request.cmd(Command.PING))
          .onFailure(should::fail)
          .onSuccess(res -> {
            System.out.println("REDIS CLIENT SUCCESS");
            test.complete();
          });
      });
  }

  @Test(timeout = 30_000L)
  public void testConnectionOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true))
        .setConnectionString("rediss://localhost:" + server.actualPort()));

    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        conn.send(Request.cmd(Command.PING))
          .onFailure(should::fail)
          .onSuccess(res -> {
            System.out.println(res);
            test.complete();
          });
      });
  }

  @Test(timeout = 30_000L)
  public void testInvalidOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true))
        // pool is SSL but connection string isn't
        .setConnectionString("redis://localhost:" + server.actualPort()));

    client.connect()
      .onFailure(t -> test.complete())
      .onSuccess(res -> should.fail());
  }
}
