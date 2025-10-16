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
package io.vertx.tests.redis.client;

import io.vertx.core.Context;
import io.vertx.core.internal.pool.PoolConnector;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.PooledRedisConnection;
import io.vertx.redis.client.impl.RedisConnectionInternal;
import io.vertx.redis.client.impl.RedisStandaloneConnection;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientTest {

  @Container
  public static final RedisStandalone redisServer = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;
  private RedisAPI redis;

  @BeforeEach
  public void before(VertxTestContext test) {
    Context context = this.context.vertx().getOrCreateContext();
    client = Redis.createClient(this.context.vertx(), new RedisOptions().setConnectionString(redisServer.getRedisUri()));
    client.connect().onComplete(test.succeeding(conn -> {
      assertEquals(context, this.context.vertx().getOrCreateContext());
      redis = RedisAPI.api(conn);
      test.completeNow();
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  private static Field getAccessibleField(Class<?> clazz, String name) throws NoSuchFieldException {
    Field field = clazz.getDeclaredField(name);
    field.setAccessible(true);
    return field;
  }

  @Test
  public void testConnectionClose(VertxTestContext test) {
    Context context = this.context.vertx().getOrCreateContext();
    client.connect().onComplete(test.succeeding(conn -> {
      assertEquals(context, this.context.vertx().getOrCreateContext());

      // Redis server has a client timeout feature where it will close idle clients after a certain point. So by closing
      // the socket, we are simulating what happens.
      try {
        // conn is a PooledRedisConnection object
        RedisConnectionInternal internalConnection = ((PooledRedisConnection) conn).actual();
        // internalConnection is a RedisStandaloneConnection object
        Field listenerField = getAccessibleField(RedisStandaloneConnection.class, "listener");
        PoolConnector.Listener originalListener = (PoolConnector.Listener) listenerField.get(internalConnection);

        listenerField.set(internalConnection, new PoolConnector.Listener() {
          @Override
          public void onRemove() {
            originalListener.onRemove();
            // When the socket is closed, evict should be call on the listener
            test.completeNow();
          }

          @Override
          public void onConcurrencyChange(long concurrency) {
            originalListener.onConcurrencyChange(concurrency);
          }
        });

        internalConnection.forceClose(); // this should cause the evict to occur
      } catch (NoSuchFieldException | IllegalAccessException e) {
        test.failNow(e);
      }
    }));
  }

  @Test
  public void testContextReturn(VertxTestContext test) {
    Context context = this.context.vertx().getOrCreateContext();
    redis.append(randomKey(), "Hello").onComplete(test.succeeding(reply1 -> {
      assertEquals(context, this.context.vertx().getOrCreateContext());
      test.completeNow();
    }));
  }

  @Test
  public void testAppend(VertxTestContext test) {
    final String key = randomKey();

    redis
      .del(List.of(key))
      .compose(reply0 -> redis.append(key, "Hello"))
      .compose(reply1 -> redis.append(key, " World"))
      .compose(reply2 -> redis.get(key))
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("Hello World", reply3.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testBgrewriteaof(VertxTestContext test) {
    redis.bgrewriteaof().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testBgsave(VertxTestContext test) {
    redis.bgsave(List.of("SCHEDULE")).onComplete(test.succeeding(reply -> {
      assertTrue(reply.toString().startsWith("Background saving"));
      test.completeNow();
    }));

  }

  @Test
  public void testBitcount(VertxTestContext test) {
    final String key = randomKey();

    redis.set(List.of(key, "foobar"))
      .compose(reply0 -> redis.bitcount(List.of(key)))
      .compose(reply1 -> {
        assertEquals(26L, reply1.toLong());
        return redis.bitcount(List.of(key, "0", "0"));
      })
      .compose(reply2 -> {
        assertEquals(4L, reply2.toLong());
        return redis.bitcount(List.of(key, "1", "1"));
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(6L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testBitop(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    final String destkey = randomKey();

    redis.set(List.of(key1, "foobar"))
      .compose(reply0 -> redis.set(List.of(key2, "abcdef")))
      .compose(reply1 -> redis.bitop(List.of("AND", destkey, key1, key2)))
      .compose(reply2 -> redis.get(destkey))
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testBlpop(VertxTestContext test) {
    final String list1 = randomKey();
    final String list2 = randomKey();

    redis.del(List.of(list1, list2))
      .compose(reply0 -> redis.rpush(List.of(list1, "a", "b", "c")))
      .compose(reply1 -> {
        assertEquals(3, reply1.toInteger());
        return redis.blpop(List.of(list1, list2, "0"));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(list1, reply2.get(0).toString());
        assertEquals("a", reply2.get(1).toString());
        test.completeNow();
      }));
  }

  @Test
  public void testBrpop(VertxTestContext test) {
    final String list1 = randomKey();
    final String list2 = randomKey();

    redis.del(List.of(list1, list2))
      .compose(reply0 -> redis.rpush(List.of(list1, "a", "b", "c")))
      .compose(reply1 -> {
        assertEquals(3, reply1.toInteger());
        return redis.brpop(List.of(list1, list2, "0"));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(list1, reply2.get(0).toString());
        assertEquals("c", reply2.get(1).toString());
        test.completeNow();
      }));
  }

  @Test
  public void testBrpoplpush(VertxTestContext test) {
    final String list1 = randomKey();
    final String list2 = randomKey();

    redis.brpoplpush(list1, list2, "10000")
      .compose(result -> redis.lpop(List.of(list2)))
      .onComplete(test.succeeding(result2 -> {
        assertEquals("hello", result2.toString());
        test.completeNow();
      }));

    Redis redis2 = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redisServer.getRedisUri()));
    redis2
      .send(Request.cmd(Command.LPUSH).arg(list1).arg("hello"))
      .onComplete(test.succeeding(reply -> {
        redis2.close();
      }));
  }

//  @Test
//  public void testClientKill(TestContext should) {
//    final Async test = should.async();
//
//    redis.clientList(reply -> {
//      should.assertTrue(reply.succeeded());
//      String clients = reply.result();
//      String[] keyvals = clients.split("\\s");
//      String addr = null;
//      for (String keyval : keyvals) {
//        String[] pair = keyval.split("=");
//        if ("addr".equals(pair[0])) {
//          addr = pair[1];
//          break;
//        }
//      }
//      should.assertNotNull("No addr in CLIENT LIST", addr);
//      redis.clientKill(new KillFilter().setAddr(addr).setType(KillFilter.Type.NORMAL), reply2 -> {
//        should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
//        test.complete();
//      });
//    });
//
//  }

  @Test
  public void testClientList(VertxTestContext test) {
    redis
      .client(List.of("LIST"))
      .onComplete(test.succeeding(result -> {
        assertNotNull(result);
        test.completeNow();
      }));
  }

  @Test
  public void testClientSetAndGetName(VertxTestContext test) {
    redis.client(List.of("GETNAME"))
      .compose(result -> {
        return redis.client(List.of("SETNAME", "test-connection"));
      })
      .compose(result -> {
        return redis.client(List.of("GETNAME"));
      })
      .onComplete(test.succeeding(result -> {
        assertEquals("test-connection", result.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testConfigGet(VertxTestContext test) {
    redis.config(List.of("GET", "*"))
      .onComplete(test.succeeding(reply -> {
        assertTrue(reply != null && reply.size() > 0);
        test.completeNow();
      }));
  }

  @Test
  public void testConfigSetAndGet(VertxTestContext test) {
    redis.config(List.of("SET", "set-max-intset-entries", "1024"))
      .compose(reply -> {
        return redis.config(List.of("GET", "set-max-intset-entries"));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertNotNull(reply2.get("set-max-intset-entries"));
        assertEquals("1024", reply2.get("set-max-intset-entries").toString());
        test.completeNow();
      }));
  }

//  @Test
//  public void testConfigResetstat(TestContext should) {
//    final Async test = should.async();
//
//    redis.info(toList(), reply -> {
//      should.assertTrue(reply.succeeded());
//      JsonObject result = reply.result().getJsonObject("stats");
//      Integer conn = Integer.valueOf(result.getString("total_connections_received"));
//      should.assertTrue(conn > 0);
//      redis.config(toList("RESETSTAT"), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        redis.info(toList(), reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          //Note, this may appear strange, but the embedded server handles stats differently
//          //Many are not reset correctly. Here we just test the flow of the COMMANDS
//          test.complete();
//        });
//
//      });
//    });
//  }

  @Test
  public void testDbsize(VertxTestContext test) {
    redis.dbsize()
      .compose(reply -> {
        long size = reply.toLong();
        return redis.set(List.of("new", "value"))
          .compose(reply2 -> redis.dbsize())
          .andThen(test.succeeding(reply3 -> assertEquals(size + 1, reply3.toLong())));
      })
      .onComplete(test.succeedingThenComplete());
  }

//  @Test
//  public void testDebugSegfault(TestContext should) {
//    final Async test = should.async();
//    RedisServer server = RedisServer.builder().port(6381).build();
//    server.start();
//    RedisOptions job = new RedisOptions()
//      .setHost("localhost")
//      .setPort(6381);
//    RedisClient rdx = RedisClient.create(vertx, job);
//
//    rdx.debugSegfault(reply -> {
//      // this should fail, since we crashed the server on purpose
//      should.assertTrue(reply.failed());
//      rdx.info(reply2 -> {
//        should.assertFalse(reply2.succeeded());
//        server.stop();
//        test.complete();
//      });
//    });
//
//  }

  @Test
  public void testDecr(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "10"))
      .compose(reply0 -> redis.decr(mykey))
      .onComplete(test.succeeding(reply1 -> {
        assertEquals(9L, reply1.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testDecrby(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "10"))
      .compose(reply0 -> redis.decrby(mykey, "5"))
      .onComplete(test.succeeding(reply1 -> {
        assertEquals(5L, reply1.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testDel(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    final String key3 = randomKey();

    redis.set(List.of(key1, "Hello"))
      .compose(reply0 -> redis.set(List.of(key2, "World")))
      .compose(reply1 -> redis.del(List.of(key1, key2, key3)))
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testDiscard(VertxTestContext test) {
    String key = randomKey();

    redis.set(List.of(key, "0"))
      .compose(reply -> redis.multi())
      .compose(reply -> redis.incr(key))
      .compose(reply -> redis.discard())
      .compose(reply -> redis.get(key))
      .onComplete(test.succeeding(reply -> {
        assertEquals(0, reply.toInteger());
        test.completeNow();
      }));
  }

  @Test
  public void testEcho(VertxTestContext test) {
    redis.echo("Hello World!")
      .onComplete(test.succeeding(reply0 -> {
        assertEquals("Hello World!", reply0.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testEval(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    redis.eval(List.of("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", "2", key1, key2, "first", "second"))
      .onComplete(test.succeeding(reply -> {
        assertNotNull(reply);
        test.completeNow();
      }));
  }

  @Test
  public void testEvalshaNumKeysAndValuesDifferent(VertxTestContext test) {
    String inline = "return 1";
    redis.script(List.of("LOAD", inline))
      .compose(reply -> {
        assertNotNull(reply);
        return redis.evalsha(List.of(reply.toString(), "2", "key1", "key2", "value1"));
      })
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testEvalsha(VertxTestContext test) {
    String inline = "return 1";
    redis.script(List.of("LOAD", inline))
      .compose(reply -> {
        assertNotNull(reply);
        return redis.evalsha(List.of(reply.toString(), "0"));
      })
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  //Note same test as testMulti, kept for consistency
  public void testExec(VertxTestContext test) {
    redis.multi()
      .compose(reply -> redis.set(List.of("multi-key", "first")))
      .compose(reply -> redis.set(List.of("multi-key2", "second")))
      .compose(reply -> redis.get("multi-key"))
      .compose(reply -> {
        assertTrue("QUEUED".equalsIgnoreCase(reply.toString()));
        return redis.exec();
      })
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testExists(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();

    redis.set(List.of(key1, "Hello"))
      .compose(reply0 -> redis.exists(List.of(key1)))
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.exists(List.of(key2));
      })
      .compose(reply2 -> {
        assertEquals(0L, reply2.toLong());
        return redis.set(List.of(key2, "Hello"));
      })
      .compose(reply3 -> {
        return redis.exists(List.of(key1, key2));
      })
      .onComplete(test.succeeding(reply4 -> {
        assertEquals(2, reply4.toInteger());
        test.completeNow();
      }));
  }

  @Test
  public void testExpire(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> redis.expire(Arrays.asList(mykey, "10")))
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.ttl(mykey);
      })
      .compose(reply2 -> {
        assertEquals(10L, reply2.toLong());
        return redis.set(List.of(mykey, "Hello World"));
      })
      .compose(reply3 -> redis.ttl(mykey))
      .onComplete(test.succeeding(reply4 -> {
        assertEquals(-1L, reply4.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testExpireat(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> redis.exists(List.of(mykey)))
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.expireat(Arrays.asList(mykey, "1293840000"));
      })
      .compose(reply2 -> {
        assertEquals(1L, reply2.toLong());
        return redis.exists(List.of(mykey));
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(0L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testFlushall(VertxTestContext test) {
    String key = randomKey();
    //As per the doc, this never fails
    redis.set(List.of(key, "blah"))
      .compose(reply -> redis.flushall(List.of()))
      .compose(reply2 -> redis.get(key))
      .onComplete(test.succeeding(reply3 -> {
        assertNull(reply3);
        test.completeNow();
      }));
  }

  @Test
  public void testFlushdb(VertxTestContext test) {
    String key = randomKey();
    //As per the doc, this never fails
    redis.set(List.of(key, "blah"))
      .compose(reply -> redis.flushall(List.of()))
      .compose(reply -> redis.get(key))
      .onComplete(test.succeeding(reply3 -> {
        assertNull(reply3);
        test.completeNow();
      }));
  }

  @Test
  public void testGet(VertxTestContext test) {
    final String nonexisting = randomKey();
    final String mykey = randomKey();

    redis.get(nonexisting)
      .compose(reply0 -> {
        assertNull(reply0);
        return redis.set(List.of(mykey, "Hello"));
      })
      .compose(reply1 -> {
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testGetbit(VertxTestContext test) {
    final String mykey = randomKey();

    redis.setbit(mykey, "7", "1")
      .compose(reply0 -> {
        assertEquals(0L, reply0.toLong());
        return redis.getbit(mykey, "0");
      })
      .compose(reply1 -> {
        assertEquals(0L, reply1.toLong());
        return redis.getbit(mykey, "7");
      })
      .compose(reply -> {
        return redis.getbit(mykey, "100");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(0L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testGetrange(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "This is a string"))
      .compose(reply0 -> {
        return redis.getrange(mykey, "0", "3");
      })
      .compose(reply1 -> {
        assertEquals("This", reply1.toString());
        return redis.getrange(mykey, "-3", "-1");
      })
      .compose(reply2 -> {
        assertEquals("ing", reply2.toString());
        return redis.getrange(mykey, "0", "-1");
      })
      .compose(reply3 -> {
        assertEquals("This is a string", reply3.toString());
        return redis.getrange(mykey, "10", "100");
      })
      .onComplete(test.succeeding(reply4 -> {
        assertEquals("string", reply4.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testGetset(VertxTestContext test) {
    final String mycounter = randomKey();

    redis.incr(mycounter)
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.getset(mycounter, "0");
      })
      .compose(reply1 -> {
        assertEquals("1", reply1.toString());
        return redis.get(mycounter);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("0", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHdel(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "foo"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hdel(List.of(myhash, "field1"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hdel(List.of(myhash, "field2"));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(0L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHexists(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "foo"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hexists(myhash, "field1");
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hexists(myhash, "field2");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(0L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHget(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "foo"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hget(myhash, "field1");
      })
      .compose(reply1 -> {
        assertEquals("foo", reply1.toString());
        return redis.hget(myhash, "field2");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertNull(reply2);
        test.completeNow();
      }));
  }

  @Test
  public void testHgetall(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hset(List.of(myhash, "field2", "World"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hgetall(myhash);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.get("field1").toString());
        assertEquals("World", reply2.get("field2").toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHincrby(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field", "5"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hincrby(myhash, "field", "1");
      })
      .compose(reply1 -> {
        assertEquals(6L, reply1.toLong());
        return redis.hincrby(myhash, "field", "-1");
      })
      .compose(reply2 -> {
        assertEquals(5L, reply2.toLong());
        return redis.hincrby(myhash, "field", "-10");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(-5L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHIncrbyfloat(VertxTestContext test) {
    final String mykey = randomKey();

    redis.hset(List.of(mykey, "field", "10.50"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hincrbyfloat(mykey, "field", "0.1");
      })
      .compose(reply1 -> {
        assertEquals("10.6", reply1.toString());
        return redis.hset(List.of(mykey, "field", "5.0e3"));
      })
      .compose(reply2 -> {
        assertEquals(0L, reply2.toLong());
        return redis.hincrbyfloat(mykey, "field", "2.0e2");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("5200", reply3.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHkeys(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1, reply0.toInteger());
        return redis.hset(List.of(myhash, "field2", "World"));
      })
      .compose(reply1 -> {
        assertEquals(1, reply1.toInteger());
        return redis.hkeys(myhash);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2, reply2.size());
        assertEquals("field1", reply2.get(0).toString());
        assertEquals("field2", reply2.get(1).toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHlen(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hset(List.of(myhash, "field2", "World"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hlen(myhash);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHmget(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hset(List.of(myhash, "field2", "World"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hmget(List.of(myhash, "field1", "field2", "nofield"));
      }).onComplete(test.succeeding(reply2 -> {
        assertEquals(3, reply2.size());
        assertEquals("Hello", reply2.get(0).toString());
        assertEquals("World", reply2.get(1).toString());
        assertEquals(null, reply2.get(2));
        test.completeNow();
      }));
  }

  @Test
  public void testHmset(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hmset(List.of(myhash, "field1", "Hello", "field2", "World"))
      .compose(reply0 -> {
        return redis.hget(myhash, "field1");
      })
      .compose(reply1 -> {
        assertEquals("Hello", reply1.toString());
        return redis.hget(myhash, "field2");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("World", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHset(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hget(myhash, "field1");
      })
      .onComplete(test.succeeding(reply1 -> {
        assertEquals("Hello", reply1.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHsetnx(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hsetnx(myhash, "field", "Hello")
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hsetnx(myhash, "field", "World");
      })
      .compose(reply1 -> {
        assertEquals(0L, reply1.toLong());
        return redis.hget(myhash, "field");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHvals(VertxTestContext test) {
    final String myhash = randomKey();

    redis.hset(List.of(myhash, "field1", "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.hset(List.of(myhash, "field2", "World"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.hvals(myhash);
      }).onComplete(test.succeeding(reply2 -> {
        assertEquals(2, reply2.size());
        assertEquals("Hello", reply2.get(0).toString());
        assertEquals("World", reply2.get(1).toString());
        test.completeNow();
      }));
  }

  @Test
  public void testIncr(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "10"))
      .compose(reply0 -> redis.incr(mykey))
      .compose(reply1 -> {
        assertEquals(11L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("11", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testIncrby(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "10"))
      .compose(reply0 -> redis.incrby(mykey, "5"))
      .onComplete(test.succeeding(reply1 -> {
        assertEquals(15L, reply1.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testIncrbyfloat(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "10.50"))
      .compose(reply0 -> redis.incrbyfloat(mykey, "0.1"))
      .compose(reply1 -> {
        assertEquals("10.6", reply1.toString());
        return redis.set(List.of(mykey, "5.0e3"));
      })
      .compose(reply2 -> redis.incrbyfloat(mykey, "2.0e2"))
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("5200", reply3.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testInfo(VertxTestContext test) {
    redis.info(List.of())
      .onComplete(test.succeeding(reply0 -> {
        assertNotNull(reply0);
        test.completeNow();
      }));
  }

  @Test
  public void testKeys(VertxTestContext test) {
    redis.mset(List.of("one", "1", "two", "2", "three", "3", "four", "4"))
      .compose(reply0 -> {
        return redis.keys("*o*");
      })
      .compose(reply1 -> {
        // there may be leftovers from previous tests
        assertTrue(3 <= reply1.size());
        return redis.keys("t??");
      }).compose(reply2 -> {
        assertEquals(1, reply2.size());
        return redis.keys("*");
      }).onComplete(test.succeeding(reply3 -> {
        assertTrue(4 <= reply3.size());
        test.completeNow();
      }));
  }

  @Test
  public void testLastsave(VertxTestContext test) {
    redis.lastsave().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testLindex(VertxTestContext test) {
    final String mykey = randomKey();

    redis.lpush(List.of(mykey, "World"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.lpush(List.of(mykey, "Hello"));
      })
      .compose(reply1 -> {
        assertEquals(2L, reply1.toLong());
        return redis.lindex(mykey, "0");
      })
      .compose(reply2 -> {
        assertEquals("Hello", reply2.toString());
        return redis.lindex(mykey, "-1");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("World", reply3.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testLinsert(VertxTestContext test) {
    final String mykey = randomKey();

    redis.rpush(List.of(mykey, "Hello"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.rpush(List.of(mykey, "World"));
      })
      .compose(reply1 -> {
        assertEquals(2L, reply1.toLong());
        return redis.linsert(mykey, "BEFORE", "World", "There");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(3L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testLlen(VertxTestContext test) {
    final String mykey = randomKey();

    redis.lpush(List.of(mykey, "World"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.lpush(List.of(mykey, "Hello"));
      })
      .compose(reply1 -> {
        assertEquals(2L, reply1.toLong());
        return redis.llen(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testLpop(VertxTestContext test) {
    final String mykey = randomKey();

    redis.rpush(List.of(mykey, "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.rpush(List.of(mykey, "two"));
      })
      .compose(reply1 -> {
        assertEquals(2L, reply1.toLong());
        return redis.rpush(List.of(mykey, "three"));
      })
      .compose(reply2 -> {
        assertEquals(3L, reply2.toLong());
        return redis.lpop(List.of(mykey));
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("one", reply3.toString());
        test.completeNow();
      }));
  }

//  @Test
//  public void testLpushOne(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.lpush(toList(mykey, "world"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.lpush(toList(mykey, "hello"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.lrange(mykey, 0, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("hello", "world"), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//  }

//  @Test
//  public void testLpush(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.lpush(toList(mykey, "!", "world"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2L, reply0.result().toLong());
//      redis.lpush(toList(mykey, ",", "hello"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(4L, reply1.result().toLong());
//        redis.lrange(mykey, 0, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(toList("hello", ",", "world", "!"), reply2.result().getList());
//          test.complete();
//        });
//      });
//    });
//  }

//  @Test
//  public void testLpushx(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//
//    redis.lpush(toList(mykey, "World"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.lpushx(toList(mykey, "Hello"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.lpushx(toList(myotherkey, "Hello"), reply2 -> {
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

//  @Test
//  public void testLrange(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(toList(mykey, "one"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1L, reply0.result().toLong());
//      redis.rpush(toList(mykey, "two"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2L, reply1.result().toLong());
//        redis.rpush(toList(mykey, "three"), reply2 -> {
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
//  }

//  @Test
//  public void testLrem(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpush(mykey, "hello", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.rpush(mykey, "foo", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3, reply2.result().toLong());
//          redis.rpush(mykey, "hello", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(4, reply3.result().toLong());
//            redis.lrem(mykey, -2, "hello", reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertEquals(2, reply4.result().toLong());
//              redis.lrange(mykey, 0, -1, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                should.assertArrayEquals(toArray("hello", "foo"), reply5.result().getList().toArray());
//                test.complete();
//              });
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testLset(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpush(mykey, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.rpush(mykey, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3, reply2.result().toLong());
//          redis.lset(mykey, 0, "four", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            redis.lset(mykey, -2, "five", reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              redis.lrange(mykey, 0, -1, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                should.assertArrayEquals(toArray("four", "five", "three"), reply5.result().getList().toArray());
//                test.complete();
//              });
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testLtrim(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpush(mykey, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.rpush(mykey, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3, reply2.result().toLong());
//          redis.ltrim(mykey, 1, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            redis.lrange(mykey, 0, -1, reply5 -> {
//              should.assertTrue(reply5.succeeded());
//              should.assertArrayEquals(toArray("two", "three"), reply5.result().getList().toArray());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testMget(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//    redis.set(mykey1, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.set(mykey2, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.mgetMany(toList(mykey1, mykey2, "nonexisting"), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertArrayEquals(toArray("Hello", "World", null), reply2.result().getList().toArray());
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testMigrate(TestContext should) {
//    final Async test = should.async();
//    RedisServer server = RedisServer.builder().port(6382).build();
//    server.start();
//    RedisOptions job = new RedisOptions()
//      .setHost("localhost")
//      .setPort(6382);
//    RedisClient rdx = RedisClient.create(vertx, job);
//
//    String key = makeKey();
//    redis.set(key, "migrate", reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.migrate("localhost", 6382, key, 0, 20000, MigrateOptions.NONE, reply2 -> {
//        should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
//        rdx.get(key, reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          should.assertTrue("migrate".equals(reply3.result()));
//          try {
//            server.stop();
//          } catch (Exception ignore) {
//          }
//          rdx.close(reply4 -> should.assertTrue(reply4.succeeded()));
//          test.complete();
//        });
//      });
//    });
//
//  }

  @Test
  public void testMonitor(VertxTestContext test) {
    redis.monitor().onComplete(test.succeedingThenComplete());

  }

  @Test
  public void testMove(VertxTestContext test) {
    String key = randomKey();

    redis.set(List.of(key, "moved_key"))
      .compose(reply -> redis.move(key, "1"))
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(1L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testMset(VertxTestContext test) {
    final String mykey1 = randomKey();
    final String mykey2 = randomKey();

    redis.mset(List.of(mykey1, "Hello", mykey2, "World"))
      .compose(reply0 -> {
        return redis.get(mykey1);
      })
      .compose(reply1 -> {
        assertEquals("Hello", reply1.toString());
        return redis.get(mykey2);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("World", reply2.toString());
        test.completeNow();
      }));
  }

//  @Test
//  public void testMsetnx(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//    final String mykey3 = makeKey();
//
//    redis.msetnx(new JsonObject(toMap(mykey1, "Hello", mykey2, "there")), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.msetnx(new JsonObject(toMap(mykey2, "there", mykey3, "world")), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(0, reply1.result().toLong());
//        redis.mgetMany(toList(mykey1, mykey2, mykey3), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertArrayEquals(toArray("Hello", "there", null), reply2.result().getList().toArray());
//          test.complete();
//        });
//      });
//    });
//
//  }

  @Test
  public void testMulti(VertxTestContext test) {
    String key = randomKey();

    redis.set(List.of(key, "0"))
      .compose(reply0 -> redis.multi())
      .compose(reply1 -> redis.set(List.of(randomKey(), "0")))
      .compose(reply2 -> redis.set(List.of(randomKey(), "0")))
      .compose(reply3 -> redis.exec())
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testObject(VertxTestContext test) {
    String mykey = randomKey();
    redis.set(List.of(mykey, "test"))
      .compose(reply -> redis.object(List.of("REFCOUNT", mykey)))
      .compose(reply -> redis.object(List.of("ENCODING", mykey)))
      .compose(reply -> redis.object(List.of("IDLETIME", mykey)))
      .onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testPersist(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> {
        return redis.expire(Arrays.asList(mykey, "10"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.ttl(mykey);
      })
      .compose(reply2 -> {
        assertEquals(10L, reply2.toLong());
        return redis.persist(mykey);
      })
      .compose(reply3 -> {
        assertEquals(1L, reply3.toLong());
        return redis.ttl(mykey);
      })
      .onComplete(test.succeeding(reply4 -> {
        assertEquals(-1L, reply4.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testPexpire(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> {
        return redis.pexpire(Arrays.asList(mykey, "1000"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeedingThenComplete());
  }

//  @Test
//  public void testPexpireat(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(toList(mykey, "Hello"), reply0 -> {
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
//  }

  @Test
  public void testPing(VertxTestContext test) {
    redis.ping(List.of())
      .compose(reply0 -> {
        assertEquals("PONG", reply0.toString());
        return redis.ping(List.of("HELLO"));
      }).onComplete(test.succeeding(reply1 -> {
        assertEquals("HELLO", reply1.toString());
        test.completeNow();
      }));
  }

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

//  @Test
//  public void testPttl(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.set(toList(mykey, "Hello"), reply0 -> {
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
//  }

//  @Test
//  public void testPublish(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//    redis.set(toList(key, "0"), reply -> {
//      should.assertTrue(reply.succeeded());
//      redis.publish(key, "1", reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result() == 0);
//        test.complete();
//      });
//    });
//
//  }

  @Test
  public void testRandomkey(VertxTestContext test) {
    redis.set(List.of("foo", "bar"))
      .compose(reply -> redis.randomkey())
      .onComplete(test.succeeding(reply2 -> {
        assertNotNull(reply2);
        test.completeNow();
      }));
  }

  @Test
  public void testRename(VertxTestContext test) {
    final String mykey = randomKey();
    final String myotherkey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> redis.rename(mykey, myotherkey))
      .compose(reply1 -> redis.get(myotherkey))
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testRenamenx(VertxTestContext test) {
    final String mykey = randomKey();
    final String myotherkey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> redis.set(List.of(myotherkey, "World")))
      .compose(reply1 -> redis.renamenx(mykey, myotherkey))
      .compose(reply2 -> {
        assertEquals(0L, reply2.toLong());
        return redis.get(myotherkey);
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals("World", reply3.toString());
        test.completeNow();
      }));
  }

//  @Test
//  public void testRestore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//    final Buffer value = Buffer.buffer(new byte[]{(byte) 0xff, (byte) 0xf0, 0x00, 'A', 'B', 'C'});
//    redis.setBinary(mykey, value, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.dump(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        String dump = reply1.result();
//        redis.restore(myotherkey, 0, dump, reply2 -> {
//          should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
//          redis.getBinary(myotherkey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(value, reply3.result());
//
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testRpop(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpush(mykey, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.rpush(mykey, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(3, reply2.result().toLong());
//          redis.rpop(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals("three", reply3.result());
//            redis.lrange(mykey, 0, -1, reply5 -> {
//              should.assertTrue(reply5.succeeded());
//              should.assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testRpoplpush(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//    redis.rpush(mykey, "one", reply0 -> {
//        should.assertTrue(reply0.succeeded());
//        should.assertEquals(1, reply0.result().toLong());
//        redis.rpush(mykey, "two", reply1 -> {
//          should.assertTrue(reply1.succeeded());
//          should.assertEquals(2, reply1.result().toLong());
//          redis.rpush(mykey, "three", reply2 -> {
//            should.assertTrue(reply2.succeeded());
//            should.assertEquals(3, reply2.result().toLong());
//            redis.rpoplpush(mykey, myotherkey, reply3 -> {
//              should.assertTrue(reply3.succeeded());
//              should.assertEquals("three", reply3.result());
//              redis.lrange(mykey, 0, -1, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                should.assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
//                redis.lrange(myotherkey, 0, -1, reply6 -> {
//                  should.assertArrayEquals(toArray("three"), reply6.result().getList().toArray());
//                  test.complete();
//                });
//              });
//            });
//          });
//        });
//      }
//
//    );
//
//
//  }

//  @Test
//  public void testRpush(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.rpush(mykey, "hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpush(mykey, "world", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.lrange(mykey, 0, -1, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertArrayEquals(toArray("hello", "world"), reply2.result().getList().toArray());
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testRpushx(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//    redis.rpush(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.rpushx(mykey, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(2, reply1.result().toLong());
//        redis.rpushx(myotherkey, "World", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0, reply2.result().toLong());
//          redis.lrange(mykey, 0, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertArrayEquals(toArray("Hello", "World"), reply3.result().getList().toArray());
//            redis.lrange(myotherkey, 0, -1, reply4 -> {
//              should.assertArrayEquals(new Object[0], reply4.result().getList().toArray());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSadd(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.sadd(mykey, "Hello", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.sadd(mykey, "World", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        redis.sadd(mykey, "World", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(0, reply2.result().toLong());
//          redis.smembers(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            Object[] expected = new Object[]{"Hello", "World"};
//            Object[] result = reply3.result().getList().toArray();
//            Arrays.sort(result);
//            should.assertArrayEquals(expected, result);
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

  @Test
  public void testSave(VertxTestContext test) {
    redis.save().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testScard(VertxTestContext test) {
    final String mykey = randomKey();
    redis.sadd(List.of(mykey, "Hello", "World"))
      .compose(reply0 -> {
        assertEquals(2L, reply0.toLong());
        return redis.scard(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2L, reply2.toLong());
        test.completeNow();
      }));
  }

//  @Test
//  public void testScriptexists(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.script(toList("LOAD", inline), reply -> {
//      should.assertTrue(reply.succeeded());
//      String hash = reply.result();
//      redis.script(toList("EXISTS", hash), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result().getInteger(0) > 0);
//        test.complete();
//      });
//    });
//
//  }

//  @Test
//  public void testScriptflush(TestContext should) {
//    final Async test = should.async();
//    String inline = "return 1";
//    redis.script(toList("LOAD", inline), reply -> {
//      should.assertTrue(reply.succeeded());
//      String hash = reply.result();
//      redis.script(toList("EXISTS", hash), reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertTrue(reply2.result().getInteger(0) > 0);
//        redis.script(toList("FLUSH"), reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          redis.script(toList("EXISTS", hash), reply4 -> {
//            should.assertTrue(reply4.succeeded());
//            should.assertTrue(reply4.result().getInteger(0) == 0);
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

  @Test
  public void testScriptload(VertxTestContext test) {
    String inline = "return 1";
    redis.script(List.of("LOAD", inline)).onComplete(test.succeeding(reply -> {
      assertNotNull(reply);
      test.completeNow();
    }));

  }

//  @Test
//  public void testSdiff(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3, reply1.result().toLong());
//        redis.sdiff(mykey1, toList(mykey2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          Object[] expected = new Object[]{"a", "b"};
//          Object[] result = reply2.result().getList().toArray();
//          Arrays.sort(result);
//          should.assertArrayEquals(expected, result);
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSdiffstore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.sadd(toList(mykey1, "a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.sadd(toList(mykey2, "c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3L, reply1.result().toLong());
//        redis.sdiffstore(toList(mykey, mykey1, mykey2), reply2 -> {
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
//  }

  @Test
  public void testSelect(VertxTestContext test) {
    redis.select("1")
      .compose(reply -> redis.set(List.of("first", "value")))
      .compose(reply2 -> redis.select("0"))
      .compose(reply3 -> redis.select("1"))
      .compose(reply4 -> redis.get("first"))
      .onComplete(test.succeeding(reply5 -> {
        assertEquals("value", reply5.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSet(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply -> redis.get(mykey))
      .onComplete(test.succeeding(reply1 -> {
        assertEquals("Hello", reply1.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetWithOptions(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello!", "NX", "EX", "10"))
      .compose(reply0 -> redis.get(mykey))
      .compose(reply1 -> {
        assertEquals("Hello!", reply1.toString());
        return redis.set(List.of(mykey, "Hello again!", "NX", "EX", "10"));
      })
      .compose(reply2 -> redis.get(mykey))
      .onComplete(test.succeeding(reply3 -> {
        // It's not 'Hello again!' but the old value since we used NX which means set unless exists
        assertEquals("Hello!", reply3.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetWithOptions2(VertxTestContext test) {
    final String mykey = randomKey();
    // 1st case SET k v NX -> OK

    redis.set(List.of(mykey, "Hello!", "NX"))
      // 2nd case SET k v NX -> nil (was already set)
      .compose(reply0 -> redis.set(List.of(mykey, "Hello again!", "NX")))
      .onComplete(test.succeeding(reply2 -> {
        assertNull(reply2);
        test.completeNow();
      }));
  }

  @Test
  public void testSetbit(VertxTestContext test) {
    final String mykey = randomKey();

    redis.setbit(mykey, "7", "1")
      .compose(reply0 -> {
        assertEquals(0L, reply0.toLong());
        return redis.setbit(mykey, "7", "0");
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("\u0000", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetex(VertxTestContext test) {
    final String mykey = randomKey();

    redis.setex(mykey, "10", "Hello")
      .compose(reply0 -> {
        return redis.ttl(mykey);
      })
      .compose(reply1 -> {
        assertEquals(10L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetnx(VertxTestContext test) {
    final String mykey = randomKey();

    redis.setnx(mykey, "Hello")
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.setnx(mykey, "World");
      })
      .compose(reply1 -> {
        assertEquals(0L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello", reply2.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetrange(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello World"))
      .compose(reply0 -> {
        return redis.setrange(mykey, "6", "Redis");
      })
      .compose(reply1 -> {
        assertEquals(11L, reply1.toLong());
        return redis.get(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals("Hello Redis", reply2.toString());
        test.completeNow();
      }));
  }

//  @Test
//  public void testSinter(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3, reply1.result().toLong());
//        redis.sinter(toList(mykey1, mykey2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertArrayEquals(new Object[]{"c"}, reply2.result().getList().toArray());
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSinterstore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.saddMany(mykey1, toList("a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.saddMany(mykey2, toList("c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3, reply1.result().toLong());
//        redis.sinterstore(mykey, toList(mykey1, mykey2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertTrue(reply2.result() == 1);
//          //should.assertArrayEquals(new Object[]{"c"}, reply6.result().getList().toArray());
//          test.complete();
//        });
//      });
//    });
//
//  }

  @Test
  public void testSismember(VertxTestContext test) {
    final String mykey = randomKey();

    redis.sadd(List.of(mykey, "one"))
      .compose(reply0 -> {
        return redis.sismember(mykey, "one");
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.sismember(mykey, "two");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(0L, reply2.toLong());
        test.completeNow();
      }));
  }

//  @Test
//  public void testSmembers(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("Hello", "World"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2, reply0.result().toLong());
//      redis.smembers(mykey, reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        Object[] expected = new Object[]{"Hello", "World"};
//        Object[] result = reply1.result().getList().toArray();
//        Arrays.sort(result);
//        should.assertArrayEquals(expected, result);
//        test.complete();
//      });
//    });
//
//  }

//  @Test
//  public void testSmove(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String myotherkey = makeKey();
//    redis.saddMany(mykey, toList("one", "two"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2, reply0.result().toLong());
//      redis.sadd(myotherkey, "three", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        redis.smove(mykey, myotherkey, "two", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1, reply2.result().toLong());
//          redis.smembers(mykey, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            Object[] expected = new Object[]{"one"};
//            Object[] result = reply3.result().getList().toArray();
//            Arrays.sort(result);
//            should.assertArrayEquals(expected, result);
//            redis.smembers(myotherkey, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              Object[] expected1 = new Object[]{"three", "two"};
//              Object[] result1 = reply4.result().getList().toArray();
//              Arrays.sort(result1);
//              should.assertArrayEquals(expected1, result1);
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSort(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//
//    final String k1 = mykey + ":1";
//    final String k2 = mykey + ":2";
//    final String k3 = mykey + ":3";
//    final String kx = mykey + ":*";
//
//    redis.saddMany(mykey, toList("1", "2", "3"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.set(k1, "one", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        redis.set(k2, "two", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          redis.set(k3, "three", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            redis.sort(mykey, new SortOptions().useDescending().addGet(kx), reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertArrayEquals(toArray("three", "two", "one"), reply4.result().getList().toArray());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSpop(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.spop(mykey, reply1 -> {
//        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
//        String ret = reply1.result();
//        should.assertTrue(ret.equals("one") || ret.equals("two") || ret.equals("three"));
//        JsonArray expected = new JsonArray();
//        if (!ret.equals("one")) {
//          expected.add("one");
//        }
//        if (!ret.equals("two")) {
//          expected.add("two");
//        }
//        if (!ret.equals("three")) {
//          expected.add("three");
//        }
//        redis.smembers(mykey, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          Object[] expectedA = expected.getList().toArray();
//          Arrays.sort(expectedA);
//          Object[] res = reply2.result().getList().toArray();
//          Arrays.sort(res);
//          should.assertArrayEquals(expectedA, res);
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSpopMany(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.saddMany(mykey, toList("one", "two", "three"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.spopMany(mykey, 3, reply1 -> {
//        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
//        JsonArray ret = reply1.result();
//        test.complete();
//      });
//    });
//
//  }

//  @Test
//  public void testSrandmember(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    redis.sadd(toList(mykey, "one", "two", "three"), reply0 -> {
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

  @Test
  public void testSrem(VertxTestContext test) {
    final String mykey = randomKey();

    redis.sadd(List.of(mykey, "one", "two", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.srem(List.of(mykey, "one"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.srem(List.of(mykey, "four"));
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(0L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testStrlen(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello world"))
      .compose(reply0 -> {
        return redis.strlen(mykey);
      })
      .compose(reply1 -> {
        assertEquals(11L, reply1.toLong());
        return redis.strlen("nonexisting");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(0L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testSubscribe(VertxTestContext test) {
    String key = randomKey();

    redis.subscribe(List.of(key))
      .compose(reply -> redis.unsubscribe(List.of(key)))
      .onComplete(test.succeedingThenComplete());
  }

//  @Test
//  public void testSunion(TestContext should) {
//    final Async test = should.async();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    CountDownLatch latch = new CountDownLatch(6);
//
//    redis.sadd(mykey1, "a", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      latch.countDown();
//
//      redis.sadd(mykey1, "b", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        latch.countDown();
//      });
//      redis.sadd(mykey1, "c", reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertEquals(1, reply2.result().toLong());
//        latch.countDown();
//      });
//      redis.sadd(mykey2, "c", reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertEquals(1, reply3.result().toLong());
//        latch.countDown();
//      });
//      redis.sadd(mykey2, "d", reply4 -> {
//        should.assertTrue(reply4.succeeded());
//        should.assertEquals(1, reply4.result().toLong());
//        latch.countDown();
//      });
//      redis.sadd(mykey2, "e", reply5 -> {
//        should.assertTrue(reply5.succeeded());
//        should.assertEquals(1, reply5.result().toLong());
//        latch.countDown();
//        try {
//          latch.await(10, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//          test.complete();
//          fail("Time out while waiting for all SADD calls to finish");
//        }
//        redis.sunion(toList(mykey1, mykey2), reply6 -> {
//          should.assertTrue(reply6.succeeded());
//          JsonArray arr = reply6.result();
//          Object[] array = arr.getList().toArray();
//          Arrays.sort(array);
//          should.assertTrue(array.length == 5);
//          should.assertArrayEquals(new Object[]{"a", "b", "c", "d", "e"}, array);
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testSunionstore(TestContext should) {
//    final Async test = should.async();
//    final String mykey = makeKey();
//    final String mykey1 = makeKey();
//    final String mykey2 = makeKey();
//
//    redis.sadd(toList(mykey1, "a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3L, reply0.result().toLong());
//      redis.sadd(toList(mykey2, "c", "d", "e"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(3L, reply1.result().toLong());
//        redis.sunionstore(toList(mykey, mykey1, mykey2), reply2 -> {
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

  @Test
  public void testSync(VertxTestContext test) {
    redis.sync().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testTime(VertxTestContext test) {
    redis.time().onComplete(test.succeeding(reply0 -> {
      assertEquals(2, reply0.size());
      test.completeNow();
    }));
  }

  @Test
  public void testTtl(VertxTestContext test) {
    final String mykey = randomKey();

    redis.set(List.of(mykey, "Hello"))
      .compose(reply0 -> {
        return redis.expire(Arrays.asList(mykey, "10"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.ttl(mykey);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(10L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testType(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    final String key3 = randomKey();

    redis.set(List.of(key1, "value"))
      .compose(reply0 -> {
        return redis.lpush(List.of(key2, "value"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.sadd(List.of(key3, "value"));
      })
      .compose(reply2 -> {
        assertEquals(1L, reply2.toLong());
        return redis.type(key1);
      })
      .compose(reply3 -> {
        assertEquals("string", reply3.toString());
        return redis.type(key2);
      })
      .compose(reply4 -> {
        assertEquals("list", reply4.toString());
        return redis.type(key3);
      })
      .onComplete(test.succeeding(reply5 -> {
        assertEquals("set", reply5.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testUnsubscribe(VertxTestContext test) {
    String key = randomKey();

    redis.subscribe(List.of(key))
      .compose(reply -> redis.unsubscribe(List.of(key)))
      .onComplete(test.succeedingThenComplete());
  }

//  @Test
//  public void testWatch(TestContext should) {
//    final Async test = should.async();
//    String key = makeKey();
//
//    RedisClient rdx = RedisClient.create(vertx, getConfig());
//    RedisTransaction transaction = redis.transaction();
//    redis.set(key, "0", reply -> {
//      should.assertTrue(reply.succeeded());
//      transaction.watch(key, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//
//        transaction.multi(reply3 -> {
//          should.assertTrue(reply3.succeeded());
//          transaction.incr(key, reply4 -> {
//            should.assertTrue(reply4.succeeded());
//
//            rdx.incr(key, reply1 -> {
//              should.assertTrue(reply1.succeeded());
//              should.assertEquals(Long.valueOf(1L), reply1.result());
//
//              transaction.incr(key, reply5 -> {
//                should.assertTrue(reply5.succeeded());
//                transaction.incrby(key, 10, reply6 -> {
//                  should.assertTrue(reply6.succeeded());
//                  transaction.exec(reply7 -> {
//                    should.assertTrue(reply7.succeeded());
//                    should.assertNull(reply7.result());
//                    redis.get(key, reply8 -> {
//                      should.assertTrue(reply8.succeeded());
//                      should.assertEquals("1", reply8.result());
//                      test.complete();
//                    });
//                  });
//                });
//              });
//            });
//          });
//        });
//      });
//    });
//  }

//  @Test
//  public void testZadd(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.zadd(key, 1, "uno", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        Map<String, Double> values = new HashMap<>();
//        values.put("one", 1.0);
//        values.put("two", 2.0);
//        values.put("two", 3.0);
//        redis.zaddMany(key, values, reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1, reply2.result().toLong());
//          redis.zadd(key, 3, "two", reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertEquals(0, reply3.result().toLong());
//            redis.zrangeWithOptions(key, 0, -1, RangeOptions.WITHSCORES, reply4 -> {
//              should.assertTrue(reply4.succeeded());
//              should.assertArrayEquals(toArray("one", "1", "uno", "1", "two", "3"), reply4.result().getList().toArray());
//              test.complete();
//            });
//          });
//        });
//      });
//    });
//  }

  @Test
  public void testZcard(VertxTestContext test) {
    final String key = randomKey();

    redis.zadd(List.of(key, "1", "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.zadd(List.of(key, "2", "two"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.zcard(key);
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(2L, reply2.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZcount(VertxTestContext test) {
    final String key = randomKey();

    redis.zadd(List.of(key, "1", "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.zadd(List.of(key, "2", "two"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.zadd(List.of(key, "3", "three"));
      })
      .compose(reply2 -> {
        assertEquals(1L, reply2.toLong());
        return redis.zcount(key, Double.toString(Double.NEGATIVE_INFINITY), Double.toString(Double.POSITIVE_INFINITY));
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(3L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZincrby(VertxTestContext test) {
    final String key = randomKey();

    redis.zadd(List.of(key, "1", "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.zadd(List.of(key, "2", "two"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.zincrby(key, "2", "one");
      })
      .onComplete(test.succeeding(reply2 -> {
        assertEquals(3.0, reply2.toDouble());
        test.completeNow();
      }));
  }

  @Test
  public void testZinterstore(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();
    final String key3 = randomKey();

    redis.zadd(List.of(key1, "1", "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.zadd(List.of(key1, "2", "two"));
      })
      .compose(reply1 -> {
        assertEquals(1L, reply1.toLong());
        return redis.zadd(List.of(key2, "1", "one"));
      })
      .compose(reply2 -> {
        assertEquals(1L, reply2.toLong());
        return redis.zadd(List.of(key2, "2", "two"));
      })
      .compose(reply3 -> {
        assertEquals(1L, reply3.toLong());
        return redis.zadd(List.of(key2, "3", "three"));
      })
      .compose(reply4 -> {
        assertEquals(1L, reply4.toLong());
        return redis.zinterstore(List.of(key3, "2", key1, key2, "WEIGHTS", "2.0", "3.0"));
      })
      .onComplete(test.succeeding(reply5 -> {
        assertEquals(2L, reply5.toLong());
        test.completeNow();
      }));
  }

//  @Test
//  public void testZrange(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.zadd(key, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        redis.zadd(key, 3, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1, reply2.result().toLong());
//          redis.zrange(key, 0, -1, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testZrangebyscore(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    redis.zadd(key, 1, "one", reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(1, reply0.result().toLong());
//      redis.zadd(key, 2, "two", reply1 -> {
//        should.assertTrue(reply1.succeeded());
//        should.assertEquals(1, reply1.result().toLong());
//        redis.zadd(key, 3, "three", reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          should.assertEquals(1, reply2.result().toLong());
//          redis.zrangebyscore(key, "-inf", "+inf", RangeLimitOptions.NONE, reply3 -> {
//            should.assertTrue(reply3.succeeded());
//            should.assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

  @Test
  public void testZrangebyscoreWithLimits(VertxTestContext test) {
    redis.zrangebyscore(List.of("yourkey", "-inf", "+inf")).onComplete(test.succeedingThenComplete());
  }

  @Test
  public void testZrank(VertxTestContext test) {
    final String key = randomKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zadd(List.of(key, "1.0", "one", "2.0", "two", "3.0", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.zrank(key, "three");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(2L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZrem(VertxTestContext test) {
    final String key = randomKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);

    redis.zadd(List.of(key, "1.0", "one", "2.0", "two", "3.0", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.zrem(List.of(key, "two"));
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(1L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZremrangebyrank(VertxTestContext test) {
    final String key = randomKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zadd(List.of(key, "1.0", "one", "2.0", "two", "3.0", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.zremrangebyrank(key, "0", "1");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(2L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZremrangebyscore(VertxTestContext test) {
    final String key = randomKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zadd(List.of(key, "1.0", "one", "2.0", "two", "3.0", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.zremrangebyscore(key, "-inf", "(2");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(1L, reply3.toLong());
        test.completeNow();
      }));
  }

//  @Test
//  public void testZrevrange(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.zrevrange(key, 0, -1, RangeOptions.NONE, reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
//        test.complete();
//      });
//    });
//
//  }

//  @Test
//  public void testZrevrangebyscore(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    values.put("three", 3.0);
//    redis.zaddMany(key, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(3, reply0.result().toLong());
//      redis.zrevrangebyscore(key, "+inf", "-inf", RangeLimitOptions.NONE, reply3 -> {
//        should.assertTrue(reply3.succeeded());
//        should.assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
//        test.complete();
//      });
//    });
//
//  }

  @Test
  public void testZrevrank(VertxTestContext test) {
    final String key = randomKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zadd(List.of(key, "1.0", "one", "2.0", "two", "3.0", "three"))
      .compose(reply0 -> {
        assertEquals(3L, reply0.toLong());
        return redis.zrevrank(key, "one");
      })
      .onComplete(test.succeeding(reply3 -> {
        assertEquals(2L, reply3.toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testZscore(VertxTestContext test) {
    final String key = randomKey();
    redis.zadd(List.of(key, "1.0", "one"))
      .compose(reply0 -> {
        assertEquals(1L, reply0.toLong());
        return redis.zscore(key, "one");
      })
      .onComplete(test.succeeding(reply1 -> {
        assertEquals(1.0, reply1.toDouble());
        test.completeNow();
      }));
  }

//  @Test
//  public void testZunionstore(TestContext should) {
//    final Async test = should.async();
//    final String key1 = makeKey();
//    final String key2 = makeKey();
//    final String key3 = makeKey();
//
//    Map<String, Double> values = new HashMap<>();
//    values.put("one", 1.0);
//    values.put("two", 2.0);
//    redis.zaddMany(key1, values, reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals(2, reply0.result().toLong());
//
//      values.put("three", 3.0);
//      redis.zaddMany(key2, values, reply2 -> {
//        should.assertTrue(reply2.succeeded());
//        should.assertEquals(3, reply2.result().toLong());
//
//        Map<String, Double> weighedSets = new HashMap<>();
//        weighedSets.put(key1, 2.0);
//        weighedSets.put(key2, 3.0);
//        redis.zunionstoreWeighed(key3, weighedSets, AggregateOptions.NONE, reply5 -> {
//          should.assertTrue(String.valueOf(reply5.cause()), reply5.succeeded());
//          should.assertEquals(3, reply5.result().toLong());
//          test.complete();
//        });
//      });
//    });
//
//  }

//  // redis-embedded version of redis does not support HSCAN
//  @Test
//  public void testHscan(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    Map<String, Object> obj = new HashMap<>();
//    for (int i = 0; i < 100; i++) {
//      obj.put("field" + i, "val" + i);
//    }
//    redis.hmset(key, new JsonObject(obj), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      should.assertEquals("OK", reply0.result());
//      redis.hscan(key, "0", new ScanOptions().setMatch("field1*"), reply1 -> {
//        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
//        JsonArray result = reply1.result();
//        should.assertEquals(2, result.size());
//
//        JsonArray page = result.getJsonArray(1);
//        should.assertEquals(22, page.size());
//
//        test.complete();
//      });
//    });
//
//  }

//  @Test
//  public void testBitpos(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    final byte[] value = new byte[]{(byte) 0xff, (byte) 0xf0, (byte) 0x00};
//    redis.setBinary(key, Buffer.buffer(value), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.bitpos(key, 0, reply1 -> {
//        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
//        should.assertEquals(12, reply1.result().toLong());
//
//        final byte[] value2 = new byte[]{0, 0, 0};
//        redis.setBinary(key, Buffer.buffer(value2), reply2 -> {
//          should.assertTrue(reply2.succeeded());
//          redis.bitpos(key, 1, reply3 -> {
//            should.assertTrue(String.valueOf(reply3.cause()), reply3.succeeded());
//            should.assertEquals(-1, reply3.result().toLong());
//
//            test.complete();
//          });
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testBitposFrom(TestContext should) {
//    final Async test = should.async();
//    final String key = makeKey();
//    final byte[] value = new byte[]{(byte) 0x00, (byte) 0xff, (byte) 0xf0};
//    redis.setBinary(key, Buffer.buffer(value), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//      redis.bitposFrom(key, 1, 0, reply1 -> {
//        should.assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
//        should.assertEquals(8, reply1.result().toLong());
//        redis.bitposFrom(key, 1, 2, reply2 -> {
//          should.assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
//          should.assertEquals(16, reply2.result().toLong());
//
//          test.complete();
//        });
//      });
//    });
//
//  }

//  @Test
//  public void testIssue5BlockingCall_shouldWork(TestContext should) {
//    final Async test = should.async();
//    final String list1 = makeKey();
//    final String list2 = makeKey();
//
//    redis.rpush(toList(list1, "a", "b", "c"), reply0 -> {
//      should.assertTrue(reply0.succeeded());
//
//      should.assertEquals(3L, reply0.result().toLong());
//
//      redis.brpop(toList(list1, list2, "0"), reply1 -> {
//        should.assertTrue(reply1.succeeded());
//
//        should.assertEquals(list1, reply1.result().getString(0));
//        should.assertEquals("c", reply1.result().getString(1));
//        test.complete();
//      });
//    });
//  }

  @Test
  public void testIssue5BlockingCall_report(VertxTestContext test) {
    final String list = randomKey();

    redis.brpop(List.of(list, "2")).onComplete(test.succeeding(reply1 -> {
      assertNull(reply1);
      test.completeNow();
    }));
  }

  @Test
  public void testSscan(VertxTestContext test) {
    final String key = randomKey();

    redis.sadd(List.of(key, "1", "2", "3", "foo", "bar", "feelsgood"))
      .compose(reply1 -> {
        assertEquals(6L, reply1.toLong());
        return redis.sscan(List.of(key, "0", "MATCH", "f*"));
      }).onComplete(test.succeeding(reply2 -> {
        assertEquals(0, reply2.get(0).toInteger());
        assertEquals("foo", reply2.get(1).get(0).toString());
        assertEquals("feelsgood", reply2.get(1).get(1).toString());
        test.completeNow();
      }));
  }
}
