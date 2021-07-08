package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.*;

import java.util.*;

public class RedisReplicationConnection implements RedisConnection {

  private static final Logger LOG = LoggerFactory.getLogger(RedisReplicationConnection.class);

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private static final SplittableRandom RANDOM = new SplittableRandom();

  // List of commands they should run every time only against master nodes
  private static final List<Command> MASTER_ONLY_COMMANDS = new ArrayList<>();

  public static void addMasterOnlyCommand(Command command) {
    MASTER_ONLY_COMMANDS.add(command);
  }

  private final RedisOptions options;
  private final RedisConnection master;
  private final List<RedisConnection> replicas;

  RedisReplicationConnection(Vertx vertx, RedisOptions options, RedisConnection master, List<RedisConnection> replicas) {
    this.options = options;
    this.master = master;
    this.replicas = replicas;
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    master.exceptionHandler(handler);

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.exceptionHandler(handler);
      }
    }
    return this;
  }

  @Override
  public RedisConnection handler(Handler<Response> handler) {
    master.handler(handler);

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.handler(handler);
      }
    }
    return this;
  }

  @Override
  public RedisConnection pause() {
    master.pause();

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.pause();
      }
    }
    return this;
  }

  @Override
  public RedisConnection resume() {
    master.resume();

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.resume();
      }
    }
    return this;
  }

  @Override
  public RedisConnection fetch(long amount) {
    master.fetch(amount);

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.fetch(amount);
      }
    }
    return this;
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> handler) {
    master.endHandler(handler);

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.endHandler(handler);
      }
    }
    return this;
  }

  @Override
  public Future<Response> send(Request request) {
    // process commands for cluster mode
    final RequestImpl req = (RequestImpl) request;
    final Command cmd = req.command();
    final boolean forceMasterEndpoint = MASTER_ONLY_COMMANDS.contains(cmd);

    return selectMasterOrReplicaEndpoint(!cmd.isWrite(), forceMasterEndpoint)
      .send(request);
  }

  @Override
  public Future<List<Response>> batch(List<Request> requests) {
    if (requests.isEmpty()) {
      LOG.debug("Empty batch");
      return Future.succeededFuture(Collections.emptyList());
    } else {
      boolean readOnly = false;
      boolean forceMasterEndpoint = false;

      // look up the base slot for the batch
      for (Request request : requests) {
        // process commands for cluster mode
        final RequestImpl req = (RequestImpl) request;
        final Command cmd = req.command();

        readOnly |= !cmd.isWrite();
        forceMasterEndpoint |= MASTER_ONLY_COMMANDS.contains(cmd);
      }

      return selectMasterOrReplicaEndpoint(readOnly, forceMasterEndpoint)
        .batch(requests);
    }
  }

  @Override
  public void close() {
    master.close();

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        conn.close();
      }
    }
  }

  @Override
  public boolean pendingQueueFull() {
    boolean result = master.pendingQueueFull();

    for (RedisConnection conn : replicas) {
      if (conn != null) {
        result |= conn.pendingQueueFull();
      }
    }

    return result;
  }

  private RedisConnection selectMasterOrReplicaEndpoint(boolean read, boolean forceMasterEndpoint) {
    if (forceMasterEndpoint) {
      return master;
    }

    // always, never, share
    RedisReplicas useReplicas = options.getUseReplicas();

    if (read && useReplicas != RedisReplicas.NEVER && replicas.size() > 0) {
      switch (useReplicas) {
        // always use a replica for read commands
        case ALWAYS:
          return replicas.get(RANDOM.nextInt(replicas.size()));
        // share read commands across master + replicas
        case SHARE:
          int r = RANDOM.nextInt(replicas.size() + 1);
          if (r == 0) {
            return master;
          } else {
            return replicas.get(r - 1);
          }
      }
    }

    // fallback to master
    return master;
  }
}
