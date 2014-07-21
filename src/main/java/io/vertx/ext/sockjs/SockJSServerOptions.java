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

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Options
public class SockJSServerOptions {

  private static final long DEFAULT_SESSIONTIMEOUT = 5l * 1000;
  private static final boolean DEFAULT_INSERTSESSIONID = true;
  private static final long DEFAULT_HEARTBEATPERIOD = 25l * 1000;
  private static final int DEFAULT_MAXBYTESSTREAMING = 128 * 1024;
  private static final String DEFAULT_PREFIX = "/";
  private static final String DEFAULT_LIBRARYURL = "http://cdn.sockjs.org/sockjs-0.3.4.min.js";

  private long sessionTimeout = 5l * 1000;
  private boolean insertJSESSIONID = true;
  private long heartbeatPeriod = 25l * 1000;
  private int maxBytesStreaming = 128 * 1024;
  private String prefix = "/";
  private String libraryURL = "http://cdn.sockjs.org/sockjs-0.3.4.min.js";
  private Set<String> disabledTransports = new HashSet<>();

  public long getSessionTimeout() {
    return sessionTimeout;
  }

  public SockJSServerOptions() {
    this.sessionTimeout = DEFAULT_SESSIONTIMEOUT;
    this.insertJSESSIONID = DEFAULT_INSERTSESSIONID;
    this.heartbeatPeriod = DEFAULT_HEARTBEATPERIOD;
    this.maxBytesStreaming = DEFAULT_MAXBYTESSTREAMING;
    this.prefix = DEFAULT_PREFIX;
    this.libraryURL = DEFAULT_LIBRARYURL;
  }

  public SockJSServerOptions(JsonObject json) {
    this.sessionTimeout = json.getLong("sessionTimeout", DEFAULT_SESSIONTIMEOUT);
    this.insertJSESSIONID = json.getBoolean("insertJSESSIONID", DEFAULT_INSERTSESSIONID);
    this.heartbeatPeriod = json.getLong("heartbeatPeriod", DEFAULT_HEARTBEATPERIOD);
    this.maxBytesStreaming = json.getInteger("maxBytesStreaming", DEFAULT_MAXBYTESSTREAMING);
    this.prefix = json.getString("prefix", DEFAULT_PREFIX);
    this.libraryURL = json.getString("libraryURL", DEFAULT_LIBRARYURL);
    JsonArray arr = json.getArray("disabledTransports");
    if (arr != null) {
      for (Object str : arr) {
        if (str instanceof String) {
          String sstr = (String) str;
          disabledTransports.add(sstr);
        } else {
          throw new IllegalArgumentException("Invalid type " + str.getClass() + " in disabledTransports array");
        }
      }
    }
  }

  public SockJSServerOptions setSessionTimeout(long sessionTimeout) {
    if (sessionTimeout < 1) {
      throw new IllegalArgumentException("sessionTimeout must be > 0");
    }
    this.sessionTimeout = sessionTimeout;
    return this;
  }

  public boolean isInsertJSESSIONID() {
    return insertJSESSIONID;
  }

  public SockJSServerOptions setInsertJSESSIONID(boolean insertJSESSIONID) {
    this.insertJSESSIONID = insertJSESSIONID;
    return this;
  }

  public long getHeartbeatPeriod() {
    return heartbeatPeriod;
  }

  public SockJSServerOptions setHeartbeatPeriod(long heartbeatPeriod) {
    if (heartbeatPeriod < 1) {
      throw new IllegalArgumentException("heartbeatPeriod must be > 0");
    }
    this.heartbeatPeriod = heartbeatPeriod;
    return this;
  }

  public int getMaxBytesStreaming() {
    return maxBytesStreaming;
  }

  public SockJSServerOptions setMaxBytesStreaming(int maxBytesStreaming) {
    if (maxBytesStreaming < 1) {
      throw new IllegalArgumentException("maxBytesStreaming must be > 0");
    }
    this.maxBytesStreaming = maxBytesStreaming;
    return this;
  }

  public String getPrefix() {
    return prefix;
  }

  public SockJSServerOptions setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public String getLibraryURL() {
    return libraryURL;
  }

  public SockJSServerOptions setLibraryURL(String libraryURL) {
    this.libraryURL = libraryURL;
    return this;
  }

  public SockJSServerOptions addDisabledTransport(String subProtocol) {
    disabledTransports.add(subProtocol);
    return this;
  }

  public Set<String> getDisabledTransports() {
    return disabledTransports;
  }
}
