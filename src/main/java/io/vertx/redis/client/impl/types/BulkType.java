package io.vertx.redis.client.impl.types;

import io.netty.buffer.ByteBuf;
import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class BulkType implements Response {

  public static Response create(ByteBuf message) {
    return new BulkType(message);
  }

  private final ByteBuf message;

  private BulkType(ByteBuf message) {
    this.message = message;
  }

  @Override
  public String getString() {
    return getString(StandardCharsets.UTF_8);
  }

  @Override
  public String getString(Charset encoding) {
    return message.toString(encoding);
  }

  @Override
  public Buffer getBuffer() {
    return Buffer.buffer(message);
  }

  @Override
  public ByteBuf getByteBuf() {
    return message;
  }

  @Override
  public byte[] getBytes() {
    return message.array();
  }
}
