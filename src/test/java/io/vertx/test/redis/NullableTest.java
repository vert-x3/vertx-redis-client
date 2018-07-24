package io.vertx.test.redis;

import org.junit.Test;

public class NullableTest extends AbstractRedisClientBase {

  private static final String notExistsKey = "notExistsKey";
  @Test
  public void testGetNullable() {
    redis.del(notExistsKey, r -> {
      if (r.failed()) {
        fail(r.cause());
      } else {
        redis.get(notExistsKey, asyncResult -> {
          if (asyncResult.failed()) {
            fail(asyncResult.cause());
          } else {
            assertNull(asyncResult.result());
          }
        });
      }
    });
  }
  @Test
  public void testGetBinaryNullable() {
    redis.del(notExistsKey, r -> {
      if (r.failed()) {
        fail(r.cause());
      } else {
        redis.getBinary(notExistsKey, asyncResult -> {
          if (asyncResult.failed()) {
            fail(asyncResult.cause());
          } else {
            assertNull(asyncResult.result());
          }
        });
      }
    });
  }
}
