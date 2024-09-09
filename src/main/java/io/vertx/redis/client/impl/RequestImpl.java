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
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.vertx.redis.client.impl.RESPEncoder.numToBytes;

public final class RequestImpl implements Request {

  private static final byte[] EMPTY_BULK = "$0\r\n\r\n".getBytes(StandardCharsets.ISO_8859_1);
  private static final byte[] EMPTY_BYTES = new byte[0];
  private static final byte[] EOL = "\r\n".getBytes(StandardCharsets.ISO_8859_1);
  private static final byte[] TRUE = new byte[]{'t'};
  private static final byte[] FALSE = new byte[]{'f'};

  private final CommandImpl cmd;
  private final List<byte[]> args;

  public RequestImpl(Command cmd) {
    this.cmd = (CommandImpl) cmd;

    if (this.cmd.getArity() != 0) {
      args = new ArrayList<>(Math.abs(this.cmd.getArity()));
    } else {
      args = Collections.emptyList();
    }
  }

  public RequestImpl(Command cmd, Object[] args) {
    this.cmd = (CommandImpl) cmd;

    if (args == null) {
      this.args = Collections.emptyList();
      return;
    }

    for (int i = 0; i < args.length; i++) {
      final Object o = args[i];
      if (o == null) {
        throw new IllegalArgumentException("Null argument at index " + i);
      } else if (o instanceof Number) {
        args[i] = o.toString().getBytes(StandardCharsets.US_ASCII);
      } else if (o instanceof Boolean) {
        args[i] = ((Boolean) o) ? TRUE : FALSE;
      } else if (o instanceof String) {
        args[i] = ((String) o).getBytes(StandardCharsets.UTF_8);
      } else if (o instanceof byte[]) {
        // already OK
      } else if (o instanceof Buffer) {
        args[i] = ((Buffer) o).getBytes();
      } else {
        throw new IllegalArgumentException("Unsupported argument type at index " + i + ": " + o.getClass());
      }
    }
    this.args = (List) Arrays.asList(args);
  }

  @Override
  public Command command() {
    return cmd;
  }

  // arguments

  // integer

  @Override
  public Request arg(long arg) {
    args.add(numToBytes(arg));
    return this;
  }

  // boolean
  @Override
  public Request arg(boolean arg) {
    arg(arg ? TRUE : FALSE);
    return this;
  }

  // bulk string

  @Override
  public Request arg(byte[] arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    }

    args.add(arg);
    return this;
  }

  @Override
  public Request arg(Buffer arg) {
    if (arg == null) {
      throw new IllegalArgumentException("Null argument not allowed");
    }

    if (arg.length() == 0) {
      args.add(EMPTY_BYTES);
      return this;
    }

    args.add(arg.getBytes());
    return this;
  }

  public Buffer encode() {
    return encode(Buffer.buffer());
  }

  Buffer encode(Buffer buffer) {
    buffer
      // array header
      .appendByte((byte) '*')
      .appendBytes(numToBytes(args.size() + 1))
      .appendBytes(EOL)
      // command
      .appendBytes(cmd.getBytes());

    for (final byte[] arg : args) {
      if (arg.length == 0) {
        buffer.appendBytes(EMPTY_BULK);
        continue;
      }

      buffer
        .appendByte((byte) '$')
        .appendBytes(numToBytes(arg.length))
        .appendBytes(EOL)
        .appendBytes(arg)
        .appendBytes(EOL);
    }

    return buffer;
  }

  public List<byte[]> getArgs() {
    return args;
  }

  public List<byte[]> keys() {
    return cmd.extractKeys(args);
  }

  @Override
  public String toString() {
    return encode().toString();
  }

  public boolean valid() {
    int arity = cmd.getArity();
    int arglen = args.size() + 1;
    if (arity >= 0) {
      return arity == arglen;
    } else {
      return -arity <= arglen;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestImpl request = (RequestImpl) o;
    return Objects.equals(cmd, request.cmd) && Objects.equals(args, request.args);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cmd, args);
  }
}
