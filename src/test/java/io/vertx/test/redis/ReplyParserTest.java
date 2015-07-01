package io.vertx.test.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.impl.ReplyParser;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

public class ReplyParserTest extends VertxTestBase {

  private void done() {
    testComplete();
  }

  @Test
  public void testArrayArrayParser() {
    Buffer b = Buffer.buffer();
    b.appendString(
        "*2\r\n" +
            "*3\r\n" +
            ":1\r\n" +
            ":2\r\n" +
            ":3\r\n" +
            "*2\r\n" +
            "+Foo\r\n" +
            "-Bar\r\n");

    ReplyParser parser = new ReplyParser(vertx, reply -> done());

    parser.handle(b);
    await();
  }

  @Test
  public void testArrayArrayEmptyParser() {
    Buffer b = Buffer.buffer();
    b.appendString(
      "*1\r\n" +
        "*0\r\n");

    ReplyParser parser = new ReplyParser(vertx, reply -> done());

    parser.handle(b);
    await();
  }
}
