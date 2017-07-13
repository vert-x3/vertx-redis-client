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
import io.vertx.core.net.NetClientOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * This object controls the connection setting to the Redis Server. There is no need to specify most of the settings
 * since it has built the following sensible defaults:
 * <p>
 * * `encoding`: `UTF-8`
 * * `host`: `localhost`
 * * `port`: 6379
 * * `tcpKeepAlive`: true
 * * `tcpNoDelay`: true
 * * `binary`: false
 * <p>
 * However there are two extra properties that have no defaults since they are optional:
 * <p>
 * * `auth`
 * * `select`
 * <p>
 * The usage of this two extra properties is to setup required authentication and optionally the selection of the active
 * database at connection time. If you define this extra properties on every connection to Redis server this client
 * will perform the authentication handshake and database selection, however if you don't do this and call {@link io.vertx.redis.RedisClient#auth(String, Handler)}
 * yourself in case of connection failure the client will not be able to perform the correct authentication handshake.
 */
@DataObject(generateConverter = true)
public class RedisOptions extends NetClientOptions {

  private static final String DEFAULT_ENCODING = "UTF-8";
  private static final String DEFAULT_HOST = "127.0.0.1";
  private static final int DEFAULT_PORT = 6379;
  private static final boolean DEFAULT_BINARY = false;
  private static final String DEFAULT_ADDRESS = "io.vertx.redis";

  private String encoding;
  private String host;
  private int port;
  private boolean binary;
  private String address;

  private String auth;
  private Integer select;

  private String masterName;
  private List<String> sentinels;

  public RedisOptions() {
    super();
    init();
  }

  public RedisOptions(RedisOptions other) {
    super(other);
    init();
    // base defaults
    this.encoding = other.encoding;
    this.host = other.host;
    this.port = other.port;
    this.binary = other.binary;
    this.address = other.address;
    // custom
    this.auth = other.auth;
    this.select = other.select;
    postInit();
  }

  public RedisOptions(JsonObject json) {
    super(json);
    init();
    RedisOptionsConverter.fromJson(json, this);
    postInit();
  }


  private void init() {
    encoding = DEFAULT_ENCODING;
    host = DEFAULT_HOST;
    port = DEFAULT_PORT;
    binary = DEFAULT_BINARY;
    address = DEFAULT_ADDRESS;
    // tcp defaults
    setTcpKeepAlive(true);
    setTcpNoDelay(true);
  }

  private void postInit() {
    // process the binary support
    if ("iso-8859-1".equalsIgnoreCase(encoding)) {
      binary = true;
      return;
    }

    if (binary) {
      encoding = "iso-8859-1";
    }
  }

  /**
   * Return the character encoding for Strings, default `UTF-8`.
   * * @return character encoding
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Set the user defined character encoding, e.g.: `iso-8859-1`.
   * * @param encoding the user character encoding
   *
   * @return self
   */
  public RedisOptions setEncoding(String encoding) {
    this.encoding = encoding;
    // special case here the binary andencodingare corelated so they need to be verified after set
    postInit();
    return this;
  }

  /**
   * Return if the messages to/from redis are binary, default `false`.
   * * @return are messages binary
   */
  public boolean isBinary() {
    return binary;
  }

  /**
   * Set the user defined character encoding, e.g.: `iso-8859-1`.
   * * @param binary use binary messages
   *
   * @return self
   */
  public RedisOptions setBinary(boolean binary) {
    this.binary = binary;
    // special case here the binary andencodingare corelated so they need to be verified after set
    postInit();
    return this;
  }

  /**
   * Get the default `PUB/SUB` eventbus address prefix, default `io.vertx.redis`.
   * * @return eventbus address prefix
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set the eventbus address prefix for `PUB/SUB`.
   * * @param address address prefix.
   *
   * @return self
   */
  public RedisOptions setAddress(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get the host name for the Redis server, default `localhost`.
   * * @return host name.
   */
  public String getHost() {
    return host;
  }

  /**
   * Set the host name where the Redis server is listening.
   * * @param host host name
   *
   * @return self
   */
  public RedisOptions setHost(String host) {
    this.host = host;
    return this;
  }

  /**
   * Get the tcp port where the Redis server is listening, default 6379.
   * * @return tcp port
   */
  public int getPort() {
    return port;
  }

  /**
   * Set the tcp port where the Redis server is listening.
   *
   * @param port tcp port.
   * @return self
   */
  public RedisOptions setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Get the password for authentication at connection time.
   * * @return password
   */
  public String getAuth() {
    return auth;
  }

  /**
   * Set the password for authentication at connection time.
   *
   * @param auth password
   * @return self
   */
  public RedisOptions setAuth(String auth) {
    this.auth = auth;
    return this;
  }

  /**
   * Get the database to select at connection time.
   * * @return database id
   */
  public Integer getSelect() {
    return select;
  }

  /**
   * Set the database to select at connection time.
   * * @param select database id
   *
   * @return self
   */
  public RedisOptions setSelect(Integer select) {
    this.select = select;
    return this;
  }

  /**
   * Get name of Redis master.
   *
   * @return Redis master name
   */
  public String getMasterName() {
    return masterName;
  }

  /**
   * Set name of Redis master (used with Sentinel).
   *
   * @param masterName name of Redis master
   * @return self
   */
  public RedisOptions setMasterName(String masterName) {
    this.masterName = masterName;
    return this;
  }

  /**
   * Add Sentinel node.
   *
   * @param sentinelHostAndPort Sentinel node on the form 'hostname:port'
   * @return self
   */
  public RedisOptions addSentinel(String sentinelHostAndPort) {
    if (sentinels == null) {
      sentinels = new ArrayList<>();
    }

    sentinels.add(sentinelHostAndPort);
    return this;
  }

  /**
   * Get list of Sentinels.
   *
   * @return List of Sentinels on the form 'hostname:port'
   */
  public List<String> getSentinels() {
    return sentinels;
  }

  /**
   * Set the list of Sentinels.
   *
   * @param sentinels a list of sentinels host and port string
   * @return self
   */
  public RedisOptions setSentinels(List<String> sentinels) {
    this.sentinels = sentinels;
    return this;
  }
}
