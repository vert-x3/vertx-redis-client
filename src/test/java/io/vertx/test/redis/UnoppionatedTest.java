package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Redis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.embedded.RedisServer;

@RunWith(VertxUnitRunner.class)
public class UnoppionatedTest {

  private final Vertx vertx = Vertx.vertx();

  private RedisServer server;
  private Redis redis;

  @Before
  public void setUp(TestContext should) throws Exception {
    final Async test = should.async();

    // start a server
    server = new RedisServer(6379);
    server.start();

    redis = Redis.create(vertx);
    redis.exceptionHandler(should::fail);

    redis.open(SocketAddress.inetSocketAddress(6379, "localhost"), open -> {
      should.assertTrue(open.succeeded());
      test.complete();
    });

    test.await();
  }

  @After
  public void tearDown() {
    server.stop();
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    redis.send("ping", send -> {
      should.assertTrue(send.succeeded());
      should.assertNotNull(send.result());

      should.assertEquals("PONG", send.result().asString());
      test.complete();
    });
    test.await();
  }
}
