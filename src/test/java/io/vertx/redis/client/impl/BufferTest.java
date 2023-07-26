package io.vertx.redis.client.impl;

import io.netty.buffer.Unpooled;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferInternal;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BufferTest {

  private static final int iterations = 50000;

  @Test
  public void testAppendString() {
    Request hmset = Request.cmd(Command.HMSET);
    String key = "mykey";

    for (int i = 0; i < iterations; i++) {
      hmset.arg(key).arg(i);
    }

    System.out.println(((RequestImpl) hmset).encode().length());

    for (int j = 0; j < 5; j++) {
      final long t0 = System.nanoTime();
      hmset = Request.cmd(Command.HMSET);

      for (int i = 0; i < iterations; i++) {
        hmset.arg(key).arg(i);
      }
      final long t1 = System.nanoTime();

      System.out.println(((RequestImpl) hmset).encode().length() + "| t " + (t1 - t0));
    }
    System.out.println("---");
  }

  @Test
  public void testAppendBytes() {
    Request hmset = Request.cmd(Command.HMSET);
    byte[] key = "my-key".getBytes(StandardCharsets.US_ASCII);

    for (int i = 0; i < iterations; i++) {
      hmset.arg(key).arg(i);
    }

    System.out.println(((RequestImpl) hmset).encode().length());

    for (int j = 0; j < 5; j++) {
      final long t0 = System.nanoTime();
      hmset = Request.cmd(Command.HMSET);

      for (int i = 0; i < iterations; i++) {
        hmset.arg(key).arg(i);
      }
      final long t1 = System.nanoTime();

      System.out.println(((RequestImpl) hmset).encode().length() + "| t " + (t1 - t0));
    }
    System.out.println("---");
  }

  @Test
  public void testAppendBuffer() {
    Request hmset = Request.cmd(Command.HMSET);
    Buffer key = Buffer.buffer("my-key");

    for (int i = 0; i < iterations; i++) {
      hmset.arg(key).arg(i);
    }

    System.out.println(((RequestImpl) hmset).encode().length());

    for (int j = 0; j < 5; j++) {
      final long t0 = System.nanoTime();
      hmset = Request.cmd(Command.HMSET);

      for (int i = 0; i < iterations; i++) {
        hmset.arg(key).arg(i);
      }
      final long t1 = System.nanoTime();

      System.out.println(((RequestImpl) hmset).encode().length() + "| t " + (t1 - t0));
    }
    System.out.println("---");
  }

  @Test
  public void testAppendStringToBytes() {
    Request hmset = Request.cmd(Command.HMSET);
    String key = "mykey";

    for (int i = 0; i < iterations; i++) {
      hmset.arg(key.getBytes()).arg(i);
    }

    System.out.println(((RequestImpl) hmset).encode().length());

    for (int j = 0; j < 5; j++) {
      final long t0 = System.nanoTime();
      hmset = Request.cmd(Command.HMSET);

      for (int i = 0; i < iterations; i++) {
        hmset.arg(key.getBytes()).arg(i);
      }
      final long t1 = System.nanoTime();

      System.out.println(((RequestImpl) hmset).encode().length() + "| t " + (t1 - t0));
    }
    System.out.println("---");
  }

  @Test
  public void testAppendBufferWrapped() {
    Charset UTF8 = StandardCharsets.UTF_8;
    Request hmset = Request.cmd(Command.HMSET);
    Buffer key = BufferInternal.buffer(Unpooled.wrappedBuffer(UTF8.encode("my-key")));

    for (int i = 0; i < iterations; i++) {
      hmset.arg(key).arg(i);
    }

    System.out.println(((RequestImpl) hmset).encode().length());

    for (int j = 0; j < 5; j++) {
      final long t0 = System.nanoTime();
      hmset = Request.cmd(Command.HMSET);

      for (int i = 0; i < iterations; i++) {
        hmset.arg(key).arg(i);
      }
      final long t1 = System.nanoTime();

      System.out.println(((RequestImpl) hmset).encode().length() + "| t " + (t1 - t0));
    }
    System.out.println("---");
  }
}
