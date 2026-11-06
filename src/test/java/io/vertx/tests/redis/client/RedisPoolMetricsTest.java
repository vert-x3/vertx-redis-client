package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisPoolMetricsTest {

  static class FakePoolMetrics implements PoolMetrics<Object, Object> {
    private final static Map<String, FakePoolMetrics> METRICS = new ConcurrentHashMap<>();

    public static Map<String, FakePoolMetrics> getMetricsMap() {
      return METRICS;
    }

    public static FakePoolMetrics getMetrics(String name) {
      return METRICS.get(name);
    }

    private static final Object TASK_SUBMITTED = new Object();
    private static final Object TASK_BEGIN = new Object();

    private final String name;
    private final int maxSize;
    private final AtomicInteger pending = new AtomicInteger();
    private final AtomicInteger inUse = new AtomicInteger();

    public FakePoolMetrics(String name, int maxSize) {
      this.name = name;
      this.maxSize = maxSize;
      METRICS.put(name, this);
    }

    @Override
    public synchronized Object enqueue() {
      pending.incrementAndGet();
      return TASK_SUBMITTED;
    }

    @Override
    public void dequeue(Object queueMetric) {
      assert queueMetric == TASK_SUBMITTED;
      pending.decrementAndGet();
    }

    @Override
    public Object begin() {
      inUse.incrementAndGet();
      return TASK_BEGIN;
    }

    public void end(Object t) {
      assert t == TASK_BEGIN;
      inUse.decrementAndGet();
    }

    public int maxSize() {
      return maxSize;
    }

    public int pending() {
      return pending.get();
    }

    public int inUse() {
      return inUse.get();
    }

    @Override
    public void close() {
      METRICS.remove(name);
    }
  }

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

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext(() -> Future.succeededFuture(getVertx()));

  private FakePoolMetrics getMetrics() {
    return FakePoolMetrics.getMetrics(POOL_NAME.get());
  }

  @Test
  public void simpleTest(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));

    client
      .connect().onComplete(test.succeeding(conn -> {
        assertEquals(0, getMetrics().pending());
        assertEquals(1, getMetrics().inUse());

        conn.exceptionHandler(ex -> {
        });

        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(send -> {
          assertNotNull(send);
          assertEquals("PONG", send.toString());

          conn.close();

          assertEquals(0, getMetrics().pending());
          assertEquals(0, getMetrics().inUse());

          client.close().onComplete(test.succeedingThenComplete());
        }));
      }));
  }

  @Test
  public void taintedConnection(VertxTestContext test) {
    Redis client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect()
      .compose(conn -> {
        assertEquals(0, getMetrics().pending());
        assertEquals(1, getMetrics().inUse());

        return conn.send(Request.cmd(Command.SELECT).arg(7)) // taints the connection
          .compose(response -> {
            assertEquals(0, getMetrics().pending());
            assertEquals(1, getMetrics().inUse());

            return conn.close();
          }).onComplete(test.succeeding(ignored -> {
            assertEquals(0, getMetrics().pending());
            assertEquals(0, getMetrics().inUse());
          }));
      })
      .compose(ignored -> client.close())
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testLifecycle(VertxTestContext test) {
    Map<String, FakePoolMetrics> metricsMap = FakePoolMetrics.getMetricsMap();
    assertEquals(Collections.emptySet(), metricsMap.keySet());
    Redis client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    assertEquals(1, metricsMap.size());
    client.connect()
      .onComplete(test.succeeding(conn -> {
        assertEquals(1, metricsMap.size());
        assertEquals(6, getMetrics().maxSize());
        conn.close();
        assertEquals(1, metricsMap.size());
        client.close();
        assertEquals(0, metricsMap.size());
        test.completeNow();
      }));
  }
}
