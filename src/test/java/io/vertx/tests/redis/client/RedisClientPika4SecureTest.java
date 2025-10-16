package io.vertx.tests.redis.client;

import io.vertx.core.json.JsonObject;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.HGETALL;
import static io.vertx.redis.client.Command.HSET;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// this is exactly the same as `RedisClientPikaSecureTest`, except of:
// - the image name uses the tag of `v4.0.2`
// - the config file is `pika4.conf`
@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientPika4SecureTest {

  @Container
  public static final GenericContainer<?> redis = new GenericContainer<>("pikadb/pika:v4.0.2")
    .withClasspathResourceMapping("pika4.conf", "/pika/conf/pika.conf", BindMode.READ_ONLY)
    .withExposedPorts(9221);

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  @BeforeEach
  public void before(VertxTestContext test) {
    client = Redis.createClient(
      context.vertx(),
      new RedisOptions().setConnectionString("redis://:userpass@" + redis.getHost() + ":" + redis.getFirstMappedPort()));

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
  public void testJson(VertxTestContext test) {
    final String mykey = randomKey();

    JsonObject json = new JsonObject()
      .put("key", "value");

    client.send(cmd(HSET).arg(mykey).arg(json))
      .compose(ignored -> client.send(cmd(HGETALL).arg(mykey)))
      .onComplete(test.succeeding(value -> {
        assertEquals("value", value.get("key").toString());
        test.completeNow();
      }));
  }
}
