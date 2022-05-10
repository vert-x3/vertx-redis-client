package io.vertx.redis.client.impl;

import io.vertx.redis.client.Command;
import io.vertx.redis.client.impl.keys.BeginSearch;
import io.vertx.redis.client.impl.keys.FindKeys;

public interface CommandInternal extends Command {

  byte[] getBytes();

  BeginSearch beginSearch();

  FindKeys findKeys();

  boolean needsGetKeys();
}
