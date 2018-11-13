package io.vertx.test.redis;

import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.Args;
import io.vertx.redis.Cluster;
import io.vertx.redis.Redis;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@Ignore
@RunWith(VertxUnitRunner.class)
public class ClusterTest {

  private final Vertx vertx = Vertx.vertx();

  private Redis redis;

  @Test
  public void setUp(TestContext should) throws Exception {
    final Async test = should.async();

    redis = Cluster.create(vertx, Arrays.asList(
      SocketAddress.inetSocketAddress(7000, "127.0.0.1"),
      SocketAddress.inetSocketAddress(7001, "127.0.0.1"),
      SocketAddress.inetSocketAddress(7002, "127.0.0.1"),
      SocketAddress.inetSocketAddress(7003, "127.0.0.1"),
      SocketAddress.inetSocketAddress(7004, "127.0.0.1"),
      SocketAddress.inetSocketAddress(7005, "127.0.0.1")
    ));
    redis.exceptionHandler(should::fail);

    redis.open(open -> {
      should.assertTrue(open.succeeded());
      redis.send("SET", Args.args().key("foo").add("bar"), set -> {
        should.assertTrue(set.succeeded());
        test.complete();
      });
    });

    test.await();
  }
}
