package io.vertx.redis.utils;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class JsonUtils {
  /**
   * Converts the array response of sentinel response to the map.
   *
   * @param array
   * @return
   */
  public static JsonObject convertToJsonObject(JsonArray array) {
    JsonObject object = new JsonObject();
    for (int fieldCount = 0; fieldCount < array.size(); fieldCount += 2) {
      object.put(array.getString(fieldCount), array.getString(fieldCount + 1));
    }
    return object;
  }
}
