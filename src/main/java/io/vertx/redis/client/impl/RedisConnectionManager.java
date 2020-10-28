package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.impl.clientconnection.*;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

import java.util.Objects;

class RedisConnectionManager {

  private static final Logger LOG = LoggerFactory.getLogger(RedisConnectionManager.class);

  private static final Handler<Throwable> DEFAULT_EXCEPTION_HANDLER = t -> LOG.error("Unhandled Error", t);

  private final Vertx vertx;
  private final NetClient netClient;

  private final RedisOptions options;
  private final ConnectionManager<ConnectionKey, RedisConnection> pooledConnectionManager;
  private long timerID;

  RedisConnectionManager(Vertx vertx, RedisOptions options) {
    this.vertx = vertx;
    this.options = options;
    this.netClient = vertx.createNetClient(options.getNetClientOptions());
    this.pooledConnectionManager = new ConnectionManager<>(this::connectionEndpointProvider);
  }

  private Endpoint<RedisConnection> connectionEndpointProvider(ConnectionKey key, ContextInternal ctx, Runnable dispose) {
    return new RedisEndpoint(dispose, ctx, key);
  }

  synchronized void start() {
    long period = options.getPoolCleanerInterval();
    this.timerID = period > 0 ? vertx.setTimer(period, id -> checkExpired(period)) : -1;
  }

  private void checkExpired(long period) {
    pooledConnectionManager.forEach(e -> ((RedisEndpoint) e).pool.closeIdle());
    timerID = vertx.setTimer(period, id -> checkExpired(period));
  }

  private static class ConnectionKey {
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
      return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
      return Objects.hash(string);
    }
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
      return ((RedisStandaloneConnection) conn).isValid();
    }

    @Override
    public void init(RedisConnection conn) {
      // NO OP!
    }

    @Override
    public void connect(ConnectionListener<RedisConnection> connectionListener, ContextInternal ctx, Handler<AsyncResult<ConnectResult<RedisConnection>>> onConnect) {

      // verify if we can make this connection
      final boolean netClientSsl = options.getNetClientOptions().isSsl();
      final boolean connectionStringSsl = redisURI.ssl();
      final boolean connectionStringInetSocket = redisURI.socketAddress().isInetSocket();

      // when dealing with sockets, ssl is only covered in case of inet sockets
      // not domain sockets
      if (connectionStringInetSocket) {
        // net client is ssl and connection string is not ssl is not allowed
        if (netClientSsl && !connectionStringSsl) {
          onConnect.handle(Future.failedFuture("Pool initialized with SSL but connection requested plain socket"));
          return;
        }
      }

      // all calls the user handler will happen in the user context (ctx)
      netClient.connect(redisURI.socketAddress(), clientConnect -> {
        if (clientConnect.failed()) {
          // connection failed
          ctx.runOnContext(v -> onConnect.handle(Future.failedFuture(clientConnect.cause())));
          return;
        }

        // socket connection succeeded
        final NetSocket netSocket = clientConnect.result();

        final Handler<Void> completeConnection = v -> {
          // the connection will inherit the user event loop context
          final RedisStandaloneConnection connection = new RedisStandaloneConnection(vertx, ctx, connectionListener, netSocket, options);

          // parser utility
          netSocket
            .handler(new RESPParser(connection, options.getMaxNestedArrays()))
            .closeHandler(connection::end)
            .exceptionHandler(connection::fatal);

          // initial handshake
          hello(connection, redisURI, hello -> {
            if (hello.failed()) {
              ctx.runOnContext(v1 -> onConnect.handle(Future.failedFuture(hello.cause())));
              return;
            }

            // perform select
            select(connection, redisURI.select(), select -> {
              if (select.failed()) {
                ctx.runOnContext(v2 -> onConnect.handle(Future.failedFuture(select.cause())));
                return;
              }

              // perform setup
              setup(connection, setup, setupResult -> {
                if (setupResult.failed()) {
                  ctx.runOnContext(v2 -> onConnect.handle(Future.failedFuture(setupResult.cause())));
                  return;
                }

                // initialization complete
                connection.handler(null);
                connection.endHandler(null);
                connection.exceptionHandler(DEFAULT_EXCEPTION_HANDLER);

                ctx.runOnContext(v2 -> onConnect.handle(Future.succeededFuture(new ConnectResult<>(connection, 1, 1))));
              });
            });
          });
        };

        // upgrade to ssl is only possible for inet sockets
        if (connectionStringInetSocket && !netClientSsl && connectionStringSsl) {
          // must upgrade protocol
          netSocket.upgradeToSsl(upgradeToSsl -> {
            if (upgradeToSsl.failed()) {
              onConnect.handle(Future.failedFuture(upgradeToSsl.cause()));
            } else {
              // complete the connection
              completeConnection.handle(null);
            }
          });
        } else {
          // no need to upgrade
          completeConnection.handle(null);
        }
      });
    }

    private void hello(RedisConnection connection, RedisURI redisURI, Handler<AsyncResult<Void>> handler) {
      Request hello = Request.cmd(Command.HELLO).arg(RESPParser.VERSION);

      String password = redisURI.password() != null ? redisURI.password() : options.getPassword();

      if (password != null) {
        String user = redisURI.user();
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
          handler.handle(Future.succeededFuture());
          return;
        }

        final Throwable err = onSend.cause();

        if (err != null && err.getMessage().startsWith("ERR unknown command")) {
          // chatting to an old server
          authenticate(connection, password, handler);
        } else {
          handler.handle(Future.failedFuture(err));
        }
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
      ((RedisStandaloneConnection) connection).forceClose();
    }
  }

  public void getConnection(Context userContext, String connectionString, Request setup, Handler<AsyncResult<RedisConnection>> handler) {
    pooledConnectionManager.getConnection((ContextInternal) userContext, new ConnectionKey(connectionString, setup), handler);
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
  }

  private class RedisEndpoint extends Endpoint<RedisConnection> {

    final Pool<RedisConnection> pool;

    public RedisEndpoint(Runnable dispose, ContextInternal ctx, ConnectionKey key) {
      super(dispose);
      ConnectionProvider<RedisConnection> connectionProvider = new RedisConnectionProvider(key.string, key.setup);
      pool = new Pool<>(
        ctx,
        connectionProvider,
        options.getMaxPoolSize(),
        1,
        options.getMaxPoolWaiting(),
        this::connectionAdded,
        this::connectionRemoved,
        false);
    }

    @Override
    public void requestConnection(ContextInternal ctx, Handler<AsyncResult<RedisConnection>> handler) {
      pool.getConnection(handler);
    }

    @Override
    protected void close(RedisConnection conn) {
      ((RedisStandaloneConnection) conn).forceClose();
    }
  }
}
