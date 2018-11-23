package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public enum RedisRole {
  MASTER,
  SLAVE,
  SENTINEL
}