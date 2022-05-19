/*
 * Copyright 2019 Red Hat, Inc.
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
package io.vertx.redis.client.impl.types;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

public final class ErrorType extends Throwable implements Response {

  public static ErrorType create(String message) {
    return new ErrorType(message, null);
  }

  public static ErrorType create(String message, Throwable cause) {
    return new ErrorType(message, cause);
  }

  private ErrorType(String message, Throwable cause) {
    super(message, cause, false, false);
  }

  @Override
  public ResponseType type() {
    return ResponseType.ERROR;
  }

  public boolean is(String kind) {
    String message = getMessage();
    if (message == null) {
      return kind == null;
    } else {
      if (kind.equals("ERR")) {
        return message.startsWith(kind);
      } else {
        // this is to address databases like PIKA which drift from the official
        // protocol by always prefixing errors with ERR
        return message.startsWith(kind) || message.startsWith("ERR " + kind);
      }
    }
  }

  @Override
  public String toString() {
    return getMessage();
  }

  public @Nullable String slice(char sep, int index) {
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
