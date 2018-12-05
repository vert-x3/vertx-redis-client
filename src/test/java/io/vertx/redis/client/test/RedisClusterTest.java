package io.vertx.redis.client.test;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Response;
import io.vertx.redis.op.BitOperation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;


@RunWith(VertxUnitRunner.class)
public class RedisClusterTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  // Server: https://github.com/Grokzen/docker-redis-cluster
  final RedisOptions options = new RedisOptions()
    // we will flood the redis server
    .setMaxWaitingHandlers(128 * 1024)
    .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7001, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7002, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7003, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7004, "127.0.0.1"))
    .addEndpoint(SocketAddress.inetSocketAddress(7005, "127.0.0.1"));

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  private static String[] toStringArray(final String... params) {
    return params;
  }

  @Test(timeout = 30_000)
  public void runTheSlotScope(TestContext should) {
    final Async test = should.async();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
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
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7002, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7004, "127.0.0.1"));

    // we miss add the odd port nodes on purpose

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
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
  public void autoFindNodes(TestContext should) {
    final Async test = should.async();

    final RedisOptions options = new RedisOptions()
      // we will flood the redis server
      .setMaxWaitingHandlers(128 * 1024)
      .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"));

    // we only provide 1 node

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      final int len = (int) Math.pow(2, 17);
      final AtomicInteger counter = new AtomicInteger();

      for (int i = 0; i < len; i++) {
        final String id = Integer.toString(i);
        cluster.send(cmd(SET).key(id).arg(id), set -> {
          should.assertTrue(set.succeeded());
          cluster.send(cmd(GET).key(id), get -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();
      cluster.send(cmd(HSET).key("testKey").arg("field1").arg("Hello"), hset1 -> {
        should.assertTrue(hset1.succeeded());
        cluster.send(cmd(HSET).key("testKey").arg("field2").arg("World"), hset2 -> {
          should.assertTrue(hset2.succeeded());
          cluster.send(cmd(HGETALL).key("testKey"), hGetAll -> {
            should.assertTrue(hGetAll.succeeded());
            try {
              Map<String, Response> obj = hGetAll.result().toMap();
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(DEL).key(key), del -> {
        should.assertTrue(del.succeeded());

        cluster.send(cmd(APPEND).key(key).arg("Hello"), append1 -> {
          should.assertTrue(append1.succeeded());
          should.assertEquals(5L, append1.result().toLong());

          cluster.send(cmd(APPEND).key(key).arg(" World"), append2 -> {
            should.assertTrue(append2.succeeded());
            should.assertEquals(11L, append2.result().toLong());

            cluster.send(cmd(GET).key(key), get -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg("foobar"), set -> {
        should.assertTrue(set.succeeded());

        cluster.send(cmd(BITCOUNT).key(key), bitCount1 -> {
          should.assertTrue(bitCount1.succeeded());
          should.assertEquals(26L, bitCount1.result().toLong());

          cluster.send(cmd(BITCOUNT).key(key).arg(0).arg(0), bitCount2 -> {
            should.assertTrue(bitCount2.succeeded());
            should.assertEquals(4L, bitCount2.result().toLong());

            cluster.send(cmd(BITCOUNT).key(key).arg(1).arg(1), bitCount3 -> {
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
  public void testBitTop(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String destkey = makeKey();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();


      cluster.send(cmd(SET).key(key1).arg("foobar"), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(SET).key(key1).arg("abcdef"), set2 -> {
          should.assertTrue(set2.succeeded());

          cluster.send(cmd(BITOP).arg(BitOperation.AND.name()).arg(destkey).arg(key1).arg(key2), bitTop -> {
            should.assertTrue(bitTop.succeeded());
            cluster.send(cmd(GET).key(destkey), get -> {
              should.assertTrue(get.succeeded());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testBlPop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(DEL).key(list1), del1 -> {
        should.assertTrue(del1.succeeded());

        cluster.send(cmd(DEL).key(list2), del2 -> {
          should.assertTrue(del2.succeeded());

          cluster.send(cmd(RPUSH).key(list1).arg("a").arg("b").arg("c"), rPush -> {
            should.assertTrue(rPush.succeeded());

            cluster.send(cmd(BLPOP).arg(list1).arg(list2).arg(0), blPop -> {
              should.assertTrue(blPop.succeeded());
              should.assertEquals(String.join(",", toStringArray(list1, "a")), blPop.result().toString());
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg(Buffer.buffer(value1)), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(BITPOS).key(key).arg(0), bitPos1 -> {
          should.assertTrue(bitPos1.succeeded());
          should.assertEquals(12L, bitPos1.result().toLong());

          cluster.send(cmd(SET).key(key).arg(Buffer.buffer(value2)), set2 -> {
            should.assertTrue(set2.succeeded());

            cluster.send(cmd(BITPOS).key(key).arg(1), bitPos2 -> {
              should.assertTrue(bitPos2.succeeded());
              should.assertEquals(-1L, bitPos2.result().toLong());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testBrPop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(DEL).key(list1), del1 -> {
        should.assertTrue(del1.succeeded());

        cluster.send(cmd(DEL).key(list2), del2 -> {
          should.assertTrue(del2.succeeded());

          cluster.send(cmd(RPUSH).key(list1).arg("a").arg("b").arg("c"), rPush -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg(10), set -> {
        should.assertTrue(set.succeeded());

        cluster.send(cmd(DECR).key(key), decr -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg(10), set -> {
        should.assertTrue(set.succeeded());

        cluster.send(cmd(DECRBY).key(key).arg(5), decrBy -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key1).arg("Hello"), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(SET).key(key1).arg("Hello"), set2 -> {
          should.assertTrue(set2.succeeded());

          cluster.send(cmd(DEL).key(key1).key(key2), del -> {
            should.assertTrue(del.succeeded());
            should.assertEquals(2, del.result().toLong());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testEcho(TestContext should) {
    final Async test = should.async();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key1).arg("Hello"), set -> {
        should.assertTrue(set.succeeded());

        cluster.send(cmd(EXISTS).key(key1), exists -> {
          should.assertTrue(exists.succeeded());
          should.assertEquals(1L, exists.result().toLong());
        });
      });

      cluster.send(cmd(EXISTS).key(key2), exists -> {
        should.assertTrue(exists.succeeded());
        should.assertEquals(1L, exists.result().toLong());
      });
    });
    test.complete();
  }

  @Test
  public void testExpire(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg("Hello"), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(EXPIRE).key(key).arg(10), expire -> {
          should.assertTrue(expire.succeeded());
          should.assertEquals(1L, expire.result().toLong());

          cluster.send(cmd(TTL).key(key), ttl1 -> {
            should.assertTrue(ttl1.succeeded());
            should.assertEquals(10L, ttl1.result().toLong());


            cluster.send(cmd(SET).key(key).arg("Hello World"), set2 -> {
              should.assertTrue(set2.succeeded());

              cluster.send(cmd(TTL).key(key), ttl2 -> {
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

    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(SET).key(key).arg("Hello"), set1 -> {
        should.assertTrue(set1.succeeded());

        cluster.send(cmd(EXISTS).key(key), exists1 -> {
          should.assertTrue(exists1.succeeded());
          should.assertEquals(1L, exists1.result().toLong());

          cluster.send(cmd(EXPIREAT).key(key).arg(1293840000), expireAt -> {
            should.assertTrue(expireAt.succeeded());
            should.assertEquals(1L, expireAt.result().toLong());


            cluster.send(cmd(EXISTS).key(key), exists2 -> {
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
    final String nonExistentKey = makeKey();


    Redis.createClusterClient(rule.vertx(), options, onCreate -> {
      should.assertTrue(onCreate.succeeded());

      final Redis cluster = onCreate.result();

      cluster.send(cmd(GET).key(nonExistentKey), get1 -> {
        should.assertTrue(get1.succeeded());
        should.assertNull(get1.result().toString());

        cluster.send(cmd(SET).key(key).arg("Hello"), set -> {
          should.assertTrue(set.succeeded());

          cluster.send(cmd(GET).key(key), get2 -> {
            should.assertTrue(get2.succeeded());
            should.assertEquals("Hello", get2.result());
            test.complete();
          });
        });

      });
    });
  }
}
