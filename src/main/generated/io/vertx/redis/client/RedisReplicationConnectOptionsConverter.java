package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisReplicationConnectOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisReplicationConnectOptions} original class using Vert.x codegen.
 */
public class RedisReplicationConnectOptionsConverter {

  private static final Base64.Decoder BASE64_DECODER = Base64.getUrlDecoder();
  private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisReplicationConnectOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "topology":
          if (member.getValue() instanceof String) {
            obj.setTopology(io.vertx.redis.client.RedisTopology.valueOf((String)member.getValue()));
          }
          break;
        case "useReplicas":
          if (member.getValue() instanceof String) {
            obj.setUseReplicas(io.vertx.redis.client.RedisReplicas.valueOf((String)member.getValue()));
          }
          break;
      }
    }
  }

   static void toJson(RedisReplicationConnectOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(RedisReplicationConnectOptions obj, java.util.Map<String, Object> json) {
    if (obj.getTopology() != null) {
      json.put("topology", obj.getTopology().name());
    }
    if (obj.getUseReplicas() != null) {
      json.put("useReplicas", obj.getUseReplicas().name());
    }
  }
}
