package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.*;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.vertx.redis.client.Command.CLUSTER_SLOTS;
import static io.vertx.redis.client.Request.cmd;

public class RedisClusterClient implements Redis {

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  private static final int RETRIES = 16;

  private static final Logger LOG = LoggerFactory.getLogger(RedisClusterClient.class);

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private static final Random RANDOM = new Random();

  public static void create(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    new RedisClusterClient(vertx, options, onCreate);
  }

  private final Vertx vertx;
  private final RedisSlaves slaves;
  private final RedisOptions options;

  // mutable state
  private final Map<SocketAddress, Redis> connections = new HashMap<>();
  private final Redis[][] slots = new Redis[16384][];

  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<Response> onMessage;

  private RedisClusterClient(Vertx vertx, RedisOptions options, Handler<AsyncResult<Redis>> onCreate) {
    this.vertx = vertx;
    this.slaves = options.getUseSlave();
    this.options = options;

    // for each endpoint open a client
    final List<SocketAddress> endpoints = options.getEndpoints();

    final AtomicInteger counter = new AtomicInteger(endpoints.size());
    // ensure that in case of error, only call the callback once
    final AtomicBoolean failed = new AtomicBoolean();

    for (SocketAddress endpoint : endpoints) {
      getClient(endpoint, options, ar -> {
        final int total = counter.decrementAndGet();

        if (ar.failed()) {
          // if the client can't connect we should warn but continue
          LOG.warn("Failed to connect to: " + endpoint);
        }

        if (total == 0) {
          // fetch slots from the cluster immediately to ensure slots are correct
          getSlots(options, getSlots -> {
            if (getSlots.failed()) {
              onCreate.handle(Future.failedFuture(getSlots.cause()));
            } else {
              onCreate.handle(Future.succeededFuture(this));
            }
          });
        }
      });
    }
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

  @Override
  public Redis exceptionHandler(Handler<Throwable> handler) {
    this.onException = handler;
    return this;
  }

  @Override
  public Redis endHandler(Handler<Void> handler) {
    this.onEnd = handler;
    return this;
  }

  @Override
  public Redis handler(Handler<Response> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public Redis pause() {
    this.connections.values().forEach(conn -> {
      if (conn != null) {
        conn.pause();
      }
    });
    return this;
  }

  @Override
  public Redis resume() {
    this.connections.values().forEach(conn -> {
      if (conn != null) {
        conn.resume();
      }
    });
    return null;
  }

  @Override
  public Redis send(Request command, Handler<AsyncResult<Response>> handler) {
    send(selectClient(command.getKeySlot(), command.isReadOnly()), options, RETRIES, command, handler);
    return this;
  }

  @Override
  public Redis batch(List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {
    // look up the base slot for the batch
    int base = commands.get(0).getKeySlot();
    for (int i = 1; i < commands.size(); i++) {
      if (commands.get(i).getKeySlot() != base) {
        base = -1;
        break;
      }
    }

    batch(selectClient(base, false), options, RETRIES, commands, handler);
    return this;
  }


  @Override
  public SocketAddress socketAddress() {
    throw new UnsupportedOperationException("Cluster Connection is not bound to a socket");
  }

  @Override
  public Redis fetch(long amount) {
    this.connections.values().forEach(conn -> {
      if (conn != null) {
        conn.fetch(amount);
      }
    });

    return this;
  }

  /**
   * Get a Redis client via the connection cache (one per host)
   */
  private void getClient(SocketAddress address, RedisOptions options, Handler<AsyncResult<Redis>> onClient) {

    Redis cli = connections.get(address);

    // already have a connection to this client, return that
    if (cli != null) {
      onClient.handle(Future.succeededFuture(cli));
      return;
    }

    RedisClient.create(vertx, address, options, create -> {
      if (create.failed()) {
        onClient.handle(create);
        return;
      }

      final Redis conn = create.result();

      conn.exceptionHandler(t -> {
        // broken connection so force a new client to be created
        connections.remove(address);
        // propagate the exception
        if (onException != null) {
          onException.handle(t);
        }

        // now since the clients are unbalanced, we need to reload the slots
        getSlots(options, ar -> {
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

  private void getSlots(RedisOptions options, Handler<AsyncResult<Void>> handler) {

    final Set<SocketAddress> exclude = new HashSet<>();
    final AtomicReference<Throwable> cause = new AtomicReference<>();

    final Runnable tryClient = new Runnable() {
      @Override
      public void run() {
        final Redis conn = getRandomConnection(exclude);
        if (conn == null) {
          // no more clients available
          handler.handle(Future.failedFuture("No client's available."));
          return;
        }

        conn.send(cmd(CLUSTER_SLOTS), send -> {
          if (send.failed()) {
            // exclude this client from then next attempt
            exclude.add(conn.socketAddress());
            cause.set(send.cause());
            // try again
            run();
            return;
          }

          final Response reply = send.result();

          if (reply.size() == 0) {
            // no slots available we can't really proceed
            // exclude this client from then next attempt
            exclude.add(conn.socketAddress());
            cause.set(new RuntimeException("No slots available in the cluster."));
            // try again
            run();
            return;
          }

          // keep a set of seen clients so in the end unseen clients can be removed
          // from the connections list
          final Set<SocketAddress> seenClients = new HashSet<>();
          final AtomicInteger slotCounter = new AtomicInteger(reply.size());

          for (int i = 0; i < reply.size(); i++) {
            // multibulk
            Response s = reply.get(i);
            // single bulk
            int start = s.get(0).int32();
            int end = s.get(1).int32();

            // array of all clients, clients[2] = master, others are slaves
            List<SocketAddress> addresses = new ArrayList<>();
            for (int index = 2; index < s.size(); index++) {
              Response c = s.get(index);
              SocketAddress address = SocketAddress.inetSocketAddress(c.get(1).int32(), c.get(0).string());
              addresses.add(address);
              seenClients.add(address);
            }
            // load this slot
            loadSlot(start, end, addresses, options, onLoad -> {
              // all slots have been loaded
              if (slotCounter.decrementAndGet() == 0) {
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
              }
            });
          }
        });
      }
    };

    // start looking
    tryClient.run();
  }

  /**
   * Get a random Redis connection
   */
  private Redis getRandomConnection(Set<SocketAddress> exclude) {
    List<Redis> available = connections.entrySet().stream()
      .filter(kv -> !exclude.contains(kv.getKey()) && kv.getValue() != null)
      .map(Map.Entry::getValue)
      .collect(Collectors.toList());

    if (available.size() == 0) {
      // signal no client
      return null;
    }

    return available.get(RANDOM.nextInt(available.size()));
  }

  private void loadSlot(int start, int end, List<SocketAddress> addresses, RedisOptions options, Handler<Void> onLoad) {
    // temporal holder for the loaded connections
    final Redis[] connections = new Redis[addresses.size()];
    final AtomicInteger counter = new AtomicInteger(addresses.size());
    for (int i = 0; i < addresses.size(); i++) {
      final int idx = i;
      final SocketAddress address = addresses.get(idx);

      getClient(address, options, getClient -> {
        // we don't care if we can't get a client, in this case the client is ignored
        if (getClient.failed()) {
          LOG.warn("Could not get a connection to node [" + address + "]");
        } else {
          connections[idx] = getClient.result();
        }
        // end condition
        if (counter.decrementAndGet() == 0) {
          // update the slot table
          for (int j = start; j <= end; j++) {
            slots[j] = connections;
          }
          // notify
          onLoad.handle(null);
        }
      });
    }
  }

  private void send(final Redis client, final RedisOptions options, final int retries, Request command, Handler<AsyncResult<Response>> handler) {

    if (client == null) {
      handler.handle(Future.failedFuture("No connection available."));
      return;
    }

    client.send(command, send -> {
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

            getClient(socketAddress, options, getClient -> {
              if (getClient.failed()) {
                handler.handle(Future.failedFuture(getClient.cause()));
                return;
              }

              // re-run on the new client
              send(getClient.result(), options, retries - 1, command, handler);
            });
          };

          // key has been moved!
          // lets re fetch slots from redis to get an up to date allocation
          if (moved) {
            getSlots(options, getSlots -> andThen.run());
          } else {
            andThen.run();
          }
          return;
        }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> send(client, options, retries - 1, command, handler));
          return;
        }
      }

      handler.handle(send);
    });
  }

  private void batch(final Redis client, final RedisOptions options, final int retries, List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {

    if (client == null) {
      handler.handle(Future.failedFuture("No connection available."));
      return;
    }

    client.batch(commands, send -> {
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

            getClient(socketAddress, options, getClient -> {
              if (getClient.failed()) {
                handler.handle(Future.failedFuture(getClient.cause()));
                return;
              }

              // re-run on the new client
              batch(getClient.result(), options, retries - 1, commands, handler);
            });
          };

          // key has been moved!
          // lets re fetch slots from redis to get an up to date allocation
          if (moved) {
            getSlots(options, getSlots -> andThen.run());
          } else {
            andThen.run();
          }
          return;
        }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> batch(client, options, retries - 1, commands, handler));
          return;
        }
      }

      handler.handle(send);
    });
  }

  /**
   * Select a Redis client for the given key
   */
  private Redis selectClient(int keySlot, boolean readOnly) {
    // this command doesn't have keys, return any connection
    // NOTE: this means slaves may be used for no key commands regardless of slave config
    if (keySlot == -1) {
      return getRandomConnection(Collections.emptySet());
    }

    Redis[] clients = slots[keySlot];

    // if we haven't got config for this slot, try any connection
    if (clients == null || clients.length == 0) {
      return getRandomConnection(Collections.emptySet());
    }

    int index = 0;

    // always, never, share
    if (readOnly && slaves != RedisSlaves.NEVER && clients.length > 1) {
      // always use a slave for read commands
      if (slaves == RedisSlaves.ALWAYS) {
        index = RANDOM.nextInt(clients.length - 1) + 1;
      }
      // share read commands across master + slaves
      if (slaves == RedisSlaves.SHARE) {
        index = RANDOM.nextInt(clients.length);
      }
    }

    return clients[index];
  }
}
