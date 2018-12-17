package io.vertx.redis.client.impl;

import io.vertx.core.buffer.Buffer;

import java.nio.charset.Charset;

final class ReadableBuffer {

  // limit of integer parsing before overflowing
  private static final long MAX_INTEGER_DIV_10 = Long.MAX_VALUE / 10;
  private static final int MARK_WATERMARK = 4 * 1024 * 1024;

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

  Buffer readLine() {
    return readLine(findLineEnd());
  }

  String readLine(Charset charset) {
    return readLine(findLineEnd(), charset);
  }

  private Buffer readLine(int end) {
    Buffer bytes = null;
    if (end >= offset) {
      bytes = buffer.getBuffer(offset, end - 1);
      offset = end + 1;
    }
    return bytes;
  }

  String readLine(int end, Charset charset) {
    byte[] bytes = null;
    if (end >= offset) {
      bytes = buffer.getBytes(offset, end - 1);
      offset = end + 1;
    }
    if (bytes != null) {
      return new String(bytes, charset);
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

  boolean skip(int count) {
    if (readableBytes() >= count) {
      offset += count;
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return buffer != null ? buffer.toString() : "null";
  }
}
