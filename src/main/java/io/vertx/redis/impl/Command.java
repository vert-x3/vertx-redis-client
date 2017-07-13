/**
 * Copyright 2015 Red Hat, Inc.
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
package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.streams.WriteStream;

import java.nio.charset.Charset;
import java.util.List;

public class Command<T> {

  private static final byte ARGS_PREFIX = '*';
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

  private final Context context;
  private final Buffer buffer;
  private final ResponseTransform transform;
  private final String encoding;
  private final Class<T> returnType;
  private int expectedReplies = 1;
  private Handler<AsyncResult<T>> handler;

  public Command(Context context, AbstractCommand command, final List<?> args, Charset encoding, ResponseTransform transform, Class<T> returnType) {
    this.context = context;
    this.encoding = encoding.name();

    this.transform = transform;
    this.returnType = returnType;

    int totalArgs;
    if (args == null) {
      totalArgs = 0;
    } else {
      totalArgs = args.size();
    }

    String[] commandTokens = command.getTokens();

    // serialize the request
    buffer = Buffer.buffer();
    buffer.appendByte(ARGS_PREFIX);
    buffer.appendBytes(numToBytes(totalArgs + commandTokens.length));
    buffer.appendBytes(CRLF);

    // serialize the command
    for (String token : commandTokens) {
      appendToBuffer(token.getBytes(encoding), encoding, buffer);
    }

    // serialize arguments
    for (int i = 0; i < totalArgs; i++) {
      appendToBuffer(args.get(i), encoding, buffer);
    }
  }

  // Optimized for the direct to ASCII bytes case
  // About 5x faster than using Long.toString.getBytes
  private static byte[] numToBytes(long value) {
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

  // setters

  public Command<T> handler(Handler<AsyncResult<T>> handler) {
    this.handler = handler;
    return this;
  }

  public int getExpectedReplies() {
    return expectedReplies;
  }

  // getters

  public Command<T> setExpectedReplies(int expectedReplies) {
    this.expectedReplies = expectedReplies;
    return this;
  }

  public ResponseTransform responseTransform() {
    return transform;
  }

  public String encoding() {
    return encoding;
  }

  public Class<T> returnType() {
    return returnType;
  }

  // methods

  public void handle(AsyncResult<T> asyncResult) {
    if (handler != null) {
      if (context != null) {
        if (Vertx.currentContext() == context) {
          handler.handle(asyncResult);
        } else {
          context.runOnContext(v -> handler.handle(asyncResult));
        }
      } else {
        handler.handle(asyncResult);
      }
    }
  }

  public void writeTo(WriteStream<Buffer> writeStream) {
    writeStream.write(buffer);
  }

  private void appendToBuffer(final Object value, final Charset encoding, final Buffer buffer) {
    buffer.appendByte(BYTES_PREFIX);
    if (value == null) {
      buffer.appendByte((byte) '0');
      buffer.appendBytes(CRLF);
      buffer.appendBytes(CRLF);
    } else {
      byte[] bytes;
      // Possible types are: String, JsonObject, JsonArray, JsonElement, Number, Boolean, byte[]

      if (value instanceof byte[]) {
        bytes = (byte[]) value;
      } else if (value instanceof Buffer) {
        bytes = ((Buffer) value).getBytes();
      } else if (value instanceof String) {
        bytes = ((String) value).getBytes(encoding);
      } else if (value instanceof Byte) {
        bytes = numToBytes((Byte) value);
      } else if (value instanceof Short) {
        bytes = numToBytes((Short) value);
      } else if (value instanceof Integer) {
        bytes = numToBytes((Integer) value);
      } else if (value instanceof Long) {
        bytes = numToBytes((Long) value);
      } else {
        bytes = value.toString().getBytes(encoding);
      }

      buffer.appendBytes(numToBytes(bytes.length));

      buffer.appendBytes(CRLF);
      buffer.appendBytes(bytes);
      buffer.appendBytes(CRLF);
    }
  }
}
