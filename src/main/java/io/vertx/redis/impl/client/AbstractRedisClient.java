package io.vertx.redis.impl.client;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractRedisClient {

  private final List<SocketAddress> endpoints;
  private final NetClientOptions netClientOptions;

  protected final Vertx vertx;
  protected final Context context;

  AbstractRedisClient(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions netClientOptions) {
    if (endpoints == null) {
      throw new IllegalStateException("endpoints cannot be null");
    }

    for (SocketAddress address : endpoints) {
      if (address == null) {
        throw new IllegalStateException("endpoint cannot be null");
      }
    }

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
    // keep a copy not the original object as we might re-order it
    this.endpoints = new ArrayList<>(endpoints);
    this.netClientOptions = netClientOptions;
  }

  AbstractRedisClient(Vertx vertx, SocketAddress endpoint, NetClientOptions netClientOptions) {

    if (endpoint == null) {
      throw new IllegalStateException("endpoint cannot be null");
    }

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

  SocketAddress endpoint() {
    return endpoints.get(0);
  }

  SocketAddress endpoint(int index) {
    return endpoints.get(index);
  }

  SocketAddress endpoint(int index, SocketAddress address) {
    return endpoints.set(index, address);
  }

  List<SocketAddress> endpoints() {
    return endpoints;
  }
}
