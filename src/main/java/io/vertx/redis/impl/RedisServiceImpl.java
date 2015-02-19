package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.InsertOptions;
import io.vertx.redis.ObjectCmd;

import java.util.List;

public final class RedisServiceImpl extends AbstractRedisService {

  public RedisServiceImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }

  public void append(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("APPEND", args, handler);
  }

  public void auth(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("AUTH", args, handler);
  }

  public void bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString("BGREWRITEAOF", null, handler);
  }

  public void bgsave(Handler<AsyncResult<String>> handler) {
    sendString("BGSAVE", null, handler);
  }

  public void bitcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", args, handler);
  }

  public void bitop(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITOP", args, handler);
  }

  public void bitpos(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", args, handler);
  }

  public void blpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", args, handler);
  }

  public void brpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", args, handler);
  }

  public void brpoplpush(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("BRPOPLPUSH", args, handler);
  }

  public void clientKill(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("CLIENT KILL", args, handler);
  }

  public void clientList(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT LIST", null, handler);
  }

  public void clientGetname(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT GETNAME", null, handler);
  }

  public void clientPause(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT PAUSE", args, handler);
  }

  public void clientSetname(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT SETNAME", args, handler);
  }

  public void clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CLUSTER SLOTS", null, handler);
  }

  public void command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND", null, handler);
  }

  public void commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong("COMMAND COUNT", null, handler);
  }

  public void commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND GETKEYS", null, handler);
  }

  public void commandInfo(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND INFO", args, handler);
  }

  public void configGet(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CONFIG GET", args, handler);
  }

  public void configRewrite(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG REWRITE", null, handler);
  }

  public void configSet(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CONFIG SET", args, handler);
  }

  public void configResetstat(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG RESETSTAT", null, handler);
  }

  public void dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong("DBSIZE", null, handler);
  }

  public void debugObject(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("DEBUG OBJECT", args, handler);
  }

  public void debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString("DEBUG SEGFAULT", null, handler);
  }

  public void decr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("DECR", new JsonArray().add(key), handler);
  }

  public void decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(decrement);
    sendLong("DECRBY", args, handler);
  }

  public void del(List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong("DEL", new JsonArray(keys), handler);
  }

  public void discard(Handler<AsyncResult<String>> handler) {
    sendString("DISCARD", null, handler);
  }

  public void dump(String key, Handler<AsyncResult<String>> handler) {
    sendString("DUMP", new JsonArray().add(key), handler);
  }

  public void echo(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ECHO", args, handler);
  }

  public void eval(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("EVAL", args, handler);
  }

  public void evalsha(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("EVALSHA", args, handler);
  }

  public void exec(Handler<AsyncResult<Void>> handler) {
    sendVoid("EXEC", null, handler);
  }

  public void exists(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("EXISTS", new JsonArray().add(key), handler);
  }

  public void expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(seconds);
    sendLong("EXPIRE", args, handler);
  }

  public void expireat(String key, long timestamp, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(timestamp);
    sendLong("EXPIREAT", args, handler);
  }

  public void flushall(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHALL", null, handler);
  }

  public void flushdb(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHDB", null, handler);
  }

  public void get(String key, Handler<AsyncResult<String>> handler) {
    sendString("GET", new JsonArray().add(key), handler);
  }

  public void getbit(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("GETBIT", args, handler);
  }

  public void getrange(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("GETRANGE", args, handler);
  }

  public void getset(String key, String value, Handler<AsyncResult<String>> handler) {
    JsonArray params = new JsonArray();
    params.add(key);
    params.add(value);
    sendString("GETSET", params, handler);
  }

  public void hdel(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", args, handler);
  }

  public void hexists(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HEXISTS", args, handler);
  }

  public void hget(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HGET", args, handler);
  }

  public void hgetall(JsonArray args, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("HGETALL", args, handler);
  }

  public void hincrby(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HINCRBY", args, handler);
  }

  public void hincrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HINCRBYFLOAT", args, handler);
  }

  public void hkeys(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HKEYS", args, handler);
  }

  public void hlen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HLEN", args, handler);
  }

  public void hmget(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HMGET", args, handler);
  }

  public void hmset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HMSET", args, handler);
  }

  public void hset(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HSET", args, handler);
  }

  public void hsetnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HSETNX", args, handler);
  }

  public void hvals(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HVALS", args, handler);
  }

  public void incr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("INCR", new JsonArray().add(key), handler);
  }

  public void incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(increment);
    sendLong("INCRBY", args, handler);
  }

  public void incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(increment);
    sendString("INCRBYFLOAT", args, handler);
  }

  public void info(JsonArray args, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", args, handler);
  }

  public void keys(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("KEYS", args, handler);
  }

  public void lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong("LASTSAVE", null, handler);
  }

  public void lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(index);
    sendString("LINDEX", args, handler);
  }

  public void linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(option.name());
    args.add(pivot);
    args.add(value);
    sendLong("LINSERT", args, handler);
  }

  public void llen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("LLEN", new JsonArray().add(key), handler);
  }

  public void lpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("LPOP", new JsonArray().add(key), handler);
  }

  public void lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    for (String value: values) {
      args.add(value);
    }
    sendLong("LPUSH", args, handler);
  }

  public void lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", new JsonArray().add(key).add(value), handler);
  }

  public void lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSHX", new JsonArray().add(key).add(value), handler);
  }

  public void lrange(String key, int from, int to, Handler<AsyncResult<JsonArray>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(from);
    args.add(to);
    sendJsonArray("LRANGE", args, handler);
  }

  public void lrem(String key, int count, String value, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(count);
    args.add(value);
    sendLong("LREM", args, handler);
  }

  public void lset(String key, int index, String value, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(index);
    args.add(value);
    sendString("LSET", args, handler);
  }

  public void ltrim(String key, int from, int to, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(from);
    args.add(to);
    sendString("LTRIM", args, handler);
  }

  public void mget(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("MGET", args, handler);
  }

  public void migrate(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MIGRATE", args, handler);
  }

  public void monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid("MONITOR", null, handler);
  }

  public void move(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MOVE", args, handler);
  }

  public void mset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MSET", args, handler);
  }

  public void msetnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MSETNX", args, handler);
  }

  public void multi(Handler<AsyncResult<String>> handler) {
    sendString("MULTI", null, handler);
  }

  public void object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    JsonArray args = new JsonArray();
    args.add(cmd.name());
    args.add(key);
    sendVoid("OBJECT", args, handler);
  }

  public void persist(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("PERSIST", new JsonArray().add(key), handler);
  }

  public void pexpire(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIRE", args, handler);
  }

  public void pexpireat(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIREAT", args, handler);
  }

  public void pfadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFADD", args, handler);
  }

  public void pfcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFCOUNT", args, handler);
  }

  public void pfmerge(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("PFMERGE", args, handler);
  }

  public void ping(Handler<AsyncResult<String>> handler) {
    sendString("PING", null, handler);
  }

  public void psetex(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSETEX", args, handler);
  }

  public void psubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSUBSCRIBE", args, handler);
  }

  public void pubsub(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBSUB", args, handler);
  }

  public void pttl(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PTTL", args, handler);
  }

  public void publish(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBLISH", args, handler);
  }

  public void punsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PUNSUBSCRIBE", args, handler);
  }

  public void quit(Handler<AsyncResult<String>> handler) {
    sendString("QUIT", null, handler);
  }

  public void randomkey(Handler<AsyncResult<String>> handler) {
    sendString("RANDOMKEY", null, handler);
  }

  public void rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(newkey);
    sendString("RENAME", args, handler);
  }

  public void renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(newkey);
    sendLong("RENAMENX", args, handler);
  }

  public void restore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RESTORE", args, handler);
  }

  public void role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ROLE", null, handler);
  }

  public void rpop(String key, Handler<AsyncResult<String>> handler) {
    sendString("RPOP", new JsonArray().add(key), handler);
  }

  public void rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    sendString("RPOPLPUSH", new JsonArray().add(key).add(destkey), handler);
  }

  public void rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    for (String value: values) {
      args.add(value);
    }
    sendLong("RPUSH", args, handler);
  }

  public void rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", new JsonArray().add(key).add(value), handler);
  }

  public void rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSHX", new JsonArray().add(key).add(value), handler);
  }

  public void sadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SADD", args, handler);
  }

  public void save(Handler<AsyncResult<String>> handler) {
    sendString("SAVE", null, handler);
  }

  public void scard(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SCARD", args, handler);
  }

  public void scriptExists(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SCRIPT EXISTS", args, handler);
  }

  public void scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT FLUSH", null, handler);
  }

  public void scriptKill(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT KILL", null, handler);
  }

  public void scriptLoad(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT LOAD", args, handler);
  }

  public void sdiff(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SDIFF", args, handler);
  }

  public void sdiffstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SDIFFSTORE", args, handler);
  }

  public void select(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SELECT", args, handler);
  }

  public void set(String key, String value, Handler<AsyncResult<Void>> handler) {
    setWithOptions(key, value, null, handler);
  }

  public void setWithOptions(String key, String value, JsonArray options, Handler<AsyncResult<Void>> handler) {
    JsonArray params = new JsonArray();
    params.add(key);
    params.add(value);
    if (options != null) {
      for (Object option: options.getList()) {
        params.add(option);
      }
    }
    sendVoid("SET", params, handler);
  }

  public void setbit(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETBIT", args, handler);
  }

  public void setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    JsonArray args = new JsonArray();
    args.add(key);
    args.add(seconds);
    args.add(value);
    sendString("SETEX", args, handler);
  }

  public void setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    JsonArray params = new JsonArray();
    params.add(key);
    params.add(value);
    sendLong("SETNX", params, handler);
  }

  public void setrange(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETRANGE", args, handler);
  }

  public void shutdown(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SHUTDOWN", args, handler);
  }

  public void sinter(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SINTER", args, handler);
  }

  public void sinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SINTERSTORE", args, handler);
  }

  public void sismember(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SISMEMBER", args, handler);
  }

  public void slaveof(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SLAVEOF", args, handler);
  }

  public void slowlog(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SLOWLOG", args, handler);
  }

  public void smembers(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SMEMBERS", args, handler);
  }

  public void smove(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SMOVE", args, handler);
  }

  public void sort(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SORT", args, handler);
  }

  public void spop(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SPOP", args, handler);
  }

  public void srandmember(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SRANDMEMBER", args, handler);
  }

  public void srem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SREM", args, handler);
  }

  public void strlen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("STRLEN", args, handler);
  }

  public void subscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SUBSCRIBE", args, handler);
  }

  public void sunion(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SUNION", args, handler);
  }

  public void sunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SUNIONSTORE", args, handler);
  }

  public void sync(Handler<AsyncResult<Void>> handler) {
    sendVoid("SYNC", null, handler);
  }

  public void time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("TIME", null, handler);
  }

  public void ttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong("TTL", new JsonArray().add(key), handler);
  }

  public void type(String key, Handler<AsyncResult<String>> handler) {
    sendString("TYPE", new JsonArray().add(key), handler);
  }

  public void unsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("UNSUBSCRIBE", args, handler);
  }

  public void unwatch(Handler<AsyncResult<String>> handler) {
    sendString("UNWATCH", null, handler);
  }

  public void watch(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("WATCH", args, handler);
  }

  public void zadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZADD", args, handler);
  }

  public void zcard(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCARD", args, handler);
  }

  public void zcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCOUNT", args, handler);
  }

  public void zincrby(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ZINCRBY", args, handler);
  }

  public void zinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZINTERSTORE", args, handler);
  }

  public void zlexcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZLEXCOUNT", args, handler);
  }

  public void zrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGE", args, handler);
  }

  public void zrangebylex(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYLEX", args, handler);
  }

  public void zrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYSCORE", args, handler);
  }

  public void zrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZRANK", args, handler);
  }

  public void zrem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREM", args, handler);
  }

  public void zremrangebylex(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYLEX", args, handler);
  }

  public void zremrangebyrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYRANK", args, handler);
  }

  public void zremrangebyscore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYSCORE", args, handler);
  }

  public void zrevrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGE", args, handler);
  }

  public void zrevrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGEBYSCORE", args, handler);
  }

  public void zrevrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREVRANK", args, handler);
  }

  public void zscore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ZSCORE", args, handler);
  }

  public void zunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZUNIONSTORE", args, handler);
  }

  public void scan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SCAN", args, handler);
  }

  public void sscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SSCAN", args, handler);
  }

  public void hscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("HSCAN", args, handler);
  }

  public void zscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("ZSCAN", args, handler);
  }
}
