package io.vertx.redis.client.test;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.function.BiPredicate;

class ClusterUtils {
  private final Vertx vertx;
  private final Redis client;

  ClusterUtils(Vertx vertx, Redis client) {
    this.vertx = vertx;
    this.client = client;
  }

  Future<Result> connectToMasterThatServesSlot(int slot) {
    return connectToMasterAndReturnId((min, max) -> slot >= min && slot <= max);
  }

  Future<Result> connectToMasterThatDoesntServeSlot(int slot) {
    return connectToMasterAndReturnId((min, max) -> slot < min || slot > max);
  }

  private Future<Result> connectToMasterAndReturnId(BiPredicate<Integer, Integer> predicate) {
    return client.send(Request.cmd(Command.CLUSTER).arg("SLOTS"))
      .compose(slots -> {
        for (int i = 0; i < slots.size(); i++) {
          Response slot = slots.get(i);
          if (predicate.test(slot.get(0).toInteger(), slot.get(1).toInteger())) {
            Response node = slot.get(2);
            String endpoint = "redis://" + node.get(0) + ":" + node.get(1);
            String id = node.get(2).toString();
            Redis redis = Redis.createClient(vertx, endpoint);
            return redis.connect()
              .map(conn -> new Result(redis, conn, id));
          }
        }
        return Future.failedFuture("Couldn't find matching slot");
      });
  }

  static class Result {
    final Redis redis;
    final RedisConnection conn;
    final String id;

    Result(Redis redis, RedisConnection conn, String id) {
      this.redis = redis;
      this.conn = conn;
      this.id = id;
    }
  }
}
