package examples;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;

import java.util.Arrays;

/**
 *
 * These are the examples used in the documentation.
 *
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class Examples {

  public void example1(Vertx vertx) {
    JsonObject config = new JsonObject()
        .put("host", "127.0.0.1");

    RedisClient redis = RedisClient.create(vertx, config);
  }

  public void example2(Vertx vertx) {
    RedisClient redis = RedisClient.create(vertx, new JsonObject());

    redis.get("mykey", res -> {
      if (res.succeeded()) {
        // so something...
      }
    });
  }

  public void example3(Vertx vertx) {

    // register a handler for the incoming message the naming the Redis module will use is base address + '.' + redis channel
    vertx.eventBus().<JsonObject>consumer("redis.sub.channel1", received -> {
      // do whatever you need to do with your message
      JsonObject value = received.body().getJsonObject("value");
      // the value is a JSON doc with the following properties
      // channel - The channel to which this message was sent
      // pattern - Pattern is present if you use psubscribe command and is the pattern that matched this message channel
      // message - The message payload
    });

    RedisClient redis = RedisClient.create(vertx, new JsonObject());

    redis.subscribe(Arrays.asList("channel1"), res -> {
        if (res.succeeded()) {
            // so something...
        }
    });
  }

  public void example4(Vertx vertx) {

    RedisClient redis = RedisClient.create(vertx, new JsonObject());

    redis.publish("channel1", "Hello World!", res -> {
        if (res.succeeded()) {
            // so something...
        }
    });
  }
}