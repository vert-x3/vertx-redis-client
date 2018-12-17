/*
 * Copyright 2018 Red Hat, Inc.
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
package io.vertx.redis.client;

/**
 * Maps a redis error message to a JVM Exception type.
 * Redis Errors have no trace so this throwable will not contain a stacktrace.
 *
 * @author Paulo Lopes
 */
public final class RedisException extends Throwable {

  public RedisException(String message) {
    super(message, null, false, false);
  }

  /**
   * Redis error messages follow the sintax: ERROR_TYPE error detail message
   * This method will evaluate is the given type matches the message ERROR_TYPE.
   *
   * @param type a type
   * @return true if match.
   */
  public boolean is(String type) {
    String message = getMessage();
    if (message == null) {
      return false;
    }
    final int len = type.length();

    if (message.length() < len) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (message.charAt(i) != type.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Some messages are well formatted and allow extracting data from.
   * This method will extract the indexed slice of the message given the seperator sep.
   *
   * @param sep a char seperator
   * @param index the desired index.
   * @return the slice or null.
   */
  public String slice(char sep, int index) {
    String message = getMessage();
    if (message == null) {
      return null;
    }

    int start = 0;
    int count = 0;
    for (int i = 0; i < message.length(); i++) {
      if (message.charAt(i) == sep) {
        if (++count > index) {
          return message.substring(start, i);
        } else {
          start = i + 1;
        }
      }
    }
    // EOL
    if (count == index) {
      return message.substring(start);
    }

    return null;
  }
}
