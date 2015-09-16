package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.op.*;
import io.vertx.redis.utils.RedisCommandUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.vertx.redis.impl.RedisCommand.*;

public final class RedisClientImpl extends AbstractRedisClient {

  public RedisClientImpl(Vertx vertx, RedisOptions config) {
    super(vertx, config);
  }

  @Override
  public RedisClient append(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(APPEND, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient auth(String password, Handler<AsyncResult<String>> handler) {
    sendString(AUTH, RedisCommandUtils.toPayload(password), handler);
    return this;
  } 

  @Override
  public RedisClient bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString(BGREWRITEAOF, null, handler);
    return this;
  } 

  @Override
  public RedisClient bgsave(Handler<AsyncResult<String>> handler) {
    sendString(BGSAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient bitcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(BITCOUNT, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  public RedisClient bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) {
    sendLong(BITCOUNT, RedisCommandUtils.toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(BITOP, RedisCommandUtils.toPayload(operation.name(), destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, RedisCommandUtils.toPayload(key, bit), handler);
    return this;
  } 

  @Override
  public RedisClient bitposFrom(String key, int bit, int start, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, RedisCommandUtils.toPayload(key, bit, start), handler);
    return this;
  } 

  @Override
  public RedisClient bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, RedisCommandUtils.toPayload(key, bit, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient blpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BLPOP, RedisCommandUtils.toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient blpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BLPOP, RedisCommandUtils.toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BRPOP, RedisCommandUtils.toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BRPOP, RedisCommandUtils.toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) {
    sendString(BRPOPLPUSH, RedisCommandUtils.toPayload(key, destkey, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient clientKill(KillFilter filter, Handler<AsyncResult<Long>> handler) {
    sendLong(CLIENT_KILL, filter.toJsonArray(), handler);
    return this;
  } 

  @Override
  public RedisClient clientList(Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_LIST, null, handler);
    return this;
  } 

  @Override
  public RedisClient clientGetname(Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_GETNAME, null, handler);
    return this;
  } 

  @Override
  public RedisClient clientPause(long millis, Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_PAUSE, RedisCommandUtils.toPayload(millis), handler);
    return this;
  } 

  @Override
  public RedisClient clientSetname(String name, Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_SETNAME, RedisCommandUtils.toPayload(name), handler);
    return this;
  }

  @Override
  public RedisClient clusterAddslots(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_ADDSLOTS, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterCountFailureReports(String nodeId, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_COUNT_FAILURE_REPORTS, RedisCommandUtils.toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterCountkeysinslot(long slot, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_COUNTKEYSINSLOT, RedisCommandUtils.toPayload(slot), handler);
    return this;
  }

  @Override
  public RedisClient clusterDelslots(long slot, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_DELSLOTS, RedisCommandUtils.toPayload(slot), handler);
    return this;
  }

  @Override
  public RedisClient clusterDelslotsMany(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_DELSLOTS, RedisCommandUtils.toPayload(slots), handler);
    return this;
  }

  @Override
  public RedisClient clusterFailover(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FAILOVER, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FAILOVER, RedisCommandUtils.toPayload(options), handler);
    return this;
  }

  @Override
  public RedisClient clusterForget(String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FORGET, RedisCommandUtils.toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_GETKEYSINSLOT, RedisCommandUtils.toPayload(slot, count), handler);
    return this;
  }

  @Override
  public RedisClient clusterInfo(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_INFO, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterKeyslot(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_KEYSLOT, RedisCommandUtils.toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient clusterMeet(String ip, long port, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_MEET, RedisCommandUtils.toPayload(ip, port), handler);
    return this;
  }

  @Override
  public RedisClient clusterNodes(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_NODES, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterReplicate(String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_REPLICATE, RedisCommandUtils.toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterReset(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_RESET, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_RESET, RedisCommandUtils.toPayload(options), handler);
    return this;
  }

  @Override
  public RedisClient clusterSaveconfig(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SAVECONFIG, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterSetConfigEpoch(long epoch, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SET_CONFIG_EPOCH, RedisCommandUtils.toPayload(epoch), handler);
    return this;
  }

  @Override
  public RedisClient clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SETSLOT, RedisCommandUtils.toPayload(slot, subcommand), handler);
    return this;
  }

  @Override
  public RedisClient clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SETSLOT, RedisCommandUtils.toPayload(slot, subcommand, nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterSlaves(String nodeId, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_SLAVES, RedisCommandUtils.toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_SLOTS, null, handler);
    return this;
  } 

