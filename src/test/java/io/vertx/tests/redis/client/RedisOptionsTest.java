package io.vertx.tests.redis.client;

import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisRole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisOptionsTest {

  @Test
  public void testRedisOptions() {
    RedisOptions options = new RedisOptions();

    assertEquals(RedisClientType.STANDALONE, options.getType()); // default value
    assertEquals(6, options.getMaxPoolSize()); // default value
    assertEquals("redis://localhost:6379", options.getEndpoint()); // default value
    assertEquals(Collections.singletonList("redis://localhost:6379"), options.getEndpoints()); // default value
    assertEquals(1000L, options.getTopologyCacheTTL()); // default value
    assertEquals(1000L, options.getHashSlotCacheTTL()); // default value
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
      .setTracingPolicy(TracingPolicy.ALWAYS)
      .setHashSlotCacheTTL(1234L) // overwritten by the next call
      .setTopologyCacheTTL(2500L);

    RedisOptions copy = new RedisOptions(original);

    assertEquals(RedisClientType.STANDALONE, copy.getType()); // default value
    assertEquals(6, copy.getMaxPoolSize()); // default value
    assertEquals(endpoints, copy.getEndpoints());
    assertEquals("someOtherMaster", copy.getMasterName());
    assertEquals(RedisRole.SENTINEL, copy.getRole());
    assertEquals("myPassword", copy.getPassword());
    assertEquals(TracingPolicy.ALWAYS, copy.getTracingPolicy());
    assertEquals(2500L, copy.getTopologyCacheTTL());
    assertEquals(2500L, copy.getHashSlotCacheTTL());
  }

  @Test
  public void testFromJsonInstance() {
    List<String> endpoints = new ArrayList<>(3);
    endpoints.add("redis://localhost:123");
    endpoints.add("redis://localhost:124");
    endpoints.add("redis://localhost:125");

    RedisOptions original = new RedisOptions()
      .setEndpoints(endpoints)
      .setMasterName("someOtherMaster")
      .setRole(RedisRole.SENTINEL)
      .setPassword("myPassword")
      .setTracingPolicy(TracingPolicy.ALWAYS)
      .setHashSlotCacheTTL(2468L) // overwritten by the next call
      .setTopologyCacheTTL(3333L);

    RedisOptions copy = new RedisOptions(original.toJson());

    assertEquals(RedisClientType.STANDALONE, copy.getType()); // default value
    assertEquals(6, copy.getMaxPoolSize()); // default value
    assertEquals(endpoints, copy.getEndpoints());
    assertEquals("someOtherMaster", copy.getMasterName());
    assertEquals(RedisRole.SENTINEL, copy.getRole());
    assertEquals("myPassword", copy.getPassword());
    assertEquals(TracingPolicy.ALWAYS, copy.getTracingPolicy());
    assertEquals(3333L, copy.getTopologyCacheTTL());
    assertEquals(3333L, copy.getHashSlotCacheTTL());
  }

}
