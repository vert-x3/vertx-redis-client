package io.vertx.redis.client.impl;

import io.vertx.core.net.SocketAddress;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RedisURI {

  private final String address;
  private final SocketAddress socketAddress;
  private final String password;
  private final Integer select;

  public RedisURI(String address) {
    this.address = address;
    try {
      final URI uri = new URI(address);

      switch (uri.getScheme()) {
        case "redis":
          socketAddress = SocketAddress.inetSocketAddress(uri.getPort(), uri.getHost());
          if (uri.getPath() != null && !uri.getPath().isEmpty()) {
            // skip initial slash
            select = Integer.parseInt(uri.getPath().substring(1));
          } else {
            select = null;
          }
          break;
        case "unix":
          socketAddress = SocketAddress.domainSocketAddress(uri.getPath());
          Map<String, String> query = parseQuery(uri);
          if (query.containsKey("select")) {
            select = Integer.parseInt(query.get("select"));
          } else {
            select = null;
          }
          break;
        default:
          throw new RuntimeException("Unsupported scheme [" + uri.getScheme() + "]");
      }

      String userInfo = uri.getUserInfo();
      if (userInfo != null) {
        if (userInfo.length() == 0 || userInfo.charAt(0) != ':') {
          throw new RuntimeException("Invalid userinfo [" + uri.getUserInfo() + "] expected format [:password]");
        }
        password = uri.getUserInfo().substring(1);
      } else {
        password = null;
      }

    } catch (URISyntaxException e) {
     throw new RuntimeException("Failed to parse the endpoint address", e);
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

  public String address() {
    return address;
  }

  @Override
  public String toString() {
    return address;
  }
}
