package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Redis;
import io.vertx.redis.RedisConnection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.Args.args;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private RedisConnection pub;
  private RedisConnection sub;

  @Before
  public void setUp(TestContext should) throws Exception {
    final Async setUp = should.async();

    Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "localhost"))
      .open(open -> {
        should.assertTrue(open.succeeded());
        pub = open.result();
        pub.exceptionHandler(should::fail);

        Redis
          .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "localhost"))
          .open(open2 -> {
            should.assertTrue(open2.succeeded());
            sub = open2.result();
            sub.exceptionHandler(should::fail);

            setUp.complete();
          });
      });
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
  }
}
