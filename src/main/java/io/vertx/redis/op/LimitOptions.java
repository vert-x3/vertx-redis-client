package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@DataObject
public class LimitOptions {

  public static final LimitOptions NONE = new LimitOptions();

  private Long offset;
  private Long count;

  public LimitOptions() {}

  public LimitOptions(LimitOptions obj) {
    offset = obj.offset;
    count = obj.count;
  }

  public LimitOptions(JsonObject obj) {
    offset = obj.getLong("offset");
    count = obj.getLong("count");
  }

  public LimitOptions setLimit(long offset, long count) {
    this.offset = offset;
    this.count = count;
    return this;
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    if (offset != null) {
      result.put("offset", offset);
      result.put("count", count);
    }
    return result;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (offset != null) {
      result.add("LIMIT");
      result.add(offset);
      result.add(count);
    }
    return result;
  }
}
