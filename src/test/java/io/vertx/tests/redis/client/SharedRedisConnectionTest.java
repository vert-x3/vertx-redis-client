package io.vertx.tests.redis.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class SharedRedisConnectionTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  private static final int VERTICLES_COUNT = 10;
  private static final int ITERATIONS_COUNT = 1000;

  private static final String REDIS_NUMBER_VALUE_KEY = "user:post:pinned:1372";
  private static final String REDIS_SET_VALUE_KEY = "user:like:post:975";

  Vertx vertx;
  RedisAPI conn;

  @Before
  public void setup(TestContext test) {
    Async async = test.async();
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
          test.fail(result.cause());
        }
        async.complete();
      });
  }

  @After
  public void teardown(TestContext test) {
    conn.close();
    vertx.close().onComplete(test.asyncAssertSuccess());
  }

  @Test
  public void test(TestContext test) {
    vertx.deployVerticle(() -> new MyVerticle(conn, test), new DeploymentOptions().setInstances(VERTICLES_COUNT));
  }

  public static class MyVerticle extends AbstractVerticle {
    private final RedisAPI conn;
    private final TestContext test;

    public MyVerticle(RedisAPI conn, TestContext test) {
      this.conn = conn;
      this.test = test;
    }

    @Override
    public void start() {
      Async async = test.async(ITERATIONS_COUNT);
      for (int i = 0; i < ITERATIONS_COUNT; i++) {
        test()
          .onSuccess(ignored -> async.countDown())
          .onFailure(test::fail);
      }
    }

    private Future<?> test() {
      Future<Response> fetchNumberFuture = conn.get(REDIS_NUMBER_VALUE_KEY)
        .onSuccess(response -> {
          try {
            response.toInteger();
          } catch (Exception e) {
            test.fail(e);
          }
        });

      Future<Response> fetchSetFuture = conn.smembers(REDIS_SET_VALUE_KEY)
        .onSuccess(response -> {
          try {
            for (Response part : response) {
              part.toInteger();
            }
          } catch (Exception e) {
            test.fail(e);
          }
        });

      return Future.all(fetchNumberFuture, fetchSetFuture);
    }
  }
}
