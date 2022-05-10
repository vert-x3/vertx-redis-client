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
import io.vertx.redis.client.impl.keys.*;

/**
 * <b>Auto generated</b> API Commands to interact with REDIS.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 * @version redis_version:7.0.0
 */
@VertxGen
public interface Command {

  Command ACL = Command.create("acl", -2, null, null, false, null, false);
  Command APPEND = Command.create("append", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ASKING = Command.create("asking", 1, null, null, false, null, false);
  Command AUTH = Command.create("auth", -2, null, null, false, null, false);
  Command BGREWRITEAOF = Command.create("bgrewriteaof", 1, null, null, false, null, false);
  Command BGSAVE = Command.create("bgsave", -1, null, null, false, null, false);
  Command BITCOUNT = Command.create("bitcount", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command BITFIELD = Command.create("bitfield", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command BITFIELD_RO = Command.create("bitfield_ro", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command BITOP = Command.create("bitop", -4, null, null, true, false, false);
  Command BITPOS = Command.create("bitpos", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command BLMOVE = Command.create("blmove", 6, null, null, true, false, false);
  Command BLMPOP = Command.create("blmpop", -5, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command BLPOP = Command.create("blpop", -3, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0), false, false, false);
  Command BRPOP = Command.create("brpop", -3, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0), false, false, false);
  Command BRPOPLPUSH = Command.create("brpoplpush", 4, null, null, true, false, false);
  Command BZMPOP = Command.create("bzmpop", -5, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command BZPOPMAX = Command.create("bzpopmax", -3, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0), false, false, false);
  Command BZPOPMIN = Command.create("bzpopmin", -3, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0), false, false, false);
  Command CLIENT = Command.create("client", -2, null, null, false, null, false);
  Command CLUSTER = Command.create("cluster", -2, null, null, false, null, false);
  Command COMMAND = Command.create("command", -1, null, null, false, null, false);
  Command CONFIG = Command.create("config", -2, null, null, false, null, false);
  Command COPY = Command.create("copy", -3, null, null, true, false, false);
  Command DBSIZE = Command.create("dbsize", 1, null, null, false, true, false);
  Command DEBUG = Command.create("debug", -2, null, null, false, null, false);
  Command DECR = Command.create("decr", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command DECRBY = Command.create("decrby", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command DEL = Command.create("del", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, false, false);
  Command DISCARD = Command.create("discard", 1, null, null, false, null, false);
  Command DUMP = Command.create("dump", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ECHO = Command.create("echo", 2, null, null, false, null, false);
  Command EVAL = Command.create("eval", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command EVALSHA = Command.create("evalsha", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command EVALSHA_RO = Command.create("evalsha_ro", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command EVAL_RO = Command.create("eval_ro", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command EXEC = Command.create("exec", 1, null, null, false, null, false);
  Command EXISTS = Command.create("exists", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command EXPIRE = Command.create("expire", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command EXPIREAT = Command.create("expireat", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command EXPIRETIME = Command.create("expiretime", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command FAILOVER = Command.create("failover", -1, null, null, false, null, false);
  Command FCALL = Command.create("fcall", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command FCALL_RO = Command.create("fcall_ro", -3, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command FLUSHALL = Command.create("flushall", -1, null, null, false, false, false);
  Command FLUSHDB = Command.create("flushdb", -1, null, null, false, false, false);
  Command FUNCTION = Command.create("function", -2, null, null, false, null, false);
  Command GEOADD = Command.create("geoadd", -5, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command GEODIST = Command.create("geodist", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEOHASH = Command.create("geohash", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEOPOS = Command.create("geopos", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEORADIUS = Command.create("georadius", -6, null, null, true, false, false);
  Command GEORADIUSBYMEMBER = Command.create("georadiusbymember", -5, null, null, true, false, false);
  Command GEORADIUSBYMEMBER_RO = Command.create("georadiusbymember_ro", -5, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEORADIUS_RO = Command.create("georadius_ro", -6, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEOSEARCH = Command.create("geosearch", -7, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GEOSEARCHSTORE = Command.create("geosearchstore", -8, null, null, true, false, false);
  Command GET = Command.create("get", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GETBIT = Command.create("getbit", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GETDEL = Command.create("getdel", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command GETEX = Command.create("getex", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command GETRANGE = Command.create("getrange", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command GETSET = Command.create("getset", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HDEL = Command.create("hdel", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HELLO = Command.create("hello", -1, null, null, false, null, false);
  Command HEXISTS = Command.create("hexists", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HGET = Command.create("hget", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HGETALL = Command.create("hgetall", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HINCRBY = Command.create("hincrby", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HINCRBYFLOAT = Command.create("hincrbyfloat", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HKEYS = Command.create("hkeys", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HLEN = Command.create("hlen", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HMGET = Command.create("hmget", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HMSET = Command.create("hmset", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HRANDFIELD = Command.create("hrandfield", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HSCAN = Command.create("hscan", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HSET = Command.create("hset", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HSETNX = Command.create("hsetnx", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command HSTRLEN = Command.create("hstrlen", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command HVALS = Command.create("hvals", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command INCR = Command.create("incr", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command INCRBY = Command.create("incrby", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command INCRBYFLOAT = Command.create("incrbyfloat", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command INFO = Command.create("info", -1, null, null, false, null, false);
  Command KEYS = Command.create("keys", 2, null, null, false, true, false);
  Command LASTSAVE = Command.create("lastsave", 1, null, null, false, null, false);
  Command LATENCY = Command.create("latency", -2, null, null, false, null, false);
  Command LCS = Command.create("lcs", -3, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0), false, true, false);
  Command LINDEX = Command.create("lindex", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command LINSERT = Command.create("linsert", 5, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LLEN = Command.create("llen", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command LMOVE = Command.create("lmove", 5, null, null, true, false, false);
  Command LMPOP = Command.create("lmpop", -4, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command LOLWUT = Command.create("lolwut", -1, null, null, false, true, false);
  Command LPOP = Command.create("lpop", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LPOS = Command.create("lpos", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command LPUSH = Command.create("lpush", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LPUSHX = Command.create("lpushx", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LRANGE = Command.create("lrange", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command LREM = Command.create("lrem", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LSET = Command.create("lset", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command LTRIM = Command.create("ltrim", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command MEMORY = Command.create("memory", -2, null, null, false, null, false);
  Command MGET = Command.create("mget", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command MIGRATE = Command.create("migrate", -6, null, null, true, false, false);
  Command MODULE = Command.create("module", -2, null, null, false, null, false);
  Command MONITOR = Command.create("monitor", 1, null, null, false, null, false);
  Command MOVE = Command.create("move", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command MSET = Command.create("mset", -3, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0), false, false, false);
  Command MSETNX = Command.create("msetnx", -3, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0), false, false, false);
  Command MULTI = Command.create("multi", 1, null, null, false, null, false);
  Command OBJECT = Command.create("object", -2, null, null, false, null, false);
  Command PERSIST = Command.create("persist", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command PEXPIRE = Command.create("pexpire", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command PEXPIREAT = Command.create("pexpireat", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command PEXPIRETIME = Command.create("pexpiretime", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command PFADD = Command.create("pfadd", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command PFCOUNT = Command.create("pfcount", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, false, false);
  Command PFDEBUG = Command.create("pfdebug", -3, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0), false, false, false);
  Command PFMERGE = Command.create("pfmerge", -2, null, null, true, false, false);
  Command PFSELFTEST = Command.create("pfselftest", 1, null, null, false, null, false);
  Command PING = Command.create("ping", -1, null, null, false, null, false);
  Command PSETEX = Command.create("psetex", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command PSUBSCRIBE = Command.create("psubscribe", -2, null, null, false, null, true);
  Command PSYNC = Command.create("psync", -3, null, null, false, null, false);
  Command PTTL = Command.create("pttl", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command PUBLISH = Command.create("publish", 3, null, null, false, null, false);
  Command PUBSUB = Command.create("pubsub", -2, null, null, false, null, false);
  Command PUNSUBSCRIBE = Command.create("punsubscribe", -1, null, null, false, null, true);
  Command QUIT = Command.create("quit", -1, null, null, false, null, false);
  Command RANDOMKEY = Command.create("randomkey", 1, null, null, false, true, false);
  Command READONLY = Command.create("readonly", 1, null, null, false, null, false);
  Command READWRITE = Command.create("readwrite", 1, null, null, false, null, false);
  Command RENAME = Command.create("rename", 3, null, null, true, false, false);
  Command RENAMENX = Command.create("renamenx", 3, null, null, true, false, false);
  Command REPLCONF = Command.create("replconf", -1, null, null, false, null, false);
  Command REPLICAOF = Command.create("replicaof", 3, null, null, false, null, false);
  Command RESET = Command.create("reset", 1, null, null, false, null, false);
  Command RESTORE = Command.create("restore", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command RESTORE_ASKING = Command.create("restore-asking", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ROLE = Command.create("role", 1, null, null, false, null, false);
  Command RPOP = Command.create("rpop", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command RPOPLPUSH = Command.create("rpoplpush", 3, null, null, true, false, false);
  Command RPUSH = Command.create("rpush", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command RPUSHX = Command.create("rpushx", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SADD = Command.create("sadd", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SAVE = Command.create("save", 1, null, null, false, null, false);
  Command SCAN = Command.create("scan", -2, null, null, false, true, false);
  Command SCARD = Command.create("scard", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SCRIPT = Command.create("script", -2, null, null, false, null, false);
  Command SDIFF = Command.create("sdiff", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command SDIFFSTORE = Command.create("sdiffstore", -3, null, null, true, false, false);
  Command SELECT = Command.create("select", 2, null, null, false, null, false);
  Command SET = Command.create("set", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SETBIT = Command.create("setbit", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SETEX = Command.create("setex", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SETNX = Command.create("setnx", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SETRANGE = Command.create("setrange", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SHUTDOWN = Command.create("shutdown", -1, null, null, false, null, false);
  Command SINTER = Command.create("sinter", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command SINTERCARD = Command.create("sintercard", -3, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command SINTERSTORE = Command.create("sinterstore", -3, null, null, true, false, false);
  Command SISMEMBER = Command.create("sismember", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SLAVEOF = Command.create("slaveof", 3, null, null, false, null, false);
  Command SLOWLOG = Command.create("slowlog", -2, null, null, false, null, false);
  Command SMEMBERS = Command.create("smembers", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SMISMEMBER = Command.create("smismember", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SMOVE = Command.create("smove", 4, null, null, true, false, false);
  Command SORT = Command.create("sort", -2, null, null, true, false, false);
  Command SORT_RO = Command.create("sort_ro", -2, null, null, true, true, false);
  Command SPOP = Command.create("spop", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SPUBLISH = Command.create("spublish", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, null, true);
  Command SRANDMEMBER = Command.create("srandmember", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SREM = Command.create("srem", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command SSCAN = Command.create("sscan", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SSUBSCRIBE = Command.create("ssubscribe", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, null, true);
  Command STRLEN = Command.create("strlen", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SUBSCRIBE = Command.create("subscribe", -2, null, null, false, null, true);
  Command SUBSTR = Command.create("substr", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command SUNION = Command.create("sunion", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command SUNIONSTORE = Command.create("sunionstore", -3, null, null, true, false, false);
  Command SUNSUBSCRIBE = Command.create("sunsubscribe", -1, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, null, true);
  Command SWAPDB = Command.create("swapdb", 3, null, null, false, false, false);
  Command SYNC = Command.create("sync", 1, null, null, false, null, false);
  Command TIME = Command.create("time", 1, null, null, false, null, false);
  Command TOUCH = Command.create("touch", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, true, false);
  Command TTL = Command.create("ttl", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command TYPE = Command.create("type", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command UNLINK = Command.create("unlink", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, false, false);
  Command UNSUBSCRIBE = Command.create("unsubscribe", -1, null, null, false, null, true);
  Command UNWATCH = Command.create("unwatch", 1, null, null, false, null, false);
  Command WAIT = Command.create("wait", 3, null, null, false, null, false);
  Command WATCH = Command.create("watch", -2, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0), false, null, false);
  Command XACK = Command.create("xack", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XADD = Command.create("xadd", -5, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XAUTOCLAIM = Command.create("xautoclaim", -6, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XCLAIM = Command.create("xclaim", -6, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XDEL = Command.create("xdel", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XGROUP = Command.create("xgroup", -2, null, null, false, null, false);
  Command XINFO = Command.create("xinfo", -2, null, null, false, null, false);
  Command XLEN = Command.create("xlen", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command XPENDING = Command.create("xpending", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command XRANGE = Command.create("xrange", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command XREAD = Command.create("xread", -4, new BeginSearchKeyword("STREAMS", 1), new FindKeysRange(-1, 1, 2), false, true, false);
  Command XREADGROUP = Command.create("xreadgroup", -7, new BeginSearchKeyword("STREAMS", 4), new FindKeysRange(-1, 1, 2), false, true, false);
  Command XREVRANGE = Command.create("xrevrange", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command XSETID = Command.create("xsetid", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command XTRIM = Command.create("xtrim", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZADD = Command.create("zadd", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZCARD = Command.create("zcard", 2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZCOUNT = Command.create("zcount", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZDIFF = Command.create("zdiff", -3, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command ZDIFFSTORE = Command.create("zdiffstore", -4, null, null, true, false, false);
  Command ZINCRBY = Command.create("zincrby", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZINTER = Command.create("zinter", -3, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command ZINTERCARD = Command.create("zintercard", -3, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command ZINTERSTORE = Command.create("zinterstore", -4, null, null, true, false, false);
  Command ZLEXCOUNT = Command.create("zlexcount", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZMPOP = Command.create("zmpop", -4, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, false, false);
  Command ZMSCORE = Command.create("zmscore", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZPOPMAX = Command.create("zpopmax", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZPOPMIN = Command.create("zpopmin", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZRANDMEMBER = Command.create("zrandmember", -2, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZRANGE = Command.create("zrange", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZRANGEBYLEX = Command.create("zrangebylex", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZRANGEBYSCORE = Command.create("zrangebyscore", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZRANGESTORE = Command.create("zrangestore", -5, null, null, true, false, false);
  Command ZRANK = Command.create("zrank", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZREM = Command.create("zrem", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZREMRANGEBYLEX = Command.create("zremrangebylex", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZREMRANGEBYRANK = Command.create("zremrangebyrank", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZREMRANGEBYSCORE = Command.create("zremrangebyscore", 4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, false, false);
  Command ZREVRANGE = Command.create("zrevrange", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZREVRANGEBYLEX = Command.create("zrevrangebylex", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZREVRANGEBYSCORE = Command.create("zrevrangebyscore", -4, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZREVRANK = Command.create("zrevrank", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZSCAN = Command.create("zscan", -3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZSCORE = Command.create("zscore", 3, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0), false, true, false);
  Command ZUNION = Command.create("zunion", -3, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1), false, true, false);
  Command ZUNIONSTORE = Command.create("zunionstore", -4, null, null, true, false, false);
  Command SENTINEL = Command.create("sentinel");

  /**
   * Creates a Redis Command from METADATA. The metadata comes from the REDIS command "COMMAND"
   * https://redis.io/commands/command
   * <p>
   * Each top-level result contains six nested results. Each nested result is:
   * <p>
   * command name
   * command arity specification
   * nested Array reply of command flags
   * position of first key in argument list
   * position of last key in argument list
   * step count for locating repeating keys
   *
   * @param command  command name
   * @param arity    arity
   * @param firstKey position of the first key
   * @param lastKey  position of the last key
   * @param interval step count for locating repeating keys
   * @param write    write flag extracted from the nested Array reply of command flags
   * @param readOnly readOnly flag extracted from the nested Array reply of command flags
   * @param movable  movable flag extracted from the nested Array reply of command flags
   * @param pubsub   flag extracted from the nested Array reply of command flags
   * @return a command instance
   */
  @Deprecated
  static Command create(String command, int arity, int firstKey, int lastKey, int interval, boolean write, boolean readOnly, boolean movable, boolean pubsub) {
    return new CommandImpl(command, arity, new BeginSearchIndex(firstKey), new FindKeysRange(lastKey, interval, 0), movable, readOnly, pubsub);
  }

  /**
   * Creates a Redis Command from METADATA. The metadata comes from the REDIS command "COMMAND"
   * https://redis.io/commands/command
   * <p>
   * Each top-level result contains six nested results. Each nested result is:
   * <p>
   * command name
   * command arity specification
   * nested Array reply of command flags
   * position of first key in argument list
   * position of last key in argument list
   * step count for locating repeating keys
   *
   * @param command     command name
   * @param arity       arity
   * @param beginSearch position of the first key
   * @param findKeys    the algorithm to look up keys
   * @param getkeys     use server side {@code COMMAND GETKEYS} for key resolution
   * @param readOnly    {@code true} the command doesn't modify data, {@code false} the command writes data, {@code null} unknown.
   * @param pubsub      flag extracted from the nested Array reply of command flags
   * @return a command instance
   */
  @GenIgnore
  static Command create(String command, int arity, BeginSearch beginSearch, FindKeys findKeys, boolean getkeys, Boolean readOnly, boolean pubsub) {
    return new CommandImpl(command, arity, beginSearch, findKeys, getkeys, readOnly, pubsub);
  }


  /**
   * Generic command generator for extensions.
   *
   * @param command
   * @return the cacheable immutable command instance
   */
  static Command create(String command) {
    return create(command, -1, null, null, false, null, false);
  }

  int getArity();

  boolean isReadOnly();

  boolean isPubSub();
}
