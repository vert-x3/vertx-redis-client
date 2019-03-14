package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.redis.client.RedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
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
        case "password":
          if (member.getValue() instanceof String) {
            obj.setPassword((String)member.getValue());
          }
          break;
        case "role":
          if (member.getValue() instanceof String) {
            obj.setRole(io.vertx.redis.client.RedisRole.valueOf((String)member.getValue()));
          }
          break;
        case "select":
          if (member.getValue() instanceof Number) {
            obj.setSelect(((Number)member.getValue()).intValue());
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
    if (obj.getMasterName() != null) {
      json.put("masterName", obj.getMasterName());
    }
    json.put("maxNestedArrays", obj.getMaxNestedArrays());
    json.put("maxWaitingHandlers", obj.getMaxWaitingHandlers());
    if (obj.getNetClientOptions() != null) {
      json.put("netClientOptions", obj.getNetClientOptions().toJson());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    if (obj.getRole() != null) {
      json.put("role", obj.getRole().name());
    }
    if (obj.getSelect() != null) {
      json.put("select", obj.getSelect());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType().name());
    }
    if (obj.getUseSlave() != null) {
      json.put("useSlave", obj.getUseSlave().name());
    }
  }
}
