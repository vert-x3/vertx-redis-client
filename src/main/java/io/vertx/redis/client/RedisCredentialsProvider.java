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

import io.vertx.core.Future;

/**
 * This interface allows to specify credentials used for authentication. Whenever a new connection is created,
 * the provider specified in {@link RedisOptions} is called to resolve the credentials.
 */
public interface RedisCredentialsProvider {

  /**
   * Resolve credentials used for connection to redis server.
   *
   * @return credentials to use for authentication.
   */
  Future<RedisCredentials> resolveCredentials();
}
