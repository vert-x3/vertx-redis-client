package io.vertx.test.redis;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.junit.Test;

public class ContextTest  extends AbstractRedisClientBase {

  @Test
  public void runOnSpecificContext() {

    final String key = makeKey();

    Context ctx = vertx.getOrCreateContext();

    ctx.runOnContext(v -> {
      redis.get(key, res -> {
        assertEquals(ctx, Vertx.currentContext());
        testComplete();
      });
    });

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
