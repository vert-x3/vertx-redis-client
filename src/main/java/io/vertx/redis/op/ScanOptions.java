/*
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.redis.op;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@DataObject
public class ScanOptions {

  public static final ScanOptions NONE = new ScanOptions();

  private String pattern;
  private Integer count;

  public ScanOptions() {
  }

  public ScanOptions(ScanOptions copy) {
    this.pattern = copy.pattern;
    this.count = copy.count;
  }

  public ScanOptions(JsonObject obj) {
    this.pattern = obj.getString("pattern");
    this.count = obj.getInteger("count");
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    if (count != null) {
      result.put("count", count);
    } else if (pattern != null) {
      result.put("pattern", pattern);
    }
    return result;
  }

  public ScanOptions setCount(int count) {
    this.count = count;
    return this;
  }

  public ScanOptions setMatch(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (pattern != null) {
      result.add("MATCH");
      result.add(pattern);
    }
    if (count != null) {
      result.add("COUNT");
      result.add(count);
    }
    return result;
  }
}
