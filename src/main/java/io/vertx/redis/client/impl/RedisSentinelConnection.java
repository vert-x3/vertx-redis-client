package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// in the Sentinel mode, a plain connection is used:
// - when connecting to a sentinel
// - when connecting to a replica
// - when connecting to a master node and automatic failover is disabled
// this class is only used when connecting to a master node and automatic failover is enabled
public class RedisSentinelConnection implements RedisConnection {

  private final AtomicReference<PooledRedisConnection> connection;
  private final SentinelFailover failover;

  public RedisSentinelConnection(PooledRedisConnection connection, SentinelFailover failover) {
    this.connection = new AtomicReference<>(connection);
    this.failover = failover;
    failover.addConnection(this);
  }

  void reconnect(PooledRedisConnection newConnection) {
    connection.set(newConnection);
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    connection.get().exceptionHandler(handler);
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    connection.get().handler(handler);
    return this;
  }

  @Override
  public RedisConnection pause() {
    connection.get().pause();
    return this;
  }

  @Override
  public RedisConnection resume() {
    connection.get().resume();
    return this;
  }

  @Override
  public RedisConnection fetch(long amount) {
    connection.get().fetch(amount);
    return this;
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> endHandler) {
    connection.get().endHandler(endHandler);
    return this;
  }

  @Override
  public Future<@Nullable Response> send(Request command) {
    return connection.get().send(command);
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    return connection.get().batch(commands);
  }

  Future<Void> closeDelegate() {
    return connection.get().close();
  }

  @Override
  public Future<Void> close() {
    failover.removeConnection(this);
    return closeDelegate();
  }

  @Override
  public boolean pendingQueueFull() {
    return connection.get().pendingQueueFull();
  }
}
