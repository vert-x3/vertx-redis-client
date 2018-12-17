package io.vertx.redis.client;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public enum RedisRole {
  MASTER,
  SLAVE,
  SENTINEL
}
