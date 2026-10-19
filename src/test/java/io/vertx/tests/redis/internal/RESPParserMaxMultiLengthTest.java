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
public class RESPParserMaxMultiLengthTest {

  @RegisterExtension
  RunTestOnContext context = new RunTestOnContext();

  private static final int MAX_MULTI_COUNT = 10;

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
    return new RESPParser(handler, new RESPParserOptions().setMaxMultiCount(MAX_MULTI_COUNT));
  }

  @Test
  public void testArrayExceedsMaxMultiLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("*11\r\n"));
  }

  @Test
  public void testMapExceedsMaxMultiLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("%11\r\n"));
  }

  @Test
  public void testSetExceedsMaxMultiLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("~11\r\n"));
  }

  @Test
  public void testPushExceedsMaxMultiLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer(">11\r\n"));
  }

  @Test
  public void testAttributeExceedsMaxMultiLength(VertxTestContext test) {
    RESPParser parser = newParser(expectFail(test, "exceeds the configured maximum"));
    parser.handle(Buffer.buffer("|11\r\n"));
  }

  @Test
  public void testArrayWithinLimit(VertxTestContext test) {
    RESPParser parser = newParser(expectResponse(test, response -> {
      assertEquals(2, response.size());
      assertEquals("foo", response.get(0).toString());
      assertEquals("bar", response.get(1).toString());
    }));
    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test
  public void testArrayAtExactLimit(VertxTestContext test) {
    RESPParser parser = newParser(expectResponse(test, response -> {
      assertEquals(10, response.size());
      for (int i = 0; i < 10; i++) {
        assertEquals(i + 1, response.get(i).toInteger());
      }
    }));

    StringBuilder sb = new StringBuilder("*10\r\n");
    for (int i = 1; i <= 10; i++) {
      sb.append(":").append(i).append("\r\n");
    }
    parser.handle(Buffer.buffer(sb.toString()));
  }
}
