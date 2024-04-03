package io.vertx.redis.client.test;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.tracing.SpanKind;
import io.vertx.core.spi.tracing.TagExtractor;
import io.vertx.core.spi.tracing.VertxTracer;
import io.vertx.core.tracing.TracingOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.impl.CommandImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.GenericContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@RunWith(VertxUnitRunner.class)
public class RedisTracingTest {
  @ClassRule
  public static final GenericContainer<?> redis = new GenericContainer<>("redis:7")
    .withExposedPorts(6379);

  Vertx vertx;
  VertxTracer tracer;
  Redis client;

  @Before
  public void setup() {
    vertx = Vertx.vertx(new VertxOptions().setTracingOptions(
      new TracingOptions().setFactory(ignored -> new VertxTracer() {
        @Override
        public Object sendRequest(Context context, SpanKind kind, TracingPolicy tracingPolicy, Object request, String operation, BiConsumer headers, TagExtractor tagExtractor) {
          return tracer.sendRequest(context, kind, tracingPolicy, request, operation, headers, tagExtractor);
        }
        @Override
        public void receiveResponse(Context context, Object response, Object payload, Throwable failure, TagExtractor tagExtractor) {
          tracer.receiveResponse(context, response, payload, failure, tagExtractor);
        }
      }))
    );
    client = Redis.createClient(vertx, new RedisOptions().setConnectionString("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort()));
  }

  @After
  public void teardown(TestContext test) {
    vertx.close().onComplete(test.asyncAssertSuccess());
  }

  @Test
  public void success(TestContext test) {
    testTracing(test, Request.cmd(Command.PING), true);
  }

  @Test
  public void failure(TestContext test) {
    testTracing(test, Request.cmd(new CommandImpl("NONEXISTING COMMAND", 0, true, false, false)), false);
  }

  private void testTracing(TestContext test, Request clientRequest, boolean success) {
    Async async = test.async();

    Object trace = new Object();
    List<String> actions = Collections.synchronizedList(new ArrayList<>());

    tracer = new VertxTracer() {
      @Override
      public Object sendRequest(Context context, SpanKind kind, TracingPolicy policy, Object request, String operation, BiConsumer headers, TagExtractor tagExtractor) {
        Map<String, String> tags = tagExtractor.extract(request);
        String redisPort = String.valueOf(redis.getFirstMappedPort());
        test.assertEquals("client", tags.get("span.kind"));
        test.assertEquals("redis", tags.get("db.type"));
        test.assertEquals("127.0.0.1", tags.get("network.peer.address"));
        test.assertEquals(redisPort, tags.get("network.peer.port"));
        test.assertEquals("localhost", tags.get("server.address"));
        test.assertEquals(redisPort, tags.get("server.port"));
        test.assertEquals(clientRequest.command().toString(), tags.get("db.statement"));
        actions.add("sendRequest");
        return trace;
      }

      @Override
      public void receiveResponse(Context context, Object response, Object payload, Throwable failure, TagExtractor tagExtractor) {
        test.assertTrue(payload == trace);
        if (success) {
          test.assertNotNull(response);
          test.assertNull(failure);
        } else {
          test.assertNull(response);
          test.assertNotNull(failure);
        }
        actions.add("receiveResponse");
      }
    };

    vertx.runOnContext(ignored -> {
      client.send(clientRequest).onComplete(result -> {
        test.assertEquals(success, result.succeeded());
        test.assertEquals(Arrays.asList("sendRequest", "receiveResponse"), actions);
        async.complete();
      });
    });
  }
}
