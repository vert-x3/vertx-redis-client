/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.ext.sockjs;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Options
public class BridgeOptions {

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

  public BridgeOptions() {
    this.authTimeout = DEFAULT_AUTHTIMEOUT;
    this.authAddress = DEFAULT_AUTHADDRESS;
    this.maxAddressLength = DEFAULT_MAXADDRESSLENGTH;
    this.maxHandlersPerSocket = DEFAULT_MAXHANDLERSPERSOCKET;
    this.pingTimeout = DEFAULT_PINGTIMEOUT;
    this.replyTimeout = DEFAULT_REPLYTIMEOUT;
  }

  public BridgeOptions(JsonObject json) {
    this.authTimeout = json.getLong("authTimeout", DEFAULT_AUTHTIMEOUT);
    this.authAddress = json.getString("authAddress", DEFAULT_AUTHADDRESS);
    this.maxAddressLength = json.getInteger("maxAddressLength", DEFAULT_MAXADDRESSLENGTH);
    this.maxHandlersPerSocket = json.getInteger("maxHandlersPerSocket", DEFAULT_MAXHANDLERSPERSOCKET);
    this.pingTimeout = json.getLong("pingTimeout", DEFAULT_PINGTIMEOUT);
    this.replyTimeout = json.getLong("replyTimeout", DEFAULT_REPLYTIMEOUT);
    //TODO simplify common code
    JsonArray arr = json.getArray("inboundPermitted");
    if (arr != null) {
      for (Object obj: arr) {
        if (obj instanceof JsonObject) {
          JsonObject jobj = (JsonObject)obj;
          inboundPermitted.add(jobj);
        } else {
          throw new IllegalArgumentException("Invalid type " + obj.getClass() + " in inboundPermitted array");
        }
      }
    }
    arr = json.getArray("outboundPermitted");
    if (arr != null) {
      for (Object obj: arr) {
        if (obj instanceof JsonObject) {
          JsonObject jobj = (JsonObject)obj;
          outboundPermitted.add(jobj);
        } else {
          throw new IllegalArgumentException("Invalid type " + obj.getClass() + " in outboundPermitted array");
        }
      }
    }
  }

  public long getAuthTimeout() {
    return authTimeout;
  }

  public BridgeOptions setAuthTimeout(long authTimeout) {
    if (authTimeout < 1) {
      throw new IllegalArgumentException("authTimeout must be > 0");
    }
    this.authTimeout = authTimeout;
    return this;
  }

  public String getAuthAddress() {
    return authAddress;
  }

  public BridgeOptions setAuthAddress(String authAddress) {
    this.authAddress = authAddress;
    return this;
  }

  public int getMaxAddressLength() {
    return maxAddressLength;
  }

  public BridgeOptions setMaxAddressLength(int maxAddressLength) {
    if (maxAddressLength < 1) {
      throw new IllegalArgumentException("maxAddressLength must be > 0");
    }
    this.maxAddressLength = maxAddressLength;
    return this;
  }

  public int getMaxHandlersPerSocket() {
    return maxHandlersPerSocket;
  }

  public BridgeOptions setMaxHandlersPerSocket(int maxHandlersPerSocket) {
    if (maxHandlersPerSocket < 1) {
      throw new IllegalArgumentException("maxHandlersPerSocket must be > 0");
    }
    this.maxHandlersPerSocket = maxHandlersPerSocket;
    return this;
  }

  public long getPingTimeout() {
    return pingTimeout;
  }

  public BridgeOptions setPingTimeout(long pingTimeout) {
    if (pingTimeout < 1) {
      throw new IllegalArgumentException("pingTimeout must be > 0");
    }
    this.pingTimeout = pingTimeout;
    return this;
  }

  public long getReplyTimeout() {
    return replyTimeout;
  }

  public BridgeOptions setReplyTimeout(long replyTimeout) {
    if (replyTimeout < 1) {
      throw new IllegalArgumentException("replyTimeout must be > 0");
    }
    this.replyTimeout = replyTimeout;
    return this;
  }

  public BridgeOptions addInboundPermitted(JsonObject json) {
    inboundPermitted.add(json);
    return this;
  }

  public List<JsonObject> getInboundPermitted() {
    return inboundPermitted;
  }

  public BridgeOptions addOutboundPermitted(JsonObject json) {
    outboundPermitted.add(json);
    return this;
  }

  public List<JsonObject> getOutboundPermitted() {
    return outboundPermitted;
  }
}
