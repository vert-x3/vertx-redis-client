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

  @SuppressWarnings("unchecked")
  <T> T pop() {
    final Object o = stack[pointer];
    stack[pointer--] = null;
    return (T) o;
  }

  boolean empty() {
    return pointer == -1;
  }

  @SuppressWarnings("unchecked")
  <T> @Nullable T peek() {
    if (pointer == -1) {
      return null;
    }
    return (T) stack[pointer];
  }

  int size() {
    return pointer + 1;
  }
}
