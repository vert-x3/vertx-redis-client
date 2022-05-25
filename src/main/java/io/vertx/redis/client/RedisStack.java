/*
 * Copyright 2022 Red Hat, Inc.
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
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.redis.client.impl.RedisStackImpl;

import static io.vertx.codegen.annotations.GenIgnore.PERMITTED_TYPE;
import static io.vertx.redis.client.Request.cmd;

/**
 * <b>Auto generated</b> Redis Stack API client wrapper.
 * @version 7.0.0
 */
@VertxGen
public interface RedisStack {

  @GenIgnore(PERMITTED_TYPE)
  static RedisStack api(Redis client) {
    return new RedisStackImpl(client);
  }

  @GenIgnore(PERMITTED_TYPE)
  static RedisStack api(RedisConnection connection) {
    return new RedisStackImpl(connection);
  }

  Future<Void> close();

  /**
   * Redis command <a href="https://redis.io/commands/FT.ADD">FT.ADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAdd(Object... args) {
    return send(cmd(Command.FT_ADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.AGGREGATE">FT.AGGREGATE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAggregate(Object... args) {
    return send(cmd(Command.FT_AGGREGATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASADD">FT.ALIASADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAliasadd(Object... args) {
    return send(cmd(Command.FT_ALIASADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASDEL">FT.ALIASDEL</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAliasdel(Object... args) {
    return send(cmd(Command.FT_ALIASDEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASUPDATE">FT.ALIASUPDATE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAliasupdate(Object... args) {
    return send(cmd(Command.FT_ALIASUPDATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALTER">FT.ALTER</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAlter(Object... args) {
    return send(cmd(Command.FT_ALTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CONFIG">FT.CONFIG</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftConfig(Object... args) {
    return send(cmd(Command.FT_CONFIG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CREATE">FT.CREATE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftCreate(Object... args) {
    return send(cmd(Command.FT_CREATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CURSOR">FT.CURSOR</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftCursor(Object... args) {
    return send(cmd(Command.FT_CURSOR, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DEBUG">FT.DEBUG</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDebug(Object... args) {
    return send(cmd(Command.FT_DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DEL">FT.DEL</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDel(Object... args) {
    return send(cmd(Command.FT_DEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTADD">FT.DICTADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDictadd(Object... args) {
    return send(cmd(Command.FT_DICTADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTDEL">FT.DICTDEL</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDictdel(Object... args) {
    return send(cmd(Command.FT_DICTDEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTDUMP">FT.DICTDUMP</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDictdump(Object... args) {
    return send(cmd(Command.FT_DICTDUMP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DROP">FT.DROP</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDrop(Object... args) {
    return send(cmd(Command.FT_DROP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DROPINDEX">FT.DROPINDEX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDropindex(Object... args) {
    return send(cmd(Command.FT_DROPINDEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.EXPLAIN">FT.EXPLAIN</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftExplain(Object... args) {
    return send(cmd(Command.FT_EXPLAIN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.EXPLAINCLI">FT.EXPLAINCLI</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftExplaincli(Object... args) {
    return send(cmd(Command.FT_EXPLAINCLI, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.GET">FT.GET</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftGet(Object... args) {
    return send(cmd(Command.FT_GET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.INFO">FT.INFO</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftInfo(Object... args) {
    return send(cmd(Command.FT_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.MGET">FT.MGET</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftMget(Object... args) {
    return send(cmd(Command.FT_MGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.PROFILE">FT.PROFILE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftProfile(Object... args) {
    return send(cmd(Command.FT_PROFILE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SAFEADD">FT.SAFEADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSafeadd(Object... args) {
    return send(cmd(Command.FT_SAFEADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SEARCH">FT.SEARCH</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSearch(Object... args) {
    return send(cmd(Command.FT_SEARCH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SPELLCHECK">FT.SPELLCHECK</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSpellcheck(Object... args) {
    return send(cmd(Command.FT_SPELLCHECK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGADD">FT.SUGADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSugadd(Object... args) {
    return send(cmd(Command.FT_SUGADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGDEL">FT.SUGDEL</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSugdel(Object... args) {
    return send(cmd(Command.FT_SUGDEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGGET">FT.SUGGET</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSugget(Object... args) {
    return send(cmd(Command.FT_SUGGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGLEN">FT.SUGLEN</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSuglen(Object... args) {
    return send(cmd(Command.FT_SUGLEN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNADD">FT.SYNADD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSynadd(Object... args) {
    return send(cmd(Command.FT_SYNADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNDUMP">FT.SYNDUMP</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSyndump(Object... args) {
    return send(cmd(Command.FT_SYNDUMP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNUPDATE">FT.SYNUPDATE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftSynupdate(Object... args) {
    return send(cmd(Command.FT_SYNUPDATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.TAGVALS">FT.TAGVALS</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftTagvals(Object... args) {
    return send(cmd(Command.FT_TAGVALS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALIASADDIFNX">FT._ALIASADDIFNX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAliasaddifnx(Object... args) {
    return send(cmd(Command.FT__ALIASADDIFNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALIASDELIFX">FT._ALIASDELIFX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAliasdelifx(Object... args) {
    return send(cmd(Command.FT__ALIASDELIFX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALTERIFNX">FT._ALTERIFNX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftAlterifnx(Object... args) {
    return send(cmd(Command.FT__ALTERIFNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._CREATEIFNX">FT._CREATEIFNX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftCreateifnx(Object... args) {
    return send(cmd(Command.FT__CREATEIFNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._DROPIFX">FT._DROPIFX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDropifx(Object... args) {
    return send(cmd(Command.FT__DROPIFX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._DROPINDEXIFX">FT._DROPINDEXIFX</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftDropindexifx(Object... args) {
    return send(cmd(Command.FT__DROPINDEXIFX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._LIST">FT._LIST</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ftList(Object... args) {
    return send(cmd(Command.FT__LIST, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/acl">acl</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> acl(Object... args) {
    return send(cmd(Command.ACL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/append">append</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> append(Object arg0, Object arg1) {
    return send(cmd(Command.APPEND, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/asking">asking</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> asking() {
    return send(cmd(Command.ASKING));
  }

  /**
   * Redis command <a href="https://redis.io/commands/auth">auth</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> auth(Object... args) {
    return send(cmd(Command.AUTH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.add">bf.add</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfAdd(Object... args) {
    return send(cmd(Command.BF_ADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.debug">bf.debug</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfDebug(Object... args) {
    return send(cmd(Command.BF_DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.exists">bf.exists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfExists(Object... args) {
    return send(cmd(Command.BF_EXISTS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.info">bf.info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfInfo(Object... args) {
    return send(cmd(Command.BF_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.insert">bf.insert</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfInsert(Object... args) {
    return send(cmd(Command.BF_INSERT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.loadchunk">bf.loadchunk</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfLoadchunk(Object... args) {
    return send(cmd(Command.BF_LOADCHUNK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.madd">bf.madd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfMadd(Object... args) {
    return send(cmd(Command.BF_MADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.mexists">bf.mexists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfMexists(Object... args) {
    return send(cmd(Command.BF_MEXISTS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.reserve">bf.reserve</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfReserve(Object... args) {
    return send(cmd(Command.BF_RESERVE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.scandump">bf.scandump</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bfScandump(Object... args) {
    return send(cmd(Command.BF_SCANDUMP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgrewriteaof">bgrewriteaof</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bgrewriteaof() {
    return send(cmd(Command.BGREWRITEAOF));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgsave">bgsave</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bgsave(Object... args) {
    return send(cmd(Command.BGSAVE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitcount">bitcount</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bitcount(Object... args) {
    return send(cmd(Command.BITCOUNT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield">bitfield</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bitfield(Object... args) {
    return send(cmd(Command.BITFIELD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield_ro">bitfield_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bitfieldRo(Object... args) {
    return send(cmd(Command.BITFIELD_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitop">bitop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bitop(Object... args) {
    return send(cmd(Command.BITOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitpos">bitpos</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bitpos(Object... args) {
    return send(cmd(Command.BITPOS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmove">blmove</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> blmove(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
    return send(cmd(Command.BLMOVE, arg0, arg1, arg2, arg3, arg4));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmpop">blmpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> blmpop(Object... args) {
    return send(cmd(Command.BLMPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blpop">blpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> blpop(Object... args) {
    return send(cmd(Command.BLPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpop">brpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> brpop(Object... args) {
    return send(cmd(Command.BRPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpoplpush">brpoplpush</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> brpoplpush(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.BRPOPLPUSH, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzmpop">bzmpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bzmpop(Object... args) {
    return send(cmd(Command.BZMPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmax">bzpopmax</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bzpopmax(Object... args) {
    return send(cmd(Command.BZPOPMAX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmin">bzpopmin</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> bzpopmin(Object... args) {
    return send(cmd(Command.BZPOPMIN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.add">cf.add</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfAdd(Object... args) {
    return send(cmd(Command.CF_ADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.addnx">cf.addnx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfAddnx(Object... args) {
    return send(cmd(Command.CF_ADDNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.compact">cf.compact</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfCompact(Object... args) {
    return send(cmd(Command.CF_COMPACT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.count">cf.count</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfCount(Object... args) {
    return send(cmd(Command.CF_COUNT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.debug">cf.debug</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfDebug(Object... args) {
    return send(cmd(Command.CF_DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.del">cf.del</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfDel(Object... args) {
    return send(cmd(Command.CF_DEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.exists">cf.exists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfExists(Object... args) {
    return send(cmd(Command.CF_EXISTS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.info">cf.info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfInfo(Object... args) {
    return send(cmd(Command.CF_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insert">cf.insert</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfInsert(Object... args) {
    return send(cmd(Command.CF_INSERT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insertnx">cf.insertnx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfInsertnx(Object... args) {
    return send(cmd(Command.CF_INSERTNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.loadchunk">cf.loadchunk</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfLoadchunk(Object... args) {
    return send(cmd(Command.CF_LOADCHUNK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.mexists">cf.mexists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfMexists(Object... args) {
    return send(cmd(Command.CF_MEXISTS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.reserve">cf.reserve</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfReserve(Object... args) {
    return send(cmd(Command.CF_RESERVE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.scandump">cf.scandump</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cfScandump(Object... args) {
    return send(cmd(Command.CF_SCANDUMP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/client">client</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> client(Object... args) {
    return send(cmd(Command.CLIENT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cluster">cluster</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cluster(Object... args) {
    return send(cmd(Command.CLUSTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.incrby">cms.incrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsIncrby(Object... args) {
    return send(cmd(Command.CMS_INCRBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.info">cms.info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsInfo(Object... args) {
    return send(cmd(Command.CMS_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbydim">cms.initbydim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsInitbydim(Object... args) {
    return send(cmd(Command.CMS_INITBYDIM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbyprob">cms.initbyprob</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsInitbyprob(Object... args) {
    return send(cmd(Command.CMS_INITBYPROB, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.merge">cms.merge</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsMerge(Object... args) {
    return send(cmd(Command.CMS_MERGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.query">cms.query</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> cmsQuery(Object... args) {
    return send(cmd(Command.CMS_QUERY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/command">command</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> command(Object... args) {
    return send(cmd(Command.COMMAND, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/config">config</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> config(Object... args) {
    return send(cmd(Command.CONFIG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/copy">copy</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> copy(Object... args) {
    return send(cmd(Command.COPY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/dbsize">dbsize</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> dbsize() {
    return send(cmd(Command.DBSIZE));
  }

  /**
   * Redis command <a href="https://redis.io/commands/debug">debug</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> debug(Object... args) {
    return send(cmd(Command.DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/decr">decr</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> decr(Object arg0) {
    return send(cmd(Command.DECR, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/decrby">decrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> decrby(Object arg0, Object arg1) {
    return send(cmd(Command.DECRBY, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/del">del</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> del(Object... args) {
    return send(cmd(Command.DEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/discard">discard</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> discard() {
    return send(cmd(Command.DISCARD));
  }

  /**
   * Redis command <a href="https://redis.io/commands/dump">dump</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> dump(Object arg0) {
    return send(cmd(Command.DUMP, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/echo">echo</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> echo(Object arg0) {
    return send(cmd(Command.ECHO, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval">eval</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> eval(Object... args) {
    return send(cmd(Command.EVAL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval_ro">eval_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> evalRo(Object... args) {
    return send(cmd(Command.EVAL_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha">evalsha</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> evalsha(Object... args) {
    return send(cmd(Command.EVALSHA, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha_ro">evalsha_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> evalshaRo(Object... args) {
    return send(cmd(Command.EVALSHA_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/exec">exec</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> exec() {
    return send(cmd(Command.EXEC));
  }

  /**
   * Redis command <a href="https://redis.io/commands/exists">exists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> exists(Object... args) {
    return send(cmd(Command.EXISTS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expire">expire</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> expire(Object... args) {
    return send(cmd(Command.EXPIRE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expireat">expireat</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> expireat(Object... args) {
    return send(cmd(Command.EXPIREAT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expiretime">expiretime</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> expiretime(Object arg0) {
    return send(cmd(Command.EXPIRETIME, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/failover">failover</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> failover(Object... args) {
    return send(cmd(Command.FAILOVER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall">fcall</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> fcall(Object... args) {
    return send(cmd(Command.FCALL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall_ro">fcall_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> fcallRo(Object... args) {
    return send(cmd(Command.FCALL_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushall">flushall</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> flushall(Object... args) {
    return send(cmd(Command.FLUSHALL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushdb">flushdb</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> flushdb(Object... args) {
    return send(cmd(Command.FLUSHDB, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/function">function</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> function(Object... args) {
    return send(cmd(Command.FUNCTION, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geoadd">geoadd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geoadd(Object... args) {
    return send(cmd(Command.GEOADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geodist">geodist</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geodist(Object... args) {
    return send(cmd(Command.GEODIST, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geohash">geohash</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geohash(Object... args) {
    return send(cmd(Command.GEOHASH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geopos">geopos</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geopos(Object... args) {
    return send(cmd(Command.GEOPOS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius">georadius</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> georadius(Object... args) {
    return send(cmd(Command.GEORADIUS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius_ro">georadius_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> georadiusRo(Object... args) {
    return send(cmd(Command.GEORADIUS_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember">georadiusbymember</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> georadiusbymember(Object... args) {
    return send(cmd(Command.GEORADIUSBYMEMBER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember_ro">georadiusbymember_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> georadiusbymemberRo(Object... args) {
    return send(cmd(Command.GEORADIUSBYMEMBER_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearch">geosearch</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geosearch(Object... args) {
    return send(cmd(Command.GEOSEARCH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearchstore">geosearchstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> geosearchstore(Object... args) {
    return send(cmd(Command.GEOSEARCHSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/get">get</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> get(Object arg0) {
    return send(cmd(Command.GET, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getbit">getbit</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> getbit(Object arg0, Object arg1) {
    return send(cmd(Command.GETBIT, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getdel">getdel</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> getdel(Object arg0) {
    return send(cmd(Command.GETDEL, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getex">getex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> getex(Object... args) {
    return send(cmd(Command.GETEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getrange">getrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> getrange(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.GETRANGE, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getset">getset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> getset(Object arg0, Object arg1) {
    return send(cmd(Command.GETSET, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.BULK">graph.BULK</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphBulk(Object... args) {
    return send(cmd(Command.GRAPH_BULK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.CONFIG">graph.CONFIG</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphConfig(Object... args) {
    return send(cmd(Command.GRAPH_CONFIG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.DEBUG">graph.DEBUG</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphDebug(Object... args) {
    return send(cmd(Command.GRAPH_DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.DELETE">graph.DELETE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphDelete(Object... args) {
    return send(cmd(Command.GRAPH_DELETE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.EXPLAIN">graph.EXPLAIN</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphExplain(Object... args) {
    return send(cmd(Command.GRAPH_EXPLAIN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.LIST">graph.LIST</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphList(Object... args) {
    return send(cmd(Command.GRAPH_LIST, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.PROFILE">graph.PROFILE</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphProfile(Object... args) {
    return send(cmd(Command.GRAPH_PROFILE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.QUERY">graph.QUERY</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphQuery(Object... args) {
    return send(cmd(Command.GRAPH_QUERY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.RO_QUERY">graph.RO_QUERY</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphRoQuery(Object... args) {
    return send(cmd(Command.GRAPH_RO_QUERY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.SLOWLOG">graph.SLOWLOG</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> graphSlowlog(Object... args) {
    return send(cmd(Command.GRAPH_SLOWLOG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hdel">hdel</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hdel(Object... args) {
    return send(cmd(Command.HDEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hello">hello</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hello(Object... args) {
    return send(cmd(Command.HELLO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hexists">hexists</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hexists(Object arg0, Object arg1) {
    return send(cmd(Command.HEXISTS, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hget">hget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hget(Object arg0, Object arg1) {
    return send(cmd(Command.HGET, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hgetall">hgetall</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hgetall(Object arg0) {
    return send(cmd(Command.HGETALL, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrby">hincrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hincrby(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.HINCRBY, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrbyfloat">hincrbyfloat</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hincrbyfloat(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.HINCRBYFLOAT, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hkeys">hkeys</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hkeys(Object arg0) {
    return send(cmd(Command.HKEYS, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hlen">hlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hlen(Object arg0) {
    return send(cmd(Command.HLEN, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmget">hmget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hmget(Object... args) {
    return send(cmd(Command.HMGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmset">hmset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hmset(Object... args) {
    return send(cmd(Command.HMSET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hrandfield">hrandfield</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hrandfield(Object... args) {
    return send(cmd(Command.HRANDFIELD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hscan">hscan</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hscan(Object... args) {
    return send(cmd(Command.HSCAN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hset">hset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hset(Object... args) {
    return send(cmd(Command.HSET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hsetnx">hsetnx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hsetnx(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.HSETNX, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hstrlen">hstrlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hstrlen(Object arg0, Object arg1) {
    return send(cmd(Command.HSTRLEN, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hvals">hvals</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> hvals(Object arg0) {
    return send(cmd(Command.HVALS, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/incr">incr</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> incr(Object arg0) {
    return send(cmd(Command.INCR, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrby">incrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> incrby(Object arg0, Object arg1) {
    return send(cmd(Command.INCRBY, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrbyfloat">incrbyfloat</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> incrbyfloat(Object arg0, Object arg1) {
    return send(cmd(Command.INCRBYFLOAT, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/info">info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> info(Object... args) {
    return send(cmd(Command.INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrappend">json.arrappend</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrappend(Object... args) {
    return send(cmd(Command.JSON_ARRAPPEND, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrindex">json.arrindex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrindex(Object... args) {
    return send(cmd(Command.JSON_ARRINDEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrinsert">json.arrinsert</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrinsert(Object... args) {
    return send(cmd(Command.JSON_ARRINSERT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrlen">json.arrlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrlen(Object... args) {
    return send(cmd(Command.JSON_ARRLEN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrpop">json.arrpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrpop(Object... args) {
    return send(cmd(Command.JSON_ARRPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrtrim">json.arrtrim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonArrtrim(Object... args) {
    return send(cmd(Command.JSON_ARRTRIM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.clear">json.clear</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonClear(Object... args) {
    return send(cmd(Command.JSON_CLEAR, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.debug">json.debug</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonDebug(Object... args) {
    return send(cmd(Command.JSON_DEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.del">json.del</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonDel(Object... args) {
    return send(cmd(Command.JSON_DEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.forget">json.forget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonForget(Object... args) {
    return send(cmd(Command.JSON_FORGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.get">json.get</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonGet(Object... args) {
    return send(cmd(Command.JSON_GET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.mget">json.mget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonMget(Object... args) {
    return send(cmd(Command.JSON_MGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numincrby">json.numincrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonNumincrby(Object... args) {
    return send(cmd(Command.JSON_NUMINCRBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.nummultby">json.nummultby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonNummultby(Object... args) {
    return send(cmd(Command.JSON_NUMMULTBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numpowby">json.numpowby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonNumpowby(Object... args) {
    return send(cmd(Command.JSON_NUMPOWBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objkeys">json.objkeys</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonObjkeys(Object... args) {
    return send(cmd(Command.JSON_OBJKEYS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objlen">json.objlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonObjlen(Object... args) {
    return send(cmd(Command.JSON_OBJLEN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.resp">json.resp</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonResp(Object... args) {
    return send(cmd(Command.JSON_RESP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.set">json.set</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonSet(Object... args) {
    return send(cmd(Command.JSON_SET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strappend">json.strappend</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonStrappend(Object... args) {
    return send(cmd(Command.JSON_STRAPPEND, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strlen">json.strlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonStrlen(Object... args) {
    return send(cmd(Command.JSON_STRLEN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.toggle">json.toggle</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonToggle(Object... args) {
    return send(cmd(Command.JSON_TOGGLE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.type">json.type</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> jsonType(Object... args) {
    return send(cmd(Command.JSON_TYPE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/keys">keys</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> keys(Object arg0) {
    return send(cmd(Command.KEYS, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lastsave">lastsave</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lastsave() {
    return send(cmd(Command.LASTSAVE));
  }

  /**
   * Redis command <a href="https://redis.io/commands/latency">latency</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> latency(Object... args) {
    return send(cmd(Command.LATENCY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lcs">lcs</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lcs(Object... args) {
    return send(cmd(Command.LCS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lindex">lindex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lindex(Object arg0, Object arg1) {
    return send(cmd(Command.LINDEX, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/linsert">linsert</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> linsert(Object arg0, Object arg1, Object arg2, Object arg3) {
    return send(cmd(Command.LINSERT, arg0, arg1, arg2, arg3));
  }

  /**
   * Redis command <a href="https://redis.io/commands/llen">llen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> llen(Object arg0) {
    return send(cmd(Command.LLEN, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmove">lmove</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lmove(Object arg0, Object arg1, Object arg2, Object arg3) {
    return send(cmd(Command.LMOVE, arg0, arg1, arg2, arg3));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmpop">lmpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lmpop(Object... args) {
    return send(cmd(Command.LMPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lolwut">lolwut</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lolwut(Object... args) {
    return send(cmd(Command.LOLWUT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpop">lpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lpop(Object... args) {
    return send(cmd(Command.LPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpos">lpos</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lpos(Object... args) {
    return send(cmd(Command.LPOS, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpush">lpush</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lpush(Object... args) {
    return send(cmd(Command.LPUSH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpushx">lpushx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lpushx(Object... args) {
    return send(cmd(Command.LPUSHX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrange">lrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lrange(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.LRANGE, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrem">lrem</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lrem(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.LREM, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lset">lset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> lset(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.LSET, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ltrim">ltrim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ltrim(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.LTRIM, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/memory">memory</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> memory(Object... args) {
    return send(cmd(Command.MEMORY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/mget">mget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> mget(Object... args) {
    return send(cmd(Command.MGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/migrate">migrate</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> migrate(Object... args) {
    return send(cmd(Command.MIGRATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/module">module</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> module(Object... args) {
    return send(cmd(Command.MODULE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/monitor">monitor</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> monitor() {
    return send(cmd(Command.MONITOR));
  }

  /**
   * Redis command <a href="https://redis.io/commands/move">move</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> move(Object arg0, Object arg1) {
    return send(cmd(Command.MOVE, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/mset">mset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> mset(Object... args) {
    return send(cmd(Command.MSET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/msetnx">msetnx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> msetnx(Object... args) {
    return send(cmd(Command.MSETNX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/multi">multi</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> multi() {
    return send(cmd(Command.MULTI));
  }

  /**
   * Redis command <a href="https://redis.io/commands/object">object</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> object(Object... args) {
    return send(cmd(Command.OBJECT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/persist">persist</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> persist(Object arg0) {
    return send(cmd(Command.PERSIST, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpire">pexpire</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pexpire(Object... args) {
    return send(cmd(Command.PEXPIRE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpireat">pexpireat</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pexpireat(Object... args) {
    return send(cmd(Command.PEXPIREAT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpiretime">pexpiretime</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pexpiretime(Object arg0) {
    return send(cmd(Command.PEXPIRETIME, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfadd">pfadd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pfadd(Object... args) {
    return send(cmd(Command.PFADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfcount">pfcount</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pfcount(Object... args) {
    return send(cmd(Command.PFCOUNT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfdebug">pfdebug</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pfdebug(Object... args) {
    return send(cmd(Command.PFDEBUG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfmerge">pfmerge</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pfmerge(Object... args) {
    return send(cmd(Command.PFMERGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfselftest">pfselftest</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pfselftest() {
    return send(cmd(Command.PFSELFTEST));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ping">ping</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ping(Object... args) {
    return send(cmd(Command.PING, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psetex">psetex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> psetex(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.PSETEX, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psubscribe">psubscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> psubscribe(Object... args) {
    return send(cmd(Command.PSUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psync">psync</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> psync(Object... args) {
    return send(cmd(Command.PSYNC, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pttl">pttl</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pttl(Object arg0) {
    return send(cmd(Command.PTTL, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/publish">publish</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> publish(Object arg0, Object arg1) {
    return send(cmd(Command.PUBLISH, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pubsub">pubsub</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> pubsub(Object... args) {
    return send(cmd(Command.PUBSUB, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/punsubscribe">punsubscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> punsubscribe(Object... args) {
    return send(cmd(Command.PUNSUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/quit">quit</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> quit(Object... args) {
    return send(cmd(Command.QUIT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/randomkey">randomkey</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> randomkey() {
    return send(cmd(Command.RANDOMKEY));
  }

  /**
   * Redis command <a href="https://redis.io/commands/readonly">readonly</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> readonly() {
    return send(cmd(Command.READONLY));
  }

  /**
   * Redis command <a href="https://redis.io/commands/readwrite">readwrite</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> readwrite() {
    return send(cmd(Command.READWRITE));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rename">rename</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> rename(Object arg0, Object arg1) {
    return send(cmd(Command.RENAME, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/renamenx">renamenx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> renamenx(Object arg0, Object arg1) {
    return send(cmd(Command.RENAMENX, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/replconf">replconf</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> replconf(Object... args) {
    return send(cmd(Command.REPLCONF, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/replicaof">replicaof</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> replicaof(Object arg0, Object arg1) {
    return send(cmd(Command.REPLICAOF, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/reset">reset</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> reset() {
    return send(cmd(Command.RESET));
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore">restore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> restore(Object... args) {
    return send(cmd(Command.RESTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore-asking">restore-asking</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> restoreAsking(Object... args) {
    return send(cmd(Command.RESTORE_ASKING, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/role">role</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> role() {
    return send(cmd(Command.ROLE));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpop">rpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> rpop(Object... args) {
    return send(cmd(Command.RPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpoplpush">rpoplpush</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> rpoplpush(Object arg0, Object arg1) {
    return send(cmd(Command.RPOPLPUSH, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpush">rpush</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> rpush(Object... args) {
    return send(cmd(Command.RPUSH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpushx">rpushx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> rpushx(Object... args) {
    return send(cmd(Command.RPUSHX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sadd">sadd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sadd(Object... args) {
    return send(cmd(Command.SADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/save">save</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> save() {
    return send(cmd(Command.SAVE));
  }

  /**
   * Redis command <a href="https://redis.io/commands/scan">scan</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> scan(Object... args) {
    return send(cmd(Command.SCAN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/scard">scard</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> scard(Object arg0) {
    return send(cmd(Command.SCARD, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/script">script</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> script(Object... args) {
    return send(cmd(Command.SCRIPT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiff">sdiff</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sdiff(Object... args) {
    return send(cmd(Command.SDIFF, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiffstore">sdiffstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sdiffstore(Object... args) {
    return send(cmd(Command.SDIFFSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/select">select</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> select(Object arg0) {
    return send(cmd(Command.SELECT, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/set">set</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> set(Object... args) {
    return send(cmd(Command.SET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/setbit">setbit</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> setbit(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.SETBIT, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/setex">setex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> setex(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.SETEX, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/setnx">setnx</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> setnx(Object arg0, Object arg1) {
    return send(cmd(Command.SETNX, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/setrange">setrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> setrange(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.SETRANGE, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/shutdown">shutdown</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> shutdown(Object... args) {
    return send(cmd(Command.SHUTDOWN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinter">sinter</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sinter(Object... args) {
    return send(cmd(Command.SINTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sintercard">sintercard</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sintercard(Object... args) {
    return send(cmd(Command.SINTERCARD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinterstore">sinterstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sinterstore(Object... args) {
    return send(cmd(Command.SINTERSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sismember">sismember</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sismember(Object arg0, Object arg1) {
    return send(cmd(Command.SISMEMBER, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/slaveof">slaveof</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> slaveof(Object arg0, Object arg1) {
    return send(cmd(Command.SLAVEOF, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/slowlog">slowlog</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> slowlog(Object... args) {
    return send(cmd(Command.SLOWLOG, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smembers">smembers</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> smembers(Object arg0) {
    return send(cmd(Command.SMEMBERS, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smismember">smismember</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> smismember(Object... args) {
    return send(cmd(Command.SMISMEMBER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smove">smove</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> smove(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.SMOVE, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort">sort</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sort(Object... args) {
    return send(cmd(Command.SORT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort_ro">sort_ro</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sortRo(Object... args) {
    return send(cmd(Command.SORT_RO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spop">spop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> spop(Object... args) {
    return send(cmd(Command.SPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spublish">spublish</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> spublish(Object arg0, Object arg1) {
    return send(cmd(Command.SPUBLISH, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/srandmember">srandmember</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> srandmember(Object... args) {
    return send(cmd(Command.SRANDMEMBER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/srem">srem</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> srem(Object... args) {
    return send(cmd(Command.SREM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sscan">sscan</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sscan(Object... args) {
    return send(cmd(Command.SSCAN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ssubscribe">ssubscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ssubscribe(Object... args) {
    return send(cmd(Command.SSUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/strlen">strlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> strlen(Object arg0) {
    return send(cmd(Command.STRLEN, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/subscribe">subscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> subscribe(Object... args) {
    return send(cmd(Command.SUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/substr">substr</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> substr(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.SUBSTR, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunion">sunion</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sunion(Object... args) {
    return send(cmd(Command.SUNION, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunionstore">sunionstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sunionstore(Object... args) {
    return send(cmd(Command.SUNIONSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunsubscribe">sunsubscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sunsubscribe(Object... args) {
    return send(cmd(Command.SUNSUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/swapdb">swapdb</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> swapdb(Object arg0, Object arg1) {
    return send(cmd(Command.SWAPDB, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sync">sync</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> sync() {
    return send(cmd(Command.SYNC));
  }

  /**
   * Redis command <a href="https://redis.io/commands/time">time</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> time() {
    return send(cmd(Command.TIME));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.CLUSTERSET">timeseries.CLUSTERSET</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesClusterset(Object... args) {
    return send(cmd(Command.TIMESERIES_CLUSTERSET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.CLUSTERSETFROMSHARD">timeseries.CLUSTERSETFROMSHARD</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesClustersetfromshard(Object... args) {
    return send(cmd(Command.TIMESERIES_CLUSTERSETFROMSHARD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.HELLO">timeseries.HELLO</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesHello(Object... args) {
    return send(cmd(Command.TIMESERIES_HELLO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.INFOCLUSTER">timeseries.INFOCLUSTER</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesInfocluster(Object... args) {
    return send(cmd(Command.TIMESERIES_INFOCLUSTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.INNERCOMMUNICATION">timeseries.INNERCOMMUNICATION</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesInnercommunication(Object... args) {
    return send(cmd(Command.TIMESERIES_INNERCOMMUNICATION, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.NETWORKTEST">timeseries.NETWORKTEST</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesNetworktest(Object... args) {
    return send(cmd(Command.TIMESERIES_NETWORKTEST, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.REFRESHCLUSTER">timeseries.REFRESHCLUSTER</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> timeseriesRefreshcluster(Object... args) {
    return send(cmd(Command.TIMESERIES_REFRESHCLUSTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.add">topk.add</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkAdd(Object... args) {
    return send(cmd(Command.TOPK_ADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.count">topk.count</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkCount(Object... args) {
    return send(cmd(Command.TOPK_COUNT, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.incrby">topk.incrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkIncrby(Object... args) {
    return send(cmd(Command.TOPK_INCRBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.info">topk.info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkInfo(Object... args) {
    return send(cmd(Command.TOPK_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.list">topk.list</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkList(Object... args) {
    return send(cmd(Command.TOPK_LIST, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.query">topk.query</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkQuery(Object... args) {
    return send(cmd(Command.TOPK_QUERY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.reserve">topk.reserve</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> topkReserve(Object... args) {
    return send(cmd(Command.TOPK_RESERVE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/touch">touch</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> touch(Object... args) {
    return send(cmd(Command.TOUCH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.add">ts.add</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsAdd(Object... args) {
    return send(cmd(Command.TS_ADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.alter">ts.alter</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsAlter(Object... args) {
    return send(cmd(Command.TS_ALTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.create">ts.create</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsCreate(Object... args) {
    return send(cmd(Command.TS_CREATE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.createrule">ts.createrule</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsCreaterule(Object... args) {
    return send(cmd(Command.TS_CREATERULE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.decrby">ts.decrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsDecrby(Object... args) {
    return send(cmd(Command.TS_DECRBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.del">ts.del</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsDel(Object... args) {
    return send(cmd(Command.TS_DEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.deleterule">ts.deleterule</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsDeleterule(Object... args) {
    return send(cmd(Command.TS_DELETERULE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.get">ts.get</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsGet(Object... args) {
    return send(cmd(Command.TS_GET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.incrby">ts.incrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsIncrby(Object... args) {
    return send(cmd(Command.TS_INCRBY, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.info">ts.info</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsInfo(Object... args) {
    return send(cmd(Command.TS_INFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.madd">ts.madd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsMadd(Object... args) {
    return send(cmd(Command.TS_MADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mget">ts.mget</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsMget(Object... args) {
    return send(cmd(Command.TS_MGET, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrange">ts.mrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsMrange(Object... args) {
    return send(cmd(Command.TS_MRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrevrange">ts.mrevrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsMrevrange(Object... args) {
    return send(cmd(Command.TS_MREVRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.queryindex">ts.queryindex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsQueryindex(Object... args) {
    return send(cmd(Command.TS_QUERYINDEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.range">ts.range</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsRange(Object... args) {
    return send(cmd(Command.TS_RANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.revrange">ts.revrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> tsRevrange(Object... args) {
    return send(cmd(Command.TS_REVRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ttl">ttl</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> ttl(Object arg0) {
    return send(cmd(Command.TTL, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/type">type</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> type(Object arg0) {
    return send(cmd(Command.TYPE, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/unlink">unlink</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> unlink(Object... args) {
    return send(cmd(Command.UNLINK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/unsubscribe">unsubscribe</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> unsubscribe(Object... args) {
    return send(cmd(Command.UNSUBSCRIBE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/unwatch">unwatch</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> unwatch() {
    return send(cmd(Command.UNWATCH));
  }

  /**
   * Redis command <a href="https://redis.io/commands/wait">wait</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> wait(Object arg0, Object arg1) {
    return send(cmd(Command.WAIT, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/watch">watch</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> watch(Object... args) {
    return send(cmd(Command.WATCH, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xack">xack</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xack(Object... args) {
    return send(cmd(Command.XACK, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xadd">xadd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xadd(Object... args) {
    return send(cmd(Command.XADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xautoclaim">xautoclaim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xautoclaim(Object... args) {
    return send(cmd(Command.XAUTOCLAIM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xclaim">xclaim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xclaim(Object... args) {
    return send(cmd(Command.XCLAIM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xdel">xdel</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xdel(Object... args) {
    return send(cmd(Command.XDEL, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xgroup">xgroup</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xgroup(Object... args) {
    return send(cmd(Command.XGROUP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xinfo">xinfo</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xinfo(Object... args) {
    return send(cmd(Command.XINFO, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xlen">xlen</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xlen(Object arg0) {
    return send(cmd(Command.XLEN, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xpending">xpending</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xpending(Object... args) {
    return send(cmd(Command.XPENDING, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrange">xrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xrange(Object... args) {
    return send(cmd(Command.XRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xread">xread</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xread(Object... args) {
    return send(cmd(Command.XREAD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xreadgroup">xreadgroup</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xreadgroup(Object... args) {
    return send(cmd(Command.XREADGROUP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrevrange">xrevrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xrevrange(Object... args) {
    return send(cmd(Command.XREVRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xsetid">xsetid</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xsetid(Object... args) {
    return send(cmd(Command.XSETID, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xtrim">xtrim</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> xtrim(Object... args) {
    return send(cmd(Command.XTRIM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zadd">zadd</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zadd(Object... args) {
    return send(cmd(Command.ZADD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcard">zcard</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zcard(Object arg0) {
    return send(cmd(Command.ZCARD, arg0));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcount">zcount</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zcount(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZCOUNT, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiff">zdiff</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zdiff(Object... args) {
    return send(cmd(Command.ZDIFF, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiffstore">zdiffstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zdiffstore(Object... args) {
    return send(cmd(Command.ZDIFFSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zincrby">zincrby</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zincrby(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZINCRBY, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinter">zinter</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zinter(Object... args) {
    return send(cmd(Command.ZINTER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zintercard">zintercard</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zintercard(Object... args) {
    return send(cmd(Command.ZINTERCARD, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinterstore">zinterstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zinterstore(Object... args) {
    return send(cmd(Command.ZINTERSTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zlexcount">zlexcount</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zlexcount(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZLEXCOUNT, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmpop">zmpop</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zmpop(Object... args) {
    return send(cmd(Command.ZMPOP, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmscore">zmscore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zmscore(Object... args) {
    return send(cmd(Command.ZMSCORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmax">zpopmax</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zpopmax(Object... args) {
    return send(cmd(Command.ZPOPMAX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmin">zpopmin</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zpopmin(Object... args) {
    return send(cmd(Command.ZPOPMIN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrandmember">zrandmember</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrandmember(Object... args) {
    return send(cmd(Command.ZRANDMEMBER, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrange">zrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrange(Object... args) {
    return send(cmd(Command.ZRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebylex">zrangebylex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrangebylex(Object... args) {
    return send(cmd(Command.ZRANGEBYLEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebyscore">zrangebyscore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrangebyscore(Object... args) {
    return send(cmd(Command.ZRANGEBYSCORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangestore">zrangestore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrangestore(Object... args) {
    return send(cmd(Command.ZRANGESTORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrank">zrank</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrank(Object arg0, Object arg1) {
    return send(cmd(Command.ZRANK, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrem">zrem</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrem(Object... args) {
    return send(cmd(Command.ZREM, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebylex">zremrangebylex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zremrangebylex(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZREMRANGEBYLEX, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyrank">zremrangebyrank</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zremrangebyrank(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZREMRANGEBYRANK, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyscore">zremrangebyscore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zremrangebyscore(Object arg0, Object arg1, Object arg2) {
    return send(cmd(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrange">zrevrange</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrevrange(Object... args) {
    return send(cmd(Command.ZREVRANGE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebylex">zrevrangebylex</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrevrangebylex(Object... args) {
    return send(cmd(Command.ZREVRANGEBYLEX, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebyscore">zrevrangebyscore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrevrangebyscore(Object... args) {
    return send(cmd(Command.ZREVRANGEBYSCORE, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrank">zrevrank</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zrevrank(Object arg0, Object arg1) {
    return send(cmd(Command.ZREVRANK, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscan">zscan</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zscan(Object... args) {
    return send(cmd(Command.ZSCAN, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscore">zscore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zscore(Object arg0, Object arg1) {
    return send(cmd(Command.ZSCORE, arg0, arg1));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunion">zunion</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zunion(Object... args) {
    return send(cmd(Command.ZUNION, args));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunionstore">zunionstore</a>.
   * @return Future asynchronous result with {@link Response} or {@code null}.
   */
  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  default Future<@Nullable Response> zunionstore(Object... args) {
    return send(cmd(Command.ZUNIONSTORE, args));
  }

  /**
   * @param req the request to fulfill
   * @return Future response.
   */
  @GenIgnore
  Future<@Nullable Response> send(Request req);
}
