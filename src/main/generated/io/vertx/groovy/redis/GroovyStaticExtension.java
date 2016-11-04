package io.vertx.groovy.redis;
public class GroovyStaticExtension {
  public static io.vertx.redis.RedisClient create(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Vertx vertx, java.util.Map<String, Object> config) {
    return io.vertx.lang.groovy.ConversionHelper.wrap(io.vertx.redis.RedisClient.create(vertx,
      config != null ? new io.vertx.redis.RedisOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(config)) : null));
  }
}
