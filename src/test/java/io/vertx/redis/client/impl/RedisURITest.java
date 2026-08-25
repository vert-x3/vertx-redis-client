package io.vertx.redis.client.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * @author <a href="mailto:artursletter@gmail.com">Artur Badretdinov</a>
 */
public class RedisURITest {

  @Test
  public void testHostAndPort() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234");
    assertEquals("Host address is not correct", "redis-1234.hosted.com", redisURI.socketAddress().host());
    assertEquals("Port is not correct", 1234, redisURI.socketAddress().port());
  }

  @Test
  public void testOnlyPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    assertNull("User is not correct", redisURI.user());
    assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
    assertEquals("Userinfo is not correct", ":p%40ssw0rd@", redisURI.userinfo());
  }

  @Test
  public void testOnlyPasswordInQueryGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    assertNull("User is not correct", redisURI.user());
    assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
    assertEquals("Userinfo is not correct", ":p%40ssw0rd@", redisURI.userinfo());
  }

  @Test
  public void testTwoPasswordsAreGiven() {
    RedisURI redisURI = new RedisURI("redis://:pass@redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    assertNull("User is not correct", redisURI.user());
    assertEquals("Password is not correct", "pass", redisURI.password());
    assertEquals("Userinfo is not correct", ":pass@", redisURI.userinfo());
  }

  @Test
  public void testLoginAndPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    assertEquals("User is not correct", "redundantName", redisURI.user());
    assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
    assertEquals("Userinfo is not correct", "redundantName:p%40ssw0rd@", redisURI.userinfo());
  }

  @Test
  public void testOnlyLoginGiven() {
    RedisURI redisURI = new RedisURI("redis://redisUs%65r@redis-1234.hosted.com:1234/0");
    assertEquals("User is not correct", "redisUser", redisURI.user());
    assertNull("Password is not correct", redisURI.password());
    assertEquals("Userinfo is not correct", "redisUser@", redisURI.userinfo());
  }

  @Test
  public void testTwoLoginsAreGiven() {
    RedisURI redisURI = new RedisURI("redis://redisUs%65r:pass@redis-1234.hosted.com:1234/0?user=otherUs%65r");
    assertEquals("User is not correct", "redisUser", redisURI.user());
    assertEquals("Password is not correct", "pass", redisURI.password());
    assertEquals("Userinfo is not correct", "redisUser:pass@", redisURI.userinfo());
  }

  @Test
  public void testPasswordNotGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0");
    assertNull("User is not null", redisURI.user());
    assertNull("Password is not null", redisURI.password());
    assertEquals("Userinfo is not empty", "", redisURI.userinfo());
  }

  @Test
  public void testDbNumberGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/2");
    assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testDbNumberGivenInQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234?db=2");
    assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testDbNumberPriorityPathOverQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/1?db=2");
    assertEquals("DB number is not correct", 1, (int) redisURI.select());
  }

  @Test
  public void testUNIX() {
    RedisURI redisURI = new RedisURI("unix:///some/file.sock?db=2&password=p%40ssw0rd");
    assertEquals("Password is not correct", "p@ssw0rd", redisURI.password());
    assertEquals("UNIX file is not correct", "/some/file.sock", redisURI.socketAddress().path());
    assertEquals("DB number is not correct", 2, (int) redisURI.select());
  }

  @Test
  public void testIPV6() {
    RedisURI redisURI = new RedisURI("redis://[::1]:1234/0");
    assertEquals("[::1]:1234", redisURI.socketAddress().toString());
  }

  @Test
  public void testColon() {
    RedisURI redisURI = new RedisURI("redis://:admin%3Aqwer@localhost:6379/1");
    assertNull(redisURI.user());
    assertEquals("admin:qwer", redisURI.password());
    assertEquals(":admin%3Aqwer@", redisURI.userinfo());
  }

  @Test
  public void testRightSyntax() {
    RedisURI redisURI = new RedisURI("redis://your-redis-domain:6379");
    assertEquals(6379, redisURI.socketAddress().port());
    assertEquals("your-redis-domain", redisURI.socketAddress().host());
  }

  @Test
  public void testWrongSyntax() {
    assertThrows(IllegalArgumentException.class, () -> new RedisURI("redis://:your-redis-domain:6379"));
  }

  @Test
  public void testDefaultPort() {
    RedisURI redisURI = new RedisURI("redis://localhost");
    assertEquals(6379, redisURI.socketAddress().port());
  }

  @Test
  public void testDefaultPortWithPassword() {
    RedisURI redisURI = new RedisURI("redis://:password@localhost");
    assertNull(redisURI.user());
    assertEquals("password", redisURI.password());
    assertEquals(":password@", redisURI.userinfo());
    assertEquals("localhost", redisURI.socketAddress().host());
    assertEquals(6379, redisURI.socketAddress().port());
  }
}
