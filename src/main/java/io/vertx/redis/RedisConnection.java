package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@VertxGen
public interface RedisConnection extends ReadStream<Reply> {

  /**
   * Closes the underlying net client.
   */
  void close();

  /**
   * Set the exception handler to be called on exception.
   * @param handler the handler
   * @return self
   */
  @Fluent
  RedisConnection exceptionHandler(Handler<Throwable> handler);

  /**
   * Set the handler to be called on end.
   * @param handler the handler
   * @return self
   */
  @Fluent
  RedisConnection endHandler(Handler<Void> handler);

  /**
   * Set the handler to be called on message.
   * @param handler the handler
   * @return self
   */
  @Fluent
  RedisConnection handler(Handler<Reply> handler);

  @Fluent
  RedisConnection pause();

  @Fluent
  RedisConnection resume();

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @return self
   */
  @Fluent
  default RedisConnection send(String command) {
    return send(command, null, null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param args the args to the command
   * @return self
   */
  @Fluent
  default RedisConnection send(String command, Args args) {
    return send(command, args, null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param handler the handler
   * @return self
   */
  @Fluent
  default RedisConnection send(String command, Handler<AsyncResult<Reply>> handler) {
    return send(command, null, handler);
  }

  @Fluent
  default RedisConnection batch(List<Command> commands) {
    return batch(commands, null);
  }

  @Fluent
  default RedisConnection batch(List<Command> commands, Handler<AsyncResult<List<Reply>>> handler) {
    final List<Reply> replies = new ArrayList<>(commands.size());
    final AtomicInteger count = new AtomicInteger(commands.size());
    final AtomicBoolean failed = new AtomicBoolean(false);
    // start sending commands
    for (int i = 0; i < commands.size(); i++) {
      final int index = i;
      final Command command = commands.get(index);

      send(command.getCommand(), command.getArgs(), command.isReadOnly(), res -> {
        if (!failed.get()) {
          if (res.failed()) {
            failed.set(true);
            if (handler != null) {
              handler.handle(Future.failedFuture(res.cause()));
            }
            return;
          }
          // set the reply
          replies.add(index, res.result());

          if (count.decrementAndGet() == 0) {
            // all results have arrived
            if (handler != null) {
              handler.handle(Future.succeededFuture(replies));
            }
          }
        }
      });
    }
    return this;
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param args the args to the command
   * @param handler the handler
   * @return self
   */
  @Fluent
  default RedisConnection send(String command, Args args, Handler<AsyncResult<Reply>> handler) {
    return send(command, args, false, handler);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param args the args to the command
   * @param readOnly hint the connection that the command is readonly
   * @param handler the handler
   * @return self
   */
  @Fluent
  RedisConnection send(String command, Args args, boolean readOnly, Handler<AsyncResult<Reply>> handler);

  /**
   * Returns the address associated with this client.
   * @return the address.
   */
  SocketAddress address();
}
