package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.sentinel.RedisSentinel;
import io.vertx.redis.utils.RedisCommandUtils;

import static io.vertx.redis.impl.RedisSentinelCommand.*;


/**
 * Implementation of {@link AbstractRedisSentinelClient}
 */
public class RedisSentinelClientImpl extends AbstractRedisSentinelClient {
  public RedisSentinelClientImpl(Vertx vertx, RedisOptions config) {
    super(vertx, config);
  }

  @Override
  public RedisSentinel masters(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MASTERS, null, handler);
    return this;
  }

  @Override
  public RedisSentinel master(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MASTER, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel slaves(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SLAVES, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel sentinels(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINELS, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel getMasterAddrByName(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GET_MASTER_ADDR_BY_NAME, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel reset(String pattern, Handler<AsyncResult<Void>> handler) {
    sendVoid(RESET, RedisCommandUtils.toPayload(pattern), handler);
    return this;
  }

  @Override
  public RedisSentinel failover(String name, Handler<AsyncResult<String>> handler) {
    sendString(FAILOVER, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel ckquorum(String name, Handler<AsyncResult<String>> handler) {
    sendString(CKQUORUM, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisSentinel flushConfig(Handler<AsyncResult<Void>> handler) {
    sendVoid(FLUSHCONFIG, null, handler);
    return this;
  }
}
