package io.vertx.test.redis;

import io.vertx.core.AsyncResult;
import org.junit.Test;

public class NullableTest extends AbstractRedisClientBase {

  private static final String notExistsKey = "notExistsKey";

  @Test
  public void testGetNullable() {
    makeSureKeyNotExistes(() -> {
      redis.get(notExistsKey, this::handle);
    });
    await();
  }

  @Test
  public void testGetBinaryNullable() {
    makeSureKeyNotExistes(() -> {
      redis.getBinary(notExistsKey, asyncResult -> {
        if (asyncResult.failed()) {
          fail(asyncResult.cause());
        } else {
          assertNull(asyncResult.result());
          testComplete();
        }
      });
    });
    await();
  }

  @Test
  public void testGetSetNullable() {
    makeSureKeyNotExistes(() -> {
      redis.getset(notExistsKey, "value", this::handle);
    });
    await();
  }

  @Test
  public void testHGetNullable() {
    makeSureKeyNotExistes(() -> {
      String notExistsField = "notExistsField";
      redis.hget(notExistsKey, notExistsField, this::handle);
    });
    await();
  }

  @Test
  public void testLPopNullable() {
    makeSureKeyNotExistes(() -> {
      redis.lpop(notExistsKey, this::handle);
    });
    await();
  }

  @Test
  public void testSPopNullable() {
    makeSureKeyNotExistes(() -> {
      redis.spop(notExistsKey, this::handle);
    });
    await();
  }

  private void makeSureKeyNotExistes(Runnable task) {
    redis.del(notExistsKey, r -> {
      if (r.failed()) {
        fail(r.cause());
      } else {
        task.run();
      }
    });
  }

  private void handle(AsyncResult<String> asyncResult) {
    if (asyncResult.failed()) {
      fail(asyncResult.cause());
    } else {
      assertNull(asyncResult.result());
      testComplete();
    }
  }
}
