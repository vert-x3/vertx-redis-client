package io.vertx.test.redis;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisService;
import io.vertx.test.core.VertxTestBase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server unless
 * the <code>host</code> or <code>port</code> system property is specified. In this case the
 * test assumes an external database will be used.
 */
public class RedisServiceTest extends VertxTestBase {

    private static String getProperty(String name) {
        String s = System.getProperty(name);
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                return s;
            }
        }
        return null;
    }

    private static String getHost() {
        return getProperty("host");
    }

    private static String getPort() {
        return getProperty("port");
    }

    static RedisService redis;
    static RedisServer redisServer;

    @BeforeClass
    static public void startRedis() throws Exception {
        if (getHost() == null && getPort() == null) {
            redisServer = new RedisServer("2.8.9", 6379);
            redisServer.start();
        }
    }

    @AfterClass
    static public void stopRedis() throws Exception {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Before
    public void before() throws Exception {
        super.setUp();
        JsonObject config = new JsonObject();
        String host = getHost();
        String port = getPort();
        if (host != null) {
            config.putString("host", host);
        }
        if (port != null) {
            config.putNumber("port", Integer.parseInt(port));
        }
        redis = RedisService.create(vertx, config);
        CountDownLatch latch = new CountDownLatch(1);
        redis.start(asyncResult -> {
            if (asyncResult.succeeded()) {
                latch.countDown();
            } else {
                throw new RuntimeException("failed to setup", asyncResult.cause());
            }
        });
        awaitLatch(latch);
    }

    @After
    public void after() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        redis.stop(asyncResult -> {
            if (asyncResult.succeeded()) {
                latch.countDown();
            } else {
                throw new RuntimeException("failed to setup", asyncResult.cause());
            }
        });
        awaitLatch(latch);
        super.tearDown();
    }

    private static JsonArray j(final Object... params) {
        JsonArray parameters = null;

        if (params != null) {
            parameters = new JsonArray();
            for (Object o : params) {
                parameters.add(o);
            }
        }

        return parameters;
    }

    private static Object[] a(final Object... params) {
        return params;
    }

    private static String makeKey() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void testAppend() {
        final String key = makeKey();

        redis.del(j(key), reply0 -> {
            assertTrue(reply0.succeeded());

            redis.append(j(key, "Hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(5l, reply1.result().longValue());

                redis.append(j(key, " World"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(11l, reply2.result().longValue());

                    redis.get(j(key), reply3 -> {
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
    @Ignore
    public void testAuth() {
    }

    @Test
    @Ignore
    public void testBgrewriteaof() {
    }

    @Test
    @Ignore
    public void testBgsave() {
    }

    @Test
    public void testBitcount() {
        final String key = makeKey();

        redis.set(j(key, "foobar"), reply0 -> {
            assertTrue(reply0.succeeded());

            redis.bitcount(j(key), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(26, reply1.result().longValue());

                redis.bitcount(j(key, 0, 0), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(4, reply2.result().longValue());

                    redis.bitcount(j(key, 1, 1), reply3 -> {
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

        redis.set(j(key1, "foobar"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.set(j(key2, "abcdef"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.bitop(j("and", destkey, key1, key2), reply2 -> {
                    assertTrue(reply2.succeeded());
                    redis.get(j(destkey), reply3 -> {
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

        redis.del(j(list1, list2), reply0 -> {
            assertTrue(reply0.succeeded());

            redis.rpush(j(list1, "a", "b", "c"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(3, reply1.result().longValue());

                redis.blpop(j(list1, list2, 0), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a(list1, "a"), reply2.result().toArray());
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

        redis.del(j(list1, list2), reply0 -> {
            assertTrue(reply0.succeeded());

            redis.rpush(j(list1, "a", "b", "c"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(3, reply1.result().longValue());

                redis.brpop(j(list1, list2, 0), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a(list1, "c"), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testBrpoplpush() {
    }

    @Test
    @Ignore
    public void testClientKill() {
    }

    @Test
    @Ignore
    public void testClientList() {
    }

    @Test
    @Ignore
    public void testClientGetname() {
    }

    @Test
    @Ignore
    public void testClientSetname() {
    }

    @Test
    public void testConfigGet() {
        redis.configGet(j("*max-*-entries*"), reply0 -> {
            // 1) "hash-max-zipmap-entries"
            // 2) "512"
            // 3) "list-max-ziplist-entries"
            // 4) "512"
            // 5) "set-max-intset-entries"
            // 6) "512"
            testComplete();
        });
        await();
    }

    @Test
    @Ignore
    public void testConfigSet() {
    }

    @Test
    @Ignore
    public void testConfigResetstat() {
    }

    @Test
    @Ignore
    public void testDbsize() {
    }

    @Test
    @Ignore
    public void testDebugObject() {
    }

    @Test
    @Ignore
    public void testDebugSegfault() {
    }

    @Test
    public void testDecr() {
        final String mykey = makeKey();

        redis.set(j(mykey, "10"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.decr(j(mykey), reply1 -> {
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

        redis.set(j(mykey, "10"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.decrby(j(mykey, 5), reply1 -> {
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

        redis.set(j(key1, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.set(j(key2, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.del(j(key1, key2, key3), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(2, reply2.result().longValue());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testDiscard() {
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
        redis.echo(j("Hello World!"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals("Hello World!", reply0.result());
            testComplete();
        });
        await();
    }

//    @Test
//    public void testEval() {
//        final String key1 = makeKey();
//        final String key2 = makeKey();
//
//        redis.eval(j("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", 2, key1, key2, "first", "second"), reply0 -> { assertTrue(reply0.succeeded());
//            assertArrayEquals(a(key1, key2, "first", "second"), reply0.result().toArray());
//            testComplete();
//        });
//        await();
//    }

    @Test
    @Ignore
    public void testEvalsha() {
    }

    @Test
    @Ignore
    public void testExec() {
    }

    @Test
    public void testExists() {
        final String key1 = makeKey();
        final String key2 = makeKey();

        redis.set(j(key1, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.exists(j(key1), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.exists(j(key2), reply2 -> {
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

        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.expire(j(mykey, 10), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.ttl(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(10, reply2.result().longValue());

                    redis.set(j(mykey, "Hello World"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        redis.ttl(j(mykey), reply4 -> {
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

        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.exists(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.expireat(j(mykey, 1293840000), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());

                    redis.exists(j(mykey), reply3 -> {
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
    @Ignore
    public void testFlushall() {
    }

    @Test
    @Ignore
    public void testFlushdb() {
    }

    @Test
    public void testGet() {
        final String nonexisting = makeKey();
        final String mykey = makeKey();

        redis.get(j(nonexisting), reply0 -> {
            assertTrue(reply0.succeeded());
            assertNull(reply0.result());

            redis.set(j(mykey, "Hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.get(j(mykey), reply2 -> {
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

        redis.setbit(j(mykey, 7, 1), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(0, reply0.result().longValue());

            redis.getbit(j(mykey, 0), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(0, reply1.result().longValue());

                redis.getbit(j(mykey, 7), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());

                    redis.getbit(j(mykey, 100), reply3 -> {
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

        redis.set(j(mykey, "This is a string"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.getrange(j(mykey, 0, 3), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("This", reply1.result());

                redis.getrange(j(mykey, -3, -1), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals("ing", reply2.result());

                    redis.getrange(j(mykey, 0, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals("This is a string", reply3.result());

                        redis.getrange(j(mykey, 10, 100), reply4 -> {
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

        redis.incr(j(mycounter), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.getset(j(mycounter, "0"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("1", reply1.result());

                redis.get(j(mycounter), reply2 -> {
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

        redis.hset(j(myhash, "field1", "foo"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hdel(j(myhash, "field1"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hdel(j(myhash, "field2"), reply2 -> {
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

        redis.hset(j(myhash, "field1", "foo"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hexists(j(myhash, "field1"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hexists(j(myhash, "field2"), reply2 -> {
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

        redis.hset(j(myhash, "field1", "foo"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hget(j(myhash, "field1"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("foo", reply1.result());

                redis.hget(j(myhash, "field2"), reply2 -> {
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

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hset(j(myhash, "field2", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hgetall(j(myhash), reply2 -> {
                    assertTrue(reply2.succeeded());
                    JsonObject obj = reply2.result();
                    assertEquals("Hello", obj.getField("field1"));
                    assertEquals("World", obj.getField("field2"));
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    public void testHincrby() {
        final String myhash = makeKey();

        redis.hset(j(myhash, "field", 5), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hincrby(j(myhash, "field", 1), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(6, reply1.result().longValue());

                redis.hincrby(j(myhash, "field", -1), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(5, reply2.result().longValue());

                    redis.hincrby(j(myhash, "field", -10), reply3 -> {
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

        redis.hset(j(mykey, "field", 10.50), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hincrbyfloat(j(mykey, "field", 0.1), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("10.6", reply1.result());

                redis.hset(j(mykey, "field", 5.0e3), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(0, reply2.result().longValue());

                    redis.hincrbyfloat(j(mykey, "field", 2.0e2), reply3 -> {
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

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hset(j(myhash, "field2", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hkeys(j(myhash), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("field1", "field2"), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    public void testHlen() {
        final String myhash = makeKey();

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hset(j(myhash, "field2", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hlen(j(myhash), reply2 -> {
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

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hset(j(myhash, "field2", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hmget(j(myhash, "field1", "field2", "nofield"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("Hello", "World", null), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    public void testHmset() {
        final String myhash = makeKey();

        redis.hmset(j(myhash, "field1", "Hello", "field2", "World"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.hget(j(myhash, "field1"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("Hello", reply1.result());
                redis.hget(j(myhash, "field2"), reply2 -> {
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

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hget(j(myhash, "field1"), reply1 -> {
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

        redis.hsetnx(j(myhash, "field", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hsetnx(j(myhash, "field", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(0, reply1.result().longValue());

                redis.hget(j(myhash, "field"), reply2 -> {
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

        redis.hset(j(myhash, "field1", "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.hset(j(myhash, "field2", "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());

                redis.hvals(j(myhash), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("Hello", "World"), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    public void testIncr() {
        final String mykey = makeKey();

        redis.set(j(mykey, "10"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.incr(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(11, reply1.result().longValue());

                redis.get(j(mykey), reply2 -> {
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

        redis.set(j(mykey, "10"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.incrby(j(mykey, 5), reply1 -> {
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

        redis.set(j(mykey, 10.50), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.incrbyfloat(j(mykey, 0.1), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("10.6", reply1.result());

                redis.set(j(mykey, 5.0e3), reply2 -> {
                    assertTrue(reply2.succeeded());
                    redis.incrbyfloat(j(mykey, 2.0e2), reply3 -> {
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
        redis.info(j(), reply0 -> {
            assertTrue(reply0.succeeded());
            assertNotNull(reply0.result());
            testComplete();
        });
        await();
    }

    @Test
    public void testKeys() {
        redis.mset(j("one", 1, "two", 2, "three", 3, "four", 4), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.keys(j("*o*"), reply1 -> {
                assertTrue(reply1.succeeded());
                JsonArray array = reply1.result();
                // this is because there are leftovers from previous tests
                assertTrue(3 <= array.size());

                redis.keys(j("t??"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    JsonArray array2 = reply2.result();
                    assertTrue(1 == array2.size());

                    redis.keys(j("*"), reply3 -> {
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

        redis.lpush(j(mykey, "World"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.lpush(j(mykey, "Hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());

                redis.lindex(j(mykey, 0), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals("Hello", reply2.result());

                    redis.lindex(j(mykey, -1), reply3 -> {
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

        redis.rpush(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());

            redis.rpush(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());

                redis.linsert(j(mykey, "before", "World", "There"), reply2 -> {
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
        redis.lpush(j(mykey, "World"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.lpush(j(mykey, "Hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.llen(j(mykey), reply2 -> {
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
        redis.rpush(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.lpop(j(mykey), reply3 -> {
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
    public void testLpush() {
        final String mykey = makeKey();
        redis.lpush(j(mykey, "world"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.lpush(j(mykey, "hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.lrange(j(mykey, 0, -1), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("hello", "world"), reply2.result().toArray());
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

        redis.lpush(j(mykey, "World"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.lpushx(j(mykey, "Hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.lpushx(j(myotherkey, "Hello"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(0, reply2.result().longValue());
                    redis.lrange(j(mykey, 0, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        JsonArray array3 = reply3.result();
                        assertTrue(2 == array3.size());

                        assertTrue("Hello".equals(array3.get(0)));
                        assertTrue("World".equals(array3.get(1)));
                        redis.lrange(j(myotherkey, 0, -1), reply4 -> {
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
        redis.rpush(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.lrange(j(mykey, 0, 0), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals("one", reply3.result().get(0));
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
        redis.rpush(j(mykey, "hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "hello"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "foo"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.rpush(j(mykey, "hello"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(4, reply3.result().longValue());
                        redis.lrem(j(mykey, -2, "hello"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals(2, reply4.result().longValue());
                            redis.lrange(j(mykey, 0, -1), reply5 -> {
                                assertTrue(reply5.succeeded());
                                assertArrayEquals(a("hello", "foo"), reply5.result().toArray());
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
        redis.rpush(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.lset(j(mykey, 0, "four"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        redis.lset(j(mykey, -2, "five"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            redis.lrange(j(mykey, 0, -1), reply5 -> {
                                assertTrue(reply5.succeeded());
                                assertArrayEquals(a("four", "five", "three"), reply5.result().toArray());
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
        redis.rpush(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.ltrim(j(mykey, 1, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        redis.lrange(j(mykey, 0, -1), reply5 -> {
                            assertTrue(reply5.succeeded());
                            assertArrayEquals(a("two", "three"), reply5.result().toArray());
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
        redis.set(j(mykey1, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.set(j(mykey2, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.mget(j(mykey1, mykey2, "nonexisting"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("Hello", "World", null), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testMigrate() {
    }

    @Test
    @Ignore
    public void testMonitor() {
    }

    @Test
    @Ignore
    public void testMove() {
    }

    @Test
    public void testMset() {
        final String mykey1 = makeKey();
        final String mykey2 = makeKey();
        redis.mset(j(mykey1, "Hello", mykey2, "World"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.get(j(mykey1), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("Hello", reply1.result());
                redis.get(j(mykey2), reply2 -> {
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

        redis.msetnx(j(mykey1, "Hello", mykey2, "there"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.msetnx(j(mykey2, "there", mykey3, "world"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(0, reply1.result().longValue());
                redis.mget(j(mykey1, mykey2, mykey3), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("Hello", "there", null), reply2.result().toArray());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testMulti() {
    }

    @Test
    @Ignore
    public void testObject() {
    }

    @Test
    public void testPersist() {
        final String mykey = makeKey();
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.expire(j(mykey, 10), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.ttl(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(10, reply2.result().longValue());
                    redis.persist(j(mykey), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.ttl(j(mykey), reply4 -> {
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
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.pexpire(j(mykey, 1500), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.ttl(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.pttl(j(mykey), reply3 -> {
                        assertTrue(reply3.succeeded());
                        long val = reply3.result();
                        assertTrue(1500 > val && val > 0);
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testPexpireat() {
        final String mykey = makeKey();
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.pexpireat(j(mykey, 1555555555005l), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.ttl(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertTrue(200000000 > reply2.result() && reply2.result() > 0);
                    redis.pttl(j(mykey), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertTrue(1555555555005l > reply3.result() && reply3.result() > 0);
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
        redis.psetex(j(mykey, 2000, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.pttl(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertTrue(3000 > reply1.result() && reply1.result() > 0);
                redis.get(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals("Hello", reply2.result());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testPsubscribe() {
    }

    @Test
    public void testPttl() {
        final String mykey = makeKey();
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.expire(j(mykey, 3), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.pttl(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertTrue(3000 > reply2.result() && reply2.result() > 0);
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testPublish() {
    }

    @Test
    @Ignore
    public void testPunsubscribe() {
    }

    @Test
    @Ignore
    public void testQuit() {
    }

    @Test
    @Ignore
    public void testRandomkey() {
    }

    @Test
    public void testRename() {
        final String mykey = makeKey();
        final String myotherkey = makeKey();

        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.rename(j(mykey, myotherkey), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.get(j(myotherkey), reply2 -> {
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

        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.set(j(myotherkey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.renamenx(j(mykey, myotherkey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(0, reply2.result().longValue());
                    redis.get(j(myotherkey), reply3 -> {
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
        redis.rpush(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpush(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(3, reply2.result().longValue());
                    redis.rpop(j(mykey), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals("three", reply3.result());
                        redis.lrange(j(mykey, 0, -1), reply5 -> {
                            assertTrue(reply5.succeeded());
                            assertArrayEquals(a("one", "two"), reply5.result().toArray());
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
        redis.rpush(j(mykey, "one"), reply0 -> {
                    assertTrue(reply0.succeeded());
                    assertEquals(1, reply0.result().longValue());
                    redis.rpush(j(mykey, "two"), reply1 -> {
                        assertTrue(reply1.succeeded());
                        assertEquals(2, reply1.result().longValue());
                        redis.rpush(j(mykey, "three"), reply2 -> {
                            assertTrue(reply2.succeeded());
                            assertEquals(3, reply2.result().longValue());
                            redis.rpoplpush(j(mykey, myotherkey), reply3 -> {
                                assertTrue(reply3.succeeded());
                                assertEquals("three", reply3.result());
                                redis.lrange(j(mykey, 0, -1), reply5 -> {
                                    assertTrue(reply5.succeeded());
                                    assertArrayEquals(a("one", "two"), reply5.result().toArray());
                                    redis.lrange(j(myotherkey, 0, -1), reply6 -> {
                                        assertArrayEquals(a("three"), reply6.result().toArray());
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
        redis.rpush(j(mykey, "hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpush(j(mykey, "world"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.lrange(j(mykey, 0, -1), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertArrayEquals(a("hello", "world"), reply2.result().toArray());
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
        redis.rpush(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.rpushx(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(2, reply1.result().longValue());
                redis.rpushx(j(myotherkey, "World"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(0, reply2.result().longValue());
                    redis.lrange(j(mykey, 0, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertArrayEquals(a("Hello", "World"), reply3.result().toArray());
                        redis.lrange(j(myotherkey, 0, -1), reply4 -> {
                            assertArrayEquals(new Object[0], reply4.result().toArray());
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
        redis.sadd(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(mykey, "World"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(0, reply2.result().longValue());
                    redis.smembers(j(mykey), reply3 -> {
                        assertTrue(reply3.succeeded());
                        Object[] expected = new Object[]{"Hello", "World"};
                        Object[] result = reply3.result().toArray();
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
    @Ignore
    public void testSave() {
    }

    @Test
    public void testScard() {
        final String mykey = makeKey();
        redis.sadd(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.scard(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(2, reply2.result().longValue());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testScriptexists() {
    }

    @Test
    @Ignore
    public void testScriptflush() {
    }

    @Test
    @Ignore
    public void testScriptkill() {
    }

    @Test
    @Ignore
    public void testScriptload() {
    }

    @Test
    public void testSdiff() {
        final String mykey1 = makeKey();
        final String mykey2 = makeKey();

        redis.sadd(j(mykey1, "a"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey1, "b"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(mykey1, "c"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.sadd(j(mykey2, "c"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.sadd(j(mykey2, "d"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals(1, reply4.result().longValue());
                            redis.sadd(j(mykey2, "e"), reply5 -> {
                                assertTrue(reply5.succeeded());
                                assertEquals(1, reply5.result().longValue());
                                redis.sdiff(j(mykey1, mykey2), reply6 -> {
                                    assertTrue(reply6.succeeded());
                                    Object[] expected = new Object[]{"a", "b"};
                                    Object[] result = reply6.result().toArray();
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
    @Ignore
    public void testSdiffstore() {
    }

    @Test
    @Ignore
    public void testSelect() {
    }

    @Test
    public void testSet() {
        final String mykey = makeKey();
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.get(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals("Hello", reply1.result());
                testComplete();
            });
        });
        await();
    }

    @Test
    public void testSetbit() {
        final String mykey = makeKey();
        redis.setbit(j(mykey, 7, 1), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(0, reply0.result().longValue());
            redis.setbit(j(mykey, 7, 0), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.get(j(mykey), reply2 -> {
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
        redis.setex(j(mykey, 10, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.ttl(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(10, reply1.result().longValue());
                redis.get(j(mykey), reply2 -> {
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
        redis.setnx(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.setnx(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(0, reply1.result().longValue());
                redis.get(j(mykey), reply2 -> {
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
        redis.set(j(mykey, "Hello World"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.setrange(j(mykey, 6, "Redis"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(11, reply1.result().longValue());
                redis.get(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals("Hello Redis", reply2.result());
                    testComplete();
                });
            });
        });
        await();
    }

    @Test
    @Ignore
    public void testShutdown() {
    }

    @Test
    public void testSinter() {
        final String mykey1 = makeKey();
        final String mykey2 = makeKey();

        redis.sadd(j(mykey1, "a"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey1, "b"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(mykey1, "c"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.sadd(j(mykey2, "c"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.sadd(j(mykey2, "d"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals(1, reply4.result().longValue());
                            redis.sadd(j(mykey2, "e"), reply5 -> {
                                assertTrue(reply5.succeeded());
                                assertEquals(1, reply5.result().longValue());
                                redis.sinter(j(mykey1, mykey2), reply6 -> {
                                    assertTrue(reply6.succeeded());
                                    assertArrayEquals(new Object[]{"c"}, reply6.result().toArray());
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
    @Ignore
    public void testSinterstore() {
    }

    @Test
    public void testSismember() {
        final String mykey = makeKey();
        redis.sadd(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.sismember(j(mykey, "one"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sismember(j(mykey, "two"), reply2 -> {
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
        redis.sadd(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "World"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.smembers(j(mykey), reply2 -> {
                    assertTrue(reply2.succeeded());
                    Object[] expected = new Object[]{"Hello", "World"};
                    Object[] result = reply2.result().toArray();
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
        redis.sadd(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(myotherkey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.smove(j(mykey, myotherkey, "two"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.smembers(j(mykey), reply4 -> {
                            assertTrue(reply4.succeeded());
                            Object[] expected = new Object[]{"one"};
                            Object[] result = reply4.result().toArray();
                            Arrays.sort(result);
                            assertArrayEquals(expected, result);
                            redis.smembers(j(myotherkey), reply5 -> {
                                assertTrue(reply5.succeeded());
                                Object[] expected1 = new Object[]{"three", "two"};
                                Object[] result1 = reply5.result().toArray();
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

        redis.sadd(j(mykey, "1", "2", "3"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(3, reply0.result().longValue());
            redis.set(j(k1, "one"), reply1 -> {
                assertTrue(reply1.succeeded());
                redis.set(j(k2, "two"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    redis.set(j(k3, "three"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        redis.sort(j(mykey, "desc", "get", kx), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertArrayEquals(a("three", "two", "one"), reply4.result().toArray());
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
        redis.sadd(j(mykey, "one"), reply0 -> { assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "two"), reply1 -> { assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(mykey, "three"), reply2 -> { assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.spop(j(mykey), reply3 -> { assertTrue(reply3.succeeded());
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
                        redis.smembers(j(mykey), reply4 -> { assertTrue(reply4.succeeded());
                            Object[] expectedA = expected.toArray();
                            Arrays.sort(expectedA);
                            Object[] res = reply4.result().toArray();
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

//    @Test
//    public void testSrandmember() {
//        final String mykey = makeKey();
//        redis.sadd(j(mykey, "one", "two", "three"), reply0 -> {
//            assertTrue(reply0.succeeded());
//            assertEquals(3, reply0.result().longValue());
//            redis.srandmember(j(mykey), reply1 -> {
//                assertTrue(reply1.succeeded());
//                String randmember = reply1.result().get(0);
//                assertTrue(randmember.equals("one") || randmember.equals("two") || randmember.equals("three"));
//                testComplete();
//            });
//        });
//        await();
//    }

    @Test
    public void testSrem() {
        final String mykey = makeKey();
        redis.sadd(j(mykey, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(mykey, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.srem(j(mykey, "one"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.srem(j(mykey, "four"), reply4 -> {
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
        redis.set(j(mykey, "Hello world"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.strlen(j(mykey), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(11, reply1.result().longValue());
                redis.strlen(j("nonexisting"), reply2 -> {
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
    public void testSubscribe() {
    }

    @Test
    public void testSunion() {
        final String mykey1 = makeKey();
        final String mykey2 = makeKey();

        redis.sadd(j(mykey1, "a"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.sadd(j(mykey1, "b"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
            });
            redis.sadd(j(mykey1, "c"), reply2 -> {
                assertTrue(reply2.succeeded());
                assertEquals(1, reply2.result().longValue());
            });
            redis.sadd(j(mykey2, "c"), reply3 -> {
                assertTrue(reply3.succeeded());
                assertEquals(1, reply3.result().longValue());
            });
            redis.sadd(j(mykey2, "d"), reply4 -> {
                assertTrue(reply4.succeeded());
                assertEquals(1, reply4.result().longValue());
            });
            redis.sadd(j(mykey2, "e"), reply5 -> {
                assertTrue(reply5.succeeded());
                assertEquals(1, reply5.result().longValue());
                redis.sunion(j(mykey1, mykey2), reply6 -> {
                    assertTrue(reply6.succeeded());
                    JsonArray arr = reply6.result();
                    Object[] array = arr.toArray();
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
    @Ignore
    public void testSunionstore() {
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
        redis.set(j(mykey, "Hello"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.expire(j(mykey, 10), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.ttl(j(mykey), reply2 -> {
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

        redis.set(j(key1, "value"), reply0 -> {
            assertTrue(reply0.succeeded());
            redis.lpush(j(key2, "value"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.sadd(j(key3, "value"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.type(j(key1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals("string", reply3.result());
                        redis.type(j(key2), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals("list", reply4.result());
                            redis.type(j(key3), reply5 -> {
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
    @Ignore
    public void testUnsubscribe() {
    }

    @Test
    @Ignore
    public void testUnwatch() {
    }

    @Test
    @Ignore
    public void testWatch() {
    }

    @Test
    public void testZadd() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 1, "uno"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 2, "two"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zadd(j(key, 3, "two"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(0, reply3.result().longValue());
                        redis.zrange(j(key, 0, -1, "withscores"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertArrayEquals(a("one", "1", "uno", "1", "two", "3"), reply4.result().toArray());
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zcard(j(key), reply2 -> {
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zcount(j(key, "-inf", "+inf"), reply3 -> {
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zincrby(j(key, 2, "one"), reply2 -> {
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

        redis.zadd(j(key1, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key1, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key2, 1, "one"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zadd(j(key2, 2, "two"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.zadd(j(key2, 3, "three"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals(1, reply4.result().longValue());
                            redis.zinterstore(j(key3, 2, key1, key2, "weights", 2, 3), reply5 -> {
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrange(j(key, 0, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertArrayEquals(a("one", "two", "three"), reply3.result().toArray());
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrangebyscore(j(key, "-inf", "+inf"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertArrayEquals(a("one", "two", "three"), reply3.result().toArray());
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
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrank(j(key, "three"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(2, reply3.result().longValue());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZrem() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrem(j(key, "two"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZremrangebyrank() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zremrangebyrank(j(key, 0, 1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(2, reply3.result().longValue());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZremrangebyscore() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zremrangebyscore(j(key, "-inf", "(2"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZrevrange() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrevrange(j(key, 0, -1), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertArrayEquals(a("three", "two", "one"), reply3.result().toArray());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZrevrangebyscore() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrevrangebyscore(j(key, "+inf", "-inf"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertArrayEquals(a("three", "two", "one"), reply3.result().toArray());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZrevrank() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key, 3, "three"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zrevrank(j(key, "one"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(2, reply3.result().longValue());
                        testComplete();
                    });
                });
            });
        });
        await();
    }

    @Test
    public void testZscore() {
        final String key = makeKey();
        redis.zadd(j(key, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zscore(j(key, "one"), reply1 -> {
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

        redis.zadd(j(key1, 1, "one"), reply0 -> {
            assertTrue(reply0.succeeded());
            assertEquals(1, reply0.result().longValue());
            redis.zadd(j(key1, 2, "two"), reply1 -> {
                assertTrue(reply1.succeeded());
                assertEquals(1, reply1.result().longValue());
                redis.zadd(j(key2, 1, "one"), reply2 -> {
                    assertTrue(reply2.succeeded());
                    assertEquals(1, reply2.result().longValue());
                    redis.zadd(j(key2, 2, "two"), reply3 -> {
                        assertTrue(reply3.succeeded());
                        assertEquals(1, reply3.result().longValue());
                        redis.zadd(j(key2, 3, "three"), reply4 -> {
                            assertTrue(reply4.succeeded());
                            assertEquals(1, reply4.result().longValue());
                            redis.zunionstore(j(key3, 2, key1, key2, "weights", 2, 3), reply5 -> {
                                assertTrue(reply5.succeeded());
                                assertEquals(3, reply5.result().longValue());
                                testComplete();
                            });
                        });
                    });
                });
            });
        });
        await();
    }
}