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

import java.util.List;

@DataObject(generateConverter = true)
public class RedisClusterConnectOptions extends RedisConnectOptions {

  private RedisReplicas useReplicas;

  public RedisClusterConnectOptions(RedisOptions options) {
    super(options);
    setUseReplicas(options.getUseReplicas());
  }

  public RedisClusterConnectOptions() {
    useReplicas = RedisReplicas.NEVER;
  }

  public RedisClusterConnectOptions(RedisClusterConnectOptions other) {
    this.useReplicas = other.useReplicas;
  }

  public RedisClusterConnectOptions(JsonObject json) {
    this();
    RedisConnectOptionsConverter.fromJson(json, this);
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
  public RedisConnectOptions setUseReplicas(RedisReplicas useReplicas) {
    this.useReplicas = useReplicas;
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
  public RedisClusterConnectOptions setPassword(String password) {
    return (RedisClusterConnectOptions) super.setPassword(password);
  }

  @Override
  public RedisClusterConnectOptions setEndpoints(List<String> endpoints) {
    return (RedisClusterConnectOptions) super.setEndpoints(endpoints);
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
