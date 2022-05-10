package io.vertx.redis.client.impl.keys;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BeginSearchKeyword implements BeginSearch {

  private static final byte UPPER_CASE_OFFSET = 'A' - 'a';

  private final byte[] keyword;
  private final int startsFrom;

  public BeginSearchKeyword(String keyword, int startsFrom) {
    this.keyword = keyword.getBytes(StandardCharsets.UTF_8);
    this.startsFrom = startsFrom;
    toUpperCase(this.keyword);
  }

  @Override
  public int begin(List<byte[]> args, int arity) {
    int start = startsFrom >= 0 ?
      startsFrom :
      Math.abs(arity) + startsFrom;

    for (int i = start; i < args.size(); i++) {
      if (match(args.get(i))) {
        return i + 1;
      }
    }
    // not found
    return -1;
  }

  /**
   * @param b ASCII character
   * @return uppercase version of arg if it was lowercase, otherwise returns arg
   */
  private static byte toUpperCase(final byte b) {
    if (b < 'a' || b > 'z') {
      return b;
    }
    return (byte) (b + UPPER_CASE_OFFSET);
  }

  /**
   * Converts in place all lower case letters to upper case in the byte array provided.
   */
  private static void toUpperCase(final byte[] bytes) {
    final int length = bytes.length;
    for (int i = 0; i < length; ++i) {
      if (bytes[i] >= 'a' && bytes[i] <= 'z') {
        bytes[i] = (byte) (bytes[i] + UPPER_CASE_OFFSET);
      }
    }
  }

  private boolean match(byte[] a) {
    if (a != null) {
      int length = a.length;
      if (keyword.length != length) {
        return false;
      } else {
        for (int i = 0; i < keyword.length; i++) {
          if (keyword[i] != toUpperCase(a[i])) {
            return false;
          }
        }
        return true;
      }
    } else {
      return false;
    }
  }
}
