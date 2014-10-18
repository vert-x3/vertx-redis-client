package io.vertx.redis.impl;

@FunctionalInterface
public interface ReplyHandler {
    void handleReply(Reply reply);
}
