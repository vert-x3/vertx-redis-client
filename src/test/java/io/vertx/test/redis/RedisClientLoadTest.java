package io.vertx.test.redis;

import java.util.concurrent.atomic.AtomicInteger;

import io.vertx.core.*;
import io.vertx.core.impl.VertxInternal;
import io.vertx.ext.unit.junit.Repeat;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import io.vertx.core.net.NetClientOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

@RunWith(VertxUnitRunner.class)
public class RedisClientLoadTest {

  @ClassRule
  public static final GenericContainer<?> redisServer = new GenericContainer<>("redis:6.2.6")
    .withExposedPorts(6379);

  private final RedisOptions REDIS_OPTIONS = new RedisOptions()
    .setMaxPoolWaiting(1000)
    .setConnectionString("redis://" + redisServer.getContainerIpAddress() + ":" + redisServer.getFirstMappedPort())
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
      .setConnectionString("redis://" + redisServer.getContainerIpAddress() + ":" + redisServer.getFirstMappedPort()));
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
      .setConnectionString("redis://" + redisServer.getContainerIpAddress() + ":" + redisServer.getFirstMappedPort()));
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
          Redis redis = Redis.createClient(rule.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString("redis://" + redisServer.getContainerIpAddress() + ":" + redisServer.getFirstMappedPort()));
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
          Redis redis = Redis.createClient(rule.vertx(), new RedisOptions().setMaxPoolWaiting(1000).setConnectionString("redis://" + redisServer.getContainerIpAddress() + ":" + redisServer.getFirstMappedPort()));
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

//	@Test
//	void test2(Vertx vertx, VertxTestContext context) {
//		int iterations = 100;
//		int instances = 100;
//		AtomicInteger countOfErrors = new AtomicInteger();
//		var checkpoints = context.checkpoint(iterations * instances);
//		vertx.deployVerticle(() -> new AbstractVerticle() {
//			@Override
//			public void start() throws Exception {
//				RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379/0");
//				var connection = redisClient.connect();
//				var commands = connection.async();
//				this.vertx.eventBus().consumer("test.redis.load").handler(m -> {
//					Future.fromCompletionStage(
//					commands.set("foo","bar").toCompletableFuture())
//					.onComplete(res -> {
//						checkpoints.flag();
//						if(res.failed()) {
//							countOfErrors.incrementAndGet();
//						}
//					});
//				});
//			}
//		}, new DeploymentOptions().setWorker(true).setInstances(instances))
//		.onComplete(res -> {
//			for (int i = 0; i < iterations; i++) {
//				vertx.eventBus().publish("test.redis.load", true);
//			}
//		});
//
//		try {
//			context.awaitCompletion(10, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//		}
//
//		if(countOfErrors.get() > 0) {
//			context.failNow("count of errors:" + countOfErrors.get());
//		} else {
//			context.completeNow();
//		}
//	}
}
