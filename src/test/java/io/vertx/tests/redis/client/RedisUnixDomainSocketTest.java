package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.condition.JRE.JAVA_16;
import static org.junit.jupiter.api.condition.OS.LINUX;

@ExtendWith(VertxExtension.class)
@Testcontainers
@EnabledOnOs({LINUX})
@EnabledForJreRange(min = JAVA_16)
public class RedisUnixDomainSocketTest {

  @Container
  public static final RedisStandalone redis = RedisStandalone.builder()
    .enableUnixDomainSocket()
    .build();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void simpleTest(VertxTestContext test) {
    Redis.createClient(context.vertx(),
        new RedisOptions().setConnectionString(redis.getRedisUnixDomainSocketUri()))
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(ex -> {
        });

        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
          assertNotNull(send);
          assertEquals("PONG", send.toString());
          test.completeNow();
        }));
      }));
  }
}
