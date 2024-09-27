package io.vertx.redis.client;

import java.util.function.Supplier;

class ConstantSupplier<T> implements Supplier<T> {
  private final T value;

  ConstantSupplier(T value) {
    this.value = value;
  }

  @Override
  public T get() {
    return value;
  }
}
