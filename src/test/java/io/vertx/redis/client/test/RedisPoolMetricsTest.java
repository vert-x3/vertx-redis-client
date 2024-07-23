package io.vertx.redis.client.test;

import io.vertx.core.*;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.metrics.impl.DummyVertxMetrics;
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
import io.vertx.redis.containers.RedisStandalone;
import io.vertx.test.fakemetrics.FakePoolMetrics;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

@RunWith(VertxUnitRunner.class)
public class RedisPoolMetricsTest {

  private static final AtomicReference<String> POOL_NAME = new AtomicReference<>();

  private static VertxOptions getOptions() {
    MetricsOptions options = new MetricsOptions().setEnabled(true);
    options.setFactory(new VertxMetricsFactory() {
      @Override
      public VertxMetrics metrics(VertxOptions options) {
        return new DummyVertxMetrics() {
          @Override
          public PoolMetrics<?> createPoolMetrics(String poolType, String poolName, int maxPoolSize) {
            if (poolType.equals("redis")) {
              POOL_NAME.set(poolName);
              return new FakePoolMetrics(poolName, maxPoolSize);
            } else {
              return super.createPoolMetrics(poolType, poolName, maxPoolSize);
            }
          }
        };
      }
    });
    return new VertxOptions().setMetricsOptions(options);
  }

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext(getOptions());

  private FakePoolMetrics getMetrics() {
    return (FakePoolMetrics) FakePoolMetrics.getPoolMetrics().get(POOL_NAME.get());
  }

  @Test
  public void simpleTest(TestContext should) {
    final Async test = should.async();

    Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));

    client
      .connect(create -> {
        should.assertTrue(create.succeeded());

        should.assertEquals(0, getMetrics().numberOfWaitingTasks());
        should.assertEquals(1, getMetrics().numberOfRunningTasks());

        final RedisConnection redis = create.result();

        redis.exceptionHandler(ex -> {

        });

        redis.send(Request.cmd(Command.PING), send -> {
          should.assertTrue(send.succeeded());
          should.assertNotNull(send.result());

          should.assertEquals("PONG", send.result().toString());

          redis.close();

          should.assertEquals(0, getMetrics().numberOfWaitingTasks());
          should.assertEquals(0, getMetrics().numberOfRunningTasks());

          client.close();
          test.complete();
        });
      });
  }

  @Test
  public void testLifecycle(TestContext should) {
    final Async test = should.async();

    Map<String, PoolMetrics> metricsMap = FakePoolMetrics.getPoolMetrics();
    should.assertEquals(Collections.emptySet(), metricsMap.keySet());
    Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    should.assertEquals(1, metricsMap.size());
    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn -> {
        should.assertEquals(1, metricsMap.size());
        should.assertEquals(6, getMetrics().getPoolSize());
        conn.close();
        should.assertEquals(1, metricsMap.size());
        client.close();
        should.assertEquals(0, metricsMap.size());
        test.complete();
      });
  }

}
