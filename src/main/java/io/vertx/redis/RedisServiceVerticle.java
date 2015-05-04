package io.vertx.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ProxyHelper;

public class RedisServiceVerticle extends AbstractVerticle {

  private RedisClient service;

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    // Create the service
    service = RedisClient.create(vertx, config());
    service.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        String address = config().getString("address", "vertx.redis");
        ProxyHelper.registerService(RedisClient.class, vertx, service, address);
        startFuture.complete();
      } else {
        startFuture.fail(asyncResult.cause());
      }
    });
  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    service.stop(asyncResult -> {
      if (asyncResult.succeeded()) {
        stopFuture.complete();
      } else {
        stopFuture.fail(asyncResult.cause());
      }
    });
  }
}
