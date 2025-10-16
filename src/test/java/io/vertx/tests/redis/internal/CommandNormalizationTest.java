package io.vertx.tests.redis.internal;

import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.CommandImpl;
import io.vertx.redis.client.impl.RequestImpl;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CommandNormalizationTest {
  @Test
  public void test() {
    RequestImpl req = (RequestImpl) Request.cmd(Command.create("hset")).arg("key").arg("field").arg("value");
    CommandImpl cmd = (CommandImpl) req.command();
    assertEquals("hset", cmd.toString());
    assertNotEquals(-1, cmd.getArity());
    assertFalse(cmd.isReadOnly(req.getArgs()));
    assertFalse(cmd.needsGetKeys());
    assertEquals(1, req.keys().size());
    assertEquals("key", new String(req.keys().get(0), StandardCharsets.UTF_8));
  }
}
