package io.vertx.redis.client.impl.keys;

import java.nio.charset.StandardCharsets;
import java.util.List;

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
  public int forEach(List<byte[]> args, int arity, int offset, KeyConsumer collector) {
    int keys = Integer.parseInt(new String(args.get(offset + keyNumIdx), StandardCharsets.US_ASCII));
    int i, stop;
    for (i = offset + firstKey, stop = 0; ++stop <= keys; i += keyStep) {
      collector.accept(offset, i, keyStep);
    }
    return i;
  }
}
