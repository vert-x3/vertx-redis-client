package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public enum ResponseType {
  SIMPLE,
  ERROR,
  INTEGER,
  BULK,
  MULTI
}
