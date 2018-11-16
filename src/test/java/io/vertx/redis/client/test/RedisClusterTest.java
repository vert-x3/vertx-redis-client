package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Args;
import io.vertx.redis.RedisCluster;
import io.vertx.redis.RedisConnection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(VertxUnitRunner.class)
public class RedisClusterTest {

  private final Random random = new Random();

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  // Server: https://github.com/Grokzen/docker-redis-cluster
  private final List<SocketAddress> clusterHosts = Arrays.asList(
    SocketAddress.inetSocketAddress(7000, "127.0.0.1"),
    SocketAddress.inetSocketAddress(7001, "127.0.0.1"),
    SocketAddress.inetSocketAddress(7002, "127.0.0.1"),
    SocketAddress.inetSocketAddress(7003, "127.0.0.1"),
    SocketAddress.inetSocketAddress(7004, "127.0.0.1"),
    SocketAddress.inetSocketAddress(7005, "127.0.0.1")
  );

  @Test(timeout = 30_000)
  public void runTheSlotScope(TestContext should) {
    final Async test = should.async();

    RedisCluster
      .create(rule.vertx(), clusterHosts)
      .open(open -> {
        should.assertTrue(open.succeeded());

        final RedisConnection conn = open.result();

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          conn.send("SET", Args.key(id).add(id), set -> {
            should.assertTrue(set.succeeded());
            conn.send("GET", Args.key(id), get -> {
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().asString());

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

    final List<SocketAddress> clusterHosts2 = new ArrayList<>(clusterHosts);
    // drop a node
    clusterHosts2.remove(random.nextInt(clusterHosts2.size()));

    RedisCluster
      .create(rule.vertx(), clusterHosts2)
      .open(open -> {
        should.assertTrue(open.succeeded());

        final RedisConnection conn = open.result();

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          conn.send("SET", Args.key(id).add(id), set -> {
            should.assertTrue(set.succeeded());
            conn.send("GET", Args.key(id), get -> {
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().asString());

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

    final List<SocketAddress> clusterHosts3 = new ArrayList<>();
    // drop a node
    clusterHosts3.add(SocketAddress.inetSocketAddress(7000, "127.0.0.1"));

    RedisCluster
      .create(rule.vertx(), clusterHosts3)
      .open(open -> {
        should.assertTrue(open.succeeded());

        final RedisConnection conn = open.result();

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          conn.send("SET", Args.key(id).add(id), set -> {
            should.assertTrue(set.succeeded());
            conn.send("GET", Args.key(id), get -> {
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().asString());

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
