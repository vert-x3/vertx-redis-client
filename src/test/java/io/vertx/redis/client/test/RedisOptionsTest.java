package io.vertx.redis.client.test;

import io.vertx.redis.client.RedisOptions;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class RedisOptionsTest {

  @Test
  public void testRedisOptions() {
    assertEquals(6, new RedisOptions().getMaxPoolSize());
    assertEquals("redis://localhost:6379", new RedisOptions().getEndpoint());
    assertEquals(Collections.singletonList("redis://localhost:6379"), new RedisOptions().getEndpoints());
  }
}
