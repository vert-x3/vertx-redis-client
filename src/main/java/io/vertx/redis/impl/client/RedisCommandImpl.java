package io.vertx.redis.impl.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisCommand;
import io.vertx.redis.RedisCommandEnum;
import io.vertx.redis.impl.ZModem;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.netty.buffer.Unpooled.*;

public class RedisCommandImpl implements RedisCommand {

  private static final byte[] ARGS_PREFIX = new byte[] { '*' };
  private static final byte[] CRLF = "\r\n".getBytes();
  private static final byte[] BYTES_PREFIX = new byte[] { '$' };
  private static final byte[] ZERO = new byte[] { '0' };

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

  private final CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
  private int size;

  // key reference
  private int slot = -1;
  private boolean readOnly = false;

  // Optimized for the direct to ASCII bytes case
  // About 5x faster than using Long.toString.getBytes
  public static byte[] numToBytes(long value) {
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

  public RedisCommandImpl() {
  }

  public RedisCommandImpl(RedisCommandEnum command) {
    size = command.getTokenCount();

    for (int i = 0; i < size; i++) {
      // serialize the command
      final byte[] token = command.getToken(i);

      buffer.addComponent(true, wrappedBuffer(BYTES_PREFIX));
      buffer.addComponent(true, wrappedBuffer(numToBytes(token.length)));
      buffer.addComponent(true, wrappedBuffer(CRLF));
      buffer.addComponent(true, wrappedBuffer(token));
      buffer.addComponent(true, wrappedBuffer(CRLF));
    }
  }

  public RedisCommandImpl(String command) {
    final String[] parts = command.split(" ");
    size = parts.length;

    for (int i = 0; i < size; i++) {
      // serialize the command
      final byte[] token = parts[i].getBytes(StandardCharsets.UTF_8);

      buffer.addComponent(true, wrappedBuffer(BYTES_PREFIX));
      buffer.addComponent(true, wrappedBuffer(numToBytes(token.length)));
      buffer.addComponent(true, wrappedBuffer(CRLF));
      buffer.addComponent(true, wrappedBuffer(token));
      buffer.addComponent(true, wrappedBuffer(CRLF));
    }
  }

  @Override
  public RedisCommand nullArg() {
    buffer.addComponent(true, wrappedBuffer(BYTES_PREFIX));
    buffer.addComponent(true, wrappedBuffer(ZERO));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    size++;
    return this;
  }

  @Override
  public RedisCommand key(ByteBuf key) {
    arg(key);
    this.slot = ZModem.generate(key);
    return this;
  }

  @Override
  public RedisCommand arg(ByteBuf arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return nullArg();
    }
    buffer.addComponent(true, wrappedBuffer(BYTES_PREFIX));
    buffer.addComponent(true, wrappedBuffer(numToBytes(arg.writerIndex())));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    buffer.addComponent(true, arg);
    buffer.addComponent(true, wrappedBuffer(CRLF));
    size++;
    return this;
  }

  @Override
  public RedisCommand arg(Long arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return nullArg();
    }
    buffer.addComponent(true, wrappedBuffer(BYTES_PREFIX));
    final byte[] bytes = numToBytes(arg);
    buffer.addComponent(true, wrappedBuffer(numToBytes(bytes.length)));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    buffer.addComponent(true, wrappedBuffer(bytes));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    size++;
    return this;
  }

  @Override
  public RedisCommand arg(List arg) {
    // short circuit the encoding if null
    if (arg == null) {
      return nullArg();
    }
    // add the size
    arg(arg.size());
    // add the values
    for (Object el : arg) {
      objectArg(el);
    }

    return this;
  }

  private RedisCommand objectArg(Object arg) throws IllegalArgumentException {
    if (arg == null) {
      return nullArg();
    }

    if (arg instanceof String) {
      return arg((String) arg);
    }

    if (arg instanceof Long) {
      return arg((Long) arg);
    }

    if (arg instanceof Integer) {
      return arg((Integer) arg);
    }

    if (arg instanceof Float) {
      return arg((Float) arg);
    }

    if (arg instanceof Double) {
      return arg((Double) arg);
    }

    if (arg instanceof Buffer) {
      return arg((Buffer) arg);
    }

    if (arg instanceof ByteBuf) {
      return arg((ByteBuf) arg);
    }

    if (arg instanceof byte[]) {
      return arg((byte[]) arg);
    }

    if (arg instanceof JsonArray) {
      return arg((JsonArray) arg);
    }

    if (arg instanceof List) {
      return arg((List) arg);
    }

    throw new IllegalArgumentException(arg.getClass().getName() + " is not allowed!");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public ByteBuf toByteBuf() {
    // serialize the request
    final CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();

    buffer.addComponent(true, wrappedBuffer(ARGS_PREFIX));
    buffer.addComponent(true, wrappedBuffer(numToBytes(size)));
    buffer.addComponent(true, wrappedBuffer(CRLF));
    // serialize the command
    buffer.addComponent(true, this.buffer);

    return buffer;
  }

  @Override
  public int getKeySlot() {
    return slot;
  }

  @Override
  public RedisCommand setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }

  @Override
  public boolean isReadOnly() {
    return readOnly;
  }
}
