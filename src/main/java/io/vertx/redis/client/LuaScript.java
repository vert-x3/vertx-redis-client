/*
 * Copyright 2018 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client;

import io.vertx.redis.client.util.Hash;

/**
 * LuaScript
 *
 * Lua script with pre-computed sha hash.
 */
public class LuaScript {

  // this lua script
  private final String lua;

  // pre-computed sha
  private final String sha;

  // pre-converted string of num of keys
  private final String numKeys;

  public LuaScript(String lua, int numKeys) {
    this.lua = lua;
    this.sha = Hash.sha1(lua);
    this.numKeys = String.valueOf(numKeys);
  }

  /**
   * @return the lua
   */
  public String getLua() {
    return lua;
  }

  /**
   * @return the sha
   */
  public String getSha() {
    return sha;
  }

  /**
   * @return the numKeys
   */
  public String getNumKeys() {
    return numKeys;
  }
}
