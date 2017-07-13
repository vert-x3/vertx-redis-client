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

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.test.core.VertxTestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import redis.embedded.RedisServer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractRedisClientBase extends VertxTestBase {

  private static final Integer DEFAULT_PORT = 6379;

  private static final Map<Integer, RedisServer> instances = new ConcurrentHashMap<>();
  protected RedisClient redis;

  private static String getHost() {
    return getProperty("host");
  }

  private static String getPort() {
    return getProperty("port");
  }

  private static String getProperty(String name) {
    String s = System.getProperty(name);
    return (s != null && s.trim().length() > 0) ? s : null;
  }

  @BeforeClass
  static public void startRedis() throws Exception {
    if (getHost() == null && getPort() == null) {
      createRedisInstance(DEFAULT_PORT);
      instances.get(DEFAULT_PORT).start();
      System.out.println("** Using embedded redis");
    } else {
      System.out.println("** Using standalone redis");
    }
  }

  @AfterClass
  static public void stopRedis() throws Exception {
    for (Map.Entry<Integer, RedisServer> entry : instances.entrySet()) {
      if (entry != null) {
        entry.getValue().stop();
      }
    }
  }

  public static void createRedisCount(final int count) throws Exception {
    Integer[] ports = new Integer[count];
    Integer basePort = DEFAULT_PORT;
    for (int i = 0; i < count; i++) {
      ports[i] = basePort++;
    }
    createRedisInstance(ports);
  }

  public static void createRedisInstance(final Integer... ports) throws Exception {
    for (Integer port : ports) {
      System.out.println("Creating redis server on port: " + port);
      instances.put(port, new RedisServer(port));
      System.out.println("Created embedded redis server on port " + port);
    }
  }

  protected static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    redis = RedisClient.create(vertx, getConfig());
  }

  @Override
  public void tearDown() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    redis.close(asyncResult -> {
      if (asyncResult.succeeded()) {
        latch.countDown();
      } else {
        throw new RuntimeException("failed to setup", asyncResult.cause());
      }
    });
    awaitLatch(latch);
    super.tearDown();
  }

  protected RedisOptions getConfig() {
    String host = getHost();
    String port = getPort();

    RedisOptions config = new RedisOptions();

    if (host != null) {
      config.setHost(host);
    }

    if (port != null) {
      config.setPort(Integer.parseInt(port));
    }

    return config;
  }

}
