/*
 * Copyright 2014 Red Hat, Inc.
 *
 *   Red Hat licenses this file to you under the Apache License, version 2.0
 *   (the "License"); you may not use this file except in compliance with the
 *   License.  You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 */

package io.vertx.ext.sockjs.impl;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sockjs.BridgeOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class BridgeOptionsImpl implements BridgeOptions {

  private static final long DEFAULT_AUTHTIMEOUT = 5 * 60 * 1000;
  private static final String DEFAULT_AUTHADDRESS = "vertx.basicauthmanager.authorise";
  private static final int DEFAULT_MAXADDRESSLENGTH = 200;
  private static final int DEFAULT_MAXHANDLERSPERSOCKET = 1000;
  private static final long DEFAULT_PINGTIMEOUT = 10 * 1000;
  private static final long DEFAULT_REPLYTIMEOUT = 30 * 1000;

  private long authTimeout;
  private String authAddress;
  private int maxAddressLength;
  private int maxHandlersPerSocket;
  private long pingTimeout;
  private long replyTimeout;

  private List<JsonObject> inboundPermitted = new ArrayList<>();
  private List<JsonObject> outboundPermitted = new ArrayList<>();

  BridgeOptionsImpl() {
    this.authTimeout = DEFAULT_AUTHTIMEOUT;
    this.authAddress = DEFAULT_AUTHADDRESS;
    this.maxAddressLength = DEFAULT_MAXADDRESSLENGTH;
    this.maxHandlersPerSocket = DEFAULT_MAXHANDLERSPERSOCKET;
    this.pingTimeout = DEFAULT_PINGTIMEOUT;
    this.replyTimeout = DEFAULT_REPLYTIMEOUT;
  }

  BridgeOptionsImpl(JsonObject json) {
    this.authTimeout = json.getLong("authTimeout", DEFAULT_AUTHTIMEOUT);
    this.authAddress = json.getString("authAddress", DEFAULT_AUTHADDRESS);
    this.maxAddressLength = json.getInteger("maxAddressLength", DEFAULT_MAXADDRESSLENGTH);
    this.maxHandlersPerSocket = json.getInteger("maxHandlersPerSocket", DEFAULT_MAXHANDLERSPERSOCKET);
    this.pingTimeout = json.getLong("pingTimeout", DEFAULT_PINGTIMEOUT);
    this.replyTimeout = json.getLong("replyTimeout", DEFAULT_REPLYTIMEOUT);
    //TODO simplify common code
    JsonArray arr = json.getArray("inboundPermitteds");
    if (arr != null) {
      for (Object obj: arr) {
        if (obj instanceof JsonObject) {
          JsonObject jobj = (JsonObject)obj;
          inboundPermitted.add(jobj);
        } else {
          throw new IllegalArgumentException("Invalid type " + obj.getClass() + " in inboundPermitteds array");
        }
      }
    }
    arr = json.getArray("outboundPermitteds");
    if (arr != null) {
      for (Object obj: arr) {
        if (obj instanceof JsonObject) {
          JsonObject jobj = (JsonObject)obj;
          outboundPermitted.add(jobj);
        } else {
          throw new IllegalArgumentException("Invalid type " + obj.getClass() + " in outboundPermitteds array");
        }
      }
    }
  }

  @Override
  public long getAuthTimeout() {
    return authTimeout;
  }

  @Override
  public BridgeOptions setAuthTimeout(long authTimeout) {
    if (authTimeout < 1) {
      throw new IllegalArgumentException("authTimeout must be > 0");
    }
    this.authTimeout = authTimeout;
    return this;
  }

  @Override
  public String getAuthAddress() {
    return authAddress;
  }

  @Override
  public BridgeOptions setAuthAddress(String authAddress) {
    this.authAddress = authAddress;
    return this;
  }

  @Override
  public int getMaxAddressLength() {
    return maxAddressLength;
  }

  @Override
  public BridgeOptions setMaxAddressLength(int maxAddressLength) {
    if (maxAddressLength < 1) {
      throw new IllegalArgumentException("maxAddressLength must be > 0");
    }
    this.maxAddressLength = maxAddressLength;
    return this;
  }

  @Override
  public int getMaxHandlersPerSocket() {
    return maxHandlersPerSocket;
  }

  @Override
  public BridgeOptions setMaxHandlersPerSocket(int maxHandlersPerSocket) {
    if (maxHandlersPerSocket < 1) {
      throw new IllegalArgumentException("maxHandlersPerSocket must be > 0");
    }
    this.maxHandlersPerSocket = maxHandlersPerSocket;
    return this;
  }

  @Override
  public long getPingTimeout() {
    return pingTimeout;
  }

  @Override
  public BridgeOptions setPingTimeout(long pingTimeout) {
    if (pingTimeout < 1) {
      throw new IllegalArgumentException("pingTimeout must be > 0");
    }
    this.pingTimeout = pingTimeout;
    return this;
  }

  @Override
  public long getReplyTimeout() {
    return replyTimeout;
  }

  @Override
  public BridgeOptions setReplyTimeout(long replyTimeout) {
    if (replyTimeout < 1) {
      throw new IllegalArgumentException("replyTimeout must be > 0");
    }
    this.replyTimeout = replyTimeout;
    return this;
  }

  @Override
  public BridgeOptions addInboundPermitted(JsonObject json) {
    inboundPermitted.add(json);
    return this;
  }

  @Override
  public List<JsonObject> getInboundPermitteds() {
    return inboundPermitted;
  }

  @Override
  public BridgeOptions addOutboundPermitted(JsonObject json) {
    outboundPermitted.add(json);
    return this;
  }

  @Override
  public List<JsonObject> getOutboundPermitteds() {
    return outboundPermitted;
  }
}