  @Override
  public RedisClient command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong(COMMAND_COUNT, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND_GETKEYS, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandInfo(List<String> commands, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND_INFO, RedisCommandUtils.toPayload(commands), handler);
    return this;
  } 

  @Override
  public RedisClient configGet(String parameter, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CONFIG_GET, RedisCommandUtils.toPayload(parameter), handler);
    return this;
  } 

  @Override
  public RedisClient configRewrite(Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_REWRITE, null, handler);
    return this;
  } 

  @Override
  public RedisClient configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_SET, RedisCommandUtils.toPayload(parameter, value), handler);
    return this;
  } 

  @Override
  public RedisClient configResetstat(Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_RESETSTAT, null, handler);
    return this;
  } 

  @Override
  public RedisClient dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong(DBSIZE, null, handler);
    return this;
  } 

  @Override
  public RedisClient debugObject(String key, Handler<AsyncResult<String>> handler) {
    sendString(DEBUG_OBJECT, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString(DEBUG_SEGFAULT, null, handler);
    return this;
  } 

  @Override
  public RedisClient decr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(DECR, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    sendLong(DECRBY, RedisCommandUtils.toPayload(key, decrement), handler);
    return this;
  } 

  @Override
  public RedisClient del(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(DEL, RedisCommandUtils.toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient delMany(List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(DEL, RedisCommandUtils.toPayload(keys), handler);
    return this;
  }

  @Override
  public RedisClient discard(Handler<AsyncResult<String>> handler) {
    sendString(DISCARD, null, handler);
    return this;
  } 

  @Override
  public RedisClient dump(String key, Handler<AsyncResult<String>> handler) {
    send(DUMP, RedisCommandUtils.toPayload(key), String.class, true, dump -> {
      if (dump.failed()) {
        handler.handle(dump);
      } else {
        handler.handle(Future.succeededFuture(RedisEncoding.encode(dump.result())));
      }
    });
    return this;
  } 

  @Override
  public RedisClient echo(String message, Handler<AsyncResult<String>> handler) {
    sendString(ECHO, RedisCommandUtils.toPayload(message), handler);
    return this;
  } 

  @Override
  public RedisClient eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    if (keys.size() != args.size()) {
      handler.handle(Future.failedFuture(new IllegalArgumentException("Key list, and argument list are not the same size")));
    } else {
      sendVoid(EVAL, RedisCommandUtils.toPayload(script, keys.size(), keys, args), handler);
    }
    return this;
  } 

  @Override
  public RedisClient evalsha(String sha1, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    sendVoid(EVALSHA, RedisCommandUtils.toPayload(sha1, keys.size(), keys, args), handler);
    return this;
  } 

  @Override
  public RedisClient exec(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(EXEC, null, handler);
    return this;
  } 

  @Override
  public RedisClient exists(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(EXISTS, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    sendLong(EXPIRE, RedisCommandUtils.toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient expireat(String key, long seconds, Handler<AsyncResult<Long>> handler) {
    sendLong(EXPIREAT, RedisCommandUtils.toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient flushall(Handler<AsyncResult<String>> handler) {
    sendString(FLUSHALL, null, handler);
    return this;
  } 

  @Override
  public RedisClient flushdb(Handler<AsyncResult<String>> handler) {
    sendString(FLUSHDB, null, handler);
    return this;
  } 

  @Override
  public RedisClient get(String key, Handler<AsyncResult<String>> handler) {
    sendString(GET, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient getBinary(String key, Handler<AsyncResult<String>> handler) {
    send(GET, RedisCommandUtils.toPayload(key), String.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient getbit(String key, long offset, Handler<AsyncResult<Long>> handler) {
    sendLong(GETBIT, RedisCommandUtils.toPayload(key, offset), handler);
    return this;
  } 

  @Override
  public RedisClient getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    sendString(GETRANGE, RedisCommandUtils.toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient getset(String key, String value, Handler<AsyncResult<String>> handler) {
    sendString(GETSET, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient hdel(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong(HDEL, RedisCommandUtils.toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) {
    sendLong(HDEL, RedisCommandUtils.toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hexists(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong(HEXISTS, RedisCommandUtils.toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hget(String key, String field, Handler<AsyncResult<String>> handler) {
    sendString(HGET, RedisCommandUtils.toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hgetall(String key, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(HGETALL, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong(HINCRBY, RedisCommandUtils.toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    sendString(HINCRBYFLOAT, RedisCommandUtils.toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hkeys(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HKEYS, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(HLEN, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hmget(String key, List<String> fields, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HMGET, RedisCommandUtils.toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler) {
    sendString(HMSET, RedisCommandUtils.toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(HSET, RedisCommandUtils.toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(HSETNX, RedisCommandUtils.toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hvals(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HVALS, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(INCR, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong(INCRBY, RedisCommandUtils.toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    sendString(INCRBYFLOAT, RedisCommandUtils.toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient info(Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(INFO, new JsonArray(), handler);
    return this;
  } 

  @Override
  public RedisClient infoSection(String section, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(INFO, RedisCommandUtils.toPayload(section), handler);
    return this;
  } 

  @Override
  public RedisClient keys(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(KEYS, RedisCommandUtils.toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong(LASTSAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    sendString(LINDEX, RedisCommandUtils.toPayload(key, index), handler);
    return this;
  } 

  @Override
  public RedisClient linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LINSERT, RedisCommandUtils.toPayload(key, option.name(), pivot, value), handler);
    return this;
  } 

  @Override
  public RedisClient llen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(LLEN, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpop(String key, Handler<AsyncResult<String>> handler) {
    sendString(LPOP, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSH, RedisCommandUtils.toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSH, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSHX, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lrange(String key, long from, long to, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(LRANGE, RedisCommandUtils.toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient lrem(String key, long count, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LREM, RedisCommandUtils.toPayload(key, count, value), handler);
    return this;
  } 

  @Override
  public RedisClient lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
    sendString(LSET, RedisCommandUtils.toPayload(key, index, value), handler);
    return this;
  } 

  @Override
  public RedisClient ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    sendString(LTRIM, RedisCommandUtils.toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient mget(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MGET, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient mgetMany(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MGET, RedisCommandUtils.toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) {
    sendString(MIGRATE, RedisCommandUtils.toPayload(host, port, key, destdb, timeout, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid(MONITOR, null, handler);
    return this;
  } 

  @Override
  public RedisClient move(String key, int destdb, Handler<AsyncResult<Long>> handler) {
    sendLong(MOVE, RedisCommandUtils.toPayload(key, destdb), handler);
    return this;
  } 

  @Override
  public RedisClient mset(JsonObject keyvals, Handler<AsyncResult<String>> handler) {
    sendString(MSET, RedisCommandUtils.toPayload(keyvals), handler);
    return this;
  } 

  @Override
  public RedisClient msetnx(JsonObject keyvals, Handler<AsyncResult<Long>> handler) {
    sendLong(MSETNX, RedisCommandUtils.toPayload(keyvals), handler);
    return this;
  } 

  @Override
  public RedisClient multi(Handler<AsyncResult<String>> handler) {
    sendString(MULTI, null, handler);
    return this;
  } 

  @Override
  public RedisClient object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    sendVoid(OBJECT, RedisCommandUtils.toPayload(cmd.name(), key), handler);
    return this;
  } 

  @Override
  public RedisClient persist(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PERSIST, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pexpire(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong(PEXPIRE, RedisCommandUtils.toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pexpireat(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong(PEXPIREAT, RedisCommandUtils.toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pfadd(String key, String element, Handler<AsyncResult<Long>> handler) {
    sendLong(PFADD, RedisCommandUtils.toPayload(key, element), handler);
    return this;
  } 

  @Override
  public RedisClient pfaddMany(String key, List<String> elements, Handler<AsyncResult<Long>> handler) {
    sendLong(PFADD, RedisCommandUtils.toPayload(key, elements), handler);
    return this;
  } 

  @Override
  public RedisClient pfcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PFCOUNT, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfcountMany(List<String> key, Handler<AsyncResult<Long>> handler) {
    sendLong(PFCOUNT, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    sendString(PFMERGE, RedisCommandUtils.toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient ping(Handler<AsyncResult<String>> handler) {
    sendString(PING, null, handler);
    return this;
  } 

  @Override
  public RedisClient psetex(String key, long millis, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid(PSETEX, RedisCommandUtils.toPayload(key, millis, value), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribe(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PSUBSCRIBE, RedisCommandUtils.toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribeMany(List<String> patterns, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PSUBSCRIBE, RedisCommandUtils.toPayload(patterns), handler);
    return this;
  } 

  @Override
  public RedisClient pubsubChannels(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PUBSUB, RedisCommandUtils.toPayload("CHANNELS", pattern == null || "" .equals(pattern) ? null : pattern), handler);
    return this;
  } 

  public RedisClient pubsubNumsub(List<String> channels, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PUBSUB, RedisCommandUtils.toPayload("NUMSUB", channels), handler);
    return this;
  } 

  public RedisClient pubsubNumpat(Handler<AsyncResult<Long>> handler) {
    sendLong(PUBSUB, RedisCommandUtils.toPayload("NUMPAT"), handler);
    return this;
  } 

  @Override
  public RedisClient pttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PTTL, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient publish(String channel, String message, Handler<AsyncResult<Long>> handler) {
    sendLong(PUBLISH, RedisCommandUtils.toPayload(channel, message), handler);
    return this;
  } 

  @Override
  public RedisClient punsubscribe(List<String> patterns, Handler<AsyncResult<Void>> handler) {
    sendVoid(PUNSUBSCRIBE, RedisCommandUtils.toPayload(patterns), handler);
    return this;
  } 


  @Override
  public RedisClient randomkey(Handler<AsyncResult<String>> handler) {
    sendString(RANDOMKEY, null, handler);
    return this;
  } 

  @Override
  public RedisClient rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    sendString(RENAME, RedisCommandUtils.toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    sendLong(RENAMENX, RedisCommandUtils.toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
    send(RESTORE, RedisCommandUtils.toPayload(key, millis, RedisEncoding.decode(serialized)), String.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ROLE, null, handler);
    return this;
  } 

  @Override
  public RedisClient rpop(String key, Handler<AsyncResult<String>> handler) {
    sendString(RPOP, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    sendString(RPOPLPUSH, RedisCommandUtils.toPayload(key, destkey), handler);
    return this;
  } 

  @Override
  public RedisClient rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSH, RedisCommandUtils.toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSH, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSHX, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient sadd(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SADD, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient saddMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(SADD, RedisCommandUtils.toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient save(Handler<AsyncResult<String>> handler) {
    sendString(SAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient scard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(SCARD, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExists(String script, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCRIPT_EXISTS, RedisCommandUtils.toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExistsMany(List<String> scripts, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCRIPT_EXISTS, RedisCommandUtils.toPayload(scripts), handler);
    return this;
  } 

  @Override
  public RedisClient scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_FLUSH, null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptKill(Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_KILL, null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptLoad(String script, Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_LOAD, RedisCommandUtils.toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SDIFF, RedisCommandUtils.toPayload(key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<Long>> handler) {
    sendLong(SDIFFSTORE, RedisCommandUtils.toPayload(destkey, key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient select(int dbindex, Handler<AsyncResult<String>> handler) {
    sendString(SELECT, RedisCommandUtils.toPayload(dbindex), handler);
    return this;
  } 

  @Override
  public RedisClient set(String key, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid(SET, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid(SET, RedisCommandUtils.toPayload(key, value, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient setBinary(String key, String value, Handler<AsyncResult<Void>> handler) {
    send(SET, RedisCommandUtils.toPayload(key, value), Void.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong(SETBIT, RedisCommandUtils.toPayload(key, offset, bit), handler);
    return this;
  } 

  @Override
  public RedisClient setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    sendString(SETEX, RedisCommandUtils.toPayload(key, seconds, value), handler);
    return this;
  } 

  @Override
  public RedisClient setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(SETNX, RedisCommandUtils.toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setrange(String key, int offset, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(SETRANGE, RedisCommandUtils.toPayload(key, offset, value), handler);
    return this;
  } 

  @Override
  public RedisClient sinter(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SINTER, RedisCommandUtils.toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sinterstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(SINTERSTORE, RedisCommandUtils.toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sismember(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SISMEMBER, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
    sendString(SLAVEOF, RedisCommandUtils.toPayload(host, port), handler);
    return this;
  } 

  @Override
  public RedisClient slaveofNoone(Handler<AsyncResult<String>> handler) {
    sendString(SLAVEOF, RedisCommandUtils.toPayload("NO", "ONE"), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogGet(int limit, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SLOWLOG, RedisCommandUtils.toPayload("GET", limit < 0 ? null : limit), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogLen(Handler<AsyncResult<Long>> handler) {
    sendLong(SLOWLOG, RedisCommandUtils.toPayload("LEN"), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogReset(Handler<AsyncResult<Void>> handler) {
    sendVoid(SLOWLOG, RedisCommandUtils.toPayload("RESET"), handler);
    return this;
  } 

  @Override
  public RedisClient smembers(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SMEMBERS, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient smove(String key, String destkey, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SMOVE, RedisCommandUtils.toPayload(key, destkey, member), handler);
    return this;
  } 

  @Override
  public RedisClient sort(String key, SortOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SORT, RedisCommandUtils.toPayload(key, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient spop(String key, Handler<AsyncResult<String>> handler) {
    sendString(SPOP, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
    sendString(SPOP, RedisCommandUtils.toPayload(key, count), handler);
    return this;
  } 

  @Override
  public RedisClient srandmember(String key, Handler<AsyncResult<String>> handler) {
    sendString(SRANDMEMBER, RedisCommandUtils.toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient srandmemberCount(String key, int count, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SRANDMEMBER, RedisCommandUtils.toPayload(key, count), handler);
    return this;
  }

  @Override
  public RedisClient srem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SREM, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient sremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(SREM, RedisCommandUtils.toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient strlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(STRLEN, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient subscribe(String channel, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUBSCRIBE, RedisCommandUtils.toPayload(channel), handler);
    return this;
  }

  @Override
  public RedisClient subscribeMany(List<String> channels, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUBSCRIBE, RedisCommandUtils.toPayload(channels), handler);
    return this;
  }

  @Override
  public RedisClient sunion(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUNION, RedisCommandUtils.toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sunionstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(SUNIONSTORE, RedisCommandUtils.toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sync(Handler<AsyncResult<Void>> handler) {
    sendVoid(SYNC, null, handler);
    return this;
  } 

  @Override
  public RedisClient time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(TIME, null, handler);
    return this;
  } 

  @Override
  public RedisClient ttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(TTL, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient type(String key, Handler<AsyncResult<String>> handler) {
    sendString(TYPE, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient unsubscribe(List<String> channels, Handler<AsyncResult<Void>> handler) {
    sendVoid(UNSUBSCRIBE, RedisCommandUtils.toPayload(channels), handler);
    return this;
  } 

  @Override
  public RedisClient unwatch(Handler<AsyncResult<String>> handler) {
    sendString(UNWATCH, null, handler);
    return this;
  }

  @Override
  public RedisClient wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) {
    sendString(WAIT, RedisCommandUtils.toPayload(numSlaves, timeout), handler);
    return this;
  }

  @Override
  public RedisClient watch(String key, Handler<AsyncResult<String>> handler) {
    sendString(WATCH, RedisCommandUtils.toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient watchMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    sendString(WATCH, RedisCommandUtils.toPayload(keys), handler);
    return this;
  }

  @Override
  public RedisClient zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZADD, RedisCommandUtils.toPayload(key, score, member), handler);
    return this;
  } 

  @Override
  public RedisClient zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<Long>> handler) {
    // flip from <String, Double> to <Double, String> when wrapping
    Stream flipped = members.entrySet().stream().map(e -> new Object[] { e.getValue(), e.getKey() });
    sendLong(ZADD, RedisCommandUtils.toPayload(key, flipped), handler);
    return this;
  } 

  @Override
  public RedisClient zcard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(ZCARD, RedisCommandUtils.toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) {
    String minVal = (min == Double.NEGATIVE_INFINITY) ? "-inf" : String.valueOf(min);
    String maxVal = (max == Double.POSITIVE_INFINITY) ? "+inf" : String.valueOf(max);
    sendLong(ZCOUNT, RedisCommandUtils.toPayload(key, minVal, maxVal), handler);
    return this;
  } 

  @Override
  public RedisClient zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    sendString(ZINCRBY, RedisCommandUtils.toPayload(key, increment, member), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZINTERSTORE, RedisCommandUtils.toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZINTERSTORE, RedisCommandUtils.toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
            options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zlexcount(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZLEXCOUNT, RedisCommandUtils.toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrange(String key, long start, long stop, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGE, RedisCommandUtils.toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGE, RedisCommandUtils.toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGEBYLEX, RedisCommandUtils.toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGEBYSCORE, RedisCommandUtils.toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZRANK, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zrem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREM, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREM, RedisCommandUtils.toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebylex(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYLEX, RedisCommandUtils.toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYRANK, RedisCommandUtils.toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyscore(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYSCORE, RedisCommandUtils.toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGE, RedisCommandUtils.toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGEBYLEX, RedisCommandUtils.toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGEBYSCORE, RedisCommandUtils.toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREVRANK, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zscore(String key, String member, Handler<AsyncResult<String>> handler) {
    sendString(ZSCORE, RedisCommandUtils.toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZUNIONSTORE, RedisCommandUtils.toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZUNIONSTORE, RedisCommandUtils.toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
            options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient scan(String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCAN, RedisCommandUtils.toPayload(cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SSCAN, RedisCommandUtils.toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HSCAN, RedisCommandUtils.toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZSCAN, RedisCommandUtils.toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  }

}
