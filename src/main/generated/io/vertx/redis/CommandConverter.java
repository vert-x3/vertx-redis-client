package io.vertx.redis;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.redis.Command}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.Command} original class using Vert.x codegen.
 */
public class CommandConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Command obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "command":
          if (member.getValue() instanceof String) {
            obj.setCommand((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Command obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Command obj, java.util.Map<String, Object> json) {
    if (obj.getCommand() != null) {
      json.put("command", obj.getCommand());
    }
  }
}
