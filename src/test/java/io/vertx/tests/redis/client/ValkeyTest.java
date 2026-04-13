package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.ValkeyAPI;
import io.vertx.tests.redis.containers.ValkeyContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class ValkeyTest {

  @Container
  public static final ValkeyContainer valkey = new ValkeyContainer();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;
  private ValkeyAPI api;

  @BeforeEach
  public void before() {
    client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(valkey.getValkeyUri()));
    api = ValkeyAPI.api(client);
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void ping_lowLevel(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(response -> {
          assertNotNull(response);
          assertEquals("PONG", response.toString());
          test.completeNow();
        }));
      }));
  }

  @Test
  public void ping_highLevel(VertxTestContext test) {
    api.ping(List.of()).onComplete(test.succeeding(response -> {
      assertNotNull(response);
      assertEquals("PONG", response.toString());
      test.completeNow();
    }));
  }

  @Test
  public void setAndGet_lowLevel(VertxTestContext test) {
    String key = randomKey();
    client.connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("hello"))
          .compose(ignored -> conn.send(Request.cmd(Command.GET).arg(key)))
          .onComplete(test.succeeding(response -> {
            assertEquals("hello", response.toString());
            test.completeNow();
          }));
      }));
  }

  @Test
  public void setAndGet_highLevel(VertxTestContext test) {
    String key = randomKey();
    api.set(List.of(key, "hello"))
      .compose(ignored -> api.get(key))
      .onComplete(test.succeeding(response -> {
        assertEquals("hello", response.toString());
        test.completeNow();
      }));
  }

  @Test
  public void delifeq_lowLevel(VertxTestContext test) {
    String key = randomKey();
    client.connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg("hello"))
          .compose(ignored -> conn.send(Request.cmd(Command.DELIFEQ).arg(key).arg("wrong")))
          .compose(response -> {
            assertEquals(0, response.toInteger());
            return conn.send(Request.cmd(Command.DELIFEQ).arg(key).arg("hello"));
          })
          .compose(response -> {
            assertEquals(1, response.toInteger());
            return conn.send(Request.cmd(Command.GET).arg(key));
          })
          .onComplete(test.succeeding(response -> {
            assertNull(response);
            test.completeNow();
          }));
      }));
  }

  @Test
  public void delifeq_highLevel(VertxTestContext test) {
    String key = randomKey();
    api.set(List.of(key, "hello"))
      .compose(ignored -> api.delifeq(key, "wrong"))
      .compose(response -> {
        assertEquals(0, response.toInteger());
        return api.delifeq(key, "hello");
      })
      .compose(response -> {
        assertEquals(1, response.toInteger());
        return api.get(key);
      })
      .onComplete(test.succeeding(response -> {
        assertNull(response);
        test.completeNow();
      }));
  }
}
