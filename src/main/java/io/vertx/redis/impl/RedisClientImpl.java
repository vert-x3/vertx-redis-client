package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisEncoding;
import io.vertx.redis.op.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class RedisClientImpl extends AbstractRedisClient {

  public RedisClientImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }

  @Override
  public RedisClient append(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("APPEND", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient auth(String password, Handler<AsyncResult<String>> handler) {
    sendString("AUTH", toPayload(password), handler);
    return this;
  } 

  @Override
  public RedisClient bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString("BGREWRITEAOF", null, handler);
    return this;
  } 

  @Override
  public RedisClient bgsave(Handler<AsyncResult<String>> handler) {
    sendString("BGSAVE", null, handler);
    return this;
  } 

  @Override
  public RedisClient bitcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", toPayload(key), handler);
    return this;
  } 

  public RedisClient bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("BITOP", toPayload(operation.name(), destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit), handler);
    return this;
  } 

  @Override
  public RedisClient bitposFrom(String key, int bit, int start, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit, start), handler);
    return this;
  } 

  @Override
  public RedisClient bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient blpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient blpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<Void>> handler) {
    sendVoid("BRPOPLPUSH", toPayload(key, destkey, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient clientKill(KillFilter filter, Handler<AsyncResult<Long>> handler) {
    sendLong("CLIENT KILL", filter.toJsonArray(), handler);
    return this;
  } 

  @Override
  public RedisClient clientList(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT LIST", null, handler);
    return this;
  } 

  @Override
  public RedisClient clientGetname(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT GETNAME", null, handler);
    return this;
  } 

  @Override
  public RedisClient clientPause(long millis, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT PAUSE", toPayload(millis), handler);
    return this;
  } 

  @Override
  public RedisClient clientSetname(String name, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT SETNAME", toPayload(name), handler);
    return this;
  } 

  @Override
  public RedisClient clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CLUSTER SLOTS", null, handler);
    return this;
  } 

  @Override
  public RedisClient command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND", null, handler);
    return this;
  } 

  @Override
  public RedisClient commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong("COMMAND COUNT", null, handler);
    return this;
  } 

  @Override
  public RedisClient commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND GETKEYS", null, handler);
    return this;
  } 

  @Override
  public RedisClient commandInfo(List<String> commands, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND INFO", toPayload(commands), handler);
    return this;
  } 

  @Override
  public RedisClient configGet(String parameter, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CONFIG GET", toPayload(parameter), handler);
    return this;
  } 

  @Override
  public RedisClient configRewrite(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG REWRITE", null, handler);
    return this;
  } 

  @Override
  public RedisClient configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    sendString("CONFIG SET", toPayload(parameter, value), handler);
    return this;
  } 

  @Override
  public RedisClient configResetstat(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG RESETSTAT", null, handler);
    return this;
  } 

  @Override
  public RedisClient dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong("DBSIZE", null, handler);
    return this;
  } 

  @Override
  public RedisClient debugObject(String key, Handler<AsyncResult<String>> handler) {
    sendString("DEBUG OBJECT", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString("DEBUG SEGFAULT", null, handler);
    return this;
  } 

  @Override
  public RedisClient decr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("DECR", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    sendLong("DECRBY", toPayload(key, decrement), handler);
    return this;
  } 

  @Override
  public RedisClient del(List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("DEL", toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient discard(Handler<AsyncResult<String>> handler) {
    sendString("DISCARD", null, handler);
    return this;
  } 

  @Override
  public RedisClient dump(String key, Handler<AsyncResult<String>> handler) {
    send("DUMP", toPayload(key), String.class, true, dump -> {
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
    sendString("ECHO", toPayload(message), handler);
    return this;
  } 

  @Override
  public RedisClient eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    if (keys.size() != args.size()) {
      handler.handle(Future.failedFuture(new IllegalArgumentException("Key list, and argument list are not the same size")));
    } else {
      sendVoid("EVAL", toPayload(script, keys.size(), keys, args), handler);
    }
    return this;
  } 

  @Override
  public RedisClient evalsha(String sha1, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    sendVoid("EVALSHA", toPayload(sha1, keys.size(), keys, args), handler);
    return this;
  } 

  @Override
  public RedisClient exec(Handler<AsyncResult<Void>> handler) {
    sendVoid("EXEC", null, handler);
    return this;
  } 

  @Override
  public RedisClient exists(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("EXISTS", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIRE", toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient expireat(String key, long seconds, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIREAT", toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient flushall(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHALL", null, handler);
    return this;
  } 

  @Override
  public RedisClient flushdb(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHDB", null, handler);
    return this;
  } 

  @Override
  public RedisClient get(String key, Handler<AsyncResult<String>> handler) {
    sendString("GET", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient getBinary(String key, Handler<AsyncResult<String>> handler) {
    send("GET", toPayload(key), String.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient getbit(String key, long offset, Handler<AsyncResult<Long>> handler) {
    sendLong("GETBIT", toPayload(key, offset), handler);
    return this;
  } 

  @Override
  public RedisClient getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    sendString("GETRANGE", toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient getset(String key, String value, Handler<AsyncResult<String>> handler) {
    sendString("GETSET", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient hdel(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hexists(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong("HEXISTS", toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hget(String key, String field, Handler<AsyncResult<String>> handler) {
    sendString("HGET", toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hgetall(String key, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("HGETALL", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong("HINCRBY", toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    sendString("HINCRBYFLOAT", toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hkeys(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HKEYS", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("HLEN", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hmget(String key, List<String> fields, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HMGET", toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hmset(String key, Map<String, String> values, Handler<AsyncResult<String>> handler) {
    sendString("HMSET", toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("HSET", toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("HSETNX", toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hvals(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HVALS", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("INCR", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong("INCRBY", toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    sendString("INCRBYFLOAT", toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient info(Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", new JsonArray(), handler);
    return this;
  } 

  @Override
  public RedisClient infoSection(String section, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", toPayload(section), handler);
    return this;
  } 

  @Override
  public RedisClient keys(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("KEYS", toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong("LASTSAVE", null, handler);
    return this;
  } 

  @Override
  public RedisClient lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    sendString("LINDEX", toPayload(key, index), handler);
    return this;
  } 

  @Override
  public RedisClient linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LINSERT", toPayload(key, option.name(), pivot, value), handler);
    return this;
  } 

  @Override
  public RedisClient llen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("LLEN", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("LPOP", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSHX", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lrange(String key, long from, long to, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("LRANGE", toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient lrem(String key, long count, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LREM", toPayload(key, count, value), handler);
    return this;
  } 

  @Override
  public RedisClient lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
    sendString("LSET", toPayload(key, index, value), handler);
    return this;
  } 

  @Override
  public RedisClient ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    sendString("LTRIM", toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient mget(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("MGET", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient mgetMany(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("MGET", toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) {
    sendString("MIGRATE", toPayload(host, port, key, destdb, timeout, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid("MONITOR", null, handler);
    return this;
  } 

  @Override
  public RedisClient move(String key, int destdb, Handler<AsyncResult<Long>> handler) {
    sendLong("MOVE", toPayload(key, destdb), handler);
    return this;
  } 

  @Override
  public RedisClient mset(Map<String, String> keyvals, Handler<AsyncResult<String>> handler) {
    sendString("MSET", toPayload(keyvals), handler);
    return this;
  } 

  @Override
  public RedisClient msetnx(Map<String, String> keyvals, Handler<AsyncResult<Long>> handler) {
    sendLong("MSETNX", toPayload(keyvals), handler);
    return this;
  } 

  @Override
  public RedisClient multi(Handler<AsyncResult<String>> handler) {
    sendString("MULTI", null, handler);
    return this;
  } 

  @Override
  public RedisClient object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    sendVoid("OBJECT", toPayload(cmd.name(), key), handler);
    return this;
  } 

  @Override
  public RedisClient persist(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("PERSIST", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pexpire(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIRE", toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pexpireat(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIREAT", toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pfadd(String key, String element, Handler<AsyncResult<Long>> handler) {
    sendLong("PFADD", toPayload(key, element), handler);
    return this;
  } 

  @Override
  public RedisClient pfaddMany(String key, List<String> elements, Handler<AsyncResult<Long>> handler) {
    sendLong("PFADD", toPayload(key, elements), handler);
    return this;
  } 

  @Override
  public RedisClient pfcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("PFCOUNT", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfcountMany(List<String> key, Handler<AsyncResult<Long>> handler) {
    sendLong("PFCOUNT", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    sendString("PFMERGE", toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient ping(Handler<AsyncResult<String>> handler) {
    sendString("PING", null, handler);
    return this;
  } 

  @Override
  public RedisClient psetex(String key, long millis, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSETEX", toPayload(key, millis, value), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribe(String pattern, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSUBSCRIBE", toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribeMany(List<String> patterns, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSUBSCRIBE", toPayload(patterns), handler);
    return this;
  } 

  @Override
  public RedisClient pubsubChannels(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("PUBSUB", toPayload("CHANNELS", pattern == null || "".equals(pattern) ? null : pattern), handler);
    return this;
  } 

  public RedisClient pubsubNumsub(List<String> channels, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("PUBSUB", toPayload("NUMSUB", channels), handler);
    return this;
  } 

  public RedisClient pubsubNumpat(Handler<AsyncResult<Long>> handler) {
    sendLong("PUBSUB", toPayload("NUMPAT"), handler);
    return this;
  } 

  @Override
  public RedisClient pttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("PTTL", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient publish(String channel, String message, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBLISH", toPayload(channel, message), handler);
    return this;
  } 

  @Override
  public RedisClient punsubscribe(List<String> patterns, Handler<AsyncResult<Void>> handler) {
    sendVoid("PUNSUBSCRIBE", toPayload(patterns), handler);
    return this;
  } 


  @Override
  public RedisClient randomkey(Handler<AsyncResult<String>> handler) {
    sendString("RANDOMKEY", null, handler);
    return this;
  } 

  @Override
  public RedisClient rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    sendString("RENAME", toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    sendLong("RENAMENX", toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
    send("RESTORE", toPayload(key, millis, RedisEncoding.decode(serialized)), String.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ROLE", null, handler);
    return this;
  } 

  @Override
  public RedisClient rpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("RPOP", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    sendString("RPOPLPUSH", toPayload(key, destkey), handler);
    return this;
  } 

  @Override
  public RedisClient rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSHX", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient sadd(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("SADD", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient saddMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong("SADD", toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient save(Handler<AsyncResult<String>> handler) {
    sendString("SAVE", null, handler);
    return this;
  } 

  @Override
  public RedisClient scard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("SCARD", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExists(String script, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SCRIPT EXISTS", toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExistsMany(List<String> scripts, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SCRIPT EXISTS", toPayload(scripts), handler);
    return this;
  } 

  @Override
  public RedisClient scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT FLUSH", null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptKill(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT KILL", null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptLoad(String script, Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT LOAD", toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SDIFF", toPayload(key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<Long>> handler) {
    sendLong("SDIFFSTORE", toPayload(destkey, key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient select(int dbindex, Handler<AsyncResult<String>> handler) {
    sendString("SELECT", toPayload(dbindex), handler);
    return this;
  } 

  @Override
  public RedisClient set(String key, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid("SET", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid("SET", toPayload(key, value, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient setBinary(String key, String value, Handler<AsyncResult<Void>> handler) {
    send("SET", toPayload(key, value), Void.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong("SETBIT", toPayload(key, offset, bit), handler);
    return this;
  } 

  @Override
  public RedisClient setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    sendString("SETEX", toPayload(key, seconds, value), handler);
    return this;
  } 

  @Override
  public RedisClient setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("SETNX", toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setrange(String key, int offset, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("SETRANGE", toPayload(key, offset, value), handler);
    return this;
  } 

  @Override
  public RedisClient sinter(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SINTER", toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sinterstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("SINTERSTORE", toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sismember(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("SISMEMBER", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
    sendString("SLAVEOF", toPayload(host, port), handler);
    return this;
  } 

  @Override
  public RedisClient slaveofNoone(Handler<AsyncResult<String>> handler) {
    sendString("SLAVEOF", toPayload("NO", "ONE"), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogGet(int limit, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SLOWLOG GET", toPayload(limit < 0 ? null : limit), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogLen(Handler<AsyncResult<Long>> handler) {
    sendLong("SLOWLOG LEN", null, handler);
    return this;
  } 

  @Override
  public RedisClient slowlogReset(Handler<AsyncResult<Void>> handler) {
    sendVoid("SLOWLOG RESET", null, handler);
    return this;
  } 

  @Override
  public RedisClient smembers(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SMEMBERS", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient smove(String key, String destkey, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("SMOVE", toPayload(key, destkey, member), handler);
    return this;
  } 

  @Override
  public RedisClient sort(String key, SortOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SORT", toPayload(key, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient spop(String key, Handler<AsyncResult<String>> handler) {
    sendString("SPOP", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
    sendString("SPOP", toPayload(key, count), handler);
    return this;
  } 

  @Override
  public RedisClient srandmember(String key, int count, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SRANDMEMBER", toPayload(key, count), handler);
    return this;
  } 

  @Override
  public RedisClient srem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("SREM", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient sremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong("SREM", toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient strlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("STRLEN", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient subscribe(List<String> channels, Handler<AsyncResult<Void>> handler) {
    sendVoid("SUBSCRIBE", toPayload(channels), handler);
    return this;
  } 

  @Override
  public RedisClient sunion(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SUNION", toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sunionstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("SUNIONSTORE", toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sync(Handler<AsyncResult<Void>> handler) {
    sendVoid("SYNC", null, handler);
    return this;
  } 

  @Override
  public RedisClient time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("TIME", null, handler);
    return this;
  } 

  @Override
  public RedisClient ttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("TTL", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient type(String key, Handler<AsyncResult<String>> handler) {
    sendString("TYPE", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient unsubscribe(List<String> channels, Handler<AsyncResult<Void>> handler) {
    sendVoid("UNSUBSCRIBE", toPayload(channels), handler);
    return this;
  } 

  @Override
  public RedisClient unwatch(Handler<AsyncResult<String>> handler) {
    sendString("UNWATCH", null, handler);
    return this;
  } 

  @Override
  public RedisClient watch(List<String> keys, Handler<AsyncResult<String>> handler) {
    sendString("WATCH", toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("ZADD", toPayload(key, score, member), handler);
    return this;
  } 

  @Override
  public RedisClient zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<Long>> handler) {
    // flip from <String, Double> to <Double, String> when wrapping
    Stream flipped = members.entrySet().stream().map(e -> new Object[] { e.getValue(), e.getKey() });
    sendLong("ZADD", toPayload(key, flipped), handler);
    return this;
  } 

  @Override
  public RedisClient zcard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCARD", toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) {
    String minVal = (min == Double.NEGATIVE_INFINITY) ? "-inf" : String.valueOf(min);
    String maxVal = (max == Double.POSITIVE_INFINITY) ? "+inf" : String.valueOf(max);
    sendLong("ZCOUNT", toPayload(key, minVal, maxVal), handler);
    return this;
  } 

  @Override
  public RedisClient zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    sendString("ZINCRBY", toPayload(key, increment, member), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong("ZINTERSTORE", toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong("ZINTERSTORE", toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
      options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zlexcount(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong("ZLEXCOUNT", toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrange(String key, long start, long stop, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGE", toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGE", toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYLEX", toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYSCORE", toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("ZRANK", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zrem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREM", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREM", toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebylex(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYLEX", toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYRANK", toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyscore(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYSCORE", toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGE", toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGEBYLEX", toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGEBYSCORE", toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREVRANK", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zscore(String key, String member, Handler<AsyncResult<String>> handler) {
    sendString("ZSCORE", toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong("ZUNIONSTORE", toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong("ZUNIONSTORE", toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
      options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient scan(String cursor, ScanOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid("SCAN", toPayload(cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid("SSCAN", toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HSCAN", toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid("ZSCAN", toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  /**
   * A helper method to package method parameters into JsonArray payload.
   *
   * Null parameters are ignored.
   *
   * @param parameters Call parameters
   * @return JsonArray that can be passed to send()
   */
  private static JsonArray toPayload(Object ... parameters) {
    JsonArray result = new JsonArray();
    for (Object param: parameters) {
      if (param instanceof JsonArray) {
        param = ((JsonArray) param).getList();
      }
      if (param instanceof Collection) {
        for (Object el : (Collection) param) {
          if (el != null) {
            result.add(el);
          }
        }
      } else if (param instanceof Map) {
        for (Map.Entry<?, ?> pair : ((Map<?, ?>) param).entrySet()) {
          result.add(pair.getKey());
          result.add(pair.getValue());
        }
      } else if (param instanceof Stream) {
        ((Stream) param).forEach(e -> {
          if (e instanceof Object[]) {
            for (Object item : (Object[]) e) {
              result.add(item);
            }
          } else {
            result.add(e);
          }
        });
      } else if (param != null) {
        result.add(param);
      }
    }
    return result;
  } 
}
