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

import java.util.List;

import static io.vertx.codegen.annotations.GenIgnore.PERMITTED_TYPE;

/**
 * <b>Auto generated</b> Redis API client wrapper.
 * @version redis_version:7.0.12
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
   * Redis command <a href="https://redis.io/commands/ft.add/">FT.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAdd(List<String> args) {
    return send(Command.FT_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.aggregate/">FT.AGGREGATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAggregate(List<String> args) {
    return send(Command.FT_AGGREGATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.aliasadd/">FT.ALIASADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasadd(List<String> args) {
    return send(Command.FT_ALIASADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.aliasdel/">FT.ALIASDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasdel(List<String> args) {
    return send(Command.FT_ALIASDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.aliasupdate/">FT.ALIASUPDATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasupdate(List<String> args) {
    return send(Command.FT_ALIASUPDATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.alter/">FT.ALTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAlter(List<String> args) {
    return send(Command.FT_ALTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.config/">FT.CONFIG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftConfig(List<String> args) {
    return send(Command.FT_CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.create/">FT.CREATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCreate(List<String> args) {
    return send(Command.FT_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.cursor/">FT.CURSOR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCursor(List<String> args) {
    return send(Command.FT_CURSOR, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.debug/">FT.DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDebug(List<String> args) {
    return send(Command.FT_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.del/">FT.DEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDel(List<String> args) {
    return send(Command.FT_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.dictadd/">FT.DICTADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictadd(List<String> args) {
    return send(Command.FT_DICTADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.dictdel/">FT.DICTDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictdel(List<String> args) {
    return send(Command.FT_DICTDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.dictdump/">FT.DICTDUMP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDictdump(List<String> args) {
    return send(Command.FT_DICTDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.drop/">FT.DROP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDrop(List<String> args) {
    return send(Command.FT_DROP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.dropindex/">FT.DROPINDEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropindex(List<String> args) {
    return send(Command.FT_DROPINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.explain/">FT.EXPLAIN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftExplain(List<String> args) {
    return send(Command.FT_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.explaincli/">FT.EXPLAINCLI</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftExplaincli(List<String> args) {
    return send(Command.FT_EXPLAINCLI, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.get/">FT.GET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftGet(List<String> args) {
    return send(Command.FT_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.info/">FT.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftInfo(List<String> args) {
    return send(Command.FT_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.mget/">FT.MGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftMget(List<String> args) {
    return send(Command.FT_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.profile/">FT.PROFILE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftProfile(List<String> args) {
    return send(Command.FT_PROFILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.safeadd/">FT.SAFEADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSafeadd(List<String> args) {
    return send(Command.FT_SAFEADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.search/">FT.SEARCH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSearch(List<String> args) {
    return send(Command.FT_SEARCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.spellcheck/">FT.SPELLCHECK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSpellcheck(List<String> args) {
    return send(Command.FT_SPELLCHECK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.sugadd/">FT.SUGADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugadd(List<String> args) {
    return send(Command.FT_SUGADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.sugdel/">FT.SUGDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugdel(List<String> args) {
    return send(Command.FT_SUGDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.sugget/">FT.SUGGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSugget(List<String> args) {
    return send(Command.FT_SUGGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.suglen/">FT.SUGLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSuglen(List<String> args) {
    return send(Command.FT_SUGLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.synadd/">FT.SYNADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSynadd(List<String> args) {
    return send(Command.FT_SYNADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.syndump/">FT.SYNDUMP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSyndump(List<String> args) {
    return send(Command.FT_SYNDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.synupdate/">FT.SYNUPDATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftSynupdate(List<String> args) {
    return send(Command.FT_SYNUPDATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft.tagvals/">FT.TAGVALS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftTagvals(List<String> args) {
    return send(Command.FT_TAGVALS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._aliasaddifnx/">FT._ALIASADDIFNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasaddifnx(List<String> args) {
    return send(Command.FT__ALIASADDIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._aliasdelifx/">FT._ALIASDELIFX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAliasdelifx(List<String> args) {
    return send(Command.FT__ALIASDELIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._alterifnx/">FT._ALTERIFNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftAlterifnx(List<String> args) {
    return send(Command.FT__ALTERIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._createifnx/">FT._CREATEIFNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftCreateifnx(List<String> args) {
    return send(Command.FT__CREATEIFNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._dropifx/">FT._DROPIFX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropifx(List<String> args) {
    return send(Command.FT__DROPIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._dropindexifx/">FT._DROPINDEXIFX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftDropindexifx(List<String> args) {
    return send(Command.FT__DROPINDEXIFX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ft._list/">FT._LIST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ftList(List<String> args) {
    return send(Command.FT__LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/acl/">ACL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> acl(List<String> args) {
    return send(Command.ACL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/append/">APPEND</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> append(String arg0, String arg1) {
    return send(Command.APPEND, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/asking/">ASKING</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> asking() {
    return send(Command.ASKING);
  }

  /**
   * Redis command <a href="https://redis.io/commands/auth/">AUTH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> auth(List<String> args) {
    return send(Command.AUTH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.add/">BF.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfAdd(List<String> args) {
    return send(Command.BF_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.card/">BF.CARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfCard(List<String> args) {
    return send(Command.BF_CARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.debug/">BF.DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfDebug(List<String> args) {
    return send(Command.BF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.exists/">BF.EXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfExists(List<String> args) {
    return send(Command.BF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.info/">BF.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfInfo(List<String> args) {
    return send(Command.BF_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.insert/">BF.INSERT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfInsert(List<String> args) {
    return send(Command.BF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.loadchunk/">BF.LOADCHUNK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfLoadchunk(List<String> args) {
    return send(Command.BF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.madd/">BF.MADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfMadd(List<String> args) {
    return send(Command.BF_MADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.mexists/">BF.MEXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfMexists(List<String> args) {
    return send(Command.BF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.reserve/">BF.RESERVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfReserve(List<String> args) {
    return send(Command.BF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bf.scandump/">BF.SCANDUMP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bfScandump(List<String> args) {
    return send(Command.BF_SCANDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgrewriteaof/">BGREWRITEAOF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bgrewriteaof() {
    return send(Command.BGREWRITEAOF);
  }

  /**
   * Redis command <a href="https://redis.io/commands/bgsave/">BGSAVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bgsave(List<String> args) {
    return send(Command.BGSAVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitcount/">BITCOUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitcount(List<String> args) {
    return send(Command.BITCOUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield/">BITFIELD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitfield(List<String> args) {
    return send(Command.BITFIELD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitfield_ro/">BITFIELD_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitfieldRo(List<String> args) {
    return send(Command.BITFIELD_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitop/">BITOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitop(List<String> args) {
    return send(Command.BITOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bitpos/">BITPOS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bitpos(List<String> args) {
    return send(Command.BITPOS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmove/">BLMOVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blmove(String arg0, String arg1, String arg2, String arg3, String arg4) {
    return send(Command.BLMOVE, arg0, arg1, arg2, arg3, arg4);
  }

  /**
   * Redis command <a href="https://redis.io/commands/blmpop/">BLMPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blmpop(List<String> args) {
    return send(Command.BLMPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/blpop/">BLPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> blpop(List<String> args) {
    return send(Command.BLPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpop/">BRPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> brpop(List<String> args) {
    return send(Command.BRPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/brpoplpush/">BRPOPLPUSH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> brpoplpush(String arg0, String arg1, String arg2) {
    return send(Command.BRPOPLPUSH, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzmpop/">BZMPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzmpop(List<String> args) {
    return send(Command.BZMPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmax/">BZPOPMAX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzpopmax(List<String> args) {
    return send(Command.BZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/bzpopmin/">BZPOPMIN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> bzpopmin(List<String> args) {
    return send(Command.BZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.add/">CF.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfAdd(List<String> args) {
    return send(Command.CF_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.addnx/">CF.ADDNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfAddnx(List<String> args) {
    return send(Command.CF_ADDNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.compact/">CF.COMPACT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfCompact(List<String> args) {
    return send(Command.CF_COMPACT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.count/">CF.COUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfCount(List<String> args) {
    return send(Command.CF_COUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.debug/">CF.DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfDebug(List<String> args) {
    return send(Command.CF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.del/">CF.DEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfDel(List<String> args) {
    return send(Command.CF_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.exists/">CF.EXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfExists(List<String> args) {
    return send(Command.CF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.info/">CF.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInfo(List<String> args) {
    return send(Command.CF_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insert/">CF.INSERT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInsert(List<String> args) {
    return send(Command.CF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.insertnx/">CF.INSERTNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfInsertnx(List<String> args) {
    return send(Command.CF_INSERTNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.loadchunk/">CF.LOADCHUNK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfLoadchunk(List<String> args) {
    return send(Command.CF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.mexists/">CF.MEXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfMexists(List<String> args) {
    return send(Command.CF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.reserve/">CF.RESERVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfReserve(List<String> args) {
    return send(Command.CF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cf.scandump/">CF.SCANDUMP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cfScandump(List<String> args) {
    return send(Command.CF_SCANDUMP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/client/">CLIENT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> client(List<String> args) {
    return send(Command.CLIENT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cluster/">CLUSTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cluster(List<String> args) {
    return send(Command.CLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.incrby/">CMS.INCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsIncrby(List<String> args) {
    return send(Command.CMS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.info/">CMS.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInfo(List<String> args) {
    return send(Command.CMS_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbydim/">CMS.INITBYDIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInitbydim(List<String> args) {
    return send(Command.CMS_INITBYDIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.initbyprob/">CMS.INITBYPROB</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsInitbyprob(List<String> args) {
    return send(Command.CMS_INITBYPROB, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.merge/">CMS.MERGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsMerge(List<String> args) {
    return send(Command.CMS_MERGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/cms.query/">CMS.QUERY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> cmsQuery(List<String> args) {
    return send(Command.CMS_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/command/">COMMAND</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> command(List<String> args) {
    return send(Command.COMMAND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/config/">CONFIG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> config(List<String> args) {
    return send(Command.CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/copy/">COPY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> copy(List<String> args) {
    return send(Command.COPY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/dbsize/">DBSIZE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> dbsize() {
    return send(Command.DBSIZE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/debug/">DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> debug(List<String> args) {
    return send(Command.DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/decr/">DECR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> decr(String arg0) {
    return send(Command.DECR, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/decrby/">DECRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> decrby(String arg0, String arg1) {
    return send(Command.DECRBY, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/del/">DEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> del(List<String> args) {
    return send(Command.DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/discard/">DISCARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> discard() {
    return send(Command.DISCARD);
  }

  /**
   * Redis command <a href="https://redis.io/commands/dump/">DUMP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> dump(String arg0) {
    return send(Command.DUMP, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/echo/">ECHO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> echo(String arg0) {
    return send(Command.ECHO, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval/">EVAL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> eval(List<String> args) {
    return send(Command.EVAL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/eval_ro/">EVAL_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalRo(List<String> args) {
    return send(Command.EVAL_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha/">EVALSHA</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalsha(List<String> args) {
    return send(Command.EVALSHA, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/evalsha_ro/">EVALSHA_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> evalshaRo(List<String> args) {
    return send(Command.EVALSHA_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/exec/">EXEC</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> exec() {
    return send(Command.EXEC);
  }

  /**
   * Redis command <a href="https://redis.io/commands/exists/">EXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> exists(List<String> args) {
    return send(Command.EXISTS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expire/">EXPIRE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expire(List<String> args) {
    return send(Command.EXPIRE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expireat/">EXPIREAT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expireat(List<String> args) {
    return send(Command.EXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/expiretime/">EXPIRETIME</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> expiretime(String arg0) {
    return send(Command.EXPIRETIME, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/failover/">FAILOVER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> failover(List<String> args) {
    return send(Command.FAILOVER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall/">FCALL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> fcall(List<String> args) {
    return send(Command.FCALL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/fcall_ro/">FCALL_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> fcallRo(List<String> args) {
    return send(Command.FCALL_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushall/">FLUSHALL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> flushall(List<String> args) {
    return send(Command.FLUSHALL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/flushdb/">FLUSHDB</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> flushdb(List<String> args) {
    return send(Command.FLUSHDB, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/function/">FUNCTION</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> function(List<String> args) {
    return send(Command.FUNCTION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geoadd/">GEOADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geoadd(List<String> args) {
    return send(Command.GEOADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geodist/">GEODIST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geodist(List<String> args) {
    return send(Command.GEODIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geohash/">GEOHASH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geohash(List<String> args) {
    return send(Command.GEOHASH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geopos/">GEOPOS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geopos(List<String> args) {
    return send(Command.GEOPOS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius/">GEORADIUS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadius(List<String> args) {
    return send(Command.GEORADIUS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadius_ro/">GEORADIUS_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusRo(List<String> args) {
    return send(Command.GEORADIUS_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember/">GEORADIUSBYMEMBER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusbymember(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/georadiusbymember_ro/">GEORADIUSBYMEMBER_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> georadiusbymemberRo(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearch/">GEOSEARCH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geosearch(List<String> args) {
    return send(Command.GEOSEARCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/geosearchstore/">GEOSEARCHSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> geosearchstore(List<String> args) {
    return send(Command.GEOSEARCHSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/get/">GET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> get(String arg0) {
    return send(Command.GET, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getbit/">GETBIT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getbit(String arg0, String arg1) {
    return send(Command.GETBIT, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getdel/">GETDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getdel(String arg0) {
    return send(Command.GETDEL, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getex/">GETEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getex(List<String> args) {
    return send(Command.GETEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/getrange/">GETRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getrange(String arg0, String arg1, String arg2) {
    return send(Command.GETRANGE, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/getset/">GETSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> getset(String arg0, String arg1) {
    return send(Command.GETSET, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.bulk/">GRAPH.BULK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphBulk(List<String> args) {
    return send(Command.GRAPH_BULK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.config/">GRAPH.CONFIG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphConfig(List<String> args) {
    return send(Command.GRAPH_CONFIG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.debug/">GRAPH.DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphDebug(List<String> args) {
    return send(Command.GRAPH_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.delete/">GRAPH.DELETE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphDelete(List<String> args) {
    return send(Command.GRAPH_DELETE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.explain/">GRAPH.EXPLAIN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphExplain(List<String> args) {
    return send(Command.GRAPH_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.list/">GRAPH.LIST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphList(List<String> args) {
    return send(Command.GRAPH_LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.profile/">GRAPH.PROFILE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphProfile(List<String> args) {
    return send(Command.GRAPH_PROFILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.query/">GRAPH.QUERY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphQuery(List<String> args) {
    return send(Command.GRAPH_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.ro_query/">GRAPH.RO_QUERY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphRoQuery(List<String> args) {
    return send(Command.GRAPH_RO_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/graph.slowlog/">GRAPH.SLOWLOG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> graphSlowlog(List<String> args) {
    return send(Command.GRAPH_SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hdel/">HDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hdel(List<String> args) {
    return send(Command.HDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hello/">HELLO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hello(List<String> args) {
    return send(Command.HELLO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hexists/">HEXISTS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hexists(String arg0, String arg1) {
    return send(Command.HEXISTS, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hget/">HGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hget(String arg0, String arg1) {
    return send(Command.HGET, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hgetall/">HGETALL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hgetall(String arg0) {
    return send(Command.HGETALL, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrby/">HINCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hincrby(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBY, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hincrbyfloat/">HINCRBYFLOAT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hincrbyfloat(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBYFLOAT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hkeys/">HKEYS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hkeys(String arg0) {
    return send(Command.HKEYS, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hlen/">HLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hlen(String arg0) {
    return send(Command.HLEN, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmget/">HMGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hmget(List<String> args) {
    return send(Command.HMGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hmset/">HMSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hmset(List<String> args) {
    return send(Command.HMSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hrandfield/">HRANDFIELD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hrandfield(List<String> args) {
    return send(Command.HRANDFIELD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hscan/">HSCAN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hscan(List<String> args) {
    return send(Command.HSCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hset/">HSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hset(List<String> args) {
    return send(Command.HSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/hsetnx/">HSETNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hsetnx(String arg0, String arg1, String arg2) {
    return send(Command.HSETNX, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hstrlen/">HSTRLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hstrlen(String arg0, String arg1) {
    return send(Command.HSTRLEN, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/hvals/">HVALS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> hvals(String arg0) {
    return send(Command.HVALS, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/incr/">INCR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incr(String arg0) {
    return send(Command.INCR, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrby/">INCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incrby(String arg0, String arg1) {
    return send(Command.INCRBY, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/incrbyfloat/">INCRBYFLOAT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> incrbyfloat(String arg0, String arg1) {
    return send(Command.INCRBYFLOAT, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/info/">INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> info(List<String> args) {
    return send(Command.INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrappend/">JSON.ARRAPPEND</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrappend(List<String> args) {
    return send(Command.JSON_ARRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrindex/">JSON.ARRINDEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrindex(List<String> args) {
    return send(Command.JSON_ARRINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrinsert/">JSON.ARRINSERT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrinsert(List<String> args) {
    return send(Command.JSON_ARRINSERT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrlen/">JSON.ARRLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrlen(List<String> args) {
    return send(Command.JSON_ARRLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrpop/">JSON.ARRPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrpop(List<String> args) {
    return send(Command.JSON_ARRPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.arrtrim/">JSON.ARRTRIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonArrtrim(List<String> args) {
    return send(Command.JSON_ARRTRIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.clear/">JSON.CLEAR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonClear(List<String> args) {
    return send(Command.JSON_CLEAR, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.debug/">JSON.DEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonDebug(List<String> args) {
    return send(Command.JSON_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.del/">JSON.DEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonDel(List<String> args) {
    return send(Command.JSON_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.forget/">JSON.FORGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonForget(List<String> args) {
    return send(Command.JSON_FORGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.get/">JSON.GET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonGet(List<String> args) {
    return send(Command.JSON_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.mget/">JSON.MGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonMget(List<String> args) {
    return send(Command.JSON_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numincrby/">JSON.NUMINCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNumincrby(List<String> args) {
    return send(Command.JSON_NUMINCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.nummultby/">JSON.NUMMULTBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNummultby(List<String> args) {
    return send(Command.JSON_NUMMULTBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.numpowby/">JSON.NUMPOWBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonNumpowby(List<String> args) {
    return send(Command.JSON_NUMPOWBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objkeys/">JSON.OBJKEYS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonObjkeys(List<String> args) {
    return send(Command.JSON_OBJKEYS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.objlen/">JSON.OBJLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonObjlen(List<String> args) {
    return send(Command.JSON_OBJLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.resp/">JSON.RESP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonResp(List<String> args) {
    return send(Command.JSON_RESP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.set/">JSON.SET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonSet(List<String> args) {
    return send(Command.JSON_SET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strappend/">JSON.STRAPPEND</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonStrappend(List<String> args) {
    return send(Command.JSON_STRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.strlen/">JSON.STRLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonStrlen(List<String> args) {
    return send(Command.JSON_STRLEN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.toggle/">JSON.TOGGLE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonToggle(List<String> args) {
    return send(Command.JSON_TOGGLE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/json.type/">JSON.TYPE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> jsonType(List<String> args) {
    return send(Command.JSON_TYPE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/keys/">KEYS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> keys(String arg0) {
    return send(Command.KEYS, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lastsave/">LASTSAVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lastsave() {
    return send(Command.LASTSAVE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/latency/">LATENCY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> latency(List<String> args) {
    return send(Command.LATENCY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lcs/">LCS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lcs(List<String> args) {
    return send(Command.LCS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lindex/">LINDEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lindex(String arg0, String arg1) {
    return send(Command.LINDEX, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/linsert/">LINSERT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> linsert(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LINSERT, arg0, arg1, arg2, arg3);
  }

  /**
   * Redis command <a href="https://redis.io/commands/llen/">LLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> llen(String arg0) {
    return send(Command.LLEN, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmove/">LMOVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lmove(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LMOVE, arg0, arg1, arg2, arg3);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lmpop/">LMPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lmpop(List<String> args) {
    return send(Command.LMPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lolwut/">LOLWUT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lolwut(List<String> args) {
    return send(Command.LOLWUT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpop/">LPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpop(List<String> args) {
    return send(Command.LPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpos/">LPOS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpos(List<String> args) {
    return send(Command.LPOS, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpush/">LPUSH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpush(List<String> args) {
    return send(Command.LPUSH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lpushx/">LPUSHX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lpushx(List<String> args) {
    return send(Command.LPUSHX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrange/">LRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lrange(String arg0, String arg1, String arg2) {
    return send(Command.LRANGE, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lrem/">LREM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lrem(String arg0, String arg1, String arg2) {
    return send(Command.LREM, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/lset/">LSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> lset(String arg0, String arg1, String arg2) {
    return send(Command.LSET, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/ltrim/">LTRIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ltrim(String arg0, String arg1, String arg2) {
    return send(Command.LTRIM, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/memory/">MEMORY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> memory(List<String> args) {
    return send(Command.MEMORY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/mget/">MGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> mget(List<String> args) {
    return send(Command.MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/migrate/">MIGRATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> migrate(List<String> args) {
    return send(Command.MIGRATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/module/">MODULE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> module(List<String> args) {
    return send(Command.MODULE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/monitor/">MONITOR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> monitor() {
    return send(Command.MONITOR);
  }

  /**
   * Redis command <a href="https://redis.io/commands/move/">MOVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> move(String arg0, String arg1) {
    return send(Command.MOVE, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/mset/">MSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> mset(List<String> args) {
    return send(Command.MSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/msetnx/">MSETNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> msetnx(List<String> args) {
    return send(Command.MSETNX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/multi/">MULTI</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> multi() {
    return send(Command.MULTI);
  }

  /**
   * Redis command <a href="https://redis.io/commands/object/">OBJECT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> object(List<String> args) {
    return send(Command.OBJECT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/persist/">PERSIST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> persist(String arg0) {
    return send(Command.PERSIST, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpire/">PEXPIRE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpire(List<String> args) {
    return send(Command.PEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpireat/">PEXPIREAT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpireat(List<String> args) {
    return send(Command.PEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pexpiretime/">PEXPIRETIME</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pexpiretime(String arg0) {
    return send(Command.PEXPIRETIME, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfadd/">PFADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfadd(List<String> args) {
    return send(Command.PFADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfcount/">PFCOUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfcount(List<String> args) {
    return send(Command.PFCOUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfdebug/">PFDEBUG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfdebug(String arg0, String arg1) {
    return send(Command.PFDEBUG, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfmerge/">PFMERGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfmerge(List<String> args) {
    return send(Command.PFMERGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pfselftest/">PFSELFTEST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pfselftest() {
    return send(Command.PFSELFTEST);
  }

  /**
   * Redis command <a href="https://redis.io/commands/ping/">PING</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ping(List<String> args) {
    return send(Command.PING, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psetex/">PSETEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psetex(String arg0, String arg1, String arg2) {
    return send(Command.PSETEX, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/psubscribe/">PSUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psubscribe(List<String> args) {
    return send(Command.PSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/psync/">PSYNC</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> psync(List<String> args) {
    return send(Command.PSYNC, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/pttl/">PTTL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pttl(String arg0) {
    return send(Command.PTTL, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/publish/">PUBLISH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> publish(String arg0, String arg1) {
    return send(Command.PUBLISH, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/pubsub/">PUBSUB</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> pubsub(List<String> args) {
    return send(Command.PUBSUB, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/punsubscribe/">PUNSUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> punsubscribe(List<String> args) {
    return send(Command.PUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/quit/">QUIT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> quit(List<String> args) {
    return send(Command.QUIT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/randomkey/">RANDOMKEY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> randomkey() {
    return send(Command.RANDOMKEY);
  }

  /**
   * Redis command <a href="https://redis.io/commands/readonly/">READONLY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> readonly() {
    return send(Command.READONLY);
  }

  /**
   * Redis command <a href="https://redis.io/commands/readwrite/">READWRITE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> readwrite() {
    return send(Command.READWRITE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/rename/">RENAME</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rename(String arg0, String arg1) {
    return send(Command.RENAME, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/renamenx/">RENAMENX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> renamenx(String arg0, String arg1) {
    return send(Command.RENAMENX, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/replconf/">REPLCONF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> replconf(List<String> args) {
    return send(Command.REPLCONF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/replicaof/">REPLICAOF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> replicaof(String arg0, String arg1) {
    return send(Command.REPLICAOF, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/reset/">RESET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> reset() {
    return send(Command.RESET);
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore/">RESTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> restore(List<String> args) {
    return send(Command.RESTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/restore asking/">RESTORE-ASKING</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> restoreAsking(List<String> args) {
    return send(Command.RESTORE_ASKING, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/role/">ROLE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> role() {
    return send(Command.ROLE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpop/">RPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpop(List<String> args) {
    return send(Command.RPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpoplpush/">RPOPLPUSH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpoplpush(String arg0, String arg1) {
    return send(Command.RPOPLPUSH, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpush/">RPUSH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpush(List<String> args) {
    return send(Command.RPUSH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/rpushx/">RPUSHX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> rpushx(List<String> args) {
    return send(Command.RPUSHX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sadd/">SADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sadd(List<String> args) {
    return send(Command.SADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/save/">SAVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> save() {
    return send(Command.SAVE);
  }

  /**
   * Redis command <a href="https://redis.io/commands/scan/">SCAN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> scan(List<String> args) {
    return send(Command.SCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/scard/">SCARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> scard(String arg0) {
    return send(Command.SCARD, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/script/">SCRIPT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> script(List<String> args) {
    return send(Command.SCRIPT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiff/">SDIFF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sdiff(List<String> args) {
    return send(Command.SDIFF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sdiffstore/">SDIFFSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sdiffstore(List<String> args) {
    return send(Command.SDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/select/">SELECT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> select(String arg0) {
    return send(Command.SELECT, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/set/">SET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> set(List<String> args) {
    return send(Command.SET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/setbit/">SETBIT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setbit(String arg0, String arg1, String arg2) {
    return send(Command.SETBIT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/setex/">SETEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setex(String arg0, String arg1, String arg2) {
    return send(Command.SETEX, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/setnx/">SETNX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setnx(String arg0, String arg1) {
    return send(Command.SETNX, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/setrange/">SETRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> setrange(String arg0, String arg1, String arg2) {
    return send(Command.SETRANGE, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/shutdown/">SHUTDOWN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> shutdown(List<String> args) {
    return send(Command.SHUTDOWN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinter/">SINTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sinter(List<String> args) {
    return send(Command.SINTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sintercard/">SINTERCARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sintercard(List<String> args) {
    return send(Command.SINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sinterstore/">SINTERSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sinterstore(List<String> args) {
    return send(Command.SINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sismember/">SISMEMBER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sismember(String arg0, String arg1) {
    return send(Command.SISMEMBER, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/slaveof/">SLAVEOF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> slaveof(String arg0, String arg1) {
    return send(Command.SLAVEOF, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/slowlog/">SLOWLOG</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> slowlog(List<String> args) {
    return send(Command.SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smembers/">SMEMBERS</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smembers(String arg0) {
    return send(Command.SMEMBERS, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/smismember/">SMISMEMBER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smismember(List<String> args) {
    return send(Command.SMISMEMBER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/smove/">SMOVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> smove(String arg0, String arg1, String arg2) {
    return send(Command.SMOVE, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort/">SORT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sort(List<String> args) {
    return send(Command.SORT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sort_ro/">SORT_RO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sortRo(List<String> args) {
    return send(Command.SORT_RO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spop/">SPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> spop(List<String> args) {
    return send(Command.SPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/spublish/">SPUBLISH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> spublish(String arg0, String arg1) {
    return send(Command.SPUBLISH, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/srandmember/">SRANDMEMBER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> srandmember(List<String> args) {
    return send(Command.SRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/srem/">SREM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> srem(List<String> args) {
    return send(Command.SREM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sscan/">SSCAN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sscan(List<String> args) {
    return send(Command.SSCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ssubscribe/">SSUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ssubscribe(List<String> args) {
    return send(Command.SSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/strlen/">STRLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> strlen(String arg0) {
    return send(Command.STRLEN, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/subscribe/">SUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> subscribe(List<String> args) {
    return send(Command.SUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/substr/">SUBSTR</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> substr(String arg0, String arg1, String arg2) {
    return send(Command.SUBSTR, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunion/">SUNION</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunion(List<String> args) {
    return send(Command.SUNION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunionstore/">SUNIONSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunionstore(List<String> args) {
    return send(Command.SUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/sunsubscribe/">SUNSUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sunsubscribe(List<String> args) {
    return send(Command.SUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/swapdb/">SWAPDB</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> swapdb(String arg0, String arg1) {
    return send(Command.SWAPDB, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/sync/">SYNC</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> sync() {
    return send(Command.SYNC);
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.add/">TDIGEST.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestAdd(List<String> args) {
    return send(Command.TDIGEST_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.byrank/">TDIGEST.BYRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestByrank(List<String> args) {
    return send(Command.TDIGEST_BYRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.byrevrank/">TDIGEST.BYREVRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestByrevrank(List<String> args) {
    return send(Command.TDIGEST_BYREVRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.cdf/">TDIGEST.CDF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestCdf(List<String> args) {
    return send(Command.TDIGEST_CDF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.create/">TDIGEST.CREATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestCreate(List<String> args) {
    return send(Command.TDIGEST_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.info/">TDIGEST.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestInfo(List<String> args) {
    return send(Command.TDIGEST_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.max/">TDIGEST.MAX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMax(List<String> args) {
    return send(Command.TDIGEST_MAX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.merge/">TDIGEST.MERGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMerge(List<String> args) {
    return send(Command.TDIGEST_MERGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.min/">TDIGEST.MIN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestMin(List<String> args) {
    return send(Command.TDIGEST_MIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.quantile/">TDIGEST.QUANTILE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestQuantile(List<String> args) {
    return send(Command.TDIGEST_QUANTILE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.rank/">TDIGEST.RANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestRank(List<String> args) {
    return send(Command.TDIGEST_RANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.reset/">TDIGEST.RESET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestReset(List<String> args) {
    return send(Command.TDIGEST_RESET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.revrank/">TDIGEST.REVRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestRevrank(List<String> args) {
    return send(Command.TDIGEST_REVRANK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/tdigest.trimmed_mean/">TDIGEST.TRIMMED_MEAN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tdigestTrimmedMean(List<String> args) {
    return send(Command.TDIGEST_TRIMMED_MEAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/time/">TIME</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> time() {
    return send(Command.TIME);
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.clusterset/">TIMESERIES.CLUSTERSET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesClusterset(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.clustersetfromshard/">TIMESERIES.CLUSTERSETFROMSHARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesClustersetfromshard(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSETFROMSHARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.hello/">TIMESERIES.HELLO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesHello(List<String> args) {
    return send(Command.TIMESERIES_HELLO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.infocluster/">TIMESERIES.INFOCLUSTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesInfocluster(List<String> args) {
    return send(Command.TIMESERIES_INFOCLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.innercommunication/">TIMESERIES.INNERCOMMUNICATION</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesInnercommunication(List<String> args) {
    return send(Command.TIMESERIES_INNERCOMMUNICATION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.networktest/">TIMESERIES.NETWORKTEST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesNetworktest(List<String> args) {
    return send(Command.TIMESERIES_NETWORKTEST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/timeseries.refreshcluster/">TIMESERIES.REFRESHCLUSTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> timeseriesRefreshcluster(List<String> args) {
    return send(Command.TIMESERIES_REFRESHCLUSTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.add/">TOPK.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkAdd(List<String> args) {
    return send(Command.TOPK_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.count/">TOPK.COUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkCount(List<String> args) {
    return send(Command.TOPK_COUNT, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.incrby/">TOPK.INCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkIncrby(List<String> args) {
    return send(Command.TOPK_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.info/">TOPK.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkInfo(List<String> args) {
    return send(Command.TOPK_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.list/">TOPK.LIST</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkList(List<String> args) {
    return send(Command.TOPK_LIST, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.query/">TOPK.QUERY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkQuery(List<String> args) {
    return send(Command.TOPK_QUERY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/topk.reserve/">TOPK.RESERVE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> topkReserve(List<String> args) {
    return send(Command.TOPK_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/touch/">TOUCH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> touch(List<String> args) {
    return send(Command.TOUCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.add/">TS.ADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsAdd(List<String> args) {
    return send(Command.TS_ADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.alter/">TS.ALTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsAlter(List<String> args) {
    return send(Command.TS_ALTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.create/">TS.CREATE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsCreate(List<String> args) {
    return send(Command.TS_CREATE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.createrule/">TS.CREATERULE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsCreaterule(List<String> args) {
    return send(Command.TS_CREATERULE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.decrby/">TS.DECRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDecrby(List<String> args) {
    return send(Command.TS_DECRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.del/">TS.DEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDel(List<String> args) {
    return send(Command.TS_DEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.deleterule/">TS.DELETERULE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsDeleterule(List<String> args) {
    return send(Command.TS_DELETERULE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.get/">TS.GET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsGet(List<String> args) {
    return send(Command.TS_GET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.incrby/">TS.INCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsIncrby(List<String> args) {
    return send(Command.TS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.info/">TS.INFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsInfo(List<String> args) {
    return send(Command.TS_INFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.madd/">TS.MADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMadd(List<String> args) {
    return send(Command.TS_MADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mget/">TS.MGET</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMget(List<String> args) {
    return send(Command.TS_MGET, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrange/">TS.MRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMrange(List<String> args) {
    return send(Command.TS_MRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.mrevrange/">TS.MREVRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsMrevrange(List<String> args) {
    return send(Command.TS_MREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.queryindex/">TS.QUERYINDEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsQueryindex(List<String> args) {
    return send(Command.TS_QUERYINDEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.range/">TS.RANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsRange(List<String> args) {
    return send(Command.TS_RANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ts.revrange/">TS.REVRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> tsRevrange(List<String> args) {
    return send(Command.TS_REVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/ttl/">TTL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> ttl(String arg0) {
    return send(Command.TTL, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/type/">TYPE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> type(String arg0) {
    return send(Command.TYPE, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/unlink/">UNLINK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unlink(List<String> args) {
    return send(Command.UNLINK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/unsubscribe/">UNSUBSCRIBE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unsubscribe(List<String> args) {
    return send(Command.UNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/unwatch/">UNWATCH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> unwatch() {
    return send(Command.UNWATCH);
  }

  /**
   * Redis command <a href="https://redis.io/commands/wait/">WAIT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> wait(String arg0, String arg1) {
    return send(Command.WAIT, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/watch/">WATCH</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> watch(List<String> args) {
    return send(Command.WATCH, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xack/">XACK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xack(List<String> args) {
    return send(Command.XACK, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xadd/">XADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xadd(List<String> args) {
    return send(Command.XADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xautoclaim/">XAUTOCLAIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xautoclaim(List<String> args) {
    return send(Command.XAUTOCLAIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xclaim/">XCLAIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xclaim(List<String> args) {
    return send(Command.XCLAIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xdel/">XDEL</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xdel(List<String> args) {
    return send(Command.XDEL, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xgroup/">XGROUP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xgroup(List<String> args) {
    return send(Command.XGROUP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xinfo/">XINFO</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xinfo(List<String> args) {
    return send(Command.XINFO, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xlen/">XLEN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xlen(String arg0) {
    return send(Command.XLEN, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/xpending/">XPENDING</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xpending(List<String> args) {
    return send(Command.XPENDING, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrange/">XRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xrange(List<String> args) {
    return send(Command.XRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xread/">XREAD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xread(List<String> args) {
    return send(Command.XREAD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xreadgroup/">XREADGROUP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xreadgroup(List<String> args) {
    return send(Command.XREADGROUP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xrevrange/">XREVRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xrevrange(List<String> args) {
    return send(Command.XREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xsetid/">XSETID</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xsetid(List<String> args) {
    return send(Command.XSETID, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/xtrim/">XTRIM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> xtrim(List<String> args) {
    return send(Command.XTRIM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zadd/">ZADD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zadd(List<String> args) {
    return send(Command.ZADD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcard/">ZCARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zcard(String arg0) {
    return send(Command.ZCARD, arg0);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zcount/">ZCOUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zcount(String arg0, String arg1, String arg2) {
    return send(Command.ZCOUNT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiff/">ZDIFF</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zdiff(List<String> args) {
    return send(Command.ZDIFF, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zdiffstore/">ZDIFFSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zdiffstore(List<String> args) {
    return send(Command.ZDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zincrby/">ZINCRBY</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zincrby(String arg0, String arg1, String arg2) {
    return send(Command.ZINCRBY, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinter/">ZINTER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zinter(List<String> args) {
    return send(Command.ZINTER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zintercard/">ZINTERCARD</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zintercard(List<String> args) {
    return send(Command.ZINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zinterstore/">ZINTERSTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zinterstore(List<String> args) {
    return send(Command.ZINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zlexcount/">ZLEXCOUNT</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zlexcount(String arg0, String arg1, String arg2) {
    return send(Command.ZLEXCOUNT, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmpop/">ZMPOP</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zmpop(List<String> args) {
    return send(Command.ZMPOP, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zmscore/">ZMSCORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zmscore(List<String> args) {
    return send(Command.ZMSCORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmax/">ZPOPMAX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zpopmax(List<String> args) {
    return send(Command.ZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zpopmin/">ZPOPMIN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zpopmin(List<String> args) {
    return send(Command.ZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrandmember/">ZRANDMEMBER</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrandmember(List<String> args) {
    return send(Command.ZRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrange/">ZRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrange(List<String> args) {
    return send(Command.ZRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebylex/">ZRANGEBYLEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebylex(List<String> args) {
    return send(Command.ZRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangebyscore/">ZRANGEBYSCORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangebyscore(List<String> args) {
    return send(Command.ZRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrangestore/">ZRANGESTORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrangestore(List<String> args) {
    return send(Command.ZRANGESTORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrank/">ZRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrank(String arg0, String arg1) {
    return send(Command.ZRANK, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrem/">ZREM</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrem(List<String> args) {
    return send(Command.ZREM, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebylex/">ZREMRANGEBYLEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebylex(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyrank/">ZREMRANGEBYRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebyrank(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zremrangebyscore/">ZREMRANGEBYSCORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zremrangebyscore(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrange/">ZREVRANGE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrange(List<String> args) {
    return send(Command.ZREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebylex/">ZREVRANGEBYLEX</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrangebylex(List<String> args) {
    return send(Command.ZREVRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrangebyscore/">ZREVRANGEBYSCORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrangebyscore(List<String> args) {
    return send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zrevrank/">ZREVRANK</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zrevrank(String arg0, String arg1) {
    return send(Command.ZREVRANK, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscan/">ZSCAN</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zscan(List<String> args) {
    return send(Command.ZSCAN, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zscore/">ZSCORE</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zscore(String arg0, String arg1) {
    return send(Command.ZSCORE, arg0, arg1);
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunion/">ZUNION</a>.
   * @return Future response.
   */
  default Future<@Nullable Response> zunion(List<String> args) {
    return send(Command.ZUNION, args.toArray(new String[0]));
  }

  /**
   * Redis command <a href="https://redis.io/commands/zunionstore/">ZUNIONSTORE</a>.
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
