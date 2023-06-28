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
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.EventLoopContext;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.impl.pool.ConnectResult;
import io.vertx.core.net.impl.pool.ConnectionManager;
import io.vertx.core.net.impl.pool.Endpoint;
import io.vertx.core.net.impl.pool.Lease;
import io.vertx.core.net.impl.pool.ConnectionPool;
import io.vertx.core.net.impl.pool.PoolConnector;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.Objects;
import java.util.function.Supplier;

class RedisConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionManager.class);

  private static final Handler<Throwable> DEFAULT_EXCEPTION_HANDLER = t -> LOG.error("Unhandled Error", t);

  private final VertxInternal vertx;
  private final ContextInternal context;
  private final NetClient netClient;
  private final PoolMetrics metrics;

  private final RedisOptions options;
  private final Supplier<Future<RedisOptions>> optionsSupplier;
  private final ConnectionManager<ConnectionKey, Lease<RedisConnectionInternal>> pooledConnectionManager;
  private long timerID;

  RedisConnectionManager(VertxInternal vertx,
                         RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier) {
    this.vertx = vertx;
    this.context = vertx.getOrCreateContext();
    this.options = options;
    this.optionsSupplier = optionsSupplier;
    VertxMetrics metricsSPI = this.vertx.metricsSPI();
    metrics = metricsSPI != null ? metricsSPI.createPoolMetrics("redis", options.getPoolName(), options.getMaxPoolSize()) : null;
    this.netClient = vertx.createNetClient(options.getNetClientOptions());
    this.pooledConnectionManager = new ConnectionManager<>();
  }

  private Endpoint<Lease<RedisConnectionInternal>> connectionEndpointProvider(ContextInternal ctx, Runnable dispose, String connectionString, Request setup) {
    return new RedisEndpoint(vertx, netClient, options, optionsSupplier, dispose, connectionString, setup);
  }

  synchronized void start() {
    long period = options.getPoolCleanerInterval();
    this.timerID = period > 0 ? vertx.setTimer(period, id -> checkExpired(period)) : -1;
  }

  private void checkExpired(long period) {
    pooledConnectionManager.forEach(e ->
      ((RedisEndpoint) e).pool.evict(conn -> !conn.isValid(), ar -> {
        if (ar.succeeded()) {
          for (RedisConnectionInternal conn : ar.result()) {
            // on close we reset the default handlers
            conn.handler(null);
            conn.endHandler(null);
            conn.exceptionHandler(null);
            conn.forceClose();
          }
        }
      }));
    timerID = vertx.setTimer(period, id -> checkExpired(period));
  }

  static class ConnectionKey {
    private final String string;
    private final Request setup;

    ConnectionKey(String string, Request setup) {
      this.string = string;
      this.setup = setup;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ConnectionKey that = (ConnectionKey) o;
      return Objects.equals(string, that.string) && Objects.equals(setup, that.setup);
    }

    @Override
    public int hashCode() {
      return Objects.hash(string, setup);
    }
  }

  static class RedisConnectionProvider implements PoolConnector<RedisConnectionInternal> {

    private final VertxInternal vertx;
    private final NetClient netClient;
    private final RedisURI redisURI;
    private final Request setup;
    private final RedisOptions options;
    private final Supplier<Future<RedisOptions>> optionsSupplier;

    public RedisConnectionProvider(VertxInternal vertx, NetClient netClient,
                                   RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier,
                                   String connectionString, Request setup) {
      this.vertx = vertx;
      this.netClient = netClient;
      this.options = options;
      this.optionsSupplier = optionsSupplier;
      this.redisURI = new RedisURI(connectionString);
      this.setup = setup;
    }

    @Override
    public boolean isValid(RedisConnectionInternal conn) {
      return conn.isValid();
    }

    @Override
    public void connect(EventLoopContext ctx, Listener listener, Handler<AsyncResult<ConnectResult<RedisConnectionInternal>>> onConnect) {
      // verify if we can make this connection
      final boolean netClientSsl = options.getNetClientOptions().isSsl();
      final boolean connectionStringSsl = redisURI.ssl();
      final boolean connectionStringInetSocket = redisURI.socketAddress().isInetSocket();

      // when dealing with sockets, ssl is only covered in case of inet sockets
      // not domain sockets
      if (connectionStringInetSocket) {
        // net client is ssl and connection string is not ssl is not allowed
        if (netClientSsl && !connectionStringSsl) {
          ctx.execute(ctx.failedFuture("Pool initialized with SSL but connection requested plain socket"), onConnect);
          return;
        }
      }

      // all calls the user handler will happen in the user context (ctx)
      try {
        netClient
          .connect(redisURI.socketAddress(), clientConnect -> {
            if (clientConnect.failed()) {
              // connection failed
              ctx.execute(ctx.failedFuture(clientConnect.cause()), onConnect);
              return;
            }

            // socket connection succeeded
            final NetSocket netSocket = clientConnect.result();

            // upgrade to ssl is only possible for inet sockets
            if (connectionStringInetSocket && !netClientSsl && connectionStringSsl) {
              // must upgrade protocol
              netSocket.upgradeToSsl(upgradeToSsl -> {
                if (upgradeToSsl.failed()) {
                  ctx.execute(ctx.failedFuture(upgradeToSsl.cause()), onConnect);
                } else {
                  // complete the connection
                  init(ctx, netSocket, listener, onConnect);
                }
              });
            } else {
              // no need to upgrade
              init(ctx, netSocket, listener, onConnect);
            }
          });
      } catch (RuntimeException err) {
        // the netClient is in a closed state?
        ctx.execute(ctx.failedFuture(err), onConnect);
      }
    }

    private void init(ContextInternal ctx, NetSocket netSocket, PoolConnector.Listener connectionListener, Handler<AsyncResult<ConnectResult<RedisConnectionInternal>>> onConnect) {
      // the connection will inherit the user event loop context
      final RedisStandaloneConnection connection = new RedisStandaloneConnection(vertx, ctx, connectionListener, netSocket, options);
      // initialization
      connection.exceptionHandler(DEFAULT_EXCEPTION_HANDLER);

      optionsSupplier.get().onComplete(optionsResult -> {
        if (optionsResult.failed()) {
          ctx.execute(ctx.failedFuture(optionsResult.cause()), onConnect);
        }

        final RedisOptions options = optionsResult.result();

        // parser utility
        netSocket
          .handler(new RESPParser(connection, options.getMaxNestedArrays()))
          .closeHandler(connection::end)
          .exceptionHandler(connection::fail);

        // initial handshake
        hello(ctx, connection, redisURI, options, hello -> {
          if (hello.failed()) {
            ctx.execute(ctx.failedFuture(hello.cause()), onConnect);
            return;
          }

          // perform select
          select(ctx, connection, redisURI.select(), select -> {
            if (select.failed()) {
              ctx.execute(ctx.failedFuture(select.cause()), onConnect);
              return;
            }

            // perform setup
            setup(ctx, connection, setup, setupResult -> {
              if (setupResult.failed()) {
                ctx.execute(ctx.failedFuture(setupResult.cause()), onConnect);
                return;
              }

              // connection is valid
              connection.setValid();

              ctx.execute(ctx.succeededFuture(new ConnectResult<>(connection, 1, 0)), onConnect);
            });
          });
        });
      });

    }

    private void resolveMutableOptions(final ContextInternal ctx, Handler<AsyncResult<RedisOptions>> handler) {
      optionsSupplier.get().onComplete(handler);
    }

    private void hello(ContextInternal ctx, RedisConnection connection, RedisURI redisURI, RedisOptions options,
                       Handler<AsyncResult<Void>> handler) {
      if (!options.isProtocolNegotiation()) {
        ping(ctx, connection, options, handler);
      } else {
        Request hello = Request.cmd(Command.HELLO).arg(RESPParser.VERSION);

        String password = redisURI.password() != null ? redisURI.password() : options.getPassword();
        String user = redisURI.user() != null ? redisURI.user() :  options.getUser();

        if (password != null) {
          // will perform auth at hello level
          hello
            .arg("AUTH")
            .arg(user == null ? "default" : user)
            .arg(password);
        }

        String client = redisURI.param("client");
        if (client != null) {
          hello.arg("SETNAME").arg(client);
        }

        connection.send(hello, onSend -> {
          if (onSend.succeeded()) {
            LOG.debug(onSend.result());
            ctx.execute(ctx.succeededFuture(), handler);
            return;
          }

          final Throwable err = onSend.cause();
          if (err != null) {
            if (err instanceof ErrorType) {
              final ErrorType redisErr = (ErrorType) err;
              if (redisErr.is("NOAUTH")) {
                authenticate(ctx, connection, user, password, handler);
                return;
              }
              if (redisErr.is("ERR")) {
                String msg = redisErr.getMessage();
                if (msg.startsWith("ERR unknown command") || msg.startsWith("ERR unknown or unsupported command")) {
                  // chatting to an old server
                  ping(ctx, connection, options, handler);
                }
                return;
              }
            }
          }

          ctx.execute(ctx.failedFuture(err), handler);
        });
      }
    }

    private void ping(ContextInternal ctx, RedisConnection connection, RedisOptions options,
                      Handler<AsyncResult<Void>> handler) {
      Request ping = Request.cmd(Command.PING);

      connection.send(ping, onSend -> {
        if (onSend.succeeded()) {
          LOG.debug(onSend.result());
          ctx.execute(ctx.succeededFuture(), handler);
          return;
        }

        final Throwable err = onSend.cause();
        if (err != null) {
          if (err instanceof ErrorType) {
            if (((ErrorType) err).is("NOAUTH")) {
              // old authentication required
              String password = redisURI.password() != null ? redisURI.password() : options.getPassword();
              authenticate(ctx, connection, redisURI.user() != null ? redisURI.user() :  options.getUser(), password, handler);
              return;
            }
          }
        }

        ctx.execute(ctx.failedFuture(err), handler);
      });
    }

    private void authenticate(ContextInternal ctx, RedisConnection connection, String user, String password, Handler<AsyncResult<Void>> handler) {
      if (password == null) {
        ctx.execute(ctx.succeededFuture(), handler);
        return;
      }

      // perform authentication
      final Request cmd = Request.cmd(Command.AUTH);
      // when working with ACLs (Redis >= 6) we may use usernames
      if (user != null) {
        cmd.arg(user);
      }
      cmd.arg(password);

      connection.send(cmd, auth -> {
        if (auth.failed()) {
          ctx.execute(ctx.failedFuture(auth.cause()), handler);
        } else {
          ctx.execute(ctx.succeededFuture(), handler);
        }
      });
    }

    private void select(ContextInternal ctx, RedisConnection connection, Integer select, Handler<AsyncResult<Void>> handler) {
      if (select == null) {
        ctx.execute(ctx.succeededFuture(), handler);
        return;
      }
      // perform select
      connection.send(Request.cmd(Command.SELECT).arg(select), auth -> {
        if (auth.failed()) {
          ctx.execute(ctx.failedFuture(auth.cause()), handler);
        } else {
          ctx.execute(ctx.succeededFuture(), handler);
        }
      });
    }

    private void setup(ContextInternal ctx, RedisConnection connection, Request setup, Handler<AsyncResult<Void>> handler) {
      if (setup == null) {
        ctx.execute(ctx.succeededFuture(), handler);
        return;
      }
      // perform setup
      connection.send(setup, req -> {
        if (req.failed()) {
          ctx.execute(ctx.failedFuture(req.cause()), handler);
        } else {
          ctx.execute(ctx.succeededFuture(), handler);
        }
      });
    }
  }

  public Future<RedisConnection> getConnection(String connectionString, Request setup) {
    final Promise<Lease<RedisConnectionInternal>> promise = vertx.promise();
    final EventLoopContext eventLoopContext;
    if (context instanceof EventLoopContext) {
      eventLoopContext = (EventLoopContext) context;
    } else {
      eventLoopContext = vertx.createEventLoopContext(context.nettyEventLoop(), context.workerPool(), context.classLoader());
    }

    final boolean metricsEnabled = metrics != null;
    final Object queueMetric = metricsEnabled ? metrics.submitted() : null;

    pooledConnectionManager.getConnection(eventLoopContext, new ConnectionKey(connectionString, setup), (ctx, dispose) -> connectionEndpointProvider(ctx, dispose, connectionString, setup), promise);
    return promise.future()
      .onFailure(err -> {
        if (metricsEnabled) {
          metrics.rejected(queueMetric);
        }
      })
      .compose(lease -> Future.succeededFuture(new PooledRedisConnection(lease, metrics, metricsEnabled ? metrics.begin(queueMetric) : null)));
  }

  public void close() {
    synchronized (this) {
      if (timerID >= 0) {
        vertx.cancelTimer(timerID);
        timerID = -1;
      }
    }
    pooledConnectionManager.close();
    netClient.close();
    if (metrics != null) {
      metrics.close();
    }
  }

  static class RedisEndpoint extends Endpoint<Lease<RedisConnectionInternal>> {

    final ConnectionPool<RedisConnectionInternal> pool;

    public RedisEndpoint(VertxInternal vertx, NetClient netClient,
                         RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier,
                         Runnable dispose, String connectionString, Request setup) {
      super(dispose);
      PoolConnector<RedisConnectionInternal> connector = new RedisConnectionProvider(vertx, netClient,
        options, optionsSupplier, connectionString, setup);
      pool = ConnectionPool.pool(connector, new int[]{options.getMaxPoolSize()}, options.getMaxPoolWaiting());
    }

    @Override
    public void requestConnection(ContextInternal ctx, long timeout, Handler<AsyncResult<Lease<RedisConnectionInternal>>> handler) {
      pool.acquire(ctx, 0, ar -> {
        if (ar.succeeded()) {
          // increment the reference counter to avoid the pool to be closed too soon
          // once there are no more connections the pool is collected, so this counter needs
          // to be as up to date as possible.
          incRefCount();
          // Integration between endpoint/pool and the standalone connection
          ((RedisStandaloneConnection) ar.result().get())
            .evictHandler(this::decRefCount);
        }
        // proceed to user
        handler.handle(ar);
      });
    }
  }
}
