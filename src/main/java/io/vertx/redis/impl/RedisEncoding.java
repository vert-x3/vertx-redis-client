/**
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.impl;

/**
 * This is how Redis escapes char codes:
 *
 * DEC      HEX    REDIS
 * 07       07     \a
 * 08       08     \b
 * 09       09     \t
 * 10       0a     \n
 * 13       0d     \r
 *
 * Otherwise:
 * 00-32    00-20  \xXX
 * 32-127   20-7f  ASCII char
 * 127-256  7f-ff  \xXX
 *
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class RedisEncoding {

  private static final char[] NUMERALS = "0123456789abcdef".toCharArray();

  /**
   * Encode characters in input string using \\xXX syntax.
   * Only lower byte of every character is used when encoding.
   *
   * @param value Input string with characters between 0 and 255
   * @return Encoded string that can be pasted into redis-cli shell
   */
  public static String encode(String value) {
    StringBuilder sb = new StringBuilder();
    int len = value.length();
    for (int i = 0; i < len; i++) {
      char c = value.charAt(i);
      int lo = 0xFF & c;
      if (lo >= 32 && lo < 127) {
        sb.append((char) lo);
        continue;
      }
      switch (lo) {
        case 7:
          sb.append("\\a");
          continue;
        case 8:
          sb.append("\\b");
          continue;
        case 9:
          sb.append("\\t");
          continue;
        case 10:
          sb.append("\\n");
          continue;
        case 13:
          sb.append("\\r");
          continue;
      }

      int ma = lo / 16;
      int mi = lo % 16;
      sb.append("\\x").append(NUMERALS[ma]).append(NUMERALS[mi]);
    }
    return sb.toString();
  }

  /**
   * Decode characters in input string from \\xXX syntax.
   * Result can be passed to setBinary(), and restore()
   *
   * @param value Input string with characters between 0 and 255
   * @return Encoded string that can be pasted into redis-cli shell
   */
  public static String decode(String value) {
    StringBuilder sb = new StringBuilder();
    int len = value.length();

    int decoding = -1;
    int d = 0;

    for (int i = 0; i < len; i++) {
      char c = value.charAt(i);

      if (decoding > -1) {
        int df = i - decoding;
        if (df == 1) {
          if (c == 'x') {
            continue;
          }
          switch (c) {
            case 'a':
              sb.append('\u0007');
              break;
            case 'b':
              sb.append('\u0008');
              break;
            case 't':
              sb.append('\u0009');
              break;
            case 'n':
              sb.append('\n');
              break;
            case 'r':
              sb.append('\r');
              break;
            case '\\':
              sb.append('\\');
              break;
            default:
              throw new RuntimeException("Invalid escape sequence: \\" + c + " at pos " + i);
          }
          decoding = -1;
          d = 0;
          continue;
        }

        int val = c >= 48 && c < 58 ? (c - 48)
          : c >= 65 && c < 71 ? (10 + c - 65)
          : c >= 97 && c < 103 ? (10 + c - 97)
          : -1;
        if (val == -1) {
          throw new RuntimeException("Invalid escape sequence: " + value.substring(decoding, i + 1) + " at pos: " + i);
        }

        d += df == 2 ? 16 * val : val;

        if (df == 3) {
          sb.append((char) d);
          decoding = -1;
          d = 0;
        }
      } else if (c == '\\') {
        decoding = i;
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}
