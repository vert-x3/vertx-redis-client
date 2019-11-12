/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl;

import io.vertx.core.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.client.impl.types.IntegerType;
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

public class RedisClusterClient implements Redis {

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  private static final int RETRIES = 16;

  private static final Logger LOG = LoggerFactory.getLogger(RedisClusterClient.class);

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private static final Random RANDOM = new Random();

  // reduce from list fo responses to a single response
  private static final Map<Command, Function<List<Response>, Response>> REDUCERS = new HashMap<>();

  private static final Map<Command, String> UNSUPPORTEDCOMMANDS = new HashMap<>();

  // List of command they should run every time only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    REDUCERS.put(command, fn);
  }

  public static void addUnSupportedCommand(Command command, String error) {
    if(error == null || error.isEmpty()) {
      UNSUPPORTEDCOMMANDS.put(command, "RedisClusterClient does not handle command " +
      new String(command.getBytes(), StandardCharsets.ISO_8859_1).split("\r\n")[1] + ", use non cluster client on the right node.");
    } else {
      UNSUPPORTEDCOMMANDS.put(command, error);
    }
  }

  public static Redis create(Vertx vertx, RedisOptions options) {
    return new RedisClusterClient(vertx, options);
  }

  static {
    // provided reducers

    addReducer(MSET, list -> {
      // Simple string reply: always OK since MSET can't fail.
      return SimpleStringType.OK;
    });

    addReducer(DEL, list -> {
      return IntegerType.create(list.stream().mapToLong(Response::toLong).sum());
    });

    addReducer(MGET, list -> {
      int total = 0;
      for (Response resp : list) {
        total += resp.size();
      }

      MultiType multi = MultiType.create(total);
      for (Response resp : list) {
        for (Response child : resp) {
          multi.add(child);
        }
      }

      return multi;
    });

    addReducer(KEYS, list -> {
      int total = 0;
      for (Response resp : list) {
        total += resp.size();
      }

      MultiType multi = MultiType.create(total);
      for (Response resp : list) {
        for (Response child : resp) {
          multi.add(child);
        }
      }

      return multi;
    });

    // Simple string reply: always OK since FLUSHDB can't fail.
    addReducer(FLUSHDB, list -> SimpleStringType.OK);

    // Sum of key numbers on all Key Slots
    addReducer(DBSIZE, list -> {
      return IntegerType.create(list.stream().mapToLong(Response::toLong).sum());
    });

    Arrays.asList(ASKING, AUTH, BGREWRITEAOF, BGSAVE, CLIENT, CLUSTER, COMMAND, CONFIG,
    DEBUG, DISCARD, HOST, INFO, LASTSAVE, LATENCY, LOLWUT, MEMORY, MODULE, MONITOR, PFDEBUG, PFSELFTEST,
    PING, READONLY, READWRITE, REPLCONF, REPLICAOF, ROLE, SAVE, SCAN, SCRIPT, SELECT, SHUTDOWN, SLAVEOF, SLOWLOG, SWAPDB,
    SYNC, SENTINEL).forEach(command -> addUnSupportedCommand(command, null));

    addUnSupportedCommand(FLUSHALL, "RedisClusterClient does not handle command FLUSHALL, use FLUSHDB");

    MASTER_ONLY_COMMANDS.add(WAIT);
  }

  private final Vertx vertx;
  private final RedisSlaves slaves;
  private final RedisOptions options;

  // mutable state
  private final Map<SocketAddress, Redis> connections = new HashMap<>();
  // Addresses of all slave nodes
  private final Set<SocketAddress> slaveAddresses = new HashSet<>();
  private final Redis[][] slots = new Redis[16384][];

  private Handler<Throwable> onException = t -> LOG.error("Unhandled Error", t);

  private Handler<Void> onEnd;
  private Handler<Response> onMessage;
  private int slotNumber;

  private RedisClusterClient(Vertx vertx, RedisOptions options) {
    this.vertx = vertx;
    this.slaves = options.getUseSlave();
    this.options = options;
  }

  @Override
  public Redis connect(Handler<AsyncResult<Redis>> onCreate) {
    // for each endpoint open a client
    final List<SocketAddress> endpoints = options.getEndpoints();

    final AtomicInteger counter = new AtomicInteger(endpoints.size());
    // ensure that in case of error, only call the callback once

    for (SocketAddress endpoint : endpoints) {
      getClient(endpoint, options, ar -> {
        final int total = counter.decrementAndGet();

        if (ar.failed()) {
          // if the client can't connect we should warn but continue
          LOG.warn("Failed to connect to: " + endpoint);
        }

        if (total == 0) {
          // fetch slots from the cluster immediately to ensure slots are correct
          getSlots(options, getSlotsResult -> {
            if (getSlotsResult.failed()) {
              onCreate.handle(Future.failedFuture(getSlotsResult.cause()));
            } else {
              slaveAddresses.addAll(getSlotsResult.result());

              onCreate.handle(Future.succeededFuture(this));
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
  public Redis send(Request request, Handler<AsyncResult<Response>> handler) {
    // process commands for cluster mode
    final RequestImpl req = (RequestImpl) request;
    final Command cmd = req.command();
    final boolean forceMasterNode = MASTER_ONLY_COMMANDS.contains(cmd);

    if (UNSUPPORTEDCOMMANDS.containsKey(cmd)) {
      try {
        handler.handle(Future.failedFuture(UNSUPPORTEDCOMMANDS.get(cmd)));
      } catch (RuntimeException e) {
        onException.handle(e);
      }
      return this;
    }

    if (cmd.isMovable()) {
      final byte[][] keys = KeyExtractor.extractMovableKeys(req);

      int hashSlot = ZModem.generateMulti(keys);
      // -1 indicates that not all keys of the command targets the same hash slot, so Redis would not be able to execute it.
      if (hashSlot == -1) {
        handler.handle(Future.failedFuture(buildCrossslotFailureMsg(req)));
        return this;
      }

      Redis[] clients = slots[hashSlot];
      send(selectMasterOrSlave(req.command().isReadOnly(), forceMasterNode, clients), options, RETRIES, req, handler);
      return this;
    }

    if (cmd.isKeyless() && REDUCERS.containsKey(cmd)) {
      final List<Future> responses = new ArrayList<>(slotNumber);

      for (int i = 1; i <= slotNumber; i++) {

        Redis[] clients = slots[(slots.length / slotNumber - 1) * i];

        final Future<Response> f = Future.future();
        send(selectMasterOrSlave(req.command().isReadOnly(), forceMasterNode, clients), options, RETRIES, req, f);
        responses.add(f);
      }
      CompositeFuture.all(responses).setHandler(composite -> {
        if (composite.failed()) {
          // means if one of the operations failed, then we can fail the handler
          try {
            handler.handle(Future.failedFuture(composite.cause()));
          } catch (RuntimeException e) {
            onException.handle(e);
          }
        } else {
          try {
            handler.handle(Future.succeededFuture(REDUCERS.get(cmd).apply(composite.result().list())));
          } catch (RuntimeException e) {
            onException.handle(e);
          }
        }
      });

      return this;
    }

    if (cmd.isKeyless()) {
      // it doesn't matter which node to use
      send(selectClient(-1, cmd.isReadOnly(), forceMasterNode), options, RETRIES, req, handler);
      return this;
    }

    final List<byte[]> args = req.getArgs();

    if (cmd.isMultiKey()) {
      int currentSlot = -1;

      // args exclude the command which is an arg in the commands response
      int start = cmd.getFirstKey() - 1;
      int end = cmd.getLastKey();
      if (end > 0) {
        end--;
      }
      if (end < 0) {
        end = args.size() + (end + 1);
      }
      int step = cmd.getInterval();

      for (int i = start; i < end; i += step) {
        int slot = ZModem.generate(args.get(i));
        if (currentSlot == -1) {
          currentSlot = slot;
          continue;
        }
        if (currentSlot != slot) {

          if (!REDUCERS.containsKey(cmd)) {
            // we can't continue as we don't know how to reduce this
            try {
              handler.handle(Future.failedFuture(buildCrossslotFailureMsg(req)));
            } catch (RuntimeException e) {
              onException.handle(e);
            }
            return this;
          }

          final Map<Integer, Request> requests = splitRequest(cmd, args, start, end, step);
          final List<Future> responses = new ArrayList<>(requests.size());

          for (Map.Entry<Integer, Request> kv : requests.entrySet()) {
            final Promise<Response> p = Promise.promise();
            send(selectClient(kv.getKey(), cmd.isReadOnly(), forceMasterNode), options, RETRIES, kv.getValue(), p);
            responses.add(p.future());
          }

          CompositeFuture.all(responses).setHandler(composite -> {
            if (composite.failed()) {
              // means if one of the operations failed, then we can fail the handler
              try {
                handler.handle(Future.failedFuture(composite.cause()));
              } catch (RuntimeException e) {
                onException.handle(e);
              }
            } else {
              try {
                handler.handle(Future.succeededFuture(REDUCERS.get(cmd).apply(composite.result().list())));
              } catch (RuntimeException e) {
                onException.handle(e);
              }
            }
          });

          return this;
        }
      }

      // all keys are on the same slot!
      send(selectClient(currentSlot, cmd.isReadOnly(), forceMasterNode), options, RETRIES, req, handler);
      return this;
    }

    // last option the command is single key
    int start = cmd.getFirstKey() - 1;
    send(selectClient(ZModem.generate(args.get(start)), cmd.isReadOnly(), forceMasterNode), options, RETRIES, req, handler);
    return this;
  }

  private Map<Integer, Request> splitRequest(Command cmd, List<byte[]> args, int start, int end, int step) {
    // we will split the request across the slots
    final Map<Integer, Request> map = new IdentityHashMap<>();

    for (int i = start; i < end; i += step) {
      int slot = ZModem.generate(args.get(i));
      // get the client for the slot
      Request request = map.get(slot);
      if (request == null) {
        // we need to create a new one
        request = Request.cmd(cmd);
        // all params before the key get added
        for (int j = 0; j < start; j++) {
          request.arg(args.get(j));
        }
        // add to the map
        map.put(slot, request);
      }
      // request isn't null anymore
      request.arg(args.get(i));
      // all params before the next key get added
      for (int j = i + 1; j < i + step; j++) {
        request.arg(args.get(j));
      }
    }

    // if there are args after the end they must be added to all requests
    final Collection<Request> col = map.values();
    col.forEach(req -> {
      for (int j = end; j < args.size(); j++) {
        req.arg(args.get(j));
      }
    });

    return map;
  }

  @Override
  public Redis batch(List<Request> requests, Handler<AsyncResult<List<Response>>> handler) {
    int currentSlot = -1;
    boolean readOnly = false;
    boolean forceMasterNode = false;

    // look up the base slot for the batch
    for (int i = 0; i < requests.size(); i++) {
      // process commands for cluster mode
      final RequestImpl req = (RequestImpl) requests.get(i);
      final Command cmd = req.command();

      if(UNSUPPORTEDCOMMANDS.containsKey(cmd)) {
        try {
          handler.handle(Future.failedFuture(UNSUPPORTEDCOMMANDS.get(cmd)));
        } catch (RuntimeException e) {
          onException.handle(e);
        }
        return this;
      }

      readOnly |= cmd.isReadOnly();
      forceMasterNode |= MASTER_ONLY_COMMANDS.contains(cmd);

      // this command can run anywhere
      if (cmd.isKeyless()) {
        continue;
      }

      if (cmd.isMovable()) {
        final byte[][] keys = KeyExtractor.extractMovableKeys(req);

        int slot = ZModem.generateMulti(keys);
        if (slot == -1 || (currentSlot != -1 && currentSlot != slot)) {
          handler.handle(Future.failedFuture(buildCrossslotFailureMsg(req)));
          return this;
        }
        currentSlot = slot;
        continue;
      }

      final List<byte[]> args = req.getArgs();

      if (cmd.isMultiKey()) {
        // args exclude the command which is an arg in the commands response
        int start = cmd.getFirstKey() - 1;
        int end = cmd.getLastKey();
        if (end > 0) {
          end--;
        }
        if (end < 0) {
          end = args.size() + (end + 1);
        }
        int step = cmd.getInterval();

        for (int j = start; j < end; j += step) {
          int slot = ZModem.generate(args.get(j));
          if (currentSlot == -1) {
            currentSlot = slot;
            continue;
          }
          if (currentSlot != slot) {
            // in cluster mode we currently do not handle batching commands which keys are not on the same slot
            handler.handle(Future.failedFuture(buildCrossslotFailureMsg(req)));
            return this;
          }
        }
        // all keys are on the same slot!
        continue;
      }

      // last option the command is single key
      int start = cmd.getFirstKey() - 1;
      final int slot = ZModem.generate(args.get(start));
      // we are checking the first request key
      if (currentSlot == -1) {
        currentSlot = slot;
        continue;
      }
      if (currentSlot != slot) {
        // in cluster mode we currently do not handle batching commands which keys are not on the same slot
        handler.handle(Future.failedFuture("RedisClusterClient does not handle batching commands with keys across different slots. TODO: Split the command into slots and then batch."));
        return this;
      }
    }

    batch(selectClient(currentSlot, readOnly, forceMasterNode), options, RETRIES, requests, handler);
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

    RedisClient.create(vertx, options, address).connect(create -> {
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

  private void getSlots(RedisOptions options, Handler<AsyncResult<List<SocketAddress>>> handler) {

    final Set<SocketAddress> exclude = new HashSet<>();
    final AtomicReference<Throwable> cause = new AtomicReference<>();

    final Runnable tryClient = new Runnable() {
      @Override
      public void run() {
        final Redis conn = getRandomConnection(exclude);
        if (conn == null) {
          // no more clients available
          handler.handle(Future.failedFuture(ErrorType.create("SLOTS No client's available.")));
          return;
        }

        conn.send(cmd(CLUSTER).arg("SLOTS"), send -> {
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
            cause.set(ErrorType.create("SLOTS No slots available in the cluster."));
            // try again
            run();
            return;
          }

          // keep a set of seen clients so in the end unseen clients can be removed
          // from the connections list
          final Set<SocketAddress> seenClients = new HashSet<>();
          final AtomicInteger slotCounter = new AtomicInteger(reply.size());
          final List<SocketAddress> slaveAddresses = new ArrayList<>();
          slotNumber = reply.size();

          for (int i = 0; i < reply.size(); i++) {
            // multibulk
            Response s = reply.get(i);
            // single bulk
            int start = s.get(0).toInteger();
            int end = s.get(1).toInteger();

            // array of all clients, clients[2] = master, others are slaves
            List<SocketAddress> addresses = new ArrayList<>();
            for (int index = 2; index < s.size(); index++) {
              Response c = s.get(index);
              SocketAddress address = SocketAddress.inetSocketAddress(c.get(1).toInteger(), c.get(0).toString());
              addresses.add(address);
              seenClients.add(address);
              if (index != 2) {
                slaveAddresses.add(address);
              }
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

                handler.handle(Future.succeededFuture(slaveAddresses));
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

      final Future<Redis> getClientFuture = Future.future();
      getClient(address, options, getClientFuture);

      getClientFuture.compose(getClient -> {
        if (RedisSlaves.NEVER != options.getUseSlave()) {
          final Future<Response> readOnlyFuture = Future.future();
          getClient.send(cmd(READONLY), readOnlyFuture);
          return readOnlyFuture.map(getClient);
        } else {
          return Future.succeededFuture(getClient);
        }
      }).setHandler(getClient -> {
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
      try {
        handler.handle(Future.failedFuture("No connection available."));
      } catch (RuntimeException e) {
        onException.handle(e);
      }
      return;
    }

    client.send(command, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        boolean ask = cause.is("ASK");
        boolean moved = !ask && cause.is("MOVED");

        if (moved || ask) {
          final Runnable andThen = () -> {
            // REQUERY THE NEW ONE (we've got the correct details)
            String addr = cause.slice(' ', 2);

            if (addr == null) {
              // bad message
              try {
                handler.handle(Future.failedFuture(cause));
              } catch (RuntimeException e) {
                onException.handle(e);
              }
              return;
            }

            int sep = addr.lastIndexOf(':');
            SocketAddress socketAddress;

            if (sep != -1) {
              // InetAddress
              socketAddress = SocketAddress.inetSocketAddress(
                Integer.parseInt(addr.substring(sep + 1)),
                addr.substring(0, sep));
            } else {
              // assume unix domain
              socketAddress = SocketAddress.domainSocketAddress(addr);
            }

            getClient(socketAddress, options, getClient -> {
              if (getClient.failed()) {
                try {
                  handler.handle(Future.failedFuture(getClient.cause()));
                } catch (RuntimeException e) {
                  onException.handle(e);
                }
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

      try {
        handler.handle(send);
      } catch (RuntimeException e) {
        onException.handle(e);
      }
    });
  }

  private void batch(final Redis client, final RedisOptions options, final int retries, List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {

    if (client == null) {
      try {
        handler.handle(Future.failedFuture("No connection available."));
      } catch (RuntimeException e) {
        onException.handle(e);
      }
      return;
    }

    client.batch(commands, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        boolean ask = cause.is("ASK");
        boolean moved = !ask && cause.is("MOVED");

        if (moved || ask) {
          final Runnable andThen = () -> {
            // REQUERY THE NEW ONE (we've got the correct details)
            String addr = cause.slice(' ', 2);

            if (addr == null) {
              // bad message
              try {
                handler.handle(Future.failedFuture(cause));
              } catch (RuntimeException e) {
                onException.handle(e);
              }
              return;
            }

            int sep = addr.lastIndexOf(':');
            SocketAddress socketAddress;

            if (sep != -1) {
              // InetAddress
              socketAddress = SocketAddress.inetSocketAddress(
                Integer.parseInt(addr.substring(sep + 1)),
                addr.substring(0, sep));
            } else {
              // assume unix domain
              socketAddress = SocketAddress.domainSocketAddress(addr);
            }

            getClient(socketAddress, options, getClient -> {
              if (getClient.failed()) {
                try {
                  handler.handle(Future.failedFuture(getClient.cause()));
                } catch (RuntimeException e) {
                  onException.handle(e);
                }
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

      try {
        handler.handle(send);
      } catch (RuntimeException e) {
        onException.handle(e);
      }
    });
  }

  /**
   * Select a Redis client for the given key
   */
  private Redis selectClient(int keySlot, boolean readOnly, boolean forceMasterNode) {
    // this command doesn't have keys, return any connection
    // NOTE: this means slaves may be used for no key commands regardless of slave config, except it's a command that have to run against master node only
    if (keySlot == -1) {
      if (forceMasterNode) {
        return getRandomConnection(slaveAddresses);
      } else {
        return getRandomConnection(Collections.emptySet());
      }
    }

    Redis[] clients = slots[keySlot];

    // if we haven't got config for this slot, try any connection
    if (clients == null || clients.length == 0) {
      return getRandomConnection(Collections.emptySet());
    }
    return selectMasterOrSlave(readOnly, forceMasterNode, clients);
  }

  private Redis selectMasterOrSlave(boolean readOnly, boolean forceMasterNode, Redis[] clients) {
    int index = 0;

    if (forceMasterNode) {
      return clients[index];
    }

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

  private String buildCrossslotFailureMsg(RequestImpl req) {
    return "Keys of command or batch: \"" + req.toString() + "\" targets not all in the same hash slot (CROSSSLOT) and client side resharding is not supported";
  }
}
