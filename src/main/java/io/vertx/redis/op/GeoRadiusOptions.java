/**
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
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@DataObject
public class GeoRadiusOptions {

  private boolean withcoord;
  private boolean withdist;
  private boolean withhash;
  private Long count;

  public GeoRadiusOptions() {}

  public GeoRadiusOptions(GeoRadiusOptions obj) {
    withcoord = obj.withcoord;
    withdist = obj.withdist;
    withhash = obj.withhash;
    count = obj.count;
  }

  public GeoRadiusOptions(JsonObject obj) {
    withcoord = obj.getBoolean("withcoord", Boolean.FALSE);
    withdist = obj.getBoolean("withdist", Boolean.FALSE);
    withhash = obj.getBoolean("withhash", Boolean.FALSE);
    count = obj.getLong("count");
  }

  public GeoRadiusOptions setWithCoord(boolean bool) {
    this.withcoord = bool;
    return this;
  }

  public GeoRadiusOptions setWithDist(boolean bool) {
    this.withdist = bool;
    return this;
  }

  public GeoRadiusOptions setWithHash(boolean bool) {
    this.withhash = bool;
    return this;
  }

  public GeoRadiusOptions setCount(Long value) {
    this.count = value;
    return this;
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();

    result
        .put("withcoord", withcoord)
        .put("withdist", withdist)
        .put("withhash", withhash);

    if (count != null) {
      result.put("count", count);
    }

    return result;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();

    if (withcoord) {
      result.add("WITHCOORD");
    }

    if (withdist) {
      result.add("WITHDIST");
    }

    if (withhash) {
      result.add("WITHHASH");
    }

    if (count != null) {
      result.add("COUNT");
      result.add(count);
    }

    return result;
  }

}
