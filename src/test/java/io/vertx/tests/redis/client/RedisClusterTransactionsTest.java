package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisClusterTransactions;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClusterTransactionsTest {
  @Container
  public static final RedisCluster redis = new RedisCluster();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private final RedisOptions options = new RedisOptions()
    .addConnectionString(redis.getRedisNode0Uri())
    .addConnectionString(redis.getRedisNode1Uri())
    .addConnectionString(redis.getRedisNode2Uri())
    .addConnectionString(redis.getRedisNode3Uri())
    .addConnectionString(redis.getRedisNode4Uri())
    .addConnectionString(redis.getRedisNode5Uri())
    .setType(RedisClientType.CLUSTER)
    .setUseReplicas(RedisReplicas.SHARE)
    .setClusterTransactions(RedisClusterTransactions.SINGLE_NODE);

  private Redis client;

  @BeforeEach
  public void createClient() {
    client = Redis.createClient(context.vertx(), options);
  }

  @AfterEach
  public void cleanRedis(VertxTestContext test) {
    client.close().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void simpleTransaction_success(VertxTestContext test) {
    String key1 = "x";
    String key2 = "exs";
    String key3 = "fubar"; // [hopefully] targets different node than key1 and key2

    client.connect().onComplete(test.succeeding(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key1).arg(1))
        .compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key2).arg(5));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.MULTI));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).compose(result -> {
          assertNotNull(result);
          assertTrue(result.isArray());
          assertEquals(2, result.get(0).toInteger());
          assertEquals(6, result.get(1).toInteger());
          return conn.send(Request.cmd(Command.DEL).arg(key1));
        }).compose(result -> {
          // transaction has ended, we can target a different node now
          return conn.send(Request.cmd(Command.SET).arg(key3).arg(true));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.DEL).arg(key2));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.DEL).arg(key3));
        }).onComplete(test.succeedingThenComplete());
    }));
  }

  @Test
  public void simpleTransaction_failure(VertxTestContext test) {
    // failure because these keys [hopefully] target different nodes
    String key1 = "foo";
    String key2 = "bar";

    client.connect().onComplete(test.succeeding(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key1).arg(1))
        .compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key2).arg(5));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.MULTI));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).recover(err -> {
          assertTrue(err.getMessage().contains("Redirect inside a transaction"));
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).recover(err -> {
          assertTrue(err.getMessage().contains("Transaction discarded"));
          return conn.send(Request.cmd(Command.GET).arg(key1));
        }).compose(result -> {
          assertNotNull(result);
          assertEquals(1, result.toInteger());
          // transaction has ended, we can target a different node now
          return conn.send(Request.cmd(Command.GET).arg(key2));
        }).onComplete(test.succeeding(result -> {
          assertNotNull(result);
          assertEquals(5, result.toInteger());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void optimisticLocking_success(VertxTestContext test) {
    String key = "mykey";

    client.connect().onComplete(test.succeeding(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key).arg(7))
        .compose(result -> {
          return conn.send(Request.cmd(Command.WATCH).arg(key));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.GET).arg(key));
        }).compose(result -> {
          int newValue = result.toInteger() + 1;
          return conn.send(Request.cmd(Command.MULTI))
            .map(res -> new Pair<>(newValue, res));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key).arg(result.a));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).compose(result -> {
          assertNotNull(result);
          assertTrue(result.isArray());
          assertEquals("OK", result.get(0).toString());
          return conn.send(Request.cmd(Command.GET).arg(key));
        }).onComplete(test.succeeding(result -> {
          assertEquals(8, result.toInteger());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void optimisticLocking_casFailure(VertxTestContext test) {
    // CAS failure on this key, because 2 connections mutate it
    String key = "mykey";

    client.connect().onComplete(test.succeeding(conn -> {
      client.connect().onComplete(test.succeeding(conn2 -> {
        conn.send(Request.cmd(Command.SET).arg(key).arg(7))
          .compose(result -> {
            return conn.send(Request.cmd(Command.WATCH).arg(key));
          }).compose(result -> {
            return conn.send(Request.cmd(Command.GET).arg(key));
          }).compose(result -> {
            int newValue = result.toInteger() + 1;
            return conn.send(Request.cmd(Command.MULTI))
              .map(res -> new Pair<>(newValue, res));
          }).compose(result -> {
            return conn.send(Request.cmd(Command.SET).arg(key).arg(result.a));
          }).compose(result -> {
            assertEquals("QUEUED", result.toString());
            return conn2.send(Request.cmd(Command.INCR).arg(key));
          }).compose(result -> {
            assertEquals(8, result.toInteger());
            return conn.send(Request.cmd(Command.EXEC));
          }).onComplete(test.succeeding(result -> {
            assertNull(result);
            test.completeNow();
          }));
      }));
    }));
  }

  @Test
  public void optimisticLocking_txFailure(VertxTestContext test) {
    // failure because these keys [hopefully] target different nodes
    String key1 = "mykey";
    String key2 = "anotherKey";

    client.connect().onComplete(test.succeeding(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key1).arg(7))
        .compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key2).arg(3));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.WATCH).arg(key1));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.GET).arg(key1));
        }).compose(result -> {
          int newValue = result.toInteger() + 1;
          return conn.send(Request.cmd(Command.MULTI))
            .map(res -> new Pair<>(newValue, res));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key1).arg(result.a));
        }).compose(result -> {
          assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).recover(err -> {
          assertTrue(err.getMessage().contains("Redirect inside a transaction"));
          return conn.send(Request.cmd(Command.EXEC));
        }).onComplete(test.failing(err -> {
          assertTrue(err.getMessage().contains("Transaction discarded"));
          test.completeNow();
        }));
    }));
  }
}
