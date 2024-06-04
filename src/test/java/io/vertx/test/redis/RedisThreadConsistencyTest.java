package io.vertx.test.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

@RunWith(VertxUnitRunner.class)
public class RedisThreadConsistencyTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void redisThreadConsistencyTest(TestContext should) {
    final Async test = should.async();

    rule.vertx()
      .deployVerticle(new Verticle(
        RedisAPI.api(
          Redis.createClient(
            rule.vertx(),
            new RedisOptions().setConnectionString(redis.getRedisUri())))))
      .onFailure(should::fail)
      .onSuccess(id -> {
        rule.vertx()
          .createHttpClient()
          .request(HttpMethod.GET, 8080, "localhost", "/")
          .onFailure(should::fail)
          .onSuccess(req -> {
            req.send()
              .onFailure(should::fail)
              .onSuccess(res -> {
                should.assertEquals(res.getHeader("initialThread"), res.getHeader("threadAfterRedisExecution"));
                test.complete();
              });
          });
      });
  }

  static class Verticle extends AbstractVerticle {

    RedisAPI redisAPI;

    Verticle(RedisAPI redisApi) {
      this.redisAPI = redisApi;
    }

    @Override
    public void start(Promise<Void> startFuture) {
      vertx.createHttpServer()
        .requestHandler(req -> {
          String threadBeforeRedisCommandExecution = Thread.currentThread().getName();
          redisAPI.info(Collections.emptyList()).onComplete(result -> {
            String threadAfterRedisCommandExecution = Thread.currentThread().getName();
            req.response().putHeader("initialThread", threadBeforeRedisCommandExecution)
              .putHeader("threadAfterRedisExecution", threadAfterRedisCommandExecution)
              .end("Ok");
          });
        })
        .listen(8080)
        .onFailure(startFuture::fail)
        .onSuccess(conn -> startFuture.complete());
    }
  }
}
