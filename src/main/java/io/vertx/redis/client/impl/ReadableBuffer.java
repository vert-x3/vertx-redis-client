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

import io.vertx.core.buffer.Buffer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

final class ReadableBuffer {

  enum NumericType {
    INTEGER,
    DECIMAL,
    BIGINTEGER
  }

  // limit of integer parsing before overflowing
  private static final long MAX_INTEGER_DIV_10 = Long.MAX_VALUE / 10;
  private static final int MARK_WATERMARK = Integer.getInteger("io.vertx.redis.parser.watermark", 512 * 1024);

  private Buffer buffer;
  private int offset;

  private int mark;

  void append(Buffer chunk) {
    // either the buffer is null or all read
    if (buffer == null || Math.min(mark, offset) == buffer.length()) {
      buffer = chunk;
      offset = 0;
      return;
    }

    // slice the buffer discarding the read bytes
    if (
      // the offset (read operations) must be further than the last checkpoint
      offset >= mark &&
        // there must be already read more than water mark
        mark > MARK_WATERMARK &&
        // and there are more bytes to read already
        buffer.length() > mark) {

      // clean up when there's too much data
      buffer = buffer.getBuffer(mark, buffer.length());
      offset -= mark;
      mark = 0;
    }

    buffer.appendBuffer(chunk);
  }

  int findLineEnd() {
    int index = -1;
    for (int i = offset; i < buffer.length(); i++) {
      if (buffer.getByte(i) == '\n') {
        index = i;
        break;
      }
    }

    return (index > 0 && buffer.getByte(index - 1) == '\r') ? index : -1;
  }

  long readLong(int end) {
    long value = 0;

    boolean negative = buffer.getByte(this.offset) == '-';

    int offset = negative ? this.offset + 1 : this.offset;

    while (offset < end - 1) {
      if (value > MAX_INTEGER_DIV_10) {
        throw new ArithmeticException("Overflow");
      }

      int digit = buffer.getByte(offset++) - '0';

      if (digit < 0 || digit > 9) {
        throw new IllegalStateException("Not a digit " + (char) digit);
      }

      value = value * 10 - digit;
    }
    if (!negative) value = -value;
    this.offset = end + 1;
    return value;
  }

  Number readNumber(int end, NumericType type) {
    Number number = null;

    if (end >= offset) {
      byte[] bytes = buffer.getBytes(offset, end - 1);
      switch (type) {
        case INTEGER:
          number = Long.parseLong(new String(bytes, StandardCharsets.US_ASCII));
          break;
        case DECIMAL:
          if (bytes.length == 3 && bytes[0] == 'i' && bytes[1] == 'n' && bytes[2] == 'f') {
            number = Double.POSITIVE_INFINITY;
          } else if (bytes.length == 4 && bytes[0] == '-' && bytes[1] == 'i' && bytes[2] == 'n' && bytes[3] == 'f') {
            number = Double.NEGATIVE_INFINITY;
          } else {
            number = Double.parseDouble(new String(bytes, StandardCharsets.US_ASCII));
          }
          break;
        case BIGINTEGER:
          number = new BigInteger(bytes);
          break;
      }
      offset = end + 1;
    }

    return number;
  }

  String readLine(int end) {
    byte[] bytes = null;
    if (end >= offset) {
      bytes = buffer.getBytes(offset, end - 1);
      offset = end + 1;
    }
    if (bytes != null) {
      return new String(bytes, StandardCharsets.ISO_8859_1);
    }
    return null;
  }

  Buffer readBytes(int count) {
    Buffer bytes = null;
    if (buffer.length() - offset >= count) {
      bytes = buffer.getBuffer(offset, offset + count);
      offset += count;
    }
    return bytes;
  }

  byte readByte() {
    return buffer.getByte(offset++);
  }

  byte getByte(int index) {
    return buffer.getByte(index);
  }

  int readableBytes() {
    return buffer.length() - offset;
  }

  void mark() {
    mark = offset;
  }

  void reset() {
    offset = mark;
  }

  int offset() {
    return offset;
  }

  boolean skipEOL() {
    while (readableBytes() > 0) {
      byte b;

      b = readByte();
      if (b == '\r') {
        if (readableBytes() > 0) {
          b = readByte();
          if (b == '\n') {
            return true;
          }
        }
      }
    }

    return false;
  }

  @Override
  public String toString() {
    return buffer != null ? buffer.toString() : "null";
  }
}
