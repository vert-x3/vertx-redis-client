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

import java.util.UUID;

@DataObject(generateConverter = true)
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
   * Set a user defined pool name (for metrics reporting).
   * @param name the user desired pool name.
   * @return fluent self
   */
  public PoolOptions setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get the pool name to be used in this client. The default name is a random UUID.
   * @return pool name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Tune how often in milliseconds should the connection pool cleaner execute.
   *
   * @return the cleaning internal
   */
  public int getCleanerInterval() {
    return cleanerInterval;
  }

  /**
   * Tune how often in milliseconds should the connection pool cleaner execute.
   *
   * For each connection in the pool, connections marked as invalid will be forcibly closed. A connection is marked
   * invalid if it enters a exception or fatal state.
   *
   * @param cleanerInterval the interval in milliseconds (-1 for never)
   * @return fluent self.
   */
  public PoolOptions setCleanerInterval(int cleanerInterval) {
    this.cleanerInterval = cleanerInterval;
    return this;
  }

  /**
   * Tune the maximum size of the connection pool.
   *
   * @return the size.
   */
  public int getMaxSize() {
    return maxSize;
  }

  /**
   * Tune the maximum size of the connection pool. When working with cluster or sentinel
   * this value should be atleast the total number of cluster member (or number of sentinels + 1)
   *
   * @param maxSize the max pool size.
   * @return fluent self.
   */
  public PoolOptions setMaxSize(int maxSize) {
    this.maxSize = maxSize;
    return this;
  }

  /**
   * Tune the maximum waiting requests for a connection from the pool.
   *
   * @return the maximum waiting requests.
   */
  public int getMaxWaiting() {
    return maxWaiting;
  }

  /**
   * Tune the maximum waiting requests for a connection from the pool.
   *
   * @param maxWaiting max waiting requests
   * @return fluent self.
   */
  public PoolOptions setMaxWaiting(int maxWaiting) {
    this.maxWaiting = maxWaiting;
    return this;
  }

  /**
   * Tune when a connection should be recycled in milliseconds.
   *
   * @return the timeout for recycling.
   */
  public int getRecycleTimeout() {
    return recycleTimeout;
  }

  /**
   * Tune when a connection should be recycled in milliseconds.
   *
   * @param recycleTimeout the timeout for recycling.
   * @return fluent self.
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
