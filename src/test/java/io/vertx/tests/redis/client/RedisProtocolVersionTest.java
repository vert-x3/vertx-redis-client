package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.ProtocolVersion;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(VertxUnitRunner.class)
public class RedisProtocolVersionTest {
  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void resp2(TestContext test) {
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setPreferredProtocolVersion(ProtocolVersion.RESP2);

    Redis client = Redis.createClient(rule.vertx(), options);

    Async async = test.async();
    client
      .send(Request.cmd(Command.DEL).arg("myhash"))
      .flatMap(ignored -> {
        return client.send(Request.cmd(Command.HSET).arg("myhash").arg("field1").arg(1).arg("field2").arg(2));
      })
      .flatMap(response -> {
        test.assertEquals(2, response.toInteger());
        return client.send(Request.cmd(Command.HGETALL).arg("myhash"));
      })
      .onSuccess(response -> {
        test.assertTrue(response.isArray());

        test.assertTrue(response.containsKey("field1"));
        test.assertEquals(1, response.get("field1").toInteger());

        test.assertTrue(response.containsKey("field2"));
        test.assertEquals(2, response.get("field2").toInteger());

        test.assertEquals(new HashSet<>(Arrays.asList("field1", "field2")), response.getKeys());

        test.assertEquals("field1", response.get(0).toString());

        client.close();
        async.complete();
      }).onFailure(test::fail);
  }

  @Test
  public void resp3(TestContext test) {
    RedisOptions options = new RedisOptions()
      .setConnectionString(redis.getRedisUri())
      .setPreferredProtocolVersion(ProtocolVersion.RESP3);

    Redis client = Redis.createClient(rule.vertx(), options);

    Async async = test.async();
    client
      .send(Request.cmd(Command.DEL).arg("myhash"))
      .flatMap(ignored -> {
        return client.send(Request.cmd(Command.HSET).arg("myhash").arg("field1").arg(3).arg("field2").arg(4));
      })
      .flatMap(response -> {
        test.assertEquals(2, response.toInteger());
        return client.send(Request.cmd(Command.HGETALL).arg("myhash"));
      })
      .onSuccess(response -> {
        test.assertTrue(response.isMap());

        test.assertTrue(response.containsKey("field1"));
        test.assertEquals(3, response.get("field1").toInteger());

        test.assertTrue(response.containsKey("field2"));
        test.assertEquals(4, response.get("field2").toInteger());

        test.assertEquals(new HashSet<>(Arrays.asList("field1", "field2")), response.getKeys());

        try {
          response.get(0);
          test.fail("Map-typed Multi should fail on get(int)");
        } catch (Exception expected) {
        }

        client.close();
        async.complete();
      }).onFailure(test::fail);
  }
}
