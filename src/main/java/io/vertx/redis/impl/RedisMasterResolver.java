package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.sentinel.RedisSentinel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use this class to resolve the address of the Redis master using Sentinel servers.
 */
class RedisMasterResolver {

  private static final Logger log = LoggerFactory.getLogger(RedisMasterResolver.class);

  private static final int MAX_WAIT_FOR_MASTER_IN_MILLIS = 3000;

  private List<RedisSentinel> sentinels = new ArrayList<>();
  private String masterName;

  private Vertx vertx;

  RedisMasterResolver(Vertx vertx, RedisOptions redisOptions) {

    this.vertx = vertx;
    this.masterName = redisOptions.getMasterName();
    redisOptions.getSentinels().forEach(s -> {
      String[] hostAndPort = s.split(":");
      if (hostAndPort.length == 2) {
        sentinels.add(RedisSentinel.create(vertx, getSentinelOptions(redisOptions, hostAndPort[0], Integer.valueOf(hostAndPort[1]))));
      } else {
        sentinels.add(RedisSentinel.create(vertx, getSentinelOptions(redisOptions, hostAndPort[0], 6379)));
      }
    });
  }

  void getMasterAddressByName(Handler<AsyncResult<JsonObject>> handler) {

    log.debug("Attempting to resolving master address");

    try {

      AtomicInteger count = new AtomicInteger(sentinels.size());
      AtomicBoolean foundMaster = new AtomicBoolean(false);

      // Try get a master from any sentinel
      for (RedisSentinel sentinelClient : sentinels) {

        // Set up handler
        Handler<AsyncResult<JsonArray>> asyncResultHandler = jsonObjectAsyncResult -> {
          count.decrementAndGet();
          if (jsonObjectAsyncResult.succeeded()) {
            JsonArray masterArray = jsonObjectAsyncResult.result();
            if (masterArray != null && masterArray.size() == 2) {
              JsonObject redisMaster = new JsonObject().put("host", masterArray.getString(0)).put("port", Integer.valueOf(masterArray.getString(1)));
              if (!foundMaster.get()) {
                log.info(String.format("Sentinel resolved address for master '%s' to %s:%d", masterName, masterArray.getString(0), Integer.valueOf(masterArray.getString(1))));
                foundMaster.set(true);
                handler.handle(Future.factory.succeededFuture(redisMaster));
              }
            } else {
              handler.handle(Future.failedFuture(String.format("Sentinel failed to resolve address for master '%s'", masterName)));
            }
          } else {
            handler.handle(Future.failedFuture(String.format("Sentinel unreachable. %s", jsonObjectAsyncResult.cause().getMessage())));
          }

          if (count.get() == 0 && !foundMaster.get()) {
            handler.handle(Future.failedFuture("Failed to resolve master address"));
          }
        };

        // Set up timer for timeout
        final long timerId = vertx.setTimer(MAX_WAIT_FOR_MASTER_IN_MILLIS, t -> asyncResultHandler.handle(Future.failedFuture("Timeout on response from Sentinel")));

        // Handle the response
        sentinelClient.getMasterAddrByName(masterName, res -> {
          // Check if timer triggered
          if (vertx.cancelTimer(timerId)) {
            asyncResultHandler.handle(res);
          }
        });
      }
    } catch (Exception ex) {
      handler.handle(Future.factory.failedFuture(ex));
    }
  }

  private RedisOptions getSentinelOptions(RedisOptions redisOptions, String host, int port) {
    // copy the base config
    RedisOptions options = new RedisOptions(redisOptions);
    // override the host and port
    options.setHost(host);
    options.setPort(port);

    return options;
  }

  void close() {
    for (RedisSentinel sentinel : sentinels) {
      sentinel.close(h -> {});
    }
  }
}
