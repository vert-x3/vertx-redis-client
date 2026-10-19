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
public class RESPParserMaxLineLengthTest {
  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  private static final int MAX_LINE_LENGTH = 10;

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
    return new RESPParser(handler, new RESPParserOptions().setMaxLineLength(MAX_LINE_LENGTH));
  }

  @Test
  public void testUnterminatedSimpleStringExceedsMax(TestContext test) {
    // 11 bytes after the type byte, no CRLF -> exceeds the limit of 10
    RESPParser parser = newParser(expectFail(test, "maximum length"));
    parser.handle(Buffer.buffer("+12345678901"));
  }

  @Test
  public void testUnterminatedBulkHeaderExceedsMax(TestContext test) {
    RESPParser parser = newParser(expectFail(test, "maximum length"));
    parser.handle(Buffer.buffer("$12345678901"));
  }

  @Test
  public void testLineWithinLimitParses(TestContext test) {
    RESPParser parser = newParser(expectResponse(test, r -> test.assertEquals("1234567890", r.toString())));
    parser.handle(Buffer.buffer("+1234567890\r\n"));
  }

  @Test
  public void testTerminatedLineLongerThanCapStillParses(TestContext test) {
    // the cap bounds unterminated accumulation; a terminated line is a complete message
    // even if it's longer than the limit
    RESPParser parser = newParser(expectResponse(test, r -> test.assertEquals("12345678901234567890", r.toString())));
    parser.handle(Buffer.buffer("+12345678901234567890\r\n"));
  }
}
