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

  @GenIgnore
  default Future<Response> append(Object... args) {
    return send(Command.APPEND, args);
  }

  @Fluent
  default RedisAPI append(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    append(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> asking(Object... args) {
    return send(Command.ASKING, args);
  }

  @Fluent
  default RedisAPI asking(String arg0, Handler<AsyncResult<Response>> handler) {
    asking(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> auth(Object... args) {
    return send(Command.AUTH, args);
  }

  @Fluent
  default RedisAPI auth(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    auth(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bgrewriteaof(Object... args) {
    return send(Command.BGREWRITEAOF, args);
  }

  @Fluent
  default RedisAPI bgrewriteaof(String arg0, Handler<AsyncResult<Response>> handler) {
    bgrewriteaof(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bgsave(Object... args) {
    return send(Command.BGSAVE, args);
  }

  @Fluent
  default RedisAPI bgsave(List<String> args, Handler<AsyncResult<Response>> handler) {
    bgsave(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bitcount(Object... args) {
    return send(Command.BITCOUNT, args);
  }

  @Fluent
  default RedisAPI bitcount(List<String> args, Handler<AsyncResult<Response>> handler) {
    bitcount(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bitfield(Object... args) {
    return send(Command.BITFIELD, args);
  }

  @Fluent
  default RedisAPI bitfield(List<String> args, Handler<AsyncResult<Response>> handler) {
    bitfield(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bitop(Object... args) {
    return send(Command.BITOP, args);
  }

  @Fluent
  default RedisAPI bitop(List<String> args, Handler<AsyncResult<Response>> handler) {
    bitop(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bitpos(Object... args) {
    return send(Command.BITPOS, args);
  }

  @Fluent
  default RedisAPI bitpos(List<String> args, Handler<AsyncResult<Response>> handler) {
    bitpos(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> blpop(Object... args) {
    return send(Command.BLPOP, args);
  }

  @Fluent
  default RedisAPI blpop(List<String> args, Handler<AsyncResult<Response>> handler) {
    blpop(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> brpop(Object... args) {
    return send(Command.BRPOP, args);
  }

  @Fluent
  default RedisAPI brpop(List<String> args, Handler<AsyncResult<Response>> handler) {
    brpop(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> brpoplpush(Object... args) {
    return send(Command.BRPOPLPUSH, args);
  }

  @Fluent
  default RedisAPI brpoplpush(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    brpoplpush(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bzpopmax(Object... args) {
    return send(Command.BZPOPMAX, args);
  }

  @Fluent
  default RedisAPI bzpopmax(List<String> args, Handler<AsyncResult<Response>> handler) {
    bzpopmax(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> bzpopmin(Object... args) {
    return send(Command.BZPOPMIN, args);
  }

  @Fluent
  default RedisAPI bzpopmin(List<String> args, Handler<AsyncResult<Response>> handler) {
    bzpopmin(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> client(Object... args) {
    return send(Command.CLIENT, args);
  }

  @Fluent
  default RedisAPI client(List<String> args, Handler<AsyncResult<Response>> handler) {
    client(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> cluster(Object... args) {
    return send(Command.CLUSTER, args);
  }

  @Fluent
  default RedisAPI cluster(List<String> args, Handler<AsyncResult<Response>> handler) {
    cluster(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> command() {
    return send(Command.COMMAND);
  }

  @Fluent
  default RedisAPI command(Handler<AsyncResult<Response>> handler) {
    command().setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> config(Object... args) {
    return send(Command.CONFIG, args);
  }

  @Fluent
  default RedisAPI config(List<String> args, Handler<AsyncResult<Response>> handler) {
    config(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> dbsize(Object... args) {
    return send(Command.DBSIZE, args);
  }

  @Fluent
  default RedisAPI dbsize(String arg0, Handler<AsyncResult<Response>> handler) {
    dbsize(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> debug(Object... args) {
    return send(Command.DEBUG, args);
  }

  @Fluent
  default RedisAPI debug(List<String> args, Handler<AsyncResult<Response>> handler) {
    debug(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> decr(Object... args) {
    return send(Command.DECR, args);
  }

  @Fluent
  default RedisAPI decr(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    decr(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> decrby(Object... args) {
    return send(Command.DECRBY, args);
  }

  @Fluent
  default RedisAPI decrby(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    decrby(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> del(Object... args) {
    return send(Command.DEL, args);
  }

  @Fluent
  default RedisAPI del(List<String> args, Handler<AsyncResult<Response>> handler) {
    del(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> discard(Object... args) {
    return send(Command.DISCARD, args);
  }

  @Fluent
  default RedisAPI discard(String arg0, Handler<AsyncResult<Response>> handler) {
    discard(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> dump(Object... args) {
    return send(Command.DUMP, args);
  }

  @Fluent
  default RedisAPI dump(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    dump(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> echo(Object... args) {
    return send(Command.ECHO, args);
  }

  @Fluent
  default RedisAPI echo(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    echo(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> eval(Object... args) {
    return send(Command.EVAL, args);
  }

  @Fluent
  default RedisAPI eval(List<String> args, Handler<AsyncResult<Response>> handler) {
    eval(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> evalsha(Object... args) {
    return send(Command.EVALSHA, args);
  }

  @Fluent
  default RedisAPI evalsha(List<String> args, Handler<AsyncResult<Response>> handler) {
    evalsha(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> exec(Object... args) {
    return send(Command.EXEC, args);
  }

  @Fluent
  default RedisAPI exec(String arg0, Handler<AsyncResult<Response>> handler) {
    exec(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> exists(Object... args) {
    return send(Command.EXISTS, args);
  }

  @Fluent
  default RedisAPI exists(List<String> args, Handler<AsyncResult<Response>> handler) {
    exists(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> expire(Object... args) {
    return send(Command.EXPIRE, args);
  }

  @Fluent
  default RedisAPI expire(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    expire(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> expireat(Object... args) {
    return send(Command.EXPIREAT, args);
  }

  @Fluent
  default RedisAPI expireat(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    expireat(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> flushall(Object... args) {
    return send(Command.FLUSHALL, args);
  }

  @Fluent
  default RedisAPI flushall(List<String> args, Handler<AsyncResult<Response>> handler) {
    flushall(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> flushdb(Object... args) {
    return send(Command.FLUSHDB, args);
  }

  @Fluent
  default RedisAPI flushdb(List<String> args, Handler<AsyncResult<Response>> handler) {
    flushdb(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> geoadd(Object... args) {
    return send(Command.GEOADD, args);
  }

  @Fluent
  default RedisAPI geoadd(List<String> args, Handler<AsyncResult<Response>> handler) {
    geoadd(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> geodist(Object... args) {
    return send(Command.GEODIST, args);
  }

  @Fluent
  default RedisAPI geodist(List<String> args, Handler<AsyncResult<Response>> handler) {
    geodist(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> geohash(Object... args) {
    return send(Command.GEOHASH, args);
  }

  @Fluent
  default RedisAPI geohash(List<String> args, Handler<AsyncResult<Response>> handler) {
    geohash(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> geopos(Object... args) {
    return send(Command.GEOPOS, args);
  }

  @Fluent
  default RedisAPI geopos(List<String> args, Handler<AsyncResult<Response>> handler) {
    geopos(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> georadius(Object... args) {
    return send(Command.GEORADIUS, args);
  }

  @Fluent
  default RedisAPI georadius(List<String> args, Handler<AsyncResult<Response>> handler) {
    georadius(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> georadiusRo(Object... args) {
    return send(Command.GEORADIUS_RO, args);
  }

  @Fluent
  default RedisAPI georadiusRo(List<String> args, Handler<AsyncResult<Response>> handler) {
    georadiusRo(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> georadiusbymember(Object... args) {
    return send(Command.GEORADIUSBYMEMBER, args);
  }

  @Fluent
  default RedisAPI georadiusbymember(List<String> args, Handler<AsyncResult<Response>> handler) {
    georadiusbymember(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> georadiusbymemberRo(Object... args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args);
  }

  @Fluent
  default RedisAPI georadiusbymemberRo(List<String> args, Handler<AsyncResult<Response>> handler) {
    georadiusbymemberRo(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> get(Object... args) {
    return send(Command.GET, args);
  }

  @Fluent
  default RedisAPI get(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    get(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> getbit(Object... args) {
    return send(Command.GETBIT, args);
  }

  @Fluent
  default RedisAPI getbit(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    getbit(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> getrange(Object... args) {
    return send(Command.GETRANGE, args);
  }

  @Fluent
  default RedisAPI getrange(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    getrange(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> getset(Object... args) {
    return send(Command.GETSET, args);
  }

  @Fluent
  default RedisAPI getset(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    getset(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hdel(Object... args) {
    return send(Command.HDEL, args);
  }

  @Fluent
  default RedisAPI hdel(List<String> args, Handler<AsyncResult<Response>> handler) {
    hdel(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hexists(Object... args) {
    return send(Command.HEXISTS, args);
  }

  @Fluent
  default RedisAPI hexists(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    hexists(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hget(Object... args) {
    return send(Command.HGET, args);
  }

  @Fluent
  default RedisAPI hget(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    hget(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hgetall(Object... args) {
    return send(Command.HGETALL, args);
  }

  @Fluent
  default RedisAPI hgetall(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    hgetall(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hincrby(Object... args) {
    return send(Command.HINCRBY, args);
  }

  @Fluent
  default RedisAPI hincrby(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    hincrby(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hincrbyfloat(Object... args) {
    return send(Command.HINCRBYFLOAT, args);
  }

  @Fluent
  default RedisAPI hincrbyfloat(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    hincrbyfloat(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hkeys(Object... args) {
    return send(Command.HKEYS, args);
  }

  @Fluent
  default RedisAPI hkeys(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    hkeys(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hlen(Object... args) {
    return send(Command.HLEN, args);
  }

  @Fluent
  default RedisAPI hlen(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    hlen(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hmget(Object... args) {
    return send(Command.HMGET, args);
  }

  @Fluent
  default RedisAPI hmget(List<String> args, Handler<AsyncResult<Response>> handler) {
    hmget(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hmset(Object... args) {
    return send(Command.HMSET, args);
  }

  @Fluent
  default RedisAPI hmset(List<String> args, Handler<AsyncResult<Response>> handler) {
    hmset(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> host(Object... args) {
    return send(Command.HOST, args);
  }

  @Fluent
  default RedisAPI host(List<String> args, Handler<AsyncResult<Response>> handler) {
    host(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hscan(Object... args) {
    return send(Command.HSCAN, args);
  }

  @Fluent
  default RedisAPI hscan(List<String> args, Handler<AsyncResult<Response>> handler) {
    hscan(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hset(Object... args) {
    return send(Command.HSET, args);
  }

  @Fluent
  default RedisAPI hset(List<String> args, Handler<AsyncResult<Response>> handler) {
    hset(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hsetnx(Object... args) {
    return send(Command.HSETNX, args);
  }

  @Fluent
  default RedisAPI hsetnx(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    hsetnx(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hstrlen(Object... args) {
    return send(Command.HSTRLEN, args);
  }

  @Fluent
  default RedisAPI hstrlen(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    hstrlen(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> hvals(Object... args) {
    return send(Command.HVALS, args);
  }

  @Fluent
  default RedisAPI hvals(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    hvals(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> incr(Object... args) {
    return send(Command.INCR, args);
  }

  @Fluent
  default RedisAPI incr(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    incr(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> incrby(Object... args) {
    return send(Command.INCRBY, args);
  }

  @Fluent
  default RedisAPI incrby(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    incrby(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> incrbyfloat(Object... args) {
    return send(Command.INCRBYFLOAT, args);
  }

  @Fluent
  default RedisAPI incrbyfloat(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    incrbyfloat(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> info(Object... args) {
    return send(Command.INFO, args);
  }

  @Fluent
  default RedisAPI info(List<String> args, Handler<AsyncResult<Response>> handler) {
    info(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> keys(Object... args) {
    return send(Command.KEYS, args);
  }

  @Fluent
  default RedisAPI keys(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    keys(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lastsave(Object... args) {
    return send(Command.LASTSAVE, args);
  }

  @Fluent
  default RedisAPI lastsave(String arg0, Handler<AsyncResult<Response>> handler) {
    lastsave(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> latency(Object... args) {
    return send(Command.LATENCY, args);
  }

  @Fluent
  default RedisAPI latency(List<String> args, Handler<AsyncResult<Response>> handler) {
    latency(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lindex(Object... args) {
    return send(Command.LINDEX, args);
  }

  @Fluent
  default RedisAPI lindex(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    lindex(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> linsert(Object... args) {
    return send(Command.LINSERT, args);
  }

  @Fluent
  default RedisAPI linsert(String arg0, String arg1, String arg2, String arg3, String arg4, Handler<AsyncResult<Response>> handler) {
    linsert(arg0, arg1, arg2, arg3, arg4).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> llen(Object... args) {
    return send(Command.LLEN, args);
  }

  @Fluent
  default RedisAPI llen(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    llen(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lolwut(Object... args) {
    return send(Command.LOLWUT, args);
  }

  @Fluent
  default RedisAPI lolwut(List<String> args, Handler<AsyncResult<Response>> handler) {
    lolwut(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lpop(Object... args) {
    return send(Command.LPOP, args);
  }

  @Fluent
  default RedisAPI lpop(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    lpop(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lpush(Object... args) {
    return send(Command.LPUSH, args);
  }

  @Fluent
  default RedisAPI lpush(List<String> args, Handler<AsyncResult<Response>> handler) {
    lpush(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lpushx(Object... args) {
    return send(Command.LPUSHX, args);
  }

  @Fluent
  default RedisAPI lpushx(List<String> args, Handler<AsyncResult<Response>> handler) {
    lpushx(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lrange(Object... args) {
    return send(Command.LRANGE, args);
  }

  @Fluent
  default RedisAPI lrange(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    lrange(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lrem(Object... args) {
    return send(Command.LREM, args);
  }

  @Fluent
  default RedisAPI lrem(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    lrem(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> lset(Object... args) {
    return send(Command.LSET, args);
  }

  @Fluent
  default RedisAPI lset(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    lset(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> ltrim(Object... args) {
    return send(Command.LTRIM, args);
  }

  @Fluent
  default RedisAPI ltrim(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    ltrim(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> memory(Object... args) {
    return send(Command.MEMORY, args);
  }

  @Fluent
  default RedisAPI memory(List<String> args, Handler<AsyncResult<Response>> handler) {
    memory(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> mget(Object... args) {
    return send(Command.MGET, args);
  }

  @Fluent
  default RedisAPI mget(List<String> args, Handler<AsyncResult<Response>> handler) {
    mget(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> migrate(Object... args) {
    return send(Command.MIGRATE, args);
  }

  @Fluent
  default RedisAPI migrate(List<String> args, Handler<AsyncResult<Response>> handler) {
    migrate(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> module(Object... args) {
    return send(Command.MODULE, args);
  }

  @Fluent
  default RedisAPI module(List<String> args, Handler<AsyncResult<Response>> handler) {
    module(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> monitor(Object... args) {
    return send(Command.MONITOR, args);
  }

  @Fluent
  default RedisAPI monitor(String arg0, Handler<AsyncResult<Response>> handler) {
    monitor(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> move(Object... args) {
    return send(Command.MOVE, args);
  }

  @Fluent
  default RedisAPI move(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    move(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> mset(Object... args) {
    return send(Command.MSET, args);
  }

  @Fluent
  default RedisAPI mset(List<String> args, Handler<AsyncResult<Response>> handler) {
    mset(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> msetnx(Object... args) {
    return send(Command.MSETNX, args);
  }

  @Fluent
  default RedisAPI msetnx(List<String> args, Handler<AsyncResult<Response>> handler) {
    msetnx(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> multi(Object... args) {
    return send(Command.MULTI, args);
  }

  @Fluent
  default RedisAPI multi(String arg0, Handler<AsyncResult<Response>> handler) {
    multi(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> object(Object... args) {
    return send(Command.OBJECT, args);
  }

  @Fluent
  default RedisAPI object(List<String> args, Handler<AsyncResult<Response>> handler) {
    object(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> persist(Object... args) {
    return send(Command.PERSIST, args);
  }

  @Fluent
  default RedisAPI persist(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    persist(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pexpire(Object... args) {
    return send(Command.PEXPIRE, args);
  }

  @Fluent
  default RedisAPI pexpire(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    pexpire(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pexpireat(Object... args) {
    return send(Command.PEXPIREAT, args);
  }

  @Fluent
  default RedisAPI pexpireat(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    pexpireat(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pfadd(Object... args) {
    return send(Command.PFADD, args);
  }

  @Fluent
  default RedisAPI pfadd(List<String> args, Handler<AsyncResult<Response>> handler) {
    pfadd(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pfcount(Object... args) {
    return send(Command.PFCOUNT, args);
  }

  @Fluent
  default RedisAPI pfcount(List<String> args, Handler<AsyncResult<Response>> handler) {
    pfcount(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pfdebug(Object... args) {
    return send(Command.PFDEBUG, args);
  }

  @Fluent
  default RedisAPI pfdebug(List<String> args, Handler<AsyncResult<Response>> handler) {
    pfdebug(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pfmerge(Object... args) {
    return send(Command.PFMERGE, args);
  }

  @Fluent
  default RedisAPI pfmerge(List<String> args, Handler<AsyncResult<Response>> handler) {
    pfmerge(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pfselftest(Object... args) {
    return send(Command.PFSELFTEST, args);
  }

  @Fluent
  default RedisAPI pfselftest(String arg0, Handler<AsyncResult<Response>> handler) {
    pfselftest(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> ping(Object... args) {
    return send(Command.PING, args);
  }

  @Fluent
  default RedisAPI ping(List<String> args, Handler<AsyncResult<Response>> handler) {
    ping(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> post(Object... args) {
    return send(Command.POST, args);
  }

  @Fluent
  default RedisAPI post(List<String> args, Handler<AsyncResult<Response>> handler) {
    post(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> psetex(Object... args) {
    return send(Command.PSETEX, args);
  }

  @Fluent
  default RedisAPI psetex(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    psetex(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> psubscribe(Object... args) {
    return send(Command.PSUBSCRIBE, args);
  }

  @Fluent
  default RedisAPI psubscribe(List<String> args, Handler<AsyncResult<Response>> handler) {
    psubscribe(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> psync(Object... args) {
    return send(Command.PSYNC, args);
  }

  @Fluent
  default RedisAPI psync(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    psync(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pttl(Object... args) {
    return send(Command.PTTL, args);
  }

  @Fluent
  default RedisAPI pttl(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    pttl(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> publish(Object... args) {
    return send(Command.PUBLISH, args);
  }

  @Fluent
  default RedisAPI publish(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    publish(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> pubsub(Object... args) {
    return send(Command.PUBSUB, args);
  }

  @Fluent
  default RedisAPI pubsub(List<String> args, Handler<AsyncResult<Response>> handler) {
    pubsub(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> punsubscribe(Object... args) {
    return send(Command.PUNSUBSCRIBE, args);
  }

  @Fluent
  default RedisAPI punsubscribe(List<String> args, Handler<AsyncResult<Response>> handler) {
    punsubscribe(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> randomkey(Object... args) {
    return send(Command.RANDOMKEY, args);
  }

  @Fluent
  default RedisAPI randomkey(String arg0, Handler<AsyncResult<Response>> handler) {
    randomkey(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> readonly(Object... args) {
    return send(Command.READONLY, args);
  }

  @Fluent
  default RedisAPI readonly(String arg0, Handler<AsyncResult<Response>> handler) {
    readonly(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> readwrite(Object... args) {
    return send(Command.READWRITE, args);
  }

  @Fluent
  default RedisAPI readwrite(String arg0, Handler<AsyncResult<Response>> handler) {
    readwrite(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> rename(Object... args) {
    return send(Command.RENAME, args);
  }

  @Fluent
  default RedisAPI rename(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    rename(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> renamenx(Object... args) {
    return send(Command.RENAMENX, args);
  }

  @Fluent
  default RedisAPI renamenx(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    renamenx(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> replconf(Object... args) {
    return send(Command.REPLCONF, args);
  }

  @Fluent
  default RedisAPI replconf(List<String> args, Handler<AsyncResult<Response>> handler) {
    replconf(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> replicaof(Object... args) {
    return send(Command.REPLICAOF, args);
  }

  @Fluent
  default RedisAPI replicaof(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    replicaof(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> restore(Object... args) {
    return send(Command.RESTORE, args);
  }

  @Fluent
  default RedisAPI restore(List<String> args, Handler<AsyncResult<Response>> handler) {
    restore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> restoreAsking(Object... args) {
    return send(Command.RESTORE_ASKING, args);
  }

  @Fluent
  default RedisAPI restoreAsking(List<String> args, Handler<AsyncResult<Response>> handler) {
    restoreAsking(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> role(Object... args) {
    return send(Command.ROLE, args);
  }

  @Fluent
  default RedisAPI role(String arg0, Handler<AsyncResult<Response>> handler) {
    role(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> rpop(Object... args) {
    return send(Command.RPOP, args);
  }

  @Fluent
  default RedisAPI rpop(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    rpop(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> rpoplpush(Object... args) {
    return send(Command.RPOPLPUSH, args);
  }

  @Fluent
  default RedisAPI rpoplpush(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    rpoplpush(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> rpush(Object... args) {
    return send(Command.RPUSH, args);
  }

  @Fluent
  default RedisAPI rpush(List<String> args, Handler<AsyncResult<Response>> handler) {
    rpush(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> rpushx(Object... args) {
    return send(Command.RPUSHX, args);
  }

  @Fluent
  default RedisAPI rpushx(List<String> args, Handler<AsyncResult<Response>> handler) {
    rpushx(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sadd(Object... args) {
    return send(Command.SADD, args);
  }

  @Fluent
  default RedisAPI sadd(List<String> args, Handler<AsyncResult<Response>> handler) {
    sadd(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> save(Object... args) {
    return send(Command.SAVE, args);
  }

  @Fluent
  default RedisAPI save(String arg0, Handler<AsyncResult<Response>> handler) {
    save(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> scan(Object... args) {
    return send(Command.SCAN, args);
  }

  @Fluent
  default RedisAPI scan(List<String> args, Handler<AsyncResult<Response>> handler) {
    scan(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> scard(Object... args) {
    return send(Command.SCARD, args);
  }

  @Fluent
  default RedisAPI scard(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    scard(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> script(Object... args) {
    return send(Command.SCRIPT, args);
  }

  @Fluent
  default RedisAPI script(List<String> args, Handler<AsyncResult<Response>> handler) {
    script(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sdiff(Object... args) {
    return send(Command.SDIFF, args);
  }

  @Fluent
  default RedisAPI sdiff(List<String> args, Handler<AsyncResult<Response>> handler) {
    sdiff(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sdiffstore(Object... args) {
    return send(Command.SDIFFSTORE, args);
  }

  @Fluent
  default RedisAPI sdiffstore(List<String> args, Handler<AsyncResult<Response>> handler) {
    sdiffstore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> select(Object... args) {
    return send(Command.SELECT, args);
  }

  @Fluent
  default RedisAPI select(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    select(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> set(Object... args) {
    return send(Command.SET, args);
  }

  @Fluent
  default RedisAPI set(List<String> args, Handler<AsyncResult<Response>> handler) {
    set(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> setbit(Object... args) {
    return send(Command.SETBIT, args);
  }

  @Fluent
  default RedisAPI setbit(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    setbit(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> setex(Object... args) {
    return send(Command.SETEX, args);
  }

  @Fluent
  default RedisAPI setex(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    setex(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> setnx(Object... args) {
    return send(Command.SETNX, args);
  }

  @Fluent
  default RedisAPI setnx(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    setnx(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> setrange(Object... args) {
    return send(Command.SETRANGE, args);
  }

  @Fluent
  default RedisAPI setrange(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    setrange(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> shutdown(Object... args) {
    return send(Command.SHUTDOWN, args);
  }

  @Fluent
  default RedisAPI shutdown(List<String> args, Handler<AsyncResult<Response>> handler) {
    shutdown(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sinter(Object... args) {
    return send(Command.SINTER, args);
  }

  @Fluent
  default RedisAPI sinter(List<String> args, Handler<AsyncResult<Response>> handler) {
    sinter(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sinterstore(Object... args) {
    return send(Command.SINTERSTORE, args);
  }

  @Fluent
  default RedisAPI sinterstore(List<String> args, Handler<AsyncResult<Response>> handler) {
    sinterstore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sismember(Object... args) {
    return send(Command.SISMEMBER, args);
  }

  @Fluent
  default RedisAPI sismember(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    sismember(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> slaveof(Object... args) {
    return send(Command.SLAVEOF, args);
  }

  @Fluent
  default RedisAPI slaveof(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    slaveof(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> slowlog(Object... args) {
    return send(Command.SLOWLOG, args);
  }

  @Fluent
  default RedisAPI slowlog(List<String> args, Handler<AsyncResult<Response>> handler) {
    slowlog(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> smembers(Object... args) {
    return send(Command.SMEMBERS, args);
  }

  @Fluent
  default RedisAPI smembers(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    smembers(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> smove(Object... args) {
    return send(Command.SMOVE, args);
  }

  @Fluent
  default RedisAPI smove(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    smove(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sort(Object... args) {
    return send(Command.SORT, args);
  }

  @Fluent
  default RedisAPI sort(List<String> args, Handler<AsyncResult<Response>> handler) {
    sort(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> spop(Object... args) {
    return send(Command.SPOP, args);
  }

  @Fluent
  default RedisAPI spop(List<String> args, Handler<AsyncResult<Response>> handler) {
    spop(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> srandmember(Object... args) {
    return send(Command.SRANDMEMBER, args);
  }

  @Fluent
  default RedisAPI srandmember(List<String> args, Handler<AsyncResult<Response>> handler) {
    srandmember(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> srem(Object... args) {
    return send(Command.SREM, args);
  }

  @Fluent
  default RedisAPI srem(List<String> args, Handler<AsyncResult<Response>> handler) {
    srem(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sscan(Object... args) {
    return send(Command.SSCAN, args);
  }

  @Fluent
  default RedisAPI sscan(List<String> args, Handler<AsyncResult<Response>> handler) {
    sscan(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> strlen(Object... args) {
    return send(Command.STRLEN, args);
  }

  @Fluent
  default RedisAPI strlen(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    strlen(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> subscribe(Object... args) {
    return send(Command.SUBSCRIBE, args);
  }

  @Fluent
  default RedisAPI subscribe(List<String> args, Handler<AsyncResult<Response>> handler) {
    subscribe(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> substr(Object... args) {
    return send(Command.SUBSTR, args);
  }

  @Fluent
  default RedisAPI substr(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    substr(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sunion(Object... args) {
    return send(Command.SUNION, args);
  }

  @Fluent
  default RedisAPI sunion(List<String> args, Handler<AsyncResult<Response>> handler) {
    sunion(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sunionstore(Object... args) {
    return send(Command.SUNIONSTORE, args);
  }

  @Fluent
  default RedisAPI sunionstore(List<String> args, Handler<AsyncResult<Response>> handler) {
    sunionstore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> swapdb(Object... args) {
    return send(Command.SWAPDB, args);
  }

  @Fluent
  default RedisAPI swapdb(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    swapdb(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> sync(Object... args) {
    return send(Command.SYNC, args);
  }

  @Fluent
  default RedisAPI sync(String arg0, Handler<AsyncResult<Response>> handler) {
    sync(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> time(Object... args) {
    return send(Command.TIME, args);
  }

  @Fluent
  default RedisAPI time(String arg0, Handler<AsyncResult<Response>> handler) {
    time(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> touch(Object... args) {
    return send(Command.TOUCH, args);
  }

  @Fluent
  default RedisAPI touch(List<String> args, Handler<AsyncResult<Response>> handler) {
    touch(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> ttl(Object... args) {
    return send(Command.TTL, args);
  }

  @Fluent
  default RedisAPI ttl(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    ttl(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> type(Object... args) {
    return send(Command.TYPE, args);
  }

  @Fluent
  default RedisAPI type(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    type(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> unlink(Object... args) {
    return send(Command.UNLINK, args);
  }

  @Fluent
  default RedisAPI unlink(List<String> args, Handler<AsyncResult<Response>> handler) {
    unlink(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> unsubscribe(Object... args) {
    return send(Command.UNSUBSCRIBE, args);
  }

  @Fluent
  default RedisAPI unsubscribe(List<String> args, Handler<AsyncResult<Response>> handler) {
    unsubscribe(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> unwatch(Object... args) {
    return send(Command.UNWATCH, args);
  }

  @Fluent
  default RedisAPI unwatch(String arg0, Handler<AsyncResult<Response>> handler) {
    unwatch(arg0).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> wait(Object... args) {
    return send(Command.WAIT, args);
  }

  @Fluent
  default RedisAPI wait(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    wait(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> watch(Object... args) {
    return send(Command.WATCH, args);
  }

  @Fluent
  default RedisAPI watch(List<String> args, Handler<AsyncResult<Response>> handler) {
    watch(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xack(Object... args) {
    return send(Command.XACK, args);
  }

  @Fluent
  default RedisAPI xack(List<String> args, Handler<AsyncResult<Response>> handler) {
    xack(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xadd(Object... args) {
    return send(Command.XADD, args);
  }

  @Fluent
  default RedisAPI xadd(List<String> args, Handler<AsyncResult<Response>> handler) {
    xadd(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xclaim(Object... args) {
    return send(Command.XCLAIM, args);
  }

  @Fluent
  default RedisAPI xclaim(List<String> args, Handler<AsyncResult<Response>> handler) {
    xclaim(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xdel(Object... args) {
    return send(Command.XDEL, args);
  }

  @Fluent
  default RedisAPI xdel(List<String> args, Handler<AsyncResult<Response>> handler) {
    xdel(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xgroup(Object... args) {
    return send(Command.XGROUP, args);
  }

  @Fluent
  default RedisAPI xgroup(List<String> args, Handler<AsyncResult<Response>> handler) {
    xgroup(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xinfo(Object... args) {
    return send(Command.XINFO, args);
  }

  @Fluent
  default RedisAPI xinfo(List<String> args, Handler<AsyncResult<Response>> handler) {
    xinfo(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xlen(Object... args) {
    return send(Command.XLEN, args);
  }

  @Fluent
  default RedisAPI xlen(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    xlen(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xpending(Object... args) {
    return send(Command.XPENDING, args);
  }

  @Fluent
  default RedisAPI xpending(List<String> args, Handler<AsyncResult<Response>> handler) {
    xpending(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xrange(Object... args) {
    return send(Command.XRANGE, args);
  }

  @Fluent
  default RedisAPI xrange(List<String> args, Handler<AsyncResult<Response>> handler) {
    xrange(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xread(Object... args) {
    return send(Command.XREAD, args);
  }

  @Fluent
  default RedisAPI xread(List<String> args, Handler<AsyncResult<Response>> handler) {
    xread(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xreadgroup(Object... args) {
    return send(Command.XREADGROUP, args);
  }

  @Fluent
  default RedisAPI xreadgroup(List<String> args, Handler<AsyncResult<Response>> handler) {
    xreadgroup(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xrevrange(Object... args) {
    return send(Command.XREVRANGE, args);
  }

  @Fluent
  default RedisAPI xrevrange(List<String> args, Handler<AsyncResult<Response>> handler) {
    xrevrange(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xsetid(Object... args) {
    return send(Command.XSETID, args);
  }

  @Fluent
  default RedisAPI xsetid(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    xsetid(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> xtrim(Object... args) {
    return send(Command.XTRIM, args);
  }

  @Fluent
  default RedisAPI xtrim(List<String> args, Handler<AsyncResult<Response>> handler) {
    xtrim(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zadd(Object... args) {
    return send(Command.ZADD, args);
  }

  @Fluent
  default RedisAPI zadd(List<String> args, Handler<AsyncResult<Response>> handler) {
    zadd(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zcard(Object... args) {
    return send(Command.ZCARD, args);
  }

  @Fluent
  default RedisAPI zcard(String arg0, String arg1, Handler<AsyncResult<Response>> handler) {
    zcard(arg0, arg1).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zcount(Object... args) {
    return send(Command.ZCOUNT, args);
  }

  @Fluent
  default RedisAPI zcount(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zcount(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zincrby(Object... args) {
    return send(Command.ZINCRBY, args);
  }

  @Fluent
  default RedisAPI zincrby(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zincrby(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zinterstore(Object... args) {
    return send(Command.ZINTERSTORE, args);
  }

  @Fluent
  default RedisAPI zinterstore(List<String> args, Handler<AsyncResult<Response>> handler) {
    zinterstore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zlexcount(Object... args) {
    return send(Command.ZLEXCOUNT, args);
  }

  @Fluent
  default RedisAPI zlexcount(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zlexcount(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zpopmax(Object... args) {
    return send(Command.ZPOPMAX, args);
  }

  @Fluent
  default RedisAPI zpopmax(List<String> args, Handler<AsyncResult<Response>> handler) {
    zpopmax(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zpopmin(Object... args) {
    return send(Command.ZPOPMIN, args);
  }

  @Fluent
  default RedisAPI zpopmin(List<String> args, Handler<AsyncResult<Response>> handler) {
    zpopmin(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrange(Object... args) {
    return send(Command.ZRANGE, args);
  }

  @Fluent
  default RedisAPI zrange(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrange(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrangebylex(Object... args) {
    return send(Command.ZRANGEBYLEX, args);
  }

  @Fluent
  default RedisAPI zrangebylex(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrangebylex(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrangebyscore(Object... args) {
    return send(Command.ZRANGEBYSCORE, args);
  }

  @Fluent
  default RedisAPI zrangebyscore(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrangebyscore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrank(Object... args) {
    return send(Command.ZRANK, args);
  }

  @Fluent
  default RedisAPI zrank(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    zrank(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrem(Object... args) {
    return send(Command.ZREM, args);
  }

  @Fluent
  default RedisAPI zrem(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrem(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zremrangebylex(Object... args) {
    return send(Command.ZREMRANGEBYLEX, args);
  }

  @Fluent
  default RedisAPI zremrangebylex(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zremrangebylex(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zremrangebyrank(Object... args) {
    return send(Command.ZREMRANGEBYRANK, args);
  }

  @Fluent
  default RedisAPI zremrangebyrank(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zremrangebyrank(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zremrangebyscore(Object... args) {
    return send(Command.ZREMRANGEBYSCORE, args);
  }

  @Fluent
  default RedisAPI zremrangebyscore(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<Response>> handler) {
    zremrangebyscore(arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrevrange(Object... args) {
    return send(Command.ZREVRANGE, args);
  }

  @Fluent
  default RedisAPI zrevrange(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrevrange(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrevrangebylex(Object... args) {
    return send(Command.ZREVRANGEBYLEX, args);
  }

  @Fluent
  default RedisAPI zrevrangebylex(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrevrangebylex(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrevrangebyscore(Object... args) {
    return send(Command.ZREVRANGEBYSCORE, args);
  }

  @Fluent
  default RedisAPI zrevrangebyscore(List<String> args, Handler<AsyncResult<Response>> handler) {
    zrevrangebyscore(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zrevrank(Object... args) {
    return send(Command.ZREVRANK, args);
  }

  @Fluent
  default RedisAPI zrevrank(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    zrevrank(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zscan(Object... args) {
    return send(Command.ZSCAN, args);
  }

  @Fluent
  default RedisAPI zscan(List<String> args, Handler<AsyncResult<Response>> handler) {
    zscan(args.toArray()).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zscore(Object... args) {
    return send(Command.ZSCORE, args);
  }

  @Fluent
  default RedisAPI zscore(String arg0, String arg1, String arg2, Handler<AsyncResult<Response>> handler) {
    zscore(arg0, arg1, arg2).setHandler(handler);
    return this;
  }
  @GenIgnore
  default Future<Response> zunionstore(Object... args) {
    return send(Command.ZUNIONSTORE, args);
  }

  @Fluent
  default RedisAPI zunionstore(List<String> args, Handler<AsyncResult<Response>> handler) {
    zunionstore(args.toArray()).setHandler(handler);
    return this;
  }

  @GenIgnore
  Future<Response> send(Command cmd, Object... args);
}
