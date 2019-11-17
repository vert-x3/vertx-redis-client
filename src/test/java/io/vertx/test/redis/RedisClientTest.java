/*
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.test.redis;

import io.vertx.core.Context;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server unless
 * the <code>host</code> or <code>port</code> system property is specified. In this case the
 * test assumes an external database will be used.
 */
@RunWith(VertxUnitRunner.class)
public class RedisClientTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private Redis client;
  private RedisAPI redis;

  @Before
  public void before(TestContext should) {
    final Async before = should.async();

    Context context = rule.vertx().getOrCreateContext();
    client = Redis.createClient(rule.vertx(), new RedisOptions().setConnectionString("redis://localhost:7006"));
    client.connect(onConnect -> {
      should.assertTrue(onConnect.succeeded());
      should.assertEquals(context, rule.vertx().getOrCreateContext());
      redis = RedisAPI.api(onConnect.result());
      before.complete();
    });
  }

  @After
  public void after() {
    client.close();
  }

  private static String getProperty(String name) {
    String s = System.getProperty(name);
    return (s != null && s.trim().length() > 0) ? s : null;
  }

  private static JsonArray toJsonArray(final Object... params) {
    return (params != null) ? new JsonArray(Arrays.asList(params)) : null;
  }

  private static Object[] toArray(final Object... params) {
    return params;
  }

  private static String errorMessage(Throwable t) {
    return t != null ? t.getMessage() : "";
  }

  private static String makeKey() {
    return UUID.randomUUID().toString();
  }

  private static Map<String, Object> toMap(final String... params) {
    if (params.length % 2 != 0) {
      throw new IllegalArgumentException("Last key has no value");
    }
    Map<String, Object> result = new HashMap<>();
    String key = null;
    for (String param : params) {
      if (key == null) {
        key = param;
      } else {
        result.put(key, param);
        key = null;
      }
    }
    return result;
  }

  @SafeVarargs
  private static <T> List<T> toList(final T... params) {
    return Arrays.asList(params);
  }

  @Test
  public void testContextReturn(TestContext should) {
    final Async test = should.async();
    Context context = rule.vertx().getOrCreateContext();
    redis.append(makeKey(), "Hello", reply1 -> {
      should.assertEquals(context, rule.vertx().getOrCreateContext());
      test.complete();
    });
  }

  @Test
  public void testAppend(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    redis.del(toList(key), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.append(key, "Hello", reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(5L, reply1.result().toLong());

        redis.append(key, " World", reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(11L, reply2.result().toLong());

          redis.get(key, reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertTrue(reply3.succeeded());
            should.assertEquals("Hello World", reply3.result().toString());
            test.complete();
          });
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

    redis.bgsave(toList("SCHEDULE"), reply -> {
      should.assertTrue(reply.succeeded());
      should.assertNotNull(reply.result().toString());
      should.assertTrue(reply.result().toString().startsWith("Background saving"));
      test.complete();
    });

  }

  @Test
  public void testBitcount(TestContext should) {
    final Async test = should.async();
    final String key = makeKey();

    redis.set(toList(key, "foobar"), reply0 -> {
      should.assertTrue(reply0.succeeded());

      redis.bitcount(toList(key), reply1 -> {
        should.assertTrue(reply1.succeeded());
        should.assertEquals(26L, reply1.result().toLong());

        redis.bitcount(toList(key, "0", "0"), reply2 -> {
          should.assertTrue(reply2.succeeded());
          should.assertEquals(4L, reply2.result().toLong());

          redis.bitcount(toList(key, "1", "1"), reply3 -> {
            should.assertTrue(reply3.succeeded());
            should.assertEquals(6L, reply3.result().toLong());
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

    redis.set(toList(key1, "foobar"), reply0 -> {
      should.assertTrue(reply0.succeeded());
      redis.set(toList(key2, "abcdef"), reply1 -> {
        should.assertTrue(reply1.succeeded());
        redis.bitop(toList("AND", destkey, key1, key2), reply2 -> {
          should.assertTrue(reply2.succeeded());
          redis.get(destkey, reply3 -> {
            should.assertTrue(reply3.succeeded());
            test.complete();
          });
        });
      });
    });
  }

////  @Test
////  public void testBlpop(TestContext should) {
////    final Async test = should.async();
////    final String list1 = makeKey();
////    final String list2 = makeKey();
////
////    redis.delMany(toList(list1, list2), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////
////      redis.rpushMany(list1, toList("a", "b", "c"), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(3, reply1.result().toLong());
////
////        redis.blpopMany(toList(list1, list2), 0, reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(toArray(list1, "a"), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testBrpop(TestContext should) {
////    final Async test = should.async();
////    final String list1 = makeKey();
////    final String list2 = makeKey();
////
////    redis.delMany(toList(list1, list2), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////
////      redis.rpushMany(list1, toList("a", "b", "c"), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(3, reply1.result().toLong());
////
////        redis.brpopMany(toList(list1, list2), 0, reply2 -> {
////          should.assertTrue(errorMessage(reply2.cause()), reply2.succeeded());
////          should.assertArrayEquals(toArray(list1, "c"), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testBrpoplpush(TestContext should) {
////    final Async test = should.async();
////    redis.brpoplpush("list1", "list2", 100, result -> {
////
////      if (result.succeeded()) {
////        redis.lpop("list2", result2 -> {
////          if (result2.succeeded()) {
////            System.out.println(result2.result());
////            should.assertTrue("hello".equals(result2.result()));
////          }
////          test.complete();
////        });
////      } else {
////        result.cause().printStackTrace();
////        fail();
////      }
////    });
////
////    RedisClient redis2 = RedisClient.create(vertx, getConfig());
////
////    redis2.lpush("list1", "hello", result -> {
////    });
////
////
////  }
//
////  @Test
////  public void testClientKill(TestContext should) {
////    final Async test = should.async();
////
////    redis.clientList(reply -> {
////      should.assertTrue(reply.succeeded());
////      String clients = reply.result();
////      String[] keyvals = clients.split("\\s");
////      String addr = null;
////      for (String keyval : keyvals) {
////        String[] pair = keyval.split("=");
////        if ("addr".equals(pair[0])) {
////          addr = pair[1];
////          break;
////        }
////      }
////      should.assertNotNull("No addr in CLIENT LIST", addr);
////      redis.clientKill(new KillFilter().setAddr(addr).setType(KillFilter.Type.NORMAL), reply2 -> {
////        should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
////        test.complete();
////      });
////    });
////
////  }
//
//  @Test
//  public void testClientList(TestContext should) {
//    final Async test = should.async();
//
//    redis.clientList(result -> {
//      should.assertTrue(result.succeeded());
//      should.assertNotNull(result.result());
//      test.complete();
//    });
//
//  }
//
////  @Test
////  public void testClientSetAndGetName(TestContext should) {
////    final Async test = should.async();
////    redis.clientGetname(result -> {
////      should.assertTrue(String.valueOf(result.cause()), result.succeeded());
////      should.assertNull(result.result());
////      redis.clientSetname("test-connection", result1 -> {
////        should.assertTrue(result1.succeeded());
////        redis.clientGetname(result2 -> {
////          should.assertTrue(result2.succeeded());
////          should.assertEquals("test-connection", result2.result());
////
////          test.complete();
////        });
////      });
////    });
////
////
////  }
//
//
//  @Test
//  public void testConfigGet(TestContext should) {
//    final Async test = should.async();
//    redis.configGet("*", reply -> {
//      should.assertTrue(reply.succeeded());
//      should.assertTrue(reply.result() != null && reply.result().size() > 0);
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testConfigSetAndGet(TestContext should) {
//    final Async test = should.async();
//
//    redis.configSet("dbfilename", "redis.dump", reply -> {
//      if (reply.succeeded()) {
//        redis.configGet("dbfilename", reply2 -> {
//          if (reply2.succeeded()) {
//            should.assertNotNull(reply2.result().getString(0));
//            should.assertTrue(reply2.result().getString(1).equals("redis.dump"));
//            test.complete();
//          }
//        });
//      }
//    });
//
//
//  }
//
//  @Test
//  public void testConfigResetstat(TestContext should) {
//    final Async test = should.async();
//
//    redis.info(reply -> {
//      should.assertTrue(reply.succeeded());
//      JsonObject result = reply.result().getJsonObject("stats");
//      Integer conn = Integer.valueOf(result.getString("total_connections_received"));
//      should.assertTrue(conn > 0);
//      redis.configResetstat(reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        redis.info(reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          //Note, this may appear strange, but the embedded server handles stats differently
//          //Many are not reset correctly. Here we just test the flow of the COMMANDS
//          test.complete();
//        });
//
//      });
//    });
//  }
//
//  @Test
//  public void testDbsize(TestContext should) {
//    final Async test = should.async();
//
//    redis.dbsize(reply -> {
//      should.assertTrue(reply.succeeded());
//      Long size = reply.result();
//      redis.set("new", "value", reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        redis.dbsize(reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          should.assertEquals(Long.valueOf(size + 1L), reply3.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testDebugSegfault(TestContext should) {
////    final Async test = should.async();
////    RedisServer server = RedisServer.builder().port(6381).build();
////    server.start();
////    RedisOptions job = new RedisOptions()
////      .setHost("localhost")
////      .setPort(6381);
////    RedisClient rdx = RedisClient.create(vertx, job);
////
////    rdx.debugSegfault(reply -> {
////      // this should fail, since we crashed the server on purpose
////      should.assertTrue(reply.failed());
////      rdx.info(reply2 -> {
////        should.assertFalse(reply2.succeeded());
////        server.stop();
////        test.complete();
////      });
////    });
////
////  }
//
//  @Test
//  public void testDecr(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "10", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.decr(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(9L, reply1.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testDecrby(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "10", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.decrby(mykey, 5, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(5L, reply1.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testDel(TestContext should) {
//    final Async test = should.async();
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//    final String key3 = makeKey();
//
//    redis.set(key1, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.set(key2, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.delMany(toList(key1, key2, key3), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(2L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testDiscard(TestContext should) {
//    final Async test = should.async();
//
//    String key = makeKey();
//    redis.set(key, "0", reply -> {
//      should.assertTrue(reply.succeeded());
//      RedisTransaction transaction = redis.transaction();
//      transaction.multi(reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        transaction.incr(key, reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          transaction.discard(reply4 -> {
//            should.assertTrue(reply4.succeeded());
//            transaction.get(key, reply5 -> {
//              should.assertTrue(reply5.succeeded());
//              should.assertTrue(Integer.valueOf(reply5.result()) == 0);
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testEcho(TestContext should) {
//    final Async test = should.async();
//    redis.echo("Hello World!", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals("Hello World!", reply0.result());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testEval(TestContext should) {
//    final Async test = should.async();
//
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//    redis.eval("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", toList(key1, key2), toList("first", "second"),
//      reply -> {
//        should.assertTrue(reply.succeeded());
//        Object r = reply.result();
//        should.assertNotNull(r);
//        test.complete();
//      });
//
//  }
//
//  @Test
//  public void testEvalshaNumKeysAndValuesDifferent(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.scriptLoad(inline, reply -> {
//      should.assertTrue(reply.succeeded());
//      should.assertNotNull(reply.result());
//
//      List<String> keys = new ArrayList<>(2);
//      List<String> values = new ArrayList<>(1);
//      keys.add("key1");
//      keys.add("key2");
//      values.add("value1");
//
//      redis.evalsha(reply.result(), keys, values, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        test.complete();
//      });
//    });
//
//
//  }
//
//  @Test
//  public void testEvalsha(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.scriptLoad(inline, reply -> {
//      should.assertTrue(reply.succeeded());
//      should.assertNotNull(reply.result());
//      redis.evalsha(reply.result(), Collections.emptyList(), Collections.emptyList(), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        test.complete();
//      });
//    });
//
//
//  }
//
//  @Test
//  //Note same test as testMulti, kept for consistency
//  public void testExec(TestContext should) {
//    final Async test = should.async();
//    RedisTransaction transaction = redis.transaction();
//    transaction.multi(reply -> {
//      should.assertTrue(reply.succeeded());
//      transaction.set("multi-key", "first", reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        transaction.set("multi-key2", "second", reply3 -> should.assertTrue(reply3.succeeded()));
//        transaction.get("multi-key", reply4 -> {
//          should.assertTrue(reply4.succeeded());
//          should.assertTrue("QUEUED".equalsIgnoreCase(reply4.result()));
//        });
//        transaction.exec(reply5 -> {
//          should.assertTrue(reply5.succeeded());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testExists(TestContext should) {
//    final Async test = should.async();
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//
//    redis.set(key1, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.exists(key1, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.exists(key2, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
////  // Require a more recent version of Redis
////  @Test
////  public void testExistsMany(TestContext should) {
////    final Async test = should.async();
////    String key1 = makeKey();
////    String key2 = makeKey();
////    redis.set(key1, "Hello", onSuccess(reply0 -> {
////      redis.existsMany(toList(key1, key2), onSuccess(reply1 -> {
////        should.assertEquals(1, reply1.toLong());
////        redis.set(key2, "Hello", onSuccess(reply2 -> {
////          redis.existsMany(toList(key1, key2), onSuccess(reply3 -> {
////            should.assertEquals(2, reply3.toLong());
////            redis.existsMany(toList(key1), onSuccess(reply4 -> {
////              should.assertEquals(1, reply4.toLong());
////              test.complete();
////            }));
////          }));
////        }));
////      }));
////    }));
////
////  }
//
//  @Test
//  public void testExpire(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.expire(mykey, 10, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.ttl(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(10L, reply2.result().toLong());
//
//          redis.set(mykey, "Hello World", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            redis.ttl(mykey, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals(-1L, reply4.result().toLong());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testExpireat(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.exists(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.expireat(mykey, 1293840000, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1L, reply2.result().toLong());
//
//          redis.exists(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(0L, reply3.result().toLong());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testFlushall(TestContext should) {
//    final Async test = should.async();
//
//    String key = makeKey();
//    //As per the doc, this never fails
//    redis.set(key, "blah", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.flushall(reply2 -> {
//        should.assertTrue(reply.succeeded());
//        redis.get(key, reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          should.assertNull(reply3.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testFlushdb(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//    //As per the doc, this never fails
//    redis.set(key, "blah", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.flushall(reply2 -> {
//        should.assertTrue(reply.succeeded());
//        redis.get(key, reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          should.assertNull(reply3.result());
//          test.complete();
//        });
//      });
//    });
//
//
//  }
//
//  @Test
//  public void testGet(TestContext should) {
//    final Async test = should.async();
//    final String nonexisting = makeKey();
//    final String mykey = makeKey();
//
//    redis.get(nonexisting, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertNull(reply0.result());
//
//      redis.set(mykey, "Hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testGetbit(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.setbit(mykey, 7, 1, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(0L, reply0.result().toLong());
//
//      redis.getbit(mykey, 0, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(0L, reply1.result().toLong());
//
//        redis.getbit(mykey, 7, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1L, reply2.result().toLong());
//
//          redis.getbit(mykey, 100, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(0L, reply3.result().toLong());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testGetrange(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "This is a string", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.getrange(mykey, 0, 3, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("This", reply1.result());
//
//        redis.getrange(mykey, -3, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("ing", reply2.result());
//
//          redis.getrange(mykey, 0, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("This is a string", reply3.result());
//
//            redis.getrange(mykey, 10, 100, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals("string", reply4.result());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testGetset(TestContext should) {
//    final Async test = should.async();
//    final String mycounter = makeKey();
//
//    redis.incr(mycounter, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.getset(mycounter, "0", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("1", reply1.result());
//
//        redis.get(mycounter, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("0", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHdel(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "foo", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hdel(myhash, "field1", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hdel(myhash, "field2", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHexists(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "foo", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hexists(myhash, "field1", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hexists(myhash, "field2", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHget(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "foo", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hget(myhash, "field1", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("foo", reply1.result());
//
//        redis.hget(myhash, "field2", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertNull(reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHgetall(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hset(myhash, "field2", "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hgetall(myhash, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          JsonObject obj = reply2.result();
//          should.assertEquals("Hello", obj.getString("field1"));
//          should.assertEquals("World", obj.getString("field2"));
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHincrby(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field", "5", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hincrby(myhash, "field", 1, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(6L, reply1.result().toLong());
//
//        redis.hincrby(myhash, "field", -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(5L, reply2.result().toLong());
//
//          redis.hincrby(myhash, "field", -10, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(-5L, reply3.result().toLong());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHIncrbyfloat(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.hset(mykey, "field", "10.50", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hincrbyfloat(mykey, "field", 0.1, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("10.6", reply1.result());
//
//        redis.hset(mykey, "field", "5.0e3", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//
//          redis.hincrbyfloat(mykey, "field", 2.0e2, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("5200", reply3.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testHkeys(TestContext should) {
////    final Async test = should.async();
////    final String myhash = makeKey();
////
////    redis.hset(myhash, "field1", "Hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////
////      redis.hset(myhash, "field2", "World", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////
////        redis.hkeys(myhash, reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(toArray("field1", "field2"), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testHlen(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hset(myhash, "field2", "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hlen(myhash, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(2L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHmget(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hset(myhash, "field2", "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hmget(myhash, toList("field1", "field2", "nofield"), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("Hello", "World", null), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHmset(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hmset(myhash, new JsonObject(toMap("field1", "Hello", "field2", "World")), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.hget(myhash, "field1", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("Hello", reply1.result());
//        redis.hget(myhash, "field2", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("World", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHset(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hget(myhash, "field1", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("Hello", reply1.result());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testHsetnx(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hsetnx(myhash, "field", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hsetnx(myhash, "field", "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(0L, reply1.result().toLong());
//
//        redis.hget(myhash, "field", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testHvals(TestContext should) {
//    final Async test = should.async();
//    final String myhash = makeKey();
//
//    redis.hset(myhash, "field1", "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.hset(myhash, "field2", "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//
//        redis.hvals(myhash, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("Hello", "World"), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testIncr(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "10", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.incr(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(11L, reply1.result().toLong());
//
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("11", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testIncrby(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "10", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.incrby(mykey, 5, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(15L, reply1.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testIncrbyfloat(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.set(mykey, "10.50", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.incrbyfloat(mykey, 0.1, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("10.6", reply1.result());
//
//        redis.set(mykey, "5.0e3", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          redis.incrbyfloat(mykey, 2.0e2, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("5200", reply3.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testInfo(TestContext should) {
//    final Async test = should.async();
//    redis.info(reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertNotNull(reply0.result());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testKeys(TestContext should) {
//    final Async test = should.async();
//    redis.mset(new JsonObject(toMap("one", "1", "two", "2", "three", "3", "four", "4")), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.keys("*o*", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        JsonArray array = reply1.result();
//        // this is because there are leftovers from previous tests
//        should.assertTrue(3 <= array.size());
//
//        redis.keys("t??", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          JsonArray array2 = reply2.result();
//          should.assertTrue(1 == array2.size());
//
//          redis.keys("*", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            JsonArray array3 = reply3.result();
//            should.assertTrue(4 <= array3.size());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLastsave(TestContext should) {
//    final Async test = should.async();
//    redis.lastsave(reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testLindex(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.lpush(mykey, "World", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.lpush(mykey, "Hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//
//        redis.lindex(mykey, 0, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//
//          redis.lindex(mykey, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("World", reply3.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLinsert(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    redis.rpush(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//
//      redis.rpush(mykey, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//
//        redis.linsert(mykey, InsertOptions.BEFORE, "World", "There", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLlen(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.lpush(mykey, "World", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.lpush(mykey, "Hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.llen(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(2L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLpop(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.rpush(mykey, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.rpush(mykey, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3L, reply2.result().toLong());
//          redis.lpop(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("one", reply3.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLpushOne(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.lpush(mykey, "world", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.lpush(mykey, "hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.lrange(mykey, 0, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("hello", "world"), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLpush(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.lpushMany(mykey, toList("!", "world"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2L, reply0.result().toLong());
//      redis.lpushMany(mykey, toList(",", "hello"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(4L, reply1.result().toLong());
//        redis.lrange(mykey, 0, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("hello", ",", "world", "!"), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLpushx(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//
//    redis.lpush(mykey, "World", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.lpushx(mykey, "Hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.lpushx(myotherkey, "Hello", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          redis.lrange(mykey, 0, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            JsonArray array3 = reply3.result();
//            should.assertTrue(2 == array3.size());
//
//            should.assertTrue("Hello".equals(array3.getString(0)));
//            should.assertTrue("World".equals(array3.getString(1)));
//            redis.lrange(myotherkey, 0, -1, reply4 -> {
//              JsonArray array4 = reply4.result();
//              should.assertTrue(0 == array4.size());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testLrange(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.rpush(mykey, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.rpush(mykey, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3L, reply2.result().toLong());
//          redis.lrange(mykey, 0, 0, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("one", reply3.result().getString(0));
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testLrem(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.rpush(mykey, "hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpush(mykey, "hello", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.rpush(mykey, "foo", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(3, reply2.result().toLong());
////          redis.rpush(mykey, "hello", reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertEquals(4, reply3.result().toLong());
////            redis.lrem(mykey, -2, "hello", reply4 -> {
////              should.assertTrue(reply4.succeeded());
////              should.assertEquals(2, reply4.result().toLong());
////              redis.lrange(mykey, 0, -1, reply5 -> {
////                should.assertTrue(reply5.succeeded());
////                should.assertArrayEquals(toArray("hello", "foo"), reply5.result().getList().toArray());
////                test.complete();
////              });
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testLset(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.rpush(mykey, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpush(mykey, "two", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.rpush(mykey, "three", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(3, reply2.result().toLong());
////          redis.lset(mykey, 0, "four", reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            redis.lset(mykey, -2, "five", reply4 -> {
////              should.assertTrue(reply4.succeeded());
////              redis.lrange(mykey, 0, -1, reply5 -> {
////                should.assertTrue(reply5.succeeded());
////                should.assertArrayEquals(toArray("four", "five", "three"), reply5.result().getList().toArray());
////                test.complete();
////              });
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testLtrim(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.rpush(mykey, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpush(mykey, "two", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.rpush(mykey, "three", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(3, reply2.result().toLong());
////          redis.ltrim(mykey, 1, -1, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            redis.lrange(mykey, 0, -1, reply5 -> {
////              should.assertTrue(reply5.succeeded());
////              should.assertArrayEquals(toArray("two", "three"), reply5.result().getList().toArray());
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testMget(TestContext should) {
////    final Async test = should.async();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////    redis.set(mykey1, "Hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      redis.set(mykey2, "World", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        redis.mgetMany(toList(mykey1, mykey2, "nonexisting"), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(toArray("Hello", "World", null), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testMigrate(TestContext should) {
////    final Async test = should.async();
////    RedisServer server = RedisServer.builder().port(6382).build();
////    server.start();
////    RedisOptions job = new RedisOptions()
////      .setHost("localhost")
////      .setPort(6382);
////    RedisClient rdx = RedisClient.create(vertx, job);
////
////    String key = makeKey();
////    redis.set(key, "migrate", reply -> {
////      should.assertTrue(reply.succeeded());
////      redis.migrate("localhost", 6382, key, 0, 20000, MigrateOptions.NONE, reply2 -> {
////        should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
////        rdx.get(key, reply3 -> {
////          should.assertTrue(reply3.succeeded());
////          should.assertTrue("migrate".equals(reply3.result()));
////          try {
////            server.stop();
////          } catch (Exception ignore) {
////          }
////          rdx.close(reply4 -> should.assertTrue(reply4.succeeded()));
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testMonitor(TestContext should) {
//    final Async test = should.async();
//
//    redis.monitor(reply -> {
//      should.assertTrue(reply.succeeded());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testMove(TestContext should) {
//    final Async test = should.async();
//
//    String key = makeKey();
//    redis.set(key, "moved_key", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.move(key, 1, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(new Long(1).equals(reply2.result()));
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testMset(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//    redis.mset(new JsonObject(toMap(mykey1, "Hello", mykey2, "World")), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.get(mykey1, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("Hello", reply1.result());
//        redis.get(mykey2, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("World", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testMsetnx(TestContext should) {
////    final Async test = should.async();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////    final String mykey3 = makeKey();
////
////    redis.msetnx(new JsonObject(toMap(mykey1, "Hello", mykey2, "there")), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.msetnx(new JsonObject(toMap(mykey2, "there", mykey3, "world")), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(0, reply1.result().toLong());
////        redis.mgetMany(toList(mykey1, mykey2, mykey3), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(toArray("Hello", "there", null), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testMulti(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//    RedisTransaction transaction = redis.transaction();
//    redis.set(key, "0", rep -> {
//      should.assertTrue(rep.succeeded());
//      transaction.multi(reply -> {
//        should.assertTrue(reply.succeeded());
//        transaction.set(makeKey(), "0", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          transaction.set(makeKey(), "0", reply3 -> should.assertTrue(reply3.succeeded()));
//          transaction.exec(reply4 -> {
//            should.assertTrue(reply4.succeeded());
//            test.complete();
//          });
//        });
//      });
//    });
//
//
//  }
//
//  @Test
//  public void testObject(TestContext should) {
//    final Async test = should.async();
//
//    String mykey = makeKey();
//    redis.set(mykey, "test", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.object(mykey, ObjectCmd.REFCOUNT, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        redis.object(mykey, ObjectCmd.ENCODING, reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          redis.object(mykey, ObjectCmd.IDLETIME, reply4 -> {
//            should.assertTrue(reply4.succeeded());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPersist(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.expire(mykey, 10, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.ttl(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(10L, reply2.result().toLong());
//          redis.persist(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(1L, reply3.result().toLong());
//            redis.ttl(mykey, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals(-1L, reply4.result().toLong());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPexpire(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.pexpire(mykey, 1000, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPexpireat(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.pexpireat(mykey, 1555555555005L, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.ttl(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertTrue(reply2.result() == -2);
//          redis.pttl(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertTrue(reply3.result() == -2);
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPing(TestContext should) {
//    final Async test = should.async();
//    redis.ping(reply0 -> {
//      should.assertEquals("PONG", reply0.result());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testPsetex(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.psetex(mykey, 2000, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.pttl(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertTrue(3000 > reply1.result() && reply1.result() > 0);
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPttl(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.expire(mykey, 3, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.pttl(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertTrue(3000 >= reply2.result() && reply2.result() > 0);
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testPublish(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//    redis.set(key, "0", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.publish(key, "1", reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result() == 0);
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testRandomkey(TestContext should) {
//    final Async test = should.async();
//
//    redis.set("foo", "bar", reply -> {
//      if (reply.succeeded()) {
//        redis.randomkey(reply2 -> {
//          if (reply2.succeeded()) {
//            should.assertNotNull(reply2.result());
//            test.complete();
//          }
//        });
//      }
//    });
//
//  }
//
//  @Test
//  public void testRename(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.rename(mykey, myotherkey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.get(myotherkey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testRenamenx(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.set(myotherkey, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.renamenx(mykey, myotherkey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          redis.get(myotherkey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("World", reply3.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testRestore(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    final String myotherkey = makeKey();
////    final Buffer value = Buffer.buffer(new byte[]{(byte) 0xff, (byte) 0xf0, 0x00, 'A', 'B', 'C'});
////    redis.setBinary(mykey, value, reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      redis.dump(mykey, reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        String dump = reply1.result();
////        redis.restore(myotherkey, 0, dump, reply2 -> {
////          should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
////          redis.getBinary(myotherkey, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertEquals(value, reply3.result());
////
////            test.complete();
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testRpop(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.rpush(mykey, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpush(mykey, "two", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.rpush(mykey, "three", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(3, reply2.result().toLong());
////          redis.rpop(mykey, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertEquals("three", reply3.result());
////            redis.lrange(mykey, 0, -1, reply5 -> {
////              should.assertTrue(reply5.succeeded());
////              should.assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testRpoplpush(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    final String myotherkey = makeKey();
////    redis.rpush(mykey, "one", reply0 -> {
////        should.assertTrue(reply0.succeeded());
////        should.assertEquals(1, reply0.result().toLong());
////        redis.rpush(mykey, "two", reply1 -> {
////          should.assertTrue(reply1.succeeded());
////          should.assertEquals(2, reply1.result().toLong());
////          redis.rpush(mykey, "three", reply2 -> {
////            should.assertTrue(reply2.succeeded());
////            should.assertEquals(3, reply2.result().toLong());
////            redis.rpoplpush(mykey, myotherkey, reply3 -> {
////              should.assertTrue(reply3.succeeded());
////              should.assertEquals("three", reply3.result());
////              redis.lrange(mykey, 0, -1, reply5 -> {
////                should.assertTrue(reply5.succeeded());
////                should.assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
////                redis.lrange(myotherkey, 0, -1, reply6 -> {
////                  should.assertArrayEquals(toArray("three"), reply6.result().getList().toArray());
////                  test.complete();
////                });
////              });
////            });
////          });
////        });
////      }
////
////    );
////
////
////  }
//
////  @Test
////  public void testRpush(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.rpush(mykey, "hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpush(mykey, "world", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.lrange(mykey, 0, -1, reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(toArray("hello", "world"), reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testRpushx(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    final String myotherkey = makeKey();
////    redis.rpush(mykey, "Hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.rpushx(mykey, "World", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(2, reply1.result().toLong());
////        redis.rpushx(myotherkey, "World", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(0, reply2.result().toLong());
////          redis.lrange(mykey, 0, -1, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertArrayEquals(toArray("Hello", "World"), reply3.result().getList().toArray());
////            redis.lrange(myotherkey, 0, -1, reply4 -> {
////              should.assertArrayEquals(new Object[0], reply4.result().getList().toArray());
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testSadd(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.sadd(mykey, "Hello", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.sadd(mykey, "World", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        redis.sadd(mykey, "World", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(0, reply2.result().toLong());
////          redis.smembers(mykey, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            Object[] expected = new Object[]{"Hello", "World"};
////            Object[] result = reply3.result().getList().toArray();
////            Arrays.sort(result);
////            should.assertArrayEquals(expected, result);
////            test.complete();
////          });
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testSave(TestContext should) {
//    final Async test = should.async();
//    redis.save(reply -> {
//      should.assertTrue(reply.succeeded());
//      //Note, there's really not much else to do
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testScard(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("Hello", "World"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2L, reply0.result().toLong());
//      redis.scard(mykey, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertEquals(2L, reply2.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testScriptexists(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.scriptLoad(inline, reply -> {
//      should.assertTrue(reply.succeeded());
//      String hash = reply.result();
//      redis.scriptExists(hash, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result().getInteger(0) > 0);
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testScriptflush(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.scriptLoad(inline, reply -> {
//      should.assertTrue(reply.succeeded());
//      String hash = reply.result();
//      redis.scriptExists(hash, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result().getInteger(0) > 0);
//        redis.scriptFlush(reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          redis.scriptExists(hash, reply4 -> {
//            should.assertTrue(reply4.succeeded());
//            should.assertTrue(reply4.result().getInteger(0) == 0);
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testScriptload(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.scriptLoad(inline, reply -> {
//      should.assertTrue(reply.succeeded());
//      should.assertNotNull(reply.result());
//      test.complete();
//    });
//
//  }
//
////  @Test
////  public void testSdiff(TestContext should) {
////    final Async test = should.async();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////
////    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(3, reply1.result().toLong());
////        redis.sdiff(mykey1, toList(mykey2), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          Object[] expected = new Object[]{"a", "b"};
////          Object[] result = reply2.result().getList().toArray();
////          Arrays.sort(result);
////          should.assertArrayEquals(expected, result);
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testSdiffstore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3L, reply1.result().toLong());
//        redis.sdiffstore(mykey, mykey1, toList(mykey2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          Long diff = reply2.result();
//          should.assertTrue(diff == 2);
//          redis.smembers(mykey, reply3 -> {
//            Set<String> expected = new HashSet(toList("a", "b"));
//            JsonArray members = reply3.result();
//            Set<String> result = new HashSet(members.getList());
//            should.assertEquals(expected, result);
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSelect(TestContext should) {
//    final Async test = should.async();
//    //Gee, think redis should have a get current DB command?
//    redis.select(1, reply -> {
//      if (reply.succeeded()) {
//        redis.set("first", "value", reply2 -> {
//          if (reply2.succeeded()) {
//            redis.select(0, reply3 -> {
//              if (reply3.succeeded()) {
//                redis.select(1, reply4 -> {
//                  if (reply4.succeeded()) {
//                    redis.get("first", reply5 -> {
//                      if (reply5.succeeded()) {
//                        should.assertTrue("value".equals(reply5.result()));
//                        test.complete();
//                      }
//                    });
//                  }
//                });
//              }
//            });
//          }
//        });
//      }
//    });
//
//  }
//
//  @Test
//  public void testSet(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.get(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("Hello", reply1.result());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetWithOptions(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.setWithOptions(mykey, "Hello!", new SetOptions().setNX(true).setEX(10), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.get(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("Hello!", reply1.result());
//
//        redis.setWithOptions(mykey, "Hello again!", new SetOptions().setNX(true).setEX(10), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          redis.get(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            // It's not 'Hello again!' but the old value since we used NX which means set unless exists
//            should.assertEquals("Hello!", reply1.result());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetWithOptions2(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    // 1st case SET k v NX -> OK
//    redis.setWithOptions(mykey, "Hello!", new SetOptions().setNX(true), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      // 2nd case SET k v NX -> nil (was already set)
//      redis.setWithOptions(mykey, "Hello again!", new SetOptions().setNX(true), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertNull(reply2.result());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetbit(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.setbit(mykey, 7, 1, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(0L, reply0.result().toLong());
//      redis.setbit(mykey, 7, 0, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("\u0000", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetex(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.setex(mykey, 10, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.ttl(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(10L, reply1.result().toLong());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetnx(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.setnx(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.setnx(mykey, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(0L, reply1.result().toLong());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSetrange(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello World", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.setrange(mykey, 6, "Redis", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(11L, reply1.result().toLong());
//        redis.get(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("Hello Redis", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testSinter(TestContext should) {
////    final Async test = should.async();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////
////    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(3, reply1.result().toLong());
////        redis.sinter(toList(mykey1, mykey2), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertArrayEquals(new Object[]{"c"}, reply2.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testSinterstore(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////
////    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(3, reply1.result().toLong());
////        redis.sinterstore(mykey, toList(mykey1, mykey2), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertTrue(reply2.result() == 1);
////          //should.assertArrayEquals(new Object[]{"c"}, reply6.result().getList().toArray());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testSismember(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.sadd(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.sismember(mykey, "one", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.sismember(mykey, "two", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testSmembers(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.saddMany(mykey, toList("Hello", "World"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(2, reply0.result().toLong());
////      redis.smembers(mykey, reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        Object[] expected = new Object[]{"Hello", "World"};
////        Object[] result = reply1.result().getList().toArray();
////        Arrays.sort(result);
////        should.assertArrayEquals(expected, result);
////        test.complete();
////      });
////    });
////
////  }
//
////  @Test
////  public void testSmove(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    final String myotherkey = makeKey();
////    redis.saddMany(mykey, toList("one", "two"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(2, reply0.result().toLong());
////      redis.sadd(myotherkey, "three", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        redis.smove(mykey, myotherkey, "two", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(1, reply2.result().toLong());
////          redis.smembers(mykey, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            Object[] expected = new Object[]{"one"};
////            Object[] result = reply3.result().getList().toArray();
////            Arrays.sort(result);
////            should.assertArrayEquals(expected, result);
////            redis.smembers(myotherkey, reply4 -> {
////              should.assertTrue(reply4.succeeded());
////              Object[] expected1 = new Object[]{"three", "two"};
////              Object[] result1 = reply4.result().getList().toArray();
////              Arrays.sort(result1);
////              should.assertArrayEquals(expected1, result1);
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testSort(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////
////    final String k1 = mykey + ":1";
////    final String k2 = mykey + ":2";
////    final String k3 = mykey + ":3";
////    final String kx = mykey + ":*";
////
////    redis.saddMany(mykey, toList("1", "2", "3"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.set(k1, "one", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        redis.set(k2, "two", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          redis.set(k3, "three", reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            redis.sort(mykey, new SortOptions().useDescending().addGet(kx), reply4 -> {
////              should.assertTrue(reply4.succeeded());
////              should.assertArrayEquals(toArray("three", "two", "one"), reply4.result().getList().toArray());
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testSpop(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.spop(mykey, reply1 -> {
////        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
////        String ret = reply1.result();
////        should.assertTrue(ret.equals("one") || ret.equals("two") || ret.equals("three"));
////        JsonArray expected = new JsonArray();
////        if (!ret.equals("one")) {
////          expected.add("one");
////        }
////        if (!ret.equals("two")) {
////          expected.add("two");
////        }
////        if (!ret.equals("three")) {
////          expected.add("three");
////        }
////        redis.smembers(mykey, reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          Object[] expectedA = expected.getList().toArray();
////          Arrays.sort(expectedA);
////          Object[] res = reply2.result().getList().toArray();
////          Arrays.sort(res);
////          should.assertArrayEquals(expectedA, res);
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testSpopMany(TestContext should) {
////    final Async test = should.async();
////    final String mykey = makeKey();
////    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.spopMany(mykey, 3, reply1 -> {
////        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
////        JsonArray ret = reply1.result();
////        test.complete();
////      });
////    });
////
////  }
//
//  @Test
//  public void testSrandmember(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.srandmember(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        String randmember = reply1.result();
//        should.assertTrue(randmember.equals("one") || randmember.equals("two") || randmember.equals("three"));
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testSrem(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.srem(mykey, "one", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.srem(mykey, "four", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testStrlen(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello world", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.strlen(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(11L, reply1.result().toLong());
//        redis.strlen("nonexisting", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSubscribe(TestContext should) {
//    final Async test = should.async();
//
//    String key = makeKey();
//    redis.subscribe(key, reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.unsubscribe(toList(key), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        test.complete();
//      });
//    });
//
//  }
//
////  @Test
////  public void testSunion(TestContext should) {
////    final Async test = should.async();
////    final String mykey1 = makeKey();
////    final String mykey2 = makeKey();
////
////    CountDownLatch latch = new CountDownLatch(6);
////
////    redis.sadd(mykey1, "a", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      latch.countDown();
////
////      redis.sadd(mykey1, "b", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        latch.countDown();
////      });
////      redis.sadd(mykey1, "c", reply2 -> {
////        should.assertTrue(reply2.succeeded());
////        should.assertEquals(1, reply2.result().toLong());
////        latch.countDown();
////      });
////      redis.sadd(mykey2, "c", reply3 -> {
////        should.assertTrue(reply3.succeeded());
////        should.assertEquals(1, reply3.result().toLong());
////        latch.countDown();
////      });
////      redis.sadd(mykey2, "d", reply4 -> {
////        should.assertTrue(reply4.succeeded());
////        should.assertEquals(1, reply4.result().toLong());
////        latch.countDown();
////      });
////      redis.sadd(mykey2, "e", reply5 -> {
////        should.assertTrue(reply5.succeeded());
////        should.assertEquals(1, reply5.result().toLong());
////        latch.countDown();
////        try {
////          latch.await(10, TimeUnit.SECONDS);
////        } catch (InterruptedException e) {
////          test.complete();
////          fail("Time out while waiting for all SADD calls to finish");
////        }
////        redis.sunion(toList(mykey1, mykey2), reply6 -> {
////          should.assertTrue(reply6.succeeded());
////          JsonArray arr = reply6.result();
////          Object[] array = arr.getList().toArray();
////          Arrays.sort(array);
////          should.assertTrue(array.length == 5);
////          should.assertArrayEquals(new Object[]{"a", "b", "c", "d", "e"}, array);
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testSunionstore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3L, reply1.result().toLong());
//        redis.sunionstore(mykey, toList(mykey1, mykey2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertTrue(reply2.result() == 5);
//          //          JsonArray arr = reply2.result();
//          //          Object[] array = arr.getList().toArray();
//          //          Arrays.sort(array);
//          //          should.assertTrue(array.length == 5);
//          //          should.assertArrayEquals(new Object[]{"a", "b", "c", "d", "e"}, array);
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testSync(TestContext should) {
//    final Async test = should.async();
//    redis.sync(v -> {
//      should.assertTrue(v.succeeded());
//      test.complete();
//
//    });
//
//  }
//
//  @Test
//  public void testTime(TestContext should) {
//    final Async test = should.async();
//    redis.time(reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertTrue(reply0.result().size() == 2);
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testTtl(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.expire(mykey, 10, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.ttl(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(10L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testType(TestContext should) {
//    final Async test = should.async();
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//    final String key3 = makeKey();
//
//    redis.set(key1, "value", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.lpush(key2, "value", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.sadd(key3, "value", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1L, reply2.result().toLong());
//          redis.type(key1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("string", reply3.result());
//            redis.type(key2, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals("list", reply4.result());
//              redis.type(key3, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                should.assertEquals("set", reply5.result());
//                test.complete();
//              });
//            });
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testUnsubscribe(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//    redis.subscribe(key, reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.unsubscribe(toList(key), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        test.complete();
//      });
//    });
//
//  }
//
////  @Test
////  public void testWatch(TestContext should) {
////    final Async test = should.async();
////    String key = makeKey();
////
////    RedisClient rdx = RedisClient.create(vertx, getConfig());
////    RedisTransaction transaction = redis.transaction();
////    redis.set(key, "0", reply -> {
////      should.assertTrue(reply.succeeded());
////      transaction.watch(key, reply2 -> {
////        should.assertTrue(reply2.succeeded());
////
////        transaction.multi(reply3 -> {
////          should.assertTrue(reply3.succeeded());
////          transaction.incr(key, reply4 -> {
////            should.assertTrue(reply4.succeeded());
////
////            rdx.incr(key, reply1 -> {
////              should.assertTrue(reply1.succeeded());
////              should.assertEquals(Long.valueOf(1L), reply1.result());
////
////              transaction.incr(key, reply5 -> {
////                should.assertTrue(reply5.succeeded());
////                transaction.incrby(key, 10, reply6 -> {
////                  should.assertTrue(reply6.succeeded());
////                  transaction.exec(reply7 -> {
////                    should.assertTrue(reply7.succeeded());
////                    should.assertNull(reply7.result());
////                    redis.get(key, reply8 -> {
////                      should.assertTrue(reply8.succeeded());
////                      should.assertEquals("1", reply8.result());
////                      test.complete();
////                    });
////                  });
////                });
////              });
////            });
////          });
////        });
////      });
////    });
////
////
////  }
//
////  @Test
////  public void testZadd(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    redis.zadd(key, 1, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.zadd(key, 1, "uno", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        Map<String, Double> values = new HashMap<>();
////        values.put("one", 1.0);
////        values.put("two", 2.0);
////        values.put("two", 3.0);
////        redis.zaddMany(key, values, reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(1, reply2.result().toLong());
////          redis.zadd(key, 3, "two", reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertEquals(0, reply3.result().toLong());
////            redis.zrangeWithOptions(key, 0, -1, RangeOptions.WITHSCORES, reply4 -> {
////              should.assertTrue(reply4.succeeded());
////              should.assertArrayEquals(toArray("one", "1", "uno", "1", "two", "3"), reply4.result().getList().toArray());
////              test.complete();
////            });
////          });
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testZcard(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.zadd(key, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.zcard(key, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(2L, reply2.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testZcount(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.zadd(key, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.zadd(key, 3, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1L, reply2.result().toLong());
//          redis.zcount(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(3L, reply3.result().toLong());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testZincrby(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.zadd(key, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.zincrby(key, 2, "one", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals("3", reply2.result());
//          test.complete();
//        });
//      });
//    });
//
//  }
//
//  @Test
//  public void testZinterstore(TestContext should) {
//    final Async test = should.async();
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//    final String key3 = makeKey();
//
//    redis.zadd(key1, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.zadd(key1, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1L, reply1.result().toLong());
//        redis.zadd(key2, 1, "one", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1L, reply2.result().toLong());
//          redis.zadd(key2, 2, "two", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(1L, reply3.result().toLong());
//            redis.zadd(key2, 3, "three", reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals(1L, reply4.result().toLong());
//              Map<String, Double> sets = new HashMap<>();
//              sets.put(key1, 2.0);
//              sets.put(key2, 3.0);
//              redis.zinterstoreWeighed(key3, sets, AggregateOptions.NONE, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                should.assertEquals(2L, reply5.result().toLong());
//                test.complete();
//              });
//            });
//          });
//        });
//      });
//    });
//
//  }
//
////  @Test
////  public void testZrange(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    redis.zadd(key, 1, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.zadd(key, 2, "two", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        redis.zadd(key, 3, "three", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(1, reply2.result().toLong());
////          redis.zrange(key, 0, -1, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
////            test.complete();
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testZrangebyscore(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    redis.zadd(key, 1, "one", reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(1, reply0.result().toLong());
////      redis.zadd(key, 2, "two", reply1 -> {
////        should.assertTrue(reply1.succeeded());
////        should.assertEquals(1, reply1.result().toLong());
////        redis.zadd(key, 3, "three", reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          should.assertEquals(1, reply2.result().toLong());
////          redis.zrangebyscore(key, "-inf", "+inf", RangeLimitOptions.NONE, reply3 -> {
////            should.assertTrue(reply3.succeeded());
////            should.assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
////            test.complete();
////          });
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testZrangebyscoreWithLimits(TestContext should) {
//    final Async test = should.async();
//    RangeLimitOptions options = new RangeLimitOptions();
//    options.setLimit(0, 1);
//    redis.zrangebyscore("yourkey", "-inf", "+inf", options, result -> {
//      // whatever
//      should.assertTrue(result.succeeded());
//      test.complete();
//    });
//
//  }
//
//  @Test
//  public void testZrank(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.zrank(key, "three", reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(2L, reply3.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testZrem(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.zrem(key, "two", reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(1L, reply3.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testZremrangebyrank(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.zremrangebyrank(key, 0, 1, reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(2L, reply3.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testZremrangebyscore(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.zremrangebyscore(key, "-inf", "(2", reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(1L, reply3.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
////  @Test
////  public void testZrevrange(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    Map<String, Double> values = new HashMap<>();
////    values.put("one", 1.0);
////    values.put("two", 2.0);
////    values.put("three", 3.0);
////    redis.zaddMany(key, values, reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.zrevrange(key, 0, -1, RangeOptions.NONE, reply3 -> {
////        should.assertTrue(reply3.succeeded());
////        should.assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
////        test.complete();
////      });
////    });
////
////  }
//
////  @Test
////  public void testZrevrangebyscore(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    Map<String, Double> values = new HashMap<>();
////    values.put("one", 1.0);
////    values.put("two", 2.0);
////    values.put("three", 3.0);
////    redis.zaddMany(key, values, reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(3, reply0.result().toLong());
////      redis.zrevrangebyscore(key, "+inf", "-inf", RangeLimitOptions.NONE, reply3 -> {
////        should.assertTrue(reply3.succeeded());
////        should.assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
////        test.complete();
////      });
////    });
////
////  }
//
//  @Test
//  public void testZrevrank(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.zrevrank(key, "one", reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(2L, reply3.result().toLong());
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testZscore(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.zscore(key, "one", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals("1", reply1.result());
//        test.complete();
//      });
//    });
//
//  }
//
////  @Test
////  public void testZunionstore(TestContext should) {
////    final Async test = should.async();
////    final String key1 = makeKey();
////    final String key2 = makeKey();
////    final String key3 = makeKey();
////
////    Map<String, Double> values = new HashMap<>();
////    values.put("one", 1.0);
////    values.put("two", 2.0);
////    redis.zaddMany(key1, values, reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals(2, reply0.result().toLong());
////
////      values.put("three", 3.0);
////      redis.zaddMany(key2, values, reply2 -> {
////        should.assertTrue(reply2.succeeded());
////        should.assertEquals(3, reply2.result().toLong());
////
////        Map<String, Double> weighedSets = new HashMap<>();
////        weighedSets.put(key1, 2.0);
////        weighedSets.put(key2, 3.0);
////        redis.zunionstoreWeighed(key3, weighedSets, AggregateOptions.NONE, reply5 -> {
////          should.assertTrue(String.valueOf(reply5.cause()), reply5.succeeded());
////          should.assertEquals(3, reply5.result().toLong());
////          test.complete();
////        });
////      });
////    });
////
////  }
//
////  // redis-embedded version of redis does not support HSCAN
////  @Test
////  public void testHscan(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    Map<String, Object> obj = new HashMap<>();
////    for (int i = 0; i < 100; i++) {
////      obj.put("field" + i, "val" + i);
////    }
////    redis.hmset(key, new JsonObject(obj), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      should.assertEquals("OK", reply0.result());
////      redis.hscan(key, "0", new ScanOptions().setMatch("field1*"), reply1 -> {
////        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
////        JsonArray result = reply1.result();
////        should.assertEquals(2, result.size());
////
////        JsonArray page = result.getJsonArray(1);
////        should.assertEquals(22, page.size());
////
////        test.complete();
////      });
////    });
////
////  }
//
////  @Test
////  public void testBitpos(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    final byte[] value = new byte[]{(byte) 0xff, (byte) 0xf0, (byte) 0x00};
////    redis.setBinary(key, Buffer.buffer(value), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      redis.bitpos(key, 0, reply1 -> {
////        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
////        should.assertEquals(12, reply1.result().toLong());
////
////        final byte[] value2 = new byte[]{0, 0, 0};
////        redis.setBinary(key, Buffer.buffer(value2), reply2 -> {
////          should.assertTrue(reply2.succeeded());
////          redis.bitpos(key, 1, reply3 -> {
////            should.assertTrue(String.valueOf(reply3.cause()), reply3.succeeded());
////            should.assertEquals(-1, reply3.result().toLong());
////
////            test.complete();
////          });
////        });
////      });
////    });
////
////  }
//
////  @Test
////  public void testBitposFrom(TestContext should) {
////    final Async test = should.async();
////    final String key = makeKey();
////    final byte[] value = new byte[]{(byte) 0x00, (byte) 0xff, (byte) 0xf0};
////    redis.setBinary(key, Buffer.buffer(value), reply0 -> {
////      should.assertTrue(reply0.succeeded());
////      redis.bitposFrom(key, 1, 0, reply1 -> {
////        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
////        should.assertEquals(8, reply1.result().toLong());
////        redis.bitposFrom(key, 1, 2, reply2 -> {
////          should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
////          should.assertEquals(16, reply2.result().toLong());
////
////          test.complete();
////        });
////      });
////    });
////
////  }
//
//  @Test
//  public void testBinarySetAndGet(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    final byte[] value = new byte[256];
//    for (int i = 0; i < value.length; i++) {
//      value[i] = (byte) i;
//    }
//    redis.setBinary(key, Buffer.buffer(value), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.getBinary(key, reply1 -> {
//        should.assertTrue(reply1.succeeded(), String.valueOf(reply1.cause()));
//        should.assertTrue(Arrays.equals(value, reply1.result().getBytes()));
//        test.complete();
//      });
//    });
//
//  }
//
//  @Test
//  public void testIssue5BlockingCall_shouldWork(TestContext should) {
//    final Async test = should.async();
//    final String list1 = makeKey();
//    final String list2 = makeKey();
//
//    redis.rpushMany(list1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//
//      should.assertEquals(3L, reply0.result().toLong());
//
//      redis.brpopMany(toList(list1, list2), 0, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//
//        should.assertEquals(list1, reply1.result().getString(0));
//        should.assertEquals("c", reply1.result().getString(1));
//        test.complete();
//      });
//    });
//
//
//  }
//
//  @Test
//  public void testIssue5BlockingCall_report(TestContext should) {
//    final Async test = should.async();
//    final String list = makeKey();
//
//    redis.brpop(list, 2, reply1 -> {
//      should.assertTrue(reply1.succeeded());
//
//      should.assertNull(reply1.result());
//      test.complete();
//    });
//
//
//  }
//
//  @Test
//  public void testSscan(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.saddMany(key, toList("1", "2", "3", "foo", "bar", "feelsgood"), reply1 -> {
//      should.assertTrue(reply1.succeeded());
//      should.assertEquals(6L, reply1.result().toLong());
//      redis.sscan(key, "0", new ScanOptions().setMatch("f*"), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertEquals("0", reply2.result().getString(0));
//
//        should.assertTrue(reply2.result().getJsonArray(1).contains("foo"));
//        should.assertTrue(reply2.result().getJsonArray(1).contains("feelsgood"));
//
//        test.complete();
//      });
//    });
//
//  }
}
