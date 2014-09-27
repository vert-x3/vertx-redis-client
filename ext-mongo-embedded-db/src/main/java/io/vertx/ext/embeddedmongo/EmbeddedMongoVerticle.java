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

package io.vertx.ext.embeddedmongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

/**
 * This verticle wraps an embedded MongoDB instance.
 *
 * It's useful if you have an application that uses MongoDB and you want to write unit tests for it
 * that run without having to manually set-up a MongoDB.
 *
 * Just deploy the verticle before your test.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class EmbeddedMongoVerticle extends AbstractVerticle {

  private MongodExecutable exe;

  @Override
  public void start() throws Exception {

    if (vertx != null && !vertx.context().isWorker()) {
      throw new IllegalStateException("Must be started as worker verticle!");
    }

    JsonObject config = vertx.context().config();

    int port = config.getInteger("port", 27018);

    IMongodConfig embeddedConfig = new MongodConfigBuilder().
      version(Version.Main.PRODUCTION).
      net(new Net(port, Network.localhostIsIPv6())).
      build();

    exe = MongodStarter.getDefaultInstance().prepare(embeddedConfig);
    exe.start();
  }

  @Override
  public void stop() {
    exe.stop();
  }
}
