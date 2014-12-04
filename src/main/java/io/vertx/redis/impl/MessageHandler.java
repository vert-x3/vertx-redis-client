package io.vertx.redis.impl;

/**
 * An interface mainly created to pass 2 parameters to pub/sub message handler (thus avoiding double casts)
 */
@FunctionalInterface
public interface MessageHandler {
    public void handle(String channelOrPattern, Reply[] replyData);
}
