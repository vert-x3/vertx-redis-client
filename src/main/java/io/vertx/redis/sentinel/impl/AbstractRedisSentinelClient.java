package io.vertx.redis.sentinel.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.impl.Command;
import io.vertx.redis.impl.RedisConnection;
import io.vertx.redis.impl.RedisSubscriptions;
import io.vertx.redis.impl.ResponseTransform;
import io.vertx.redis.sentinel.RedisSentinel;
import io.vertx.redis.sentinel.RedisSentinelCommand;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract Redis Sentinel Client.
 */
public abstract class AbstractRedisSentinelClient implements RedisSentinel {
    private final EventBus eb;
    private final RedisSubscriptions subscriptions;
    private final String encoding;
    private final Charset charset;
    private final Charset binaryCharset;
    private final String baseAddress;

    // we need 2 connections for redis sentinel, one for normal commands and a second in case we do pub/sub
    private final RedisConnection redis;
    private final RedisConnection pubsub;

    public AbstractRedisSentinelClient(Vertx vertx, RedisOptions config) {
        this.eb = vertx.eventBus();
        this.encoding = config.getEncoding();
        this.charset = Charset.forName(encoding);
        this.binaryCharset = Charset.forName("iso-8859-1");
        this.baseAddress = config.getAddress();

        // create a netClient for the connection
        final NetClient client = vertx.createNetClient(new NetClientOptions()
                .setTcpKeepAlive(config.isTcpKeepAlive())
                .setTcpNoDelay(config.isTcpNoDelay()));

        subscriptions = new RedisSubscriptions(vertx);

        redis = new RedisConnection(client, config);
        pubsub = new RedisConnection(client, config, subscriptions);
    }

    @Override
    public synchronized void close(Handler<AsyncResult<Void>> handler) {
        // this is a special case it should sent the message QUIT and then close the sockets
        final AtomicInteger cnt = new AtomicInteger(0);

        final Handler<AsyncResult<Void>> cb = v -> {
            if (cnt.incrementAndGet() == 2) {
                handler.handle(Future.succeededFuture());
            }
        };

        redis.disconnect(cb);
        pubsub.disconnect(cb);
    }

    private ResponseTransform getResponseTransformFor(RedisSentinelCommand command) {
        if (command == RedisSentinelCommand.INFO) {
            return ResponseTransform.INFO;
        }

        return ResponseTransform.NONE;
    }

    final void sendJsonArray(final RedisSentinelCommand command, final JsonArray args, final Handler<AsyncResult<JsonArray>> resultHandler) {
        sendSentinel(command, args, JsonArray.class, resultHandler);
    }

    final void sendJsonObject(final RedisSentinelCommand command, final JsonArray args, final Handler<AsyncResult<JsonObject>> resultHandler) {
        sendSentinel(command, args, JsonObject.class, resultHandler);
    }

    final void sendVoid(final RedisSentinelCommand command, final JsonArray args, final Handler<AsyncResult<Void>> resultHandler) {
        sendSentinel(command, args, Void.class, resultHandler);
    }

    final void sendString(final RedisSentinelCommand command, final JsonArray args, final Handler<AsyncResult<String>> resultHandler) {
        sendSentinel(command, args, String.class, resultHandler);
    }

    final <T> void sendSentinel(final RedisSentinelCommand command, final JsonArray redisArgs,
                        final Class<T> returnType,
                        final Handler<AsyncResult<T>> resultHandler) {
        sendSentinel(command, redisArgs, returnType, false, resultHandler);
    }

    final <T> void sendSentinel(final RedisSentinelCommand command, final JsonArray redisArgs, final Class<T> returnType,
                        final boolean binary,
                        final Handler<AsyncResult<T>> resultHandler) {

        final Command<T> cmd = new Command<>(command.getTokens(), redisArgs,
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
