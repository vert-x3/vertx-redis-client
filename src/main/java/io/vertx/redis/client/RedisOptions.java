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
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.impl.RedisURI;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis Client Configuration options.
 *
 * @author Paulo Lopes
 */
@DataObject
@JsonGen(publicConverter = false)
public class RedisOptions {

  /**
   * The default Redis endpoint: {@code redis://localhost:6379}
   */
  public static final String DEFAULT_ENDPOINT = "redis://localhost:6379";

  private RedisClientType type;
  private NetClientOptions netClientOptions;
  private List<String> endpoints;
  private RedisTopology topology;
  private PoolOptions poolOptions;
  private int maxWaitingHandlers;
  private int maxNestedArrays;
  private String masterName;
  private RedisRole role;
  private RedisReplicas useReplicas;
  private volatile String password;
  private boolean protocolNegotiation;
  private ProtocolVersion preferredProtocolVersion;
  private long hashSlotCacheTTL;
  private TracingPolicy tracingPolicy;
  private boolean autoFailover;

  /**
   * Creates a default configuration object using redis server defaults
   */
  public RedisOptions() {
    type = RedisClientType.STANDALONE;
    netClientOptions =
      new NetClientOptions()
        .setTcpKeepAlive(true)
        .setTcpNoDelay(true);
    topology = RedisTopology.DISCOVER;
    poolOptions = new PoolOptions();
    maxWaitingHandlers = 2048;
    maxNestedArrays = 32;
    masterName = "mymaster";
    role = RedisRole.MASTER;
    useReplicas = RedisReplicas.NEVER;
    protocolNegotiation = true;
    hashSlotCacheTTL = 1000;
  }

  /**
   * Copy constructor.
   *
   * @param other the object to clone.
   */
  public RedisOptions(RedisOptions other) {
    this();
    this.type = other.type;
    this.netClientOptions = other.netClientOptions;
    this.endpoints = other.endpoints;
    this.topology = other.topology;
    this.poolOptions = new PoolOptions(other.poolOptions);
    this.maxWaitingHandlers = other.maxWaitingHandlers;
    this.maxNestedArrays = other.maxNestedArrays;
    this.masterName = other.masterName;
    this.role = other.role;
    this.useReplicas = other.useReplicas;
    this.password = other.password;
    this.protocolNegotiation = other.protocolNegotiation;
    this.preferredProtocolVersion = other.preferredProtocolVersion;
    this.hashSlotCacheTTL = other.hashSlotCacheTTL;
    this.tracingPolicy = other.tracingPolicy;
    this.autoFailover = other.autoFailover;
  }

  /**
   * Copy from JSON constructor.
   *
   * @param json source json
   */
  public RedisOptions(JsonObject json) {
    this();
    RedisOptionsConverter.fromJson(json, this);
  }

  /**
   * Get the type of client to be created.
   *
   * @return the desired client type.
   */
  public RedisClientType getType() {
    return type;
  }

  /**
   * Set the desired client type to be created.
   *
   * @param type the client type.
   * @return fluent self.
   */
  public RedisOptions setType(RedisClientType type) {
    this.type = type;
    return this;
  }


