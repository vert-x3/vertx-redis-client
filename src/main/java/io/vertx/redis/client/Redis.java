package io.vertx.redis.client;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.client.impl.RedisFactory;

import java.util.List;

/**
 * A simple Redis client.
 */
@VertxGen
public interface Redis extends ReadStream<Response> {

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClient(Vertx vertx, SocketAddress address, Handler<AsyncResult<Redis>> onCreate) {
    createClient(vertx, new RedisOptions().setEndpoint(address), onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisFactory.createClient(vertx, options.getEndpoint(), options, onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createSentinelClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisFactory.createSentinelClient(vertx, options, onCreate);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link Redis} instance.
   */
  static void createClusterClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    RedisFactory.createClusterClient(vertx, options, onCreate);
  }

  /**
   * Set an exception handler on the read stream.
   *
   * @param handler  the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  Redis exceptionHandler(Handler<Throwable> handler);

  /**
   * Set a data handler. As data is read, the handler will be called with the data.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis handler(Handler<Response> handler);

  /**
   * Pause the {@code ReadStream}, it sets the buffer in {@code fetch} mode and clears the actual demand.
   * <p>
   * While it's paused, no data will be sent to the data {@code handler}.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis pause();

  /**
   * Resume reading, and sets the buffer in {@code flowing} mode.
   * <p/>
   * If the {@code ReadStream} has been paused, reading will recommence on it.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis resume();

  /**
   * Fetch the specified {@code amount} of elements. If the {@code ReadStream} has been paused, reading will
   * recommence with the specified {@code amount} of items, otherwise the specified {@code amount} will
   * be added to the current stream demand.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis fetch(long amount);

  /**
   * Set an end handler. Once the stream has ended, and there is no more data to be read, this handler will be called.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  Redis endHandler(@Nullable Handler<Void> endHandler);


  @Fluent
  Redis send(Request command, Handler<AsyncResult<Response>> onSend);

  @Fluent
  Redis batch(List<Request> commands, Handler<AsyncResult<List<Response>>> handler);


  /**
   * Returns the address associated with this client.
   * @return the address.
   */
  SocketAddress socketAddress();

  void close();
}
