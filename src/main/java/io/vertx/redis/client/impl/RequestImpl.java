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
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.vertx.redis.client.impl.RESPEncoder.*;

public final class RequestImpl implements Request {

  private final Command cmd;
  private final List<byte[]> args;

  public RequestImpl(Command cmd) {
    this.cmd = cmd;

    if (cmd.getArity() != 0) {
      args = new ArrayList<>(Math.abs(cmd.getArity()));
    } else {
      args = Collections.emptyList();
    }
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

  // null

  @Override
  public Request nullArg() {
    args.add(NULL_BULK);
    return this;
  }

  // bulk string

  @Override
  public Request arg(byte[] arg) {
    if (arg == null) {
      return nullArg();
    }

    if (arg.length == 0) {
      args.add(EMPTY_BULK);
      return this;
    }

    args.add(arg);
    return this;
  }

  @Override
  public Request arg(Buffer arg) {
    if (arg == null) {
      return nullArg();
    }

    if (arg.length() == 0) {
      args.add(EMPTY_BULK);
      return this;
    }

    args.add(arg.getBytes());
    return this;
  }

  Buffer encode() {
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
      buffer
        .appendByte((byte) '$')
        .appendBytes(numToBytes(arg.length))
        .appendBytes(EOL)
        .appendBytes(arg)
        .appendBytes(EOL);
    }

    return buffer;
  }

  List<byte[]> getArgs() {
    return args;
  }

  @Override
  public String toString() {
    return encode().toString();
  }
}
