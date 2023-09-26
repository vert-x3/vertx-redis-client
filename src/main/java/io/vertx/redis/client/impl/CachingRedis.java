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

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.impl.cache.CacheKey;

import java.util.Collection;

public interface CachingRedis extends Redis {

  /**
   * Flush the local cache.
   *
   * <p>
   * This operation only clears the local cache and has no interaction with the server.
   *
   * @return a future indicating the status of the operation
   */
  Future<Void> flush();

  /**
   * Set a handler to be called when invalidation is performed.
   *
   * <p>
   * The client will clear the keys before this handler is invoked. It is not recommended to modify
   * the cache as a part of this handler. The primary function is for instrumentation.
   *
   * @param handler a handler that accepts the keys which were invalidated
   * @return fluent self
   */
  CachingRedis invalidationHandler(Handler<Collection<CacheKey>> handler);
}
