package io.vertx.redis.client.test;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;


@RunWith(VertxUnitRunner.class)
public class RedisClusterTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  // Server: https://github.com/Grokzen/docker-redis-cluster
  private final RedisOptions options = new RedisOptions()
    .setType(RedisClientType.CLUSTER)
    .setUseSlave(RedisSlaves.SHARE)
    // we will flood the redis server
    .setMaxWaitingHandlers(128 * 1024)
    .addConnectionString("redis://127.0.0.1:7000")
    .addConnectionString("redis://127.0.0.1:7001")
    .addConnectionString("redis://127.0.0.1:7002")
    .addConnectionString("redis://127.0.0.1:7003")
    .addConnectionString("redis://127.0.0.1:7004")
    .addConnectionString("redis://127.0.0.1:7005")
    .setMaxPoolSize(8)
    .setMaxPoolWaiting(16);

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  private static String[] toStringArray(final String... params) {
    return params;
  }

  private Redis client;

  @Before
  public void createClient() {
    client = Redis.createClient(rule.vertx(), options);
  }

  @After
  public void cleanRedis(TestContext should) {
    final Async test = should.async();

    client.connect(onCreate -> {
      should.assertTrue(onCreate.succeeded());
      final RedisConnection cluster = onCreate.result();
      cluster.send(cmd(FLUSHDB), flushDB -> {
        should.assertTrue(flushDB.succeeded());
        client.close();
        test.complete();
      });
    });
  }

  @Test
  public void testContextReturn(TestContext should) {
    final Async test = should.async();
    Context context = rule.vertx().getOrCreateContext();
    client.connect(onCreate -> {
      should.assertEquals(context, rule.vertx().getOrCreateContext());
      test.complete();
    });
  }

  @Test(timeout = 30_000)
  public void runTheSlotScope(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          cluster.send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
            cluster.send(cmd(GET).arg(id), get -> {
              if (get.failed()) {
                get.cause().printStackTrace();
              }
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().toString());

              final int cnt = counter.incrementAndGet();
              if (cnt % 1024 == 0) {
                System.out.print('.');
              }

              if (cnt == len) {
                test.complete();
              }
            });
          });
        }
      });
  }

  @Test(timeout = 30_000)
  public void autoFindNodeByMOVEDAndASK(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addConnectionString("redis://127.0.0.1:7000")
      .addConnectionString("redis://127.0.0.1:7002")
      .addConnectionString("redis://127.0.0.1:7004")
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    // we miss add the odd port nodes on purpose

    final Redis client2 = Redis.createClient(rule.vertx(), options);

    client2
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          cluster.send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
            cluster.send(cmd(GET).arg(id), get -> {
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().toString());

              final int cnt = counter.incrementAndGet();
              if (cnt % 1024 == 0) {
                System.out.print('.');
              }

              if (cnt == len) {
                client2.close();
                test.complete();
              }
            });
          });
        }
      });
  }

  @Test(timeout = 30_000)
  public void autoFindNodes(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addConnectionString("redis://127.0.0.1:7000")
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    // we only provide 1 node

    final Redis client2 = Redis.createClient(rule.vertx(), options);

    client2
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();

        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          cluster.send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
            cluster.send(cmd(GET).arg(id), get -> {
              should.assertTrue(get.succeeded());
              should.assertEquals(id, get.result().toString());

              final int cnt = counter.incrementAndGet();
              if (cnt % 1024 == 0) {
                System.out.print('.');
              }

              if (cnt == len) {
                client2.close();
                test.complete();
              }
            });
          });
        }
      });
  }

  @Test(timeout = 30_000)
  public void autoFindNodesAcross24Instances(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      .setType(RedisClientType.CLUSTER)
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addConnectionString("redis://127.0.0.1:7000")
      .setMaxPoolSize(8)
      .setMaxPoolWaiting(16);

    List<Future> futures = new ArrayList<>(24);

    for (int i = 0; i < 24; i++) {
      Promise p = Promise.promise();
      Redis.createClient(rule.vertx(), options).connect(p);
      futures.add(p.future());
    }


    CompositeFuture.all(futures).setHandler(all -> {
      should.assertFalse(all.failed());

      final Random rnd = new Random();
      final List<RedisConnection> clients = all.result().list();
      // ensure we fail on client error
      clients.forEach(client -> client.exceptionHandler(should::fail));

      System.out.println("We have " + clients.size() + " clients");

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        clients.get(rnd.nextInt(clients.size()))
          .send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
            clients.get(rnd.nextInt(clients.size()))
              .send(cmd(GET).arg(id), get -> {
                should.assertTrue(get.succeeded());
                should.assertEquals(id, get.result().toString());

                final int cnt = counter.incrementAndGet();
                if (cnt % 1024 == 0) {
                  System.out.print('.');
                }

                if (cnt == len) {
                  test.complete();
                }
              });
          });
      }

    });
  }

  @Test(timeout = 30_000)
  public void testHgetall(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(HSET).arg("testKey").arg("field1").arg("Hello"), hset1 -> {
          should.assertTrue(hset1.succeeded());
          cluster.send(cmd(HSET).arg("testKey").arg("field2").arg("World"), hset2 -> {
            should.assertTrue(hset2.succeeded());
            cluster.send(cmd(HGETALL).arg("testKey"), hGetAll -> {
              should.assertTrue(hGetAll.succeeded());
              try {
                Response obj = hGetAll.result();
                should.assertEquals("Hello", obj.get("field1").toString());
                should.assertEquals("World", obj.get("field2").toString());
                test.complete();
              } catch (Exception ex) {
                should.fail(ex);
              }
            });
          });
        });
      });
  }

  @Test
  public void testAppend(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(DEL).arg(key), del -> {
          should.assertTrue(del.succeeded());

          cluster.send(cmd(APPEND).arg(key).arg("Hello"), append1 -> {
            should.assertTrue(append1.succeeded());
            should.assertEquals(5L, append1.result().toLong());

            cluster.send(cmd(APPEND).arg(key).arg(" World"), append2 -> {
              should.assertTrue(append2.succeeded());
              should.assertEquals(11L, append2.result().toLong());

              cluster.send(cmd(GET).arg(key), get -> {
                should.assertTrue(get.succeeded());
                should.assertEquals("Hello World", get.result().toString());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test
  public void testBitCount(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg("foobar"), set -> {
          should.assertTrue(set.succeeded());

          cluster.send(cmd(BITCOUNT).arg(key), bitCount1 -> {
            should.assertTrue(bitCount1.succeeded());
            should.assertEquals(26L, bitCount1.result().toLong());

            cluster.send(cmd(BITCOUNT).arg(key).arg(0).arg(0), bitCount2 -> {
              should.assertTrue(bitCount2.succeeded());
              should.assertEquals(4L, bitCount2.result().toLong());

              cluster.send(cmd(BITCOUNT).arg(key).arg(1).arg(1), bitCount3 -> {
                should.assertTrue(bitCount3.succeeded());
                should.assertEquals(6L, bitCount3.result().toLong());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test
  @Ignore
  public void testBitTop(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String destkey = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key1).arg("foobar"), set1 -> {
          should.assertTrue(set1.succeeded());

          cluster.send(cmd(SET).arg(key1).arg("abcdef"), set2 -> {
            should.assertTrue(set2.succeeded());

            cluster.send(cmd(BITOP).arg("AND").arg(destkey).arg(key1).arg(key2), bitTop -> {
              should.assertTrue(bitTop.succeeded());
              cluster.send(cmd(GET).arg(destkey), get -> {
                should.assertTrue(get.succeeded());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test//(timeout = 5_000)
  public void testBlPop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(DEL).arg(list1), del1 -> {
          should.assertTrue(del1.succeeded());

          cluster.send(cmd(DEL).arg(list2), del2 -> {
            should.assertTrue(del2.succeeded());

            cluster.send(cmd(RPUSH).arg(list1).arg("a").arg("b").arg("c"), rPush -> {
              should.assertTrue(rPush.succeeded());

              cluster.send(cmd(BLPOP).arg(list1).arg(0), blPop -> {
                should.assertTrue(blPop.succeeded());
                should.assertEquals(list1, blPop.result().get(0).toString());
                should.assertEquals("a", blPop.result().get(1).toString());
                should.assertEquals("[" + String.join(", ", toStringArray(list1, "a")) + "]", blPop.result().toString());
                test.complete();
              });
            });
          });
        });
      });
  }


  @Test
  public void testBitPos(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    final byte[] value1 = new byte[]{(byte) 0xff, (byte) 0xf0, (byte) 0x00};
    final byte[] value2 = new byte[]{0, 0, 0};

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg(Buffer.buffer(value1)), set1 -> {
          should.assertTrue(set1.succeeded());

          cluster.send(cmd(BITPOS).arg(key).arg(0), bitPos1 -> {
            should.assertTrue(bitPos1.succeeded());
            should.assertEquals(12L, bitPos1.result().toLong());

            cluster.send(cmd(SET).arg(key).arg(Buffer.buffer(value2)), set2 -> {
              should.assertTrue(set2.succeeded());

              cluster.send(cmd(BITPOS).arg(key).arg(1), bitPos2 -> {
                should.assertTrue(bitPos2.succeeded());
                should.assertEquals(-1L, bitPos2.result().toLong());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test(timeout = 5_000)
  @Ignore
  public void testBrPop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(DEL).arg(list1), del1 -> {
          should.assertTrue(del1.succeeded());

          cluster.send(cmd(DEL).arg(list2), del2 -> {
            should.assertTrue(del2.succeeded());

            cluster.send(cmd(RPUSH).arg(list1).arg("a").arg("b").arg("c"), rPush -> {
              should.assertTrue(rPush.succeeded());

              cluster.send(cmd(BRPOP).arg(list1).arg(list2).arg(0), brPop -> {
                should.assertTrue(brPop.succeeded());
                should.assertEquals(String.join(",", toStringArray(list1, "a")), brPop.result().toString());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test
  public void testDecr(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg(10), set -> {
          should.assertTrue(set.succeeded());

          cluster.send(cmd(DECR).arg(key), decr -> {
            should.assertTrue(decr.succeeded());
            should.assertEquals(9L, decr.result().toLong());
            test.complete();
          });
        });
      });
  }

  @Test
  public void testDecrBy(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg(10), set -> {
          should.assertTrue(set.succeeded());

          cluster.send(cmd(DECRBY).arg(key).arg(5), decrBy -> {
            should.assertTrue(decrBy.succeeded());
            should.assertEquals(5L, decrBy.result().toLong());
            test.complete();
          });
        });
      });
  }

  @Test
  public void testDel(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();

    client.connect(onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final RedisConnection cluster = onCreate.result();
      cluster.exceptionHandler(should::fail);

      cluster.send(cmd(SET).arg(key1).arg("Hello"), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(SET).arg(key2).arg("Hello"), set2 -> {
          should.assertTrue(set2.succeeded());

          cluster.send(cmd(DEL).arg(key1).arg(key2), del -> {
            should.assertTrue(del.succeeded());
            should.assertEquals(2, del.result().toInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testEcho(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(ECHO).arg("Hello Wordl"), echo -> {
          should.assertTrue(echo.succeeded());
          should.assertEquals("Hello Wordl", echo.result().toString());
          test.complete();
        });
      });
  }

  @Test
  public void testExists(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();

    final AtomicInteger counter = new AtomicInteger();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key1).arg("Hello"), set -> {
          should.assertTrue(set.succeeded());

          cluster.send(cmd(EXISTS).arg(key1), exists -> {
            should.assertTrue(exists.succeeded());
            should.assertEquals(1L, exists.result().toLong());
            if (counter.incrementAndGet() == 2) {
              test.complete();
            }
          });
        });

        cluster.send(cmd(EXISTS).arg(key2), exists -> {
          should.assertTrue(exists.succeeded());
          should.assertEquals(0L, exists.result().toLong());
          if (counter.incrementAndGet() == 2) {
            test.complete();
          }
        });
      });
  }

  @Test
  public void testExpire(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg("Hello"), set1 -> {
          should.assertTrue(set1.succeeded());

          cluster.send(cmd(EXPIRE).arg(key).arg(10), expire -> {
            should.assertTrue(expire.succeeded());
            should.assertEquals(1L, expire.result().toLong());

            cluster.send(cmd(TTL).arg(key), ttl1 -> {
              should.assertTrue(ttl1.succeeded());
              should.assertEquals(10L, ttl1.result().toLong());


              cluster.send(cmd(SET).arg(key).arg("Hello World"), set2 -> {
                should.assertTrue(set2.succeeded());

                cluster.send(cmd(TTL).arg(key), ttl2 -> {
                  should.assertTrue(ttl2.succeeded());
                  should.assertEquals(-1L, ttl2.result().toLong());
                  test.complete();
                });
              });
            });
          });
        });
      });
  }

  @Test
  public void testExpireAt(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg(key).arg("Hello"), set1 -> {
          should.assertTrue(set1.succeeded());

          cluster.send(cmd(EXISTS).arg(key), exists1 -> {
            should.assertTrue(exists1.succeeded());
            should.assertEquals(1L, exists1.result().toLong());

            cluster.send(cmd(EXPIREAT).arg(key).arg(1293840000), expireAt -> {
              should.assertTrue(expireAt.succeeded());
              should.assertEquals(1L, expireAt.result().toLong());


              cluster.send(cmd(EXISTS).arg(key), exists2 -> {
                should.assertTrue(exists2.succeeded());
                should.assertEquals(0L, exists2.result().toLong());
                test.complete();
              });
            });
          });
        });
      });
  }

  @Test(timeout = 10_000)
  public void testGet(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    final String nonExistentKey = "---";


    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();

        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(GET).arg(nonExistentKey), get1 -> {
          should.assertTrue(get1.succeeded());
          should.assertNull(get1.result());

          cluster.send(cmd(SET).arg(key).arg("Hello"), set -> {
            should.assertTrue(set.succeeded());

            cluster.send(cmd(GET).arg(key), get2 -> {
              should.assertTrue(get2.succeeded());
              should.assertEquals("Hello", get2.result().toString());
              test.complete();
            });
          });
        });
      });
  }

  @Test(timeout = 30_000)
  public void dbSize(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger(len);
        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          cluster.send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
            if (counter.decrementAndGet() == 0) {
              cluster.send(cmd(DBSIZE), dbSize -> {
                should.assertTrue(dbSize.succeeded());
                should.assertEquals(len, dbSize.result().toInteger());
                test.complete();
              });
            }
          });
        }
      });
  }

  @Test(timeout = 30_000)
  public void flushDB(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        final int len = (int) Math.pow(2, 17);
        final AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < len; i++) {
          final String id = Integer.toString(i);
          cluster.send(cmd(SET).arg(id).arg(id), set -> {
            should.assertTrue(set.succeeded());
          });
        }

        cluster.send(cmd(FLUSHDB), flushDb -> {
          should.assertTrue(flushDb.succeeded());

          cluster.send(cmd(DBSIZE), dbSize -> {
            should.assertTrue(dbSize.succeeded());
            should.assertEquals(0L, dbSize.result().toLong());
            test.complete();
          });
        });

      });
  }

  @Test(timeout = 30_000)
  public void keys(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(MSET).arg("1").arg("1").arg("2").arg("2").arg("3").arg("3").arg("key").arg("value"), mset -> {
          should.assertTrue(mset.succeeded());
          cluster.send(cmd(KEYS).arg("[0-9]"), keys -> {
            should.assertTrue(keys.succeeded());
            should.assertEquals(3, keys.result().size());
            test.complete();
          });
        });
      });
  }

  @Test(timeout = 30_000)
  public void mget(TestContext should) {
    final Async test = should.async();

    client
      .connect(onCreate -> {
        should.assertTrue(onCreate.succeeded());

        final RedisConnection cluster = onCreate.result();
        cluster.exceptionHandler(should::fail);

        cluster.send(cmd(SET).arg("key1").arg("Hello"), set1 -> {
          should.assertTrue(set1.succeeded());
          cluster.send(cmd(SET).arg("key2").arg("World"), set2 -> {
            should.assertTrue(set2.succeeded());
            cluster.send(cmd(MGET).arg("key1").arg("key2").arg("nonexisting"), mget -> {
              should.assertTrue(mget.succeeded());
              should.assertEquals(3, mget.result().size());
              List<String> values = new ArrayList<>();
              mget.result().forEach(value -> {
                if (value != null) {
                  values.add(value.toString());
                } else {
                  values.add(null);
                }
              });
              should.assertTrue(values.contains("Hello"));
              should.assertTrue(values.contains("World"));
              should.assertTrue(values.contains(null));
              test.complete();
            });
          });
        });
      });
  }
}
