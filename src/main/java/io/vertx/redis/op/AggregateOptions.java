package io.vertx.redis.op;

import io.vertx.core.json.JsonArray;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public enum AggregateOptions {
  NONE,
  SUM,
  MIN,
  MAX;

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (this == NONE) {
      return result;
    }
    result.add("AGGREGATE");
    result.add(name());
    return result;
  }
}
