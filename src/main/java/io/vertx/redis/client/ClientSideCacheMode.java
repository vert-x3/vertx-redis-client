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
package io.vertx.redis.client;

/**
 * The mode to caching clients will configure invalidations connections in.
 *
 * @see <a href="https://redis.io/docs/manual/client-side-caching/#the-redis-implementation-of-client-side-caching">Tracking modes</a>
 */
public enum ClientSideCacheMode {
  PER_CLIENT,
  BROADCAST
}
