package io.vertx.tests.redis.client;

import io.vertx.core.net.NetSocket;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

@ExtendWith(VertxExtension.class)
public class RedisClientToBadServerTest {

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void helloTest(VertxTestContext test) {
    context.vertx()
      .createNetServer()
      .connectHandler(NetSocket::close)
      .listen(9876);

    Redis client = Redis.createClient(
      context.vertx(),
      new RedisOptions().setConnectionString("redis://localhost:9876"));

    client.connect().onComplete(test.failingThenComplete());
  }
}
