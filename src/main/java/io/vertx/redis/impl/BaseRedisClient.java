package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisOptions;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class BaseRedisClient<C> {

  final EventBus eb;
  final RedisSubscriptions subscriptions;
  final String encoding;
  final Charset charset;
  final Charset binaryCharset;
  final String baseAddress;

  // we need 2 connections, one for normal commands and a second in case we do pub/sub
  final RedisConnection redis;
  final RedisConnection pubsub;

  BaseRedisClient(Vertx vertx, RedisOptions config) {
    this.eb = vertx.eventBus();
    this.encoding = config.getEncoding();
    this.charset = Charset.forName(encoding);
    this.binaryCharset = Charset.forName("iso-8859-1");
    this.baseAddress = config.getAddress();

    subscriptions = new RedisSubscriptions(vertx);

    redis = new RedisConnection(vertx, config, null);
    pubsub = new RedisConnection(vertx, config, subscriptions);
  }

  public synchronized void close(Handler<AsyncResult<Void>> handler) {
    // this is a special case it should sent the message QUIT and then close the sockets
    final AtomicInteger cnt = new AtomicInteger(0);

    final Handler<AsyncResult<Void>> cb = v -> {
      if (cnt.incrementAndGet() == 2) {
        handler.handle(Future.succeededFuture());
      }
    };

    redis.disconnect(cb);
    pubsub.disconnect(cb);
  }

  final void sendString(final C command, final List<?> args, final Handler<AsyncResult<String>> resultHandler) {
    send(command, args, String.class, false, resultHandler);
  }

  final void sendLong(final C command, final List<?> args, final Handler<AsyncResult<Long>> resultHandler) {
    send(command, args, Long.class, false, resultHandler);
  }

  final void sendVoid(final C command, final List<?> args, final Handler<AsyncResult<Void>> resultHandler) {
    send(command, args, Void.class, false, resultHandler);
  }

  final void sendJsonArray(final C command, final List<?> args, final Handler<AsyncResult<JsonArray>> resultHandler) {
    send(command, args, JsonArray.class, false, resultHandler);
  }

  final void sendJsonObject(final C command, final List<?> args, final Handler<AsyncResult<JsonObject>> resultHandler) {
    send(command, args, JsonObject.class, false, resultHandler);
  }

  abstract <R> void send(final C command, final List<?> redisArgs, final Class<R> returnType,
                         final boolean binary,
                         final Handler<AsyncResult<R>> resultHandler);
}
