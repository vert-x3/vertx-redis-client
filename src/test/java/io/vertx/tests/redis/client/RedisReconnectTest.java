package io.vertx.tests.redis.client;

import io.vertx.core.Context;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisReconnectTest {

  private static final int RETRIES = 10;

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  // this connection will mutate during tests with re-connect
  private RedisConnection connection;

  @BeforeEach
  public void before(VertxTestContext test) {
    Context context = this.context.vertx().getOrCreateContext();
    client = Redis.createClient(this.context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect().onComplete(test.succeeding(conn -> {
      assertEquals(context, this.context.vertx().getOrCreateContext());
      connection = conn;
      test.completeNow();
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void testConnection(VertxTestContext test) {
    connection
      .exceptionHandler(test::failNow)
      .endHandler(end -> {
        // the connection was closed, will reconnect
        reconnect(0);
      })
      .send(Request.cmd(Command.CLIENT).arg("LIST"))
      .onComplete(test.succeeding(list -> {
        String res = list.toString();
        // this is a hack
        String id = res.substring(3, res.indexOf(' '));

        // kill the connection
        final RedisConnection orig = connection;

        connection
          .send(Request.cmd(Command.CLIENT).arg("KILL").arg("SKIPME").arg("no").arg("ID").arg(id))
          .onComplete(test.succeeding(kill -> {
            assertEquals(1, kill.toInteger());
            // wait until the connection is updated
            context.vertx()
              .setPeriodic(500, t -> {
                if (orig != connection) {
                  // when the 2 references change
                  // it means the connection has been replaced
                  test.completeNow();
                }
              });
          }));
      }));
  }

  private void reconnect(int retry) {
    if (retry < RETRIES) {
      // retry with backoff
      long backoff = (long) (Math.pow(2, Math.min(retry, RETRIES)) * RETRIES);

      context
        .vertx()
        .setTimer(backoff, timer -> {
          client.connect()
            .onSuccess(conn -> connection = conn)
            .onFailure(err -> reconnect(retry + 1));
        });
    }
  }
}
