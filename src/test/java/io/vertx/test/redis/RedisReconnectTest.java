package io.vertx.test.redis;

import io.vertx.core.Context;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisReconnectTest {

  private static final int RETRIES = 10;

  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis client;

  // this connection will mutate during tests with re-connect
  private RedisConnection connection;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    Context context = rule.vertx().getOrCreateContext();
    client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect().onComplete(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      should.assertEquals(context, rule.vertx().getOrCreateContext());
      connection = onConnect.result();
      before.complete();
    });
  }

  @After
  public void after() {
    client.close();
  }

  @Test
  public void testConnection(TestContext should) {
    final Async test = should.async();

    connection
      .exceptionHandler(should::fail)
      .endHandler(end -> {
        // the connection was closed, will reconnect
        reconnect(0);
      })
      .send(Request.cmd(Command.CLIENT).arg("LIST"))
      .onFailure(should::fail)
      .onSuccess(list -> {
        String res = list.toString();
        // this is a hack
        String id = res.substring(3, res.indexOf(' '));

        // kill the connection
        final RedisConnection orig = connection;

        connection
          .send(Request.cmd(Command.CLIENT).arg("KILL").arg("SKIPME").arg("no").arg("ID").arg(id))
          .onFailure(should::fail)
          .onSuccess(kill -> {
            should.assertEquals(1, kill.toInteger());
            // wait until the connection is updated
            rule.vertx()
              .setPeriodic(500, t -> {
                if (orig != connection) {
                  // when the 2 references change
                  // it means the connection has been replaced
                  test.complete();
                }
              });
          });

      });
  }

  private void reconnect(int retry) {
    if (retry < RETRIES) {
      // retry with backoff
      long backoff = (long) (Math.pow(2, Math.min(retry, RETRIES)) * RETRIES);

      rule
        .vertx()
        .setTimer(backoff, timer -> client.connect().onComplete(onReconnect -> {
          if (onReconnect.failed()) {
            reconnect(retry + 1);
          } else {
            connection = onReconnect.result();
          }
        }));
    }
  }
}
