package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.http.HttpMethod;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisThreadConsistencyTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void redisThreadConsistencyTest(VertxTestContext test) {
    context.vertx()
      .deployVerticle(new Verticle(
        RedisAPI.api(
          Redis.createClient(
            context.vertx(),
            new RedisOptions().setConnectionString(redis.getRedisUri())))))
      .onComplete(test.succeeding(id -> {
        context.vertx()
          .createHttpClient()
          .request(HttpMethod.GET, 8080, "localhost", "/")
          .onComplete(test.succeeding(req -> {
            req.send()
              .onComplete(test.succeeding(res -> {
                assertEquals(res.getHeader("initialThread"), res.getHeader("threadAfterRedisExecution"));
                test.completeNow();
              }));
          }));
      }));
  }

  static class Verticle extends VerticleBase {

    RedisAPI redisAPI;

    Verticle(RedisAPI redisApi) {
      this.redisAPI = redisApi;
    }

    @Override
    public Future<?> start() {
      return vertx.createHttpServer()
        .requestHandler(req -> {
          String threadBeforeRedisCommandExecution = Thread.currentThread().getName();
          redisAPI.info(Collections.emptyList()).onComplete(result -> {
            String threadAfterRedisCommandExecution = Thread.currentThread().getName();
            req.response().putHeader("initialThread", threadBeforeRedisCommandExecution)
              .putHeader("threadAfterRedisExecution", threadAfterRedisCommandExecution)
              .end("Ok");
          });
        })
        .listen(8080);
    }
  }
}
