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
public class KillFilter {

  private String addr;
  private String clientid;
  private Type type;
  private Boolean skipme;

  public KillFilter() {
  }

  public KillFilter(KillFilter copy) {
    this.addr = copy.addr;
    this.clientid = copy.clientid;
    this.type = copy.type;
    this.skipme = copy.skipme;
  }

  public KillFilter(JsonObject json) {
    String value = json.getString("addr");
    if (value != null) {
      addr = value;
    }
    value = json.getString("clientid");
    if (value != null) {
      clientid = value;
    }
    value = json.getString("type");
    if (value != null) {
      type = Type.valueOf(value.toUpperCase());
    }
    Boolean bval = json.getBoolean("skipme");
    if (bval != null) {
      skipme = bval;
    }
  }

  /**
   * Set ADDR filter
   *
   * @param addr Address filter of form addr:port
   * @return This
   */
  public KillFilter setAddr(String addr) {
    this.addr = addr;
    return this;
  }

  /**
   * Set ID filter
   *
   * @param clientid Client id as returned by CLIENT LIST command
   * @return This
   */
  public KillFilter setId(String clientid) {
    this.clientid = clientid;
    return this;
  }

  /**
   * Set TYPE filter
   *
   * @param type KillType to include
   * @return This
   */
  public KillFilter setType(Type type) {
    this.type = type;
    return this;
  }

  /**
   * Set SKIPME filter
   *
   * @param skip True if current client should be excluded from KILL
   * @return This
   */
  public KillFilter setSkipme(boolean skip) {
    this.skipme = skip;
    return this;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    if (addr != null) {
      json.put("addr", addr);
    }
    if (clientid != null) {
      json.put("clientid", clientid);
    }
    if (type != null) {
      json.put("type", type.name());
    }
    if (skipme != null) {
      json.put("skipme", skipme);
    }
    return json;
  }

  public JsonArray toJsonArray() {
    JsonArray result = new JsonArray();
    if (addr != null) {
      result.add("ADDR");
      result.add(addr);
    }
    if (clientid != null) {
      result.add("ID");
      result.add(clientid);
    }
    if (type != null) {
      result.add("TYPE");
      result.add(type.name().toLowerCase());
    }
    if (skipme != null) {
      result.add("SKIPME");
      result.add(skipme ? "yes" : "no");
    }
    return result;
  }

  public static enum Type {
    NORMAL,
    SLAVE,
    PUBSUB
  }
}
