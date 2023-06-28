package io.vertx.test.redis;

import static io.vertx.redis.client.Command.ACL;
import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Request.cmd;

import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;

@RunWith(VertxUnitRunner.class)
public class RedisClientChangeCredentialsTest {

  private static final Network network = Network.newNetwork();

  @Rule
  public final GenericContainer<?> redis =
    new GenericContainer<>("redis:7-alpine")
      .withExposedPorts(6379)
      .withNetwork(network)
      .withCommand("redis-server --port 6379 --cluster-enabled no --cluster-config-file nodes.conf --cluster-node-timeout 5000 --appendonly yes --requirepass test");

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Before
  public void before() {
    redis.followOutput(outputFrame -> System.out.println(outputFrame.getUtf8String()));
  }

  private Redis createClient(final GenericContainer<?> container, final String password) {
    return createClient(container, null, password);
  }

  private Redis createClient(final GenericContainer<?> container, final String username, final String password) {
    final RedisOptions options = new RedisOptions()
      .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort());
    return createClient(options,
      Future.succeededFuture(new RedisOptions(options).setUser(username).setPassword(password)));
  }

  private Redis createClient(final GenericContainer<?> container, Future<RedisOptions> optionsFuture) {
    final RedisOptions options = new RedisOptions()
      .setConnectionString("redis://" + container.getHost() + ":" + container.getFirstMappedPort());
    return createClient(options, optionsFuture);
  }

  private Redis createClient(final RedisOptions options, Future<RedisOptions> optionsFuture) {
    return Redis.createClient(rule.vertx(), options, () -> optionsFuture);
  }

  @Test(timeout = 10_000L)
  public void testSimpleAuth(TestContext should) {
    final Async test = should.async();
    final String nonexisting = makeKey();
    final String mykey = makeKey();

    final Redis client = createClient(redis, "test");

    client.send(cmd(GET).arg(nonexisting), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNull(reply0.result());

      client.send(cmd(SET).arg(mykey).arg("Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        client.send(cmd(GET).arg(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().toString());
          test.complete();
        });
      });
    });
  }

  @Test(timeout = 10_000L)
  public void testWithUser(TestContext should) {
    final Async test = should.async();
    final String nonexisting = makeKey();
    final String mykey = makeKey();

    final Redis client = createClient(redis, "test");

    client.send(cmd(ACL).arg("SETUSER").arg("alice").arg("on").arg(">p1pp0").arg("~*").arg("+get").arg("+set"))
      .onFailure(should::fail).onSuccess(ok -> {
        // create a new client, this time using alice ACL
        Redis alice = createClient(redis, "alice", "p1pp0");

        alice.send(cmd(GET).arg(nonexisting), reply0 -> {
          should.assertTrue(reply0.succeeded());
          should.assertNull(reply0.result());

          alice.send(cmd(SET).arg(mykey).arg("Hello"), reply1 -> {
            should.assertTrue(reply1.succeeded());
            alice.send(cmd(GET).arg(mykey), reply2 -> {
              should.assertTrue(reply2.succeeded());
              should.assertEquals("Hello", reply2.result().toString());
              test.complete();
            });
          });
        });
      });
  }

  @Test(timeout = 10_000L)
  public void testFailure(TestContext should) {
    final Async test = should.async();
    final String nonexisting = makeKey();

    final IllegalStateException failure = new IllegalStateException("Simulate change credentials failure");

    final Redis client = createClient(redis, Future.failedFuture(failure));

    client.send(cmd(GET).arg(nonexisting), reply0 -> {
      should.assertTrue(reply0.failed());
      should.assertNull(reply0.result());
      should.assertEquals(failure, reply0.cause());
      test.complete();
    });
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }
}
