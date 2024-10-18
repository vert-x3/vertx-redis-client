package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisClusterConnectOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisClusterConnectOptions} original class using Vert.x codegen.
 */
public class RedisClusterConnectOptionsConverter {

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisClusterConnectOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "useReplicas":
          if (member.getValue() instanceof String) {
            obj.setUseReplicas(io.vertx.redis.client.RedisReplicas.valueOf((String)member.getValue()));
          }
          break;
        case "hashSlotCacheTTL":
          if (member.getValue() instanceof Number) {
            obj.setHashSlotCacheTTL(((Number)member.getValue()).longValue());
          }
          break;
        case "clusterTransactions":
          if (member.getValue() instanceof String) {
            obj.setClusterTransactions(io.vertx.redis.client.RedisClusterTransactions.valueOf((String)member.getValue()));
          }
          break;
      }
    }
  }

   static void toJson(RedisClusterConnectOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(RedisClusterConnectOptions obj, java.util.Map<String, Object> json) {
    if (obj.getUseReplicas() != null) {
      json.put("useReplicas", obj.getUseReplicas().name());
    }
    json.put("hashSlotCacheTTL", obj.getHashSlotCacheTTL());
    if (obj.getClusterTransactions() != null) {
      json.put("clusterTransactions", obj.getClusterTransactions().name());
    }
  }
}
