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
      redis.info(res -> {
        assertTrue(res.succeeded());
        System.out.println(res.result().encodePrettily());
        testComplete();
      });
    });
    await();
  }
}
