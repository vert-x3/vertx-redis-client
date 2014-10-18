package io.vertx.redis.impl;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisService;
import io.vertx.redis.spi.RedisServiceFactory;

public class RedisServiceFactoryImpl implements RedisServiceFactory {

    @Override
    public RedisService create(Vertx vertx, JsonObject config) {
        return new RedisServiceImpl(vertx, config);
    }

    @Override
    public RedisService createEventBusProxy(Vertx vertx, String address) {
        return vertx.eventBus().createProxy(RedisService.class, address);
    }
}
