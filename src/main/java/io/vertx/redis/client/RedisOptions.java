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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Redis Client Configuration options.
 *
 * @author Paulo Lopes
 */
@DataObject(generateConverter = true)
public class RedisOptions {

  private RedisClientType type;
  private NetClientOptions netClientOptions;
  private List<String> endpoints;
  private int maxWaitingHandlers;
  private int maxNestedArrays;
  private String masterName;
  private RedisRole role;
  private RedisSlaves slaves;

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
    type = RedisClientType.STANDALONE;
  }

  /**
   * Creates a default configuration object using redis server defaults
   */
  public RedisOptions() {
    init();
  }

  /**
   * Copy constructor.
   * @param other the object to clone.
   */
  public RedisOptions(RedisOptions other) {
    this.type = other.type;
    this.netClientOptions = other.netClientOptions;
    this.endpoints = other.endpoints;
    this.maxWaitingHandlers = other.maxWaitingHandlers;
    this.maxNestedArrays = other.maxNestedArrays;
    this.masterName = other.masterName;
    this.role = other.role;
    this.slaves = other.slaves;
  }

  /**
   * Copy from JSON constructor.
   * @param json source json
   */
  public RedisOptions(JsonObject json) {
    init();
    RedisOptionsConverter.fromJson(json, this);
  }

  /**
   * Get the type of client to be created.
   * @return the desired client type.
   */
  public RedisClientType getType() {
    return type;
  }

  /**
   * Set the desired client type to be created.
   * @param type the client type.
   * @return fluent self.
   */
  public RedisOptions setType(RedisClientType type) {
    this.type = type;
    return this;
  }


  /**
   * Get the net client options used to connect to the server.
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
   * Gets the list of redis endpoints to use (mostly used while connecting to a cluster)
   * @return list of socket addresses.
   */
  public List<String> getEndpoints() {
    return endpoints;
  }

  /**
   * Gets the redis endpoint to use
   * @return socket address.
   */
  public String getEndpoint() {
    if (endpoints == null || endpoints.size() == 0) {
      return "redis://localhost:6379";
    }

    return endpoints.get(0);
  }

  /**
   * Set the endpoints to use while connecting to the redis server. Only the cluster mode will consider more than
   * 1 element. If more are provided, they are not considered by the client when in single server mode.
   *
   * @param endpoints list of socket addresses.
   * @return fluent self.
   */
  public RedisOptions setEndpoints(List<String> endpoints) {
    this.endpoints = endpoints;
    return this;
  }

  /**
   * Adds a endpoint to use while connecting to the redis server. Only the cluster mode will consider more than
   * 1 element. If more are provided, they are not considered by the client when in single server mode.
   *
   * @param endpoint a socket addresses.
   * @return fluent self.
   */
  public RedisOptions addEndpoint(String endpoint) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    }
    this.endpoints.add(endpoint);
    return this;
  }

  /**
   * Sets a single endpoint to use while connecting to the redis server. Will replace the previously configured endpoints.
   *
   * @param endpoint a socket addresses.
   * @return fluent self.
   */
  public RedisOptions setEndpoint(String endpoint) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    } else {
      endpoints.clear();
    }

    this.endpoints.add(endpoint);
    return this;
  }

  /**
   * The client will always work on pipeline mode, this means that messages can start queueing. You can control how much
   * backlog you're willing to accept. This methods returns how much handlers is the client willing to queue.
   *
   * @return max allowed queued waiting handlers.
   */
  public int getMaxWaitingHandlers() {
    return maxWaitingHandlers;
  }

  /**
   * The client will always work on pipeline mode, this means that messages can start queueing. You can control how much
   * backlog you're willing to accept. This methods sets how much handlers is the client willing to queue.
   *
   * @param maxWaitingHandlers max allowed queued waiting handlers.
   * @return fluent self.
   */
  public RedisOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    this.maxWaitingHandlers = maxWaitingHandlers;
    return this;
  }

  /**
   * Get the master name (only considered in HA mode).
   * @return the master name.
   */
  public String getMasterName() {
    return masterName;
  }

  /**
   * Set the master name (only considered in HA mode).
   * @param masterName the master name.
   * @return fluent self.
   */
  public RedisOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  /**
   * Get the role name (only considered in HA mode).
   * @return the master name.
   */
  public RedisRole getRole() {
    return role;
  }

  /**
   * Set the role name (only considered in HA mode).
   * @param role the master name.
   * @return fluent self.
   */
  public RedisOptions setRole(RedisRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get whether or not to use slave nodes (only considered in Cluster mode).
   * @return the cluster slave mode.
   */
  public RedisSlaves getUseSlave() {
    return slaves;
  }

  /**
   * Set whether or not to use slave nodes (only considered in Cluster mode).
   * @param slaves the cluster slave mode.
   * @return fluent self.
   */
  public RedisOptions setUseSlave(RedisSlaves slaves) {
    this.slaves = slaves;
    return this;
  }


  /**
   * Tune how much nested arrays are allowed on a redis response. This affects the parser performance.
   * @return the configured max nested arrays allowance.
   */
  public int getMaxNestedArrays() {
    return maxNestedArrays;
  }

  /**
   * Tune how much nested arrays are allowed on a redis response. This affects the parser performance.
   * @param maxNestedArrays the configured max nested arrays allowance.
   * @return fluent self.
   */
  public RedisOptions setMaxNestedArrays(int maxNestedArrays) {
    this.maxNestedArrays = maxNestedArrays;
    return this;
  }

  /**
   * Converts this object to JSON notation.
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    RedisOptionsConverter.toJson(this, json);
    return json;
  }

  public static void main(String[] args) throws URISyntaxException {

    List<String> uris = Arrays.asList(
      "redis://localhost",
      "redis://localhost:7936",
      "redis://user:password@localhost:7936",
      "redis://user@localhost:7936",
      "redis://:password@localhost:7936",
      "redis://localhost:7936/1",
      "redis:///tmp/redis.sock",
      "unix:///tmp/redis.sock?select=1");

    for (String uri : uris) {
      URI conn = new URI(uri);
      System.out.println(conn);
      System.out.println(conn.getScheme());
      System.out.println(conn.getUserInfo());
      System.out.println(conn.getHost());
      System.out.println(conn.getPort());
      System.out.println(conn.getPath());
      System.out.println(conn.getQuery());
      System.out.println("---");
    }
  }
}
