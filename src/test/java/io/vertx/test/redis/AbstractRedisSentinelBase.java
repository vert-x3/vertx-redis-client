package io.vertx.test.redis;

import io.vertx.test.core.VertxTestBase;
import redis.embedded.RedisSentinel;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRedisSentinelBase extends VertxTestBase {
  public static final String MASTER_NAME = "mymaster";

  protected static final Integer DEFAULT_PORT = 6379;
  protected static final Integer DEFAULT_SENTINEL_PORT = 26739;
  // set of redis sentinels
  protected static final Map<Integer, RedisSentinel> sentinels = new ConcurrentHashMap<>();
  // set of redis instances
  protected static final Map<Integer, redis.embedded.RedisServer> instances = new ConcurrentHashMap<>();
  protected static String host = null;

  protected static void createRedisSentinelInstance(final Integer sentinelPort, final Integer masterPort)
    throws Exception {
    System.out.println("Creating redis sentinel on port: " + sentinelPort);

    redis.embedded.RedisSentinel sentinel = redis.embedded.RedisSentinel.builder()
      .port(sentinelPort)
      .setting(String.format("sentinel monitor %s %s %s 1", MASTER_NAME, host, DEFAULT_PORT))
      .setting(String.format("sentinel down-after-milliseconds %s 200", MASTER_NAME))
      .setting(String.format("sentinel failover-timeout %s 1000", MASTER_NAME))
      .build();

    sentinels.put(sentinelPort, sentinel);
    System.out.println("Created embedded redis server on port " + sentinelPort);
  }

  protected static void createRedisInstance(final Integer port, final String... settings) throws Exception {
    System.out.println("Creating redis server on port: " + port);
    RedisServerBuilder builder = RedisServer.builder().port(port);

    for (String setting : settings) {
      builder.setting(setting);
    }

    instances.put(port, builder.build());
    System.out.println("Created embedded redis server on port " + port);
  }

  protected static void createSlaveRedisInstance(final Integer port, final Integer masterPort,
                                                 final String... settings) throws Exception {
    System.out.println("Creating redis slave server on port: " + port);
    RedisServerBuilder builder = RedisServer.builder().port(port);

    for (String setting : settings) {
      builder.setting(setting);
    }

    builder.slaveOf(host, masterPort);

    instances.put(port, builder.build());
    System.out.println("Created embedded redis server on port " + port);
  }
}
