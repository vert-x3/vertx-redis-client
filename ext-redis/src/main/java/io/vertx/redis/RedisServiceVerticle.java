package io.vertx.redis;

import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class RedisServiceVerticle implements Verticle {

    private RedisService service;

    protected Vertx vertx;

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        JsonObject config = vertx.context().config();
        // Create the service
        service = RedisService.create(vertx, config);
        service.start(asyncResult -> {
            if (asyncResult.succeeded()) {
                String address = config.getString("address", "vertx.redis");
                vertx.eventBus().registerService(service, address);
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
