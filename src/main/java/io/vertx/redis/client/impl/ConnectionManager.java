package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.impl.pool.ConnectResult;
import io.vertx.core.http.impl.pool.ConnectionListener;
import io.vertx.core.http.impl.pool.ConnectionProvider;
import io.vertx.core.http.impl.pool.Pool;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.client.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

class ConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);

  private static final LongSupplier CLOCK = System::currentTimeMillis;
  private static final Handler<Throwable> DEFAULT_EXCEPTION_HANDLER = t -> LOG.error("Unhandled Error", t);

  private final Vertx vertx;
  private final ContextInternal ctx;
  private final NetClient netClient;

  private final RedisOptions options;
  private final Map<String, RedisConnection> connectionMap = new ConcurrentHashMap<>();
  private final Map<String, Pool<RedisConnection>> endpointMap = new ConcurrentHashMap<>();
  private long timerID;

  ConnectionManager(Vertx vertx, RedisOptions options) {
    this.vertx = vertx;
    this.options = options;

    this.ctx = (ContextInternal) vertx.getOrCreateContext();
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

    public RedisConnectionProvider(String address, Request setup) {
      this.redisURI = new RedisURI(address);
      this.setup = setup;
    }

    @Override
    public void connect(ConnectionListener<RedisConnection> connectionListener, ContextInternal contextInternal, Handler<AsyncResult<ConnectResult<RedisConnection>>> onConnect) {
      netClient.connect(redisURI.socketAddress(), clientConnect -> {
        if (clientConnect.failed()) {
          // connection failed
          onConnect.handle(Future.failedFuture(clientConnect.cause()));
          return;
        }

        // socket connection succeeded
        final NetSocket netSocket = clientConnect.result();
        // the connection
        final RedisConnectionImpl connection = new RedisConnectionImpl(vertx, connectionListener, netSocket, options);

        // parser utility
        netSocket
          .handler(new RESPParser(connection, options.getMaxNestedArrays()))
          .closeHandler(connection::end)
          .exceptionHandler(connection::fatal);

        // perform authentication
        authenticate(connection, redisURI.password(), authenticate -> {
          if (authenticate.failed()) {
            onConnect.handle(Future.failedFuture(authenticate.cause()));
            return;
          }

          // perform select
          select(connection, redisURI.select(), select -> {
            if (select.failed()) {
              onConnect.handle(Future.failedFuture(select.cause()));
              return;
            }

            // perform setup
            setup(connection, setup, setup -> {
              if (setup.failed()) {
                onConnect.handle(Future.failedFuture(setup.cause()));
                return;
              }

              // initialization complete
              connection.handler(null);
              connection.endHandler(null);
              connection.exceptionHandler(DEFAULT_EXCEPTION_HANDLER);

              onConnect.handle(Future.succeededFuture(new ConnectResult<>(connection, 1, options.getMaxPoolSize())));
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

  public void getConnection(String address, Request setup, Handler<AsyncResult<RedisConnection>> handler) {
    final ConnectionProvider<RedisConnection> connectionProvider = new RedisConnectionProvider(address, setup);
    while (true) {
      Pool<RedisConnection> endpoint = endpointMap.computeIfAbsent(address, targetAddress ->
        new Pool<>(
          ctx,
          connectionProvider,
          CLOCK,
          options.getMaxPoolWaiting(),
          options.getMaxPoolSize(),
          options.getMaxPoolSize() * 4,
          v -> endpointMap.remove(address),
          conn -> connectionMap.put(address, conn),
          conn -> connectionMap.remove(address, conn),
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
