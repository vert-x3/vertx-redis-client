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
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClientOptions;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Redis Client Configuration options.
 *
 * @author Paulo Lopes
 */
@DataObject(generateConverter = true)
public class RedisOptions extends MutableRedisOptions {

  private RedisClientType type;
  private NetClientOptions netClientOptions;

  private RedisReplicas useReplicas;

  // pool related options
  private String poolName;
  private int poolCleanerInterval;
  private int maxPoolSize;
  private int maxPoolWaiting;
  private int poolRecycleTimeout;

  private void init() {
    netClientOptions =
      new NetClientOptions()
        .setTcpKeepAlive(true)
        .setTcpNoDelay(true);

    type = RedisClientType.STANDALONE;
    useReplicas = RedisReplicas.NEVER;
    poolName = UUID.randomUUID().toString();
    // thumb guess based on web browser defaults
    poolCleanerInterval = 30_000;
    maxPoolSize = 6;
    maxPoolWaiting = 24;
    poolRecycleTimeout = 180_000;
  }

  /**
   * Creates a default configuration object using redis server defaults
   */
  public RedisOptions() {
    init();
  }

  /**
   * Copy constructor.
   *
   * @param other the object to clone.
   */
  public RedisOptions(RedisOptions other) {
    this.type = other.type;
    this.netClientOptions = other.netClientOptions;
    // pool related options
    this.poolName = other.poolName;
    this.poolCleanerInterval = other.poolCleanerInterval;
    this.maxPoolSize = other.maxPoolSize;
    this.maxPoolWaiting = other.maxPoolWaiting;
    this.poolRecycleTimeout = other.poolRecycleTimeout;
  }

  /**
   * Copy from JSON constructor.
   *
   * @param json source json
   */
  public RedisOptions(JsonObject json) {
    init();
    RedisOptionsConverter.fromJson(json, this);
  }

  /**
   * Get the type of client to be created.
   *
   * @return the desired client type.
   */
  public RedisClientType getType() {
    return type;
  }

  /**
   * Set the desired client type to be created.
   *
   * @param type the client type.
   * @return fluent self.
   */
  public RedisOptions setType(RedisClientType type) {
    this.type = type;
    return this;
  }


  /**
   * Get the net client options used to connect to the server.
   *
   * @return the net socket options.
   */
  public NetClientOptions getNetClientOptions() {
    return netClientOptions;
  }

  /**
   * Set the net client options to be used while connecting to the redis server. Use this to tune your connection.
   *
   * @param netClientOptions custom net client options.
   * @return fluent self.
   */
  public RedisOptions setNetClientOptions(NetClientOptions netClientOptions) {
    this.netClientOptions = netClientOptions;
    return this;
  }

  /**
   * Get whether or not to use replica nodes (only considered in Cluster mode).
   *
   * @return the cluster replica node use mode.
   */
  public RedisReplicas getUseReplicas() {
    return useReplicas;
  }

  /**
   * Set whether or not to use replica nodes (only considered in Cluster mode).
   *
   * @param useReplicas the cluster replica use mode.
   * @return fluent self.
   */
  public RedisOptions setUseReplicas(RedisReplicas useReplicas) {
    this.useReplicas = useReplicas;
    return this;
  }

  /**
   * Tune how often in milliseconds should the connection pool cleaner execute.
   *
   * @return the cleaning internal
   */
  public int getPoolCleanerInterval() {
    return poolCleanerInterval;
  }

  /**
   * Tune how often in milliseconds should the connection pool cleaner execute.
   * <p>
   * For each connection in the pool, connections marked as invalid will be forcibly closed. A connection is marked
   * invalid if it enters a exception or fatal state.
   *
   * @param poolCleanerInterval the interval in milliseconds (-1 for never)
   * @return fluent self.
   */
  public RedisOptions setPoolCleanerInterval(int poolCleanerInterval) {
    this.poolCleanerInterval = poolCleanerInterval;
    return this;
  }

  /**
   * Tune the maximum size of the connection pool.
   *
   * @return the size.
   */
  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  /**
   * Tune the maximum size of the connection pool. When working with cluster or sentinel
   * this value should be atleast the total number of cluster member (or number of sentinels + 1)
   *
   * @param maxPoolSize the max pool size.
   * @return fluent self.
   */
  public RedisOptions setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
    return this;
  }

  /**
   * Tune the maximum waiting requests for a connection from the pool.
   *
   * @return the maximum waiting requests.
   */
  public int getMaxPoolWaiting() {
    return maxPoolWaiting;
  }

  /**
   * Tune the maximum waiting requests for a connection from the pool.
   *
   * @param maxPoolWaiting max waiting requests
   * @return fluent self.
   */
  public RedisOptions setMaxPoolWaiting(int maxPoolWaiting) {
    this.maxPoolWaiting = maxPoolWaiting;
    return this;
  }

  /**
   * Tune when a connection should be recycled in milliseconds.
   *
   * @return the timeout for recycling.
   */
  public int getPoolRecycleTimeout() {
    return poolRecycleTimeout;
  }

  /**
   * Tune when a connection should be recycled in milliseconds.
   *
   * @param poolRecycleTimeout the timeout for recycling.
   * @return fluent self.
   */
  public RedisOptions setPoolRecycleTimeout(int poolRecycleTimeout) {
    this.poolRecycleTimeout = poolRecycleTimeout;
    return this;
  }

  /**
   * Set a user defined pool name (for metrics reporting).
   *
   * @param poolName the user desired pool name.
   * @return fluent self
   */
  public RedisOptions setPoolName(String poolName) {
    this.poolName = poolName;
    return this;
  }

  /**
   * Get the pool name to be used in this client. The default name is a random UUID.
   *
   * @return pool name.
   */
  public String getPoolName() {
    return this.poolName;
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    RedisOptionsConverter.toJson(this, json);
    return json;
  }


  // Overwrite due to inheritance

  /**
   * {@inheritDoc}
   */
  public RedisOptions setEndpoints(List<String> endpoints) {
    return (RedisOptions)super.setEndpoints(endpoints);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions addEndpoint(String connectionString) {
    return (RedisOptions)super.addEndpoint(connectionString);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setEndpoint(String connectionString) {
    return (RedisOptions)super.setEndpoint(connectionString);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions addConnectionString(String connectionString) {
    return (RedisOptions)super.addConnectionString(connectionString);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setConnectionString(String connectionString) {
    return (RedisOptions)super.setConnectionString(connectionString);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    return (RedisOptions)super.setMaxWaitingHandlers(maxWaitingHandlers);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setMasterName(String masterName) {
    return (RedisOptions)super.setMasterName(masterName);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setRole(RedisRole role) {
    return (RedisOptions)super.setRole(role);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setMaxNestedArrays(int maxNestedArrays) {
    return (RedisOptions)super.setMaxNestedArrays(maxNestedArrays);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setPassword(String password) {
    return (RedisOptions)super.setPassword(password);
  }

  /**
   * {@inheritDoc}
   */
  public RedisOptions setProtocolNegotiation(boolean protocolNegotiation) {
    return (RedisOptions)super.setProtocolNegotiation(protocolNegotiation);
  }
}
