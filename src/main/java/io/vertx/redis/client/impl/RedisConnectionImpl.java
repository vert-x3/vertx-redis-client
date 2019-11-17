package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.impl.pool.ConnectionListener;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisConnectionImpl implements RedisConnection, ParserHandler {

  private static final String BASE_ADDRESS = "io.vertx.redis";

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionImpl.class);

  private static final ErrorType CONNECTION_CLOSED = ErrorType.create("CONNECTION_CLOSED");

  private final ConnectionListener<RedisConnection> listener;
  private final EventBus eventBus;
  private final NetSocket netSocket;
  // waiting: commands that have been sent but not answered
  // the queue is only accessed from the event loop
  private final ArrayQueue waiting;
  private final int recycleTimeout;

  // state
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Response> onMessage;

  public RedisConnectionImpl(EventBus eventBus, ConnectionListener<RedisConnection> connectionListener, NetSocket netSocket, RedisOptions options) {
    this.listener = connectionListener;
    this.eventBus = eventBus;
    this.netSocket = netSocket;
    this.waiting = new ArrayQueue(options.getMaxWaitingHandlers());
    this.recycleTimeout = options.getPoolRecycleTimeout();
  }

  void forceClose() {
    listener.onEvict();
    netSocket.close();
  }

  @Override
  public void close() {
    // recycle this connection from the pool
    long expired = recycleTimeout > 0 ? System.currentTimeMillis() + recycleTimeout : 0L;
    listener.onRecycle(expired);
  }

  @Override
  public boolean pendingQueueFull() {
    return waiting.isFull();
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    this.onException = handler;
    return this;
  }

  @Override
  public RedisConnection endHandler(Handler<Void> handler) {
    this.onEnd = handler;
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public RedisConnection pause() {
    netSocket.pause();
    return this;
  }

  @Override
  public RedisConnection resume() {
    netSocket.resume();
    return this;
  }

  @Override
  public RedisConnection fetch(long size) {
    // no-op
    return this;
  }

  @Override
  public RedisConnection send(final Request request, Handler<AsyncResult<Response>> handler) {
    if (waiting.isFull()) {
      handler.handle(Future.failedFuture("Redis waiting Queue is full"));
      return this;
    }

    // encode the message to a buffer
    final Buffer message = ((RequestImpl) request).encode();
    // all update operations happen inside the context
    // offer the handler to the waiting queue
    waiting.offer(handler);
    // write to the socket
    netSocket.write(message, write -> {
      if (write.failed()) {
        // if the write fails, this connection enters a unknown state
        // which means it should be terminated
        fatal(write.cause());
      }
    });

    return this;
  }

  @Override
  public RedisConnection batch(List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {
    if (waiting.freeSlots() < commands.size()) {
      handler.handle(Future.failedFuture("Redis waiting Queue is full"));
      return this;
    }

    // will re-encode the handler into a list of handlers
    final List<Handler<AsyncResult<Response>>> callbacks = new ArrayList<>(commands.size());
    final List<Response> replies = new ArrayList<>(commands.size());
    final AtomicInteger count = new AtomicInteger(commands.size());
    final AtomicBoolean failed = new AtomicBoolean(false);

    // encode the message to a single buffer
    final Buffer messages = Buffer.buffer();

    for (int i = 0; i < commands.size(); i++) {
      final int index = i;
      final RequestImpl req = (RequestImpl) commands.get(index);
      // encode to the single buffer
      req.encode(messages);
      // unwrap the handler into a single handler
      callbacks.add(index, command -> {
        if (!failed.get()) {
          if (command.failed()) {
            failed.set(true);
            if (handler != null) {
              handler.handle(Future.failedFuture(command.cause()));
            }
            return;
          }
          // set the reply
          replies.add(index, command.result());

          if (count.decrementAndGet() == 0) {
            // all results have arrived
            if (handler != null) {
              handler.handle(Future.succeededFuture(replies));
            }
          }
        }
      });
    }

    // offer all handlers to the waiting queue
    for (Handler<AsyncResult<Response>> callback : callbacks) {
      waiting.offer(callback);
    }
    // write to the socket
    netSocket.write(messages, write -> {
      if (write.failed()) {
        // if the write fails, this connection enters a unknown state
        // which means it should be terminated
        fatal(write.cause());
      }
    });

    return this;
  }


  @Override
  public void handle(Response reply) {
    // pub/sub mode
    if (waiting.isEmpty()) {
      if (onMessage != null) {
        onMessage.handle(reply);
      } else {
        // pub/sub messages are arrays
        if (reply.type() == ResponseType.MULTI) {
          // Detect valid published messages according to https://redis.io/topics/pubsub

          if (reply.size() == 3 && "message".equals(reply.get(0).toString())) {
            // channel
            eventBus.send(
              BASE_ADDRESS + "." + reply.get(1).toString(),
              new JsonObject()
                .put("status", "OK")
                .put("value", new JsonObject()
                  .put("channel", reply.get(1).toString())
                  .put("message", reply.get(2).toString())));
            return;
          }

          if (reply.size() == 4 && "pmessage".equals(reply.get(0).toString())) {
            // pattern
            eventBus.send(
              BASE_ADDRESS + "." + reply.get(1).toString(),
              new JsonObject()
                .put("status", "OK")
                .put("value", new JsonObject()
                  .put("pattern", reply.get(1).toString())
                  .put("channel", reply.get(2).toString())
                  .put("message", reply.get(3).toString())));
            return;
          }
          // fallback will just go to the log
        }
        LOG.warn("No handler waiting for message: " + reply);
      }
      return;
    }

    final Handler<AsyncResult<Response>> req = waiting.poll();
    if (req != null) {
      // special case (nulls are always a success)
      // the reason is that nil is only a valid value for
      // bulk or multi
      if (reply == null) {
        try {
          req.handle(Future.succeededFuture());
        } catch (RuntimeException e) {
          fail(e);
        }
        return;
      }
      // errors
      if (reply.type() == ResponseType.ERROR) {
        try {
          req.handle(Future.failedFuture((ErrorType) reply));
        } catch (RuntimeException e) {
          fail(e);
        }
        return;
      }
      // everything else
      try {
        req.handle(Future.succeededFuture(reply));
      } catch (RuntimeException e) {
        fail(e);
      }
    } else {
      LOG.error("No handler waiting for message: " + reply);
    }
  }

  public void end(Void v) {
    // clean up the pending queue
    cleanupQueue(CONNECTION_CLOSED);
    // call the forceClose handler if any
    if (onEnd != null) {
      onEnd.handle(v);
    }
  }

  @Override
  public void fail(Throwable t) {
    // evict this connection from the pool
    listener.onEvict();
    // call the exception handler if any
    if (onException != null) {
      onException.handle(t);
    }
  }

  @Override
  public void fatal(Throwable t) {
    // if there are still on going requests
    // the are all cancelled with the given
    // throwable
    cleanupQueue(t);
    fail(t);
  }

  private void cleanupQueue(Throwable t) {
    Handler<AsyncResult<Response>> req;

    while ((req = waiting.poll()) != null) {
      if (t != null) {
        try {
          req.handle(Future.failedFuture(t));
        } catch (RuntimeException e) {
          LOG.warn("Exception during cleanup", e);
        }
      }
    }
  }
}
