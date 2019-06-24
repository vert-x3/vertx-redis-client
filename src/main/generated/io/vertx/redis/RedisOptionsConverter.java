package io.vertx.redis;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.redis.RedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter implements JsonCodec<RedisOptions, JsonObject> {

  public static final RedisOptionsConverter INSTANCE = new RedisOptionsConverter();

  @Override public JsonObject encode(RedisOptions value) { return (value != null) ? value.toJson() : null; }

  @Override public RedisOptions decode(JsonObject value) { return (value != null) ? new RedisOptions(value) : null; }

  @Override public Class<RedisOptions> getTargetClass() { return RedisOptions.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "address":
          if (member.getValue() instanceof String) {
            obj.setAddress((String)member.getValue());
          }
          break;
        case "auth":
          if (member.getValue() instanceof String) {
            obj.setAuth((String)member.getValue());
          }
          break;
        case "binary":
          if (member.getValue() instanceof Boolean) {
            obj.setBinary((Boolean)member.getValue());
          }
          break;
        case "domainSocket":
          if (member.getValue() instanceof Boolean) {
            obj.setDomainSocket((Boolean)member.getValue());
          }
          break;
        case "domainSocketAddress":
          if (member.getValue() instanceof String) {
            obj.setDomainSocketAddress((String)member.getValue());
          }
          break;
        case "encoding":
          if (member.getValue() instanceof String) {
            obj.setEncoding((String)member.getValue());
          }
          break;
        case "host":
          if (member.getValue() instanceof String) {
            obj.setHost((String)member.getValue());
          }
          break;
        case "masterName":
          if (member.getValue() instanceof String) {
            obj.setMasterName((String)member.getValue());
          }
          break;
        case "port":
          if (member.getValue() instanceof Number) {
            obj.setPort(((Number)member.getValue()).intValue());
          }
          break;
        case "select":
          if (member.getValue() instanceof Number) {
            obj.setSelect(((Number)member.getValue()).intValue());
          }
          break;
        case "sentinels":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setSentinels(list);
          }
          break;
      }
    }
  }

  public static void toJson(RedisOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(RedisOptions obj, java.util.Map<String, Object> json) {
    if (obj.getAddress() != null) {
      json.put("address", obj.getAddress());
    }
    if (obj.getAuth() != null) {
      json.put("auth", obj.getAuth());
    }
    json.put("binary", obj.isBinary());
    json.put("domainSocket", obj.isDomainSocket());
    if (obj.getDomainSocketAddress() != null) {
      json.put("domainSocketAddress", obj.getDomainSocketAddress());
    }
    if (obj.getEncoding() != null) {
      json.put("encoding", obj.getEncoding());
    }
    if (obj.getHost() != null) {
      json.put("host", obj.getHost());
    }
    if (obj.getMasterName() != null) {
      json.put("masterName", obj.getMasterName());
    }
    json.put("port", obj.getPort());
    if (obj.getSelect() != null) {
      json.put("select", obj.getSelect());
    }
    if (obj.getSentinels() != null) {
      JsonArray array = new JsonArray();
      obj.getSentinels().forEach(item -> array.add(item));
      json.put("sentinels", array);
    }
  }
}
