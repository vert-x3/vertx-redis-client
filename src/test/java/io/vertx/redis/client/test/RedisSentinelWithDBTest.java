package io.vertx.redis.client.test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RedisSentinelWithDBTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testGetClientToMaster(TestContext should) {
    final Async test = should.async();

    Redis.createClient(
      rule.vertx(),
      new RedisOptions()
        .setType(RedisClientType.SENTINEL)
        .addConnectionString("redis://localhost:5000/0")
        .addConnectionString("redis://localhost:5001/0")
        .addConnectionString("redis://localhost:5002/0")
        .setMasterName("sentinel7000")
        .setRole(RedisRole.MASTER)
        .setMaxPoolSize(4)
        .setMaxPoolWaiting(16))
      .connect(onCreate -> {
        // get a connection to the master node
        should.assertTrue(onCreate.succeeded());
        // query the info
        onCreate.result()
          .send(Request.cmd(Command.CLIENT).arg("LIST"), info -> {
            should.assertTrue(info.succeeded());
            System.out.println(info.result());
            test.complete();
          });
      });
  }
}
