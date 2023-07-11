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
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.redis.client.impl.RedisAPIImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.vertx.codegen.annotations.GenIgnore.PERMITTED_TYPE;

/**
 * <b>Auto generated</b> Redis API client wrapper.
 * @version redis_version:7.0.9
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
   * Redis command <a href="https://redis.io/commands/FT.ADD">ftAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAdd(List<String> args) {
    return send(Command.FT_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.AGGREGATE">ftAggregate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAggregate(List<String> args) {
    return send(Command.FT_AGGREGATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASADD">ftAliasadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasadd(List<String> args) {
    return send(Command.FT_ALIASADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASDEL">ftAliasdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasdel(List<String> args) {
    return send(Command.FT_ALIASDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALIASUPDATE">ftAliasupdate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasupdate(List<String> args) {
    return send(Command.FT_ALIASUPDATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.ALTER">ftAlter</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAlter(List<String> args) {
    return send(Command.FT_ALTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CONFIG">ftConfig</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftConfig(List<String> args) {
    return send(Command.FT_CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CREATE">ftCreate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCreate(List<String> args) {
    return send(Command.FT_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.CURSOR">ftCursor</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCursor(List<String> args) {
    return send(Command.FT_CURSOR, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DEBUG">ftDebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDebug(List<String> args) {
    return send(Command.FT_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DEL">ftDel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDel(List<String> args) {
    return send(Command.FT_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTADD">ftDictadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictadd(List<String> args) {
    return send(Command.FT_DICTADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTDEL">ftDictdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictdel(List<String> args) {
    return send(Command.FT_DICTDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DICTDUMP">ftDictdump</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictdump(List<String> args) {
    return send(Command.FT_DICTDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DROP">ftDrop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDrop(List<String> args) {
    return send(Command.FT_DROP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.DROPINDEX">ftDropindex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropindex(List<String> args) {
    return send(Command.FT_DROPINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.EXPLAIN">ftExplain</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftExplain(List<String> args) {
    return send(Command.FT_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.EXPLAINCLI">ftExplaincli</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftExplaincli(List<String> args) {
    return send(Command.FT_EXPLAINCLI, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.GET">ftGet</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftGet(List<String> args) {
    return send(Command.FT_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.INFO">ftInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftInfo(List<String> args) {
    return send(Command.FT_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.MGET">ftMget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftMget(List<String> args) {
    return send(Command.FT_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.PROFILE">ftProfile</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftProfile(List<String> args) {
    return send(Command.FT_PROFILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SAFEADD">ftSafeadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSafeadd(List<String> args) {
    return send(Command.FT_SAFEADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SEARCH">ftSearch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSearch(List<String> args) {
    return send(Command.FT_SEARCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SPELLCHECK">ftSpellcheck</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSpellcheck(List<String> args) {
    return send(Command.FT_SPELLCHECK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGADD">ftSugadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugadd(List<String> args) {
    return send(Command.FT_SUGADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGDEL">ftSugdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugdel(List<String> args) {
    return send(Command.FT_SUGDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGGET">ftSugget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugget(List<String> args) {
    return send(Command.FT_SUGGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SUGLEN">ftSuglen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSuglen(List<String> args) {
    return send(Command.FT_SUGLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNADD">ftSynadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSynadd(List<String> args) {
    return send(Command.FT_SYNADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNDUMP">ftSyndump</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSyndump(List<String> args) {
    return send(Command.FT_SYNDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.SYNUPDATE">ftSynupdate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSynupdate(List<String> args) {
    return send(Command.FT_SYNUPDATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT.TAGVALS">ftTagvals</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftTagvals(List<String> args) {
    return send(Command.FT_TAGVALS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALIASADDIFNX">ftAliasaddifnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasaddifnx(List<String> args) {
    return send(Command.FT__ALIASADDIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALIASDELIFX">ftAliasdelifx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasdelifx(List<String> args) {
    return send(Command.FT__ALIASDELIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._ALTERIFNX">ftAlterifnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAlterifnx(List<String> args) {
    return send(Command.FT__ALTERIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._CREATEIFNX">ftCreateifnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCreateifnx(List<String> args) {
    return send(Command.FT__CREATEIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._DROPIFX">ftDropifx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropifx(List<String> args) {
    return send(Command.FT__DROPIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._DROPINDEXIFX">ftDropindexifx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropindexifx(List<String> args) {
    return send(Command.FT__DROPINDEXIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/FT._LIST">ftList</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftList(List<String> args) {
    return send(Command.FT__LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/acl">acl</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> acl(List<String> args) {
    return send(Command.ACL, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> asking() {
    return send(Command.ASKING);
  }

  /**
   * Redis command <a href="https://redis.io/commands/auth">auth</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> auth(List<String> args) {
    return send(Command.AUTH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.add">bfAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfAdd(List<String> args) {
    return send(Command.BF_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.card">bfCard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfCard(List<String> args) {
    return send(Command.BF_CARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.debug">bfDebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfDebug(List<String> args) {
    return send(Command.BF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.exists">bfExists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfExists(List<String> args) {
    return send(Command.BF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.info">bfInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfInfo(List<String> args) {
    return send(Command.BF_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.insert">bfInsert</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfInsert(List<String> args) {
    return send(Command.BF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.loadchunk">bfLoadchunk</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfLoadchunk(List<String> args) {
    return send(Command.BF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.madd">bfMadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfMadd(List<String> args) {
    return send(Command.BF_MADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.mexists">bfMexists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfMexists(List<String> args) {
    return send(Command.BF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.reserve">bfReserve</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfReserve(List<String> args) {
    return send(Command.BF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.scandump">bfScandump</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfScandump(List<String> args) {
    return send(Command.BF_SCANDUMP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> bgsave(List<String> args) {
    return send(Command.BGSAVE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> bitfield(List<String> args) {
    return send(Command.BITFIELD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield_ro">bitfieldRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitfieldRo(List<String> args) {
    return send(Command.BITFIELD_RO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> bitpos(List<String> args) {
    return send(Command.BITPOS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmove">blmove</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blmove(String arg0, String arg1, String arg2, String arg3, String arg4) {
    return send(Command.BLMOVE, arg0, arg1, arg2, arg3, arg4);
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmpop">blmpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blmpop(List<String> args) {
    return send(Command.BLMPOP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> brpop(List<String> args) {
    return send(Command.BRPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpoplpush">brpoplpush</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> brpoplpush(String arg0, String arg1, String arg2) {
    return send(Command.BRPOPLPUSH, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzmpop">bzmpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzmpop(List<String> args) {
    return send(Command.BZMPOP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> bzpopmin(List<String> args) {
    return send(Command.BZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.add">cfAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfAdd(List<String> args) {
    return send(Command.CF_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.addnx">cfAddnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfAddnx(List<String> args) {
    return send(Command.CF_ADDNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.compact">cfCompact</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfCompact(List<String> args) {
    return send(Command.CF_COMPACT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.count">cfCount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfCount(List<String> args) {
    return send(Command.CF_COUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.debug">cfDebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfDebug(List<String> args) {
    return send(Command.CF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.del">cfDel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfDel(List<String> args) {
    return send(Command.CF_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.exists">cfExists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfExists(List<String> args) {
    return send(Command.CF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.info">cfInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInfo(List<String> args) {
    return send(Command.CF_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insert">cfInsert</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInsert(List<String> args) {
    return send(Command.CF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insertnx">cfInsertnx</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInsertnx(List<String> args) {
    return send(Command.CF_INSERTNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.loadchunk">cfLoadchunk</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfLoadchunk(List<String> args) {
    return send(Command.CF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.mexists">cfMexists</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfMexists(List<String> args) {
    return send(Command.CF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.reserve">cfReserve</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfReserve(List<String> args) {
    return send(Command.CF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.scandump">cfScandump</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfScandump(List<String> args) {
    return send(Command.CF_SCANDUMP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> cluster(List<String> args) {
    return send(Command.CLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.incrby">cmsIncrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsIncrby(List<String> args) {
    return send(Command.CMS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.info">cmsInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInfo(List<String> args) {
    return send(Command.CMS_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbydim">cmsInitbydim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInitbydim(List<String> args) {
    return send(Command.CMS_INITBYDIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbyprob">cmsInitbyprob</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInitbyprob(List<String> args) {
    return send(Command.CMS_INITBYPROB, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.merge">cmsMerge</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsMerge(List<String> args) {
    return send(Command.CMS_MERGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.query">cmsQuery</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsQuery(List<String> args) {
    return send(Command.CMS_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/command">command</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> command(List<String> args) {
    return send(Command.COMMAND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/config">config</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> config(List<String> args) {
    return send(Command.CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/copy">copy</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> copy(List<String> args) {
    return send(Command.COPY, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> debug(List<String> args) {
    return send(Command.DEBUG, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> decrby(String arg0, String arg1) {
    return send(Command.DECRBY, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> discard() {
    return send(Command.DISCARD);
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
   * @return Future response.
   */
  default Future<@Nullable Response> echo(String arg0) {
    return send(Command.ECHO, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval">eval</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> eval(List<String> args) {
    return send(Command.EVAL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval_ro">evalRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalRo(List<String> args) {
    return send(Command.EVAL_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha">evalsha</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalsha(List<String> args) {
    return send(Command.EVALSHA, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha_ro">evalshaRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalshaRo(List<String> args) {
    return send(Command.EVALSHA_RO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> exists(List<String> args) {
    return send(Command.EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expire">expire</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expire(List<String> args) {
    return send(Command.EXPIRE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expireat">expireat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expireat(List<String> args) {
    return send(Command.EXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expiretime">expiretime</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expiretime(String arg0) {
    return send(Command.EXPIRETIME, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/failover">failover</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> failover(List<String> args) {
    return send(Command.FAILOVER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall">fcall</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> fcall(List<String> args) {
    return send(Command.FCALL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall_ro">fcallRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> fcallRo(List<String> args) {
    return send(Command.FCALL_RO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> flushdb(List<String> args) {
    return send(Command.FLUSHDB, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/function">function</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> function(List<String> args) {
    return send(Command.FUNCTION, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> geodist(List<String> args) {
    return send(Command.GEODIST, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> geopos(List<String> args) {
    return send(Command.GEOPOS, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusRo(List<String> args) {
    return send(Command.GEORADIUS_RO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusbymemberRo(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearch">geosearch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geosearch(List<String> args) {
    return send(Command.GEOSEARCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearchstore">geosearchstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geosearchstore(List<String> args) {
    return send(Command.GEOSEARCHSTORE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> getbit(String arg0, String arg1) {
    return send(Command.GETBIT, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getdel">getdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getdel(String arg0) {
    return send(Command.GETDEL, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getex">getex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getex(List<String> args) {
    return send(Command.GETEX, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> getset(String arg0, String arg1) {
    return send(Command.GETSET, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.BULK">graphBulk</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphBulk(List<String> args) {
    return send(Command.GRAPH_BULK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.CONFIG">graphConfig</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphConfig(List<String> args) {
    return send(Command.GRAPH_CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.DEBUG">graphDebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphDebug(List<String> args) {
    return send(Command.GRAPH_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.DELETE">graphDelete</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphDelete(List<String> args) {
    return send(Command.GRAPH_DELETE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.EXPLAIN">graphExplain</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphExplain(List<String> args) {
    return send(Command.GRAPH_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.LIST">graphList</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphList(List<String> args) {
    return send(Command.GRAPH_LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.PROFILE">graphProfile</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphProfile(List<String> args) {
    return send(Command.GRAPH_PROFILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.QUERY">graphQuery</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphQuery(List<String> args) {
    return send(Command.GRAPH_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.RO_QUERY">graphRoQuery</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphRoQuery(List<String> args) {
    return send(Command.GRAPH_RO_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.SLOWLOG">graphSlowlog</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphSlowlog(List<String> args) {
    return send(Command.GRAPH_SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hdel">hdel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hdel(List<String> args) {
    return send(Command.HDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hello">hello</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hello(List<String> args) {
    return send(Command.HELLO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> hget(String arg0, String arg1) {
    return send(Command.HGET, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> hincrby(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBY, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> hkeys(String arg0) {
    return send(Command.HKEYS, arg0);
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
   * @return Future response.
   */
  default Future<@Nullable Response> hmget(List<String> args) {
    return send(Command.HMGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmset">hmset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hmset(List<String> args) {
    return send(Command.HMSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hrandfield">hrandfield</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hrandfield(List<String> args) {
    return send(Command.HRANDFIELD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> hset(List<String> args) {
    return send(Command.HSET, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> hstrlen(String arg0, String arg1) {
    return send(Command.HSTRLEN, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> incr(String arg0) {
    return send(Command.INCR, arg0);
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
   * @return Future response.
   */
  default Future<@Nullable Response> incrbyfloat(String arg0, String arg1) {
    return send(Command.INCRBYFLOAT, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/info">info</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> info(List<String> args) {
    return send(Command.INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrappend">jsonArrappend</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrappend(List<String> args) {
    return send(Command.JSON_ARRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrindex">jsonArrindex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrindex(List<String> args) {
    return send(Command.JSON_ARRINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrinsert">jsonArrinsert</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrinsert(List<String> args) {
    return send(Command.JSON_ARRINSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrlen">jsonArrlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrlen(List<String> args) {
    return send(Command.JSON_ARRLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrpop">jsonArrpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrpop(List<String> args) {
    return send(Command.JSON_ARRPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrtrim">jsonArrtrim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrtrim(List<String> args) {
    return send(Command.JSON_ARRTRIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.clear">jsonClear</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonClear(List<String> args) {
    return send(Command.JSON_CLEAR, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.debug">jsonDebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonDebug(List<String> args) {
    return send(Command.JSON_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.del">jsonDel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonDel(List<String> args) {
    return send(Command.JSON_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.forget">jsonForget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonForget(List<String> args) {
    return send(Command.JSON_FORGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.get">jsonGet</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonGet(List<String> args) {
    return send(Command.JSON_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.mget">jsonMget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonMget(List<String> args) {
    return send(Command.JSON_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numincrby">jsonNumincrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNumincrby(List<String> args) {
    return send(Command.JSON_NUMINCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.nummultby">jsonNummultby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNummultby(List<String> args) {
    return send(Command.JSON_NUMMULTBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numpowby">jsonNumpowby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNumpowby(List<String> args) {
    return send(Command.JSON_NUMPOWBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objkeys">jsonObjkeys</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonObjkeys(List<String> args) {
    return send(Command.JSON_OBJKEYS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objlen">jsonObjlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonObjlen(List<String> args) {
    return send(Command.JSON_OBJLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.resp">jsonResp</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonResp(List<String> args) {
    return send(Command.JSON_RESP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.set">jsonSet</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonSet(List<String> args) {
    return send(Command.JSON_SET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strappend">jsonStrappend</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonStrappend(List<String> args) {
    return send(Command.JSON_STRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strlen">jsonStrlen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonStrlen(List<String> args) {
    return send(Command.JSON_STRLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.toggle">jsonToggle</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonToggle(List<String> args) {
    return send(Command.JSON_TOGGLE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.type">jsonType</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonType(List<String> args) {
    return send(Command.JSON_TYPE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> lastsave() {
    return send(Command.LASTSAVE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/latency">latency</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> latency(List<String> args) {
    return send(Command.LATENCY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lcs">lcs</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lcs(List<String> args) {
    return send(Command.LCS, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> linsert(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LINSERT, arg0, arg1, arg2, arg3);
  }

  /**
   * Redis command <a href="https://redis.io/commands/llen">llen</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> llen(String arg0) {
    return send(Command.LLEN, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmove">lmove</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lmove(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LMOVE, arg0, arg1, arg2, arg3);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmpop">lmpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lmpop(List<String> args) {
    return send(Command.LMPOP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> lpop(List<String> args) {
    return send(Command.LPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpos">lpos</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpos(List<String> args) {
    return send(Command.LPOS, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> lpushx(List<String> args) {
    return send(Command.LPUSHX, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> lrem(String arg0, String arg1, String arg2) {
    return send(Command.LREM, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> ltrim(String arg0, String arg1, String arg2) {
    return send(Command.LTRIM, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> mget(List<String> args) {
    return send(Command.MGET, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> module(List<String> args) {
    return send(Command.MODULE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> move(String arg0, String arg1) {
    return send(Command.MOVE, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> msetnx(List<String> args) {
    return send(Command.MSETNX, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> object(List<String> args) {
    return send(Command.OBJECT, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> pexpire(List<String> args) {
    return send(Command.PEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpireat">pexpireat</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpireat(List<String> args) {
    return send(Command.PEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpiretime">pexpiretime</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpiretime(String arg0) {
    return send(Command.PEXPIRETIME, arg0);
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
   * @return Future response.
   */
  default Future<@Nullable Response> pfcount(List<String> args) {
    return send(Command.PFCOUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfdebug">pfdebug</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfdebug(String arg0, String arg1) {
    return send(Command.PFDEBUG, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> pfselftest() {
    return send(Command.PFSELFTEST);
  }

  /**
   * Redis command <a href="https://redis.io/commands/ping">ping</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ping(List<String> args) {
    return send(Command.PING, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> psubscribe(List<String> args) {
    return send(Command.PSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psync">psync</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psync(List<String> args) {
    return send(Command.PSYNC, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> publish(String arg0, String arg1) {
    return send(Command.PUBLISH, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> punsubscribe(List<String> args) {
    return send(Command.PUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/quit">quit</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> quit(List<String> args) {
    return send(Command.QUIT, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> readonly() {
    return send(Command.READONLY);
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
   * @return Future response.
   */
  default Future<@Nullable Response> rename(String arg0, String arg1) {
    return send(Command.RENAME, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> replconf(List<String> args) {
    return send(Command.REPLCONF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/replicaof">replicaof</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> replicaof(String arg0, String arg1) {
    return send(Command.REPLICAOF, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/reset">reset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> reset() {
    return send(Command.RESET);
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
   * @return Future response.
   */
  default Future<@Nullable Response> restoreAsking(List<String> args) {
    return send(Command.RESTORE_ASKING, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> rpop(List<String> args) {
    return send(Command.RPOP, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> rpush(List<String> args) {
    return send(Command.RPUSH, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sadd(List<String> args) {
    return send(Command.SADD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> scan(List<String> args) {
    return send(Command.SCAN, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> script(List<String> args) {
    return send(Command.SCRIPT, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sdiffstore(List<String> args) {
    return send(Command.SDIFFSTORE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> set(List<String> args) {
    return send(Command.SET, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> setex(String arg0, String arg1, String arg2) {
    return send(Command.SETEX, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> setrange(String arg0, String arg1, String arg2) {
    return send(Command.SETRANGE, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> sinter(List<String> args) {
    return send(Command.SINTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sintercard">sintercard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sintercard(List<String> args) {
    return send(Command.SINTERCARD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sismember(String arg0, String arg1) {
    return send(Command.SISMEMBER, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> slowlog(List<String> args) {
    return send(Command.SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smembers">smembers</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smembers(String arg0) {
    return send(Command.SMEMBERS, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/smismember">smismember</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smismember(List<String> args) {
    return send(Command.SMISMEMBER, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sort(List<String> args) {
    return send(Command.SORT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort_ro">sortRo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sortRo(List<String> args) {
    return send(Command.SORT_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spop">spop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> spop(List<String> args) {
    return send(Command.SPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spublish">spublish</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> spublish(String arg0, String arg1) {
    return send(Command.SPUBLISH, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> srem(List<String> args) {
    return send(Command.SREM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sscan">sscan</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sscan(List<String> args) {
    return send(Command.SSCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ssubscribe">ssubscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ssubscribe(List<String> args) {
    return send(Command.SSUBSCRIBE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> subscribe(List<String> args) {
    return send(Command.SUBSCRIBE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sunion(List<String> args) {
    return send(Command.SUNION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunionstore">sunionstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunionstore(List<String> args) {
    return send(Command.SUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunsubscribe">sunsubscribe</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunsubscribe(List<String> args) {
    return send(Command.SUNSUBSCRIBE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> sync() {
    return send(Command.SYNC);
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.add">tdigestAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestAdd(List<String> args) {
    return send(Command.TDIGEST_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.byrank">tdigestByrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestByrank(List<String> args) {
    return send(Command.TDIGEST_BYRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.byrevrank">tdigestByrevrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestByrevrank(List<String> args) {
    return send(Command.TDIGEST_BYREVRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.cdf">tdigestCdf</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestCdf(List<String> args) {
    return send(Command.TDIGEST_CDF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.create">tdigestCreate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestCreate(List<String> args) {
    return send(Command.TDIGEST_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.info">tdigestInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestInfo(List<String> args) {
    return send(Command.TDIGEST_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.max">tdigestMax</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMax(List<String> args) {
    return send(Command.TDIGEST_MAX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.merge">tdigestMerge</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMerge(List<String> args) {
    return send(Command.TDIGEST_MERGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.min">tdigestMin</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMin(List<String> args) {
    return send(Command.TDIGEST_MIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.quantile">tdigestQuantile</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestQuantile(List<String> args) {
    return send(Command.TDIGEST_QUANTILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.rank">tdigestRank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestRank(List<String> args) {
    return send(Command.TDIGEST_RANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.reset">tdigestReset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestReset(List<String> args) {
    return send(Command.TDIGEST_RESET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.revrank">tdigestRevrank</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestRevrank(List<String> args) {
    return send(Command.TDIGEST_REVRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.trimmed_mean">tdigestTrimmedMean</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestTrimmedMean(List<String> args) {
    return send(Command.TDIGEST_TRIMMED_MEAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/time">time</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> time() {
    return send(Command.TIME);
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.CLUSTERSET">timeseriesClusterset</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesClusterset(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.CLUSTERSETFROMSHARD">timeseriesClustersetfromshard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesClustersetfromshard(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSETFROMSHARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.HELLO">timeseriesHello</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesHello(List<String> args) {
    return send(Command.TIMESERIES_HELLO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.INFOCLUSTER">timeseriesInfocluster</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesInfocluster(List<String> args) {
    return send(Command.TIMESERIES_INFOCLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.INNERCOMMUNICATION">timeseriesInnercommunication</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesInnercommunication(List<String> args) {
    return send(Command.TIMESERIES_INNERCOMMUNICATION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.NETWORKTEST">timeseriesNetworktest</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesNetworktest(List<String> args) {
    return send(Command.TIMESERIES_NETWORKTEST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.REFRESHCLUSTER">timeseriesRefreshcluster</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesRefreshcluster(List<String> args) {
    return send(Command.TIMESERIES_REFRESHCLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.add">topkAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkAdd(List<String> args) {
    return send(Command.TOPK_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.count">topkCount</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkCount(List<String> args) {
    return send(Command.TOPK_COUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.incrby">topkIncrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkIncrby(List<String> args) {
    return send(Command.TOPK_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.info">topkInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkInfo(List<String> args) {
    return send(Command.TOPK_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.list">topkList</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkList(List<String> args) {
    return send(Command.TOPK_LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.query">topkQuery</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkQuery(List<String> args) {
    return send(Command.TOPK_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.reserve">topkReserve</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkReserve(List<String> args) {
    return send(Command.TOPK_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/touch">touch</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> touch(List<String> args) {
    return send(Command.TOUCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.add">tsAdd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsAdd(List<String> args) {
    return send(Command.TS_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.alter">tsAlter</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsAlter(List<String> args) {
    return send(Command.TS_ALTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.create">tsCreate</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsCreate(List<String> args) {
    return send(Command.TS_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.createrule">tsCreaterule</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsCreaterule(List<String> args) {
    return send(Command.TS_CREATERULE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.decrby">tsDecrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDecrby(List<String> args) {
    return send(Command.TS_DECRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.del">tsDel</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDel(List<String> args) {
    return send(Command.TS_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.deleterule">tsDeleterule</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDeleterule(List<String> args) {
    return send(Command.TS_DELETERULE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.get">tsGet</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsGet(List<String> args) {
    return send(Command.TS_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.incrby">tsIncrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsIncrby(List<String> args) {
    return send(Command.TS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.info">tsInfo</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsInfo(List<String> args) {
    return send(Command.TS_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.madd">tsMadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMadd(List<String> args) {
    return send(Command.TS_MADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mget">tsMget</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMget(List<String> args) {
    return send(Command.TS_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrange">tsMrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMrange(List<String> args) {
    return send(Command.TS_MRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrevrange">tsMrevrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMrevrange(List<String> args) {
    return send(Command.TS_MREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.queryindex">tsQueryindex</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsQueryindex(List<String> args) {
    return send(Command.TS_QUERYINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.range">tsRange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsRange(List<String> args) {
    return send(Command.TS_RANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.revrange">tsRevrange</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsRevrange(List<String> args) {
    return send(Command.TS_REVRANGE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> type(String arg0) {
    return send(Command.TYPE, arg0);
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
   * @return Future response.
   */
  default Future<@Nullable Response> unsubscribe(List<String> args) {
    return send(Command.UNSUBSCRIBE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> wait(String arg0, String arg1) {
    return send(Command.WAIT, arg0, arg1);
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
   * @return Future response.
   */
  default Future<@Nullable Response> xack(List<String> args) {
    return send(Command.XACK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xadd">xadd</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xadd(List<String> args) {
    return send(Command.XADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xautoclaim">xautoclaim</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xautoclaim(List<String> args) {
    return send(Command.XAUTOCLAIM, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> xdel(List<String> args) {
    return send(Command.XDEL, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> xinfo(List<String> args) {
    return send(Command.XINFO, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> xpending(List<String> args) {
    return send(Command.XPENDING, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> xread(List<String> args) {
    return send(Command.XREAD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> xrevrange(List<String> args) {
    return send(Command.XREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xsetid">xsetid</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xsetid(List<String> args) {
    return send(Command.XSETID, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zadd(List<String> args) {
    return send(Command.ZADD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zcount(String arg0, String arg1, String arg2) {
    return send(Command.ZCOUNT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiff">zdiff</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zdiff(List<String> args) {
    return send(Command.ZDIFF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiffstore">zdiffstore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zdiffstore(List<String> args) {
    return send(Command.ZDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zincrby">zincrby</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zincrby(String arg0, String arg1, String arg2) {
    return send(Command.ZINCRBY, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinter">zinter</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zinter(List<String> args) {
    return send(Command.ZINTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zintercard">zintercard</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zintercard(List<String> args) {
    return send(Command.ZINTERCARD, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zlexcount(String arg0, String arg1, String arg2) {
    return send(Command.ZLEXCOUNT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmpop">zmpop</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zmpop(List<String> args) {
    return send(Command.ZMPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmscore">zmscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zmscore(List<String> args) {
    return send(Command.ZMSCORE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zpopmin(List<String> args) {
    return send(Command.ZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrandmember">zrandmember</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrandmember(List<String> args) {
    return send(Command.ZRANDMEMBER, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebylex(List<String> args) {
    return send(Command.ZRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebyscore">zrangebyscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebyscore(List<String> args) {
    return send(Command.ZRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangestore">zrangestore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangestore(List<String> args) {
    return send(Command.ZRANGESTORE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zrem(List<String> args) {
    return send(Command.ZREM, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebyrank(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2);
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
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrange(List<String> args) {
    return send(Command.ZREVRANGE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrangebyscore(List<String> args) {
    return send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0]));
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
   * @return Future response.
   */
  default Future<@Nullable Response> zscan(List<String> args) {
    return send(Command.ZSCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscore">zscore</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zscore(String arg0, String arg1) {
    return send(Command.ZSCORE, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunion">zunion</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zunion(List<String> args) {
    return send(Command.ZUNION, args.toArray(new String[0]));
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

  /**
   * Run redis command eval lua with arguments but will try evalsha first, if
   * evalsha failed with `NOSCRIPT` error, will retry with the script, so the
   * next try evalsha will success as script has already been cached.
   *
   * Example:
   * {@code
   * final static LuaScript luaScript = new LuaScript("return ARGV[1]", 0);
   * ... some codes ...
   * evalLua(luaScript, Arrays.asList("hello"));
   * }
   *
   * @param luaScript lua script with pre-computed sha hash
   * @return Future response.
   */
  @GenIgnore
  default Future<Response> evalLua(LuaScript luaScript, List<String> args) {
    // pass empty args if no args, no null check
    List<String> redisArgs = new ArrayList<>(args.size() + 1);
    redisArgs.add(luaScript.getSha());
    redisArgs.addAll(args);

    return evalsha(redisArgs)
        .compose(
            // directly use result when succeeded
            result -> Future.succeededFuture(result),
            t -> {
              // load script and try ONCE again when failed cause script missing
              if (t.getMessage().startsWith("NOSCRIPT")) {
                // directly eval script and then script will be cached in redis, so
                // evalhash will success next time
                redisArgs.set(0, luaScript.getLua());
                return eval(redisArgs);
              }

              // fail when failed cause not script missing
              return Future.failedFuture(t);
            });
  }

  /**
   * evalLua with no pre-computed sha.
   *
   * NOTE: prefer to define a static variable with type of LuaScript and use the
   * `evalLua(LuaScript, List)`, with this method, sha will always be computed.
   *
   * @param lua  string of lua script
   * @param keys list of keys, size of keys will be numkeys argument
   * @param args list of args
   * @return Future response.
   */
  @GenIgnore
  default Future<Response> evalLua(String lua, List<String> keys, List<String> args) {
    LuaScript luaScript = new LuaScript(lua);

    List<String> allArgs = new ArrayList<>();

    int numKeys;
    if (keys != null) {
      numKeys = keys.size();
    } else {
      numKeys = 0;
    }
    allArgs.add(String.valueOf(numKeys));

    if (numKeys != 0) {
      allArgs.addAll(keys);
    }

    if (args != null && args.size() != 0) {
      allArgs.addAll(args);
    }

    return evalLua(luaScript, allArgs);
  }

  /**
   * evalLua with no pre-computed sha.
   *
   * NOTE: prefer to define a static variable with type of LuaScript and use the
   * `evalLua(LuaScript, List)`, with this method, sha will always be computed.
   *
   * @param numkeys, keys, args. If args is empty, a numkeys = 0 will add by default
   * @return Future response.
   */
  @GenIgnore
  default Future<Response> evalLua(String lua, String... args) {
    if (args.length == 0) {
      return evalLua(new LuaScript(lua), Arrays.asList("0"));
    }
    return evalLua(new LuaScript(lua), Arrays.asList(args));
  }
}
