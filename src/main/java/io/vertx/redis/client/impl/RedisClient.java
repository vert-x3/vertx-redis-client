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

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisClient implements Redis, ParserHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RedisClient.class);

  private static final ErrorType CONNECTION_CLOSED = ErrorType.create("CONNECTION_CLOSED");

  public static Redis create(Vertx vertx, RedisOptions options) {
    return create(vertx, options, options.getEndpoint());
  }

  static Redis create(Vertx vertx, RedisOptions options, SocketAddress address) {
    return new RedisClient(vertx, options, address);
  }

  private static void authenticate(Redis client, RedisOptions options, Handler<AsyncResult<Void>> handler) {
    if (options.getPassword() == null) {
      handler.handle(Future.succeededFuture());
      return;
    }
    // perform authentication
    client.send(Request.cmd(Command.AUTH).arg(options.getPassword()), auth -> {
      if (auth.failed()) {
        handler.handle(Future.failedFuture(auth.cause()));
      } else {
        handler.handle(Future.succeededFuture());
      }
    });
  }

  private static void select(Redis client, RedisOptions options, Handler<AsyncResult<Void>> handler) {
    if (options.getSelect() == null) {
      handler.handle(Future.succeededFuture());
      return;
    }
    // perform select
    client.send(Request.cmd(Command.SELECT).arg(options.getSelect()), auth -> {
      if (auth.failed()) {
        handler.handle(Future.failedFuture(auth.cause()));
      } else {
        handler.handle(Future.succeededFuture());
      }
    });
  }

  private final Context context;
  // waiting: commands that have been sent but not answered
  // the queue is only accessed from the event loop
  private final ArrayQueue waiting;

  private final NetClient netClient;
  private final SocketAddress socketAddress;

  private final RedisOptions options;

  // state
  private Handler<Throwable> onException = t -> LOG.error("Unhandled Error", t);

  private Handler<Void> onEnd;
  private Handler<Response> onMessage;

  private NetSocket netSocket;
  // connected flag to signal that the underlying socket
  // connection is operational.
  private boolean connected = false;

  private RedisClient(Vertx vertx, RedisOptions options, SocketAddress endpoint) {
    this.context = vertx.getOrCreateContext();
    this.netClient = vertx.createNetClient(options.getNetClientOptions());
    this.waiting = new ArrayQueue(options.getMaxWaitingHandlers());
    this.socketAddress = endpoint;
    this.options = options;
  }

  @Override
  public Redis connect(Handler<AsyncResult<Redis>> onConnect) {

    if (connected) {
      onConnect.handle(Future.succeededFuture(this));
      return this;
    }

    netClient.connect(socketAddress, clientConnect -> {
      if (clientConnect.failed()) {
        // connection failed
        netClient.close();
        onConnect.handle(Future.failedFuture(clientConnect.cause()));
        return;
      }

      // socket connection succeeded
      netSocket = clientConnect.result();
      this.connected = true;

      // parser utility
      netSocket
        .handler(new RESPParser(this, options.getMaxNestedArrays()))
        .closeHandler(close -> {
          netClient.close();
          // clean up the pending queue
          cleanupQueue(CONNECTION_CLOSED);
          // the underlying socket connection is closed
          connected  = false;
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
          // the underlying socket connection is broken
          connected  = false;
          // call the exception handler if any
          if (onException != null) {
            onException.handle(exception);
          }
        });

      // perform authentication
      authenticate(this, options, authenticate -> {
        if (authenticate.failed()) {
          onConnect.handle(Future.failedFuture(authenticate.cause()));
          return;
        }

        // perform select
        select(this, options, select -> {
          if (select.failed()) {
            onConnect.handle(Future.failedFuture(select.cause()));
            return;
          }

          // initialization complete
          onConnect.handle(Future.succeededFuture(this));
        });
      });
    });

    return this;
  }

  @Override
  public void close() {
    if (netSocket != null) {
      netSocket.close();
    }
    // the underlying socket connection is closed
    connected  = false;
  }

  @Override
  public Redis exceptionHandler(Handler<Throwable> handler) {
    this.onException = handler;
    return this;
  }

  @Override
  public Redis endHandler(Handler<Void> handler) {
    this.onEnd = handler;
    return this;
  }

  @Override
  public Redis handler(Handler<Response> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public Redis pause() {
    netSocket.pause();
    return this;
  }

  @Override
  public Redis resume() {
    netSocket.resume();
    return this;
  }

  @Override
  public Redis fetch(long size) {
    // no-op
    return this;
  }

  private void cleanupQueue(Throwable t) {
    // all update operations happen inside the context
    context.runOnContext(v -> {
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
    });
  }

  @Override
  public Redis send(final Request request, Handler<AsyncResult<Response>> handler) {
    if (!connected) {
      // this avoids entering the socket exception handler as it is well known
      // that the transport is broken.
      handler.handle(Future.failedFuture("Redis connection is broken."));
      return this;
    }

    if (waiting.isFull()) {
      handler.handle(Future.failedFuture("Redis waiting Queue is full"));
      return this;
    }

    // encode the message to a buffer
    final Buffer message = ((RequestImpl) request).encode();
    // all update operations happen inside the context
    context.runOnContext(v -> {
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
    });
    return this;
  }

  @Override
  public Redis batch(List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {
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

// all update operations happen inside the context
    context.runOnContext(v -> {
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
        LOG.warn("No handler waiting for message: " + reply);
      }
      return;
    }

// all update operations happen inside the context
    context.runOnContext(v -> {
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
    });
  }

  @Override
  public SocketAddress socketAddress() {
    return socketAddress;
  }

  @Override
  public void fail(Throwable t) {
    if (onException != null) {
      onException.handle(t);
    } else {
      LOG.error("External failure", t);
    }
  }

  @Override
  public void fatal(Throwable t) {
    if (onException != null) {
      onException.handle(t);
    } else {
      LOG.error("External failure", t);
    }
    close();
    // if there are still on going requests
    // the are all cancelled with the given
    // throwable
    cleanupQueue(t);
  }
}
