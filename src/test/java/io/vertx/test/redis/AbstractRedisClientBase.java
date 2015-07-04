package io.vertx.test.redis;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.op.*;
import io.vertx.test.core.VertxTestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import redis.embedded.RedisServer;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisClientBase extends VertxTestBase {

  private static final Integer DEFAULT_PORT = 6379;

  private static final Map<Integer, RedisServer> instances = new ConcurrentHashMap<>();

  private static String getHost() {
    return getProperty("host");
  }

  private static String getPort() {
    return getProperty("port");
  }

  private static String getProperty(String name) {
    String s = System.getProperty(name);
    return (s != null && s.trim().length() > 0) ?  s : null;
  }

  protected RedisClient redis;

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
    for(Map.Entry<Integer, RedisServer> entry: instances.entrySet()) {
      if(entry != null){
        entry.getValue().stop();
      }
    }
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

  public static void createRedisCount(final int count) throws Exception {
    Integer[] ports = new Integer[count];
    Integer basePort = DEFAULT_PORT;
    for(int i = 0; i < count; i++) {
      ports[i] = basePort++;
    }
    createRedisInstance(ports);
  }

  public static void createRedisInstance(final Integer... ports) throws Exception {
    for(Integer port: ports) {
      System.out.println("Creating redis server on port: " + port);
      instances.put(port, new RedisServer(port));
      System.out.println("Created embedded redis server on port " + port);
    }
  }

  protected JsonObject getConfig() {
    JsonObject config = new JsonObject();
    String host = getHost();
    String port = getPort();
    if (host != null) {
      config.put("host", host);
    }
    if (port != null) {
      config.put("port", Integer.parseInt(port));
    }
    return config;
  }

  protected static JsonArray toJsonArray(final Object... params) {
    return (params != null) ? new JsonArray(Arrays.asList(params)) : null;
  }

  protected static Object[] toArray(final Object... params) {
    return params;
  }

  protected static String errorMessage(Throwable t) {
    return t != null ? t.getMessage() : "";
  }

  protected static String makeKey() {
    return UUID.randomUUID().toString();
  }

  protected static Map<String, String> toMap(final String ... params) {
    if (params.length % 2 != 0) {
      throw new IllegalArgumentException("Last key has no value");
    }
    Map<String, String> result = new HashMap<>();
    String key = null;
    for (String param: params) {
      if (key == null) {
        key = param;
      } else {
        result.put(key, param);
        key = null;
      }
    }
    return result;
  }

  protected static <T> List<T> toList(final T ... params) {
    return Arrays.asList(params);
  }
}
