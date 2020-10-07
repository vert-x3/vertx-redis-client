package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.*;
import io.vertx.redis.client.impl.types.ErrorType;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

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

  private static final Map<Command, String> UNSUPPORTEDCOMMANDS = new HashMap<>();
  // reduce from list fo responses to a single response
  private static final Map<Command, Function<List<Response>, Response>> REDUCERS = new HashMap<>();
  // List of commands they should run every time only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  public static void addReducer(Command command, Function<List<Response>, Response> fn) {
    REDUCERS.put(command, fn);
  }

  public static void addUnSupportedCommand(Command command, String error) {
    if (error == null || error.isEmpty()) {
      UNSUPPORTEDCOMMANDS.put(command, "RedisClusterClient does not handle command " +
        new String(command.getBytes(), StandardCharsets.ISO_8859_1).split("\r\n")[1] + ", use non cluster client on the right node.");
    } else {
      UNSUPPORTEDCOMMANDS.put(command, error);
    }
  }

  public static void addMasterOnlyCommand(Command command) {
    MASTER_ONLY_COMMANDS.add(command);
  }

  private final Vertx vertx;
  private final RedisOptions options;
  private final Slots slots;
  private final Map<String, RedisConnection> connections;

  RedisClusterConnection(Vertx vertx, RedisOptions options, Slots slots, Map<String, RedisConnection> connections) {
    this.vertx = vertx;
    this.options = options;
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
  public RedisConnection send(Request request, Handler<AsyncResult<Response>> handler) {
    // process commands for cluster mode
    final RequestImpl req = (RequestImpl) request;
    final Command cmd = req.command();
    final boolean forceMasterEndpoint = MASTER_ONLY_COMMANDS.contains(cmd);

    if (UNSUPPORTEDCOMMANDS.containsKey(cmd)) {
      handler.handle(Future.failedFuture(UNSUPPORTEDCOMMANDS.get(cmd)));
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

      String[] endpoints = slots.endpointsForKey(hashSlot);
      send(selectMasterOrReplicaEndpoint(req.command().isReadOnly(), endpoints, forceMasterEndpoint), RETRIES, req, handler);
      return this;
    }

    if (cmd.isKeyless() && REDUCERS.containsKey(cmd)) {
      final List<Future> responses = new ArrayList<>(slots.size());

      for (int i = 0; i < slots.size(); i++) {

        String[] endpoints = slots.endpointsForSlot(i);

        final Promise<Response> p = Promise.promise();
        send(selectMasterOrReplicaEndpoint(req.command().isReadOnly(), endpoints, forceMasterEndpoint), RETRIES, req, p);
        responses.add(p.future());
      }
      CompositeFuture.all(responses).onComplete(composite -> {
        if (composite.failed()) {
          // means if one of the operations failed, then we can fail the handler
          handler.handle(Future.failedFuture(composite.cause()));
        } else {
          handler.handle(Future.succeededFuture(REDUCERS.get(cmd).apply(composite.result().list())));
        }
      });

      return this;
    }

    if (cmd.isKeyless()) {
      // it doesn't matter which node to use
      send(selectEndpoint(-1, cmd.isReadOnly(), forceMasterEndpoint), RETRIES, req, handler);
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
            handler.handle(Future.failedFuture(buildCrossslotFailureMsg(req)));
            return this;
          }

          final Map<Integer, Request> requests = splitRequest(cmd, args, start, end, step);
          final List<Future> responses = new ArrayList<>(requests.size());

          for (Map.Entry<Integer, Request> kv : requests.entrySet()) {
            final Promise<Response> p = Promise.promise();
            send(selectEndpoint(kv.getKey(), cmd.isReadOnly(), forceMasterEndpoint), RETRIES, kv.getValue(), p);
            responses.add(p.future());
          }

          CompositeFuture.all(responses).onComplete(composite -> {
            if (composite.failed()) {
              // means if one of the operations failed, then we can fail the handler
              handler.handle(Future.failedFuture(composite.cause()));
            } else {
              handler.handle(Future.succeededFuture(REDUCERS.get(cmd).apply(composite.result().list())));
            }
          });

          return this;
        }
      }

      // all keys are on the same slot!
      send(selectEndpoint(currentSlot, cmd.isReadOnly(), forceMasterEndpoint), RETRIES, req, handler);
      return this;
    }

    // last option the command is single key
    int start = cmd.getFirstKey() - 1;
    send(selectEndpoint(ZModem.generate(args.get(start)), cmd.isReadOnly(), forceMasterEndpoint), RETRIES, req, handler);
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
            String addr = cause.slice(' ', 2);

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
          connection.send(cmd(AUTH).arg(options.getPassword()), auth -> {
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
  public RedisConnection batch(List<Request> requests, Handler<AsyncResult<List<Response>>> handler) {
    int currentSlot = -1;
    boolean readOnly = false;
    boolean forceMasterEndpoint = false;

    // look up the base slot for the batch
    for (Request request : requests) {
      // process commands for cluster mode
      final RequestImpl req = (RequestImpl) request;
      final Command cmd = req.command();

      if (UNSUPPORTEDCOMMANDS.containsKey(cmd)) {
        handler.handle(Future.failedFuture(UNSUPPORTEDCOMMANDS.get(cmd)));
        return this;
      }

      readOnly |= cmd.isReadOnly();
      forceMasterEndpoint |= MASTER_ONLY_COMMANDS.contains(cmd);

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
      final int start = cmd.getFirstKey() - 1;
      final int slot = ZModem.generate(args.get(start));
      // we are checking the first request key
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

    batch(selectEndpoint(currentSlot, readOnly, forceMasterEndpoint), RETRIES, requests, handler);
    return this;
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
            String addr = cause.slice(' ', 2);

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
          connection.send(cmd(AUTH).arg(options.getPassword()), auth -> {
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
  public void close() {
    connections.forEach((key, value) -> {
      if (value != null) {
        value.close();
      }
    });
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
  private String selectEndpoint(int keySlot, boolean readOnly, boolean forceMasterEndpoint) {
    // this command doesn't have keys, return any connection
    // NOTE: this means replicas may be used for no key commands regardless of the config
    if (keySlot == -1) {
      return slots.randomEndPoint(forceMasterEndpoint);
    }

    String[] endpoints = slots.endpointsForKey(keySlot);

    // if we haven't got config for this slot, try any connection
    if (endpoints == null || endpoints.length == 0) {
      return options.getEndpoint();
    }
    return selectMasterOrReplicaEndpoint(readOnly, endpoints, forceMasterEndpoint);
  }

  private String selectMasterOrReplicaEndpoint(boolean readOnly, String[] endpoints, boolean forceMasterEndpoint) {
    int index = 0;

    if (forceMasterEndpoint) {
      return endpoints[index];
    }

    // always, never, share
    RedisReplicas useReplicas = options.getUseReplicas();

    if (readOnly && useReplicas != RedisReplicas.NEVER && endpoints.length > 1) {
      // always use a replica for read commands
      if (useReplicas == RedisReplicas.ALWAYS) {
        index = RANDOM.nextInt(endpoints.length - 1) + 1;
      }
      // share read commands across master + replicas
      if (useReplicas == RedisReplicas.SHARE) {
        index = RANDOM.nextInt(endpoints.length);
      }
    }
    return endpoints[index];
  }

  private String buildCrossslotFailureMsg(RequestImpl req) {
    return "Keys of command or batch: \"" + req.toString() + "\" targets not all in the same hash slot (CROSSSLOT) and client side resharding is not supported";
  }
}
