package io.vertx.redis.client.impl;

import io.vertx.core.Vertx;
import io.vertx.core.internal.pool.ConnectionPool;
import io.vertx.core.net.impl.endpoint.EndpointManager;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.RedisConnectionManager.ConnectionKey;
import io.vertx.redis.client.impl.RedisConnectionManager.RedisEndpoint;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

@RunWith(VertxUnitRunner.class)
public class ConnectionRecyclingTest {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  Vertx vertx;
  Redis client;

  @Before
  public void setup() {
    vertx = Vertx.vertx();
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setMaxPoolSize(2)
      .setPoolCleanerInterval(100)
      .setPoolRecycleTimeout(1000);
    client = Redis.createClient(vertx, options);
  }

  @After
  public void teardown(TestContext test) {
    client.close();
    vertx.close().onComplete(test.asyncAssertSuccess());
  }

  @Test
  public void testUsageShorterThanRecycleTimeout(TestContext test) {
    Async async = test.async();

    assertConnectionPool(test, 0);

    client.connect()
      .flatMap(conn -> {
        assertConnectionPool(test, 1);
        return conn.close();
      }).onComplete(test.asyncAssertSuccess(ignored -> {
        assertConnectionPool(test, 1);

        vertx.setTimer(2000, ignored2 -> {
          assertConnectionPool(test, 0);
          async.complete();
        });
      }));
  }

  @Test
  public void testUsageLongerThanRecycleTimeout(TestContext test) {
    Async async = test.async();

    assertConnectionPool(test, 0);

    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
      assertConnectionPool(test, 1);
      useConnectionForLongTime(conn, System.currentTimeMillis() + 2000);
    }));

    vertx.setTimer(2500, ignored -> {
      assertConnectionPool(test, 1);

      vertx.setTimer(1000, ignored2 -> {
        assertConnectionPool(test, 0);
        async.complete();
      });
    });
  }

  private void useConnectionForLongTime(RedisConnection conn, long endTime) {
    if (endTime < System.currentTimeMillis()) {
      conn.close();
      return;
    }

    conn.send(Request.cmd(Command.INFO))
      .onSuccess(ignored -> {
        vertx.setTimer(100, ignored2 -> {
          useConnectionForLongTime(conn, endTime);
        });
      });
  }

  private void assertConnectionPool(TestContext test, int expectedSize) {
    IntBox size = new IntBox(0);

    try {
      RedisConnectionManager connManager = ((RedisClient) client).connectionManager;
      Field field = RedisConnectionManager.class.getDeclaredField("pooledConnectionManager");
      field.setAccessible(true);
      EndpointManager<ConnectionKey, RedisEndpoint> endpointManager =
        (EndpointManager<ConnectionKey, RedisEndpoint>) field.get(connManager);
      endpointManager.forEach(endpoint -> {
        ConnectionPool<RedisConnectionInternal> pool = ((RedisEndpoint) endpoint).pool;
        size.value += pool.size();
      });
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

    test.assertEquals(expectedSize, size.value);
  }

  static class IntBox {
    int value;

    IntBox(int value) {
      this.value = value;
    }
  }
}
