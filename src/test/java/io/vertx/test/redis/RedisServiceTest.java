/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.test.redis;

import io.vertx.redis.RedisService;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class RedisServiceTest extends RedisServiceTestBase {

  @Override
  public void setUp() throws Exception {
    super.setUp();
    redis = RedisService.create(vertx, getConfig());
    CountDownLatch latch = new CountDownLatch(1);
    redis.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        latch.countDown();
      } else {
        throw new RuntimeException("failed to setup", asyncResult.cause());
      }
    });
    awaitLatch(latch);
  }

  @Override
  public void tearDown() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    redis.stop(asyncResult -> {
      if (asyncResult.succeeded()) {
        latch.countDown();
      } else {
        throw new RuntimeException("failed to setup", asyncResult.cause());
      }
    });
    awaitLatch(latch);
    super.tearDown();
  }
}
