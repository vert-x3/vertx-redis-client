package io.vertx.redis.impl;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.Args;
import io.vertx.redis.Redis;

import java.util.LinkedList;
import java.util.Queue;

public class RedisImpl implements Redis, Handler<Reply> {

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

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnection.class);
  private static final byte ARGS_PREFIX = '*';
  private static final byte[] CRLF = "\r\n".getBytes();
  private static final byte BYTES_PREFIX = '$';

  // waiting: commands that have been sent but not answered
  private final Queue<Req> waiting = new LinkedList<>();
  // parser utility
  private final ReplyParser parser = new ReplyParser(this);

  private final Vertx vertx;
  private final Context context;

  // state
  private NetSocket netSocket;
  private Handler<Throwable> onException;
  private Handler<Void> onClose;
  private Handler<io.vertx.redis.Reply> onMessage;

  public RedisImpl(Vertx vertx) {
    // Make sure we have an event loop context for serializability of the commands
    Context ctx = Vertx.currentContext();
    if (ctx == null) {
      ctx = vertx.getOrCreateContext();
    } else if (!ctx.isEventLoopContext()) {
      VertxInternal vi = (VertxInternal) vertx;
      ctx = vi.createEventLoopContext(null, null, new JsonObject(), Thread.currentThread().getContextClassLoader());
    }

    this.vertx = vertx;
    this.context = ctx;
  }

  @Override
  public Redis open(SocketAddress socketAddress, NetClientOptions options, Handler<AsyncResult<Void>> onOpen) {
    parser.reset();
    // create a netClient for the connection
    final NetClient client = vertx.createNetClient(options);
    client.connect(socketAddress, asyncResult -> {
      if (asyncResult.failed()) {
        client.close();
        onOpen.handle(Future.failedFuture(asyncResult.cause()));
        return;
      }
      netSocket = asyncResult.result();

      netSocket
        .handler(parser)
        .closeHandler(close -> {
          client.close();
          // clean up the pending queue
          Req req;
          while ((req = waiting.poll()) != null) {
            try {
              req.handle(Future.failedFuture("Connection closed"));
            } catch (RuntimeException e) {
              LOG.warn("Exception during cleanup", e);
            }
          }
          // call the close handler if any
          if (onClose != null) {
            onClose.handle(close);
          }
        })
        .exceptionHandler(exception -> {
          netSocket.close();
          client.close();
          // clean up the pending queue
          Req req;
          while ((req = waiting.poll()) != null) {
            try {
              req.handle(Future.failedFuture(exception));
            } catch (RuntimeException e) {
              LOG.warn("Exception during cleanup", e);
            }
          }
          // call the exception handler if any
          if (onException != null) {
            onException.handle(exception);
          }
        });

      // ready
      onOpen.handle(Future.succeededFuture());
    });

    return this;
  }

  @Override
  public void close() {
    if (netSocket != null) {
      netSocket.close();
    }
  }

  @Override
  public Redis exceptionHandler(Handler<Throwable> handler) {
    this.onException = handler;
    return this;
  }

  @Override
  public Redis closeHandler(Handler<Void> handler) {
    this.onClose = handler;
    return this;
  }

  @Override
  public Redis handler(Handler<io.vertx.redis.Reply> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public Redis send(String command, Args args, Handler<AsyncResult<io.vertx.redis.Reply>> handler) {
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
        req.handle(Future.failedFuture(reply.status()));
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
}
