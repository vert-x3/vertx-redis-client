/*
 * Copyright 2018 Red Hat, Inc.
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

import io.vertx.redis.RedisConnection;
import io.vertx.redis.RedisAPI;

/**
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public class RedisAPIImpl implements RedisAPI {

  private final RedisConnection delegate;


  public RedisAPIImpl(RedisConnection client) {
    delegate = client;
  }

  @Override
  public RedisConnection unwrap() {
    return delegate;
  }
}
