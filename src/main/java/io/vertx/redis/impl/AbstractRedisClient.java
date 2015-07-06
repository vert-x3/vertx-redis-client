package io.vertx.redis.impl;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.redis.RedisClient;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRedisClient implements RedisClient {

  private final Vertx vertx;
  private final EventBus eb;
  private final RedisSubscriptions subscriptions;
  private final String encoding;
  private final Charset charset;
  private final Charset binaryCharset;
  private final String baseAddress;

  // we need 2 connections, one for normal commands and a second in case we do pub/sub
  private final RedisConnection redis;
  private final RedisConnection pubsub;

  AbstractRedisClient(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.eb = vertx.eventBus();
    this.encoding = config.getString("encoding", "UTF-8");
    this.charset = Charset.forName(encoding);
    this.binaryCharset = Charset.forName("iso-8859-1");
    this.baseAddress = config.getString("address", "io.vertx.redis");

    final String host = config.getString("host", "localhost");
    final int port = config.getInteger("port", 6379);

    // create a netClient for the connection
    final NetClient client = vertx.createNetClient(new NetClientOptions()
        .setTcpKeepAlive(config.getBoolean("tcpKeepAlive", true))
        .setTcpNoDelay(config.getBoolean("tcpNoDelay", true)));

    subscriptions = new RedisSubscriptions(vertx);

    redis = new RedisConnection(vertx, client, host, port);
    pubsub = new RedisConnection(vertx, client, host, port, subscriptions);
  }

  @Override
  public synchronized void close(Handler<AsyncResult<Void>> handler) {
    // this is a special case it should sent the message QUIT and then close the sockets
    final AtomicInteger cnt = new AtomicInteger(0);

    sendVoid("QUIT", null, v -> {
      if (cnt.incrementAndGet() == 2) {
        handler.handle(Future.succeededFuture());
      }
    });
  }

  private ResponseTransform getResponseTransformFor(String command) {
    if (command.equals("HGETALL")) {
      return ResponseTransform.ARRAY_TO_OBJECT;
    }
    if (command.equals("INFO")) {
      return ResponseTransform.INFO;
    }

    return ResponseTransform.NONE;
  }

  final void sendString(final String command, final JsonArray args, final Handler<AsyncResult<String>> resultHandler) {
    send(command, args, String.class, resultHandler);
  }

  final void sendLong(final String command, final JsonArray args, final Handler<AsyncResult<Long>> resultHandler) {
    send(command, args, Long.class, resultHandler);
  }

  final void sendVoid(final String command, final JsonArray args, final Handler<AsyncResult<Void>> resultHandler) {
    send(command, args, Void.class, resultHandler);
  }

  final void sendJsonArray(final String command, final JsonArray args, final Handler<AsyncResult<JsonArray>> resultHandler) {
    send(command, args, JsonArray.class, resultHandler);
  }

  final void sendJsonObject(final String command, final JsonArray args, final Handler<AsyncResult<JsonObject>> resultHandler) {
    send(command, args, JsonObject.class, resultHandler);
  }

  final <T> void send(final String command, final JsonArray redisArgs,
                      final Class<T> returnType,
                      final Handler<AsyncResult<T>> resultHandler) {

    send(command, redisArgs, returnType, false, resultHandler);
  }

  final <T> void send(final String command, final JsonArray redisArgs, final Class<T> returnType,
                      final boolean binary,
                      final Handler<AsyncResult<T>> resultHandler) {

    final Command<T> cmd = new Command<>(
        vertx.getOrCreateContext(),
        command,
        redisArgs,
        binary ? binaryCharset : charset,
        getResponseTransformFor(command),
        returnType).handler(resultHandler);

    switch (command) {
      case "PSUBSCRIBE":
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

      case "SUBSCRIBE":
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

      case "PUNSUBSCRIBE":
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

      case "UNSUBSCRIBE":
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
      case "QUIT":
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