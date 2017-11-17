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

public class ExceptionTest extends AbstractRedisClientBase {

  @Test
  public void test1() {
    for (int i = 0 ; i < 100; i++) {
      final int idx = i;
      redis.info(r -> {
        if (idx == 10) {
          try {
            stopRedis();
          } catch (Exception e) {
            fail(e);
          }
        } else {
          try {
            r.result().toString();
          } catch (RuntimeException e) {
            fail(e);
          }
        }

        if (idx == 98) {
          try {
            startRedis();
          } catch (Exception e) {
            fail(e);
          }
        }

        if (idx == 99) {
          testComplete();
        }
      });
    }
    await();
  }

  @Test
  public void test2() {
    redis.info(r -> {
      // connection is established

      // enqueue a new call
      vertx.setTimer(100, t -> {
        redis.info(r2 -> {
          assertTrue(r.succeeded());
          testComplete();
        });
      });

      // blow up and see if the connection is resilient
      throw new RuntimeException("Kaboom: User error");
    });

    await();
  }
}
