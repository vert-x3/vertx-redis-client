package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Redis;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.vertx.redis.RedisCommand.cmd;
import static io.vertx.redis.RedisCommandEnum.*;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class RedisTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    final Redis redis = Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"));

    redis
      .connect(open -> {
        should.assertTrue(open.succeeded());

        redis.send("ping", send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().asString());
          test.complete();
        });
      });
  }

  @Test
  public void batchTest(TestContext should) {
    final Async test = should.async();

    final Redis redis = Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"));

    redis
      .connect(open -> {
        should.assertTrue(open.succeeded());

        redis.batch(Arrays.asList(
          cmd(MULTI),
          cmd(SET).arg("a").arg(3),
          cmd(LPOP).arg("a"),
          cmd(EXEC)
        ), batch -> {
          should.assertTrue(batch.succeeded());
          test.complete();
        });
      });
  }
}
