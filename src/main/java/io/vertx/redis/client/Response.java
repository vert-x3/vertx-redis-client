package io.vertx.redis.client;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;

import java.nio.charset.Charset;

@VertxGen
public interface Response {

  ResponseType type();

  default String string() {
    throw new UnsupportedOperationException("This types doesn't hold a String/Bulk type");
  }

  default long int64() {
    return Long.parseLong(string());
  }

  default int int32() {
    return Integer.parseInt(string());
  }

  default short int16() {
    return Short.parseShort(string());
  }

  default byte int8() {
    return Byte.parseByte(string());
  }

  default boolean bool() {
    return 1L == int64();
  }

  @GenIgnore
  default String string(Charset encoding) {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  default Buffer buffer() {
    throw new UnsupportedOperationException("This types doesn't hold a Bulk type");
  }

  @GenIgnore
  default byte[] bytes() {
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
