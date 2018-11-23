package io.vertx.redis.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.RedisRole;
import io.vertx.redis.RedisSlaves;

import java.util.ArrayList;
import java.util.List;

@DataObject(generateConverter = true)
public class RedisOptions {

  private NetClientOptions netClientOptions;
  private List<SocketAddress> endpoints;
  private int maxWaitingHandlers;
  private String masterName;
  private RedisRole role;
  private RedisSlaves slaves;


  private void init() {
    netClientOptions =
      new NetClientOptions()
        .setTcpKeepAlive(true)
        .setTcpNoDelay(true);

    maxWaitingHandlers = 500;
    masterName = "mymaster";
    role = RedisRole.MASTER;
    slaves = RedisSlaves.NEVER;
  }

  public RedisOptions() {
    init();
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
}
