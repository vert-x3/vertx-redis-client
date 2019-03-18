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

/**
 * The response received from the REDIS server. Redis responses can have several representations:
 *
 * <ul>
 *     <li>simple string - C string</li>
 *     <li>integer - 64bit integer value</li>
 *     <li>bulk - byte array</li>
 *     <li>multi - list</li>
 * </ul>
 *
 * Due to the dynamic nature the response object will try to cast the received response to the desired type. A special
 * case should be noted that multi responses are also handled by the response object as it implements the iterable
 * interface. So in this case constructs like for loops on the response will give you access to the underlying elements.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Response extends Iterable<Response> {

  /**
   * The response return type.
   * @return the type.
   */
  ResponseType type();

  /**
   * Get this response as a String.
   * @return string value
   */
  @Override
  String toString();

  /**
   * Get this response as a Long.
   * @return long value.
   */
  default Long toLong() {
    final String msg = toString();
    if (msg != null) {
      return Long.parseLong(msg);
    }
    return null;
  }

  /**
   * Get this response as a Integer.
   * @return int value.
   */
  default Integer toInteger() {
    final String msg = toString();
    if (msg != null) {
      return Integer.parseInt(msg);
    }
    return null;
  }

  /**
   * Get this response as a Short.
   * @return short value.
   */
  default Short toShort() {
    final String msg = toString();
    if (msg != null) {
      return Short.parseShort(msg);
    }
    return null;
  }

  /**
   * Get this response as a Byte.
   * @return byte value.
   */
  default Byte toByte() {
    final String msg = toString();
    if (msg != null) {
      return Byte.parseByte(msg);
    }
    return null;
  }

  /**
   * Get this response as a Boolean.
   * @return boolean value.
   */
  default Boolean toBoolean() {
    final String msg = toString();
    if (msg != null) {
      return 1L == Long.parseLong(msg);
    }
    return null;
  }

  /**
   * Get this response as a String encoded with the given charset.
   * @return String value.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default String toString(Charset encoding) {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  /**
   * Get this response as Buffer.
   * @return buffer value.
   */
  default Buffer toBuffer() {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  /**
   * Get this response as a byte[].
   * @return byte[] value.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default byte[] toBytes() {
    throw new UnsupportedOperationException("This type doesn't hold a Bulk type");
  }

  /**
   * Get this multi response value at a numerical index.
   * @param index the required index.
   * @return Response value.
   */
  default Response get(int index) {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  /**
   * Get this multi response value at a string key. Note that REDIS does not support strings as keys but by convention
   * it encodes hashes in lists where index i is the key, and index i+1 is the value.
   * @param key the required key.
   * @return Response value.
   */
  default Response get(String key) {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  /**
   * Get this multi response keys from a hash. Note that REDIS does not support strings as keys but by convention
   * it encodes hashes in lists where index i is the key, and index i+1 is the value.
   * @return the set of keys.
   */
  default Set<String> getKeys() {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  /**
   * Get this size of this multi response.
   * @return the size of the multi.
   */
  default int size() {
    throw new UnsupportedOperationException("This type doesn't hold an Array type");
  }

  /**
   * Return an iterator so it can be iterated using the foreach construct.
   * @return response iterator.
   */
  @Override
  @GenIgnore
  default Iterator<Response> iterator() {
    throw new UnsupportedOperationException("This type doesn't hold a Array type");
  }
}
