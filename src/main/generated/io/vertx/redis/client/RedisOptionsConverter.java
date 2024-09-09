package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter {

  private static final Base64.Decoder BASE64_DECODER = Base64.getUrlDecoder();
  private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "type":
          if (member.getValue() instanceof String) {
            obj.setType(io.vertx.redis.client.RedisClientType.valueOf((String)member.getValue()));
          }
          break;
        case "netClientOptions":
          if (member.getValue() instanceof JsonObject) {
            obj.setNetClientOptions(new io.vertx.core.net.NetClientOptions((io.vertx.core.json.JsonObject)member.getValue()));
          }
          break;
        case "topology":
          if (member.getValue() instanceof String) {
            obj.setTopology(io.vertx.redis.client.RedisTopology.valueOf((String)member.getValue()));
          }
          break;
        case "endpoints":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setEndpoints(list);
          }
          break;
        case "maxWaitingHandlers":
          if (member.getValue() instanceof Number) {
            obj.setMaxWaitingHandlers(((Number)member.getValue()).intValue());
          }
          break;
        case "masterName":
          if (member.getValue() instanceof String) {
            obj.setMasterName((String)member.getValue());
          }
          break;
        case "role":
          if (member.getValue() instanceof String) {
            obj.setRole(io.vertx.redis.client.RedisRole.valueOf((String)member.getValue()));
          }
          break;
        case "useReplicas":
          if (member.getValue() instanceof String) {
            obj.setUseReplicas(io.vertx.redis.client.RedisReplicas.valueOf((String)member.getValue()));
          }
          break;
        case "maxNestedArrays":
          if (member.getValue() instanceof Number) {
            obj.setMaxNestedArrays(((Number)member.getValue()).intValue());
          }
          break;
        case "tracingPolicy":
          if (member.getValue() instanceof String) {
            obj.setTracingPolicy(io.vertx.core.tracing.TracingPolicy.valueOf((String)member.getValue()));
          }
          break;
        case "poolCleanerInterval":
          if (member.getValue() instanceof Number) {
            obj.setPoolCleanerInterval(((Number)member.getValue()).intValue());
          }
          break;
        case "maxPoolSize":
          if (member.getValue() instanceof Number) {
            obj.setMaxPoolSize(((Number)member.getValue()).intValue());
          }
          break;
        case "maxPoolWaiting":
          if (member.getValue() instanceof Number) {
            obj.setMaxPoolWaiting(((Number)member.getValue()).intValue());
          }
          break;
        case "poolRecycleTimeout":
          if (member.getValue() instanceof Number) {
            obj.setPoolRecycleTimeout(((Number)member.getValue()).intValue());
          }
          break;
        case "password":
          if (member.getValue() instanceof String) {
            obj.setPassword((String)member.getValue());
          }
          break;
        case "protocolNegotiation":
          if (member.getValue() instanceof Boolean) {
            obj.setProtocolNegotiation((Boolean)member.getValue());
          }
          break;
        case "preferredProtocolVersion":
          if (member.getValue() instanceof String) {
            obj.setPreferredProtocolVersion(io.vertx.redis.client.ProtocolVersion.valueOf((String)member.getValue()));
          }
          break;
        case "poolName":
          if (member.getValue() instanceof String) {
            obj.setPoolName((String)member.getValue());
          }
          break;
        case "metricsName":
          if (member.getValue() instanceof String) {
            obj.setMetricsName((String)member.getValue());
          }
          break;
        case "hashSlotCacheTTL":
          if (member.getValue() instanceof Number) {
            obj.setHashSlotCacheTTL(((Number)member.getValue()).longValue());
          }
          break;
        case "autoFailover":
          if (member.getValue() instanceof Boolean) {
            obj.setAutoFailover((Boolean)member.getValue());
          }
          break;
      }
    }
  }

   static void toJson(RedisOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(RedisOptions obj, java.util.Map<String, Object> json) {
    if (obj.getType() != null) {
      json.put("type", obj.getType().name());
    }
    if (obj.getNetClientOptions() != null) {
      json.put("netClientOptions", obj.getNetClientOptions().toJson());
    }
    if (obj.getTopology() != null) {
      json.put("topology", obj.getTopology().name());
    }
    if (obj.getEndpoints() != null) {
      JsonArray array = new JsonArray();
      obj.getEndpoints().forEach(item -> array.add(item));
      json.put("endpoints", array);
    }
    json.put("maxWaitingHandlers", obj.getMaxWaitingHandlers());
    if (obj.getMasterName() != null) {
      json.put("masterName", obj.getMasterName());
    }
    if (obj.getRole() != null) {
      json.put("role", obj.getRole().name());
    }
    if (obj.getUseReplicas() != null) {
      json.put("useReplicas", obj.getUseReplicas().name());
    }
    json.put("maxNestedArrays", obj.getMaxNestedArrays());
    if (obj.getTracingPolicy() != null) {
      json.put("tracingPolicy", obj.getTracingPolicy().name());
    }
    json.put("poolCleanerInterval", obj.getPoolCleanerInterval());
    json.put("maxPoolSize", obj.getMaxPoolSize());
    json.put("maxPoolWaiting", obj.getMaxPoolWaiting());
    json.put("poolRecycleTimeout", obj.getPoolRecycleTimeout());
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    json.put("protocolNegotiation", obj.isProtocolNegotiation());
    if (obj.getPreferredProtocolVersion() != null) {
      json.put("preferredProtocolVersion", obj.getPreferredProtocolVersion().name());
    }
    if (obj.getPoolName() != null) {
      json.put("poolName", obj.getPoolName());
    }
    if (obj.getMetricsName() != null) {
      json.put("metricsName", obj.getMetricsName());
    }
    json.put("hashSlotCacheTTL", obj.getHashSlotCacheTTL());
    json.put("autoFailover", obj.isAutoFailover());
  }
}
