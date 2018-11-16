package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Args;
import io.vertx.redis.Command;
import io.vertx.redis.Redis;
import io.vertx.redis.RedisConnection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class RedisTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"))
      .open(open -> {
        should.assertTrue(open.succeeded());
        final RedisConnection conn = open.result();

        conn.send("ping", send -> {
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

    Redis
      .create(rule.vertx(), SocketAddress.inetSocketAddress(7006, "127.0.0.1"))
      .open(open -> {
        should.assertTrue(open.succeeded());
        final RedisConnection conn = open.result();

        conn.batch(Arrays.asList(
          new Command().setCommand("MULTI"),
          new Command().setCommand("SET").setArgs(Args.args().add("a").add(3)),
          new Command().setCommand("LPOP").setArgs(Args.args().add("a")),
          new Command().setCommand("EXEC")), batch -> {
          should.assertTrue(batch.succeeded());
          test.complete();
        });
      });
  }
}
