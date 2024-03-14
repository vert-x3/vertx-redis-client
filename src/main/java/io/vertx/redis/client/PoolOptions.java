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
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

@DataObject
@JsonGen(publicConverter = false)
public class PoolOptions {

  private String name;
  private int cleanerInterval;
  private int maxSize;
  private int maxWaiting;
  private int recycleTimeout;

  public PoolOptions() {
    name = UUID.randomUUID().toString();
    // thumb guess based on web browser defaults
    cleanerInterval = 30_000;
    maxSize = 6;
    maxWaiting = 24;
    recycleTimeout = 180_000;
  }

  public PoolOptions(PoolOptions other) {
    this.name = other.name;
    this.cleanerInterval = other.cleanerInterval;
    this.maxSize = other.maxSize;
    this.maxWaiting = other.maxWaiting;
    this.recycleTimeout = other.recycleTimeout;
  }

  public PoolOptions(JsonObject json) {
    this();
    PoolOptionsConverter.fromJson(json, this);
  }

  /**
   * Get the connection pool name to be used for metrics reporting. The default name is a random UUID.
   *
   * @return the pool name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Set the connection pool name to be used for metrics reporting. The default name is a random UUID.
   *
   * @param name the pool name
   * @return fluent self
   */
  public PoolOptions setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get how often the connection pool will be cleaned. Cleaning the connection pool
   * means scanning for unused invalid connections and if any are found, they are forcibly
   * closed and evicted from the pool.
   * <p>
   * A connection is marked invalid if it enters a exception or fatal state or if it exists
   * longer than the {@linkplain #getRecycleTimeout() recycle timeout}.
   * <p>
   * The return value is in milliseconds. By default, the cleaning interval is 30 seconds.
   * The value of -1 means connection pool cleaning is disabled.
   *
   * @return the cleaning interval in milliseconds, or -1 for never
   */
  public int getCleanerInterval() {
    return cleanerInterval;
  }

  /**
   * Set how often the connection pool will be cleaned. Cleaning the connection pool
   * means scanning for unused invalid connections and if any are found, they are forcibly
   * closed and evicted from the pool.
   * <p>
   * A connection is marked invalid if it enters a exception or fatal state or if it exists
   * longer than the {@linkplain #setRecycleTimeout(int)} recycle timeout}.
   * <p>
   * The value is in milliseconds. By default, the cleaning interval is 30 seconds.
   * The value of -1 means connection pool cleaning is disabled.
   *
   * @param cleanerInterval the cleaning interval in milliseconds, or -1 for never
   * @return fluent self
   */
  public PoolOptions setCleanerInterval(int cleanerInterval) {
    this.cleanerInterval = cleanerInterval;
    return this;
  }

  /**
   * Get the maximum size of the connection pool.
   * <p>
   * By default, the maximum pool size is 6.
   *
   * @return the maximum pool size
   */
  public int getMaxSize() {
    return maxSize;
  }

  /**
   * Set the maximum size of the connection pool.
   * <p>
   * By default, the maximum pool size is 6.
   * <p>
   * When working with cluster or sentinel, this value should be at least the total
   * number of cluster member (or number of sentinels + 1).
   *
   * @param maxSize the maximum pool size
   * @return fluent self
   */
  public PoolOptions setMaxSize(int maxSize) {
    this.maxSize = maxSize;
    return this;
  }

  /**
   * Get the maximum number of requests waiting for a connection from the pool.
   * <p>
   * By default, the maximum number of waiting requests size is 24.
   *
   * @return the maximum number of waiting requests
   */
  public int getMaxWaiting() {
    return maxWaiting;
  }

  /**
   * Set the maximum number of requests waiting for a connection from the pool.
   * <p>
   * By default, the maximum number of waiting requests size is 24.
   *
   * @param maxWaiting the maximum number of waiting requests
   * @return fluent self
   */
  public PoolOptions setMaxWaiting(int maxWaiting) {
    this.maxWaiting = maxWaiting;
    return this;
  }

  /**
   * Get how long a connection can exist before it is recycled during connection pool
   * {@linkplain #getCleanerInterval() cleaning}.
   * <p>
   * The value is in milliseconds. By default, the recycle timeout is 3 minutes.
   * The value of -1 means connection recycling is disabled.
   *
   * @return the recycle timeout
   */
  public int getRecycleTimeout() {
    return recycleTimeout;
  }

  /**
   * Set how long a connection can exist before it is recycled during connection pool
   * {@linkplain #setCleanerInterval(int) cleaning}.
   * <p>
   * The value is in milliseconds. By default, the recycle timeout is 3 minutes.
   * The value of -1 means connection recycling is disabled.
   *
   * @param recycleTimeout the recycle timeout
   * @return fluent self
   */
  public PoolOptions setRecycleTimeout(int recycleTimeout) {
    this.recycleTimeout = recycleTimeout;
    return this;
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    PoolOptionsConverter.toJson(this, json);
    return json;
  }
}
