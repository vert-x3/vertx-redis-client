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
import io.vertx.ext.sockjs.SockJSServerOptions;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SockJSServerOptionsImpl implements SockJSServerOptions {

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


  SockJSServerOptionsImpl() {
    this.sessionTimeout = DEFAULT_SESSIONTIMEOUT;
    this.insertJSESSIONID = DEFAULT_INSERTSESSIONID;
    this.heartbeatPeriod = DEFAULT_HEARTBEATPERIOD;
    this.maxBytesStreaming = DEFAULT_MAXBYTESSTREAMING;
    this.prefix = DEFAULT_PREFIX;
    this.libraryURL = DEFAULT_LIBRARYURL;
  }

  SockJSServerOptionsImpl(JsonObject json) {
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

  @Override
  public long getSessionTimeout() {
    return sessionTimeout;
  }

  @Override
  public SockJSServerOptions setSessionTimeout(long sessionTimeout) {
    if (sessionTimeout < 1) {
      throw new IllegalArgumentException("sessionTimeout must be > 0");
    }
    this.sessionTimeout = sessionTimeout;
    return this;
  }

  @Override
  public boolean isInsertJSESSIONID() {
    return insertJSESSIONID;
  }

  @Override
  public SockJSServerOptions setInsertJSESSIONID(boolean insertJSESSIONID) {
    this.insertJSESSIONID = insertJSESSIONID;
    return this;
  }

  @Override
  public long getHeartbeatPeriod() {
    return heartbeatPeriod;
  }

  @Override
  public SockJSServerOptions setHeartbeatPeriod(long heartbeatPeriod) {
    if (heartbeatPeriod < 1) {
      throw new IllegalArgumentException("heartbeatPeriod must be > 0");
    }
    this.heartbeatPeriod = heartbeatPeriod;
    return this;
  }

  @Override
  public int getMaxBytesStreaming() {
    return maxBytesStreaming;
  }

  @Override
  public SockJSServerOptions setMaxBytesStreaming(int maxBytesStreaming) {
    if (maxBytesStreaming < 1) {
      throw new IllegalArgumentException("maxBytesStreaming must be > 0");
    }
    this.maxBytesStreaming = maxBytesStreaming;
    return this;
  }

  @Override
  public String getPrefix() {
    return prefix;
  }

  @Override
  public SockJSServerOptions setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  @Override
  public String getLibraryURL() {
    return libraryURL;
  }

  @Override
  public SockJSServerOptions setLibraryURL(String libraryURL) {
    this.libraryURL = libraryURL;
    return this;
  }

  @Override
  public SockJSServerOptions addDisabledTransport(String subProtocol) {
    disabledTransports.add(subProtocol);
    return this;
  }

  @Override
  public Set<String> getDisabledTransports() {
    return disabledTransports;
  }
}
