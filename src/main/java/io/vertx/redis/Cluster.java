package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.ClusterImpl;

import java.util.List;

import static io.vertx.redis.ClusterSlaves.NEVER;

@VertxGen
public interface Cluster extends Redis {

  static Cluster create(Vertx vertx, List<SocketAddress> endpoints) {
    return new ClusterImpl(vertx, endpoints, new NetClientOptions(), NEVER);
  }

  static Cluster create(Vertx vertx, List<SocketAddress> endpoints, ClusterSlaves slaves) {
    return new ClusterImpl(vertx, endpoints, new NetClientOptions(), slaves);
  }

  static Cluster create(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    return new ClusterImpl(vertx, endpoints, options, NEVER);
  }

  static Cluster create(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options, ClusterSlaves slaves) {
    return new ClusterImpl(vertx, endpoints, options, slaves);
  }
}
