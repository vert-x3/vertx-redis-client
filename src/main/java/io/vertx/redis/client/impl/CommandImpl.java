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

import io.vertx.redis.client.Command;
import io.vertx.redis.client.impl.keys.KeyConsumer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the command metadata
 *
 * @author Paulo Lopes
 */
public class CommandImpl implements Command {

  private final String command;
  private final byte[] bytes;
  private final int arity;

  private final KeyLocator[] keyLocators;
  private final boolean needGetKeys;
  private final Boolean readOnly;
  private final boolean pubsub;

  public CommandImpl(String command, int arity, Boolean readOnly, boolean pubsub, boolean needGetKeys, KeyLocator... keyLocators) {
    this.command = command;
    this.bytes = ("$" + command.length() + "\r\n" + command + "\r\n").getBytes(StandardCharsets.ISO_8859_1);
    this.arity = arity;
    this.needGetKeys = needGetKeys;
    this.keyLocators = keyLocators;
    this.readOnly = readOnly;
    this.pubsub = pubsub;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public int getArity() {
    return arity;
  }

  public boolean isReadOnly(List<byte[]> args) {
    if (keyLocators != null) {
      for (int i = keyLocators.length - 1; i >= 0; i--) {
        final KeyLocator keyLocator = keyLocators[i];
        if (keyLocator.begin == null || keyLocator.find == null) {
          continue;
        }

        final int offset = keyLocator.begin.begin(args, arity);
        if (offset == -1) {
          continue;
        }
        if (keyLocator.ro == null) {
          // when read only is undefined, we assume that the command has no side effects,
          // so it can be execution on either a master or replica node
          return true;
        } else {
          return keyLocator.ro;
        }
      }
    }
    if (readOnly == null) {
      // when read only is undefined, we assume that the command has no side effects,
      // so it can be execution on either a master or replica node
      return true;
    } else {
      return readOnly;
    }
  }

  public boolean isPubSub() {
    return pubsub;
  }

  public boolean needsGetKeys() {
    return needGetKeys;
  }

  public List<byte[]> extractKeys(List<byte[]> args) {
    if (keyLocators != null) {
      for (int i = keyLocators.length - 1; i >= 0; i--) {
        final KeyLocator keyLocator = keyLocators[i];
        if (keyLocator.begin == null || keyLocator.find == null) {
          continue;
        }

        final int offset = keyLocator.begin.begin(args, arity);
        if (offset == -1) {
          continue;
        }

        final List<byte[]> collector = new ArrayList<>();
        keyLocator.find.forEach(args, arity, offset, (begin, keyIdx, keyStep) -> collector.add(args.get(keyIdx)));
        return collector;
      }
    }
    return Collections.emptyList();
  }

  public int iterateKeys(List<byte[]> args, KeyConsumer consumer) {
    if (keyLocators != null) {
      for (int i = keyLocators.length - 1; i >= 0; i--) {
        final KeyLocator keyLocator = keyLocators[i];
        if (keyLocator.begin == null || keyLocator.find == null) {
          continue;
        }

        final int offset = keyLocator.begin.begin(args, arity);
        if (offset == -1) {
          continue;
        }

        return keyLocator.find.forEach(args, arity, offset, consumer);
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    return command;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandImpl command1 = (CommandImpl) o;
    // the command itself must be unique, the remaining properties are not relevant as they are metadata for the command
    return Objects.equals(command, command1.command);
  }

  @Override
  public int hashCode() {
    return Objects.hash(command);
  }
}
