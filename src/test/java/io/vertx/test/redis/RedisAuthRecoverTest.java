package io.vertx.test.redis;

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.junit.Test;
import redis.embedded.RedisServer;

public class RedisAuthRecoverTest extends AbstractRedisClientBase {

  @Test
  public void testAutoAuth() throws Exception {

    RedisServer server = RedisServer.builder().port(6381).setting("requirepass foobar").build();
    server.start();

    RedisOptions job = new RedisOptions()
      .setHost("localhost")
      .setPort(6381)
      .setAuth("foobar");

    RedisClient rdx = RedisClient.create(vertx, job);

    final String key = makeKey();

    rdx.set(key, "1", set -> {
      assertTrue(set.succeeded());
      rdx.get(key, get -> {
        assertTrue(get.succeeded());
        assertEquals("1", get.result());

        try {
          server.stop();
        } catch (Exception ignore) {
        }

        testComplete();
      });
    });

    await();
  }

  @Test
  public void testRecover() {

    RedisServer server = RedisServer.builder().port(6381).setting("requirepass foobar").build();
    server.start();

    RedisOptions job = new RedisOptions()
      .setHost("localhost")
      .setPort(6381)
      .setAuth("foobar");

    RedisClient rdx = RedisClient.create(vertx, job);

    final String key = makeKey();

    rdx.set(key, "1", set -> {
      assertTrue(set.succeeded());

      // now kill the server
      server.stop();
      // and start again
      server.start();

      // just issue a get, which should reconnect using the right auth
      rdx.get(key, x -> {
        // INFO: there are 2 gets here because we are killing the server process before and do not let the
        // OS deliver the socket error in time, so the 1st attempt will get a connection lost error and the
        // second will the properly reconnect.
        vertx.runOnContext(v -> {
          rdx.get(key, get -> {

            assertTrue(get.succeeded());

            try {
              server.stop();
            } catch (Exception ignore) {
            }

            testComplete();
          });
        });
      });
    });

    await();
  }

}
