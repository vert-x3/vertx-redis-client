package examples;

import io.vertx.core.*;
import io.vertx.redis.client.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * These are the examples used in the documentation.
 *
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class RedisExamples {

  public void example1(Vertx vertx) {
    Redis.createClient(vertx)
      .connect()
      .onSuccess(conn -> {
        // use the connection
      });
  }

  public void example2(Vertx vertx) {
    Redis.createClient(
      vertx,
      // The client handles REDIS URLs. The select database as per spec is the
      // numerical path of the URL and the password is the password field of
      // the URL authority
      "redis://:abracadabra@localhost:6379/1")
      .connect()
      .onSuccess(conn -> {
        // use the connection
      });
  }

  public void example3(RedisConnection client) {
    // wrap the client in a API object that is aware
    // of the redis commands and it's arity
    RedisAPI redis = RedisAPI.api(client);

    redis
      .get("mykey")
      .onSuccess(value -> {
        // do something...
      });
  }

  public void example4(Response response) {
    // this is a multi redis response (think of it as an array)
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
      .connect()
      .onSuccess(conn -> {
        conn.send(Request.cmd(Command.INFO))
          .onSuccess(info -> {
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
      .connect()
      .onSuccess(conn -> {
        conn.handler(message -> {
          // do whatever you need to do with your message
        });
      });
  }

  public void example8(RedisConnection redis) {

    redis.send(Request.cmd(Command.PUBLISH).arg("channel1").arg("Hello World!"))
      .onSuccess(res -> {
        // published!
      });
  }

  public void example9(Vertx vertx) {

    Redis.createClient(vertx, "unix:///tmp/redis.sock")
      .connect()
      .onSuccess(conn -> {
        // so something...
      });
  }

  public void example10() {

    class RedisVerticle extends AbstractVerticle {

      private static final int MAX_RECONNECT_RETRIES = 16;

      private final RedisOptions options = new RedisOptions();
      private Redis redis;
      private RedisConnection client;
      private final AtomicBoolean CONNECTING = new AtomicBoolean();

      @Override
      public void start() {
        createRedisClient()
          .onSuccess(conn -> {
            // connected to redis!
          });
      }

      /**
       * Will create a redis client and setup a reconnect handler when there is
       * an exception in the connection.
       */
      private Future<RedisConnection> createRedisClient() {
        Promise<RedisConnection> promise = Promise.promise();

        // make sure to invalidate old connection if present
        if (redis != null) {
          redis.close();;
        }

        if (CONNECTING.compareAndSet(false, true)) {
          redis = Redis.createClient(vertx, options);
          redis
            .connect()
            .onSuccess(conn -> {
              client = conn;

              // make sure the client is reconnected on error
              // eg, the underlying TCP connection is closed but the client side doesn't know it yet
              //     the client tries to use the staled connection to talk to server. An exceptions will be raised
              conn.exceptionHandler(e -> {
                attemptReconnect(0);
              });

              // make sure the client is reconnected on connection close
              // eg, the underlying TCP connection is closed with normal 4-Way-Handshake
              //     this handler will be notified instantly
              conn.endHandler(placeHolder -> {
                attemptReconnect(0);
              });

              // allow further processing
              promise.complete(conn);
              CONNECTING.set(false);
            }).onFailure(t -> {
              promise.fail(t);
              CONNECTING.set(false);
            });
        } else {
          promise.complete();
        }

        return promise.future();
      }

      /**
       * Attempt to reconnect up to MAX_RECONNECT_RETRIES
       */
      private void attemptReconnect(int retry) {
        if (retry > MAX_RECONNECT_RETRIES) {
          // we should stop now, as there's nothing we can do.
          CONNECTING.set(false);
        } else {
          // retry with backoff up to 10240 ms
          long backoff = (long) (Math.pow(2, Math.min(retry, 10)) * 10);

          vertx.setTimer(backoff, timer -> {
            createRedisClient()
              .onFailure(t -> attemptReconnect(retry + 1));
          });
        }
      }
    }
  }

  public void example11(Vertx vertx) {
    Redis.createClient(vertx, "redis://localhost:7006")
      .send(Request.cmd(Command.PING))
      .onSuccess(res -> {
        // Should have received a pong...
      });
  }

  public void example12(Vertx vertx) {
    Redis.createClient(
      vertx,
      new RedisOptions()
        .setConnectionString("redis://localhost:7006")
        // allow at max 8 connections to redis
        .setMaxPoolSize(8)
        // allow 32 connection requests to queue waiting
        // for a connection to be available.
        .setMaxWaitingHandlers(32))
      .send(Request.cmd(Command.PING))
      .onSuccess(res -> {
        // Should have received a pong...
      });
  }

  public void example13(Vertx vertx) {
    Redis.createClient(
      vertx,
      new RedisOptions()
        .setType(RedisClientType.REPLICATION)
        .addConnectionString("redis://localhost:7000")
        .setMaxPoolSize(4)
        .setMaxPoolWaiting(16))
      .connect()
      .onSuccess(conn -> {
        // this is a replication client.
        // write operations will end up in the master node
        conn.send(Request.cmd(Command.SET).arg("key").arg("value"));
        // and read operations will end up in the replica nodes if available
        conn.send(Request.cmd(Command.GET).arg("key"));
      });
  }
}
