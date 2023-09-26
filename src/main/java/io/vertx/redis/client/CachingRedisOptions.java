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
package io.vertx.redis.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DataObject(generateConverter = true)
public class CachingRedisOptions {

  /**
   * The default number of max entries the cache will store.
   */
  public static final int DEFAULT_MAX_SIZE = 1024;

  /**
   * The default max age, in {@link #DEFAULT_MAX_AGE_UNIT} units, of an item in the cache.
   */
  public static final long DEFAULT_MAX_AGE = 3_600_000; // 1 hour in milliseconds
  public static final TimeUnit DEFAULT_MAX_AGE_UNIT = TimeUnit.MILLISECONDS;

  private boolean enabled;
  private int maxCacheSize;
  private long maxAge;
  private TimeUnit maxAgeUnit;
  private ClientSideCacheMode mode;
  private List<String> prefixes;

  /**
   * Creates a default configuration.
   */
  public CachingRedisOptions() {
    this.enabled = false;
    this.maxCacheSize = DEFAULT_MAX_SIZE;
    this.maxAge = DEFAULT_MAX_AGE;
    this.maxAgeUnit = DEFAULT_MAX_AGE_UNIT;
    this.mode = ClientSideCacheMode.PER_CLIENT;
    this.prefixes = new ArrayList<>();
  }

  /**
   * Copy constructor.
   *
   * @param other the options to clone
   */
  public CachingRedisOptions(CachingRedisOptions other) {
    this.enabled = other.enabled;
    this.maxCacheSize = other.maxCacheSize;
    this.maxAge = other.maxAge;
    this.maxAgeUnit = other.maxAgeUnit;
    this.mode = other.mode;
    this.prefixes = other.prefixes;
  }

  /**
   * Create from JSON constructor.
   *
   * @param json the source JSON
   */
  public CachingRedisOptions(JsonObject json) {
    this();
    CachingRedisOptionsConverter.fromJson(json, this);
  }

  /**
   * @return true if caching is enabled
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Set if caching is enabled.
   *
   * @param enabled if caching is enabled
   * @return fluent self
   */
  public CachingRedisOptions setEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get the max cache size.
   *
   * @return the max size
   */
  public int getMaxCacheSize() {
    return maxCacheSize;
  }

  /**
   * Set the max cache size.
   *
   * @param maxCacheSize the max size
   * @return fluent self
   */
  public CachingRedisOptions setMaxCacheSize(int maxCacheSize) {
    this.maxCacheSize = maxCacheSize;
    return this;
  }

  /**
   * Get the max item age.
   *
   * @return the max age value
   */
  public long getMaxAge() {
    return maxAge;
  }

  /**
   * Get the max item age time unit.
   *
   * @return the time unit
   */
  public TimeUnit getMaxAgeUnit() {
    return maxAgeUnit;
  }

  /**
   * Set the max age value.
   *
   * <p>
   * By default, the max age is in milliseconds. If you want to change the unit, use {@link #setMaxAgeUnit(TimeUnit)}.
   *
   * @param maxAge the max age
   * @return fluent self
   */
  public CachingRedisOptions setMaxAge(long maxAge) {
    this.maxAge = maxAge;
    return this;
  }

  /**
   * Set the max age time unit.
   *
   * @param unit the time unit
   * @return fluent self
   */
  public CachingRedisOptions setMaxAgeUnit(TimeUnit unit) {
    this.maxAgeUnit = unit;
    return this;
  }

  /**
   * Get the tracking mode the clients configure the data connection with.
   *
   * @return the mode
   */
  public ClientSideCacheMode getMode() {
    return mode;
  }

  /**
   * Set the tracking mode the clients configure the data connection with.
   *
   * @param mode the mode
   * @return fluent self
   */
  public CachingRedisOptions setMode(ClientSideCacheMode mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get the key prefixes the server should send invalidation messages for.
   *
   * @return the prefixes
   */
  public List<String> getPrefixes() {
    return prefixes;
  }

  /**
   * Set the key prefixes the server should send invalidation messages for, replacing any existing values.
   *
   * @param prefixes the prefix list
   * @return fluent self
   */
  public CachingRedisOptions setPrefixes(List<String> prefixes) {
    this.prefixes.clear();
    this.prefixes.addAll(prefixes);
    return this;
  }

  /**
   * Set a single prefix the server should send invalidation messages for, replacing any existing values.
   *
   * @param prefix the prefix
   * @return fluent self
   */
  public CachingRedisOptions setPrefix(String prefix) {
    prefixes.clear();
    prefixes.add(prefix);
    return this;
  }

  /**
   * Add a prefix the server should send invalidation messages for.
   *
   * @param prefix the prefix to add
   * @return fluent self
   */
  public CachingRedisOptions addPrefix(String prefix) {
    prefixes.add(prefix);
    return this;
  }
}
