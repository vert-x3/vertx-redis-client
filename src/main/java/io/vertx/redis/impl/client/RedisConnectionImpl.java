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
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.Args;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.RedisException;
import io.vertx.redis.impl.Reply;
import io.vertx.redis.impl.ArgsImpl;
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
  private static final byte ARGS_PREFIX = '*';
  private static final byte[] CRLF = "\r\n".getBytes();
  private static final byte BYTES_PREFIX = '$';

  // waiting: commands that have been sent but not answered
  private final Queue<Req> waiting = new LinkedList<>();

  private final Context context;
  private final NetSocket netSocket;
  private final SocketAddress socketAddress;

  // state
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<io.vertx.redis.Reply> onMessage;

  public RedisConnectionImpl(Context context, NetClient netClient, NetSocket netSocket, SocketAddress endpoint) {
    this.context = context;
    this.netSocket = netSocket;
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

  void cleanupQueue(String errorMessage) {
    Req req;
    while ((req = waiting.poll()) != null) {
      try {
        req.handle(Future.failedFuture(errorMessage));
      } catch (RuntimeException e) {
        LOG.warn("Exception during cleanup", e);
      }
    }
  }

  void cleanupQueue(Throwable t) {
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
  public RedisConnection send(String command, Args args, boolean readOnly, Handler<AsyncResult<io.vertx.redis.Reply>> handler) {
    if (command == null) {
      handler.handle(Future.failedFuture("Command cannot be null"));
      return this;
    }

    // there is always 1 argument (the command)
    int totalArgs = 1;

    for (int i = 0; i < command.length(); i++) {
      if (command.charAt(i) == ' ') {
        totalArgs++;
      }
    }

    final boolean multiWordCommand = totalArgs > 1;

    if (args != null) {
      totalArgs += args.size();
    }

    // serialize the request
    final Buffer buffer = Buffer.buffer();
    buffer.appendByte(ARGS_PREFIX);
    buffer.appendBytes(ArgsImpl.numToBytes(totalArgs));
    buffer.appendBytes(CRLF);
    // serialize the command
    int last = 0;
    // will avoid running the loop again as we already know this
    // command is composed of a single word
    if (multiWordCommand) {
      for (int i = 0; i < command.length(); i++) {
        if (command.charAt(i) == ' ') {
          buffer.appendByte(BYTES_PREFIX);
          buffer.appendBytes(ArgsImpl.numToBytes(i - last));
          buffer.appendBytes(CRLF);
          buffer.appendString(command.substring(last, i));
          buffer.appendBytes(CRLF);
          last = ++i;
        }
      }
    }

    buffer.appendByte(BYTES_PREFIX);
    buffer.appendBytes(ArgsImpl.numToBytes(command.length() - last));
    buffer.appendBytes(CRLF);
    buffer.appendString(command.substring(last, command.length()));
    buffer.appendBytes(CRLF);
    // serialize arguments if any
    if (args != null) {
      buffer.appendBuffer(args.toBuffer());
    }

    final Req req = new Req(handler);

    // write to the socket in the netSocket context
    runOnContext(v -> {
      waiting.add(req);
      netSocket.write(buffer);
    });

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

  private void runOnContext(Handler<Void> handler) {
    // Use only if it's the same context and we are on the event loop thread
    if (Vertx.currentContext() == context && Context.isOnEventLoopThread()) {
      handler.handle(null);
    } else {
      context.runOnContext(handler);
    }
  }

  @Override
  public SocketAddress address() {
    return socketAddress;
  }
}
