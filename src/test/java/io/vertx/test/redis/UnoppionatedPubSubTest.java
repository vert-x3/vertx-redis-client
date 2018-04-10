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

import static io.vertx.redis.Args.args;

@RunWith(VertxUnitRunner.class)
public class UnoppionatedPubSubTest {

  private final Vertx vertx = Vertx.vertx();

  private RedisServer server;
  private Redis pub;
  private Redis sub;

  @Before
  public void setUp(TestContext should) throws Exception {
    final Async test = should.async();

    // start a server
    server = new RedisServer(6379);
    server.start();

    pub = Redis.create(vertx);
    pub.exceptionHandler(should::fail);

    pub.open(SocketAddress.inetSocketAddress(6379, "localhost"), pubOpen -> {
      should.assertTrue(pubOpen.succeeded());

      sub = Redis.create(vertx);
      sub.exceptionHandler(should::fail);

      sub.open(SocketAddress.inetSocketAddress(6379, "localhost"), subOpen -> {
        should.assertTrue(subOpen.succeeded());
        test.complete();
      });
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

    sub.handler(message -> {
      should.assertTrue(message.isMulti());
      should.assertEquals(3, message.size());
      should.assertEquals("message", message.get(0).asString());
      should.assertEquals("mychannel", message.get(1).asString());
      should.assertEquals(123456, message.get(2).asInteger());
      test.complete();
    });

    sub.send("subscribe", args().add("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      pub.send("publish", args().add("mychannel").add(123456), publish -> {
        should.assertTrue(publish.succeeded());
        should.assertNotNull(publish.result());
      });
    });

    test.await();
  }
}
