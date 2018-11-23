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
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default int int32() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default short int16() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default byte int8() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
  }

  default boolean bool() {
    throw new UnsupportedOperationException("This types doesn't hold an Integer type");
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
