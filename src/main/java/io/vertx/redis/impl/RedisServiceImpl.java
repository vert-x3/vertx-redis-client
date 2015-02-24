package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.BitOperation;
import io.vertx.redis.InsertOptions;
import io.vertx.redis.KillFilter;
import io.vertx.redis.ObjectCmd;
import io.vertx.redis.ScanOptions;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class RedisServiceImpl extends AbstractRedisService {

  public RedisServiceImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }

  @Override
  public void append(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("APPEND", toPayload(key, value), handler);
  }

  @Override
  public void auth(String password, Handler<AsyncResult<String>> handler) {
    sendString("AUTH", toPayload(password), handler);
  }

  @Override
  public void bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString("BGREWRITEAOF", null, handler);
  }

  @Override
  public void bgsave(Handler<AsyncResult<String>> handler) {
    sendString("BGSAVE", null, handler);
  }

  @Override
  public void bitcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", toPayload(key), handler);
  }

  public void bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", toPayload(key, start, end), handler);
  }

  @Override
  public void bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("BITOP", toPayload(operation.name(), destkey, keys), handler);
  }

  @Override
  public void bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit), handler);
  }

  @Override
  public void bitposFrom(String key, int bit, int start, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit, start), handler);
  }

  @Override
  public void bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", toPayload(key, bit, start, stop), handler);
  }

  @Override
  public void blpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", toPayload(key, seconds), handler);
  }

  @Override
  public void blpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", toPayload(keys, seconds), handler);
  }

  @Override
  public void brpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", toPayload(key, seconds), handler);
  }

  @Override
  public void brpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", toPayload(keys, seconds), handler);
  }

  @Override
  public void brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<Void>> handler) {
    sendVoid("BRPOPLPUSH", toPayload(key, destkey, seconds), handler);
  }

  @Override
  public void clientKill(KillFilter filter, Handler<AsyncResult<Long>> handler) {
    sendLong("CLIENT KILL", filter.toJsonArray(), handler);
  }

  @Override
  public void clientList(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT LIST", null, handler);
  }

  @Override
  public void clientGetname(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT GETNAME", null, handler);
  }

  @Override
  public void clientPause(long millis, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT PAUSE", toPayload(millis), handler);
  }

  @Override
  public void clientSetname(String name, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT SETNAME", toPayload(name), handler);
  }

  @Override
  public void clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CLUSTER SLOTS", null, handler);
  }

  @Override
  public void command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND", null, handler);
  }

  @Override
  public void commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong("COMMAND COUNT", null, handler);
  }

  @Override
  public void commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND GETKEYS", null, handler);
  }

  @Override
  public void commandInfo(List<String> commands, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND INFO", toPayload(commands), handler);
  }

  @Override
  public void configGet(String parameter, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CONFIG GET", toPayload(parameter), handler);
  }

  @Override
  public void configRewrite(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG REWRITE", null, handler);
  }

  @Override
  public void configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    sendString("CONFIG SET", toPayload(parameter, value), handler);
  }

  @Override
  public void configResetstat(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG RESETSTAT", null, handler);
  }

  @Override
  public void dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong("DBSIZE", null, handler);
  }

  @Override
  public void debugObject(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("DEBUG OBJECT", args, handler);
  }

  @Override
  public void debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString("DEBUG SEGFAULT", null, handler);
  }

  @Override
  public void decr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("DECR", toPayload(key), handler);
  }

  @Override
  public void decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    sendLong("DECRBY", toPayload(key, decrement), handler);
  }

  @Override
  public void del(List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("DEL", toPayload(keys), handler);
  }

  @Override
  public void discard(Handler<AsyncResult<String>> handler) {
    sendString("DISCARD", null, handler);
  }

  @Override
  public void dump(String key, Handler<AsyncResult<String>> handler) {
    sendString("DUMP", toPayload(key), handler);
  }

  @Override
  public void echo(String message, Handler<AsyncResult<String>> handler) {
    sendString("ECHO", toPayload(message), handler);
  }

  @Override
  public void eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    if (keys.size() != args.size()) {
      handler.handle(new RedisAsyncResult<Void>(new IllegalArgumentException("Key list, and argument list are not the same size")));
      return;
    }
    sendVoid("EVAL", toPayload(script, keys.size(), keys, args), handler);
  }

  @Override
  public void evalsha(String sha1, List<String> keys, List<String> args, Handler<AsyncResult<Void>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    if (keys.size() != args.size()) {
      handler.handle(new RedisAsyncResult<Void>(new IllegalArgumentException("Key list, and argument list are not the same size")));
      return;
    }
    sendVoid("EVALSHA", toPayload(sha1, keys.size(), keys, args), handler);
  }

  @Override
  public void exec(Handler<AsyncResult<Void>> handler) {
    sendVoid("EXEC", null, handler);
  }

  @Override
  public void exists(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("EXISTS", toPayload(key), handler);
  }

  @Override
  public void expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIRE", toPayload(key, seconds), handler);
  }

  @Override
  public void expireat(String key, long timestamp, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIREAT", toPayload(key, timestamp), handler);
  }

  @Override
  public void flushall(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHALL", null, handler);
  }

  @Override
  public void flushdb(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHDB", null, handler);
  }

  @Override
  public void get(String key, Handler<AsyncResult<String>> handler) {
    sendString("GET", toPayload(key), handler);
  }

  @Override
  public void getBinary(String key, Handler<AsyncResult<String>> handler) {
    send("GET", toPayload(key), String.class, true, handler);
  }

  @Override
  public void getbit(String key, long offset, Handler<AsyncResult<Long>> handler) {
    sendLong("GETBIT", toPayload(key, offset), handler);
  }

  @Override
  public void getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    sendString("GETRANGE", toPayload(key, start, end), handler);
  }

  @Override
  public void getset(String key, String value, Handler<AsyncResult<String>> handler) {
    sendString("GETSET", toPayload(key, value), handler);
  }

  @Override
  public void hdel(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", toPayload(key, field), handler);
  }

  @Override
  public void hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", toPayload(key, fields), handler);
  }

  @Override
  public void hexists(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong("HEXISTS", toPayload(key, field), handler);
  }

  @Override
  public void hget(String key, String field, Handler<AsyncResult<String>> handler) {
    sendString("HGET", toPayload(key, field), handler);
  }

  @Override
  public void hgetall(String key, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("HGETALL", toPayload(key), handler);
  }

  @Override
  public void hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong("HINCRBY", toPayload(key, field, increment), handler);
  }

  @Override
  public void hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    sendString("HINCRBYFLOAT", toPayload(key, field, increment), handler);
  }

  @Override
  public void hkeys(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HKEYS", toPayload(key), handler);
  }

  @Override
  public void hlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("HLEN", toPayload(key), handler);
  }

  @Override
  public void hmget(String key, List<String> fields, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HMGET", toPayload(key, fields), handler);
  }

  @Override
  public void hmset(String key, Map<String, String> values, Handler<AsyncResult<String>> handler) {
    sendString("HMSET", toPayload(key, values), handler);
  }

  @Override
  public void hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("HSET", toPayload(key, field, value), handler);
  }

  @Override
  public void hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("HSETNX", toPayload(key, field, value), handler);
  }

  @Override
  public void hvals(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HVALS", toPayload(key), handler);
  }

  @Override
  public void incr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("INCR", toPayload(key), handler);
  }

  @Override
  public void incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong("INCRBY", toPayload(key, increment), handler);
  }

  @Override
  public void incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    sendString("INCRBYFLOAT", toPayload(key, increment), handler);
  }

  @Override
  public void info(Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", new JsonArray(), handler);
  }

  @Override
  public void infoSection(String section, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", toPayload(section), handler);
  }

  @Override
  public void keys(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("KEYS", toPayload(pattern), handler);
  }

  @Override
  public void lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong("LASTSAVE", null, handler);
  }

  @Override
  public void lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    sendString("LINDEX", toPayload(key, index), handler);
  }

  @Override
  public void linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LINSERT", toPayload(key, option.name(), pivot, value), handler);
  }

  @Override
  public void llen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("LLEN", toPayload(key), handler);
  }

  @Override
  public void lpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("LPOP", toPayload(key), handler);
  }

  @Override
  public void lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", toPayload(key, values), handler);
  }

  @Override
  public void lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", toPayload(key, value), handler);
  }

  @Override
  public void lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSHX", toPayload(key, value), handler);
  }

  @Override
  public void lrange(String key, int from, int to, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("LRANGE", toPayload(key, from, to), handler);
  }

  @Override
  public void lrem(String key, int count, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LREM", toPayload(key, count, value), handler);
  }

  @Override
  public void lset(String key, int index, String value, Handler<AsyncResult<String>> handler) {
    sendString("LSET", toPayload(key, index, value), handler);
  }

  @Override
  public void ltrim(String key, int from, int to, Handler<AsyncResult<String>> handler) {
    sendString("LTRIM", toPayload(key, from, to), handler);
  }

  @Override
  public void mget(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("MGET", args, handler);
  }

  @Override
  public void migrate(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MIGRATE", args, handler);
  }

  @Override
  public void monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid("MONITOR", null, handler);
  }

  @Override
  public void move(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MOVE", args, handler);
  }

  @Override
  public void mset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MSET", args, handler);
  }

  @Override
  public void msetnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MSETNX", args, handler);
  }

  @Override
  public void multi(Handler<AsyncResult<String>> handler) {
    sendString("MULTI", null, handler);
  }

  @Override
  public void object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    sendVoid("OBJECT", toPayload(cmd.name(), key), handler);
  }

  @Override
  public void persist(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("PERSIST", toPayload(key), handler);
  }

  @Override
  public void pexpire(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIRE", args, handler);
  }

  @Override
  public void pexpireat(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIREAT", args, handler);
  }

  @Override
  public void pfadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFADD", args, handler);
  }

  @Override
  public void pfcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFCOUNT", args, handler);
  }

  @Override
  public void pfmerge(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("PFMERGE", args, handler);
  }

  @Override
  public void ping(Handler<AsyncResult<String>> handler) {
    sendString("PING", null, handler);
  }

  @Override
  public void psetex(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSETEX", args, handler);
  }

  @Override
  public void psubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSUBSCRIBE", args, handler);
  }

  @Override
  public void pubsub(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBSUB", args, handler);
  }

  @Override
  public void pttl(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PTTL", args, handler);
  }

  @Override
  public void publish(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBLISH", args, handler);
  }

  @Override
  public void punsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PUNSUBSCRIBE", args, handler);
  }

  @Override
  public void quit(Handler<AsyncResult<String>> handler) {
    sendString("QUIT", null, handler);
  }

  @Override
  public void randomkey(Handler<AsyncResult<String>> handler) {
    sendString("RANDOMKEY", null, handler);
  }

  @Override
  public void rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    sendString("RENAME", toPayload(key, newkey), handler);
  }

  @Override
  public void renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    sendLong("RENAMENX", toPayload(key, newkey), handler);
  }

  @Override
  public void restore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RESTORE", args, handler);
  }

  @Override
  public void role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ROLE", null, handler);
  }

  @Override
  public void rpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("RPOP", toPayload(key), handler);
  }

  @Override
  public void rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    sendString("RPOPLPUSH", toPayload(key, destkey), handler);
  }

  @Override
  public void rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", toPayload(key, values), handler);
  }

  @Override
  public void rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", toPayload(key, value), handler);
  }

  @Override
  public void rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSHX", toPayload(key, value), handler);
  }

  @Override
  public void sadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SADD", args, handler);
  }

  @Override
  public void save(Handler<AsyncResult<String>> handler) {
    sendString("SAVE", null, handler);
  }

  @Override
  public void scard(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SCARD", args, handler);
  }

  @Override
  public void scriptExists(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SCRIPT EXISTS", args, handler);
  }

  @Override
  public void scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT FLUSH", null, handler);
  }

  @Override
  public void scriptKill(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT KILL", null, handler);
  }

  @Override
  public void scriptLoad(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT LOAD", args, handler);
  }

  @Override
  public void sdiff(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SDIFF", args, handler);
  }

  @Override
  public void sdiffstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SDIFFSTORE", args, handler);
  }

  @Override
  public void select(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SELECT", args, handler);
  }

  @Override
  public void set(String key, String value, Handler<AsyncResult<Void>> handler) {
    setWithOptions(key, value, null, handler);
  }

  @Override
  public void setWithOptions(String key, String value, JsonArray options, Handler<AsyncResult<Void>> handler) {
    sendVoid("SET", toPayload(key, value, options != null ? options.getList() : null), handler);
  }

  @Override
  public void setBinary(String key, String value, Handler<AsyncResult<Void>> handler) {
    send("SET", toPayload(key, value), Void.class, true, handler);
  }

  @Override
  public void setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong("SETBIT", toPayload(key, offset, bit), handler);
  }

  @Override
  public void setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    sendString("SETEX", toPayload(key, seconds, value), handler);
  }

  @Override
  public void setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("SETNX", toPayload(key, value), handler);
  }

  @Override
  public void setrange(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETRANGE", args, handler);
  }

  @Override
  public void shutdown(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SHUTDOWN", args, handler);
  }

  @Override
  public void sinter(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SINTER", args, handler);
  }

  @Override
  public void sinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SINTERSTORE", args, handler);
  }

  @Override
  public void sismember(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SISMEMBER", args, handler);
  }

  @Override
  public void slaveof(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SLAVEOF", args, handler);
  }

  @Override
  public void slowlog(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SLOWLOG", args, handler);
  }

  @Override
  public void smembers(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SMEMBERS", args, handler);
  }

  @Override
  public void smove(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SMOVE", args, handler);
  }

  @Override
  public void sort(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SORT", args, handler);
  }

  @Override
  public void spop(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SPOP", args, handler);
  }

  @Override
  public void srandmember(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SRANDMEMBER", args, handler);
  }

  @Override
  public void srem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SREM", args, handler);
  }

  @Override
  public void strlen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("STRLEN", args, handler);
  }

  @Override
  public void subscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SUBSCRIBE", args, handler);
  }

  @Override
  public void sunion(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SUNION", args, handler);
  }

  @Override
  public void sunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SUNIONSTORE", args, handler);
  }

  @Override
  public void sync(Handler<AsyncResult<Void>> handler) {
    sendVoid("SYNC", null, handler);
  }

  @Override
  public void time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("TIME", null, handler);
  }

  @Override
  public void ttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("TTL", toPayload(key), handler);
  }

  @Override
  public void type(String key, Handler<AsyncResult<String>> handler) {
    sendString("TYPE", toPayload(key), handler);
  }

  @Override
  public void unsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("UNSUBSCRIBE", args, handler);
  }

  @Override
  public void unwatch(Handler<AsyncResult<String>> handler) {
    sendString("UNWATCH", null, handler);
  }

  @Override
  public void watch(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("WATCH", args, handler);
  }

  @Override
  public void zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) {
    sendLong("ZADD", toPayload(key, score, member), handler);
  }

  @Override
  public void zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<Long>> handler) {
    // flip from <String, Double> to <Double, String> when wrapping
    Stream flipped = members.entrySet().stream().map(e -> new Object[] { e.getValue(), e.getKey() });
    sendLong("ZADD", toPayload(key, flipped), handler);
  }

  @Override
  public void zcard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCARD", toPayload(key), handler);
  }

  @Override
  public void zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) {
    String minVal = (min == Double.NEGATIVE_INFINITY) ? "-inf" : String.valueOf(min);
    String maxVal = (max == Double.POSITIVE_INFINITY) ? "+inf" : String.valueOf(max);
    sendLong("ZCOUNT", toPayload(key, minVal, maxVal), handler);
  }

  @Override
  public void zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    sendString("ZINCRBY", toPayload(key, increment, member), handler);
  }

  @Override
  public void zinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZINTERSTORE", args, handler);
  }

  @Override
  public void zlexcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZLEXCOUNT", args, handler);
  }

  @Override
  public void zrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGE", args, handler);
  }

  @Override
  public void zrangebylex(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYLEX", args, handler);
  }

  @Override
  public void zrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYSCORE", args, handler);
  }

  @Override
  public void zrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZRANK", args, handler);
  }

  @Override
  public void zrem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREM", args, handler);
  }

  @Override
  public void zremrangebylex(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYLEX", args, handler);
  }

  @Override
  public void zremrangebyrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYRANK", args, handler);
  }

  @Override
  public void zremrangebyscore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYSCORE", args, handler);
  }

  @Override
  public void zrevrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGE", args, handler);
  }

  @Override
  public void zrevrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGEBYSCORE", args, handler);
  }

  @Override
  public void zrevrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREVRANK", args, handler);
  }

  @Override
  public void zscore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ZSCORE", args, handler);
  }

  @Override
  public void zunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZUNIONSTORE", args, handler);
  }

  @Override
  public void scan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SCAN", args, handler);
  }

  @Override
  public void sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid("SSCAN", toPayload(key, cursor, options != null ? options.toJsonArray().getList() : null), handler);
  }

  @Override
  public void hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HSCAN", toPayload(key, cursor, options != null ? options.toJsonArray().getList() : null), handler);
  }

  @Override
  public void zscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("ZSCAN", args, handler);
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
      if (param instanceof List) {
        for (Object el : (List) param) {
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
            for (Object item: (Object []) e) {
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

  /**
   * Merge two list into one by first adding the next item from the first list,
   * followed by the next item from the second list
   *
   * @param list1 First list
   * @param list2 Second list
   * @return JsonArray that can be passed to send()
   */
  private static JsonArray zip(List<?> list1, List<?> list2) {
    JsonArray result = new JsonArray();
    if (list1 == null && list2 == null) {
      return result;
    }
    if (list1.size() != list2.size()) {
      throw new IllegalArgumentException("Lists should be the same size");
    }

    Iterator<?> it1 = list1.iterator();
    Iterator<?> it2 = list2.iterator();
    while (it1.hasNext() && it2.hasNext()) {
      result.add(it1.next());
      result.add(it2.next());
    }
    return result;
  }
}
