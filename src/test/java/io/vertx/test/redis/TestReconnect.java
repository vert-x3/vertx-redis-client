package io.vertx.test.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TestReconnect extends VertxTestBase {

  static final int PORT = 2000;

  private static final Buffer PING = Buffer.buffer("*1\r\n$4\r\nPING\r\n");
  private static final Buffer PONG = Buffer.buffer("*1\r\n$4\r\nPONG\r\n");

  @Test
  public void test() throws Exception {
    long numCommands = 100_000;
    NetServer server = vertx.createNetServer();
    server.connectHandler(so -> {
      AtomicInteger received = new AtomicInteger();
      AtomicInteger index = new AtomicInteger();
      so.handler(buff -> {
        int val = index.addAndGet(buff.length());
        int pongs = val / PING.length();
        index.set(val % PING.length());
        for (int i = 0; i < pongs; i++) {
          if (received.incrementAndGet() == 10000) {
            so.close();
            return;
          }
          so.write(PONG);
        }
      });
    });
    CountDownLatch latch = new CountDownLatch(1);
    server.listen(PORT, "localhost", onSuccess(v -> latch.countDown()));
    awaitLatch(latch);
    RedisClient client = RedisClient.create(vertx, new RedisOptions().setAddress("localhost").setPort(PORT));
    AtomicInteger inflight = new AtomicInteger();
    AtomicInteger done = new AtomicInteger();
    AtomicInteger sent = new AtomicInteger();
    while (sent.get() < numCommands) {
      if (inflight.get() < 5000) {
        inflight.incrementAndGet();
        sent.incrementAndGet();
        client.ping(ar -> {
          inflight.decrementAndGet();
          if (done.incrementAndGet() == numCommands) {
            testComplete();
          }
        });
      } else {
        Thread.yield();
      }
    }
    await();
  }
}
