package io.vertx.redis.client.impl.types;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class BulkType implements Response {

  public static final BulkType EMPTY = new BulkType(Buffer.buffer(""));

  public static BulkType create(Buffer message) {
    return new BulkType(message);
  }

  private final Buffer message;

  private BulkType(Buffer message) {
    this.message = message;
  }

  @Override
  public ResponseType type() {
    return ResponseType.BULK;
  }

  @Override
  public String toString() {
    return toString(StandardCharsets.UTF_8);
  }

  @Override
  public String toString(Charset encoding) {
    return new String(message.getBytes(), encoding);
  }

  @Override
  public Buffer toBuffer() {
    return message;
  }

  @Override
  public byte[] toBytes() {
    return message.getBytes();
  }
}
