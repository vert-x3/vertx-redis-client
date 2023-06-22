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

import io.vertx.redis.client.impl.RedisCredentialsImpl;

/**
 * This interface is providing the username and password used for authentication against a redis server.
 */
public interface RedisCredentials {

  /**
   * Provide the username for authentication. Can be <code>null</code> to use "default".
   *
   * @return the username
   */
  String getUser();

  /**
   * Provide the password for authentication. Can be <code>null</code> in case no password is set.
   *
   * @return the password
   */
  String getPassword();

  /**
   * Create an empty instance of {@link RedisCredentials}. Username and password are both <code>null</code>.
   *
   * @return empty credentials
   */
  static RedisCredentials createEmptyCredentials(){
    return RedisCredentialsImpl.EMPTY_CREDENTIALS;
  }

  /**
   * Create {@link RedisCredentials} with "default" username and specified password.
   *
   * @param password password for authentication
   *
   * @return credentials
   */
  static RedisCredentials create(final String password) {
    return create(null, password);
  }

  /**
   * Create {@link RedisCredentials} with specified user and password.
   *
   * @param user user for authentication
   * @param password for authentication
   *
   * @return credentials
   */
  static RedisCredentials create(final String user, final String password) {
    return new RedisCredentialsImpl(user, password);
  }
}
