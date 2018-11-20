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
public interface RedisCluster extends RedisConnection {

  static RedisCluster create(Vertx vertx, List<SocketAddress> endpoints) {
    return create(vertx, endpoints, new NetClientOptions().setTcpKeepAlive(true).setTcpNoDelay(true));
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
  default RedisCluster connect(Handler<AsyncResult<Void>> handler) {
    return connect(RedisSlaves.NEVER, handler);
  }

  /**
   * Create a cluster client that never uses slaves.
   * @param handler async result status of the operation
   * @return self
   */
  @Fluent
  RedisCluster connect(RedisSlaves slaves, Handler<AsyncResult<Void>> handler);
}
