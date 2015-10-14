package io.vertx.test.redis;

import io.vertx.redis.op.ScanOptions;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class RedisIOBTest extends AbstractRedisClientBase {

  @Test
  public void test1() {

    // setup
    redis.sadd("a09a4013-8856-487b-ba2e-fa464b35b8fc", "0", res -> {

      final int numberOfIterations = 200000;

      AtomicInteger cnt = new AtomicInteger(0);

      for (int i = 0; i < numberOfIterations; i++) {
        redis.zscan("gm:1:lb:0:rm:b:players", "0", ScanOptions.NONE, event -> {
          int runs = cnt.incrementAndGet();
          if (event.failed()) {
            fail(event.cause().getMessage());
          }
          if (runs == numberOfIterations) {
            testComplete();
          }
        });
      }
    });


    await();
  }
}
