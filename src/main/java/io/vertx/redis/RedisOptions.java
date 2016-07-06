/*
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
package io.vertx.redis;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * This object controls the connection setting to the Redis Server. There is no need to specify most of the settings
 * since it has built the following sensible defaults:
 *
 * * `encoding`: `UTF-8`
 * * `host`: `localhost`
 * * `port`: 6379
 * * `tcpKeepAlive`: true
 * * `tcpNoDelay`: true
 * * `binary`: false
 *
 * However there are two extra properties that have no defaults since they are optional:
 *
 * * `auth`
 * * `select`
 *
 * The usage of this two extra properties is to setup required authentication and optionally the selection of the active
 * database at connection time. If you define this extra properties on every connection to Redis server this client
 * will perform the authentication handshake and database selection, however if you don't do this and call {@link io.vertx.redis.RedisClient#auth(String, Handler)}
 * yourself in case of connection failure the client will not be able to perform the correct authentication handshake.
 */
@DataObject
public class RedisOptions {

  private final JsonObject json;

  /**
   * Return the character encoding for Strings, default `UTF-8`.
   * @return character encoding
   */
  public String getEncoding() {
    return json.getString("encoding", "UTF-8");
  }

  /**
   * Set the user defined character encoding, e.g.: `iso-8859-1`.
   * @param encoding the user character encoding
   * @return self
   */
  public RedisOptions setEncoding(String encoding) {
    json.put("encoding", encoding);
    if ("iso-8859-1".equalsIgnoreCase(encoding)) {
      json.put("binary", true);
    }
    return this;
  }

  /**
   * Return if the messages to/from redis are binary, default `false`.
   * @return are messages binary
   */
  public boolean isBinary() {
    return json.getBoolean("binary", false);
  }

  /**
   * Set the user defined character encoding, e.g.: `iso-8859-1`.
   * @param binary use binary messages
   * @return self
   */
  public RedisOptions setBinary(boolean binary) {
    json.put("binary", binary);
    json.put("encoding", "iso-8859-1");
    return this;
  }

  /**
   * Get the default `PUB/SUB` eventbus address prefix, default `io.vertx.redis`.
   * @return eventbus address prefix
   */
  public String getAddress() {
    return json.getString("address", "io.vertx.redis");
  }

  /**
   * Set the eventbus address prefix for `PUB/SUB`.
   * @param address address prefix.
   * @return self
   */
  public RedisOptions setAddress(String address) {
    json.put("address", address);
    return this;
  }

  /**
   * Get the host name for the Redis server, default `localhost`.
   * @return host name.
   */
  public String getHost() {
    return json.getString("host", "localhost");
  }

  /**
   * Set the host name where the Redis server is listening.
   * @param host host name
   * @return self
   */
  public RedisOptions setHost(String host) {
    json.put("host", host);
    return this;
  }

  /**
   * Get the tcp port where the Redis server is listening, default 6379.
   * @return tcp port
   */
  public int getPort() {
    return json.getInteger("port", 6379);
  }

  /**
   * Set the tcp port where the Redis server is listening.
   * @param port tcp port.
   * @return self
   */
  public RedisOptions setPort(int port) {
    json.put("port", port);
    return this;
  }

  /**
   * Get wheather TCP KeepAlive option should be enabled, default true.
   * @return keep alive
   */
  public boolean isTcpKeepAlive() {
    return json.getBoolean("tcpKeepAlive", true);
  }

  /**
   * Set the TCP KeepAlive option
   * @param tcpKeepAlive boolean
   * @return self
   */
  public RedisOptions setTcpKeepAlive(boolean tcpKeepAlive) {
    json.put("tcpKeepAlive", tcpKeepAlive);
    return this;
  }

  /**
   * Get wheather TCP NoDelay should be enabled, default true
   * @return tcp no delay
   */
  public boolean isTcpNoDelay() {
    return json.getBoolean("tcpNoDelay", true);
  }

  /**
   * Set the TCP NoDelay at the socket level.
   * @param tcpNoDelay boolean
   * @return self
   */
  public RedisOptions setTcpNoDelay(boolean tcpNoDelay) {
    json.put("tcpNoDelay", tcpNoDelay);
    return this;
  }

  /**
   * Get the password for authentication at connection time.
   * @return password
   */
  public String getAuth() {
    return json.getString("auth");
  }

  /**
   * Set the password for authentication at connection time.
   *
   * @param auth password
   * @return self
   */
  public RedisOptions setAuth(String auth) {
    json.put("auth", auth);
    return this;
  }

  /**
   * Get the database to select at connection time.
   * @return database id
   */
  public Integer getSelect() {
    return json.getInteger("select");
  }

  /**
   * Set the database to select at connection time.
   * @param select database id
   * @return self
   */
  public RedisOptions setSelect(Integer select) {
    if (select != null) {
      json.put("select", select);
    } else {
      json.remove("select");
    }
    return this;
  }

  public RedisOptions() {
    json = new JsonObject();
  }

  public RedisOptions(JsonObject json) {
    this.json = json.copy();

    // process the binary support
    if (getEncoding().equalsIgnoreCase("iso-8859-1")) {
      setBinary(true);
      return;
    }

    if (isBinary()) {
      setEncoding("iso-8859-1");
    }
  }

  public RedisOptions(RedisOptions options) {
    this(options.toJSON());
  }

  public JsonObject toJSON() {
    return json;
  }
}
