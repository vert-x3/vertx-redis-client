package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.client.RedisClusterImpl;

import java.util.List;

@VertxGen
public interface RedisCluster {

  static RedisCluster create(Vertx vertx, List<SocketAddress> endpoints) {
    return create(vertx, endpoints, new NetClientOptions().setTcpNoDelay(true));
  }

  static RedisCluster create(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    return new RedisClusterImpl(vertx, endpoints, options);
  }

  /**
   * Create a cluster client that never uses slaves.
   * @param handler async result status of the operation
   * @return self
   */
  @Fluent
  default RedisCluster open(Handler<AsyncResult<RedisConnection>> handler) {
    return open(RedisSlaves.NEVER, handler);
  }

  /**
   * Create a cluster client that never uses slaves.
   * @param handler async result status of the operation
   * @return self
   */
  @Fluent
  RedisCluster open(RedisSlaves slaves, Handler<AsyncResult<RedisConnection>> handler);

  /**
   * Closes the client.
   */
  void close();
}
