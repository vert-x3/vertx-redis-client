package io.vertx.redis.utils;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.stream.Stream;

public class RedisCommandUtils {
  /**
   * A helper method to package method parameters into JsonArray payload.
   * <p>
   * Null parameters are ignored.
   *
   * @param parameters Call parameters
   * @return JsonArray that can be passed to send()
   */
  @SuppressWarnings("unchecked")
  public static List<?> toPayload(Object... parameters) {
    List<Object> result = new ArrayList<>(parameters.length);

    for (Object param : parameters) {
      // unwrap
      if (param instanceof JsonArray) {
        param = ((JsonArray) param).getList();
      }
      // unwrap
      if (param instanceof JsonObject) {
        param = ((JsonObject) param).getMap();
      }

      if (param instanceof Collection) {
        ((Collection) param).stream().filter(Objects::nonNull).forEach(result::add);
      } else if (param instanceof Map) {
        for (Map.Entry<?, ?> pair : ((Map<?, ?>) param).entrySet()) {
          result.add(pair.getKey());
          result.add(pair.getValue());
        }
      } else if (param instanceof Stream) {
        ((Stream) param).forEach(e -> {
          if (e instanceof Object[]) {
            Collections.addAll(result, (Object[]) e);
          } else {
            result.add(e);
          }
        });
      } else if (param instanceof Buffer) {
        result.add(((Buffer) param).getBytes());
      } else if (param != null) {
        result.add(param);
      }
    }
    return result;
  }
}
