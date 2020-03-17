package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

/**
 * @deprecated Use {@link io.vertx.redis.client.Redis}
 */
@Deprecated
@DataObject
public class BitFieldGetCommand {

  private String type;
  private long offset;

  public BitFieldGetCommand() {
  }

  public BitFieldGetCommand(BitFieldGetCommand obj) {
    type = obj.type;
    offset = obj.offset;
  }

  public BitFieldGetCommand(JsonObject obj) {
    type = obj.getString("type");
    offset = obj.getLong("offset");
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

  public JsonObject toJson() {
    return new JsonObject()
      .put("type", type)
      .put("offset", offset);
  }
}
