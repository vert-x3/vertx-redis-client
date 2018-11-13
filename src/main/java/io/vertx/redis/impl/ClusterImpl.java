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

    Slot(int start, int end, List<Redis> clients) {
      this.start = start;
      this.end = end;
      this.clients = clients;
    }

    int start;
    int end;
    List<Redis> clients;
  }

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  // https://github.com/antirez/redis-rb-cluster/blob/fd931ed34dfc53159e2f52c9ea2d4a5073faabeb/cluster.rb#L29
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
      getClient(endpoint, false, ar -> {
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
    return this;
  }

  @Override
  public Redis resume() {
    return this;
  }

  @Override
  public ReadStream<Reply> fetch(long amount) {
    return this;
  }

  @Override
  public Redis send(String command, Args args, Handler<AsyncResult<Reply>> handler) {

    send(selectClient(args.getKey()), RETRIES, command, args, handler);
    return this;
  }

  private void send(final Redis client, final int retries, String command, Args args, Handler<AsyncResult<Reply>> handler) {
    client.send(command, args, send -> {
      if (send.failed() && retries >= 0) {
        String message = send.cause().getMessage();
        boolean ask = message != null && message.startsWith("ASK ");
        boolean moved = !ask && message != null && message.startsWith("MOVED ");

        if (moved || ask) {
          final Runnable andThen = () -> {
            // REQUERY THE NEW ONE (we've got the correct details)
            String addr = message.split(" ")[2];
            String[] saddr = addr.split(":");
            getClient(SocketAddress.inetSocketAddress(Integer.parseInt(saddr[1]), saddr[0]), true, getClient -> {
              if (getClient.failed()) {
                handler.handle(Future.failedFuture(getClient.cause()));
                return;
              }

              final Redis c = getClient.result();

              if (ask) {
                c.send("asking", asking -> {
                  if (asking.failed()) {
                    handler.handle(Future.failedFuture(asking.cause()));
                    return;
                  }
                  // re-run on the new client
                  send(c, retries - 1, command, args, handler);
                });
              } else {
                // re-run on the new client
                send(c, retries - 1, command, args, handler);
              }
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

        if (message != null && (message.startsWith("TRYAGAIN") || message.startsWith("CLUSTERDOWN"))) {
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
  public boolean closed() {
    return false;
  }

  @Override
  public SocketAddress address() {
    throw new UnsupportedOperationException("Cluster address depends on the command");
  }


  /**
   * Get a Redis client via the connection cache (one per host)
   */
  private void getClient(SocketAddress address, boolean master, Handler<AsyncResult<Redis>> handler) {

    Redis cli = connections.get(address);

    // already have a connection to this client, return that
    if (cli != null) {
      handler.handle(Future.succeededFuture(cli));
      return;
    }

    final Redis client = Redis.create(vertx, address, options);

    client.exceptionHandler(t -> {
      // broken connection so force a new client to be created
      connections.put(address, null);
      if (onException != null) {
        onException.handle(t);
      }
      // slots are unbalanced need to refresh
      getSlots(ar -> {
        if (ar.failed()) {
          if (onException != null) {
            onException.handle(ar.cause());
          }
        }
      });
    });


    client.endHandler(v -> {
      for (Redis conn : connections.values()) {
        if (conn != null && !conn.closed()) {
          return;
        }
      }
      // all connections are closed
      if (onEnd != null) {
        onEnd.handle(null);
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

            List<Redis> clients = new ArrayList<>();
            // array of all clients, clients[2] = master, others are slaves
            for (int index = 2; index < s.size(); index++) {
              Reply c = s.get(index);
              SocketAddress address = SocketAddress.inetSocketAddress(c.get(1).asInteger(), c.get(0).asString());
              seenClients.add(address);
              getClient(address, index == 2, getClient -> {
                if (getClient.succeeded()) {
                  clients.add(getClient.result());
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

    return available.get(random.nextInt(available.size()));
  }

  /**
   * Select a Redis client for the given key
   */
  private Redis selectClient(String key) {
    // this command doesn't have keys, return any connection
    // NOTE: this means slaves may be used for no key commands regardless of slave config
    if (key == null) {
      return getRandomConnection(Collections.emptySet());
    }

    int slot = ZModem.generate(key);

    List<Redis> clients = null;
    for (Slot s : slots) {
      if (s.start <= slot && slot >= s.end) {
        clients = s.clients;
      }
    }
    // if we haven't got config for this slot, try any connection
    if (clients == null || clients.size() == 0) {
      return getRandomConnection(Collections.emptySet());
    }

    int index = 0;

    // always use a slave for read commands
    if (slaves == ClusterSlaves.ALWAYS) {
      index = 1 + random.nextInt(clients.size() - 1);
    }
    // share read commands across master + slaves
    if (slaves == ClusterSlaves.SHARE) {
      index = random.nextInt(clients.size());
    }

    return clients.get(index);
  }
}

