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

import java.util.Objects;

public class CacheKey {

  private final String key;
  private final long createdAtMillis;

  public CacheKey(String key) {
    this(key, System.currentTimeMillis());
  }

  public CacheKey(String key, long createdAtMillis) {
    this.key = key;
    this.createdAtMillis = createdAtMillis;
  }

  public String getKey() {
    return key;
  }

  public long createdAt() {
    return createdAtMillis;
  }

  public long ageMillis() {
    return System.currentTimeMillis() - createdAtMillis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CacheKey cacheKey = (CacheKey) o;
    return Objects.equals(key, cacheKey.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }
}
