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
package io.vertx.redis.client.impl;

import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.PoolOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.RedisSentinelConnectOptions;
import io.vertx.redis.client.Request;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class RedisSentinelClient extends BaseRedisClient<RedisSentinelConnectOptions> implements Redis {

  private final SharedSentinelTopology sharedTopology;
  private final AtomicReference<SentinelFailover> failover = new AtomicReference<>();

  public RedisSentinelClient(Vertx vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, Supplier<Future<RedisSentinelConnectOptions>> connectOptions, TracingPolicy tracingPolicy) {
    super(vertx, tcpOptions, poolOptions, connectOptions, tracingPolicy);
    this.sharedTopology = new SharedSentinelTopology(vertx, connectOptions, connectionManager);
    // validate options
    if (poolOptions.getMaxWaiting() < poolOptions.getMaxSize()) {
      throw new IllegalStateException("Invalid options: maxWaiting < maxSize");
    }
  }

  @Override
  public Future<RedisConnection> connect() {
    final Promise<RedisConnection> promise = vertx.promise();
    sharedTopology.get()
      .onSuccess(topology -> connect(topology, promise))
      .onFailure(promise::fail);
    return promise.future();
  }

  private void connect(SentinelTopology topology, Completable<RedisConnection> promise) {
    connectOptions.get()
      .onSuccess(opts -> connect(topology, opts, promise))
      .onFailure(promise::fail);
  }

  private void connect(SentinelTopology topology, RedisSentinelConnectOptions connectOptions, Completable<RedisConnection> promise) {
    createConnectionInternal(topology, connectOptions.getRole(), (conn, err) -> {
      if (err != null) {
        promise.fail(err);
        return;
      }

      if (connectOptions.getRole() == RedisRole.SENTINEL || connectOptions.getRole() == RedisRole.REPLICA) {
        // it is possible that a replica is later promoted to a master, but that shouldn't be too big of a deal
        promise.succeed(conn);
        return;
      }
      if (!connectOptions.isAutoFailover()) {
        // no auto failover, return the master connection directly
        promise.succeed(conn);
        return;
      }

      SentinelFailover failover = setupFailover(connectOptions.getMasterName());
      RedisSentinelConnection sentinelConn = new RedisSentinelConnection(conn, failover);
      promise.succeed(sentinelConn);
    });
  }

  private SentinelFailover setupFailover(String masterName) {
    SentinelFailover result = this.failover.get();

    if (result == null) {
      result = new SentinelFailover(vertx, masterName, role -> {
        Promise<PooledRedisConnection> promise = Promise.promise();
        sharedTopology.get()
          .onSuccess(topology -> createConnectionInternal(topology, role, promise))
          .onFailure(promise::fail);
        return promise.future();
      });
      if (this.failover.compareAndSet(null, result)) {
        result.start();
      } else {
        result = this.failover.get();
      }
    }

    return result;
  }

  @Override
  public Future<Void> close() {
    SentinelFailover failover = this.failover.get();
    if (failover != null) {
      failover.close();
    }
    return super.close();
  }

  private void createConnectionInternal(SentinelTopology topology, RedisRole role, Completable<PooledRedisConnection> onCreate) {
    RedisURI uri;
    switch (role) {
      case SENTINEL:
        uri = topology.getRandomSentinel();
        break;
      case MASTER:
        uri = topology.getMaster();
        break;
      case REPLICA:
        uri = topology.getRandomReplica();
        break;
      default:
        onCreate.fail("Unknown role: " + role);
        return;
    }

    Request setup = null;

    // `SELECT` is only allowed on non-sentinel nodes
    // we don't send `READONLY` setup to replica nodes, because that's a cluster-only command
    if (role != RedisRole.SENTINEL && uri.select() != null) {
      setup = Request.cmd(Command.SELECT).arg(uri.select());
    }

    // wrap a new client
    connectionManager.getConnection(uri.baseUri(), setup).onComplete(onCreate);
  }
}
