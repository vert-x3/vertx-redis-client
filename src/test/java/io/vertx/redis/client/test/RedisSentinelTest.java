//package io.vertx.redis.client.test;
//
//import io.vertx.core.net.SocketAddress;
//import io.vertx.ext.unit.Async;
//import io.vertx.ext.unit.TestContext;
//import io.vertx.ext.unit.junit.RunTestOnContext;
//import io.vertx.ext.unit.junit.VertxUnitRunner;
//import io.vertx.redis.*;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import redis.embedded.RedisSentinel;
//import redis.embedded.RedisServer;
//
//import java.net.InetAddress;
//import java.util.Arrays;
//
//@RunWith(VertxUnitRunner.class)
//public class RedisSentinelTest {
//
//  private static String host;
//
//  private static RedisServer master;
//  private static RedisServer slave;
//  private static RedisSentinel[] sentinels = new RedisSentinel[3];
//
//  @Rule
//  public RunTestOnContext rule = new RunTestOnContext();
//
//  @BeforeClass
//  public static void setUp() throws Exception {
//    /*
//     * Test setup is
//     *
//     * Sentinel S1 -> Redis Master M
//     * Sentinel S2 -> Redis Master M
//     * Sentinel S3 -> Redis Master M
//     *
//     * Redis Master M -> Redis Slave S
//     */
//
//    // redis slave <-> master sync doesn't work with 127.0.0.1/localhost.
//    // hence getting ip address.
//    host = InetAddress.getLocalHost().getHostAddress();
//
//    // wrap Redis Master
//    master = RedisServer.builder()
//      .port(6379)
//      .setting("loglevel debug")
//      .build();
//
//    master.start();
//
//    // wrap Redis Slave
//    slave = RedisServer.builder()
//      .port(6379 + 1)
//      .setting("loglevel debug")
//      .slaveOf(host, 6379)
//      .build();
//
//    slave.start();
//
//    // wrap sentinels
//    for (int i = 0; i < sentinels.length; i++) {
//      sentinels[i] = RedisSentinel.builder()
//        .port(26739 + i)
//        .setting(String.format("sentinel monitor %s %s %s 1", "mymaster", host, 6379))
//        .setting(String.format("sentinel down-after-milliseconds %s 200", "mymaster"))
//        .setting(String.format("sentinel failover-timeout %s 1000", "mymaster"))
//        .build();
//
//      sentinels[i].start();
//    }
//  }
//
//  @AfterClass
//  public static void tearDown() {
//    for (int i = 0; i < sentinels.length; i++) {
//      sentinels[i].stop();
//    }
//    slave.stop();
//    master.stop();
//  }
//
//  @Test
//  public void testGetClientToMaster(TestContext should) {
//    final Async test = should.async();
//
//    Sentinel sentinel = Sentinel.create(
//      rule.vertx(),
//      Arrays.asList(
//        SocketAddress.inetSocketAddress(26739, host),
//        SocketAddress.inetSocketAddress(26740, host),
//        SocketAddress.inetSocketAddress(26741, host)
//      ));
//
//    // get a connection to the master node
//    sentinel.open(RedisRole.MASTER, open -> {
//      should.assertTrue(open.succeeded());
//      // query the info
//      open.result().send("INFO", info -> {
//        should.assertTrue(info.succeeded());
//        should.assertTrue(info.result().asString().contains("tcp_port:6379"));
//        test.complete();
//      });
//    });
//  }
//
//  @Test
//  public void testGetClientToSlave(TestContext should) {
//    final Async test = should.async();
//
//    Sentinel sentinel = Sentinel.create(
//      rule.vertx(),
//      Arrays.asList(
//        SocketAddress.inetSocketAddress(26739, host),
//        SocketAddress.inetSocketAddress(26740, host),
//        SocketAddress.inetSocketAddress(26741, host)
//      ));
//
//    // get a connection to the master node
//    sentinel.open(RedisRole.SLAVE, open -> {
//      should.assertTrue(open.succeeded());
//      // query the info
//      open.result().send("INFO", info -> {
//        should.assertTrue(info.succeeded());
//        should.assertTrue(info.result().asString().contains("tcp_port:6380"));
//        test.complete();
//      });
//    });
//  }
//
//  @Test
//  public void testGetClientToSentinel(TestContext should) {
//    final Async test = should.async();
//
//    Sentinel sentinel = Sentinel.create(
//      rule.vertx(),
//      Arrays.asList(
//        SocketAddress.inetSocketAddress(26739, host),
//        SocketAddress.inetSocketAddress(26740, host),
//        SocketAddress.inetSocketAddress(26741, host)
//      ));
//
//    // get a connection to the master node
//    sentinel.open(RedisRole.SENTINEL, open -> {
//      should.assertTrue(open.succeeded());
//      // query the info
//      open.result().send("INFO", info -> {
//        should.assertTrue(info.succeeded());
//        String res = info.result().asString();
//        should.assertTrue(res.contains("tcp_port:26739") || res.contains("tcp_port:26740") || res.contains("tcp_port:26741"));
//        test.complete();
//      });
//    });
//  }
//}
