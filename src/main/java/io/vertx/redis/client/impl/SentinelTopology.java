package io.vertx.redis.client.impl;

import java.util.Random;

class SentinelTopology {
  // we need some randomness, it doesn't need to be cryptographically secure
  private static final Random RANDOM = new Random();

  private final RedisURI[] sentinels;
  private final RedisURI master;
  private final RedisURI[] replicas;

  public SentinelTopology(RedisURI[] sentinels, RedisURI master, RedisURI[] replicas) {
    this.sentinels = sentinels;
    this.master = master;
    this.replicas = replicas;
  }

  public RedisURI getRandomSentinel() {
    return sentinels[RANDOM.nextInt(sentinels.length)];
  }

  public RedisURI getMaster() {
    return master;
  }

  public RedisURI getRandomReplica() {
    return replicas[RANDOM.nextInt(replicas.length)];
  }
}
