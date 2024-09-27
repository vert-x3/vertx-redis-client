package examples;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.redis.client.RedisConnectOptions;
import io.vertx.redis.client.RedisOptions;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

public class RedisConnectOptionsSupplier<OPTS extends RedisConnectOptions> implements Supplier<Future<OPTS>> {
  private final Vertx vertx;
  private final RedisOptions options;
  private final Function<RedisOptions, OPTS> creator;
  private final Supplier<String> userName;
  private final Supplier<String> password;
  private final AtomicReference<Future<OPTS>> future;

  public RedisConnectOptionsSupplier(Vertx vertx, RedisOptions options, Function<RedisOptions, OPTS> creator,
    Supplier<String> userName, Supplier<String> password) {
    this.vertx = vertx;
    this.options = options;
    this.creator = creator;
    this.userName = userName;
    this.password = password;
    this.future = new AtomicReference<>();
  }

  @Override
  public Future<OPTS> get() {
    while (true) {
      Future<OPTS> currentFuture = this.future.get();
      if (currentFuture != null) {
        return currentFuture;
      }

      Promise<OPTS> promise = Promise.promise();
      Future<OPTS> future = promise.future();
      if (this.future.compareAndSet(null, future)) {
        OPTS result = creator.apply(options);
        result.setUser(userName.get());
        result.setPassword(password.get()); // TODO run on worker thread
        promise.complete(result);

        // clean up in 10 minutes, to force refresh
        vertx.setTimer(10 * 60 * 1000, ignored -> {
          this.future.set(null);
        });

        return future;
      }
    }
  }
}
