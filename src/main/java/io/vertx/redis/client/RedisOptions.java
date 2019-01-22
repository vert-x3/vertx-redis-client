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
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;

import java.util.ArrayList;
import java.util.List;

@DataObject(generateConverter = true)
public class RedisOptions {

  private NetClientOptions netClientOptions;
  private List<SocketAddress> endpoints;
  private int maxWaitingHandlers;
  private int maxNestedArrays;
  private String masterName;
  private RedisRole role;
  private RedisSlaves slaves;
  private String password;
  private Integer select;

  private void init() {
    netClientOptions =
      new NetClientOptions()
        .setTcpKeepAlive(true)
        .setTcpNoDelay(true);

    maxWaitingHandlers = 2048;
    maxNestedArrays = 32;
    masterName = "mymaster";
    role = RedisRole.MASTER;
    slaves = RedisSlaves.NEVER;
  }

  public RedisOptions() {
    init();
  }

  public RedisOptions(RedisOptions other) {
    this.netClientOptions = other.netClientOptions;
    this.endpoints = other.endpoints;
    this.maxWaitingHandlers = other.maxWaitingHandlers;
    this.maxNestedArrays = other.maxNestedArrays;
    this.masterName = other.masterName;
    this.role = other.role;
    this.slaves = other.slaves;
    this.password = other.password;
    this.select = other.select;
  }

  public RedisOptions(JsonObject json) {
    init();
    RedisOptionsConverter.fromJson(json, this);
  }

  public NetClientOptions getNetClientOptions() {
    return netClientOptions;
  }

  public RedisOptions setNetClientOptions(NetClientOptions netClientOptions) {
    this.netClientOptions = netClientOptions;
    return this;
  }

  public List<SocketAddress> getEndpoints() {
    return endpoints;
  }

  public SocketAddress getEndpoint() {
    if (endpoints == null || endpoints.size() == 0) {
      return SocketAddress.inetSocketAddress(6379, "localhost");
    }

    return endpoints.get(0);
  }

  public RedisOptions setEndpoints(List<SocketAddress> endpoints) {
    this.endpoints = endpoints;
    return this;
  }

  public RedisOptions addEndpoint(SocketAddress endpoint) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    }
    this.endpoints.add(endpoint);
    return this;
  }

  public RedisOptions setEndpoint(SocketAddress endpoint) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    } else {
      endpoints.clear();
    }

    this.endpoints.add(endpoint);
    return this;
  }

  public int getMaxWaitingHandlers() {
    return maxWaitingHandlers;
  }

  public RedisOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    this.maxWaitingHandlers = maxWaitingHandlers;
    return this;
  }

  public String getMasterName() {
    return masterName;
  }

  public RedisOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  public RedisRole getRole() {
    return role;
  }

  public RedisOptions setRole(RedisRole role) {
    this.role = role;
    return this;
  }

  public RedisSlaves getUseSlave() {
    return slaves;
  }

  public RedisOptions setUseSlave(RedisSlaves slaves) {
    this.slaves = slaves;
    return this;
  }


  public int getMaxNestedArrays() {
    return maxNestedArrays;
  }

  public RedisOptions setMaxNestedArrays(int maxNestedArrays) {
    this.maxNestedArrays = maxNestedArrays;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public RedisOptions setPassword(String password) {
    this.password = password;
    return this;
  }

  public Integer getSelect() {
    return select;
  }

  public RedisOptions setSelect(Integer select) {
    this.select = select;
    return this;
  }

  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    RedisOptionsConverter.toJson(this, json);
    return json;
  }
}
