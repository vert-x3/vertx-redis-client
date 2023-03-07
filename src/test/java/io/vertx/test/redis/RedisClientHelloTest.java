package io.vertx.test.redis;

import io.vertx.core.net.NetSocket;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisClientHelloTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void helloTest(TestContext should) {
    final Async before = should.async();

    rule.vertx()
      .createNetServer()
      .connectHandler(NetSocket::close)
      .listen(9876);

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString("redis://localhost:9876"));

    client.connect().onComplete(onConnect -> {
      should.assertFalse(onConnect.succeeded());
      before.complete();
    });
  }
}
