package examples;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.RedisTransaction;
import io.vertx.redis.Script;

/**
 * These are the examples used in the documentation.
 *
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class Examples {

  public void example1(Vertx vertx) {
    RedisOptions config = new RedisOptions()
      .setHost("127.0.0.1");

    RedisClient redis = RedisClient.create(vertx, config);
  }

  public void example2(Vertx vertx) {
    RedisClient redis = RedisClient.create(vertx, new RedisOptions());

    redis.get("mykey", res -> {
      if (res.succeeded()) {
        // so something...
      }
    });
  }

  public void example3(Vertx vertx) {

    // register a handler for the incoming message the naming the Redis module will use is base address + '.' + redis channel
    vertx.eventBus().<JsonObject>consumer("io.vertx.redis.channel1", received -> {
      // do whatever you need to do with your message
      JsonObject value = received.body().getJsonObject("value");
      // the value is a JSON doc with the following properties
      // channel - The channel to which this message was sent
      // pattern - Pattern is present if you use psubscribe command and is the pattern that matched this message channel
      // message - The message payload
    });

    RedisClient redis = RedisClient.create(vertx, new RedisOptions());

    redis.subscribe("channel1", res -> {
      if (res.succeeded()) {
        // so something...
      }
    });
  }

  public void example4(Vertx vertx) {

    RedisClient redis = RedisClient.create(vertx, new RedisOptions());

    redis.publish("channel1", "Hello World!", res -> {
      if (res.succeeded()) {
        // so something...
      }
    });
  }

  public void example5() {
    RedisClient client = RedisClient.create(Vertx.vertx(), new RedisOptions().setAddress("127.0.0.1").setPort(6379));
    RedisTransaction transaction = client.transaction();
    transaction.multi(event -> {
      transaction.hgetall("mykey", getAllEvent -> {
        if (getAllEvent.succeeded() && "QUEUED".equals(getAllEvent.result())) {
          transaction.exec(execEvent -> System.out.println(execEvent.result()));
        } else {
          transaction.discard(discardEvent -> {
          });
        }
      });
    });
  }

  public void example6() {
    RedisClient client = RedisClient.create(Vertx.vertx(), new RedisOptions().setAddress("127.0.0.1").setPort(6379));
    client.evalScript(Script.create("return 42"), null, null, res -> {
      if (res.succeeded()) {
        System.out.println(res.result().getInteger(0));
      } else {
        System.err.println(res.cause());
      }
    });
  }
}
