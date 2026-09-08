---
name: writing-tests
description: >
  Testing patterns for Vert.x Redis Client: test frameworks, async testing,
  test locations, container setup, and how to run tests.
---

# Writing Tests

Vert.x Redis Client uses JUnit 5 with Vert.x-specific test utilities and Testcontainers for Redis infrastructure. Tests are mandatory for contributions.

## Test Framework

- **JUnit 5** (Jupiter) — Standard test framework
- **VertxExtension** — JUnit 5 extension for async tests
- **VertxTestContext** — Async test utility for handling asynchronous operations
- **RunTestOnContext** — JUnit 5 extension that provides a Vert.x instance and event loop context
- **Testcontainers** — Docker-based Redis containers for integration tests

## Test Annotations and Extensions

### Standard Integration Test Setup

```java
@ExtendWith(VertxExtension.class)
@Testcontainers
public class MyTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;
  private RedisAPI api;

  @BeforeEach
  public void before(VertxTestContext test) {
    client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect().onComplete(test.succeeding(conn -> {
      api = RedisAPI.api(conn);
      test.completeNow();
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }
}
```

## Async Test Patterns

### Pattern 1: Using `test.succeeding()` with `test.completeNow()`

The most common pattern. Use `test.succeeding()` to assert the operation succeeded, then complete the test.

```java
@Test
public void testSet(VertxTestContext test) {
  final String key = TestUtils.randomKey();
  api.set(List.of(key, "Hello"))
    .onComplete(test.succeeding(reply -> {
      assertEquals("OK", reply.toString());
      test.completeNow();
    }));
}
```

### Pattern 2: Using `test.succeedingThenComplete()`

Shorthand when you only need to verify the operation succeeds without inspecting the result.

```java
@Test
public void testDel(VertxTestContext test) {
  final String key = TestUtils.randomKey();
  api.set(List.of(key, "Hello"))
    .compose(ignored -> api.del(List.of(key)))
    .onComplete(test.succeedingThenComplete());
}
```

### Pattern 3: Chaining with `compose()`

Chain multiple async operations using `Future.compose()`.

```java
@Test
public void testAppend(VertxTestContext test) {
  final String key = TestUtils.randomKey();
  api.del(List.of(key))
    .compose(ignored -> api.append(key, "Hello"))
    .compose(reply -> {
      assertEquals(5, reply.toLong());
      return api.append(key, " World");
    })
    .onComplete(test.succeeding(reply -> {
      assertEquals(11, reply.toLong());
      test.completeNow();
    }));
}
```

### Pattern 4: Expecting failure with `test.failingThenComplete()`

When the operation is expected to fail.

```java
@Test
public void testFailure(VertxTestContext test) {
  client.send(Request.cmd(Command.create("INVALID_COMMAND")))
    .onComplete(test.failingThenComplete());
}
```

## Available Test Containers

All containers are in `io.vertx.tests.redis.containers`:

| Container | Description | Usage |
|---|---|---|
| `RedisStandalone` | Single Redis instance | `new RedisStandalone()` or use `RedisStandalone.builder()` for options (version, password, TLS, mutual TLS, Unix domain sockets) |
| `RedisCluster` | Redis Cluster setup | Clustered Redis with multiple nodes |
| `RedisSentinel` | Redis Sentinel setup | Sentinel-managed Redis |
| `RedisReplication` | Redis Replication setup | Primary/replica Redis |
| `PikaContainer` | Pika (Redis-compatible) | Alternative Redis-compatible store |
| `ValkeyContainer` | Valkey container | Valkey (Redis fork) |

### Container Builder Example

```java
@Container
public static final RedisStandalone redis = RedisStandalone.builder()
  .setVersion("7.2")
  .setPassword("secret")
  .build();
```

## Test Location

- **Unit tests** (no Redis required): `src/test/java/io/vertx/tests/redis/internal/`
  - Parser tests, URI handling, data structures (`ArrayQueue`, `ZModem`, `MultiType`, …)
- **Integration tests** (require Docker): `src/test/java/io/vertx/tests/redis/client/`
  - Client tests, pub/sub, cluster, sentinel, replication, TLS, tracing, metrics, reconnect, …
- **Test containers**: `src/test/java/io/vertx/tests/redis/containers/`

## Test Utilities

### `TestUtils.randomKey()`

Always use `TestUtils.randomKey()` instead of hardcoded key names. This avoids collisions between test runs and parallel tests.

```java
import static io.vertx.tests.redis.client.TestUtils.randomKey;

final String key = randomKey();
```

### `TestUtils.retryUntilSuccess()`

Retry an async operation until it succeeds or reaches the maximum number of retries. Useful for waiting for cluster state to converge.

```java
TestUtils.retryUntilSuccess(vertx, () -> api.clusterInfo(), 10)
  .onComplete(test.succeedingThenComplete());
```

### `TestUtils.executeWhenConditionSatisfied()`

Wait until a boolean condition is true, then execute an action. Uses Vert.x timers (non-blocking).

```java
TestUtils.executeWhenConditionSatisfied(vertx, () -> ready, () -> {
  // proceed with test
  test.completeNow();
});
```

## Assertions

Use JUnit 5 assertions:

```java
import static org.junit.jupiter.api.Assertions.*;

assertEquals(expected, actual);
assertTrue(condition);
assertFalse(condition);
assertNull(value);
assertNotNull(value);
```

## Test Requirements

- **All new features must include tests**
- **Tests must clean up resources** — close clients in `@AfterEach`
- **Use random keys** — `TestUtils.randomKey()` to avoid collisions
- **Integration tests need Docker** — ensure Docker is running before executing

## Common Pitfalls

1. **Forgetting to complete async tests** — Always call `test.completeNow()` or use `test.succeedingThenComplete()` / `test.failingThenComplete()`
2. **Using hardcoded Redis keys** — Use `TestUtils.randomKey()` to prevent test interference
3. **Not closing the client** — Always close `Redis` client in `@AfterEach` to avoid resource leaks
4. **Mixing up `RedisAPI.api(client)` vs `RedisAPI.api(conn)`** — `api(client)` uses pooled connections, `api(conn)` uses a specific connection; choose based on what you're testing
