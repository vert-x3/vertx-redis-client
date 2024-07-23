package io.vertx.test.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.net.NetClientOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.Repeat;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.containers.RedisStandalone;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(VertxUnitRunner.class)
public class RedisClientLoadTest {

  @ClassRule
  public static final RedisStandalone redisServer = new RedisStandalone();

  private final RedisOptions REDIS_OPTIONS = new RedisOptions()
    .setMaxPoolWaiting(1000)
    .setConnectionString(redisServer.getRedisUri())
    .setNetClientOptions(new NetClientOptions()
      .setConnectTimeout(100)
      .setReuseAddress(true)
      .setTcpKeepAlive(true)
      .setReconnectAttempts(100)
      .setTcpNoDelay(true)
    );

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testStatePooled(TestContext should) {
    final Async test = should.async();

    Redis redis = Redis.createClient(rule.vertx(), new RedisOptions()
      .setMaxPoolWaiting(1)
      .setConnectionString(redisServer.getRedisUri()));
    // acquire connection and close
    redis.connect()
      .onSuccess(conn -> {
        conn.close()
          .onFailure(should::fail)
          .onSuccess(ok -> test.complete());
      })
      .onFailure(should::fail);

  }

  @Test
  public void testStateExpired(TestContext should) {
    final Async test = should.async();

    Redis redis = Redis.createClient(rule.vertx(), new RedisOptions()
      .setMaxPoolWaiting(1)
      .setPoolRecycleTimeout(1)
      .setConnectionString(redisServer.getRedisUri()));
    // acquire connection and close
    redis.connect()
      .onSuccess(conn -> {
        rule.vertx()
            .setTimer(5L, v -> {
              conn.close()
                .onFailure(should::fail)
                .onSuccess(ok -> test.complete());
            });
      })
      .onFailure(should::fail);

  }

  @Test
  @Repeat(10)
  public void test1(TestContext should) {
    final Async test = should.async();
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();

    Promise<Void> donePromise = ((VertxInternal) rule.vertx()).promise();

    rule.vertx().deployVerticle(() -> new AbstractVerticle() {
        @Override
        public void start() throws Exception {
          Redis redis = Redis.createClient(rule.vertx(), REDIS_OPTIONS);
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
        }
      }, new DeploymentOptions().setInstances(instances))
      .onFailure(should::fail)
      .onSuccess(deploymentId -> {
        for (int i = 0; i < iterations; i++) {
          rule.vertx().eventBus().publish("test.redis.load", true);
        }
      });

    donePromise.future()
      .onFailure(should::fail)
      .onSuccess(ok -> {
        // I'm suspecting that the issue is happening now here.
        // when the test fails, an exception is thrown from the assert bellow
        should.assertTrue(countOfErrors.get() == 0, "Count of errors: " + countOfErrors.get());
        // then this triggers a re-run of the promise resolver which is not valid anymore
        test.complete();
      });
  }

  @Test
  @Repeat(10)
  public void test2(TestContext should) {
    final Async test = should.async();
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();

    Promise<Void> donePromise = Promise.promise();

    rule.vertx().deployVerticle(() -> new AbstractVerticle() {
        @Override
        public void start() throws Exception {
          Redis redis = Redis.createClient(rule.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString(redisServer.getRedisUri()));
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
        }
      }, new DeploymentOptions().setInstances(instances).setWorker(true).setWorkerPoolName("RedisLoadTest"))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          rule.vertx().eventBus().publish("test.redis.load", true);
        }
      });

    donePromise.future().onComplete(res -> {
      if (countOfErrors.get() > 0) {
        should.fail("count of errors:" + countOfErrors.get());
      }
      test.complete();
    });
  }

  @Test
  @Repeat(10)
  public void test3(TestContext should) {
    final Async test = should.async();
    int iterations = 100;
    int instances = 100;
    AtomicInteger countOfErrors = new AtomicInteger();
    AtomicInteger countOfSuccess = new AtomicInteger();
    AtomicInteger connectionsInProcess = new AtomicInteger();
    Promise<Void> donePromise = Promise.promise();
    rule.vertx().deployVerticle(() -> new AbstractVerticle() {
        @Override
        public void start() throws Exception {
          Redis redis = Redis.createClient(rule.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString(redisServer.getRedisUri()));
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
        }
      }, new DeploymentOptions().setInstances(instances).setWorker(true).setWorkerPoolName("RedisLoadTest"))
      .onComplete(res -> {
        for (int i = 0; i < iterations; i++) {
          rule.vertx().eventBus().publish("test.redis.load", true);
        }
      });

    donePromise.future().onComplete(res -> {

      if (countOfErrors.get() > 0) {
        should.fail("count of errors:" + countOfErrors.get());
      }
      test.complete();
    });
  }
}
