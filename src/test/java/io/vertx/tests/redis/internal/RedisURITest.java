package io.vertx.tests.redis.internal;

import io.vertx.redis.client.impl.RedisURI;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

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
    RedisURI redisURI = new RedisURI("redis://:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    Assert.assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
  }

  @Test
  public void testOnlyPasswordInQueryGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    Assert.assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
  }

  @Test
  public void testTwoPasswordsAreGiven() {
    RedisURI redisURI = new RedisURI("redis://:pass@redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    Assert.assertEquals("Password is not correct", "pass", redisURI.password());
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
    Assert.assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testDbNumberGivenInQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234?db=2");
    Assert.assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testDbNumberPriorityPathOverQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/1?db=2");
    Assert.assertEquals("DB number is not correct", 1, (int) redisURI.select());
  }

  @Test
  public void testUNIX() {
    RedisURI redisURI = new RedisURI("unix:///some/file.sock?db=2&password=p%40ssw0rd");
    Assert.assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
    Assert.assertEquals("UNIX file is not correct", "/some/file.sock", redisURI.socketAddress().path());
    Assert.assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testIPV6() {
    RedisURI redisURI = new RedisURI("redis://[::1]:1234/0");
    Assert.assertEquals("[::1]:1234", redisURI.socketAddress().toString());
  }

  @Test
  public void testColon() {
    RedisURI redisURI = new RedisURI("redis://:admin%3Aqwer@localhost:6379/1");
    Assert.assertEquals("admin:qwer", redisURI.password());
  }

  @Test
  public void testRightSyntax() {
    RedisURI redisURI = new RedisURI("redis://your-redis-domain:6379");
    Assert.assertEquals(6379, redisURI.socketAddress().port());
    Assert.assertEquals("your-redis-domain", redisURI.socketAddress().host());
  }

  @Test
  public void testWrongSyntax() {
    assertThrows(IllegalArgumentException.class, () -> new RedisURI("redis://:your-redis-domain:6379"));
  }

  @Test
  public void testDefaultPort() {
    RedisURI redisURI = new RedisURI("redis://localhost");
    Assert.assertEquals(6379, redisURI.socketAddress().port());
  }

  @Test
  public void testDefaultPortWithPassword() {
    RedisURI redisURI = new RedisURI("redis://:password@localhost");
    Assert.assertEquals("password", redisURI.password());
    Assert.assertEquals("localhost", redisURI.socketAddress().host());
    Assert.assertEquals(6379, redisURI.socketAddress().port());
  }
}
