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
public class MigrateOptions {

  public static final MigrateOptions NONE = new MigrateOptions();

  Boolean copy;
  Boolean replace;

  public MigrateOptions() {
  }

  public MigrateOptions(MigrateOptions obj) {
    this.copy = obj.copy;
    this.replace = obj.replace;
  }

  public MigrateOptions(JsonObject json) {
    copy = json.getBoolean("copy");
    replace = json.getBoolean("replace");
  }

  public MigrateOptions useCopy() {
    copy = true;
    return this;
  }

  public MigrateOptions useReplace() {
    replace = true;
    return this;
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    if (copy != null) {
      result.put("copy", copy);
    }
    if (replace != null) {
      result.put("replace", replace);
    }
    return result;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (copy != null && copy) {
      result.add("COPY");
    }
    if (replace != null && replace) {
      result.add("REPLACE");
    }
    return result;
  }
}
