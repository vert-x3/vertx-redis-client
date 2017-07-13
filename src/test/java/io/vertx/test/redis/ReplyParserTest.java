/**
 * Copyright 2015 Red Hat, Inc.
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
package io.vertx.test.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.impl.ReplyParser;
import io.vertx.test.core.VertxTestBase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReplyParserTest extends VertxTestBase {

  @Test
  public void testArrayArrayParser() {
    Buffer b = Buffer.buffer();
    b.appendString(
      "*2\r\n" +
        "*3\r\n" +
        ":1\r\n" +
        ":2\r\n" +
        ":3\r\n" +
        "*2\r\n" +
        "+Foo\r\n" +
        "-Bar\r\n");

    ReplyParser parser = new ReplyParser(reply -> testComplete());

    parser.handle(b);
    await();
  }

  @Test
  public void testArrayArrayEmptyParser() {
    Buffer b = Buffer.buffer();
    b.appendString(
      "*1\r\n" +
        "*0\r\n");

    ReplyParser parser = new ReplyParser(reply -> testComplete());

    parser.handle(b);
    await();
  }

  @Test
  @Ignore
  public void testCleanupInternalBuffer() {
    // this test cannot really be automated
    // in order to verify this one needs to set breaktpoints on the parser
    AtomicInteger cnt = new AtomicInteger(0);

    ReplyParser parser = new ReplyParser(reply -> {
      if (cnt.incrementAndGet() == 2) {
        testComplete();
      }
    });

    List<String> parts = Arrays.asList(
      ":1",
      // second message
      "\r\n:1",
      // third
      "\r\n:1\r\n"
    );

    for (String part : parts) {
      parser.handle(Buffer.buffer().appendString(part));
    }

    await();
  }
}
