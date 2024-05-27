/*
 * Copyright 2024 Red Hat, Inc.
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

import java.util.List;

@DataObject
@JsonGen(publicConverter = false)
public class RedisReplicationConnectOptions extends RedisConnectOptions {

  private RedisTopology topology;
  private RedisReplicas useReplicas;

  public RedisReplicationConnectOptions() {
    super();
    topology = RedisTopology.DISCOVER;
    useReplicas = RedisReplicas.NEVER;
  }

  public RedisReplicationConnectOptions(RedisOptions options) {
    super(options);
    setTopology(options.getTopology());
    setUseReplicas(options.getUseReplicas());
  }

  public RedisReplicationConnectOptions(RedisReplicationConnectOptions other) {
    super(other);
    setTopology(other.getTopology());
    setUseReplicas(other.getUseReplicas());
  }

  public RedisReplicationConnectOptions(JsonObject json) {
    super(json);
    RedisReplicationConnectOptionsConverter.fromJson(json, this);
  }

  /**
   * Get how the {@linkplain RedisTopology topology} should be obtained. By default,
   * the topology is {@linkplain RedisTopology#DISCOVER discovered} automatically.
   *
   * @return the topology mode
   */
  public RedisTopology getTopology() {
    return topology;
  }

  /**
   * Set how the {@linkplain RedisTopology topology} should be obtained. By default,
   * the topology is {@linkplain RedisTopology#DISCOVER discovered} automatically.
   *
   * @param topology the topology mode
   * @return fluent self
   */
  public RedisReplicationConnectOptions setTopology(RedisTopology topology) {
    this.topology = topology;
    return this;
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
  public RedisReplicationConnectOptions setUseReplicas(RedisReplicas useReplicas) {
    this.useReplicas = useReplicas;
    return this;
  }

  @Override
  public RedisReplicationConnectOptions setMaxNestedArrays(int maxNestedArrays) {
    return (RedisReplicationConnectOptions) super.setMaxNestedArrays(maxNestedArrays);
  }

  @Override
  public RedisReplicationConnectOptions setProtocolNegotiation(boolean protocolNegotiation) {
    return (RedisReplicationConnectOptions) super.setProtocolNegotiation(protocolNegotiation);
  }

  @Override
  public RedisReplicationConnectOptions setPreferredProtocolVersion(ProtocolVersion preferredProtocolVersion) {
    return (RedisReplicationConnectOptions) super.setPreferredProtocolVersion(preferredProtocolVersion);
  }

  @Override
  public RedisReplicationConnectOptions setPassword(String password) {
    return (RedisReplicationConnectOptions) super.setPassword(password);
  }

  @Override
  public RedisReplicationConnectOptions setEndpoints(List<String> endpoints) {
    return (RedisReplicationConnectOptions) super.setEndpoints(endpoints);
  }

  @Override
  public RedisReplicationConnectOptions addConnectionString(String connectionString) {
    return (RedisReplicationConnectOptions) super.addConnectionString(connectionString);
  }

  @Override
  public RedisReplicationConnectOptions setConnectionString(String connectionString) {
    return (RedisReplicationConnectOptions) super.setConnectionString(connectionString);
  }

  @Override
  public RedisReplicationConnectOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    return (RedisReplicationConnectOptions) super.setMaxWaitingHandlers(maxWaitingHandlers);
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = super.toJson();
    RedisReplicationConnectOptionsConverter.toJson(this, json);
    return json;
  }
}
