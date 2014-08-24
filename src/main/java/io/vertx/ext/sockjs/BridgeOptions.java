/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.ext.sockjs;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.ServiceHelper;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sockjs.spi.BridgeOptionsFactory;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Options
public interface BridgeOptions {

  static BridgeOptions options() {
    return factory.options();
  }

  static BridgeOptions optionsFromJson(JsonObject json) {
    return factory.options(json);
  }

  long getAuthTimeout();

  BridgeOptions setAuthTimeout(long authTimeout);

  String getAuthAddress();

  BridgeOptions setAuthAddress(String authAddress);

  int getMaxAddressLength();

  BridgeOptions setMaxAddressLength(int maxAddressLength);

  int getMaxHandlersPerSocket();

  BridgeOptions setMaxHandlersPerSocket(int maxHandlersPerSocket);

  long getPingTimeout();

  BridgeOptions setPingTimeout(long pingTimeout);

  long getReplyTimeout();

  BridgeOptions setReplyTimeout(long replyTimeout);

  BridgeOptions addInboundPermitted(JsonObject json);

  List<JsonObject> getInboundPermitteds();

  BridgeOptions addOutboundPermitted(JsonObject json);

  List<JsonObject> getOutboundPermitteds();

  static final BridgeOptionsFactory factory = ServiceHelper.loadFactory(BridgeOptionsFactory.class);

}
