package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
@Deprecated
public class BitFieldSetCommand {

  private String type;
  private long offset;
  private long value;

  public BitFieldSetCommand() {
  }

  public BitFieldSetCommand(BitFieldSetCommand obj) {
    type = obj.type;
    offset = obj.offset;
    value = obj.value;
  }

  public BitFieldSetCommand(JsonObject obj) {
    type = obj.getString("type");
    offset = obj.getLong("offset");
    value = obj.getLong("value");
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

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("type", type)
      .put("offset", offset)
      .put("value", value);
  }
}
