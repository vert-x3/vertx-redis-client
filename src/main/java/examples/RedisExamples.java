package examples;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.*;

import java.util.concurrent.atomic.AtomicReference;

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
    Redis.createSentinelClient(
      vertx,
      new RedisOptions()
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

  public void example10(Vertx vertx) {

    final RedisOptions options = new RedisOptions();

    Redis.createClient(vertx, options, onCreate -> {
      if (onCreate.succeeded()) {
        final AtomicReference<Redis> client = new AtomicReference<>(onCreate.result());
        // configure the number of retries to reconnect
        final Handler<Integer> reconnect = new Handler<Integer>() {
          @Override
          public void handle(Integer retry) {
            if (retry < 0) {
              // stop it has been 16 times without success!
            } else {
              // retry with backoff up to 1280ms
              long backoff = (long) (Math.pow(2, 16 - Math.max(retry, 9)) * 10);

              vertx.setTimer(backoff, timer -> {
                Redis.createClient(vertx, options, onReconnect -> {
                  if (onReconnect.succeeded()) {
                    client.set(onReconnect.result());
                    // Reconnected!
                    client.get().exceptionHandler(ex -> {
                      // attempt to reconnect with in 16 times
                      this.handle(16);
                    });
                  } else {
                    this.handle(retry - 1);
                  }
                });
              });
            }
          }
        };

        client.get().exceptionHandler(ex -> {
          // attempt to reconnect with in 16 times
          reconnect.handle(16);
        });
      }
    });
  }
}
