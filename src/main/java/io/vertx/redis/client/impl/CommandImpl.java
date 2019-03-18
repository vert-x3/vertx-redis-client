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

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the command metadata
 *
 * @author Paulo Lopes
 */
public class CommandImpl implements Command {

  private final byte[] bytes;
  private final int arity;

  private final String commandName;
  private final boolean multiKey;
  private final int firstKey;
  private final int lastKey;
  private final int interval;
  private final boolean keyless;
  private final boolean readOnly;
  private final boolean movable;
  private final boolean clusterWide;

  public CommandImpl(String command, int arity, int firstKey, int lastKey, int interval, boolean readOnly, boolean movable, boolean clusterWide) {
    bytes = ("$" + command.length() + "\r\n" + command + "\r\n").getBytes(StandardCharsets.ISO_8859_1);
    this.commandName = command;
    this.arity = arity;
    this.firstKey = firstKey;
    this.lastKey = lastKey;
    this.interval = interval;
    this.multiKey = lastKey < 0;
    this.keyless = interval == 0 && !movable;
    this.readOnly = readOnly;
    this.movable = movable;
    this.clusterWide = clusterWide;
  }

  @Override
  public String getCommandName() {
    return commandName;
  }

  @Override
  public byte[] getBytes() {
    return bytes;
  }

  @Override
  public int getArity() {
    return arity;
  }

  @Override
  public boolean isMultiKey() {
    return multiKey;
  }

  @Override
  public int getFirstKey() {
    return firstKey;
  }

  @Override
  public int getLastKey() {
    return lastKey;
  }

  @Override
  public int getInterval() {
    return interval;
  }

  @Override
  public boolean isKeyless() {
    return keyless;
  }

  @Override
  public boolean isReadOnly() {
    return readOnly;
  }

  @Override
  public boolean isMovable() {
    return movable;
  }

  @Override
  public boolean isClusterWide() {
    return clusterWide;
  }
}
