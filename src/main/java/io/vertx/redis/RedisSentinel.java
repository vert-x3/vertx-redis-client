package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.client.RedisSentinelImpl;

import java.util.List;

@VertxGen
public interface RedisSentinel {

  static RedisSentinel create(Vertx vertx, List<SocketAddress> endpoints) {
    return create(
      vertx,
      endpoints,
      // by default redis works with TCP_NO_DELAY
      new NetClientOptions().setTcpNoDelay(true));
  }

  static RedisSentinel create(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    return new RedisSentinelImpl(vertx, endpoints, options);
  }

  /**
   * Create a client with the default master name of "mymaster".
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  default RedisSentinel open(Handler<AsyncResult<RedisConnection>> handler) {
    return open("mymaster", RedisRole.MASTER, handler);
  }

  /**
   * Create a client with the default master name of "mymaster".
   * @param  role       the role of the server to connect to
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  default RedisSentinel open(RedisRole role, Handler<AsyncResult<RedisConnection>> handler) {
    return open("mymaster", role, handler);
  }

  /**
   * Create a client
   * @param  masterName the name of the master. Defaults to mymaster
   * @param  role       the role of the server to connect to
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  RedisSentinel open(String masterName, RedisRole role, Handler<AsyncResult<RedisConnection>> handler);

  /**
   * Notify the user that the master node to this sentinel setup is switched.
   * The expected action to be performed by the user would be to restart all active connections.
   *
   * @param handler the asynchronous handler.
   * @return self
   */
  @Fluent
  RedisSentinel masterSwitchHandler(Handler<Void> handler);

  /**
   * Closes the client.
   */
  void close();
}
