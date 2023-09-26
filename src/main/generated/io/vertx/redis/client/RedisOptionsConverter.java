package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisOptions obj) {
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
        case "endpoint":
          if (member.getValue() instanceof String) {
            obj.setEndpoint((String)member.getValue());
          }
          break;
        case "connectionStrings":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addConnectionString((String)item);
            });
          }
          break;
        case "connectionString":
          if (member.getValue() instanceof String) {
            obj.setConnectionString((String)member.getValue());
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
        case "poolName":
          if (member.getValue() instanceof String) {
            obj.setPoolName((String)member.getValue());
          }
          break;
        case "cachingOptions":
          break;
        case "cacheEnabled":
          if (member.getValue() instanceof Boolean) {
            obj.setCacheEnabled((Boolean)member.getValue());
          }
          break;
        case "cacheMaxSize":
          if (member.getValue() instanceof Number) {
            obj.setCacheMaxSize(((Number)member.getValue()).intValue());
          }
          break;
        case "cacheMaxAge":
          if (member.getValue() instanceof Number) {
            obj.setCacheMaxAge(((Number)member.getValue()).longValue());
          }
          break;
        case "cacheMaxAgeUnit":
          if (member.getValue() instanceof String) {
            obj.setCacheMaxAgeUnit(java.util.concurrent.TimeUnit.valueOf((String)member.getValue()));
          }
          break;
        case "cacheMode":
          if (member.getValue() instanceof String) {
            obj.setCacheMode(io.vertx.redis.client.ClientSideCacheMode.valueOf((String)member.getValue()));
          }
          break;
        case "cachePrefixes":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setCachePrefixes(list);
          }
          break;
        case "cachePrefix":
          if (member.getValue() instanceof String) {
            obj.setCachePrefix((String)member.getValue());
          }
          break;
        case "cachePrefixs":
          if (member.getValue() instanceof JsonArray) {
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                obj.addCachePrefix((String)item);
            });
          }
          break;
      }
    }
  }

  public static void toJson(RedisOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(RedisOptions obj, java.util.Map<String, Object> json) {
    if (obj.getType() != null) {
      json.put("type", obj.getType().name());
    }
    if (obj.getNetClientOptions() != null) {
      json.put("netClientOptions", obj.getNetClientOptions().toJson());
    }
    if (obj.getEndpoints() != null) {
      JsonArray array = new JsonArray();
      obj.getEndpoints().forEach(item -> array.add(item));
      json.put("endpoints", array);
    }
    if (obj.getEndpoint() != null) {
      json.put("endpoint", obj.getEndpoint());
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
    if (obj.getPoolName() != null) {
      json.put("poolName", obj.getPoolName());
    }
    json.put("cacheEnabled", obj.getCacheEnabled());
    json.put("cacheMaxSize", obj.getCacheMaxSize());
    json.put("cacheMaxAge", obj.getCacheMaxAge());
    if (obj.getCacheMaxAgeUnit() != null) {
      json.put("cacheMaxAgeUnit", obj.getCacheMaxAgeUnit().name());
    }
    if (obj.getCacheMode() != null) {
      json.put("cacheMode", obj.getCacheMode().name());
    }
    if (obj.getCachePrefixes() != null) {
      JsonArray array = new JsonArray();
      obj.getCachePrefixes().forEach(item -> array.add(item));
      json.put("cachePrefixes", array);
    }
  }
}
