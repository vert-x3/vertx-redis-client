package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(VertxUnitRunner.class)
public class RedisSentinelTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    RedisSentinel sentinel = RedisSentinel.create(
      rule.vertx(),
      Arrays.asList(
        SocketAddress.inetSocketAddress(5000, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5001, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5002, "127.0.0.1")
      ));

    // get a connection to the master node
    sentinel.connect("sentinel7000", RedisRole.MASTER, open -> {
      should.assertTrue(open.succeeded());
      // query the info
      sentinel.send("INFO", info -> {
        should.assertTrue(info.succeeded());
        should.assertTrue(info.result().asString().contains("tcp_port:7000"));
        test.complete();
      });
    });
  }

  @Test
  public void testGetClientToSlave(TestContext should) {
    final Async test = should.async();

    RedisSentinel sentinel = RedisSentinel.create(
      rule.vertx(),
      Arrays.asList(
        SocketAddress.inetSocketAddress(5000, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5001, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5002, "127.0.0.1")
      ));

    // get a connection to the master node
    sentinel.connect("sentinel7000", RedisRole.SLAVE, open -> {
      should.assertTrue(open.succeeded());
      // query the info
      sentinel.send("INFO", info -> {
        should.assertTrue(info.succeeded());
        should.assertTrue(info.result().asString().contains("tcp_port:700"));
        test.complete();
      });
    });
  }

  @Test
  public void testGetClientToSentinel(TestContext should) {
    final Async test = should.async();

    RedisSentinel sentinel = RedisSentinel.create(
      rule.vertx(),
      Arrays.asList(
        SocketAddress.inetSocketAddress(5000, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5001, "127.0.0.1"),
        SocketAddress.inetSocketAddress(5002, "127.0.0.1")
      ));

    // get a connection to the master node
    sentinel.connect(RedisRole.SENTINEL, open -> {
      should.assertTrue(open.succeeded());
      // query the info
      sentinel.send("INFO", info -> {
        should.assertTrue(info.succeeded());
        String res = info.result().asString();
        System.out.println(res);
        should.assertTrue(res.contains("tcp_port:5000") || res.contains("tcp_port:5001") || res.contains("tcp_port:5002"));
        test.complete();
      });
    });
  }
}
