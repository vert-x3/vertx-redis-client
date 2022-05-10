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

import io.vertx.redis.client.impl.keys.BeginSearch;
import io.vertx.redis.client.impl.keys.FindKeys;

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the command metadata
 *
 * @author Paulo Lopes
 */
public class CommandImpl implements CommandInternal {

  private final String command;
  private final byte[] bytes;
  private final int arity;

  private final BeginSearch beginSearch;
  private final FindKeys findKeys;

  private final boolean needGetKeys;
  private final boolean readOnly;
  private final boolean pubsub;

  public CommandImpl(String command, int arity, BeginSearch beginSearch, FindKeys findKeys, boolean needGetKeys, Boolean readOnly, boolean pubsub) {
    this.command = command;
    this.bytes = ("$" + command.length() + "\r\n" + command + "\r\n").getBytes(StandardCharsets.ISO_8859_1);
    this.arity = arity;
    this.beginSearch = beginSearch;
    this.findKeys = findKeys;
    this.needGetKeys = needGetKeys;
    this.readOnly = readOnly == null || readOnly;
    this.pubsub = pubsub;
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
  public boolean isReadOnly() {
    return readOnly;
  }

  @Override
  public boolean isPubSub() {
    return pubsub;
  }

  @Override
  public BeginSearch beginSearch() {
    return beginSearch;
  }

  @Override
  public FindKeys findKeys() {
    return findKeys;
  }

  @Override
  public boolean needsGetKeys() {
    return needGetKeys;
  }

  @Override
  public String toString() {
    return command;
  }
}
