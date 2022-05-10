package io.vertx.redis.client.impl.keys;

import java.util.List;

public class FindKeysRange implements FindKeys {

  private final int lastKey;
  private final int keyStep;
  private final int limit;

  public FindKeysRange(int lastKey, int keyStep, int limit) {
    this.lastKey = lastKey;
    this.keyStep = keyStep;
    this.limit = limit;
  }

  @Override
  public int forEach(List<byte[]> args, int arity, int offset, KeyConsumer collector) {
    int stop;

    if (lastKey >= 0) {
      stop = offset + lastKey;
    } else {
      stop = args.size() + lastKey;
      if (lastKey == -1) {
        // if lastkey is has the value of -1, we use the limit to stop the search by a factor.
        // 0 and 1 mean no limit.
        // 2 means half of the remaining arguments, 3 means a third, and so on.
        if (limit > 1) {
          stop = offset + ((stop - offset) / limit);
        }
      }
    }

    int i;
    for (i = offset; i <= stop; i += keyStep) {
      collector.accept(offset, i, keyStep);
    }

    return i;
  }
}
