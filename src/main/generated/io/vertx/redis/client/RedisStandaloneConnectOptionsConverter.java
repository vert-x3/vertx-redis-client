package io.vertx.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter and mapper for {@link io.vertx.redis.client.RedisStandaloneConnectOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.client.RedisStandaloneConnectOptions} original class using Vert.x codegen.
 */
public class RedisStandaloneConnectOptionsConverter {

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, RedisStandaloneConnectOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
      }
    }
  }

   static void toJson(RedisStandaloneConnectOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(RedisStandaloneConnectOptions obj, java.util.Map<String, Object> json) {
  }
}
