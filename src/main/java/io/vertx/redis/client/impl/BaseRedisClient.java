package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;

public abstract class BaseRedisClient implements Redis {

  protected final VertxInternal vertx;
  protected final RedisConnectionManager connectionManager;

  public BaseRedisClient(Vertx vertx, RedisOptions options) {
    this.vertx = (VertxInternal) vertx;
    this.connectionManager = new RedisConnectionManager(this.vertx, options);
    this.connectionManager.start();
  }

  @Override
  public void close() {
    this.connectionManager.close();
  }

  @Override
  public Future<@Nullable Response> send(Request command) {
    final Promise<Response> promise = vertx.promise();

    if (command.command().isPubSub()) {
      // mixing pubSub cannot be used on a one-shot operation
      promise.fail("PubSub command in connection-less mode not allowed");
      return promise.future();
    }

    connect()
      .onFailure(promise::fail)
      .onSuccess(conn -> conn.send(command)
        .onComplete(send -> {
          try {
            promise.handle(send);
          } finally {
            // regardless of the result, return the connection to the pool
            conn.close();
          }
        }));

    return promise.future();
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    final Promise<List<Response>> promise = vertx.promise();

    if (commands.isEmpty()) {
      promise.complete();
    } else {
      for (Request req : commands) {
        if (req.command().isPubSub()) {
          // mixing pubSub cannot be used on a one-shot operation
          promise.fail("PubSub command in connection-less batch not allowed");
          return promise.future();
        }
      }

      connect()
        .onFailure(promise::fail)
        .onSuccess(conn -> {
          conn.batch(commands)
            .onSuccess(responses -> {
              try {
                promise.complete(responses);
              } finally {
                // regardless of the result, return the connection to the pool
                conn.close();
              }
            })
            .onFailure(err -> {
              try {
                promise.fail(err);
              } finally {
                // regardless of the result, return the connection to the pool
                conn.close();
              }
            });
        });
    }

    return promise.future();
  }
}
