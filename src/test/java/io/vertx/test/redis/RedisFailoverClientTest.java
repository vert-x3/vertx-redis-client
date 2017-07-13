package io.vertx.test.redis;

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.junit.Test;

import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server
 */
public class RedisFailoverClientTest extends RedisFailoverClientTestBase {

  @Override
  public void setUp() throws Exception {
    super.setUp();

    host = InetAddress.getLocalHost().getHostAddress();
    RedisOptions ro = new RedisOptions();
    for (int i = 0; i < 3; i++) {
      ro.addSentinel(String.format("%s:%d", host, DEFAULT_SENTINEL_PORT + i));
    }
    ro.setMasterName("mymaster");
    redis = RedisClient.create(vertx, ro);

    // sleeping for 1 second for client to refresh once.
    Thread.sleep(1000L);
  }

  @Test
  public void testInfo() {
    redis.info(reply0 -> {
      assertTrue(reply0.succeeded());
      assertNotNull(reply0.result());
      testComplete();
    });
    await();
  }

  @Override
  public void tearDown() throws Exception {
    // close the failover client
    CountDownLatch latch = new CountDownLatch(1);
    redis.close(asyncResult -> {
      latch.countDown();
    });

    awaitLatch(latch);
    super.tearDown();
  }
}
