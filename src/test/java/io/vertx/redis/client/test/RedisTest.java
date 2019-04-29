package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.*;

@RunWith(VertxUnitRunner.class)
public class RedisTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"))
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final Redis redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void simpleSelectTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), new RedisOptions().addEndpoint(SocketAddress.inetSocketAddress(7006, "127.0.0.1")).setSelect(0))
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final Redis redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void batchTest(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"))
      .connect(create -> {
        should.assertTrue(create.succeeded());

        final Redis redis = create.result();

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

  @Test
  public void simpleTestAPI(TestContext should) {
    final Async test = should.async();

    Redis.createClient(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"))
      .connect(create -> {
        should.assertTrue(create.succeeded());

        RedisAPI redis = RedisAPI.api(create.result());

        redis.set(Arrays.asList("key1", "value1"), set -> {
          should.assertTrue(set.succeeded());
          should.assertNotNull(set.result());

          should.assertEquals("OK", set.result().toString());
          test.complete();
        });
      });
  }
}
