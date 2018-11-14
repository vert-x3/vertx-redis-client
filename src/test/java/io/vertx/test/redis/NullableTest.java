package io.vertx.test.redis;

import io.vertx.core.AsyncResult;
import org.junit.Test;

public class NullableTest extends AbstractRedisClientBase {

  private static final String notExistsKey = "notExistsKey";

  @Test
  public void testGetNullable() {
    makeSureKeyNotExists(() -> {
      redis.get(notExistsKey, this::handle);
    });
    await();
  }

  @Test
  public void testGetBinaryNullable() {
    makeSureKeyNotExists(() -> {
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
    makeSureKeyNotExists(() -> {
      redis.getset(notExistsKey, "value", this::handle);
    });
    await();
  }

  @Test
  public void testHGetNullable() {
    makeSureKeyNotExists(() -> {
      String notExistsField = "notExistsField";
      redis.hget(notExistsKey, notExistsField, this::handle);
    });
    await();
  }

  @Test
  public void testLPopNullable() {
    makeSureKeyNotExists(() -> {
      redis.lpop(notExistsKey, this::handle);
    });
    await();
  }

  @Test
  public void testSPopNullable() {
    makeSureKeyNotExists(() -> {
      redis.spop(notExistsKey, this::handle);
    });
    await();
  }

  private void makeSureKeyNotExists(Runnable task) {
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
