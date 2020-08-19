package io.vertx.redis.client.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.BulkType;
import io.vertx.redis.client.impl.types.MultiType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(VertxUnitRunner.class)
public class ReplyParserTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test(timeout = 30_000)
  public void testParseSimple(TestContext should) {
    final Async test = should.async();
    final AtomicInteger counter = new AtomicInteger();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        System.out.println(response.toString());
        if (counter.incrementAndGet() == 4) {
          test.complete();
        }
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("+PONG\r\n+PONG\r\n+PONG\r\n+PONG\r\n"));
  }

  @Test(timeout = 30_000)
  public void testParseSimpleInChunks(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        System.out.println(response.toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("+"));
    parser.handle(Buffer.buffer("P"));
    parser.handle(Buffer.buffer("O"));
    parser.handle(Buffer.buffer("N"));
    parser.handle(Buffer.buffer("G"));
    parser.handle(Buffer.buffer("\r"));
    parser.handle(Buffer.buffer("\n"));
  }

  @Test(timeout = 30_000)
  public void testIntegerType(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(1000L, response.toLong());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer(":1000\r\n"));
  }

  @Test(timeout = 30_000)
  public void testNegIntegerType(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(-1L, response.toLong());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer(":-1\r\n"));
  }

  @Test(timeout = 30_000)
  public void testBulk(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals("foobar", response.toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$6\r\nfoobar\r\n"));
  }

  @Test(timeout = 30_000)
  public void testEmptyBulk(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals("", response.toString());
        should.assertTrue(response == BulkType.EMPTY);
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$0\r\n\r\n"));
  }

  @Test(timeout = 30_000)
  public void testNullBulk(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertTrue(response == null);
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$-1\r\n"));
  }

  @Test(timeout = 30_000)
  public void testEmptyMulti(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(0, response.size());
        should.assertTrue(response == MultiType.EMPTY_MULTI);
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*0\r\n"));
  }

  @Test(timeout = 30_000)
  public void testNullMulti(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertTrue(response == null);
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*-1\r\n"));
  }

  @Test(timeout = 30_000)
  public void testMulti(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(2, response.size());
        should.assertEquals("foo", response.get(0).toString());
        should.assertEquals("bar", response.get(1).toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test(timeout = 30_000)
  public void testMultiOfInteger(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(3, response.size());
        should.assertEquals(1, response.get(0).toInteger());
        should.assertEquals(2, response.get(1).toInteger());
        should.assertEquals(3, response.get(2).toInteger());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*3\r\n:1\r\n:2\r\n:3\r\n"));
  }

  @Test(timeout = 30_000)
  public void testMultiOfMixed(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(5, response.size());
        should.assertEquals(1, response.get(0).toInteger());
        should.assertEquals(2, response.get(1).toInteger());
        should.assertEquals(3, response.get(2).toInteger());
        should.assertEquals(4, response.get(3).toInteger());
        should.assertEquals("foobar", response.get(4).toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n"));
  }

  @Test(timeout = 30_000)
  public void testMultiOfMulti(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(2, response.size());
        should.assertEquals(1, response.get(0).get(0).toInteger());
        should.assertEquals(2, response.get(0).get(1).toInteger());
        should.assertEquals(3, response.get(0).get(2).toInteger());
        should.assertEquals("Foo", response.get(1).get(0).toString());
        should.assertEquals("Bar", response.get(1).get(1).toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n-Bar\r\n"));
  }

  @Test//(timeout = 30_000)
  public void testMultiWithNull(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(3, response.size());
        should.assertEquals("foo", response.get(0).toString());
        should.assertTrue(response.get(1) == null);
        should.assertEquals("bar", response.get(2).toString());
        test.complete();
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*3\r\n$3\r\nfoo\r\n$-1\r\n$3\r\nbar\r\n"));
  }

  @Test(timeout = 30_000)
  public void parseLargePayload(TestContext should) {
    final Async test = should.async();

    final AtomicInteger counter = new AtomicInteger();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        System.out.println(response);
        if (counter.incrementAndGet() == 17) {
          test.complete();
        }
      }

      @Override
      public void fatal(Throwable t) {
        should.fail(t);
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16);

    parser.handle(Buffer.buffer(
      "+OK\r\n" +
      "+OK\r\n" +
      "$1\r\n" +
      "1\r\n" +
      "+OK\r\n" +
      "+OK\r\n" +
      "+OK\r\n" +
      "$5\r\n" +
      "91600\r\n" +
      "$6\r\n" +
      "104099\r\n" +
      "+OK\r\n" +
      "$6\r\n" +
      "104099\r\n" +
      "$6\r\n" +
      "104099\r\n" +
      "+OK\r\n" +
      "+OK\r\n" +
      "+OK\r\n" +
      "$5\r\n" +
      "77347\r\n" +
      "$5\r\n" +
      "91600\r\n" +
      "$5\r\n" +
      "91600\r\n"));
  }
}
