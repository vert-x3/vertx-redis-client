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
public class RangeLimitOptions extends LimitOptions {

  public static final RangeLimitOptions NONE = new RangeLimitOptions();

  private Boolean withscores;

  public RangeLimitOptions() {
  }

  public RangeLimitOptions(RangeLimitOptions obj) {
    super(obj);
    withscores = obj.withscores;
  }

  public RangeLimitOptions(JsonObject obj) {
    super(obj);
    withscores = obj.getBoolean("withscores");
  }

  public RangeLimitOptions useWithScores() {
    withscores = true;
    return this;
  }

  public JsonObject toJson() {
    JsonObject json = super.toJson();
    if (withscores != null && withscores) {
      json.put("withscores", withscores);
    }
    return json;
  }

  public JsonArray toJsonArray() {
    JsonArray json = super.toJsonArray();
    if (withscores != null && withscores) {
      json.add("WITHSCORES");
    }
    return json;
  }
}
