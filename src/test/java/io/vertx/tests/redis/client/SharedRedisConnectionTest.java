package io.vertx.tests.redis.client;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class SharedRedisConnectionTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  private static final int VERTICLES_COUNT = 10;
  private static final int ITERATIONS_COUNT = 1000;

  private static final String REDIS_NUMBER_VALUE_KEY = "user:post:pinned:1372";
  private static final String REDIS_SET_VALUE_KEY = "user:like:post:975";

  Vertx vertx;
  RedisAPI conn;

  @BeforeEach
  public void setup(VertxTestContext test) {
    vertx = Vertx.vertx();
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setMaxWaitingHandlers(VERTICLES_COUNT * ITERATIONS_COUNT * 2); // 2 requests per iteration
    Redis.createClient(vertx, options)
      .connect()
      .map(RedisAPI::api)
      .flatMap(api -> {
        return api.set(Arrays.asList(REDIS_NUMBER_VALUE_KEY, "42"))
          .map(api);
      }).flatMap(api -> {
        return api.sadd(Arrays.asList(REDIS_SET_VALUE_KEY, "100", "101", "102"))
          .map(api);
      })
      .onComplete(result -> {
        if (result.succeeded()) {
          conn = result.result();
        } else {
          test.failNow(result.cause());
        }
        test.completeNow();
      });
  }

  @AfterEach
  public void teardownVer(VertxTestContext test) {
    conn.close();
    vertx.close().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void test(VertxTestContext test) {
    vertx.deployVerticle(() -> new MyVerticle(conn, test), new DeploymentOptions().setInstances(VERTICLES_COUNT));
  }

  public static class MyVerticle extends VerticleBase {
    private final RedisAPI conn;
    private final VertxTestContext test;

    public MyVerticle(RedisAPI conn, VertxTestContext test) {
      this.conn = conn;
      this.test = test;
    }

    @Override
    public Future<?> start() throws Exception {
      Checkpoint checkpoint = test.checkpoint(ITERATIONS_COUNT);
      for (int i = 0; i < ITERATIONS_COUNT; i++) {
        test()
          .onSuccess(ignored -> checkpoint.flag())
          .onFailure(test::failNow);
      }
      return super.start();
    }

    private Future<?> test() {
      Future<Response> fetchNumberFuture = conn.get(REDIS_NUMBER_VALUE_KEY)
        .onSuccess(response -> {
          try {
            response.toInteger();
          } catch (Exception e) {
            test.failNow(e);
          }
        });

      Future<Response> fetchSetFuture = conn.smembers(REDIS_SET_VALUE_KEY)
        .onSuccess(response -> {
          try {
            for (Response part : response) {
              part.toInteger();
            }
          } catch (Exception e) {
            test.failNow(e);
          }
        });

      return Future.all(fetchNumberFuture, fetchSetFuture);
    }
  }
}
