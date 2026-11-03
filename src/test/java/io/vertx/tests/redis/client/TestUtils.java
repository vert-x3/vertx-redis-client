package io.vertx.tests.redis.client;

import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.internal.resource.ResourceManager;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.RedisConnectionManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestUtils {
  /**
   * Returns a short random string that is usable as a key in Redis with very high probability
   * of being unique.
   * <p>
   * Implementation note: this is currently a random UUID, but that is <em>not</em> guaranteed
   * and may change without notice.
   */
  public static String randomKey() {
    return UUID.randomUUID().toString();
  }

  /**
   * Waits until the given {@code condition} is {@code true} and then invokes the given {@code action} once.
   * Waiting is not active, it uses the Vert.x {@linkplain Vertx#setTimer(long, Handler) timer} facility.
   * The delay between retries is 5 millis.
   */
  public static void executeWhenConditionSatisfied(Vertx vertx, BooleanSupplier condition, Runnable action) {
    if (condition.getAsBoolean()) {
      action.run();
    } else {
      vertx.setTimer(5, ignored -> {
        executeWhenConditionSatisfied(vertx, condition, action);
      });
    }
  }

  /**
   * Returns a future that completes successfully when the given {@code condition} becomes {@code true}.
   * This is essentially another API for
   * {@link #executeWhenConditionSatisfied(Vertx, BooleanSupplier, Runnable) executeWhenConditionSatisfied()}.
   */
  public static Future<Void> whenConditionSatisfied(Vertx vertx, BooleanSupplier condition) {
    Promise<Void> promise = Promise.promise();
    executeWhenConditionSatisfied(vertx, condition, promise::succeed);
    return promise.future();
  }

  /**
   * Retries the given {@code action} until it succeeds or until the number of retries reaches the given maximum.
   * The delay between retries is 500 millis.
   */
  public static <T> Future<T> retryUntilSuccess(Vertx vertx, Supplier<Future<T>> action, int maxRetries) {
    Promise<T> promise = Promise.promise();
    retryUntilSuccess(vertx, action, maxRetries, promise);
    return promise.future();
  }

  private static <T> void retryUntilSuccess(Vertx vertx, Supplier<Future<T>> action, int maxRetries, Completable<T> promise) {
    action.get().onComplete(result -> {
      if (result.succeeded()) {
        promise.succeed(result.result());
      } else {
        if (maxRetries < 1) {
          promise.fail(result.cause());
        } else {
          vertx.setTimer(500, ignored -> {
            retryUntilSuccess(vertx, action, maxRetries - 1, promise);
          });
        }
      }
    });
  }

  /**
   * Returns the number of requests that wait in the queue of the connection pool to the given {@code endpoint},
   * without a setup command.
   */
  @SuppressWarnings("unchecked")
  public static int poolWaiters(RedisConnectionManager connectionManager, String endpoint) {
    try {
      Field field = RedisConnectionManager.class.getDeclaredField("pooledConnectionManager");
      field.setAccessible(true);
      ResourceManager<RedisConnectionManager.ConnectionKey, RedisConnectionManager.RedisEndpoint> endpoints = (ResourceManager<RedisConnectionManager.ConnectionKey, RedisConnectionManager.RedisEndpoint>) field.get(connectionManager);
      Constructor<RedisConnectionManager.ConnectionKey> key = RedisConnectionManager.ConnectionKey.class.getDeclaredConstructor(String.class, Request.class);
      key.setAccessible(true);
      return endpoints.withResource(key.newInstance(endpoint, null), connectionManager, Function.identity(),
        (resource, created) -> resource.pool().waiters());
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
