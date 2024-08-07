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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to parse redis URLs. The URI should follow the scheme: redis://[username:password@][host][:port][/[database]
 * An example URI can be found at <a href="https://redis.io/topics/rediscli">Redis cli docs</a>
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
public final class RedisURI {

  private static final int DEFAULT_PORT = 6379;

  /**
   * Address, including host and port
   */
  private final SocketAddress socketAddress;
  /**
   * Password to the Redis instance
   */
  private final String user;
  /**
   * Password to the Redis instance
   */
  private final String password;
  /**
   * Database number. With a default Redis config it might be a number from 0 to 15. Not supported in clustered mode.
   */
  private final Integer select;
  /**
   * SSL
   */
  private final boolean ssl;
  /**
   * UNIX
   */
  private final boolean unix;
  /**
   * Params
   */
  private final Map<String, String> params;

  public RedisURI(RedisURI base, String host, int port) {
    socketAddress = SocketAddress.inetSocketAddress(port, host);
    unix = false;
    // use the base data
    user = base.user;
    password = base.password;
    select = base.select;
    ssl = base.ssl;
    params = base.params;
  }

  public RedisURI(String connectionString) {
    try {
      final URI uri = new URI(connectionString);

      final String host = uri.getHost();
      final int port = uri.getPort() == -1 ? DEFAULT_PORT : uri.getPort();

      if (uri.getAuthority() != null) {
        // if we have an authority, then we have a host and port or userinfo
        if (uri.getRawUserInfo() == null && uri.getHost() == null && uri.getPort() == -1) {
          throw new IllegalArgumentException("Fail to parse connection string authority");
        }
      }

      final String path = (uri.getPath() == null || uri.getPath().isEmpty()) ? "/" : uri.getPath();

      // According to https://www.iana.org/assignments/uri-schemes/prov/redis there is no specified order of decision
      // in case if db number or password are given in 2 different ways (e.g. in path and query).
      // Implementation uses the query values as a fallback if another one is missing.
      params = parseQuery(uri);
      switch (uri.getScheme()) {
        case "rediss":
          ssl = true;
          unix = false;
          socketAddress = SocketAddress.inetSocketAddress(port, host);
          if (path.length() > 1) {
            // skip initial slash
            select = Integer.parseInt(uri.getPath().substring(1));
          } else if (params.containsKey("db")) {
            select = Integer.parseInt(params.get("db"));
          } else {
            select = null;
          }
          break;
        case "redis":
          ssl = false;
          unix = false;
          socketAddress = SocketAddress.inetSocketAddress(port, host);
          if (path.length() > 1) {
            // skip initial slash
            select = Integer.parseInt(uri.getPath().substring(1));
          } else if (params.containsKey("db")) {
            select = Integer.parseInt(params.get("db"));
          } else {
            select = null;
          }
          break;
        case "unix":
          ssl = false;
          unix = true;
          socketAddress = SocketAddress.domainSocketAddress(path);
          if (params.containsKey("db")) {
            select = Integer.parseInt(params.get("db"));
          } else {
            select = null;
          }
          break;
        default:
          throw new IllegalArgumentException("Unsupported Redis connection string scheme [" + uri.getScheme() + "]");
      }

      String userInfo = uri.getRawUserInfo();
      if (userInfo != null) {
        int sep = userInfo.indexOf(':');
        if (sep != -1) {
          if (sep > 0) {
            user = urlDecode(userInfo.substring(0, sep));
          } else {
            user = params.getOrDefault("user", null);
          }
          password = urlDecode(userInfo.substring(sep + 1));
        } else {
          user = params.getOrDefault("user", null);
          password = params.getOrDefault("password", null);
        }
      } else {
        user = params.getOrDefault("user", null);
        password = params.getOrDefault("password", null);
      }

    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Failed to parse the connection string", e);
    }
  }

  private static String urlDecode(String raw) {
    try {
      return URLDecoder.decode(raw, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  private static String urlEncode(String raw) {
    try {
      return URLEncoder.encode(raw, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
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

  public String user() {
    return user;
  }

  public String password() {
    return password;
  }

  public Integer select() {
    return select;
  }

  public boolean ssl() {
    return ssl;
  }

  public boolean unix() {
    return unix;
  }

  public String param(String key) {
    return params.get(key);
  }

  public String userinfo() {
    if (user == null && password == null) {
      return "";
    }

    return
      (user == null ? "" : user) +
        ":" +
        (password == null ? "" : urlEncode(password)) +
        "@";
  }

  public String protocol() {
    if (unix) {
      return "unix";
    } else {
      if (ssl) {
        return "rediss";
      } else {
        return "redis";
      }
    }
  }

  /**
   * Returns the base of this URI, which consists of the scheme, optional user info, host and port
   * (or path in case of a UNIX domain socket). Does not include the database number or the query parameters.
   */
  public String baseUri() {
    StringBuilder result = new StringBuilder();
    if (unix()) {
      result.append("unix://");
      result.append(socketAddress().path());
    } else {
      result.append("redis");
      if (ssl()) {
        result.append('s');
      }
      result.append("://");
      result.append(userinfo());
      result.append(socketAddress().host());
      result.append(':');
      result.append(socketAddress().port());
    }
    return result.toString();
  }

  @Override
  public String toString() {
    return protocol() + "://" + socketAddress() + "/" + (select == null ? "" : select);
  }
}
