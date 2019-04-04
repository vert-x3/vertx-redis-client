package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.redis.impl.ScriptImpl;

/**
 * Container for a script and its sha1 hash.
 */
@VertxGen
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
