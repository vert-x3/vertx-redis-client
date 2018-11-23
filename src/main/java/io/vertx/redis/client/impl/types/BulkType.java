package io.vertx.redis.client.impl.types;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class BulkType implements Response {

  public static final BulkType NULL = new BulkType(null);
  public static final BulkType EMPTY = new BulkType(Unpooled.EMPTY_BUFFER);

  public static Response create(ByteBuf message) {
    return new BulkType(message);
  }

  private final ByteBuf message;

  private BulkType(ByteBuf message) {
    this.message = message;
  }

  @Override
  public ResponseType type() {
    return ResponseType.BULK;
  }

  @Override
  public String string() {
    return string(StandardCharsets.UTF_8);
  }

  @Override
  public String string(Charset encoding) {
    if (message == null) {
      return null;
    }
    return message.toString(encoding);
  }

  @Override
  public Buffer buffer() {
    return Buffer.buffer(message);
  }

  @Override
  public byte[] bytes() {
    if (message == null) {
      return null;
    }
    return message.array();
  }

  @Override
  public boolean isNull() {
    return message == null;
  }

  @Override
  public String toString() {
    return message.toString(StandardCharsets.UTF_8);
  }
}
