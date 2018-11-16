package io.vertx.redis.impl.client;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.*;
import io.vertx.redis.RedisConnection;
import io.vertx.redis.Reply;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class RedisClusterImpl extends AbstractRedisClient implements RedisCluster {

  class RedisClusterConnectionImpl implements RedisConnection {

    // number of attempts/redirects when we get connection errors
    // or when we get MOVED/ASK responses
    private static final int RETRIES = 16;

    private final RedisClusterImpl self;
    private final RedisSlaves slaves;

    RedisClusterConnectionImpl(RedisSlaves slaves) {
      this.slaves = slaves;
      self = RedisClusterImpl.this;
    }

    @Override
    public void close() {
      self.close();
    }

    @Override
    public RedisConnection exceptionHandler(Handler<Throwable> handler) {
      self.onException = handler;
      return this;
    }

    @Override
    public RedisConnection endHandler(Handler<Void> handler) {
      self.onEnd = handler;
      return this;
    }

    @Override
    public RedisConnection handler(Handler<Reply> handler) {
      self.onMessage = handler;
      return this;
    }

    @Override
    public RedisConnection pause() {
      self.connections.values().forEach(conn -> {
        if (conn != null) {
          try {
            conn.pause();
          } catch (RuntimeException e) {
            if (self.onException != null) {
              self.onException.handle(e);
            }
          }
        }
      });
      return this;
    }

    @Override
    public RedisConnection resume() {
      self.connections.values().forEach(conn -> {
        if (conn != null) {
          try {
            conn.resume();
          } catch (RuntimeException e) {
            if (self.onException != null) {
              self.onException.handle(e);
            }
          }
        }
      });
      return null;
    }

    @Override
    public RedisConnection send(String command, Args args, boolean readonly, Handler<AsyncResult<Reply>> handler) {
      send(selectClient(args.getKeySlot(), readonly), RETRIES, command, args, handler);
      return this;
    }

    @Override
    public SocketAddress address() {
      throw new UnsupportedOperationException("Cluster Connection is not bound to a socket");
    }

    @Override
    public ReadStream<Reply> fetch(long amount) {
      return this;
    }

    private void send(final RedisConnection client, final int retries, String command, Args args, Handler<AsyncResult<Reply>> handler) {

      if (client == null) {
        handler.handle(Future.failedFuture("No connection available."));
        return;
      }

      client.send(command, args, send -> {
        if (send.failed() && send.cause() instanceof RedisException && retries >= 0) {
          final RedisException cause = (RedisException) send.cause();

          boolean ask = cause.is("ASK");
          boolean moved = !ask && cause.is("MOVED");

          if (moved || ask) {
            final Runnable andThen = () -> {
              // REQUERY THE NEW ONE (we've got the correct details)
              String addr = cause.slice(' ', 2);

              if (addr == null) {
                // bad message
                handler.handle(Future.failedFuture(cause));
                return;
              }

              int sep = addr.lastIndexOf(':');
              SocketAddress socketAddress;

              if (sep != -1) {
                // InetAddress
                socketAddress = SocketAddress.inetSocketAddress(
                  Integer.parseInt(addr.substring(sep + 1)),
                  addr.substring(0, sep)
                );
              } else {
                // assume unix domain
                socketAddress = SocketAddress.domainSocketAddress(addr);
              }

              getClient(socketAddress, getClient -> {
                if (getClient.failed()) {
                  handler.handle(Future.failedFuture(getClient.cause()));
                  return;
                }

                // re-run on the new client
                send(getClient.result(), retries - 1, command, args, handler);
              });
            };

            // key has been moved!
            // lets re fetch slots from redis to get an up to date allocation
            if (moved) {
              getSlots(getSlots -> andThen.run());
            } else {
              andThen.run();
            }
            return;
          }

          if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
            // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
            long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
            vertx.setTimer(backoff, t -> send(client, retries - 1, command, args, handler));
            return;
          }
        }

        handler.handle(send);
      });
    }

    /**
     * Select a Redis client for the given key
     */
    private RedisConnection selectClient(int keySlot, boolean readOnly) {
      // this command doesn't have keys, return any connection
      // NOTE: this means slaves may be used for no key commands regardless of slave config
      if (keySlot == -1) {
        return getRandomConnection(Collections.emptySet());
      }

      RedisConnection[] clients = slots[keySlot];

      // if we haven't got config for this slot, try any connection
      if (clients == null || clients.length == 0) {
        return getRandomConnection(Collections.emptySet());
      }

      int index = 0;

      // always, never, share
      if (readOnly && slaves != RedisSlaves.NEVER && clients.length > 1) {
        // always use a slave for read commands
        if (slaves == RedisSlaves.ALWAYS) {
          index = random.nextInt(clients.length - 1) + 1;
        }
        // share read commands across master + slaves
        if (slaves == RedisSlaves.SHARE) {
          index = random.nextInt(clients.length);
        }
      }

      return clients[index];
    }
  }

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private final Random random = new Random();

  // mutable state
  private final Map<SocketAddress, RedisConnection> connections = new HashMap<>();
  private final RedisConnection[][] slots = new RedisConnection[16384][];

  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Reply> onMessage;

  public RedisClusterImpl(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options) {
    super(vertx, endpoints, options);
  }

  @Override
  public RedisCluster open(RedisSlaves slaves, Handler<AsyncResult<RedisConnection>> handler) {
    // for each endpoint open a client
    final AtomicInteger counter = new AtomicInteger(endpoints.size());
    final AtomicBoolean failed = new AtomicBoolean();

    for (SocketAddress endpoint : endpoints) {
      getClient(endpoint, ar -> {
        if (ar.failed()) {
          if (failed.compareAndSet(false, true)) {
            handler.handle(Future.failedFuture(ar.cause()));
          }
          return;
        }
        if (counter.decrementAndGet() == 0) {
          // fetch slots from the cluster immediately to ensure slots are correct
          getSlots(getSlots -> {
            if (getSlots.failed()) {
              handler.handle(Future.failedFuture(getSlots.cause()));
            } else {
              handler.handle(Future.succeededFuture(new RedisClusterConnectionImpl(slaves)));
            }
          });
        }
      });
    }

    return this;
  }

  @Override
  public void close() {
    connections.entrySet().removeIf(kv -> {
      if (kv.getValue() != null) {
        kv.getValue().close();
      }
      return true;
    });
  }

  /**
   * Get a Redis client via the connection cache (one per host)
   */
  private void getClient(SocketAddress address, Handler<AsyncResult<RedisConnection>> onClient) {

    RedisConnection cli = connections.get(address);

    // already have a connection to this client, return that
    if (cli != null) {
      onClient.handle(Future.succeededFuture(cli));
      return;
    }

    final NetClient client = createNetClient();
    client.connect(address, connect -> {
      if (connect.failed()) {
        client.close();
        onClient.handle(Future.failedFuture(connect.cause()));
        return;
      }
      // ready
      final RedisConnection conn = new RedisConnectionImpl(context, client, connect.result(), address);

      conn.exceptionHandler(t -> {
        // broken connection so force a new client to be created
        connections.remove(address);
        // propagate the exception
        if (onException != null) {
          onException.handle(t);
        }

        // now since the clients are unbalanced, we need to reload the slots
        getSlots(ar -> {
          if (ar.failed()) {
            // getting slots failed, so raise the exception
            if (onException != null) {
              onException.handle(ar.cause());
            }
          }
        });
      });

      conn.endHandler(v -> {
        // closed connections should be removed
        connections.remove(address);
        // how many connections are still open?
        // when there's more than 0 then we can still operate
        if (connections.size() == 0) {
          // all connections are closed so we must assume this
          // cluster is ended (or we can't recover)
          if (onEnd != null) {
            onEnd.handle(null);
          }
        }
      });

      conn.handler(r -> {
        if (onMessage != null) {
          onMessage.handle(r);
        }
      });

      connections.put(address, conn);
      onClient.handle(Future.succeededFuture(conn));
    });
  }

  private void clearSlots() {
    for (int i = 0; i < slots.length; i++) {
      slots[i] = null;
    }
  }

  private void getSlots(Handler<AsyncResult<Void>> handler) {

    final Set<SocketAddress> exclude = new HashSet<>();
    final AtomicReference<Throwable> cause = new AtomicReference<>();

    final Handler<Throwable> cleanUp = err -> {

    };

    final Runnable tryClient = new Runnable() {
      @Override
      public void run() {
        final RedisConnection conn = getRandomConnection(exclude);
        if (conn == null) {
          // no more clients available
          handler.handle(Future.failedFuture("No client's available."));
          return;
        }

        conn.send("CLUSTER", Args.args("slots"), send -> {
          if (send.failed()) {
            // exclude this client from then next attempt
            exclude.add(conn.address());
            cause.set(send.cause());
            // try again
            run();
            return;
          }


          final Reply reply = send.result();

          Set<SocketAddress> seenClients = new HashSet<>();
          clearSlots();

          for (int i = 0; i < reply.size(); i++) {
            // multibulk
            Reply s = reply.get(i);
            // single bulk
            int start = s.get(0).asInteger();
            int end = s.get(1).asInteger();

            final RedisConnection[] connections = new RedisConnection[s.size() - 2];

            // expensive precomputed table of slots
            for (int j = start; j <= end; j++) {
              slots[j] = connections;
            }

            // array of all clients, clients[2] = master, others are slaves
            for (int index = 2; index < s.size(); index++) {
              final int idx = index - 2;
              Reply c = s.get(index);
              SocketAddress address = SocketAddress.inetSocketAddress(c.get(1).asInteger(), c.get(0).asString());
              seenClients.add(address);
              getClient(address, getClient -> {
                if (getClient.succeeded()) {
                  connections[idx] = getClient.result();
                }
              });
            }
          }

          // quit now-unused clients
          connections.entrySet().removeIf(kv -> {
            if (kv.getValue() == null) {
              return true;
            }

            if (!seenClients.contains(kv.getKey())) {
              kv.getValue().close();
              kv.setValue(null);
              return true;
            }

            return false;
          });

          handler.handle(Future.succeededFuture());
        });
      }
    };

    // start looking
    tryClient.run();
  }

  /**
   * Get a random Redis connection
   */
  private RedisConnection getRandomConnection(Set<SocketAddress> exclude) {
    List<RedisConnection> available = connections.entrySet().stream()
      .filter(kv -> !exclude.contains(kv.getKey()) && kv.getValue() != null)
      .map(Map.Entry::getValue)
      .collect(Collectors.toList());

    if (available.size() == 0) {
      // signal no client
      return null;
    }

    return available.get(random.nextInt(available.size()));
  }
}

