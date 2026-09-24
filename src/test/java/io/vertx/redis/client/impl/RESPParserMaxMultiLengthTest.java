package io.vertx.redis.client.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RESPParserMaxMultiLengthTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private static final int MAX_MULTI_LENGTH = 10;

  @Test(timeout = 30_000)
  public void testArrayExceedsMaxMultiLength(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        should.assertTrue(t.getMessage().contains("maxMultiLength"));
        test.complete();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("*11\r\n"));
  }

  @Test(timeout = 30_000)
  public void testMapExceedsMaxMultiLength(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        should.assertTrue(t.getMessage().contains("maxMultiLength"));
        test.complete();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("%11\r\n"));
  }

  @Test(timeout = 30_000)
  public void testSetExceedsMaxMultiLength(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        should.assertTrue(t.getMessage().contains("maxMultiLength"));
        test.complete();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("~11\r\n"));
  }

  @Test(timeout = 30_000)
  public void testPushExceedsMaxMultiLength(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        should.assertTrue(t.getMessage().contains("maxMultiLength"));
        test.complete();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer(">11\r\n"));
  }

  @Test(timeout = 30_000)
  public void testAttributeExceedsMaxMultiLength(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        should.assertTrue(t.getMessage().contains("maxMultiLength"));
        test.complete();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("|11\r\n"));
  }

  @Test(timeout = 30_000)
  public void testArrayWithinLimit(TestContext should) {
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
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test(timeout = 30_000)
  public void testArrayAtExactLimit(TestContext should) {
    final Async test = should.async();

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        should.assertEquals(10, response.size());
        for (int i = 0; i < 10; i++) {
          should.assertEquals(i + 1, response.get(i).toInteger());
        }
        test.complete();
      }

      @Override
      public void fail(Throwable t) {
        should.fail(t);
      }
    }, 16, MAX_MULTI_LENGTH);

    StringBuilder sb = new StringBuilder("*10\r\n");
    for (int i = 1; i <= 10; i++) {
      sb.append(":").append(i).append("\r\n");
    }
    parser.handle(Buffer.buffer(sb.toString()));
  }
}
