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

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.redis.client.impl.CommandImpl;
import io.vertx.redis.client.impl.KeyLocator;
import io.vertx.redis.client.impl.keys.BeginSearchIndex;
import io.vertx.redis.client.impl.keys.BeginSearchKeyword;
import io.vertx.redis.client.impl.keys.FindKeysKeynum;
import io.vertx.redis.client.impl.keys.FindKeysRange;

/**
 * <b>Auto generated</b> API Commands to interact with REDIS.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 * @version redis_version:7.0.12
 */
@VertxGen
public interface Command {

  /**
   * A container for Access List Control commands.
   */
  Command ACL = new CommandImpl("acl", -2, null, false, false);
  /**
   * Append a value to a key.
   */
  Command APPEND = new CommandImpl("append", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sent by cluster clients after an -ASK redirect.
   */
  Command ASKING = new CommandImpl("asking", 1, null, false, false);
  /**
   * Authenticate to the server.
   */
  Command AUTH = new CommandImpl("auth", -2, null, false, false);
  Command BF_ADD = new CommandImpl("bf.add", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_CARD = new CommandImpl("bf.card", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_DEBUG = new CommandImpl("bf.debug", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_EXISTS = new CommandImpl("bf.exists", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_INFO = new CommandImpl("bf.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_INSERT = new CommandImpl("bf.insert", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_LOADCHUNK = new CommandImpl("bf.loadchunk", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_MADD = new CommandImpl("bf.madd", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_MEXISTS = new CommandImpl("bf.mexists", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_RESERVE = new CommandImpl("bf.reserve", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command BF_SCANDUMP = new CommandImpl("bf.scandump", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Asynchronously rewrite the append-only file.
   */
  Command BGREWRITEAOF = new CommandImpl("bgrewriteaof", 1, null, false, false);
  /**
   * Asynchronously save the dataset to disk.
   */
  Command BGSAVE = new CommandImpl("bgsave", -1, null, false, false);
  /**
   * Count set bits in a string.
   */
  Command BITCOUNT = new CommandImpl("bitcount", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Perform arbitrary bitfield integer operations on strings.
   */
  Command BITFIELD = new CommandImpl("bitfield", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Perform arbitrary bitfield integer operations on strings. Read-only variant of BITFIELD.
   */
  Command BITFIELD_RO = new CommandImpl("bitfield_ro", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Perform bitwise operations between strings.
   */
  Command BITOP = new CommandImpl("bitop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(3), new FindKeysRange(-1, 1, 0)));
  /**
   * Find first bit set or clear in a string.
   */
  Command BITPOS = new CommandImpl("bitpos", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Pop an element from a list, push it to another list and return it; or block until one is available.
   */
  Command BLMOVE = new CommandImpl("blmove", 6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Pop elements from a list, or block until one is available.
   */
  Command BLMPOP = new CommandImpl("blmpop", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Remove and get the first element in a list, or block until one is available.
   */
  Command BLPOP = new CommandImpl("blpop", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Remove and get the last element in a list, or block until one is available.
   */
  Command BRPOP = new CommandImpl("brpop", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Pop an element from a list, push it to another list and return it; or block until one is available.
   * @deprecated since: 6.2.0, replaced by: {@code BLMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  @Deprecated
  Command BRPOPLPUSH = new CommandImpl("brpoplpush", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return members with scores in a sorted set or block until one is available.
   */
  Command BZMPOP = new CommandImpl("bzmpop", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Remove and return the member with the highest score from one or more sorted sets, or block until one is available.
   */
  Command BZPOPMAX = new CommandImpl("bzpopmax", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Remove and return the member with the lowest score from one or more sorted sets, or block until one is available.
   */
  Command BZPOPMIN = new CommandImpl("bzpopmin", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  Command CF_ADD = new CommandImpl("cf.add", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_ADDNX = new CommandImpl("cf.addnx", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_COMPACT = new CommandImpl("cf.compact", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_COUNT = new CommandImpl("cf.count", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_DEBUG = new CommandImpl("cf.debug", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_DEL = new CommandImpl("cf.del", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_EXISTS = new CommandImpl("cf.exists", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_INFO = new CommandImpl("cf.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_INSERT = new CommandImpl("cf.insert", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_INSERTNX = new CommandImpl("cf.insertnx", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_LOADCHUNK = new CommandImpl("cf.loadchunk", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_MEXISTS = new CommandImpl("cf.mexists", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_RESERVE = new CommandImpl("cf.reserve", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CF_SCANDUMP = new CommandImpl("cf.scandump", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for client connection commands.
   */
  Command CLIENT = new CommandImpl("client", -2, null, false, false);
  /**
   * A container for cluster commands.
   */
  Command CLUSTER = new CommandImpl("cluster", -2, null, false, false);
  Command CMS_INCRBY = new CommandImpl("cms.incrby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CMS_INFO = new CommandImpl("cms.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CMS_INITBYDIM = new CommandImpl("cms.initbydim", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CMS_INITBYPROB = new CommandImpl("cms.initbyprob", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CMS_MERGE = new CommandImpl("cms.merge", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command CMS_QUERY = new CommandImpl("cms.query", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get array of Redis command details.
   */
  Command COMMAND = new CommandImpl("command", -1, null, false, false);
  /**
   * A container for server configuration commands.
   */
  Command CONFIG = new CommandImpl("config", -2, null, false, false);
  /**
   * Copy a key.
   */
  Command COPY = new CommandImpl("copy", -3, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Return the number of keys in the selected database.
   */
  Command DBSIZE = new CommandImpl("dbsize", 1, true, false, false);
  /**
   * A container for debugging commands.
   */
  Command DEBUG = new CommandImpl("debug", -2, null, false, false);
  /**
   * Decrement the integer value of a key by one.
   */
  Command DECR = new CommandImpl("decr", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Decrement the integer value of a key by the given number.
   */
  Command DECRBY = new CommandImpl("decrby", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete a key.
   */
  Command DEL = new CommandImpl("del", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Discard all commands issued after MULTI.
   */
  Command DISCARD = new CommandImpl("discard", 1, null, false, false);
  /**
   * Return a serialized version of the value stored at the specified key.
   */
  Command DUMP = new CommandImpl("dump", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Echo the given string.
   */
  Command ECHO = new CommandImpl("echo", 2, null, false, false);
  /**
   * Execute a Lua script server side.
   */
  Command EVAL = new CommandImpl("eval", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Execute a Lua script server side.
   */
  Command EVALSHA = new CommandImpl("evalsha", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Execute a read-only Lua script server side.
   */
  Command EVALSHA_RO = new CommandImpl("evalsha_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Execute a read-only Lua script server side.
   */
  Command EVAL_RO = new CommandImpl("eval_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Execute all commands issued after MULTI.
   */
  Command EXEC = new CommandImpl("exec", 1, null, false, false);
  /**
   * Determine if a key exists.
   */
  Command EXISTS = new CommandImpl("exists", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Set a key's time to live in seconds.
   */
  Command EXPIRE = new CommandImpl("expire", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the expiration for a key as a UNIX timestamp.
   */
  Command EXPIREAT = new CommandImpl("expireat", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the expiration Unix timestamp for a key.
   */
  Command EXPIRETIME = new CommandImpl("expiretime", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Start a coordinated failover between this server and one of its replicas.
   */
  Command FAILOVER = new CommandImpl("failover", -1, null, false, false);
  /**
   * Invoke a function.
   */
  Command FCALL = new CommandImpl("fcall", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Invoke a read-only function.
   */
  Command FCALL_RO = new CommandImpl("fcall_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Remove all keys from all databases.
   */
  Command FLUSHALL = new CommandImpl("flushall", -1, false, false, false);
  /**
   * Remove all keys from the current database.
   */
  Command FLUSHDB = new CommandImpl("flushdb", -1, false, false, false);
  Command FT_ADD = new CommandImpl("FT.ADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_AGGREGATE = new CommandImpl("FT.AGGREGATE", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_ALIASADD = new CommandImpl("FT.ALIASADD", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_ALIASDEL = new CommandImpl("FT.ALIASDEL", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_ALIASUPDATE = new CommandImpl("FT.ALIASUPDATE", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_ALTER = new CommandImpl("FT.ALTER", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_CONFIG = new CommandImpl("FT.CONFIG", -1, true, false, false);
  Command FT_CREATE = new CommandImpl("FT.CREATE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_CURSOR = new CommandImpl("FT.CURSOR", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  Command FT_DEBUG = new CommandImpl("FT.DEBUG", -1, true, false, false);
  Command FT_DEL = new CommandImpl("FT.DEL", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_DICTADD = new CommandImpl("FT.DICTADD", -1, true, false, false);
  Command FT_DICTDEL = new CommandImpl("FT.DICTDEL", -1, true, false, false);
  Command FT_DICTDUMP = new CommandImpl("FT.DICTDUMP", -1, true, false, false);
  Command FT_DROP = new CommandImpl("FT.DROP", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_DROPINDEX = new CommandImpl("FT.DROPINDEX", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_EXPLAIN = new CommandImpl("FT.EXPLAIN", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_EXPLAINCLI = new CommandImpl("FT.EXPLAINCLI", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_GET = new CommandImpl("FT.GET", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_INFO = new CommandImpl("FT.INFO", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_MGET = new CommandImpl("FT.MGET", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  Command FT_PROFILE = new CommandImpl("FT.PROFILE", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SAFEADD = new CommandImpl("FT.SAFEADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT_SEARCH = new CommandImpl("FT.SEARCH", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SPELLCHECK = new CommandImpl("FT.SPELLCHECK", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SUGADD = new CommandImpl("FT.SUGADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SUGDEL = new CommandImpl("FT.SUGDEL", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SUGGET = new CommandImpl("FT.SUGGET", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SUGLEN = new CommandImpl("FT.SUGLEN", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SYNADD = new CommandImpl("FT.SYNADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SYNDUMP = new CommandImpl("FT.SYNDUMP", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_SYNUPDATE = new CommandImpl("FT.SYNUPDATE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT_TAGVALS = new CommandImpl("FT.TAGVALS", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__ALIASADDIFNX = new CommandImpl("FT._ALIASADDIFNX", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  Command FT__ALIASDELIFX = new CommandImpl("FT._ALIASDELIFX", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__ALTERIFNX = new CommandImpl("FT._ALTERIFNX", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__CREATEIFNX = new CommandImpl("FT._CREATEIFNX", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__DROPIFX = new CommandImpl("FT._DROPIFX", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__DROPINDEXIFX = new CommandImpl("FT._DROPINDEXIFX", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command FT__LIST = new CommandImpl("FT._LIST", -1, true, false, false);
  /**
   * A container for function commands.
   */
  Command FUNCTION = new CommandImpl("function", -2, null, false, false);
  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   */
  Command GEOADD = new CommandImpl("geoadd", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the distance between two members of a geospatial index.
   */
  Command GEODIST = new CommandImpl("geodist", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members of a geospatial index as standard geohash strings.
   */
  Command GEOHASH = new CommandImpl("geohash", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns longitude and latitude of members of a geospatial index.
   */
  Command GEOPOS = new CommandImpl("geopos", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Query a sorted set representing a geospatial index to fetch members matching a given maximum distance from a point.
   * @deprecated since: 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} argument
   */
  @Deprecated
  Command GEORADIUS = new CommandImpl("georadius", -6, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STORE", 6), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STOREDIST", 6), new FindKeysRange(0, 1, 0)));
  /**
   * Query a sorted set representing a geospatial index to fetch members matching a given maximum distance from a member.
   * @deprecated since: 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  @Deprecated
  Command GEORADIUSBYMEMBER = new CommandImpl("georadiusbymember", -5, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STORE", 5), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STOREDIST", 5), new FindKeysRange(0, 1, 0)));
  /**
   * A read-only variant for GEORADIUSBYMEMBER.
   * @deprecated since: 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  @Deprecated
  Command GEORADIUSBYMEMBER_RO = new CommandImpl("georadiusbymember_ro", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A read-only variant for GEORADIUS.
   * @deprecated since: 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} argument
   */
  @Deprecated
  Command GEORADIUS_RO = new CommandImpl("georadius_ro", -6, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Query a sorted set representing a geospatial index to fetch members inside an area of a box or a circle.
   */
  Command GEOSEARCH = new CommandImpl("geosearch", -7, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Query a sorted set representing a geospatial index to fetch members inside an area of a box or a circle, and store the result in another key.
   */
  Command GEOSEARCHSTORE = new CommandImpl("geosearchstore", -8, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Get the value of a key.
   */
  Command GET = new CommandImpl("get", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the bit value at offset in the string value stored at key.
   */
  Command GETBIT = new CommandImpl("getbit", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the value of a key and delete the key.
   */
  Command GETDEL = new CommandImpl("getdel", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the value of a key and optionally set its expiration.
   */
  Command GETEX = new CommandImpl("getex", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get a substring of the string stored at a key.
   */
  Command GETRANGE = new CommandImpl("getrange", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the string value of a key and return its old value.
   * @deprecated since: 6.2.0, replaced by: {@code SET} with the {@code !GET} argument
   */
  @Deprecated
  Command GETSET = new CommandImpl("getset", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_BULK = new CommandImpl("graph.BULK", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_CONFIG = new CommandImpl("graph.CONFIG", -1, true, false, false);
  Command GRAPH_DEBUG = new CommandImpl("graph.DEBUG", -1, true, false, false);
  Command GRAPH_DELETE = new CommandImpl("graph.DELETE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_EXPLAIN = new CommandImpl("graph.EXPLAIN", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_LIST = new CommandImpl("graph.LIST", -1, true, false, false);
  Command GRAPH_PROFILE = new CommandImpl("graph.PROFILE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_QUERY = new CommandImpl("graph.QUERY", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_RO_QUERY = new CommandImpl("graph.RO_QUERY", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command GRAPH_SLOWLOG = new CommandImpl("graph.SLOWLOG", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete one or more hash fields.
   */
  Command HDEL = new CommandImpl("hdel", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Handshake with Redis.
   */
  Command HELLO = new CommandImpl("hello", -1, null, false, false);
  /**
   * Determine if a hash field exists.
   */
  Command HEXISTS = new CommandImpl("hexists", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the value of a hash field.
   */
  Command HGET = new CommandImpl("hget", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get all the fields and values in a hash.
   */
  Command HGETALL = new CommandImpl("hgetall", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the integer value of a hash field by the given number.
   */
  Command HINCRBY = new CommandImpl("hincrby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the float value of a hash field by the given amount.
   */
  Command HINCRBYFLOAT = new CommandImpl("hincrbyfloat", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get all the fields in a hash.
   */
  Command HKEYS = new CommandImpl("hkeys", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the number of fields in a hash.
   */
  Command HLEN = new CommandImpl("hlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the values of all the given hash fields.
   */
  Command HMGET = new CommandImpl("hmget", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set multiple hash fields to multiple values.
   * @deprecated since: 4.0.0, replaced by: {@code HSET} with multiple field-value pairs
   */
  @Deprecated
  Command HMSET = new CommandImpl("hmset", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get one or multiple random fields from a hash.
   */
  Command HRANDFIELD = new CommandImpl("hrandfield", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Incrementally iterate hash fields and associated values.
   */
  Command HSCAN = new CommandImpl("hscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the string value of a hash field.
   */
  Command HSET = new CommandImpl("hset", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the value of a hash field, only if the field does not exist.
   */
  Command HSETNX = new CommandImpl("hsetnx", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the length of the value of a hash field.
   */
  Command HSTRLEN = new CommandImpl("hstrlen", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get all the values in a hash.
   */
  Command HVALS = new CommandImpl("hvals", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the integer value of a key by one.
   */
  Command INCR = new CommandImpl("incr", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the integer value of a key by the given amount.
   */
  Command INCRBY = new CommandImpl("incrby", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the float value of a key by the given amount.
   */
  Command INCRBYFLOAT = new CommandImpl("incrbyfloat", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get information and statistics about the server.
   */
  Command INFO = new CommandImpl("info", -1, null, false, false);
  Command JSON_ARRAPPEND = new CommandImpl("json.arrappend", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_ARRINDEX = new CommandImpl("json.arrindex", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_ARRINSERT = new CommandImpl("json.arrinsert", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_ARRLEN = new CommandImpl("json.arrlen", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_ARRPOP = new CommandImpl("json.arrpop", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_ARRTRIM = new CommandImpl("json.arrtrim", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_CLEAR = new CommandImpl("json.clear", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_DEBUG = new CommandImpl("json.debug", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  Command JSON_DEL = new CommandImpl("json.del", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_FORGET = new CommandImpl("json.forget", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_GET = new CommandImpl("json.get", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_MGET = new CommandImpl("json.mget", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_NUMINCRBY = new CommandImpl("json.numincrby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_NUMMULTBY = new CommandImpl("json.nummultby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_NUMPOWBY = new CommandImpl("json.numpowby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_OBJKEYS = new CommandImpl("json.objkeys", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_OBJLEN = new CommandImpl("json.objlen", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_RESP = new CommandImpl("json.resp", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_SET = new CommandImpl("json.set", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_STRAPPEND = new CommandImpl("json.strappend", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_STRLEN = new CommandImpl("json.strlen", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_TOGGLE = new CommandImpl("json.toggle", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command JSON_TYPE = new CommandImpl("json.type", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Find all keys matching the given pattern.
   */
  Command KEYS = new CommandImpl("keys", 2, true, false, false);
  /**
   * Get the UNIX time stamp of the last successful save to disk.
   */
  Command LASTSAVE = new CommandImpl("lastsave", 1, null, false, false);
  /**
   * A container for latency diagnostics commands.
   */
  Command LATENCY = new CommandImpl("latency", -2, null, false, false);
  /**
   * Find longest common substring.
   */
  Command LCS = new CommandImpl("lcs", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  /**
   * Get an element from a list by its index.
   */
  Command LINDEX = new CommandImpl("lindex", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Insert an element before or after another element in a list.
   */
  Command LINSERT = new CommandImpl("linsert", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the length of a list.
   */
  Command LLEN = new CommandImpl("llen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Pop an element from a list, push it to another list and return it.
   */
  Command LMOVE = new CommandImpl("lmove", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Pop elements from a list.
   */
  Command LMPOP = new CommandImpl("lmpop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Display some computer art and the Redis version.
   */
  Command LOLWUT = new CommandImpl("lolwut", -1, true, false, false);
  /**
   * Remove and get the first elements in a list.
   */
  Command LPOP = new CommandImpl("lpop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the index of matching elements on a list.
   */
  Command LPOS = new CommandImpl("lpos", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Prepend one or multiple elements to a list.
   */
  Command LPUSH = new CommandImpl("lpush", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Prepend an element to a list, only if the list exists.
   */
  Command LPUSHX = new CommandImpl("lpushx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get a range of elements from a list.
   */
  Command LRANGE = new CommandImpl("lrange", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove elements from a list.
   */
  Command LREM = new CommandImpl("lrem", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the value of an element in a list by its index.
   */
  Command LSET = new CommandImpl("lset", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Trim a list to the specified range.
   */
  Command LTRIM = new CommandImpl("ltrim", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for memory diagnostics commands.
   */
  Command MEMORY = new CommandImpl("memory", -2, null, false, false);
  /**
   * Get the values of all the given keys.
   */
  Command MGET = new CommandImpl("mget", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Atomically transfer a key from a Redis instance to another one.
   */
  Command MIGRATE = new CommandImpl("migrate", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(3), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("KEYS", -2), new FindKeysRange(-1, 1, 0)));
  /**
   * A container for module commands.
   */
  Command MODULE = new CommandImpl("module", -2, null, false, false);
  /**
   * Listen for all requests received by the server in real time.
   */
  Command MONITOR = new CommandImpl("monitor", 1, null, false, false);
  /**
   * Move a key to another database.
   */
  Command MOVE = new CommandImpl("move", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set multiple keys to multiple values.
   */
  Command MSET = new CommandImpl("mset", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0)));
  /**
   * Set multiple keys to multiple values, only if none of the keys exist.
   */
  Command MSETNX = new CommandImpl("msetnx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0)));
  /**
   * Mark the start of a transaction block.
   */
  Command MULTI = new CommandImpl("multi", 1, null, false, false);
  /**
   * A container for object introspection commands.
   */
  Command OBJECT = new CommandImpl("object", -2, null, false, false);
  /**
   * Remove the expiration from a key.
   */
  Command PERSIST = new CommandImpl("persist", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set a key's time to live in milliseconds.
   */
  Command PEXPIRE = new CommandImpl("pexpire", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds.
   */
  Command PEXPIREAT = new CommandImpl("pexpireat", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the expiration Unix timestamp for a key in milliseconds.
   */
  Command PEXPIRETIME = new CommandImpl("pexpiretime", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds the specified elements to the specified HyperLogLog.
   */
  Command PFADD = new CommandImpl("pfadd", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   */
  Command PFCOUNT = new CommandImpl("pfcount", -2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Internal commands for debugging HyperLogLog values.
   */
  Command PFDEBUG = new CommandImpl("pfdebug", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Merge N different HyperLogLogs into a single one.
   */
  Command PFMERGE = new CommandImpl("pfmerge", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * An internal command for testing HyperLogLog values.
   */
  Command PFSELFTEST = new CommandImpl("pfselftest", 1, null, false, false);
  /**
   * Ping the server.
   */
  Command PING = new CommandImpl("ping", -1, null, false, false);
  /**
   * Set the value and expiration in milliseconds of a key.
   */
  Command PSETEX = new CommandImpl("psetex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listen for messages published to channels matching the given patterns.
   */
  Command PSUBSCRIBE = new CommandImpl("psubscribe", -2, null, true, false);
  /**
   * Internal command used for replication.
   */
  Command PSYNC = new CommandImpl("psync", -3, null, false, false);
  /**
   * Get the time to live for a key in milliseconds.
   */
  Command PTTL = new CommandImpl("pttl", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Post a message to a channel.
   */
  Command PUBLISH = new CommandImpl("publish", 3, null, false, false);
  /**
   * A container for Pub/Sub commands.
   */
  Command PUBSUB = new CommandImpl("pubsub", -2, null, false, false);
  /**
   * Stop listening for messages posted to channels matching the given patterns.
   */
  Command PUNSUBSCRIBE = new CommandImpl("punsubscribe", -1, null, true, false);
  /**
   * Close the connection.
   */
  Command QUIT = new CommandImpl("quit", -1, null, false, false);
  /**
   * Return a random key from the keyspace.
   */
  Command RANDOMKEY = new CommandImpl("randomkey", 1, true, false, false);
  /**
   * Enables read queries for a connection to a cluster replica node.
   */
  Command READONLY = new CommandImpl("readonly", 1, null, false, false);
  /**
   * Disables read queries for a connection to a cluster replica node.
   */
  Command READWRITE = new CommandImpl("readwrite", 1, null, false, false);
  /**
   * Rename a key.
   */
  Command RENAME = new CommandImpl("rename", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Rename a key, only if the new key does not exist.
   */
  Command RENAMENX = new CommandImpl("renamenx", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for configuring the replication stream.
   */
  Command REPLCONF = new CommandImpl("replconf", -1, null, false, false);
  /**
   * Make the server a replica of another instance, or promote it as master.
   */
  Command REPLICAOF = new CommandImpl("replicaof", 3, null, false, false);
  /**
   * Reset the connection.
   */
  Command RESET = new CommandImpl("reset", 1, null, false, false);
  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   */
  Command RESTORE = new CommandImpl("restore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for migrating keys in a cluster.
   */
  Command RESTORE_ASKING = new CommandImpl("restore-asking", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the role of the instance in the context of replication.
   */
  Command ROLE = new CommandImpl("role", 1, null, false, false);
  /**
   * Remove and get the last elements in a list.
   */
  Command RPOP = new CommandImpl("rpop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove the last element in a list, prepend it to another list and return it.
   * @deprecated since: 6.2.0, replaced by: {@code LMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  @Deprecated
  Command RPOPLPUSH = new CommandImpl("rpoplpush", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Append one or multiple elements to a list.
   */
  Command RPUSH = new CommandImpl("rpush", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Append an element to a list, only if the list exists.
   */
  Command RPUSHX = new CommandImpl("rpushx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Add one or more members to a set.
   */
  Command SADD = new CommandImpl("sadd", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Synchronously save the dataset to disk.
   */
  Command SAVE = new CommandImpl("save", 1, null, false, false);
  /**
   * Incrementally iterate the keys space.
   */
  Command SCAN = new CommandImpl("scan", -2, true, false, false);
  /**
   * Get the number of members in a set.
   */
  Command SCARD = new CommandImpl("scard", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for Lua scripts management commands.
   */
  Command SCRIPT = new CommandImpl("script", -2, null, false, false);
  /**
   * Subtract multiple sets.
   */
  Command SDIFF = new CommandImpl("sdiff", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Subtract multiple sets and store the resulting set in a key.
   */
  Command SDIFFSTORE = new CommandImpl("sdiffstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * Change the selected database for the current connection.
   */
  Command SELECT = new CommandImpl("select", 2, null, false, false);
  /**
   * Set the string value of a key.
   */
  Command SET = new CommandImpl("set", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets or clears the bit at offset in the string value stored at key.
   */
  Command SETBIT = new CommandImpl("setbit", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the value and expiration of a key.
   */
  Command SETEX = new CommandImpl("setex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the value of a key, only if the key does not exist.
   */
  Command SETNX = new CommandImpl("setnx", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Overwrite part of a string at key starting at the specified offset.
   */
  Command SETRANGE = new CommandImpl("setrange", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Synchronously save the dataset to disk and then shut down the server.
   */
  Command SHUTDOWN = new CommandImpl("shutdown", -1, null, false, false);
  /**
   * Intersect multiple sets.
   */
  Command SINTER = new CommandImpl("sinter", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Intersect multiple sets and return the cardinality of the result.
   */
  Command SINTERCARD = new CommandImpl("sintercard", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Intersect multiple sets and store the resulting set in a key.
   */
  Command SINTERSTORE = new CommandImpl("sinterstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * Determine if a given value is a member of a set.
   */
  Command SISMEMBER = new CommandImpl("sismember", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Make the server a replica of another instance, or promote it as master.
   * @deprecated since: 5.0.0, replaced by: {@code REPLICAOF}
   */
  @Deprecated
  Command SLAVEOF = new CommandImpl("slaveof", 3, null, false, false);
  /**
   * A container for slow log commands.
   */
  Command SLOWLOG = new CommandImpl("slowlog", -2, null, false, false);
  /**
   * Get all the members in a set.
   */
  Command SMEMBERS = new CommandImpl("smembers", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the membership associated with the given elements for a set.
   */
  Command SMISMEMBER = new CommandImpl("smismember", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Move a member from one set to another.
   */
  Command SMOVE = new CommandImpl("smove", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Sort the elements in a list, set or sorted set.
   */
  Command SORT = new CommandImpl("sort", -2, false, false, true, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sort the elements in a list, set or sorted set. Read-only variant of SORT.
   */
  Command SORT_RO = new CommandImpl("sort_ro", -2, true, false, true, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return one or multiple random members from a set.
   */
  Command SPOP = new CommandImpl("spop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Post a message to a shard channel.
   */
  Command SPUBLISH = new CommandImpl("spublish", 3, null, false, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get one or multiple random members from a set.
   */
  Command SRANDMEMBER = new CommandImpl("srandmember", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove one or more members from a set.
   */
  Command SREM = new CommandImpl("srem", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Incrementally iterate Set elements.
   */
  Command SSCAN = new CommandImpl("sscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listen for messages published to the given shard channels.
   */
  Command SSUBSCRIBE = new CommandImpl("ssubscribe", -2, null, true, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Get the length of the value stored in a key.
   */
  Command STRLEN = new CommandImpl("strlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listen for messages published to the given channels.
   */
  Command SUBSCRIBE = new CommandImpl("subscribe", -2, null, true, false);
  /**
   * Get a substring of the string stored at a key.
   * @deprecated since: 2.0.0, replaced by: {@code GETRANGE}
   */
  @Deprecated
  Command SUBSTR = new CommandImpl("substr", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Add multiple sets.
   */
  Command SUNION = new CommandImpl("sunion", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Add multiple sets and store the resulting set in a key.
   */
  Command SUNIONSTORE = new CommandImpl("sunionstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * Stop listening for messages posted to the given shard channels.
   */
  Command SUNSUBSCRIBE = new CommandImpl("sunsubscribe", -1, null, true, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Swaps two Redis databases.
   */
  Command SWAPDB = new CommandImpl("swapdb", 3, false, false, false);
  /**
   * Internal command used for replication.
   */
  Command SYNC = new CommandImpl("sync", 1, null, false, false);
  Command TDIGEST_ADD = new CommandImpl("tdigest.add", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_BYRANK = new CommandImpl("tdigest.byrank", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_BYREVRANK = new CommandImpl("tdigest.byrevrank", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_CDF = new CommandImpl("tdigest.cdf", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_CREATE = new CommandImpl("tdigest.create", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_INFO = new CommandImpl("tdigest.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_MAX = new CommandImpl("tdigest.max", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_MERGE = new CommandImpl("tdigest.merge", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_MIN = new CommandImpl("tdigest.min", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_QUANTILE = new CommandImpl("tdigest.quantile", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_RANK = new CommandImpl("tdigest.rank", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_RESET = new CommandImpl("tdigest.reset", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_REVRANK = new CommandImpl("tdigest.revrank", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TDIGEST_TRIMMED_MEAN = new CommandImpl("tdigest.trimmed_mean", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the current server time.
   */
  Command TIME = new CommandImpl("time", 1, null, false, false);
  Command TIMESERIES_CLUSTERSET = new CommandImpl("timeseries.CLUSTERSET", -1, true, false, false);
  Command TIMESERIES_CLUSTERSETFROMSHARD = new CommandImpl("timeseries.CLUSTERSETFROMSHARD", -1, true, false, false);
  Command TIMESERIES_HELLO = new CommandImpl("timeseries.HELLO", -1, true, false, false);
  Command TIMESERIES_INFOCLUSTER = new CommandImpl("timeseries.INFOCLUSTER", -1, true, false, false);
  Command TIMESERIES_INNERCOMMUNICATION = new CommandImpl("timeseries.INNERCOMMUNICATION", -1, true, false, false);
  Command TIMESERIES_NETWORKTEST = new CommandImpl("timeseries.NETWORKTEST", -1, true, false, false);
  Command TIMESERIES_REFRESHCLUSTER = new CommandImpl("timeseries.REFRESHCLUSTER", -1, true, false, false);
  Command TOPK_ADD = new CommandImpl("topk.add", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_COUNT = new CommandImpl("topk.count", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_INCRBY = new CommandImpl("topk.incrby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_INFO = new CommandImpl("topk.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_LIST = new CommandImpl("topk.list", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_QUERY = new CommandImpl("topk.query", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TOPK_RESERVE = new CommandImpl("topk.reserve", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Alters the last access time of a key(s). Returns the number of existing keys specified.
   */
  Command TOUCH = new CommandImpl("touch", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  Command TS_ADD = new CommandImpl("ts.add", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_ALTER = new CommandImpl("ts.alter", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_CREATE = new CommandImpl("ts.create", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_CREATERULE = new CommandImpl("ts.createrule", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_DECRBY = new CommandImpl("ts.decrby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_DEL = new CommandImpl("ts.del", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_DELETERULE = new CommandImpl("ts.deleterule", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_GET = new CommandImpl("ts.get", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_INCRBY = new CommandImpl("ts.incrby", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_INFO = new CommandImpl("ts.info", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_MADD = new CommandImpl("ts.madd", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 3, 0)));
  Command TS_MGET = new CommandImpl("ts.mget", -1, true, false, false);
  Command TS_MRANGE = new CommandImpl("ts.mrange", -1, true, false, false);
  Command TS_MREVRANGE = new CommandImpl("ts.mrevrange", -1, true, false, false);
  Command TS_QUERYINDEX = new CommandImpl("ts.queryindex", -1, true, false, false);
  Command TS_RANGE = new CommandImpl("ts.range", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  Command TS_REVRANGE = new CommandImpl("ts.revrange", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the time to live for a key in seconds.
   */
  Command TTL = new CommandImpl("ttl", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Determine the type stored at key.
   */
  Command TYPE = new CommandImpl("type", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete a key asynchronously in another thread. Otherwise it is just as DEL, but non blocking.
   */
  Command UNLINK = new CommandImpl("unlink", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Stop listening for messages posted to the given channels.
   */
  Command UNSUBSCRIBE = new CommandImpl("unsubscribe", -1, null, true, false);
  /**
   * Forget about all watched keys.
   */
  Command UNWATCH = new CommandImpl("unwatch", 1, null, false, false);
  /**
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection.
   */
  Command WAIT = new CommandImpl("wait", 3, null, false, false);
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block.
   */
  Command WATCH = new CommandImpl("watch", -2, null, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Marks a pending message as correctly processed, effectively removing it from the pending entries list of the consumer group. Return value of the command is the number of messages successfully acknowledged, that is, the IDs we were actually able to resolve in the PEL.
   */
  Command XACK = new CommandImpl("xack", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Appends a new entry to a stream.
   */
  Command XADD = new CommandImpl("xadd", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Changes (or acquires) ownership of messages in a consumer group, as if the messages were delivered to the specified consumer.
   */
  Command XAUTOCLAIM = new CommandImpl("xautoclaim", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Changes (or acquires) ownership of a message in a consumer group, as if the message was delivered to the specified consumer.
   */
  Command XCLAIM = new CommandImpl("xclaim", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes the specified entries from the stream. Returns the number of items actually deleted, that may be different from the number of IDs passed in case certain IDs do not exist.
   */
  Command XDEL = new CommandImpl("xdel", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for consumer groups commands.
   */
  Command XGROUP = new CommandImpl("xgroup", -2, null, false, false);
  /**
   * A container for stream introspection commands.
   */
  Command XINFO = new CommandImpl("xinfo", -2, null, false, false);
  /**
   * Return the number of entries in a stream.
   */
  Command XLEN = new CommandImpl("xlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return information and entries from a stream consumer group pending entries list, that are messages fetched but never acknowledged.
   */
  Command XPENDING = new CommandImpl("xpending", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of elements in a stream, with IDs matching the specified IDs interval.
   */
  Command XRANGE = new CommandImpl("xrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return never seen elements in multiple streams, with IDs greater than the ones reported by the caller for each stream. Can block.
   */
  Command XREAD = new CommandImpl("xread", -4, true, false, false, new KeyLocator(true, new BeginSearchKeyword("STREAMS", 1), new FindKeysRange(-1, 1, 2)));
  /**
   * Return new entries from a stream using a consumer group, or access the history of the pending entries for a given consumer. Can block.
   */
  Command XREADGROUP = new CommandImpl("xreadgroup", -7, false, false, false, new KeyLocator(true, new BeginSearchKeyword("STREAMS", 4), new FindKeysRange(-1, 1, 2)));
  /**
   * Return a range of elements in a stream, with IDs matching the specified IDs interval, in reverse order (from greater to smaller IDs) compared to XRANGE.
   */
  Command XREVRANGE = new CommandImpl("xrevrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for replicating stream values.
   */
  Command XSETID = new CommandImpl("xsetid", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Trims the stream to (approximately if '~' is passed) a certain size.
   */
  Command XTRIM = new CommandImpl("xtrim", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Add one or more members to a sorted set, or update its score if it already exists.
   */
  Command ZADD = new CommandImpl("zadd", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the number of members in a sorted set.
   */
  Command ZCARD = new CommandImpl("zcard", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Count the members in a sorted set with scores within the given values.
   */
  Command ZCOUNT = new CommandImpl("zcount", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Subtract multiple sorted sets.
   */
  Command ZDIFF = new CommandImpl("zdiff", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Subtract multiple sorted sets and store the resulting sorted set in a new key.
   */
  Command ZDIFFSTORE = new CommandImpl("zdiffstore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Increment the score of a member in a sorted set.
   */
  Command ZINCRBY = new CommandImpl("zincrby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Intersect multiple sorted sets.
   */
  Command ZINTER = new CommandImpl("zinter", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Intersect multiple sorted sets and return the cardinality of the result.
   */
  Command ZINTERCARD = new CommandImpl("zintercard", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key.
   */
  Command ZINTERSTORE = new CommandImpl("zinterstore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Count the number of members in a sorted set between a given lexicographical range.
   */
  Command ZLEXCOUNT = new CommandImpl("zlexcount", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return members with scores in a sorted set.
   */
  Command ZMPOP = new CommandImpl("zmpop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Get the score associated with the given members in a sorted set.
   */
  Command ZMSCORE = new CommandImpl("zmscore", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return members with the highest scores in a sorted set.
   */
  Command ZPOPMAX = new CommandImpl("zpopmax", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return members with the lowest scores in a sorted set.
   */
  Command ZPOPMIN = new CommandImpl("zpopmin", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get one or multiple random elements from a sorted set.
   */
  Command ZRANDMEMBER = new CommandImpl("zrandmember", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set.
   */
  Command ZRANGE = new CommandImpl("zrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set, by lexicographical range.
   * @deprecated since: 6.2.0, replaced by: {@code ZRANGE} with the {@code BYLEX} argument
   */
  @Deprecated
  Command ZRANGEBYLEX = new CommandImpl("zrangebylex", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set, by score.
   * @deprecated since: 6.2.0, replaced by: {@code ZRANGE} with the {@code BYSCORE} argument
   */
  @Deprecated
  Command ZRANGEBYSCORE = new CommandImpl("zrangebyscore", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Store a range of members from sorted set into another key.
   */
  Command ZRANGESTORE = new CommandImpl("zrangestore", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Determine the index of a member in a sorted set.
   */
  Command ZRANK = new CommandImpl("zrank", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove one or more members from a sorted set.
   */
  Command ZREM = new CommandImpl("zrem", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove all members in a sorted set between the given lexicographical range.
   */
  Command ZREMRANGEBYLEX = new CommandImpl("zremrangebylex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove all members in a sorted set within the given indexes.
   */
  Command ZREMRANGEBYRANK = new CommandImpl("zremrangebyrank", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove all members in a sorted set within the given scores.
   */
  Command ZREMRANGEBYSCORE = new CommandImpl("zremrangebyscore", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low.
   * @deprecated since: 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} argument
   */
  @Deprecated
  Command ZREVRANGE = new CommandImpl("zrevrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set, by lexicographical range, ordered from higher to lower strings.
   * @deprecated since: 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYLEX} arguments
   */
  @Deprecated
  Command ZREVRANGEBYLEX = new CommandImpl("zrevrangebylex", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low.
   * @deprecated since: 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYSCORE} arguments
   */
  @Deprecated
  Command ZREVRANGEBYSCORE = new CommandImpl("zrevrangebyscore", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low.
   */
  Command ZREVRANK = new CommandImpl("zrevrank", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Incrementally iterate sorted sets elements and associated scores.
   */
  Command ZSCAN = new CommandImpl("zscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the score associated with the given member in a sorted set.
   */
  Command ZSCORE = new CommandImpl("zscore", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Add multiple sorted sets.
   */
  Command ZUNION = new CommandImpl("zunion", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key.
   */
  Command ZUNIONSTORE = new CommandImpl("zunionstore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));

  // synthetic
  Command SENTINEL = create("sentinel");

  /**
   * Generic command generator for extensions.
   * <p>
   * To avoid inconsistent behavior, when {@code command} is one of the known commands
   * for which a static instance exists, the static instance is returned.
   *
   * @param command command name
   * @return the cacheable immutable command instance
   */
  static Command create(String command) {
    Command known = CommandMap.getKnownCommand(command);
    return known != null ? known : new CommandImpl(command, -1, null, false, true);
  }
  
  static Command create(String command, int arity, Boolean readOnly, boolean pubsub, boolean needGetKeys) {
    return new CommandImpl(command, arity, readOnly, pubsub, needGetKeys);
  }
}
