package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.http.impl.pool.ConnectResult;
import io.vertx.core.http.impl.pool.ConnectionListener;
import io.vertx.core.http.impl.pool.ConnectionProvider;
import io.vertx.core.http.impl.pool.Pool;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);

  private static final Handler<Throwable> DEFAULT_EXCEPTION_HANDLER = t -> LOG.error("Unhandled Error", t);

  private final Vertx vertx;
  private final NetClient netClient;

  private final RedisOptions options;
  private final Map<String, RedisConnection> connectionMap = new ConcurrentHashMap<>();
  private final Map<String, Pool<RedisConnection>> endpointMap = new ConcurrentHashMap<>();
  private long timerID;

  ConnectionManager(Vertx vertx, RedisOptions options) {
    this.vertx = vertx;
    this.options = options;
    this.netClient = vertx.createNetClient(options.getNetClientOptions());
  }

  synchronized void start() {
    long period = options.getPoolCleanerInterval();
    this.timerID = period > 0 ? vertx.setTimer(period, id -> checkExpired(period)) : -1;
  }

  private synchronized void checkExpired(long period) {
    endpointMap.values().forEach(Pool::closeIdle);
    timerID = vertx.setTimer(period, id -> checkExpired(period));
  }

  class RedisConnectionProvider implements ConnectionProvider<RedisConnection> {

    private final RedisURI redisURI;
    private final Request setup;

    public RedisConnectionProvider(String connectionString, Request setup) {
      this.redisURI = new RedisURI(connectionString);
      this.setup = setup;
    }

    @Override
    public boolean isValid(RedisConnection conn) {
      return ((RedisConnectionImpl)conn).isValid();
    }

    @Override
    public void connect(ConnectionListener<RedisConnection> connectionListener, ContextInternal ctx, Handler<AsyncResult<ConnectResult<RedisConnection>>> onConnect) {
      // all calls the user handler will happen in the user context (ctx)
      netClient.connect(redisURI.socketAddress(), clientConnect -> {
        if (clientConnect.failed()) {
          // connection failed
          ctx.runOnContext(v -> onConnect.handle(Future.failedFuture(clientConnect.cause())));
          return;
        }

        // socket connection succeeded
        final NetSocket netSocket = clientConnect.result();
        // the connection will inherit the user event loop context
        final RedisConnectionImpl connection = new RedisConnectionImpl(vertx, ctx, connectionListener, netSocket, options);

        // parser utility
        netSocket
          .handler(new RESPParser(connection, options.getMaxNestedArrays()))
          .closeHandler(connection::end)
          .exceptionHandler(connection::fatal);

        // perform authentication
        authenticate(connection, redisURI.password(), authenticate -> {
          if (authenticate.failed()) {
            ctx.runOnContext(v -> onConnect.handle(Future.failedFuture(authenticate.cause())));
            return;
          }

          // perform select
          select(connection, redisURI.select(), select -> {
            if (select.failed()) {
              ctx.runOnContext(v -> onConnect.handle(Future.failedFuture(select.cause())));
              return;
            }

            // perform setup
            setup(connection, setup, setupResult -> {
              if (setupResult.failed()) {
                ctx.runOnContext(v -> onConnect.handle(Future.failedFuture(setupResult.cause())));
                return;
              }

              // initialization complete
              connection.handler(null);
              connection.endHandler(null);
              connection.exceptionHandler(DEFAULT_EXCEPTION_HANDLER);

              ctx.runOnContext(v -> onConnect.handle(Future.succeededFuture(new ConnectResult<>(connection, 1, options.getMaxPoolSize()))));
            });
          });
        });
      });
    }

    private void authenticate(RedisConnection connection, String password, Handler<AsyncResult<Void>> handler) {
      if (password == null) {
        handler.handle(Future.succeededFuture());
        return;
      }
      // perform authentication
      connection.send(Request.cmd(Command.AUTH).arg(password), auth -> {
        if (auth.failed()) {
          handler.handle(Future.failedFuture(auth.cause()));
        } else {
          handler.handle(Future.succeededFuture());
        }
      });
    }

    private void select(RedisConnection connection, Integer select, Handler<AsyncResult<Void>> handler) {
      if (select == null) {
        handler.handle(Future.succeededFuture());
        return;
      }
      // perform select
      connection.send(Request.cmd(Command.SELECT).arg(select), auth -> {
        if (auth.failed()) {
          handler.handle(Future.failedFuture(auth.cause()));
        } else {
          handler.handle(Future.succeededFuture());
        }
      });
    }

    private void setup(RedisConnection connection, Request setup, Handler<AsyncResult<Void>> handler) {
      if (setup == null) {
        handler.handle(Future.succeededFuture());
        return;
      }
      // perform setup
      connection.send(setup, req -> {
        if (req.failed()) {
          handler.handle(Future.failedFuture(req.cause()));
        } else {
          handler.handle(Future.succeededFuture());
        }
      });
    }

    @Override
    public void close(RedisConnection connection) {
      // on close we reset the default handlers
      connection.handler(null);
      connection.endHandler(null);
      connection.exceptionHandler(null);
      ((RedisConnectionImpl) connection).forceClose();
    }
  }

  public void getConnection(Context userContext, String connectionString, Request setup, Handler<AsyncResult<RedisConnection>> handler) {
    final ConnectionProvider<RedisConnection> connectionProvider = new RedisConnectionProvider(connectionString, setup);
    while (true) {
      Pool<RedisConnection> endpoint = endpointMap.computeIfAbsent(connectionString, targetAddress ->
        new Pool<>(
          userContext,
          connectionProvider,
          options.getMaxPoolWaiting(),
          options.getMaxPoolSize(),
          options.getMaxPoolSize() * options.getMaxPoolSize(),
          v -> endpointMap.remove(connectionString),
          conn -> connectionMap.put(connectionString, conn),
          conn -> connectionMap.remove(connectionString, conn),
          false));
      if (endpoint.getConnection(handler)) {
        break;
      }
    }
  }

  public void close() {
    synchronized (this) {
      if (timerID >= 0) {
        vertx.cancelTimer(timerID);
        timerID = -1;
      }
    }
    endpointMap.clear();
    for (RedisConnection conn : connectionMap.values()) {
      ((RedisConnectionImpl) conn).forceClose();
    }
    // forceClose the underlying netclient
    netClient.close();
  }
}
