package io.vertx.redis.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisSubscriptions {

  private final Map<String, MessageHandler> channelSubscribers = new ConcurrentHashMap<>();
  private final Map<String, MessageHandler> patternSubscribers = new ConcurrentHashMap<>();

  public void registerChannelSubscribeHandler(String channel, MessageHandler messageHandler) {
    channelSubscribers.put(channel, messageHandler);
  }

  public void registerPatternSubscribeHandler(String pattern, MessageHandler messageHandler) {
    patternSubscribers.put(pattern, messageHandler);
  }

  public void unregisterChannelSubscribeHandler(String channel) {
    if (channel == null) {
      channelSubscribers.clear();
    } else {
      channelSubscribers.remove(channel);
    }
  }

  public void unregisterPatternSubscribeHandler(String pattern) {
    if (pattern == null) {
      patternSubscribers.clear();
    } else {
      patternSubscribers.remove(pattern);
    }
  }

  public MessageHandler getChannelHandler(String channel) {
    return channelSubscribers.get(channel);
  }

  public MessageHandler getPatternHandler(String pattern) {
    return patternSubscribers.get(pattern);
  }

  public int channelSize() {
    return channelSubscribers.size();
  }

  public int patternSize() {
    return patternSubscribers.size();
  }
}
