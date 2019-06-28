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
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

class ConnectionManager {

  private static final LongSupplier CLOCK = System::currentTimeMillis;

  private final Vertx vertx;
  private final ContextInternal ctx;
  private final NetClient netClient;

  private final int maxWaitQueueSize;
  private final RedisOptions options;
  private final Map<String, RedisConnection> connectionMap = new ConcurrentHashMap<>();
  private final Map<String, Pool<RedisConnection>> endpointMap = new ConcurrentHashMap<>();
  private final long maxSize;
  private long timerID;

  ConnectionManager(Vertx vertx,
                    RedisOptions options,
                    long maxSize,
                    int maxWaitQueueSize) {
    this.vertx = vertx;
    this.options = options;
    this.maxWaitQueueSize = maxWaitQueueSize;
    this.maxSize = maxSize;

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

    public RedisConnectionProvider(String address) {
      this.redisURI = new RedisURI(address);
    }

    @Override
    public void connect(ConnectionListener<RedisConnection> connectionListener, ContextInternal contextInternal, Handler<AsyncResult<ConnectResult<RedisConnection>>> onConnect) {
      netClient.connect(redisURI.socketAddress(), clientConnect -> {
        if (clientConnect.failed()) {
          // connection failed
          netClient.close();
          onConnect.handle(Future.failedFuture(clientConnect.cause()));
          return;
        }

        // socket connection succeeded
        final NetSocket netSocket = clientConnect.result();
        // the connection
        final RedisConnectionImpl connection = new RedisConnectionImpl(redisURI.address(), netSocket, options.getMaxWaitingHandlers());

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

            // initialization complete
            onConnect.handle(Future.succeededFuture(new ConnectResult<>(connection, 1, options.getMaxPoolSize())));
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

    @Override
    public void close(RedisConnection connection) {
      connection.close();
    }
  }

  void getConnection(String address, Handler<AsyncResult<RedisConnection>> handler) {
    while (true) {
      Pool<RedisConnection> endpoint = endpointMap.computeIfAbsent(address, targetAddress -> {
        RedisConnectionProvider connector = new RedisConnectionProvider(address);
        return new Pool<>(
          ctx,
          connector,
          CLOCK,
          maxWaitQueueSize,
          options.getMaxPoolSize(),
          maxSize,
          v -> endpointMap.remove(address),
          conn -> connectionMap.put(address, conn),
          conn -> connectionMap.remove(address, conn),
          false);
      });
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
      conn.close();
    }
  }

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
    vertx.runOnContext(v -> {
      final ConnectionManager manager = new ConnectionManager(vertx, new RedisOptions(), 1, 10);
      manager.start();

      manager.getConnection("redis://localhost:7006", ar -> {
        if (ar.failed()) {
          ar.cause().printStackTrace();
        } else {
          ar.result().send(Request.cmd(Command.PING), send -> {
            if (send.failed()) {
              send.cause().printStackTrace();
            } else {
              System.out.println(send.result());
            }
          });
        }
      });
    });
  }
}
