package io.vertx.tests.redis.client;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.spi.tracing.SpanKind;
import io.vertx.core.spi.tracing.TagExtractor;
import io.vertx.core.spi.tracing.VertxTracer;
import io.vertx.core.tracing.TracingPolicy;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(VertxExtension.class)
@Testcontainers
public class RedisTracingTest {
  @Container
  public static final RedisStandalone redis = new RedisStandalone();

  Vertx vertx;
  VertxTracer tracer;
  Redis client;

  @BeforeEach
  public void setup() {
    vertx = Vertx.builder()
      .withTracer(ignored -> new VertxTracer() {
        @Override
        public Object sendRequest(Context context, SpanKind kind, TracingPolicy tracingPolicy, Object request, String operation, BiConsumer headers, TagExtractor tagExtractor) {
          return tracer.sendRequest(context, kind, tracingPolicy, request, operation, headers, tagExtractor);
        }

        @Override
        public void receiveResponse(Context context, Object response, Object payload, Throwable failure, TagExtractor tagExtractor) {
          tracer.receiveResponse(context, response, payload, failure, tagExtractor);
        }
      }).build();
    client = Redis.createClient(vertx, new RedisOptions().setConnectionString(redis.getRedisUri()));
  }

  @AfterEach
  public void teardown(VertxTestContext test) {
    vertx.close().onComplete(test.succeedingThenComplete());
  }

  @Test
  public void success(VertxTestContext test) {
    testTracing(test, Request.cmd(Command.PING), true);
  }

  @Test
  public void failure(VertxTestContext test) {
    testTracing(test, Request.cmd(new CommandImpl("NONEXISTING COMMAND", 0, true, false, false)), false);
  }

  private void testTracing(VertxTestContext test, Request clientRequest, boolean success) {
    Object trace = new Object();
    List<String> actions = Collections.synchronizedList(new ArrayList<>());

    tracer = new VertxTracer() {
      @Override
      public Object sendRequest(Context context, SpanKind kind, TracingPolicy policy, Object request, String operation, BiConsumer headers, TagExtractor tagExtractor) {
        Map<String, String> tags = tagExtractor.extract(request);
        String redisPort = String.valueOf(redis.getPort());
        assertEquals("client", tags.get("span.kind"));
        assertEquals("redis", tags.get("db.system"));
        assertEquals("127.0.0.1", tags.get("network.peer.address"));
        assertEquals(redisPort, tags.get("network.peer.port"));
        assertEquals("localhost", tags.get("server.address"));
        assertEquals(redisPort, tags.get("server.port"));
        assertEquals(clientRequest.command().toString(), tags.get("db.operation.name"));
        actions.add("sendRequest");
        return trace;
      }

      @Override
      public void receiveResponse(Context context, Object response, Object payload, Throwable failure, TagExtractor tagExtractor) {
        assertSame(trace, payload);
        if (success) {
          assertNotNull(response);
          assertNull(failure);
        } else {
          assertNull(response);
          assertNotNull(failure);
        }
        actions.add("receiveResponse");
      }
    };

    vertx.runOnContext(ignored -> {
      client.send(clientRequest).onComplete(result -> {
        test.verify(() -> {
          assertEquals(success, result.succeeded());
          assertEquals(List.of("sendRequest", "receiveResponse"), actions);
        });
        test.completeNow();
      });
    });
  }
}
