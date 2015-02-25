package io.vertx.test.redis;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.BitOperation;
import io.vertx.redis.InsertOptions;
import io.vertx.redis.KillFilter;
import io.vertx.redis.MigrateOptions;
import io.vertx.redis.ObjectCmd;
import io.vertx.redis.RedisService;
import io.vertx.redis.ScanOptions;
import io.vertx.redis.SetOptions;
import io.vertx.test.core.VertxTestBase;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import redis.embedded.RedisServer;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server unless
 * the <code>host</code> or <code>port</code> system property is specified. In this case the
 * test assumes an external database will be used.
 */
public class RedisServiceTestBase extends VertxTestBase {

  private static final Integer DEFAULT_PORT = 6379;

  static RedisServer redisServer;

  private static final Map<Integer, RedisServer> instances = new ConcurrentHashMap<>();

  private static String getHost() {
    return getProperty("host");
  }

  private static String getPort() {
    return getProperty("port");
  }

  private static String getProperty(String name) {
    String s = System.getProperty(name);
    return (s != null && s.trim().length() > 0) ?  s : null;
  }

  protected RedisService redis;

  @BeforeClass
  static public void startRedis() throws Exception {
    if (getHost() == null && getPort() == null) {
      createRedisInstance(DEFAULT_PORT);
      instances.get(DEFAULT_PORT).start();
    }
  }

  @AfterClass
  static public void stopRedis() throws Exception {
    for(Map.Entry<Integer, RedisServer> entry: instances.entrySet()) {
      if(entry != null){
        entry.getValue().stop();
      }
    }
  }

  public static void createRedisCount(final int count) throws Exception {
    Integer[] ports = new Integer[count];
    Integer basePort = DEFAULT_PORT;
    for(int i = 0; i < count; i++) {
      ports[i] = basePort++;
    }
    createRedisInstance(ports);
  }

  public static void createRedisInstance(final Integer... ports) throws Exception {
    for(Integer port: ports) {
      instances.put(port, new RedisServer(port));
    }
  }

  protected JsonObject getConfig() {
    JsonObject config = new JsonObject();
    String host = getHost();
    String port = getPort();
    if (host != null) {
      config.put("host", host);
    }
    if (port != null) {
      config.put("port", Integer.parseInt(port));
    }
    return config;
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

  private static Map<String, String> toMap(final String ... params) {
    if (params.length % 2 != 0) {
      throw new IllegalArgumentException("Last key has no value");
    }
    Map<String, String> result = new HashMap<>();
    String key = null;
    for (String param: params) {
      if (key == null) {
        key = param;
      } else {
        result.put(key, param);
        key = null;
      }
    }
    return result;
  }

  private static <T> List<T> toList(final T ... params) {
    return Arrays.asList(params);
  }

  @Test
  public void testAppend() {
    final String key = makeKey();

    redis.del(toList(key), reply0 -> {
      assertTrue(reply0.succeeded());

      redis.append(key, "Hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(5l, reply1.result().longValue());

        redis.append(key, " World", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(11l, reply2.result().longValue());

          redis.get(key, reply3 -> {
            assertTrue(reply3.succeeded());
            assertTrue(reply3.succeeded());
            assertEquals("Hello World", reply3.result());
            testComplete();
          });
        });
      });
    });

