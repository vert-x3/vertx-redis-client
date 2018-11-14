package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.ReadStream;
import io.vertx.redis.*;
import io.vertx.redis.Reply;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ClusterImpl implements Cluster {

  private static class Slot {

    Slot(int start, int end, Redis[] clients) {
      this.start = start;
      this.end = end;
      this.clients = clients;
    }

    final int start;
    final int end;
    final Redis[] clients;
  }

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  private static final int RETRIES = 16;

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private final Random random = new Random();

  private final Vertx vertx;
  private final List<SocketAddress> endpoints;
  private final NetClientOptions options;
  private final ClusterSlaves slaves;

  // mutable state
  private final Map<SocketAddress, Redis> connections = new HashMap<>();
  private final List<Slot> slots = new ArrayList<>();

  private Handler<Throwable> onException;
  private Handler<Void> onEnd;
  private Handler<io.vertx.redis.Reply> onMessage;

  public ClusterImpl(Vertx vertx, List<SocketAddress> endpoints, NetClientOptions options, ClusterSlaves slaves) {
    this.vertx = vertx;
    this.endpoints = endpoints;
    this.options = options;
    this.slaves = slaves;
  }

  @Override
  public Cluster open(Handler<AsyncResult<Void>> handler) {
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
              handler.handle(Future.succeededFuture());
            }
          });
        }
      });
    }

    return this;
  }

  @Override
  public void close() {
    for (Redis conn : connections.values()) {
      if (conn != null) {
        conn.close();
      }
    }
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
  public Redis handler(Handler<Reply> handler) {
    this.onMessage = handler;
    return this;
  }

  @Override
  public Redis pause() {
    for (Redis cli : connections.values()) {
      cli.pause();
    }
    return this;
  }

  @Override
  public Redis resume() {
    for (Redis cli : connections.values()) {
      cli.resume();
    }
    return this;
  }

  @Override
  public ReadStream<Reply> fetch(long amount) {
    for (Redis cli : connections.values()) {
      cli.fetch(amount);
    }
    return this;
  }

  @Override
  public Redis send(String command, Args args, boolean readOnly, Handler<AsyncResult<Reply>> handler) {
    send(selectClient(args.getKeySlot(), readOnly), RETRIES, command, args, handler);
    return this;
  }

  private void send(final Redis client, final int retries, String command, Args args, Handler<AsyncResult<Reply>> handler) {

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

  @Override
  public SocketAddress address() {
    throw new UnsupportedOperationException("Cluster address depends on the command");
  }


  /**
   * Get a Redis client via the connection cache (one per host)
   */
  private void getClient(SocketAddress address, Handler<AsyncResult<Redis>> handler) {

    Redis cli = connections.get(address);

    // already have a connection to this client, return that
    if (cli != null) {
      handler.handle(Future.succeededFuture(cli));
      return;
    }

    final Redis client = Redis.create(vertx, address, options);

    client.exceptionHandler(t -> {
      // broken connection so force a new client to be created
      connections.remove(address);
      // propagate the exception
      if (onException != null) {
        onException.handle(t);
      }
      // now since the clients are unbalenced, we need to reload the slots
      getSlots(ar -> {
        if (ar.failed()) {
          // getting slots failed, so raise the exception
          if (onException != null) {
            onException.handle(ar.cause());
          }
        }
      });
    });


    client.endHandler(v -> {
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

    client.handler(r -> {
      if (onMessage != null) {
        onMessage.handle(r);
      }
    });

    // as soon as one client is ready, we're connected (ready to fetch slot allocation)
    client.open(open -> {
      if (open.succeeded()) {
        connections.put(address, client);
        handler.handle(Future.succeededFuture(client));
      } else {
        handler.handle(Future.failedFuture(open.cause()));
      }
    });
  }

  private void getSlots(Handler<AsyncResult<Void>> handler) {

    final Set<SocketAddress> exclude = new HashSet<>();
    final AtomicReference<Throwable> cause = new AtomicReference<>();

    final Runnable tryClient = new Runnable() {
      @Override
      public void run() {
        final Redis client = getRandomConnection(exclude);
        if (client == null) {
          // no more clients available
          handler.handle(Future.failedFuture("No client's available."));
          return;
        }

        client.send("cluster", Args.args("slots"), send -> {
          if (send.failed()) {
            // exclude this client from then next attempt
            exclude.add(client.address());
            cause.set(send.cause());
            // try again
            run();
            return;
          }


          final Reply slots = send.result();

          Set<SocketAddress> seenClients = new HashSet<>();
          ClusterImpl.this.slots.clear();

          for (int i = 0; i < slots.size(); i++) {
            // multibulk
            Reply s = slots.get(i);
            // single bulk
            int start = s.get(0).asInteger();
            int end = s.get(1).asInteger();

            final Redis[] clients = new Redis[s.size() - 2];

            // array of all clients, clients[2] = master, others are slaves
            for (int index = 2; index < s.size(); index++) {
              final int idx = index - 2;
              Reply c = s.get(index);
              SocketAddress address = SocketAddress.inetSocketAddress(c.get(1).asInteger(), c.get(0).asString());
              seenClients.add(address);
              getClient(address, getClient -> {
                if (getClient.succeeded()) {
                  clients[idx] = getClient.result();
                }
              });
            }

            ClusterImpl.this.slots.add(new Slot(start, end, clients));
          }

          // quit now-unused clients
          for (Map.Entry<SocketAddress, Redis> kv : connections.entrySet()) {
            if (kv.getValue() == null) {
              continue;
            }

            if (!seenClients.contains(kv.getKey())) {
              kv.getValue().close();
              kv.setValue(null);
            }
          }

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
  private Redis getRandomConnection(Set<SocketAddress> exclude) {
    List<Redis> available = connections.entrySet().stream()
      .filter(kv -> !exclude.contains(kv.getKey()) && kv.getValue() != null)
      .map(Map.Entry::getValue)
      .collect(Collectors.toList());

    if (available.size() == 0) {
      // signal no client
      return null;
    }

    return available.get(random.nextInt(available.size()));
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

    Redis[] clients = null;
    for (Slot s : slots) {
      if (s.start <= keySlot && keySlot >= s.end) {
        clients = s.clients;
      }
    }
    // if we haven't got config for this slot, try any connection
    if (clients == null || clients.length == 0) {
      return getRandomConnection(Collections.emptySet());
    }

    int index = 0;

    // always, never, share
    if (readOnly && slaves != ClusterSlaves.NEVER && clients.length > 1) {
      // always use a slave for read commands
      if (slaves == ClusterSlaves.ALWAYS) {
        index = random.nextInt(clients.length - 1) + 1;
      }
      // share read commands across master + slaves
      if (slaves == ClusterSlaves.SHARE) {
        index = random.nextInt(clients.length);
      }
    }

    return clients[index];
  }
}

