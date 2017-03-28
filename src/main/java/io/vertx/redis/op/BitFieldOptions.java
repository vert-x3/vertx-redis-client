package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@DataObject
public class BitFieldOptions {

  private BitFieldGetCommand get;
  private BitFieldSetCommand set;
  private BitFieldIncrbyCommand incrby;

  public BitFieldOptions() {
  }

  public BitFieldOptions(BitFieldOptions obj) {
    get = obj.get;
    set = obj.set;
    incrby = obj.incrby;
  }

  public BitFieldOptions(JsonObject obj) {
    if (obj.containsKey("get")) {
      get = new BitFieldGetCommand(obj.getJsonObject("get"));
    }
    if (obj.containsKey("set")) {
      set = new BitFieldSetCommand(obj.getJsonObject("set"));
    }
    if (obj.containsKey("incrby")) {
      incrby = new BitFieldIncrbyCommand(obj.getJsonObject("incrby"));
    }
  }

  public BitFieldGetCommand getGet() {
    return get;
  }

  public void setGet(BitFieldGetCommand get) {
    this.get = get;
  }

  public BitFieldSetCommand getSet() {
    return set;
  }

  public void setSet(BitFieldSetCommand set) {
    this.set = set;
  }

  public BitFieldIncrbyCommand getIncrby() {
    return incrby;
  }

  public void setIncrby(BitFieldIncrbyCommand incrby) {
    this.incrby = incrby;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("get", get.toJson())
      .put("set", set.toJson())
      .put("incrby", incrby.toJson());
  }

  public JsonArray toJsonArray() {
    final JsonArray array = new JsonArray();

    if (get != null) {
      array.add("GET").add(get.getType()).add(get.getOffset());
    }

    if (set != null) {
      array.add("SET").add(set.getType()).add(set.getOffset()).add(set.getValue());
    }

    if (incrby != null) {
      array.add("INCRBY").add(incrby.getType()).add(incrby.getOffset()).add(incrby.getIncrement());
    }

    return array;
  }
}
