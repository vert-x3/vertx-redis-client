package io.vertx.redis.op;

import io.vertx.core.json.JsonArray;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public enum ShutdownOptions {
  NONE,
  SAVE,
  NOSAVE;

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    switch(this) {
      case SAVE:
        result.add("SAVE");
        break;
      case NOSAVE:
        result.add("NOSAVE");
        break;
    }
    return result;
  }
}
