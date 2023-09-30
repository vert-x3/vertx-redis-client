package io.vertx.redis.client.util;

import io.vertx.redis.client.RedisConnectOptions;
import io.vertx.redis.client.impl.RedisURI;

/**
 * This helper class provides some useful methods to resolve some options
 */
public class OptionsResolver {

  private OptionsResolver() {
    // hide constructor
  }

  /**
   * Resolve the password based on the provided parameters.
   * The priority is {@link RedisURI} > {@link RedisConnectOptions}
   *
   * @param redisURI provided redis uri
   * @param options provided options
   * @return the resolved password
   */
  public static String resolvePassword(final RedisURI redisURI, final RedisConnectOptions options) {
    return redisURI.password() != null ? redisURI.password() : options.getPassword();
  }

  /**
   * Resolve the user based on the provided parameters.
   * The priority is {@link RedisURI} > {@link RedisConnectOptions}
   *
   * @param redisURI provided redis uri
   * @param options provided options
   * @return the resolved user
   */
  public static String resolveUser(final RedisURI redisURI, final RedisConnectOptions options) {
    return redisURI.user() != null ? redisURI.user() : options.getUser();
  }
}
