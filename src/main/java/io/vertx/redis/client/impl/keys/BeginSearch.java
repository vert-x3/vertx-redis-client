package io.vertx.redis.client.impl.keys;

import java.util.List;

public interface BeginSearch {

  static BeginSearch create() {
    return null;
  }

  int begin(List<byte[]> args, int arity);
}
