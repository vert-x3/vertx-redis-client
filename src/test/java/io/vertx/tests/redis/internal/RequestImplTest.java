package io.vertx.tests.redis.internal;

import io.vertx.redis.client.Command;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.RequestImpl;
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
