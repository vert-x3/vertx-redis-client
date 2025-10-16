package io.vertx.tests.redis.client;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.ThreadingModel;
import io.vertx.core.VerticleBase;
import io.vertx.core.internal.VertxInternal;
import io.vertx.core.net.NetClientOptions;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientLoadTest {

  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  private final RedisOptions REDIS_OPTIONS = new RedisOptions()
    .setMaxPoolWaiting(1000)
    .setConnectionString(redis.getRedisUri())
    .setNetClientOptions(new NetClientOptions()
      .setConnectTimeout(100)
      .setReuseAddress(true)
      .setTcpKeepAlive(true)
      .setReconnectAttempts(100)
      .setTcpNoDelay(true)
    );

  @RegisterExtension
  public final RunTestOnContext context = new RunTestOnContext();

  @Test
  public void testStatePooled(VertxTestContext test) {
    Redis redis = Redis.createClient(context.vertx(), new RedisOptions()
      .setMaxPoolWaiting(1)
      .setConnectionString(RedisClientLoadTest.redis.getRedisUri()));
    // acquire connection and close
    redis.connect()
      .onComplete(test.succeeding(conn -> {
        conn.close().onComplete(test.succeedingThenComplete());
      }));

  }

  @Test
  public void testStateExpired(VertxTestContext test) {
    Redis redis = Redis.createClient(context.vertx(), new RedisOptions()
      .setMaxPoolWaiting(1)
      .setPoolRecycleTimeout(1)
      .setConnectionString(RedisClientLoadTest.redis.getRedisUri()));
    // acquire connection and close
    redis.connect()
      .onComplete(test.succeeding(conn -> {
        context.vertx()
          .setTimer(5L, v -> {
            conn.close().onComplete(test.succeedingThenComplete());
          });
      }));

  }

  @RepeatedTest(10)
  public void test1(VertxTestContext test) {
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();

    Promise<Void> donePromise = ((VertxInternal) context.vertx()).promise();

    context.vertx().deployVerticle(() -> new VerticleBase() {
        @Override
        public Future<?> start() throws Exception {
          Redis redis = Redis.createClient(RedisClientLoadTest.this.context.vertx(), REDIS_OPTIONS);
          this.vertx.eventBus().consumer("test.redis.load").handler(m -> {
            redis.send(Request.cmd(Command.SET).arg("foo").arg("bar")).onComplete(res -> {
              if (res.failed()) {
                //System.err.println(res.cause().getMessage());
                countOfErrors.incrementAndGet();
              } else {
                countOfSuccess.incrementAndGet();
              }

              if (countOfErrors.get() + countOfSuccess.get() >= iterations * instances) {
                donePromise.complete();
              }
            });
          });
          return super.start();
        }
      }, new DeploymentOptions().setInstances(instances))
      .onComplete(test.succeeding(deploymentId -> {
        for (int i = 0; i < iterations; i++) {
          context.vertx().eventBus().publish("test.redis.load", true);
        }
      }));

    donePromise.future()
      .onComplete(test.succeeding(ok -> {
        // I'm suspecting that the issue is happening now here.
        // when the test fails, an exception is thrown from the assert bellow
        assertEquals(0, countOfErrors.get(), "Count of errors: " + countOfErrors.get());
        // then this triggers a re-run of the promise resolver which is not valid anymore
        test.completeNow();
      }));
  }

  @RepeatedTest(10)
  public void test2(VertxTestContext test) {
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();

    Promise<Void> donePromise = Promise.promise();

    context.vertx().deployVerticle(() -> new VerticleBase() {
        @Override
        public Future<?> start() throws Exception {
          Redis redis = Redis.createClient(RedisClientLoadTest.this.context.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString(RedisClientLoadTest.redis.getRedisUri()));
          this.vertx.eventBus().consumer("test.redis.load").handler(m -> {
            redis.connect().compose(r -> r.send(Request.cmd(Command.SET).arg("foo").arg("bar")).onComplete(res -> r.close()))
              .onComplete(res -> {
                if (res.failed()) {
                  //System.err.println(res.cause().getMessage());
                  countOfErrors.incrementAndGet();
                } else {
                  countOfSuccess.incrementAndGet();
                }

                if (countOfErrors.get() + countOfSuccess.get() >= iterations * instances) {
                  donePromise.complete();
                }
              });
          });
          return super.start();
        }
      }, new DeploymentOptions().setInstances(instances).setThreadingModel(ThreadingModel.WORKER).setWorkerPoolName("RedisLoadTest"))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          context.vertx().eventBus().publish("test.redis.load", true);
        }
      });

    donePromise.future().onComplete(res -> {
      if (countOfErrors.get() > 0) {
        test.failNow("count of errors:" + countOfErrors.get());
      }
      test.completeNow();
    });
  }

  @RepeatedTest(10)
  public void test3(VertxTestContext test) {
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();
    AtomicInteger connectionsInProcess = new AtomicInteger();
    Promise<Void> donePromise = Promise.promise();
    context.vertx().deployVerticle(() -> new VerticleBase() {
        @Override
        public Future<?> start() throws Exception {
          Redis redis = Redis.createClient(RedisClientLoadTest.this.context.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString(RedisClientLoadTest.redis.getRedisUri()));
          this.vertx.eventBus().consumer("test.redis.load").handler(m -> {
            connectionsInProcess.incrementAndGet();
            redis.connect()
              .compose(r -> {
                if (r.pendingQueueFull()) {
                  System.out.println("!!! pendingQueueFull");
                }

                r.exceptionHandler(e -> System.err.println("ERROR IN connect: " + e.getMessage()));

                return Future.succeededFuture(r);
              })
              .compose(r -> r.send(Request.cmd(Command.SET).arg("foo").arg("bar")).onComplete(res -> r.close()))
              .onComplete(res -> {
                if (res.failed()) {
                  //System.err.println(res.cause().getMessage());
                  countOfErrors.incrementAndGet();
                } else {
                  countOfSuccess.incrementAndGet();
                }

                if (countOfErrors.get() + countOfSuccess.get() >= iterations * instances) {
                  donePromise.complete();
                }
              });
          });

          return super.start();
        }
      }, new DeploymentOptions().setInstances(instances).setThreadingModel(ThreadingModel.WORKER).setWorkerPoolName("RedisLoadTest"))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          context.vertx().eventBus().publish("test.redis.load", true);
        }
      });

    donePromise.future().onComplete(res -> {

      if (countOfErrors.get() > 0) {
        test.failNow("count of errors:" + countOfErrors.get());
      }
      test.completeNow();
    });
  }
}
