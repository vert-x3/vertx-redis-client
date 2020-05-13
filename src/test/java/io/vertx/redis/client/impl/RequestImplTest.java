package io.vertx.redis.client.impl;

import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;
import org.junit.Test;

public class RequestImplTest {

  @Test
  public void testSimple() {
    RequestImpl r = (RequestImpl) Request.cmd(Command.PING);
    System.out.println(r.encode());
  }

  @Test
  public void testWithArgs() {
    RequestImpl r = (RequestImpl) Request.cmd(Command.LLEN).arg("mylist");
    System.out.println(r.encode());
  }
}
