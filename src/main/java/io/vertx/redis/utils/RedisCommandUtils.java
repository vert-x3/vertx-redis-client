package io.vertx.redis.utils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class RedisCommandUtils {
  /**
   * A helper method to package method parameters into JsonArray payload.
   *
   * Null parameters are ignored.
   *
   * @param parameters Call parameters
   * @return JsonArray that can be passed to send()
   */
  @SuppressWarnings("unchecked")
  public static JsonArray toPayload(Object... parameters) {
    JsonArray result = new JsonArray();
    for (Object param: parameters) {
      // unwrap
      if (param instanceof JsonArray) {
        param = ((JsonArray) param).getList();
      }
      // unwrap
      if (param instanceof JsonObject) {
        param = ((JsonObject) param).getMap();
      }

      if (param instanceof Collection) {
        ((Collection) param).stream().filter(el -> el != null).forEach(result::add);
      } else if (param instanceof Map) {
        for (Map.Entry<?, ?> pair : ((Map<?, ?>) param).entrySet()) {
          result.add(pair.getKey());
          result.add(pair.getValue());
        }
      } else if (param instanceof Stream) {
        ((Stream) param).forEach(e -> {
          if (e instanceof Object[]) {
            for (Object item : (Object[]) e) {
              result.add(item);
            }
          } else {
            result.add(e);
          }
        });
      } else if (param != null) {
        result.add(param);
      }
    }
    return result;
  }
}
