package io.vertx.redis.client.impl;

import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisRole;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.ResponseType;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

class SentinelFailover {
  private static final Logger LOG = LoggerFactory.getLogger(SentinelFailover.class);

  private static final int RETRIES = 3;

  private final String masterSetName;
  private final Function<RedisRole, Future<PooledRedisConnection>> connectionFactory;

  private final AtomicReference<PooledRedisConnection> sentinelConnection = new AtomicReference<>();
  private final Set<RedisSentinelConnection> masterConnections = ConcurrentHashMap.newKeySet();

  SentinelFailover(String masterSetName, Function<RedisRole, Future<PooledRedisConnection>> connectionFactory) {
    this.masterSetName = masterSetName;
    this.connectionFactory = connectionFactory;
  }

  void start() {
    start(RETRIES);
  }

  private void start(int retries) {
    connectionFactory.apply(RedisRole.SENTINEL)
      .onFailure(t -> {
        if (retries == 0) {
          LOG.error("Failed to obtain a connection to Redis sentinel, automatic failover will not work: " + t);
        } else {
          start(retries - 1);
        }
      })
      .onSuccess(sentinel -> {
        PooledRedisConnection old = sentinelConnection.getAndSet(sentinel);
        if (old != null) {
          old.close()
            .onFailure(err -> LOG.warn("Failed to close connection: " + err));
        }

        sentinel.handler(msg -> {
          if (msg.type() == ResponseType.PUSH
              && "message".equalsIgnoreCase(msg.get(0).toString())
              && msg.get(2).toString().startsWith(masterSetName + " ")) {
            reconnectAll();
          }
        });
        sentinel.exceptionHandler(t -> {
          sentinel.close();
          start(RETRIES);
        });
        sentinel.send(Request.cmd(Command.SUBSCRIBE).arg("+switch-master"))
          .onFailure(t -> {
            sentinel.close();
            if (retries == 0) {
              LOG.error("Failed to subscribe +switch-master on Redis sentinel connection, reconnection to new master on failover will not work: " + t);
            } else {
              start(retries - 1);
            }
          });
      });
  }

  private void reconnectAll() {
    for (RedisSentinelConnection connection : masterConnections) {
      connection.closeDelegate()
        .recover(ignored -> Future.succeededFuture())
        .compose(ignored -> connectionFactory.apply(RedisRole.MASTER))
        .onSuccess(connection::reconnect)
        .onFailure(t -> {
          LOG.error("Failed to reconnect to master after failover: " + t);
        });
    }
  }

  void addConnection(RedisSentinelConnection sentinelConn) {
    masterConnections.add(sentinelConn);
  }

  void removeConnection(RedisSentinelConnection sentinelConn) {
    masterConnections.remove(sentinelConn);
  }

  void close() {
    PooledRedisConnection sentinelConnection = this.sentinelConnection.get();
    if (sentinelConnection != null) {
      sentinelConnection.close()
        .onFailure(err -> LOG.warn("Failed to close connection: " + err));
    }
  }
}
