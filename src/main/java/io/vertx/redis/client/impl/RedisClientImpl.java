package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.RedisClient;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.impl.client.RedisConnectionImpl;

public class RedisClientImpl implements RedisClient, Handler<Response> {

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionImpl.class);

  private static final ErrorType CONNECTION_BROKEN = ErrorType.create("CONNECTION_BROKEN");

  public static void connect(Vertx vertx, SocketAddress address, NetClientOptions options, Handler<AsyncResult<RedisClient>> onConnect) {
    final NetClient netClient = vertx.createNetClient(options);

    netClient.connect(address, clientConnect -> {
      if (clientConnect.failed()) {
        // connection failed
        netClient.close();
        onConnect.handle(Future.failedFuture(clientConnect.cause()));
        return;
      }

      // socket connection succeeded
      onConnect.handle(
        Future.succeededFuture(
          new RedisClientImpl(200, netClient, clientConnect.result(), address)));
    });
  }

  // waiting: commands that have been sent but not answered
  // the queue is only accessed from the event loop
  private final ArrayQueue waiting;

  private final NetSocket netSocket;
  private final SocketAddress socketAddress;

  // state
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Response> onMessage;

  RedisClientImpl(int maxQueue, NetClient netClient, NetSocket netSocket, SocketAddress endpoint) {
    this.waiting = new ArrayQueue(maxQueue);
    this.netSocket = netSocket;
    this.socketAddress = endpoint;

    // parser utility
    netSocket
      .handler(new RESPParser(this))
      .closeHandler(close -> {
        netClient.close();
        // clean up the pending queue
        cleanupQueue(CONNECTION_BROKEN);
        // call the close handler if any
        if (onEnd != null) {
          onEnd.handle(close);
        }
      })
      .exceptionHandler(exception -> {
        netSocket.close();
        netClient.close();
        // clean up the pending queue
        cleanupQueue(exception);
        // call the exception handler if any
        if (onException != null) {
          onException.handle(exception);
        }
      });
  }

  @Override
  public void close() {
    netSocket.close();
  }

  @Override
  public RedisClient exceptionHandler(Handler<Throwable> handler) {
    this.onException = handler;
    return this;
  }

  @Override
  public RedisClient endHandler(Handler<Void> handler) {
    this.onEnd = handler;
    return this;
  }

  @Override
  public RedisClient handler(Handler<Response> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public RedisClient pause() {
    netSocket.pause();
    return this;
  }

  @Override
  public RedisClient resume() {
    netSocket.resume();
    return this;
  }

  @Override
  public RedisClient fetch(long size) {
    // no-op
    return this;
  }

  private void cleanupQueue(Throwable t) {
    Handler<AsyncResult<Response>> callback;

    while ((callback = waiting.poll()) != null) {
      try {
        callback.handle(Future.failedFuture(t));
      } catch (RuntimeException e) {
        LOG.warn("Exception during cleanup", e);
      }
    }
  }

  @Override
  public RedisClient send(final Request request, Handler<AsyncResult<Response>> handler) {
    if (waiting.isFull()) {
      handler.handle(Future.failedFuture("Redis waiting Queue is full"));
      return this;
    }
    // encode the message to a buffer
    final Buffer message = ((RequestImpl) request).encode();
    // write to the socket
    netSocket.write(message, write -> {
      if (write.succeeded()) {
        waiting.offer(handler);
      } else {
        handler.handle(Future.failedFuture(write.cause()));
      }
    });
    return this;
  }

  @Override
  public void handle(Response reply) {
    // pub/sub mode
    if (waiting.isEmpty() && onMessage != null) {
      onMessage.handle(reply);
      return;
    }

    final Handler<AsyncResult<Response>> req = waiting.poll();

    if (req != null) {
      if (reply.type() == ResponseType.ERROR) {
        req.handle(Future.failedFuture((ErrorType) reply));
        return;
      }

      req.handle(Future.succeededFuture(reply));
    } else {
      LOG.error("No handler waiting for message: " + reply);
    }
  }

  @Override
  public SocketAddress socketAddress() {
    return socketAddress;
  }
}
