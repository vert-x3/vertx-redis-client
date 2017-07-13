/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.redis;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.redis.RedisOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.RedisOptions} original class using Vert.x codegen.
 */
public class RedisOptionsConverter {

  public static void fromJson(JsonObject json, RedisOptions obj) {
    if (json.getValue("address") instanceof String) {
      obj.setAddress((String)json.getValue("address"));
    }
    if (json.getValue("auth") instanceof String) {
      obj.setAuth((String)json.getValue("auth"));
    }
    if (json.getValue("binary") instanceof Boolean) {
      obj.setBinary((Boolean)json.getValue("binary"));
    }
    if (json.getValue("encoding") instanceof String) {
      obj.setEncoding((String)json.getValue("encoding"));
    }
    if (json.getValue("host") instanceof String) {
      obj.setHost((String)json.getValue("host"));
    }
    if (json.getValue("masterName") instanceof String) {
      obj.setMasterName((String)json.getValue("masterName"));
    }
    if (json.getValue("port") instanceof Number) {
      obj.setPort(((Number)json.getValue("port")).intValue());
    }
    if (json.getValue("select") instanceof Number) {
      obj.setSelect(((Number)json.getValue("select")).intValue());
    }
    if (json.getValue("sentinels") instanceof JsonArray) {
      json.getJsonArray("sentinels").forEach(item -> {
        if (item instanceof String)
          obj.addSentinel((String)item);
      });
    }
  }

  public static void toJson(RedisOptions obj, JsonObject json) {
    if (obj.getAddress() != null) {
      json.put("address", obj.getAddress());
    }
    if (obj.getAuth() != null) {
      json.put("auth", obj.getAuth());
    }
    json.put("binary", obj.isBinary());
    if (obj.getEncoding() != null) {
      json.put("encoding", obj.getEncoding());
    }
    if (obj.getHost() != null) {
      json.put("host", obj.getHost());
    }
    if (obj.getMasterName() != null) {
      json.put("masterName", obj.getMasterName());
    }
    json.put("port", obj.getPort());
    if (obj.getSelect() != null) {
      json.put("select", obj.getSelect());
    }
    if (obj.getSentinels() != null) {
      JsonArray array = new JsonArray();
      obj.getSentinels().forEach(item -> array.add(item));
      json.put("sentinels", array);
    }
  }
}