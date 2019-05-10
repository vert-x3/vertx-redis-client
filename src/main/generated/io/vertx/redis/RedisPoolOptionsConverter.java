package io.vertx.redis;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.redis.RedisPoolOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.RedisPoolOptions} original class using Vert.x codegen.
 */
public class RedisPoolOptionsConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisPoolOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "connectionReleaseDelay":
          if (member.getValue() instanceof Number) {
            obj.setConnectionReleaseDelay(((Number)member.getValue()).intValue());
          }
          break;
        case "connectionRetryDelay":
          if (member.getValue() instanceof Number) {
            obj.setConnectionRetryDelay(((Number)member.getValue()).intValue());
          }
          break;
        case "connectionTestTimeout":
          if (member.getValue() instanceof Number) {
            obj.setConnectionTestTimeout(((Number)member.getValue()).intValue());
          }
          break;
        case "maxConnectionRetries":
          if (member.getValue() instanceof Number) {
            obj.setMaxConnectionRetries(((Number)member.getValue()).intValue());
          }
          break;
        case "maxPoolSize":
          if (member.getValue() instanceof Number) {
            obj.setMaxPoolSize(((Number)member.getValue()).intValue());
          }
          break;
        case "maxQueueSize":
          if (member.getValue() instanceof Number) {
            obj.setMaxQueueSize(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(RedisPoolOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(RedisPoolOptions obj, java.util.Map<String, Object> json) {
    json.put("connectionReleaseDelay", obj.getConnectionReleaseDelay());
    json.put("connectionRetryDelay", obj.getConnectionRetryDelay());
    json.put("connectionTestTimeout", obj.getConnectionTestTimeout());
    json.put("maxConnectionRetries", obj.getMaxConnectionRetries());
    json.put("maxPoolSize", obj.getMaxPoolSize());
    json.put("maxQueueSize", obj.getMaxQueueSize());
  }
}
