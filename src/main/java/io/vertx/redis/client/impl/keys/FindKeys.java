package io.vertx.redis.client.impl.keys;

import java.util.List;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface FindKeys {

  int forEach(List<byte[]> args, int arity, int offset, BiConsumer<Integer, Integer> collector);
}
