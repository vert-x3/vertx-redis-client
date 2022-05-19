package io.vertx.redis.client.impl.keys;

import java.util.List;

public class BeginSearchIndex implements BeginSearch {

  private final int index;

  public BeginSearchIndex(int index) {
    this.index = index;
  }

  @Override
  public int begin(List<byte[]> args, int arity) {
    final int result = index - 1;
    if (result >= args.size()) {
      return -1;
    }
    return result;
  }
}
