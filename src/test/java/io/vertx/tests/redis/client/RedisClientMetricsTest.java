package io.vertx.tests.redis.client;

import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.metrics.ClientMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.CommandImpl;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisClientMetricsTest {
  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  Vertx vertx;
  ClientMetrics metrics;
  Redis client;
  String reportedNamespace;

  @BeforeEach
  public void setup() {
    vertx = Vertx.builder()
      .withMetrics(ignored -> new VertxMetrics() {
        @Override
        public ClientMetrics<?, ?, ?> createClientMetrics(SocketAddress remoteAddress, String type, String namespace) {
          reportedNamespace = namespace;
          return metrics;
        }
      })
      .build();
    client = Redis.createClient(vertx, new RedisOptions().setConnectionString(redis.getRedisUri()).setMetricsName("the-namespace"));
  }

  @AfterEach
  public void teardown(VertxTestContext test) {
    vertx.close().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void success(VertxTestContext test) {
    testClientMetrics(test, Request.cmd(Command.PING), true);
  }

  @Test
  public void failure(VertxTestContext test) {
    testClientMetrics(test, Request.cmd(new CommandImpl("NONEXISTING COMMAND", 0, true, false, false)), false);
  }

  private void testClientMetrics(VertxTestContext test, Request request, boolean success) {
    Object metric = new Object();
    List<String> actions = Collections.synchronizedList(new ArrayList<>());

    metrics = new ClientMetrics() {
      @Override
      public Object init() {
        return metric;
      }

      @Override
      public void requestBegin(Object requestMetric, String uri, Object request) {
        actions.add("requestBegin");
      }

      @Override
      public void requestEnd(Object requestMetric, long bytesWritten) {
        test.verify(() -> {
          assertSame(metric, requestMetric);
        });
        actions.add("requestEnd");
      }

      @Override
      public void responseBegin(Object requestMetric, Object response) {
        test.verify(() -> {
          assertSame(metric, requestMetric);
        });
        actions.add("responseBegin");
      }

      @Override
      public void responseEnd(Object requestMetric, long bytesRead) {
        test.verify(() -> {
          assertSame(metric, requestMetric);
        });
        actions.add("responseEnd");
      }

      @Override
      public void requestReset(Object requestMetric) {
        test.verify(() -> {
          assertSame(metric, requestMetric);
        });
        actions.add("fail");
      }
    };

    vertx.runOnContext(ignored -> {
      client.send(request).onComplete(result -> {
        test.verify(() -> {
          if (success) {
            assertTrue(result.succeeded());
            assertEquals(Arrays.asList("requestBegin", "requestEnd", "responseBegin", "responseEnd"), actions);
          } else {
            assertTrue(result.failed());
            assertEquals(Arrays.asList("requestBegin", "requestEnd", "fail"), actions);
          }
          assertEquals("the-namespace", reportedNamespace);
        });
        test.completeNow();
      });
    });
  }
}
