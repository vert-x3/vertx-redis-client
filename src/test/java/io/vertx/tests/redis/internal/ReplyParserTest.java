package io.vertx.tests.redis.internal;

import io.vertx.core.buffer.Buffer;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.ParserHandler;
import io.vertx.redis.client.impl.RESPParser;
import io.vertx.redis.client.impl.types.BulkType;
import io.vertx.redis.client.impl.types.MultiType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(VertxExtension.class)
public class ReplyParserTest {

  @RegisterExtension
  RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testParseSimple(VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint(4);

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        checkpoint.flag();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("+PONG\r\n+PONG\r\n+PONG\r\n+PONG\r\n"));
  }

  @Test
  public void testParseSimpleInChunks(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
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

  @Test
  public void testIntegerType(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(1000L, response.toLong());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer(":1000\r\n"));
  }

  @Test
  public void testNegIntegerType(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(-1L, response.toLong());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer(":-1\r\n"));
  }

  @Test
  public void testBulk(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals("foobar", response.toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$6\r\nfoobar\r\n"));
  }

  @Test
  public void testEmptyBulk(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(BulkType.EMPTY, response);
          assertEquals("", response.toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$0\r\n\r\n"));
  }

  @Test
  public void testNullBulk(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertNull(response);
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("$-1\r\n"));
  }

  @Test
  public void testEmptyMulti(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(MultiType.EMPTY_MULTI, response);
          assertEquals(0, response.size());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*0\r\n"));
  }

  @Test
  public void testNullMulti(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertNull(response);
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*-1\r\n"));
  }

  @Test
  public void testMulti(VertxTestContext test) {
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
    }, 16);

    parser.handle(Buffer.buffer("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
  }

  @Test
  public void testMultiOfInteger(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(3, response.size());
          assertEquals(1, response.get(0).toInteger());
          assertEquals(2, response.get(1).toInteger());
          assertEquals(3, response.get(2).toInteger());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*3\r\n:1\r\n:2\r\n:3\r\n"));
  }

  @Test
  public void testMultiOfMixed(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(5, response.size());
          assertEquals(1, response.get(0).toInteger());
          assertEquals(2, response.get(1).toInteger());
          assertEquals(3, response.get(2).toInteger());
          assertEquals(4, response.get(3).toInteger());
          assertEquals("foobar", response.get(4).toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n"));
  }

  @Test
  public void testMultiOfMulti(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(2, response.size());
          assertEquals(1, response.get(0).get(0).toInteger());
          assertEquals(2, response.get(0).get(1).toInteger());
          assertEquals(3, response.get(0).get(2).toInteger());
          assertEquals("Foo", response.get(1).get(0).toString());
          assertEquals("Bar", response.get(1).get(1).toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n-Bar\r\n"));
  }

  @Test
  public void testMultiWithNull(VertxTestContext test) {
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        test.verify(() -> {
          assertEquals(3, response.size());
          assertEquals("foo", response.get(0).toString());
          assertNull(response.get(1));
          assertEquals("bar", response.get(2).toString());
        });
        test.completeNow();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    parser.handle(Buffer.buffer("*3\r\n$3\r\nfoo\r\n$-1\r\n$3\r\nbar\r\n"));
  }

  @Test
  public void parseLargePayload(VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint(17);

    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        checkpoint.flag();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
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

  @Test
  @Disabled("Broken Azure output")
  public void parseAzureHello(VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint(17);
    final RESPParser parser = new RESPParser(new ParserHandler() {
      @Override
      public void handle(Response response) {
        checkpoint.flag();
      }

      @Override
      public void fail(Throwable t) {
        test.failNow(t);
      }
    }, 16);

    byte[] hello = Base64.getDecoder().decode("JTcNCiQ2DQpzZXJ2ZXINCiQ1DQpyZWRpcw0KJDcNCnZlcnNpb24NCiQ1DQo2LjAuMw0KJDUNCnByb3RvDQo6Mw0KJDINCmlkDQo6MzIxMDUNCiQ0DQptb2RlDQokMTANCnN0YW5kYWxvbmUNCiQ0DQpyb2xlDQokNg0KbWFzdGVyDQo=");

    parser.handle(Buffer.buffer(hello));
  }
}
