/*
 * Copyright 2019 Red Hat, Inc.
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
package io.vertx.redis.client.impl;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.impl.VertxInternal;
import io.vertx.redis.client.CachingRedisOptions;
import io.vertx.redis.client.spi.RedisClientCache;
import io.vertx.redis.client.ClientSideCacheMode;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.util.List;

public class CachingRedisConnection implements RedisConnection {

  private final VertxInternal vertx;
  private final RedisClientCache cache;
  private final RedisConnection delegate;
  private final RedisStandaloneConnection actual;
  private final Request trackingCommand;

  public CachingRedisConnection(VertxInternal vertx, RedisClientCache cache, RedisConnection delegate, int clientId, CachingRedisOptions options) {
    this.vertx = vertx;
    this.cache = cache;
    this.delegate = delegate;
    this.trackingCommand = trackingCommand(clientId, options);

    if (delegate instanceof PooledRedisConnection) {
      this.actual = (RedisStandaloneConnection) ((PooledRedisConnection) delegate).actual();
    } else if (delegate instanceof RedisSentinelConnection) {
      this.actual = (RedisStandaloneConnection) ((RedisSentinelConnection) delegate).actual();
    } else {
      this.actual = (RedisStandaloneConnection) delegate;
    }
  }

  @Override
  public RedisConnection exceptionHandler(Handler<Throwable> handler) {
    return delegate.exceptionHandler(handler);
  }

  @Override
  public RedisConnection handler(@Nullable Handler<Response> handler) {
    return delegate.handler(handler);
  }

  @Override
  public RedisConnection pause() {
    return delegate.pause();
  }

  @Override
  public RedisConnection resume() {
    return delegate.resume();
  }

  @Override
  public RedisConnection fetch(long amount) {
    return delegate.fetch(amount);
  }

  @Override
  public RedisConnection endHandler(@Nullable Handler<Void> endHandler) {
    return delegate.endHandler(endHandler);
  }

  @Override
  public Future<@Nullable Response> send(Request request) {
    final RequestImpl req = (RequestImpl) request;
    final Promise<Response> promise = vertx.promise();

    // Result could possibly be cached, check cache first
    if (req.isCacheable()) {
      final Response cachedValue = cache.get(req.cacheKey());
      if (cachedValue != null) {
        promise.complete(cachedValue);
        return promise.future();
      }
    }

    configureTracking()
      .onFailure(promise::fail)
      .onSuccess(v ->
        delegate
          .send(request)
          .onFailure(promise::fail)
          .onSuccess(resp -> {
            if (req.isCacheable() && resp != null) {
              cache.put(req.cacheKey(), resp);
            }
            promise.complete(resp);
          }));

    return promise.future();
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> requests) {
    // Currently we don't support batching because order can matter. If an earlier request in the
    // batch changes the value that a later, cacheable, request reads, we can't guarantee we
    // invalidate in time. Rather than risk returning unexpected or stale values, simple don't deal
    // with caching during a batch.
    return delegate.batch(requests);
  }

  @Override
  public Future<Void> close() {
    return delegate.close();
  }

  @Override
  public boolean pendingQueueFull() {
    return delegate.pendingQueueFull();
  }

  private Future<Void> configureTracking() {
    final Promise<Void> trackingPromise = vertx.promise();

    if (actual.isTrackingConfigured()) {
      trackingPromise.complete();
    } else {
      actual.send(trackingCommand).onComplete(ar -> {
        if (ar.succeeded()) {
          actual.setTrackingConfigured();
        }

        trackingPromise.handle(ar.mapEmpty());
      });
    }

    return trackingPromise.future();
  }

  private static Request trackingCommand(int clientId, CachingRedisOptions options) {
    final Request request = Request.cmd(Command.CLIENT)
      .arg("TRACKING")
      .arg("ON")
      .arg("REDIRECT")
      .arg(clientId);

    for (String prefix : options.getPrefixes()) {
      request.arg("PREFIX").arg(prefix);
    }

    if (ClientSideCacheMode.BROADCAST == options.getMode()) {
      request.arg("BCAST");
    }

    return request;
  }
}
