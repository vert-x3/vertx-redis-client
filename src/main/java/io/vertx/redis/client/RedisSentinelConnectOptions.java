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
   * Get the client role; that is, to which kind of node should the connection be established.
   *
   * @return the role
   */
  public RedisRole getRole() {
    return role;
  }

  /**
   * Set the client role; that is, to which kind of node should the connection be established.
   *
   * @param role the role
   * @return fluent self
   */
  public RedisConnectOptions setRole(RedisRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get the name of the master set.
   *
   * @return the master set name
   */
  public String getMasterName() {
    return masterName;
  }

  /**
   * Set the name of the master set.
   *
   * @param masterName the master set name
   * @return fluent self
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
