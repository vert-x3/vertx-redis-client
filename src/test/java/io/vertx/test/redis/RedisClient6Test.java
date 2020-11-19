package io.vertx.test.redis;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.UUID;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisClient6Test {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:6")
    .withExposedPorts(6379);

  private Redis client;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    client = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString("redis://" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort() + "?client=tester"));

    client.connect(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      onConnect.result().close();
      before.complete();
    });
  }

  @After
  public void after() {
    client.close();
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Test(timeout = 10_000L)
  public void testBasicInterop(TestContext should) {
    final Async test = should.async();
    final String nonexisting = makeKey();
    final String mykey = makeKey();

    client.send(cmd(GET).arg(nonexisting), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNull(reply0.result());

      client.send(cmd(SET).arg(mykey).arg("Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        client.send(cmd(GET).arg(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().toString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testAssistedCaching2Connections(TestContext should) {
    final Async test = should.async();

    // get connection #1
    client.connect()
      .onFailure(should::fail)
      .onSuccess(conn1 -> {
        conn1.send(cmd(CLIENT).arg("ID"))
          .onFailure(should::fail)
          .onSuccess(id -> {
            conn1
              .handler(push -> {
                if ("invalidate".equals(push.get(0).toString())) {
                  for (Response invalidated : push.get(1)) {
                    if ("foo".equals(invalidated.toString())) {
                      test.complete();
                    }
                  }
                }
              })
              .send(cmd(SUBSCRIBE).arg("__redis__:invalidate"))
              .onFailure(should::fail)
              .onSuccess(subscribe -> {
                // get connection #2
                client.connect()
                  .onFailure(should::fail)
                  .onSuccess(conn2 -> {
                    conn2.send(cmd(CLIENT).arg("TRACKING").arg("on").arg("REDIRECT").arg(id.toInteger()))
                      .onFailure(should::fail)
                      .onSuccess(redirect -> {
                        conn2.send(cmd(GET).arg("foo"))
                          .onFailure(should::fail)
                          .onSuccess(get0 -> {
                            System.out.println(get0);
                            // some other unrelated connection
                            client.connect()
                              .onFailure(should::fail)
                              .onSuccess(conn3 -> {
                                conn3.send(cmd(SET).arg("foo").arg("bar"))
                                  .onFailure(should::fail)
                                  .onSuccess(System.out::println);
                              });
                          });
                      });
                  });
              });
          });
      });
  }
}
