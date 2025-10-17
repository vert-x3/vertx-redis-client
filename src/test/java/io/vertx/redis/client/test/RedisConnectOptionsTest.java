package io.vertx.redis.client.test;

import io.vertx.redis.client.RedisClusterConnectOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.RedisReplicationConnectOptions;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.RedisSentinelConnectOptions;
import io.vertx.redis.client.RedisStandaloneConnectOptions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RedisConnectOptionsTest {
  @Test
  public void testRedisStandaloneConnectOptions() {
    RedisStandaloneConnectOptions options = new RedisStandaloneConnectOptions();
    assertTrue(options.isProtocolNegotiation()); // default value
  }

  @Test
  public void testRedisStandaloneConnectOptionsCopy() {
    RedisStandaloneConnectOptions previousOptions = new RedisStandaloneConnectOptions();
    previousOptions.setPassword("password");

    RedisStandaloneConnectOptions options = new RedisStandaloneConnectOptions(previousOptions);
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals("password", options.getPassword());
  }

  @Test
  public void testRedisStandaloneConnectOptionsFromJson() {
    RedisStandaloneConnectOptions previousOptions = new RedisStandaloneConnectOptions();
    previousOptions.setPassword("password");

    RedisStandaloneConnectOptions options = new RedisStandaloneConnectOptions(previousOptions.toJson());
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals("password", options.getPassword());
  }

  // ---

  @Test
  public void testRedisClusterConnectOptions() {
    RedisClusterConnectOptions options = new RedisClusterConnectOptions();
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals(RedisReplicas.NEVER, options.getUseReplicas()); // default value
    assertEquals(1000L, options.getTopologyCacheTTL());
    assertEquals(1000L, options.getHashSlotCacheTTL());
  }

  @Test
  public void testRedisClusterConnectOptionsCopy() {
    RedisClusterConnectOptions original = new RedisClusterConnectOptions();
    original.setPassword("password");
    original.setUseReplicas(RedisReplicas.ALWAYS);
    original.setHashSlotCacheTTL(1234L); // overwritten by the next call
    original.setTopologyCacheTTL(2500L);

    RedisClusterConnectOptions copy = new RedisClusterConnectOptions(original);
    assertTrue(copy.isProtocolNegotiation()); // default value
    assertEquals("password", copy.getPassword());
    assertEquals(RedisReplicas.ALWAYS, copy.getUseReplicas());
    assertEquals(2500L, copy.getTopologyCacheTTL());
    assertEquals(2500L, copy.getHashSlotCacheTTL());
  }

  @Test
  public void testRedisClusterConnectOptionsFromJson() {
    RedisClusterConnectOptions original = new RedisClusterConnectOptions();
    original.setPassword("password");
    original.setUseReplicas(RedisReplicas.SHARE);
    original.setHashSlotCacheTTL(2468L); // overwritten by the next call
    original.setTopologyCacheTTL(3333L);

    RedisClusterConnectOptions copy = new RedisClusterConnectOptions(original.toJson());
    assertTrue(copy.isProtocolNegotiation()); // default value
    assertEquals("password", copy.getPassword());
    assertEquals(RedisReplicas.SHARE, copy.getUseReplicas());
    assertEquals(3333L, copy.getTopologyCacheTTL());
    assertEquals(3333L, copy.getHashSlotCacheTTL());
  }

  // ---

  @Test
  public void testRedisReplicationConnectOptions() {
    RedisReplicationConnectOptions options = new RedisReplicationConnectOptions();
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals(RedisReplicas.NEVER, options.getUseReplicas()); // default value
  }

  @Test
  public void testRedisReplicationConnectOptionsCopy() {
    RedisReplicationConnectOptions original = new RedisReplicationConnectOptions();
    original.setPassword("password");
    original.setUseReplicas(RedisReplicas.ALWAYS);

    RedisReplicationConnectOptions copy = new RedisReplicationConnectOptions(original);
    assertTrue(copy.isProtocolNegotiation()); // default value
    assertEquals("password", copy.getPassword());
    assertEquals(RedisReplicas.ALWAYS, copy.getUseReplicas());
  }

  @Test
  public void testRedisReplicationConnectOptionsFromJson() {
    RedisReplicationConnectOptions original = new RedisReplicationConnectOptions();
    original.setPassword("password");
    original.setUseReplicas(RedisReplicas.SHARE);

    RedisReplicationConnectOptions copy = new RedisReplicationConnectOptions(original.toJson());
    assertTrue(copy.isProtocolNegotiation()); // default value
    assertEquals("password", copy.getPassword());
    assertEquals(RedisReplicas.SHARE, copy.getUseReplicas());
  }

  // ---

  @Test
  public void testRedisSentinelConnectOptions() {
    RedisSentinelConnectOptions options = new RedisSentinelConnectOptions();
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals(RedisRole.MASTER, options.getRole()); // default value
    assertEquals(1000L, options.getTopologyCacheTTL()); // default value
  }

  @Test
  public void testRedisSentinelConnectOptionsCopy() {
    RedisSentinelConnectOptions previousOptions = new RedisSentinelConnectOptions();
    previousOptions.setPassword("password");
    previousOptions.setRole(RedisRole.SENTINEL);
    previousOptions.setTopologyCacheTTL(2500L);

    RedisSentinelConnectOptions options = new RedisSentinelConnectOptions(previousOptions);
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals("password", options.getPassword());
    assertEquals(RedisRole.SENTINEL, options.getRole());
    assertEquals(2500L, options.getTopologyCacheTTL());
  }

  @Test
  public void testRedisSentinelConnectOptionsFromJson() {
    RedisSentinelConnectOptions previousOptions = new RedisSentinelConnectOptions();
    previousOptions.setPassword("password");
    previousOptions.setRole(RedisRole.REPLICA);
    previousOptions.setTopologyCacheTTL(3333L);

    RedisSentinelConnectOptions options = new RedisSentinelConnectOptions(previousOptions.toJson());
    assertTrue(options.isProtocolNegotiation()); // default value
    assertEquals("password", options.getPassword());
    assertEquals(RedisRole.REPLICA, options.getRole());
    assertEquals(3333L, options.getTopologyCacheTTL());
  }
}
