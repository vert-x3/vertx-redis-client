package io.vertx.test.redis;

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

@RunWith(VertxUnitRunner.class)
public class RedisClientTLSTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:6.2.1")
    .withExposedPorts(6379);

  private volatile NetServer  server;

  @Before
  public void setUp(TestContext should) {
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

    rule.vertx()
      .createNetServer(options)
      .connectHandler(sockA -> {
        // client A is connected, open a socket to the redis server
        rule.vertx()
          .createNetClient()
          .connect(redis.getFirstMappedPort(), redis.getContainerIpAddress())
          .onFailure(should::fail)
          .onSuccess(sockB -> {
            // pump
            Pump.pump(sockA, sockB).start();
            Pump.pump(sockB, sockA).start();
          });
      }).listen(0)
      .onFailure(should::fail)
      .onSuccess(server -> {
        this.server = server;
        test.complete();
      });
  }

  @After
  public void tearDown(TestContext should) {
    final Async test = should.async();

    server.close()
      .onFailure(should::fail)
      .onSuccess(v -> {
        server = null;
        test.complete();
      });
  }

  @Test
  public void testConnectionStringUpgrade(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setTrustAll(true))
        .setConnectionString("rediss://localhost:" + server.actualPort()));

    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        conn.send(Request.cmd(Command.INFO))
          .onFailure(should::fail)
          .onSuccess(res -> {
            System.out.println(res);
            test.complete();
          });
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
        .setConnectionString("rediss://localhost:" + server.actualPort()));

    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        conn.send(Request.cmd(Command.INFO))
          .onFailure(should::fail)
          .onSuccess(res -> {
            System.out.println(res);
            test.complete();
          });
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
        .setConnectionString("redis://localhost:" + server.actualPort()));

    client.connect()
      .onFailure(t -> test.complete())
      .onSuccess(res -> should.fail());
  }
}
