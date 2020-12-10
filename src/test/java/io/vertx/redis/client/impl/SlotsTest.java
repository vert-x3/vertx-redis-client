package io.vertx.redis.client.impl;

import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.IntegerType;
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.SimpleStringType;
import org.junit.Assert;
import org.junit.Test;

public class SlotsTest {

  private static final MultiType SLOTS_RESPONSE = MultiType.create(new Response[]{
    MultiType.create(new Response[]{ // 1st slot
      IntegerType.create(0L), // slot min
      IntegerType.create(8191L), // slot max
      MultiType.create(new Response[]{ // master
        SimpleStringType.create("redis-0001-001.redis.k9mgrw.euw1.cache.amazonaws.com"),
        IntegerType.create(7011L)
      }),
      MultiType.create(new Response[]{ // replica
        SimpleStringType.create("redis-0001-002.redis.k9mgrw.euw1.cache.amazonaws.com"),
        IntegerType.create(7012L)
      }),
    }),
    MultiType.create(new Response[]{ // 2nd slot
      IntegerType.create(8192L), // slot min
      IntegerType.create(16383L), // slot max
      MultiType.create(new Response[]{ // master
        SimpleStringType.create("redis-0002-001.redis.k9mgrw.euw1.cache.amazonaws.com"),
        IntegerType.create(7021L)
      }),
      MultiType.create(new Response[]{ // replica
        SimpleStringType.create("redis-0002-002.redis.k9mgrw.euw1.cache.amazonaws.com"),
        IntegerType.create(7022L)
      }),
    })
  });

  @Test
  public void testExtractsSlotsWithMastersAndReplicas() {
    final Slots slots = new Slots("redis://clustercfg.redis.k9mgrw.euw1.cache.amazonaws.com:7000", SLOTS_RESPONSE);
    Assert.assertEquals(2, slots.size());
    Assert.assertTrue(slots.contains("redis://redis-0001-001.redis.k9mgrw.euw1.cache.amazonaws.com:7011"));
    Assert.assertTrue(slots.contains("redis://redis-0001-002.redis.k9mgrw.euw1.cache.amazonaws.com:7012"));
    Assert.assertTrue(slots.contains("redis://redis-0002-001.redis.k9mgrw.euw1.cache.amazonaws.com:7021"));
    Assert.assertTrue(slots.contains("redis://redis-0002-002.redis.k9mgrw.euw1.cache.amazonaws.com:7022"));
  }

  @Test
  public void shouldPatchExtractedEndpointsWithUserinfo() {
    final Slots slots = new Slots("redis://:password@clustercfg.redis.k9mgrw.euw1.cache.amazonaws.com:7000", SLOTS_RESPONSE);
    Assert.assertEquals(2, slots.size());
    Assert.assertTrue(slots.contains("redis://:password@redis-0001-001.redis.k9mgrw.euw1.cache.amazonaws.com:7011"));
    Assert.assertTrue(slots.contains("redis://:password@redis-0001-002.redis.k9mgrw.euw1.cache.amazonaws.com:7012"));
    Assert.assertTrue(slots.contains("redis://:password@redis-0002-001.redis.k9mgrw.euw1.cache.amazonaws.com:7021"));
    Assert.assertTrue(slots.contains("redis://:password@redis-0002-002.redis.k9mgrw.euw1.cache.amazonaws.com:7022"));
  }
}
