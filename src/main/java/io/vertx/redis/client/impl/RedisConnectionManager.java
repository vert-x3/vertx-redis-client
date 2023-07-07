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

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.EventLoopContext;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.future.PromiseInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.impl.pool.*;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.Objects;

class RedisConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionManager.class);

  private static final Handler<Throwable> DEFAULT_EXCEPTION_HANDLER = t -> LOG.error("Unhandled Error", t);

  private final VertxInternal vertx;
  private final NetClient netClient;
  private final PoolMetrics metrics;
  private final NetClientOptions tcpOptions;
  private final PoolOptions poolOptions;
  private final RedisConnectOptions connectOptions;

  private final ConnectionManager<ConnectionKey, Lease<RedisConnectionInternal>> pooledConnectionManager;
  private long timerID;

  RedisConnectionManager(VertxInternal vertx, NetClientOptions tcpOptions, PoolOptions poolOptions, RedisConnectOptions connectOptions) {
    this.vertx = vertx;
    this.tcpOptions = tcpOptions;
    this.poolOptions = poolOptions;
    this.connectOptions = connectOptions;
    VertxMetrics metricsSPI = this.vertx.metricsSPI();
    metrics = metricsSPI != null ? metricsSPI.createPoolMetrics("redis", poolOptions.getName(), poolOptions.getMaxSize()) : null;
    this.netClient = vertx.createNetClient(tcpOptions);
    this.pooledConnectionManager = new ConnectionManager<>((key, dispose) -> connectionEndpointProvider(dispose, key.string, key.setup));
  }

  private Endpoint<Lease<RedisConnectionInternal>> connectionEndpointProvider(Runnable dispose, String connectionString, Request setup) {
    return new RedisEndpoint(vertx, netClient, tcpOptions, poolOptions, connectOptions, dispose, connectionString, setup);
  }

  synchronized void start() {
    long period = poolOptions.getCleanerInterval();
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
    private final NetClientOptions netClientOptions;
    private final PoolOptions poolOptions;
    private final RedisConnectOptions options;

    public RedisConnectionProvider(VertxInternal vertx, NetClient netClient, NetClientOptions netClientOptions, PoolOptions poolOptions, RedisConnectOptions options, String connectionString, Request setup) {
      this.vertx = vertx;
      this.netClient = netClient;
      this.netClientOptions = netClientOptions;
      this.poolOptions = poolOptions;
      this.options = options;
      this.redisURI = new RedisURI(connectionString);
      this.setup = setup;
    }

    @Override
    public boolean isValid(RedisConnectionInternal conn) {
      return conn.isValid();
    }

    @Override
    public Future<ConnectResult<RedisConnectionInternal>> connect(EventLoopContext ctx, Listener listener) {
      // verify if we can make this connection
      final boolean netClientSsl = netClientOptions.isSsl();
      final boolean connectionStringSsl = redisURI.ssl();
      final boolean connectionStringInetSocket = redisURI.socketAddress().isInetSocket();

      // when dealing with sockets, ssl is only covered in case of inet sockets
      // not domain sockets
      if (connectionStringInetSocket) {
        // net client is ssl and connection string is not ssl is not allowed
        if (netClientSsl && !connectionStringSsl) {
          return ctx.failedFuture("Pool initialized with SSL but connection requested plain socket");
        }
      }

      // all calls the user handler will happen in the user context (ctx)
      return connectAndSetup(ctx, listener, connectionStringInetSocket, connectionStringSsl, netClientSsl);
    }

    private Future<ConnectResult<RedisConnectionInternal>> connectAndSetup(
      ContextInternal ctx,
      Listener listener,
      boolean connectionStringInetSocket,
      boolean connectionStringSsl,
      boolean netClientSsl) {
      try {
        return netClient
          .connect(redisURI.socketAddress())
          .compose(so -> {
            // upgrade to ssl is only possible for inet sockets
            if (connectionStringInetSocket && !netClientSsl && connectionStringSsl) {
              // must upgrade protocol
              return so
                .upgradeToSsl()
                .compose(v -> init(ctx, so, listener));
            } else {
              // no need to upgrade
              return init(ctx, so, listener);
            }
          });
      } catch (RuntimeException err) {
        // the netClient is in a closed state?
        return ctx.failedFuture(err);
      }
    }

    private Future<ConnectResult<RedisConnectionInternal>> init(ContextInternal ctx, NetSocket netSocket, PoolConnector.Listener connectionListener) {
      // the connection will inherit the user event loop context
      final RedisStandaloneConnection connection = new RedisStandaloneConnection(vertx, ctx, connectionListener, netSocket, poolOptions, options.getMaxWaitingHandlers());
      // initialization
      connection.exceptionHandler(DEFAULT_EXCEPTION_HANDLER);

      // parser utility
      netSocket
        .handler(new RESPParser(connection, options.getMaxNestedArrays()))
        .closeHandler(connection::end)
        .exceptionHandler(connection::fail);

      // initial handshake
      return hello(ctx, connection, redisURI)
        .compose(hello -> {
          // perform select
          return select(ctx, connection, redisURI.select());
        }).compose(select -> {
          // perform setup
          return setup(ctx, connection, setup);
        }).map(setup -> {
          // connection is valid
          connection.setValid();
          return new ConnectResult<>(connection, 1, 0);
        });
    }

    private Future<Void> hello(ContextInternal ctx, RedisConnection connection, RedisURI redisURI) {
      if (!options.isProtocolNegotiation()) {
        return ping(ctx, connection);
      } else {
        Request hello = Request.cmd(Command.HELLO).arg(RESPParser.VERSION);

        String password = redisURI.password() != null ? redisURI.password() : options.getPassword();
        String user = redisURI.user();

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

        return connection
          .send(hello)
          .<Void>mapEmpty()
          .transform(ar -> {
            if (ar.failed()) {
              Throwable err = ar.cause();
              if (err instanceof ErrorType) {
                final ErrorType redisErr = (ErrorType) err;
                if (redisErr.is("NOAUTH")) {
                  return authenticate(ctx, connection, user, password);
                }
                if (redisErr.is("ERR")) {
                  String msg = redisErr.getMessage();
                  if (msg.startsWith("ERR unknown command") || msg.startsWith("ERR unknown or unsupported command")) {
                    // chatting to an old server
                    return ping(ctx, connection);
                  }
                }
              }
            } else {
              LOG.debug(ar.result());
            }
            return (Future<Void>) ar;
          });
      }
    }

    private Future<Void> ping(ContextInternal ctx, RedisConnection connection) {
      Request ping = Request.cmd(Command.PING);

      return connection
        .send(ping)
        .onSuccess(LOG::debug)
        .transform(ar -> {
          if (ar.failed()) {
            Throwable err = ar.cause();
            if (err instanceof ErrorType) {
              if (((ErrorType) err).is("NOAUTH")) {
                // old authentication required
                String password = redisURI.password() != null ? redisURI.password() : options.getPassword();
                return this.authenticate(ctx, connection, redisURI.user(), password);
              }
            }
          }
          return ((Future<Response>) ar).mapEmpty();
        });
    }

    private Future<Void> authenticate(ContextInternal ctx, RedisConnection connection, String user, String password) {
      if (password == null) {
        return ctx.succeededFuture();
      }

      // perform authentication
      final Request cmd = Request.cmd(Command.AUTH);
      // when working with ACLs (Redis >= 6) we may use usernames
      if (user != null) {
        cmd.arg(user);
      }
      cmd.arg(password);

      return connection.send(cmd).mapEmpty();
    }

    private Future<Void> select(ContextInternal ctx, RedisConnection connection, Integer select) {
      if (select == null) {
        return ctx.succeededFuture();
      }
      // perform select
      return connection
        .send(Request.cmd(Command.SELECT).arg(select))
        .mapEmpty();
    }

    private Future<Void> setup(ContextInternal ctx, RedisConnection connection, Request setup) {
      if (setup == null) {
        return ctx.succeededFuture();
      }
      // perform setup
      return connection
        .send(setup)
        .mapEmpty();
    }
  }

  public Future<RedisConnection> getConnection(String connectionString, Request setup) {
    final ContextInternal context = vertx.getOrCreateContext();
    final EventLoopContext eventLoopContext;
    if (context instanceof EventLoopContext) {
      eventLoopContext = (EventLoopContext) context;
    } else {
      eventLoopContext = vertx.createEventLoopContext(context.nettyEventLoop(), context.workerPool(), context.classLoader());
    }

    final boolean metricsEnabled = metrics != null;
    final Object queueMetric = metricsEnabled ? metrics.submitted() : null;

    Future<Lease<RedisConnectionInternal>> future = pooledConnectionManager.getConnection(eventLoopContext, new ConnectionKey(connectionString, setup));
    return future
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

    public RedisEndpoint(VertxInternal vertx, NetClient netClient, NetClientOptions netClientOptions, PoolOptions poolOptions, RedisConnectOptions connectOptions, Runnable dispose, String connectionString, Request setup) {
      super(dispose);
      PoolConnector<RedisConnectionInternal> connector = new RedisConnectionProvider(vertx, netClient, netClientOptions, poolOptions, connectOptions, connectionString, setup);
      pool = ConnectionPool.pool(connector, new int[]{poolOptions.getMaxSize()}, poolOptions.getMaxWaiting());
    }

    @Override
    public Future<Lease<RedisConnectionInternal>> requestConnection(ContextInternal ctx, long timeout) {
      PromiseInternal<Lease<RedisConnectionInternal>> promise = ctx.promise();
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
        promise.handle(ar);
      });
      return promise.future();
    }
  }
}
