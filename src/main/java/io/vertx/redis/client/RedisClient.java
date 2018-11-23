package io.vertx.redis.client;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.client.impl.RedisClientImpl;

/**
 * A simple Redis client.
 */
@VertxGen
public interface RedisClient extends ReadStream<Response> {

  /**
   * Connect to redis, the {@code onConnect} will get the {@link RedisClient} instance.
   */
  static void connect(Vertx vertx, SocketAddress address, Handler<AsyncResult<RedisClient>> onConnect) {
    connect(vertx, address, new NetClientOptions().setTcpKeepAlive(true).setTcpNoDelay(true), onConnect);
  }

  /**
   * Connect to redis, the {@code onConnect} will get the {@link RedisClient} instance.
   */
  static void connect(Vertx vertx, SocketAddress address, NetClientOptions options, Handler<AsyncResult<RedisClient>> onConnect) {
    RedisClientImpl.connect(vertx, address, options, onConnect);
  }

  /**
   * Set an exception handler on the read stream.
   *
   * @param handler  the exception handler
   * @return a reference to this, so the API can be used fluently
   */
  RedisClient exceptionHandler(Handler<Throwable> handler);

  /**
   * Set a data handler. As data is read, the handler will be called with the data.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisClient handler(Handler<Response> handler);

  /**
   * Pause the {@code ReadStream}, it sets the buffer in {@code fetch} mode and clears the actual demand.
   * <p>
   * While it's paused, no data will be sent to the data {@code handler}.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisClient pause();

  /**
   * Resume reading, and sets the buffer in {@code flowing} mode.
   * <p/>
   * If the {@code ReadStream} has been paused, reading will recommence on it.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisClient resume();

  /**
   * Fetch the specified {@code amount} of elements. If the {@code ReadStream} has been paused, reading will
   * recommence with the specified {@code amount} of items, otherwise the specified {@code amount} will
   * be added to the current stream demand.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisClient fetch(long amount);

  /**
   * Set an end handler. Once the stream has ended, and there is no more data to be read, this handler will be called.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  RedisClient endHandler(@Nullable Handler<Void> endHandler);


  @Fluent
  RedisClient send(Request command, Handler<AsyncResult<Response>> onSend);


  /**
   * Returns the address associated with this client.
   * @return the address.
   */
  SocketAddress socketAddress();

  void close();
}
