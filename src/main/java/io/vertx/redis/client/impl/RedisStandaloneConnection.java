package io.vertx.redis.client.impl;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.future.PromiseInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.impl.pool.PoolConnector;
import io.vertx.core.spi.metrics.ClientMetrics;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.client.impl.types.Multi;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisStandaloneConnection implements RedisConnectionInternal, ParserHandler {

  private static final String BASE_ADDRESS = "io.vertx.redis";

  private static final Logger LOG = LoggerFactory.getLogger(RedisStandaloneConnection.class);

  private static final ErrorType CONNECTION_CLOSED = ErrorType.create("CONNECTION_CLOSED");

  private final PoolConnector.Listener listener;
  // to be used for callbacks
  private final VertxInternal vertx;
  // to be used for callbacks
  private final ContextInternal context;
  private final EventBus eventBus;
  private final NetSocket netSocket;
  private final long expiresAt;
  // waiting: commands that have been sent but not answered
  // the queue is only accessed from the event loop
  private final ArrayQueue waiting;
  private final RedisURI uri;
  private final ClientMetrics metrics;
  private final TracingPolicy tracingPolicy;

  // state
  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Response> onMessage;
  private Runnable onEvict;
  private boolean closed = false;
  private boolean tainted = false;

  public RedisStandaloneConnection(VertxInternal vertx, ContextInternal context, PoolConnector.Listener connectionListener, NetSocket netSocket, PoolOptions options, int maxWaitingHandlers, RedisURI uri, ClientMetrics metrics, TracingPolicy tracingPolicy) {
    //System.out.println("<ctor>#" + this.hashCode());
    this.vertx = vertx;
    this.context = context;
    this.listener = connectionListener;
    this.eventBus = vertx.eventBus();
    this.netSocket = netSocket;
    this.waiting = new ArrayQueue(maxWaitingHandlers);
    this.expiresAt = options.getRecycleTimeout() == -1 ? -1 : System.currentTimeMillis() + options.getRecycleTimeout();
    this.uri = uri;
    this.metrics = metrics;
    this.tracingPolicy = tracingPolicy;
  }

  synchronized void setValid() {
    //System.out.println("setValid()#" + this.hashCode());
    closed = false;
    // the `tainted` flag must be reset, as a `SELECT` or `AUTH` during the handshake
    // could have tainted the connection
    tainted = false;
  }

  @Override
  public void forceClose() {
    //System.out.println("forceClose()#" + this.hashCode());
    // The socket will call the endHandler which in turn will flip the closed flag
    netSocket.close();
  }

  @Override
  public boolean isValid() {
    //System.out.println("isValid()#" + this.hashCode());
    return !closed && (expiresAt <= 0 || System.currentTimeMillis() < expiresAt);
  }

  @Override
  public Future<Void> close() {
    //System.out.println("close()#" + this.hashCode());
    if (listener == null) {
      // no pool is being used. The socket will call the endHandler which in turn will flip the closed flag
      return netSocket.close();
    } else {
      // Pooled mode, we don't really close the socket, just mark the connection as closed.
      synchronized (this) {
        closed = true;
      }
      return Future.succeededFuture();
    }
  }

  @Override
  public boolean pendingQueueFull() {
    //System.out.println("pendingQueueFull()#" + this.hashCode());
    synchronized (waiting) {
      return waiting.isFull();
    }
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    //System.out.println("exceptionHandler()#" + this.hashCode());
    this.onException = handler;
    return this;
  }

  @Override
  public RedisConnection endHandler(Handler<Void> handler) {
    //System.out.println("endHandler()#" + this.hashCode());
    this.onEnd = handler;
    return this;
  }

  RedisConnection evictHandler(Runnable handler) {
    //System.out.println("evictHandler()#" + this.hashCode());
    this.onEvict = handler;
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    //System.out.println("handler()#" + this.hashCode());
    this.onMessage = handler;
    return this;
  }

  @Override
  public RedisConnection pause() {
    //System.out.println("pause()#" + this.hashCode());
    netSocket.pause();
    return this;
  }

  @Override
  public RedisConnection resume() {
    //System.out.println("resume()#" + this.hashCode());
    netSocket.resume();
    return this;
  }

  @Override
  public RedisConnection fetch(long size) {
    //System.out.println("fetch()#" + this.hashCode());
    netSocket.fetch(size);
    return this;
  }

  /**
   * Checks if an executed command has tainted the connection. A connection is tainted if it changes the default state,
   * for example, when a connection enters pub sub mode, or specific features are activated such as changing a database
   * or different authentication is used.
   * <p>
   * This is only relevant for pooled connections
   */
  private void taintCheck(CommandImpl cmd) {
    //System.out.println("taintCheck()#" + this.hashCode());
    if (listener != null) {
      if (cmd.isPubSub() || Command.SELECT.equals(cmd) || Command.AUTH.equals(cmd)) {
        tainted = true;
      }
    }
  }

  @Override
  public Future<Response> send(final Request request) {
    //System.out.println("send()#" + this.hashCode());
    final Promise<Response> promise;

    if (closed) {
      throw new IllegalStateException("Connection is closed");
    }

    if (!((RequestImpl) request).valid()) {
      return Future.failedFuture("Redis command is not valid, check https://redis.io/commands");
    }

    final CommandImpl cmd = (CommandImpl) request.command();

    // tag this connection as tainted if needed
    context.execute(cmd, this::taintCheck);

    final boolean voidCmd = cmd.isPubSub();
    // encode the message to a buffer
    final Buffer message = ((RequestImpl) request).encode();
    // offer the handler to the waiting queue if not void command
    if (!voidCmd) {
      // we might have switch thread/context
      synchronized (waiting) {
        if (waiting.isFull()) {
          return Future.failedFuture("Redis waiting Queue is full");
        }
        // create a new promise bound to the caller not
        // the instance of this object (a.k.a. "context")
        promise = vertx.promise();
        waiting.offer(promise);
      }
    } else {
      // create a new promise bound to the caller not
      // the instance of this object (a.k.a. "context")
      promise = vertx.promise();
    }
    // write to the socket
    try {
      netSocket.write(message)
        // if the write fails, this connection enters a unknown state
        // which means it should be terminated
        .onFailure(this::fail)
        .onSuccess(ok -> {
          if (voidCmd) {
            // only on this case notify the promise
            if (!promise.tryComplete()) {
              // if the promise fail (e.g.: an client error forced a cleanup)
              // call the exception handler if any
              if (onException != null) {
                context.execute(new IllegalStateException("Result is already complete: [" + promise + "]"), onException);
              }
            }
          }
        });
    } catch (RuntimeException err) {
      // is the socket in a broken state?
      context.execute(err, this::fail);
      promise.fail(err);
    }

    return promise.future();
  }

  @Override
  public Future<List<Response>> batch(List<Request> commands) {
    //System.out.println("batch()#" + this.hashCode());

    if (closed) {
      throw new IllegalStateException("Connection is closed");
    }

    if (commands.isEmpty()) {
      LOG.debug("Empty batch");
      return Future.succeededFuture(Collections.emptyList());
    } else {
      // create a new promise bound to the caller not
      // the instance of this object (a.k.a. "context")
      final Promise<List<Response>> promise = vertx.promise();

      // will re-encode the handler into a list of promises
      final List<Promise<Response>> callbacks = new ArrayList<>(commands.size());
      final Response[] replies = new Response[commands.size()];
      final AtomicInteger count = new AtomicInteger(commands.size());
      final StringBuilder errorMsg = new StringBuilder();

      // encode the message to a single buffer
      final Buffer messages = Buffer.buffer();

      for (int i = 0; i < commands.size(); i++) {
        final int index = i;
        final RequestImpl req = (RequestImpl) commands.get(index);
        final CommandImpl cmd = (CommandImpl) req.command();

        if (!req.valid()) {
          return Future.failedFuture("Redis command is not valid, check https://redis.io/commands");
        }

        if (cmd.isPubSub()) {
          // mixing pubSub cannot be used on a one-shot operation
          return Future.failedFuture("PubSub command in batch not allowed");
        }
        // encode to the single buffer
        req.encode(messages);
        // tag this connection as tainted if needed
        taintCheck(cmd);
        // unwrap the handler into a single handler
        callbacks.add(index, vertx.promise(command -> {
          if (command.failed()) {
            if (errorMsg.length() > 0) {
              errorMsg.append(System.lineSeparator());
            }
            String cause = null;
            if (command.cause() != null) {
              cause = command.cause().toString();
              if (cause != null) {
                if (cause.startsWith("ERR ")) {
                  // strip the ERR prefix
                  cause = cause.substring(4);
                }
              }
            }
            // the message rewrite is just to comply to the redis error message contract
            errorMsg.append("ERR [").append(index).append("] ").append(cause);
          } else {
            // set the reply
            replies[index] = command.result();
          }

          if (count.decrementAndGet() == 0) {
            // all results have arrived
            boolean resolved = errorMsg.length() > 0 ?
              promise.tryFail(ErrorType.create(errorMsg.toString())) :
              promise.tryComplete(Arrays.asList(replies));

            if (!resolved) {
              // if the promise fail (e.g.: an client error forced a cleanup)
              // call the exception handler if any
              if (onException != null) {
                context.execute(new IllegalStateException("Result is already complete: [" + promise + "]"), onException);
              }
            }
          }
        }));
      }

      synchronized (waiting) {
        // we might have switch thread/context
        // this means the check needs to be performed again
        if (waiting.freeSlots() < callbacks.size()) {
          return Future.failedFuture("Redis waiting Queue is full");
        }
        // offer all handlers to the waiting queue
        for (Promise<Response> callback : callbacks) {
          waiting.offer(callback);
        }
      }
      // write to the socket
      try {
        netSocket.write(messages)
          // if the write fails, this connection enters an unknown state
          // which means it should be terminated
          .onFailure(this::fail);
      } catch (RuntimeException err) {
        // is the socket in a broken state?
        context.execute(err, this::fail);
        promise.fail(err);
      }

      return promise.future();
    }
  }

  @Override
  public void handle(Response reply) {
    //System.out.println("handle()#" + this.hashCode());
    final boolean empty;
    final Promise<Response> req;

    synchronized (waiting) {
      empty = waiting.isEmpty();
      if (!empty) {
        req = waiting.poll();
      } else {
        req = null;
      }
    }

    // pub/sub mode
    if ((reply != null && reply.type() == ResponseType.PUSH) || empty) {
      if (onMessage != null) {
        context.execute(reply, onMessage);
      } else {
        // pub/sub messages are arrays
        if (reply instanceof Multi) {
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

    if (req != null) {
      final boolean resolved;
      if (reply == null) {
        // special case (nulls are always a success)
        // the reason is that nil is only a valid value for
        // bulk or multi
        resolved = req.tryComplete();
      } else {
        resolved = reply.type() == ResponseType.ERROR ?
          req.tryFail((ErrorType) reply) :
          req.tryComplete(reply);
      }

      if (!resolved) {
        // call the exception handler if any
        if (onException != null) {
          context.execute(new IllegalStateException("Result is already complete: [" + req + "]"), onException);
        }
      }

    } else {
      LOG.error("No handler waiting for message: " + reply);
    }
  }

  public synchronized void end(Void v) {
    //System.out.println("end()#" + this.hashCode());
    assert !closed;
    closed = true;
    // evict this connection from the pool
    evict();
    // clean up the pending queue
    cleanupQueue(CONNECTION_CLOSED);
    // call the end handler if any
    if (onEnd != null) {
      context.execute(v, onEnd);
    }
  }

  @Override
  public synchronized void fail(Throwable t) {
    //System.out.println("fail()#" + this.hashCode());
    assert !closed;
    closed = true;
    // evict this connection from the pool
    evict();
    // if there are still "on going" requests
    // these are all cancelled with the given
    // throwable
    cleanupQueue(t);
    // call the exception handler if any
    if (onException != null) {
      context.execute(t, onException);
    }
  }

  @Override
  public synchronized boolean reset() {
    //System.out.println("reset()#" + this.hashCode());
    // cannot reset if connection is already closed
    if (closed) {
      return false;
    }
    // cannot reset if connection is tainted (custom DB/AUTH/PUBSUB)
    if (tainted) {
      evict();
      forceClose();
      return false;
    }
    return true;
  }

  private void evict() {
    //System.out.println("evict()#" + this.hashCode());
    // evict this connection from the pool
    if (listener != null) {
      listener.onRemove();
    }
    if (onEvict != null) {
      onEvict.run();
    }
  }

  private synchronized void cleanupQueue(Throwable t) {
    //System.out.println("cleanupQueue()#" + this.hashCode());
    Promise<Response> req;
    synchronized (waiting) {
      while ((req = waiting.poll()) != null) {
        if (req instanceof PromiseInternal) {
          if (((PromiseInternal<?>) req).isComplete()) {
            // skip if already resolved
            continue;
          }
        }
        try {
          req.tryFail(t);
        } catch (RuntimeException err) {
          LOG.warn("Exception while running cleanup", err);
        }
      }
    }
  }

  @Override
  public boolean isTainted() {
    return tainted;
  }

  @Override
  public VertxInternal vertx() {
    return vertx;
  }

  @Override
  public RedisURI uri() {
    return uri;
  }

  @Override
  public ClientMetrics metrics() {
    return metrics;
  }

  @Override
  public TracingPolicy tracingPolicy() {
    return tracingPolicy;
  }
}
