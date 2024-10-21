package io.vertx.redis.client;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.impl.types.Multi;

/**
 * A handler that can be installed on a Redis connection using {@link RedisConnection#handler(Handler)}
 * in order to consume subscription messages and send them on the Vert.x {@linkplain Vertx#eventBus() event bus}.
 * <p>
 * The message sent to the Vert.x event bus is a {@link JsonObject} with the following format:
 * <pre>
 * {
 *   "status": "OK",
 *   "type": "message|subscribe|unsubscribe|pmessage|psubscribe|punsubscribe",
 *   "value": {
 *     "channel": "&lt;the channel&gt;", // for `[p]message`, `subscribe` and `unsubscribe`
 *     "message": "&lt;the message&gt;", // for `[p]message`
 *     "pattern": "&lt;the pattern&gt;", // for `pmessage`, `psubscribe` and `punsubscribe`
 *     "current": &lt;number of current subscriptions&gt; // for `[p]subscribe` and `[p]unsubscribe`
 *   }
 * }
 * </pre>
 * By default, the address to which the messages are sent is {@code io.vertx.redis.<the channel>}.
 * This can be changed by passing a different address prefix to {@link #create(Vertx, String)}.
 * For example, if the prefix is {@code com.example}, the address is {@code com.example.<the channel>}.
 * <p>
 * Note that for messages of types {@code psubscribe} and {@code punsubscribe}, there is no channel
 * name. Therefore, these messages are sent to the address of {@code <prefix>.<the pattern>}.
 */
public class EventBusHandler implements Handler<Response> {
  private static final Logger LOG = LoggerFactory.getLogger(EventBusHandler.class);

  private static final String DEFAULT_ADDRESS_PREFIX = "io.vertx.redis";

  private final String prefix;
  private final EventBus eventBus;

  /**
   * Creates a subscription handler that forwards messages to the event bus of the given {@code vertx}.
   * The default address prefix is used.
   */
  public static EventBusHandler create(Vertx vertx) {
    return new EventBusHandler(DEFAULT_ADDRESS_PREFIX, vertx.eventBus());
  }

  /**
   * Creates a subscription handler that forwards messages to the event bus of the given {@code vertx}.
   * The given address prefix is used.
   */
  public static EventBusHandler create(Vertx vertx, String addressPrefix) {
    return new EventBusHandler(addressPrefix, vertx.eventBus());
  }

  private EventBusHandler(String prefix, EventBus eventBus) {
    this.prefix = prefix;
    this.eventBus = eventBus;
  }

  @Override
  public void handle(Response reply) {
    // pub/sub messages are arrays
    if (reply instanceof Multi) {
      // Detect valid published messages according to https://redis.io/topics/pubsub
      String type = reply.get(0).toString();

      if (reply.size() == 3 && "message".equals(type)) {
        eventBus.send(prefix + "." + reply.get(1).toString(),
          new JsonObject()
            .put("status", "OK")
            .put("type", type)
            .put("value", new JsonObject()
              .put("channel", reply.get(1).toString())
              .put("message", reply.get(2).toString())));
        return;
      }

      if (reply.size() == 4 && "pmessage".equals(type)) {
        eventBus.send(prefix + "." + reply.get(1).toString(),
          new JsonObject()
            .put("status", "OK")
            .put("type", type)
            .put("value", new JsonObject()
              .put("pattern", reply.get(1).toString())
              .put("channel", reply.get(2).toString())
              .put("message", reply.get(3).toString())));
        return;
      }

      if (reply.size() == 3 && ("subscribe".equals(type) || "unsubscribe".equals(type))) {
        eventBus.send(prefix + "." + reply.get(1).toString(),
          new JsonObject()
            .put("status", "OK")
            .put("type", type)
            .put("value", new JsonObject()
              .put("channel", reply.get(1).toString())
              .put("current", reply.get(2).toLong())));
        return;
      }

      if (reply.size() == 3 && ("psubscribe".equals(type) || "punsubscribe".equals(type))) {
        eventBus.send(prefix + "." + reply.get(1).toString(),
          new JsonObject()
            .put("status", "OK")
            .put("type", type)
            .put("value", new JsonObject()
              .put("pattern", reply.get(1).toString())
              .put("current", reply.get(2).toLong())));
        return;
      }
    }

    LOG.warn("No handler waiting for message: " + reply);
  }
}
