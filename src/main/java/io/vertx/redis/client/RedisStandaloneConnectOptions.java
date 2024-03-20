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
public class RedisStandaloneConnectOptions extends RedisConnectOptions {

  public RedisStandaloneConnectOptions() {
    super();
  }

  public RedisStandaloneConnectOptions(RedisOptions options) {
    super(options);
  }

  public RedisStandaloneConnectOptions(RedisStandaloneConnectOptions other) {
    super(other);
  }

  public RedisStandaloneConnectOptions(JsonObject json) {
    super(json);
    RedisStandaloneConnectOptionsConverter.fromJson(json, this);
  }

  @Override
  public RedisStandaloneConnectOptions setMaxNestedArrays(int maxNestedArrays) {
    return (RedisStandaloneConnectOptions) super.setMaxNestedArrays(maxNestedArrays);
  }

  @Override
  public RedisStandaloneConnectOptions setProtocolNegotiation(boolean protocolNegotiation) {
    return (RedisStandaloneConnectOptions) super.setProtocolNegotiation(protocolNegotiation);
  }

  @Override
  public RedisStandaloneConnectOptions setPreferredProtocolVersion(ProtocolVersion preferredProtocolVersion) {
    return (RedisStandaloneConnectOptions) super.setPreferredProtocolVersion(preferredProtocolVersion);
  }

  @Override
  public RedisStandaloneConnectOptions setPassword(String password) {
    return (RedisStandaloneConnectOptions) super.setPassword(password);
  }

  @Override
  public RedisStandaloneConnectOptions setEndpoints(List<String> endpoints) {
    return (RedisStandaloneConnectOptions) super.setEndpoints(endpoints);
  }

  @Override
  public RedisStandaloneConnectOptions addConnectionString(String connectionString) {
    return (RedisStandaloneConnectOptions) super.addConnectionString(connectionString);
  }

  @Override
  public RedisStandaloneConnectOptions setConnectionString(String connectionString) {
    return (RedisStandaloneConnectOptions) super.setConnectionString(connectionString);
  }

  @Override
  public RedisStandaloneConnectOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    return (RedisStandaloneConnectOptions) super.setMaxWaitingHandlers(maxWaitingHandlers);
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = super.toJson();
    RedisStandaloneConnectOptionsConverter.toJson(this, json);
    return json;
  }
}
