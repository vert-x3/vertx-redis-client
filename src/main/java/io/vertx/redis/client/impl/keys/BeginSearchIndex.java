package io.vertx.redis.client.impl.keys;

import java.util.List;

public class BeginSearchIndex implements BeginSearch {

  private final int index;

  public BeginSearchIndex(int index) {
    this.index = index;
  }

  @Override
  public int begin(List<byte[]> args, int arity) {
    // TODO: validation
    return index - 1;
  }
}
