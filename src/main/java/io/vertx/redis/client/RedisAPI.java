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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.redis.client.impl.RedisAPIImpl;

import java.util.List;

/**
 * <b>Auto generated</b> Redis API client wrapper.
 */
@VertxGen
public interface RedisAPI {

  static RedisAPI api(Redis client) {
    return new RedisAPIImpl(client);
  }

  @Fluent
  default RedisAPI append(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.APPEND, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI asking(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ASKING).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI auth(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.AUTH, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bgrewriteaof(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BGREWRITEAOF).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bgsave(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BGSAVE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bitcount(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITCOUNT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bitfield(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITFIELD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bitop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITOP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bitpos(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITPOS, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI blpop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BLPOP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI brpop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BRPOP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI brpoplpush(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BRPOPLPUSH, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bzpopmax(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BZPOPMAX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI bzpopmin(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BZPOPMIN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI client(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CLIENT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI cluster(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CLUSTER, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI command(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.COMMAND, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI config(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CONFIG, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI dbsize(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DBSIZE).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI debug(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DEBUG, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI decr(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DECR, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI decrby(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DECRBY, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI del(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DEL, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI discard(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DISCARD).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI dump(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DUMP, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI echo(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ECHO, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI eval(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EVAL, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI evalsha(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EVALSHA, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI exec(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXEC).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI exists(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXISTS, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI expire(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXPIRE, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI expireat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXPIREAT, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI flushall(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.FLUSHALL, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI flushdb(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.FLUSHDB, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI geoadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOADD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI geodist(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEODIST, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI geohash(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOHASH, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI geopos(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOPOS, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI georadius(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUS, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI georadiusRo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUS_RO, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI georadiusbymember(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUSBYMEMBER, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI georadiusbymemberRo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUSBYMEMBER_RO, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI get(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GET, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI getbit(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETBIT, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI getrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI getset(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETSET, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hdel(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HDEL, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hexists(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HEXISTS, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hget(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HGET, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hgetall(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HGETALL, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hincrby(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HINCRBY, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hincrbyfloat(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HINCRBYFLOAT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hkeys(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HKEYS, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HLEN, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hmget(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HMGET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hmset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HMSET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI host(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HOST, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSCAN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hsetnx(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSETNX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hstrlen(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSTRLEN, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI hvals(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HVALS, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI incr(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCR, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI incrby(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCRBY, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI incrbyfloat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCRBYFLOAT, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI info(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INFO, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI keys(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.KEYS, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lastsave(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LASTSAVE).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI latency(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LATENCY, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lindex(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LINDEX, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI linsert(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LINSERT, arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI llen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LLEN, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lolwut(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LOLWUT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lpop(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPOP, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lpush(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPUSH, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lpushx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPUSHX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lrem(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LREM, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI lset(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LSET, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI ltrim(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LTRIM, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI memory(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MEMORY, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI mget(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MGET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI migrate(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MIGRATE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI module(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MODULE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI monitor(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MONITOR).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI move(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MOVE, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI mset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MSET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI msetnx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MSETNX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI multi(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MULTI).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI object(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.OBJECT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI persist(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PERSIST, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pexpire(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PEXPIRE, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pexpireat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PEXPIREAT, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pfadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFADD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pfcount(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFCOUNT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pfdebug(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFDEBUG, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pfmerge(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFMERGE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pfselftest(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFSELFTEST).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI ping(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PING, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI post(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.POST, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI psetex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSETEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI psubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSUBSCRIBE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI psync(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSYNC, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pttl(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PTTL, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI publish(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUBLISH, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI pubsub(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUBSUB, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI punsubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUNSUBSCRIBE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI randomkey(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RANDOMKEY).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI readonly(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.READONLY).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI readwrite(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.READWRITE).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI rename(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RENAME, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI renamenx(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RENAMENX, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI replconf(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.REPLCONF, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI replicaof(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.REPLICAOF, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI restore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RESTORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI restoreAsking(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RESTORE_ASKING, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI role(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ROLE).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI rpop(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPOP, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI rpoplpush(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPOPLPUSH, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI rpush(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPUSH, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI rpushx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPUSHX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SADD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI save(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SAVE).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI scan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCAN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI scard(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCARD, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI script(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCRIPT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sdiff(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SDIFF, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sdiffstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SDIFFSTORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI select(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SELECT, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI set(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SET, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI setbit(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETBIT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI setex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI setnx(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETNX, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI setrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI shutdown(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SHUTDOWN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sinter(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SINTER, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sinterstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SINTERSTORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sismember(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SISMEMBER, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI slaveof(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SLAVEOF, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI slowlog(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SLOWLOG, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI smembers(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SMEMBERS, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI smove(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SMOVE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sort(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SORT, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI spop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SPOP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI srandmember(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SRANDMEMBER, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI srem(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SREM, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SSCAN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI strlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.STRLEN, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI subscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUBSCRIBE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI substr(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUBSTR, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sunion(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUNION, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sunionstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUNIONSTORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI swapdb(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SWAPDB, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI sync(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SYNC).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI time(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TIME).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI touch(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TOUCH, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI ttl(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TTL, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI type(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TYPE, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI unlink(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNLINK, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI unsubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNSUBSCRIBE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI unwatch(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNWATCH).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI wait(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.WAIT, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI watch(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.WATCH, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xack(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XACK, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XADD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xclaim(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XCLAIM, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xdel(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XDEL, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xgroup(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XGROUP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xinfo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XINFO, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XLEN, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xpending(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XPENDING, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XRANGE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xread(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREAD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xreadgroup(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREADGROUP, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xrevrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREVRANGE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xsetid(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XSETID, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI xtrim(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XTRIM, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZADD, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zcard(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZCARD, arg0).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zcount(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZCOUNT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zincrby(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZINCRBY, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zinterstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZINTERSTORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zlexcount(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZLEXCOUNT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zpopmax(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZPOPMAX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zpopmin(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZPOPMIN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrangebylex(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGEBYLEX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrangebyscore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGEBYSCORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrank(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANK, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrem(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREM, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zremrangebylex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zremrangebyrank(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zremrangebyscore(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrevrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrevrangebylex(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGEBYLEX, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrevrangebyscore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGEBYSCORE, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zrevrank(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANK, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZSCAN, args.toArray()).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zscore(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZSCORE, arg0, arg1).setHandler(handler);
    return this;
  }
  @Fluent
  default RedisAPI zunionstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZUNIONSTORE, args.toArray()).setHandler(handler);
    return this;
  }

  @GenIgnore
  Future<Response> send(Command cmd, Object... args);
}
