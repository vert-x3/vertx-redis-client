package io.vertx.tests.redis.client;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.internal.ContextInternal;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.Request;

import java.util.Collections;

class PreservesContext {
  static void sendWithoutConnect(Redis client, TestContext test) {
    Async async = test.async();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.send(Request.cmd(Command.PING)).onComplete(result -> {
        test.assertTrue(result.succeeded());
        test.assertTrue(context == Vertx.currentContext());
        async.complete();
      });
    });
  }

  static void batchWithoutConnect(Redis client, TestContext test) {
    Async async = test.async();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.batch(Collections.singletonList(Request.cmd(Command.PING))).onComplete(result -> {
        test.assertTrue(result.succeeded());
        test.assertTrue(context == Vertx.currentContext());
        async.complete();
      });
    });
  }

  static void connect(Redis client, TestContext test) {
    Async async = test.async();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(connectResult -> {
        test.assertTrue(connectResult.succeeded());
        test.assertTrue(context == Vertx.currentContext());
        async.complete();
      });
    });
  }

  static void connectThenSend(Redis client, TestContext test) {
    Async async = test.async();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(connectResult -> {
        test.assertTrue(connectResult.succeeded());

        RedisConnection connection = connectResult.result();
        connection.send(Request.cmd(Command.PING)).onComplete(result -> {
          test.assertTrue(result.succeeded());
          test.assertTrue(context == Vertx.currentContext());
          async.complete();
        });
      });
    });
  }

  static void connectThenBatch(Redis client, TestContext test) {
    Async async = test.async();

    Context context = ContextInternal.current().duplicate();
    context.runOnContext(ignored -> {
      client.connect().onComplete(connectResult -> {
        test.assertTrue(connectResult.succeeded());

        RedisConnection connection = connectResult.result();
        connection.batch(Collections.singletonList(Request.cmd(Command.PING))).onComplete(result -> {
          test.assertTrue(result.succeeded());
          test.assertTrue(context == Vertx.currentContext());
          async.complete();
        });
      });
    });
  }
}
