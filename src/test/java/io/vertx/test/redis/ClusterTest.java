package io.vertx.test.redis;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Args;
import io.vertx.redis.Cluster;
import io.vertx.redis.Redis;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(VertxUnitRunner.class)
public class ClusterTest {

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

  private Redis client;

  @Before
  public void setUp(TestContext should) {
    final Async test = should.async();
    // create the basic client
    client = Cluster
      .create(rule.vertx(), clusterHosts)
      .open(v -> {
        should.assertTrue(v.succeeded());
        // verify
        client.send("cluster", Args.args("slots"), send -> {
          should.assertTrue(send.succeeded());
          System.out.println(send.result());
          test.complete();
        });
      });
  }

  @Test(timeout = 30_000)
  public void autoFindNodeByMOVEDAndASK(TestContext should) {
    final Async test = should.async();

    final List<SocketAddress> clusterHosts2 = new ArrayList<>(clusterHosts);
    // drop a node
    clusterHosts2.remove(random.nextInt(clusterHosts2.size()));
    // create a new client
    final Redis client2 = Cluster
      .create(rule.vertx(), clusterHosts2);

    client2
      .open(v -> {
        should.assertTrue(v.succeeded());
        // verify
        client2.send("cluster", Args.args("slots"), send -> {
          should.assertTrue(send.succeeded());
          System.out.println(send.result());

          final AtomicInteger counter = new AtomicInteger(16384);

          (new Runnable() {
            @Override
            public void run() {
              final int id = counter.decrementAndGet();
              client2.send("set", Args.key("" + id).add(id), set -> {
                should.assertTrue(set.succeeded());
                client2.send("get", Args.key("" + id), get -> {
                  should.assertTrue(get.succeeded());
                  // debug
                  if (get.result().asInteger() % 500 == 0) {
                    System.out.print(".");
                    System.out.flush();
                  }
                  // close step
                  if (counter.get() == 0) {
                    test.complete();
                  } else {
                    // re-run
                    run();
                  }
                });
              });
            }
          }).run();
        })
        // some debug
        .exceptionHandler(Throwable::printStackTrace);
      });
  }
}
