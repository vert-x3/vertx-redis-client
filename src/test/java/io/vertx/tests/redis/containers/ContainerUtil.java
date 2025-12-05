package io.vertx.tests.redis.containers;

import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.GenericContainer;

final class ContainerUtil {
  private static final Logger LOG = LoggerFactory.getLogger(ContainerUtil.class);

  // for some reason, `container.start()` sometimes fail (in CI) with
  //
  // ```
  // failed to set up container networking:
  // driver failed programming external connectivity on endpoint ...:
  // failed to bind host port for ...:
  // address already in use
  // ```
  //
  // turns out it's usually enough to stop the container and start over
  // we attempt to retry at most 10 times, that should be enough
  static void start(GenericContainer<?> container) {
    ContainerLaunchException lastException = null;

    for (int i = 0; i < 10; i++) {
      try {
        container.start();
        return;
      } catch (ContainerLaunchException e) {
        LOG.info("Attempt " + (i + 1) + " failed: " + e.getMessage());
        lastException = e;
        container.stop();
      }
    }

    throw new ContainerLaunchException("Could not start container " + container.getDockerImageName(), lastException);
  }

  static void stop(GenericContainer<?> container) {
    container.stop();
  }
}
