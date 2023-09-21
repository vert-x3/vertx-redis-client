package io.vertx.redis.client.test;

import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisRole;
import java.util.ArrayList;
import java.util.List;
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

  @Test
  public void testOptionsCopy() {
    List<String> endpoints = new ArrayList<>(3);
    endpoints.add("redis://localhost:123");
    endpoints.add("redis://localhost:124");
    endpoints.add("redis://localhost:125");

    // Set values for which there is no default to ensure they are copied correctly
    RedisOptions original = new RedisOptions()
      .setEndpoints(endpoints)
      .setMasterName("someOtherMaster")
      .setRole(RedisRole.SENTINEL)
      .setPassword("myPassword")
      .setTracingPolicy(TracingPolicy.ALWAYS);

    RedisOptions copy = new RedisOptions(original);

    assertEquals(original.toJson(), copy.toJson());
  }
}
