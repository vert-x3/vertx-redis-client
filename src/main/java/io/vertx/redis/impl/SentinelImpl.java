package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.*;
import io.vertx.redis.Reply;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SentinelImpl implements Sentinel {

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

  private static final Logger LOG = LoggerFactory.getLogger(SentinelImpl.class);

  private final Vertx vertx;
  private final List<SocketAddress> endpoints;
  private final NetClientOptions options;

  private final List<Redis> clients = new LinkedList<>();
  private final AtomicBoolean pubsub = new AtomicBoolean();

  public SentinelImpl(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    this.vertx = vertx;
    this.endpoints = endpoints;
    this.options = options;

  }

  @Override
  public Sentinel open(String masterName, Role role, Handler<AsyncResult<Redis>> callback) {
    // When the client is ready wrap another client and subscribe to the
    // switch-master event. Then any time there is a message on the channel it
    // must be a master change, so reconnect all clients. This avoids combining
    // the pub/sub client with the normal client and interfering with whatever
    // the user is trying to do.
    if (!pubsub.getAndSet(true)) {
      createClientInternal(masterName, Role.SENTINEL, create -> {
        if (create.failed()) {
          LOG.error("Redis PUB/SUB wrap failed.", create.cause());
          return;
        }

        final Redis pubsubClient = create.result();

        pubsubClient.send("SUBSCRIBE", Args.args("+switch-master"), send -> {
          if (send.failed()) {
            LOG.error("Unable to subscribe to Sentinel PUBSUB", send.cause());
          }
        });

        pubsubClient.endHandler(v -> {
          pubsub.set(false);
        });

        pubsubClient.exceptionHandler(t -> {
          LOG.error("Unhandled exception in Sentinel PUBSUB", t);
          pubsubClient.close();
        });

        pubsubClient.handler(msg -> {
          if (msg.isMulti()) {
            if ("MESSAGE".equalsIgnoreCase(msg.get(0).asString())) {
              // we don't care about the payload
              LOG.warn("Received +switch-master message from Redis Sentinel. Reconnecting clients.");
              disconnectAllClients();
            }
          }
        });

      });
    }

    createClientInternal(masterName, role, callback);
    return this;
  }

  @Override
  public void close() {
    for (Redis client : clients) {
      client.close();
      // forget about this client
      clients.remove(client);
    }
  }

  /*
   * Ensure that all clients are disconnected as we switched master.
   */
  private void disconnectAllClients() {
    for (Redis client : clients) {
      if (client instanceof RedisImpl) {
        ((RedisImpl) client)
          .cleanupQueue("sentinel switch-master")
          .close();
      }
      // forget about this client
      clients.remove(client);
    }
  }

  private void createClientInternal(String masterName, Role role, Handler<AsyncResult<Redis>> callback) {

    final Handler<AsyncResult<SocketAddress>> createAndConnect = resolve -> {
      if (resolve.failed()) {
        callback.handle(Future.failedFuture(resolve.cause()));
        return;
      }
      // wrap a new client
      final Redis client = Redis.create(vertx, resolve.result(), options);
      // keep track of the clients
      clients.add(client);
      client
        // if we're purposefully ending, forget us
        .endHandler(v -> clients.remove(client))
        // open the connection
        .open(v -> {
          if (v.failed()) {
            // forget about this client
            clients.remove(client);
            callback.handle(Future.failedFuture(v.cause()));
          } else {
            callback.handle(Future.succeededFuture(client));
          }
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


  private void isSentinelOk(SocketAddress endpoint, String argument, Handler<AsyncResult<SocketAddress>> callback) {
    final Redis client = Redis.create(vertx, endpoint, options);

    client.exceptionHandler(t -> {
      callback.handle(Future.failedFuture(t));
    });

    client.open(open -> {
      if (open.failed()) {
        callback.handle(Future.failedFuture(open.cause()));
        return;
      }

      // Send a command just to check we can...
      client.send("INFO", info -> {
        if (info.failed()) {
          callback.handle(Future.failedFuture(info.cause()));
          return;
        }

        callback.handle(Future.succeededFuture(endpoint));
        client.close();
      });
    });
  }

  private void getMasterFromEndpoint(SocketAddress endpoint, String masterName, Handler<AsyncResult<SocketAddress>> callback) {
    final Redis client = Redis.create(vertx, endpoint, options);

    client.exceptionHandler(t -> {
      callback.handle(Future.failedFuture(t));
    });

    client.open(open -> {
      if (open.failed()) {
        callback.handle(Future.failedFuture(open.cause()));
        return;
      }

      // Send a command just to check we can...
      client.send("SENTINEL", Args.args("get-master-addr-by-name", masterName), sentinel -> {
        if (sentinel.failed()) {
          callback.handle(Future.failedFuture(sentinel.cause()));
          return;
        }

        // Test the response
        if (!sentinel.result().isMulti()) {
          callback.handle(Future.failedFuture("Unknown master name: " + masterName));
        } else {
          Reply result = sentinel.result();
          callback.handle(Future.succeededFuture(SocketAddress.inetSocketAddress(result.get(1).asInteger(), result.get(0).asString())));
        }
        client.close();
      });
    });
  }

  private void getSlaveFromEndpoint(SocketAddress endpoint, String masterName, Handler<AsyncResult<SocketAddress>> callback) {
    final Redis client = Redis.create(vertx, endpoint, options);

    client.exceptionHandler(t -> {
      callback.handle(Future.failedFuture(t));
    });

    client.open(open -> {
      if (open.failed()) {
        callback.handle(Future.failedFuture(open.cause()));
        return;
      }

      // Send a command just to check we can...
      client.send("SENTINEL", Args.args("slaves", masterName), sentinel -> {
        if (sentinel.failed()) {
          callback.handle(Future.failedFuture(sentinel.cause()));
          return;
        }

        // Test the response
        if (!sentinel.result().isMulti()) {
          callback.handle(Future.failedFuture("Unknown master name: " + masterName));
        } else if (sentinel.result().size() == 0) {
          callback.handle(Future.failedFuture("No slaves linked to the master: " + masterName));
        } else {
          Reply result = sentinel.result();

          Reply slaveInfoArr = result.get(RANDOM.nextInt(result.size()));
          if ((slaveInfoArr.size() % 2) > 0) {
            callback.handle(Future.failedFuture("Corrupted response from the sentinel"));
          } else {
            JsonObject json = slaveInfoArr.asJsonObject();
            // Redis JSONs are usually Strings, so we need to perform some parsing
            callback.handle(Future.succeededFuture(
              SocketAddress.inetSocketAddress(Integer.parseInt(json.getString("port")), json.getString("ip"))));
          }
        }
        client.close();
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
