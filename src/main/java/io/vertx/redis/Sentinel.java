package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.SentinelImpl;

import java.util.List;

@VertxGen
public interface Sentinel {

  static Sentinel create(Vertx vertx, List<SocketAddress> endpoints) {
    return new SentinelImpl(
      vertx,
      endpoints,
      // by default redis works with TCP_NO_DELAY
      new NetClientOptions().setTcpNoDelay(true));
  }

  static Sentinel create(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    return new SentinelImpl(vertx, endpoints, options);
  }

  /**
   * Create a client with the default master name of "mymaster".
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  default Sentinel open(Handler<AsyncResult<Redis>> handler) {
    return open("mymaster", Role.MASTER, handler);
  }

  /**
   * Create a client with the default master name of "mymaster".
   * @param  role       the role of the server to connect to
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  default Sentinel open(Role role, Handler<AsyncResult<Redis>> handler) {
    return open("mymaster", role, handler);
  }

  /**
   * Create a client
   * @param  masterName the name of the master. Defaults to mymaster
   * @param  role       the role of the server to connect to
   * @return            the Redis client for the desired endpoint
   */
  @Fluent
  Sentinel open(String masterName, Role role, Handler<AsyncResult<Redis>> handler);

  /**
   * Closes the underlying net client.
   */
  void close();
}
