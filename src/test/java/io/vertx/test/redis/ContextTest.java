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

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.junit.Test;

public class ContextTest extends AbstractRedisClientBase {

  @Test
  public void runOnSpecificContext() {

    final String key = makeKey();

    Context ctx = vertx.getOrCreateContext();

    ctx.runOnContext(v -> redis.get(key, res -> {
      assertEquals(ctx, Vertx.currentContext());
      testComplete();
    }));

    await();
  }

  @Test
  public void runWithoutContext() {

    final String key = makeKey();

    Context ctx = Vertx.currentContext();
    assertNull(ctx);

    redis.get(key, res -> {
      assertNotSame(ctx, Vertx.currentContext());
      testComplete();
    });

    await();
  }
}
