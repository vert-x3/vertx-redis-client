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
import io.vertx.ext.sockjs.spi.SockJSServerOptionsFactory;

import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Options
public interface SockJSServerOptions {

  static SockJSServerOptions options() {
    return factory.options();
  }

  static SockJSServerOptions optionsFromJson(JsonObject json) {
    return factory.options(json);
  }

  long getSessionTimeout();

  SockJSServerOptions setSessionTimeout(long sessionTimeout);

  boolean isInsertJSESSIONID();

  SockJSServerOptions setInsertJSESSIONID(boolean insertJSESSIONID);

  long getHeartbeatPeriod();

  SockJSServerOptions setHeartbeatPeriod(long heartbeatPeriod);

  int getMaxBytesStreaming();

  SockJSServerOptions setMaxBytesStreaming(int maxBytesStreaming);

  String getPrefix();

  SockJSServerOptions setPrefix(String prefix);

  String getLibraryURL();

  SockJSServerOptions setLibraryURL(String libraryURL);

  SockJSServerOptions addDisabledTransport(String subProtocol);

  Set<String> getDisabledTransports();

  static final SockJSServerOptionsFactory factory = ServiceHelper.loadFactory(SockJSServerOptionsFactory.class);

}
