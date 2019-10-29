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

import static io.vertx.codegen.annotations.GenIgnore.PERMITTED_TYPE;

/**
 * <b>Auto generated</b> Redis API client wrapper.
 */
@VertxGen
public interface RedisAPI {

  @GenIgnore(PERMITTED_TYPE)
  static RedisAPI api(Redis client) {
    return new RedisAPIImpl(client);
  }

  @GenIgnore(PERMITTED_TYPE)
  static RedisAPI api(RedisConnection connection) {
    return new RedisAPIImpl(connection);
  }

  void close();

  /**
   * Redis command <a href="https://redis.io/commands/append">append</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI append(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.APPEND, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/append">append</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> append(String arg0, String arg1) {
    return send(Command.APPEND, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/asking">asking</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI asking(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ASKING).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/asking">asking</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> asking() {
    return send(Command.ASKING);
  }
  /**
   * Redis command <a href="https://redis.io/commands/auth">auth</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI auth(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.AUTH, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/auth">auth</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> auth(String arg0) {
    return send(Command.AUTH, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/bgrewriteaof">bgrewriteaof</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bgrewriteaof(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BGREWRITEAOF).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgrewriteaof">bgrewriteaof</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bgrewriteaof() {
    return send(Command.BGREWRITEAOF);
  }
  /**
   * Redis command <a href="https://redis.io/commands/bgsave">bgsave</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bgsave(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BGSAVE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgsave">bgsave</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bgsave(List<String> args) {
    return send(Command.BGSAVE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/bitcount">bitcount</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bitcount(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITCOUNT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitcount">bitcount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitcount(List<String> args) {
    return send(Command.BITCOUNT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/bitfield">bitfield</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bitfield(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITFIELD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield">bitfield</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitfield(List<String> args) {
    return send(Command.BITFIELD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/bitop">bitop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bitop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITOP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitop">bitop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitop(List<String> args) {
    return send(Command.BITOP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/bitpos">bitpos</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bitpos(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BITPOS, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitpos">bitpos</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitpos(List<String> args) {
    return send(Command.BITPOS, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/blpop">blpop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI blpop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BLPOP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/blpop">blpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blpop(List<String> args) {
    return send(Command.BLPOP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/brpop">brpop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI brpop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BRPOP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpop">brpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> brpop(List<String> args) {
    return send(Command.BRPOP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/brpoplpush">brpoplpush</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI brpoplpush(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BRPOPLPUSH, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpoplpush">brpoplpush</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> brpoplpush(String arg0, String arg1, String arg2) {
    return send(Command.BRPOPLPUSH, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/bzpopmax">bzpopmax</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bzpopmax(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BZPOPMAX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmax">bzpopmax</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzpopmax(List<String> args) {
    return send(Command.BZPOPMAX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/bzpopmin">bzpopmin</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI bzpopmin(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.BZPOPMIN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmin">bzpopmin</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzpopmin(List<String> args) {
    return send(Command.BZPOPMIN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/client">client</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI client(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CLIENT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/client">client</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> client(List<String> args) {
    return send(Command.CLIENT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/cluster">cluster</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI cluster(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CLUSTER, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/cluster">cluster</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cluster(List<String> args) {
    return send(Command.CLUSTER, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/command">command</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI command(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.COMMAND).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/command">command</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> command() {
    return send(Command.COMMAND);
  }
  /**
   * Redis command <a href="https://redis.io/commands/config">config</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI config(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.CONFIG, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/config">config</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> config(List<String> args) {
    return send(Command.CONFIG, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/dbsize">dbsize</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI dbsize(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DBSIZE).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/dbsize">dbsize</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> dbsize() {
    return send(Command.DBSIZE);
  }
  /**
   * Redis command <a href="https://redis.io/commands/debug">debug</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI debug(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DEBUG, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/debug">debug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> debug(List<String> args) {
    return send(Command.DEBUG, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/decr">decr</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI decr(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DECR, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/decr">decr</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> decr(String arg0) {
    return send(Command.DECR, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/decrby">decrby</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI decrby(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DECRBY, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/decrby">decrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> decrby(String arg0, String arg1) {
    return send(Command.DECRBY, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/del">del</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI del(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DEL, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/del">del</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> del(List<String> args) {
    return send(Command.DEL, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/discard">discard</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI discard(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DISCARD).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/discard">discard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> discard() {
    return send(Command.DISCARD);
  }
  /**
   * Redis command <a href="https://redis.io/commands/dump">dump</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI dump(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.DUMP, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/dump">dump</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> dump(String arg0) {
    return send(Command.DUMP, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/echo">echo</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI echo(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ECHO, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/echo">echo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> echo(String arg0) {
    return send(Command.ECHO, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/eval">eval</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI eval(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EVAL, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval">eval</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> eval(List<String> args) {
    return send(Command.EVAL, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/evalsha">evalsha</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI evalsha(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EVALSHA, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha">evalsha</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalsha(List<String> args) {
    return send(Command.EVALSHA, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/exec">exec</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI exec(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXEC).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/exec">exec</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> exec() {
    return send(Command.EXEC);
  }
  /**
   * Redis command <a href="https://redis.io/commands/exists">exists</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI exists(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXISTS, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/exists">exists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> exists(List<String> args) {
    return send(Command.EXISTS, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/expire">expire</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI expire(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXPIRE, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/expire">expire</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expire(String arg0, String arg1) {
    return send(Command.EXPIRE, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/expireat">expireat</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI expireat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.EXPIREAT, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/expireat">expireat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expireat(String arg0, String arg1) {
    return send(Command.EXPIREAT, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/flushall">flushall</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI flushall(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.FLUSHALL, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushall">flushall</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> flushall(List<String> args) {
    return send(Command.FLUSHALL, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/flushdb">flushdb</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI flushdb(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.FLUSHDB, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushdb">flushdb</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> flushdb(List<String> args) {
    return send(Command.FLUSHDB, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/geoadd">geoadd</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI geoadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOADD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/geoadd">geoadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geoadd(List<String> args) {
    return send(Command.GEOADD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/geodist">geodist</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI geodist(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEODIST, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/geodist">geodist</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geodist(List<String> args) {
    return send(Command.GEODIST, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/geohash">geohash</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI geohash(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOHASH, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/geohash">geohash</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geohash(List<String> args) {
    return send(Command.GEOHASH, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/geopos">geopos</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI geopos(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEOPOS, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/geopos">geopos</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geopos(List<String> args) {
    return send(Command.GEOPOS, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/georadius">georadius</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI georadius(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUS, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius">georadius</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadius(List<String> args) {
    return send(Command.GEORADIUS, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/georadius_ro">georadiusRo</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI georadiusRo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUS_RO, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius_ro">georadiusRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusRo(List<String> args) {
    return send(Command.GEORADIUS_RO, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember">georadiusbymember</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI georadiusbymember(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUSBYMEMBER, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember">georadiusbymember</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusbymember(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember_ro">georadiusbymemberRo</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI georadiusbymemberRo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember_ro">georadiusbymemberRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusbymemberRo(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/get">get</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI get(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GET, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/get">get</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> get(String arg0) {
    return send(Command.GET, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/getbit">getbit</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI getbit(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETBIT, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/getbit">getbit</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getbit(String arg0, String arg1) {
    return send(Command.GETBIT, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/getrange">getrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI getrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/getrange">getrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getrange(String arg0, String arg1, String arg2) {
    return send(Command.GETRANGE, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/getset">getset</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI getset(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.GETSET, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/getset">getset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getset(String arg0, String arg1) {
    return send(Command.GETSET, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hdel">hdel</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hdel(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HDEL, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hdel">hdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hdel(List<String> args) {
    return send(Command.HDEL, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/hexists">hexists</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hexists(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HEXISTS, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hexists">hexists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hexists(String arg0, String arg1) {
    return send(Command.HEXISTS, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hget">hget</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hget(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HGET, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hget">hget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hget(String arg0, String arg1) {
    return send(Command.HGET, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hgetall">hgetall</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hgetall(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HGETALL, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hgetall">hgetall</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hgetall(String arg0) {
    return send(Command.HGETALL, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hincrby">hincrby</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hincrby(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HINCRBY, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrby">hincrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hincrby(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBY, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hincrbyfloat">hincrbyfloat</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hincrbyfloat(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HINCRBYFLOAT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrbyfloat">hincrbyfloat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hincrbyfloat(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBYFLOAT, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hkeys">hkeys</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hkeys(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HKEYS, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hkeys">hkeys</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hkeys(String arg0) {
    return send(Command.HKEYS, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hlen">hlen</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HLEN, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hlen">hlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hlen(String arg0) {
    return send(Command.HLEN, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hmget">hmget</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hmget(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HMGET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmget">hmget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hmget(List<String> args) {
    return send(Command.HMGET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/hmset">hmset</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hmset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HMSET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmset">hmset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hmset(List<String> args) {
    return send(Command.HMSET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/host:">host</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI host(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HOST, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/host:">host</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> host(List<String> args) {
    return send(Command.HOST, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/hscan">hscan</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSCAN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hscan">hscan</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hscan(List<String> args) {
    return send(Command.HSCAN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/hset">hset</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hset">hset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hset(List<String> args) {
    return send(Command.HSET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/hsetnx">hsetnx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hsetnx(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSETNX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hsetnx">hsetnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hsetnx(String arg0, String arg1, String arg2) {
    return send(Command.HSETNX, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hstrlen">hstrlen</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hstrlen(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HSTRLEN, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hstrlen">hstrlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hstrlen(String arg0, String arg1) {
    return send(Command.HSTRLEN, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/hvals">hvals</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI hvals(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.HVALS, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/hvals">hvals</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hvals(String arg0) {
    return send(Command.HVALS, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/incr">incr</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI incr(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCR, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/incr">incr</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incr(String arg0) {
    return send(Command.INCR, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/incrby">incrby</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI incrby(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCRBY, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrby">incrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incrby(String arg0, String arg1) {
    return send(Command.INCRBY, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/incrbyfloat">incrbyfloat</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI incrbyfloat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INCRBYFLOAT, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrbyfloat">incrbyfloat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incrbyfloat(String arg0, String arg1) {
    return send(Command.INCRBYFLOAT, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/info">info</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI info(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.INFO, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/info">info</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> info(List<String> args) {
    return send(Command.INFO, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/keys">keys</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI keys(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.KEYS, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/keys">keys</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> keys(String arg0) {
    return send(Command.KEYS, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/lastsave">lastsave</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lastsave(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LASTSAVE).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lastsave">lastsave</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lastsave() {
    return send(Command.LASTSAVE);
  }
  /**
   * Redis command <a href="https://redis.io/commands/latency">latency</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI latency(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LATENCY, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/latency">latency</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> latency(List<String> args) {
    return send(Command.LATENCY, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/lindex">lindex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lindex(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LINDEX, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lindex">lindex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lindex(String arg0, String arg1) {
    return send(Command.LINDEX, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/linsert">linsert</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI linsert(String arg0, String arg1, String arg2, String arg3, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LINSERT, arg0, arg1, arg2, arg3).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/linsert">linsert</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> linsert(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LINSERT, arg0, arg1, arg2, arg3);
  }
  /**
   * Redis command <a href="https://redis.io/commands/llen">llen</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI llen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LLEN, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/llen">llen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> llen(String arg0) {
    return send(Command.LLEN, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/lolwut">lolwut</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lolwut(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LOLWUT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lolwut">lolwut</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lolwut(List<String> args) {
    return send(Command.LOLWUT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/lpop">lpop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lpop(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPOP, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpop">lpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpop(String arg0) {
    return send(Command.LPOP, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/lpush">lpush</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lpush(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPUSH, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpush">lpush</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpush(List<String> args) {
    return send(Command.LPUSH, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/lpushx">lpushx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lpushx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LPUSHX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpushx">lpushx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpushx(List<String> args) {
    return send(Command.LPUSHX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/lrange">lrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrange">lrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lrange(String arg0, String arg1, String arg2) {
    return send(Command.LRANGE, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/lrem">lrem</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lrem(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LREM, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrem">lrem</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lrem(String arg0, String arg1, String arg2) {
    return send(Command.LREM, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/lset">lset</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI lset(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LSET, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/lset">lset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lset(String arg0, String arg1, String arg2) {
    return send(Command.LSET, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/ltrim">ltrim</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI ltrim(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.LTRIM, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/ltrim">ltrim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ltrim(String arg0, String arg1, String arg2) {
    return send(Command.LTRIM, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/memory">memory</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI memory(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MEMORY, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/memory">memory</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> memory(List<String> args) {
    return send(Command.MEMORY, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/mget">mget</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI mget(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MGET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/mget">mget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> mget(List<String> args) {
    return send(Command.MGET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/migrate">migrate</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI migrate(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MIGRATE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/migrate">migrate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> migrate(List<String> args) {
    return send(Command.MIGRATE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/module">module</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI module(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MODULE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/module">module</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> module(List<String> args) {
    return send(Command.MODULE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/monitor">monitor</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI monitor(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MONITOR).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/monitor">monitor</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> monitor() {
    return send(Command.MONITOR);
  }
  /**
   * Redis command <a href="https://redis.io/commands/move">move</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI move(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MOVE, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/move">move</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> move(String arg0, String arg1) {
    return send(Command.MOVE, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/mset">mset</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI mset(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MSET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/mset">mset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> mset(List<String> args) {
    return send(Command.MSET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/msetnx">msetnx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI msetnx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MSETNX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/msetnx">msetnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> msetnx(List<String> args) {
    return send(Command.MSETNX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/multi">multi</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI multi(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.MULTI).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/multi">multi</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> multi() {
    return send(Command.MULTI);
  }
  /**
   * Redis command <a href="https://redis.io/commands/object">object</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI object(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.OBJECT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/object">object</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> object(List<String> args) {
    return send(Command.OBJECT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/persist">persist</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI persist(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PERSIST, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/persist">persist</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> persist(String arg0) {
    return send(Command.PERSIST, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/pexpire">pexpire</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pexpire(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PEXPIRE, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpire">pexpire</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpire(String arg0, String arg1) {
    return send(Command.PEXPIRE, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/pexpireat">pexpireat</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pexpireat(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PEXPIREAT, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpireat">pexpireat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpireat(String arg0, String arg1) {
    return send(Command.PEXPIREAT, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/pfadd">pfadd</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pfadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFADD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfadd">pfadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfadd(List<String> args) {
    return send(Command.PFADD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/pfcount">pfcount</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pfcount(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFCOUNT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfcount">pfcount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfcount(List<String> args) {
    return send(Command.PFCOUNT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/pfdebug">pfdebug</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pfdebug(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFDEBUG, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfdebug">pfdebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfdebug(List<String> args) {
    return send(Command.PFDEBUG, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/pfmerge">pfmerge</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pfmerge(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFMERGE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfmerge">pfmerge</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfmerge(List<String> args) {
    return send(Command.PFMERGE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/pfselftest">pfselftest</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pfselftest(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PFSELFTEST).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfselftest">pfselftest</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfselftest() {
    return send(Command.PFSELFTEST);
  }
  /**
   * Redis command <a href="https://redis.io/commands/ping">ping</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI ping(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PING, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/ping">ping</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ping(List<String> args) {
    return send(Command.PING, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/post">post</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI post(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.POST, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/post">post</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> post(List<String> args) {
    return send(Command.POST, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/psetex">psetex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI psetex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSETEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/psetex">psetex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psetex(String arg0, String arg1, String arg2) {
    return send(Command.PSETEX, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/psubscribe">psubscribe</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI psubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSUBSCRIBE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/psubscribe">psubscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psubscribe(List<String> args) {
    return send(Command.PSUBSCRIBE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/psync">psync</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI psync(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PSYNC, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/psync">psync</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psync(String arg0, String arg1) {
    return send(Command.PSYNC, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/pttl">pttl</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pttl(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PTTL, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pttl">pttl</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pttl(String arg0) {
    return send(Command.PTTL, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/publish">publish</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI publish(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUBLISH, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/publish">publish</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> publish(String arg0, String arg1) {
    return send(Command.PUBLISH, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/pubsub">pubsub</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI pubsub(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUBSUB, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/pubsub">pubsub</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pubsub(List<String> args) {
    return send(Command.PUBSUB, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/punsubscribe">punsubscribe</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI punsubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.PUNSUBSCRIBE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/punsubscribe">punsubscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> punsubscribe(List<String> args) {
    return send(Command.PUNSUBSCRIBE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/randomkey">randomkey</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI randomkey(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RANDOMKEY).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/randomkey">randomkey</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> randomkey() {
    return send(Command.RANDOMKEY);
  }
  /**
   * Redis command <a href="https://redis.io/commands/readonly">readonly</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI readonly(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.READONLY).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/readonly">readonly</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> readonly() {
    return send(Command.READONLY);
  }
  /**
   * Redis command <a href="https://redis.io/commands/readwrite">readwrite</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI readwrite(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.READWRITE).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/readwrite">readwrite</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> readwrite() {
    return send(Command.READWRITE);
  }
  /**
   * Redis command <a href="https://redis.io/commands/rename">rename</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI rename(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RENAME, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/rename">rename</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rename(String arg0, String arg1) {
    return send(Command.RENAME, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/renamenx">renamenx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI renamenx(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RENAMENX, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/renamenx">renamenx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> renamenx(String arg0, String arg1) {
    return send(Command.RENAMENX, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/replconf">replconf</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI replconf(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.REPLCONF, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/replconf">replconf</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> replconf(List<String> args) {
    return send(Command.REPLCONF, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/replicaof">replicaof</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI replicaof(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.REPLICAOF, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/replicaof">replicaof</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> replicaof(String arg0, String arg1) {
    return send(Command.REPLICAOF, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/restore">restore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI restore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RESTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore">restore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> restore(List<String> args) {
    return send(Command.RESTORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/restore-asking">restoreAsking</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI restoreAsking(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RESTORE_ASKING, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore-asking">restoreAsking</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> restoreAsking(List<String> args) {
    return send(Command.RESTORE_ASKING, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/role">role</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI role(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ROLE).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/role">role</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> role() {
    return send(Command.ROLE);
  }
  /**
   * Redis command <a href="https://redis.io/commands/rpop">rpop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI rpop(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPOP, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpop">rpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpop(String arg0) {
    return send(Command.RPOP, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/rpoplpush">rpoplpush</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI rpoplpush(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPOPLPUSH, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpoplpush">rpoplpush</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpoplpush(String arg0, String arg1) {
    return send(Command.RPOPLPUSH, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/rpush">rpush</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI rpush(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPUSH, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpush">rpush</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpush(List<String> args) {
    return send(Command.RPUSH, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/rpushx">rpushx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI rpushx(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.RPUSHX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpushx">rpushx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpushx(List<String> args) {
    return send(Command.RPUSHX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sadd">sadd</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SADD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sadd">sadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sadd(List<String> args) {
    return send(Command.SADD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/save">save</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI save(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SAVE).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/save">save</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> save() {
    return send(Command.SAVE);
  }
  /**
   * Redis command <a href="https://redis.io/commands/scan">scan</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI scan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCAN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/scan">scan</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> scan(List<String> args) {
    return send(Command.SCAN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/scard">scard</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI scard(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCARD, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/scard">scard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> scard(String arg0) {
    return send(Command.SCARD, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/script">script</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI script(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SCRIPT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/script">script</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> script(List<String> args) {
    return send(Command.SCRIPT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sdiff">sdiff</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sdiff(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SDIFF, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiff">sdiff</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sdiff(List<String> args) {
    return send(Command.SDIFF, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sdiffstore">sdiffstore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sdiffstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SDIFFSTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiffstore">sdiffstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sdiffstore(List<String> args) {
    return send(Command.SDIFFSTORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/select">select</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI select(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SELECT, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/select">select</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> select(String arg0) {
    return send(Command.SELECT, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/set">set</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI set(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SET, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/set">set</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> set(List<String> args) {
    return send(Command.SET, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/setbit">setbit</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI setbit(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETBIT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/setbit">setbit</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setbit(String arg0, String arg1, String arg2) {
    return send(Command.SETBIT, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/setex">setex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI setex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/setex">setex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setex(String arg0, String arg1, String arg2) {
    return send(Command.SETEX, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/setnx">setnx</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI setnx(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETNX, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/setnx">setnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setnx(String arg0, String arg1) {
    return send(Command.SETNX, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/setrange">setrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI setrange(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SETRANGE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/setrange">setrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setrange(String arg0, String arg1, String arg2) {
    return send(Command.SETRANGE, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/shutdown">shutdown</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI shutdown(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SHUTDOWN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/shutdown">shutdown</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> shutdown(List<String> args) {
    return send(Command.SHUTDOWN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sinter">sinter</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sinter(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SINTER, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinter">sinter</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sinter(List<String> args) {
    return send(Command.SINTER, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sinterstore">sinterstore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sinterstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SINTERSTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinterstore">sinterstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sinterstore(List<String> args) {
    return send(Command.SINTERSTORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sismember">sismember</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sismember(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SISMEMBER, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sismember">sismember</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sismember(String arg0, String arg1) {
    return send(Command.SISMEMBER, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/slaveof">slaveof</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI slaveof(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SLAVEOF, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/slaveof">slaveof</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> slaveof(String arg0, String arg1) {
    return send(Command.SLAVEOF, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/slowlog">slowlog</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI slowlog(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SLOWLOG, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/slowlog">slowlog</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> slowlog(List<String> args) {
    return send(Command.SLOWLOG, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/smembers">smembers</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI smembers(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SMEMBERS, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/smembers">smembers</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smembers(String arg0) {
    return send(Command.SMEMBERS, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/smove">smove</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI smove(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SMOVE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/smove">smove</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smove(String arg0, String arg1, String arg2) {
    return send(Command.SMOVE, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/sort">sort</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sort(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SORT, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort">sort</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sort(List<String> args) {
    return send(Command.SORT, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/spop">spop</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI spop(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SPOP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/spop">spop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> spop(List<String> args) {
    return send(Command.SPOP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/srandmember">srandmember</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI srandmember(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SRANDMEMBER, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/srandmember">srandmember</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> srandmember(List<String> args) {
    return send(Command.SRANDMEMBER, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/srem">srem</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI srem(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SREM, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/srem">srem</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> srem(List<String> args) {
    return send(Command.SREM, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sscan">sscan</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SSCAN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sscan">sscan</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sscan(List<String> args) {
    return send(Command.SSCAN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/strlen">strlen</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI strlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.STRLEN, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/strlen">strlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> strlen(String arg0) {
    return send(Command.STRLEN, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/subscribe">subscribe</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI subscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUBSCRIBE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/subscribe">subscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> subscribe(List<String> args) {
    return send(Command.SUBSCRIBE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/substr">substr</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI substr(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUBSTR, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/substr">substr</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> substr(String arg0, String arg1, String arg2) {
    return send(Command.SUBSTR, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/sunion">sunion</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sunion(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUNION, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunion">sunion</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunion(List<String> args) {
    return send(Command.SUNION, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/sunionstore">sunionstore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sunionstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SUNIONSTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunionstore">sunionstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunionstore(List<String> args) {
    return send(Command.SUNIONSTORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/swapdb">swapdb</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI swapdb(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SWAPDB, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/swapdb">swapdb</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> swapdb(String arg0, String arg1) {
    return send(Command.SWAPDB, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/sync">sync</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI sync(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.SYNC).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/sync">sync</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sync() {
    return send(Command.SYNC);
  }
  /**
   * Redis command <a href="https://redis.io/commands/time">time</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI time(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TIME).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/time">time</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> time() {
    return send(Command.TIME);
  }
  /**
   * Redis command <a href="https://redis.io/commands/touch">touch</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI touch(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TOUCH, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/touch">touch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> touch(List<String> args) {
    return send(Command.TOUCH, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/ttl">ttl</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI ttl(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TTL, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/ttl">ttl</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ttl(String arg0) {
    return send(Command.TTL, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/type">type</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI type(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.TYPE, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/type">type</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> type(String arg0) {
    return send(Command.TYPE, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/unlink">unlink</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI unlink(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNLINK, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/unlink">unlink</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unlink(List<String> args) {
    return send(Command.UNLINK, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/unsubscribe">unsubscribe</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI unsubscribe(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNSUBSCRIBE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/unsubscribe">unsubscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unsubscribe(List<String> args) {
    return send(Command.UNSUBSCRIBE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/unwatch">unwatch</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI unwatch(Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.UNWATCH).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/unwatch">unwatch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unwatch() {
    return send(Command.UNWATCH);
  }
  /**
   * Redis command <a href="https://redis.io/commands/wait">wait</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI wait(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.WAIT, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/wait">wait</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> wait(String arg0, String arg1) {
    return send(Command.WAIT, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/watch">watch</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI watch(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.WATCH, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/watch">watch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> watch(List<String> args) {
    return send(Command.WATCH, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xack">xack</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xack(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XACK, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xack">xack</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xack(List<String> args) {
    return send(Command.XACK, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xadd">xadd</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XADD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xadd">xadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xadd(List<String> args) {
    return send(Command.XADD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xclaim">xclaim</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xclaim(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XCLAIM, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xclaim">xclaim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xclaim(List<String> args) {
    return send(Command.XCLAIM, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xdel">xdel</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xdel(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XDEL, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xdel">xdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xdel(List<String> args) {
    return send(Command.XDEL, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xgroup">xgroup</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xgroup(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XGROUP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xgroup">xgroup</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xgroup(List<String> args) {
    return send(Command.XGROUP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xinfo">xinfo</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xinfo(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XINFO, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xinfo">xinfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xinfo(List<String> args) {
    return send(Command.XINFO, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xlen">xlen</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xlen(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XLEN, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xlen">xlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xlen(String arg0) {
    return send(Command.XLEN, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/xpending">xpending</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xpending(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XPENDING, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xpending">xpending</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xpending(List<String> args) {
    return send(Command.XPENDING, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xrange">xrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XRANGE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrange">xrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xrange(List<String> args) {
    return send(Command.XRANGE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xread">xread</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xread(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREAD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xread">xread</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xread(List<String> args) {
    return send(Command.XREAD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xreadgroup">xreadgroup</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xreadgroup(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREADGROUP, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xreadgroup">xreadgroup</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xreadgroup(List<String> args) {
    return send(Command.XREADGROUP, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xrevrange">xrevrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xrevrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XREVRANGE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrevrange">xrevrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xrevrange(List<String> args) {
    return send(Command.XREVRANGE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/xsetid">xsetid</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xsetid(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XSETID, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xsetid">xsetid</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xsetid(String arg0, String arg1) {
    return send(Command.XSETID, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/xtrim">xtrim</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI xtrim(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.XTRIM, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/xtrim">xtrim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xtrim(List<String> args) {
    return send(Command.XTRIM, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zadd">zadd</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zadd(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZADD, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zadd">zadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zadd(List<String> args) {
    return send(Command.ZADD, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zcard">zcard</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zcard(String arg0, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZCARD, arg0).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcard">zcard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zcard(String arg0) {
    return send(Command.ZCARD, arg0);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zcount">zcount</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zcount(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZCOUNT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcount">zcount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zcount(String arg0, String arg1, String arg2) {
    return send(Command.ZCOUNT, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zincrby">zincrby</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zincrby(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZINCRBY, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zincrby">zincrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zincrby(String arg0, String arg1, String arg2) {
    return send(Command.ZINCRBY, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zinterstore">zinterstore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zinterstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZINTERSTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinterstore">zinterstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zinterstore(List<String> args) {
    return send(Command.ZINTERSTORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zlexcount">zlexcount</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zlexcount(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZLEXCOUNT, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zlexcount">zlexcount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zlexcount(String arg0, String arg1, String arg2) {
    return send(Command.ZLEXCOUNT, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zpopmax">zpopmax</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zpopmax(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZPOPMAX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmax">zpopmax</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zpopmax(List<String> args) {
    return send(Command.ZPOPMAX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zpopmin">zpopmin</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zpopmin(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZPOPMIN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmin">zpopmin</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zpopmin(List<String> args) {
    return send(Command.ZPOPMIN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrange">zrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrange">zrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrange(List<String> args) {
    return send(Command.ZRANGE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrangebylex">zrangebylex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrangebylex(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGEBYLEX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebylex">zrangebylex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebylex(List<String> args) {
    return send(Command.ZRANGEBYLEX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrangebyscore">zrangebyscore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrangebyscore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANGEBYSCORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebyscore">zrangebyscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebyscore(List<String> args) {
    return send(Command.ZRANGEBYSCORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrank">zrank</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrank(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZRANK, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrank">zrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrank(String arg0, String arg1) {
    return send(Command.ZRANK, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrem">zrem</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrem(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREM, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrem">zrem</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrem(List<String> args) {
    return send(Command.ZREM, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zremrangebylex">zremrangebylex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zremrangebylex(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebylex">zremrangebylex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebylex(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyrank">zremrangebyrank</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zremrangebyrank(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyrank">zremrangebyrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebyrank(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyscore">zremrangebyscore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zremrangebyscore(String arg0, String arg1, String arg2, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyscore">zremrangebyscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebyscore(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrevrange">zrevrange</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrevrange(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrange">zrevrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrange(List<String> args) {
    return send(Command.ZREVRANGE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebylex">zrevrangebylex</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrevrangebylex(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGEBYLEX, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebylex">zrevrangebylex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrangebylex(List<String> args) {
    return send(Command.ZREVRANGEBYLEX, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebyscore">zrevrangebyscore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrevrangebyscore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebyscore">zrevrangebyscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrangebyscore(List<String> args) {
    return send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zrevrank">zrevrank</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zrevrank(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZREVRANK, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrank">zrevrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrank(String arg0, String arg1) {
    return send(Command.ZREVRANK, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zscan">zscan</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zscan(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZSCAN, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscan">zscan</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zscan(List<String> args) {
    return send(Command.ZSCAN, args.toArray(new String[0]));
  }
  /**
   * Redis command <a href="https://redis.io/commands/zscore">zscore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zscore(String arg0, String arg1, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZSCORE, arg0, arg1).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscore">zscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zscore(String arg0, String arg1) {
    return send(Command.ZSCORE, arg0, arg1);
  }
  /**
   * Redis command <a href="https://redis.io/commands/zunionstore">zunionstore</a>.
   * @return fluent self
   */
  @Fluent
  default RedisAPI zunionstore(List<String> args, Handler<AsyncResult<@Nullable Response>> handler) {
    send(Command.ZUNIONSTORE, args.toArray(new String[0])).setHandler(handler);
    return this;
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunionstore">zunionstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zunionstore(List<String> args) {
    return send(Command.ZUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Send untyped command to redis.
   *
   * @param cmd the command
   * @param args var args
   * @return Future response.
   */
  @GenIgnore
  Future<@Nullable Response> send(Command cmd, String... args);
}
