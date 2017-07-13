/**
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@DataObject
public class SetOptions {

  public static final SetOptions NONE = new SetOptions();

  private Long ex;
  private Long px;
  private Boolean nx;
  private Boolean xx;

  public SetOptions() {
  }

  public SetOptions(SetOptions obj) {
    this.ex = obj.ex;
    this.px = obj.px;
    this.nx = obj.nx;
    this.xx = obj.xx;
  }

  public SetOptions(JsonObject json) {
    Long lval = json.getLong("ex");
    if (lval != null) {
      ex = lval;
    }
    lval = json.getLong("px");
    if (lval != null) {
      px = lval;
    }
    Boolean bval = json.getBoolean("nx");
    if (bval != null) {
      nx = bval;
    }
    bval = json.getBoolean("xx");
    if (bval != null) {
      xx = bval;
    }
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    if (ex != null) {
      json.put("ex", ex);
    }
    if (px != null) {
      json.put("px", px);
    }
    if (nx != null && nx) {
      json.put("nx", nx);
    }
    if (xx != null && xx) {
      json.put("xx", xx);
    }
    return json;
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

  public JsonArray toJsonArray() {
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
