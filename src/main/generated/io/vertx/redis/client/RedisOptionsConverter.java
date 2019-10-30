package io.vertx.redis.client;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "endpoint":
          if (member.getValue() instanceof String) {
            obj.setConnectionString((String)member.getValue());
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
        case "masterName":
          if (member.getValue() instanceof String) {
            obj.setMasterName((String)member.getValue());
          }
          break;
        case "maxNestedArrays":
          if (member.getValue() instanceof Number) {
            obj.setMaxNestedArrays(((Number)member.getValue()).intValue());
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
        case "maxWaitingHandlers":
          if (member.getValue() instanceof Number) {
            obj.setMaxWaitingHandlers(((Number)member.getValue()).intValue());
          }
          break;
        case "netClientOptions":
          if (member.getValue() instanceof JsonObject) {
            obj.setNetClientOptions(new io.vertx.core.net.NetClientOptions((JsonObject)member.getValue()));
          }
          break;
        case "poolCleanerInterval":
          if (member.getValue() instanceof Number) {
            obj.setPoolCleanerInterval(((Number)member.getValue()).intValue());
          }
          break;
        case "poolRecycleTimeout":
          if (member.getValue() instanceof Number) {
            obj.setPoolRecycleTimeout(((Number)member.getValue()).intValue());
          }
          break;
        case "role":
          if (member.getValue() instanceof String) {
            obj.setRole(io.vertx.redis.client.RedisRole.valueOf((String)member.getValue()));
          }
          break;
        case "type":
          if (member.getValue() instanceof String) {
            obj.setType(io.vertx.redis.client.RedisClientType.valueOf((String)member.getValue()));
          }
          break;
        case "useSlave":
          if (member.getValue() instanceof String) {
            obj.setUseSlave(io.vertx.redis.client.RedisSlaves.valueOf((String)member.getValue()));
          }
          break;
      }
    }
  }

  public static void toJson(RedisOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(RedisOptions obj, java.util.Map<String, Object> json) {
    if (obj.getEndpoint() != null) {
      json.put("endpoint", obj.getEndpoint());
    }
    if (obj.getEndpoints() != null) {
      JsonArray array = new JsonArray();
      obj.getEndpoints().forEach(item -> array.add(item));
      json.put("endpoints", array);
    }
    if (obj.getMasterName() != null) {
      json.put("masterName", obj.getMasterName());
    }
    json.put("maxNestedArrays", obj.getMaxNestedArrays());
    json.put("maxPoolSize", obj.getMaxPoolSize());
    json.put("maxPoolWaiting", obj.getMaxPoolWaiting());
    json.put("maxWaitingHandlers", obj.getMaxWaitingHandlers());
    if (obj.getNetClientOptions() != null) {
      json.put("netClientOptions", obj.getNetClientOptions().toJson());
    }
    json.put("poolCleanerInterval", obj.getPoolCleanerInterval());
    json.put("poolRecycleTimeout", obj.getPoolRecycleTimeout());
    if (obj.getRole() != null) {
      json.put("role", obj.getRole().name());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType().name());
    }
    if (obj.getUseSlave() != null) {
      json.put("useSlave", obj.getUseSlave().name());
    }
  }
}
