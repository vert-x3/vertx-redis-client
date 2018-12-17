/*
 * Copyright 2018 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client;

import java.nio.charset.StandardCharsets;

import io.vertx.codegen.annotations.VertxGen;

/**
 * <b>Auto generated</b> API Commands to interact with REDIS.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@VertxGen
public enum Command {
  APPEND("append", 3, 1, 1, 1, false, false),
  ASKING("asking", 1, 0, 0, 0, false, false),
  AUTH("auth", 2, 0, 0, 0, false, false),
  BGREWRITEAOF("bgrewriteaof", 1, 0, 0, 0, false, false),
  BGSAVE("bgsave", -1, 0, 0, 0, false, false),
  BITCOUNT("bitcount", -2, 1, 1, 1, true, false),
  BITFIELD("bitfield", -2, 1, 1, 1, false, false),
  BITOP("bitop", -4, 2, -1, 1, false, false),
  BITPOS("bitpos", -3, 1, 1, 1, true, false),
  BLPOP("blpop", -3, 1, -2, 1, false, false),
  BRPOP("brpop", -3, 1, -2, 1, false, false),
  BRPOPLPUSH("brpoplpush", 4, 1, 2, 1, false, false),
  BZPOPMAX("bzpopmax", -2, 1, -2, 1, false, false),
  BZPOPMIN("bzpopmin", -2, 1, -2, 1, false, false),
  CLIENT("client", -2, 0, 0, 0, false, false),
  CLUSTER("cluster", -2, 0, 0, 0, false, false),
  COMMAND("command", 0, 0, 0, 0, false, false),
  CONFIG("config", -2, 0, 0, 0, false, false),
  DBSIZE("dbsize", 1, 0, 0, 0, true, false),
  DEBUG("debug", -2, 0, 0, 0, false, false),
  DECR("decr", 2, 1, 1, 1, false, false),
  DECRBY("decrby", 3, 1, 1, 1, false, false),
  DEL("del", -2, 1, -1, 1, false, false),
  DISCARD("discard", 1, 0, 0, 0, false, false),
  DUMP("dump", 2, 1, 1, 1, true, false),
  ECHO("echo", 2, 0, 0, 0, false, false),
  EVAL("eval", -3, 0, 0, 0, false, true),
  EVALSHA("evalsha", -3, 0, 0, 0, false, true),
  EXEC("exec", 1, 0, 0, 0, false, false),
  EXISTS("exists", -2, 1, -1, 1, true, false),
  EXPIRE("expire", 3, 1, 1, 1, false, false),
  EXPIREAT("expireat", 3, 1, 1, 1, false, false),
  FLUSHALL("flushall", -1, 0, 0, 0, false, false),
  FLUSHDB("flushdb", -1, 0, 0, 0, false, false),
  GEOADD("geoadd", -5, 1, 1, 1, false, false),
  GEODIST("geodist", -4, 1, 1, 1, true, false),
  GEOHASH("geohash", -2, 1, 1, 1, true, false),
  GEOPOS("geopos", -2, 1, 1, 1, true, false),
  GEORADIUS("georadius", -6, 1, 1, 1, false, true),
  GEORADIUS_RO("georadius_ro", -6, 1, 1, 1, true, true),
  GEORADIUSBYMEMBER("georadiusbymember", -5, 1, 1, 1, false, true),
  GEORADIUSBYMEMBER_RO("georadiusbymember_ro", -5, 1, 1, 1, true, true),
  GET("get", 2, 1, 1, 1, true, false),
  GETBIT("getbit", 3, 1, 1, 1, true, false),
  GETRANGE("getrange", 4, 1, 1, 1, true, false),
  GETSET("getset", 3, 1, 1, 1, false, false),
  HDEL("hdel", -3, 1, 1, 1, false, false),
  HEXISTS("hexists", 3, 1, 1, 1, true, false),
  HGET("hget", 3, 1, 1, 1, true, false),
  HGETALL("hgetall", 2, 1, 1, 1, true, false),
  HINCRBY("hincrby", 4, 1, 1, 1, false, false),
  HINCRBYFLOAT("hincrbyfloat", 4, 1, 1, 1, false, false),
  HKEYS("hkeys", 2, 1, 1, 1, true, false),
  HLEN("hlen", 2, 1, 1, 1, true, false),
  HMGET("hmget", -3, 1, 1, 1, true, false),
  HMSET("hmset", -4, 1, 1, 1, false, false),
  HOST("host:", -1, 0, 0, 0, false, false),
  HSCAN("hscan", -3, 1, 1, 1, true, false),
  HSET("hset", -4, 1, 1, 1, false, false),
  HSETNX("hsetnx", 4, 1, 1, 1, false, false),
  HSTRLEN("hstrlen", 3, 1, 1, 1, true, false),
  HVALS("hvals", 2, 1, 1, 1, true, false),
  INCR("incr", 2, 1, 1, 1, false, false),
  INCRBY("incrby", 3, 1, 1, 1, false, false),
  INCRBYFLOAT("incrbyfloat", 3, 1, 1, 1, false, false),
  INFO("info", -1, 0, 0, 0, false, false),
  KEYS("keys", 2, 0, 0, 0, true, false),
  LASTSAVE("lastsave", 1, 0, 0, 0, false, false),
  LATENCY("latency", -2, 0, 0, 0, false, false),
  LINDEX("lindex", 3, 1, 1, 1, true, false),
  LINSERT("linsert", 5, 1, 1, 1, false, false),
  LLEN("llen", 2, 1, 1, 1, true, false),
  LOLWUT("lolwut", -1, 0, 0, 0, true, false),
  LPOP("lpop", 2, 1, 1, 1, false, false),
  LPUSH("lpush", -3, 1, 1, 1, false, false),
  LPUSHX("lpushx", -3, 1, 1, 1, false, false),
  LRANGE("lrange", 4, 1, 1, 1, true, false),
  LREM("lrem", 4, 1, 1, 1, false, false),
  LSET("lset", 4, 1, 1, 1, false, false),
  LTRIM("ltrim", 4, 1, 1, 1, false, false),
  MEMORY("memory", -2, 0, 0, 0, true, false),
  MGET("mget", -2, 1, -1, 1, true, false),
  MIGRATE("migrate", -6, 0, 0, 0, false, true),
  MODULE("module", -2, 0, 0, 0, false, false),
  MONITOR("monitor", 1, 0, 0, 0, false, false),
  MOVE("move", 3, 1, 1, 1, false, false),
  MSET("mset", -3, 1, -1, 2, false, false),
  MSETNX("msetnx", -3, 1, -1, 2, false, false),
  MULTI("multi", 1, 0, 0, 0, false, false),
  OBJECT("object", -2, 2, 2, 1, true, false),
  PERSIST("persist", 2, 1, 1, 1, false, false),
  PEXPIRE("pexpire", 3, 1, 1, 1, false, false),
  PEXPIREAT("pexpireat", 3, 1, 1, 1, false, false),
  PFADD("pfadd", -2, 1, 1, 1, false, false),
  PFCOUNT("pfcount", -2, 1, -1, 1, true, false),
  PFDEBUG("pfdebug", -3, 0, 0, 0, false, false),
  PFMERGE("pfmerge", -2, 1, -1, 1, false, false),
  PFSELFTEST("pfselftest", 1, 0, 0, 0, false, false),
  PING("ping", -1, 0, 0, 0, false, false),
  POST("post", -1, 0, 0, 0, false, false),
  PSETEX("psetex", 4, 1, 1, 1, false, false),
  PSUBSCRIBE("psubscribe", -2, 0, 0, 0, false, false),
  PSYNC("psync", 3, 0, 0, 0, true, false),
  PTTL("pttl", 2, 1, 1, 1, true, false),
  PUBLISH("publish", 3, 0, 0, 0, false, false),
  PUBSUB("pubsub", -2, 0, 0, 0, false, false),
  PUNSUBSCRIBE("punsubscribe", -1, 0, 0, 0, false, false),
  RANDOMKEY("randomkey", 1, 0, 0, 0, true, false),
  READONLY("readonly", 1, 0, 0, 0, false, false),
  READWRITE("readwrite", 1, 0, 0, 0, false, false),
  RENAME("rename", 3, 1, 2, 1, false, false),
  RENAMENX("renamenx", 3, 1, 2, 1, false, false),
  REPLCONF("replconf", -1, 0, 0, 0, false, false),
  REPLICAOF("replicaof", 3, 0, 0, 0, false, false),
  RESTORE("restore", -4, 1, 1, 1, false, false),
  RESTORE_ASKING("restore-asking", -4, 1, 1, 1, false, false),
  ROLE("role", 1, 0, 0, 0, false, false),
  RPOP("rpop", 2, 1, 1, 1, false, false),
  RPOPLPUSH("rpoplpush", 3, 1, 2, 1, false, false),
  RPUSH("rpush", -3, 1, 1, 1, false, false),
  RPUSHX("rpushx", -3, 1, 1, 1, false, false),
  SADD("sadd", -3, 1, 1, 1, false, false),
  SAVE("save", 1, 0, 0, 0, false, false),
  SCAN("scan", -2, 0, 0, 0, true, false),
  SCARD("scard", 2, 1, 1, 1, true, false),
  SCRIPT("script", -2, 0, 0, 0, false, false),
  SDIFF("sdiff", -2, 1, -1, 1, true, false),
  SDIFFSTORE("sdiffstore", -3, 1, -1, 1, false, false),
  SELECT("select", 2, 0, 0, 0, false, false),
  SET("set", -3, 1, 1, 1, false, false),
  SETBIT("setbit", 4, 1, 1, 1, false, false),
  SETEX("setex", 4, 1, 1, 1, false, false),
  SETNX("setnx", 3, 1, 1, 1, false, false),
  SETRANGE("setrange", 4, 1, 1, 1, false, false),
  SHUTDOWN("shutdown", -1, 0, 0, 0, false, false),
  SINTER("sinter", -2, 1, -1, 1, true, false),
  SINTERSTORE("sinterstore", -3, 1, -1, 1, false, false),
  SISMEMBER("sismember", 3, 1, 1, 1, true, false),
  SLAVEOF("slaveof", 3, 0, 0, 0, false, false),
  SLOWLOG("slowlog", -2, 0, 0, 0, false, false),
  SMEMBERS("smembers", 2, 1, 1, 1, true, false),
  SMOVE("smove", 4, 1, 2, 1, false, false),
  SORT("sort", -2, 1, 1, 1, false, true),
  SPOP("spop", -2, 1, 1, 1, false, false),
  SRANDMEMBER("srandmember", -2, 1, 1, 1, true, false),
  SREM("srem", -3, 1, 1, 1, false, false),
  SSCAN("sscan", -3, 1, 1, 1, true, false),
  STRLEN("strlen", 2, 1, 1, 1, true, false),
  SUBSCRIBE("subscribe", -2, 0, 0, 0, false, false),
  SUBSTR("substr", 4, 1, 1, 1, true, false),
  SUNION("sunion", -2, 1, -1, 1, true, false),
  SUNIONSTORE("sunionstore", -3, 1, -1, 1, false, false),
  SWAPDB("swapdb", 3, 0, 0, 0, false, false),
  SYNC("sync", 1, 0, 0, 0, true, false),
  TIME("time", 1, 0, 0, 0, false, false),
  TOUCH("touch", -2, 1, 1, 1, true, false),
  TTL("ttl", 2, 1, 1, 1, true, false),
  TYPE("type", 2, 1, 1, 1, true, false),
  UNLINK("unlink", -2, 1, -1, 1, false, false),
  UNSUBSCRIBE("unsubscribe", -1, 0, 0, 0, false, false),
  UNWATCH("unwatch", 1, 0, 0, 0, false, false),
  WAIT("wait", 3, 0, 0, 0, false, false),
  WATCH("watch", -2, 1, -1, 1, false, false),
  XACK("xack", -4, 1, 1, 1, false, false),
  XADD("xadd", -5, 1, 1, 1, false, false),
  XCLAIM("xclaim", -6, 1, 1, 1, false, false),
  XDEL("xdel", -3, 1, 1, 1, false, false),
  XGROUP("xgroup", -2, 2, 2, 1, false, false),
  XINFO("xinfo", -2, 2, 2, 1, true, false),
  XLEN("xlen", 2, 1, 1, 1, true, false),
  XPENDING("xpending", -3, 1, 1, 1, true, false),
  XRANGE("xrange", -4, 1, 1, 1, true, false),
  XREAD("xread", -4, 1, 1, 1, true, true),
  XREADGROUP("xreadgroup", -7, 1, 1, 1, false, true),
  XREVRANGE("xrevrange", -4, 1, 1, 1, true, false),
  XSETID("xsetid", 3, 1, 1, 1, false, false),
  XTRIM("xtrim", -2, 1, 1, 1, false, false),
  ZADD("zadd", -4, 1, 1, 1, false, false),
  ZCARD("zcard", 2, 1, 1, 1, true, false),
  ZCOUNT("zcount", 4, 1, 1, 1, true, false),
  ZINCRBY("zincrby", 4, 1, 1, 1, false, false),
  ZINTERSTORE("zinterstore", -4, 0, 0, 0, false, true),
  ZLEXCOUNT("zlexcount", 4, 1, 1, 1, true, false),
  ZPOPMAX("zpopmax", -2, 1, 1, 1, false, false),
  ZPOPMIN("zpopmin", -2, 1, 1, 1, false, false),
  ZRANGE("zrange", -4, 1, 1, 1, true, false),
  ZRANGEBYLEX("zrangebylex", -4, 1, 1, 1, true, false),
  ZRANGEBYSCORE("zrangebyscore", -4, 1, 1, 1, true, false),
  ZRANK("zrank", 3, 1, 1, 1, true, false),
  ZREM("zrem", -3, 1, 1, 1, false, false),
  ZREMRANGEBYLEX("zremrangebylex", 4, 1, 1, 1, false, false),
  ZREMRANGEBYRANK("zremrangebyrank", 4, 1, 1, 1, false, false),
  ZREMRANGEBYSCORE("zremrangebyscore", 4, 1, 1, 1, false, false),
  ZREVRANGE("zrevrange", -4, 1, 1, 1, true, false),
  ZREVRANGEBYLEX("zrevrangebylex", -4, 1, 1, 1, true, false),
  ZREVRANGEBYSCORE("zrevrangebyscore", -4, 1, 1, 1, true, false),
  ZREVRANK("zrevrank", 3, 1, 1, 1, true, false),
  ZSCAN("zscan", -3, 1, 1, 1, true, false),
  ZSCORE("zscore", 3, 1, 1, 1, true, false),
  ZUNIONSTORE("zunionstore", -4, 0, 0, 0, false, true),
  SENTINEL("sentinel", -1, 0, 0, 0, false, false);

  private final byte[] bytes;
  private final int arity;

  private final boolean multiKey;
  private final int firstKey;
  private final int lastKey;
  private final int interval;
  private final boolean keyless;
  private final boolean readOnly;
  private final boolean movable;

  Command(String command, int arity, int firstKey, int lastKey, int interval, boolean readOnly, boolean movable) {
    bytes = ("$" + command.length() + "\r\n" + command + "\r\n").getBytes(StandardCharsets.ISO_8859_1);
    this.arity = arity;
    this.firstKey = firstKey;
    this.lastKey = lastKey;
    this.interval = interval;
    this.multiKey = lastKey < 0;
    this.keyless = interval == 0 && !movable;
    this.readOnly = readOnly;
    this.movable = movable;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public int getArity() {
    return arity;
  }

  public boolean isMultiKey() {
    return multiKey;
  }

  public int getFirstKey() {
    return firstKey;
  }

  public int getLastKey() {
    return lastKey;
  }

  public int getInterval() {
    return interval;
  }

  public boolean isKeyless() {
    return keyless;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public boolean isMovable() {
    return movable;
  }
}
