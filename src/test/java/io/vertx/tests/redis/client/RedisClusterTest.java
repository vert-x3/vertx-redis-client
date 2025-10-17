package io.vertx.tests.redis.client;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisReplicas;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;
import io.vertx.tests.redis.containers.RedisCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.vertx.redis.client.Command.ACL;
import static io.vertx.redis.client.Command.APPEND;
import static io.vertx.redis.client.Command.BITCOUNT;
import static io.vertx.redis.client.Command.BITOP;
import static io.vertx.redis.client.Command.BITPOS;
import static io.vertx.redis.client.Command.BLPOP;
import static io.vertx.redis.client.Command.BRPOP;
import static io.vertx.redis.client.Command.CLUSTER;
import static io.vertx.redis.client.Command.DBSIZE;
import static io.vertx.redis.client.Command.DECR;
import static io.vertx.redis.client.Command.DECRBY;
import static io.vertx.redis.client.Command.DEL;
import static io.vertx.redis.client.Command.ECHO;
import static io.vertx.redis.client.Command.EVAL;
import static io.vertx.redis.client.Command.EXISTS;
import static io.vertx.redis.client.Command.EXPIRE;
import static io.vertx.redis.client.Command.EXPIREAT;
import static io.vertx.redis.client.Command.FLUSHDB;
import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.HGETALL;
import static io.vertx.redis.client.Command.HSET;
import static io.vertx.redis.client.Command.KEYS;
import static io.vertx.redis.client.Command.MGET;
import static io.vertx.redis.client.Command.MSET;
import static io.vertx.redis.client.Command.RPUSH;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Command.TTL;
import static io.vertx.redis.client.Command.WAIT;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static io.vertx.tests.redis.client.TestUtils.retryUntilSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClusterTest {

  @Container
  public static final RedisCluster redis = new RedisCluster();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private final RedisOptions options = new RedisOptions()
    .setType(RedisClientType.CLUSTER)
    .setUseReplicas(RedisReplicas.SHARE)
    // we will flood the redis server
    .setMaxWaitingHandlers(128 * 1024)
    .addConnectionString(redis.getRedisNode0Uri())
    .addConnectionString(redis.getRedisNode1Uri())
    .addConnectionString(redis.getRedisNode2Uri())
    .addConnectionString(redis.getRedisNode3Uri())
    .addConnectionString(redis.getRedisNode4Uri())
    .addConnectionString(redis.getRedisNode5Uri())
    .setMaxPoolSize(8)
    .setMaxPoolWaiting(16)
    .setTopologyCacheTTL(10_000);

  private Redis client;

  @BeforeEach
  public void createClient() {
    client = Redis.createClient(context.vertx(), options);
  }

  @AfterEach
  public void cleanRedis(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.send(cmd(FLUSHDB)).onComplete(test.succeeding(flushDB -> {
        client.close().onComplete(test.succeedingThenComplete());
      }));
    }));
  }

  @Test
  public void testContextReturn(VertxTestContext test) {
    Context context = this.context.vertx().getOrCreateContext();
    client.connect().onComplete(test.succeeding(conn -> {
      assertEquals(context, this.context.vertx().getOrCreateContext());
      conn.close().onComplete(test.succeedingThenComplete());
    }));
  }

  @Test
  public void testConnectTime(VertxTestContext test) {
    RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      .setUseReplicas(RedisReplicas.SHARE)
      // the first hosts are fake to simulate wrong setup
      .addConnectionString("redis://127.0.0.1:9999")
      .addConnectionString("redis://127.0.0.1:8888")
      .addConnectionString(redis.getRedisNode0Uri())
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    long t0 = System.currentTimeMillis();
    client = Redis.createClient(context.vertx(), options);
    long t1 = System.currentTimeMillis();

    client
      .connect().onComplete(test.succeeding(conn -> {
        long t2 = System.currentTimeMillis();

        System.out.println(t1 - t0);
        System.out.println(t2 - t1);
        test.completeNow();
      }));
  }


  @Test
  public void runTheSlotScope(VertxTestContext test) {
    client
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(test::failNow);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          conn.send(cmd(SET).arg(id).arg(id)).onComplete(test.succeeding(set -> {
            conn.send(cmd(GET).arg(id)).onComplete(test.succeeding(get -> {
              assertEquals(id, get.toString());

              final int cnt = counter.incrementAndGet();
              if (cnt == len) {
                test.completeNow();
              }
            }));
          }));
        }
      }));
  }

  @Test
  public void autoFindNodes(VertxTestContext test) {
    final RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addConnectionString(redis.getRedisNode0Uri())
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    // we only provide 1 node

    final Redis client2 = Redis.createClient(context.vertx(), options);

    client2
      .connect().onComplete(test.succeeding(conn -> {
        conn.exceptionHandler(test::failNow);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          conn.send(cmd(SET).arg(id).arg(id)).onComplete(test.succeeding(set -> {
            conn.send(cmd(GET).arg(id)).onComplete(test.succeeding(get -> {
              assertEquals(id, get.toString());

              final int cnt = counter.incrementAndGet();
              if (cnt == len) {
                client2.close();
                test.completeNow();
              }
            }));
          }));
        }
      }));
  }

  @Test
  public void autoFindNodesAcross24Instances(VertxTestContext test) {
    final RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addConnectionString(redis.getRedisNode0Uri())
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    List<Future<RedisConnection>> futures = new ArrayList<>(24);

    for (int i = 0; i < 24; i++) {
      futures.add(Redis.createClient(context.vertx(), options).connect());
    }

    Future.all(futures).onComplete(all -> {
      test.verify(() -> {
        assertFalse(all.failed());
      });

      final Random rnd = new Random();
      final List<RedisConnection> clients = all.result().list();
      // ensure we fail on client error
      clients.forEach(client -> client.exceptionHandler(test::failNow));

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        clients.get(rnd.nextInt(clients.size()))
          .send(cmd(SET).arg(id).arg(id)).onComplete(test.succeeding(set -> {
            clients.get(rnd.nextInt(clients.size()))
              .send(cmd(GET).arg(id)).onComplete(test.succeeding(get -> {
                assertEquals(id, get.toString());

                final int cnt = counter.incrementAndGet();
                if (cnt == len) {
                  test.completeNow();
                }
              }));
          }));
      }
    });
  }

  @Test
  public void testHgetall(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(HSET).arg("testKey").arg("field1").arg("Hello"))
        .compose(hset1 -> {
          return conn.send(cmd(HSET).arg("testKey").arg("field2").arg("World"));
        })
        .compose(hset2 -> {
          return conn.send(cmd(HGETALL).arg("testKey"));
        }).onComplete(test.succeeding(hGetAll -> {
          try {
            assertEquals("Hello", hGetAll.get("field1").toString());
            assertEquals("World", hGetAll.get("field2").toString());
            test.completeNow();
          } catch (Exception ex) {
            test.failNow(ex);
          }
        }));
    }));
  }

  @Test
  public void testAppend(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(DEL).arg(key))
        .compose(del -> {
          return conn.send(cmd(APPEND).arg(key).arg("Hello"));
        })
        .compose(append1 -> {
          assertEquals(5L, append1.toLong());
          return conn.send(cmd(APPEND).arg(key).arg(" World"));
        })
        .compose(append2 -> {
          assertEquals(11L, append2.toLong());
          return conn.send(cmd(GET).arg(key));
        })
        .onComplete(test.succeeding(get -> {
          assertEquals("Hello World", get.toString());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testBitCount(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg("foobar"))
        .compose(set -> {
          return conn.send(cmd(BITCOUNT).arg(key));
        })
        .compose(bitCount1 -> {
          assertEquals(26L, bitCount1.toLong());
          return conn.send(cmd(BITCOUNT).arg(key).arg(0).arg(0));
        })
        .compose(bitCount2 -> {
          assertEquals(4L, bitCount2.toLong());
          return conn.send(cmd(BITCOUNT).arg(key).arg(1).arg(1));
        })
        .onComplete(test.succeeding(bitCount3 -> {
          assertEquals(6L, bitCount3.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  @Disabled
  public void testBitTop(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    final String destkey = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key1).arg("foobar"))
        .compose(set1 -> {
          return conn.send(cmd(SET).arg(key2).arg("abcdef"));
        })
        .compose(set2 -> {
          return conn.send(cmd(BITOP).arg("AND").arg(destkey).arg(key1).arg(key2));
        })
        .compose(bitOp -> {
          return conn.send(cmd(GET).arg(destkey));
        })
        .onComplete(test.succeedingThenComplete());
    }));
  }

  @Test
  public void testBlPop(VertxTestContext test) {
    final String list1 = randomKey();
    final String list2 = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(DEL).arg(list1))
        .compose(del1 -> {
          return conn.send(cmd(DEL).arg(list2));
        })
        .compose(del2 -> {
          return conn.send(cmd(RPUSH).arg(list1).arg("a").arg("b").arg("c"));
        })
        .compose(rpush -> {
          return conn.send(cmd(BLPOP).arg(list1).arg(0));
        })
        .onComplete(test.succeeding(blPop -> {
          assertEquals(list1, blPop.get(0).toString());
          assertEquals("a", blPop.get(1).toString());
          assertEquals("[" + String.join(", ", list1, "a") + "]", blPop.toString());
          test.completeNow();
        }));
    }));
  }


  @Test
  public void testBitPos(VertxTestContext test) {
    final String key = randomKey();
    final byte[] value1 = new byte[]{(byte) 0xff, (byte) 0xf0, (byte) 0x00};
    final byte[] value2 = new byte[]{0, 0, 0};

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg(Buffer.buffer(value1)))
        .compose(set1 -> {
          return conn.send(cmd(BITPOS).arg(key).arg(0));
        })
        .compose(bitPos1 -> {
          assertEquals(12L, bitPos1.toLong());
          return conn.send(cmd(SET).arg(key).arg(Buffer.buffer(value2)));
        })
        .compose(set2 -> {
          return conn.send(cmd(BITPOS).arg(key).arg(1));
        })
        .onComplete(test.succeeding(bitPos2 -> {
          assertEquals(-1L, bitPos2.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  @Disabled
  public void testBrPop(VertxTestContext test) {
    final String list1 = randomKey();
    final String list2 = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(DEL).arg(list1))
        .compose(del1 -> {
          return conn.send(cmd(DEL).arg(list2));
        })
        .compose(del2 -> {
          return conn.send(cmd(RPUSH).arg(list1).arg("a").arg("b").arg("c"));
        })
        .compose(rpush -> {
          return conn.send(cmd(BRPOP).arg(list1).arg(list2).arg(0));
        })
        .onComplete(test.succeeding(brPop -> {
          assertEquals(String.join(",", list1, "a"), brPop.toString());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testDecr(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg(10))
        .compose(set -> {
          return conn.send(cmd(DECR).arg(key));
        })
        .onComplete(test.succeeding(decr -> {
          assertEquals(9L, decr.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testDecrBy(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg(10))
        .compose(set -> {
          return conn.send(cmd(DECRBY).arg(key).arg(5));
        }).onComplete(test.succeeding(decrBy -> {
          assertEquals(5L, decrBy.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testDel(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key1).arg("Hello"))
        .compose(set1 -> {
          return conn.send(cmd(SET).arg(key2).arg("Hello"));
        }).compose(set2 -> {
          return conn.send(cmd(DEL).arg(key1).arg(key2));
        }).onComplete(test.succeeding(del -> {
          assertEquals(2, del.toInteger());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testEcho(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(ECHO).arg("Hello Wordl")).onComplete(test.succeeding(echo -> {
        assertEquals("Hello Wordl", echo.toString());
        test.completeNow();
      }));
    }));
  }

  @Test
  public void testExists(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();

    Checkpoint checkpoint = test.checkpoint(2);

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key1).arg("Hello"))
        .compose(set -> {
          return conn.send(cmd(EXISTS).arg(key1));
        })
        .onComplete(test.succeeding(exists -> {
          assertEquals(1L, exists.toLong());
          checkpoint.flag();
        }));

      conn.send(cmd(EXISTS).arg(key2)).onComplete(test.succeeding(exists -> {
        assertEquals(0L, exists.toLong());
        checkpoint.flag();
      }));
    }));
  }

  @Test
  public void testExpire(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg("Hello"))
        .compose(set1 -> {
          return conn.send(cmd(EXPIRE).arg(key).arg(10));
        }).compose(expire -> {
          assertEquals(1L, expire.toLong());
          return conn.send(cmd(TTL).arg(key));
        }).compose(ttl1 -> {
          assertEquals(10L, ttl1.toLong());
          return conn.send(cmd(SET).arg(key).arg("Hello World"));
        }).compose(set2 -> {
          return conn.send(cmd(TTL).arg(key));
        }).onComplete(test.succeeding(ttl2 -> {
          assertEquals(-1L, ttl2.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testExpireAt(VertxTestContext test) {
    final String key = randomKey();

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg(key).arg("Hello"))
        .compose(set1 -> {
          return conn.send(cmd(EXISTS).arg(key));
        }).compose(exists1 -> {
          assertEquals(1L, exists1.toLong());
          return conn.send(cmd(EXPIREAT).arg(key).arg(1293840000));
        }).compose(expireAt -> {
          assertEquals(1L, expireAt.toLong());
          return conn.send(cmd(EXISTS).arg(key));
        }).onComplete(test.succeeding(exists2 -> {
          assertEquals(0L, exists2.toLong());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void testGet(VertxTestContext test) {
    final String key = randomKey();
    final String nonExistentKey = "---";


    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(GET).arg(nonExistentKey))
        .compose(get1 -> {
          assertNull(get1);
          return conn.send(cmd(SET).arg(key).arg("Hello"));
        }).compose(set -> {
          return conn.send(cmd(GET).arg(key));
        }).onComplete(test.succeeding(get2 -> {
          assertEquals("Hello", get2.toString());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void dbSize(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger(len);
      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        conn.send(cmd(SET).arg(id).arg(id)).onComplete(test.succeeding(set -> {
          if (counter.decrementAndGet() == 0) {
            // CI is slow, give it a few seconds to sync the cluster
            context.vertx().setTimer(2_000L, v -> {
              conn.send(cmd(DBSIZE)).onComplete(test.succeeding(dbSize -> {
                assertEquals(len, dbSize.toInteger());
                test.completeNow();
              }));
            });
          }
        }));
      }
    }));
  }

  @Test
  public void flushDB(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger(len);
      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        conn.send(cmd(SET).arg(id).arg(id)).onComplete(test.succeeding(set -> {
          if (counter.decrementAndGet() == 0) {
            // CI is slow, give it a few seconds to sync the cluster
            context.vertx().setTimer(2_000L, v -> {
              conn.send(cmd(FLUSHDB))
                .compose(flushDb -> {
                  return conn.send(cmd(DBSIZE));
                })
                .onComplete(test.succeeding(dbSize -> {
                  assertEquals(0L, dbSize.toLong());
                  test.completeNow();
                }));
            });
          }
        }));
      }
    }));
  }

  @Test
  public void keys(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(MSET).arg("1").arg("1").arg("2").arg("2").arg("3").arg("3").arg("key").arg("value"))
        .compose(mset -> {
          return conn.send(cmd(KEYS).arg("[0-9]"));
        }).onComplete(test.succeeding(keys -> {
          assertEquals(3, keys.size());
          test.completeNow();
        }));
    }));
  }

  @Test
  public void mget(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(SET).arg("key1").arg("Hello"))
        .compose(set1 -> {
          return conn.send(cmd(SET).arg("key2").arg("World"));
        }).compose(set2 -> {
          return conn.send(cmd(MGET).arg("key1").arg("key2").arg("nonexisting"));
        }).onComplete(test.succeeding(mget -> {
          assertEquals(3, mget.size());
          List<String> values = new ArrayList<>();
          mget.forEach(value -> {
            if (value != null) {
              values.add(value.toString());
            } else {
              values.add(null);
            }
          });
          assertTrue(values.contains("Hello"));
          assertTrue(values.contains("World"));
          assertTrue(values.contains(null));
          test.completeNow();
        }));
    }));
  }

  @Test
  public void mgetMultiKeyInDifferentSlotsWithFirstTwoInSameSlots(VertxTestContext test) {
    final String key1 = "{hash_tag}.some-key1";
    final String argv1 = "some-value1";
    final String key2 = "{hash_tag}.some-key2";
    final String argv2 = "some-value2";

    final String key3 = "{other_hash_tag}.other-key1";
    final String argv3 = "other-value1";
    final String key4 = "{other_hash_tag}.other-key2";
    final String argv4 = "other-value2";

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      Future<@Nullable Response> setFuture1 = conn.send(cmd(SET).arg(key1).arg(argv1));
      Future<@Nullable Response> setFuture2 = conn.send(cmd(SET).arg(key2).arg(argv2));
      Future<@Nullable Response> setFuture3 = conn.send(cmd(SET).arg(key3).arg(argv3));
      Future<@Nullable Response> setFuture4 = conn.send(cmd(SET).arg(key4).arg(argv4));
      Future.all(setFuture1, setFuture2, setFuture3, setFuture4)
        .compose(compositeRet -> {
          return conn.send(cmd(MGET).arg(key1).arg(key2).arg(key3).arg(key4));
        })
        .onComplete(test.succeeding(mgetResponse -> {
          Set<String> mgetRet = mgetResponse.stream().map(Response::toString).collect(Collectors.toSet());
          assertTrue(mgetRet.contains(argv1));
          assertTrue(mgetRet.contains(argv2));
          assertTrue(mgetRet.contains(argv3));
          assertTrue(mgetRet.contains(argv4));
          test.completeNow();
        }));
    }));
  }

  @Test
  public void mgetMultiKeyInDifferentSlotsWithFirstTwoInDifferentSlots(VertxTestContext test) {
    final String key1 = "{hash_tag}.some-key1";
    final String argv1 = "some-value1";
    final String key2 = "{hash_tag}.some-key2";
    final String argv2 = "some-value2";

    final String key3 = "{other_hash_tag}.other-key1";
    final String argv3 = "other-value1";
    final String key4 = "{other_hash_tag}.other-key2";
    final String argv4 = "other-value2";

    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      Future<@Nullable Response> setFuture1 = conn.send(cmd(SET).arg(key1).arg(argv1));
      Future<@Nullable Response> setFuture2 = conn.send(cmd(SET).arg(key2).arg(argv2));
      Future<@Nullable Response> setFuture3 = conn.send(cmd(SET).arg(key3).arg(argv3));
      Future<@Nullable Response> setFuture4 = conn.send(cmd(SET).arg(key4).arg(argv4));
      Future.all(setFuture1, setFuture2, setFuture3, setFuture4)
        .compose(compositeRet -> {
          return conn.send(cmd(MGET).arg(key1).arg(key3).arg(key2).arg(key4));
        })
        .onComplete(test.succeeding(mgetResponse -> {
          Set<String> mgetRet = mgetResponse.stream().map(Response::toString)
            .collect(Collectors.toSet());
          assertTrue(mgetRet.contains(argv1));
          assertTrue(mgetRet.contains(argv2));
          assertTrue(mgetRet.contains(argv3));
          assertTrue(mgetRet.contains(argv4));
          test.completeNow();
        }));
    }));
  }

  @Test
  public void evalSingleKey(VertxTestContext test) {
    final String key = "{hash_tag}.some-key";
    final String argv = "some-value";

    Redis.createClient(context.vertx(), options).connect()
      .onComplete(test.succeeding(conn -> {
          conn.send(cmd(EVAL).arg("return redis.call('SET', KEYS[1], ARGV[1])").arg(1).arg(key).arg(argv))
            .onComplete(test.succeeding(response -> {
              assertEquals("OK", response.toString());
              test.completeNow();
            }));
        }
      ));
  }

  @Test
  public void evalSingleKeyBatch(VertxTestContext test) {
    final String key = "{hash_tag}.some-key";
    final String argv = "some-value";

    Request req = cmd(EVAL).arg("return redis.call('SET', KEYS[1], ARGV[1])").arg(1).arg(key).arg(argv);
    final List<Request> cmdList = new ArrayList<>();
    cmdList.add(req);
    cmdList.add(req);
    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.batch(cmdList).onComplete(test.succeeding(response -> {
          assertEquals(2, response.size());
          response.forEach(r -> assertEquals("OK", r.toString()));
          test.completeNow();
        }));
      }
    ));
  }

  @Test
  public void evalMultiKey(VertxTestContext test) {
    final String key1 = "{hash_tag}.some-key";
    final String argv1 = "some-value";
    final String key2 = "{hash_tag}.other-key";
    final String argv2 = "other-value";

    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.send(cmd(EVAL).arg("local r1 = redis.call('SET', KEYS[1], ARGV[1]) \n" +
                                "local r2 = redis.call('SET', KEYS[2], ARGV[2]) \n" +
                                "return {r1, r2}").arg(2).arg(key1).arg(key2).arg(argv1).arg(argv2))
          .onComplete(test.succeeding(response -> {
            assertEquals(2, response.size());
            response.forEach(r -> assertEquals("OK", r.toString()));
            test.completeNow();
          }));
      }
    ));
  }

  @Test
  public void evalMultiKeyDifferentSlots(VertxTestContext test) {
    final String key1 = "{hash_tag}.some-key";
    final String argv1 = "some-value";
    final String key2 = "{other_hash_tag}.other-key";
    final String argv2 = "other-value";

    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.send(cmd(EVAL).arg("local r1 = redis.call('SET', KEYS[1], ARGV[1]) \n" +
                                "local r2 = redis.call('SET', KEYS[2], ARGV[2]) \n" +
                                "return {r1, r2}").arg(2).arg(key1).arg(key2).arg(argv1).arg(argv2))
          .onComplete(test.failing(err -> {
            assertTrue(err.getMessage().startsWith("Keys of command or batch"));
            test.completeNow();
          }));
      }
    ));
  }

  @Test
  public void evalSingleKeyDifferentSlotsBatch(VertxTestContext test) {
    final String argv = "some-value";

    final List<Request> cmdList = new ArrayList<>();
    cmdList.add(cmd(EVAL).arg("return redis.call('SET', KEYS[1], ARGV[1])").arg(1).arg("{hash_tag}.some-key").arg(argv));
    cmdList.add(cmd(EVAL).arg("return redis.call('SET', KEYS[1], ARGV[1])").arg(1).arg("{other_hash_tag}.some-key").arg(argv));
    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.batch(cmdList).onComplete(test.failing(err -> {
          assertTrue(err.getMessage().startsWith("Keys of command or batch"));
          test.completeNow();
        }));
      }
    ));
  }

  /**
   * Wait must run every time against a master node.
   */
  @Test
  public void setAndWait(VertxTestContext test) {
    final int runs = 10;

    Checkpoint checkpoint = test.checkpoint(runs);

    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        for (int i = 0; i < runs; i++) {
          conn.send(cmd(SET).arg("key").arg("value")).onComplete(test.succeeding(setResponse -> {
            assertEquals("OK", setResponse.toString().toUpperCase());

            conn.send(cmd(WAIT).arg(1).arg(2000)).onComplete(test.succeeding(waitResponse -> {
              assertEquals(1, waitResponse.toInteger());
              checkpoint.flag();
            }));
          }));
        }
      }
    ));
  }

  @Test
  public void setAndWaitBatch(VertxTestContext test) {
    final int runs = 10;

    Checkpoint checkpoint = test.checkpoint(runs);

    final List<Request> cmdList = new ArrayList<>();
    cmdList.add(cmd(SET).arg("key").arg("value"));
    cmdList.add(cmd(WAIT).arg(1).arg(2000));
    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        for (int i = 0; i < runs; i++) {
          conn.batch(cmdList).onComplete(test.succeeding(responses -> {
            assertEquals(2, responses.size());
            Response setResponse = responses.get(0);
            assertEquals("OK", setResponse.toString().toUpperCase());
            Response waitResponse = responses.get(1);
            assertEquals(1, waitResponse.toInteger());

            checkpoint.flag();
          }));
        }
      }
    ));
  }

  @Test
  public void setAndWaitEmptyBatch(VertxTestContext test) {
    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.batch(List.of()).onComplete(test.succeeding(responses -> {
          assertEquals(0, responses.size());
          test.completeNow();
        }));
      }
    ));
  }

  @Test
  public void batchSameSlotGroupByMultipleSlotsCommands(VertxTestContext test) {
    Redis.createClient(context.vertx(), options)
      .connect()
      .onComplete(test.succeeding(conn -> {
        io.vertx.redis.client.RedisCluster cluster = io.vertx.redis.client.RedisCluster.create(conn);

        List<Request> commands = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
          String key = "key-" + i;
          String value = "value-" + i;
          commands.add(cmd(SET).arg(key).arg(value));
        }

        cluster.groupByNodes(commands)
          .onComplete(test.succeeding(groupedCommands -> {
            List<Future<List<Response>>> futures = new ArrayList<>();
            for (List<Request> commandGroup : groupedCommands.getKeyed()) {
              futures.add(conn.batch(commandGroup));
            }
            Future.all(futures)
              .onComplete(test.succeeding(responses -> {
                assertEquals(
                  groupedCommands.getKeyed()
                    .stream()
                    .map(List::size)
                    .reduce(0, Integer::sum),
                  responses.result()
                    .list()
                    .stream()
                    .map(item -> ((List<Request>) item).size())
                    .reduce(0, Integer::sum));
                test.completeNow();
              }));
          }));
      }));
  }

  @Test
  public void batchSameSlotsCommands(VertxTestContext test) {
    Redis.createClient(context.vertx(), options)
      .connect()
      .onComplete(test.succeeding(conn -> {
        io.vertx.redis.client.RedisCluster cluster = io.vertx.redis.client.RedisCluster.create(conn);

        List<Request> commands = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
          String key = "key-" + i;
          String value = "value-" + i;
          commands.add(cmd(SET).arg(key).arg(value));
        }

        cluster.groupByNodes(commands)
          .onComplete(test.succeeding(groupedCommands -> {
            List<Request> commandGroup = groupedCommands.getKeyed().iterator().next();

            conn.batch(commandGroup)
              .onComplete(test.succeeding(responses -> {
                assertEquals(commandGroup.size(), responses.size());
                test.completeNow();
              }));
          }));
      }));
  }

  @Test
  public void groupByNodesCrossSlotFailure(VertxTestContext test) {
    Redis.createClient(context.vertx(), options)
      .connect()
      .onComplete(test.succeeding(conn -> {
        io.vertx.redis.client.RedisCluster cluster = io.vertx.redis.client.RedisCluster.create(conn);

        List<Request> commands = Collections.singletonList(cmd(DEL).arg("key1").arg("key2").arg("key3"));

        cluster.groupByNodes(commands)
          .onComplete(test.failing(err -> {
            err.getMessage().contains("CROSSSLOT");
            test.completeNow();
          }));
      }));
  }

  @Test
  public void clusterInfoReturnsVerbatimString(VertxTestContext test) {
    Redis.createClient(context.vertx(), options).connect().onComplete(test.succeeding(conn -> {
        conn.send(cmd(CLUSTER).arg("INFO")).onComplete(test.succeeding(response -> {
          assertEquals("txt", response.format());
          assertTrue(response.toString().startsWith("cluster_state:ok"));
          test.completeNow();
        }));
      }
    ));
  }

  @Test
  public void testUnsupportedCommand(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(WAIT).arg(1).arg(0)).onComplete(test.succeedingThenComplete());
    }));
  }

  @Test
  public void testCommandWithoutReadOrWrite(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      conn.exceptionHandler(test::failNow);

      conn.send(cmd(ACL, "users")).onComplete(test.succeeding(aclUsers -> {
        assertEquals(1, aclUsers.size());
        test.completeNow();
      }));
    }));
  }

  @Test
  public void preservesContext(VertxTestContext test) {
    PreservesContext.sendWithoutConnect(client, test);
    PreservesContext.batchWithoutConnect(client, test);
    PreservesContext.connect(client, test);
    PreservesContext.connectThenSend(client, test);
    PreservesContext.connectThenBatch(client, test);
  }

  @Test
  public void testWriteToMasterReadFromReplica(VertxTestContext test) {
    final String key = randomKey();

    Redis.createClient(
        context.vertx(),
        new RedisOptions()
          .setType(RedisClientType.CLUSTER)
          .setUseReplicas(RedisReplicas.ALWAYS)
          .addConnectionString(redis.getRedisNode0Uri())
          .addConnectionString(redis.getRedisNode1Uri())
          .addConnectionString(redis.getRedisNode2Uri())
          .addConnectionString(redis.getRedisNode3Uri())
          .addConnectionString(redis.getRedisNode4Uri())
          .addConnectionString(redis.getRedisNode5Uri())
          .setMaxPoolSize(8)
          .setMaxPoolWaiting(16))
      .connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(SET).arg(key).arg("foobar"))
          .compose(ignored -> retryUntilSuccess(context.vertx(), () -> {
            return conn.send(Request.cmd(GET).arg(key));
          }, 10))
          .onComplete(test.succeeding(result -> {
            assertEquals("foobar", result.toString());
            test.completeNow();
          }));
      }));
  }

  @Test
  public void testRedisClusterOnAllNodes(VertxTestContext test) {
    client.connect().onComplete(test.succeeding(conn -> {
      io.vertx.redis.client.RedisCluster cluster = io.vertx.redis.client.RedisCluster.create(conn);
      cluster.onAllMasterNodes(cmd(FLUSHDB))
        .compose(result -> {
          for (Response response : result) {
            assertEquals(ResponseType.SIMPLE, response.type());
          }

          return conn.send(cmd(SET).arg("key1").arg("value1"));
        }).compose(ignored -> {
          return conn.send(cmd(SET).arg("key2").arg("value2"));
        }).compose(ignored -> {
          return conn.send(cmd(SET).arg("key3").arg("value3"));
        }).compose(ignored -> {
          return cluster.onAllMasterNodes(cmd(KEYS).arg("*"));
        }).onComplete(test.succeeding(responses -> {
          Set<String> keys = new HashSet<>();
          for (Response response : responses) {
            for (Response item : response) {
              keys.add(item.toString());
            }
          }
          assertEquals(new HashSet<>(Arrays.asList("key1", "key2", "key3")), keys);
          test.completeNow();
        }));
    }));
  }
}
