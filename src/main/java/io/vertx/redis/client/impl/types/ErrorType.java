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

import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

public final class ErrorType extends Throwable implements Response {

  public static ErrorType create(String message) {
    return new ErrorType(message);
  }

  private final String kind;

  private ErrorType(String message) {
    super(message, null, false, false);

    if (message != null) {
      for (int i = 0; i < message.length(); i++) {
        if (message.charAt(i) == ' ') {
          kind = message.substring(0, i);
          return;
        }
      }
    }
    kind = null;
  }

  @Override
  public ResponseType type() {
    return ResponseType.ERROR;
  }

  public boolean is(String kind) {
    if (this.kind == null) {
      return kind == null;
    } else {
      return this.kind.equalsIgnoreCase(kind);
    }
  }

  public String getKind() {
    return kind;
  }

  @Override
  public String toString() {
    return getMessage();
  }

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
