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

public final class RESPEncoder {

  // precache -1
  private static final byte[] NEG_ONE = convert(-1);

  // Cache 256 number conversions. That should cover a huge
  // percentage of numbers passed over the wire.
  private static final int NUM_MAP_LENGTH = 256;
  private static final byte[][] NUM_MAP = new byte[NUM_MAP_LENGTH][];

  static {
    for (int i = 0; i < NUM_MAP_LENGTH; i++) {
      NUM_MAP[i] = convert(i);
    }
  }

  /**
   * Convert the given long value to a byte[]
   */
  private static byte[] convert(long value) {
    boolean negative = value < 0;
    // Checked javadoc: If the argument is equal to 10^n for integer n, then the result is n.
    // Also, if negative, leave another slot for the sign.
    long abs = Math.abs(value);
    int index = (value == 0 ? 0 : (int) Math.log10(abs)) + (negative ? 2 : 1);
    byte[] bytes = new byte[index];
    // Put the sign in the slot we saved
    if (negative) bytes[0] = '-';
    long next = abs;
    while ((next /= 10) > 0) {
      bytes[--index] = (byte) ('0' + (abs % 10));
      abs = next;
    }
    bytes[--index] = (byte) ('0' + abs);
    return bytes;
  }

  // Optimized for the direct to ASCII bytes case
  // About 5x faster than using Long.toString.bytes
  public static byte[] numToBytes(long value) {
    if (value >= 0 && value < NUM_MAP_LENGTH) {
      int index = (int) value;
      return NUM_MAP[index];
    } else if (value == -1) {
      return NEG_ONE;
    }
    return convert(value);
  }
}
