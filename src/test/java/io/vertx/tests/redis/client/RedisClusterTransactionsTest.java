package io.vertx.tests.redis.client;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisClusterTransactions;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisClusterTransactionsTest {
  @ClassRule
  public static final RedisCluster redis = new RedisCluster();

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

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

  @Before
  public void createClient() {
    client = Redis.createClient(rule.vertx(), options);
  }

  @After
  public void cleanRedis(TestContext should) {
    final Async test = should.async();
    client.close().onComplete(should.asyncAssertSuccess(ignored -> test.complete()));
  }

  @Test
  public void simpleTransaction_success(TestContext test) {
    String key1 = "x";
    String key2 = "exs";
    String key3 = "fubar"; // [hopefully] targets different node than key1 and key2

    Async async = test.async();
    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key1).arg(1))
        .compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key2).arg(5));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.MULTI));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).compose(result -> {
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).compose(result -> {
          test.assertNotNull(result);
          test.assertTrue(result.isArray());
          test.assertEquals(2, result.get(0).toInteger());
          test.assertEquals(6, result.get(1).toInteger());
          return conn.send(Request.cmd(Command.DEL).arg(key1));
        }).compose(result -> {
          // transaction has ended, we can target a different node now
          return conn.send(Request.cmd(Command.SET).arg(key3).arg(true));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.DEL).arg(key2));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.DEL).arg(key3));
        }).onComplete(test.asyncAssertSuccess(result -> {
          async.complete();
        }));
    }));
  }

  @Test
  public void simpleTransaction_failure(TestContext test) {
    // failure because these keys [hopefully] target different nodes
    String key1 = "foo";
    String key2 = "bar";

    Async async = test.async();
    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
      conn.send(Request.cmd(Command.SET).arg(key1).arg(1))
        .compose(result -> {
          return conn.send(Request.cmd(Command.SET).arg(key2).arg(5));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.MULTI));
        }).compose(result -> {
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).recover(err -> {
          test.assertTrue(err.getMessage().contains("Redirect inside a transaction"));
          return conn.send(Request.cmd(Command.INCR).arg(key1));
        }).compose(result -> {
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).recover(err -> {
          test.assertTrue(err.getMessage().contains("Transaction discarded"));
          return conn.send(Request.cmd(Command.GET).arg(key1));
        }).compose(result -> {
          test.assertNotNull(result);
          test.assertEquals(1, result.toInteger());
          // transaction has ended, we can target a different node now
          return conn.send(Request.cmd(Command.GET).arg(key2));
        }).onComplete(test.asyncAssertSuccess(result -> {
          test.assertNotNull(result);
          test.assertEquals(5, result.toInteger());
          async.complete();
        }));
    }));
  }

  @Test
  public void optimisticLocking_success(TestContext test) {
    String key = "mykey";

    Async async = test.async();
    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
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
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.EXEC));
        }).compose(result -> {
          test.assertNotNull(result);
          test.assertTrue(result.isArray());
          test.assertEquals("OK", result.get(0).toString());
          return conn.send(Request.cmd(Command.GET).arg(key));
        }).onComplete(test.asyncAssertSuccess(result -> {
          test.assertEquals(8, result.toInteger());
          async.complete();
        }));
    }));
  }

  @Test
  public void optimisticLocking_casFailure(TestContext test) {
    // CAS failure on this key, because 2 connections mutate it
    String key = "mykey";

    Async async = test.async();
    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
      client.connect().onComplete(test.asyncAssertSuccess(conn2 -> {
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
            test.assertEquals("QUEUED", result.toString());
            return conn2.send(Request.cmd(Command.INCR).arg(key));
          }).compose(result -> {
            test.assertEquals(8, result.toInteger());
            return conn.send(Request.cmd(Command.EXEC));
          }).onComplete(test.asyncAssertSuccess(result -> {
            test.assertNull(result);
            async.complete();
          }));
      }));
    }));
  }

  @Test
  public void optimisticLocking_txFailure(TestContext test) {
    // failure because these keys [hopefully] target different nodes
    String key1 = "mykey";
    String key2 = "anotherKey";

    Async async = test.async();
    client.connect().onComplete(test.asyncAssertSuccess(conn -> {
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
          test.assertEquals("QUEUED", result.toString());
          return conn.send(Request.cmd(Command.INCR).arg(key2));
        }).recover(err -> {
          test.assertTrue(err.getMessage().contains("Redirect inside a transaction"));
          return conn.send(Request.cmd(Command.EXEC));
        }).onComplete(test.asyncAssertFailure(err -> {
          test.assertTrue(err.getMessage().contains("Transaction discarded"));
          async.complete();
        }));
    }));
  }
}
