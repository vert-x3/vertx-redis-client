/*
 * Copyright 2018 Red Hat, Inc.
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
package io.vertx.redis.impl.client;

import io.vertx.core.*;
import io.vertx.core.impl.NetSocketInternal;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.RedisCommand;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.RedisException;
import io.vertx.redis.impl.Reply;
import io.vertx.redis.impl.ReplyParser;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public class RedisConnectionImpl implements RedisConnection, Handler<Reply> {

  private static class Req {
    final Context ctx;
    final Handler<AsyncResult<io.vertx.redis.Reply>> handler;

    Req(Handler<AsyncResult<io.vertx.redis.Reply>> handler) {
      this.handler = handler;
      this.ctx = Vertx.currentContext();
    }

    void handle(AsyncResult<io.vertx.redis.Reply> asyncResult) {
      if (handler != null) {
        if (ctx != null) {
          if (Vertx.currentContext() == ctx) {
            try {
              handler.handle(asyncResult);
            } catch (Throwable e) {
              LOG.error("Handler error", e);
            }
          } else {
            ctx.runOnContext(v -> {
              try {
                handler.handle(asyncResult);
              } catch (Throwable e) {
                LOG.error("Handler error", e);
              }
            });
          }
        } else {
          try {
            handler.handle(asyncResult);
          } catch (Throwable e) {
            LOG.error("Handler error", e);
          }
        }
      }
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionImpl.class);

  // waiting: commands that have been sent but not answered
  private final Queue<Req> waiting = new LinkedList<>();

  private final Context context;
  private final NetSocketInternal netSocket;
  private final SocketAddress socketAddress;

  // state
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<io.vertx.redis.Reply> onMessage;

  RedisConnectionImpl(Context context, NetClient netClient, NetSocket netSocket, SocketAddress endpoint) {
    this.context = context;
    this.netSocket = (NetSocketInternal) netSocket;
    this.socketAddress = endpoint;

    // parser utility
    netSocket
      .handler(new ReplyParser(this))
      .closeHandler(close -> {
        netClient.close();
        // clean up the pending queue
        cleanupQueue(new RedisException("CONNECTION_CLOSED"));
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
  public RedisConnection handler(Handler<io.vertx.redis.Reply> handler) {
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

  private void cleanupQueue(Throwable t) {
    Req req;

    while ((req = waiting.poll()) != null) {
      try {
        req.handle(Future.failedFuture(t));
      } catch (RuntimeException e) {
        LOG.warn("Exception during cleanup", e);
      }
    }
  }

  @Override
  public RedisConnection send(final RedisCommand command, Handler<AsyncResult<io.vertx.redis.Reply>> handler) {
    // write to the socket in the netSocket context
    final Req req = new Req(handler);
    // ensure we're on the right context
    // Use only if it's the same context and we are on the event loop thread
    if (Vertx.currentContext() == context && Context.isOnEventLoopThread()) {
      waiting.add(req);
      netSocket.writeMessage(command.toByteBuf());
    } else {
      context.runOnContext(v -> {
        waiting.add(req);
        netSocket.writeMessage(command.toByteBuf());
      });
    }

    return this;
  }

  @Override
  public void handle(Reply reply) {
    // pub/sub mode
    if (waiting.isEmpty() && onMessage != null) {
      onMessage.handle(reply);
      return;
    }

    final Req req = waiting.poll();

    if (req != null) {
      if (reply.is('-')) {
        req.handle(Future.failedFuture(new RedisException(reply.status())));
        return;
      }

      req.handle(Future.succeededFuture(reply));
    } else {
      LOG.error("No handler waiting for message: " + reply);
    }
  }

  @Override
  public SocketAddress address() {
    return socketAddress;
  }
}
