package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.INFO;
import static io.vertx.redis.client.Request.cmd;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientConnectionFailTest {
  @Container
  public static final RedisStandalone redis = RedisStandalone.builder().setPassword("foobar").build();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void closeSocketOnInitError(VertxTestContext test) {
    AtomicInteger attempts = new AtomicInteger(0);

    Future<RedisConnection> failedFuture = Future.failedFuture("initial failure, to be recovered");

    for (int i = 0; i < 5; i++) {
      failedFuture.recover(ex -> {
        attempts.incrementAndGet();
        Redis client = Redis.createClient(context.vertx(),
          new RedisOptions().setConnectionString("redis://:wrong_password@" + redis.getHost() + ":" + redis.getPort()));
        return client.connect();
      });
    }

    failedFuture.recover(ex -> {
        attempts.incrementAndGet();
        Redis client = Redis.createClient(context.vertx(),
          new RedisOptions().setConnectionString("redis://:foobar@" + redis.getHost() + ":" + redis.getPort()));
        return client.connect();
      })
      .compose(conn -> {
        return context.vertx().timer(1, TimeUnit.SECONDS).map(ignored -> conn);
      })
      .compose(conn -> {
        return conn.send(cmd(INFO).arg("clients"));
      })
      .onComplete(test.succeeding(resp -> {
        assertEquals(6, attempts.intValue());
        Optional<String> connectedClients = Arrays.stream(resp.toString().split("\n"))
          .filter(line -> line.startsWith("connected_clients:"))
          .findFirst();
        if (connectedClients.isPresent()) {
          assertEquals(1, Integer.parseInt(connectedClients.get().split(":")[1].trim()));
          test.completeNow();
        } else {
          test.failNow("connected_clients field not found in INFO clients reply");
        }
      }));
  }

  @Test
  public void closeSocketOnUpgradeSslError(VertxTestContext test) {
    String connstr = ":foobar@" + redis.getHost() + ":" + redis.getPort();

    Redis.createClient(context.vertx(), new RedisOptions().setConnectionString("rediss://" + connstr))
      .connect()
      .recover(ex -> {
        Redis client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString("redis://" + connstr));
        return client.connect();
      })
      .compose(conn -> {
        return context.vertx().timer(1, TimeUnit.SECONDS).map(ignored -> conn);
      })
      .compose(conn -> {
        return conn.send(cmd(INFO).arg("clients"));
      })
      .onComplete(test.succeeding(resp -> {
        Optional<String> connectedClients = Arrays.stream(resp.toString().split("\n"))
          .filter(line -> line.startsWith("connected_clients:"))
          .findFirst();
        if (connectedClients.isPresent()) {
          assertEquals(1, Integer.parseInt(connectedClients.get().split(":")[1].trim()));
          test.completeNow();
        } else {
          test.failNow("connected_clients field not found in INFO clients reply");
        }
      }));
  }
}
