package examples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.redis.client.*;

/**
 * These are the examples used in the documentation.
 *
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class RedisExamples {

  public void example1(Vertx vertx) {
    Redis.createClient(vertx, new RedisOptions())
      .connect(onConnect -> {
        if (onConnect.succeeded()) {
          RedisConnection client = onConnect.result();
        }
      });
  }

  public void example2(Vertx vertx) {
    Redis.createClient(vertx, "redis://:abracadabra@localhost:6379/1")
      .connect(onConnect -> {
        if (onConnect.succeeded()) {
          RedisConnection client = onConnect.result();
        }
      });
  }

  public void example3(RedisConnection client) {
    RedisAPI redis = RedisAPI.api(client);

    redis.get("mykey", res -> {
      if (res.succeeded()) {
        // so something...
      }
    });
  }

  public void example4(Response response) {
    // this is a multi redis response (think of it as an array
    if (response.type() == ResponseType.MULTI) {
      for (Response item : response) {
        // do something with item...
      }
    }
  }

  public void example5(Vertx vertx) {
    Redis.createClient(
      vertx,
      new RedisOptions()
        .setType(RedisClientType.SENTINEL)
        .addConnectionString("redis://127.0.0.1:5000")
        .addConnectionString("redis://127.0.0.1:5001")
        .addConnectionString("redis://127.0.0.1:5002")
        .setMasterName("sentinel7000")
        .setRole(RedisRole.MASTER))
      .connect(onConnect -> {
        // assuming we got a connection to the master node
        // query the info for the node
        onConnect.result()
          .send(Request.cmd(Command.INFO), info -> {
            // do something...
          });
      });
  }

  public void example6() {
    final RedisOptions options = new RedisOptions()
      .addConnectionString("redis://127.0.0.1:7000")
      .addConnectionString("redis://127.0.0.1:7001")
      .addConnectionString("redis://127.0.0.1:7002")
      .addConnectionString("redis://127.0.0.1:7003")
      .addConnectionString("redis://127.0.0.1:7004")
      .addConnectionString("redis://127.0.0.1:7005");
  }

  public void example7(Vertx vertx) {

    Redis.createClient(vertx, new RedisOptions())
      .connect(onConnect -> {
        if (onConnect.succeeded()) {
          RedisConnection client = onConnect.result();

          client.handler(message -> {
            // do whatever you need to do with your message
          });
        }
      });
  }

  public void example8(RedisConnection redis) {

    redis.send(Request.cmd(Command.PUBLISH).arg("channel1").arg("Hello World!"), res -> {
      if (res.succeeded()) {
        // published!
      }
    });
  }

  public void example9(Vertx vertx) {

    Redis.createClient(vertx, "unix:///tmp/redis.sock")
      .connect(onConnect -> {
        if (onConnect.succeeded()) {
          RedisConnection client = onConnect.result();
        }
      });
  }

  public void example10() {

    class RedisVerticle extends AbstractVerticle {

      private static final int MAX_RECONNECT_RETRIES = 16;

      private RedisOptions options = new RedisOptions();
      private RedisConnection client;

      @Override
      public void start() {
        createRedisClient(onCreate -> {
          if (onCreate.succeeded()) {
            // connected to redis!
          }
        });
      }

      /**
       * Will create a redis client and setup a reconnect handler when there is
       * an exception in the connection.
       */
      private void createRedisClient(Handler<AsyncResult<RedisConnection>> handler) {
        Redis.createClient(vertx, options)
          .connect(onConnect -> {
            if (onConnect.succeeded()) {
              client = onConnect.result();
              // make sure the client is reconnected on error
              client.exceptionHandler(e -> {
                // attempt to reconnect
                attemptReconnect(0);
              });
            }
            // allow further processing
            handler.handle(onConnect);
          });
      }

      /**
       * Attempt to reconnect up to MAX_RECONNECT_RETRIES
       */
      private void attemptReconnect(int retry) {
        if (retry > MAX_RECONNECT_RETRIES) {
          // we should stop now, as there's nothing we can do.
        } else {
          // retry with backoff up to 1280ms
          long backoff = (long) (Math.pow(2, MAX_RECONNECT_RETRIES - Math.max(MAX_RECONNECT_RETRIES - retry, 9)) * 10);

          vertx.setTimer(backoff, timer -> createRedisClient(onReconnect -> {
            if (onReconnect.failed()) {
              attemptReconnect(retry + 1);
            }
          }));
        }
      }
    }
  }
}
