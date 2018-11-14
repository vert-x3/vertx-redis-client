package io.vertx.test.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.*;
import org.junit.*;
import org.junit.runner.RunWith;
import redis.embedded.RedisServer;

import java.util.*;

import static io.vertx.redis.Args.*;

@RunWith(VertxUnitRunner.class)
public class UnoppionatedAPITest {

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private RedisServer server;
  private RedisAPI redis;

  @Before
  public void setUp(TestContext should) throws Exception {
    final Async test = should.async();

    // start a server
    server = new RedisServer(6379);
    server.start();

    Redis client = Redis.create(rule.vertx(), SocketAddress.inetSocketAddress(6379, "localhost"));

    client.exceptionHandler(should::fail);
    client.open(open -> {
      should.assertTrue(open.succeeded());
      redis = RedisAPI.wrap(client);
      test.complete();
    });
  }

  @After
  public void tearDown() {
    server.stop();
  }

  @Test
  public void testAppend(TestContext should) {
    final Async test = should.async();

    final String key = makeKey();

    redis.del(args(key), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.append(args(key, "Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(5L, reply1.result().asLong());

        redis.append(args(key, " World"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(11L, reply2.result().asLong());

          redis.get(args(key), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertTrue(reply3.succeeded());
            should.assertEquals("Hello World", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  //Note the try/finally is to ensure that the server is shutdown so other tests do not have to
  //provide auth information
  public void testAuth(TestContext should) {
    final Async test = should.async();

    RedisServer server = RedisServer.builder().port(6381).setting("requirepass foobar").build();
    server.start();

    Redis client = Redis.create(rule.vertx(), SocketAddress.inetSocketAddress(6381, "localhost"));

    client.open(open -> {
      should.assertTrue(open.succeeded());
      final RedisAPI rdx = RedisAPI.wrap(client);

      rdx.auth(args("barfoo"), reply -> {
        should.assertFalse(reply.succeeded());
        rdx.auth(args("foobar"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          client.close();
          try {
            server.stop();
          } catch (Exception ignore) {
          }
          test.complete();
        });
      });
    });
  }

  @Test
  public void testBgrewriteaof(TestContext should) {
    final Async test = should.async();
    redis.bgrewriteaof(reply -> {
      should.assertTrue(reply.succeeded());
      test.complete();
    });
  }

  @Test
  public void testBgsave(TestContext should) {
    final Async test = should.async();

    redis.bgsave(reply -> {
      should.assertTrue(reply.succeeded());
      should.assertEquals("Background saving started", reply.result().asString());
      test.complete();
    });
  }

  @Test
  public void testBitcount(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    redis.set(args(key, "foobar"), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.bitcount(args(key), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(26, reply1.result().asInteger());

        redis.bitcount(args(key, 0, 0), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(4, reply2.result().asInteger());

          redis.bitcount(args(key, 1, 1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(6, reply3.result().asInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testBitop(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String destkey = makeKey();

    redis.set(args(key1, "foobar"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.set(args(key2, "abcdef"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.bitop(args("AND", destkey, key1, key2), reply2 -> {
          should.assertTrue(reply2.succeeded());
          redis.get(args(destkey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testBlpop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    redis.del(args(list1, list2), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.rpush(args(list1, "a", "b", "c"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(3, reply1.result().asInteger());

        redis.blpop(args(list1, list2, 0), reply2 -> {
          should.assertTrue(reply2.succeeded());
          JsonArray list = reply2.result().asJsonArray();
          should.assertEquals(list1, list.getString(0));
          should.assertEquals("a", list.getString(1));
          test.complete();
        });
      });
    });
  }

  @Test
  public void testBrpop(TestContext should) {
    final Async test = should.async();
    final String list1 = makeKey();
    final String list2 = makeKey();

    redis.del(args(list1, list2), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.rpush(args(list1, "a", "b", "c"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(3, reply1.result().asInteger());

        redis.brpop(args(list1, list2, 0), reply2 -> {
          should.assertTrue(reply2.succeeded());
          JsonArray list = reply2.result().asJsonArray();
          should.assertEquals(list1, list.getString(0));
          should.assertEquals("c", list.getString(1));
          test.complete();
        });
      });
    });
  }

  @Test
  public void testClientList(TestContext should) {
    final Async test = should.async();

    redis.clientList(Args.args(), result -> {
      should.assertTrue(result.succeeded());
      should.assertNotNull(result.result());
      test.complete();
    });
  }

  @Test
  public void testClientSetAndGetName(TestContext should) {
    final Async test = should.async();

    redis.clientGetname(result -> {
      should.assertTrue(result.succeeded());
      should.assertNull(result.result().asString());
      redis.clientSetname(args("test-connection"), result1 -> {
        should.assertTrue(result1.succeeded());
        redis.clientGetname(result2 -> {
          should.assertTrue(result2.succeeded());
          should.assertEquals("test-connection", result2.result().asString());

          test.complete();
        });
      });
    });
  }


  @Test
  public void testConfigGet(TestContext should) {
    final Async test = should.async();
    redis.configGet(args("*"), reply -> {
      should.assertTrue(reply.succeeded());
      should.assertTrue(reply.result() != null && reply.result().size() > 0);
      test.complete();
    });
  }

  @Test
  public void testConfigSetAndGet(TestContext should) {
    final Async test = should.async();

    redis.configSet(args("dbfilename", "redis.dump"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.configGet(args("dbfilename"), reply2 -> {
        if (reply2.succeeded()) {
          should.assertNotNull(reply2.result().asJsonArray().getString(0));
          should.assertTrue(reply2.result().asJsonArray().getString(1).equals("redis.dump"));
          test.complete();
        }
      });
    });
  }

  @Test
  public void testConfigResetstat(TestContext should) {
    final Async test = should.async();

    redis.info(args(), reply -> {
      should.assertTrue(reply.succeeded());
      String[] info = reply.result().asString().split("\r\n");
      int conn = 0;
      for (String l : info) {
        if (l.startsWith("total_connections_received:")) {
          conn = Integer.parseInt(l.substring(27));
          break;
        }
      }

      should.assertTrue(conn > 0);
      redis.configResetstat(reply2 -> {
        should.assertTrue(reply2.succeeded());
        redis.info(args(), reply3 -> {
          should.assertTrue(reply3.succeeded());
          //Note, this may appear strange, but the embedded server handles stats differently
          //Many are not reset correctly. Here we just test the flow of the COMMANDS
          test.complete();
        });

      });
    });
  }

  @Test
  public void testDbsize(TestContext should) {
    final Async test = should.async();

    redis.dbsize(reply -> {
      should.assertTrue(reply.succeeded());
      Long size = reply.result().asLong();
      redis.set(args("new", "value"), reply2 -> {
        should.assertTrue(reply2.succeeded());
        redis.dbsize(reply3 -> {
          should.assertTrue(reply3.succeeded());
          should.assertEquals(size + 1, reply3.result().asLong());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testDecr(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "10"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.decr(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(9, reply1.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testDecrby(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "10"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.decrby(args(mykey, 5), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(5, reply1.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testDel(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    redis.set(args(key1, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.set(args(key2, "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.del(args(key1, key2, key3), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testDiscard(TestContext should) {
    final Async test = should.async();

    String key = makeKey();
    redis.set(args(key, "0"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.multi(reply2 -> {
        should.assertTrue(reply2.succeeded());
        redis.incr(args(key), reply3 -> {
          should.assertTrue(reply3.succeeded());
          redis.discard(reply4 -> {
            should.assertTrue(reply4.succeeded());
            redis.get(args(key), reply5 -> {
              should.assertTrue(reply5.succeeded());
              should.assertEquals(0, reply5.result().asInteger());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testEcho(TestContext should) {
    final Async test = should.async();
    redis.echo(args("Hello World!"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals("Hello World!", reply0.result().asString());
      test.complete();
    });
  }

  @Test
  public void testEval(TestContext should) {
    final Async test = should.async();

    final String key1 = makeKey();
    final String key2 = makeKey();
    redis.eval(args("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", 2, key1, key2, "first", "second"), reply -> {
      should.assertTrue(reply.succeeded());
      Object r = reply.result();
      should.assertNotNull(r);
      test.complete();
    });
  }

  @Test
  public void testEvalshaNumKeysAndValuesDifferent(TestContext should) {
    final Async test = should.async();
    String inline = "return 1";
    redis.scriptLoad(args(inline), reply -> {
      should.assertTrue(reply.succeeded());
      should.assertNotNull(reply.result());

      List<String> keys = new ArrayList<>(2);
      List<String> values = new ArrayList<>(1);
      keys.add("key1");
      keys.add("key2");
      values.add("value1");

      redis.evalsha(args(reply.result().asString(), keys, values), reply2 -> {
        should.assertTrue(reply2.succeeded());
        test.complete();
      });
    });
  }

  @Test
  public void testEvalsha(TestContext should) {
    final Async test = should.async();
    String inline = "return 1";
    redis.scriptLoad(args(inline), reply -> {
      should.assertTrue(reply.succeeded());
      should.assertNotNull(reply.result().asString());
      redis.evalsha(args(reply.result().asString(), 0), reply2 -> {
        should.assertTrue(reply2.succeeded());
        test.complete();
      });
    });
  }

  @Test
  public void testExists(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();

    redis.set(args(key1, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.exists(args(key1), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.exists(args(key2), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testExpire(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.expire(args(mykey, 10), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.ttl(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(10, reply2.result().asInteger());

          redis.set(args(mykey, "Hello World"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            redis.ttl(args(mykey), reply4 -> {
              should.assertTrue(reply4.succeeded());
              should.assertEquals(-1, reply4.result().asInteger());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testExpireat(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.exists(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.expireat(args(mykey, 1293840000), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(1, reply2.result().asInteger());

          redis.exists(args(mykey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(0, reply3.result().asInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testFlushall(TestContext should) {
    final Async test = should.async();

    String key = makeKey();
    //As per the doc, this never fails
    redis.set(args(key, "blah"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.flushall(args(), reply2 -> {
        should.assertTrue(reply.succeeded());
        redis.get(args(key), reply3 -> {
          should.assertTrue(reply3.succeeded());
          should.assertNull(reply3.result().asBuffer());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testFlushdb(TestContext should) {
    final Async test = should.async();
    String key = makeKey();
    //As per the doc, this never fails
    redis.set(args(key, "blah"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.flushdb(args(), reply2 -> {
        should.assertTrue(reply.succeeded());
        redis.get(args(key), reply3 -> {
          should.assertTrue(reply3.succeeded());
          should.assertNull(reply3.result().asBuffer());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testGet(TestContext should) {
    final Async test = should.async();

    final String nonexisting = makeKey();
    final String mykey = makeKey();

    redis.get(args(nonexisting), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNull(reply0.result().asBuffer());

      redis.set(args(mykey, "Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testGetbit(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.setbit(args(mykey, 7, 1), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(0, reply0.result().asInteger());

      redis.getbit(args(mykey, 0), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(0, reply1.result().asInteger());

        redis.getbit(args(mykey, 7), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(1, reply2.result().asInteger());

          redis.getbit(args(mykey, 100), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(0, reply3.result().asInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testGetrange(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "This is a string"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.getrange(args(mykey, 0, 3), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("This", reply1.result().asString());

        redis.getrange(args(mykey, -3, -1), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("ing", reply2.result().asString());

          redis.getrange(args(mykey, 0, -1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("This is a string", reply3.result().asString());

            redis.getrange(args(mykey, 10, 100), reply4 -> {
              should.assertTrue(reply4.succeeded());
              should.assertEquals("string", reply4.result().asString());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testGetset(TestContext should) {
    final Async test = should.async();
    final String mycounter = makeKey();

    redis.incr(args(mycounter), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.getset(args(mycounter, "0"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("1", reply1.result().asString());

        redis.get(args(mycounter), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("0", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHdel(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "foo"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hdel(args(myhash, "field1"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hdel(args(myhash, "field2"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHexists(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "foo"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hexists(args(myhash, "field1"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hexists(args(myhash, "field2"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHget(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "foo"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hget(args(myhash, "field1"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("foo", reply1.result().asString());

        redis.hget(args(myhash, "field2"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertNull(reply2.result().asBuffer());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHgetall(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hset(args(myhash, "field2", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hgetall(args(myhash), reply2 -> {
          should.assertTrue(reply2.succeeded());
          JsonObject obj = reply2.result().asJsonObject();
          should.assertEquals("Hello", obj.getString("field1"));
          should.assertEquals("World", obj.getString("field2"));
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHincrby(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field", "5"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hincrby(args(myhash, "field", 1), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(6, reply1.result().asInteger());

        redis.hincrby(args(myhash, "field", -1), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(5, reply2.result().asInteger());

          redis.hincrby(args(myhash, "field", -10), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(-5, reply3.result().asInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testHIncrbyfloat(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.hset(args(mykey, "field", "10.50"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hincrbyfloat(args(mykey, "field", 0.1), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("10.6", reply1.result().asString());

        redis.hset(args(mykey, "field", "5.0e3"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());

          redis.hincrbyfloat(args(mykey, "field", 2.0e2), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("5200", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testHkeys(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hset(args(myhash, "field2", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hkeys(args(myhash), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().size());
          should.assertEquals("field1", reply2.result().get(0).asString());
          should.assertEquals("field2", reply2.result().get(1).asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHlen(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hset(args(myhash, "field2", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hlen(args(myhash), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHmget(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hset(args(myhash, "field2", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hmget(args(myhash, "field1", "field2", "nofield"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().size());
          should.assertEquals("Hello", reply2.result().get(0).asString());
          should.assertEquals("World", reply2.result().get(1).asString());
          should.assertTrue(reply2.result().get(2).isNull());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHmset(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hmset(args(myhash, "field1", "Hello", "field2", "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.hget(args(myhash, "field1"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("Hello", reply1.result().asString());
        redis.hget(args(myhash, "field2"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("World", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHset(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hget(args(myhash, "field1"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("Hello", reply1.result().asString());
        test.complete();
      });
    });
  }

  @Test
  public void testHsetnx(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hsetnx(args(myhash, "field", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hsetnx(args(myhash, "field", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(0, reply1.result().asInteger());

        redis.hget(args(myhash, "field"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testHvals(TestContext should) {
    final Async test = should.async();
    final String myhash = makeKey();

    redis.hset(args(myhash, "field1", "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.hset(args(myhash, "field2", "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());

        redis.hvals(args(myhash), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().size());
          should.assertEquals("Hello", reply2.result().get(0).asString());
          should.assertEquals("World", reply2.result().get(1).asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testIncr(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "10"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.incr(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(11, reply1.result().asInteger());

        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("11", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testIncrby(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "10"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.incrby(args(mykey, 5), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(15, reply1.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testIncrbyfloat(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.set(args(mykey, "10.50"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.incrbyfloat(args(mykey, 0.1), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("10.6", reply1.result().asString());

        redis.set(args(mykey, "5.0e3"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          redis.incrbyfloat(args(mykey, 2.0e2), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("5200", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testInfo(TestContext should) {
    final Async test = should.async();
    redis.info(args(), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertNotNull(reply0.result());
      test.complete();
    });
  }

  @Test
  public void testKeys(TestContext should) {
    final Async test = should.async();
    redis.mset(args("one", "1", "two", "2", "three", "3", "four", "4"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.keys(args("*o*"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        JsonArray array = reply1.result().asJsonArray();
        // this is because there are leftovers from previous tests
        should.assertTrue(3 <= array.size());

        redis.keys(args("t??"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          JsonArray array2 = reply2.result().asJsonArray();
          should.assertTrue(1 == array2.size());

          redis.keys(args("*"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            JsonArray array3 = reply3.result().asJsonArray();
            should.assertTrue(4 <= array3.size());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testLastsave(TestContext should) {
    final Async test = should.async();
    redis.lastsave(reply0 -> {
      should.assertTrue(reply0.succeeded());
      test.complete();
    });
  }

  @Test
  public void testLindex(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.lpush(args(mykey, "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.lpush(args(mykey, "Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());

        redis.lindex(args(mykey, 0), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());

          redis.lindex(args(mykey, -1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("World", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testLinsert(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();

    redis.rpush(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());

      redis.rpush(args(mykey, "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());

        redis.linsert(args(mykey, "BEFORE", "World", "There"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testLlen(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.lpush(args(mykey, "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.lpush(args(mykey, "Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.llen(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testLpop(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.rpush(args(mykey, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.rpush(args(mykey, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.rpush(args(mykey, "three"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          redis.lpop(args(mykey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("one", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testLpushOne(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.lpush(args(mykey, "world"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.lpush(args(mykey, "hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.lrange(args(mykey, 0, -1), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().size());
          should.assertEquals("hello", reply2.result().get(0).asString());
          should.assertEquals("world", reply2.result().get(1).asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testLpushx(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.lpush(args(mykey, "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.lpushx(args(mykey, "Hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.lpushx(args(myotherkey, "Hello"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          redis.lrange(args(mykey, 0, -1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            JsonArray array3 = reply3.result().asJsonArray();
            should.assertTrue(2 == array3.size());

            should.assertTrue("Hello".equals(array3.getString(0)));
            should.assertTrue("World".equals(array3.getString(1)));
            redis.lrange(args(myotherkey, 0, -1), reply4 -> {
              JsonArray array4 = reply4.result().asJsonArray();
              should.assertTrue(0 == array4.size());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testLrange(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.rpush(args(mykey, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.rpush(args(mykey, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.rpush(args(mykey, "three"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          redis.lrange(args(mykey, 0, 0), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("one", reply3.result().get(0).asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testLrem(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.rpush(args(mykey, "hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.rpush(args(mykey, "hello"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.rpush(args(mykey, "foo"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          redis.rpush(args(mykey, "hello"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(4, reply3.result().asInteger());
            redis.lrem(args(mykey, -2, "hello"), reply4 -> {
              should.assertTrue(reply4.succeeded());
              should.assertEquals(2, reply4.result().asInteger());
              redis.lrange(args(mykey, 0, -1), reply5 -> {
                should.assertTrue(reply5.succeeded());
                should.assertEquals(2, reply5.result().size());
                should.assertEquals("hello", reply5.result().get(0).asString());
                should.assertEquals("foo", reply5.result().get(1).asString());
                test.complete();
              });
            });
          });
        });
      });
    });
  }

  @Test
  public void testLset(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.rpush(args(mykey, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.rpush(args(mykey, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.rpush(args(mykey, "three"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          redis.lset(args(mykey, 0, "four"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            redis.lset(args(mykey, -2, "five"), reply4 -> {
              should.assertTrue(reply4.succeeded());
              redis.lrange(args(mykey, 0, -1), reply5 -> {
                should.assertTrue(reply5.succeeded());
                should.assertEquals(3, reply5.result().size());
                should.assertEquals("four", reply5.result().get(0).asString());
                should.assertEquals("five", reply5.result().get(1).asString());
                should.assertEquals("three", reply5.result().get(2).asString());
                test.complete();
              });
            });
          });
        });
      });
    });
  }

  @Test
  public void testLtrim(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.rpush(args(mykey, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.rpush(args(mykey, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(2, reply1.result().asInteger());
        redis.rpush(args(mykey, "three"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().asInteger());
          redis.ltrim(args(mykey, 1, -1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            redis.lrange(args(mykey, 0, -1), reply5 -> {
              should.assertTrue(reply5.succeeded());
              should.assertEquals(2, reply5.result().size());
              should.assertEquals("two", reply5.result().get(0).asString());
              should.assertEquals("three", reply5.result().get(1).asString());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testMget(TestContext should) {
    final Async test = should.async();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    redis.set(args(mykey1, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.set(args(mykey2, "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.mget(args(mykey1, mykey2, "nonexisting"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().size());
          should.assertEquals("Hello", reply2.result().get(0).asString());
          should.assertEquals("World", reply2.result().get(1).asString());
          should.assertTrue(reply2.result().get(2).isNull());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testMonitor(TestContext should) {
    final Async test = should.async();

    redis.monitor(reply -> {
      should.assertTrue(reply.succeeded());
      test.complete();
    });
  }

  @Test
  public void testMove(TestContext should) {
    final Async test = should.async();

    String key = makeKey();
    redis.set(args(key, "moved_key"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.move(args(key, 1), reply2 -> {
        should.assertTrue(reply2.succeeded());
        should.assertEquals(1, reply2.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testMset(TestContext should) {
    final Async test = should.async();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    redis.mset(args(mykey1, "Hello", mykey2, "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.get(args(mykey1), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("Hello", reply1.result().asString());
        redis.get(args(mykey2), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("World", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testMsetnx(TestContext should) {
    final Async test = should.async();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    final String mykey3 = makeKey();

    redis.msetnx(args(mykey1, "Hello", mykey2, "there"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.msetnx(args(mykey2, "there", mykey3, "world"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(0, reply1.result().asInteger());
        redis.mget(args(mykey1, mykey2, mykey3), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(3, reply2.result().size());
          should.assertEquals("Hello", reply2.result().get(0).asString());
          should.assertEquals("there", reply2.result().get(1).asString());
          should.assertTrue(reply2.result().get(2).isNull());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testMulti(TestContext should) {
    final Async test = should.async();

    String key = makeKey();
    redis.set(args(key, "0"), rep -> {
      should.assertTrue(rep.succeeded());
      redis.multi(reply -> {
        should.assertTrue(reply.succeeded());
        redis.set(args(makeKey(), "0"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          redis.set(args(makeKey(), "0"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            redis.exec(reply4 -> {
              should.assertTrue(reply4.succeeded());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testObject(TestContext should) {
    final Async test = should.async();

    String mykey = makeKey();
    redis.set(args(mykey, "test"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.object(args("REFCOUNT", mykey), reply2 -> {
        should.assertTrue(reply2.succeeded());
        redis.object(args("ENCODING", mykey), reply3 -> {
          should.assertTrue(reply3.succeeded());
          redis.object(args("IDLETIME", mykey), reply4 -> {
            should.assertTrue(reply4.succeeded());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testPersist(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.expire(args(mykey, 10), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.ttl(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(10, reply2.result().asInteger());
          redis.persist(args(mykey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(1, reply3.result().asInteger());
            redis.ttl(args(mykey), reply4 -> {
              should.assertTrue(reply4.succeeded());
              should.assertEquals(-1, reply4.result().asInteger());
              test.complete();
            });
          });
        });
      });
    });
  }

  @Test
  public void testPexpire(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.pexpire(args(mykey, 1000), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testPexpireat(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.pexpireat(args(mykey, 1555555555005L), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.ttl(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertTrue(200000000 > reply2.result().asLong() && reply2.result().asLong() > 0);
          redis.pttl(args(mykey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertTrue(1555555555005L > reply3.result().asLong() && reply3.result().asLong() > 0);
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testPing(TestContext should) {
    final Async test = should.async();
    redis.ping(args(), reply0 -> {
      should.assertEquals("PONG", reply0.result().asString());
      test.complete();
    });
  }

  @Test
  public void testPsetex(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.psetex(args(mykey, 2000, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.pttl(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertTrue(3000 > reply1.result().asLong() && reply1.result().asLong() > 0);
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testPttl(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.expire(args(mykey, 3), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.pttl(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertTrue(3000 >= reply2.result().asLong() && reply2.result().asLong() > 0);
          test.complete();
        });
      });
    });
  }

  @Test
  public void testPublish(TestContext should) {
    final Async test = should.async();
    String key = makeKey();
    redis.set(args(key, "0"), reply -> {
      should.assertTrue(reply.succeeded());
      redis.publish(args(key, "1"), reply2 -> {
        should.assertTrue(reply2.succeeded());
        should.assertEquals(0, reply2.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testRandomkey(TestContext should) {
    final Async test = should.async();

    redis.set(args("foo", "bar"), reply -> {
      if (reply.succeeded()) {
        redis.randomkey(reply2 -> {
          if (reply2.succeeded()) {
            should.assertNotNull(reply2.result());
            test.complete();
          }
        });
      }
    });
  }

  @Test
  public void testRename(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.rename(args(mykey, myotherkey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.get(args(myotherkey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testRenamenx(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.set(args(myotherkey, "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.renamenx(args(mykey, myotherkey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          redis.get(args(myotherkey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("World", reply3.result().asString());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testRestore(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    final String myotherkey = makeKey();
    final Buffer value = Buffer.buffer(new byte[]{(byte) 0xff, (byte) 0xf0, 0x00, 'A', 'B', 'C'});
    redis.set(args(mykey, value), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.dump(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        Buffer dump = reply1.result().asBuffer();
        redis.restore(args(myotherkey, 0, dump), reply2 -> {
          should.assertTrue(reply2.succeeded());
          redis.get(args(myotherkey), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(value, reply3.result().asBuffer());

            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testSave(TestContext should) {
    final Async test = should.async();
    redis.save(reply -> {
      should.assertTrue(reply.succeeded());
      //Note, there's really not much else to do
      test.complete();
    });
  }

  @Test
  public void testScard(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.sadd(args(mykey, "Hello", "World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(2, reply0.result().asInteger());
      redis.scard(args(mykey), reply2 -> {
        should.assertTrue(reply2.succeeded());
        should.assertEquals(2, reply2.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testScriptexists(TestContext should) {
    final Async test = should.async();
    String inline = "return 1";
    redis.scriptLoad(args(inline), reply -> {
      should.assertTrue(reply.succeeded());
      String hash = reply.result().asString();
      redis.scriptExists(args(hash), reply2 -> {
        should.assertTrue(reply2.succeeded());
        should.assertTrue(reply2.result().get(0).asInteger() > 0);
        test.complete();
      });
    });
  }

  @Test
  public void testScriptload(TestContext should) {
    final Async test = should.async();
    String inline = "return 1";
    redis.scriptLoad(args(inline), reply -> {
      should.assertTrue(reply.succeeded());
      should.assertNotNull(reply.result());
      test.complete();
    });
  }

  @Test
  public void testSelect(TestContext should) {
    final Async test = should.async();
    //Gee, think redis should have a get current DB command?
    redis.select(args(1), reply -> {
      if (reply.succeeded()) {
        redis.set(args("first", "value"), reply2 -> {
          if (reply2.succeeded()) {
            redis.select(args(0), reply3 -> {
              if (reply3.succeeded()) {
                redis.select(args(1), reply4 -> {
                  if (reply4.succeeded()) {
                    redis.get(args("first"), reply5 -> {
                      if (reply5.succeeded()) {
                        should.assertEquals("value", reply5.result().asString());
                        test.complete();
                      }
                    });
                  }
                });
              }
            });
          }
        });
      }
    });
  }

  @Test
  public void testSet(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.get(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("Hello", reply1.result().asString());
        test.complete();
      });
    });
  }

  @Test
  public void testSetWithOptions2(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    // 1st case SET k v NX -> OK
    redis.set(args(mykey, "Hello!", "NX"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      // 2nd case SET k v NX -> nil (was already set)
      redis.set(args(mykey, "Hello again!", "NX"), reply2 -> {
        should.assertTrue(reply2.succeeded());
        should.assertTrue(reply2.result().isNull());
        test.complete();
      });
    });
  }

  @Test
  public void testSetbit(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.setbit(args(mykey, 7, 1), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(0, reply0.result().asInteger());
      redis.setbit(args(mykey, 7, 0), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("\u0000", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSetex(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.setex(args(mykey, 10, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.ttl(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(10, reply1.result().asInteger());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSetnx(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.setnx(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.setnx(args(mykey, "World"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(0, reply1.result().asInteger());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSetrange(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello World"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.setrange(args(mykey, 6, "Redis"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(11, reply1.result().asInteger());
        redis.get(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("Hello Redis", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSinterstore(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(args(mykey1, "a", "b", "c"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.sadd(args(mykey2, "c", "d", "e"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(3, reply1.result().asInteger());
        redis.sinterstore(args(mykey, mykey1, mykey2), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(1, reply2.result().asInteger());
          //assertArrayEquals(new Object[]{"c"}, reply6.result().getList().toArray());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSismember(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.sadd(args(mykey, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.sismember(args(mykey, "one"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.sismember(args(mykey, "two"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSrem(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.sadd(args(mykey, "one", "two", "three"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.srem(args(mykey, "one"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.srem(args(mykey, "four"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testStrlen(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello world"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.strlen(args(mykey), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(11, reply1.result().asInteger());
        redis.strlen(args("nonexisting"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(0, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testSubscribe(TestContext should) {
    final Async test = should.async();

    String key = makeKey();
    redis.subscribe(args(key), reply -> {
      should.assertTrue(reply.succeeded());
      redis.unsubscribe(args(key), reply2 -> {
        should.assertTrue(reply2.succeeded());
        test.complete();
      });
    });
  }

  @Test
  public void testSync(TestContext should) {
    final Async test = should.async();
    redis.sync(v -> {
      should.assertTrue(v.succeeded());
      test.complete();

    });
  }

  @Test
  public void testTime(TestContext should) {
    final Async test = should.async();
    redis.time(reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertTrue(reply0.result().size() == 2);
      test.complete();
    });
  }

  @Test
  public void testTtl(TestContext should) {
    final Async test = should.async();
    final String mykey = makeKey();
    redis.set(args(mykey, "Hello"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.expire(args(mykey, 10), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.ttl(args(mykey), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(10, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testType(TestContext should) {
    final Async test = should.async();
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    redis.set(args(key1, "value"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.lpush(args(key2, "value"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.sadd(args(key3, "value"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(1, reply2.result().asInteger());
          redis.type(args(key1), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals("string", reply3.result().asString());
            redis.type(args(key2), reply4 -> {
              should.assertTrue(reply4.succeeded());
              should.assertEquals("list", reply4.result().asString());
              redis.type(args(key3), reply5 -> {
                should.assertTrue(reply5.succeeded());
                should.assertEquals("set", reply5.result().asString());
                test.complete();
              });
            });
          });
        });
      });
    });
  }

  @Test
  public void testUnsubscribe(TestContext should) {
    final Async test = should.async();
    String key = makeKey();
    redis.subscribe(args(key), reply -> {
      should.assertTrue(reply.succeeded());
      redis.unsubscribe(args(key), reply2 -> {
        should.assertTrue(reply2.succeeded());
        test.complete();
      });
    });
  }

  @Test
  public void testZcard(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.zadd(args(key, 2, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.zcard(args(key), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(2, reply2.result().asInteger());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testZcount(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.zadd(args(key, 2, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.zadd(args(key, 3, "three"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(1, reply2.result().asInteger());
          redis.zcount(args(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(3, reply3.result().asInteger());
            test.complete();
          });
        });
      });
    });
  }

  @Test
  public void testZincrby(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.zadd(args(key, 2, "two"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(1, reply1.result().asInteger());
        redis.zincrby(args(key, 2, "one"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals("3", reply2.result().asString());
          test.complete();
        });
      });
    });
  }

  @Test
  public void testZrangebyscoreWithLimits(TestContext should) {
    final Async test = should.async();
    redis.zrangebyscore(args("yourkey", "-inf", "+inf", "LIMIT", 0, 1), result -> {
      // whatever
      should.assertTrue(result.succeeded());
      test.complete();
    });
  }

  @Test
  public void testZrank(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1.0, "one", 2.0, "two", 3.0, "three"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.zrank(args(key, "three"), reply3 -> {
        should.assertTrue(reply3.succeeded());
        should.assertEquals(2, reply3.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testZrem(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1.0, "one", 2.0, "two", 3.0, "three"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.zrem(args(key, "two"), reply3 -> {
        should.assertTrue(reply3.succeeded());
        should.assertEquals(1, reply3.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testZremrangebyrank(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1.0, "one", 2.0, "two", 3.0, "three"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.zremrangebyrank(args(key, 0, 1), reply3 -> {
        should.assertTrue(reply3.succeeded());
        should.assertEquals(2, reply3.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testZrevrank(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1.0, "one", 2.0, "two", 3.0, "three"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(3, reply0.result().asInteger());
      redis.zrevrank(args(key, "one"), reply3 -> {
        should.assertTrue(reply3.succeeded());
        should.assertEquals(2, reply3.result().asInteger());
        test.complete();
      });
    });
  }

  @Test
  public void testZscore(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();
    redis.zadd(args(key, 1, "one"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      should.assertEquals(1, reply0.result().asInteger());
      redis.zscore(args(key, "one"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals("1", reply1.result().asString());
        test.complete();
      });
    });
  }
}
