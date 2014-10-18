package io.vertx.redis.spi;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisService;

public interface RedisServiceFactory {

  RedisService create(Vertx vertx, JsonObject config);

  RedisService createEventBusProxy(Vertx vertx, String address);
}
