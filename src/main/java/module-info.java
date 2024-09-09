
module io.vertx.client.redis {
  requires io.vertx.core;
  requires io.vertx.core.logging;
  requires static io.vertx.docgen;
  requires static io.vertx.codegen.api;
  requires static io.vertx.codegen.json;
  exports io.vertx.redis.client;
}
