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
