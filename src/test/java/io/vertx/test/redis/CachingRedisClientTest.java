package io.vertx.test.redis;

import io.vertx.core.Context;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.impl.CachingRedis;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.UUID;

@RunWith(VertxUnitRunner.class)
public class CachingRedisClientTest {

  @ClassRule
  public static final GenericContainer<?> container = new GenericContainer<>("redis:6.0.6")
    .withExposedPorts(6379);

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  private Redis client;
  private RedisAPI api;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    Context context = rule.vertx().getOrCreateContext();
    client = Redis.createClient(rule.vertx(), new RedisOptions()
      .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort())
      .setCacheEnabled(true));

    client.connect().onComplete(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      should.assertEquals(context, rule.vertx().getOrCreateContext());
      api = RedisAPI.api(client);
      before.complete();
    });
  }

  @After
  public void after() {
    client.close();
  }

  @Test
  public void testSimpleSetAndGet(TestContext should) {
    final Async test = should.async();
    final String key = UUID.randomUUID().toString();

    api
      .send(Command.SET, key, "hello, world")
      .onComplete(setResponse -> {
        should.assertTrue(setResponse.succeeded());

        api
          .get(key)
          .onComplete(get1Response -> {
            should.assertTrue(get1Response.succeeded());

            final Response response = get1Response.result();

            should.assertEquals(response.toString(), "hello, world");

            // The cache impl stores the actual object, so we can assert it's from cache by
            // asserting a subsequent get returns the same object
            api
              .get(key)
              .onComplete(get2Response -> {
                should.assertTrue(get2Response.succeeded());
                should.assertEquals(get2Response.result().toString(), "hello, world");
                should.assertTrue(get2Response.result() == response);
                test.complete();
              });
          });
      });
  }

  @Test
  public void testInvalidation(TestContext should) {
    final Async test = should.async();
    final String key = UUID.randomUUID().toString();

    ((CachingRedis) client).invalidationHandler(keys -> {
      should.assertEquals(keys.size(), 1);
      test.complete();
    });

    api.send(Command.SET, key, "hello, world").onComplete(set1Response -> {
      should.assertTrue(set1Response.succeeded());

      api.get(key).onComplete(get1Response -> {
        should.assertTrue(get1Response.succeeded());

        final Response response = get1Response.result();

        should.assertEquals(response.toString(), "hello, world");

        // The cache impl stores the actual object, so we can assert it's from cache by
        // asserting a subsequent get returns the same object
        api.get(key).onComplete(get2Response -> {
          should.assertTrue(get2Response.succeeded());
          should.assertEquals(get2Response.result().toString(), "hello, world");
          should.assertTrue(get2Response.result() == response);

          api.send(Command.SET, key, "new value").onComplete(set2Response -> {
            should.assertTrue(set2Response.succeeded());
          });
        });
      });
    });
  }
}
