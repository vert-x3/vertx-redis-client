package io.vertx.redis.client.test;

import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisSentinelTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setType(RedisClientType.SENTINEL)
        .addEndpoint(SocketAddress.inetSocketAddress(5000, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5001, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5002, "127.0.0.1"))
        .setMasterName("sentinel7000")
        .setRole(RedisRole.MASTER))
      .connect(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.INFO), info -> {
            should.assertTrue(info.succeeded());
            should.assertTrue(info.result().toString().contains("tcp_port:7000"));
            test.complete();
          });
      });
  }

  @Test
  public void testGetClientToSlave(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setType(RedisClientType.SENTINEL)
        .addEndpoint(SocketAddress.inetSocketAddress(5000, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5001, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5002, "127.0.0.1"))
        .setMasterName("sentinel7000")
        .setRole(RedisRole.SLAVE))
      .connect(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.INFO), info -> {
            should.assertTrue(info.succeeded());
            should.assertTrue(info.result().toString().contains("tcp_port:700"));
            test.complete();
          });
      });
  }

  @Test
  public void testGetClientToSentinel(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setType(RedisClientType.SENTINEL)
        .addEndpoint(SocketAddress.inetSocketAddress(5000, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5001, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5002, "127.0.0.1"))
        .setMasterName("sentinel7000")
        .setRole(RedisRole.SENTINEL))
      .connect(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.INFO), info -> {
            should.assertTrue(info.succeeded());
            String res = info.result().toString();
            System.out.println(res);
            should.assertTrue(res.contains("tcp_port:5000") || res.contains("tcp_port:5001") || res.contains("tcp_port:5002"));
            test.complete();
          });
      });
  }
}
