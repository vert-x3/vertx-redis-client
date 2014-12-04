package io.vertx.test.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.impl.ReplyParser;
import org.junit.Test;

public class ReplyParserTest {

    private void done() {}

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

        ReplyParser parser = new ReplyParser(reply -> done());

        parser.handle(b);
    }

    @Test
    public void testArrayArrayEmptyParser() {
        Buffer b = Buffer.buffer();
        b.appendString(
                        "*1\r\n" +
                        "*0\r\n");

        ReplyParser parser = new ReplyParser(reply -> done());

        parser.handle(b);
    }
}
