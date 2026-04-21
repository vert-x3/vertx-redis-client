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
 * @version redis_version:8.6.2
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
   * @see <a href="https://redis.io/commands/ft.add/">FT.ADD</a>
   */
  default Future<@Nullable Response> ftAdd(List<String> args) {
    return send(Command.FT_ADD, args.toArray(new String[0]));
  }

  /**
   * Run a search query on an index and perform aggregate transformations on the results.
   * @see <a href="https://redis.io/commands/ft.aggregate/">FT.AGGREGATE</a>
   * @since Redis Search 1.1.0
   */
  default Future<@Nullable Response> ftAggregate(List<String> args) {
    return send(Command.FT_AGGREGATE, args.toArray(new String[0]));
  }

  /**
   * Adds an alias to the index.
   * @see <a href="https://redis.io/commands/ft.aliasadd/">FT.ALIASADD</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAliasadd(List<String> args) {
    return send(Command.FT_ALIASADD, args.toArray(new String[0]));
  }

  /**
   * Deletes an alias from the index.
   * @see <a href="https://redis.io/commands/ft.aliasdel/">FT.ALIASDEL</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAliasdel(List<String> args) {
    return send(Command.FT_ALIASDEL, args.toArray(new String[0]));
  }

  /**
   * Adds or updates an alias to the index.
   * @see <a href="https://redis.io/commands/ft.aliasupdate/">FT.ALIASUPDATE</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAliasupdate(List<String> args) {
    return send(Command.FT_ALIASUPDATE, args.toArray(new String[0]));
  }

  /**
   * Adds a new field to the index.
   * @see <a href="https://redis.io/commands/ft.alter/">FT.ALTER</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAlter(List<String> args) {
    return send(Command.FT_ALTER, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.config/">FT.CONFIG</a>
   */
  default Future<@Nullable Response> ftConfig(List<String> args) {
    return send(Command.FT_CONFIG, args.toArray(new String[0]));
  }

  /**
   * Creates an index with the given spec.
   * @see <a href="https://redis.io/commands/ft.create/">FT.CREATE</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftCreate(List<String> args) {
    return send(Command.FT_CREATE, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.cursor/">FT.CURSOR</a>
   */
  default Future<@Nullable Response> ftCursor(List<String> args) {
    return send(Command.FT_CURSOR, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.debug/">FT.DEBUG</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> ftDebug(List<String> args) {
    return send(Command.FT_DEBUG, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.del/">FT.DEL</a>
   */
  default Future<@Nullable Response> ftDel(List<String> args) {
    return send(Command.FT_DEL, args.toArray(new String[0]));
  }

  /**
   * Adds terms to a dictionary.
   * @see <a href="https://redis.io/commands/ft.dictadd/">FT.DICTADD</a>
   * @since Redis Search 1.4.0
   */
  default Future<@Nullable Response> ftDictadd(List<String> args) {
    return send(Command.FT_DICTADD, args.toArray(new String[0]));
  }

  /**
   * Deletes terms from a dictionary.
   * @see <a href="https://redis.io/commands/ft.dictdel/">FT.DICTDEL</a>
   * @since Redis Search 1.4.0
   */
  default Future<@Nullable Response> ftDictdel(List<String> args) {
    return send(Command.FT_DICTDEL, args.toArray(new String[0]));
  }

  /**
   * Dumps all terms in the given dictionary.
   * @see <a href="https://redis.io/commands/ft.dictdump/">FT.DICTDUMP</a>
   * @since Redis Search 1.4.0
   */
  default Future<@Nullable Response> ftDictdump(List<String> args) {
    return send(Command.FT_DICTDUMP, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.drop/">FT.DROP</a>
   */
  default Future<@Nullable Response> ftDrop(List<String> args) {
    return send(Command.FT_DROP, args.toArray(new String[0]));
  }

  /**
   * Deletes the index.
   * @see <a href="https://redis.io/commands/ft.dropindex/">FT.DROPINDEX</a>
   * @since Redis Search 2.0.0
   */
  default Future<@Nullable Response> ftDropindex(List<String> args) {
    return send(Command.FT_DROPINDEX, args.toArray(new String[0]));
  }

  /**
   * Returns the execution plan for a complex query.
   * @see <a href="https://redis.io/commands/ft.explain/">FT.EXPLAIN</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftExplain(List<String> args) {
    return send(Command.FT_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * Returns the execution plan for a complex query.
   * @see <a href="https://redis.io/commands/ft.explaincli/">FT.EXPLAINCLI</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftExplaincli(List<String> args) {
    return send(Command.FT_EXPLAINCLI, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.get/">FT.GET</a>
   */
  default Future<@Nullable Response> ftGet(List<String> args) {
    return send(Command.FT_GET, args.toArray(new String[0]));
  }

  /**
   * Performs hybrid search combining text search and vector similarity search.
   * @see <a href="https://redis.io/commands/ft.hybrid/">FT.HYBRID</a>
   * @since Redis Search 8.4.4
   */
  default Future<@Nullable Response> ftHybrid(List<String> args) {
    return send(Command.FT_HYBRID, args.toArray(new String[0]));
  }

  /**
   * Returns information and statistics on the index.
   * @see <a href="https://redis.io/commands/ft.info/">FT.INFO</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftInfo(List<String> args) {
    return send(Command.FT_INFO, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.mget/">FT.MGET</a>
   */
  default Future<@Nullable Response> ftMget(List<String> args) {
    return send(Command.FT_MGET, args.toArray(new String[0]));
  }

  /**
   * Performs a &#x60;FT.SEARCH&#x60; or &#x60;FT.AGGREGATE&#x60; command and collects performance information.
   * @see <a href="https://redis.io/commands/ft.profile/">FT.PROFILE</a>
   * @since Redis Search 2.2.0
   */
  default Future<@Nullable Response> ftProfile(List<String> args) {
    return send(Command.FT_PROFILE, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.safeadd/">FT.SAFEADD</a>
   */
  default Future<@Nullable Response> ftSafeadd(List<String> args) {
    return send(Command.FT_SAFEADD, args.toArray(new String[0]));
  }

  /**
   * Searches the index with a textual query, returning either documents or just ids.
   * @see <a href="https://redis.io/commands/ft.search/">FT.SEARCH</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftSearch(List<String> args) {
    return send(Command.FT_SEARCH, args.toArray(new String[0]));
  }

  /**
   * Performs spelling correction on a query, returning suggestions for misspelled terms.
   * @see <a href="https://redis.io/commands/ft.spellcheck/">FT.SPELLCHECK</a>
   * @since Redis Search 1.4.0
   */
  default Future<@Nullable Response> ftSpellcheck(List<String> args) {
    return send(Command.FT_SPELLCHECK, args.toArray(new String[0]));
  }

  /**
   * Adds a suggestion string to an auto-complete suggestion dictionary.
   * @see <a href="https://redis.io/commands/ft.sugadd/">FT.SUGADD</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftSugadd(List<String> args) {
    return send(Command.FT_SUGADD, args.toArray(new String[0]));
  }

  /**
   * Deletes a string from a suggestion index.
   * @see <a href="https://redis.io/commands/ft.sugdel/">FT.SUGDEL</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftSugdel(List<String> args) {
    return send(Command.FT_SUGDEL, args.toArray(new String[0]));
  }

  /**
   * Gets completion suggestions for a prefix.
   * @see <a href="https://redis.io/commands/ft.sugget/">FT.SUGGET</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftSugget(List<String> args) {
    return send(Command.FT_SUGGET, args.toArray(new String[0]));
  }

  /**
   * Gets the size of an auto-complete suggestion dictionary.
   * @see <a href="https://redis.io/commands/ft.suglen/">FT.SUGLEN</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftSuglen(List<String> args) {
    return send(Command.FT_SUGLEN, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft.synadd/">FT.SYNADD</a>
   */
  default Future<@Nullable Response> ftSynadd(List<String> args) {
    return send(Command.FT_SYNADD, args.toArray(new String[0]));
  }

  /**
   * Dumps the contents of a synonym group.
   * @see <a href="https://redis.io/commands/ft.syndump/">FT.SYNDUMP</a>
   * @since Redis Search 1.2.0
   */
  default Future<@Nullable Response> ftSyndump(List<String> args) {
    return send(Command.FT_SYNDUMP, args.toArray(new String[0]));
  }

  /**
   * Creates or updates a synonym group with additional terms.
   * @see <a href="https://redis.io/commands/ft.synupdate/">FT.SYNUPDATE</a>
   * @since Redis Search 1.2.0
   */
  default Future<@Nullable Response> ftSynupdate(List<String> args) {
    return send(Command.FT_SYNUPDATE, args.toArray(new String[0]));
  }

  /**
   * Returns the distinct tags indexed in a Tag field.
   * @see <a href="https://redis.io/commands/ft.tagvals/">FT.TAGVALS</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftTagvals(List<String> args) {
    return send(Command.FT_TAGVALS, args.toArray(new String[0]));
  }

  /**
   * Adds an alias to the index.
   * @see <a href="https://redis.io/commands/ft._aliasaddifnx/">FT._ALIASADDIFNX</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAliasaddifnx(List<String> args) {
    return send(Command.FT__ALIASADDIFNX, args.toArray(new String[0]));
  }

  /**
   * Deletes an alias from the index.
   * @see <a href="https://redis.io/commands/ft._aliasdelifx/">FT._ALIASDELIFX</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAliasdelifx(List<String> args) {
    return send(Command.FT__ALIASDELIFX, args.toArray(new String[0]));
  }

  /**
   * Adds a new field to the index.
   * @see <a href="https://redis.io/commands/ft._alterifnx/">FT._ALTERIFNX</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftAlterifnx(List<String> args) {
    return send(Command.FT__ALTERIFNX, args.toArray(new String[0]));
  }

  /**
   * Creates an index with the given spec.
   * @see <a href="https://redis.io/commands/ft._createifnx/">FT._CREATEIFNX</a>
   * @since Redis Search 1.0.0
   */
  default Future<@Nullable Response> ftCreateifnx(List<String> args) {
    return send(Command.FT__CREATEIFNX, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/ft._dropifx/">FT._DROPIFX</a>
   */
  default Future<@Nullable Response> ftDropifx(List<String> args) {
    return send(Command.FT__DROPIFX, args.toArray(new String[0]));
  }

  /**
   * Deletes the index.
   * @see <a href="https://redis.io/commands/ft._dropindexifx/">FT._DROPINDEXIFX</a>
   * @since Redis Search 2.0.0
   */
  default Future<@Nullable Response> ftDropindexifx(List<String> args) {
    return send(Command.FT__DROPINDEXIFX, args.toArray(new String[0]));
  }

  /**
   * Returns a list of all existing indexes.
   * @see <a href="https://redis.io/commands/ft._list/">FT._LIST</a>
   * @since Redis Search 2.0.0
   */
  default Future<@Nullable Response> ftList(List<String> args) {
    return send(Command.FT__LIST, args.toArray(new String[0]));
  }

  /**
   * Add one or more elements to a vector set, or update its vector if it already exists.
   * @see <a href="https://redis.io/commands/vadd/">VADD</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vadd(List<String> args) {
    return send(Command.VADD, args.toArray(new String[0]));
  }

  /**
   * Return the number of elements in a vector set.
   * @see <a href="https://redis.io/commands/vcard/">VCARD</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vcard(String arg0) {
    return send(Command.VCARD, arg0);
  }

  /**
   * Return the dimension of vectors in the vector set.
   * @see <a href="https://redis.io/commands/vdim/">VDIM</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vdim(String arg0) {
    return send(Command.VDIM, arg0);
  }

  /**
   * Return the vector associated with an element.
   * @see <a href="https://redis.io/commands/vemb/">VEMB</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vemb(List<String> args) {
    return send(Command.VEMB, args.toArray(new String[0]));
  }

  /**
   * Retrieve the JSON attributes of elements.
   * @see <a href="https://redis.io/commands/vgetattr/">VGETATTR</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vgetattr(String arg0, String arg1) {
    return send(Command.VGETATTR, arg0, arg1);
  }

  /**
   * Return information about a vector set.
   * @see <a href="https://redis.io/commands/vinfo/">VINFO</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vinfo(String arg0) {
    return send(Command.VINFO, arg0);
  }

  /**
   * Check if an element exists in a vector set.
   * @see <a href="https://redis.io/commands/vismember/">VISMEMBER</a>
   * @since Redis VectorSet 8.2.0
   */
  default Future<@Nullable Response> vismember(String arg0, String arg1) {
    return send(Command.VISMEMBER, arg0, arg1);
  }

  /**
   * Return the neighbors of an element at each layer in the HNSW graph.
   * @see <a href="https://redis.io/commands/vlinks/">VLINKS</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vlinks(List<String> args) {
    return send(Command.VLINKS, args.toArray(new String[0]));
  }

  /**
   * Return one or multiple random members from a vector set.
   * @see <a href="https://redis.io/commands/vrandmember/">VRANDMEMBER</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vrandmember(List<String> args) {
    return send(Command.VRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Return vector set elements in a lex range.
   * @see <a href="https://redis.io/commands/vrange/">VRANGE</a>
   * @since Redis VectorSet 8.4.0
   */
  default Future<@Nullable Response> vrange(List<String> args) {
    return send(Command.VRANGE, args.toArray(new String[0]));
  }

  /**
   * Remove an element from a vector set.
   * @see <a href="https://redis.io/commands/vrem/">VREM</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vrem(String arg0, String arg1) {
    return send(Command.VREM, arg0, arg1);
  }

  /**
   * Associate or remove the JSON attributes of elements.
   * @see <a href="https://redis.io/commands/vsetattr/">VSETATTR</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vsetattr(String arg0, String arg1, String arg2) {
    return send(Command.VSETATTR, arg0, arg1, arg2);
  }

  /**
   * Return elements by vector similarity.
   * @see <a href="https://redis.io/commands/vsim/">VSIM</a>
   * @since Redis VectorSet 8.0.0
   */
  default Future<@Nullable Response> vsim(List<String> args) {
    return send(Command.VSIM, args.toArray(new String[0]));
  }

  /**
   * A container for Access List Control commands.
   * @see <a href="https://redis.io/commands/acl/">ACL</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> acl(List<String> args) {
    return send(Command.ACL, args.toArray(new String[0]));
  }

  /**
   * Appends a string to the value of a key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/append/">APPEND</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> append(String arg0, String arg1) {
    return send(Command.APPEND, arg0, arg1);
  }

  /**
   * Signals that a cluster client is following an -ASK redirect.
   * @see <a href="https://redis.io/commands/asking/">ASKING</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> asking() {
    return send(Command.ASKING);
  }

  /**
   * Authenticates the connection.
   * @see <a href="https://redis.io/commands/auth/">AUTH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> auth(List<String> args) {
    return send(Command.AUTH, args.toArray(new String[0]));
  }

  /**
   * Adds an item to a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.add/">BF.ADD</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfAdd(String arg0, String arg1) {
    return send(Command.BF_ADD, arg0, arg1);
  }

  /**
   * Adds an item to a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.add/">BF.ADD</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> bfAdd(List<String> args) {
    return send(Command.BF_ADD, args.toArray(new String[0]));
  }

  /**
   * Returns the cardinality of a Bloom filter.
   * @see <a href="https://redis.io/commands/bf.card/">BF.CARD</a>
   * @since Redis Bloom 2.4.4
   */
  default Future<@Nullable Response> bfCard(String arg0) {
    return send(Command.BF_CARD, arg0);
  }

  /**
   * Returns the cardinality of a Bloom filter.
   * @see <a href="https://redis.io/commands/bf.card/">BF.CARD</a>
   * @since Redis Bloom 2.4.4
   */
  // backwards compatibility overload
  default Future<@Nullable Response> bfCard(List<String> args) {
    return send(Command.BF_CARD, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/bf.debug/">BF.DEBUG</a>
   */
  default Future<@Nullable Response> bfDebug(List<String> args) {
    return send(Command.BF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Checks whether an item exists in a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.exists/">BF.EXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfExists(String arg0, String arg1) {
    return send(Command.BF_EXISTS, arg0, arg1);
  }

  /**
   * Checks whether an item exists in a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.exists/">BF.EXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> bfExists(List<String> args) {
    return send(Command.BF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Returns information about a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.info/">BF.INFO</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfInfo(List<String> args) {
    return send(Command.BF_INFO, args.toArray(new String[0]));
  }

  /**
   * Adds one or more items to a Bloom Filter. A filter will be created if it does not exist.
   * @see <a href="https://redis.io/commands/bf.insert/">BF.INSERT</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfInsert(List<String> args) {
    return send(Command.BF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Restores a filter previously saved using SCANDUMP.
   * @see <a href="https://redis.io/commands/bf.loadchunk/">BF.LOADCHUNK</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfLoadchunk(String arg0, String arg1, String arg2) {
    return send(Command.BF_LOADCHUNK, arg0, arg1, arg2);
  }

  /**
   * Restores a filter previously saved using SCANDUMP.
   * @see <a href="https://redis.io/commands/bf.loadchunk/">BF.LOADCHUNK</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> bfLoadchunk(List<String> args) {
    return send(Command.BF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Adds one or more items to a Bloom Filter. A filter will be created if it does not exist.
   * @see <a href="https://redis.io/commands/bf.madd/">BF.MADD</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfMadd(List<String> args) {
    return send(Command.BF_MADD, args.toArray(new String[0]));
  }

  /**
   * Checks whether one or more items exist in a Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.mexists/">BF.MEXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfMexists(List<String> args) {
    return send(Command.BF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Creates a new Bloom Filter.
   * @see <a href="https://redis.io/commands/bf.reserve/">BF.RESERVE</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfReserve(List<String> args) {
    return send(Command.BF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Begins an incremental save of the bloom filter.
   * @see <a href="https://redis.io/commands/bf.scandump/">BF.SCANDUMP</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> bfScandump(String arg0, String arg1) {
    return send(Command.BF_SCANDUMP, arg0, arg1);
  }

  /**
   * Begins an incremental save of the bloom filter.
   * @see <a href="https://redis.io/commands/bf.scandump/">BF.SCANDUMP</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> bfScandump(List<String> args) {
    return send(Command.BF_SCANDUMP, args.toArray(new String[0]));
  }

  /**
   * Asynchronously rewrites the append-only file to disk.
   * @see <a href="https://redis.io/commands/bgrewriteaof/">BGREWRITEAOF</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> bgrewriteaof() {
    return send(Command.BGREWRITEAOF);
  }

  /**
   * Asynchronously saves the database(s) to disk.
   * @see <a href="https://redis.io/commands/bgsave/">BGSAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> bgsave(List<String> args) {
    return send(Command.BGSAVE, args.toArray(new String[0]));
  }

  /**
   * Counts the number of set bits (population counting) in a string.
   * @see <a href="https://redis.io/commands/bitcount/">BITCOUNT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> bitcount(List<String> args) {
    return send(Command.BITCOUNT, args.toArray(new String[0]));
  }

  /**
   * Performs arbitrary bitfield integer operations on strings.
   * @see <a href="https://redis.io/commands/bitfield/">BITFIELD</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> bitfield(List<String> args) {
    return send(Command.BITFIELD, args.toArray(new String[0]));
  }

  /**
   * Performs arbitrary read-only bitfield integer operations on strings.
   * @see <a href="https://redis.io/commands/bitfield_ro/">BITFIELD_RO</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> bitfieldRo(List<String> args) {
    return send(Command.BITFIELD_RO, args.toArray(new String[0]));
  }

  /**
   * Performs bitwise operations on multiple strings, and stores the result.
   * @see <a href="https://redis.io/commands/bitop/">BITOP</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> bitop(List<String> args) {
    return send(Command.BITOP, args.toArray(new String[0]));
  }

  /**
   * Finds the first set (1) or clear (0) bit in a string.
   * @see <a href="https://redis.io/commands/bitpos/">BITPOS</a>
   * @since Redis 2.8.7
   */
  default Future<@Nullable Response> bitpos(List<String> args) {
    return send(Command.BITPOS, args.toArray(new String[0]));
  }

  /**
   * Pops an element from a list, pushes it to another list and returns it. Blocks until an element is available otherwise. Deletes the list if the last element was moved.
   * @see <a href="https://redis.io/commands/blmove/">BLMOVE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> blmove(String arg0, String arg1, String arg2, String arg3, String arg4) {
    return send(Command.BLMOVE, arg0, arg1, arg2, arg3, arg4);
  }

  /**
   * Pops the first element from one of multiple lists. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/blmpop/">BLMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> blmpop(List<String> args) {
    return send(Command.BLMPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the first element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/blpop/">BLPOP</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> blpop(List<String> args) {
    return send(Command.BLPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the last element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/brpop/">BRPOP</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> brpop(List<String> args) {
    return send(Command.BRPOP, args.toArray(new String[0]));
  }

  /**
   * Pops an element from a list, pushes it to another list and returns it. Block until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/brpoplpush/">BRPOPLPUSH</a>
   * @since Redis 2.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code BLMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  default Future<@Nullable Response> brpoplpush(String arg0, String arg1, String arg2) {
    return send(Command.BRPOPLPUSH, arg0, arg1, arg2);
  }

  /**
   * Removes and returns a member by score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @see <a href="https://redis.io/commands/bzmpop/">BZMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> bzmpop(List<String> args) {
    return send(Command.BZMPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the member with the highest score from one or more sorted sets. Blocks until a member available otherwise.  Deletes the sorted set if the last element was popped.
   * @see <a href="https://redis.io/commands/bzpopmax/">BZPOPMAX</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> bzpopmax(List<String> args) {
    return send(Command.BZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the member with the lowest score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @see <a href="https://redis.io/commands/bzpopmin/">BZPOPMIN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> bzpopmin(List<String> args) {
    return send(Command.BZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Adds an item to a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.add/">CF.ADD</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfAdd(String arg0, String arg1) {
    return send(Command.CF_ADD, arg0, arg1);
  }

  /**
   * Adds an item to a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.add/">CF.ADD</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfAdd(List<String> args) {
    return send(Command.CF_ADD, args.toArray(new String[0]));
  }

  /**
   * Adds an item to a Cuckoo Filter if the item did not exist previously.
   * @see <a href="https://redis.io/commands/cf.addnx/">CF.ADDNX</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfAddnx(String arg0, String arg1) {
    return send(Command.CF_ADDNX, arg0, arg1);
  }

  /**
   * Adds an item to a Cuckoo Filter if the item did not exist previously.
   * @see <a href="https://redis.io/commands/cf.addnx/">CF.ADDNX</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfAddnx(List<String> args) {
    return send(Command.CF_ADDNX, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/cf.compact/">CF.COMPACT</a>
   */
  default Future<@Nullable Response> cfCompact(List<String> args) {
    return send(Command.CF_COMPACT, args.toArray(new String[0]));
  }

  /**
   * Return the number of times an item might be in a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.count/">CF.COUNT</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfCount(String arg0, String arg1) {
    return send(Command.CF_COUNT, arg0, arg1);
  }

  /**
   * Return the number of times an item might be in a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.count/">CF.COUNT</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfCount(List<String> args) {
    return send(Command.CF_COUNT, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/cf.debug/">CF.DEBUG</a>
   */
  default Future<@Nullable Response> cfDebug(List<String> args) {
    return send(Command.CF_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Deletes an item from a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.del/">CF.DEL</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfDel(String arg0, String arg1) {
    return send(Command.CF_DEL, arg0, arg1);
  }

  /**
   * Deletes an item from a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.del/">CF.DEL</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfDel(List<String> args) {
    return send(Command.CF_DEL, args.toArray(new String[0]));
  }

  /**
   * Checks whether one or more items exist in a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.exists/">CF.EXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfExists(String arg0, String arg1) {
    return send(Command.CF_EXISTS, arg0, arg1);
  }

  /**
   * Checks whether one or more items exist in a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.exists/">CF.EXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfExists(List<String> args) {
    return send(Command.CF_EXISTS, args.toArray(new String[0]));
  }

  /**
   * Returns information about a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.info/">CF.INFO</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfInfo(String arg0) {
    return send(Command.CF_INFO, arg0);
  }

  /**
   * Returns information about a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.info/">CF.INFO</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfInfo(List<String> args) {
    return send(Command.CF_INFO, args.toArray(new String[0]));
  }

  /**
   * Adds one or more items to a Cuckoo Filter. A filter will be created if it does not exist.
   * @see <a href="https://redis.io/commands/cf.insert/">CF.INSERT</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfInsert(List<String> args) {
    return send(Command.CF_INSERT, args.toArray(new String[0]));
  }

  /**
   * Adds one or more items to a Cuckoo Filter if the items did not exist previously. A filter will be created if it does not exist.
   * @see <a href="https://redis.io/commands/cf.insertnx/">CF.INSERTNX</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfInsertnx(List<String> args) {
    return send(Command.CF_INSERTNX, args.toArray(new String[0]));
  }

  /**
   * Restores a filter previously saved using SCANDUMP.
   * @see <a href="https://redis.io/commands/cf.loadchunk/">CF.LOADCHUNK</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfLoadchunk(String arg0, String arg1, String arg2) {
    return send(Command.CF_LOADCHUNK, arg0, arg1, arg2);
  }

  /**
   * Restores a filter previously saved using SCANDUMP.
   * @see <a href="https://redis.io/commands/cf.loadchunk/">CF.LOADCHUNK</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfLoadchunk(List<String> args) {
    return send(Command.CF_LOADCHUNK, args.toArray(new String[0]));
  }

  /**
   * Checks whether one or more items exist in a Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.mexists/">CF.MEXISTS</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfMexists(List<String> args) {
    return send(Command.CF_MEXISTS, args.toArray(new String[0]));
  }

  /**
   * Creates a new Cuckoo Filter.
   * @see <a href="https://redis.io/commands/cf.reserve/">CF.RESERVE</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfReserve(List<String> args) {
    return send(Command.CF_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Begins an incremental save of the bloom filter.
   * @see <a href="https://redis.io/commands/cf.scandump/">CF.SCANDUMP</a>
   * @since Redis Bloom 1.0.0
   */
  default Future<@Nullable Response> cfScandump(String arg0, String arg1) {
    return send(Command.CF_SCANDUMP, arg0, arg1);
  }

  /**
   * Begins an incremental save of the bloom filter.
   * @see <a href="https://redis.io/commands/cf.scandump/">CF.SCANDUMP</a>
   * @since Redis Bloom 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cfScandump(List<String> args) {
    return send(Command.CF_SCANDUMP, args.toArray(new String[0]));
  }

  /**
   * A container for client connection commands.
   * @see <a href="https://redis.io/commands/client/">CLIENT</a>
   * @since Redis 2.4.0
   */
  default Future<@Nullable Response> client(List<String> args) {
    return send(Command.CLIENT, args.toArray(new String[0]));
  }

  /**
   * A container for Redis Cluster commands.
   * @see <a href="https://redis.io/commands/cluster/">CLUSTER</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> cluster(List<String> args) {
    return send(Command.CLUSTER, args.toArray(new String[0]));
  }

  /**
   * Increases the count of one or more items by increment.
   * @see <a href="https://redis.io/commands/cms.incrby/">CMS.INCRBY</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsIncrby(List<String> args) {
    return send(Command.CMS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Returns information about a sketch.
   * @see <a href="https://redis.io/commands/cms.info/">CMS.INFO</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsInfo(String arg0) {
    return send(Command.CMS_INFO, arg0);
  }

  /**
   * Returns information about a sketch.
   * @see <a href="https://redis.io/commands/cms.info/">CMS.INFO</a>
   * @since Redis Bloom 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cmsInfo(List<String> args) {
    return send(Command.CMS_INFO, args.toArray(new String[0]));
  }

  /**
   * Initializes a Count-Min Sketch to dimensions specified by user.
   * @see <a href="https://redis.io/commands/cms.initbydim/">CMS.INITBYDIM</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsInitbydim(String arg0, String arg1, String arg2) {
    return send(Command.CMS_INITBYDIM, arg0, arg1, arg2);
  }

  /**
   * Initializes a Count-Min Sketch to dimensions specified by user.
   * @see <a href="https://redis.io/commands/cms.initbydim/">CMS.INITBYDIM</a>
   * @since Redis Bloom 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cmsInitbydim(List<String> args) {
    return send(Command.CMS_INITBYDIM, args.toArray(new String[0]));
  }

  /**
   * Initializes a Count-Min Sketch to accommodate requested tolerances.
   * @see <a href="https://redis.io/commands/cms.initbyprob/">CMS.INITBYPROB</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsInitbyprob(String arg0, String arg1, String arg2) {
    return send(Command.CMS_INITBYPROB, arg0, arg1, arg2);
  }

  /**
   * Initializes a Count-Min Sketch to accommodate requested tolerances.
   * @see <a href="https://redis.io/commands/cms.initbyprob/">CMS.INITBYPROB</a>
   * @since Redis Bloom 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> cmsInitbyprob(List<String> args) {
    return send(Command.CMS_INITBYPROB, args.toArray(new String[0]));
  }

  /**
   * Merges several sketches into one sketch.
   * @see <a href="https://redis.io/commands/cms.merge/">CMS.MERGE</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsMerge(List<String> args) {
    return send(Command.CMS_MERGE, args.toArray(new String[0]));
  }

  /**
   * Returns the count for one or more items in a sketch.
   * @see <a href="https://redis.io/commands/cms.query/">CMS.QUERY</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> cmsQuery(List<String> args) {
    return send(Command.CMS_QUERY, args.toArray(new String[0]));
  }

  /**
   * Returns detailed information about all commands.
   * @see <a href="https://redis.io/commands/command/">COMMAND</a>
   * @since Redis 2.8.13
   */
  default Future<@Nullable Response> command(List<String> args) {
    return send(Command.COMMAND, args.toArray(new String[0]));
  }

  /**
   * A container for server configuration commands.
   * @see <a href="https://redis.io/commands/config/">CONFIG</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> config(List<String> args) {
    return send(Command.CONFIG, args.toArray(new String[0]));
  }

  /**
   * Copies the value of a key to a new key.
   * @see <a href="https://redis.io/commands/copy/">COPY</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> copy(List<String> args) {
    return send(Command.COPY, args.toArray(new String[0]));
  }

  /**
   * Returns the number of keys in the database.
   * @see <a href="https://redis.io/commands/dbsize/">DBSIZE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> dbsize() {
    return send(Command.DBSIZE);
  }

  /**
   * A container for debugging commands.
   * @see <a href="https://redis.io/commands/debug/">DEBUG</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> debug(List<String> args) {
    return send(Command.DEBUG, args.toArray(new String[0]));
  }

  /**
   * Decrements the integer value of a key by one. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/decr/">DECR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> decr(String arg0) {
    return send(Command.DECR, arg0);
  }

  /**
   * Decrements a number from the integer value of a key. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/decrby/">DECRBY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> decrby(String arg0, String arg1) {
    return send(Command.DECRBY, arg0, arg1);
  }

  /**
   * Deletes one or more keys.
   * @see <a href="https://redis.io/commands/del/">DEL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> del(List<String> args) {
    return send(Command.DEL, args.toArray(new String[0]));
  }

  /**
   * Conditionally removes the specified key based on value or digest comparison.
   * @see <a href="https://redis.io/commands/delex/">DELEX</a>
   * @since Redis 8.4.0
   */
  default Future<@Nullable Response> delex(List<String> args) {
    return send(Command.DELEX, args.toArray(new String[0]));
  }

  /**
   * Returns the XXH3 hash of a string value.
   * @see <a href="https://redis.io/commands/digest/">DIGEST</a>
   * @since Redis 8.4.0
   */
  default Future<@Nullable Response> digest(String arg0) {
    return send(Command.DIGEST, arg0);
  }

  /**
   * Discards a transaction.
   * @see <a href="https://redis.io/commands/discard/">DISCARD</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> discard() {
    return send(Command.DISCARD);
  }

  /**
   * Returns a serialized representation of the value stored at a key.
   * @see <a href="https://redis.io/commands/dump/">DUMP</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> dump(String arg0) {
    return send(Command.DUMP, arg0);
  }

  /**
   * Returns the given string.
   * @see <a href="https://redis.io/commands/echo/">ECHO</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> echo(String arg0) {
    return send(Command.ECHO, arg0);
  }

  /**
   * Executes a server-side Lua script.
   * @see <a href="https://redis.io/commands/eval/">EVAL</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> eval(List<String> args) {
    return send(Command.EVAL, args.toArray(new String[0]));
  }

  /**
   * Executes a read-only server-side Lua script.
   * @see <a href="https://redis.io/commands/eval_ro/">EVAL_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> evalRo(List<String> args) {
    return send(Command.EVAL_RO, args.toArray(new String[0]));
  }

  /**
   * Executes a server-side Lua script by SHA1 digest.
   * @see <a href="https://redis.io/commands/evalsha/">EVALSHA</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> evalsha(List<String> args) {
    return send(Command.EVALSHA, args.toArray(new String[0]));
  }

  /**
   * Executes a read-only server-side Lua script by SHA1 digest.
   * @see <a href="https://redis.io/commands/evalsha_ro/">EVALSHA_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> evalshaRo(List<String> args) {
    return send(Command.EVALSHA_RO, args.toArray(new String[0]));
  }

  /**
   * Executes all commands in a transaction.
   * @see <a href="https://redis.io/commands/exec/">EXEC</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> exec() {
    return send(Command.EXEC);
  }

  /**
   * Determines whether one or more keys exist.
   * @see <a href="https://redis.io/commands/exists/">EXISTS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> exists(List<String> args) {
    return send(Command.EXISTS, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key in seconds.
   * @see <a href="https://redis.io/commands/expire/">EXPIRE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> expire(List<String> args) {
    return send(Command.EXPIRE, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key to a Unix timestamp.
   * @see <a href="https://redis.io/commands/expireat/">EXPIREAT</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> expireat(List<String> args) {
    return send(Command.EXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a key as a Unix timestamp.
   * @see <a href="https://redis.io/commands/expiretime/">EXPIRETIME</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> expiretime(String arg0) {
    return send(Command.EXPIRETIME, arg0);
  }

  /**
   * Starts a coordinated failover from a server to one of its replicas.
   * @see <a href="https://redis.io/commands/failover/">FAILOVER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> failover(List<String> args) {
    return send(Command.FAILOVER, args.toArray(new String[0]));
  }

  /**
   * Invokes a function.
   * @see <a href="https://redis.io/commands/fcall/">FCALL</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> fcall(List<String> args) {
    return send(Command.FCALL, args.toArray(new String[0]));
  }

  /**
   * Invokes a read-only function.
   * @see <a href="https://redis.io/commands/fcall_ro/">FCALL_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> fcallRo(List<String> args) {
    return send(Command.FCALL_RO, args.toArray(new String[0]));
  }

  /**
   * Removes all keys from all databases.
   * @see <a href="https://redis.io/commands/flushall/">FLUSHALL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> flushall(List<String> args) {
    return send(Command.FLUSHALL, args.toArray(new String[0]));
  }

  /**
   * Remove all keys from the current database.
   * @see <a href="https://redis.io/commands/flushdb/">FLUSHDB</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> flushdb(List<String> args) {
    return send(Command.FLUSHDB, args.toArray(new String[0]));
  }

  /**
   * A container for function commands.
   * @see <a href="https://redis.io/commands/function/">FUNCTION</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> function(List<String> args) {
    return send(Command.FUNCTION, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a geospatial index. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/geoadd/">GEOADD</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geoadd(List<String> args) {
    return send(Command.GEOADD, args.toArray(new String[0]));
  }

  /**
   * Returns the distance between two members of a geospatial index.
   * @see <a href="https://redis.io/commands/geodist/">GEODIST</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geodist(List<String> args) {
    return send(Command.GEODIST, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index as geohash strings.
   * @see <a href="https://redis.io/commands/geohash/">GEOHASH</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geohash(List<String> args) {
    return send(Command.GEOHASH, args.toArray(new String[0]));
  }

  /**
   * Returns the longitude and latitude of members from a geospatial index.
   * @see <a href="https://redis.io/commands/geopos/">GEOPOS</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geopos(List<String> args) {
    return send(Command.GEOPOS, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members within a distance from a coordinate, optionally stores the result.
   * @see <a href="https://redis.io/commands/georadius/">GEORADIUS</a>
   * @since Redis 3.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} argument
   */
  default Future<@Nullable Response> georadius(List<String> args) {
    return send(Command.GEORADIUS, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index that are within a distance from a coordinate.
   * @see <a href="https://redis.io/commands/georadius_ro/">GEORADIUS_RO</a>
   * @since Redis 3.2.10
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} argument
   */
  default Future<@Nullable Response> georadiusRo(List<String> args) {
    return send(Command.GEORADIUS_RO, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members within a distance from a member, optionally stores the result.
   * @see <a href="https://redis.io/commands/georadiusbymember/">GEORADIUSBYMEMBER</a>
   * @since Redis 3.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  default Future<@Nullable Response> georadiusbymember(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index that are within a distance from a member.
   * @see <a href="https://redis.io/commands/georadiusbymember_ro/">GEORADIUSBYMEMBER_RO</a>
   * @since Redis 3.2.10
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  default Future<@Nullable Response> georadiusbymemberRo(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members inside an area of a box or a circle.
   * @see <a href="https://redis.io/commands/geosearch/">GEOSEARCH</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> geosearch(List<String> args) {
    return send(Command.GEOSEARCH, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members inside an area of a box or a circle, optionally stores the result.
   * @see <a href="https://redis.io/commands/geosearchstore/">GEOSEARCHSTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> geosearchstore(List<String> args) {
    return send(Command.GEOSEARCHSTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the string value of a key.
   * @see <a href="https://redis.io/commands/get/">GET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> get(String arg0) {
    return send(Command.GET, arg0);
  }

  /**
   * Returns a bit value by offset.
   * @see <a href="https://redis.io/commands/getbit/">GETBIT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> getbit(String arg0, String arg1) {
    return send(Command.GETBIT, arg0, arg1);
  }

  /**
   * Returns the string value of a key after deleting the key.
   * @see <a href="https://redis.io/commands/getdel/">GETDEL</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> getdel(String arg0) {
    return send(Command.GETDEL, arg0);
  }

  /**
   * Returns the string value of a key after setting its expiration time.
   * @see <a href="https://redis.io/commands/getex/">GETEX</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> getex(List<String> args) {
    return send(Command.GETEX, args.toArray(new String[0]));
  }

  /**
   * Returns a substring of the string stored at a key.
   * @see <a href="https://redis.io/commands/getrange/">GETRANGE</a>
   * @since Redis 2.4.0
   */
  default Future<@Nullable Response> getrange(String arg0, String arg1, String arg2) {
    return send(Command.GETRANGE, arg0, arg1, arg2);
  }

  /**
   * Returns the previous string value of a key after setting it to a new value.
   * @see <a href="https://redis.io/commands/getset/">GETSET</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 6.2.0, replaced by: {@code SET} with the {@code !GET} argument
   */
  default Future<@Nullable Response> getset(String arg0, String arg1) {
    return send(Command.GETSET, arg0, arg1);
  }

  /**
   * @see <a href="https://redis.io/commands/graph.bulk/">GRAPH.BULK</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphBulk(List<String> args) {
    return send(Command.GRAPH_BULK, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.config/">GRAPH.CONFIG</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphConfig(List<String> args) {
    return send(Command.GRAPH_CONFIG, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.debug/">GRAPH.DEBUG</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphDebug(List<String> args) {
    return send(Command.GRAPH_DEBUG, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.delete/">GRAPH.DELETE</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphDelete(List<String> args) {
    return send(Command.GRAPH_DELETE, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.explain/">GRAPH.EXPLAIN</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphExplain(List<String> args) {
    return send(Command.GRAPH_EXPLAIN, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.list/">GRAPH.LIST</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphList(List<String> args) {
    return send(Command.GRAPH_LIST, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.profile/">GRAPH.PROFILE</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphProfile(List<String> args) {
    return send(Command.GRAPH_PROFILE, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.query/">GRAPH.QUERY</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphQuery(List<String> args) {
    return send(Command.GRAPH_QUERY, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.ro_query/">GRAPH.RO_QUERY</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphRoQuery(List<String> args) {
    return send(Command.GRAPH_RO_QUERY, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/graph.slowlog/">GRAPH.SLOWLOG</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> graphSlowlog(List<String> args) {
    return send(Command.GRAPH_SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Deletes one or more fields and their values from a hash. Deletes the hash if no fields remain.
   * @see <a href="https://redis.io/commands/hdel/">HDEL</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hdel(List<String> args) {
    return send(Command.HDEL, args.toArray(new String[0]));
  }

  /**
   * Handshakes with the Redis server.
   * @see <a href="https://redis.io/commands/hello/">HELLO</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> hello(List<String> args) {
    return send(Command.HELLO, args.toArray(new String[0]));
  }

  /**
   * Determines whether a field exists in a hash.
   * @see <a href="https://redis.io/commands/hexists/">HEXISTS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hexists(String arg0, String arg1) {
    return send(Command.HEXISTS, arg0, arg1);
  }

  /**
   * Set expiry for hash field using relative time to expire (seconds).
   * @see <a href="https://redis.io/commands/hexpire/">HEXPIRE</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hexpire(List<String> args) {
    return send(Command.HEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Set expiry for hash field using an absolute Unix timestamp (seconds).
   * @see <a href="https://redis.io/commands/hexpireat/">HEXPIREAT</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hexpireat(List<String> args) {
    return send(Command.HEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a hash field as a Unix timestamp, in seconds.
   * @see <a href="https://redis.io/commands/hexpiretime/">HEXPIRETIME</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hexpiretime(List<String> args) {
    return send(Command.HEXPIRETIME, args.toArray(new String[0]));
  }

  /**
   * Returns the value of a field in a hash.
   * @see <a href="https://redis.io/commands/hget/">HGET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hget(String arg0, String arg1) {
    return send(Command.HGET, arg0, arg1);
  }

  /**
   * Returns all fields and values in a hash.
   * @see <a href="https://redis.io/commands/hgetall/">HGETALL</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hgetall(String arg0) {
    return send(Command.HGETALL, arg0);
  }

  /**
   * Returns the value of a field and deletes it from the hash.
   * @see <a href="https://redis.io/commands/hgetdel/">HGETDEL</a>
   * @since Redis 8.0.0
   */
  default Future<@Nullable Response> hgetdel(List<String> args) {
    return send(Command.HGETDEL, args.toArray(new String[0]));
  }

  /**
   * Get the value of one or more fields of a given hash key, and optionally set their expiration.
   * @see <a href="https://redis.io/commands/hgetex/">HGETEX</a>
   * @since Redis 8.0.0
   */
  default Future<@Nullable Response> hgetex(List<String> args) {
    return send(Command.HGETEX, args.toArray(new String[0]));
  }

  /**
   * Increments the integer value of a field in a hash by a number. Uses 0 as initial value if the field doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/hincrby/">HINCRBY</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hincrby(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBY, arg0, arg1, arg2);
  }

  /**
   * Increments the floating point value of a field by a number. Uses 0 as initial value if the field doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/hincrbyfloat/">HINCRBYFLOAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> hincrbyfloat(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBYFLOAT, arg0, arg1, arg2);
  }

  /**
   * Returns all fields in a hash.
   * @see <a href="https://redis.io/commands/hkeys/">HKEYS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hkeys(String arg0) {
    return send(Command.HKEYS, arg0);
  }

  /**
   * Returns the number of fields in a hash.
   * @see <a href="https://redis.io/commands/hlen/">HLEN</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hlen(String arg0) {
    return send(Command.HLEN, arg0);
  }

  /**
   * Returns the values of all fields in a hash.
   * @see <a href="https://redis.io/commands/hmget/">HMGET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hmget(List<String> args) {
    return send(Command.HMGET, args.toArray(new String[0]));
  }

  /**
   * Sets the values of multiple fields.
   * @see <a href="https://redis.io/commands/hmset/">HMSET</a>
   * @since Redis 2.0.0
   * @deprecated since Redis 4.0.0, replaced by: {@code HSET} with multiple field-value pairs
   */
  default Future<@Nullable Response> hmset(List<String> args) {
    return send(Command.HMSET, args.toArray(new String[0]));
  }

  /**
   * A container for hotkeys tracking commands.
   * @see <a href="https://redis.io/commands/hotkeys/">HOTKEYS</a>
   * @since Redis 8.6.0
   */
  default Future<@Nullable Response> hotkeys(List<String> args) {
    return send(Command.HOTKEYS, args.toArray(new String[0]));
  }

  /**
   * Removes the expiration time for each specified field.
   * @see <a href="https://redis.io/commands/hpersist/">HPERSIST</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hpersist(List<String> args) {
    return send(Command.HPERSIST, args.toArray(new String[0]));
  }

  /**
   * Set expiry for hash field using relative time to expire (milliseconds).
   * @see <a href="https://redis.io/commands/hpexpire/">HPEXPIRE</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hpexpire(List<String> args) {
    return send(Command.HPEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Set expiry for hash field using an absolute Unix timestamp (milliseconds).
   * @see <a href="https://redis.io/commands/hpexpireat/">HPEXPIREAT</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hpexpireat(List<String> args) {
    return send(Command.HPEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a hash field as a Unix timestamp, in msec.
   * @see <a href="https://redis.io/commands/hpexpiretime/">HPEXPIRETIME</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hpexpiretime(List<String> args) {
    return send(Command.HPEXPIRETIME, args.toArray(new String[0]));
  }

  /**
   * Returns the TTL in milliseconds of a hash field.
   * @see <a href="https://redis.io/commands/hpttl/">HPTTL</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> hpttl(List<String> args) {
    return send(Command.HPTTL, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random fields from a hash.
   * @see <a href="https://redis.io/commands/hrandfield/">HRANDFIELD</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> hrandfield(List<String> args) {
    return send(Command.HRANDFIELD, args.toArray(new String[0]));
  }

  /**
   * Iterates over fields and values of a hash.
   * @see <a href="https://redis.io/commands/hscan/">HSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> hscan(List<String> args) {
    return send(Command.HSCAN, args.toArray(new String[0]));
  }

  /**
   * Creates or modifies the value of a field in a hash.
   * @see <a href="https://redis.io/commands/hset/">HSET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hset(List<String> args) {
    return send(Command.HSET, args.toArray(new String[0]));
  }

  /**
   * Set the value of one or more fields of a given hash key, and optionally set their expiration.
   * @see <a href="https://redis.io/commands/hsetex/">HSETEX</a>
   * @since Redis 8.0.0
   */
  default Future<@Nullable Response> hsetex(List<String> args) {
    return send(Command.HSETEX, args.toArray(new String[0]));
  }

  /**
   * Sets the value of a field in a hash only when the field doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/hsetnx/">HSETNX</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hsetnx(String arg0, String arg1, String arg2) {
    return send(Command.HSETNX, arg0, arg1, arg2);
  }

  /**
   * Returns the length of the value of a field.
   * @see <a href="https://redis.io/commands/hstrlen/">HSTRLEN</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> hstrlen(String arg0, String arg1) {
    return send(Command.HSTRLEN, arg0, arg1);
  }

  /**
   * Returns the TTL in seconds of a hash field.
   * @see <a href="https://redis.io/commands/httl/">HTTL</a>
   * @since Redis 7.4.0
   */
  default Future<@Nullable Response> httl(List<String> args) {
    return send(Command.HTTL, args.toArray(new String[0]));
  }

  /**
   * Returns all values in a hash.
   * @see <a href="https://redis.io/commands/hvals/">HVALS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hvals(String arg0) {
    return send(Command.HVALS, arg0);
  }

  /**
   * Increments the integer value of a key by one. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/incr/">INCR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> incr(String arg0) {
    return send(Command.INCR, arg0);
  }

  /**
   * Increments the integer value of a key by a number. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/incrby/">INCRBY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> incrby(String arg0, String arg1) {
    return send(Command.INCRBY, arg0, arg1);
  }

  /**
   * Increment the floating point value of a key by a number. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/incrbyfloat/">INCRBYFLOAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> incrbyfloat(String arg0, String arg1) {
    return send(Command.INCRBYFLOAT, arg0, arg1);
  }

  /**
   * Returns information and statistics about the server.
   * @see <a href="https://redis.io/commands/info/">INFO</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> info(List<String> args) {
    return send(Command.INFO, args.toArray(new String[0]));
  }

  /**
   * Append the JSON values into the array at path after the last element in it.
   * @see <a href="https://redis.io/commands/json.arrappend/">JSON.ARRAPPEND</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrappend(List<String> args) {
    return send(Command.JSON_ARRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Search for the first occurrence of a JSON value in an array.
   * @see <a href="https://redis.io/commands/json.arrindex/">JSON.ARRINDEX</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrindex(List<String> args) {
    return send(Command.JSON_ARRINDEX, args.toArray(new String[0]));
  }

  /**
   * Insert the json values into the array at path before the index (shifts to the right).
   * @see <a href="https://redis.io/commands/json.arrinsert/">JSON.ARRINSERT</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrinsert(List<String> args) {
    return send(Command.JSON_ARRINSERT, args.toArray(new String[0]));
  }

  /**
   * Report the length of the JSON array at path in key.
   * @see <a href="https://redis.io/commands/json.arrlen/">JSON.ARRLEN</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrlen(List<String> args) {
    return send(Command.JSON_ARRLEN, args.toArray(new String[0]));
  }

  /**
   * Remove and return the element at the specified index in the array at path.
   * @see <a href="https://redis.io/commands/json.arrpop/">JSON.ARRPOP</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrpop(List<String> args) {
    return send(Command.JSON_ARRPOP, args.toArray(new String[0]));
  }

  /**
   * Trim an array so that it contains only the specified inclusive range of elements.
   * @see <a href="https://redis.io/commands/json.arrtrim/">JSON.ARRTRIM</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonArrtrim(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.JSON_ARRTRIM, arg0, arg1, arg2, arg3);
  }

  /**
   * Trim an array so that it contains only the specified inclusive range of elements.
   * @see <a href="https://redis.io/commands/json.arrtrim/">JSON.ARRTRIM</a>
   * @since Redis ReJSON 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> jsonArrtrim(List<String> args) {
    return send(Command.JSON_ARRTRIM, args.toArray(new String[0]));
  }

  /**
   * Clear container values (arrays/objects) and set numeric values to 0.
   * @see <a href="https://redis.io/commands/json.clear/">JSON.CLEAR</a>
   * @since Redis ReJSON 2.0.0
   */
  default Future<@Nullable Response> jsonClear(List<String> args) {
    return send(Command.JSON_CLEAR, args.toArray(new String[0]));
  }

  /**
   * This is a container command for debugging related tasks.
   * @see <a href="https://redis.io/commands/json.debug/">JSON.DEBUG</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonDebug(List<String> args) {
    return send(Command.JSON_DEBUG, args.toArray(new String[0]));
  }

  /**
   * Delete a value.
   * @see <a href="https://redis.io/commands/json.del/">JSON.DEL</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonDel(List<String> args) {
    return send(Command.JSON_DEL, args.toArray(new String[0]));
  }

  /**
   * Delete a value.
   * @see <a href="https://redis.io/commands/json.forget/">JSON.FORGET</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonForget(List<String> args) {
    return send(Command.JSON_FORGET, args.toArray(new String[0]));
  }

  /**
   * Get JSON value at path.
   * @see <a href="https://redis.io/commands/json.get/">JSON.GET</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonGet(List<String> args) {
    return send(Command.JSON_GET, args.toArray(new String[0]));
  }

  /**
   * Merge a given JSON value into matching paths. Consequently, JSON values at matching paths are updated, deleted, or expanded with new children.
   * @see <a href="https://redis.io/commands/json.merge/">JSON.MERGE</a>
   * @since Redis ReJSON 2.6.0
   */
  default Future<@Nullable Response> jsonMerge(List<String> args) {
    return send(Command.JSON_MERGE, args.toArray(new String[0]));
  }

  /**
   * Return the values at path from multiple key arguments.
   * @see <a href="https://redis.io/commands/json.mget/">JSON.MGET</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonMget(List<String> args) {
    return send(Command.JSON_MGET, args.toArray(new String[0]));
  }

  /**
   * Set or update one or more JSON values according to the specified key-path-value triplets.
   * @see <a href="https://redis.io/commands/json.mset/">JSON.MSET</a>
   * @since Redis ReJSON 2.6.0
   */
  default Future<@Nullable Response> jsonMset(List<String> args) {
    return send(Command.JSON_MSET, args.toArray(new String[0]));
  }

  /**
   * Increment the number value stored at path by number.
   * @see <a href="https://redis.io/commands/json.numincrby/">JSON.NUMINCRBY</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonNumincrby(String arg0, String arg1, String arg2) {
    return send(Command.JSON_NUMINCRBY, arg0, arg1, arg2);
  }

  /**
   * Increment the number value stored at path by number.
   * @see <a href="https://redis.io/commands/json.numincrby/">JSON.NUMINCRBY</a>
   * @since Redis ReJSON 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> jsonNumincrby(List<String> args) {
    return send(Command.JSON_NUMINCRBY, args.toArray(new String[0]));
  }

  /**
   * Multiply the number value stored at path by number.
   * @see <a href="https://redis.io/commands/json.nummultby/">JSON.NUMMULTBY</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonNummultby(String arg0, String arg1, String arg2) {
    return send(Command.JSON_NUMMULTBY, arg0, arg1, arg2);
  }

  /**
   * Multiply the number value stored at path by number.
   * @see <a href="https://redis.io/commands/json.nummultby/">JSON.NUMMULTBY</a>
   * @since Redis ReJSON 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> jsonNummultby(List<String> args) {
    return send(Command.JSON_NUMMULTBY, args.toArray(new String[0]));
  }

  /**
   * Raise the number value stored at path to the power of number.
   * @see <a href="https://redis.io/commands/json.numpowby/">JSON.NUMPOWBY</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonNumpowby(String arg0, String arg1, String arg2) {
    return send(Command.JSON_NUMPOWBY, arg0, arg1, arg2);
  }

  /**
   * Raise the number value stored at path to the power of number.
   * @see <a href="https://redis.io/commands/json.numpowby/">JSON.NUMPOWBY</a>
   * @since Redis ReJSON 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> jsonNumpowby(List<String> args) {
    return send(Command.JSON_NUMPOWBY, args.toArray(new String[0]));
  }

  /**
   * Return the keys in the object that&#x27;s referenced by path.
   * @see <a href="https://redis.io/commands/json.objkeys/">JSON.OBJKEYS</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonObjkeys(List<String> args) {
    return send(Command.JSON_OBJKEYS, args.toArray(new String[0]));
  }

  /**
   * Report the number of keys in the JSON object at path in key.
   * @see <a href="https://redis.io/commands/json.objlen/">JSON.OBJLEN</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonObjlen(List<String> args) {
    return send(Command.JSON_OBJLEN, args.toArray(new String[0]));
  }

  /**
   * Return the JSON in key in Redis serialization protocol specification form.
   * @see <a href="https://redis.io/commands/json.resp/">JSON.RESP</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonResp(List<String> args) {
    return send(Command.JSON_RESP, args.toArray(new String[0]));
  }

  /**
   * Set the JSON value at path in key.
   * @see <a href="https://redis.io/commands/json.set/">JSON.SET</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonSet(List<String> args) {
    return send(Command.JSON_SET, args.toArray(new String[0]));
  }

  /**
   * Append the json-string values to the string at path.
   * @see <a href="https://redis.io/commands/json.strappend/">JSON.STRAPPEND</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonStrappend(List<String> args) {
    return send(Command.JSON_STRAPPEND, args.toArray(new String[0]));
  }

  /**
   * Report the length of the JSON String at path in key.
   * @see <a href="https://redis.io/commands/json.strlen/">JSON.STRLEN</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonStrlen(List<String> args) {
    return send(Command.JSON_STRLEN, args.toArray(new String[0]));
  }

  /**
   * Toggle the boolean value stored at path.
   * @see <a href="https://redis.io/commands/json.toggle/">JSON.TOGGLE</a>
   * @since Redis ReJSON 2.0.0
   */
  default Future<@Nullable Response> jsonToggle(String arg0, String arg1) {
    return send(Command.JSON_TOGGLE, arg0, arg1);
  }

  /**
   * Toggle the boolean value stored at path.
   * @see <a href="https://redis.io/commands/json.toggle/">JSON.TOGGLE</a>
   * @since Redis ReJSON 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> jsonToggle(List<String> args) {
    return send(Command.JSON_TOGGLE, args.toArray(new String[0]));
  }

  /**
   * Report the type of JSON value at path.
   * @see <a href="https://redis.io/commands/json.type/">JSON.TYPE</a>
   * @since Redis ReJSON 1.0.0
   */
  default Future<@Nullable Response> jsonType(List<String> args) {
    return send(Command.JSON_TYPE, args.toArray(new String[0]));
  }

  /**
   * Returns all key names that match a pattern.
   * @see <a href="https://redis.io/commands/keys/">KEYS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> keys(String arg0) {
    return send(Command.KEYS, arg0);
  }

  /**
   * Returns the Unix timestamp of the last successful save to disk.
   * @see <a href="https://redis.io/commands/lastsave/">LASTSAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lastsave() {
    return send(Command.LASTSAVE);
  }

  /**
   * A container for latency diagnostics commands.
   * @see <a href="https://redis.io/commands/latency/">LATENCY</a>
   * @since Redis 2.8.13
   */
  default Future<@Nullable Response> latency(List<String> args) {
    return send(Command.LATENCY, args.toArray(new String[0]));
  }

  /**
   * Finds the longest common substring.
   * @see <a href="https://redis.io/commands/lcs/">LCS</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> lcs(List<String> args) {
    return send(Command.LCS, args.toArray(new String[0]));
  }

  /**
   * Returns an element from a list by its index.
   * @see <a href="https://redis.io/commands/lindex/">LINDEX</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lindex(String arg0, String arg1) {
    return send(Command.LINDEX, arg0, arg1);
  }

  /**
   * Inserts an element before or after another element in a list.
   * @see <a href="https://redis.io/commands/linsert/">LINSERT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> linsert(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LINSERT, arg0, arg1, arg2, arg3);
  }

  /**
   * Returns the length of a list.
   * @see <a href="https://redis.io/commands/llen/">LLEN</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> llen(String arg0) {
    return send(Command.LLEN, arg0);
  }

  /**
   * Returns an element after popping it from one list and pushing it to another. Deletes the list if the last element was moved.
   * @see <a href="https://redis.io/commands/lmove/">LMOVE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> lmove(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LMOVE, arg0, arg1, arg2, arg3);
  }

  /**
   * Returns multiple elements from a list after removing them. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/lmpop/">LMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> lmpop(List<String> args) {
    return send(Command.LMPOP, args.toArray(new String[0]));
  }

  /**
   * Displays computer art and the Redis version.
   * @see <a href="https://redis.io/commands/lolwut/">LOLWUT</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> lolwut(List<String> args) {
    return send(Command.LOLWUT, args.toArray(new String[0]));
  }

  /**
   * Returns the first elements in a list after removing it. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/lpop/">LPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lpop(List<String> args) {
    return send(Command.LPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the index of matching elements in a list.
   * @see <a href="https://redis.io/commands/lpos/">LPOS</a>
   * @since Redis 6.0.6
   */
  default Future<@Nullable Response> lpos(List<String> args) {
    return send(Command.LPOS, args.toArray(new String[0]));
  }

  /**
   * Prepends one or more elements to a list. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/lpush/">LPUSH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lpush(List<String> args) {
    return send(Command.LPUSH, args.toArray(new String[0]));
  }

  /**
   * Prepends one or more elements to a list only when the list exists.
   * @see <a href="https://redis.io/commands/lpushx/">LPUSHX</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> lpushx(List<String> args) {
    return send(Command.LPUSHX, args.toArray(new String[0]));
  }

  /**
   * Returns a range of elements from a list.
   * @see <a href="https://redis.io/commands/lrange/">LRANGE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lrange(String arg0, String arg1, String arg2) {
    return send(Command.LRANGE, arg0, arg1, arg2);
  }

  /**
   * Removes elements from a list. Deletes the list if the last element was removed.
   * @see <a href="https://redis.io/commands/lrem/">LREM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lrem(String arg0, String arg1, String arg2) {
    return send(Command.LREM, arg0, arg1, arg2);
  }

  /**
   * Sets the value of an element in a list by its index.
   * @see <a href="https://redis.io/commands/lset/">LSET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lset(String arg0, String arg1, String arg2) {
    return send(Command.LSET, arg0, arg1, arg2);
  }

  /**
   * Removes elements from both ends a list. Deletes the list if all elements were trimmed.
   * @see <a href="https://redis.io/commands/ltrim/">LTRIM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ltrim(String arg0, String arg1, String arg2) {
    return send(Command.LTRIM, arg0, arg1, arg2);
  }

  /**
   * A container for memory diagnostics commands.
   * @see <a href="https://redis.io/commands/memory/">MEMORY</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> memory(List<String> args) {
    return send(Command.MEMORY, args.toArray(new String[0]));
  }

  /**
   * Atomically returns the string values of one or more keys.
   * @see <a href="https://redis.io/commands/mget/">MGET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> mget(List<String> args) {
    return send(Command.MGET, args.toArray(new String[0]));
  }

  /**
   * Atomically transfers a key from one Redis instance to another.
   * @see <a href="https://redis.io/commands/migrate/">MIGRATE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> migrate(List<String> args) {
    return send(Command.MIGRATE, args.toArray(new String[0]));
  }

  /**
   * A container for module commands.
   * @see <a href="https://redis.io/commands/module/">MODULE</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> module(List<String> args) {
    return send(Command.MODULE, args.toArray(new String[0]));
  }

  /**
   * Listens for all requests received by the server in real-time.
   * @see <a href="https://redis.io/commands/monitor/">MONITOR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> monitor() {
    return send(Command.MONITOR);
  }

  /**
   * Moves a key to another database.
   * @see <a href="https://redis.io/commands/move/">MOVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> move(String arg0, String arg1) {
    return send(Command.MOVE, arg0, arg1);
  }

  /**
   * Atomically creates or modifies the string values of one or more keys.
   * @see <a href="https://redis.io/commands/mset/">MSET</a>
   * @since Redis 1.0.1
   */
  default Future<@Nullable Response> mset(List<String> args) {
    return send(Command.MSET, args.toArray(new String[0]));
  }

  /**
   * Atomically sets multiple string keys with a shared expiration in a single operation. Supports flexible argument parsing where condition and expiration flags can appear in any order.
   * @see <a href="https://redis.io/commands/msetex/">MSETEX</a>
   * @since Redis 8.4.0
   */
  default Future<@Nullable Response> msetex(List<String> args) {
    return send(Command.MSETEX, args.toArray(new String[0]));
  }

  /**
   * Atomically modifies the string values of one or more keys only when all keys don&#x27;t exist.
   * @see <a href="https://redis.io/commands/msetnx/">MSETNX</a>
   * @since Redis 1.0.1
   */
  default Future<@Nullable Response> msetnx(List<String> args) {
    return send(Command.MSETNX, args.toArray(new String[0]));
  }

  /**
   * Starts a transaction.
   * @see <a href="https://redis.io/commands/multi/">MULTI</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> multi() {
    return send(Command.MULTI);
  }

  /**
   * A container for object introspection commands.
   * @see <a href="https://redis.io/commands/object/">OBJECT</a>
   * @since Redis 2.2.3
   */
  default Future<@Nullable Response> object(List<String> args) {
    return send(Command.OBJECT, args.toArray(new String[0]));
  }

  /**
   * Removes the expiration time of a key.
   * @see <a href="https://redis.io/commands/persist/">PERSIST</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> persist(String arg0) {
    return send(Command.PERSIST, arg0);
  }

  /**
   * Sets the expiration time of a key in milliseconds.
   * @see <a href="https://redis.io/commands/pexpire/">PEXPIRE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pexpire(List<String> args) {
    return send(Command.PEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key to a Unix milliseconds timestamp.
   * @see <a href="https://redis.io/commands/pexpireat/">PEXPIREAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pexpireat(List<String> args) {
    return send(Command.PEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a key as a Unix milliseconds timestamp.
   * @see <a href="https://redis.io/commands/pexpiretime/">PEXPIRETIME</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> pexpiretime(String arg0) {
    return send(Command.PEXPIRETIME, arg0);
  }

  /**
   * Adds elements to a HyperLogLog key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/pfadd/">PFADD</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfadd(List<String> args) {
    return send(Command.PFADD, args.toArray(new String[0]));
  }

  /**
   * Returns the approximated cardinality of the set(s) observed by the HyperLogLog key(s).
   * @see <a href="https://redis.io/commands/pfcount/">PFCOUNT</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfcount(List<String> args) {
    return send(Command.PFCOUNT, args.toArray(new String[0]));
  }

  /**
   * Internal commands for debugging HyperLogLog values.
   * @see <a href="https://redis.io/commands/pfdebug/">PFDEBUG</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfdebug(String arg0, String arg1) {
    return send(Command.PFDEBUG, arg0, arg1);
  }

  /**
   * Merges one or more HyperLogLog values into a single key.
   * @see <a href="https://redis.io/commands/pfmerge/">PFMERGE</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfmerge(List<String> args) {
    return send(Command.PFMERGE, args.toArray(new String[0]));
  }

  /**
   * An internal command for testing HyperLogLog values.
   * @see <a href="https://redis.io/commands/pfselftest/">PFSELFTEST</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfselftest() {
    return send(Command.PFSELFTEST);
  }

  /**
   * Returns the server&#x27;s liveliness response.
   * @see <a href="https://redis.io/commands/ping/">PING</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ping(List<String> args) {
    return send(Command.PING, args.toArray(new String[0]));
  }

  /**
   * Sets both string value and expiration time in milliseconds of a key. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/psetex/">PSETEX</a>
   * @since Redis 2.6.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code PX} argument
   */
  default Future<@Nullable Response> psetex(String arg0, String arg1, String arg2) {
    return send(Command.PSETEX, arg0, arg1, arg2);
  }

  /**
   * Listens for messages published to channels that match one or more patterns.
   * @see <a href="https://redis.io/commands/psubscribe/">PSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> psubscribe(List<String> args) {
    return send(Command.PSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * An internal command used in replication.
   * @see <a href="https://redis.io/commands/psync/">PSYNC</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> psync(List<String> args) {
    return send(Command.PSYNC, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time in milliseconds of a key.
   * @see <a href="https://redis.io/commands/pttl/">PTTL</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pttl(String arg0) {
    return send(Command.PTTL, arg0);
  }

  /**
   * Posts a message to a channel.
   * @see <a href="https://redis.io/commands/publish/">PUBLISH</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> publish(String arg0, String arg1) {
    return send(Command.PUBLISH, arg0, arg1);
  }

  /**
   * A container for Pub/Sub commands.
   * @see <a href="https://redis.io/commands/pubsub/">PUBSUB</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> pubsub(List<String> args) {
    return send(Command.PUBSUB, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages published to channels that match one or more patterns.
   * @see <a href="https://redis.io/commands/punsubscribe/">PUNSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> punsubscribe(List<String> args) {
    return send(Command.PUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Closes the connection.
   * @see <a href="https://redis.io/commands/quit/">QUIT</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 7.2.0, replaced by: just closing the connection
   */
  default Future<@Nullable Response> quit(List<String> args) {
    return send(Command.QUIT, args.toArray(new String[0]));
  }

  /**
   * Returns a random key name from the database.
   * @see <a href="https://redis.io/commands/randomkey/">RANDOMKEY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> randomkey() {
    return send(Command.RANDOMKEY);
  }

  /**
   * Enables read-only queries for a connection to a Redis Cluster replica node.
   * @see <a href="https://redis.io/commands/readonly/">READONLY</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> readonly() {
    return send(Command.READONLY);
  }

  /**
   * Enables read-write queries for a connection to a Reids Cluster replica node.
   * @see <a href="https://redis.io/commands/readwrite/">READWRITE</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> readwrite() {
    return send(Command.READWRITE);
  }

  /**
   * Renames a key and overwrites the destination.
   * @see <a href="https://redis.io/commands/rename/">RENAME</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rename(String arg0, String arg1) {
    return send(Command.RENAME, arg0, arg1);
  }

  /**
   * Renames a key only when the target key name doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/renamenx/">RENAMENX</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> renamenx(String arg0, String arg1) {
    return send(Command.RENAMENX, arg0, arg1);
  }

  /**
   * An internal command for configuring the replication stream.
   * @see <a href="https://redis.io/commands/replconf/">REPLCONF</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> replconf(List<String> args) {
    return send(Command.REPLCONF, args.toArray(new String[0]));
  }

  /**
   * Configures a server as replica of another, or promotes it to a master.
   * @see <a href="https://redis.io/commands/replicaof/">REPLICAOF</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> replicaof(String arg0, String arg1) {
    return send(Command.REPLICAOF, arg0, arg1);
  }

  /**
   * Resets the connection.
   * @see <a href="https://redis.io/commands/reset/">RESET</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> reset() {
    return send(Command.RESET);
  }

  /**
   * Creates a key from the serialized representation of a value.
   * @see <a href="https://redis.io/commands/restore/">RESTORE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> restore(List<String> args) {
    return send(Command.RESTORE, args.toArray(new String[0]));
  }

  /**
   * An internal command for migrating keys in a cluster.
   * @see <a href="https://redis.io/commands/restore-asking/">RESTORE-ASKING</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> restoreAsking(List<String> args) {
    return send(Command.RESTORE_ASKING, args.toArray(new String[0]));
  }

  /**
   * Returns the replication role.
   * @see <a href="https://redis.io/commands/role/">ROLE</a>
   * @since Redis 2.8.12
   */
  default Future<@Nullable Response> role() {
    return send(Command.ROLE);
  }

  /**
   * Returns and removes the last elements of a list. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/rpop/">RPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rpop(List<String> args) {
    return send(Command.RPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the last element of a list after removing and pushing it to another list. Deletes the list if the last element was popped.
   * @see <a href="https://redis.io/commands/rpoplpush/">RPOPLPUSH</a>
   * @since Redis 1.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code LMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  default Future<@Nullable Response> rpoplpush(String arg0, String arg1) {
    return send(Command.RPOPLPUSH, arg0, arg1);
  }

  /**
   * Appends one or more elements to a list. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/rpush/">RPUSH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rpush(List<String> args) {
    return send(Command.RPUSH, args.toArray(new String[0]));
  }

  /**
   * Appends an element to a list only when the list exists.
   * @see <a href="https://redis.io/commands/rpushx/">RPUSHX</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> rpushx(List<String> args) {
    return send(Command.RPUSHX, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a set. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/sadd/">SADD</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sadd(List<String> args) {
    return send(Command.SADD, args.toArray(new String[0]));
  }

  /**
   * Synchronously saves the database(s) to disk.
   * @see <a href="https://redis.io/commands/save/">SAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> save() {
    return send(Command.SAVE);
  }

  /**
   * Iterates over the key names in the database.
   * @see <a href="https://redis.io/commands/scan/">SCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> scan(List<String> args) {
    return send(Command.SCAN, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a set.
   * @see <a href="https://redis.io/commands/scard/">SCARD</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> scard(String arg0) {
    return send(Command.SCARD, arg0);
  }

  /**
   * A container for Lua scripts management commands.
   * @see <a href="https://redis.io/commands/script/">SCRIPT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> script(List<String> args) {
    return send(Command.SCRIPT, args.toArray(new String[0]));
  }

  /**
   * Returns the difference of multiple sets.
   * @see <a href="https://redis.io/commands/sdiff/">SDIFF</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sdiff(List<String> args) {
    return send(Command.SDIFF, args.toArray(new String[0]));
  }

  /**
   * Stores the difference of multiple sets in a key.
   * @see <a href="https://redis.io/commands/sdiffstore/">SDIFFSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sdiffstore(List<String> args) {
    return send(Command.SDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/search.clusterinfo/">SEARCH.CLUSTERINFO</a>
   */
  default Future<@Nullable Response> searchClusterinfo(List<String> args) {
    return send(Command.SEARCH_CLUSTERINFO, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/search.clusterrefresh/">SEARCH.CLUSTERREFRESH</a>
   */
  default Future<@Nullable Response> searchClusterrefresh(List<String> args) {
    return send(Command.SEARCH_CLUSTERREFRESH, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/search.clusterset/">SEARCH.CLUSTERSET</a>
   */
  default Future<@Nullable Response> searchClusterset(List<String> args) {
    return send(Command.SEARCH_CLUSTERSET, args.toArray(new String[0]));
  }

  /**
   * Changes the selected database.
   * @see <a href="https://redis.io/commands/select/">SELECT</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> select(String arg0) {
    return send(Command.SELECT, arg0);
  }

  /**
   * Sets the string value of a key, ignoring its type. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/set/">SET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> set(List<String> args) {
    return send(Command.SET, args.toArray(new String[0]));
  }

  /**
   * Sets or clears the bit at offset of the string value. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/setbit/">SETBIT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> setbit(String arg0, String arg1, String arg2) {
    return send(Command.SETBIT, arg0, arg1, arg2);
  }

  /**
   * Sets the string value and expiration time of a key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/setex/">SETEX</a>
   * @since Redis 2.0.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code EX} argument
   */
  default Future<@Nullable Response> setex(String arg0, String arg1, String arg2) {
    return send(Command.SETEX, arg0, arg1, arg2);
  }

  /**
   * Set the string value of a key only when the key doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/setnx/">SETNX</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code NX} argument
   */
  default Future<@Nullable Response> setnx(String arg0, String arg1) {
    return send(Command.SETNX, arg0, arg1);
  }

  /**
   * Overwrites a part of a string value with another by an offset. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/setrange/">SETRANGE</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> setrange(String arg0, String arg1, String arg2) {
    return send(Command.SETRANGE, arg0, arg1, arg2);
  }

  /**
   * Synchronously saves the database(s) to disk and shuts down the Redis server.
   * @see <a href="https://redis.io/commands/shutdown/">SHUTDOWN</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> shutdown(List<String> args) {
    return send(Command.SHUTDOWN, args.toArray(new String[0]));
  }

  /**
   * Returns the intersect of multiple sets.
   * @see <a href="https://redis.io/commands/sinter/">SINTER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sinter(List<String> args) {
    return send(Command.SINTER, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members of the intersect of multiple sets.
   * @see <a href="https://redis.io/commands/sintercard/">SINTERCARD</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sintercard(List<String> args) {
    return send(Command.SINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Stores the intersect of multiple sets in a key.
   * @see <a href="https://redis.io/commands/sinterstore/">SINTERSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sinterstore(List<String> args) {
    return send(Command.SINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Determines whether a member belongs to a set.
   * @see <a href="https://redis.io/commands/sismember/">SISMEMBER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sismember(String arg0, String arg1) {
    return send(Command.SISMEMBER, arg0, arg1);
  }

  /**
   * Sets a Redis server as a replica of another, or promotes it to being a master.
   * @see <a href="https://redis.io/commands/slaveof/">SLAVEOF</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 5.0.0, replaced by: {@code REPLICAOF}
   */
  default Future<@Nullable Response> slaveof(String arg0, String arg1) {
    return send(Command.SLAVEOF, arg0, arg1);
  }

  /**
   * A container for slow log commands.
   * @see <a href="https://redis.io/commands/slowlog/">SLOWLOG</a>
   * @since Redis 2.2.12
   */
  default Future<@Nullable Response> slowlog(List<String> args) {
    return send(Command.SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Returns all members of a set.
   * @see <a href="https://redis.io/commands/smembers/">SMEMBERS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> smembers(String arg0) {
    return send(Command.SMEMBERS, arg0);
  }

  /**
   * Determines whether multiple members belong to a set.
   * @see <a href="https://redis.io/commands/smismember/">SMISMEMBER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> smismember(List<String> args) {
    return send(Command.SMISMEMBER, args.toArray(new String[0]));
  }

  /**
   * Moves a member from one set to another.
   * @see <a href="https://redis.io/commands/smove/">SMOVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> smove(String arg0, String arg1, String arg2) {
    return send(Command.SMOVE, arg0, arg1, arg2);
  }

  /**
   * Sorts the elements in a list, a set, or a sorted set, optionally storing the result.
   * @see <a href="https://redis.io/commands/sort/">SORT</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sort(List<String> args) {
    return send(Command.SORT, args.toArray(new String[0]));
  }

  /**
   * Returns the sorted elements of a list, a set, or a sorted set.
   * @see <a href="https://redis.io/commands/sort_ro/">SORT_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sortRo(List<String> args) {
    return send(Command.SORT_RO, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random members from a set after removing them. Deletes the set if the last member was popped.
   * @see <a href="https://redis.io/commands/spop/">SPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> spop(List<String> args) {
    return send(Command.SPOP, args.toArray(new String[0]));
  }

  /**
   * Post a message to a shard channel.
   * @see <a href="https://redis.io/commands/spublish/">SPUBLISH</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> spublish(String arg0, String arg1) {
    return send(Command.SPUBLISH, arg0, arg1);
  }

  /**
   * Get one or multiple random members from a set.
   * @see <a href="https://redis.io/commands/srandmember/">SRANDMEMBER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> srandmember(List<String> args) {
    return send(Command.SRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Removes one or more members from a set. Deletes the set if the last member was removed.
   * @see <a href="https://redis.io/commands/srem/">SREM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> srem(List<String> args) {
    return send(Command.SREM, args.toArray(new String[0]));
  }

  /**
   * Iterates over members of a set.
   * @see <a href="https://redis.io/commands/sscan/">SSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> sscan(List<String> args) {
    return send(Command.SSCAN, args.toArray(new String[0]));
  }

  /**
   * Listens for messages published to shard channels.
   * @see <a href="https://redis.io/commands/ssubscribe/">SSUBSCRIBE</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> ssubscribe(List<String> args) {
    return send(Command.SSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Returns the length of a string value.
   * @see <a href="https://redis.io/commands/strlen/">STRLEN</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> strlen(String arg0) {
    return send(Command.STRLEN, arg0);
  }

  /**
   * Listens for messages published to channels.
   * @see <a href="https://redis.io/commands/subscribe/">SUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> subscribe(List<String> args) {
    return send(Command.SUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Returns a substring from a string value.
   * @see <a href="https://redis.io/commands/substr/">SUBSTR</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 2.0.0, replaced by: {@code GETRANGE}
   */
  default Future<@Nullable Response> substr(String arg0, String arg1, String arg2) {
    return send(Command.SUBSTR, arg0, arg1, arg2);
  }

  /**
   * Returns the union of multiple sets.
   * @see <a href="https://redis.io/commands/sunion/">SUNION</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sunion(List<String> args) {
    return send(Command.SUNION, args.toArray(new String[0]));
  }

  /**
   * Stores the union of multiple sets in a key.
   * @see <a href="https://redis.io/commands/sunionstore/">SUNIONSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sunionstore(List<String> args) {
    return send(Command.SUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages posted to shard channels.
   * @see <a href="https://redis.io/commands/sunsubscribe/">SUNSUBSCRIBE</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sunsubscribe(List<String> args) {
    return send(Command.SUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Swaps two Redis databases.
   * @see <a href="https://redis.io/commands/swapdb/">SWAPDB</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> swapdb(String arg0, String arg1) {
    return send(Command.SWAPDB, arg0, arg1);
  }

  /**
   * An internal command used in replication.
   * @see <a href="https://redis.io/commands/sync/">SYNC</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sync() {
    return send(Command.SYNC);
  }

  /**
   * Adds one or more observations to a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.add/">TDIGEST.ADD</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestAdd(List<String> args) {
    return send(Command.TDIGEST_ADD, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input rank, an estimation of the value (floating-point) with that rank.
   * @see <a href="https://redis.io/commands/tdigest.byrank/">TDIGEST.BYRANK</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestByrank(List<String> args) {
    return send(Command.TDIGEST_BYRANK, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input reverse rank, an estimation of the value (floating-point) with that reverse rank.
   * @see <a href="https://redis.io/commands/tdigest.byrevrank/">TDIGEST.BYREVRANK</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestByrevrank(List<String> args) {
    return send(Command.TDIGEST_BYREVRANK, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input value, an estimation of the floating-point fraction of (observations smaller than the given value + half the observations equal to the given value). Multiple fractions can be retrieved in a single call.
   * @see <a href="https://redis.io/commands/tdigest.cdf/">TDIGEST.CDF</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestCdf(List<String> args) {
    return send(Command.TDIGEST_CDF, args.toArray(new String[0]));
  }

  /**
   * Allocates memory and initializes a new t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.create/">TDIGEST.CREATE</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestCreate(List<String> args) {
    return send(Command.TDIGEST_CREATE, args.toArray(new String[0]));
  }

  /**
   * Returns information and statistics about a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.info/">TDIGEST.INFO</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestInfo(String arg0) {
    return send(Command.TDIGEST_INFO, arg0);
  }

  /**
   * Returns information and statistics about a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.info/">TDIGEST.INFO</a>
   * @since Redis Bloom 2.4.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tdigestInfo(List<String> args) {
    return send(Command.TDIGEST_INFO, args.toArray(new String[0]));
  }

  /**
   * Returns the maximum observation value from a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.max/">TDIGEST.MAX</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestMax(String arg0) {
    return send(Command.TDIGEST_MAX, arg0);
  }

  /**
   * Returns the maximum observation value from a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.max/">TDIGEST.MAX</a>
   * @since Redis Bloom 2.4.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tdigestMax(List<String> args) {
    return send(Command.TDIGEST_MAX, args.toArray(new String[0]));
  }

  /**
   * Merges multiple t-digest sketches into a single sketch.
   * @see <a href="https://redis.io/commands/tdigest.merge/">TDIGEST.MERGE</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestMerge(List<String> args) {
    return send(Command.TDIGEST_MERGE, args.toArray(new String[0]));
  }

  /**
   * Returns the minimum observation value from a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.min/">TDIGEST.MIN</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestMin(String arg0) {
    return send(Command.TDIGEST_MIN, arg0);
  }

  /**
   * Returns the minimum observation value from a t-digest sketch.
   * @see <a href="https://redis.io/commands/tdigest.min/">TDIGEST.MIN</a>
   * @since Redis Bloom 2.4.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tdigestMin(List<String> args) {
    return send(Command.TDIGEST_MIN, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input fraction, an estimation of the value (floating point) that is smaller than the given fraction of observations.
   * @see <a href="https://redis.io/commands/tdigest.quantile/">TDIGEST.QUANTILE</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestQuantile(List<String> args) {
    return send(Command.TDIGEST_QUANTILE, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input value (floating-point), the estimated rank of the value (the number of observations in the sketch that are smaller than the value + half the number of observations that are equal to the value).
   * @see <a href="https://redis.io/commands/tdigest.rank/">TDIGEST.RANK</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestRank(List<String> args) {
    return send(Command.TDIGEST_RANK, args.toArray(new String[0]));
  }

  /**
   * Resets a t-digest sketch: empty the sketch and re-initializes it.
   * @see <a href="https://redis.io/commands/tdigest.reset/">TDIGEST.RESET</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestReset(String arg0) {
    return send(Command.TDIGEST_RESET, arg0);
  }

  /**
   * Resets a t-digest sketch: empty the sketch and re-initializes it.
   * @see <a href="https://redis.io/commands/tdigest.reset/">TDIGEST.RESET</a>
   * @since Redis Bloom 2.4.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tdigestReset(List<String> args) {
    return send(Command.TDIGEST_RESET, args.toArray(new String[0]));
  }

  /**
   * Returns, for each input value (floating-point), the estimated reverse rank of the value (the number of observations in the sketch that are larger than the value + half the number of observations that are equal to the value).
   * @see <a href="https://redis.io/commands/tdigest.revrank/">TDIGEST.REVRANK</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestRevrank(List<String> args) {
    return send(Command.TDIGEST_REVRANK, args.toArray(new String[0]));
  }

  /**
   * Returns an estimation of the mean value from the sketch, excluding observation values outside the low and high cutoff quantiles.
   * @see <a href="https://redis.io/commands/tdigest.trimmed_mean/">TDIGEST.TRIMMED_MEAN</a>
   * @since Redis Bloom 2.4.0
   */
  default Future<@Nullable Response> tdigestTrimmedMean(String arg0, String arg1, String arg2) {
    return send(Command.TDIGEST_TRIMMED_MEAN, arg0, arg1, arg2);
  }

  /**
   * Returns an estimation of the mean value from the sketch, excluding observation values outside the low and high cutoff quantiles.
   * @see <a href="https://redis.io/commands/tdigest.trimmed_mean/">TDIGEST.TRIMMED_MEAN</a>
   * @since Redis Bloom 2.4.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tdigestTrimmedMean(List<String> args) {
    return send(Command.TDIGEST_TRIMMED_MEAN, args.toArray(new String[0]));
  }

  /**
   * Returns the server time.
   * @see <a href="https://redis.io/commands/time/">TIME</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> time() {
    return send(Command.TIME);
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.clusterset/">TIMESERIES.CLUSTERSET</a>
   */
  default Future<@Nullable Response> timeseriesClusterset(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSET, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.clustersetfromshard/">TIMESERIES.CLUSTERSETFROMSHARD</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> timeseriesClustersetfromshard(List<String> args) {
    return send(Command.TIMESERIES_CLUSTERSETFROMSHARD, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.hello/">TIMESERIES.HELLO</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> timeseriesHello(List<String> args) {
    return send(Command.TIMESERIES_HELLO, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.infocluster/">TIMESERIES.INFOCLUSTER</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> timeseriesInfocluster(List<String> args) {
    return send(Command.TIMESERIES_INFOCLUSTER, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.innercommunication/">TIMESERIES.INNERCOMMUNICATION</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> timeseriesInnercommunication(List<String> args) {
    return send(Command.TIMESERIES_INNERCOMMUNICATION, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.networktest/">TIMESERIES.NETWORKTEST</a>
   * @deprecated this command no longer exists in the latest Redis release
   */
  default Future<@Nullable Response> timeseriesNetworktest(List<String> args) {
    return send(Command.TIMESERIES_NETWORKTEST, args.toArray(new String[0]));
  }

  /**
   * @see <a href="https://redis.io/commands/timeseries.refreshcluster/">TIMESERIES.REFRESHCLUSTER</a>
   */
  default Future<@Nullable Response> timeseriesRefreshcluster(List<String> args) {
    return send(Command.TIMESERIES_REFRESHCLUSTER, args.toArray(new String[0]));
  }

  /**
   * Adds an item to a Top-k sketch. Multiple items can be added at the same time.
   * @see <a href="https://redis.io/commands/topk.add/">TOPK.ADD</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkAdd(List<String> args) {
    return send(Command.TOPK_ADD, args.toArray(new String[0]));
  }

  /**
   * Return the count for one or more items are in a sketch.
   * @see <a href="https://redis.io/commands/topk.count/">TOPK.COUNT</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkCount(List<String> args) {
    return send(Command.TOPK_COUNT, args.toArray(new String[0]));
  }

  /**
   * Increases the count of one or more items by increment.
   * @see <a href="https://redis.io/commands/topk.incrby/">TOPK.INCRBY</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkIncrby(List<String> args) {
    return send(Command.TOPK_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Returns information about a sketch.
   * @see <a href="https://redis.io/commands/topk.info/">TOPK.INFO</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkInfo(String arg0) {
    return send(Command.TOPK_INFO, arg0);
  }

  /**
   * Returns information about a sketch.
   * @see <a href="https://redis.io/commands/topk.info/">TOPK.INFO</a>
   * @since Redis Bloom 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> topkInfo(List<String> args) {
    return send(Command.TOPK_INFO, args.toArray(new String[0]));
  }

  /**
   * Return the full list of items in Top-K sketch.
   * @see <a href="https://redis.io/commands/topk.list/">TOPK.LIST</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkList(List<String> args) {
    return send(Command.TOPK_LIST, args.toArray(new String[0]));
  }

  /**
   * Checks whether one or more items are in a sketch.
   * @see <a href="https://redis.io/commands/topk.query/">TOPK.QUERY</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkQuery(List<String> args) {
    return send(Command.TOPK_QUERY, args.toArray(new String[0]));
  }

  /**
   * Initializes a Top-K sketch with specified parameters.
   * @see <a href="https://redis.io/commands/topk.reserve/">TOPK.RESERVE</a>
   * @since Redis Bloom 2.0.0
   */
  default Future<@Nullable Response> topkReserve(List<String> args) {
    return send(Command.TOPK_RESERVE, args.toArray(new String[0]));
  }

  /**
   * Returns the number of existing keys out of those specified after updating the time they were last accessed.
   * @see <a href="https://redis.io/commands/touch/">TOUCH</a>
   * @since Redis 3.2.1
   */
  default Future<@Nullable Response> touch(List<String> args) {
    return send(Command.TOUCH, args.toArray(new String[0]));
  }

  /**
   * Trim the keys that belong to specified slots.
   * @see <a href="https://redis.io/commands/trimslots/">TRIMSLOTS</a>
   * @since Redis 8.4.0
   */
  default Future<@Nullable Response> trimslots(List<String> args) {
    return send(Command.TRIMSLOTS, args.toArray(new String[0]));
  }

  /**
   * Append a sample to a time series.
   * @see <a href="https://redis.io/commands/ts.add/">TS.ADD</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsAdd(List<String> args) {
    return send(Command.TS_ADD, args.toArray(new String[0]));
  }

  /**
   * Update the retention, chunk size, duplicate policy, and labels of an existing time series.
   * @see <a href="https://redis.io/commands/ts.alter/">TS.ALTER</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsAlter(List<String> args) {
    return send(Command.TS_ALTER, args.toArray(new String[0]));
  }

  /**
   * Create a new time series.
   * @see <a href="https://redis.io/commands/ts.create/">TS.CREATE</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsCreate(List<String> args) {
    return send(Command.TS_CREATE, args.toArray(new String[0]));
  }

  /**
   * Create a compaction rule.
   * @see <a href="https://redis.io/commands/ts.createrule/">TS.CREATERULE</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsCreaterule(List<String> args) {
    return send(Command.TS_CREATERULE, args.toArray(new String[0]));
  }

  /**
   * Decrease the value of the latest sample.
   * @see <a href="https://redis.io/commands/ts.decrby/">TS.DECRBY</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsDecrby(List<String> args) {
    return send(Command.TS_DECRBY, args.toArray(new String[0]));
  }

  /**
   * Delete all samples between two timestamps for a given time series.
   * @see <a href="https://redis.io/commands/ts.del/">TS.DEL</a>
   * @since Redis TimeSeries 1.6.0
   */
  default Future<@Nullable Response> tsDel(String arg0, String arg1, String arg2) {
    return send(Command.TS_DEL, arg0, arg1, arg2);
  }

  /**
   * Delete all samples between two timestamps for a given time series.
   * @see <a href="https://redis.io/commands/ts.del/">TS.DEL</a>
   * @since Redis TimeSeries 1.6.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tsDel(List<String> args) {
    return send(Command.TS_DEL, args.toArray(new String[0]));
  }

  /**
   * Delete a compaction rule.
   * @see <a href="https://redis.io/commands/ts.deleterule/">TS.DELETERULE</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsDeleterule(String arg0, String arg1) {
    return send(Command.TS_DELETERULE, arg0, arg1);
  }

  /**
   * Delete a compaction rule.
   * @see <a href="https://redis.io/commands/ts.deleterule/">TS.DELETERULE</a>
   * @since Redis TimeSeries 1.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> tsDeleterule(List<String> args) {
    return send(Command.TS_DELETERULE, args.toArray(new String[0]));
  }

  /**
   * Get the sample with the highest timestamp from a given time series.
   * @see <a href="https://redis.io/commands/ts.get/">TS.GET</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsGet(List<String> args) {
    return send(Command.TS_GET, args.toArray(new String[0]));
  }

  /**
   * Increase the value of the latest sample.
   * @see <a href="https://redis.io/commands/ts.incrby/">TS.INCRBY</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsIncrby(List<String> args) {
    return send(Command.TS_INCRBY, args.toArray(new String[0]));
  }

  /**
   * Returns information and statistics for a time series.
   * @see <a href="https://redis.io/commands/ts.info/">TS.INFO</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsInfo(List<String> args) {
    return send(Command.TS_INFO, args.toArray(new String[0]));
  }

  /**
   * Append new samples to one or more time series.
   * @see <a href="https://redis.io/commands/ts.madd/">TS.MADD</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsMadd(List<String> args) {
    return send(Command.TS_MADD, args.toArray(new String[0]));
  }

  /**
   * Get the sample with the highest timestamp from each time series matching a specific filter.
   * @see <a href="https://redis.io/commands/ts.mget/">TS.MGET</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsMget(List<String> args) {
    return send(Command.TS_MGET, args.toArray(new String[0]));
  }

  /**
   * Query a range across multiple time series by filters in forward direction.
   * @see <a href="https://redis.io/commands/ts.mrange/">TS.MRANGE</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsMrange(List<String> args) {
    return send(Command.TS_MRANGE, args.toArray(new String[0]));
  }

  /**
   * Query a range across multiple time series by filters in reverse direction.
   * @see <a href="https://redis.io/commands/ts.mrevrange/">TS.MREVRANGE</a>
   * @since Redis TimeSeries 1.4.0
   */
  default Future<@Nullable Response> tsMrevrange(List<String> args) {
    return send(Command.TS_MREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Get all time series keys matching a filter list.
   * @see <a href="https://redis.io/commands/ts.queryindex/">TS.QUERYINDEX</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsQueryindex(List<String> args) {
    return send(Command.TS_QUERYINDEX, args.toArray(new String[0]));
  }

  /**
   * Query a range in forward direction.
   * @see <a href="https://redis.io/commands/ts.range/">TS.RANGE</a>
   * @since Redis TimeSeries 1.0.0
   */
  default Future<@Nullable Response> tsRange(List<String> args) {
    return send(Command.TS_RANGE, args.toArray(new String[0]));
  }

  /**
   * Query a range in reverse direction.
   * @see <a href="https://redis.io/commands/ts.revrange/">TS.REVRANGE</a>
   * @since Redis TimeSeries 1.4.0
   */
  default Future<@Nullable Response> tsRevrange(List<String> args) {
    return send(Command.TS_REVRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time in seconds of a key.
   * @see <a href="https://redis.io/commands/ttl/">TTL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ttl(String arg0) {
    return send(Command.TTL, arg0);
  }

  /**
   * Determines the type of value stored at a key.
   * @see <a href="https://redis.io/commands/type/">TYPE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> type(String arg0) {
    return send(Command.TYPE, arg0);
  }

  /**
   * Asynchronously deletes one or more keys.
   * @see <a href="https://redis.io/commands/unlink/">UNLINK</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> unlink(List<String> args) {
    return send(Command.UNLINK, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages posted to channels.
   * @see <a href="https://redis.io/commands/unsubscribe/">UNSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> unsubscribe(List<String> args) {
    return send(Command.UNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Forgets about watched keys of a transaction.
   * @see <a href="https://redis.io/commands/unwatch/">UNWATCH</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> unwatch() {
    return send(Command.UNWATCH);
  }

  /**
   * Blocks until the asynchronous replication of all preceding write commands sent by the connection is completed.
   * @see <a href="https://redis.io/commands/wait/">WAIT</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> wait(String arg0, String arg1) {
    return send(Command.WAIT, arg0, arg1);
  }

  /**
   * Blocks until all of the preceding write commands sent by the connection are written to the append-only file of the master and/or replicas.
   * @see <a href="https://redis.io/commands/waitaof/">WAITAOF</a>
   * @since Redis 7.2.0
   */
  default Future<@Nullable Response> waitaof(String arg0, String arg1, String arg2) {
    return send(Command.WAITAOF, arg0, arg1, arg2);
  }

  /**
   * Monitors changes to keys to determine the execution of a transaction.
   * @see <a href="https://redis.io/commands/watch/">WATCH</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> watch(List<String> args) {
    return send(Command.WATCH, args.toArray(new String[0]));
  }

  /**
   * Returns the number of messages that were successfully acknowledged by the consumer group member of a stream.
   * @see <a href="https://redis.io/commands/xack/">XACK</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xack(List<String> args) {
    return send(Command.XACK, args.toArray(new String[0]));
  }

  /**
   * Acknowledges and deletes one or multiple messages for a stream consumer group.
   * @see <a href="https://redis.io/commands/xackdel/">XACKDEL</a>
   * @since Redis 8.2.0
   */
  default Future<@Nullable Response> xackdel(List<String> args) {
    return send(Command.XACKDEL, args.toArray(new String[0]));
  }

  /**
   * Appends a new message to a stream. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/xadd/">XADD</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xadd(List<String> args) {
    return send(Command.XADD, args.toArray(new String[0]));
  }

  /**
   * Changes, or acquires, ownership of messages in a consumer group, as if the messages were delivered to as consumer group member.
   * @see <a href="https://redis.io/commands/xautoclaim/">XAUTOCLAIM</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> xautoclaim(List<String> args) {
    return send(Command.XAUTOCLAIM, args.toArray(new String[0]));
  }

  /**
   * Sets the IDMP configuration parameters for a stream.
   * @see <a href="https://redis.io/commands/xcfgset/">XCFGSET</a>
   * @since Redis 8.6.0
   */
  default Future<@Nullable Response> xcfgset(List<String> args) {
    return send(Command.XCFGSET, args.toArray(new String[0]));
  }

  /**
   * Changes, or acquires, ownership of a message in a consumer group, as if the message was delivered a consumer group member.
   * @see <a href="https://redis.io/commands/xclaim/">XCLAIM</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xclaim(List<String> args) {
    return send(Command.XCLAIM, args.toArray(new String[0]));
  }

  /**
   * Returns the number of messages after removing them from a stream.
   * @see <a href="https://redis.io/commands/xdel/">XDEL</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xdel(List<String> args) {
    return send(Command.XDEL, args.toArray(new String[0]));
  }

  /**
   * Deletes one or multiple entries from the stream.
   * @see <a href="https://redis.io/commands/xdelex/">XDELEX</a>
   * @since Redis 8.2.0
   */
  default Future<@Nullable Response> xdelex(List<String> args) {
    return send(Command.XDELEX, args.toArray(new String[0]));
  }

  /**
   * A container for consumer groups commands.
   * @see <a href="https://redis.io/commands/xgroup/">XGROUP</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xgroup(List<String> args) {
    return send(Command.XGROUP, args.toArray(new String[0]));
  }

  /**
   * An internal command for setting IDMP metadata on an existing stream message.
   * @see <a href="https://redis.io/commands/xidmprecord/">XIDMPRECORD</a>
   * @since Redis 8.6.2
   */
  default Future<@Nullable Response> xidmprecord(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.XIDMPRECORD, arg0, arg1, arg2, arg3);
  }

  /**
   * A container for stream introspection commands.
   * @see <a href="https://redis.io/commands/xinfo/">XINFO</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xinfo(List<String> args) {
    return send(Command.XINFO, args.toArray(new String[0]));
  }

  /**
   * Return the number of messages in a stream.
   * @see <a href="https://redis.io/commands/xlen/">XLEN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xlen(String arg0) {
    return send(Command.XLEN, arg0);
  }

  /**
   * Returns the information and entries from a stream consumer group&#x27;s pending entries list.
   * @see <a href="https://redis.io/commands/xpending/">XPENDING</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xpending(List<String> args) {
    return send(Command.XPENDING, args.toArray(new String[0]));
  }

  /**
   * Returns the messages from a stream within a range of IDs.
   * @see <a href="https://redis.io/commands/xrange/">XRANGE</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xrange(List<String> args) {
    return send(Command.XRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns messages from multiple streams with IDs greater than the ones requested. Blocks until a message is available otherwise.
   * @see <a href="https://redis.io/commands/xread/">XREAD</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xread(List<String> args) {
    return send(Command.XREAD, args.toArray(new String[0]));
  }

  /**
   * Returns new or historical messages from a stream for a consumer in a group. Blocks until a message is available otherwise.
   * @see <a href="https://redis.io/commands/xreadgroup/">XREADGROUP</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xreadgroup(List<String> args) {
    return send(Command.XREADGROUP, args.toArray(new String[0]));
  }

  /**
   * Returns the messages from a stream within a range of IDs in reverse order.
   * @see <a href="https://redis.io/commands/xrevrange/">XREVRANGE</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xrevrange(List<String> args) {
    return send(Command.XREVRANGE, args.toArray(new String[0]));
  }

  /**
   * An internal command for replicating stream values.
   * @see <a href="https://redis.io/commands/xsetid/">XSETID</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xsetid(List<String> args) {
    return send(Command.XSETID, args.toArray(new String[0]));
  }

  /**
   * Deletes messages from the beginning of a stream.
   * @see <a href="https://redis.io/commands/xtrim/">XTRIM</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xtrim(List<String> args) {
    return send(Command.XTRIM, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a sorted set, or updates their scores. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://redis.io/commands/zadd/">ZADD</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zadd(List<String> args) {
    return send(Command.ZADD, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a sorted set.
   * @see <a href="https://redis.io/commands/zcard/">ZCARD</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zcard(String arg0) {
    return send(Command.ZCARD, arg0);
  }

  /**
   * Returns the count of members in a sorted set that have scores within a range.
   * @see <a href="https://redis.io/commands/zcount/">ZCOUNT</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zcount(String arg0, String arg1, String arg2) {
    return send(Command.ZCOUNT, arg0, arg1, arg2);
  }

  /**
   * Returns the difference between multiple sorted sets.
   * @see <a href="https://redis.io/commands/zdiff/">ZDIFF</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zdiff(List<String> args) {
    return send(Command.ZDIFF, args.toArray(new String[0]));
  }

  /**
   * Stores the difference of multiple sorted sets in a key.
   * @see <a href="https://redis.io/commands/zdiffstore/">ZDIFFSTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zdiffstore(List<String> args) {
    return send(Command.ZDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Increments the score of a member in a sorted set.
   * @see <a href="https://redis.io/commands/zincrby/">ZINCRBY</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zincrby(String arg0, String arg1, String arg2) {
    return send(Command.ZINCRBY, arg0, arg1, arg2);
  }

  /**
   * Returns the intersect of multiple sorted sets.
   * @see <a href="https://redis.io/commands/zinter/">ZINTER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zinter(List<String> args) {
    return send(Command.ZINTER, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members of the intersect of multiple sorted sets.
   * @see <a href="https://redis.io/commands/zintercard/">ZINTERCARD</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> zintercard(List<String> args) {
    return send(Command.ZINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Stores the intersect of multiple sorted sets in a key.
   * @see <a href="https://redis.io/commands/zinterstore/">ZINTERSTORE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zinterstore(List<String> args) {
    return send(Command.ZINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a sorted set within a lexicographical range.
   * @see <a href="https://redis.io/commands/zlexcount/">ZLEXCOUNT</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zlexcount(String arg0, String arg1, String arg2) {
    return send(Command.ZLEXCOUNT, arg0, arg1, arg2);
  }

  /**
   * Returns the highest- or lowest-scoring members from one or more sorted sets after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://redis.io/commands/zmpop/">ZMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> zmpop(List<String> args) {
    return send(Command.ZMPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the score of one or more members in a sorted set.
   * @see <a href="https://redis.io/commands/zmscore/">ZMSCORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zmscore(List<String> args) {
    return send(Command.ZMSCORE, args.toArray(new String[0]));
  }

  /**
   * Returns the highest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://redis.io/commands/zpopmax/">ZPOPMAX</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> zpopmax(List<String> args) {
    return send(Command.ZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Returns the lowest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://redis.io/commands/zpopmin/">ZPOPMIN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> zpopmin(List<String> args) {
    return send(Command.ZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random members from a sorted set.
   * @see <a href="https://redis.io/commands/zrandmember/">ZRANDMEMBER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zrandmember(List<String> args) {
    return send(Command.ZRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of indexes.
   * @see <a href="https://redis.io/commands/zrange/">ZRANGE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zrange(List<String> args) {
    return send(Command.ZRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a lexicographical range.
   * @see <a href="https://redis.io/commands/zrangebylex/">ZRANGEBYLEX</a>
   * @since Redis 2.8.9
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code BYLEX} argument
   */
  default Future<@Nullable Response> zrangebylex(List<String> args) {
    return send(Command.ZRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of scores.
   * @see <a href="https://redis.io/commands/zrangebyscore/">ZRANGEBYSCORE</a>
   * @since Redis 1.0.5
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code BYSCORE} argument
   */
  default Future<@Nullable Response> zrangebyscore(List<String> args) {
    return send(Command.ZRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Stores a range of members from sorted set in a key.
   * @see <a href="https://redis.io/commands/zrangestore/">ZRANGESTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zrangestore(List<String> args) {
    return send(Command.ZRANGESTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by ascending scores.
   * @see <a href="https://redis.io/commands/zrank/">ZRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zrank(List<String> args) {
    return send(Command.ZRANK, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by ascending scores.
   * @see <a href="https://redis.io/commands/zrank/">ZRANK</a>
   * @since Redis 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> zrank(String arg0, String arg1) {
    return send(Command.ZRANK, arg0, arg1);
  }

  /**
   * Removes one or more members from a sorted set. Deletes the sorted set if all members were removed.
   * @see <a href="https://redis.io/commands/zrem/">ZREM</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zrem(List<String> args) {
    return send(Command.ZREM, args.toArray(new String[0]));
  }

  /**
   * Removes members in a sorted set within a lexicographical range. Deletes the sorted set if all members were removed.
   * @see <a href="https://redis.io/commands/zremrangebylex/">ZREMRANGEBYLEX</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zremrangebylex(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2);
  }

  /**
   * Removes members in a sorted set within a range of indexes. Deletes the sorted set if all members were removed.
   * @see <a href="https://redis.io/commands/zremrangebyrank/">ZREMRANGEBYRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zremrangebyrank(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2);
  }

  /**
   * Removes members in a sorted set within a range of scores. Deletes the sorted set if all members were removed.
   * @see <a href="https://redis.io/commands/zremrangebyscore/">ZREMRANGEBYSCORE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zremrangebyscore(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2);
  }

  /**
   * Returns members in a sorted set within a range of indexes in reverse order.
   * @see <a href="https://redis.io/commands/zrevrange/">ZREVRANGE</a>
   * @since Redis 1.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} argument
   */
  default Future<@Nullable Response> zrevrange(List<String> args) {
    return send(Command.ZREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a lexicographical range in reverse order.
   * @see <a href="https://redis.io/commands/zrevrangebylex/">ZREVRANGEBYLEX</a>
   * @since Redis 2.8.9
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYLEX} arguments
   */
  default Future<@Nullable Response> zrevrangebylex(List<String> args) {
    return send(Command.ZREVRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of scores in reverse order.
   * @see <a href="https://redis.io/commands/zrevrangebyscore/">ZREVRANGEBYSCORE</a>
   * @since Redis 2.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYSCORE} arguments
   */
  default Future<@Nullable Response> zrevrangebyscore(List<String> args) {
    return send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by descending scores.
   * @see <a href="https://redis.io/commands/zrevrank/">ZREVRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zrevrank(List<String> args) {
    return send(Command.ZREVRANK, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by descending scores.
   * @see <a href="https://redis.io/commands/zrevrank/">ZREVRANK</a>
   * @since Redis 2.0.0
   */
  // backwards compatibility overload
  default Future<@Nullable Response> zrevrank(String arg0, String arg1) {
    return send(Command.ZREVRANK, arg0, arg1);
  }

  /**
   * Iterates over members and scores of a sorted set.
   * @see <a href="https://redis.io/commands/zscan/">ZSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> zscan(List<String> args) {
    return send(Command.ZSCAN, args.toArray(new String[0]));
  }

  /**
   * Returns the score of a member in a sorted set.
   * @see <a href="https://redis.io/commands/zscore/">ZSCORE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zscore(String arg0, String arg1) {
    return send(Command.ZSCORE, arg0, arg1);
  }

  /**
   * Returns the union of multiple sorted sets.
   * @see <a href="https://redis.io/commands/zunion/">ZUNION</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zunion(List<String> args) {
    return send(Command.ZUNION, args.toArray(new String[0]));
  }

  /**
   * Stores the union of multiple sorted sets in a key.
   * @see <a href="https://redis.io/commands/zunionstore/">ZUNIONSTORE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zunionstore(List<String> args) {
    return send(Command.ZUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Send untyped command to Redis.
   *
   * @param cmd the command
   * @param args var args
   * @return Future response.
   */
  @GenIgnore
  Future<@Nullable Response> send(Command cmd, String... args);
}