    await();
  }

  @Test
  //Note the try/finally is to ensure that the server is shutdown so other tests do not have to
  //provide auth information
  public void testAuth() throws Exception {

    RedisServer server = RedisServer.builder().port(6381).setting("requirepass foobar").build();
      server.start();
      JsonObject job = new JsonObject().put("host", "localhost").put("port", 6381);
      RedisService rdx = RedisService.create(vertx, job);

      CountDownLatch latch = new CountDownLatch(1);
      rdx.start(asyncResult -> {
        assertTrue(asyncResult.succeeded());
        latch.countDown();
      });

      awaitLatch(latch);

      rdx.auth("barfoo", reply -> {
        assertFalse(reply.succeeded());
        rdx.auth("foobar", reply2 -> {
          assertTrue(reply2.succeeded());
          try{
            server.stop();            
          }catch(Exception ignore){}
          testComplete();
        });
      });
      await();    
  }

  @Test
  public void testBgrewriteaof() {
    redis.bgrewriteaof(reply ->{
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testBgsave() {

    redis.bgsave(reply ->{
      assertTrue(reply.succeeded());
      assertEquals("Background saving started", reply.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testBitcount() {
    final String key = makeKey();

    redis.set(key, "foobar", reply0 -> {
      assertTrue(reply0.succeeded());

      redis.bitcount(key, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(26, reply1.result().longValue());

        redis.bitcountRange(key, 0, 0, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(4, reply2.result().longValue());

          redis.bitcountRange(key, 1, 1, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(6, reply3.result().longValue());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testBitop() {
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String destkey = makeKey();

    redis.set(key1, "foobar", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.set(key2, "abcdef", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.bitop(BitOperation.AND, destkey, toList(key1, key2), reply2 -> {
          assertTrue(reply2.succeeded());
          redis.get(destkey, reply3 -> {
            assertTrue(reply3.succeeded());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testBlpop() {
    final String list1 = makeKey();
    final String list2 = makeKey();

    redis.del(toList(list1, list2), reply0 -> {
      assertTrue(reply0.succeeded());

      redis.rpushMany(list1, toList("a", "b", "c"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(3, reply1.result().longValue());

        redis.blpopMany(toList(list1, list2), 0, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray(list1, "a"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testBrpop() {
    final String list1 = makeKey();
    final String list2 = makeKey();

    redis.del(toList(list1, list2), reply0 -> {
      assertTrue(reply0.succeeded());

      redis.rpushMany(list1, toList("a", "b", "c"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(3, reply1.result().longValue());

        redis.brpopMany(toList(list1, list2), 0, reply2 -> {
          assertTrue(errorMessage(reply2.cause()), reply2.succeeded());
          assertArrayEquals(toArray(list1, "c"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testBrpoplpush() throws Exception{

    redis.brpoplpush("list1", "list2", 100, result ->{

      if(result.succeeded()){
        redis.lpop("list2", result2 ->{
          if(result2.succeeded()){
            System.out.println(result2.result());
            assertTrue("hello".equals(result2.result()));
          }
          testComplete();
        });
      }
    });

    RedisService redis2 = RedisService.create(vertx, getConfig());
    CountDownLatch latch = new CountDownLatch(1);
    redis2.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        latch.countDown();
      } else {
        throw new RuntimeException("failed to setup", asyncResult.cause());
      }
    });

    awaitLatch(latch);

    redis2.lpush("list1", "hello", result -> {
    });

    await();

  }

  @Test
  public void testClientKill() {

    redis.clientList(reply -> {
      assertTrue(reply.succeeded());
      String clients = reply.result();
      String add = clients.split("\\s")[0].split("=")[1];
      redis.clientKill(new KillFilter().setAddr(add).setType(KillFilter.Type.NORMAL), reply2 ->{
        assertTrue(reply2.succeeded());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testClientList() {

    redis.clientList(result -> {
      assertTrue(result.succeeded());
      assertNotNull(result.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testClientSetAndGetName() throws Exception{

    CountDownLatch clientLatch = new CountDownLatch(1);

    redis.clientGetname(result -> {

      if(result.succeeded()) {
        assertNull(result.result());
      }
      clientLatch.countDown();
    });

    awaitLatch(clientLatch);

    CountDownLatch setLatch = new CountDownLatch(1);
    redis.clientSetname("test-connection", result -> {
      assertTrue(result.succeeded());
      setLatch.countDown();
    });

    awaitLatch(setLatch);

    redis.clientGetname(result -> {
      assertTrue(result.succeeded());
      assertEquals("test-connection", result.result());
      testComplete();
    });

    await();
  }


  @Test
  public void testConfigGet() {
    redis.configGet("*", reply -> {
      assertTrue(reply.succeeded());
      assertTrue(reply.result() != null && reply.result().size() > 0);
      testComplete();
    });
    await();
  }

  @Test
  public void testConfigSetAndGet() {

    redis.configSet("dbfilename", "redis.dump", reply ->{
      if(reply.succeeded()){
        redis.configGet("dbfilename", reply2 -> {
          if(reply2.succeeded()){
            assertNotNull(reply2.result().getString(0));
            assertTrue(reply2.result().getString(1).equals("redis.dump"));
            testComplete();
          }
        });
      }
    });

    await();
  }

  @Test
  public void testConfigResetstat() {

    redis.info(reply ->{
      assertTrue(reply.succeeded());
      JsonObject result = reply.result().getJsonObject("stats");
      Integer conn = Integer.valueOf(result.getString("total_connections_received"));
      assertTrue(conn > 0);
      redis.configResetstat(reply2 -> {
        assertTrue(reply2.succeeded());
        redis.info(reply3 ->{
          assertTrue(reply3.succeeded());
          //Note, this may appear strange, but the embedded server handles stats differently
          //Many are not reset correctly. Here we just test the flow of the COMMANDS
          testComplete();
        });

      });
    });
    await();
  }

  @Test
  public void testDbsize() {

    redis.dbsize(reply-> {
      assertTrue(reply.succeeded());
      Long size = reply.result();
      redis.set("new", "value", reply2 ->{
        assertTrue(reply2.succeeded());
        redis.dbsize(reply3 ->{
          assertTrue(reply3.succeeded());
          assert(reply3.result() == size + 1);
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  //Per the redis doc, this should not be used by clients, perhaps remove it from the API?
  public void testDebugObject() {
  }

  @Test
  public void testDebugSegfault() throws Exception {

    RedisServer server = RedisServer.builder().port(6381).build();
    server.start();
    JsonObject job = new JsonObject().put("host", "localhost").put("port", 6381);
    RedisService rdx = RedisService.create(vertx, job);

    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      assertTrue(asyncResult.succeeded());
      latch.countDown();
    });

    awaitLatch(latch);

    rdx.debugSegfault(reply ->{
      assertTrue(reply.succeeded());
      rdx.info(reply2 ->{
        assertFalse(reply2.succeeded());
        try{
          server.stop();          
        }catch(Exception ignore){}
        testComplete();
      });
    });

  }

  @Test
  public void testDecr() {
    final String mykey = makeKey();

    redis.set(mykey, "10", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.decr(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(9, reply1.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testDecrby() {
    final String mykey = makeKey();

    redis.set(mykey, "10", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.decrby(mykey, 5, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(5, reply1.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testDel() {
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    redis.set(key1, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.set(key2, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.del(toList(key1, key2, key3), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(2, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testDiscard() {
    
    String key = makeKey();    
    redis.set(key, "0", reply ->{
      assertTrue(reply.succeeded());
      redis.multi(reply2 ->{
        assertTrue(reply2.succeeded());
        redis.incr(key, reply3 ->{
          assertTrue(reply3.succeeded());
          redis.discard(reply4 ->{
            assertTrue(reply4.succeeded());
            redis.get(key, reply5 ->{
              assertTrue(reply5.succeeded());
              assertTrue(Integer.valueOf(reply5.result()) == 0);
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  //    @Test
  //    public void testDump() {
  //        final String mykey = makeKey();
  //
  //        redis.set(j(mykey, 10), reply0 -> {
  //            assertTrue(reply0.succeeded());
  //            redis.dump(j(mykey), reply1 -> {
  //                assertTrue(reply1.succeeded());
  //                try {
  //                    byte[] data = reply1.result().getBytes("ISO-8859-1");
  //
  //                    assertEquals(data[0], (byte) 0);
  //                    assertEquals(data[1], (byte) 0xc0);
  //                    assertEquals(data[2], (byte) '\n');
  //                    assertEquals(data[3], (byte) 6);
  //                    assertEquals(data[4], (byte) 0);
  //                    assertEquals(data[5], (byte) 0xf8);
  //                    assertEquals(data[6], (byte) 'r');
  //                    assertEquals(data[7], (byte) '?');
  //                    assertEquals(data[8], (byte) 0xc5);
  //                    assertEquals(data[9], (byte) 0xfb);
  //                    assertEquals(data[10], (byte) 0xfb);
  //                    assertEquals(data[11], (byte) '_');
  //                    assertEquals(data[12], (byte) '(');
  //                    testComplete();
  //                } catch (UnsupportedEncodingException e) {
  //                    fail(e.getMessage());
  //                }
  //            });
  //        });
  //        await();
  //    }

  @Test
  public void testEcho() {
    redis.echo("Hello World!", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals("Hello World!", reply0.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testEval() {

    final String key1 = makeKey();
    final String key2 = makeKey();
    redis.eval("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", toList(key1, key2), toList("first", "second"),
        reply -> {
          assertTrue(reply.succeeded());
          Object r = reply.result();
          assertNotNull(r);
          testComplete();
        });
    await();
  }

  @Test
  public void testEvalsha() {
    String inline = "return 1";
    redis.scriptLoad(new JsonArray().add(inline), reply->{
      assertTrue(reply.succeeded());
      assertNotNull(reply.result());
      redis.evalsha(reply.result(), Collections.emptyList(), Collections.emptyList(), reply2 ->{
        assertTrue(reply2.succeeded());
        testComplete();
      });
    });
    await();

  }

  @Test
  //Note same test as testMulti, kept for consistency
  public void testExec() {
    redis.multi(reply -> {
      assertTrue(reply.succeeded());
      redis.set("multi-key", "first", reply2 -> {
        assertTrue(reply2.succeeded());
        redis.set("multi-key2", "second", reply3 ->{
          assertTrue(reply3.succeeded());
        });
        redis.get("multi-key", reply4 ->{
          assertTrue(reply4.succeeded());
          assertTrue("QUEUED".equalsIgnoreCase(reply4.result()));
        });
        redis.exec(reply5 ->{
          assertTrue(reply5.succeeded());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testExists() {
    final String key1 = makeKey();
    final String key2 = makeKey();

    redis.set(key1, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.exists(key1, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.exists(key2, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testExpire() {
    final String mykey = makeKey();

    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.expire(mykey, 10, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.ttl(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(10, reply2.result().longValue());

          redis.set(mykey, "Hello World", reply3 -> {
            assertTrue(reply3.succeeded());
            redis.ttl(mykey, reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(-1, reply4.result().longValue());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testExpireat() {
    final String mykey = makeKey();

    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.exists(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.expireat(mykey, 1293840000, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());

          redis.exists(mykey, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(0, reply3.result().longValue());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testFlushall() {

    String key = makeKey();
    //As per the doc, this never fails
    redis.set(key, "blah", reply ->{
      assertTrue(reply.succeeded());
      redis.flushall(reply2 ->{
        assertTrue(reply.succeeded());
        redis.get(key, reply3 ->{
          assertTrue(reply3.succeeded());
          assertNull(reply3.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testFlushdb() {
    String key = makeKey();
    //As per the doc, this never fails
    redis.set(key, "blah", reply ->{
      assertTrue(reply.succeeded());
      redis.flushall(reply2 ->{
        assertTrue(reply.succeeded());
        redis.get(key, reply3 ->{
          assertTrue(reply3.succeeded());
          assertNull(reply3.result());
          testComplete();
        });
      });
    });
    await();

  }

  @Test
  public void testGet() {
    final String nonexisting = makeKey();
    final String mykey = makeKey();

    redis.get(nonexisting, reply0 -> {
      assertTrue(reply0.succeeded());
      assertNull(reply0.result());

      redis.set(mykey, "Hello", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testGetbit() {
    final String mykey = makeKey();

    redis.setbit(mykey, 7, 1, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(0, reply0.result().longValue());

      redis.getbit(mykey, 0, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(0, reply1.result().longValue());

        redis.getbit(mykey, 7, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());

          redis.getbit(mykey, 100, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(0, reply3.result().longValue());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testGetrange() {
    final String mykey = makeKey();

    redis.set(mykey, "This is a string", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.getrange(mykey, 0, 3, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("This", reply1.result());

        redis.getrange(mykey, -3, -1, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("ing", reply2.result());

          redis.getrange(mykey, 0, -1, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("This is a string", reply3.result());

            redis.getrange(mykey, 10, 100, reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals("string", reply4.result());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testGetset() {
    final String mycounter = makeKey();

    redis.incr(mycounter, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.getset(mycounter, "0", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("1", reply1.result());

        redis.get(mycounter, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("0", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHdel() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "foo", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hdel(myhash, "field1", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hdel(myhash, "field2", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHexists() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "foo", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hexists(myhash, "field1", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hexists(myhash, "field2", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHget() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "foo", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hget(myhash, "field1", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("foo", reply1.result());

        redis.hget(myhash, "field2", reply2 -> {
          assertTrue(reply2.succeeded());
          assertNull(reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHgetall() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hset(myhash, "field2", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hgetall(myhash, reply2 -> {
          assertTrue(reply2.succeeded());
          JsonObject obj = reply2.result();
          assertEquals("Hello", obj.getString("field1"));
          assertEquals("World", obj.getString("field2"));
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHincrby() {
    final String myhash = makeKey();

    redis.hset(myhash, "field", "5", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hincrby(myhash, "field", 1, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(6, reply1.result().longValue());

        redis.hincrby(myhash, "field", -1, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(5, reply2.result().longValue());

          redis.hincrby(myhash, "field", -10, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(-5, reply3.result().longValue());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testHIncrbyfloat() {
    final String mykey = makeKey();

    redis.hset(mykey, "field", "10.50", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hincrbyfloat(mykey, "field", 0.1, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("10.6", reply1.result());

        redis.hset(mykey, "field", "5.0e3", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());

          redis.hincrbyfloat(mykey, "field", 2.0e2, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("5200", reply3.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testHkeys() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hset(myhash, "field2", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hkeys(myhash, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("field1", "field2"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHlen() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hset(myhash, "field2", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hlen(myhash, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(2, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHmget() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hset(myhash, "field2", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hmget(myhash, toList("field1", "field2", "nofield"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("Hello", "World", null), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHmset() {
    final String myhash = makeKey();

    redis.hmset(myhash, toMap("field1", "Hello", "field2", "World"), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.hget(myhash, "field1", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello", reply1.result());
        redis.hget(myhash, "field2", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("World", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHset() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hget(myhash, "field1", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello", reply1.result());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testHsetnx() {
    final String myhash = makeKey();

    redis.hsetnx(myhash, "field", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hsetnx(myhash, "field", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(0, reply1.result().longValue());

        redis.hget(myhash, "field", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testHvals() {
    final String myhash = makeKey();

    redis.hset(myhash, "field1", "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.hset(myhash, "field2", "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());

        redis.hvals(myhash, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("Hello", "World"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testIncr() {
    final String mykey = makeKey();

    redis.set(mykey, "10", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.incr(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(11, reply1.result().longValue());

        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("11", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testIncrby() {
    final String mykey = makeKey();

    redis.set(mykey, "10", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.incrby(mykey, 5, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(15, reply1.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testIncrbyfloat() {
    final String mykey = makeKey();

    redis.set(mykey, "10.50", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.incrbyfloat(mykey, 0.1, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("10.6", reply1.result());

        redis.set(mykey, "5.0e3", reply2 -> {
          assertTrue(reply2.succeeded());
          redis.incrbyfloat(mykey, 2.0e2, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("5200", reply3.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testInfo() {
    redis.info(reply0 -> {
      assertTrue(reply0.succeeded());
      assertNotNull(reply0.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testKeys() {
    redis.mset(toMap("one", "1", "two", "2", "three", "3", "four", "4"), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.keys("*o*", reply1 -> {
        assertTrue(reply1.succeeded());
        JsonArray array = reply1.result();
        // this is because there are leftovers from previous tests
        assertTrue(3 <= array.size());

        redis.keys("t??", reply2 -> {
          assertTrue(reply2.succeeded());
          JsonArray array2 = reply2.result();
          assertTrue(1 == array2.size());

          redis.keys("*", reply3 -> {
            assertTrue(reply3.succeeded());
            JsonArray array3 = reply3.result();
            assertTrue(4 <= array3.size());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLastsave() {
    redis.lastsave(reply0 -> {
      assertTrue(reply0.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testLindex() {
    final String mykey = makeKey();

    redis.lpush(mykey, "World", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.lpush(mykey, "Hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());

        redis.lindex(mykey, 0, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());

          redis.lindex(mykey, -1, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("World", reply3.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLinsert() {
    final String mykey = makeKey();

    redis.rpush(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());

      redis.rpush(mykey, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());

        redis.linsert(mykey, InsertOptions.BEFORE, "World", "There", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testLlen() {
    final String mykey = makeKey();
    redis.lpush(mykey, "World", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.lpush(mykey, "Hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.llen(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(2, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testLpop() {
    final String mykey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.lpop(mykey, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("one", reply3.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLpushOne() {
    final String mykey = makeKey();
    redis.lpush(mykey, "world", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.lpush(mykey, "hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.lrange(mykey, 0, -1, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("hello", "world"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testLpush() {
    final String mykey = makeKey();
    redis.lpushMany(mykey, toList("!", "world"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(2, reply0.result().longValue());
      redis.lpushMany(mykey, toList(",", "hello"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(4, reply1.result().longValue());
        redis.lrange(mykey, 0, -1, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("hello", ",", "world", "!"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testLpushx() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.lpush(mykey, "World", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.lpushx(mykey, "Hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.lpushx(myotherkey, "Hello", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          redis.lrange(mykey, 0, -1, reply3 -> {
            assertTrue(reply3.succeeded());
            JsonArray array3 = reply3.result();
            assertTrue(2 == array3.size());

            assertTrue("Hello".equals(array3.getString(0)));
            assertTrue("World".equals(array3.getString(1)));
            redis.lrange(myotherkey, 0, -1, reply4 -> {
              JsonArray array4 = reply4.result();
              assertTrue(0 == array4.size());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLrange() {
    final String mykey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.lrange(mykey, 0, 0, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("one", reply3.result().getString(0));
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLrem() {
    final String mykey = makeKey();
    redis.rpush(mykey, "hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "hello", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "foo", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.rpush(mykey, "hello", reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(4, reply3.result().longValue());
            redis.lrem(mykey, -2, "hello", reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(2, reply4.result().longValue());
              redis.lrange(mykey, 0, -1, reply5 -> {
                assertTrue(reply5.succeeded());
                assertArrayEquals(toArray("hello", "foo"), reply5.result().getList().toArray());
                testComplete();
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLset() {
    final String mykey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.lset(mykey, 0, "four", reply3 -> {
            assertTrue(reply3.succeeded());
            redis.lset(mykey, -2, "five", reply4 -> {
              assertTrue(reply4.succeeded());
              redis.lrange(mykey, 0, -1, reply5 -> {
                assertTrue(reply5.succeeded());
                assertArrayEquals(toArray("four", "five", "three"), reply5.result().getList().toArray());
                testComplete();
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testLtrim() {
    final String mykey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.ltrim(mykey, 1, -1, reply3 -> {
            assertTrue(reply3.succeeded());
            redis.lrange(mykey, 0, -1, reply5 -> {
              assertTrue(reply5.succeeded());
              assertArrayEquals(toArray("two", "three"), reply5.result().getList().toArray());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testMget() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    redis.set(mykey1, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.set(mykey2, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.mgetMany(toList(mykey1, mykey2, "nonexisting"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("Hello", "World", null), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testMigrate() throws Exception {
    RedisServer server = RedisServer.builder().port(6382).build();
    server.start();
    JsonObject job = new JsonObject().put("host", "localhost").put("port", 6382);
    RedisService rdx = RedisService.create(vertx, job);

    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      assertTrue(asyncResult.succeeded());
      latch.countDown();
    });

    awaitLatch(latch);
    String key = makeKey();
    redis.set(key, "migrate", reply ->{
      assertTrue(reply.succeeded());
      redis.migrate("localhost", 6382, key, 0, 20000, MigrateOptions.NONE, reply2 ->{
        assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
        rdx.get(key, reply3 ->{
          assertTrue(reply3.succeeded());
          assertTrue("migrate".equals(reply3.result()));
          try{
            server.stop();            
          }catch(Exception ignore){}          
          testComplete();
        });
      });
    });
    await();
    rdx.stop(reply ->{
      assertTrue(reply.succeeded());
    });
  }

  @Test
  public void testMonitor() {

    redis.monitor(reply ->{
      assertTrue(reply.succeeded());
      testComplete();
    });
    await();
  }

  @Test
  public void testMove() {

    String key = makeKey();
    redis.set(key, "moved_key", reply ->{
      assertTrue(reply.succeeded());
      redis.move(key, 1, reply2 ->{
        assertTrue(reply2.succeeded());
        assertTrue(new Long(1).equals(reply2.result()));
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testMset() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    redis.mset(toMap(mykey1, "Hello", mykey2, "World"), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.get(mykey1, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello", reply1.result());
        redis.get(mykey2, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("World", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testMsetnx() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();
    final String mykey3 = makeKey();

    redis.msetnx(toMap(mykey1, "Hello", mykey2, "there"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.msetnx(toMap(mykey2, "there", mykey3, "world"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(0, reply1.result().longValue());
        redis.mgetMany(toList(mykey1, mykey2, mykey3), reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("Hello", "there", null), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testMulti() throws Exception {

    String key = makeKey();
    RedisService rdx = RedisService.create(vertx, getConfig());
    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      assertTrue(asyncResult.succeeded());
      latch.countDown();
    });

    awaitLatch(latch);
    
    redis.set(key, "0", reply ->{
      assertTrue(reply.succeeded());
      
    });
    
    redis.multi(reply -> {
      assertTrue(reply.succeeded());
      redis.set(makeKey(), "0", reply2 -> {
        assertTrue(reply2.succeeded());
        redis.set(makeKey(), "0", reply3 ->{
          assertTrue(reply3.succeeded());
        });
        redis.exec(reply4 ->{
          assertTrue(reply4.succeeded());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testObject() {

    String mykey = makeKey();
    redis.set(mykey, "test", reply ->{
      assertTrue(reply.succeeded());
      redis.object(mykey, ObjectCmd.REFCOUNT, reply2 ->{
        assertTrue(reply2.succeeded());
        redis.object(mykey, ObjectCmd.ENCODING, reply3 ->{
          assertTrue(reply3.succeeded());
          redis.object(mykey, ObjectCmd.IDLETIME, reply4 ->{
            assertTrue(reply4.succeeded());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testPersist() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.expire(mykey, 10, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.ttl(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(10, reply2.result().longValue());
          redis.persist(mykey, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.ttl(mykey, reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(-1, reply4.result().longValue());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testPexpire() {    
    final String mykey = makeKey();
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.pexpire(mykey, 1000, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());                
        redis.get(mykey, reply2 -> {
            assertTrue(reply2.succeeded());
            testComplete();
          });
        });
      });
    await();
  }

  @Test
  public void testPexpireat() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.pexpireat(mykey, 1555555555005L, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.ttl(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertTrue(200000000 > reply2.result() && reply2.result() > 0);
          redis.pttl(toJsonArray(mykey), reply3 -> {
            assertTrue(reply3.succeeded());
            assertTrue(1555555555005L > reply3.result() && reply3.result() > 0);
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testPing() {
    redis.ping(reply0 -> {
      assertEquals("PONG", reply0.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testPsetex() {
    final String mykey = makeKey();
    redis.psetex(mykey, 2000, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.pttl(toJsonArray(mykey), reply1 -> {
        assertTrue(reply1.succeeded());
        assertTrue(3000 > reply1.result() && reply1.result() > 0);
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testPubSubChannels() {

    redis.subscribe(new JsonArray().add("rustic"), sub->{

    });
  }

  @Test
  @Ignore
  public void testPubSubNumsub() {

    redis.subscribe(new JsonArray().add("rustic"), sub->{

    });
  }

  @Test
  @Ignore
  public void testPubSubNumpat() {

    redis.subscribe(new JsonArray().add("rustic"), sub->{

    });
  }

  @Test
  @Ignore
  public void testPsubscribe() {
  }

  @Test
  public void testPttl() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.expire(mykey, 3, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.pttl(toJsonArray(mykey), reply2 -> {
          assertTrue(reply2.succeeded());
          assertTrue(3000 >= reply2.result() && reply2.result() > 0);
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testPublish() {
    String key = makeKey();
    redis.set(key, "0", reply ->{
      assertTrue(reply.succeeded());
      redis.publish(toJsonArray(key, 1), reply2 ->{
        assertTrue(reply2.succeeded());
        assertTrue(reply2.result() == 0);
        testComplete();
      });
    });  
    await();
  }

  @Test
  @Ignore
  public void testPunsubscribe() {
  }

  @Test

  public void testQuit() {

    redis.quit(reply -> {
      if(reply.succeeded()){
        redis.ping(reply2 ->{
          if(reply2.succeeded()){
            fail("Connection is closed.");
          }
          testComplete();
        });
      }
    });
    await();
  }

  @Test
  public void testRandomkey() {

    redis.set("foo", "bar", reply->{
      if(reply.succeeded()){
        redis.randomkey(reply2->{
          if(reply2.succeeded()){
            assertNotNull(reply2.result());
            testComplete();
          }
        });
      }
    });
    await();
  }

  @Test
  public void testRename() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.rename(mykey, myotherkey, reply1 -> {
        assertTrue(reply1.succeeded());
        redis.get(myotherkey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testRenamenx() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();

    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.set(myotherkey, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.renamenx(mykey, myotherkey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          redis.get(myotherkey, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("World", reply3.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  @Ignore
  public void testRestore() {
  }

  @Test
  public void testRpop() {
    final String mykey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpush(mykey, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(3, reply2.result().longValue());
          redis.rpop(mykey, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("three", reply3.result());
            redis.lrange(mykey, 0, -1, reply5 -> {
              assertTrue(reply5.succeeded());
              assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testRpoplpush() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();
    redis.rpush(mykey, "one", reply0 -> {
        assertTrue(reply0.succeeded());
        assertEquals(1, reply0.result().longValue());
        redis.rpush(mykey, "two", reply1 -> {
          assertTrue(reply1.succeeded());
          assertEquals(2, reply1.result().longValue());
          redis.rpush(mykey, "three", reply2 -> {
            assertTrue(reply2.succeeded());
            assertEquals(3, reply2.result().longValue());
            redis.rpoplpush(mykey, myotherkey, reply3 -> {
              assertTrue(reply3.succeeded());
              assertEquals("three", reply3.result());
              redis.lrange(mykey, 0, -1, reply5 -> {
                assertTrue(reply5.succeeded());
                assertArrayEquals(toArray("one", "two"), reply5.result().getList().toArray());
                redis.lrange(myotherkey, 0, -1, reply6 -> {
                  assertArrayEquals(toArray("three"), reply6.result().getList().toArray());
                  testComplete();
                });
              });
            });
          });
        });
      }

    );

    await();

  }

  @Test
  public void testRpush() {
    final String mykey = makeKey();
    redis.rpush(mykey, "hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpush(mykey, "world", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.lrange(mykey, 0, -1, reply2 -> {
          assertTrue(reply2.succeeded());
          assertArrayEquals(toArray("hello", "world"), reply2.result().getList().toArray());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testRpushx() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();
    redis.rpush(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.rpushx(mykey, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(2, reply1.result().longValue());
        redis.rpushx(myotherkey, "World", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          redis.lrange(mykey, 0, -1, reply3 -> {
            assertTrue(reply3.succeeded());
            assertArrayEquals(toArray("Hello", "World"), reply3.result().getList().toArray());
            redis.lrange(myotherkey, 0, -1, reply4 -> {
              assertArrayEquals(new Object[0], reply4.result().getList().toArray());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSadd() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "Hello"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "World"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey, "World"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          redis.smembers(toJsonArray(mykey), reply3 -> {
            assertTrue(reply3.succeeded());
            Object[] expected = new Object[]{"Hello", "World"};
            Object[] result = reply3.result().getList().toArray();
            Arrays.sort(result);
            assertArrayEquals(expected, result);
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSave() {
    redis.save(reply ->{
      assertTrue(reply.succeeded());
      //Note, there's really not much else to do
      testComplete();
    });
    await();
  }

  @Test
  public void testScard() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "Hello"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "World"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.scard(toJsonArray(mykey), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(2, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testScriptexists() {
    String inline = "return 1";
    redis.scriptLoad(new JsonArray().add(inline), reply ->{
      assertTrue(reply.succeeded());
      String hash = reply.result();
      redis.scriptExists(new JsonArray().add(hash), reply2 ->{
        assertTrue(reply2.succeeded());
        assertTrue(reply2.result().getInteger(0) > 0);
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testScriptflush() {
    String inline = "return 1";
    redis.scriptLoad(new JsonArray().add(inline), reply ->{
      assertTrue(reply.succeeded());
      String hash = reply.result();
      redis.scriptExists(new JsonArray().add(hash), reply2 ->{
        assertTrue(reply2.succeeded());
        assertTrue(reply2.result().getInteger(0) > 0);
        redis.scriptFlush(reply3 ->{
          assertTrue(reply3.succeeded());
          redis.scriptExists(new JsonArray().add(hash), reply4 ->{
            assertTrue(reply4.succeeded());
            assertTrue(reply4.result().getInteger(0) == 0);
            testComplete();
          });
        });
      });
    });
    await();
  }

  //@Test
  public void testScriptkill() throws Exception {

    String inline = "while true do end";
    redis.eval(inline, Collections.emptyList(), Collections.emptyList(), reply ->{
      //server should be locked at this point
    });

    JsonObject job = new JsonObject().put("host", "localhost").put("port", 6379);
    RedisService rdx = RedisService.create(vertx, job);

    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      assertTrue(asyncResult.succeeded());
      latch.countDown();
    });

    awaitLatch(latch);

    rdx.scriptKill(reply ->{
      assertTrue(reply.succeeded());
      rdx.info(reply2 ->{
        assertTrue(reply2.succeeded());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testScriptload() {
    String inline = "return 1";
    redis.scriptLoad(new JsonArray().add(inline), reply->{
      assertTrue(reply.succeeded());
      assertNotNull(reply.result());
      testComplete();
    });
    await();
  }

  @Test
  public void testSdiff() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(1, reply4.result().longValue());
              redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(1, reply5.result().longValue());
                redis.sdiff(toJsonArray(mykey1, mykey2), reply6 -> {
                  assertTrue(reply6.succeeded());
                  Object[] expected = new Object[]{"a", "b"};
                  Object[] result = reply6.result().getList().toArray();
                  Arrays.sort(result);
                  assertArrayEquals(expected, result);
                  testComplete();
                });
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSdiffstore() {
    final String mykey = makeKey();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(1, reply4.result().longValue());
              redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(1, reply5.result().longValue());
                redis.sdiffstore(toJsonArray(mykey, mykey1, mykey2), reply6 -> {
                  assertTrue(reply6.succeeded());
                  Long diff = reply6.result().longValue();
                  assertTrue(diff == 2);
                  redis.smembers(new JsonArray().add(mykey), reply7 ->{
                    Set<String> expected = new HashSet(toList("a", "b"));
                    JsonArray members = reply7.result();
                    Set<String> result = new HashSet(members.getList());
                    assertEquals(expected, result);
                    testComplete();
                  });
                });
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSelect() {
    //Gee, think redis should have a get current DB command?
    redis.select(new JsonArray().add(1), reply -> {
      if(reply.succeeded()){
        redis.set("first", "value", reply2->{
          if(reply2.succeeded()){
            redis.select(new JsonArray().add(0), reply3 ->{
              if(reply3.succeeded()){
                redis.select(new JsonArray().add(1), reply4 -> {
                  if(reply4.succeeded()){
                    redis.get("first",reply5->{
                      if(reply5.succeeded()){
                        assertTrue("value".equals(reply5.result()));
                        testComplete();
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
    await();
  }

  @Test
  public void testSet() {
    final String mykey = makeKey();        
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.get(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello", reply1.result());        
            testComplete();
          });
        });
    await();
  }

  @Test
  public void testSetWithOptions() {
    final String mykey = makeKey();
    redis.setWithOptions(mykey, "Hello!", new SetOptions().setNX(true).setEX(10), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.get(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello!", reply1.result());

        redis.setWithOptions(mykey, "Hello again!", new SetOptions().setNX(true).setEX(10), reply2 -> {
          assertTrue(reply2.succeeded());
          redis.get(mykey, reply3 -> {
            assertTrue(reply3.succeeded());
            // It's not 'Hello again!' but the old value since we used NX which means set unless exists
            assertEquals("Hello!", reply1.result());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSetbit() {
    final String mykey = makeKey();
    redis.setbit(mykey, 7, 1, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(0, reply0.result().longValue());
      redis.setbit(mykey, 7, 0, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("\u0000", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testSetex() {
    final String mykey = makeKey();
    redis.setex(mykey, 10, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.ttl(mykey, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(10, reply1.result().longValue());
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testSetnx() {
    final String mykey = makeKey();
    redis.setnx(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.setnx(mykey, "World", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(0, reply1.result().longValue());
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testSetrange() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello World", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.setrange(toJsonArray(mykey, 6, "Redis"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(11, reply1.result().longValue());
        redis.get(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("Hello Redis", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testShutdown() throws Exception {

    RedisServer testServer = new RedisServer(6380);
    testServer.start();

    JsonObject job = new JsonObject().put("host", "localhost").put("port", 6380);
    RedisService rdx = RedisService.create(vertx, job);

    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        latch.countDown();
      } else {
        throw new RuntimeException("failed to setup", asyncResult.cause());
      }
    });

    awaitLatch(latch);

    rdx.shutdown(new JsonArray().add("NOSAVE"), reply ->{
      fail("server has been terminated. No reply expected");
    });

    rdx.ping(reply ->{
      assertFalse(reply.succeeded());
      testComplete();
    });

  }

  @Test
  public void testSinter() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(1, reply4.result().longValue());
              redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(1, reply5.result().longValue());
                redis.sinter(toJsonArray(mykey1, mykey2), reply6 -> {
                  assertTrue(reply6.succeeded());
                  assertArrayEquals(new Object[]{"c"}, reply6.result().getList().toArray());
                  testComplete();
                });
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSinterstore() {
    final String mykey = makeKey();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(1, reply4.result().longValue());
              redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(1, reply5.result().longValue());
                redis.sinterstore(toJsonArray(mykey, mykey1, mykey2), reply6 -> {
                  assertTrue(reply6.succeeded());
                  assertTrue(reply6.result() == 1);
                  //assertArrayEquals(new Object[]{"c"}, reply6.result().getList().toArray());
                  testComplete();
                });
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSismember() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "one"), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.sismember(toJsonArray(mykey, "one"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sismember(toJsonArray(mykey, "two"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  @Ignore
  public void testSlaveof() {
  }

  @Test
  @Ignore
  public void testSlowlog() {
  }

  @Test
  public void testSmembers() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "Hello"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "World"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.smembers(toJsonArray(mykey), reply2 -> {
          assertTrue(reply2.succeeded());
          Object[] expected = new Object[]{"Hello", "World"};
          Object[] result = reply2.result().getList().toArray();
          Arrays.sort(result);
          assertArrayEquals(expected, result);
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testSmove() {
    final String mykey = makeKey();
    final String myotherkey = makeKey();
    redis.sadd(toJsonArray(mykey, "one"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "two"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(myotherkey, "three"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.smove(toJsonArray(mykey, myotherkey, "two"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.smembers(toJsonArray(mykey), reply4 -> {
              assertTrue(reply4.succeeded());
              Object[] expected = new Object[]{"one"};
              Object[] result = reply4.result().getList().toArray();
              Arrays.sort(result);
              assertArrayEquals(expected, result);
              redis.smembers(toJsonArray(myotherkey), reply5 -> {
                assertTrue(reply5.succeeded());
                Object[] expected1 = new Object[]{"three", "two"};
                Object[] result1 = reply5.result().getList().toArray();
                Arrays.sort(result1);
                assertArrayEquals(expected1, result1);
                testComplete();
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSort() {
    final String mykey = makeKey();

    final String k1 = mykey + ":1";
    final String k2 = mykey + ":2";
    final String k3 = mykey + ":3";
    final String kx = mykey + ":*";

    redis.sadd(toJsonArray(mykey, "1", "2", "3"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.set(k1, "one", reply1 -> {
        assertTrue(reply1.succeeded());
        redis.set(k2, "two", reply2 -> {
          assertTrue(reply2.succeeded());
          redis.set(k3, "three", reply3 -> {
            assertTrue(reply3.succeeded());
            redis.sort(toJsonArray(mykey, "desc", "get", kx), reply4 -> {
              assertTrue(reply4.succeeded());
              assertArrayEquals(toArray("three", "two", "one"), reply4.result().getList().toArray());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSpop() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "one"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "two"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey, "three"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.spop(toJsonArray(mykey), reply3 -> {
            assertTrue(reply3.succeeded());
            String ret = reply3.result();
            assertTrue(ret.equals("one") || ret.equals("two") || ret.equals("three"));
            JsonArray expected = new JsonArray();
            if (!ret.equals("one")) {
              expected.add("one");
            }
            if (!ret.equals("two")) {
              expected.add("two");
            }
            if (!ret.equals("three")) {
              expected.add("three");
            }
            redis.smembers(toJsonArray(mykey), reply4 -> {
              assertTrue(reply4.succeeded());
              Object[] expectedA = expected.getList().toArray();
              Arrays.sort(expectedA);
              Object[] res = reply4.result().getList().toArray();
              Arrays.sort(res);
              assertArrayEquals(expectedA, res);
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testSrandmember() {
    //        final String mykey = makeKey();
    //        redis.sadd(toJsonArray(mykey, "one", "two", "three"), reply0 -> {
    //            assertTrue(reply0.succeeded());
    //            assertEquals(3, reply0.result().longValue());
    //            redis.srandmember(toJsonArray(mykey), reply1 -> {
    //                assertTrue(reply1.succeeded());
    //                String randmember = reply1.result().getString(1);
    //                assertTrue(randmember.equals("one") || randmember.equals("two") || randmember.equals("three"));
    //                testComplete();
    //            });
    //        });
    //        await();
  }

  @Test
  public void testSrem() {
    final String mykey = makeKey();
    redis.sadd(toJsonArray(mykey, "one"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey, "two"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(mykey, "three"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.srem(toJsonArray(mykey, "one"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.srem(toJsonArray(mykey, "four"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(0, reply4.result().longValue());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testStrlen() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello world", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.strlen(toJsonArray(mykey), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(11, reply1.result().longValue());
        redis.strlen(toJsonArray("nonexisting"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(0, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test  
  public void testSubscribe() {
    
    String key = makeKey();
    redis.subscribe(toJsonArray(key), reply ->{
      assertTrue(reply.succeeded());
      redis.unsubscribe(toJsonArray(key), reply2 ->{
        assertTrue(reply2.succeeded());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testSunion() {
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
      });
      redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
        assertTrue(reply2.succeeded());
        assertEquals(1, reply2.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(1, reply3.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
        assertTrue(reply4.succeeded());
        assertEquals(1, reply4.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
        assertTrue(reply5.succeeded());
        assertEquals(1, reply5.result().longValue());
        redis.sunion(toJsonArray(mykey1, mykey2), reply6 -> {
          assertTrue(reply6.succeeded());
          JsonArray arr = reply6.result();
          Object[] array = arr.getList().toArray();
          Arrays.sort(array);
          assertTrue(array.length == 5);
          assertArrayEquals(new Object[]{"a", "b", "c", "d", "e"}, array);
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testSunionstore() {
    final String mykey = makeKey();
    final String mykey1 = makeKey();
    final String mykey2 = makeKey();

    redis.sadd(toJsonArray(mykey1, "a"), reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.sadd(toJsonArray(mykey1, "b"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
      });
      redis.sadd(toJsonArray(mykey1, "c"), reply2 -> {
        assertTrue(reply2.succeeded());
        assertEquals(1, reply2.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "c"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(1, reply3.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "d"), reply4 -> {
        assertTrue(reply4.succeeded());
        assertEquals(1, reply4.result().longValue());
      });
      redis.sadd(toJsonArray(mykey2, "e"), reply5 -> {
        assertTrue(reply5.succeeded());
        assertEquals(1, reply5.result().longValue());
        redis.sunionstore(toJsonArray(mykey,mykey1, mykey2), reply6 -> {
          assertTrue(reply6.succeeded());
          assertTrue(reply6.result() == 5);
          //          JsonArray arr = reply6.result();
          //          Object[] array = arr.getList().toArray();
          //          Arrays.sort(array);
          //          assertTrue(array.length == 5);
          //          assertArrayEquals(new Object[]{"a", "b", "c", "d", "e"}, array);
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  @Ignore
  public void testSync() {
  }

  @Test
  public void testTime() {
    redis.time(reply0 -> {
      assertTrue(reply0.succeeded());
      assertTrue(reply0.result().size() == 2);
      testComplete();
    });
    await();
  }

  @Test
  public void testTtl() {
    final String mykey = makeKey();
    redis.set(mykey, "Hello", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.expire(mykey, 10, reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.ttl(mykey, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(10, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testType() {
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    redis.set(key1, "value", reply0 -> {
      assertTrue(reply0.succeeded());
      redis.lpush(key2, "value", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.sadd(toJsonArray(key3, "value"), reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.type(key1, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals("string", reply3.result());
            redis.type(key2, reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals("list", reply4.result());
              redis.type(key3, reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals("set", reply5.result());
                testComplete();
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testUnsubscribe() {
    String key = makeKey();
    redis.subscribe(toJsonArray(key), reply ->{
      assertTrue(reply.succeeded());
      redis.unsubscribe(toJsonArray(key), reply2 ->{
        assertTrue(reply2.succeeded());
        testComplete();
      });
    });
    await();
  }

  @Test
  @Ignore
  public void testUnwatch() {
  }

  @Test
  @Ignore
  public void testWatch() throws Exception {

    String key = makeKey();
    
    RedisService rdx = RedisService.create(vertx, getConfig());
    CountDownLatch latch = new CountDownLatch(1);
    rdx.start(asyncResult -> {
      assertTrue(asyncResult.succeeded());
      latch.countDown();
    });

    awaitLatch(latch);
    
    CountDownLatch clientLatch = new CountDownLatch(1);
    
    redis.set(key, "0", reply ->{
      assertTrue(reply.succeeded());
      redis.watch(toJsonArray(key), reply2 ->{
        assertTrue(reply2.succeeded());
        redis.multi(reply3 ->{
          assertTrue(reply3.succeeded());
          redis.incr(key, reply4 ->{
            assertTrue(reply4.succeeded());
              try {
                clientLatch.wait();                
              }catch(Exception e){}
              redis.incr(key, reply5 ->{
                assertTrue(reply5.succeeded());
              });  
              redis.incrby(key, 10, reply6 ->{
                assertTrue(reply6.succeeded());
              });
              redis.exec(reply7 ->{
                /**
                 * Note, this should really fail as we have watched a key and modified
                 * it outside of the multi protocol. However, it does not appear to be 
                 * showing this
                 */
                assertFalse(reply7.succeeded());
                redis.get(key, reply8 ->{
                  assertTrue(reply8.succeeded());
                  assertTrue(Integer.valueOf(reply8.result()) == 1);
                  testComplete();
                });
              });
            });
          });
        });
      });
    
    rdx.incr(key, reply ->{
      assertTrue(reply.succeeded());
      clientLatch.countDown();
    });
        
    await();
  }

  @Test
  public void testZadd() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 1, "uno", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        Map<String, Double> values = new HashMap<>();
        values.put("one", 1.0);
        values.put("two", 2.0);
        values.put("two", 3.0);
        redis.zaddMany(key, values, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.zadd(key, 3, "two", reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(0, reply3.result().longValue());
            redis.zrange(toJsonArray(key, 0, -1, "withscores"), reply4 -> {
              assertTrue(reply4.succeeded());
              assertArrayEquals(toArray("one", "1", "uno", "1", "two", "3"), reply4.result().getList().toArray());
              testComplete();
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testZcard() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zcard(key, reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(2, reply2.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testZcount() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zadd(key, 3, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.zcount(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(3, reply3.result().longValue());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testZincrby() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zincrby(key, 2, "one", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals("3", reply2.result());
          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testZinterstore() {
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    redis.zadd(key1, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key1, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zadd(key2, 1, "one", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.zadd(key2, 2, "two", reply3 -> {
            assertTrue(reply3.succeeded());
            assertEquals(1, reply3.result().longValue());
            redis.zadd(key2, 3, "three", reply4 -> {
              assertTrue(reply4.succeeded());
              assertEquals(1, reply4.result().longValue());
              redis.zinterstore(toJsonArray(key3, 2, key1, key2, "weights", 2, 3), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(2, reply5.result().longValue());
                testComplete();
              });
            });
          });
        });
      });
    });
    await();
  }

  @Test
  public void testZrange() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zadd(key, 3, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.zrange(toJsonArray(key, 0, -1), reply3 -> {
            assertTrue(reply3.succeeded());
            assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testZrangebyscore() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zadd(key, 2, "two", reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals(1, reply1.result().longValue());
        redis.zadd(key, 3, "three", reply2 -> {
          assertTrue(reply2.succeeded());
          assertEquals(1, reply2.result().longValue());
          redis.zrangebyscore(toJsonArray(key, "-inf", "+inf"), reply3 -> {
            assertTrue(reply3.succeeded());
            assertArrayEquals(toArray("one", "two", "three"), reply3.result().getList().toArray());
            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testZrank() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zrank(toJsonArray(key, "three"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(2, reply3.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZrem() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zrem(toJsonArray(key, "two"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(1, reply3.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZremrangebyrank() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zremrangebyrank(toJsonArray(key, 0, 1), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(2, reply3.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZremrangebyscore() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zremrangebyscore(toJsonArray(key, "-inf", "(2"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(1, reply3.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZrevrange() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zrevrange(toJsonArray(key, 0, -1), reply3 -> {
        assertTrue(reply3.succeeded());
        assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZrevrangebyscore() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zrevrangebyscore(toJsonArray(key, "+inf", "-inf"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertArrayEquals(toArray("three", "two", "one"), reply3.result().getList().toArray());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZrevrank() {
    final String key = makeKey();
    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    values.put("three", 3.0);
    redis.zaddMany(key, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(3, reply0.result().longValue());
      redis.zrevrank(toJsonArray(key, "one"), reply3 -> {
        assertTrue(reply3.succeeded());
        assertEquals(2, reply3.result().longValue());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZscore() {
    final String key = makeKey();
    redis.zadd(key, 1, "one", reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(1, reply0.result().longValue());
      redis.zscore(toJsonArray(key, "one"), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("1", reply1.result());
        testComplete();
      });
    });
    await();
  }

  @Test
  public void testZunionstore() {
    final String key1 = makeKey();
    final String key2 = makeKey();
    final String key3 = makeKey();

    Map<String, Double> values = new HashMap<>();
    values.put("one", 1.0);
    values.put("two", 2.0);
    redis.zaddMany(key1, values, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals(2, reply0.result().longValue());

      values.put("three", 3.0);
      redis.zaddMany(key2, values, reply2 -> {
        assertTrue(reply2.succeeded());
        assertEquals(3, reply2.result().longValue());
        redis.zunionstore(toJsonArray(key3, 2, key1, key2, "weights", 2, 3), reply5 -> {
          assertTrue(reply5.succeeded());
          assertEquals(3, reply5.result().longValue());
          testComplete();
        });
      });
    });
    await();
  }

  // TODO redis-embedded version of redis does not support HSCAN
  @Test
  public void testHscan() {
    final String key = makeKey();
    Map<String, String> obj = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      obj.put("field" + i, "val" + i);
    }
    redis.hmset(key, obj, reply0 -> {
      assertTrue(reply0.succeeded());
      assertEquals("OK", reply0.result().toString());
      redis.hscan(key, "0", new ScanOptions().setMatch("field1*"), reply1 -> {
        assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
        JsonArray result = reply1.result();
        assertEquals(2, result.size());

        JsonArray page = result.getJsonArray(1);
        assertEquals(22, page.size());

        testComplete();
      });
    });
    await();
  }

  @Test
  public void testBitpos() {
    final String key = makeKey();
    final byte[] value = new byte[] {(byte) 0xff, (byte) 0xf0, (byte) 0x00};
    Charset charset = Charset.forName("iso-8859-1");
    redis.setBinary(key, new String(value, charset), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.bitpos(key, 0, reply1 -> {
        assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
        assertEquals(12, reply1.result().longValue());

        final byte[] value2 = new byte[] {0, 0, 0};
        redis.setBinary(key, new String(value2, charset), reply2 -> {
          assertTrue(reply2.succeeded());
          redis.bitpos(key, 1, reply3 -> {
            assertTrue(String.valueOf(reply3.cause()), reply3.succeeded());
            assertEquals(-1, reply3.result().longValue());

            testComplete();
          });
        });
      });
    });
    await();
  }

  @Test
  public void testBitposFrom() {
    final String key = makeKey();
    final byte[] value = new byte[] {(byte) 0x00, (byte) 0xff, (byte) 0xf0};
    Charset charset = Charset.forName("iso-8859-1");
    redis.setBinary(key, new String(value, charset), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.bitposFrom(key, 1, 0, reply1 -> {
        assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
        assertEquals(8, reply1.result().longValue());
        redis.bitposFrom(key, 1, 2, reply2 -> {
          assertTrue(String.valueOf(reply2.cause()), reply2.succeeded());
          assertEquals(16, reply2.result().longValue());

          testComplete();
        });
      });
    });
    await();
  }

  @Test
  public void testBinarySetAndGet() {
    final String key = makeKey();
    final byte[] value = new byte[256];
    for (int i=0; i < value.length; i++) {
      value[i] = (byte) i;
    }
    Charset charset = Charset.forName("iso-8859-1");
    redis.setBinary(key, new String(value, charset), reply0 -> {
      assertTrue(reply0.succeeded());
      redis.getBinary(key, reply1 -> {
        assertTrue(String.valueOf(reply1.cause()), reply1.succeeded());
        assertArrayEquals(value, reply1.result().getBytes(charset));

        testComplete();
      });
    });
    await();
  }
}
