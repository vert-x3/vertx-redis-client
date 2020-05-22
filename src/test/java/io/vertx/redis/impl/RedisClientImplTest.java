package io.vertx.redis.impl;

import io.vertx.core.Vertx;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.client.impl.RedisURI;
import org.junit.Assert;
import org.junit.Test;

public class RedisClientImplTest {

  @Test
  @SuppressWarnings("deprecation")
  public void testPasswordWithEncodeInQuery() {
    Vertx vertx = Vertx.vertx();
    String auth = "abc#123";
    RedisOptions options = new RedisOptions();
    options.setAuth(auth);
    RedisClientImpl client = new RedisClientImpl(vertx, options);
    RedisURI redisURI = new RedisURI(client.getConnectionString());
    Assert.assertEquals("Password is not correct", auth, redisURI.password());
    vertx.close();
  }
}
