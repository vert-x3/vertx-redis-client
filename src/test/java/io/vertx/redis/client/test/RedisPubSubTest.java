package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.ResponseType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.Command.*;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private Redis pub;
  private Redis sub;

  @Before
  public void setUp(TestContext should) {
    final Async setUp = should.async();

    Redis.createClient(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"), onCreate -> {
      should.assertTrue(onCreate.succeeded());
      pub = onCreate.result();
      pub.exceptionHandler(should::fail);

      Redis.createClient(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"), onCreate2 -> {
        should.assertTrue(onCreate2.succeeded());
        sub = onCreate2.result();
        sub.exceptionHandler(should::fail);

        setUp.complete();
      });
    });
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    sub.handler(message -> {
      should.assertTrue(message.type() == ResponseType.MULTI);
      should.assertEquals(3, message.size());
      should.assertEquals("message", message.get(0).string());
      should.assertEquals("mychannel", message.get(1).string());
      should.assertEquals("123456", message.get(2).string());
      test.complete();
    });

    sub.send(cmd(SUBSCRIBE).arg("mychannel"), subscribe -> {
      should.assertTrue(subscribe.succeeded());

      rule.vertx().setTimer(100L, t -> {
        pub.send(cmd(PUBLISH).arg("mychannel").arg(123456), publish -> {
          should.assertTrue(publish.succeeded());
          should.assertNotNull(publish.result());
        });
      });
    });
  }
}
