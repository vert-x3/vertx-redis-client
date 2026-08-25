package io.vertx.tests.redis.internal;

import io.vertx.redis.client.impl.RedisURI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author <a href="mailto:artursletter@gmail.com">Artur Badretdinov</a>
 */
public class RedisURITest {

  @Test
  public void testHostAndPort() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234");
    assertEquals("redis-1234.hosted.com", redisURI.socketAddress().host(), "Host address is not correct");
    assertEquals(1234, redisURI.socketAddress().port(), "Port is not correct");
  }

  @Test
  public void testOnlyPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    assertNull(redisURI.user(), "User is not correct");
    assertEquals("p@ssw0rd", redisURI.password(), "Password is not correct");
    assertEquals(":p%40ssw0rd@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testOnlyPasswordInQueryGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    assertNull(redisURI.user(), "User is not correct");
    assertEquals("p@ssw0rd", redisURI.password(), "Password is not correct");
    assertEquals(":p%40ssw0rd@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testTwoPasswordsAreGiven() {
    RedisURI redisURI = new RedisURI("redis://:pass@redis-1234.hosted.com:1234/0?password=p%40ssw0rd");
    assertNull(redisURI.user(), "User is not correct");
    assertEquals("pass", redisURI.password(), "Password is not correct");
    assertEquals(":pass@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testLoginAndPasswordGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/0");
    assertEquals("redundantName", redisURI.user(), "User is not correct");
    assertEquals("p@ssw0rd", redisURI.password(), "Password is not correct");
    assertEquals("redundantName:p%40ssw0rd@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testOnlyLoginGiven() {
    RedisURI redisURI = new RedisURI("redis://redisUs%65r@redis-1234.hosted.com:1234/0");
    assertEquals("redisUser", redisURI.user(), "User is not correct");
    assertNull(redisURI.password(), "Password is not correct");
    assertEquals("redisUser@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testTwoLoginsAreGiven() {
    RedisURI redisURI = new RedisURI("redis://redisUs%65r:pass@redis-1234.hosted.com:1234/0?user=otherUs%65r");
    assertEquals("redisUser", redisURI.user(), "User is not correct");
    assertEquals("pass", redisURI.password(), "Password is not correct");
    assertEquals("redisUser:pass@", redisURI.userinfo(), "Userinfo is not correct");
  }

  @Test
  public void testPasswordNotGiven() {
    RedisURI redisURI = new RedisURI("redis://redis-1234.hosted.com:1234/0");
    assertNull(redisURI.user(), "User is not null");
    assertNull(redisURI.password(), "Password is not null");
    assertEquals("", redisURI.userinfo(), "Userinfo is not empty");
  }

  @Test
  public void testDbNumberGiven() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/2");
    assertEquals(2, redisURI.select(), "DB number is not correct");
  }

  @Test
  public void testDbNumberGivenInQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234?db=2");
    assertEquals(2, redisURI.select(), "DB number is not correct");
  }

  @Test
  public void testDbNumberPriorityPathOverQuery() {
    RedisURI redisURI = new RedisURI("redis://redundantName:p%40ssw0rd@redis-1234.hosted.com:1234/1?db=2");
    assertEquals(1, redisURI.select(), "DB number is not correct");
  }

  @Test
  public void testUNIX() {
    RedisURI redisURI = new RedisURI("unix:///some/file.sock?db=2&password=p%40ssw0rd");
    assertEquals("p@ssw0rd", redisURI.password(), "Password is not correct");
    assertEquals("/some/file.sock", redisURI.socketAddress().path(), "UNIX file is not correct");
    assertEquals(2, redisURI.select(), "DB number is not correct");
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

  @Test
  public void testValkeyScheme() {
    RedisURI redisURI = new RedisURI("valkey://localhost:6379/0");
    assertEquals("localhost", redisURI.socketAddress().host());
    assertEquals(6379, redisURI.socketAddress().port());
    assertEquals(0, redisURI.select());
    assertEquals(false, redisURI.ssl());
    assertEquals("valkey", redisURI.protocol());
  }

  @Test
  public void testValkeysScheme() {
    RedisURI redisURI = new RedisURI("valkeys://localhost:6379/0");
    assertEquals("localhost", redisURI.socketAddress().host());
    assertEquals(6379, redisURI.socketAddress().port());
    assertEquals(0, redisURI.select());
    assertEquals(true, redisURI.ssl());
    assertEquals("valkeys", redisURI.protocol());
  }

  @Test
  public void testValkeySchemeWithCredentials() {
    RedisURI redisURI = new RedisURI("valkey://user:p%40ss@localhost:6379/2");
    assertEquals("user", redisURI.user());
    assertEquals("p@ss", redisURI.password());
    assertEquals("user:p%40ss@", redisURI.userinfo());
    assertEquals(2, redisURI.select());
  }
}
