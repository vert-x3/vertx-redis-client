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
package io.vertx.redis.client.spi;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.CachingRedis;
import io.vertx.redis.client.impl.cache.CacheKey;

/**
 * A client-side cache store used by a {@link CachingRedis}.
 */
public interface RedisClientCache {

  /**
   * Get an item if it exists.
   *
   * @param key the key to get
   * @return the item, or null if it does not exist
   */
  @Nullable Response get(CacheKey key);

  /**
   * Put an item, replacing anything that may exist.
   *
   * @param key   the key
   * @param value the value
   */
  void put(CacheKey key, Response value);

  /**
   * Remove an item if it exists.
   *
   * @param key the key to remove
   */
  void delete(CacheKey key);

  /**
   * Invalidate or remove all items, emptying the cache.
   */
  void flush();

  /**
   * Close the cache and cleanup any resources.
   */
  void close();
}
