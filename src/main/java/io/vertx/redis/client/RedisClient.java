package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.impl.RedisClientImpl;

/**
 * A simple Redis client.
 */
@VertxGen
public interface RedisClient {

  /**
   * Connect to redis, the {@code onConnect} will get the {@link RedisClient} instance.
   */
  static void connect(Vertx vertx, SocketAddress address, Handler<AsyncResult<RedisClient>> onConnect) {
    connect(vertx, address, new NetClientOptions().setTcpKeepAlive(true).setTcpNoDelay(true), onConnect);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link RedisClient} instance.
   */
  static void connect(Vertx vertx, SocketAddress address, NetClientOptions options, Handler<AsyncResult<RedisClient>> onConnect) {
    RedisClientImpl.connect(vertx, address, options, onConnect);
  }

  void send(Request command, Handler<AsyncResult<Response>> onSend);
}
