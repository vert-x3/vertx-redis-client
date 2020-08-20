package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public interface Multi extends Response {

  /**
   * Adds a reply to the current response as it gets parsed from the wire.
   * @param reply the reply to add
   */
  void add(Response reply);


  /**
   * Signals that we received all required replies.
   */
  boolean complete();
}
