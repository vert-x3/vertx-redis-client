/**
 * Copyright (c) 2011-2016 The original author or authors
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
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@DataObject
public class GeoMember {

  private Double longitude;
  private Double latitude;
  private String member;

  public GeoMember() {
  }

  public GeoMember(GeoMember obj) {
    longitude = obj.longitude;
    latitude = obj.latitude;
    member = obj.member;
  }

  public GeoMember(JsonObject obj) {
    longitude = obj.getDouble("longitude");
    latitude = obj.getDouble("latitude");
    member = obj.getString("member");
  }

  /**
   * Set Longitude as per EPSG:900913 / EPSG:3785 / OSGEO:41001
   *
   * @param val Valid longitudes are from -180 to 180 degrees
   * @return self
   */
  public GeoMember setLongitude(Double val) {
    this.longitude = val;
    return this;
  }

  /**
   * Set Latitude as per EPSG:900913 / EPSG:3785 / OSGEO:41001
   *
   * @param val Valid latitudes are from -85.05112878 to 85.05112878 degrees.
   * @return self
   */
  public GeoMember setLatitude(Double val) {
    this.latitude = val;
    return this;
  }

  /**
   * Set the member name.
   * @param val the name given
   * @return self
   */
  public GeoMember setMember(String val) {
    this.member = val;
    return this;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("longitude", longitude)
      .put("latitude", latitude)
      .put("member", member);
  }

  public JsonArray toJsonArray() {
    return new JsonArray()
      .add(longitude)
      .add(latitude)
      .add(member);
  }
}
