package io.vertx.redis.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.Args;

import java.util.List;


public class ArgsImpl implements Args {

  private static final byte[] CRLF = "\r\n".getBytes();
  private static final byte BYTES_PREFIX = '$';

  private static final byte[] NEG_ONE = convert(-1);

  // Cache 256 number conversions. That should cover a huge
  // percentage of numbers passed over the wire.
  private static final int NUM_MAP_LENGTH = 256;
  private static final byte[][] numMap = new byte[NUM_MAP_LENGTH][];

  static {
    for (int i = 0; i < NUM_MAP_LENGTH; i++) {
      numMap[i] = convert(i);
    }
  }

  private final Buffer buffer = Buffer.buffer();
  private int size;

  // Optimized for the direct to ASCII bytes case
  // About 5x faster than using Long.toString.getBytes
  static byte[] numToBytes(long value) {
    if (value >= 0 && value < NUM_MAP_LENGTH) {
      int index = (int) value;
      return numMap[index];
    } else if (value == -1) {
      return NEG_ONE;
    }
    return convert(value);
  }

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

  public ArgsImpl() {

  }

  public ArgsImpl(Object[] args) {
    for (Object el : args) {
      addObject(el);
    }
  }

  @Override
  public Args addNull() {
    buffer.appendByte(BYTES_PREFIX);
    buffer.appendByte((byte) '0');
    buffer.appendBytes(CRLF);
    buffer.appendBytes(CRLF);
    size++;
    return this;
  }

  @Override
  public Args add(Buffer arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return addNull();
    }
    buffer.appendByte(BYTES_PREFIX);
    buffer.appendBytes(numToBytes(arg.length()));
    buffer.appendBytes(CRLF);
    buffer.appendBuffer(arg);
    buffer.appendBytes(CRLF);
    size++;
    return this;
  }

  @Override
  public Args add(Long arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return addNull();
    }
    buffer.appendByte(BYTES_PREFIX);
    final byte[] bytes = numToBytes(arg);
    buffer.appendBytes(numToBytes(bytes.length));
    buffer.appendBytes(CRLF);
    buffer.appendBytes(bytes);
    buffer.appendBytes(CRLF);
    size++;
    return this;
  }

  @Override
  public Args add(List arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return addNull();
    }
    // add the size
    add(arg.size());
    // add the values
    for (Object el : arg) {
      addObject(el);
    }

    return this;
  }

  private Args addObject(Object arg) throws IllegalArgumentException {
    if (arg == null) {
      return addNull();
    }

    if (arg instanceof String) {
      return add((String) arg);
    }

    if (arg instanceof Long) {
      return add((Long) arg);
    }

    if (arg instanceof Integer) {
      return add((Integer) arg);
    }

    if (arg instanceof Float) {
      return add((Float) arg);
    }

    if (arg instanceof Double) {
      return add((Double) arg);
    }

    if (arg instanceof Buffer) {
      return add((Buffer) arg);
    }

    if (arg instanceof byte[]) {
      return add((byte[]) arg);
    }

    if (arg instanceof JsonArray) {
      return add((JsonArray) arg);
    }

    if (arg instanceof List) {
      return add((List) arg);
    }

    throw new IllegalArgumentException(arg.getClass().getName() + " is not allowed!");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Buffer toBuffer() {
    return buffer;
  }
}
