package io.vertx.tests.redis.client;

import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.vertx.redis.client.Command.DELEX;
import static io.vertx.redis.client.Command.DIGEST;
import static io.vertx.redis.client.Command.GET;
import static io.vertx.redis.client.Command.HEXPIRE;
import static io.vertx.redis.client.Command.HGETDEL;
import static io.vertx.redis.client.Command.HGETEX;
import static io.vertx.redis.client.Command.HSET;
import static io.vertx.redis.client.Command.HSETEX;
import static io.vertx.redis.client.Command.HTTL;
import static io.vertx.redis.client.Command.MSETEX;
import static io.vertx.redis.client.Command.SET;
import static io.vertx.redis.client.Command.TTL;
import static io.vertx.redis.client.Command.VADD;
import static io.vertx.redis.client.Command.VCARD;
import static io.vertx.redis.client.Command.VSIM;
import static io.vertx.redis.client.Request.cmd;
import static io.vertx.tests.redis.client.TestUtils.randomKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClient8Test {

  @Container
  public static final RedisStandalone redis = RedisStandalone.builder().setVersion("8.6").build();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  @BeforeEach
  public void before(VertxTestContext test) {
    client = Redis.createClient(
      context.vertx(),
      new RedisOptions().setConnectionString(redis.getRedisUri()));

    client.connect().onComplete(test.succeeding(conn -> {
      conn.close().onComplete(test.succeedingThenComplete());
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void testBasicInterop(VertxTestContext test) {
    final String nonexisting = randomKey();
    final String mykey = randomKey();

    client.send(cmd(GET).arg(nonexisting))
      .compose(resp -> {
        assertNull(resp);
        return client.send(cmd(SET).arg(mykey).arg("Hello"));
      })
      .compose(resp -> {
        return client.send(cmd(GET).arg(mykey));
      })
      .onComplete(test.succeeding(resp -> {
        assertEquals("Hello", resp.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHashFieldExpiration(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(HSET).arg(key).arg("field1").arg("value1").arg("field2").arg("value2"))
      .compose(resp -> {
        assertEquals(2, resp.toInteger());
        return client.send(cmd(HEXPIRE).arg(key).arg(60).arg("FIELDS").arg(1).arg("field1"));
      })
      .compose(resp -> {
        assertEquals(1, resp.get(0).toInteger());
        return client.send(cmd(HTTL).arg(key).arg("FIELDS").arg(2).arg("field1").arg("field2"));
      })
      .onComplete(test.succeeding(resp -> {
        long ttl = resp.get(0).toLong();
        assertTrue(ttl > 0 && ttl <= 60, "Expected TTL in (0, 60], got " + ttl);
        assertEquals(-1, resp.get(1).toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHgetdel(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(HSET).arg(key).arg("field1").arg("value1").arg("field2").arg("value2"))
      .compose(resp -> {
        assertEquals(2, resp.toInteger());
        return client.send(cmd(HGETDEL).arg(key).arg("FIELDS").arg(1).arg("field1"));
      })
      .compose(resp -> {
        assertEquals("value1", resp.get(0).toString());
        return client.send(cmd(HGETDEL).arg(key).arg("FIELDS").arg(1).arg("field1"));
      })
      .onComplete(test.succeeding(resp -> {
        assertNull(resp.get(0));
        test.completeNow();
      }));
  }

  @Test
  public void testDelex(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(SET).arg(key).arg("hello"))
      .compose(resp -> {
        return client.send(cmd(DELEX).arg(key).arg("IFEQ").arg("wrong"));
      })
      .compose(resp -> {
        assertEquals(0, resp.toInteger());
        return client.send(cmd(DELEX).arg(key).arg("IFEQ").arg("hello"));
      })
      .compose(resp -> {
        assertEquals(1, resp.toInteger());
        return client.send(cmd(GET).arg(key));
      })
      .onComplete(test.succeeding(resp -> {
        assertNull(resp);
        test.completeNow();
      }));
  }

  @Test
  public void testDigest(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(SET).arg(key).arg("hello"))
      .compose(resp -> {
        return client.send(cmd(DIGEST).arg(key));
      })
      .compose(resp -> {
        assertNotNull(resp);
        return client.send(cmd(DIGEST).arg(key))
          .map(resp2 -> new Pair<>(resp.toString(), resp2.toString()));
      })
      .compose(pair -> {
        assertEquals(pair.a, pair.b);
        return client.send(cmd(SET).arg(key).arg("world"))
          .compose(resp -> client.send(cmd(DIGEST).arg(key)))
          .map(resp -> new Pair<>(pair.a, resp.toString()));
      })
      .onComplete(test.succeeding(pair -> {
        assertNotEquals(pair.a, pair.b);
        test.completeNow();
      }));
  }

  @Test
  public void testMsetex(VertxTestContext test) {
    final String key1 = randomKey();
    final String key2 = randomKey();

    client.send(cmd(MSETEX).arg(2).arg(key1).arg("value1").arg(key2).arg("value2").arg("EX").arg(60))
      .compose(resp -> {
        assertEquals(1, resp.toInteger());
        return client.send(cmd(GET).arg(key1));
      })
      .compose(resp -> {
        assertEquals("value1", resp.toString());
        return client.send(cmd(GET).arg(key2));
      })
      .compose(resp -> {
        assertEquals("value2", resp.toString());
        return client.send(cmd(TTL).arg(key1));
      })
      .compose(resp -> {
        long ttl = resp.toLong();
        assertTrue(ttl > 0 && ttl <= 60, "Expected TTL for key1 in (0, 60], got " + ttl);
        return client.send(cmd(TTL).arg(key2));
      })
      .onComplete(test.succeeding(resp -> {
        long ttl = resp.toLong();
        assertTrue(ttl > 0 && ttl <= 60, "Expected TTL for key2 in (0, 60], got " + ttl);
        test.completeNow();
      }));
  }

  @Test
  public void testSetIfeq(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(SET).arg(key).arg("hello"))
      .compose(resp -> {
        return client.send(cmd(SET).arg(key).arg("world").arg("IFEQ").arg("wrong"));
      })
      .compose(resp -> {
        assertNull(resp);
        return client.send(cmd(GET).arg(key));
      })
      .compose(resp -> {
        assertEquals("hello", resp.toString());
        return client.send(cmd(SET).arg(key).arg("world").arg("IFEQ").arg("hello"));
      })
      .compose(resp -> {
        assertEquals("OK", resp.toString());
        return client.send(cmd(GET).arg(key));
      })
      .onComplete(test.succeeding(resp -> {
        assertEquals("world", resp.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testSetIfne(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(SET).arg(key).arg("hello"))
      .compose(resp -> {
        return client.send(cmd(SET).arg(key).arg("world").arg("IFNE").arg("hello"));
      })
      .compose(resp -> {
        assertNull(resp);
        return client.send(cmd(GET).arg(key));
      })
      .compose(resp -> {
        assertEquals("hello", resp.toString());
        return client.send(cmd(SET).arg(key).arg("world").arg("IFNE").arg("other"));
      })
      .compose(resp -> {
        assertEquals("OK", resp.toString());
        return client.send(cmd(GET).arg(key));
      })
      .onComplete(test.succeeding(resp -> {
        assertEquals("world", resp.toString());
        test.completeNow();
      }));
  }

  @Test
  public void testHsetex(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(HSETEX).arg(key).arg("EX").arg(60).arg("FIELDS").arg(2)
        .arg("field1").arg("value1").arg("field2").arg("value2"))
      .compose(resp -> {
        return client.send(cmd(HGETEX).arg(key).arg("PERSIST").arg("FIELDS").arg(2)
          .arg("field1").arg("field2"));
      })
      .compose(resp -> {
        assertEquals("value1", resp.get(0).toString());
        assertEquals("value2", resp.get(1).toString());
        return client.send(cmd(HTTL).arg(key).arg("FIELDS").arg(1).arg("field1"));
      })
      .onComplete(test.succeeding(resp -> {
        assertEquals(-1, resp.get(0).toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testHgetex(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(HSET).arg(key).arg("field1").arg("value1").arg("field2").arg("value2"))
      .compose(resp -> {
        assertEquals(2, resp.toInteger());
        return client.send(cmd(HGETEX).arg(key).arg("EX").arg(60).arg("FIELDS").arg(1).arg("field1"));
      })
      .compose(resp -> {
        assertEquals("value1", resp.get(0).toString());
        return client.send(cmd(HTTL).arg(key).arg("FIELDS").arg(2).arg("field1").arg("field2"));
      })
      .onComplete(test.succeeding(resp -> {
        long ttl = resp.get(0).toLong();
        assertTrue(ttl > 0 && ttl <= 60, "Expected TTL for field1 in (0, 60], got " + ttl);
        assertEquals(-1, resp.get(1).toLong());
        test.completeNow();
      }));
  }

  @Test
  public void testVectorSet(VertxTestContext test) {
    final String key = randomKey();

    client.send(cmd(VADD).arg(key).arg("VALUES").arg(3).arg("1.0").arg("0.0").arg("0.0").arg("elem1"))
      .compose(resp -> {
        assertTrue(resp.toBoolean());
        return client.send(cmd(VADD).arg(key).arg("VALUES").arg(3).arg("0.0").arg("1.0").arg("0.0").arg("elem2"));
      })
      .compose(resp -> {
        assertTrue(resp.toBoolean());
        return client.send(cmd(VADD).arg(key).arg("VALUES").arg(3).arg("0.0").arg("0.0").arg("1.0").arg("elem3"));
      })
      .compose(resp -> {
        assertTrue(resp.toBoolean());
        return client.send(cmd(VCARD).arg(key));
      })
      .compose(resp -> {
        assertEquals(3, resp.toInteger());
        // find 2 most similar to [1,0,0]
        return client.send(cmd(VSIM).arg(key).arg("VALUES").arg(3).arg("1.0").arg("0.0").arg("0.0").arg("COUNT").arg(2));
      })
      .onComplete(test.succeeding(resp -> {
        // first result should be elem1 (exact match)
        assertEquals("elem1", resp.get(0).toString());
        assertEquals(2, resp.size());
        test.completeNow();
      }));
  }
}
