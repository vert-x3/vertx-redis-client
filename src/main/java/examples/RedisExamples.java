package examples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.*;

/**
 * These are the examples used in the documentation.
 *
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class RedisExamples {

  public void example1(Vertx vertx) {
    Redis.createClient(vertx, new RedisOptions(), onCreate -> {
      if (onCreate.succeeded()) {
        Redis client = onCreate.result();
      }
    });
  }

  public void example2(Vertx vertx) {
    RedisOptions options = new RedisOptions()
      .setPassword("abracadabra")
      .setSelect(1);

    Redis.createClient(vertx, options, onCreate -> {
      if (onCreate.succeeded()) {
        Redis client = onCreate.result();
      }
    });
  }

  public void example3(Redis client) {
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
        .addEndpoint(SocketAddress.inetSocketAddress(5000, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5001, "127.0.0.1"))
        .addEndpoint(SocketAddress.inetSocketAddress(5002, "127.0.0.1"))
        .setMasterName("sentinel7000")
        .setRole(RedisRole.MASTER),
      onCreate -> {
        // assuming we got a connection to the master node
        // query the info for the node
        onCreate.result()
          .send(Request.cmd(Command.INFO), info -> {
            // do something...
          });
      });
  }

  public void example6() {
    final RedisOptions options = new RedisOptions()
      .addEndpoint(SocketAddress.inetSocketAddress(7000, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7001, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7002, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7003, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7004, "127.0.0.1"))
      .addEndpoint(SocketAddress.inetSocketAddress(7005, "127.0.0.1"));
  }

  public void example7(Vertx vertx) {

    Redis.createClient(vertx, new RedisOptions(), onCreate -> {
      if (onCreate.succeeded()) {
        Redis client = onCreate.result();

        client.handler(message -> {
          // do whatever you need to do with your message
        });
      }
    });
  }

  public void example8(Redis redis) {

    redis.send(Request.cmd(Command.PUBLISH).arg("channel1").arg("Hello World!"), res -> {
      if (res.succeeded()) {
        // published!
      }
    });
  }

  public void example9(Vertx vertx) {

    Redis.createClient(vertx, SocketAddress.domainSocketAddress("/tmp/redis.sock"), onCreate -> {
      if (onCreate.succeeded()) {
        Redis client = onCreate.result();
      }
    });
  }

  public void example10() {

    class RedisVerticle extends AbstractVerticle {

      private static final int MAX_RECONNECT_RETRIES = 16;

      private RedisOptions options = new RedisOptions();
      private Redis client;

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
      private void createRedisClient(Handler<AsyncResult<Redis>> handler) {
        Redis.createClient(vertx, options, onCreate -> {
          if (onCreate.succeeded()) {
            client = onCreate.result();
            // make sure the client is reconnected on error
            client.exceptionHandler(e -> {
              // attempt to reconnect
              attemptReconnect(0);
            });
          }
          // allow further processing
          handler.handle(onCreate);
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
