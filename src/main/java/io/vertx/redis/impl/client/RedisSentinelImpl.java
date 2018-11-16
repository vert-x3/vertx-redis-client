package io.vertx.redis.impl.client;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class RedisSentinelImpl extends AbstractRedisClient implements RedisSentinel {

  private static class Pair<L, R> {
    final L left;
    final R right;

    Pair(L left, R right) {
      this.left = left;
      this.right = right;
    }
  }

  // We don't need to be secure, we just want so simple
  // randomization to avoid picking the same slave all the time
  private static final Random RANDOM = new Random();

  private static final Logger LOG = LoggerFactory.getLogger(RedisSentinelImpl.class);
  // ensure that just 1 connection is created to listen to sentinel push messages
  private final AtomicBoolean pubsub = new AtomicBoolean();
  // mutable state
  private Handler<Void> masterSwitchHandler;
  private RedisConnection sentinelPubSub;

  public RedisSentinelImpl(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    super(vertx, endpoints, options);
  }

  @Override
  public RedisSentinel open(String masterName, RedisRole role, Handler<AsyncResult<RedisConnection>> callback) {
    // When the client is created wrap another client and subscribe to the
    // switch-master event. Then any time there is a message on the channel it
    // must be a master change, so reconnect all clients. This avoids combining
    // the pub/sub client with the normal client and interfering with whatever
    // the user is trying to do.
    if (!pubsub.getAndSet(true)) {
      createClientInternal(masterName, RedisRole.SENTINEL, create -> {
        if (create.failed()) {
          LOG.error("Redis PUB/SUB wrap failed.", create.cause());
          pubsub.set(false);
          return;
        }

        sentinelPubSub = create.result();

        sentinelPubSub.send("SUBSCRIBE", Args.args("+switch-master"), send -> {
          if (send.failed()) {
            LOG.error("Unable to subscribe to Sentinel PUBSUB", send.cause());
            sentinelPubSub.close();
            pubsub.set(false);
          }
        });

        sentinelPubSub.endHandler(v -> pubsub.set(false));

        sentinelPubSub.exceptionHandler(t -> {
          LOG.error("Unhandled exception in Sentinel PUBSUB", t);
          sentinelPubSub.close();
          pubsub.set(false);
        });

        sentinelPubSub.handler(msg -> {
          if (msg.isMulti()) {
            if ("MESSAGE".equalsIgnoreCase(msg.get(0).asString())) {
              // we don't care about the payload
              if (masterSwitchHandler != null) {
                masterSwitchHandler.handle(null);
              } else {
                LOG.warn("Received +switch-master message from Redis Sentinel. Reconnecting clients.");
              }
            }
          }
        });
      });
    }

    createClientInternal(masterName, role, callback);
    return this;
  }

  @Override
  public RedisSentinel masterSwitchHandler(Handler<Void> handler) {
    this.masterSwitchHandler = handler;
    return this;
  }

  @Override
  public void close() {
    if (pubsub.compareAndSet(true, false)) {
      sentinelPubSub.close();
      pubsub.set(false);
    }
  }

  private void createClientInternal(String masterName, RedisRole role, Handler<AsyncResult<RedisConnection>> onCreate) {

    final Handler<AsyncResult<SocketAddress>> createAndConnect = resolve -> {
      if (resolve.failed()) {
        onCreate.handle(Future.failedFuture(resolve.cause()));
        return;
      }
      // wrap a new client
      final NetClient client = createNetClient();
      client.connect(resolve.result(), connect -> {
        if (connect.failed()) {
          client.close();
          onCreate.handle(Future.failedFuture(connect.cause()));
          return;
        }
        // TCP connected
        onCreate.handle(Future.succeededFuture(
          new RedisConnectionImpl(context, client, connect.result(), resolve.result())));
      });
    };

    switch (role) {
      case SENTINEL:
        resolveClient(this::isSentinelOk, masterName, createAndConnect);
        break;

      case MASTER:
        resolveClient(this::getMasterFromEndpoint, masterName, createAndConnect);
        break;

      case SLAVE:
        resolveClient(this::getSlaveFromEndpoint, masterName, createAndConnect);
    }
  }

  /**
   * We use the algorithm from http://redis.io/topics/sentinel-clients
   * to get a sentinel client and then do 'stuff' with it
   */
  private void resolveClient(Resolver checkEndpointFn, String masterName, Handler<AsyncResult<SocketAddress>> callback) {
    // Because finding the master is going to be an async list we will terminate
    // when we find one then use promises...
    iterate(0, checkEndpointFn, masterName, iterate -> {
      if (iterate.failed()) {
        callback.handle(Future.failedFuture(iterate.cause()));
      } else {
        final Pair<Integer, SocketAddress> found = iterate.result();
        // This is the endpoint that has responded so stick it on the top of
        // the list
        SocketAddress endpoint = endpoints.get(found.left);
        endpoints.set(found.left, endpoints.get(0));
        endpoints.set(0, endpoint);
        // now return the right address
        callback.handle(Future.succeededFuture(found.right));
      }
    });
  }


  private void isSentinelOk(SocketAddress endpoint, String argument, Handler<AsyncResult<SocketAddress>> handler) {
    final NetClient client = createNetClient();
    client.connect(endpoint, connect -> {
      if (connect.failed()) {
        client.close();
        handler.handle(Future.failedFuture(connect.cause()));
        return;
      }
      // TCP connected
      final RedisConnectionImpl conn = new RedisConnectionImpl(context, client, connect.result(), endpoint);

      // Send a command just to check we have a working node
      conn.send("INFO", info -> {
        if (info.failed()) {
          handler.handle(Future.failedFuture(info.cause()));
          return;
        }

        handler.handle(Future.succeededFuture(endpoint));
        conn.close();
      });
    });
  }

  private void getMasterFromEndpoint(SocketAddress endpoint, String masterName, Handler<AsyncResult<SocketAddress>> handler) {
    final NetClient client = createNetClient();
    client.connect(endpoint, connect -> {
      if (connect.failed()) {
        client.close();
        handler.handle(Future.failedFuture(connect.cause()));
        return;
      }
      // TCP connected
      final RedisConnectionImpl conn = new RedisConnectionImpl(context, client, connect.result(), endpoint);

      // Send a command just to check we have a working node
      conn.send("SENTINEL", Args.args("get-master-addr-by-name", masterName), sentinel -> {
        if (sentinel.failed()) {
          handler.handle(Future.failedFuture(sentinel.cause()));
          return;
        }

        // Test the response
        if (!sentinel.result().isMulti()) {
          handler.handle(Future.failedFuture("Unknown master name: " + masterName));
        } else {
          Reply result = sentinel.result();
          handler.handle(Future.succeededFuture(SocketAddress.inetSocketAddress(result.get(1).asInteger(), result.get(0).asString())));
        }
        conn.close();
      });
    });
  }

  private void getSlaveFromEndpoint(SocketAddress endpoint, String masterName, Handler<AsyncResult<SocketAddress>> handler) {
    final NetClient client = createNetClient();
    client.connect(endpoint, connect -> {
      if (connect.failed()) {
        client.close();
        handler.handle(Future.failedFuture(connect.cause()));
        return;
      }
      // TCP connected
      final RedisConnectionImpl conn = new RedisConnectionImpl(context, client, connect.result(), endpoint);

      // Send a command just to check we have a working node
      conn.send("SENTINEL", Args.args("slaves", masterName), sentinel -> {
        if (sentinel.failed()) {
          handler.handle(Future.failedFuture(sentinel.cause()));
          return;
        }

        // Test the response
        if (!sentinel.result().isMulti()) {
          handler.handle(Future.failedFuture("Unknown master name: " + masterName));
        } else if (sentinel.result().size() == 0) {
          handler.handle(Future.failedFuture("No slaves linked to the master: " + masterName));
        } else {
          Reply result = sentinel.result();

          Reply slaveInfoArr = result.get(RANDOM.nextInt(result.size()));
          if ((slaveInfoArr.size() % 2) > 0) {
            handler.handle(Future.failedFuture("Corrupted response from the sentinel"));
          } else {
            JsonObject json = slaveInfoArr.asJsonObject();
            // Redis JSONs are usually Strings, so we need to perform some parsing
            handler.handle(Future.succeededFuture(
              SocketAddress.inetSocketAddress(Integer.parseInt(json.getString("port")), json.getString("ip"))));
          }
        }
        conn.close();
      });
    });
  }

  private void iterate(final int idx, final Resolver checkEndpointFn, final String argument, final Handler<AsyncResult<Pair<Integer, SocketAddress>>> resultHandler) {
    // stop condition
    if (idx >= endpoints.size()) {
      resultHandler.handle(Future.failedFuture("No more endpoints in chain."));
      return;
    }

    // attempt to perform operation
    checkEndpointFn.resolve(endpoints.get(idx), argument, res -> {
      if (res.succeeded()) {
        resultHandler.handle(Future.succeededFuture(new Pair<>(idx, res.result())));
      } else {
        // try again with next endpoint
        iterate(idx + 1, checkEndpointFn, argument, resultHandler);
      }
    });
  }
}
