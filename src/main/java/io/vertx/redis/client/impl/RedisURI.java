/*
 * Copyright 2019 Red Hat, Inc.
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
package io.vertx.redis.client.impl;

import io.vertx.core.net.SocketAddress;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to parse redis URLs. An example URI can be found at <a href="https://redis.io/topics/rediscli">Redis cli docs</a>
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public final class RedisURI {

  /**
   * Original address string
   */
  private final String connectionString;
  /**
   * Address, including host and port
   */
  private final SocketAddress socketAddress;
  /**
   * Password to the Redis instance
   */
  private final String password;
  /**
   * Database number
   */
  private final Integer select;

  public RedisURI(String connectionString) {
    this.connectionString = connectionString;
    try {
      final URI uri = new URI(connectionString);

      final String host = uri.getHost() == null ? "localhost" : uri.getHost();
      final int port = uri.getPort() == -1 ? 6379 : uri.getPort();
      final String path = (uri.getPath() == null || uri.getPath().isEmpty()) ? "/" : uri.getPath();

      switch (uri.getScheme()) {
        case "redis":
          socketAddress = SocketAddress.inetSocketAddress(port, host);
          if (path.length() > 1) {
            // skip initial slash
            select = Integer.parseInt(uri.getPath().substring(1));
          } else {
            select = null;
          }
          break;
        case "unix":
          socketAddress = SocketAddress.domainSocketAddress(path);
          Map<String, String> query = parseQuery(uri);
          if (query.containsKey("select")) {
            select = Integer.parseInt(query.get("select"));
          } else {
            select = null;
          }
          break;
        default:
          throw new RuntimeException("Unsupported Redis connection string scheme [" + uri.getScheme() + "]");
      }

      String userInfo = uri.getUserInfo();
      if (userInfo != null) {
        String[] userInfoArray = userInfo.split(":");
        if (userInfoArray.length > 0) {
          password = userInfoArray[userInfoArray.length - 1];
        } else {
          password = null;
        }
      } else {
        password = null;
      }

    } catch (URISyntaxException e) {
     throw new RuntimeException("Failed to parse the connection string", e);
    }
  }

  private static Map<String, String> parseQuery(URI uri) {
    if (uri.getQuery() != null) {
      final Map<String, String> query = new HashMap<>();

      for (String kv : uri.getQuery().split("&")) {
        int eq = kv.indexOf('=');
        if (eq != -1) {
          query.put(kv.substring(0, eq), kv.substring(eq + 1));
        }
      }

      return query;
    }

    return Collections.emptyMap();
  }

  public SocketAddress socketAddress() {
    return socketAddress;
  }

  public String password() {
    return password;
  }

  public Integer select() {
    return select;
  }

  public String connectionString() {
    return connectionString;
  }

  @Override
  public String toString() {
    return connectionString;
  }
}
