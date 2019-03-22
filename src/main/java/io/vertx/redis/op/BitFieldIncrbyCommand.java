package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class BitFieldIncrbyCommand {

  private String type;
  private long offset;
  private long increment;

  public BitFieldIncrbyCommand() {
  }

  public BitFieldIncrbyCommand(BitFieldIncrbyCommand obj) {
    type = obj.type;
    offset = obj.offset;
    increment = obj.increment;
  }

  public BitFieldIncrbyCommand(JsonObject obj) {
    type = obj.getString("type");
    offset = obj.getLong("offset");
    increment = obj.getLong("increment");
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  public long getIncrement() {
    return increment;
  }

  public void setIncrement(long increment) {
    this.increment = increment;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("type", type)
      .put("offset", offset)
      .put("increment", increment);
  }
}
