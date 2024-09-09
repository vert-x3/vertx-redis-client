package io.vertx.tests.redis.client;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.UUID;
import java.util.function.Supplier;

public class TestUtils {
  public static String randomKey() {
    return UUID.randomUUID().toString();
  }

  public static <T> Future<T> retryUntilSuccess(Vertx vertx, Supplier<Future<T>> action, int maxRetries) {
    Promise<T> promise = Promise.promise();
    retryUntilSuccess(vertx, action, maxRetries, promise);
    return promise.future();
  }

  private static <T> void retryUntilSuccess(Vertx vertx, Supplier<Future<T>> action, int maxRetries, Promise<T> promise) {
    action.get().onComplete(result -> {
      if (result.succeeded()) {
        promise.complete(result.result());
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
