/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;

import java.util.Objects;

final class ArrayQueue {

  private int
    cur,      // current number of elements
    front,    // front index
    back;     // back index

  private final Object[] queue;

  /**
   * Creates a new empty queue.
   */
  ArrayQueue(int capacity) {
    queue = new Object[capacity];
    back = -1;
    front = 0;
  }

  /**
   * Tests if the queue is logically empty.
   *
   * @return true if the queue is empty and false otherwise
   */
  boolean isEmpty() {
    return cur == 0;
  }

  /**
   * Puts a value into the back of the queue. It works with wraparound.
   * If the queue is full, it doubles its size.
   *
   * @param value the item to insert.
   * @throws IndexOutOfBoundsException if the queue is full.
   * @throws NullPointerException if the value is {@code null}
   */
  <T> void offer(T value) {
    Objects.requireNonNull(value, "'value' cannot be null");
    if (isFull()) {
      throw new IndexOutOfBoundsException();
    }
    back++;
    if (back == Integer.MAX_VALUE) {
      // ensure the indexes stay positive
      back %= queue.length;
    }
    queue[back % queue.length] = value;
    cur++;
  }

  /**
   * Returns the first element in the queue.
   *
   * @return element at front of the queue, null if empty.
   */
  @SuppressWarnings("unchecked")
  <T> @Nullable T peek() {
    if (isEmpty()) {
      return null;
    } else {
      return (T) queue[front % queue.length];
    }
  }

  /**
   * Returns and removes the front element of the queue. It works with wraparound.
   *
   * @return element at front of the queue or {@code null} if the queue is empty.
   */
  <T> @Nullable T poll() {
    if (isEmpty()) {
      return null;
    }
    T e = peek();
    queue[front % queue.length] = null; // for garbage collection
    front++;
    if (front == Integer.MAX_VALUE) {
      // ensure the indexes stay positive
      front %= queue.length;
    }
    cur--;
    return e;
  }

  int freeSlots() {
    return queue.length - cur;
  }

  /**
   * Tests if the queue is logically full
   */
  boolean isFull() {
    return cur == queue.length;
  }
}
