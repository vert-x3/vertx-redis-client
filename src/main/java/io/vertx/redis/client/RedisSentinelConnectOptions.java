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

import java.util.List;

@DataObject
@JsonGen(publicConverter = false)
public class RedisSentinelConnectOptions extends RedisConnectOptions {

  private RedisRole role;
  private String masterName;

  public RedisSentinelConnectOptions() {
    super();
    role = RedisRole.MASTER;
    masterName = "mymaster";
  }

  public RedisSentinelConnectOptions(RedisOptions options) {
    super(options);
    setRole(options.getRole());
    setMasterName(options.getMasterName());
  }

  public RedisSentinelConnectOptions(RedisSentinelConnectOptions other) {
    super(other);
    setRole(other.getRole());
    setMasterName(other.getMasterName());
  }

  public RedisSentinelConnectOptions(JsonObject json) {
    super(json);
    RedisSentinelConnectOptionsConverter.fromJson(json, this);
  }

  /**
   * Get the role name (only considered in HA mode).
   *
   * @return the master name.
   */
  public RedisRole getRole() {
    return role;
  }

  /**
   * Set the role name (only considered in HA mode).
   *
   * @param role the master name.
   * @return fluent self.
   */
  public RedisConnectOptions setRole(RedisRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get the master name (only considered in HA mode).
   *
   * @return the master name.
   */
  public String getMasterName() {
    return masterName;
  }

  /**
   * Set the master name (only considered in HA mode).
   *
   * @param masterName the master name.
   * @return fluent self.
   */
  public RedisConnectOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  @Override
  public RedisSentinelConnectOptions setMaxNestedArrays(int maxNestedArrays) {
    return (RedisSentinelConnectOptions) super.setMaxNestedArrays(maxNestedArrays);
  }

  @Override
  public RedisSentinelConnectOptions setProtocolNegotiation(boolean protocolNegotiation) {
    return (RedisSentinelConnectOptions) super.setProtocolNegotiation(protocolNegotiation);
  }

  @Override
  public RedisSentinelConnectOptions setPreferredProtocolVersion(ProtocolVersion preferredProtocolVersion) {
    return (RedisSentinelConnectOptions) super.setPreferredProtocolVersion(preferredProtocolVersion);
  }

  @Override
  public RedisSentinelConnectOptions setPassword(String password) {
    return (RedisSentinelConnectOptions) super.setPassword(password);
  }

  @Override
  public RedisSentinelConnectOptions setEndpoints(List<String> endpoints) {
    return (RedisSentinelConnectOptions) super.setEndpoints(endpoints);
  }

  @Override
  public RedisSentinelConnectOptions addConnectionString(String connectionString) {
    return (RedisSentinelConnectOptions) super.addConnectionString(connectionString);
  }

  @Override
  public RedisSentinelConnectOptions setConnectionString(String connectionString) {
    return (RedisSentinelConnectOptions) super.setConnectionString(connectionString);
  }

  @Override
  public RedisSentinelConnectOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    return (RedisSentinelConnectOptions) super.setMaxWaitingHandlers(maxWaitingHandlers);
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = super.toJson();
    RedisSentinelConnectOptionsConverter.toJson(this, json);
    return json;
  }
}
