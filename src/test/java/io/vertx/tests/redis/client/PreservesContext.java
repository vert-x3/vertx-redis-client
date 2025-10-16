package io.vertx.tests.redis.client;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.internal.ContextInternal;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxTestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

class PreservesContext {
  static void sendWithoutConnect(Redis client, VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.send(Request.cmd(Command.PING)).onComplete(test.succeeding(result -> {
        assertSame(context, Vertx.currentContext());
        checkpoint.flag();
      }));
    });
  }

  static void batchWithoutConnect(Redis client, VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.batch(List.of(Request.cmd(Command.PING))).onComplete(test.succeeding(result -> {
        assertSame(context, Vertx.currentContext());
        checkpoint.flag();
      }));
    });
  }

  static void connect(Redis client, VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(test.succeeding(conn -> {
        assertSame(context, Vertx.currentContext());
        conn.close().onComplete(test.succeeding(ignored2 -> {
          checkpoint.flag();
        }));
      }));
    });
  }

  static void connectThenSend(Redis client, VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(test.succeeding(conn -> {
        conn.send(Request.cmd(Command.PING)).onComplete(test.succeeding(result -> {
          assertSame(context, Vertx.currentContext());
          conn.close().onComplete(test.succeeding(ignored2 -> {
            checkpoint.flag();
          }));
        }));
      }));
    });
  }

  static void connectThenBatch(Redis client, VertxTestContext test) {
    Checkpoint checkpoint = test.checkpoint();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(test.succeeding(conn -> {
        conn.batch(List.of(Request.cmd(Command.PING))).onComplete(test.succeeding(result -> {
          assertSame(context, Vertx.currentContext());
          conn.close().onComplete(test.succeeding(ignored2 -> {
            checkpoint.flag();
          }));
        }));
      }));
    });
  }
}
