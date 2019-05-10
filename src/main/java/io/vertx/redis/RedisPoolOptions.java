package io.vertx.redis;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.RedisSlaves;

import java.util.List;

@DataObject(generateConverter = true)
public class RedisPoolOptions extends RedisOptions {

  public static final int DEFAULT_MAX_POOL_SIZE = 1;
  public static final int DEFAULT_MAX_CONNECTION_RETRIES = 0;       // No connection retries by default
  public static final int DEFAULT_CONNECTION_RETRY_DELAY = 5_000;   // 5 seconds between retries by default
  public static final int DEFAULT_CONNECTION_RELEASE_DELAY = 0;     // never release idle connection by default
  public static final int DEFAULT_MAX_WAIT_QUEUE_SIZE = -1;         // unlimited queue size
  public static final int DEFAULT_CONNECTION_TEST_TIMEOUT = -1;     // timeout before verifying if the connection is OK

  private int maxPoolSize;
  private int maxConnectionRetries;
  private int connectionRetryDelay;
  private int connectionReleaseDelay;
  private int maxQueueSize;
  private int connectionTestTimeout;

  private void init() {
    maxPoolSize = DEFAULT_MAX_POOL_SIZE;
    maxConnectionRetries = DEFAULT_MAX_CONNECTION_RETRIES;
    connectionRetryDelay = DEFAULT_CONNECTION_RETRY_DELAY;
    connectionReleaseDelay = DEFAULT_CONNECTION_RELEASE_DELAY;
    maxQueueSize = DEFAULT_MAX_WAIT_QUEUE_SIZE;
    connectionTestTimeout = DEFAULT_CONNECTION_TEST_TIMEOUT;
  }

  /**
   * Creates a default configuration object using redis server defaults
   */
  public RedisPoolOptions() {
    super();
    init();
  }

  /**
   * Copy constructor.
   * @param other the object to clone.
   */
  public RedisPoolOptions(RedisPoolOptions other) {
    super(other);
    this.maxPoolSize = other.maxPoolSize;
    this.maxConnectionRetries = other.maxConnectionRetries;
    this.connectionRetryDelay = other.connectionRetryDelay;
    this.connectionReleaseDelay = other.connectionReleaseDelay;
    this.maxQueueSize = other.maxQueueSize;
    this.connectionTestTimeout = other.connectionTestTimeout;
  }

  /**
   * Copy from JSON constructor.
   * @param json source json
   */
  public RedisPoolOptions(JsonObject json) {
    super(json);
    init();
    RedisPoolOptionsConverter.fromJson(json, this);
  }
  
  public int getConnectionTestTimeout() {
    return connectionTestTimeout;
  }

  public RedisPoolOptions setConnectionTestTimeout(int connectionTestTimeout) {
    this.connectionTestTimeout = connectionTestTimeout;
    return this;
  }

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  public RedisPoolOptions setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
    return this;
  }

  public int getMaxConnectionRetries() {
    return maxConnectionRetries;
  }

  public RedisPoolOptions setMaxConnectionRetries(int maxConnectionRetries) {
    this.maxConnectionRetries = maxConnectionRetries;
    return this;
  }

  public int getConnectionRetryDelay() {
    return connectionRetryDelay;
  }

  public RedisPoolOptions setConnectionRetryDelay(int connectionRetryDelay) {
    this.connectionRetryDelay = connectionRetryDelay;
    return this;
  }

  public int getConnectionReleaseDelay() {
    return connectionReleaseDelay;
  }

  public RedisPoolOptions setConnectionReleaseDelay(int connectionReleaseDelay) {
    this.connectionReleaseDelay = connectionReleaseDelay;
    return this;
  }

  public int getMaxQueueSize() {
    return maxQueueSize;
  }

  public RedisPoolOptions setMaxQueueSize(int maxQueueSize) {
    this.maxQueueSize = maxQueueSize;
    return this;
  }









  @Override
  public RedisPoolOptions setType(RedisClientType type) {
    super.setType(type);
    return this;
  }

  @Override
  public RedisPoolOptions setNetClientOptions(NetClientOptions netClientOptions) {
    super.setNetClientOptions(netClientOptions);
    return this;
  }

  @Override
  public RedisPoolOptions setEndpoints(List<SocketAddress> endpoints) {
    super.setEndpoints(endpoints);
    return this;
  }

  @Override
  public RedisPoolOptions addEndpoint(SocketAddress endpoint) {
    super.addEndpoint(endpoint);
    return this;
  }

  @Override
  public RedisPoolOptions setEndpoint(SocketAddress endpoint) {
    super.setEndpoint(endpoint);
    return this;
  }

  @Override
  public RedisPoolOptions setMaxWaitingHandlers(int maxWaitingHandlers) {
    super.setMaxWaitingHandlers(maxWaitingHandlers);
    return this;
  }

  @Override
  public RedisPoolOptions setMasterName(String masterName) {
    super.setMasterName(masterName);
    return this;
  }

  @Override
  public RedisPoolOptions setRole(RedisRole role) {
    super.setRole(role);
    return this;
  }

  @Override
  public RedisPoolOptions setUseSlave(RedisSlaves slaves) {
    super.setUseSlave(slaves);
    return this;
  }

  @Override
  public RedisPoolOptions setMaxNestedArrays(int maxNestedArrays) {
    super.setMaxNestedArrays(maxNestedArrays);
    return this;
  }

  @Override
  public RedisPoolOptions setPassword(String password) {
    super.setPassword(password);
    return this;
  }

  @Override
  public RedisPoolOptions setSelect(Integer select) {
    super.setSelect(select);
    return this;
  }


  /**
   * Converts this object to JSON notation.
   * @return JSON
   */
  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    RedisPoolOptionsConverter.toJson(this, json);
    return json;
  }
}
