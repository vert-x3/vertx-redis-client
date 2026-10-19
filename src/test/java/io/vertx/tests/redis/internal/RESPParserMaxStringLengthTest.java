package io.vertx.tests.redis.internal;

import io.vertx.core.buffer.Buffer;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.ParserHandler;
import io.vertx.redis.client.impl.RESPParser;
import io.vertx.redis.client.impl.RESPParserOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class RESPParserMaxStringLengthTest {
  @RegisterExtension
  RunTestOnContext context = new RunTestOnContext();

  private static final long MAX_STRING_LENGTH = 10;

  private static ParserHandler expectFail(VertxTestContext test, String messageFragment) {
    return new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> assertTrue(t.getMessage().contains(messageFragment), "unexpected message: " + t.getMessage()));
        test.completeNow();
      }
    };
  }

  private static ParserHandler expectResponse(VertxTestContext test, Consumer<Response> assertion) {
    return new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> assertion.accept(response));
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    };
  }

  private static RESPParser newParser(ParserHandler handler) {
    return new RESPParser(handler, new RESPParserOptions().setMaxStringLength(MAX_STRING_LENGTH));
  }

  @Test
  public void testBulkStringExceedsMaxStringLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "Redis Bulk cannot be larger than"));
    parser.handle(Buffer.buffer("$11\r\n"));
  }

  @Test
  public void testVerbatimStringExceedsMaxStringLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "Redis Bulk cannot be larger than"));
    parser.handle(Buffer.buffer("=11\r\n"));
  }

  @Test
  public void testBulkStringWithinLimitParses(VertxTestContext test) {
    RESPParser parser = newParser(expectResponse(test, r -> assertEquals("1234567890", r.toString())));
    parser.handle(Buffer.buffer("$10\r\n1234567890\r\n"));
  }
}
