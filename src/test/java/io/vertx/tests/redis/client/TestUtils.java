package io.vertx.tests.redis.client;

import io.vertx.core.Completable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.UUID;
import java.util.function.BooleanSupplier;
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
   * Waits until the given {@code condition} is {@code true} and then invokes the given {@code action} once,
   * returning its result. Waiting is not active, it uses the Vert.x {@linkplain Vertx#setTimer(long, Handler) timer}
   * facility.
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
   * Retries the given {@code action} until it succeeds or until the number of retries reaches the given maximum.
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
}
