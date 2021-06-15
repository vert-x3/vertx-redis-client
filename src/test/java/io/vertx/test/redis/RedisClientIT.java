package io.vertx.test.redis;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import org.junit.*;
import org.junit.runner.RunWith;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisClientIT {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  @Ignore("This requires a proper Azure account")
  public void azureTest(TestContext should) {
    final Async test = should.async();

    String cacheHostname = System.getenv("REDISCACHEHOSTNAME");
    String cachekey = System.getenv("REDISCACHEKEY");

    Redis client = Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setNoHello(true)
        .setPassword(cachekey)
        .setConnectionString("redis://" + cacheHostname + ":6380"));

    client.connect(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      onConnect.result()
        .send(cmd(KEYS).arg("*"))
        .onFailure(should::fail)
        .onSuccess(res -> {
          System.out.println(res);
          test.complete();
        });
    });
  }
}
