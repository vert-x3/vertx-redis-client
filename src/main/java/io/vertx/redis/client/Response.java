package io.vertx.redis.client;

import io.netty.buffer.ByteBuf;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;

import java.nio.charset.Charset;

@VertxGen
public interface Response {

  default String getString() {
    throw new UnsupportedOperationException("This types doesn't hold a String/Bulk type");
  }

  default long getLong() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default int getInteger() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default short getShort() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default byte getByte() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default boolean getBoolean() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  @GenIgnore
  default String getString(Charset encoding) {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  default Buffer getBuffer() {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  @GenIgnore
  default ByteBuf getByteBuf() {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  @GenIgnore
  default byte[] getBytes() {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  default Response get(int index) {
    throw new UnsupportedOperationException("This types doesn't hold an Array type");
  }

  default int size() {
    throw new UnsupportedOperationException("This types doesn't hold an Array type");
  }

  default boolean isNull() {
    return false;
  }
}
