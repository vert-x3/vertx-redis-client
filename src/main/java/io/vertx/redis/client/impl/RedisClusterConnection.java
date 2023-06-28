package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.*;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.vertx.redis.client.Command.ASKING;
import static io.vertx.redis.client.Command.AUTH;
import static io.vertx.redis.client.Request.cmd;

public class RedisClusterConnection implements RedisConnection {

  private static final Logger LOG = LoggerFactory.getLogger(RedisClusterConnection.class);

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private static final Random RANDOM = new Random();

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  private static final int RETRIES = 16;

  // reduce from list fo responses to a single response
  private static final Map<Command, Function<List<Response>, Response>> REDUCERS = new HashMap<>();
  // List of commands they should run every time only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    REDUCERS.put(command, fn);
  }

  public static void addMasterOnlyCommand(Command command) {
    MASTER_ONLY_COMMANDS.add(command);
  }

  private final VertxInternal vertx;
  private final RedisOptions options;
  private final Supplier<Future<RedisOptions>> optionsSupplier;
  private final Slots slots;
  private final Map<String, RedisConnection> connections;

  RedisClusterConnection(Vertx vertx, RedisOptions options, Supplier<Future<RedisOptions>> optionsSupplier,
                         Slots slots, Map<String, RedisConnection> connections) {
    this.vertx = (VertxInternal) vertx;
    this.options = options;
    this.optionsSupplier = optionsSupplier;
    this.slots = slots;
    this.connections = connections;
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.exceptionHandler(handler);
      }
    }
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.handler(handler);
      }
    }
    return this;
  }

  @Override
  public RedisConnection pause() {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.pause();
      }
    }
    return this;
  }

  @Override
  public RedisConnection resume() {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.resume();
      }
    }
    return this;
  }

  @Override
  public RedisConnection fetch(long amount) {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.fetch(amount);
      }
    }
    return this;
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> handler) {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        conn.endHandler(handler);
      }
    }
    return this;
  }

  @Override
  public Future<Response> send(Request request) {
    return optionsSupplier.get().flatMap(options -> {

      final Promise<Response> promise = vertx.promise();

      // process commands for cluster mode
      final RequestImpl req = (RequestImpl) request;
      final CommandImpl cmd = (CommandImpl) req.command();
      final List<byte[]> args = req.getArgs();

      if (cmd.needsGetKeys()) {
        // it is required to resolve the keys at the server side as we cannot deduct where they are algorithmically
        // we shall run this commands on the master node always
        send(selectEndpoint(-1, cmd.isReadOnly(args), true, options), RETRIES, req, promise);
        return promise.future();
      }

      final boolean forceMasterEndpoint = MASTER_ONLY_COMMANDS.contains(cmd);
      final List<byte[]> keys = req.keys();

      switch (keys.size()) {
        case 0:
          // can run anywhere
          if (REDUCERS.containsKey(cmd)) {
            final List<Future<Response>> responses = new ArrayList<>(slots.size());

            for (int i = 0; i < slots.size(); i++) {
              String[] endpoints = slots.endpointsForSlot(i);

              final Promise<Response> p = vertx.promise();
              send(selectMasterOrReplicaEndpoint(cmd.isReadOnly(args), endpoints, forceMasterEndpoint, options), RETRIES, req, p);
              responses.add(p.future());
            }

            Future.all(responses).onComplete(composite -> {
              if (composite.failed()) {
                // means if one of the operations failed, then we can fail the handler
                promise.fail(composite.cause());
              } else {
                promise.complete(REDUCERS.get(cmd).apply(composite.result().list()));
              }
            });
          } else {
            // it doesn't matter which node to use
            send(selectEndpoint(-1, cmd.isReadOnly(args), forceMasterEndpoint, options), RETRIES, req, promise);
          }
          return promise.future();
        case 1:
          // trivial option the command is single key
          send(selectEndpoint(ZModem.generate(keys.get(0)), cmd.isReadOnly(args), forceMasterEndpoint, options), RETRIES, req, promise);
          return promise.future();
        default:
          // hashSlot -1 indicates that not all keys of the command targets the same hash slot,
          // so Redis would not be able to execute it.
          int hashSlot = ZModem.generateMultiRaw(keys);
          if (hashSlot == -1) {
            // not all keys are in same slot// we try to perform a reduction if we know how


            if (!REDUCERS.containsKey(cmd)) {
              // we can't continue as we don't know how to reduce this
              promise.fail(buildCrossslotFailureMsg(req));
              return promise.future();
            }

            final Map<Integer, Request> requests = splitRequest(cmd, args);

            if (requests.isEmpty()) {
              // we can't continue as we don't know how to split this command
              promise.fail(buildCrossslotFailureMsg(req));
              return promise.future();
            }

            final List<Future<Response>> responses = new ArrayList<>(requests.size());

            for (Map.Entry<Integer, Request> kv : requests.entrySet()) {
              final Promise<Response> p = vertx.promise();
              send(selectEndpoint(kv.getKey(), cmd.isReadOnly(args), forceMasterEndpoint, options), RETRIES, kv.getValue(), p);
              responses.add(p.future());
            }

            Future.all(responses).onComplete(composite -> {
              if (composite.failed()) {
                // means if one of the operations failed, then we can fail the handler
                promise.fail(composite.cause());
              } else {
                promise.complete(REDUCERS.get(cmd).apply(composite.result().list()));
              }
            });

            return promise.future();
          } else {
            // all keys are in same slot
            String[] endpoints = slots.endpointsForKey(hashSlot);
            send(selectMasterOrReplicaEndpoint(cmd.isReadOnly(args), endpoints, forceMasterEndpoint, options), RETRIES, req, promise);
            return promise.future();
          }
      }
    });
  }

  private Map<Integer, Request> splitRequest(CommandImpl cmd, List<byte[]> args) {
    // we will split the request across the slots
    final Map<Integer, Request> map = new IdentityHashMap<>();

    int lastKey = cmd.iterateKeys(args, (begin, keyIdx, keyStep) -> {
      int slot = ZModem.generate(args.get(keyIdx));
      // get the client for the slot
      Request request = map.get(slot);
      if (request == null) {
        // we need to create a new one
        request = Request.cmd(cmd);
        // all params before the key get added
        for (int j = 0; j < begin; j++) {
          request.arg(args.get(j));
        }
        // add to the map
        map.put(slot, request);
      }
      // request isn't null anymore
      request.arg(args.get(keyIdx));
      // all params before the next key get added
      for (int j = keyIdx + 1; j < keyIdx + keyStep; j++) {
        request.arg(args.get(j));
      }
    });

    // if there are args after the end they must be added to all requests
    final Collection<Request> col = map.values();
    col.forEach(req -> {
      for (int j = lastKey; j < args.size(); j++) {
        req.arg(args.get(j));
      }
    });

    return map;
  }

  private void send(String endpoint, int retries, Request command, Handler<AsyncResult<Response>> handler) {

    final RedisConnection connection = connections.get(endpoint);

    if (connection == null) {
      handler.handle(Future.failedFuture("Missing connection to: " + endpoint));
      return;
    }

    connection.send(command, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        if (cause.is("MOVED")) {
          // cluster is unbalanced, need to reconnect
          handler.handle(Future.failedFuture(cause));
          return;
        }

        if (cause.is("ASK")) {
          connection.send(cmd(ASKING), asking -> {
            if (asking.failed()) {
              handler.handle(Future.failedFuture(asking.cause()));
              return;
            }
            // attempt to recover
            // REQUERY THE NEW ONE (we've got the correct details)
            String addr = cause.slice(' ', cause.is("ERR") ? 3 : 2);

            if (addr == null) {
              // bad message
              handler.handle(Future.failedFuture(cause));
              return;
            }
            // inherit protocol config from the current connection
            final RedisURI uri = new RedisURI(endpoint);
            // re-run on the new endpoint
            send(uri.protocol() + "://" + uri.userinfo() + addr, retries - 1, command, handler);
          });
          return;
        }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> send(endpoint, retries - 1, command, handler));
          return;
        }

        if (cause.is("NOAUTH") && options.getPassword() != null) {
          // NOAUTH will try to authenticate
          final Future<Response> authenticate = optionsSupplier.get().flatMap(options -> {
            final Promise<Response> promise = vertx.promise();
            connection.send(cmd(AUTH).arg(options.getPassword()), auth -> {
              if (auth.failed()) {
                promise.fail(auth.cause());
                handler.handle(Future.failedFuture(auth.cause()));
                return;
              }
              // again
              send(endpoint, retries - 1, command, promise);
            });
            return promise.future();
          });
          handler.handle(authenticate);
          return;
        }
      }

      try {
        handler.handle(send);
      } catch (RuntimeException e) {
        LOG.error("Handler failure", e);
      }
    });
  }

  @Override
  public Future<List<Response>> batch(List<Request> requests) {
    return optionsSupplier.get().flatMap(options -> {

      final Promise<List<Response>> promise = vertx.promise();

      if (requests.isEmpty()) {
        LOG.debug("Empty batch");
        promise.complete(Collections.emptyList());
      } else {
        int currentSlot = -1;
        boolean readOnly = false;
        boolean forceMasterEndpoint = false;

        // look up the base slot for the batch
        for (Request request : requests) {
          // process commands for cluster mode
          final RequestImpl req = (RequestImpl) request;
          final CommandImpl cmd = (CommandImpl) req.command();
          final List<byte[]> args = req.getArgs();


          readOnly |= cmd.isReadOnly(args);

          if (cmd.needsGetKeys()) {
            // it is required to resolve the keys at the server side as we cannot deduct where they are algorithmically
            // we shall run this commands on the master node always
            forceMasterEndpoint = true;
            continue;
          }

          final List<byte[]> keys = req.keys();
          forceMasterEndpoint |= MASTER_ONLY_COMMANDS.contains(cmd);
          int slot;

          // process slots, need to verify if we can run this batch
          switch (keys.size()) {
            case 0:
              // this command can run anywhere
              break;
            case 1:
              // command is single key, as long as we're on the same slot, it's OK
              slot = ZModem.generate(keys.get(0));
              // we are checking the first request key
              if (currentSlot == -1) {
                currentSlot = slot;
              } else if (currentSlot != slot) {
                // in cluster mode we currently do not handle batching commands which keys are not on the same slot
                promise.fail(buildCrossslotFailureMsg(req));
                return promise.future();
              }
              break;
            default:
              // multiple keys on the command
              for (byte[] key : keys) {
                slot = ZModem.generate(key);
                if (currentSlot == -1) {
                  currentSlot = slot;
                } else if (currentSlot != slot) {
                  // in cluster mode we currently do not handle batching commands which keys are not on the same slot
                  promise.fail(buildCrossslotFailureMsg(req));
                  return promise.future();
                }
                break;
              }
          }
        }

        // all keys are on the same slot!
        batch(selectEndpoint(currentSlot, readOnly, forceMasterEndpoint, options), RETRIES, requests, promise);
      }
      return promise.future();
    });
  }

  private void batch(String endpoint, int retries, List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {

    final RedisConnection connection = connections.get(endpoint);

    if (connection == null) {
      handler.handle(Future.failedFuture("Missing connection to: " + endpoint));
      return;
    }

    connection.batch(commands, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        if (cause.is("MOVED")) {
          // cluster is unbalanced, need to reconnect
          handler.handle(Future.failedFuture(cause));
          return;
        }

        if (cause.is("ASK")) {
          connection.send(cmd(ASKING), asking -> {
            if (asking.failed()) {
              handler.handle(Future.failedFuture(asking.cause()));
              return;
            }
            // attempt to recover
            // REQUERY THE NEW ONE (we've got the correct details)
            String addr = cause.slice(' ', cause.is("ERR") ? 3 : 2);

            if (addr == null) {
              // bad message
              handler.handle(Future.failedFuture(cause));
              return;
            }

            // inherit protocol config from the current connection
            final RedisURI uri = new RedisURI(endpoint);
            // re-run on the new endpoint
            batch(uri.protocol() + "://" + uri.userinfo() + addr, retries - 1, commands, handler);
          });
          return;
        }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> batch(endpoint, retries - 1, commands, handler));
          return;
        }

        if (cause.is("NOAUTH") && options.getPassword() != null) {
          // try to authenticate

          final Future<List<Response>> authenticate = optionsSupplier.get().flatMap(options -> {
            final Promise<List<Response>> promise = vertx.promise();
            connection.send(cmd(AUTH).arg(options.getPassword()), auth -> {
              if (auth.failed()) {
                promise.fail(auth.cause());
                handler.handle(Future.failedFuture(auth.cause()));
                return;
              }
              // again
              batch(endpoint, retries - 1, commands, promise);
            });
            return promise.future();
          });
          handler.handle(authenticate);
          return;
        }
      }

      try {
        handler.handle(send);
      } catch (RuntimeException e) {
        LOG.error("Handler failure", e);
      }
    });
  }

  @Override
  public Future<Void> close() {
    List<Future<Void>> futures = new ArrayList<>();
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        futures.add(conn.close());
      }
    }

    return Future.all(futures)
      .mapEmpty();
  }

  @Override
  public boolean pendingQueueFull() {
    for (RedisConnection conn : connections.values()) {
      if (conn != null) {
        if (conn.pendingQueueFull()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Select a Redis client for the given key
   */
  private String selectEndpoint(int keySlot, boolean readOnly, boolean forceMasterEndpoint, RedisOptions options) {
    // this command doesn't have keys, return any connection
    // NOTE: this means replicas may be used for no key commands regardless of the config
    if (keySlot == -1) {
      return slots.randomEndPoint(forceMasterEndpoint);
    }

    String[] endpoints = slots.endpointsForKey(keySlot);

    // if we haven't got config for this slot, try any connection
    if (endpoints == null || endpoints.length == 0) {
      return this.options.getEndpoint();
    }
    return selectMasterOrReplicaEndpoint(readOnly, endpoints, forceMasterEndpoint, options);
  }

  private String selectMasterOrReplicaEndpoint(boolean readOnly, String[] endpoints, boolean forceMasterEndpoint,
                                               RedisOptions options) {
    if (forceMasterEndpoint) {
      return endpoints[0];
    }

    // always, never, share
    RedisReplicas useReplicas = options.getUseReplicas();

    if (readOnly && useReplicas != RedisReplicas.NEVER && endpoints.length > 1) {
      switch (useReplicas) {
        // always use a replica for read commands
        case ALWAYS:
          // index must always be more than 1 as 0 denotes master
          return endpoints[1 + RANDOM.nextInt(endpoints.length - 1)];
        // share read commands across master + replicas
        case SHARE:
          return endpoints[RANDOM.nextInt(endpoints.length)];
      }
    }

    // fallback to master
    return endpoints[0];
  }

  private String buildCrossslotFailureMsg(RequestImpl req) {
    return "Keys of command or batch: \"" + req.toString() + "\" targets not all in the same hash slot (CROSSSLOT) and client side resharding is not supported";
  }
}
