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
  APPEND("append", 3, 1, 1, 1, false, false, false),
  ASKING("asking", 1, 0, 0, 0, false, false, false),
  AUTH("auth", 2, 0, 0, 0, false, false, false),
  BGREWRITEAOF("bgrewriteaof", 1, 0, 0, 0, false, false, false),
  BGSAVE("bgsave", -1, 0, 0, 0, false, false, false),
  BITCOUNT("bitcount", -2, 1, 1, 1, true, false, false),
  BITFIELD("bitfield", -2, 1, 1, 1, false, false, false),
  BITOP("bitop", -4, 2, -1, 1, false, false, false),
  BITPOS("bitpos", -3, 1, 1, 1, true, false, false),
  BLPOP("blpop", -3, 1, -2, 1, false, false, false),
  BRPOP("brpop", -3, 1, -2, 1, false, false, false),
  BRPOPLPUSH("brpoplpush", 4, 1, 2, 1, false, false, false),
  BZPOPMAX("bzpopmax", -2, 1, -2, 1, false, false, false),
  BZPOPMIN("bzpopmin", -2, 1, -2, 1, false, false, false),
  CLIENT("client", -2, 0, 0, 0, false, false, false),
  CLUSTER("cluster", -2, 0, 0, 0, false, false, false),
  COMMAND("command", 0, 0, 0, 0, false, false, false),
  CONFIG("config", -2, 0, 0, 0, false, false, false),
  DBSIZE("dbsize", 1, 0, 0, 0, true, false, false),
  DEBUG("debug", -2, 0, 0, 0, false, false, false),
  DECR("decr", 2, 1, 1, 1, false, false, false),
  DECRBY("decrby", 3, 1, 1, 1, false, false, false),
  DEL("del", -2, 1, -1, 1, false, false, false),
  DISCARD("discard", 1, 0, 0, 0, false, false, false),
  DUMP("dump", 2, 1, 1, 1, true, false, false),
  ECHO("echo", 2, 0, 0, 0, false, false, false),
  EVAL("eval", -3, 0, 0, 0, false, true, false),
  EVALSHA("evalsha", -3, 0, 0, 0, false, true, false),
  EXEC("exec", 1, 0, 0, 0, false, false, false),
  EXISTS("exists", -2, 1, -1, 1, true, false, false),
  EXPIRE("expire", 3, 1, 1, 1, false, false, false),
  EXPIREAT("expireat", 3, 1, 1, 1, false, false, false),
  FLUSHALL("flushall", -1, 0, 0, 0, false, false, false),
  FLUSHDB("flushdb", -1, 0, 0, 0, false, false, true),
  GEOADD("geoadd", -5, 1, 1, 1, false, false, false),
  GEODIST("geodist", -4, 1, 1, 1, true, false, true),
  GEOHASH("geohash", -2, 1, 1, 1, true, false, false),
  GEOPOS("geopos", -2, 1, 1, 1, true, false, false),
  GEORADIUS("georadius", -6, 1, 1, 1, false, true, false),
  GEORADIUS_RO("georadius_ro", -6, 1, 1, 1, true, true, false),
  GEORADIUSBYMEMBER("georadiusbymember", -5, 1, 1, 1, false, true, false),
  GEORADIUSBYMEMBER_RO("georadiusbymember_ro", -5, 1, 1, 1, true, true, false),
  GET("get", 2, 1, 1, 1, true, false, false),
  GETBIT("getbit", 3, 1, 1, 1, true, false, false),
  GETRANGE("getrange", 4, 1, 1, 1, true, false, false),
  GETSET("getset", 3, 1, 1, 1, false, false, false),
  HDEL("hdel", -3, 1, 1, 1, false, false, false),
  HEXISTS("hexists", 3, 1, 1, 1, true, false, false),
  HGET("hget", 3, 1, 1, 1, true, false, false),
  HGETALL("hgetall", 2, 1, 1, 1, true, false, false),
  HINCRBY("hincrby", 4, 1, 1, 1, false, false, false),
  HINCRBYFLOAT("hincrbyfloat", 4, 1, 1, 1, false, false, false),
  HKEYS("hkeys", 2, 1, 1, 1, true, false, false),
  HLEN("hlen", 2, 1, 1, 1, true, false, false),
  HMGET("hmget", -3, 1, 1, 1, true, false, false),
  HMSET("hmset", -4, 1, 1, 1, false, false, false),
  HOST("host:", -1, 0, 0, 0, false, false, false),
  HSCAN("hscan", -3, 1, 1, 1, true, false, false),
  HSET("hset", -4, 1, 1, 1, false, false, false),
  HSETNX("hsetnx", 4, 1, 1, 1, false, false, false),
  HSTRLEN("hstrlen", 3, 1, 1, 1, true, false, false),
  HVALS("hvals", 2, 1, 1, 1, true, false, false),
  INCR("incr", 2, 1, 1, 1, false, false, false),
  INCRBY("incrby", 3, 1, 1, 1, false, false, false),
  INCRBYFLOAT("incrbyfloat", 3, 1, 1, 1, false, false, false),
  INFO("info", -1, 0, 0, 0, false, false, false),
  KEYS("keys", 2, 0, 0, 0, true, false, true),
  LASTSAVE("lastsave", 1, 0, 0, 0, false, false, false),
  LATENCY("latency", -2, 0, 0, 0, false, false, false),
  LINDEX("lindex", 3, 1, 1, 1, true, false, false),
  LINSERT("linsert", 5, 1, 1, 1, false, false, false),
  LLEN("llen", 2, 1, 1, 1, true, false, false),
  LOLWUT("lolwut", -1, 0, 0, 0, true, false, false),
  LPOP("lpop", 2, 1, 1, 1, false, false, false),
  LPUSH("lpush", -3, 1, 1, 1, false, false, false),
  LPUSHX("lpushx", -3, 1, 1, 1, false, false, false),
  LRANGE("lrange", 4, 1, 1, 1, true, false, false),
  LREM("lrem", 4, 1, 1, 1, false, false, false),
  LSET("lset", 4, 1, 1, 1, false, false, false),
  LTRIM("ltrim", 4, 1, 1, 1, false, false, false),
  MEMORY("memory", -2, 0, 0, 0, true, false, false),
  MGET("mget", -2, 1, -1, 1, true, false, false),
  MIGRATE("migrate", -6, 0, 0, 0, false, true, false),
  MODULE("module", -2, 0, 0, 0, false, false, false),
  MONITOR("monitor", 1, 0, 0, 0, false, false, false),
  MOVE("move", 3, 1, 1, 1, false, false, false),
  MSET("mset", -3, 1, -1, 2, false, false, false),
  MSETNX("msetnx", -3, 1, -1, 2, false, false, false),
  MULTI("multi", 1, 0, 0, 0, false, false, false),
  OBJECT("object", -2, 2, 2, 1, true, false, false),
  PERSIST("persist", 2, 1, 1, 1, false, false, false),
  PEXPIRE("pexpire", 3, 1, 1, 1, false, false, false),
  PEXPIREAT("pexpireat", 3, 1, 1, 1, false, false, false),
  PFADD("pfadd", -2, 1, 1, 1, false, false, false),
  PFCOUNT("pfcount", -2, 1, -1, 1, true, false, false),
  PFDEBUG("pfdebug", -3, 0, 0, 0, false, false, false),
  PFMERGE("pfmerge", -2, 1, -1, 1, false, false, false),
  PFSELFTEST("pfselftest", 1, 0, 0, 0, false, false, false),
  PING("ping", -1, 0, 0, 0, false, false, false),
  POST("post", -1, 0, 0, 0, false, false, false),
  PSETEX("psetex", 4, 1, 1, 1, false, false, false),
  PSUBSCRIBE("psubscribe", -2, 0, 0, 0, false, false, false),
  PSYNC("psync", 3, 0, 0, 0, true, false, false),
  PTTL("pttl", 2, 1, 1, 1, true, false, false),
  PUBLISH("publish", 3, 0, 0, 0, false, false, false),
  PUBSUB("pubsub", -2, 0, 0, 0, false, false, false),
  PUNSUBSCRIBE("punsubscribe", -1, 0, 0, 0, false, false, false),
  RANDOMKEY("randomkey", 1, 0, 0, 0, true, false, false),
  READONLY("readonly", 1, 0, 0, 0, false, false, false),
  READWRITE("readwrite", 1, 0, 0, 0, false, false, false),
  RENAME("rename", 3, 1, 2, 1, false, false, false),
  RENAMENX("renamenx", 3, 1, 2, 1, false, false, false),
  REPLCONF("replconf", -1, 0, 0, 0, false, false, false),
  REPLICAOF("replicaof", 3, 0, 0, 0, false, false, false),
  RESTORE("restore", -4, 1, 1, 1, false, false, false),
  RESTORE_ASKING("restore-asking", -4, 1, 1, 1, false, false, false),
  ROLE("role", 1, 0, 0, 0, false, false, false),
  RPOP("rpop", 2, 1, 1, 1, false, false, false),
  RPOPLPUSH("rpoplpush", 3, 1, 2, 1, false, false, false),
  RPUSH("rpush", -3, 1, 1, 1, false, false, false),
  RPUSHX("rpushx", -3, 1, 1, 1, false, false, false),
  SADD("sadd", -3, 1, 1, 1, false, false, false),
  SAVE("save", 1, 0, 0, 0, false, false, false),
  SCAN("scan", -2, 0, 0, 0, true, false, false),
  SCARD("scard", 2, 1, 1, 1, true, false, false),
  SCRIPT("script", -2, 0, 0, 0, false, false, false),
  SDIFF("sdiff", -2, 1, -1, 1, true, false, false),
  SDIFFSTORE("sdiffstore", -3, 1, -1, 1, false, false, false),
  SELECT("select", 2, 0, 0, 0, false, false, false),
  SET("set", -3, 1, 1, 1, false, false, false),
  SETBIT("setbit", 4, 1, 1, 1, false, false, false),
  SETEX("setex", 4, 1, 1, 1, false, false, false),
  SETNX("setnx", 3, 1, 1, 1, false, false, false),
  SETRANGE("setrange", 4, 1, 1, 1, false, false, false),
  SHUTDOWN("shutdown", -1, 0, 0, 0, false, false, false),
  SINTER("sinter", -2, 1, -1, 1, true, false, false),
  SINTERSTORE("sinterstore", -3, 1, -1, 1, false, false, false),
  SISMEMBER("sismember", 3, 1, 1, 1, true, false, false),
  SLAVEOF("slaveof", 3, 0, 0, 0, false, false, false),
  SLOWLOG("slowlog", -2, 0, 0, 0, false, false, false),
  SMEMBERS("smembers", 2, 1, 1, 1, true, false, false),
  SMOVE("smove", 4, 1, 2, 1, false, false, false),
  SORT("sort", -2, 1, 1, 1, false, true, false),
  SPOP("spop", -2, 1, 1, 1, false, false, false),
  SRANDMEMBER("srandmember", -2, 1, 1, 1, true, false, false),
  SREM("srem", -3, 1, 1, 1, false, false, false),
  SSCAN("sscan", -3, 1, 1, 1, true, false, false),
  STRLEN("strlen", 2, 1, 1, 1, true, false, false),
  SUBSCRIBE("subscribe", -2, 0, 0, 0, false, false, false),
  SUBSTR("substr", 4, 1, 1, 1, true, false, false),
  SUNION("sunion", -2, 1, -1, 1, true, false, false),
  SUNIONSTORE("sunionstore", -3, 1, -1, 1, false, false, false),
  SWAPDB("swapdb", 3, 0, 0, 0, false, false, false),
  SYNC("sync", 1, 0, 0, 0, true, false, false),
  TIME("time", 1, 0, 0, 0, false, false, false),
  TOUCH("touch", -2, 1, 1, 1, true, false, false),
  TTL("ttl", 2, 1, 1, 1, true, false, false),
  TYPE("type", 2, 1, 1, 1, true, false, false),
  UNLINK("unlink", -2, 1, -1, 1, false, false, false),
  UNSUBSCRIBE("unsubscribe", -1, 0, 0, 0, false, false, false),
  UNWATCH("unwatch", 1, 0, 0, 0, false, false, false),
  WAIT("wait", 3, 0, 0, 0, false, false, false),
  WATCH("watch", -2, 1, -1, 1, false, false, false),
  XACK("xack", -4, 1, 1, 1, false, false, false),
  XADD("xadd", -5, 1, 1, 1, false, false, false),
  XCLAIM("xclaim", -6, 1, 1, 1, false, false, false),
  XDEL("xdel", -3, 1, 1, 1, false, false, false),
  XGROUP("xgroup", -2, 2, 2, 1, false, false, false),
  XINFO("xinfo", -2, 2, 2, 1, true, false, false),
  XLEN("xlen", 2, 1, 1, 1, true, false, false),
  XPENDING("xpending", -3, 1, 1, 1, true, false, false),
  XRANGE("xrange", -4, 1, 1, 1, true, false, false),
  XREAD("xread", -4, 1, 1, 1, true, true, false),
  XREADGROUP("xreadgroup", -7, 1, 1, 1, false, true, false),
  XREVRANGE("xrevrange", -4, 1, 1, 1, true, false, false),
  XSETID("xsetid", 3, 1, 1, 1, false, false, false),
  XTRIM("xtrim", -2, 1, 1, 1, false, false, false),
  ZADD("zadd", -4, 1, 1, 1, false, false, false),
  ZCARD("zcard", 2, 1, 1, 1, true, false, false),
  ZCOUNT("zcount", 4, 1, 1, 1, true, false, false),
  ZINCRBY("zincrby", 4, 1, 1, 1, false, false, false),
  ZINTERSTORE("zinterstore", -4, 0, 0, 0, false, true, false),
  ZLEXCOUNT("zlexcount", 4, 1, 1, 1, true, false, false),
  ZPOPMAX("zpopmax", -2, 1, 1, 1, false, false, false),
  ZPOPMIN("zpopmin", -2, 1, 1, 1, false, false, false),
  ZRANGE("zrange", -4, 1, 1, 1, true, false, false),
  ZRANGEBYLEX("zrangebylex", -4, 1, 1, 1, true, false, false),
  ZRANGEBYSCORE("zrangebyscore", -4, 1, 1, 1, true, false, false),
  ZRANK("zrank", 3, 1, 1, 1, true, false, false),
  ZREM("zrem", -3, 1, 1, 1, false, false, false),
  ZREMRANGEBYLEX("zremrangebylex", 4, 1, 1, 1, false, false, false),
  ZREMRANGEBYRANK("zremrangebyrank", 4, 1, 1, 1, false, false, false),
  ZREMRANGEBYSCORE("zremrangebyscore", 4, 1, 1, 1, false, false, false),
  ZREVRANGE("zrevrange", -4, 1, 1, 1, true, false, false),
  ZREVRANGEBYLEX("zrevrangebylex", -4, 1, 1, 1, true, false, false),
  ZREVRANGEBYSCORE("zrevrangebyscore", -4, 1, 1, 1, true, false, false),
  ZREVRANK("zrevrank", 3, 1, 1, 1, true, false, false),
  ZSCAN("zscan", -3, 1, 1, 1, true, false, false),
  ZSCORE("zscore", 3, 1, 1, 1, true, false, false),
  ZUNIONSTORE("zunionstore", -4, 0, 0, 0, false, true, false),
  SENTINEL("sentinel", -1, 0, 0, 0, false, false, false);

  private final byte[] bytes;
  private final int arity;

  private final boolean multiKey;
  private final int firstKey;
  private final int lastKey;
  private final int interval;
  private final boolean keyless;
  private final boolean readOnly;
  private final boolean movable;
  private final boolean clusterWide;

  Command(String command, int arity, int firstKey, int lastKey, int interval, boolean readOnly, boolean movable, boolean clusterWide) {
    bytes = ("$" + command.length() + "\r\n" + command + "\r\n").getBytes(StandardCharsets.ISO_8859_1);
    this.arity = arity;
    this.firstKey = firstKey;
    this.lastKey = lastKey;
    this.interval = interval;
    this.multiKey = lastKey < 0;
    this.keyless = interval == 0 && !movable;
    this.readOnly = readOnly;
    this.movable = movable;
    this.clusterWide = clusterWide;
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

  public boolean isClusterWide() {
    return clusterWide;
  }}
