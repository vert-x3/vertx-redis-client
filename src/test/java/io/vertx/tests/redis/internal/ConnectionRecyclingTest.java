package io.vertx.tests.redis.internal;

import io.vertx.core.Vertx;
import io.vertx.core.internal.pool.ConnectionPool;
import io.vertx.core.internal.resource.ResourceManager;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.Primitives.Int;
import io.vertx.redis.client.impl.RedisClient;
import io.vertx.redis.client.impl.RedisConnectionInternal;
import io.vertx.redis.client.impl.RedisConnectionManager;
import io.vertx.redis.client.impl.RedisConnectionManager.ConnectionKey;
import io.vertx.redis.client.impl.RedisConnectionManager.RedisEndpoint;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class ConnectionRecyclingTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  Vertx vertx;
  Redis client;

  @BeforeEach
  public void setup() {
    vertx = Vertx.vertx();
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setMaxPoolSize(2)
      .setPoolCleanerInterval(100)
      .setPoolRecycleTimeout(1000);
    client = Redis.createClient(vertx, options);
  }

  @AfterEach
  public void teardown(VertxTestContext test) {
    client.close();
    vertx.close().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testUsageShorterThanRecycleTimeout(VertxTestContext test) {
    assertConnectionPool(test, 0);

    client.connect()
      .flatMap(conn -> {
        assertConnectionPool(test, 1);
        return conn.close();
      }).onComplete(test.succeeding(ignored -> {
        assertConnectionPool(test, 1);

        vertx.setTimer(2000, ignored2 -> {
          assertConnectionPool(test, 0);
          test.completeNow();
        });
      }));
  }

  @Test
  public void testUsageLongerThanRecycleTimeout(VertxTestContext test) {
    assertConnectionPool(test, 0);

    client.connect().onComplete(test.succeeding(conn -> {
      assertConnectionPool(test, 1);
      useConnectionForLongTime(conn, System.currentTimeMillis() + 2000);
    }));

    vertx.setTimer(2500, ignored -> {
      assertConnectionPool(test, 1);

      vertx.setTimer(1500, ignored2 -> {
        assertConnectionPool(test, 0);
        test.completeNow();
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

  private void assertConnectionPool(VertxTestContext test, int expectedSize) {
    Int size = new Int(0);

    try {
      RedisConnectionManager connManager = ((RedisClient) client).connectionManager();
      Field field = RedisConnectionManager.class.getDeclaredField("pooledConnectionManager");
      field.setAccessible(true);
      ResourceManager<ConnectionKey, RedisEndpoint> endpointManager =
        (ResourceManager<ConnectionKey, RedisEndpoint>) field.get(connManager);
      endpointManager.forEach(endpoint -> {
        ConnectionPool<RedisConnectionInternal> pool = ((RedisEndpoint) endpoint).pool();
        size.value += pool.size();
      });
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }

    test.verify(() -> {
      assertEquals(expectedSize, size.value);
    });
  }
}
