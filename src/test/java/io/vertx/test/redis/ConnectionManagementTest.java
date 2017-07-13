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

import org.junit.Test;

public class ConnectionManagementTest extends AbstractRedisClientBase {

  @Test
  public void testQuit() {
    redis.close(v -> {
      testComplete();
    });
    await();
  }

  @Test
  public void testCommandAfterQuit() {
    redis.close(v -> {
      redis.info(res -> {
        assertTrue(res.succeeded());
        assertNotNull(res.result());
        testComplete();
      });
    });
    await();
  }
}
