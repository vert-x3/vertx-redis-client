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
package io.vertx.redis.client;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.buffer.Buffer;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

@VertxGen
public interface Response extends Iterable<Response> {

  ResponseType type();

  @Override
  String toString();

  default Long toLong() {
    final String msg = toString();
    if (msg != null) {
      return Long.parseLong(msg);
    }
    return null;
  }

  default Integer toInteger() {
    final String msg = toString();
    if (msg != null) {
      return Integer.parseInt(msg);
    }
    return null;
  }

  default Short toShort() {
    final String msg = toString();
    if (msg != null) {
      return Short.parseShort(msg);
    }
    return null;
  }

  default Byte toByte() {
    final String msg = toString();
    if (msg != null) {
      return Byte.parseByte(msg);
    }
    return null;
  }

  default Boolean toBoolean() {
    final String msg = toString();
    if (msg != null) {
      return 1L == Long.parseLong(msg);
    }
    return null;
  }

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default String toString(Charset encoding) {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  default Buffer toBuffer() {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default byte[] toBytes() {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  default Response get(int index) {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  default Response get(String key) {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  default Set<String> getKeys() {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  default int size() {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  @Override
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Iterator<Response> iterator() {
    throw new UnsupportedOperationException("This type doesn't hold a Array type");
  }
}
