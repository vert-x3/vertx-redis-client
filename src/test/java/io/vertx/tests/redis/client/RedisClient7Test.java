package io.vertx.tests.redis.client;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.ThreadingModel;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.ErrorType;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClient7Test {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  @BeforeEach
  public void before(VertxTestContext test) {
    client = Redis.createClient(
      context.vertx(),
      new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort() + "?client=tester"));

    client.connect().onComplete(test.succeeding(conn -> {
      conn.close().onComplete(test.succeedingThenComplete());
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void testBasicInterop(VertxTestContext test) {
    final String nonexisting = randomKey();
    final String mykey = randomKey();

    client.send(cmd(GET).arg(nonexisting))
      .compose(reply0 -> {
        assertNull(reply0);
        return client.send(cmd(SET).arg(mykey).arg("Hello"));
      })
      .compose(reply1 -> {
        return client.send(cmd(GET).arg(mykey));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testAssistedCaching2Connections(VertxTestContext test) {
    // get connection #1
    client.connect()
      .onFailure(test::failNow)
      .onSuccess(conn1 -> {
        conn1.send(cmd(CLIENT).arg("ID"))
          .onFailure(test::failNow)
          .onSuccess(id -> {
            conn1
              .handler(push -> {
                if ("invalidate".equals(push.get(0).toString())) {
                  for (Response invalidated : push.get(1)) {
                    if ("foo".equals(invalidated.toString())) {
                      test.completeNow();
                    }
                  }
                }
              })
              .send(cmd(SUBSCRIBE).arg("__redis__:invalidate"))
              .onFailure(test::failNow)
              .onSuccess(subscribe -> {
                // get connection #2
                client.connect()
                  .onFailure(test::failNow)
                  .onSuccess(conn2 -> {
                    conn2.send(cmd(CLIENT).arg("TRACKING").arg("on").arg("REDIRECT").arg(id.toInteger()))
                      .onFailure(test::failNow)
                      .onSuccess(redirect -> {
                        conn2.send(cmd(GET).arg("foo"))
                          .onFailure(test::failNow)
                          .onSuccess(get0 -> {
                            System.out.println(get0);
                            // some other unrelated connection
                            client.connect()
                              .onFailure(test::failNow)
                              .onSuccess(conn3 -> {
                                conn3.send(cmd(SET).arg("foo").arg("bar"))
                                  .onFailure(test::failNow)
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
  public void testBoolean(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(HSET).arg(key).arg("true").arg(true).arg("false").arg(false))
      .compose(reply0 -> {
        return client.send(cmd(HGETALL).arg(key));
      })
      .onComplete(test.succeeding(reply1 -> {
        assertFalse(reply1.get("false").toBoolean());
        assertTrue(reply1.get("true").toBoolean());
        test.completeNow();
      }));
  }

  @Test
  public void testBooleanVarArgs(VertxTestContext test) {
    final String key = randomKey();

    client.send(Request.cmd(HSET, key, "true", true, "false", false))
      .compose(reply0 -> {
        return client.send(cmd(HGETALL).arg(key));
      })
      .onComplete(test.succeeding(reply1 -> {
        assertFalse(reply1.get("false").toBoolean());
        assertTrue(reply1.get("true").toBoolean());
        test.completeNow();
      }));
  }

  @Test
  public void testJson(VertxTestContext test) {
    final String mykey = randomKey();

    JsonObject json = new JsonObject()
      .put("key", "value");

    client.send(cmd(HSET).arg(mykey).arg(json))
      .compose(reply0 -> client.send(cmd(HGETALL).arg(mykey)))
      .onComplete(test.succeeding(reply1 -> {
        assertEquals("value", reply1.get("key").toString());
        test.completeNow();
      }));
  }

  @Test
  public void testACL(VertxTestContext test) {
    // create a alice user
    client.send(cmd(ACL).arg("SETUSER").arg("alice").arg("on").arg(">p1pp0").arg("~cached:*").arg("+get"))
      .onFailure(test::failNow)
      .onSuccess(ok -> {
        // create a new client, this time using alice ACL
        Redis alice = Redis.createClient(
          context.vertx(),
          new RedisOptions().setConnectionString("redis://alice:p1pp0@" + redis.getHost() + ":" + redis.getPort() + "?client=tester"));

        // connect should be fine
        alice.connect()
          .onFailure(test::failNow)
          .onSuccess(conn -> {
            // there should not be allowed to GET
            conn.send(cmd(GET).arg("foo"))
              .onSuccess(res -> test.failNow("This should fail with a NOPERM"))
              .onFailure(err -> {
                assertInstanceOf(ErrorType.class, err);
                ErrorType error = (ErrorType) err;
                assertTrue(error.is("NOPERM"));
                test.completeNow();
              });
          });

      });
  }

  @Test
  @Disabled
  public void perfRegression(VertxTestContext test) {
    int iterations = 1000;
    int instances = 100;
    AtomicInteger count = new AtomicInteger();

    context.vertx()
      .deployVerticle(() -> new VerticleBase() {
        @Override
        public Future<?> start() throws Exception {
          Redis redisClient = Redis.createClient(RedisClient7Test.this.context.vertx(), new RedisOptions()
            .setConnectionString("redis://" + redis.getHost() + ":" + redis.getPort() + "/0")
            .setMaxPoolSize(10)
            .setMaxPoolWaiting(10000));

          RedisClient7Test.this.context.vertx().eventBus()
            .consumer("test.redis.load")
            .handler(m -> {
              redisClient
                .send(cmd(SET).arg("foo").arg("bar"))
                .onSuccess(res -> {
                  if (count.incrementAndGet() == iterations * instances) {
                    test.completeNow();
                  }
                })
                .onFailure(test::failNow);
            });

          return super.start();
        }
      }, new DeploymentOptions().setInstances(instances).setThreadingModel(ThreadingModel.WORKER))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          context.vertx().eventBus().publish("test.redis.load", i);
        }
      });

  }

  @Test
  public void testNaN(VertxTestContext test) {
    client.send(cmd(EVAL).arg("return tostring(0/0)").arg(0))
      .onComplete(test.succeeding(reply0 -> {
        assertTrue(Double.isNaN(reply0.toDouble()));
        test.completeNow();
      }));
  }
}
