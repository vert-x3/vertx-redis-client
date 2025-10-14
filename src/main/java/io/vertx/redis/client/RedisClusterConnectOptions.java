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
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

import java.util.List;

@DataObject
@JsonGen(publicConverter = false)
public class RedisClusterConnectOptions extends RedisConnectOptions {

  private RedisReplicas useReplicas;
  private RedisClusterTransactions clusterTransactions;
  private long topologyCacheTTL;

  public RedisClusterConnectOptions() {
    super();
    useReplicas = RedisReplicas.NEVER;
    clusterTransactions = RedisClusterTransactions.DISABLED;
    topologyCacheTTL = 1000;
  }

  public RedisClusterConnectOptions(RedisOptions options) {
    super(options);
    setUseReplicas(options.getUseReplicas());
    setClusterTransactions(options.getClusterTransactions());
    setTopologyCacheTTL(options.getTopologyCacheTTL());
  }

  public RedisClusterConnectOptions(RedisClusterConnectOptions other) {
    super(other);
    setUseReplicas(other.getUseReplicas());
    setClusterTransactions(other.getClusterTransactions());
    setTopologyCacheTTL(other.getTopologyCacheTTL());
  }

  public RedisClusterConnectOptions(JsonObject json) {
    super(json);
    RedisClusterConnectOptionsConverter.fromJson(json, this);
  }

  /**
   * Get whether to use replica nodes for read only queries.
   *
   * @return the cluster replica node use mode.
   */
  public RedisReplicas getUseReplicas() {
    return useReplicas;
  }

  /**
   * Set whether to use replica nodes for read only queries.
   *
   * @param useReplicas the cluster replica use mode.
   * @return fluent self.
   */
  public RedisClusterConnectOptions setUseReplicas(RedisReplicas useReplicas) {
    this.useReplicas = useReplicas;
    return this;
  }

  @Deprecated
  @GenIgnore
  public long getHashSlotCacheTTL() {
    return getTopologyCacheTTL();
  }

  /**
   * Returns the TTL of the topology cache. The TTL is expressed in milliseconds.
   * Defaults to 1000 millis (1 second).
   *
   * @return the TTL of the topology cache
   */
  public long getTopologyCacheTTL() {
    return topologyCacheTTL;
  }

  @Deprecated
  @GenIgnore
  public RedisClusterConnectOptions setHashSlotCacheTTL(long hashSlotCacheTTL) {
    return setTopologyCacheTTL(hashSlotCacheTTL);
  }

  /**
   * Sets the TTL of the hash slot cache. The TTL is expressed in milliseconds.
   * Defaults to 1000 millis (1 second).
   *
   * @param topologyCacheTTL the TTL of the hash slot cache, in millis
   */
  public RedisClusterConnectOptions setTopologyCacheTTL(long topologyCacheTTL) {
    this.topologyCacheTTL = topologyCacheTTL;
    return this;
  }

  /**
   * Get how Redis transactions are handled in cluster mode.
   *
   * @return how transactions are handled
   */
  public RedisClusterTransactions getClusterTransactions() {
    return clusterTransactions;
  }

  /**
   * Set how Redis transactions are handled in cluster mode.
   *
   * @param clusterTransactions transaction handling mode
   * @return fluent self
   */
  public RedisClusterConnectOptions setClusterTransactions(RedisClusterTransactions clusterTransactions) {
    this.clusterTransactions = clusterTransactions;
    return this;
  }

  @Override
  public RedisClusterConnectOptions setMaxNestedArrays(int maxNestedArrays) {
    return (RedisClusterConnectOptions) super.setMaxNestedArrays(maxNestedArrays);
  }

  @Override
  public RedisClusterConnectOptions setProtocolNegotiation(boolean protocolNegotiation) {
    return (RedisClusterConnectOptions) super.setProtocolNegotiation(protocolNegotiation);
  }

  @Override
  public RedisClusterConnectOptions setPreferredProtocolVersion(ProtocolVersion preferredProtocolVersion) {
    return (RedisClusterConnectOptions) super.setPreferredProtocolVersion(preferredProtocolVersion);
  }

  @Override
  public RedisClusterConnectOptions setUser(String user) {
    return (RedisClusterConnectOptions) super.setUser(user);
  }

  @Override
  public RedisClusterConnectOptions setPassword(String password) {
    return (RedisClusterConnectOptions) super.setPassword(password);
  }

  @Override
  public RedisClusterConnectOptions setEndpoints(List<String> endpoints) {
    return (RedisClusterConnectOptions) super.setEndpoints(endpoints);
  }

  @Override
  public RedisClusterConnectOptions addConnectionString(String connectionString) {
    return (RedisClusterConnectOptions) super.addConnectionString(connectionString);
  }

  @Override
  public RedisClusterConnectOptions setConnectionString(String connectionString) {
    return (RedisClusterConnectOptions) super.setConnectionString(connectionString);
  }

  @Override
  public RedisClusterConnectOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    return (RedisClusterConnectOptions) super.setMaxWaitingHandlers(maxWaitingHandlers);
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = super.toJson();
    RedisClusterConnectOptionsConverter.toJson(this, json);
    return json;
  }
}
