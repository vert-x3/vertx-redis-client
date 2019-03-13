package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.sentinel.RedisSentinel;

import java.util.*;
import java.util.stream.Stream;

import static io.vertx.redis.client.Command.*;

/**
 * Implementation of {@link RedisSentinel}
 */
@Deprecated
public class RedisSentinelClientImpl implements RedisSentinel {

  private final Redis client;

  public static void create(Vertx vertx, io.vertx.redis.client.RedisOptions options, Handler<AsyncResult<RedisSentinel>> ready) {
    Redis.createSentinelClient(vertx, options, onReady -> {
      if (onReady.succeeded()) {
        ready.handle(Future.succeededFuture(new RedisSentinelClientImpl(onReady.result())));
      } else {
        ready.handle(Future.failedFuture(onReady.cause()));
      }
    });
  }

  /**
   * A helper method to package method parameters into JsonArray payload.
   *
   * Null parameters are ignored.
   *
   * @param parameters Call parameters
   * @return JsonArray that can be passed to send()
   */
  @SuppressWarnings("unchecked")
  private static List<?> toPayload(Object... parameters) {
    List<Object> result = new ArrayList<>(parameters.length);

    for (Object param : parameters) {
      // unwrap
      if (param instanceof JsonArray) {
        param = ((JsonArray) param).getList();
      }
      // unwrap
      if (param instanceof JsonObject) {
        param = ((JsonObject) param).getMap();
      }

      if (param instanceof Collection) {
        ((Collection) param).stream().filter(Objects::nonNull).forEach(result::add);
      } else if (param instanceof Map) {
        for (Map.Entry<?, ?> pair : ((Map<?, ?>) param).entrySet()) {
          result.add(pair.getKey());
          result.add(pair.getValue());
        }
      } else if (param instanceof Stream) {
        ((Stream) param).forEach(e -> {
          if (e instanceof Object[]) {
            Collections.addAll(result, (Object[]) e);
          } else {
            result.add(e);
          }
        });
      } else if (param instanceof Buffer) {
        result.add(((Buffer) param).getBytes());
      } else if (param != null) {
        result.add(param);
      }
    }
    return result;
  }

  private void send(io.vertx.redis.client.Command command, List arguments, Handler<AsyncResult<Response>> handler) {
    final Request req = Request.cmd(command);

    if (arguments != null) {
      for (Object o : arguments) {
        if (o == null) {
          req.nullArg();
        } else {
          req.arg(o.toString());
        }
      }
    }

    client.send(req, handler);
  }

  private void sendString(io.vertx.redis.client.Command command, List arguments, Handler<AsyncResult<String>> handler) {
    send(command, arguments, ar -> {
      if (ar.failed()) {
        handler.handle(Future.failedFuture(ar.cause()));
      } else {
        handler.handle(Future.succeededFuture(ar.result().toString()));
      }
    });
  }

  private static JsonArray toJsonArray(Response response) {
    final JsonArray json = new JsonArray();
    for (Response r : response) {
      switch (r.type()) {
        case INTEGER:
          json.add(r.toLong());
          break;
        case SIMPLE:
        case BULK:
          json.add(r.toString());
          break;
        case MULTI:
          json.add(toJsonArray(r));
          break;
      }
    }

    return json;
  }

  private void sendJsonArray(io.vertx.redis.client.Command command, List arguments, Handler<AsyncResult<JsonArray>> handler) {
    send(command, arguments, ar -> {
      if (ar.failed()) {
        handler.handle(Future.failedFuture(ar.cause()));
      } else {
        handler.handle(Future.succeededFuture(toJsonArray(ar.result())));
      }
    });
  }

  private void sendVoid(io.vertx.redis.client.Command command, List arguments, Handler<AsyncResult<Void>> handler) {
    send(command, arguments, ar -> {
      if (ar.failed()) {
        handler.handle(Future.failedFuture(ar.cause()));
      } else {
        handler.handle(Future.succeededFuture());
      }
    });
  }

  public RedisSentinelClientImpl(Redis redis) {
    this.client = redis;
  }

  @Override
  public void close(Handler<AsyncResult<Void>> handler) {
    client.close();
    if (handler != null) {
      handler.handle(Future.succeededFuture());
    }
  }

  @Override
  public RedisSentinel masters(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINEL, toPayload("MASTERS"), handler);
    return this;
  }

  @Override
  public RedisSentinel master(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINEL, toPayload("MASTER", name), handler);
    return this;
  }

  @Override
  public RedisSentinel slaves(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINEL, toPayload("SLAVES", name), handler);
    return this;
  }

  @Override
  public RedisSentinel sentinels(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINEL, toPayload("SENTINELS", name), handler);
    return this;
  }

  @Override
  public RedisSentinel getMasterAddrByName(String name, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SENTINEL, toPayload("GET-MASTER-ADDR-BY-NAME", name), handler);
    return this;
  }

  @Override
  public RedisSentinel reset(String pattern, Handler<AsyncResult<Void>> handler) {
    sendVoid(SENTINEL, toPayload("RESET", pattern), handler);
    return this;
  }

  @Override
  public RedisSentinel failover(String name, Handler<AsyncResult<String>> handler) {
    sendString(SENTINEL, toPayload("FAILOVER", name), handler);
    return this;
  }

  @Override
  public RedisSentinel ckquorum(String name, Handler<AsyncResult<String>> handler) {
    sendString(SENTINEL, toPayload("CKQUORUM", name), handler);
    return this;
  }

  @Override
  public RedisSentinel flushConfig(Handler<AsyncResult<Void>> handler) {
    sendVoid(SENTINEL, toPayload("FLUSHCONFIG"), handler);
    return this;
  }
}
