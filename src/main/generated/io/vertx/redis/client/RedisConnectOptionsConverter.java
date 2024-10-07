package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisConnectOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisConnectOptions} original class using Vert.x codegen.
 */
public class RedisConnectOptionsConverter {

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisConnectOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "maxNestedArrays":
          if (member.getValue() instanceof Number) {
            obj.setMaxNestedArrays(((Number)member.getValue()).intValue());
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
        case "user":
          if (member.getValue() instanceof String) {
            obj.setUser((String)member.getValue());
          }
          break;
        case "password":
          if (member.getValue() instanceof String) {
            obj.setPassword((String)member.getValue());
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
      }
    }
  }

   static void toJson(RedisConnectOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(RedisConnectOptions obj, java.util.Map<String, Object> json) {
    json.put("maxNestedArrays", obj.getMaxNestedArrays());
    json.put("protocolNegotiation", obj.isProtocolNegotiation());
    if (obj.getPreferredProtocolVersion() != null) {
      json.put("preferredProtocolVersion", obj.getPreferredProtocolVersion().name());
    }
    if (obj.getUser() != null) {
      json.put("user", obj.getUser());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    if (obj.getEndpoints() != null) {
      JsonArray array = new JsonArray();
      obj.getEndpoints().forEach(item -> array.add(item));
      json.put("endpoints", array);
    }
    json.put("maxWaitingHandlers", obj.getMaxWaitingHandlers());
  }
}
