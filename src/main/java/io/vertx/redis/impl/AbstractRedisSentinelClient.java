package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.sentinel.RedisSentinel;
import io.vertx.redis.sentinel.RedisSentinelCommand;

import java.util.List;

/**
 * Abstract Redis Sentinel Client.
 */
public abstract class AbstractRedisSentinelClient extends BaseRedisClient<RedisSentinelCommand> implements RedisSentinel {

  AbstractRedisSentinelClient(Vertx vertx, RedisOptions config) {
    super(vertx, config);
  }

  private ResponseTransform getResponseTransformFor(RedisSentinelCommand command) {
    if (command == RedisSentinelCommand.INFO) {
      return ResponseTransform.INFO;
    }

    return ResponseTransform.NONE;
  }

  @Override
  final <T> void send(final RedisSentinelCommand command, final List<?> redisArgs, final Class<T> returnType,
                      final boolean binary,
                      final Handler<AsyncResult<T>> resultHandler) {

    final Command<T> cmd = new Command<>(Vertx.currentContext(), command, redisArgs,
      binary ? binaryCharset : charset, getResponseTransformFor(command), returnType).handler(resultHandler);

    switch (command) {
      case PSUBSCRIBE:
        cmd.setExpectedReplies(redisArgs.size());

        for (Object obj : redisArgs) {
          String pattern = (String) obj;
          // compose the listening address as base + . + pattern
          final String vertxChannel = baseAddress + "." + pattern;
          subscriptions.registerPatternSubscribeHandler(pattern, (pattern1, replyData) -> {
            JsonObject replyMessage = new JsonObject();
            replyMessage.put("status", "ok");
            JsonObject message = new JsonObject();
            message.put("pattern", pattern1);
            message.put("channel", replyData[2].asType(String.class, encoding));
            message.put("message", replyData[3].asType(String.class, encoding));
            replyMessage.put("value", message);
            eb.send(vertxChannel, replyMessage);
          });
        }
        pubsub.send(cmd);
        break;

      case SUBSCRIBE:
        cmd.setExpectedReplies(redisArgs.size());

        for (Object obj : redisArgs) {
          String channel = (String) obj;
          // compose the listening address as base + . + channel
          final String vertxChannel = baseAddress + "." + channel;
          subscriptions.registerChannelSubscribeHandler(channel, (channel1, replyData) -> {
            JsonObject replyMessage = new JsonObject();
            replyMessage.put("status", "ok");
            JsonObject message = new JsonObject();
            message.put("channel", channel1);
            message.put("message", replyData[2].asType(String.class, encoding));
            replyMessage.put("value", message);
            eb.send(vertxChannel, replyMessage);
          });
        }
        pubsub.send(cmd);
        break;

      case PUNSUBSCRIBE:
        // unregister all channels
        if (redisArgs == null || redisArgs.size() == 0) {
          // unsubscribe all
          cmd.setExpectedReplies(subscriptions.patternSize());
          subscriptions.unregisterPatternSubscribeHandler(null);
        } else {
          cmd.setExpectedReplies(redisArgs.size());

          for (Object obj : redisArgs) {
            String pattern = (String) obj;
            subscriptions.unregisterPatternSubscribeHandler(pattern);
          }
        }
        pubsub.send(cmd);
        break;

      case UNSUBSCRIBE:
        // unregister all channels
        if (redisArgs == null || redisArgs.size() == 0) {
          // unsubscribe all
          cmd.setExpectedReplies(subscriptions.channelSize());
          subscriptions.unregisterChannelSubscribeHandler(null);
        } else {
          cmd.setExpectedReplies(redisArgs.size());

          for (Object obj : redisArgs) {
            String channel = (String) obj;
            subscriptions.unregisterChannelSubscribeHandler(channel);
          }
        }
        pubsub.send(cmd);
        break;
      case QUIT:
        // this is a special case that must be sent to all connections
        redis.send(cmd);
        pubsub.send(cmd);
        break;
      default:
        // all other commands are sent to the normal connection
        redis.send(cmd);
        break;
    }
  }

}
