package io.vertx.redis.impl;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.impl.RedisURI;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisClientImplTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private Redis client;

  @Test
  @SuppressWarnings("deprecation")
  public void testPasswordWithEncodeInQuery() {
    Vertx vertx = Vertx.vertx();
    String auth = "abc#123";
    RedisOptions options = new RedisOptions();
    options.setAuth(auth);
    RedisClientImpl client = new RedisClientImpl(vertx, options);
    RedisURI redisURI = new RedisURI(client.getConnectionString());
    Assert.assertEquals("Password is not correct", auth, redisURI.password());
    vertx.close();
  }

  @Test
  public void testUsernameIsNotDefault(TestContext testCxt) {

    final Async before = testCxt.async();

    client = Redis.createClient(rule.vertx(), new io.vertx.redis.client.RedisOptions().setConnectionString("redis://USER_NOT_DEFAULT:pwd@localhost:7002"));
    client.connect(onConnect -> {
      testCxt.assertTrue(onConnect.failed());
      testCxt.assertTrue(onConnect.cause().toString().contains("WRONGPASS invalid username-password"));
      before.complete();
    });
  }
}
