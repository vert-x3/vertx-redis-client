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
package io.vertx.redis.client.impl.cache;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.redis.client.CachingRedisOptions;
import io.vertx.redis.client.spi.RedisClientCache;
import io.vertx.redis.client.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRURedisClientCache implements RedisClientCache {

  private final Store store;
  private final long maxAgeMillis;

  public LRURedisClientCache(CachingRedisOptions options) {
    this.store = new Store(options.getMaxCacheSize());
    this.maxAgeMillis = options.getMaxAgeUnit().toMillis(options.getMaxAge());
  }

  @Override
  public @Nullable Response get(CacheKey key) {
    final CacheEntry entry = store.get(key);

    if (entry == null) {
      return null;
    }

    if (entry.getAgeMillis() > maxAgeMillis) {
      store.remove(key);
      return null;
    }

    return entry.response;
  }

  @Override
  public void put(CacheKey key, Response value) {
    store.put(key, new CacheEntry(value));
  }

  @Override
  public void delete(CacheKey key) {
    store.remove(key);
  }

  @Override
  public void flush() {
    store.clear();
  }

  @Override
  public void close() {
    flush();
  }

  static final class Store extends LinkedHashMap<CacheKey, CacheEntry> {

    private final int maxSize;

    Store(int maxSize) {
      super(maxSize, 0.75f, true);
      this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<CacheKey, CacheEntry> eldest) {
      return size() > maxSize;
    }
  }

  static final class CacheEntry {

    private final Response response;
    private final long insertTime;

    CacheEntry(Response response) {
      this.response = response;
      this.insertTime = System.currentTimeMillis();
    }

    long getAgeMillis() {
      return System.currentTimeMillis() - insertTime;
    }
  }
}
