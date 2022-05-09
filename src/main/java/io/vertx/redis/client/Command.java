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

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.redis.client.impl.CommandImpl;

/**
 * <b>Auto generated</b> API Commands to interact with REDIS.
 * @version redis_version:7.0.0
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@VertxGen
public interface Command {

  Command ACL = Command.create("acl", -2, 0, 0, 0, false, false, false, false);
  Command APPEND = Command.create("append", 3, 1, 1, 1, true, false, false, false);
  Command ASKING = Command.create("asking", 1, 0, 0, 0, false, false, false, false);
  Command AUTH = Command.create("auth", -2, 0, 0, 0, false, false, false, false);
  Command BGREWRITEAOF = Command.create("bgrewriteaof", 1, 0, 0, 0, false, false, false, false);
  Command BGSAVE = Command.create("bgsave", -1, 0, 0, 0, false, false, false, false);
  Command BITCOUNT = Command.create("bitcount", -2, 1, 1, 1, false, true, false, false);
  Command BITFIELD = Command.create("bitfield", -2, 1, 1, 1, true, false, true, false);
  Command BITFIELD_RO = Command.create("bitfield_ro", -2, 1, 1, 1, false, true, false, false);
  Command BITOP = Command.create("bitop", -4, 2, -1, 1, true, false, false, false);
  Command BITPOS = Command.create("bitpos", -3, 1, 1, 1, false, true, false, false);
  Command BLMOVE = Command.create("blmove", 6, 1, 2, 1, true, false, false, false);
  Command BLMPOP = Command.create("blmpop", -5, 0, 0, 0, true, false, true, false);
  Command BLPOP = Command.create("blpop", -3, 1, -2, 1, true, false, false, false);
  Command BRPOP = Command.create("brpop", -3, 1, -2, 1, true, false, false, false);
  Command BRPOPLPUSH = Command.create("brpoplpush", 4, 1, 2, 1, true, false, false, false);
  Command BZMPOP = Command.create("bzmpop", -5, 0, 0, 0, true, false, true, false);
  Command BZPOPMAX = Command.create("bzpopmax", -3, 1, -2, 1, true, false, false, false);
  Command BZPOPMIN = Command.create("bzpopmin", -3, 1, -2, 1, true, false, false, false);
  Command CLIENT = Command.create("client", -2, 0, 0, 0, false, false, false, false);
  Command CLUSTER = Command.create("cluster", -2, 0, 0, 0, false, false, false, false);
  Command COMMAND = Command.create("command", -1, 0, 0, 0, false, false, false, false);
  Command CONFIG = Command.create("config", -2, 0, 0, 0, false, false, false, false);
  Command COPY = Command.create("copy", -3, 1, 2, 1, true, false, false, false);
  Command DBSIZE = Command.create("dbsize", 1, 0, 0, 0, false, true, false, false);
  Command DEBUG = Command.create("debug", -2, 0, 0, 0, false, false, false, false);
  Command DECR = Command.create("decr", 2, 1, 1, 1, true, false, false, false);
  Command DECRBY = Command.create("decrby", 3, 1, 1, 1, true, false, false, false);
  Command DEL = Command.create("del", -2, 1, -1, 1, true, false, false, false);
  Command DISCARD = Command.create("discard", 1, 0, 0, 0, false, false, false, false);
  Command DUMP = Command.create("dump", 2, 1, 1, 1, false, true, false, false);
  Command ECHO = Command.create("echo", 2, 0, 0, 0, false, false, false, false);
  Command EVAL = Command.create("eval", -3, 0, 0, 0, false, false, true, false);
  Command EVAL_RO = Command.create("eval_ro", -3, 0, 0, 0, false, false, true, false);
  Command EVALSHA = Command.create("evalsha", -3, 0, 0, 0, false, false, true, false);
  Command EVALSHA_RO = Command.create("evalsha_ro", -3, 0, 0, 0, false, false, true, false);
  Command EXEC = Command.create("exec", 1, 0, 0, 0, false, false, false, false);
  Command EXISTS = Command.create("exists", -2, 1, -1, 1, false, true, false, false);
  Command EXPIRE = Command.create("expire", -3, 1, 1, 1, true, false, false, false);
  Command EXPIREAT = Command.create("expireat", -3, 1, 1, 1, true, false, false, false);
  Command EXPIRETIME = Command.create("expiretime", 2, 1, 1, 1, false, true, false, false);
  Command FAILOVER = Command.create("failover", -1, 0, 0, 0, false, false, false, false);
  Command FCALL = Command.create("fcall", -3, 0, 0, 0, false, false, true, false);
  Command FCALL_RO = Command.create("fcall_ro", -3, 0, 0, 0, false, false, true, false);
  Command FLUSHALL = Command.create("flushall", -1, 0, 0, 0, true, false, false, false);
  Command FLUSHDB = Command.create("flushdb", -1, 0, 0, 0, true, false, false, false);
  Command FUNCTION = Command.create("function", -2, 0, 0, 0, false, false, false, false);
  Command GEOADD = Command.create("geoadd", -5, 1, 1, 1, true, false, false, false);
  Command GEODIST = Command.create("geodist", -4, 1, 1, 1, false, true, false, false);
  Command GEOHASH = Command.create("geohash", -2, 1, 1, 1, false, true, false, false);
  Command GEOPOS = Command.create("geopos", -2, 1, 1, 1, false, true, false, false);
  Command GEORADIUS = Command.create("georadius", -6, 1, 1, 1, true, false, true, false);
  Command GEORADIUS_RO = Command.create("georadius_ro", -6, 1, 1, 1, false, true, false, false);
  Command GEORADIUSBYMEMBER = Command.create("georadiusbymember", -5, 1, 1, 1, true, false, true, false);
  Command GEORADIUSBYMEMBER_RO = Command.create("georadiusbymember_ro", -5, 1, 1, 1, false, true, false, false);
  Command GEOSEARCH = Command.create("geosearch", -7, 1, 1, 1, false, true, false, false);
  Command GEOSEARCHSTORE = Command.create("geosearchstore", -8, 1, 2, 1, true, false, false, false);
  Command GET = Command.create("get", 2, 1, 1, 1, false, true, false, false);
  Command GETBIT = Command.create("getbit", 3, 1, 1, 1, false, true, false, false);
  Command GETDEL = Command.create("getdel", 2, 1, 1, 1, true, false, false, false);
  Command GETEX = Command.create("getex", -2, 1, 1, 1, true, false, false, false);
  Command GETRANGE = Command.create("getrange", 4, 1, 1, 1, false, true, false, false);
  Command GETSET = Command.create("getset", 3, 1, 1, 1, true, false, false, false);
  Command HDEL = Command.create("hdel", -3, 1, 1, 1, true, false, false, false);
  Command HELLO = Command.create("hello", -1, 0, 0, 0, false, false, false, false);
  Command HEXISTS = Command.create("hexists", 3, 1, 1, 1, false, true, false, false);
  Command HGET = Command.create("hget", 3, 1, 1, 1, false, true, false, false);
  Command HGETALL = Command.create("hgetall", 2, 1, 1, 1, false, true, false, false);
  Command HINCRBY = Command.create("hincrby", 4, 1, 1, 1, true, false, false, false);
  Command HINCRBYFLOAT = Command.create("hincrbyfloat", 4, 1, 1, 1, true, false, false, false);
  Command HKEYS = Command.create("hkeys", 2, 1, 1, 1, false, true, false, false);
  Command HLEN = Command.create("hlen", 2, 1, 1, 1, false, true, false, false);
  Command HMGET = Command.create("hmget", -3, 1, 1, 1, false, true, false, false);
  Command HMSET = Command.create("hmset", -4, 1, 1, 1, true, false, false, false);
  Command HRANDFIELD = Command.create("hrandfield", -2, 1, 1, 1, false, true, false, false);
  Command HSCAN = Command.create("hscan", -3, 1, 1, 1, false, true, false, false);
  Command HSET = Command.create("hset", -4, 1, 1, 1, true, false, false, false);
  Command HSETNX = Command.create("hsetnx", 4, 1, 1, 1, true, false, false, false);
  Command HSTRLEN = Command.create("hstrlen", 3, 1, 1, 1, false, true, false, false);
  Command HVALS = Command.create("hvals", 2, 1, 1, 1, false, true, false, false);
  Command INCR = Command.create("incr", 2, 1, 1, 1, true, false, false, false);
  Command INCRBY = Command.create("incrby", 3, 1, 1, 1, true, false, false, false);
  Command INCRBYFLOAT = Command.create("incrbyfloat", 3, 1, 1, 1, true, false, false, false);
  Command INFO = Command.create("info", -1, 0, 0, 0, false, false, false, false);
  Command KEYS = Command.create("keys", 2, 0, 0, 0, false, true, false, false);
  Command LASTSAVE = Command.create("lastsave", 1, 0, 0, 0, false, false, false, false);
  Command LATENCY = Command.create("latency", -2, 0, 0, 0, false, false, false, false);
  Command LCS = Command.create("lcs", -3, 1, 2, 1, false, true, false, false);
  Command LINDEX = Command.create("lindex", 3, 1, 1, 1, false, true, false, false);
  Command LINSERT = Command.create("linsert", 5, 1, 1, 1, true, false, false, false);
  Command LLEN = Command.create("llen", 2, 1, 1, 1, false, true, false, false);
  Command LMOVE = Command.create("lmove", 5, 1, 2, 1, true, false, false, false);
  Command LMPOP = Command.create("lmpop", -4, 0, 0, 0, true, false, true, false);
  Command LOLWUT = Command.create("lolwut", -1, 0, 0, 0, false, true, false, false);
  Command LPOP = Command.create("lpop", -2, 1, 1, 1, true, false, false, false);
  Command LPOS = Command.create("lpos", -3, 1, 1, 1, false, true, false, false);
  Command LPUSH = Command.create("lpush", -3, 1, 1, 1, true, false, false, false);
  Command LPUSHX = Command.create("lpushx", -3, 1, 1, 1, true, false, false, false);
  Command LRANGE = Command.create("lrange", 4, 1, 1, 1, false, true, false, false);
  Command LREM = Command.create("lrem", 4, 1, 1, 1, true, false, false, false);
  Command LSET = Command.create("lset", 4, 1, 1, 1, true, false, false, false);
  Command LTRIM = Command.create("ltrim", 4, 1, 1, 1, true, false, false, false);
  Command MEMORY = Command.create("memory", -2, 0, 0, 0, false, false, false, false);
  Command MGET = Command.create("mget", -2, 1, -1, 1, false, true, false, false);
  Command MIGRATE = Command.create("migrate", -6, 3, 3, 1, true, false, true, false);
  Command MODULE = Command.create("module", -2, 0, 0, 0, false, false, false, false);
  Command MONITOR = Command.create("monitor", 1, 0, 0, 0, false, false, false, false);
  Command MOVE = Command.create("move", 3, 1, 1, 1, true, false, false, false);
  Command MSET = Command.create("mset", -3, 1, -1, 2, true, false, false, false);
  Command MSETNX = Command.create("msetnx", -3, 1, -1, 2, true, false, false, false);
  Command MULTI = Command.create("multi", 1, 0, 0, 0, false, false, false, false);
  Command OBJECT = Command.create("object", -2, 0, 0, 0, false, false, false, false);
  Command PERSIST = Command.create("persist", 2, 1, 1, 1, true, false, false, false);
  Command PEXPIRE = Command.create("pexpire", -3, 1, 1, 1, true, false, false, false);
  Command PEXPIREAT = Command.create("pexpireat", -3, 1, 1, 1, true, false, false, false);
  Command PEXPIRETIME = Command.create("pexpiretime", 2, 1, 1, 1, false, true, false, false);
  Command PFADD = Command.create("pfadd", -2, 1, 1, 1, true, false, false, false);
  Command PFCOUNT = Command.create("pfcount", -2, 1, -1, 1, false, true, false, false);
  Command PFDEBUG = Command.create("pfdebug", -3, 2, 2, 1, true, false, false, false);
  Command PFMERGE = Command.create("pfmerge", -2, 1, -1, 1, true, false, false, false);
  Command PFSELFTEST = Command.create("pfselftest", 1, 0, 0, 0, false, false, false, false);
  Command PING = Command.create("ping", -1, 0, 0, 0, false, false, false, false);
  Command PSETEX = Command.create("psetex", 4, 1, 1, 1, true, false, false, false);
  Command PSUBSCRIBE = Command.create("psubscribe", -2, 0, 0, 0, false, false, false, true);
  Command PSYNC = Command.create("psync", -3, 0, 0, 0, false, false, false, false);
  Command PTTL = Command.create("pttl", 2, 1, 1, 1, false, true, false, false);
  Command PUBLISH = Command.create("publish", 3, 0, 0, 0, false, false, false, false);
  Command PUBSUB = Command.create("pubsub", -2, 0, 0, 0, false, false, false, false);
  Command PUNSUBSCRIBE = Command.create("punsubscribe", -1, 0, 0, 0, false, false, false, true);
  Command QUIT = Command.create("quit", -1, 0, 0, 0, false, false, false, false);
  Command RANDOMKEY = Command.create("randomkey", 1, 0, 0, 0, false, true, false, false);
  Command READONLY = Command.create("readonly", 1, 0, 0, 0, false, false, false, false);
  Command READWRITE = Command.create("readwrite", 1, 0, 0, 0, false, false, false, false);
  Command RENAME = Command.create("rename", 3, 1, 2, 1, true, false, false, false);
  Command RENAMENX = Command.create("renamenx", 3, 1, 2, 1, true, false, false, false);
  Command REPLCONF = Command.create("replconf", -1, 0, 0, 0, false, false, false, false);
  Command REPLICAOF = Command.create("replicaof", 3, 0, 0, 0, false, false, false, false);
  Command RESET = Command.create("reset", 1, 0, 0, 0, false, false, false, false);
  Command RESTORE = Command.create("restore", -4, 1, 1, 1, true, false, false, false);
  Command RESTORE_ASKING = Command.create("restore-asking", -4, 1, 1, 1, true, false, false, false);
  Command ROLE = Command.create("role", 1, 0, 0, 0, false, false, false, false);
  Command RPOP = Command.create("rpop", -2, 1, 1, 1, true, false, false, false);
  Command RPOPLPUSH = Command.create("rpoplpush", 3, 1, 2, 1, true, false, false, false);
  Command RPUSH = Command.create("rpush", -3, 1, 1, 1, true, false, false, false);
  Command RPUSHX = Command.create("rpushx", -3, 1, 1, 1, true, false, false, false);
  Command SADD = Command.create("sadd", -3, 1, 1, 1, true, false, false, false);
  Command SAVE = Command.create("save", 1, 0, 0, 0, false, false, false, false);
  Command SCAN = Command.create("scan", -2, 0, 0, 0, false, true, false, false);
  Command SCARD = Command.create("scard", 2, 1, 1, 1, false, true, false, false);
  Command SCRIPT = Command.create("script", -2, 0, 0, 0, false, false, false, false);
  Command SDIFF = Command.create("sdiff", -2, 1, -1, 1, false, true, false, false);
  Command SDIFFSTORE = Command.create("sdiffstore", -3, 1, -1, 1, true, false, false, false);
  Command SELECT = Command.create("select", 2, 0, 0, 0, false, false, false, false);
  Command SET = Command.create("set", -3, 1, 1, 1, true, false, true, false);
  Command SETBIT = Command.create("setbit", 4, 1, 1, 1, true, false, false, false);
  Command SETEX = Command.create("setex", 4, 1, 1, 1, true, false, false, false);
  Command SETNX = Command.create("setnx", 3, 1, 1, 1, true, false, false, false);
  Command SETRANGE = Command.create("setrange", 4, 1, 1, 1, true, false, false, false);
  Command SHUTDOWN = Command.create("shutdown", -1, 0, 0, 0, false, false, false, false);
  Command SINTER = Command.create("sinter", -2, 1, -1, 1, false, true, false, false);
  Command SINTERCARD = Command.create("sintercard", -3, 0, 0, 0, false, true, true, false);
  Command SINTERSTORE = Command.create("sinterstore", -3, 1, -1, 1, true, false, false, false);
  Command SISMEMBER = Command.create("sismember", 3, 1, 1, 1, false, true, false, false);
  Command SLAVEOF = Command.create("slaveof", 3, 0, 0, 0, false, false, false, false);
  Command SLOWLOG = Command.create("slowlog", -2, 0, 0, 0, false, false, false, false);
  Command SMEMBERS = Command.create("smembers", 2, 1, 1, 1, false, true, false, false);
  Command SMISMEMBER = Command.create("smismember", -3, 1, 1, 1, false, true, false, false);
  Command SMOVE = Command.create("smove", 4, 1, 2, 1, true, false, false, false);
  Command SORT = Command.create("sort", -2, 1, 1, 1, true, false, true, false);
  Command SORT_RO = Command.create("sort_ro", -2, 1, 1, 1, false, true, true, false);
  Command SPOP = Command.create("spop", -2, 1, 1, 1, true, false, false, false);
  Command SPUBLISH = Command.create("spublish", 3, 1, 1, 1, false, false, false, true);
  Command SRANDMEMBER = Command.create("srandmember", -2, 1, 1, 1, false, true, false, false);
  Command SREM = Command.create("srem", -3, 1, 1, 1, true, false, false, false);
  Command SSCAN = Command.create("sscan", -3, 1, 1, 1, false, true, false, false);
  Command SSUBSCRIBE = Command.create("ssubscribe", -2, 1, -1, 1, false, false, false, true);
  Command STRLEN = Command.create("strlen", 2, 1, 1, 1, false, true, false, false);
  Command SUBSCRIBE = Command.create("subscribe", -2, 0, 0, 0, false, false, false, true);
  Command SUBSTR = Command.create("substr", 4, 1, 1, 1, false, true, false, false);
  Command SUNION = Command.create("sunion", -2, 1, -1, 1, false, true, false, false);
  Command SUNIONSTORE = Command.create("sunionstore", -3, 1, -1, 1, true, false, false, false);
  Command SUNSUBSCRIBE = Command.create("sunsubscribe", -1, 1, -1, 1, false, false, false, true);
  Command SWAPDB = Command.create("swapdb", 3, 0, 0, 0, true, false, false, false);
  Command SYNC = Command.create("sync", 1, 0, 0, 0, false, false, false, false);
  Command TIME = Command.create("time", 1, 0, 0, 0, false, false, false, false);
  Command TOUCH = Command.create("touch", -2, 1, -1, 1, false, true, false, false);
  Command TTL = Command.create("ttl", 2, 1, 1, 1, false, true, false, false);
  Command TYPE = Command.create("type", 2, 1, 1, 1, false, true, false, false);
  Command UNLINK = Command.create("unlink", -2, 1, -1, 1, true, false, false, false);
  Command UNSUBSCRIBE = Command.create("unsubscribe", -1, 0, 0, 0, false, false, false, true);
  Command UNWATCH = Command.create("unwatch", 1, 0, 0, 0, false, false, false, false);
  Command WAIT = Command.create("wait", 3, 0, 0, 0, false, false, false, false);
  Command WATCH = Command.create("watch", -2, 1, -1, 1, false, false, false, false);
  Command XACK = Command.create("xack", -4, 1, 1, 1, true, false, false, false);
  Command XADD = Command.create("xadd", -5, 1, 1, 1, true, false, false, false);
  Command XAUTOCLAIM = Command.create("xautoclaim", -6, 1, 1, 1, true, false, false, false);
  Command XCLAIM = Command.create("xclaim", -6, 1, 1, 1, true, false, false, false);
  Command XDEL = Command.create("xdel", -3, 1, 1, 1, true, false, false, false);
  Command XGROUP = Command.create("xgroup", -2, 0, 0, 0, false, false, false, false);
  Command XINFO = Command.create("xinfo", -2, 0, 0, 0, false, false, false, false);
  Command XLEN = Command.create("xlen", 2, 1, 1, 1, false, true, false, false);
  Command XPENDING = Command.create("xpending", -3, 1, 1, 1, false, true, false, false);
  Command XRANGE = Command.create("xrange", -4, 1, 1, 1, false, true, false, false);
  Command XREAD = Command.create("xread", -4, 0, 0, 0, false, true, true, false);
  Command XREADGROUP = Command.create("xreadgroup", -7, 0, 0, 0, true, false, true, false);
  Command XREVRANGE = Command.create("xrevrange", -4, 1, 1, 1, false, true, false, false);
  Command XSETID = Command.create("xsetid", -3, 1, 1, 1, true, false, false, false);
  Command XTRIM = Command.create("xtrim", -4, 1, 1, 1, true, false, false, false);
  Command ZADD = Command.create("zadd", -4, 1, 1, 1, true, false, false, false);
  Command ZCARD = Command.create("zcard", 2, 1, 1, 1, false, true, false, false);
  Command ZCOUNT = Command.create("zcount", 4, 1, 1, 1, false, true, false, false);
  Command ZDIFF = Command.create("zdiff", -3, 0, 0, 0, false, true, true, false);
  Command ZDIFFSTORE = Command.create("zdiffstore", -4, 1, 1, 1, true, false, true, false);
  Command ZINCRBY = Command.create("zincrby", 4, 1, 1, 1, true, false, false, false);
  Command ZINTER = Command.create("zinter", -3, 0, 0, 0, false, true, true, false);
  Command ZINTERCARD = Command.create("zintercard", -3, 0, 0, 0, false, true, true, false);
  Command ZINTERSTORE = Command.create("zinterstore", -4, 1, 1, 1, true, false, true, false);
  Command ZLEXCOUNT = Command.create("zlexcount", 4, 1, 1, 1, false, true, false, false);
  Command ZMPOP = Command.create("zmpop", -4, 0, 0, 0, true, false, true, false);
  Command ZMSCORE = Command.create("zmscore", -3, 1, 1, 1, false, true, false, false);
  Command ZPOPMAX = Command.create("zpopmax", -2, 1, 1, 1, true, false, false, false);
  Command ZPOPMIN = Command.create("zpopmin", -2, 1, 1, 1, true, false, false, false);
  Command ZRANDMEMBER = Command.create("zrandmember", -2, 1, 1, 1, false, true, false, false);
  Command ZRANGE = Command.create("zrange", -4, 1, 1, 1, false, true, false, false);
  Command ZRANGEBYLEX = Command.create("zrangebylex", -4, 1, 1, 1, false, true, false, false);
  Command ZRANGEBYSCORE = Command.create("zrangebyscore", -4, 1, 1, 1, false, true, false, false);
  Command ZRANGESTORE = Command.create("zrangestore", -5, 1, 2, 1, true, false, false, false);
  Command ZRANK = Command.create("zrank", 3, 1, 1, 1, false, true, false, false);
  Command ZREM = Command.create("zrem", -3, 1, 1, 1, true, false, false, false);
  Command ZREMRANGEBYLEX = Command.create("zremrangebylex", 4, 1, 1, 1, true, false, false, false);
  Command ZREMRANGEBYRANK = Command.create("zremrangebyrank", 4, 1, 1, 1, true, false, false, false);
  Command ZREMRANGEBYSCORE = Command.create("zremrangebyscore", 4, 1, 1, 1, true, false, false, false);
  Command ZREVRANGE = Command.create("zrevrange", -4, 1, 1, 1, false, true, false, false);
  Command ZREVRANGEBYLEX = Command.create("zrevrangebylex", -4, 1, 1, 1, false, true, false, false);
  Command ZREVRANGEBYSCORE = Command.create("zrevrangebyscore", -4, 1, 1, 1, false, true, false, false);
  Command ZREVRANK = Command.create("zrevrank", 3, 1, 1, 1, false, true, false, false);
  Command ZSCAN = Command.create("zscan", -3, 1, 1, 1, false, true, false, false);
  Command ZSCORE = Command.create("zscore", 3, 1, 1, 1, false, true, false, false);
  Command ZUNION = Command.create("zunion", -3, 0, 0, 0, false, true, true, false);
  Command ZUNIONSTORE = Command.create("zunionstore", -4, 1, 1, 1, true, false, true, false);
  Command SENTINEL = Command.create("sentinel");

  /**
   * Creates a Redis Command from METADATA. The metadata comes from the REDIS command "COMMAND"
   * https://redis.io/commands/command
   *
   * Each top-level result contains six nested results. Each nested result is:
   *
   *     command name
   *     command arity specification
   *     nested Array reply of command flags
   *     position of first key in argument list
   *     position of last key in argument list
   *     step count for locating repeating keys
   *
   * @param command command name
   * @param arity arity
   * @param firstKey position of the first key
   * @param lastKey position of the last key
   * @param interval step count for locating repeating keys
   * @param write write flag extracted from the nested Array reply of command flags
   * @param readOnly readOnly flag extracted from the nested Array reply of command flags
   * @param movable movable flag extracted from the nested Array reply of command flags
   * @param pubsub flag extracted from the nested Array reply of command flags
   * @return a command instance
   */
  static Command create(String command, int arity, int firstKey, int lastKey, int interval, boolean write, boolean readOnly, boolean movable, boolean pubsub) {
    return new CommandImpl(command, arity, firstKey, lastKey, interval, write, readOnly, movable, pubsub);
  }

  /**
   * Generic command generator for extensions.
   *
   * @param command
   * @return the cachable immutable command instance
   */
  static Command create(String command) {
    return new CommandImpl(command, -1, 0, 0, 0, false, false, false, false);
  }

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  byte[] getBytes();

  int getArity();

  boolean isMultiKey();

  int getFirstKey();

  int getLastKey();

  int getInterval();

  boolean isKeyless();

  boolean isWrite();

  boolean isReadOnly();

  boolean isMovable();

  /**
   * @deprecated use {@link #isPubSub()}
   */
  @Deprecated
  default boolean isVoid() {
    return isPubSub();
  }

  boolean isPubSub();
}
