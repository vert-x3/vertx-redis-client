package io.vertx.redis.client.impl.keys;

import java.util.List;

@FunctionalInterface
public interface BeginSearch {
  int begin(List<byte[]> args, int arity);
}
