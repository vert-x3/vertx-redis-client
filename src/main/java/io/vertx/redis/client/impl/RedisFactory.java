package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;

public final class RedisFactory {

  public static void createClient(Vertx vertx, SocketAddress address, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisClient.create(vertx, address, options, onCreate);
  }

  public static void createSentinelClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisSentinelClient.create(vertx, options, onCreate);
  }

  public static void createClusterClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisClusterClient.create(vertx, options, onCreate);
  }
}
