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
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.impl.RedisSentinelConnection;

import java.util.List;

@DataObject
@JsonGen(publicConverter = false)
public class RedisSentinelConnectOptions extends RedisConnectOptions {

  private RedisRole role;
  private String masterName;
  private boolean autoFailover;
  private long topologyCacheTTL;

  public RedisSentinelConnectOptions() {
    super();
    role = RedisRole.MASTER;
    masterName = "mymaster";
    topologyCacheTTL = 1000;
  }

  public RedisSentinelConnectOptions(RedisOptions options) {
    super(options);
    setRole(options.getRole());
    setMasterName(options.getMasterName());
    setAutoFailover(options.isAutoFailover());
    setTopologyCacheTTL(options.getTopologyCacheTTL());
  }

  public RedisSentinelConnectOptions(RedisSentinelConnectOptions other) {
    super(other);
    setRole(other.getRole());
    setMasterName(other.getMasterName());
    setAutoFailover(other.isAutoFailover());
    setTopologyCacheTTL(other.getTopologyCacheTTL());
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
  public RedisSentinelConnectOptions setRole(RedisRole role) {
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
  public RedisSentinelConnectOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  /**
   * Returns whether automatic failover is enabled. This only makes sense for sentinel clients
   * with role of {@link RedisRole#MASTER} and is ignored otherwise.
   * <p>
   * If enabled, the sentinel client will additionally create a connection to one sentinel node
   * and watch for failover events. When new master is elected, all connections to the old master
   * are automatically closed and new connections to the new master are created. Note that
   * these new connections will <em>not</em> have the same event handlers
   * ({@link RedisConnection#handler(Handler) handler()},
   * {@link RedisConnection#exceptionHandler(Handler) exceptionHandler()} and
   * {@link RedisConnection#endHandler(Handler) endHandler()}), will <em>not</em> be
   * in the same streaming mode ({@link RedisConnection#pause() pause()},
   * {@link RedisConnection#resume() resume()} and {@link RedisConnection#fetch(long) fetch()}),
   * and will <em>not</em> watch the same subscriptions ({@code SUBSCRIBE}, {@code PSUBSCRIBE}, etc.)
   * as the old ones. In other words, automatic failover makes sense for connections executing
   * regular commands, but not for connections used to subscribe to Redis pub/sub channels.
   * <p>
   * Note that there is a brief period of time between the old master failing and the new
   * master being elected when the existing connections will temporarily fail all operations.
   * After the new master is elected, the connections will automatically fail over and
   * start working again.
   *
   * @return whether automatic failover is enabled
   */
  public boolean isAutoFailover() {
    return autoFailover;
  }

  /**
   * Sets whether automatic failover is enabled. This only makes sense for sentinel clients
   * with role of {@link RedisRole#MASTER} and is ignored otherwise.
   * <p>
   * If enabled, the sentinel client will additionally create a connection to one sentinel node
   * and watch for failover events. When new master is elected, all connections to the old master
   * are automatically closed and new connections to the new master are created. Note that
   * these new connections will <em>not</em> have the same event handlers
   * ({@link RedisConnection#handler(Handler) handler()},
   * {@link RedisConnection#exceptionHandler(Handler) exceptionHandler()} and
   * {@link RedisConnection#endHandler(Handler) endHandler()}), will <em>not</em> be
   * in the same streaming mode ({@link RedisConnection#pause() pause()},
   * {@link RedisConnection#resume() resume()} and {@link RedisConnection#fetch(long) fetch()}),
   * and will <em>not</em> watch the same subscriptions ({@code SUBSCRIBE}, {@code PSUBSCRIBE}, etc.)
   * as the old ones. In other words, automatic failover makes sense for connections executing
   * regular commands, but not for connections used to subscribe to Redis pub/sub channels.
   * <p>
   * Note that there is a brief period of time between the old master failing and the new
   * master being elected when the existing connections will temporarily fail all operations.
   * After the new master is elected, the connections will automatically fail over and
   * start working again.
   *
   * @param autoFailover whether automatic failover should be enabled
   * @return fluent self
   */
  public RedisSentinelConnectOptions setAutoFailover(boolean autoFailover) {
    this.autoFailover = autoFailover;
    return this;
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

  /**
   * Sets the TTL of the topology cache. The TTL is expressed in milliseconds.
   * Defaults to 1000 millis (1 second).
   *
   * @param topologyCacheTTL the TTL of the hash topology cache, in millis
   */
  public RedisSentinelConnectOptions setTopologyCacheTTL(long topologyCacheTTL) {
    this.topologyCacheTTL = topologyCacheTTL;
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
  public RedisSentinelConnectOptions setUser(String user) {
    return (RedisSentinelConnectOptions) super.setUser(user);
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
