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
    kind = message;
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
}
