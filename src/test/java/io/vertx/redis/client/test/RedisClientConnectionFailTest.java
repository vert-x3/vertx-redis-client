package io.vertx.redis.client.test;

import io.vertx.core.Future;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.INFO;
import static io.vertx.redis.client.Request.cmd;

@RunWith(VertxUnitRunner.class)
public class RedisClientConnectionFailTest {
  @ClassRule
  public static final RedisStandalone redis = RedisStandalone.builder().setPassword("foobar").build();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void closeSocketOnInitError(TestContext test) {
    Async async = test.async();

    AtomicInteger attempts = new AtomicInteger(0);

    Future<RedisConnection> failedFuture = Future.failedFuture("initial failure, to be recovered");

    for (int i = 0; i < 5; i++) {
      failedFuture.recover(ex -> {
        attempts.incrementAndGet();
        Redis client = Redis.createClient(rule.vertx(),
          new RedisOptions().setConnectionString("redis://:wrong_password@" + redis.getHost() + ":" + redis.getPort()));
        return client.connect();
      });
    }

    failedFuture.recover(ex -> {
        attempts.incrementAndGet();
        Redis client = Redis.createClient(rule.vertx(),
          new RedisOptions().setConnectionString("redis://:foobar@" + redis.getHost() + ":" + redis.getPort()));
        return client.connect();
      })
      .compose(conn -> {
        return rule.vertx().timer(1, TimeUnit.SECONDS).map(ignored -> conn);
      })
      .compose(conn -> {
        return conn.send(cmd(INFO).arg("clients"));
      })
      .onComplete(test.asyncAssertSuccess(resp -> {
        test.assertEquals(6, attempts.intValue());
        Optional<String> connectedClients = Arrays.stream(resp.toString().split("\n"))
          .filter(line -> line.startsWith("connected_clients:"))
          .findFirst();
        if (connectedClients.isPresent()) {
          test.assertEquals(1, Integer.parseInt(connectedClients.get().split(":")[1].trim()));
          async.complete();
        } else {
          test.fail("connected_clients field not found in INFO clients reply");
        }
      }));
  }

  @Test
  public void closeSocketOnUpgradeSslError(TestContext test) {
    Async async = test.async();

    String connstr = ":foobar@" + redis.getHost() + ":" + redis.getPort();

    Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("rediss://" + connstr))
      .connect()
      .recover(ex -> {
        Redis client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://" + connstr));
        return client.connect();
      })
      .compose(conn -> {
        return rule.vertx().timer(1, TimeUnit.SECONDS).map(ignored -> conn);
      })
      .compose(conn -> {
        return conn.send(cmd(INFO).arg("clients"));
      })
      .onComplete(test.asyncAssertSuccess(resp -> {
        Optional<String> connectedClients = Arrays.stream(resp.toString().split("\n"))
          .filter(line -> line.startsWith("connected_clients:"))
          .findFirst();
        if (connectedClients.isPresent()) {
          test.assertEquals(1, Integer.parseInt(connectedClients.get().split(":")[1].trim()));
          async.complete();
        } else {
          test.fail("connected_clients field not found in INFO clients reply");
        }
      }));
  }
}
