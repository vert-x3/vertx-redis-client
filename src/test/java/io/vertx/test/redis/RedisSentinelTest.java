package io.vertx.test.redis;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.redis.sentinel.RedisSentinel;

import java.util.concurrent.CountDownLatch;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server
 */
public class RedisSentinelTest extends RedisSentinelClientTestBase {

  @Override
  public void setUp() throws Exception {
    super.setUp();
    // blocking for 1 sec for sentinel to settle down and detect master and slaves
    CountDownLatch latch = new CountDownLatch(1);
    Handler<Future<Void>> blockingCodeHandler = future -> {
      // Non event loop
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        //ignoring it
      } finally {
        future.complete();
      }
    };

    vertx.executeBlocking(blockingCodeHandler, asyncResult -> {
      redisSentinel = RedisSentinel.create(vertx, getSentinelConfig());
      latch.countDown();
    });
    awaitLatch(latch);
  }

  @Override
  public void tearDown() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    redisSentinel.close(asyncResult -> {
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
