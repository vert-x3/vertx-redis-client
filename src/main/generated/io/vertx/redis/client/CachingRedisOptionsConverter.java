package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.redis.client.CachingRedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.CachingRedisOptions} original class using Vert.x codegen.
 */
public class CachingRedisOptionsConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, CachingRedisOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "enabled":
          if (member.getValue() instanceof Boolean) {
            obj.setEnabled((Boolean)member.getValue());
          }
          break;
        case "maxCacheSize":
          if (member.getValue() instanceof Number) {
            obj.setMaxCacheSize(((Number)member.getValue()).intValue());
          }
          break;
        case "maxAge":
          if (member.getValue() instanceof Number) {
            obj.setMaxAge(((Number)member.getValue()).longValue());
          }
          break;
        case "maxAgeUnit":
          if (member.getValue() instanceof String) {
            obj.setMaxAgeUnit(java.util.concurrent.TimeUnit.valueOf((String)member.getValue()));
          }
          break;
        case "mode":
          if (member.getValue() instanceof String) {
            obj.setMode(io.vertx.redis.client.ClientSideCacheMode.valueOf((String)member.getValue()));
          }
          break;
        case "prefixes":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setPrefixes(list);
          }
          break;
        case "prefix":
          if (member.getValue() instanceof String) {
            obj.setPrefix((String)member.getValue());
          }
          break;
        case "prefixs":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addPrefix((String)item);
            });
          }
          break;
      }
    }
  }

  public static void toJson(CachingRedisOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(CachingRedisOptions obj, java.util.Map<String, Object> json) {
    json.put("enabled", obj.getEnabled());
    json.put("maxCacheSize", obj.getMaxCacheSize());
    json.put("maxAge", obj.getMaxAge());
    if (obj.getMaxAgeUnit() != null) {
      json.put("maxAgeUnit", obj.getMaxAgeUnit().name());
    }
    if (obj.getMode() != null) {
      json.put("mode", obj.getMode().name());
    }
    if (obj.getPrefixes() != null) {
      JsonArray array = new JsonArray();
      obj.getPrefixes().forEach(item -> array.add(item));
      json.put("prefixes", array);
    }
  }
}
