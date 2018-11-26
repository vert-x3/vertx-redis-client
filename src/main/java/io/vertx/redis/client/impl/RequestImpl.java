package io.vertx.redis.client.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Command;
import io.vertx.redis.impl.ZModem;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class RequestImpl implements Request {

  // precache -1
  private static final byte[] NEG_ONE = convert(-1);

  // Cache 256 number conversions. That should cover a huge
  // percentage of numbers passed over the wire.
  private static final int NUM_MAP_LENGTH = 256;
  private static final byte[][] NUM_MAP = new byte[NUM_MAP_LENGTH][];

  private static final byte[] EMPTY_BULK = "$0\r\n\r\n".getBytes(StandardCharsets.ISO_8859_1);
  private static final byte[] NULL_BULK = "$-1\r\n".getBytes(StandardCharsets.ISO_8859_1);
  private static final byte[] EOL = "\r\n".getBytes(StandardCharsets.ISO_8859_1);

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
  private static byte[] numToBytes(long value) {
    if (value >= 0 && value < NUM_MAP_LENGTH) {
      int index = (int) value;
      return NUM_MAP[index];
    } else if (value == -1) {
      return NEG_ONE;
    }
    return convert(value);
  }

  private final Buffer nakedBuffer = Buffer.buffer();
  private int elements = 0;
  private int slot = -1;
  private boolean readOnly;

  public RequestImpl(String command) {
    Objects.requireNonNull(command, "command");

    final String[] parts = command.split(" ");

    for (String part : parts) {
      arg(part);
    }
  }

  public RequestImpl(Command commandEnum) {
    // we have a hint how many parts this message will contain
    // initial elements
    elements = commandEnum.length();
    nakedBuffer.appendBytes(commandEnum.bytes());
  }

  // key management - keys are redis strings

  @Override
  public Request key(String key) {
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
  public Request key(Buffer key) {
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
    nakedBuffer.appendBytes(NULL_BULK);
    elements++;
    return this;
  }

  // bulk string

  @Override
  public Request arg(byte[] arg) {
    if (arg == null) {
      nakedBuffer.appendBytes(NULL_BULK);
      elements++;
      return this;
    }

    if (arg.length == 0) {
      nakedBuffer.appendBytes(EMPTY_BULK);
      elements++;
      return this;
    }

    nakedBuffer.appendByte((byte) '$');
    nakedBuffer.appendBytes(numToBytes(arg.length));
    nakedBuffer.appendBytes(EOL);
    nakedBuffer.appendBytes(arg);
    nakedBuffer.appendBytes(EOL);
    elements++;
    return this;
  }

  @Override
  public Request arg(String arg) {
    if (arg == null) {
      nakedBuffer.appendBytes(NULL_BULK);
      elements++;
      return this;
    }

    if (arg.length() == 0) {
      nakedBuffer.appendBytes(EMPTY_BULK);
      elements++;
      return this;
    }

    nakedBuffer.appendByte((byte) '$');
    final byte[] bytes = arg.getBytes(StandardCharsets.UTF_8);
    nakedBuffer.appendBytes(numToBytes(bytes.length));
    nakedBuffer.appendBytes(EOL);
    nakedBuffer.appendBytes(bytes);
    nakedBuffer.appendBytes(EOL);
    elements++;
    return this;
  }

  @Override
  public Request arg(String arg, String charset) {
    if (arg == null) {
      nakedBuffer.appendBytes(NULL_BULK);
      elements++;
      return this;
    }

    if (arg.length() == 0) {
      nakedBuffer.appendBytes(EMPTY_BULK);
      elements++;
      return this;
    }

    nakedBuffer.appendByte((byte) '$');
    final byte[] bytes = arg.getBytes(Charset.forName(charset));
    nakedBuffer.appendBytes(numToBytes(bytes.length));
    nakedBuffer.appendBytes(EOL);
    nakedBuffer.appendBytes(bytes);
    nakedBuffer.appendBytes(EOL);
    elements++;
    return this;
  }

  @Override
  public Request arg(Buffer arg) {
    if (arg == null) {
      nakedBuffer.appendBytes(NULL_BULK);
      elements++;
      return this;
    }

    if (arg.length() == 0) {
      nakedBuffer.appendBytes(EMPTY_BULK);
      elements++;
      return this;
    }

    nakedBuffer.appendByte((byte) '$');
    nakedBuffer.appendBytes(numToBytes(arg.length()));
    nakedBuffer.appendBytes(EOL);
    nakedBuffer.appendBuffer(arg);
    nakedBuffer.appendBytes(EOL);

    elements++;
    return this;
  }

  Buffer encode() {
    final byte[] len = numToBytes(elements);
    // we know for sure the final size
    final Buffer encoded = Buffer.buffer(1 + len.length + 2 + nakedBuffer.length());
    encoded
      .appendByte((byte) '*')
      .appendBytes(len)
      .appendBytes(EOL)
      .appendBuffer(nakedBuffer);

    return encoded;
  }

  void encode(Buffer encoded) {
    encoded
      .appendByte((byte) '*')
      .appendBytes(numToBytes(elements))
      .appendBytes(EOL)
      .appendBuffer(nakedBuffer);
  }
}
