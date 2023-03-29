package io.vertx.test.redis;

import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

@RunWith(VertxUnitRunner.class)
public class RedisPubSubTest {

  @ClassRule
  public static final GenericContainer<?> redis = new FixedHostPortGenericContainer<>("grokzen/redis-cluster:6.2.0")
    .withEnv("IP", "0.0.0.0")
    .withEnv("STANDALONE", "true")
    .withEnv("SENTINEL", "true")
    .withExposedPorts(7000, 7001, 7002, 7003, 7004, 7005, 7006, 7007, 5000, 5001, 5002)
    // cluster ports (7000-7005) 6x (master+replica) 3 nodes
    .withFixedExposedPort(7000, 7000)
    .withFixedExposedPort(7001, 7001)
    .withFixedExposedPort(7002, 7002)
    .withFixedExposedPort(7003, 7003)
    .withFixedExposedPort(7004, 7004)
    .withFixedExposedPort(7005, 7005)
    // standalone ports (7006-7007) 2x
    .withFixedExposedPort(7006, 7006)
    .withFixedExposedPort(7007, 7007)
    // sentinel ports (5000-5002) 3x (match the cluster master nodes)
    .withFixedExposedPort(5000, 5000)
    .withFixedExposedPort(5001, 5001)
    .withFixedExposedPort(5002, 5002);

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext(new VertxOptions().setEventLoopPoolSize(1));

  private Redis redisPublish;
  private Redis redisSubscribe;

  private RedisConnection pubConn;
  private RedisConnection subConn;

  @Before
  public void before(TestContext should) {
    Async test = should.async();
    RedisOptions options = new RedisOptions()
      .setConnectionString("redis://localhost:7001")
      .setType(RedisClientType.CLUSTER);
    redisPublish = Redis.createClient(rule.vertx(), options)
      .connect(connectPub -> {
        should.assertTrue(connectPub.succeeded());

        pubConn = connectPub.result();

        redisSubscribe = Redis.createClient(rule.vertx(), options)
          .connect(connectSub -> {
            should.assertTrue(connectSub.succeeded());

            subConn = connectSub.result();
            test.complete();
          });
      });
  }

  @After
  public void after(TestContext should) {
    redisPublish.close();
    redisSubscribe.close();
  }

  @Test
  public void testSubscribeMultipleTimes(TestContext should){
    int N = 10;
    String CHAN="chan1";
    final Async test = should.async(N);
    for (int i = 0; i < N; i++)
      rule.vertx().eventBus().consumer(CHAN, msg -> {
        test.countDown();
      });
    rule.vertx().eventBus().consumer("io.vertx.redis." + CHAN, msg -> {
      System.out.println("received redis msg " + msg.body());
      rule.vertx().eventBus().publish(CHAN, msg.body());
    });
    subUnsub(CHAN, N, should.async(N));
    rule.vertx().setTimer(1000, id ->
      pubConn.send(Request.cmd(Command.PUBLISH).arg(CHAN).arg("hello"), preply -> should.assertTrue(preply.succeeded())));
  }

  private void subUnsub(String channel, int attempts, Async testSub){
    subConn.send(Request.cmd(Command.UNSUBSCRIBE).arg(channel), unreply -> {
      subConn.send(Request.cmd(Command.SUBSCRIBE).arg(channel), reply -> {
        testSub.countDown();
        if (attempts > 1) {
          subUnsub(channel, attempts - 1, testSub);
        }
      });
    });
  }
}
