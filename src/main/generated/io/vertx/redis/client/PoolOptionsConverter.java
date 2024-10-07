package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.redis.client.PoolOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.PoolOptions} original class using Vert.x codegen.
 */
public class PoolOptionsConverter {

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, PoolOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "cleanerInterval":
          if (member.getValue() instanceof Number) {
            obj.setCleanerInterval(((Number)member.getValue()).intValue());
          }
          break;
        case "maxSize":
          if (member.getValue() instanceof Number) {
            obj.setMaxSize(((Number)member.getValue()).intValue());
          }
          break;
        case "maxWaiting":
          if (member.getValue() instanceof Number) {
            obj.setMaxWaiting(((Number)member.getValue()).intValue());
          }
          break;
        case "recycleTimeout":
          if (member.getValue() instanceof Number) {
            obj.setRecycleTimeout(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

   static void toJson(PoolOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(PoolOptions obj, java.util.Map<String, Object> json) {
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    json.put("cleanerInterval", obj.getCleanerInterval());
    json.put("maxSize", obj.getMaxSize());
    json.put("maxWaiting", obj.getMaxWaiting());
    json.put("recycleTimeout", obj.getRecycleTimeout());
  }
}
