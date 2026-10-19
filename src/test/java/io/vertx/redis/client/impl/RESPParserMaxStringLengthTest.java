package io.vertx.redis.client.impl;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Consumer;

@RunWith(VertxUnitRunner.class)
public class RESPParserMaxStringLengthTest {
  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private static final long MAX_STRING_LENGTH = 10;

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
    return new RESPParser(handler, new RESPParserOptions().setMaxStringLength(MAX_STRING_LENGTH));
  }

  @Test
  public void testBulkStringExceedsMaxStringLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "Redis Bulk cannot be larger than"));
    parser.handle(Buffer.buffer("$11\r\n"));
  }

  @Test
  public void testVerbatimStringExceedsMaxStringLength(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "Redis Bulk cannot be larger than"));
    parser.handle(Buffer.buffer("=11\r\n"));
  }

  @Test
  public void testBulkStringWithinLimitParses(TestContext test) {
    RESPParser parser = newParser(expectResponse(test, r -> test.assertEquals("1234567890", r.toString())));
    parser.handle(Buffer.buffer("$10\r\n1234567890\r\n"));
  }


  @Test
  public void testBulkLargerThanMaxMultiCountParses(TestContext test) {
    // `maxMultiCount` is an element count and must NOT cap a bulk byte length.
    // A 100-byte bulk far exceeds `maxMultiCount=10` yet is a perfectly valid value.
    RESPParser parser = new RESPParser(expectResponse(test, r -> {
      test.assertEquals(ResponseType.BULK, r.type());
      test.assertEquals(100, r.toString().length());
    }), new RESPParserOptions().setMaxMultiCount(10));
    StringBuilder msg = new StringBuilder("$100\r\n");
    for (int i = 0; i < 100; i++) {
      msg.append("x");
    }
    msg.append("\r\n");
    parser.handle(Buffer.buffer(msg.toString()));
  }

  @Test
  public void testNullBulkIgnoresMaxMultiCount(TestContext test) {
    // the -1 null sentinel is shared with arrays but must work regardless of `maxMultiCount`
    RESPParser parser = new RESPParser(expectResponse(test, test::assertNull),
      new RESPParserOptions().setMaxMultiCount(10));
    parser.handle(Buffer.buffer("$-1\r\n"));
  }
}
