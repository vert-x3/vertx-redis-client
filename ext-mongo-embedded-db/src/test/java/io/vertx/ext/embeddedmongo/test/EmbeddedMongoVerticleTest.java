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

package io.vertx.ext.embeddedmongo.test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.ext.embeddedmongo.EmbeddedMongoVerticle;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class EmbeddedMongoVerticleTest extends VertxTestBase {

  @Override
  public VertxOptions getOptions() {
    // It can take some time to download the first time!
    return new VertxOptions().setMaxWorkerExecuteTime(30 * 60 * 1000);
  }

  @Test
  public void testEmbeddedMongo() {
    // Not really sure what to test here apart from start and stop
    vertx.deployVerticle("java:" + EmbeddedMongoVerticle.class.getName(), new DeploymentOptions().setWorker(true), onSuccess(deploymentID -> {
      assertNotNull(deploymentID);
      vertx.undeployVerticle(deploymentID, onSuccess(v -> {
        assertNull(v);
        testComplete();
      }));
    }));
    await();
  }
}
