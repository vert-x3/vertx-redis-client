package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;

/**
 * How the Redis multi-node topology should be obtained.
 * <p>
 * This is only meaningful in case of a {@linkplain RedisClientType#REPLICATION replication}
 * Redis client. In case of a {@linkplain RedisClientType#CLUSTER cluster} and
 * {@linkplain RedisClientType#SENTINEL sentinel} Redis client, topology is currently
 * always discovered automatically and the topology mode is ignored.
 * </p>
 */
@VertxGen
public enum RedisTopology {
  /**
   * Redis topology is discovered automatically from the first node that is capable of providing
   * the necessary information.
   */
  DISCOVER,

  /**
   * Redis topology is configured statically. The user must ensure that the configuration is correct.
   * <p>
   * In case of a {@linkplain RedisClientType#REPLICATION replication} Redis client, the first
   * node in the list is considered a <em>master</em> and the remaining nodes in the list
   * are considered <em>replicas</em>.
   * </p>
   * <p>
   * In case of a {@linkplain RedisClientType#CLUSTER cluster} and {@linkplain RedisClientType#SENTINEL sentinel}
   * Redis client, static topology configuration is currently not meaningful and is ignored.
   * </p>
   */
  STATIC,
}
