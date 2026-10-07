package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.internal.VertxInternal;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisClusterConnectOptions;
import io.vertx.redis.client.RedisClusterTransactions;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.Primitives.Int;
import io.vertx.redis.client.impl.Primitives.IntList;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.client.impl.types.SimpleStringType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
  private static final Map<Command, Function<List<ResponseWithPositions>, Response>> REDUCERS = new HashMap<>();
  // List of commands that should always run only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  @Deprecated(forRemoval = true)
  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    REDUCERS.put(command, list -> {
      List<Response> responses = new ArrayList<>(list.size());
      for (ResponseWithPositions r : list) {
        responses.add(r.response());
      }
      return fn.apply(responses);
    });
  }

  static void addNewReducer(Command command, Function<List<ResponseWithPositions>, Response> fn) {
    REDUCERS.put(command, fn);
  }

  @Deprecated(forRemoval = true)
  public static void addMasterOnlyCommand(Command command) {
    MASTER_ONLY_COMMANDS.add(command);
  }

  static class ResponseWithPositions {
    private final Response response;
    private final IntList positions;

    private ResponseWithPositions(Response response, IntList positions) {
      this.response = response;
      this.positions = positions;
    }

    public Response response() {
      return response;
    }

    public IntList positions() {
      return positions;
    }
  }

  final VertxInternal vertx;
  private final RedisConnectionManager connectionManager;
  private final RedisClusterConnectOptions connectOptions;
  final SharedSlots sharedSlots;
  private Slots lastSlots;
  private final Map<String, PooledRedisConnection> connections = new HashMap<>();
  private boolean connectedToAllNodes = false;

  // these fields are only used in `send()` and are ignored in `batch()`, because request batches
  // are always sent to a single node and so no extra support is necessary
  private boolean deferredMulti = false;
  private String boundToEndpoint = null;

  /**
   * Creates a cluster connection that doesn't hold any pooled connection yet. Unless {@link #connectToAllNodes(Slots)}
   * is called, it acquires a pooled connection to a node only when a request is sent there, and releases it before
   * acquiring another one, so it must only be used for a single connection-less request.
   */
  RedisClusterConnection(Vertx vertx, RedisConnectionManager connectionManager, RedisClusterConnectOptions connectOptions,
      SharedSlots sharedSlots, Slots lastSlots) {
    this.vertx = (VertxInternal) vertx;
    this.connectionManager = connectionManager;
    this.connectOptions = connectOptions;
    this.sharedSlots = sharedSlots;
    this.lastSlots = lastSlots;
  }

  /**
   * Acquires a pooled connection to every node, one at a time, in the order of {@link Slots#endpoints()}.
   * Cluster connections that acquire their pooled connections this way can't wait for each other's
   * pooled connections in a cycle. On failure, releases the pooled connections acquired so far.
   */
  Future<Void> connectToAllNodes(Slots slots) {
    Promise<Void> promise = vertx.promise();
    connectToAllNodes(slots.endpoints(), 0, promise);
    return promise.future();
  }

  private void connectToAllNodes(String[] endpoints, int index, Completable<Void> onConnected) {
    // a pooled connection is often available immediately, so this loops instead of recursing,
    // which keeps the stack depth independent of the number of nodes
    for (int i = index; i < endpoints.length; i++) {
      String endpoint = endpoints[i];
      Future<PooledRedisConnection> future = connectionManager.getConnection(endpoint, RedisReplicas.NEVER != connectOptions.getUseReplicas() ? Request.cmd(Command.READONLY) : null);
      if (!future.isComplete()) {
        int next = i + 1;
        future.onComplete(ignored -> {
          if (addConnection(endpoint, future, onConnected)) {
            connectToAllNodes(endpoints, next, onConnected);
          }
        });
        return;
      }
      if (!addConnection(endpoint, future, onConnected)) {
        return;
      }
    }

    connectedToAllNodes = true;
    onConnected.succeed();
  }

  // on failure, releases the pooled connections acquired so far and fails `onConnected`
  private boolean addConnection(String endpoint, Future<PooledRedisConnection> future, Completable<Void> onConnected) {
    if (future.failed()) {
      releaseAll();
      onConnected.fail(new RedisConnectException("Failed to connect to all nodes of the cluster\n- " + endpoint + ": " + future.cause().getMessage()));
      return false;
    }

    synchronized (connections) {
      connections.put(endpoint, future.result());
    }
    return true;
  }

  /**
   * Acquires a pooled connection to given {@code endpoint} for a connection-less request, after releasing
   * the one it holds, if any. The request therefore never waits for a pooled connection while holding
   * another one.
   */
  private void connectTo(String endpoint, Completable<Void> onConnected) {
    releaseAll();
    connectionManager.getConnection(endpoint, RedisReplicas.NEVER != connectOptions.getUseReplicas() ? Request.cmd(Command.READONLY) : null)
      .onFailure(onConnected::fail)
      .onSuccess(conn -> {
        synchronized (connections) {
          connections.put(endpoint, conn);
        }
        onConnected.succeed();
      });
  }

  private void releaseAll() {
    synchronized (connections) {
      for (RedisConnection conn : connections.values()) {
        conn.close().onFailure(LOG::warn);
      }
      connections.clear();
    }
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

  /**
   * Returns the best available slots: fresh slots from the shared cache if available,
   * otherwise the last known slots. Triggers a refresh of the cache if it has been
   * invalidated, but doesn't wait for the refresh to finish.
   */
  Slots currentSlots() {
    Future<Slots> future = sharedSlots.get();
    if (future.succeeded()) {
      lastSlots = future.result();
    }
    return lastSlots;
  }

  @Override
  public Future<Response> send(Request request) {
    Future<Response> future = send(request, currentSlots());

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
          if (!connectedToAllNodes) {
            // the parts of a connection-less request run on multiple nodes at the same time, so it first
            // acquires pooled connections to all nodes, in order, like `RedisClusterClient.connect()` does
            return connectToAllNodes(slots).compose(ignored -> send(request, slots));
          }

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
              List<Response> list = composite.result().list();
              List<ResponseWithPositions> listWithPositions = new ArrayList<>(list.size());
              for (Response resp : list) {
                listWithPositions.add(new ResponseWithPositions(resp, new IntList()));
              }
              promise.succeed(REDUCERS.get(cmd).apply(listWithPositions));
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

          final Collection<RequestWithSlotNumber> groupedRequests = splitRequest(cmd, args);

          if (groupedRequests.isEmpty()) {
            // we can't continue as we don't know how to split this command
            promise.fail(buildCrossslotFailureMsg(req));
            return promise.future();
          }

          if (!connectedToAllNodes) {
            return connectToAllNodes(slots).compose(ignored -> send(request, slots));
          }

          final List<Future<Response>> responses = new ArrayList<>(groupedRequests.size());
          final Map<Integer, IntList> responsePositions = new HashMap<>();

          int i = 0;
          for (RequestWithSlotNumber rwsn : groupedRequests) {
            final Promise<Response> p = vertx.promise();
            send(selectEndpoint(slots, rwsn.slot, cmd.isReadOnly(args), forceMasterEndpoint), RETRIES, rwsn.request, p);
            responses.add(p.future());

            responsePositions.put(i, rwsn.includedArguments);
            i++;
          }

          Future.all(responses).onComplete(composite -> {
            if (composite.failed()) {
              // means if one of the operations failed, then we can fail the handler
              promise.fail(composite.cause());
            } else {
              List<Response> list = composite.result().list();
              List<ResponseWithPositions> listWithPositions = new ArrayList<>(list.size());
              for (int j = 0; j < list.size(); j++) {
                listWithPositions.add(new ResponseWithPositions(list.get(j), responsePositions.get(j)));
              }
              promise.succeed(REDUCERS.get(cmd).apply(listWithPositions));
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

  private static class RequestWithSlotNumber {
    final int slot;
    final Request request;
    final IntList includedArguments;

    RequestWithSlotNumber(int slot, Request request) {
      this.slot = slot;
      this.request = request;
      this.includedArguments = new IntList();
    }
  }

  private Collection<RequestWithSlotNumber> splitRequest(CommandImpl cmd, List<byte[]> args) {
    // we will split the request across the slots
    final Map<Integer, RequestWithSlotNumber> map = new HashMap<>();
    final Int argCounter = new Int(0);

    int lastKey = cmd.iterateKeys(args, (begin, keyIdx, keyStep) -> {
      int slot = ZModem.generate(args.get(keyIdx));
      // get the client for the slot
      Request request;
      RequestWithSlotNumber rwsn = map.get(slot);
      if (rwsn == null) {
        // we need to create a new one
        request = Request.cmd(cmd);
        rwsn = new RequestWithSlotNumber(slot, request);
        // all params before the key get added
        for (int j = 0; j < begin; j++) {
          request.arg(args.get(j));
        }
        // add to the map
        map.put(slot, rwsn);
      } else {
        request = rwsn.request;
      }
      // all params before the next key get added
      for (int j = keyIdx; j < keyIdx + keyStep; j++) {
        request.arg(args.get(j));
      }
      rwsn.includedArguments.add(argCounter.value++);
    });

    // if there are args after the end they must be added to all requests
    final Collection<RequestWithSlotNumber> requests = map.values();
    for (RequestWithSlotNumber rwsn : requests) {
      Request req = rwsn.request;
      for (int j = lastKey; j < args.size(); j++) {
        req.arg(args.get(j));
      }
    }

    return requests;
  }

  void send(String selectedEndpoint, int retries, Request command, Completable<Response> handler) {
    String endpoint = boundToEndpoint != null ? boundToEndpoint : selectedEndpoint;

    if (deferredMulti) {
      deferredMulti = false;
      boundToEndpoint = endpoint;

      send(endpoint, retries, Request.cmd(Command.MULTI), (result, failure) -> {
        if (failure == null) {
          send(endpoint, retries, command, handler);
        } else {
          handler.fail(failure);
        }
      });
      return;
    }

    PooledRedisConnection connection = connections.get(endpoint);
    if (connection == null) {
      if (!connectedToAllNodes) {
        connectTo(endpoint, (ignored, err) -> {
          if (err == null) {
            send(endpoint, retries, command, handler);
          } else {
            handler.fail(err);
          }
        });
        return;
      }

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
            handler.fail("Failed obtaining connection to: " + endpoint);
          }
        });
      return;
    }

    connection
      .send(command)
      .onComplete(send -> {
        if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
          final ErrorType cause = (ErrorType) send.cause();

          boolean ask = cause.is("ASK");
          boolean moved = cause.is("MOVED");
          if (ask || moved) {
            if (moved) {
              sharedSlots.invalidate();
            }

            // attempt to recover
            String addr = cause.slice(' ', 2);
            if (addr == null) {
              // bad message
              handler.fail("Cannot find endpoint:port in redirection: " + cause);
              return;
            }

            RedisURI uri = new RedisURI(endpoint);
            if (addr.startsWith(":")) {
              // unknown endpoint, need to use the current one but the provided port
              addr = uri.socketAddress().host() + addr;
            }
            String newEndpoint = uri.protocol() + "://" + uri.userinfo() + addr;
            if (boundToEndpoint != null && !boundToEndpoint.equalsIgnoreCase(newEndpoint)) {
              handler.fail("Redirect inside a transaction: " + cause);
              return;
            }
            if (ask) {
              send(newEndpoint, retries - 1, Request.cmd(Command.ASKING), (resp, err) -> {
                if (err != null) {
                  handler.fail("Failed ASKING: " + err + ", caused by " + cause);
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
            connection
              .send(Request.cmd(Command.AUTH).arg(connectOptions.getPassword()))
              .onFailure(handler::fail)
              .onSuccess(auth -> {
                // again
                send(endpoint, retries - 1, command, handler);
              });
            return;
          }
        }

        try {
          handler.complete(send.result(), send.cause());
        } catch (RuntimeException e) {
          LOG.error("Handler failure", e);
        }
      });
  }

  @Override
  public Future<List<Response>> batch(List<Request> requests) {
    return batch(requests, currentSlots());
  }

  private Future<List<Response>> batch(List<Request> requests, Slots slots) {
    final Promise<List<Response>> promise = vertx.promise();

    if (requests.isEmpty()) {
      LOG.debug("Empty batch");
      promise.succeed(Collections.emptyList());
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

  private void batch(String endpoint, int retries, List<Request> commands, Completable<List<Response>> handler) {
    RedisConnection connection = connections.get(endpoint);
    if (connection == null) {
      if (!connectedToAllNodes) {
        connectTo(endpoint, (ignored, err) -> {
          if (err == null) {
            batch(endpoint, retries, commands, handler);
          } else {
            handler.fail(err);
          }
        });
        return;
      }

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
            handler.fail("Failed obtaining connection to: " + endpoint);
          }
        });
      return;
    }

    connection
      .batch(commands)
      .onComplete(send -> {
        if (send.failed() && send.cause() instanceof ErrorType && retries >= 0) {
          final ErrorType cause = (ErrorType) send.cause();

          boolean ask = cause.is("ASK");
          boolean moved = cause.is("MOVED");
          if (ask || moved) {
            if (moved) {
              sharedSlots.invalidate();
            }

            // attempt to recover
            String addr = cause.slice(' ', 2);
            if (addr == null) {
              // bad message
              handler.fail("Cannot find endpoint:port in redirection: " + cause);
              return;
            }

            RedisURI uri = new RedisURI(endpoint);
            if (addr.startsWith(":")) {
              // unknown endpoint, need to use the current one but the provided port
              addr = uri.socketAddress().host() + addr;
            }
            String newEndpoint = uri.protocol() + "://" + uri.userinfo() + addr;
            if (ask) {
              batch(newEndpoint, retries - 1, Collections.singletonList(Request.cmd(Command.ASKING)), (resp, err) -> {
                if (err != null) {
                  handler.fail("Failed ASKING: " + err + ", caused by " + cause);
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
            connection
              .send(Request.cmd(Command.AUTH).arg(connectOptions.getPassword()))
              .onFailure(handler::fail)
              .onSuccess(auth -> {
                // again
                batch(endpoint, retries - 1, commands, handler);
              });
            return;
          }
        }

        try {
          handler.complete(send.result(), send.cause());
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
