package io.vertx.tests.redis.client;

import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.metrics.ClientMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.CommandImpl;
import io.vertx.tests.redis.containers.RedisStandalone;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class RedisClientMetricsTest {
  @ClassRule
  public static final RedisStandalone redis = new RedisStandalone();

  Vertx vertx;
  ClientMetrics metrics;
  Redis client;
  String reportedNamespace;

  @Before
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

  @After
  public void teardown(TestContext test) {
    vertx.close().onComplete(test.asyncAssertSuccess());
  }

  @Test
  public void success(TestContext test) {
    testClientMetrics(test, Request.cmd(Command.PING), true);
  }

  @Test
  public void failure(TestContext test) {
    testClientMetrics(test, Request.cmd(new CommandImpl("NONEXISTING COMMAND", 0, true, false, false)), false);
  }

  private void testClientMetrics(TestContext test, Request request, boolean success) {
    Async async = test.async();

    Object metric = new Object();
    List<String> actions = Collections.synchronizedList(new ArrayList<>());

    metrics = new ClientMetrics() {
      @Override
      public Object requestBegin(String uri, Object request) {
        actions.add("requestBegin");
        return metric;
      }

      @Override
      public void requestEnd(Object requestMetric, long bytesWritten) {
        test.assertTrue(requestMetric == metric);
        actions.add("requestEnd");
      }

      @Override
      public void responseBegin(Object requestMetric, Object response) {
        test.assertTrue(requestMetric == metric);
        actions.add("responseBegin");
      }

      @Override
      public void responseEnd(Object requestMetric, long bytesRead) {
        test.assertTrue(requestMetric == metric);
        actions.add("responseEnd");
      }

      @Override
      public void requestReset(Object requestMetric) {
        test.assertTrue(requestMetric == metric);
        actions.add("fail");
      }
    };

    vertx.runOnContext(ignored -> {
      client.send(request).onComplete(result -> {
        if (success) {
          test.assertTrue(result.succeeded());
          test.assertEquals(Arrays.asList("requestBegin", "requestEnd", "responseBegin", "responseEnd"), actions);
        } else {
          test.assertTrue(result.failed());
          test.assertEquals(Arrays.asList("requestBegin", "requestEnd", "fail"), actions);
        }
        test.assertEquals("the-namespace", reportedNamespace);
        async.complete();
      });
    });
  }
}
