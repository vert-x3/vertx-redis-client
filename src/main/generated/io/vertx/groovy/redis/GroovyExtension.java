package io.vertx.groovy.redis;
public class GroovyExtension {
  public static io.vertx.redis.RedisClient blpop(io.vertx.redis.RedisClient j_receiver, java.lang.String key, int seconds, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.blpop(key,
      seconds,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient blpopMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> keys, int seconds, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.blpopMany(keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      seconds,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient brpop(io.vertx.redis.RedisClient j_receiver, java.lang.String key, int seconds, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.brpop(key,
      seconds,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient brpopMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> keys, int seconds, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.brpopMany(keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      seconds,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clientKill(io.vertx.redis.RedisClient j_receiver, java.util.Map<String, Object> filter, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clientKill(filter != null ? new io.vertx.redis.op.KillFilter(io.vertx.lang.groovy.ConversionHelper.toJsonObject(filter)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Long> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clusterGetkeysinslot(io.vertx.redis.RedisClient j_receiver, long slot, long count, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clusterGetkeysinslot(slot,
      count,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clusterInfo(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clusterInfo(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clusterNodes(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clusterNodes(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clusterSlaves(io.vertx.redis.RedisClient j_receiver, java.lang.String nodeId, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clusterSlaves(nodeId,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient clusterSlots(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clusterSlots(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient command(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.command(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient commandGetkeys(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.commandGetkeys(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient commandInfo(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> commands, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.commandInfo(commands != null ? commands.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient configGet(io.vertx.redis.RedisClient j_receiver, java.lang.String parameter, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.configGet(parameter,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient eval(io.vertx.redis.RedisClient j_receiver, java.lang.String script, java.util.List<java.lang.String> keys, java.util.List<java.lang.String> args, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.eval(script,
      keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      args != null ? args.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient evalsha(io.vertx.redis.RedisClient j_receiver, java.lang.String sha1, java.util.List<java.lang.String> keys, java.util.List<java.lang.String> values, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.evalsha(sha1,
      keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      values != null ? values.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hgetall(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hgetall(key,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonObject(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hkeys(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hkeys(key,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hmget(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.List<java.lang.String> fields, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hmget(key,
      fields != null ? fields.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hmset(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.Map<String, Object> values, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hmset(key,
      values != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(values) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hvals(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hvals(key,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient info(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.info(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonObject(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient infoSection(io.vertx.redis.RedisClient j_receiver, java.lang.String section, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Map<String, Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.infoSection(section,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonObject> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonObject(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient keys(io.vertx.redis.RedisClient j_receiver, java.lang.String pattern, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.keys(pattern,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient lrange(io.vertx.redis.RedisClient j_receiver, java.lang.String key, long from, long to, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.lrange(key,
      from,
      to,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient mget(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.mget(key,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient mgetMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> keys, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.mgetMany(keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient migrate(io.vertx.redis.RedisClient j_receiver, java.lang.String host, int port, java.lang.String key, int destdb, long timeout, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.migrate(host,
      port,
      key,
      destdb,
      timeout,
      options != null ? new io.vertx.redis.op.MigrateOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient mset(io.vertx.redis.RedisClient j_receiver, java.util.Map<String, Object> keyvals, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.mset(keyvals != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(keyvals) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient msetnx(io.vertx.redis.RedisClient j_receiver, java.util.Map<String, Object> keyvals, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.msetnx(keyvals != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(keyvals) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Long> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient psubscribe(io.vertx.redis.RedisClient j_receiver, java.lang.String pattern, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.psubscribe(pattern,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient psubscribeMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> patterns, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.psubscribeMany(patterns != null ? patterns.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient pubsubChannels(io.vertx.redis.RedisClient j_receiver, java.lang.String pattern, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.pubsubChannels(pattern,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient pubsubNumsub(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> channels, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.pubsubNumsub(channels != null ? channels.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient role(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.role(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient scriptExists(io.vertx.redis.RedisClient j_receiver, java.lang.String script, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.scriptExists(script,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient scriptExistsMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> scripts, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.scriptExistsMany(scripts != null ? scripts.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient sdiff(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.List<java.lang.String> cmpkeys, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sdiff(key,
      cmpkeys != null ? cmpkeys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient setWithOptions(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String value, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.setWithOptions(key,
      value,
      options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient setBinaryWithOptions(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.buffer.Buffer value, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.setBinaryWithOptions(key,
      value,
      options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Void> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.wrap(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient sinter(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> keys, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sinter(keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient slowlogGet(io.vertx.redis.RedisClient j_receiver, int limit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.slowlogGet(limit,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient smembers(io.vertx.redis.RedisClient j_receiver, java.lang.String key, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.smembers(key,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient sort(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sort(key,
      options != null ? new io.vertx.redis.op.SortOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient spopMany(io.vertx.redis.RedisClient j_receiver, java.lang.String key, int count, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.spopMany(key,
      count,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient srandmemberCount(io.vertx.redis.RedisClient j_receiver, java.lang.String key, int count, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.srandmemberCount(key,
      count,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient subscribe(io.vertx.redis.RedisClient j_receiver, java.lang.String channel, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.subscribe(channel,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient subscribeMany(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> channels, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.subscribeMany(channels != null ? channels.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient sunion(io.vertx.redis.RedisClient j_receiver, java.util.List<java.lang.String> keys, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sunion(keys != null ? keys.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient time(io.vertx.redis.RedisClient j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.time(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrange(io.vertx.redis.RedisClient j_receiver, java.lang.String key, long start, long stop, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrange(key,
      start,
      stop,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrangeWithOptions(io.vertx.redis.RedisClient j_receiver, java.lang.String key, long start, long stop, io.vertx.redis.op.RangeOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrangeWithOptions(key,
      start,
      stop,
      options,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrangebylex(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String min, java.lang.String max, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrangebylex(key,
      min,
      max,
      options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrangebyscore(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String min, java.lang.String max, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrangebyscore(key,
      min,
      max,
      options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrevrange(io.vertx.redis.RedisClient j_receiver, java.lang.String key, long start, long stop, io.vertx.redis.op.RangeOptions options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrevrange(key,
      start,
      stop,
      options,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrevrangebylex(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String max, java.lang.String min, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrevrangebylex(key,
      max,
      min,
      options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zrevrangebyscore(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String max, java.lang.String min, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrevrangebyscore(key,
      max,
      min,
      options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient scan(io.vertx.redis.RedisClient j_receiver, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.scan(cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient sscan(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient hscan(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient zscan(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient geoaddMany(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.List<java.util.Map<String, Object>> members, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geoaddMany(key,
      members != null ? members.stream().map(elt -> elt != null ? new io.vertx.redis.op.GeoMember(io.vertx.lang.groovy.ConversionHelper.toJsonObject(elt)) : null).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.Long> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient geohash(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String member, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geohash(key,
      member,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient geohashMany(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.List<java.lang.String> members, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geohashMany(key,
      members != null ? members.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient geopos(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String member, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geopos(key,
      member,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient geoposMany(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.util.List<java.lang.String> members, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geoposMany(key,
      members != null ? members.stream().map(elt -> elt).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient georadius(io.vertx.redis.RedisClient j_receiver, java.lang.String key, double longitude, double latitude, double radius, io.vertx.redis.op.GeoUnit unit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadius(key,
      longitude,
      latitude,
      radius,
      unit,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient georadiusWithOptions(io.vertx.redis.RedisClient j_receiver, java.lang.String key, double longitude, double latitude, double radius, io.vertx.redis.op.GeoUnit unit, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadiusWithOptions(key,
      longitude,
      latitude,
      radius,
      unit,
      options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient georadiusbymember(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String member, double radius, io.vertx.redis.op.GeoUnit unit, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadiusbymember(key,
      member,
      radius,
      unit,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisClient georadiusbymemberWithOptions(io.vertx.redis.RedisClient j_receiver, java.lang.String key, java.lang.String member, double radius, io.vertx.redis.op.GeoUnit unit, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadiusbymemberWithOptions(key,
      member,
      radius,
      unit,
      options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction clientKill(io.vertx.redis.RedisTransaction j_receiver, java.util.Map<String, Object> filter, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.clientKill(filter != null ? new io.vertx.redis.op.KillFilter(io.vertx.lang.groovy.ConversionHelper.toJsonObject(filter)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction exec(io.vertx.redis.RedisTransaction j_receiver, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<Object>>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.exec(handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(io.vertx.core.AsyncResult<io.vertx.core.json.JsonArray> ar) {
        handler.handle(ar.map(event -> io.vertx.lang.groovy.ConversionHelper.fromJsonArray(event)));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction hmset(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.util.Map<String, Object> values, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hmset(key,
      values != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(values) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction migrate(io.vertx.redis.RedisTransaction j_receiver, java.lang.String host, int port, java.lang.String key, int destdb, long timeout, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.migrate(host,
      port,
      key,
      destdb,
      timeout,
      options != null ? new io.vertx.redis.op.MigrateOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction mset(io.vertx.redis.RedisTransaction j_receiver, java.util.Map<String, Object> keyvals, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.mset(keyvals != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(keyvals) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction msetnx(io.vertx.redis.RedisTransaction j_receiver, java.util.Map<String, Object> keyvals, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.msetnx(keyvals != null ? io.vertx.lang.groovy.ConversionHelper.toJsonObject(keyvals) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction setWithOptions(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String value, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.setWithOptions(key,
      value,
      options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction setBinaryWithOptions(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, io.vertx.core.buffer.Buffer value, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.setBinaryWithOptions(key,
      value,
      options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction sort(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sort(key,
      options != null ? new io.vertx.redis.op.SortOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction zrangebylex(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String min, java.lang.String max, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrangebylex(key,
      min,
      max,
      options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction zrangebyscore(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String min, java.lang.String max, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrangebyscore(key,
      min,
      max,
      options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction zrevrangebylex(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String max, java.lang.String min, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrevrangebylex(key,
      max,
      min,
      options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction zrevrangebyscore(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String max, java.lang.String min, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zrevrangebyscore(key,
      max,
      min,
      options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction scan(io.vertx.redis.RedisTransaction j_receiver, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.scan(cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction sscan(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.sscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction hscan(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.hscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction zscan(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String cursor, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.zscan(key,
      cursor,
      options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction geoaddMany(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.util.List<java.util.Map<String, Object>> members, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.geoaddMany(key,
      members != null ? members.stream().map(elt -> elt != null ? new io.vertx.redis.op.GeoMember(io.vertx.lang.groovy.ConversionHelper.toJsonObject(elt)) : null).collect(java.util.stream.Collectors.toList()) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction georadiusWithOptions(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, double longitude, double latitude, double radius, io.vertx.redis.op.GeoUnit unit, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadiusWithOptions(key,
      longitude,
      latitude,
      radius,
      unit,
      options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
  public static io.vertx.redis.RedisTransaction georadiusbymemberWithOptions(io.vertx.redis.RedisTransaction j_receiver, java.lang.String key, java.lang.String member, double radius, io.vertx.redis.op.GeoUnit unit, java.util.Map<String, Object> options, io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>> handler) {
    io.vertx.lang.groovy.ConversionHelper.wrap(j_receiver.georadiusbymemberWithOptions(key,
      member,
      radius,
      unit,
      options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.ConversionHelper.toJsonObject(options)) : null,
      handler != null ? new io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>() {
      public void handle(io.vertx.core.AsyncResult<java.lang.String> ar) {
        handler.handle(ar.map(event -> event));
      }
    } : null));
    return j_receiver;
  }
}
