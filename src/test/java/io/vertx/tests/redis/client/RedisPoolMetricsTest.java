package io.vertx.tests.redis.client;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import io.vertx.test.fakemetrics.FakePoolMetrics;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(VertxUnitRunner.class)
public class RedisPoolMetricsTest {

  private static final AtomicReference<String> POOL_NAME = new AtomicReference<>();

  private static Vertx getVertx() {
    return Vertx.builder()
      .withMetrics(new VertxMetricsFactory() {
        @Override
        public VertxMetrics metrics(VertxOptions options) {
          return new VertxMetrics() {
            @Override
            public PoolMetrics<?, ?> createPoolMetrics(String poolType, String poolName, int maxPoolSize) {
              if (poolType.equals("redis")) {
                POOL_NAME.set(poolName);
                return new FakePoolMetrics(poolName, maxPoolSize);
              } else {
                return VertxMetrics.super.createPoolMetrics(poolType, poolName, maxPoolSize);
              }
            }
          };
        }
      })
      .build();
  }

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext(RedisPoolMetricsTest::getVertx);

  private FakePoolMetrics getMetrics() {
    return FakePoolMetrics.getMetrics(POOL_NAME.get());
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));

    client
      .connect().onComplete(create -> {
        should.assertTrue(create.succeeded());

        should.assertEquals(0, getMetrics().pending());
        should.assertEquals(1, getMetrics().inUse());

        final RedisConnection redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING)).onComplete(send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());

          redis.close();

          should.assertEquals(0, getMetrics().pending());
          should.assertEquals(0, getMetrics().inUse());

          client.close();
          test.complete();
        });
      });
  }

  @Test
  public void taintedConnection(TestContext test) {
    Async async = test.async();

    Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect()
      .compose(conn -> {
        test.assertEquals(0, getMetrics().pending());
        test.assertEquals(1, getMetrics().inUse());

        return conn.send(Request.cmd(Command.SELECT).arg(7)) // taints the connection
          .compose(response -> {
            test.assertEquals(0, getMetrics().pending());
            test.assertEquals(1, getMetrics().inUse());

            return conn.close();
          }).onComplete(test.asyncAssertSuccess(ignored -> {
            test.assertEquals(0, getMetrics().pending());
            test.assertEquals(0, getMetrics().inUse());
          }));
      })
      .compose(ignored -> client.close())
      .onComplete(test.asyncAssertSuccess(ignored -> {
        async.complete();
      }));
  }

  @Test
  public void testLifecycle(TestContext should) {
    final Async test = should.async();

    Map<String, FakePoolMetrics> metricsMap = FakePoolMetrics.getMetrics();
    should.assertEquals(Collections.emptySet(), metricsMap.keySet());
    Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    should.assertEquals(1, metricsMap.size());
    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        should.assertEquals(1, metricsMap.size());
        should.assertEquals(6, getMetrics().maxSize());
        conn.close();
        should.assertEquals(1, metricsMap.size());
        client.close();
        should.assertEquals(0, metricsMap.size());
        test.complete();
      });
  }

}
