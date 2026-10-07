package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.core.net.SocketAddress;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.PooledRedisConnection;
import io.vertx.redis.client.impl.RedisClusterClient;
import io.vertx.redis.client.impl.RedisConnectionManager;
import io.vertx.redis.client.impl.ZModem;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClusterMovedTest {
  @Container
  public static final RedisCluster redis = new RedisCluster();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private final RedisOptions options = new RedisOptions()
    .setType(RedisClientType.CLUSTER)
    .setUseReplicas(RedisReplicas.NEVER)
    .addConnectionString(redis.getRedisNode0Uri())
    .addConnectionString(redis.getRedisNode1Uri())
    .addConnectionString(redis.getRedisNode2Uri())
    .addConnectionString(redis.getRedisNode3Uri())
    .addConnectionString(redis.getRedisNode4Uri())
    .addConnectionString(redis.getRedisNode5Uri());

  private Redis client;
  private ClusterUtils cluster;

  @BeforeEach
  public void createClient() {
    client = Redis.createClient(context.vertx(), options);
    cluster = new ClusterUtils(context.vertx(), client);
  }

  @AfterEach
  public void cleanRedis() {
    client.close();
  }

  @Test
  public void test(VertxTestContext test) {
    // slot number: 16287
    // keys hashing to the slot: x, exs
    int slot = 16287;
    String key1 = "x";
    String key2 = "exs";

    client.connect().compose(clusterConn -> {
      return cluster.connectToMasterThatServesSlot(slot).compose(masterResult -> {
        Redis master = masterResult.redis;
        RedisConnection masterConn = masterResult.conn;
        String masterId = masterResult.id;
        return cluster.connectToMasterThatDoesntServeSlot(slot).compose(otherMasterResult -> {
          Redis otherMaster = otherMasterResult.redis;
          RedisConnection otherMasterConn = otherMasterResult.conn;
          String otherMasterId = otherMasterResult.id;
          return clusterConn.send(Request.cmd(Command.SET).arg(key1).arg("fubar"))
            .compose(ignored -> {
              return clusterConn.send(Request.cmd(Command.SET).arg(key2).arg("quux"));
            })
            .compose(ignored -> {
              return otherMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("IMPORTING").arg(masterId));
            })
            .compose(ignored -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("MIGRATING").arg(otherMasterId));
            })
            .compose(ignored -> {
              SocketAddress otherMasterAddr = ((PooledRedisConnection) otherMasterConn).actual().uri().socketAddress();
              return masterConn.send(Request.cmd(Command.MIGRATE).arg(otherMasterAddr.host()).arg(otherMasterAddr.port())
                .arg("").arg(0).arg(5000).arg("KEYS").arg(key1).arg(key2));
            })
            .compose(ignored -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(otherMasterId));
            })
            .compose(ignored -> {
              return otherMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(otherMasterId));
            })
            .compose(ignored -> {
              return clusterConn.send(Request.cmd(Command.GET).arg(key1)); // MOVED
            })
            .compose(result -> {
              assertEquals("fubar", result.toString());
              return clusterConn.send(Request.cmd(Command.GET).arg(key2)); // not MOVED, slots were reread
            })
            .compose(result -> {
              assertEquals("quux", result.toString());
              master.close();
              otherMaster.close();
              return Future.succeededFuture();
            });
        });
      });
    }).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testConnectionLess(VertxTestContext test) {
    // all keys with the `{a}` hash tag hash to this slot
    int slot = ZModem.generate("a");
    String key = "{a}" + randomKey();

    // the topology cache must not be refreshed before the request is MOVED
    Redis connectionLessClient = Redis.createClient(context.vertx(), new RedisOptions(options)
      .setMaxPoolSize(1)
      .setTopologyCacheTTL(60_000));
    RedisConnectionManager connectionManager = ((RedisClusterClient) connectionLessClient).connectionManager();

    connectionLessClient.send(Request.cmd(Command.SET).arg(key).arg("fubar")).compose(ignored -> {
      return cluster.connectToMasterThatServesSlot(slot).compose(masterResult -> {
        Redis master = masterResult.redis;
        RedisConnection masterConn = masterResult.conn;
        String masterId = masterResult.id;
        return cluster.connectToMasterThatDoesntServeSlot(slot).compose(otherMasterResult -> {
          Redis otherMaster = otherMasterResult.redis;
          RedisConnection otherMasterConn = otherMasterResult.conn;
          String otherMasterId = otherMasterResult.id;
          return otherMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("IMPORTING").arg(masterId))
            .compose(ignored2 -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("MIGRATING").arg(otherMasterId));
            })
            .compose(ignored2 -> {
              SocketAddress otherMasterAddr = ((PooledRedisConnection) otherMasterConn).actual().uri().socketAddress();
              return masterConn.send(Request.cmd(Command.MIGRATE).arg(otherMasterAddr.host()).arg(otherMasterAddr.port())
                .arg("").arg(0).arg(5000).arg("KEYS").arg(key));
            })
            .compose(ignored2 -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(otherMasterId));
            })
            .compose(ignored2 -> {
              return otherMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(otherMasterId));
            })
            .compose(ignored2 -> {
              // while the only pooled connection to the new node is held (maxPoolSize=1), the MOVED
              // request must release its pooled connection to the old node, otherwise this deadlocks
              return connectionManager.getConnection(endpoint(otherMasterConn), null).compose(otherMasterPooled -> {
                Future<Response> get = connectionLessClient.send(Request.cmd(Command.GET).arg(key)); // MOVED
                return connectionManager.getConnection(endpoint(masterConn), null).compose(masterPooled -> {
                  otherMasterPooled.close();
                  masterPooled.close();
                  return get;
                });
              });
            })
            .compose(result -> {
              assertEquals("fubar", result.toString());
              master.close();
              otherMaster.close();
              return connectionLessClient.close();
            });
        });
      });
    }).onComplete(test.succeedingThenComplete());
  }

  private static String endpoint(RedisConnection conn) {
    SocketAddress addr = ((PooledRedisConnection) conn).actual().uri().socketAddress();
    return "redis://" + addr.host() + ":" + addr.port();
  }
}
