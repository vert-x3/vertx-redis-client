package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.redis.impl.ScriptImpl;

/**
 * This factory is deprecated use {@link io.vertx.redis.client.Redis} instead,
 * or for a typed API {@link io.vertx.redis.client.RedisAPI}.
 */
@VertxGen
@Deprecated
public interface Script {
  static Script create(String script) {
    return new ScriptImpl(script);
  }
  static Script create(String script, String sha1) {
    return new ScriptImpl(script, sha1);
  }

  String getScript();
  String getSha1();
}
