# Agent Guidelines - vertx-redis-client

Instructions for AI coding agents working in this repository.
Checkout relevant `.agents/skills/` to accomplish specific tasks.

## Build & Verify Commands

```bash
mvn test-compile         # compile code and tests
mvn verify               # run all tests (requires Docker for Redis containers)
mvn spotless:check       # verify formatting
mvn spotless:apply       # auto-fix formatting
```

## Project Structure

This is a **single-module** Maven project (`io.vertx:vertx-redis-client`).

### Directory Layout

- `src/main/java/io/vertx/redis/client/` — Public API interfaces (`Redis`, `RedisAPI`, `Command`, `Request`, `Response`, `RedisOptions`, …)
- `src/main/java/io/vertx/redis/client/impl/` — Internal implementation (not exported)
  - `impl/types/` — RESP type implementations (`BulkType`, `MultiType`, `ErrorType`, …)
  - `impl/keys/` — Key locators for cluster routing
- `src/main/generated/` — Auto-generated `*Converter.java` classes (do not edit manually)
- `src/test/java/io/vertx/tests/redis/client/` — Integration tests (require Docker)
- `src/test/java/io/vertx/tests/redis/internal/` — Unit tests (no Redis required)
- `src/test/java/io/vertx/tests/redis/containers/` — Testcontainers wrappers (`RedisStandalone`, `RedisCluster`, `RedisSentinel`, `RedisReplication`, `PikaContainer`, `ValkeyContainer`)
- `tools/` — Node.js code generation scripts (`generate.js` + Handlebars templates)
- `module-info.java` — Defines module exports and dependencies

### Code Generation

Redis commands (`Command.java`, `RedisAPI.java`, `ValkeyAPI.java`, `CommandMap.java`) are auto-generated from Redis/Valkey `COMMAND DOCS` metadata using `tools/generate.js`.
Do not edit these files manually; modify the Handlebars templates or `generate.js` instead.

## General Coding Rules

These rules apply when **writing or modifying code**. Code review is the checkpoint where compliance is verified.

### Logging

In production code, use the Vert.x internal logger, never SLF4J, Log4j, or `java.util.logging` directly.

```java
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
```

Vert.x internal logging API doesn't support parameter placeholders.
Check the active level before debug or trace logging.

```java
if (logger.isDebugEnabled()) {
  logger.debug("Prepared parameters: " + paramDesc);
}
```

### Async Patterns

Use Vert.x `Future<T>` and `Promise<T>` throughout. Do not use raw callbacks or `CompletableFuture` in production code.

### API Design

- Public contracts are interfaces in `io.vertx.redis.client`
- Implementations go in the `impl/` subpackage
- Annotate public API interfaces and methods with `@VertxGen` for code-generation support
- Options classes use `@DataObject` with generated JSON converters
- Expose construction via static factory methods, not constructors

### Module Boundaries

`module-info.java` governs exports.
Internal packages are not exported; do not widen exports without discussion.

### Copyright Header

New Java files must include the dual-license header matching existing files:

```java
/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
```

## Testing Guidelines

For comprehensive testing patterns and examples, see `.agents/skills/writing-tests/SKILL.md`.

### Test Framework

- Use **JUnit 5** (Jupiter) for all tests
- Async tests use **VertxExtension** and **VertxTestContext**
- Integration tests require **Docker** for Redis containers via **Testcontainers**

### Test Patterns

```java
@ExtendWith(VertxExtension.class)
@Testcontainers
public class MyTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  private Redis client;

  @BeforeEach
  public void before(VertxTestContext test) {
    client = Redis.createClient(context.vertx(), new RedisOptions().setConnectionString(redis.getRedisUri()));
    client.connect().onComplete(test.succeeding(conn -> {
      // setup
      test.completeNow();
    }));
  }

  @AfterEach
  public void after() {
    client.close();
  }

  @Test
  public void testSomething(VertxTestContext test) {
    RedisAPI api = RedisAPI.api(client);
    api.set(List.of(TestUtils.randomKey(), "value"))
      .onComplete(test.succeeding(reply -> {
        // assertions
        test.completeNow();
      }));
  }
}
```

### Test Location

- **Unit tests** (no Redis required): `src/test/java/io/vertx/tests/redis/internal/`
- **Integration tests** (require Docker): `src/test/java/io/vertx/tests/redis/client/`
- **Test containers**: `src/test/java/io/vertx/tests/redis/containers/`

### Running Tests

```bash
mvn verify                              # Run all tests (requires Docker)
mvn test -Dtest=MyTest                  # Run specific test class
mvn test -Dtest=MyTest#testMethod       # Run specific test method
```

### Test Requirements

- All new features must include tests
- Tests must clean up resources (connections, clients)
- Use `TestUtils.randomKey()` for unique Redis key names to avoid collisions

## Development Workflow

### Incremental Development

When making changes:
1. Compile frequently: `mvn compile`
2. Run affected tests: `mvn test -Dtest=TestClass`
3. Run full build before PR: `mvn clean verify`

### Build Optimization

```bash
# Skip tests during development
mvn compile -DskipTests

# Compile tests without running them
mvn test-compile
```

## Specialized Skills

When performing specific tasks, read the relevant skill file for detailed guidance:

- **Writing tests** - Read `.agents/skills/writing-tests/SKILL.md` when creating or modifying tests

## Contribution Process

- All commits must be signed off: `git commit -s` (DCO)
- Commit messages should end with: `Assisted-by: [Provider] [Model-Family] ([Version/ID])` (replace placeholders)
- Contributors must have signed the [Eclipse Contributor Agreement (ECA)](https://www.eclipse.org/legal/ECA.php)

See [CONTRIBUTING.md](CONTRIBUTING.md) for the full contribution workflow.

## Code Review Guidelines

### Verify

- General coding rules above are followed
- Test coverage is present; async tests use `VertxExtension` and `VertxTestContext`
- No breaking changes to public interfaces without prior discussion

### Do Not Comment On

- Patterns already used consistently throughout the codebase
