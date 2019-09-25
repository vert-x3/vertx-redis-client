package io.vertx.redis.client.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:artursletter@gmail.com">Artur Badretdinov</a>
 */
public class RedisURITest {

  @Test
  public void testHostAndPort() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234");
    Assert.assertEquals("Host address is not correct", "redis-1234.hosted.com", redisURI.socketAddress().host());
    Assert.assertEquals("Port is not correct", 1234, redisURI.socketAddress().port());
  }

  @Test
  public void testOnlyPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://p%40ssw0rd@redis-1234.hosted.com:1234/0");
    Assert.assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
  }

  @Test
  public void testLoginAndPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    Assert.assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
  }

  @Test
  public void testPasswordNotGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0");
    Assert.assertNull("Password is not null", redisURI.password());
  }

  @Test
  public void testDbNumberGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/2");
    Assert.assertEquals("Password is not correct", 2, (long) redisURI.select());
  }
}
