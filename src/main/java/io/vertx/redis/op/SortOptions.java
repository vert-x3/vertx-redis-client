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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@DataObject
public class SortOptions {

  public static final SortOptions NONE = new SortOptions();

  private Long offset;
  private Long count;
  private String by;
  private List<String> getList = new LinkedList<>();
  private Boolean descending;
  private Boolean alpha;
  private String store;

  public SortOptions() {
  }

  public SortOptions(SortOptions obj) {
    offset = obj.offset;
    count = obj.count;
    by = obj.by;
    getList.addAll(obj.getList);
    descending = obj.descending;
    alpha = obj.alpha;
    store = obj.store;
  }

  public SortOptions(JsonObject obj) {
    offset = obj.getLong("offset");
    count = obj.getLong("count");
    by = obj.getString("by");
    descending = obj.getBoolean("descending");
    alpha = obj.getBoolean("alpha");
    store = obj.getString("store");
    JsonArray list = obj.getJsonArray("get");
    if (list != null) {
      getList.addAll(list.getList());
    }
  }

  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    if (offset != null) {
      result.put("offset", offset);
      result.put("count", count);
    }
    if (by != null) {
      result.put("by", by);
    }
    if (getList.size() > 0) {
      result.put("get", new JsonArray(new ArrayList(getList)));
    }
    if (descending != null) {
      result.put("descending", descending);
    }
    if (alpha != null) {
      result.put("alpha", alpha);
    }
    if (store != null) {
      result.put("store", store);
    }
    return result;
  }

  public SortOptions setLimit(long offset, long count) {
    this.offset = offset;
    this.count = count;
    return this;
  }

  public SortOptions setBy(String pattern) {
    this.by = pattern;
    return this;
  }

  public SortOptions addGet(String pattern) {
    this.getList.add(pattern);
    return this;
  }

  public SortOptions useDescending() {
    this.descending = true;
    return this;
  }

  public SortOptions useAlpha() {
    this.alpha = true;
    return this;
  }

  public SortOptions setStore(String store) {
    this.store = store;
    return this;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (by != null) {
      result.add("BY");
      result.add(by);
    }
    if (offset != null) {
      result.add("LIMIT");
      result.add(offset);
      result.add(count);
    }
    for (String pattern : getList) {
      result.add("GET");
      result.add(pattern);
    }
    if (descending != null && descending) {
      result.add("DESC");
    }
    if (alpha != null && alpha) {
      result.add("ALPHA");
    }
    if (store != null) {
      result.add("STORE");
      result.add(store);
    }
    return result;
  }
}
