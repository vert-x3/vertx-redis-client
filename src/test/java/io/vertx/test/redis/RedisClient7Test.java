package io.vertx.test.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.ACL;
import static io.vertx.redis.client.Command.CLIENT;
import static io.vertx.redis.client.Command.EVAL;
import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.HGETALL;
import static io.vertx.redis.client.Command.HSET;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Command.SUBSCRIBE;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.redis.client.test.TestUtils.randomKey;

@RunWith(VertxUnitRunner.class)
public class RedisClient7Test {

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis client;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    client = Redis.createClient(
      rule.vertx(),
      new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort() + "?client=tester"));

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

  @Test
  public void testBasicInterop(TestContext should) {
    final Async test = should.async();
    final String nonexisting = randomKey();
    final String mykey = randomKey();

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

  @Test
  public void testBoolean(TestContext should) {
    final Async test = should.async();
    final String key = randomKey();

    client.send(cmd(HSET).arg(key).arg("true").arg(true).arg("false").arg(false))
      .onFailure(should::fail)
      .onSuccess(ok -> {
        client.send(cmd(HGETALL).arg(key))
          .onFailure(should::fail)
          .onSuccess(hgetall -> {
            should.assertFalse(hgetall.get("false").toBoolean());
            should.assertTrue(hgetall.get("true").toBoolean());
            test.complete();
          });
      });
  }

  @Test(timeout = 10_000L)
  public void testJson(TestContext should) {
    final Async test = should.async();
    final String mykey = randomKey();

    JsonObject json = new JsonObject()
      .putNull("nullKey");

    client.send(cmd(HSET).arg(mykey).arg(json))
      .onSuccess(res -> {
        System.out.println(res);
        test.complete();
      })
      .onFailure(should::fail);
  }

  @Test(timeout = 10_000L)
  public void testACL(TestContext should) {
    final Async test = should.async();

    // create a alice user
    client.send(cmd(ACL).arg("SETUSER").arg("alice").arg("on").arg(">p1pp0").arg("~cached:*").arg("+get"))
      .onFailure(should::fail)
      .onSuccess(ok -> {
        // create a new client, this time using alice ACL
        Redis alice = Redis.createClient(
          rule.vertx(),
          new RedisOptions().setConnectionString("redis://alice:p1pp0@" + redis.getHost() + ":" + redis.getPort() + "?client=tester"));

        // connect should be fine
        alice.connect()
          .onFailure(should::fail)
          .onSuccess(conn -> {
            // there should not be allowed to GET
            conn.send(cmd(GET).arg("foo"))
              .onSuccess(res -> should.fail("This should fail with a NOPERM"))
              .onFailure(err -> {
                should.assertTrue(err instanceof ErrorType);
                ErrorType error = (ErrorType) err;
                should.assertTrue(error.is("NOPERM"));
                test.complete();
              });
          });

      });
  }

  @Test
  @Ignore
  public void perfRegression(TestContext should) {
    final Async test = should.async();

    int iterations = 1000;
    int instances = 100;
    AtomicInteger count = new AtomicInteger();

    rule.vertx()
      .deployVerticle(() -> new AbstractVerticle() {
        @Override
        public void start(Promise<Void> onStart) {
          Redis redisClient = Redis.createClient(rule.vertx(), new RedisOptions()
            .setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort() + "/0")
            .setMaxPoolSize(10)
            .setMaxPoolWaiting(10000));

          rule.vertx().eventBus()
            .consumer("test.redis.load")
            .handler(m -> {
              redisClient
                .send(cmd(SET).arg("foo").arg("bar"))
                .onSuccess(res -> {
                  if (count.incrementAndGet() == iterations * instances) {
                    test.complete();
                  }
                })
                .onFailure(should::fail);
            });

          onStart.complete();
        }
      }, new DeploymentOptions().setInstances(instances).setWorker(true))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          rule.vertx().eventBus().publish("test.redis.load", i);
        }
      });

  }

  @Test
  public void testBooleanVarArgs(TestContext should) {
    final Async test = should.async();
    final String key = randomKey();

    client.send(cmd(HSET, key, "true", true, "false", false))
      .onFailure(should::fail)
      .onSuccess(ok -> {
        client.send(cmd(HGETALL).arg(key))
          .onFailure(should::fail)
          .onSuccess(hgetall -> {
            should.assertFalse(hgetall.get("false").toBoolean());
            should.assertTrue(hgetall.get("true").toBoolean());
            test.complete();
          });
      });
  }

  @Test
  public void testNaN(TestContext should) {
    final Async test = should.async();
    final String key = randomKey();

    client.send(cmd(EVAL).arg("return tostring(0/0)").arg(0))
      .onFailure(should::fail)
      .onSuccess(ok -> {
        should.assertTrue(Double.isNaN(ok.toDouble()));
        test.complete();
      });
  }
}
