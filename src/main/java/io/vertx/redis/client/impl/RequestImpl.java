package io.vertx.redis.client.impl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Command;
import io.vertx.redis.impl.ZModem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.netty.buffer.Unpooled.*;

public final class RequestImpl implements Request {

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
  // About 5x faster than using Long.toString.getBytes
  private static byte[] numToBytes(long value) {
    if (value >= 0 && value < NUM_MAP_LENGTH) {
      int index = (int) value;
      return NUM_MAP[index];
    } else if (value == -1) {
      return NEG_ONE;
    }
    return convert(value);
  }

  private final List<RedisMessage> parts;
  private int slot = -1;
  private boolean readOnly;

  public RequestImpl(String command) {
    Objects.requireNonNull(command, "command");

    parts = new ArrayList<>();

    final String[] parts = command.split(" ");

    for (String part : parts) {
      arg(part);
    }
  }

  public RequestImpl(Command commandEnum) {
    // we have a hint how many parts this message will contain
    parts = new ArrayList<>(commandEnum.lengthHint());
    for (byte[] part : commandEnum.tokens()) {
      arg(wrappedBuffer(part));
    }
  }

  // key management - keys are redis strings

  @Override
  public Request key(ByteBuf key) {
    arg(key);
    this.slot = ZModem.generate(key);
    return this;
  }

  @Override
  public Request key(byte[] key) {
    arg(key);
    this.slot = ZModem.generate(key);
    return this;
  }

  @Override
  public int getKeySlot() {
    return slot;
  }

  // metadata

  @Override
  public Request setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }

  @Override
  public boolean isReadOnly() {
    return readOnly;
  }

  // arguments

  // integer

  @Override
  public Request arg(long arg) {
    return arg(numToBytes(arg));
  }

  // null

  @Override
  public Request nullArg() {
    parts.add(FullBulkStringRedisMessage.NULL_INSTANCE);
    return this;
  }

  // empty

  @Override
  public Request arg() {
    parts.add(FullBulkStringRedisMessage.EMPTY_INSTANCE);
    return this;
  }

  // bulk string

  @Override
  public Request arg(ByteBuf arg) {
    if (arg == null) {
      parts.add(FullBulkStringRedisMessage.NULL_INSTANCE);
      return this;
    }

    parts.add(new FullBulkStringRedisMessage(arg));
    return this;
  }

  // bulk string

  @Override
  public Request arg(byte[] arg) {
    if (arg == null) {
      parts.add(FullBulkStringRedisMessage.NULL_INSTANCE);
      return this;
    }

    parts.add(new FullBulkStringRedisMessage(wrappedBuffer(arg)));
    return this;
  }

  RedisMessage message() {
    return new ArrayRedisMessage(parts);
  }
}
