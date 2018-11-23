package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.Command.*;


@RunWith(VertxUnitRunner.class)
public class RedisClusterTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  // Server: https://github.com/Grokzen/docker-redis-cluster
  final RedisOptions options = new RedisOptions()
    // we will flood the redis server
    .setMaxWaitingHandlers(128 * 1024)
    .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7001, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7002, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7003, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7004, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7005, "127.0.0.1"));

  @Test(timeout = 30_000)
  public void runTheSlotScope(TestContext should) {
    final Async test = should.async();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
            should.assertTrue(get.succeeded());
            should.assertEquals(id, get.result().string());

            final int cnt = counter.incrementAndGet();
            if (cnt % 1024 == 0) {
              System.out.print('.');
            }

            if (cnt == len) {
              test.complete();
            }
          });
        });
      }
    });
  }

  @Test(timeout = 30_000)
  public void autoFindNodeByMOVEDAndASK(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7002, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7004, "127.0.0.1"));

    // we miss add the odd port nodes on purpose

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
            should.assertTrue(get.succeeded());
            should.assertEquals(id, get.result().string());

            final int cnt = counter.incrementAndGet();
            if (cnt % 1024 == 0) {
              System.out.print('.');
            }

            if (cnt == len) {
              test.complete();
            }
          });
        });
      }
    });
  }

  @Test(timeout = 30_000)
  public void autoFindNodes(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"));

    // we only provide 1 node

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
            should.assertTrue(get.succeeded());
            should.assertEquals(id, get.result().string());

            final int cnt = counter.incrementAndGet();
            if (cnt % 1024 == 0) {
              System.out.print('.');
            }

            if (cnt == len) {
              test.complete();
            }
          });
        });
      }
    });
  }
}
