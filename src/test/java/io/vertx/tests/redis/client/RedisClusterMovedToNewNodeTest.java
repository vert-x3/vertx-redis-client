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
import io.vertx.redis.client.impl.PooledRedisConnection;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClusterMovedToNewNodeTest {
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
        return addNewMaster().compose(newMasterResult -> {
          Redis newMaster = newMasterResult.redis;
          RedisConnection newMasterConn = newMasterResult.conn;
          String newMasterId = newMasterResult.id;
          return clusterConn.send(Request.cmd(Command.SET).arg(key1).arg("fubar"))
            .compose(ignored -> {
              return clusterConn.send(Request.cmd(Command.SET).arg(key2).arg("quux"));
            })
            .compose(ignored -> {
              return newMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("IMPORTING").arg(masterId));
            })
            .compose(ignored -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("MIGRATING").arg(newMasterId));
            }).compose(ignored -> {
              SocketAddress newMasterAddr = ((PooledRedisConnection) newMasterConn).actual().uri().socketAddress();
              return masterConn.send(Request.cmd(Command.MIGRATE).arg(newMasterAddr.host()).arg(newMasterAddr.port())
                .arg("").arg(0).arg(5000).arg("KEYS").arg(key1).arg(key2));
            })
            .compose(ignored -> {
              return masterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(newMasterId));
            })
            .compose(ignored -> {
              return newMasterConn.send(Request.cmd(Command.CLUSTER).arg("SETSLOT").arg(slot).arg("NODE").arg(newMasterId));
            })
            .compose(ignored -> {
              return clusterConn.send(Request.cmd(Command.GET).arg(key1));
            })
            .compose(result -> {
              assertEquals("fubar", result.toString());
              return clusterConn.send(Request.cmd(Command.GET).arg(key2));
            })
            .compose(result -> {
              assertEquals("quux", result.toString());
              master.close();
              newMaster.close();
              return Future.succeededFuture();
            });
        });
      });
    }).onComplete(test.succeedingThenComplete());
  }

  private Future<ClusterUtils.Result> addNewMaster() {
    return context.vertx()
      .executeBlocking(() -> {
        redis.addMaster(7006);
        return null;
      })
      .compose(ignored -> {
        Redis client = Redis.createClient(context.vertx(), "redis://127.0.0.1:7006");
        return client
          .connect()
          .compose(conn -> {
            return conn.send(Request.cmd(Command.CLUSTER).arg("MYID"))
              .map(resp -> {
                String newId = resp.toString();
                return new ClusterUtils.Result(client, conn, newId);
              });
          });
      });
  }
}
