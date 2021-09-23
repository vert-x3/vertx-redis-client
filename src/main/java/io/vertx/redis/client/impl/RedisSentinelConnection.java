package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;

import static io.vertx.redis.client.Command.UNSUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;

public class RedisSentinelConnection implements RedisConnection {

  private static final Logger LOG = LoggerFactory.getLogger(RedisSentinelConnection.class);

  private final RedisConnection connection;
  private final RedisConnection sentinel;

  public RedisSentinelConnection(RedisConnection connection, RedisConnection sentinel) {
    this.connection = connection;
    this.sentinel = sentinel;
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
    return connection.send(command);
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    return connection.batch(commands);
  }

  @Override
  public void close() {
//    // we need to be sure that the previous subscriptions are
//    // clean before we return the connection to the pool
//    sentinel.send(cmd(UNSUBSCRIBE))
//      .onSuccess(ok -> sentinel.close())
//      .onFailure(err -> LOG.error("Failed to unsubscribe sentinel subscriptions", err));

    connection.close();
  }

  @Override
  public boolean pendingQueueFull() {
    return connection.pendingQueueFull();
  }
}
