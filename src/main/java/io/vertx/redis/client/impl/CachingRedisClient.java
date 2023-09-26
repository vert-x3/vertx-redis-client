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
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.redis.client.CachingRedisOptions;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.cache.CacheKey;
import io.vertx.redis.client.impl.cache.LRURedisClientCache;
import io.vertx.redis.client.impl.types.MultiType;
import io.vertx.redis.client.impl.types.PushType;
import io.vertx.redis.client.spi.RedisClientCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CachingRedisClient implements CachingRedis {

  private static final Logger LOG = LoggerFactory.getLogger(CachingRedis.class);
  private static final String INVALIDATIONS_CHANNEL = "__redis__:invalidate";
  private static final Request CLIENT_ID_COMMAND = Request.cmd(Command.CLIENT).arg("ID");
  private static final Request SUBSCRIBE_COMMAND = Request.cmd(Command.SUBSCRIBE).arg(INVALIDATIONS_CHANNEL);
  private static final int MAX_RECONNECT_ATTEMPTS = 5;

  private final VertxInternal vertx;
  private final Redis redis;
  private final CachingRedisOptions options;
  private final RedisClientCache cache;
  private final AtomicBoolean closed = new AtomicBoolean();
  private Promise<Integer> clientIdPromise;
  private Handler<Collection<CacheKey>> invalidationHandler;

  public CachingRedisClient(Vertx vertx, Redis redis, CachingRedisOptions options) {
    if (redis instanceof CachingRedis) {
      throw new IllegalArgumentException("CachingRedisClient can not be self referential");
    }

    this.vertx = (VertxInternal) vertx;
    this.redis = redis;
    this.options = options;
    this.cache = discoverCacheImpl(options);
  }

  @Override
  public Future<RedisConnection> connect() {
    if (closed.get()) {
      return vertx.getContext().failedFuture("Client is closed");
    }

    final Promise<RedisConnection> promise = vertx.promise();

    getClientId().onComplete(id -> {
      if (id.failed()) {
        promise.fail(id.cause());
        return;
      }

      final int clientId = id.result();

      redis.connect().onComplete(connect -> {
        if (connect.failed()) {
          promise.fail(connect.cause());
          return;
        }

        final RedisConnection connection = connect.result();

        promise.complete(new CachingRedisConnection(vertx, cache, connection, clientId, options));
      });
    });

    return promise.future();
  }

  @Override
  public void close() {
    if (closed.compareAndSet(false, true)) {
      redis.close();
      cache.close();
    }
  }

  @Override
  public Future<@Nullable Response> send(Request request) {
    if (closed.get()) {
      return vertx.getContext().failedFuture("Client is closed");
    }

    return connect().compose(conn ->
      conn.send(request)
        .eventually(() ->
          conn.close().onFailure(LOG::warn)));
  }

  @Override
  public Future<List<@Nullable Response>> batch(List<Request> commands) {
    return redis.batch(commands);
  }

  @Override
  public Future<Void> flush() {
    cache.flush();
    return Future.succeededFuture();
  }

  @Override
  public CachingRedis invalidationHandler(Handler<Collection<CacheKey>> handler) {
    this.invalidationHandler = handler;
    return this;
  }

  Future<Integer> getClientId() {
    synchronized (this) {
      if (clientIdPromise != null) {
        return clientIdPromise.future();
      } else {
        clientIdPromise = vertx.promise();
      }
    }

    createInvalidationsConnection(0).onComplete(clientIdPromise);

    return clientIdPromise.future();
  }

  private Future<Integer> createInvalidationsConnection(int attempt) {
    if (closed.get()) {
      // we're shutting down, the endHandler will have sent us here so don't try to reconnect
      return Future.failedFuture("Client is closed");
    }
    if (attempt > MAX_RECONNECT_ATTEMPTS) {
      return Future.failedFuture("max reconnect attempts exceeded");
    }

    return redis
      .connect()
      .compose(this::initInvalidationsConnection)
      .recover(e -> createInvalidationsConnection(attempt + 1));
  }

  private Future<Integer> initInvalidationsConnection(RedisConnection connection) {
    connection.exceptionHandler(e -> {
      LOG.warn(e);
      // flush the cache to ensure no stale data is served while reconnecting
      cache.flush();
      connection.close();
      reconnectInvalidationsConnection();
    });

    connection.endHandler(v -> {
      // flush the cache to ensure no stale data is served while reconnecting
      cache.flush();
      reconnectInvalidationsConnection();
    });

    return connection
      .send(CLIENT_ID_COMMAND)
      .map(Response::toInteger)
      .compose(clientId -> connection
        .send(SUBSCRIBE_COMMAND)
        .onSuccess(r -> connection.handler(this::handleInvalidationMessage))
        .map(clientId));
  }

  private void reconnectInvalidationsConnection() {
    if (closed.get()) {
      return;
    }
    synchronized (this) {
      clientIdPromise = vertx.promise();
    }

    createInvalidationsConnection(0).onComplete(clientIdPromise);
  }

  private void handleInvalidationMessage(Response message) {
    // only process messages like `invalidate: [key1,key2,key3]` or `invalidate: key`
    if (message instanceof PushType) {
      final PushType pushMessage = (PushType) message;
      final String pushCommand = pushMessage.get(0).toString();

      if ("INVALIDATE".equalsIgnoreCase(pushCommand)) {
        final Response invalidationMessage = pushMessage.get(1);
        final Set<CacheKey> invalidatedKeys = new HashSet<>();

        if (invalidationMessage instanceof MultiType) {
          final Iterator<Response> manyKeys = invalidationMessage.iterator();

          do {
            final CacheKey key = new CacheKey(manyKeys.next().toString());
            cache.delete(key);
            invalidatedKeys.add(key);
          } while (manyKeys.hasNext());
        } else {
          final CacheKey key = new CacheKey(invalidationMessage.toString());
          cache.delete(key);
          invalidatedKeys.add(key);
        }

        if (invalidationHandler != null) {
          invalidationHandler.handle(invalidatedKeys);
        }
      }
    }
  }

  private static RedisClientCache discoverCacheImpl(CachingRedisOptions options) {
    final List<RedisClientCache> impls = new ArrayList<>();
    ServiceLoader.load(RedisClientCache.class).iterator().forEachRemaining(impls::add);

    if (impls.isEmpty()) {
      return new LRURedisClientCache(options);
    }

    final RedisClientCache selected = impls.get(0);

    if (impls.size() > 1) {
      LOG.debug("Multiple implementations of RedisClientCache found: "
        + impls.stream().map(c -> c.getClass().getName()).collect(Collectors.joining(", "))
        + ", using: " + selected.getClass().getName());
    }

    return selected;
  }
}
