package io.vertx.redis.client.impl;

import io.vertx.core.Handler;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.impl.clientconnection.Endpoint;
import io.vertx.core.net.impl.clientconnection.EndpointProvider;
import io.vertx.core.net.impl.clientconnection.Lease;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

@RunWith(VertxUnitRunner.class)
public class RedisConnectionManagerTest {

  @Rule
  public final RunTestOnContext rule = new RunTestOnContext();

  @ClassRule
  public static final GenericContainer<?> container = new GenericContainer<>("redis:6.0.6")
    .withExposedPorts(6379);

  /**
   * In this test, the map compute creates a pool object for each get connection call, while it should just do it
   * once as the key is always the same.
   */
  @Test
  public void testConnectionManager(TestContext should) {

    final Async test = should.async();

    VertxInternal vertxInternal = (VertxInternal) rule.vertx();
    RedisOptions options = new RedisOptions()
      .addConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .setMaxPoolSize(10)
      .setMaxPoolWaiting(10);

    RedisConnectionManager connectionManager = new RedisConnectionManager(vertxInternal, options);
    final AtomicInteger cnt = new AtomicInteger();

    vertxInternal.setPeriodic(500, v -> {
      System.out.println(cnt.incrementAndGet());
      connectionManager.getConnection("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort(), null)
        .onFailure(should::fail)
        .onSuccess(conn -> {
          System.out.println(conn);

          conn.send(Request.cmd(Command.PING))
            .onFailure(should::fail)
            .onSuccess(System.out::println);
        });
    });


  }

  /**
   * In this test, the map compute only creates a single pool object (linked to the endpoint)
   */
  @Test
  public void akward(TestContext should) {
    final Async test = should.async();
    Map<RedisConnectionManager.ConnectionKey, Object> endpointMap = new ConcurrentHashMap<>();

    VertxInternal vertx = (VertxInternal) rule.vertx();
    ContextInternal ctx = vertx.getContext();
    NetClient netClient = vertx.createNetClient();
    RedisOptions options = new RedisOptions()
      .addConnectionString("redis://" + container.getContainerIpAddress() + ":" + container.getFirstMappedPort())
      .setMaxPoolSize(10)
      .setMaxPoolWaiting(10);

    EndpointProvider<RedisConnectionManager.ConnectionKey, Lease<RedisConnection>> endpointProvider =
      (key, _ctx, dispose) -> new RedisConnectionManager.RedisEndpoint(
        vertx,
        netClient,
        options,
        dispose,
        _ctx,
        key);

    final AtomicInteger cnt = new AtomicInteger();

    vertx.setPeriodic(500, t -> {
      System.out.println(cnt.incrementAndGet());
      RedisConnectionManager.ConnectionKey key = new RedisConnectionManager.ConnectionKey("redis://localhost:7006", null);
      endpointMap.computeIfAbsent(key, k -> endpointProvider.create(key, ctx, () -> {}));
    });
  }
}
