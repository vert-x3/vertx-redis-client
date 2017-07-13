/**
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.impl;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RedisSubscriptions {

  private static final Logger log = LoggerFactory.getLogger(RedisSubscriptions.class);
  private final Vertx vertx;
  private final Map<String, Subscription> channelSubscribers = new ConcurrentHashMap<>();
  private final Map<String, Subscription> patternSubscribers = new ConcurrentHashMap<>();
  public RedisSubscriptions(Vertx vertx) {
    this.vertx = vertx;
  }

  public void registerChannelSubscribeHandler(String channel, MessageHandler messageHandler) {
    channelSubscribers.put(channel, new Subscription(messageHandler));
  }

  public void registerPatternSubscribeHandler(String pattern, MessageHandler messageHandler) {
    patternSubscribers.put(pattern, new Subscription(messageHandler));
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

  public void handleChannel(String channel, Reply[] replyData) {
    Subscription s = channelSubscribers.get(channel);
    if (s != null) {
      s.handle(channel, replyData);
    } else {
      log.warn("No pub/sub handler waiting for message");
    }
  }

  public void handlePattern(String pattern, Reply[] replyData) {
    Subscription s = patternSubscribers.get(pattern);
    if (s != null) {
      s.handle(pattern, replyData);
    } else {
      log.warn("No pub/sub handler waiting for message");
    }
  }

  public int channelSize() {
    return channelSubscribers.size();
  }

  public int patternSize() {
    return patternSubscribers.size();
  }

  public Set<String> channelNames() {
    return channelSubscribers.keySet();
  }

  public Set<String> patternNames() {
    return patternSubscribers.keySet();
  }

  private class Subscription {
    final Context context;
    final MessageHandler handler;

    Subscription(MessageHandler handler) {
      context = vertx.getOrCreateContext();
      this.handler = handler;
    }

    void handle(String channelOrPattern, Reply[] replyData) {
      context.runOnContext(v -> {
        handler.handle(channelOrPattern, replyData);
      });
    }
  }
}
