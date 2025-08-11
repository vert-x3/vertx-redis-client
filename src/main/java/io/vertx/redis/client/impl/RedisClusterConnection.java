package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisClusterConnectOptions;
import io.vertx.redis.client.RedisClusterTransactions;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class RedisClusterConnection implements RedisConnection {

  private static final Logger LOG = LoggerFactory.getLogger(RedisClusterConnection.class);

  // we need some randomness, it doesn't need to be cryptographically secure
  private static final Random RANDOM = new Random();

  // number of attempts/redirects when we get connection errors
  // or when we get MOVED/ASK responses
  static final int RETRIES = 16;

  // reduce from list of responses to a single response
  private static final Map<Command, Function<List<Response>, Response>> REDUCERS = new HashMap<>();
  // List of commands that should always run only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    REDUCERS.put(command, fn);
  }

  public static void addMasterOnlyCommand(Command command) {
    MASTER_ONLY_COMMANDS.add(command);
  }

  final VertxInternal vertx;
  private final RedisConnectionManager connectionManager;
  private final RedisClusterConnectOptions connectOptions;
  final SharedSlots sharedSlots;
  private final Map<String, PooledRedisConnection> connections;

  // these fields are only used in `send()` and are ignored in `batch()`, because request batches
  // are always sent to a single node and so no extra support is necessary
  private boolean deferredMulti = false;
  private String boundToEndpoint = null;

  RedisClusterConnection(Vertx vertx, RedisConnectionManager connectionManager, RedisClusterConnectOptions connectOptions,
      SharedSlots sharedSlots, Map<String, PooledRedisConnection> connections) {
    this.vertx = (VertxInternal) vertx;
    this.connectionManager = connectionManager;
    this.connectOptions = connectOptions;
    this.sharedSlots = sharedSlots;
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
    Future<Response> future = sharedSlots.get()
      .compose(slots -> send(request, slots));

    if (connectOptions.getClusterTransactions() == RedisClusterTransactions.SINGLE_NODE) {
      return future.andThen(ignored -> {
        String cmdName = request.command().toString();
        if ("exec".equals(cmdName) || "discard".equals(cmdName)) {
          deferredMulti = false;
          boundToEndpoint = null;
        }
      });
    } else {
      return future;
    }
  }

  private Future<Response> send(Request request, Slots slots) {
    final Promise<Response> promise = vertx.promise();

    // process commands for cluster mode
    final RequestImpl req = (RequestImpl) request;
    final CommandImpl cmd = (CommandImpl) req.command();
    final List<byte[]> args = req.getArgs();
    final List<byte[]> keys = req.keys();

    if (cmd.isTransactional()) {
      RedisClusterTransactions txMode = connectOptions.getClusterTransactions();
      if (txMode == RedisClusterTransactions.DISABLED) {
        promise.fail("Transactions in Redis cluster disabled");
        return promise.future();
      } else if (txMode == RedisClusterTransactions.SINGLE_NODE && boundToEndpoint == null) {
        String cmdName = cmd.toString();
        if ("multi".equals(cmdName)) {
          deferredMulti = true;
          return Future.succeededFuture(SimpleStringType.OK);
        } else if ("watch".equals(cmdName)) {
          int hashSlot = ZModem.generateMultiRaw(keys);
          String[] endpoints = slots.endpointsForKey(hashSlot);
          boundToEndpoint = endpoints[0]; // always master, since transactions are writing
        }
      }
    }

    if (cmd.needsGetKeys()) {
      // it is required to resolve the keys at the server side as we cannot deduct where they are algorithmically
      // we shall run this commands on the master node always
      send(selectEndpoint(slots, -1, cmd.isReadOnly(args), true), RETRIES, req, promise);
      return promise.future();
    }

    final boolean forceMasterEndpoint = MASTER_ONLY_COMMANDS.contains(cmd)
      || deferredMulti; // always master, since transactions are writing

    switch (keys.size()) {
      case 0:
        // can run anywhere
        if (REDUCERS.containsKey(cmd)) {
          final List<Future<Response>> responses = new ArrayList<>(slots.size());

          for (int i = 0; i < slots.size(); i++) {
            String[] endpoints = slots.endpointsForSlot(i);

            final Promise<Response> p = vertx.promise();
            send(selectMasterOrReplicaEndpoint(cmd.isReadOnly(args), endpoints, forceMasterEndpoint), RETRIES, req, p);
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
          send(selectEndpoint(slots, -1, cmd.isReadOnly(args), forceMasterEndpoint), RETRIES, req, promise);
        }
        return promise.future();
      case 1:
        // trivial option the command is single key
        send(selectEndpoint(slots, ZModem.generate(keys.get(0)), cmd.isReadOnly(args), forceMasterEndpoint), RETRIES, req, promise);
        return promise.future();
      default:
        // hashSlot -1 indicates that not all keys of the command targets the same hash slot,
        // so Redis would not be able to execute it.
        int hashSlot = ZModem.generateMultiRaw(keys);
        if (hashSlot == -1) {
          // not all keys are in same slot
          // we try to perform a reduction if we know how
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
            send(selectEndpoint(slots, kv.getKey(), cmd.isReadOnly(args), forceMasterEndpoint), RETRIES, kv.getValue(), p);
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
          send(selectMasterOrReplicaEndpoint(cmd.isReadOnly(args), endpoints, forceMasterEndpoint), RETRIES, req, promise);
          return promise.future();
        }
    }
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

  void send(String selectedEndpoint, int retries, Request command, Handler<AsyncResult<Response>> handler) {
    String endpoint = boundToEndpoint != null ? boundToEndpoint : selectedEndpoint;

    if (deferredMulti) {
      deferredMulti = false;
      boundToEndpoint = endpoint;

      send(endpoint, retries, Request.cmd(Command.MULTI), resp -> {
        if (resp.succeeded()) {
          send(endpoint, retries, command, handler);
        } else {
          handler.handle(Future.failedFuture(resp.cause()));
        }
      });
      return;
    }
    PooledRedisConnection connection = connections.get(endpoint);
    if (connection == null) {
      connectionManager.getConnection(endpoint, RedisReplicas.NEVER != connectOptions.getUseReplicas() ? Request.cmd(Command.READONLY) : null)
        .onSuccess(conn -> {
          synchronized (connections) {
            if (connections.containsKey(endpoint)) {
              conn.close()
                .onFailure(t -> LOG.warn("Failed closing connection: " + t));
            } else {
              connections.put(endpoint, conn);
            }
          }
          send(endpoint, retries, command, handler);
        })
        .onFailure(t -> {
          if (retries > 0) {
            send(endpoint, retries - 1, command, handler);
          } else {
            handler.handle(Future.failedFuture("Failed obtaining connection to: " + endpoint));
          }
        });
      return;
    }

    connection.send(command, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        boolean ask = cause.is("ASK");
          boolean moved = cause.is("MOVED");
          if (ask || moved) {
            if (moved) {  sharedSlots.invalidate();

        }

            // attempt to recover
            String addr = cause.slice(' ', 2);
            if (addr == null) {
              // bad message
              handler.handle(Future.failedFuture("Cannot find endpoint:port in redirection: " + cause));
              return;
            }

            RedisURI uri = new RedisURI(endpoint);
            if (addr.startsWith(":")) {
              // unknown endpoint, need to use the current one but the provided port
              addr = uri.socketAddress().host() + addr;
            }
            String newEndpoint = uri.protocol() + "://" + uri.userinfo() + addr;
            if (boundToEndpoint != null && !boundToEndpoint.equalsIgnoreCase(newEndpoint)) {
              handler.handle(Future.failedFuture("Redirect inside a transaction: " + cause));
              return;
            }
            if (ask) {
              send(newEndpoint, retries - 1, Request.cmd(Command.ASKING), resp -> {
                if (resp.failed()) {
                  handler.handle(Future.failedFuture("Failed ASKING: " + resp.cause() + ", caused by " + cause));
                } else {
                  send(newEndpoint, retries - 1, command, handler);
                }
              });
            } else {
              send(newEndpoint, retries - 1, command, handler);
            }
            return;
          }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> send(endpoint, retries - 1, command, handler));
          return;
        }

        if (cause.is("NOAUTH") && connectOptions.getPassword() != null) {
          // NOAUTH will try to authenticate
          connection.send(Request.cmd(Command.AUTH).arg(connectOptions.getPassword()), auth -> {
            if (auth.failed()) {
              handler.handle(Future.failedFuture(auth.cause()));
              return;
            }
            // again
            send(endpoint, retries - 1, command, handler);
          });
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
    return sharedSlots.get()
      .compose(slots -> batch(requests, slots));
  }

  private Future<List<Response>> batch(List<Request> requests, Slots slots) {
    final Promise<List<Response>> promise = vertx.promise();

    if (requests.isEmpty()) {
      LOG.debug("Empty batch");
      promise.complete(Collections.emptyList());
    } else {
      int correctSlot = -1;
      String currentEndpoint = null;
      boolean readOnly = false;
      boolean forceMasterEndpoint = false;

      // look up the base slot for the batch
      for (Request request : requests) {
        // process commands for cluster mode
        final RequestImpl req = (RequestImpl) request;
        final CommandImpl cmd = (CommandImpl) req.command();
        final List<byte[]> args = req.getArgs();

        // someone might expect that for symmetry with `send()`, we'll also check the commands here
        // and fail if any of them is transactional, but that would be wrong -- a batch is always
        // executed on a single node and can therefore contain the whole transaction

        readOnly |= cmd.isReadOnly(args);

        if (cmd.needsGetKeys()) {
          // it is required to resolve the keys at the server side as we cannot deduct where they are algorithmically
          // we shall run this commands on the master node always
          forceMasterEndpoint = true;
          continue;
        }

        final List<byte[]> keys = req.keys();
        forceMasterEndpoint |= MASTER_ONLY_COMMANDS.contains(cmd)
          || cmd.isTransactional(); // always master, since transactions are writing
        int slot;
        String endpoint;

        // process slots, need to verify if we can run this batch
        switch (keys.size()) {
          case 0:
            // this command can run anywhere
            break;
          case 1:
            // command is single key, as long as we're on the same slot, it's OK
            slot = ZModem.generate(keys.get(0));
            // as cluster server serves range of slots we need to compare to server range and not exact slot
            //always take master to make sure we have same endpoint
            endpoint = slots.endpointsForKey(slot)[0];
            // we are checking the first request key
            if (currentEndpoint == null) {
              currentEndpoint = endpoint;
              correctSlot = slot;
            } else if (!currentEndpoint.equals(endpoint)) {
              // in cluster mode we currently do not handle batching commands which keys are not on the same slot
              promise.fail(buildCrossslotFailureMsg(req));
              return promise.future();
            }
            break;
          default:
            // multiple keys on the command
            for (byte[] key : keys) {
              slot = ZModem.generate(key);
              endpoint = slots.endpointsForKey(slot)[0];
              if (currentEndpoint == null) {
                correctSlot = slot;
                currentEndpoint = endpoint;
              } else if (!currentEndpoint.equals(endpoint)) {
                // in cluster mode we currently do not handle batching commands which keys are not on the same slot
                promise.fail(buildCrossslotFailureMsg(req));
                return promise.future();
              }
              break;
            }
        }
      }

      // all keys are on the same slot!
      //we just need to decide which endpoint to use based on additional options
      batch(selectEndpoint(slots, correctSlot, readOnly, forceMasterEndpoint), RETRIES, requests, promise);
    }

    return promise.future();
  }

  private void batch(String endpoint, int retries, List<Request> commands, Handler<AsyncResult<List<Response>>> handler) {
    RedisConnection connection = connections.get(endpoint);
    if (connection == null) {
      connectionManager.getConnection(endpoint, RedisReplicas.NEVER != connectOptions.getUseReplicas() ? Request.cmd(Command.READONLY) : null)
        .onSuccess(conn -> {
          synchronized (connections) {
            if (connections.containsKey(endpoint)) {
              conn.close()
                .onFailure(t -> LOG.warn("Failed closing connection: " + t));
            } else {
              connections.put(endpoint, conn);
            }
          }
          batch(endpoint, retries, commands, handler);
        })
        .onFailure(t -> {
          if (retries > 0) {
            batch(endpoint, retries - 1, commands, handler);
          } else {
            handler.handle(Future.failedFuture("Failed obtaining connection to: " + endpoint));
          }
        });
      return;
    }

    connection.batch(commands, send -> {
      if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
        final ErrorType cause = (ErrorType) send.cause();

        boolean ask = cause.is("ASK");
          boolean moved = cause.is("MOVED");
          if (ask || moved) {
            if (moved) {  sharedSlots.invalidate();

        }

            // attempt to recover
            String addr = cause.slice(' ', 2);
            if (addr == null) {
              // bad message
              handler.handle(Future.failedFuture("Cannot find endpoint:port in redirection: " + cause));
              return;
            }

             RedisURI uri = new RedisURI(endpoint);
            if (addr.startsWith(":")) {
              // unknown endpoint, need to use the current one but the provided port
              addr = uri.socketAddress().host() + addr;
            }
            String newEndpoint = uri.protocol() + "://" + uri.userinfo() + addr;
            if (ask) {
              batch(newEndpoint, retries - 1, Collections.singletonList(Request.cmd(Command.ASKING)), resp -> {
                if (resp.failed()) {
                  handler.handle(Future.failedFuture("Failed ASKING: " + resp.cause() + ", caused by " + cause));
                } else {
                  batch(newEndpoint, retries - 1, commands, handler);
                }
              });
            } else {
              batch(newEndpoint, retries - 1, commands, handler);
          }
          return;
        }

        if (cause.is("TRYAGAIN") || cause.is("CLUSTERDOWN")) {
          // TRYAGAIN response or cluster down, retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, 16 - Math.max(retries, 9)) * 10);
          vertx.setTimer(backoff, t -> batch(endpoint, retries - 1, commands, handler));
          return;
        }

        if (cause.is("NOAUTH") && connectOptions.getPassword() != null) {
          // try to authenticate
          connection.send(Request.cmd(Command.AUTH).arg(connectOptions.getPassword()), auth -> {
            if (auth.failed()) {
              handler.handle(Future.failedFuture(auth.cause()));
              return;
            }
            // again
            batch(endpoint, retries - 1, commands, handler);
          });
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
    deferredMulti = false;
    boundToEndpoint = null;

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
  private String selectEndpoint(Slots slots, int keySlot, boolean readOnly, boolean forceMasterEndpoint) {
    // this command doesn't have keys, return any connection
    // NOTE: this means replicas may be used for no key commands regardless of the config
    if (keySlot == -1) {
      return slots.randomEndPoint(forceMasterEndpoint);
    }

    String[] endpoints = slots.endpointsForKey(keySlot);

    // if we haven't got config for this slot, try any connection
    if (endpoints == null || endpoints.length == 0) {
      RedisURI uri = new RedisURI(connectOptions.getEndpoint());
      return uri.protocol() + "://" + uri.userinfo() + uri.socketAddress();
    }
    return selectMasterOrReplicaEndpoint(readOnly, endpoints, forceMasterEndpoint);
  }

  private String selectMasterOrReplicaEndpoint(boolean readOnly, String[] endpoints, boolean forceMasterEndpoint) {
    if (forceMasterEndpoint) {
      return endpoints[0];
    }

    // always, never, share
    RedisReplicas useReplicas = connectOptions.getUseReplicas();

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

  String buildCrossslotFailureMsg(RequestImpl req) {
    return "Keys of command or batch: \"" + req.toString() + "\" targets not all in the same hash slot (CROSSSLOT) and client side resharding is not supported";
  }
}
