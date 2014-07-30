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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.ServiceHelper;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.sockjs.spi.SockJSServerFactory;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface SockJSServer {

  static SockJSServer sockJSServer(Vertx vertx, HttpServer httpServer) {
    return factory.sockJSServer(vertx, httpServer);
  }

  @GenIgnore
  @Fluent
  SockJSServer setHook(EventBusBridgeHook hook);

  @Fluent
  SockJSServer installApp(SockJSServerOptions options,
                              Handler<SockJSSocket> sockHandler);

  @Fluent
  SockJSServer bridge(SockJSServerOptions options, BridgeOptions bridgeOptions);

  void close();

  /*
  These applications are required by the SockJS protocol and QUnit tests
   */
  void installTestApplications();

  static final SockJSServerFactory factory = ServiceHelper.loadFactory(SockJSServerFactory.class);

}
