package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.net.impl.pool.Lease;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A pooled Redis connection
 */
public class PooledRedisConnection implements RedisConnection {

  private final Lease<RedisConnectionInternal> lease;
  private final RedisConnectionInternal connection;
  private final PoolMetrics metrics;
  private final Object metric;
  private final AtomicBoolean ended = new AtomicBoolean();

  public PooledRedisConnection(Lease<RedisConnectionInternal> lease, PoolMetrics<?> poolMetrics, Object metric) {
    this.lease = lease;
    this.connection = lease.get();
    this.metrics = poolMetrics;
    this.metric = metric;
  }

  public RedisConnectionInternal actual() {
    return connection;
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    connection.exceptionHandler(handler);
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    connection.handler(handler);
    return this;
  }

  @Override
  public RedisConnection pause() {
    connection.pause();
    return this;
  }

  @Override
  public RedisConnection resume() {
    connection.resume();
    return this;
  }

  @Override
  public RedisConnection fetch(long amount) {
    connection.fetch(amount);
    return this;
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> endHandler) {
    connection.endHandler(endHandler);
    return this;
  }

  @Override
  public Future<@Nullable Response> send(Request command) {
    CommandReporter reporter = new CommandReporter(connection, command.command().toString());
    reporter.before();
    return connection.send(command)
      .andThen(reporter::after);
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    CommandReporter reporter = new CommandReporter(connection, "batch");
    reporter.before();
    return connection.batch(commands)
      .andThen(reporter::after);
  }

  @Override
  public Future<Void> close() {
    if (connection.reset()) {
      lease.recycle();
    }

    if (ended.compareAndSet(false, true)) {
      if (metrics != null) {
        metrics.end(metric, true);
      }
    }

    return Future.succeededFuture();
  }

  @Override
  public boolean pendingQueueFull() {
    return connection.pendingQueueFull();
  }
}
