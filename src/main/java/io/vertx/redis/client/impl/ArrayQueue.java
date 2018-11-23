package io.vertx.redis.client.impl;

import java.util.NoSuchElementException;

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
   */
  <T> void offer(T value) {
    if (isFull()) {
      throw new IndexOutOfBoundsException();
    }
    back++;
    queue[back % queue.length] = value;
    cur++;
  }

  /**
   * Returns the first element in the queue.
   *
   * @return element at front of the queue, null if empty.
   */
  <T> T peek() {
    if (isEmpty()) {
      return null;
    } else {
      return (T) queue[front % queue.length];
    }
  }

  /**
   * Returns and removes the front element of the queuee. It works with wraparound.
   *
   * @return element at front of the queue
   * @throws NoSuchElementException if the queue is empty.
   */
  <T> T poll() {
    T e = peek();
    queue[front % queue.length] = null; // for garbage collection
    front++;
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
