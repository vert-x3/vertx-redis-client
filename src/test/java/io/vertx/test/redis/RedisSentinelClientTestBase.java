package io.vertx.test.redis;

import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.sentinel.RedisSentinel;
import io.vertx.redis.utils.JsonUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.net.InetAddress;
import java.util.Map;


public abstract class RedisSentinelClientTestBase extends AbstractRedisSentinelBase {
  protected RedisSentinel redisSentinel;

  @BeforeClass
  static public void startRedis() throws Exception {

    /**
     * Test setup is
     *
     * Sentinel S1 -> Redis Master M
     * Sentinel S2 -> Redis Master M
     * Sentinel S3 -> Redis Master M
     *
     * Redis Master M -> Redis Slave S
     */

    // redis slave <-> master sync doesn't work with 127.0.0.1/localhost.
    // hence getting ip address.
    host = InetAddress.getLocalHost().getHostAddress();

    // create Redis Master
    createRedisInstance(DEFAULT_PORT, "loglevel debug");
    instances.get(DEFAULT_PORT).start();

    // create Redis Slave
    createSlaveRedisInstance(DEFAULT_PORT + 1, DEFAULT_PORT, "loglevel debug");
    instances.get(DEFAULT_PORT + 1).start();

    // create sentinels
    for (int i = 0; i < 3; i++) {
      createRedisSentinelInstance(DEFAULT_SENTINEL_PORT + i, DEFAULT_PORT);
      sentinels.get(DEFAULT_SENTINEL_PORT + i).start();
    }

  }

  @AfterClass
  static public void stopRedis() throws Exception {
    // stop sentinels
    for (Map.Entry<Integer, redis.embedded.RedisSentinel> entry : sentinels.entrySet()) {
      if (entry != null) {
        entry.getValue().stop();
      }
    }

    // stop redis instances
    for (Map.Entry<Integer, RedisServer> entry : instances.entrySet()) {
      if (entry != null) {
        entry.getValue().stop();
      }
    }
  }

  protected RedisOptions getSentinelConfig() {
    RedisOptions config = new RedisOptions();
    config.setHost(host);
    config.setPort(DEFAULT_SENTINEL_PORT);
    return config;
  }

  @Test
  @Ignore //embedded redis doesn't support flushConfig command
  public void testFlushConfig() {
    redisSentinel.flushConfig(reply -> {
      assertTrue(reply.succeeded());
      if (reply.succeeded()) {
        reply.result();
        testComplete();
      }
    });
    await();
  }

  @Test
  public void testMasters() {
    redisSentinel.masters(reply -> {
      assertTrue(reply.succeeded());
      assertEquals(1, reply.result().size());
      JsonObject result = JsonUtils.convertToJsonObject(reply.result().getJsonArray(0));
      assertEquals(MASTER_NAME, result.getString("name"));
      assertEquals(host, result.getString("ip"));
      assertEquals(String.valueOf(DEFAULT_PORT), result.getString("port"));
      testComplete();
    });
    await();
  }

  @Test
  public void testMaster() {
    redisSentinel.master(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testSlaves() {
    redisSentinel.slaves(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testSentinels() {
    redisSentinel.sentinels(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testMasterAddrByName() {
    redisSentinel.getMasterAddrByName(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testReset() {
    redisSentinel.reset(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  @Ignore // ignoring as test is flaky, sentinel does not discovery slave
  public void testFailover() {
    redisSentinel.failover(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      assertEquals("OK", reply.result().toString());
      testComplete();
    });
    await();
  }

  @Test
  @Ignore // ignoring as test is flaky, sentinel does not discovery slave or is subjected to timing
  public void testCkquorum() {
    redisSentinel.failover(MASTER_NAME, reply -> {
      assertTrue(reply.succeeded());
      assertEquals("OK", reply.result().toString());
      testComplete();
    });
    await();
  }

}
