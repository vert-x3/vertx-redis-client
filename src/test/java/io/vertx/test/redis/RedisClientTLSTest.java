package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import org.junit.BeforeClass;
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
      .setTcpKeepAlive(true)
      .setTcpNoDelay(true)
      .setSsl(true)
      .setKeyCertOptions(
        new JksOptions()
          .setPath("server-keystore.jks")
          .setPassword("wibble"));

    proxyVertx
      .createNetServer(options)
      .connectHandler(sockA -> {
        // pause until the proxy client is ready
        sockA.pause();

        sockA.exceptionHandler(Throwable::printStackTrace);

        // client A is connected, open a socket to the redis server
        proxyVertx
          .createNetClient(new NetClientOptions()
            .setTcpKeepAlive(true)
            .setTcpNoDelay(true))
          .connect(redis.getFirstMappedPort(), redis.getHost())
          .onFailure(err -> {
            System.out.println("PROXY CLIENT ERR: " + err);
            should.fail(err);
          })
          .onSuccess(sockB -> {

            sockB.exceptionHandler(Throwable::printStackTrace);

            // pump
            sockA.handler(buff -> {
//              System.out.println("-> " + buff);
              sockB.write(buff)
                .onFailure(should::fail);
            });

            // resume from pause
            sockA.resume();

            sockB.handler(buff -> {
//              System.out.println("<- " + buff);
              sockA.write(buff)
                .onFailure(should::fail);
            });

            sockA.endHandler(v -> sockB.end());
            sockB.endHandler(v -> sockA.end());
          });
      }).listen(0)
      .onFailure(should::fail)
      .onSuccess(server -> {
        RedisClientTLSTest.server = server;
        test.complete();
      });
  }

  @Test(timeout = 30_000L)
  public void testConnectionStringUpgrade(TestContext should) {
    final Async test = should.async();

    // begin test
    final int port = server.actualPort();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setTrustAll(true)
          // default values
          .setTcpKeepAlive(true)
          .setTcpNoDelay(true)
          .setHostnameVerificationAlgorithm("HTTPS"))
        .setConnectionString("rediss://localhost:" + port + "?test=upgrade"));

    client.connect()
      .onComplete(should.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.PING))
          .onComplete(should.asyncAssertSuccess(ignored -> {
            conn.close();
            test.complete();
          }));
      }));
  }

  @Test(timeout = 30_000L)
  public void testConnectionOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true).setHostnameVerificationAlgorithm("HTTPS")
          // default values
          .setTcpKeepAlive(true)
          .setTcpNoDelay(true))
        .setConnectionString("rediss://localhost:" + server.actualPort()));

    client.connect()
      .onComplete(should.asyncAssertSuccess(conn -> {
        conn.send(Request.cmd(Command.PING))
          .onComplete(should.asyncAssertSuccess(ignored -> {
            conn.close();
            test.complete();
          }));
      }));
  }

  @Test(timeout = 30_000L)
  public void testInvalidOptions(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        // were using self signed certificates so we need to trust all
        .setNetClientOptions(new NetClientOptions().setSsl(true).setTrustAll(true).setHostnameVerificationAlgorithm("HTTPS")
          // default values
          .setTcpKeepAlive(true)
          .setTcpNoDelay(true))
        // pool is SSL but connection string isn't
        .setConnectionString("redis://localhost:" + server.actualPort()));

    client.connect()
      .onComplete(should.asyncAssertFailure(ignored -> {
        test.complete();
      }));
  }
}
