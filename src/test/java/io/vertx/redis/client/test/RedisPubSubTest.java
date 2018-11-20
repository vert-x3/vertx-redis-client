package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Redis;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.RedisCommand.cmd;
import static io.vertx.redis.RedisCommandEnum.*;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private Redis pub;
  private Redis sub;

  @Before
  public void setUp(TestContext should) throws Exception {
    final Async setUp = should.async();

    pub = Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"));

    pub.connect(open -> {
      should.assertTrue(open.succeeded());
      pub.exceptionHandler(should::fail);

      sub = Redis
        .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"));

      sub.connect(open2 -> {
        should.assertTrue(open2.succeeded());
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

    sub.send(cmd(SUBSCRIBE).arg("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      pub.send(cmd(PUBLISH).arg("mychannel").arg(123456), publish -> {
        should.assertTrue(publish.succeeded());
        should.assertNotNull(publish.result());
      });
    });
  }
}
