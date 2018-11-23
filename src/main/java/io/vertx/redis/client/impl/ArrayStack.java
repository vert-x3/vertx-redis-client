package io.vertx.redis.client.impl;

final class ArrayStack {

  private final Object[] stack;
  private int pointer;

  ArrayStack(int size) {
    stack = new Object[size];
    pointer = -1;
  }

  <T> void push(T item) {
    stack[++pointer] = item;
  }

  <T> T pop() {
    final Object o = stack[pointer];
    stack[pointer--] = null;
    return (T) o;
  }

  boolean empty() {
    return pointer == -1;
  }

  <T> T peek() {
    if (pointer == -1) {
      return null;
    }
    return (T) stack[pointer];
  }
}
