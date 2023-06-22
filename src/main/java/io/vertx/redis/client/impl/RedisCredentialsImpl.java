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

import io.vertx.redis.client.RedisCredentials;

public class RedisCredentialsImpl implements RedisCredentials {

  public static final RedisCredentials EMPTY_CREDENTIALS = new RedisCredentialsImpl();

  private final String user;
  private final String password;

  public RedisCredentialsImpl() {
    this(null, null);
  }

  public RedisCredentialsImpl(final String user, final String password) {
    this.user = user;
    this.password = password;
  }

  @Override
  public String getUser() {
    return this.user;
  }

  @Override
  public String getPassword() {
    return this.password;
  }
}
