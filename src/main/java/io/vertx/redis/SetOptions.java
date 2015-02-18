package io.vertx.redis;

import io.vertx.core.json.JsonArray;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class SetOptions {

  private Long ex;
  private Long px;
  private Boolean nx;
  private Boolean xx;

  public SetOptions() {
  }

  public SetOptions setEX(long seconds) {
    if (px != null) {
      throw new IllegalStateException("PX is already set - can't have both");
    }
    ex = seconds;
    return this;
  }

  public SetOptions setPX(long millis) {
    if (ex != null) {
      throw new IllegalStateException("EX is already set - can't have both");
    }
    px = millis;
    return this;
  }

  public SetOptions setNX(boolean unlessExists) {
    if (!unlessExists) {
      nx = null;
    }
    if (xx != null && xx) {
      throw new IllegalStateException("XX is already set - can't have both");
    }

    nx = unlessExists;
    return this;
  }

  public SetOptions setXX(boolean onlyIfExists) {
    if (!onlyIfExists) {
      xx = null;
    }
    if (nx != null && nx) {
      throw new IllegalStateException("NX is already set - can't have both");
    }
    xx = onlyIfExists;
    return this;
  }

  public JsonArray toJson() {
    JsonArray result = new JsonArray();
    if (ex != null) {
      result.add("EX").add(ex);
    }
    if (px != null) {
      result.add("PX").add(px);
    }
    if (nx != null && nx) {
      result.add("NX");
    }
    if (xx != null && xx) {
      result.add("XX");
    }
    return result;
  }
}
