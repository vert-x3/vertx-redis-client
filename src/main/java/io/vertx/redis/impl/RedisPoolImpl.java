package io.vertx.redis.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.RedisPool;
import io.vertx.redis.RedisPoolOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.impl.pool.AsyncConnectionPool;

import java.util.List;

public class RedisPoolImpl extends AsyncConnectionPool implements RedisPool {

  public RedisPoolImpl(Vertx vertx, RedisPoolOptions redisPoolOptions) {
    super(vertx, redisPoolOptions);
  }

  public RedisPoolImpl(Vertx vertx, RedisPoolOptions redisPoolOptions, String poolName) {
    super(vertx, redisPoolOptions, poolName);
  }

  private Handler<Throwable> exceptionHandler;
  private Handler<Void> endHandler;
  private Handler<Response> handler;

  @Override
  public RedisConnection send(Request command, Handler<AsyncResult<@Nullable Response>> callback) {
    take(take -> {
      if (take.succeeded()) {
        final RedisConnection conn = take.result();
        // setup the handlers
        conn.exceptionHandler(ex -> {
          expire(conn);
          if (exceptionHandler != null) {
            exceptionHandler.handle(ex);
          }
        });
        conn.endHandler(v -> {
          expire(conn);
          if (endHandler != null) {
            endHandler.handle(v);
          }
        });
        conn.handler(resp -> {
          if (handler != null) {
            handler.handle(resp);
          }
        });

        conn.send(command, onSend -> {
          // cleanup handlers
          conn.exceptionHandler(null);
          conn.endHandler(null);
          conn.handler(null);
          // return to the pool
          giveBack(conn);
          // callback
          callback.handle(onSend);
        });
      } else {
        callback.handle(Future.failedFuture(take.cause()));
      }
    });
    return this;
  }

  @Override
  public RedisConnection batch(List<Request> commands, Handler<AsyncResult<List<@Nullable Response>>> callback) {
    take(take -> {
      if (take.succeeded()) {
        final RedisConnection conn = take.result();
        // setup the handlers
        conn.exceptionHandler(ex -> {
          expire(conn);
          if (exceptionHandler != null) {
            exceptionHandler.handle(ex);
          }
        });
        conn.endHandler(v -> {
          expire(conn);
          if (endHandler != null) {
            endHandler.handle(v);
          }
        });
        conn.handler(resp -> {
          if (handler != null) {
            handler.handle(resp);
          }
        });

        conn.batch(commands, onBatch -> {
          // cleanup handlers
          conn.exceptionHandler(null);
          conn.endHandler(null);
          conn.handler(null);
          // return to the pool
          giveBack(conn);
          // callback
          callback.handle(onBatch);
        });
      } else {
        callback.handle(Future.failedFuture(take.cause()));
      }
    });
    return this;
  }

  @Override
  public boolean isConnected() {
    return getPoolSize() > 0;
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    this.exceptionHandler = handler;
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    this.handler = handler;
    return this;
  }

  @Override
  public RedisConnection pause() {
    throw new UnsupportedOperationException("Cannot pause without a explicit connection.");
  }

  @Override
  public RedisConnection resume() {
    throw new UnsupportedOperationException("Cannot resume without a explicit connection.");
  }

  @Override
  public RedisConnection fetch(long amount) {
    throw new UnsupportedOperationException("Cannot fetch without a explicit connection.");
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> endHandler) {
    this.endHandler = endHandler;
    return this;
  }

  @Override
  public SocketAddress socketAddress() {
    throw new UnsupportedOperationException("Cannot get socket address without a explicit connection.");
  }
}
