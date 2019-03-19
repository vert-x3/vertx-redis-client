package io.vertx.redis.impl;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ScriptImpl implements io.vertx.redis.Script {
  private final String script;
  private final String sha1;

  private static final char[] DIGITS_LOWER =
    {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  public ScriptImpl(String script) {
    this(script, digestSha1(script));
  }

  public ScriptImpl(String script, byte[] sha1) {
    this(script, String.valueOf(encodeHex(sha1)));
  }

  public ScriptImpl(String script, String sha1) {
    this.script = script;
    this.sha1 = sha1;
  }

  private static byte[] digestSha1(String script) {
    final MessageDigest sha1;
    try {
      sha1 = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return sha1.digest(script.getBytes(Charset.forName("UTF-8")));
  }

  private static char[] encodeHex(byte[] data) {
    final int l = data.length;
    final char[] out = new char[l << 1];
    // two characters form the hex value.
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
      out[j++] = DIGITS_LOWER[0x0F & data[i]];
    }
    return out;
  }

  @Override
  public String getScript() {
    return script;
  }

  @Override
  public String getSha1() {
    return sha1;
  }
}
