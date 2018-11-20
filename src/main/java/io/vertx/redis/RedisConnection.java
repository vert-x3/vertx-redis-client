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

@VertxGen(concrete = false)
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
    return send(RedisCommand.create(command), null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @return self
   */
  @Fluent
  default RedisConnection send(RedisCommand command) {
    return send(command, null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param handler the handler
   * @return self
   */
  @Fluent
  default RedisConnection send(String command, Handler<AsyncResult<Reply>> handler) {
    return send(RedisCommand.create(command), handler);
  }

  @Fluent
  default RedisConnection batch(List<RedisCommand> commands) {
    return batch(commands, null);
  }

  @Fluent
  default RedisConnection batch(List<RedisCommand> commands, Handler<AsyncResult<List<Reply>>> handler) {
    final List<Reply> replies = new ArrayList<>(commands.size());
    final AtomicInteger count = new AtomicInteger(commands.size());
    final AtomicBoolean failed = new AtomicBoolean(false);
    // start sending commands
    for (int i = 0; i < commands.size(); i++) {
      final int index = i;
      final RedisCommand command = commands.get(index);

      send(command, res -> {
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
   * @param handler the handler
   * @return self
   */
  @Fluent
  RedisConnection send(RedisCommand command, Handler<AsyncResult<Reply>> handler);

  /**
   * Returns the address associated with this client.
   * @return the address.
   */
  SocketAddress address();
}
