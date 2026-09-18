package io.vertx.tests.redis.internal;

import io.vertx.core.buffer.Buffer;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.ParserHandler;
import io.vertx.redis.client.impl.RESPParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class RESPParserMaxMultiLengthTest {

  @RegisterExtension
  RunTestOnContext context = new RunTestOnContext();

  private static final int MAX_MULTI_LENGTH = 10;

  @Test
  public void testArrayExceedsMaxMultiLength(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> {
          assertTrue(t.getMessage().contains("maxMultiLength"));
        });
        test.completeNow();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("*11\r\n"));
  }

  @Test
  public void testMapExceedsMaxMultiLength(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> {
          assertTrue(t.getMessage().contains("maxMultiLength"));
        });
        test.completeNow();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("%11\r\n"));
  }

  @Test
  public void testSetExceedsMaxMultiLength(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> {
          assertTrue(t.getMessage().contains("maxMultiLength"));
        });
        test.completeNow();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("~11\r\n"));
  }

  @Test
  public void testPushExceedsMaxMultiLength(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> {
          assertTrue(t.getMessage().contains("maxMultiLength"));
        });
        test.completeNow();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer(">11\r\n"));
  }

  @Test
  public void testAttributeExceedsMaxMultiLength(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.failNow("should not receive a response");
      }

      @Override
      public void fail(Throwable t) {
        test.verify(() -> {
          assertTrue(t.getMessage().contains("maxMultiLength"));
        });
        test.completeNow();
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("|11\r\n"));
  }

  @Test
  public void testArrayWithinLimit(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(2, response.size());
          assertEquals("foo", response.get(0).toString());
          assertEquals("bar", response.get(1).toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16, MAX_MULTI_LENGTH);

    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test
  public void testArrayAtExactLimit(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(10, response.size());
          for (int i = 0; i < 10; i++) {
            assertEquals(i + 1, response.get(i).toInteger());
          }
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16, MAX_MULTI_LENGTH);

    StringBuilder sb = new StringBuilder("*10\r\n");
    for (int i = 1; i <= 10; i++) {
      sb.append(":").append(i).append("\r\n");
    }
    parser.handle(Buffer.buffer(sb.toString()));
  }
}
