package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;

/**
 * Redis protocol version to be used.
 */
@VertxGen
public enum ProtocolVersion {
  /**
   * RESP2
   */
  RESP2("2"),
  /**
   * RESP3
   */
  RESP3("3"),
  ;

  private final String value;

  ProtocolVersion(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
