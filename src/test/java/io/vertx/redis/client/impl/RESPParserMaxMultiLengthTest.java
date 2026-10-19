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

import java.util.function.Consumer;

@RunWith(VertxUnitRunner.class)
public class RESPParserMaxMultiLengthTest {
  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private static final int MAX_MULTI_COUNT = 10;

  private static ParserHandler expectFail(TestContext test, String messageFragment) {
    final Async async = test.async();

    return new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.fail("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.assertTrue(t.getMessage().contains(messageFragment), "unexpected message: " + t.getMessage());
        async.complete();
      }
    };
  }

  private static ParserHandler expectResponse(TestContext test, Consumer<Response> assertion) {
    final Async async = test.async();

    return new ParserHandler() {
      @Override
      public void handle(Response response) {
        assertion.accept(response);
        async.complete();
      }

      @Override
      public void fail(Throwable t) {
        test.fail(t);
      }
    };
  }

  private static RESPParser newParser(ParserHandler handler) {
    return new RESPParser(handler, new RESPParserOptions().setMaxMultiCount(MAX_MULTI_COUNT));
  }

  @Test
  public void testArrayExceedsMaxMultiLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("*11\r\n"));
  }

  @Test
  public void testMapExceedsMaxMultiLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("%11\r\n"));
  }

  @Test
  public void testSetExceedsMaxMultiLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("~11\r\n"));
  }

  @Test
  public void testPushExceedsMaxMultiLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer(">11\r\n"));
  }

  @Test
  public void testAttributeExceedsMaxMultiLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("|11\r\n"));
  }

  @Test
  public void testArrayWithinLimit(TestContext test) {
    RESPParser parser = newParser(expectResponse(test, response -> {
      test.assertEquals(2, response.size());
      test.assertEquals("foo", response.get(0).toString());
      test.assertEquals("bar", response.get(1).toString());
    }));
    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test
  public void testArrayAtExactLimit(TestContext test) {
    RESPParser parser = newParser(expectResponse(test, response -> {
      test.assertEquals(10, response.size());
      for (int i = 0; i < 10; i++) {
        test.assertEquals(i + 1, response.get(i).toInteger());
      }
    }));

    StringBuilder sb = new StringBuilder("*10\r\n");
    for (int i = 1; i <= 10; i++) {
      sb.append(":").append(i).append("\r\n");
    }
    parser.handle(Buffer.buffer(sb.toString()));
  }
}
