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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.redis.client.RedisConnectOptions;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.RedisSentinelConnectOptions;

@FunctionalInterface
interface Resolver {

  void resolve(String endpoint, RedisSentinelConnectOptions parameter, Handler<AsyncResult<RedisURI>> callback);
}
