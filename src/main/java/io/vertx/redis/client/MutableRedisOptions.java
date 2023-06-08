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

import io.vertx.redis.client.impl.RedisURI;

import java.util.ArrayList;
import java.util.List;

public class MutableRedisOptions {

  /**
   * The default redis endpoint = {@code redis://localhost:6379}
   */
  public static final String DEFAULT_ENDPOINT = "redis://localhost:6379";
  private List<String> endpoints;
  private int maxWaitingHandlers;
  private int maxNestedArrays;
  private String masterName;
  private RedisRole role;
  private String user;
  private RedisReplicas useReplicas;
  private volatile String password;
  private boolean protocolNegotiation;

  private void init() {
    protocolNegotiation = true;
    maxWaitingHandlers = 2048;
    maxNestedArrays = 32;
    masterName = "mymaster";
    role = RedisRole.MASTER;
    useReplicas = RedisReplicas.NEVER;
  }

  /**
   * Creates a default configuration object using redis server defaults
   */
  public MutableRedisOptions() {
    init();
  }

  /**
   * Copy constructor.
   *
   * @param other the object to clone.
   */
  public MutableRedisOptions(MutableRedisOptions other) {
    this.endpoints = other.endpoints;
    this.maxWaitingHandlers = other.maxWaitingHandlers;
    this.maxNestedArrays = other.maxNestedArrays;
    this.masterName = other.masterName;
    this.role = other.role;
    this.useReplicas = other.useReplicas;
    this.password = other.password;
    this.protocolNegotiation = other.protocolNegotiation;
  }

  /**
   * Gets the list of redis endpoints to use (mostly used while connecting to a cluster)
   *
   * @return list of socket addresses.
   */
  public List<String> getEndpoints() {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
      endpoints.add(DEFAULT_ENDPOINT);
    }
    return endpoints;
  }

  /**
   * Gets the redis endpoint to use
   *
   * @return the Redis connection string URI
   */
  public String getEndpoint() {
    if (endpoints == null || endpoints.isEmpty()) {
      return DEFAULT_ENDPOINT;
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
  public MutableRedisOptions setEndpoints(List<String> endpoints) {
    this.endpoints = endpoints;
    return this;
  }

  /**
   * Adds an endpoint to use while connecting to the redis server. Only the cluster mode will consider more than
   * 1 element. If more are provided, they are not considered by the client when in single server mode.
   *
   * @param connectionString a string URI following the scheme: redis://[username:password@][host][:port][/database]
   * @return fluent self.
   * @deprecated see {@link #setConnectionString(String connectionString)} for a better naming
   */
  @Deprecated
  public MutableRedisOptions addEndpoint(String connectionString) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    }
    this.endpoints.add(connectionString);
    return this;
  }

  /**
   * Sets a single connection string to use while connecting to the redis server.
   * Will replace the previously configured connection strings.
   *
   * @param connectionString a string following the scheme: redis://[username:password@][host][:port][/[database]
   * @return fluent self.
   * @deprecated see {@link #setConnectionString(String connectionString)} for a better naming
   */
  @Deprecated
  public MutableRedisOptions setEndpoint(String connectionString) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    } else {
      endpoints.clear();
    }

    this.endpoints.add(connectionString);
    return this;
  }

  /**
   * Adds a connection string (endpoint) to use while connecting to the redis server. Only the cluster mode will
   * consider more than 1 element. If more are provided, they are not considered by the client when in single server mode.
   *
   * @param connectionString a string URI following the scheme: redis://[username:password@][host][:port][/database]
   * @return fluent self.
   * @see <a href="https://www.iana.org/assignments/uri-schemes/prov/redis">Redis scheme on iana.org</a>
   */
  public MutableRedisOptions addConnectionString(String connectionString) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    }
    this.endpoints.add(connectionString);
    return this;
  }

  /**
   * Sets a single connection string (endpoint) to use while connecting to the redis server.
   * Will replace the previously configured connection strings.
   *
   * @param connectionString a string following the scheme: redis://[username:password@][host][:port][/[database].
   * @return fluent self.
   * @see <a href="https://www.iana.org/assignments/uri-schemes/prov/redis">Redis scheme on iana.org</a>
   */
  public MutableRedisOptions setConnectionString(String connectionString) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    } else {
      endpoints.clear();
    }

    this.endpoints.add(connectionString);
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
  public MutableRedisOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    this.maxWaitingHandlers = maxWaitingHandlers;
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
  public MutableRedisOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
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
  public MutableRedisOptions setRole(RedisRole role) {
    this.role = role;
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
  public MutableRedisOptions setUseReplicas(RedisReplicas useReplicas) {
    this.useReplicas = useReplicas;
    return this;
  }

  /**
   * Tune how much nested arrays are allowed on a redis response. This affects the parser performance.
   *
   * @return the configured max nested arrays allowance.
   */
  public int getMaxNestedArrays() {
    return maxNestedArrays;
  }

  /**
   * Tune how much nested arrays are allowed on a redis response. This affects the parser performance.
   *
   * @param maxNestedArrays the configured max nested arrays allowance.
   * @return fluent self.
   */
  public MutableRedisOptions setMaxNestedArrays(int maxNestedArrays) {
    this.maxNestedArrays = maxNestedArrays;
    return this;
  }

  /**
   * Get the user information for cluster/sentinel connections, if not set it will try to
   * extract it from the current default endpoint.
   *
   * @return user
   */
  public String getUser() {
    return user;
  }

  /**
   * Set the user for cluster/sentinel connections.
   *
   * @param user the user
   * @return fluent self
   */
  public MutableRedisOptions setUser(final String user) {
    this.user = user;
    return this;
  }

  /**
   * Get the default password for cluster/sentinel connections, if not set it will try to
   * extract it from the current default endpoint.
   *
   * @return password
   */
  public String getPassword() {
    if (password == null) {
      // try to fetch it from the endpoint
      synchronized (this) {
        if (password == null) {
          RedisURI uri = new RedisURI(getEndpoint());
          // use the parsed value
          password = uri.password();
        }
      }
    }
    return password;
  }

  /**
   * Set the default password for cluster/sentinel connections.
   *
   * @param password the default password
   * @return fluent self
   */
  public MutableRedisOptions setPassword(String password) {
    this.password = password;
    return this;
  }

  /**
   * Should the client perform {@code RESP} protocol negotiation during the connection handshake.
   * By default this is {@code true}, but there are situations when using broken servers it may
   * be useful to skip this and always fallback to {@code RESP2} without using the {@code HELLO}
   * command.
   *
   * @return true to perform negotiation.
   */
  public boolean isProtocolNegotiation() {
    return protocolNegotiation;
  }

  /**
   * Should the client perform {@code REST} protocol negotiation during the connection acquire.
   * By default this is {@code true}, but there are situations when using broken servers it may
   * be useful to skip this and always fallback to {@code RESP2} without using the {@code HELLO}
   * command.
   *
   * @param protocolNegotiation false to disable {@code HELLO} (not recommended) unless reasons...
   * @return fluent self
   */
  public MutableRedisOptions setProtocolNegotiation(boolean protocolNegotiation) {
    this.protocolNegotiation = protocolNegotiation;
    return this;
  }
}
