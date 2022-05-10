package io.vertx.redis.client.impl.keys;

@FunctionalInterface
public interface KeyConsumer {
  void accept(int begin, int keyIdx, int keyStep);
}
