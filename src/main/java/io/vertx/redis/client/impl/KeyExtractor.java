package io.vertx.redis.client.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.vertx.redis.client.Command.*;

class KeyExtractor {

  static final byte[] STREAMS_UPPER_CASE = "STREAMS".getBytes(StandardCharsets.UTF_8);
  static final byte[] STREAMS_LOWER_CASE = "streams".getBytes(StandardCharsets.UTF_8);
  static final byte[] KEYS_UPPER_CASE = "KEYS".getBytes(StandardCharsets.UTF_8);
  static final byte[] KEYS_LOWER_CASE = "keys".getBytes(StandardCharsets.UTF_8);
  static final byte[] STORE_UPPER_CASE = "STORE".getBytes(StandardCharsets.UTF_8);
  static final byte[] STORE_LOWER_CASE = "store".getBytes(StandardCharsets.UTF_8);
  static final byte[] STOREDIST_UPPER_CASE = "STOREDIST".getBytes(StandardCharsets.UTF_8);
  static final byte[] STOREDIST_LOWER_CASE = "storedist".getBytes(StandardCharsets.UTF_8);
  private static final byte ZERO = 48;
  private static final byte HYPHEN = 45;
  private static final byte DOLLAR = 36;


  /**
   * Extract movable keys from given command request. Movable means the count of keys and may even their position within
   * command is movable.
   *
   * @return All keys of the command of the given request.
   */
  static byte[][] extractMovableKeys(final RequestImpl req) {
    final List<byte[]> args = Collections.unmodifiableList(req.getArgs());
    final List<byte[]> keys = new ArrayList<>();

    if (req.command().equals(EVAL) || req.command().equals(EVALSHA)) {
      final int keyCount = Integer.parseInt(new String(args.get(1)));
      final int keysStartIndex = 2;
      keys.addAll(args.subList(keysStartIndex, keysStartIndex + keyCount));
    }

    if (req.command().equals(GEORADIUS)
      || req.command().equals(GEORADIUS_RO)
      || req.command().equals(GEORADIUSBYMEMBER)
      || req.command().equals(GEORADIUSBYMEMBER_RO)) {
      keys.add(args.get(0));
      if (!addKeyAfterKeywordIfPresent(args, keys, STORE_LOWER_CASE)) {
        addKeyAfterKeywordIfPresent(args, keys, STORE_UPPER_CASE);
      }
      if (!addKeyAfterKeywordIfPresent(args, keys, STOREDIST_LOWER_CASE)) {
        addKeyAfterKeywordIfPresent(args, keys, STOREDIST_UPPER_CASE);
      }
    }

    if (req.command().equals(SORT)) {
      keys.add(args.get(0));
      if (!addKeyAfterKeywordIfPresent(args, keys, STORE_LOWER_CASE)) {
        addKeyAfterKeywordIfPresent(args, keys, STORE_UPPER_CASE);
      }
    }

    if (req.command().equals(MIGRATE)) {
      int keysKeywordIndex = getIndexOfArgContainsKeyword(args, KEYS_UPPER_CASE);
      if (keysKeywordIndex == -1) {
        keysKeywordIndex = getIndexOfArgContainsKeyword(args, KEYS_LOWER_CASE);
      }
      if (keysKeywordIndex > -1) {
        keys.addAll(args.subList(keysKeywordIndex + 1, args.size()));
      } else {
        keys.add(args.get(2));
      }
    }

    if (req.command().equals(XREAD) || req.command().equals(XREADGROUP)) {
      int streamsKeywordIndex = getIndexOfArgContainsKeyword(args, STREAMS_UPPER_CASE);
      if (streamsKeywordIndex == -1) {
        streamsKeywordIndex = getIndexOfArgContainsKeyword(args, STREAMS_LOWER_CASE);
      }

      final List<byte[]> argsAfterStreamsKeyword = args.subList(streamsKeywordIndex + 1, args.size());

      for (byte[] arg : argsAfterStreamsKeyword) {
        if (!isArgEqualsSign(arg, DOLLAR) && !isArgEqualsSign(arg, ZERO) && !isArgContainsSign(arg, HYPHEN)) {
          keys.add(arg);
        } else if (isArgContainsSign(arg, HYPHEN)) {
          // If hyphen separator, we need to verify both parts, before and after hyphen are parsable to long
          String mayId = new String(arg, StandardCharsets.UTF_8);
          String[] millisAndSequence = mayId.split("-");
          try {
            Long.parseLong(millisAndSequence[0]);
            Long.parseLong(millisAndSequence[1]);
          } catch (NumberFormatException e) {
            keys.add(arg);
          }
        }
      }
    }

    if (req.command().equals(ZINTERSTORE) || req.command().equals(ZUNIONSTORE)) {
      final int keyCount = Integer.parseInt(new String(args.get(1)));
      // destination
      keys.add(args.get(0));
      final int keysStartIndex = 2;
      keys.addAll(args.subList(keysStartIndex, keysStartIndex + keyCount));
    }

    return keys.toArray(new byte[keys.size()][]);
  }

  private static boolean addKeyAfterKeywordIfPresent(List<byte[]> args, List<byte[]> keys, byte[] keyword) {
    int keywordIndex = getIndexOfArgContainsKeyword(args, keyword);
    if (keywordIndex > -1) {
      keys.add(args.get(keywordIndex + 1));
      return true;
    }
    return false;
  }

  private static int getIndexOfArgContainsKeyword(List<byte[]> args, byte[] keyword) {
    int keywordIndex = -1;
    for (int i = 0; i < args.size(); i++) {
      if (Arrays.equals(args.get(i), keyword)) {
        keywordIndex = i;
        break;
      }
    }
    return keywordIndex;
  }

  private static boolean isArgContainsSign(byte[] arg, byte sign) {
    for (byte argByte : arg) {
      if (argByte == sign) {
        return true;
      }
    }
    return false;
  }

  private static boolean isArgEqualsSign(byte[] arg, byte sign) {
    return arg.length == 1 && arg[0] == sign;
  }
}
