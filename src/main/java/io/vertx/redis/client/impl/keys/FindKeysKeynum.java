package io.vertx.redis.client.impl.keys;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.BiConsumer;

public class FindKeysKeynum implements FindKeys {

  final int keyNumIdx;
  final int firstKey;
  final int keyStep;

  public FindKeysKeynum(int keyNumIdx, int firstKey, int keyStep) {
    this.keyNumIdx = keyNumIdx;
    this.firstKey = firstKey;
    this.keyStep = keyStep;
  }

  @Override
  public int forEach(List<byte[]> args, int arity, int offset, BiConsumer<Integer, Integer> collector) {
    int keys = Integer.parseInt(new String(args.get(offset + keyNumIdx), StandardCharsets.US_ASCII));
    int i, stop;
    for (i = offset + firstKey, stop = 0; ++stop <= keys; i += keyStep) {
      collector.accept(i, keyStep);
    }
    return i;
  }
}
