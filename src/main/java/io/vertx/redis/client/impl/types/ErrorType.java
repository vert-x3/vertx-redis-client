package io.vertx.redis.client.impl.types;

import io.vertx.redis.client.Response;

public final class ErrorType extends Throwable implements Response {

  public static Response create(String message) {
    return new ErrorType(message);
  }

  private final String kind;

  private ErrorType(String message) {
    super(message);

    if (message != null) {
      for (int i = 0; i < message.length(); i++) {
        if (message.charAt(i) == ' ') {
          kind = message.substring(0, i);
          return;
        }
      }
    }
    kind = message;
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
}