  /**
   * Get the net client options used to connect to the server.
   *
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
   * Gets the redis endpoint to use
   *
   * @return the Redis connection string URI
   */
  @GenIgnore
  public String getEndpoint() {
    if (endpoints == null || endpoints.isEmpty()) {
      return DEFAULT_ENDPOINT;
    }

    return endpoints.get(0);
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
  @GenIgnore
  public RedisOptions addEndpoint(String connectionString) {
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
  @GenIgnore
  public RedisOptions setEndpoint(String connectionString) {
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
  @GenIgnore
  public RedisOptions addConnectionString(String connectionString) {
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
  @GenIgnore
  public RedisOptions setConnectionString(String connectionString) {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    } else {
      endpoints.clear();
    }

    this.endpoints.add(connectionString);
    return this;
  }

  /**
   * Get how the {@linkplain RedisTopology topology} should be obtained. By default,
   * the topology is {@linkplain RedisTopology#DISCOVER discovered} automatically.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#REPLICATION replication}
   * Redis client. In case of a {@linkplain RedisClientType#CLUSTER cluster} and
   * {@linkplain RedisClientType#SENTINEL sentinel} Redis client, topology is currently
   * always discovered automatically and the topology mode is ignored.
   * </p>
   *
   * @return the topology mode
   */
  public RedisTopology getTopology() {
    return topology;
  }

  /**
   * Set how the {@linkplain RedisTopology topology} should be obtained. By default,
   * the topology is {@linkplain RedisTopology#DISCOVER discovered} automatically.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#REPLICATION replication}
   * Redis client. In case of a {@linkplain RedisClientType#CLUSTER cluster} and
   * {@linkplain RedisClientType#SENTINEL sentinel} Redis client, topology is currently
   * always discovered automatically and the topology mode is ignored.
   * </p>
   *
   * @param topology the topology mode
   * @return fluent self
   */
  public RedisOptions setTopology(RedisTopology topology) {
    this.topology = topology;
    return this;
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
   * Get the name of the master set.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#SENTINEL sentinel} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @return the master set name
   */
  public String getMasterName() {
    return masterName;
  }

  /**
   * Set the name of the master set.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#SENTINEL sentinel} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @param masterName the master set name
   * @return fluent self
   */
  public RedisOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  /**
   * Get the client role; that is, to which kind of node should the connection be established.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#SENTINEL sentinel} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @return the role
   */
  public RedisRole getRole() {
    return role;
  }

  /**
   * Set the client role; that is, to which kind of node should the connection be established.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#SENTINEL sentinel} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @param role the role
   * @return fluent self
   */
  public RedisOptions setRole(RedisRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get whether to use replica nodes for read only queries.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#REPLICATION replication} and
   * {@linkplain RedisClientType#CLUSTER cluster} Redis client and is ignored otherwise.
   * </p>
   *
   * @return the cluster replica node use mode.
   */
  public RedisReplicas getUseReplicas() {
    return useReplicas;
  }

  /**
   * Set whether to use replica nodes for read only queries.
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#REPLICATION replication} and
   * {@linkplain RedisClientType#CLUSTER cluster} Redis client and is ignored otherwise.
   * </p>
   *
   * @param useReplicas the cluster replica use mode.
   * @return fluent self.
   */
  public RedisOptions setUseReplicas(RedisReplicas useReplicas) {
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
  public RedisOptions setMaxNestedArrays(int maxNestedArrays) {
    this.maxNestedArrays = maxNestedArrays;
    return this;
  }

  /**
   * @return the tracing policy
   */
  public TracingPolicy getTracingPolicy() {
    return tracingPolicy;
  }

  /**
   * Set the tracing policy for the client behavior when Vert.x has tracing enabled.
   *
   * @param tracingPolicy the tracing policy
   * @return fluent self.
   */
  public RedisOptions setTracingPolicy(TracingPolicy tracingPolicy) {
    this.tracingPolicy = tracingPolicy;
    return this;
  }

  /**
   * @return the pool options
   */
  @GenIgnore
  public PoolOptions getPoolOptions() {
    return poolOptions;
  }

  /**
   * Get how often the connection pool will be cleaned. Cleaning the connection pool
   * means scanning for unused and invalid connections and if any are found, they are forcibly
   * closed and evicted from the pool.
   * <p>
   * A connection is marked invalid if it enters a exception or fatal state. It is marked unused
   * if it is unused for longer than the {@linkplain #getPoolRecycleTimeout() recycle timeout}.
   * <p>
   * The return value is in milliseconds. By default, the cleaning interval is 30 seconds.
   * The value of -1 means connection pool cleaning is disabled.
   *
   * @return the cleaning interval in milliseconds, or -1 for never
   */
  public int getPoolCleanerInterval() {
    return poolOptions.getCleanerInterval();
  }

  /**
   * Set how often the connection pool will be cleaned. Cleaning the connection pool
   * means scanning for unused and invalid connections and if any are found, they are forcibly
   * closed and evicted from the pool.
   * <p>
   * A connection is marked invalid if it enters a exception or fatal state. It is marked unused
   * if it is unused for longer than the {@linkplain #setPoolRecycleTimeout(int) recycle timeout}.
   * <p>
   * The value is in milliseconds. By default, the cleaning interval is 30 seconds.
   * The value of -1 means connection pool cleaning is disabled.
   *
   * @param poolCleanerInterval the cleaning interval in milliseconds, or -1 for never
   * @return fluent self
   */
  public RedisOptions setPoolCleanerInterval(int poolCleanerInterval) {
    poolOptions.setCleanerInterval(poolCleanerInterval);
    return this;
  }

  /**
   * Get the maximum size of the connection pool.
   * <p>
   * By default, the maximum pool size is 6.
   *
   * @return the maximum pool size
   */
  public int getMaxPoolSize() {
    return poolOptions.getMaxSize();
  }

  /**
   * Set the maximum size of the connection pool.
   * <p>
   * By default, the maximum pool size is 6.
   * <p>
   * When working with cluster or sentinel, this value should be at least the total
   * number of cluster member (or number of sentinels + 1).
   *
   * @param maxPoolSize the maximum pool size
   * @return fluent self
   */
  public RedisOptions setMaxPoolSize(int maxPoolSize) {
    poolOptions.setMaxSize(maxPoolSize);
    return this;
  }

  /**
   * Get the maximum number of requests waiting for a connection from the pool.
   * <p>
   * By default, the maximum number of waiting requests size is 24.
   *
   * @return the maximum number of waiting requests
   */
  public int getMaxPoolWaiting() {
    return poolOptions.getMaxWaiting();
  }

  /**
   * Set the maximum number of requests waiting for a connection from the pool.
   * <p>
   * By default, the maximum number of waiting requests size is 24.
   *
   * @param maxPoolWaiting the maximum number of waiting requests
   * @return fluent self
   */
  public RedisOptions setMaxPoolWaiting(int maxPoolWaiting) {
    poolOptions.setMaxWaiting(maxPoolWaiting);
    return this;
  }

  /**
   * Get how long a connection can stay unused before it is recycled during connection pool
   * {@linkplain #getPoolCleanerInterval() cleaning}.
   * <p>
   * The value is in milliseconds. By default, the recycle timeout is 3 minutes.
   * The value of -1 means connection recycling is disabled.
   *
   * @return the recycle timeout
   */
  public int getPoolRecycleTimeout() {
    return poolOptions.getRecycleTimeout();
  }

  /**
   * Set how long a connection can stay unused before it is recycled during connection pool
   * {@linkplain #setPoolCleanerInterval(int) cleaning}.
   * <p>
   * The value is in milliseconds. By default, the recycle timeout is 3 minutes.
   * The value of -1 means connection recycling is disabled.
   *
   * @param poolRecycleTimeout the recycle timeout
   * @return fluent self
   */
  public RedisOptions setPoolRecycleTimeout(int poolRecycleTimeout) {
    poolOptions.setRecycleTimeout(poolRecycleTimeout);
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
  public RedisOptions setPassword(String password) {
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
  public RedisOptions setProtocolNegotiation(boolean protocolNegotiation) {
    this.protocolNegotiation = protocolNegotiation;
    return this;
  }

  /**
   * Returns the preferred protocol version to be used during protocol negotiation. When not set,
   * defaults to RESP 3. When protocol negotiation is disabled, this setting has no effect.
   *
   * @return preferred protocol version
   */
  public ProtocolVersion getPreferredProtocolVersion() {
    return preferredProtocolVersion;
  }

  /**
   * Sets the preferred protocol version to be used during protocol negotiation. When not set,
   * defaults to RESP 3. When protocol negotiation is disabled, this setting has no effect.
   *
   * @param preferredProtocolVersion preferred protocol version
   * @return fluent self
   */
  public RedisOptions setPreferredProtocolVersion(ProtocolVersion preferredProtocolVersion) {
    this.preferredProtocolVersion = preferredProtocolVersion;
    return this;
  }


  /**
   * Get the connection pool name to be used for metrics reporting. The default name is a random UUID.
   *
   * @return the pool name
   */
  public String getPoolName() {
    return poolOptions.getName();
  }

  /**
   * Set the connection pool name to be used for metrics reporting. The default name is a random UUID.
   *
   * @param poolName the pool name
   * @return fluent self
   */
  public RedisOptions setPoolName(String poolName) {
    poolOptions.setName(poolName);
    return this;
  }

  /**
   * @return the metrics name identifying the reported metrics.
   */
  public String getMetricsName() {
    return netClientOptions.getMetricsName();
  }

  /**
   * Set the metrics name identifying the reported metrics, useful for grouping metrics
   * with the same name.
   *
   * @param metricsName the metrics name
   * @return a reference to this, so the API can be used fluently
   */
  public RedisOptions setMetricsName(String metricsName) {
    netClientOptions.setMetricsName(metricsName);
    return this;
  }

  /**
   * Returns the TTL of the hash slot cache. The TTL is expressed in milliseconds.
   * Defaults to 1000 millis (1 second).
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#CLUSTER cluster} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @return the TTL of the hash slot cache
   */
  public long getHashSlotCacheTTL() {
    return hashSlotCacheTTL;
  }

  /**
   * Sets the TTL of the hash slot cache. The TTL is expressed in milliseconds.
   * Defaults to 1000 millis (1 second).
   * <p>
   * This is only meaningful in case of a {@linkplain RedisClientType#CLUSTER cluster} Redis client
   * and is ignored otherwise.
   * </p>
   *
   * @param hashSlotCacheTTL the TTL of the hash slot cache, in millis
   * @return fluent self
   */
  public RedisOptions setHashSlotCacheTTL(long hashSlotCacheTTL) {
    this.hashSlotCacheTTL = hashSlotCacheTTL;
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
   * @param autoFailover whether automatic failover should be enabled
   * @return fluent self
   */
  public RedisOptions setAutoFailover(boolean autoFailover) {
    this.autoFailover = autoFailover;
    return this;
  }

  /**
   * Converts this object to JSON notation.
   *
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    RedisOptionsConverter.toJson(this, json);
    return json;
  }
}
