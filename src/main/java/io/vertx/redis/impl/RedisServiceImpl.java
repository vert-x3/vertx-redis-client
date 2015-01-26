package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisService;

public final class RedisServiceImpl extends AbstractRedisService {

  public RedisServiceImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }


  public RedisService append(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("APPEND", args, handler);
    return this;
  }

  public RedisService auth(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("AUTH", args, handler);
    return this;
  }

  public RedisService bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString("BGREWRITEAOF", null, handler);
    return this;
  }

  public RedisService bgsave(Handler<AsyncResult<String>> handler) {
    sendString("BGSAVE", null, handler);
    return this;
  }

  public RedisService bitcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITCOUNT", args, handler);
    return this;
  }

  public RedisService bitop(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITOP", args, handler);
    return this;
  }

  public RedisService bitpos(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("BITPOS", args, handler);
    return this;
  }

  public RedisService blpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BLPOP", args, handler);
    return this;
  }

  public RedisService brpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("BRPOP", args, handler);
    return this;
  }

  public RedisService brpoplpush(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("BRPOPLPUSH", args, handler);
    return this;
  }

  public RedisService clientKill(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("CLIENT KILL", args, handler);
    return this;
  }

  public RedisService clientList(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT LIST", null, handler);
    return this;
  }

  public RedisService clientGetname(Handler<AsyncResult<String>> handler) {
    sendString("CLIENT GETNAME", null, handler);
    return this;
  }

  public RedisService clientPause(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT PAUSE", args, handler);
    return this;
  }

  public RedisService clientSetname(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CLIENT SETNAME", args, handler);
    return this;
  }

  public RedisService clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CLUSTER SLOTS", null, handler);
    return this;
  }

  public RedisService command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND", null, handler);
    return this;
  }

  public RedisService commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong("COMMAND COUNT", null, handler);
    return this;
  }

  public RedisService commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND GETKEYS", null, handler);
    return this;
  }

  public RedisService commandInfo(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("COMMAND INFO", args, handler);
    return this;
  }

  public RedisService configGet(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("CONFIG GET", args, handler);
    return this;
  }

  public RedisService configRewrite(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG REWRITE", null, handler);
    return this;
  }

  public RedisService configSet(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("CONFIG SET", args, handler);
    return this;
  }

  public RedisService configResetstat(Handler<AsyncResult<String>> handler) {
    sendString("CONFIG RESETSTAT", null, handler);
    return this;
  }

  public RedisService dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong("DBSIZE", null, handler);
    return this;
  }

  public RedisService debugObject(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("DEBUG OBJECT", args, handler);
    return this;
  }

  public RedisService debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString("DEBUG SEGFAULT", null, handler);
    return this;
  }

  public RedisService decr(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("DECR", args, handler);
    return this;
  }

  public RedisService decrby(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("DECRBY", args, handler);
    return this;
  }

  public RedisService del(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("DEL", args, handler);
    return this;
  }

  public RedisService discard(Handler<AsyncResult<String>> handler) {
    sendString("DISCARD", null, handler);
    return this;
  }

  public RedisService dump(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("DUMP", args, handler);
    return this;
  }

  public RedisService echo(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ECHO", args, handler);
    return this;
  }

  public RedisService eval(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("EVAL", args, handler);
    return this;
  }

  public RedisService evalsha(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("EVALSHA", args, handler);
    return this;
  }

  public RedisService exec(Handler<AsyncResult<Void>> handler) {
    sendVoid("EXEC", null, handler);
    return this;
  }

  public RedisService exists(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("EXISTS", args, handler);
    return this;
  }

  public RedisService expire(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIRE", args, handler);
    return this;
  }

  public RedisService expireat(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("EXPIREAT", args, handler);
    return this;
  }

  public RedisService flushall(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHALL", null, handler);
    return this;
  }

  public RedisService flushdb(Handler<AsyncResult<String>> handler) {
    sendString("FLUSHDB", null, handler);
    return this;
  }

  public RedisService get(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("GET", args, handler);
    return this;
  }

  public RedisService getbit(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("GETBIT", args, handler);
    return this;
  }

  public RedisService getrange(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("GETRANGE", args, handler);
    return this;
  }

  public RedisService getset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("GETSET", args, handler);
    return this;
  }

  public RedisService hdel(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HDEL", args, handler);
    return this;
  }

  public RedisService hexists(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HEXISTS", args, handler);
    return this;
  }

  public RedisService hget(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HGET", args, handler);
    return this;
  }

  public RedisService hgetall(JsonArray args, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("HGETALL", args, handler);
    return this;
  }

  public RedisService hincrby(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HINCRBY", args, handler);
    return this;
  }

  public RedisService hincrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HINCRBYFLOAT", args, handler);
    return this;
  }

  public RedisService hkeys(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HKEYS", args, handler);
    return this;
  }

  public RedisService hlen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HLEN", args, handler);
    return this;
  }

  public RedisService hmget(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HMGET", args, handler);
    return this;
  }

  public RedisService hmset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("HMSET", args, handler);
    return this;
  }

  public RedisService hset(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HSET", args, handler);
    return this;
  }

  public RedisService hsetnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("HSETNX", args, handler);
    return this;
  }

  public RedisService hvals(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("HVALS", args, handler);
    return this;
  }

  public RedisService incr(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("INCR", args, handler);
    return this;
  }

  public RedisService incrby(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("INCRBY", args, handler);
    return this;
  }

  public RedisService incrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("INCRBYFLOAT", args, handler);
    return this;
  }

  public RedisService info(JsonArray args, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject("INFO", args, handler);
    return this;
  }

  public RedisService keys(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("KEYS", args, handler);
    return this;
  }

  public RedisService lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong("LASTSAVE", null, handler);
    return this;
  }

  public RedisService lindex(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("LINDEX", args, handler);
    return this;
  }

  public RedisService linsert(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("LINSERT", args, handler);
    return this;
  }

  public RedisService llen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("LLEN", args, handler);
    return this;
  }

  public RedisService lpop(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("LPOP", args, handler);
    return this;
  }

  public RedisService lpush(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSH", args, handler);
    return this;
  }

  public RedisService lpushx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("LPUSHX", args, handler);
    return this;
  }

  public RedisService lrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("LRANGE", args, handler);
    return this;
  }

  public RedisService lrem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("LREM", args, handler);
    return this;
  }

  public RedisService lset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("LSET", args, handler);
    return this;
  }

  public RedisService ltrim(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("LTRIM", args, handler);
    return this;
  }

  public RedisService mget(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("MGET", args, handler);
    return this;
  }

  public RedisService migrate(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MIGRATE", args, handler);
    return this;
  }

  public RedisService monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid("MONITOR", null, handler);
    return this;
  }

  public RedisService move(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MOVE", args, handler);
    return this;
  }

  public RedisService mset(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("MSET", args, handler);
    return this;
  }

  public RedisService msetnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("MSETNX", args, handler);
    return this;
  }

  public RedisService multi(Handler<AsyncResult<String>> handler) {
    sendString("MULTI", null, handler);
    return this;
  }

  public RedisService object(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("OBJECT", args, handler);
    return this;
  }

  public RedisService persist(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PERSIST", args, handler);
    return this;
  }

  public RedisService pexpire(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIRE", args, handler);
    return this;
  }

  public RedisService pexpireat(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PEXPIREAT", args, handler);
    return this;
  }

  public RedisService pfadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFADD", args, handler);
    return this;
  }

  public RedisService pfcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PFCOUNT", args, handler);
    return this;
  }

  public RedisService pfmerge(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("PFMERGE", args, handler);
    return this;
  }

  public RedisService ping(Handler<AsyncResult<String>> handler) {
    sendString("PING", null, handler);
    return this;
  }

  public RedisService psetex(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSETEX", args, handler);
    return this;
  }

  public RedisService psubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PSUBSCRIBE", args, handler);
    return this;
  }

  public RedisService pubsub(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBSUB", args, handler);
    return this;
  }

  public RedisService pttl(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PTTL", args, handler);
    return this;
  }

  public RedisService publish(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("PUBLISH", args, handler);
    return this;
  }

  public RedisService punsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("PUNSUBSCRIBE", args, handler);
    return this;
  }

  public RedisService quit(Handler<AsyncResult<String>> handler) {
    sendString("QUIT", null, handler);
    return this;
  }

  public RedisService randomkey(Handler<AsyncResult<String>> handler) {
    sendString("RANDOMKEY", null, handler);
    return this;
  }

  public RedisService rename(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RENAME", args, handler);
    return this;
  }

  public RedisService renamenx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("RENAMENX", args, handler);
    return this;
  }

  public RedisService restore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RESTORE", args, handler);
    return this;
  }

  public RedisService role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ROLE", null, handler);
    return this;
  }

  public RedisService rpop(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RPOP", args, handler);
    return this;
  }

  public RedisService rpoplpush(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("RPOPLPUSH", args, handler);
    return this;
  }

  public RedisService rpush(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSH", args, handler);
    return this;
  }

  public RedisService rpushx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("RPUSHX", args, handler);
    return this;
  }

  public RedisService sadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SADD", args, handler);
    return this;
  }

  public RedisService save(Handler<AsyncResult<String>> handler) {
    sendString("SAVE", null, handler);
    return this;
  }

  public RedisService scard(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SCARD", args, handler);
    return this;
  }

  public RedisService scriptExists(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SCRIPT EXISTS", args, handler);
    return this;
  }

  public RedisService scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT FLUSH", null, handler);
    return this;
  }

  public RedisService scriptKill(Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT KILL", null, handler);
    return this;
  }

  public RedisService scriptLoad(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SCRIPT LOAD", args, handler);
    return this;
  }

  public RedisService sdiff(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SDIFF", args, handler);
    return this;
  }

  public RedisService sdiffstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SDIFFSTORE", args, handler);
    return this;
  }

  public RedisService select(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SELECT", args, handler);
    return this;
  }

  public RedisService set(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SET", args, handler);
    return this;
  }

  public RedisService setbit(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETBIT", args, handler);
    return this;
  }

  public RedisService setex(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SETEX", args, handler);
    return this;
  }

  public RedisService setnx(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETNX", args, handler);
    return this;
  }

  public RedisService setrange(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SETRANGE", args, handler);
    return this;
  }

  public RedisService shutdown(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SHUTDOWN", args, handler);
    return this;
  }

  public RedisService sinter(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SINTER", args, handler);
    return this;
  }

  public RedisService sinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SINTERSTORE", args, handler);
    return this;
  }

  public RedisService sismember(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SISMEMBER", args, handler);
    return this;
  }

  public RedisService slaveof(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SLAVEOF", args, handler);
    return this;
  }

  public RedisService slowlog(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SLOWLOG", args, handler);
    return this;
  }

  public RedisService smembers(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SMEMBERS", args, handler);
    return this;
  }

  public RedisService smove(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SMOVE", args, handler);
    return this;
  }

  public RedisService sort(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SORT", args, handler);
    return this;
  }

  public RedisService spop(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("SPOP", args, handler);
    return this;
  }

  public RedisService srandmember(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SRANDMEMBER", args, handler);
    return this;
  }

  public RedisService srem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SREM", args, handler);
    return this;
  }

  public RedisService strlen(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("STRLEN", args, handler);
    return this;
  }

  public RedisService subscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SUBSCRIBE", args, handler);
    return this;
  }

  public RedisService sunion(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("SUNION", args, handler);
    return this;
  }

  public RedisService sunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("SUNIONSTORE", args, handler);
    return this;
  }

  public RedisService sync(Handler<AsyncResult<Void>> handler) {
    sendVoid("SYNC", null, handler);
    return this;
  }

  public RedisService time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("TIME", null, handler);
    return this;
  }

  public RedisService ttl(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("TTL", args, handler);
    return this;
  }

  public RedisService type(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("TYPE", args, handler);
    return this;
  }

  public RedisService unsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("UNSUBSCRIBE", args, handler);
    return this;
  }

  public RedisService unwatch(Handler<AsyncResult<String>> handler) {
    sendString("UNWATCH", null, handler);
    return this;
  }

  public RedisService watch(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("WATCH", args, handler);
    return this;
  }

  public RedisService zadd(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZADD", args, handler);
    return this;
  }

  public RedisService zcard(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCARD", args, handler);
    return this;
  }

  public RedisService zcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZCOUNT", args, handler);
    return this;
  }

  public RedisService zincrby(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ZINCRBY", args, handler);
    return this;
  }

  public RedisService zinterstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZINTERSTORE", args, handler);
    return this;
  }

  public RedisService zlexcount(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZLEXCOUNT", args, handler);
    return this;
  }

  public RedisService zrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGE", args, handler);
    return this;
  }

  public RedisService zrangebylex(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYLEX", args, handler);
    return this;
  }

  public RedisService zrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZRANGEBYSCORE", args, handler);
    return this;
  }

  public RedisService zrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZRANK", args, handler);
    return this;
  }

  public RedisService zrem(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREM", args, handler);
    return this;
  }

  public RedisService zremrangebylex(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYLEX", args, handler);
    return this;
  }

  public RedisService zremrangebyrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYRANK", args, handler);
    return this;
  }

  public RedisService zremrangebyscore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREMRANGEBYSCORE", args, handler);
    return this;
  }

  public RedisService zrevrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGE", args, handler);
    return this;
  }

  public RedisService zrevrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray("ZREVRANGEBYSCORE", args, handler);
    return this;
  }

  public RedisService zrevrank(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZREVRANK", args, handler);
    return this;
  }

  public RedisService zscore(JsonArray args, Handler<AsyncResult<String>> handler) {
    sendString("ZSCORE", args, handler);
    return this;
  }

  public RedisService zunionstore(JsonArray args, Handler<AsyncResult<Long>> handler) {
    sendLong("ZUNIONSTORE", args, handler);
    return this;
  }

  public RedisService scan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SCAN", args, handler);
    return this;
  }

  public RedisService sscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("SSCAN", args, handler);
    return this;
  }

  public RedisService hscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("HSCAN", args, handler);
    return this;
  }

  public RedisService zscan(JsonArray args, Handler<AsyncResult<Void>> handler) {
    sendVoid("ZSCAN", args, handler);
    return this;
  }

}
