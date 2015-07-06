package io.vertx.test.redis;

import org.junit.Test;

public class ConnectionManagementTest extends AbstractRedisClientBase {

  @Test
  public void testQuit() {
    redis.close(v -> {
      testComplete();
    });
    await();
  }

  @Test
  public void testCommandAfterQuit() {
    redis.close(v -> {
      // wait a bit to make sure the socket is closed
      vertx.setTimer(200l, v0 -> {
        redis.info(res -> {
          assertTrue(res.succeeded());
          assertNotNull(res.result());
          testComplete();
        });
      });
    });
    await();
  }
}
