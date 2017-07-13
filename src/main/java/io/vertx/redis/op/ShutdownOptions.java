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

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonArray;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@VertxGen
public enum ShutdownOptions {
  NONE,
  SAVE,
  NOSAVE;

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    switch (this) {
      case SAVE:
        result.add("SAVE");
        break;
      case NOSAVE:
        result.add("NOSAVE");
        break;
    }
    return result;
  }
}
