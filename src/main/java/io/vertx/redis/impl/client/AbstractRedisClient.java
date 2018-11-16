package io.vertx.redis.impl.client;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;

import java.util.Collections;
import java.util.List;

public class AbstractRedisClient {

  final List<SocketAddress> endpoints;
  private final NetClientOptions netClientOptions;

  protected final Vertx vertx;
  protected final Context context;

  AbstractRedisClient(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions netClientOptions) {
    // Make sure we have an event loop context for serializability of the commands
    Context ctx = Vertx.currentContext();
    if (ctx == null) {
      ctx = vertx.getOrCreateContext();
    } else if (!ctx.isEventLoopContext()) {
      VertxInternal vi = (VertxInternal) vertx;
      ctx = vi.createEventLoopContext(null, null, new JsonObject(), Thread.currentThread().getContextClassLoader());
    }

    this.vertx = vertx;
    this.context = ctx;
    this.endpoints = endpoints;
    this.netClientOptions = netClientOptions;
  }

  AbstractRedisClient(Vertx vertx, SocketAddress endpoint, NetClientOptions netClientOptions) {
    // Make sure we have an event loop context for serializability of the commands
    Context ctx = Vertx.currentContext();
    if (ctx == null) {
      ctx = vertx.getOrCreateContext();
    } else if (!ctx.isEventLoopContext()) {
      VertxInternal vi = (VertxInternal) vertx;
      ctx = vi.createEventLoopContext(null, null, new JsonObject(), Thread.currentThread().getContextClassLoader());
    }

    this.vertx = vertx;
    this.context = ctx;
    this.endpoints = Collections.singletonList(endpoint);
    this.netClientOptions = netClientOptions;
  }

  NetClient createNetClient() {
    return vertx.createNetClient(netClientOptions);
  }
}
