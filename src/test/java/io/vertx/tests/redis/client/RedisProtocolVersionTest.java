package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.ProtocolVersion;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisProtocolVersionTest {
  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void resp2(VertxTestContext test) {
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setPreferredProtocolVersion(ProtocolVersion.RESP2);

    Redis client = Redis.createClient(context.vertx(), options);

    client
      .send(Request.cmd(Command.DEL).arg("myhash"))
      .compose(ignored -> {
        return client.send(Request.cmd(Command.HSET).arg("myhash").arg("field1").arg(1).arg("field2").arg(2));
      })
      .compose(response -> {
        assertEquals(2, response.toInteger());
        return client.send(Request.cmd(Command.HGETALL).arg("myhash"));
      })
      .onComplete(test.succeeding(response -> {
        assertTrue(response.isArray());

        assertTrue(response.containsKey("field1"));
        assertEquals(1, response.get("field1").toInteger());

        assertTrue(response.containsKey("field2"));
        assertEquals(2, response.get("field2").toInteger());

        assertEquals(new HashSet<>(Arrays.asList("field1", "field2")), response.getKeys());

        assertEquals("field1", response.get(0).toString());

        client.close().onComplete(test.succeedingThenComplete());
      }));
  }

  @Test
  public void resp3(VertxTestContext test) {
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setPreferredProtocolVersion(ProtocolVersion.RESP3);

    Redis client = Redis.createClient(context.vertx(), options);

    client
      .send(Request.cmd(Command.DEL).arg("myhash"))
      .compose(ignored -> {
        return client.send(Request.cmd(Command.HSET).arg("myhash").arg("field1").arg(3).arg("field2").arg(4));
      })
      .compose(response -> {
        assertEquals(2, response.toInteger());
        return client.send(Request.cmd(Command.HGETALL).arg("myhash"));
      })
      .onComplete(test.succeeding(response -> {
        assertTrue(response.isMap());

        assertTrue(response.containsKey("field1"));
        assertEquals(3, response.get("field1").toInteger());

        assertTrue(response.containsKey("field2"));
        assertEquals(4, response.get("field2").toInteger());

        assertEquals(new HashSet<>(Arrays.asList("field1", "field2")), response.getKeys());

        try {
          response.get(0);
          fail("Map-typed Multi should fail on get(int)");
        } catch (Exception expected) {
        }

        client.close().onComplete(test.succeedingThenComplete());
      }));
  }
}
