package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClient6SecureFailedTest {

  @Container
  public static final RedisStandalone redis = RedisStandalone.builder().setVersion("6.2").setPassword("foobar").build();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  @BeforeEach
  public void before() {
    client = Redis.createClient(
      context.vertx(),
      new RedisOptions().setConnectionString("redis://:foobared@" + redis.getHost() + ":" + redis.getPort()));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void testBasicInterop(VertxTestContext test) {
    final String nonexisting = randomKey();

    client.send(cmd(GET).arg(nonexisting)).onComplete(test.failing(err -> {
      assertTrue(((ErrorType) err).is("WRONGPASS"));
      test.completeNow();
    }));
  }
}
