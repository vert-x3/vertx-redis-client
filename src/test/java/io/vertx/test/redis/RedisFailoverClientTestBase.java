package io.vertx.test.redis;

import io.vertx.redis.RedisClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import redis.embedded.RedisServer;

import java.net.InetAddress;
import java.util.Map;

public abstract class RedisFailoverClientTestBase extends AbstractRedisSentinelBase {

  protected RedisClient redis;

  @BeforeClass
  static public void startRedis() throws Exception {
    //determine the host address
    host = InetAddress.getLocalHost().getHostAddress();

        /*
         * Setup
         *
         * 3 Sentinels (quorum 2)
         *
         * S1, S2, S3
         *
         * 2 redis instances
         *
         * Sentinel S1 -> Redis Master M
         * Sentinel S2 -> Redis Master M
         * Sentinel S3 -> Redis Master M
         *
         * Redis Master M -> Redis Slave S
         */

    // create Redis Master M
    createRedisInstance(DEFAULT_PORT);
    instances.get(DEFAULT_PORT).start();

    // create Redis Slave S
    createSlaveRedisInstance(DEFAULT_PORT + 1, DEFAULT_PORT);
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
}
