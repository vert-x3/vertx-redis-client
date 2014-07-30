package io.vertx.ext.sockjs.impl;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sockjs.BridgeOptions;
import io.vertx.ext.sockjs.spi.BridgeOptionsFactory;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class BridgeOptionsFactoryImpl implements BridgeOptionsFactory {

  @Override
  public BridgeOptions options() {
    return new BridgeOptionsImpl();
  }

  @Override
  public BridgeOptions options(JsonObject json) {
    return new BridgeOptionsImpl(json);
  }
}
