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

import io.vertx.codegen.annotations.DataObject;
import io.vertx.redis.client.impl.CommandImpl;
import io.vertx.redis.client.impl.CommandMap;
import io.vertx.redis.client.impl.KeyLocator;
import io.vertx.redis.client.impl.keys.BeginSearchIndex;
import io.vertx.redis.client.impl.keys.BeginSearchKeyword;
import io.vertx.redis.client.impl.keys.FindKeysKeynum;
import io.vertx.redis.client.impl.keys.FindKeysRange;

/**
 * <b>Auto generated</b> API Commands to interact with REDIS.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 * @version redis_version:8.6.2
 */
@DataObject
public interface Command {

  /**
   * A container for Access List Control commands.
   * @since Redis 6.0.0
   */
  Command ACL = new CommandImpl("acl", -2, null, false, false);
  /**
   * Appends a string to the value of a key. Creates the key if it doesn't exist.
   * @since Redis 2.0.0
   */
  Command APPEND = new CommandImpl("append", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Signals that a cluster client is following an -ASK redirect.
   * @since Redis 3.0.0
   */
  Command ASKING = new CommandImpl("asking", 1, null, false, false);
  /**
   * Authenticates the connection.
   * @since Redis 1.0.0
   */
  Command AUTH = new CommandImpl("auth", -2, null, false, false);
  /**
   * Adds an item to a Bloom Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_ADD = new CommandImpl("bf.add", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the cardinality of a Bloom filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.4
   */
  Command BF_CARD = new CommandImpl("bf.card", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command BF_DEBUG = new CommandImpl("bf.debug", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Checks whether an item exists in a Bloom Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_EXISTS = new CommandImpl("bf.exists", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information about a Bloom Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_INFO = new CommandImpl("bf.info", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more items to a Bloom Filter. A filter will be created if it does not exist.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_INSERT = new CommandImpl("bf.insert", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Restores a filter previously saved using SCANDUMP.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_LOADCHUNK = new CommandImpl("bf.loadchunk", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more items to a Bloom Filter. A filter will be created if it does not exist.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_MADD = new CommandImpl("bf.madd", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Checks whether one or more items exist in a Bloom Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_MEXISTS = new CommandImpl("bf.mexists", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Creates a new Bloom Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_RESERVE = new CommandImpl("bf.reserve", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Begins an incremental save of the bloom filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command BF_SCANDUMP = new CommandImpl("bf.scandump", 3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Asynchronously rewrites the append-only file to disk.
   * @since Redis 1.0.0
   */
  Command BGREWRITEAOF = new CommandImpl("bgrewriteaof", 1, null, false, false);
  /**
   * Asynchronously saves the database(s) to disk.
   * @since Redis 1.0.0
   */
  Command BGSAVE = new CommandImpl("bgsave", -1, null, false, false);
  /**
   * Counts the number of set bits (population counting) in a string.
   * @since Redis 2.6.0
   */
  Command BITCOUNT = new CommandImpl("bitcount", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Performs arbitrary bitfield integer operations on strings.
   * @since Redis 3.2.0
   */
  Command BITFIELD = new CommandImpl("bitfield", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Performs arbitrary read-only bitfield integer operations on strings.
   * @since Redis 6.0.0
   */
  Command BITFIELD_RO = new CommandImpl("bitfield_ro", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Performs bitwise operations on multiple strings, and stores the result.
   * @since Redis 2.6.0
   */
  Command BITOP = new CommandImpl("bitop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(3), new FindKeysRange(-1, 1, 0)));
  /**
   * Finds the first set (1) or clear (0) bit in a string.
   * @since Redis 2.8.7
   */
  Command BITPOS = new CommandImpl("bitpos", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Pops an element from a list, pushes it to another list and returns it. Blocks until an element is available otherwise. Deletes the list if the last element was moved.
   * @since Redis 6.2.0
   */
  Command BLMOVE = new CommandImpl("blmove", 6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Pops the first element from one of multiple lists. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @since Redis 7.0.0
   */
  Command BLMPOP = new CommandImpl("blmpop", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Removes and returns the first element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @since Redis 2.0.0
   */
  Command BLPOP = new CommandImpl("blpop", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Removes and returns the last element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @since Redis 2.0.0
   */
  Command BRPOP = new CommandImpl("brpop", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Pops an element from a list, pushes it to another list and returns it. Block until an element is available otherwise. Deletes the list if the last element was popped.
   * @since Redis 2.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code BLMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  Command BRPOPLPUSH = new CommandImpl("brpoplpush", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Removes and returns a member by score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @since Redis 7.0.0
   */
  Command BZMPOP = new CommandImpl("bzmpop", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Removes and returns the member with the highest score from one or more sorted sets. Blocks until a member available otherwise.  Deletes the sorted set if the last element was popped.
   * @since Redis 5.0.0
   */
  Command BZPOPMAX = new CommandImpl("bzpopmax", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Removes and returns the member with the lowest score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @since Redis 5.0.0
   */
  Command BZPOPMIN = new CommandImpl("bzpopmin", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-2, 1, 0)));
  /**
   * Adds an item to a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_ADD = new CommandImpl("cf.add", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds an item to a Cuckoo Filter if the item did not exist previously.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_ADDNX = new CommandImpl("cf.addnx", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command CF_COMPACT = new CommandImpl("cf.compact", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the number of times an item might be in a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_COUNT = new CommandImpl("cf.count", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command CF_DEBUG = new CommandImpl("cf.debug", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes an item from a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_DEL = new CommandImpl("cf.del", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Checks whether one or more items exist in a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_EXISTS = new CommandImpl("cf.exists", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information about a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_INFO = new CommandImpl("cf.info", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more items to a Cuckoo Filter. A filter will be created if it does not exist.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_INSERT = new CommandImpl("cf.insert", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more items to a Cuckoo Filter if the items did not exist previously. A filter will be created if it does not exist.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_INSERTNX = new CommandImpl("cf.insertnx", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Restores a filter previously saved using SCANDUMP.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_LOADCHUNK = new CommandImpl("cf.loadchunk", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Checks whether one or more items exist in a Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_MEXISTS = new CommandImpl("cf.mexists", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Creates a new Cuckoo Filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_RESERVE = new CommandImpl("cf.reserve", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Begins an incremental save of the bloom filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 1.0.0
   */
  Command CF_SCANDUMP = new CommandImpl("cf.scandump", 3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for client connection commands.
   * @since Redis 2.4.0
   */
  Command CLIENT = new CommandImpl("client", -2, null, false, false);
  /**
   * A container for Redis Cluster commands.
   * @since Redis 3.0.0
   */
  Command CLUSTER = new CommandImpl("cluster", -2, null, false, false);
  /**
   * Increases the count of one or more items by increment.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_INCRBY = new CommandImpl("cms.incrby", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information about a sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_INFO = new CommandImpl("cms.info", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Initializes a Count-Min Sketch to dimensions specified by user.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_INITBYDIM = new CommandImpl("cms.initbydim", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Initializes a Count-Min Sketch to accommodate requested tolerances.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_INITBYPROB = new CommandImpl("cms.initbyprob", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Merges several sketches into one sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_MERGE = new CommandImpl("cms.merge", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the count for one or more items in a sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command CMS_QUERY = new CommandImpl("cms.query", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns detailed information about all commands.
   * @since Redis 2.8.13
   */
  Command COMMAND = new CommandImpl("command", -1, null, false, false);
  /**
   * A container for command log commands.
   * <p>
   * This command only exists in Valkey.
   * @since Valkey 8.1.0
   */
  Command COMMANDLOG = new CommandImpl("commandlog", -2, null, false, false);
  /**
   * A container for server configuration commands.
   * @since Redis 2.0.0
   */
  Command CONFIG = new CommandImpl("config", -2, null, false, false);
  /**
   * Copies the value of a key to a new key.
   * @since Redis 6.2.0
   */
  Command COPY = new CommandImpl("copy", -3, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the number of keys in the database.
   * @since Redis 1.0.0
   */
  Command DBSIZE = new CommandImpl("dbsize", 1, true, false, false);
  /**
   * A container for debugging commands.
   * @since Redis 1.0.0
   */
  Command DEBUG = new CommandImpl("debug", -2, null, false, false);
  /**
   * Decrements the integer value of a key by one. Uses 0 as initial value if the key doesn't exist.
   * @since Redis 1.0.0
   */
  Command DECR = new CommandImpl("decr", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Decrements a number from the integer value of a key. Uses 0 as initial value if the key doesn't exist.
   * @since Redis 1.0.0
   */
  Command DECRBY = new CommandImpl("decrby", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes one or more keys.
   * @since Redis 1.0.0
   */
  Command DEL = new CommandImpl("del", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Conditionally removes the specified key based on value or digest comparison.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.4.0
   */
  Command DELEX = new CommandImpl("delex", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete key if value matches string.
   * <p>
   * This command only exists in Valkey.
   * @since Valkey 9.0.0
   */
  Command DELIFEQ = new CommandImpl("delifeq", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the XXH3 hash of a string value.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.4.0
   */
  Command DIGEST = new CommandImpl("digest", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Discards a transaction.
   * @since Redis 2.0.0
   */
  Command DISCARD = new CommandImpl("discard", 1, null, false, false);
  /**
   * Returns a serialized representation of the value stored at a key.
   * @since Redis 2.6.0
   */
  Command DUMP = new CommandImpl("dump", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the given string.
   * @since Redis 1.0.0
   */
  Command ECHO = new CommandImpl("echo", 2, null, false, false);
  /**
   * Executes a server-side Lua script.
   * @since Redis 2.6.0
   */
  Command EVAL = new CommandImpl("eval", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Executes a server-side Lua script by SHA1 digest.
   * @since Redis 2.6.0
   */
  Command EVALSHA = new CommandImpl("evalsha", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Executes a read-only server-side Lua script by SHA1 digest.
   * @since Redis 7.0.0
   */
  Command EVALSHA_RO = new CommandImpl("evalsha_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Executes a read-only server-side Lua script.
   * @since Redis 7.0.0
   */
  Command EVAL_RO = new CommandImpl("eval_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Executes all commands in a transaction.
   * @since Redis 1.2.0
   */
  Command EXEC = new CommandImpl("exec", 1, null, false, false);
  /**
   * Determines whether one or more keys exist.
   * @since Redis 1.0.0
   */
  Command EXISTS = new CommandImpl("exists", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Sets the expiration time of a key in seconds.
   * @since Redis 1.0.0
   */
  Command EXPIRE = new CommandImpl("expire", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the expiration time of a key to a Unix timestamp.
   * @since Redis 1.2.0
   */
  Command EXPIREAT = new CommandImpl("expireat", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the expiration time of a key as a Unix timestamp.
   * @since Redis 7.0.0
   */
  Command EXPIRETIME = new CommandImpl("expiretime", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Starts a coordinated failover from a server to one of its replicas.
   * @since Redis 6.2.0
   */
  Command FAILOVER = new CommandImpl("failover", -1, null, false, false);
  /**
   * Invokes a function.
   * @since Redis 7.0.0
   */
  Command FCALL = new CommandImpl("fcall", -3, null, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Invokes a read-only function.
   * @since Redis 7.0.0
   */
  Command FCALL_RO = new CommandImpl("fcall_ro", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Removes all keys from all databases.
   * @since Redis 1.0.0
   */
  Command FLUSHALL = new CommandImpl("flushall", -1, false, false, false);
  /**
   * Remove all keys from the current database.
   * @since Redis 1.0.0
   */
  Command FLUSHDB = new CommandImpl("flushdb", -1, false, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_ADD = new CommandImpl("FT.ADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Run a search query on an index and perform aggregate transformations on the results.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.1.0
   */
  Command FT_AGGREGATE = new CommandImpl("FT.AGGREGATE", -3, true, false, false);
  /**
   * Adds an alias to the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_ALIASADD = new CommandImpl("FT.ALIASADD", -3, false, false, false);
  /**
   * Deletes an alias from the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_ALIASDEL = new CommandImpl("FT.ALIASDEL", -2, false, false, false);
  /**
   * Adds or updates an alias to the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_ALIASUPDATE = new CommandImpl("FT.ALIASUPDATE", -3, false, false, false);
  /**
   * Adds a new field to the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_ALTER = new CommandImpl("FT.ALTER", -6, false, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_CONFIG = new CommandImpl("FT.CONFIG", -2, true, false, false);
  /**
   * Creates an index with the given spec.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_CREATE = new CommandImpl("FT.CREATE", -4, false, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_CURSOR = new CommandImpl("FT.CURSOR", -2, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command FT_DEBUG = new CommandImpl("FT.DEBUG", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_DEL = new CommandImpl("FT.DEL", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Adds terms to a dictionary.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.4.0
   */
  Command FT_DICTADD = new CommandImpl("FT.DICTADD", -3, false, false, false);
  /**
   * Deletes terms from a dictionary.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.4.0
   */
  Command FT_DICTDEL = new CommandImpl("FT.DICTDEL", -3, false, false, false);
  /**
   * Dumps all terms in the given dictionary.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.4.0
   */
  Command FT_DICTDUMP = new CommandImpl("FT.DICTDUMP", -2, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_DROP = new CommandImpl("FT.DROP", -1, false, false, false);
  /**
   * Deletes the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 2.0.0
   */
  Command FT_DROPINDEX = new CommandImpl("FT.DROPINDEX", -2, false, false, false);
  /**
   * Returns the execution plan for a complex query.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_EXPLAIN = new CommandImpl("FT.EXPLAIN", -3, true, false, false);
  /**
   * Returns the execution plan for a complex query.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_EXPLAINCLI = new CommandImpl("FT.EXPLAINCLI", -3, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_GET = new CommandImpl("FT.GET", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Performs hybrid search combining text search and vector similarity search.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 8.4.4
   */
  Command FT_HYBRID = new CommandImpl("FT.HYBRID", -4, true, false, false);
  /**
   * Returns information and statistics on the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_INFO = new CommandImpl("FT.INFO", -2, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_MGET = new CommandImpl("FT.MGET", -1, true, false, false);
  /**
   * Performs a `FT.SEARCH` or `FT.AGGREGATE` command and collects performance information.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 2.2.0
   */
  Command FT_PROFILE = new CommandImpl("FT.PROFILE", -5, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_SAFEADD = new CommandImpl("FT.SAFEADD", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Searches the index with a textual query, returning either documents or just ids.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_SEARCH = new CommandImpl("FT.SEARCH", -3, true, false, false);
  /**
   * Performs spelling correction on a query, returning suggestions for misspelled terms.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.4.0
   */
  Command FT_SPELLCHECK = new CommandImpl("FT.SPELLCHECK", -3, true, false, false);
  /**
   * Adds a suggestion string to an auto-complete suggestion dictionary.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_SUGADD = new CommandImpl("FT.SUGADD", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes a string from a suggestion index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_SUGDEL = new CommandImpl("FT.SUGDEL", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Gets completion suggestions for a prefix.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_SUGGET = new CommandImpl("FT.SUGGET", -3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Gets the size of an auto-complete suggestion dictionary.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_SUGLEN = new CommandImpl("FT.SUGLEN", -2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT_SYNADD = new CommandImpl("FT.SYNADD", -1, false, false, false);
  /**
   * Dumps the contents of a synonym group.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.2.0
   */
  Command FT_SYNDUMP = new CommandImpl("FT.SYNDUMP", -2, true, false, false);
  /**
   * Creates or updates a synonym group with additional terms.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.2.0
   */
  Command FT_SYNUPDATE = new CommandImpl("FT.SYNUPDATE", -4, false, false, false);
  /**
   * Returns the distinct tags indexed in a Tag field.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT_TAGVALS = new CommandImpl("FT.TAGVALS", -3, true, false, false);
  /**
   * Adds an alias to the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT__ALIASADDIFNX = new CommandImpl("FT._ALIASADDIFNX", -3, false, false, false);
  /**
   * Deletes an alias from the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT__ALIASDELIFX = new CommandImpl("FT._ALIASDELIFX", -2, false, false, false);
  /**
   * Adds a new field to the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT__ALTERIFNX = new CommandImpl("FT._ALTERIFNX", -6, false, false, false);
  /**
   * Creates an index with the given spec.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 1.0.0
   */
  Command FT__CREATEIFNX = new CommandImpl("FT._CREATEIFNX", -4, false, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command FT__DROPIFX = new CommandImpl("FT._DROPIFX", -1, false, false, false);
  /**
   * Deletes the index.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 2.0.0
   */
  Command FT__DROPINDEXIFX = new CommandImpl("FT._DROPINDEXIFX", -2, false, false, false);
  /**
   * Returns a list of all existing indexes.
   * <p>
   * This command only exists in Redis.
   * @since Redis Search 2.0.0
   */
  Command FT__LIST = new CommandImpl("FT._LIST", -1, true, false, false);
  /**
   * A container for function commands.
   * @since Redis 7.0.0
   */
  Command FUNCTION = new CommandImpl("function", -2, null, false, false);
  /**
   * Adds one or more members to a geospatial index. The key is created if it doesn't exist.
   * @since Redis 3.2.0
   */
  Command GEOADD = new CommandImpl("geoadd", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the distance between two members of a geospatial index.
   * @since Redis 3.2.0
   */
  Command GEODIST = new CommandImpl("geodist", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members from a geospatial index as geohash strings.
   * @since Redis 3.2.0
   */
  Command GEOHASH = new CommandImpl("geohash", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the longitude and latitude of members from a geospatial index.
   * @since Redis 3.2.0
   */
  Command GEOPOS = new CommandImpl("geopos", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Queries a geospatial index for members within a distance from a coordinate, optionally stores the result.
   * @since Redis 3.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} argument
   */
  Command GEORADIUS = new CommandImpl("georadius", -6, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STORE", 6), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STOREDIST", 6), new FindKeysRange(0, 1, 0)));
  /**
   * Queries a geospatial index for members within a distance from a member, optionally stores the result.
   * @since Redis 3.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} and {@code GEOSEARCHSTORE} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  Command GEORADIUSBYMEMBER = new CommandImpl("georadiusbymember", -5, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STORE", 5), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("STOREDIST", 5), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members from a geospatial index that are within a distance from a member.
   * @since Redis 3.2.10
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} and {@code FROMMEMBER} arguments
   */
  Command GEORADIUSBYMEMBER_RO = new CommandImpl("georadiusbymember_ro", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members from a geospatial index that are within a distance from a coordinate.
   * @since Redis 3.2.10
   * @deprecated since Redis 6.2.0, replaced by: {@code GEOSEARCH} with the {@code BYRADIUS} argument
   */
  Command GEORADIUS_RO = new CommandImpl("georadius_ro", -6, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Queries a geospatial index for members inside an area of a box or a circle.
   * @since Redis 6.2.0
   */
  Command GEOSEARCH = new CommandImpl("geosearch", -7, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Queries a geospatial index for members inside an area of a box or a circle, optionally stores the result.
   * @since Redis 6.2.0
   */
  Command GEOSEARCHSTORE = new CommandImpl("geosearchstore", -8, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the string value of a key.
   * @since Redis 1.0.0
   */
  Command GET = new CommandImpl("get", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns a bit value by offset.
   * @since Redis 2.2.0
   */
  Command GETBIT = new CommandImpl("getbit", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the string value of a key after deleting the key.
   * @since Redis 6.2.0
   */
  Command GETDEL = new CommandImpl("getdel", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the string value of a key after setting its expiration time.
   * @since Redis 6.2.0
   */
  Command GETEX = new CommandImpl("getex", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns a substring of the string stored at a key.
   * @since Redis 2.4.0
   */
  Command GETRANGE = new CommandImpl("getrange", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the previous string value of a key after setting it to a new value.
   * @since Redis 1.0.0
   * @deprecated since Redis 6.2.0, replaced by: {@code SET} with the {@code !GET} argument
   */
  Command GETSET = new CommandImpl("getset", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_BULK = new CommandImpl("graph.BULK", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_CONFIG = new CommandImpl("graph.CONFIG", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_DEBUG = new CommandImpl("graph.DEBUG", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_DELETE = new CommandImpl("graph.DELETE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_EXPLAIN = new CommandImpl("graph.EXPLAIN", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_LIST = new CommandImpl("graph.LIST", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_PROFILE = new CommandImpl("graph.PROFILE", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_QUERY = new CommandImpl("graph.QUERY", -1, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_RO_QUERY = new CommandImpl("graph.RO_QUERY", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command GRAPH_SLOWLOG = new CommandImpl("graph.SLOWLOG", -1, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes one or more fields and their values from a hash. Deletes the hash if no fields remain.
   * @since Redis 2.0.0
   */
  Command HDEL = new CommandImpl("hdel", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Handshakes with the Redis server.
   * @since Redis 6.0.0
   */
  Command HELLO = new CommandImpl("hello", -1, null, false, false);
  /**
   * Determines whether a field exists in a hash.
   * @since Redis 2.0.0
   */
  Command HEXISTS = new CommandImpl("hexists", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set expiry for hash field using relative time to expire (seconds).
   * @since Redis 7.4.0
   */
  Command HEXPIRE = new CommandImpl("hexpire", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set expiry for hash field using an absolute Unix timestamp (seconds).
   * @since Redis 7.4.0
   */
  Command HEXPIREAT = new CommandImpl("hexpireat", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the expiration time of a hash field as a Unix timestamp, in seconds.
   * @since Redis 7.4.0
   */
  Command HEXPIRETIME = new CommandImpl("hexpiretime", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the value of a field in a hash.
   * @since Redis 2.0.0
   */
  Command HGET = new CommandImpl("hget", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns all fields and values in a hash.
   * @since Redis 2.0.0
   */
  Command HGETALL = new CommandImpl("hgetall", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the value of a field and deletes it from the hash.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.0.0
   */
  Command HGETDEL = new CommandImpl("hgetdel", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get the value of one or more fields of a given hash key, and optionally set their expiration.
   * @since Redis 8.0.0
   */
  Command HGETEX = new CommandImpl("hgetex", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increments the integer value of a field in a hash by a number. Uses 0 as initial value if the field doesn't exist.
   * @since Redis 2.0.0
   */
  Command HINCRBY = new CommandImpl("hincrby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increments the floating point value of a field by a number. Uses 0 as initial value if the field doesn't exist.
   * @since Redis 2.6.0
   */
  Command HINCRBYFLOAT = new CommandImpl("hincrbyfloat", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns all fields in a hash.
   * @since Redis 2.0.0
   */
  Command HKEYS = new CommandImpl("hkeys", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the number of fields in a hash.
   * @since Redis 2.0.0
   */
  Command HLEN = new CommandImpl("hlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the values of all fields in a hash.
   * @since Redis 2.0.0
   */
  Command HMGET = new CommandImpl("hmget", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the values of multiple fields.
   * @since Redis 2.0.0
   * @deprecated since Redis 4.0.0, replaced by: {@code HSET} with multiple field-value pairs
   */
  Command HMSET = new CommandImpl("hmset", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for hotkeys tracking commands.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.6.0
   */
  Command HOTKEYS = new CommandImpl("hotkeys", -2, null, false, false);
  /**
   * Removes the expiration time for each specified field.
   * @since Redis 7.4.0
   */
  Command HPERSIST = new CommandImpl("hpersist", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set expiry for hash field using relative time to expire (milliseconds).
   * @since Redis 7.4.0
   */
  Command HPEXPIRE = new CommandImpl("hpexpire", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set expiry for hash field using an absolute Unix timestamp (milliseconds).
   * @since Redis 7.4.0
   */
  Command HPEXPIREAT = new CommandImpl("hpexpireat", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the expiration time of a hash field as a Unix timestamp, in msec.
   * @since Redis 7.4.0
   */
  Command HPEXPIRETIME = new CommandImpl("hpexpiretime", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the TTL in milliseconds of a hash field.
   * @since Redis 7.4.0
   */
  Command HPTTL = new CommandImpl("hpttl", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns one or more random fields from a hash.
   * @since Redis 6.2.0
   */
  Command HRANDFIELD = new CommandImpl("hrandfield", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Iterates over fields and values of a hash.
   * @since Redis 2.8.0
   */
  Command HSCAN = new CommandImpl("hscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Creates or modifies the value of a field in a hash.
   * @since Redis 2.0.0
   */
  Command HSET = new CommandImpl("hset", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the value of one or more fields of a given hash key, and optionally set their expiration.
   * @since Redis 8.0.0
   */
  Command HSETEX = new CommandImpl("hsetex", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the value of a field in a hash only when the field doesn't exist.
   * @since Redis 2.0.0
   */
  Command HSETNX = new CommandImpl("hsetnx", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the length of the value of a field.
   * @since Redis 3.2.0
   */
  Command HSTRLEN = new CommandImpl("hstrlen", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the TTL in seconds of a hash field.
   * @since Redis 7.4.0
   */
  Command HTTL = new CommandImpl("httl", -5, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns all values in a hash.
   * @since Redis 2.0.0
   */
  Command HVALS = new CommandImpl("hvals", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increments the integer value of a key by one. Uses 0 as initial value if the key doesn't exist.
   * @since Redis 1.0.0
   */
  Command INCR = new CommandImpl("incr", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increments the integer value of a key by a number. Uses 0 as initial value if the key doesn't exist.
   * @since Redis 1.0.0
   */
  Command INCRBY = new CommandImpl("incrby", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increment the floating point value of a key by a number. Uses 0 as initial value if the key doesn't exist.
   * @since Redis 2.6.0
   */
  Command INCRBYFLOAT = new CommandImpl("incrbyfloat", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information and statistics about the server.
   * @since Redis 1.0.0
   */
  Command INFO = new CommandImpl("info", -1, null, false, false);
  /**
   * Append the JSON values into the array at path after the last element in it.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRAPPEND = new CommandImpl("json.arrappend", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Search for the first occurrence of a JSON value in an array.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRINDEX = new CommandImpl("json.arrindex", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Insert the json values into the array at path before the index (shifts to the right).
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRINSERT = new CommandImpl("json.arrinsert", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Report the length of the JSON array at path in key.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRLEN = new CommandImpl("json.arrlen", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove and return the element at the specified index in the array at path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRPOP = new CommandImpl("json.arrpop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Trim an array so that it contains only the specified inclusive range of elements.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_ARRTRIM = new CommandImpl("json.arrtrim", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Clear container values (arrays/objects) and set numeric values to 0.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 2.0.0
   */
  Command JSON_CLEAR = new CommandImpl("json.clear", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * This is a container command for debugging related tasks.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_DEBUG = new CommandImpl("json.debug", -2, true, false, false);
  /**
   * Delete a value.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_DEL = new CommandImpl("json.del", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete a value.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_FORGET = new CommandImpl("json.forget", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get JSON value at path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_GET = new CommandImpl("json.get", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Merge a given JSON value into matching paths. Consequently, JSON values at matching paths are updated, deleted, or expanded with new children.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 2.6.0
   */
  Command JSON_MERGE = new CommandImpl("json.merge", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the values at path from multiple key arguments.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_MGET = new CommandImpl("json.mget", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set or update one or more JSON values according to the specified key-path-value triplets.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 2.6.0
   */
  Command JSON_MSET = new CommandImpl("json.mset", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 3, 0)));
  /**
   * Increment the number value stored at path by number.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_NUMINCRBY = new CommandImpl("json.numincrby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Multiply the number value stored at path by number.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_NUMMULTBY = new CommandImpl("json.nummultby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Raise the number value stored at path to the power of number.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_NUMPOWBY = new CommandImpl("json.numpowby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the keys in the object that's referenced by path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_OBJKEYS = new CommandImpl("json.objkeys", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Report the number of keys in the JSON object at path in key.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_OBJLEN = new CommandImpl("json.objlen", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the JSON in key in Redis serialization protocol specification form.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_RESP = new CommandImpl("json.resp", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the JSON value at path in key.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_SET = new CommandImpl("json.set", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Append the json-string values to the string at path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_STRAPPEND = new CommandImpl("json.strappend", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Report the length of the JSON String at path in key.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_STRLEN = new CommandImpl("json.strlen", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Toggle the boolean value stored at path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 2.0.0
   */
  Command JSON_TOGGLE = new CommandImpl("json.toggle", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Report the type of JSON value at path.
   * <p>
   * This command only exists in Redis.
   * @since Redis ReJSON 1.0.0
   */
  Command JSON_TYPE = new CommandImpl("json.type", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns all key names that match a pattern.
   * @since Redis 1.0.0
   */
  Command KEYS = new CommandImpl("keys", 2, true, false, false);
  /**
   * Returns the Unix timestamp of the last successful save to disk.
   * @since Redis 1.0.0
   */
  Command LASTSAVE = new CommandImpl("lastsave", 1, null, false, false);
  /**
   * A container for latency diagnostics commands.
   * @since Redis 2.8.13
   */
  Command LATENCY = new CommandImpl("latency", -2, null, false, false);
  /**
   * Finds the longest common substring.
   * @since Redis 7.0.0
   */
  Command LCS = new CommandImpl("lcs", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(1, 1, 0)));
  /**
   * Returns an element from a list by its index.
   * @since Redis 1.0.0
   */
  Command LINDEX = new CommandImpl("lindex", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Inserts an element before or after another element in a list.
   * @since Redis 2.2.0
   */
  Command LINSERT = new CommandImpl("linsert", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the length of a list.
   * @since Redis 1.0.0
   */
  Command LLEN = new CommandImpl("llen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns an element after popping it from one list and pushing it to another. Deletes the list if the last element was moved.
   * @since Redis 6.2.0
   */
  Command LMOVE = new CommandImpl("lmove", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Returns multiple elements from a list after removing them. Deletes the list if the last element was popped.
   * @since Redis 7.0.0
   */
  Command LMPOP = new CommandImpl("lmpop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Displays computer art and the Redis version.
   * @since Redis 5.0.0
   */
  Command LOLWUT = new CommandImpl("lolwut", -1, true, false, false);
  /**
   * Returns the first elements in a list after removing it. Deletes the list if the last element was popped.
   * @since Redis 1.0.0
   */
  Command LPOP = new CommandImpl("lpop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the index of matching elements in a list.
   * @since Redis 6.0.6
   */
  Command LPOS = new CommandImpl("lpos", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Prepends one or more elements to a list. Creates the key if it doesn't exist.
   * @since Redis 1.0.0
   */
  Command LPUSH = new CommandImpl("lpush", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Prepends one or more elements to a list only when the list exists.
   * @since Redis 2.2.0
   */
  Command LPUSHX = new CommandImpl("lpushx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns a range of elements from a list.
   * @since Redis 1.0.0
   */
  Command LRANGE = new CommandImpl("lrange", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes elements from a list. Deletes the list if the last element was removed.
   * @since Redis 1.0.0
   */
  Command LREM = new CommandImpl("lrem", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the value of an element in a list by its index.
   * @since Redis 1.0.0
   */
  Command LSET = new CommandImpl("lset", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes elements from both ends a list. Deletes the list if all elements were trimmed.
   * @since Redis 1.0.0
   */
  Command LTRIM = new CommandImpl("ltrim", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for memory diagnostics commands.
   * @since Redis 4.0.0
   */
  Command MEMORY = new CommandImpl("memory", -2, null, false, false);
  /**
   * Atomically returns the string values of one or more keys.
   * @since Redis 1.0.0
   */
  Command MGET = new CommandImpl("mget", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Atomically transfers a key from one Redis instance to another.
   * @since Redis 2.6.0
   */
  Command MIGRATE = new CommandImpl("migrate", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(3), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchKeyword("KEYS", -2), new FindKeysRange(-1, 1, 0)));
  /**
   * A container for module commands.
   * @since Redis 4.0.0
   */
  Command MODULE = new CommandImpl("module", -2, null, false, false);
  /**
   * Listens for all requests received by the server in real-time.
   * @since Redis 1.0.0
   */
  Command MONITOR = new CommandImpl("monitor", 1, null, false, false);
  /**
   * Moves a key to another database.
   * @since Redis 1.0.0
   */
  Command MOVE = new CommandImpl("move", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Atomically creates or modifies the string values of one or more keys.
   * @since Redis 1.0.1
   */
  Command MSET = new CommandImpl("mset", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0)));
  /**
   * Atomically sets multiple string keys with a shared expiration in a single operation. Supports flexible argument parsing where condition and expiration flags can appear in any order.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.4.0
   */
  Command MSETEX = new CommandImpl("msetex", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 2)));
  /**
   * Atomically modifies the string values of one or more keys only when all keys don't exist.
   * @since Redis 1.0.1
   */
  Command MSETNX = new CommandImpl("msetnx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 2, 0)));
  /**
   * Starts a transaction.
   * @since Redis 1.2.0
   */
  Command MULTI = new CommandImpl("multi", 1, null, false, false);
  /**
   * A container for object introspection commands.
   * @since Redis 2.2.3
   */
  Command OBJECT = new CommandImpl("object", -2, null, false, false);
  /**
   * Removes the expiration time of a key.
   * @since Redis 2.2.0
   */
  Command PERSIST = new CommandImpl("persist", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the expiration time of a key in milliseconds.
   * @since Redis 2.6.0
   */
  Command PEXPIRE = new CommandImpl("pexpire", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the expiration time of a key to a Unix milliseconds timestamp.
   * @since Redis 2.6.0
   */
  Command PEXPIREAT = new CommandImpl("pexpireat", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the expiration time of a key as a Unix milliseconds timestamp.
   * @since Redis 7.0.0
   */
  Command PEXPIRETIME = new CommandImpl("pexpiretime", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds elements to a HyperLogLog key. Creates the key if it doesn't exist.
   * @since Redis 2.8.9
   */
  Command PFADD = new CommandImpl("pfadd", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the approximated cardinality of the set(s) observed by the HyperLogLog key(s).
   * @since Redis 2.8.9
   */
  Command PFCOUNT = new CommandImpl("pfcount", -2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Internal commands for debugging HyperLogLog values.
   * @since Redis 2.8.9
   */
  Command PFDEBUG = new CommandImpl("pfdebug", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Merges one or more HyperLogLog values into a single key.
   * @since Redis 2.8.9
   */
  Command PFMERGE = new CommandImpl("pfmerge", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * An internal command for testing HyperLogLog values.
   * @since Redis 2.8.9
   */
  Command PFSELFTEST = new CommandImpl("pfselftest", 1, null, false, false);
  /**
   * Returns the server's liveliness response.
   * @since Redis 1.0.0
   */
  Command PING = new CommandImpl("ping", -1, null, false, false);
  /**
   * Sets both string value and expiration time in milliseconds of a key. The key is created if it doesn't exist.
   * @since Redis 2.6.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code PX} argument
   */
  Command PSETEX = new CommandImpl("psetex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listens for messages published to channels that match one or more patterns.
   * @since Redis 2.0.0
   */
  Command PSUBSCRIBE = new CommandImpl("psubscribe", -2, null, true, false);
  /**
   * An internal command used in replication.
   * @since Redis 2.8.0
   */
  Command PSYNC = new CommandImpl("psync", -3, null, false, false);
  /**
   * Returns the expiration time in milliseconds of a key.
   * @since Redis 2.6.0
   */
  Command PTTL = new CommandImpl("pttl", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Posts a message to a channel.
   * @since Redis 2.0.0
   */
  Command PUBLISH = new CommandImpl("publish", 3, null, false, false);
  /**
   * A container for Pub/Sub commands.
   * @since Redis 2.8.0
   */
  Command PUBSUB = new CommandImpl("pubsub", -2, null, false, false);
  /**
   * Stops listening to messages published to channels that match one or more patterns.
   * @since Redis 2.0.0
   */
  Command PUNSUBSCRIBE = new CommandImpl("punsubscribe", -1, null, true, false);
  /**
   * Closes the connection.
   * @since Redis 1.0.0
   * @deprecated since Redis 7.2.0, replaced by: just closing the connection
   */
  Command QUIT = new CommandImpl("quit", -1, null, false, false);
  /**
   * Returns a random key name from the database.
   * @since Redis 1.0.0
   */
  Command RANDOMKEY = new CommandImpl("randomkey", 1, true, false, false);
  /**
   * Enables read-only queries for a connection to a Redis Cluster replica node.
   * @since Redis 3.0.0
   */
  Command READONLY = new CommandImpl("readonly", 1, null, false, false);
  /**
   * Enables read-write queries for a connection to a Reids Cluster replica node.
   * @since Redis 3.0.0
   */
  Command READWRITE = new CommandImpl("readwrite", 1, null, false, false);
  /**
   * Renames a key and overwrites the destination.
   * @since Redis 1.0.0
   */
  Command RENAME = new CommandImpl("rename", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Renames a key only when the target key name doesn't exist.
   * @since Redis 1.0.0
   */
  Command RENAMENX = new CommandImpl("renamenx", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for configuring the replication stream.
   * @since Redis 3.0.0
   */
  Command REPLCONF = new CommandImpl("replconf", -1, null, false, false);
  /**
   * Configures a server as replica of another, or promotes it to a master.
   * @since Redis 5.0.0
   */
  Command REPLICAOF = new CommandImpl("replicaof", 3, null, false, false);
  /**
   * Resets the connection.
   * @since Redis 6.2.0
   */
  Command RESET = new CommandImpl("reset", 1, null, false, false);
  /**
   * Creates a key from the serialized representation of a value.
   * @since Redis 2.6.0
   */
  Command RESTORE = new CommandImpl("restore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for migrating keys in a cluster.
   * @since Redis 3.0.0
   */
  Command RESTORE_ASKING = new CommandImpl("restore-asking", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the replication role.
   * @since Redis 2.8.12
   */
  Command ROLE = new CommandImpl("role", 1, null, false, false);
  /**
   * Returns and removes the last elements of a list. Deletes the list if the last element was popped.
   * @since Redis 1.0.0
   */
  Command RPOP = new CommandImpl("rpop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the last element of a list after removing and pushing it to another list. Deletes the list if the last element was popped.
   * @since Redis 1.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code LMOVE} with the {@code RIGHT} and {@code LEFT} arguments
   */
  Command RPOPLPUSH = new CommandImpl("rpoplpush", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Appends one or more elements to a list. Creates the key if it doesn't exist.
   * @since Redis 1.0.0
   */
  Command RPUSH = new CommandImpl("rpush", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Appends an element to a list only when the list exists.
   * @since Redis 2.2.0
   */
  Command RPUSHX = new CommandImpl("rpushx", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more members to a set. Creates the key if it doesn't exist.
   * @since Redis 1.0.0
   */
  Command SADD = new CommandImpl("sadd", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Synchronously saves the database(s) to disk.
   * @since Redis 1.0.0
   */
  Command SAVE = new CommandImpl("save", 1, null, false, false);
  /**
   * Iterates over the key names in the database.
   * @since Redis 2.8.0
   */
  Command SCAN = new CommandImpl("scan", -2, true, false, false);
  /**
   * Returns the number of members in a set.
   * @since Redis 1.0.0
   */
  Command SCARD = new CommandImpl("scard", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for Lua scripts management commands.
   * @since Redis 2.6.0
   */
  Command SCRIPT = new CommandImpl("script", -2, null, false, false);
  /**
   * Returns the difference of multiple sets.
   * @since Redis 1.0.0
   */
  Command SDIFF = new CommandImpl("sdiff", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Stores the difference of multiple sets in a key.
   * @since Redis 1.0.0
   */
  Command SDIFFSTORE = new CommandImpl("sdiffstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command SEARCH_CLUSTERINFO = new CommandImpl("search.CLUSTERINFO", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command SEARCH_CLUSTERREFRESH = new CommandImpl("search.CLUSTERREFRESH", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command SEARCH_CLUSTERSET = new CommandImpl("search.CLUSTERSET", -1, true, false, false);
  /**
   * Changes the selected database.
   * @since Redis 1.0.0
   */
  Command SELECT = new CommandImpl("select", 2, null, false, false);
  /**
   * Sets the string value of a key, ignoring its type. The key is created if it doesn't exist.
   * @since Redis 1.0.0
   */
  Command SET = new CommandImpl("set", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets or clears the bit at offset of the string value. Creates the key if it doesn't exist.
   * @since Redis 2.2.0
   */
  Command SETBIT = new CommandImpl("setbit", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the string value and expiration time of a key. Creates the key if it doesn't exist.
   * @since Redis 2.0.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code EX} argument
   */
  Command SETEX = new CommandImpl("setex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Set the string value of a key only when the key doesn't exist.
   * @since Redis 1.0.0
   * @deprecated since Redis 2.6.12, replaced by: {@code SET} with the {@code NX} argument
   */
  Command SETNX = new CommandImpl("setnx", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Overwrites a part of a string value with another by an offset. Creates the key if it doesn't exist.
   * @since Redis 2.2.0
   */
  Command SETRANGE = new CommandImpl("setrange", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Synchronously saves the database(s) to disk and shuts down the Redis server.
   * @since Redis 1.0.0
   */
  Command SHUTDOWN = new CommandImpl("shutdown", -1, null, false, false);
  /**
   * Returns the intersect of multiple sets.
   * @since Redis 1.0.0
   */
  Command SINTER = new CommandImpl("sinter", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Returns the number of members of the intersect of multiple sets.
   * @since Redis 7.0.0
   */
  Command SINTERCARD = new CommandImpl("sintercard", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Stores the intersect of multiple sets in a key.
   * @since Redis 1.0.0
   */
  Command SINTERSTORE = new CommandImpl("sinterstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * Determines whether a member belongs to a set.
   * @since Redis 1.0.0
   */
  Command SISMEMBER = new CommandImpl("sismember", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets a Redis server as a replica of another, or promotes it to being a master.
   * @since Redis 1.0.0
   * @deprecated since Redis 5.0.0, replaced by: {@code REPLICAOF}
   */
  Command SLAVEOF = new CommandImpl("slaveof", 3, null, false, false);
  /**
   * A container for slow log commands.
   * @since Redis 2.2.12
   */
  Command SLOWLOG = new CommandImpl("slowlog", -2, null, false, false);
  /**
   * Returns all members of a set.
   * @since Redis 1.0.0
   */
  Command SMEMBERS = new CommandImpl("smembers", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Determines whether multiple members belong to a set.
   * @since Redis 6.2.0
   */
  Command SMISMEMBER = new CommandImpl("smismember", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Moves a member from one set to another.
   * @since Redis 1.0.0
   */
  Command SMOVE = new CommandImpl("smove", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Sorts the elements in a list, a set, or a sorted set, optionally storing the result.
   * @since Redis 1.0.0
   */
  Command SORT = new CommandImpl("sort", -2, false, false, true, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the sorted elements of a list, a set, or a sorted set.
   * @since Redis 7.0.0
   */
  Command SORT_RO = new CommandImpl("sort_ro", -2, true, false, true, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns one or more random members from a set after removing them. Deletes the set if the last member was popped.
   * @since Redis 1.0.0
   */
  Command SPOP = new CommandImpl("spop", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Post a message to a shard channel.
   * @since Redis 7.0.0
   */
  Command SPUBLISH = new CommandImpl("spublish", 3, null, false, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Get one or multiple random members from a set.
   * @since Redis 1.0.0
   */
  Command SRANDMEMBER = new CommandImpl("srandmember", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes one or more members from a set. Deletes the set if the last member was removed.
   * @since Redis 1.0.0
   */
  Command SREM = new CommandImpl("srem", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Iterates over members of a set.
   * @since Redis 2.8.0
   */
  Command SSCAN = new CommandImpl("sscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listens for messages published to shard channels.
   * @since Redis 7.0.0
   */
  Command SSUBSCRIBE = new CommandImpl("ssubscribe", -2, null, true, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Returns the length of a string value.
   * @since Redis 2.2.0
   */
  Command STRLEN = new CommandImpl("strlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Listens for messages published to channels.
   * @since Redis 2.0.0
   */
  Command SUBSCRIBE = new CommandImpl("subscribe", -2, null, true, false);
  /**
   * Returns a substring from a string value.
   * @since Redis 1.0.0
   * @deprecated since Redis 2.0.0, replaced by: {@code GETRANGE}
   */
  Command SUBSTR = new CommandImpl("substr", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the union of multiple sets.
   * @since Redis 1.0.0
   */
  Command SUNION = new CommandImpl("sunion", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Stores the union of multiple sets in a key.
   * @since Redis 1.0.0
   */
  Command SUNIONSTORE = new CommandImpl("sunionstore", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(-1, 1, 0)));
  /**
   * Stops listening to messages posted to shard channels.
   * @since Redis 7.0.0
   */
  Command SUNSUBSCRIBE = new CommandImpl("sunsubscribe", -1, null, true, false, new KeyLocator(null, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Swaps two Redis databases.
   * @since Redis 4.0.0
   */
  Command SWAPDB = new CommandImpl("swapdb", 3, false, false, false);
  /**
   * An internal command used in replication.
   * @since Redis 1.0.0
   */
  Command SYNC = new CommandImpl("sync", 1, null, false, false);
  /**
   * Adds one or more observations to a t-digest sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_ADD = new CommandImpl("tdigest.add", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input rank, an estimation of the value (floating-point) with that rank.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_BYRANK = new CommandImpl("tdigest.byrank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input reverse rank, an estimation of the value (floating-point) with that reverse rank.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_BYREVRANK = new CommandImpl("tdigest.byrevrank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input value, an estimation of the floating-point fraction of (observations smaller than the given value + half the observations equal to the given value). Multiple fractions can be retrieved in a single call.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_CDF = new CommandImpl("tdigest.cdf", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Allocates memory and initializes a new t-digest sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_CREATE = new CommandImpl("tdigest.create", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information and statistics about a t-digest sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_INFO = new CommandImpl("tdigest.info", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the maximum observation value from a t-digest sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_MAX = new CommandImpl("tdigest.max", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Merges multiple t-digest sketches into a single sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_MERGE = new CommandImpl("tdigest.merge", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Returns the minimum observation value from a t-digest sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_MIN = new CommandImpl("tdigest.min", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input fraction, an estimation of the value (floating point) that is smaller than the given fraction of observations.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_QUANTILE = new CommandImpl("tdigest.quantile", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input value (floating-point), the estimated rank of the value (the number of observations in the sketch that are smaller than the value + half the number of observations that are equal to the value).
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_RANK = new CommandImpl("tdigest.rank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Resets a t-digest sketch: empty the sketch and re-initializes it.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_RESET = new CommandImpl("tdigest.reset", 2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns, for each input value (floating-point), the estimated reverse rank of the value (the number of observations in the sketch that are larger than the value + half the number of observations that are equal to the value).
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_REVRANK = new CommandImpl("tdigest.revrank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns an estimation of the mean value from the sketch, excluding observation values outside the low and high cutoff quantiles.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.4.0
   */
  Command TDIGEST_TRIMMED_MEAN = new CommandImpl("tdigest.trimmed_mean", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the server time.
   * @since Redis 2.6.0
   */
  Command TIME = new CommandImpl("time", 1, null, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command TIMESERIES_CLUSTERSET = new CommandImpl("timeseries.CLUSTERSET", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command TIMESERIES_CLUSTERSETFROMSHARD = new CommandImpl("timeseries.CLUSTERSETFROMSHARD", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command TIMESERIES_HELLO = new CommandImpl("timeseries.HELLO", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command TIMESERIES_INFOCLUSTER = new CommandImpl("timeseries.INFOCLUSTER", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command TIMESERIES_INNERCOMMUNICATION = new CommandImpl("timeseries.INNERCOMMUNICATION", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   * @deprecated this command no longer exists in the latest Redis release
   */
  Command TIMESERIES_NETWORKTEST = new CommandImpl("timeseries.NETWORKTEST", -1, true, false, false);
  /**
   * <p>
   * This command only exists in Redis.
   */
  Command TIMESERIES_REFRESHCLUSTER = new CommandImpl("timeseries.REFRESHCLUSTER", -1, true, false, false);
  /**
   * Adds an item to a Top-k sketch. Multiple items can be added at the same time.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_ADD = new CommandImpl("topk.add", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the count for one or more items are in a sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_COUNT = new CommandImpl("topk.count", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increases the count of one or more items by increment.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_INCRBY = new CommandImpl("topk.incrby", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information about a sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_INFO = new CommandImpl("topk.info", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the full list of items in Top-K sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_LIST = new CommandImpl("topk.list", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Checks whether one or more items are in a sketch.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_QUERY = new CommandImpl("topk.query", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Initializes a Top-K sketch with specified parameters.
   * <p>
   * This command only exists in Redis.
   * @since Redis Bloom 2.0.0
   */
  Command TOPK_RESERVE = new CommandImpl("topk.reserve", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the number of existing keys out of those specified after updating the time they were last accessed.
   * @since Redis 3.2.1
   */
  Command TOUCH = new CommandImpl("touch", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Trim the keys that belong to specified slots.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.4.0
   */
  Command TRIMSLOTS = new CommandImpl("trimslots", -5, false, false, false);
  /**
   * Append a sample to a time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_ADD = new CommandImpl("ts.add", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Update the retention, chunk size, duplicate policy, and labels of an existing time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_ALTER = new CommandImpl("ts.alter", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Create a new time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_CREATE = new CommandImpl("ts.create", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Create a compaction rule.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_CREATERULE = new CommandImpl("ts.createrule", -5, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Decrease the value of the latest sample.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_DECRBY = new CommandImpl("ts.decrby", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete all samples between two timestamps for a given time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.6.0
   */
  Command TS_DEL = new CommandImpl("ts.del", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Delete a compaction rule.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_DELETERULE = new CommandImpl("ts.deleterule", 3, false, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(false, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Get the sample with the highest timestamp from a given time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_GET = new CommandImpl("ts.get", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Increase the value of the latest sample.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_INCRBY = new CommandImpl("ts.incrby", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns information and statistics for a time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_INFO = new CommandImpl("ts.info", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Append new samples to one or more time series.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_MADD = new CommandImpl("ts.madd", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 3, 0)));
  /**
   * Get the sample with the highest timestamp from each time series matching a specific filter.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_MGET = new CommandImpl("ts.mget", -3, true, false, false);
  /**
   * Query a range across multiple time series by filters in forward direction.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_MRANGE = new CommandImpl("ts.mrange", -4, true, false, false);
  /**
   * Query a range across multiple time series by filters in reverse direction.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.4.0
   */
  Command TS_MREVRANGE = new CommandImpl("ts.mrevrange", -4, true, false, false);
  /**
   * Get all time series keys matching a filter list.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_QUERYINDEX = new CommandImpl("ts.queryindex", -2, true, false, false);
  /**
   * Query a range in forward direction.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.0.0
   */
  Command TS_RANGE = new CommandImpl("ts.range", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Query a range in reverse direction.
   * <p>
   * This command only exists in Redis.
   * @since Redis TimeSeries 1.4.0
   */
  Command TS_REVRANGE = new CommandImpl("ts.revrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the expiration time in seconds of a key.
   * @since Redis 1.0.0
   */
  Command TTL = new CommandImpl("ttl", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Determines the type of value stored at a key.
   * @since Redis 1.0.0
   */
  Command TYPE = new CommandImpl("type", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Asynchronously deletes one or more keys.
   * @since Redis 4.0.0
   */
  Command UNLINK = new CommandImpl("unlink", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Stops listening to messages posted to channels.
   * @since Redis 2.0.0
   */
  Command UNSUBSCRIBE = new CommandImpl("unsubscribe", -1, null, true, false);
  /**
   * Forgets about watched keys of a transaction.
   * @since Redis 2.2.0
   */
  Command UNWATCH = new CommandImpl("unwatch", 1, null, false, false);
  /**
   * Add one or more elements to a vector set, or update its vector if it already exists.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VADD = new CommandImpl("VADD", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the number of elements in a vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VCARD = new CommandImpl("VCARD", 2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the dimension of vectors in the vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VDIM = new CommandImpl("VDIM", 2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the vector associated with an element.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VEMB = new CommandImpl("VEMB", -3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Retrieve the JSON attributes of elements.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VGETATTR = new CommandImpl("VGETATTR", 3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return information about a vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VINFO = new CommandImpl("VINFO", 2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Check if an element exists in a vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.2.0
   */
  Command VISMEMBER = new CommandImpl("VISMEMBER", 3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return the neighbors of an element at each layer in the HNSW graph.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VLINKS = new CommandImpl("VLINKS", -3, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return one or multiple random members from a vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VRANDMEMBER = new CommandImpl("VRANDMEMBER", -2, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return vector set elements in a lex range.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.4.0
   */
  Command VRANGE = new CommandImpl("VRANGE", -4, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Remove an element from a vector set.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VREM = new CommandImpl("VREM", 3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Associate or remove the JSON attributes of elements.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VSETATTR = new CommandImpl("VSETATTR", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Return elements by vector similarity.
   * <p>
   * This command only exists in Redis.
   * @since Redis VectorSet 8.0.0
   */
  Command VSIM = new CommandImpl("VSIM", -4, true, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Blocks until the asynchronous replication of all preceding write commands sent by the connection is completed.
   * @since Redis 3.0.0
   */
  Command WAIT = new CommandImpl("wait", 3, null, false, false);
  /**
   * Blocks until all of the preceding write commands sent by the connection are written to the append-only file of the master and/or replicas.
   * @since Redis 7.2.0
   */
  Command WAITAOF = new CommandImpl("waitaof", 4, null, false, false);
  /**
   * Monitors changes to keys to determine the execution of a transaction.
   * @since Redis 2.2.0
   */
  Command WATCH = new CommandImpl("watch", -2, null, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(-1, 1, 0)));
  /**
   * Returns the number of messages that were successfully acknowledged by the consumer group member of a stream.
   * @since Redis 5.0.0
   */
  Command XACK = new CommandImpl("xack", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Acknowledges and deletes one or multiple messages for a stream consumer group.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.2.0
   */
  Command XACKDEL = new CommandImpl("xackdel", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Appends a new message to a stream. Creates the key if it doesn't exist.
   * @since Redis 5.0.0
   */
  Command XADD = new CommandImpl("xadd", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Changes, or acquires, ownership of messages in a consumer group, as if the messages were delivered to as consumer group member.
   * @since Redis 6.2.0
   */
  Command XAUTOCLAIM = new CommandImpl("xautoclaim", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Sets the IDMP configuration parameters for a stream.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.6.0
   */
  Command XCFGSET = new CommandImpl("xcfgset", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Changes, or acquires, ownership of a message in a consumer group, as if the message was delivered a consumer group member.
   * @since Redis 5.0.0
   */
  Command XCLAIM = new CommandImpl("xclaim", -6, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the number of messages after removing them from a stream.
   * @since Redis 5.0.0
   */
  Command XDEL = new CommandImpl("xdel", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes one or multiple entries from the stream.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.2.0
   */
  Command XDELEX = new CommandImpl("xdelex", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for consumer groups commands.
   * @since Redis 5.0.0
   */
  Command XGROUP = new CommandImpl("xgroup", -2, null, false, false);
  /**
   * An internal command for setting IDMP metadata on an existing stream message.
   * <p>
   * This command only exists in Redis.
   * @since Redis 8.6.2
   */
  Command XIDMPRECORD = new CommandImpl("xidmprecord", 5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * A container for stream introspection commands.
   * @since Redis 5.0.0
   */
  Command XINFO = new CommandImpl("xinfo", -2, null, false, false);
  /**
   * Return the number of messages in a stream.
   * @since Redis 5.0.0
   */
  Command XLEN = new CommandImpl("xlen", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the information and entries from a stream consumer group's pending entries list.
   * @since Redis 5.0.0
   */
  Command XPENDING = new CommandImpl("xpending", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the messages from a stream within a range of IDs.
   * @since Redis 5.0.0
   */
  Command XRANGE = new CommandImpl("xrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns messages from multiple streams with IDs greater than the ones requested. Blocks until a message is available otherwise.
   * @since Redis 5.0.0
   */
  Command XREAD = new CommandImpl("xread", -4, true, false, false, new KeyLocator(true, new BeginSearchKeyword("STREAMS", 1), new FindKeysRange(-1, 1, 2)));
  /**
   * Returns new or historical messages from a stream for a consumer in a group. Blocks until a message is available otherwise.
   * @since Redis 5.0.0
   */
  Command XREADGROUP = new CommandImpl("xreadgroup", -7, false, false, false, new KeyLocator(true, new BeginSearchKeyword("STREAMS", 4), new FindKeysRange(-1, 1, 2)));
  /**
   * Returns the messages from a stream within a range of IDs in reverse order.
   * @since Redis 5.0.0
   */
  Command XREVRANGE = new CommandImpl("xrevrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * An internal command for replicating stream values.
   * @since Redis 5.0.0
   */
  Command XSETID = new CommandImpl("xsetid", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Deletes messages from the beginning of a stream.
   * @since Redis 5.0.0
   */
  Command XTRIM = new CommandImpl("xtrim", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Adds one or more members to a sorted set, or updates their scores. Creates the key if it doesn't exist.
   * @since Redis 1.2.0
   */
  Command ZADD = new CommandImpl("zadd", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the number of members in a sorted set.
   * @since Redis 1.2.0
   */
  Command ZCARD = new CommandImpl("zcard", 2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the count of members in a sorted set that have scores within a range.
   * @since Redis 2.0.0
   */
  Command ZCOUNT = new CommandImpl("zcount", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the difference between multiple sorted sets.
   * @since Redis 6.2.0
   */
  Command ZDIFF = new CommandImpl("zdiff", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Stores the difference of multiple sorted sets in a key.
   * @since Redis 6.2.0
   */
  Command ZDIFFSTORE = new CommandImpl("zdiffstore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Increments the score of a member in a sorted set.
   * @since Redis 1.2.0
   */
  Command ZINCRBY = new CommandImpl("zincrby", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the intersect of multiple sorted sets.
   * @since Redis 6.2.0
   */
  Command ZINTER = new CommandImpl("zinter", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Returns the number of members of the intersect of multiple sorted sets.
   * @since Redis 7.0.0
   */
  Command ZINTERCARD = new CommandImpl("zintercard", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Stores the intersect of multiple sorted sets in a key.
   * @since Redis 2.0.0
   */
  Command ZINTERSTORE = new CommandImpl("zinterstore", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysKeynum(0, 1, 1)));
  /**
   * Returns the number of members in a sorted set within a lexicographical range.
   * @since Redis 2.8.9
   */
  Command ZLEXCOUNT = new CommandImpl("zlexcount", 4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the highest- or lowest-scoring members from one or more sorted sets after removing them. Deletes the sorted set if the last member was popped.
   * @since Redis 7.0.0
   */
  Command ZMPOP = new CommandImpl("zmpop", -4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Returns the score of one or more members in a sorted set.
   * @since Redis 6.2.0
   */
  Command ZMSCORE = new CommandImpl("zmscore", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the highest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @since Redis 5.0.0
   */
  Command ZPOPMAX = new CommandImpl("zpopmax", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the lowest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @since Redis 5.0.0
   */
  Command ZPOPMIN = new CommandImpl("zpopmin", -2, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns one or more random members from a sorted set.
   * @since Redis 6.2.0
   */
  Command ZRANDMEMBER = new CommandImpl("zrandmember", -2, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a range of indexes.
   * @since Redis 1.2.0
   */
  Command ZRANGE = new CommandImpl("zrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a lexicographical range.
   * @since Redis 2.8.9
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code BYLEX} argument
   */
  Command ZRANGEBYLEX = new CommandImpl("zrangebylex", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a range of scores.
   * @since Redis 1.0.5
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code BYSCORE} argument
   */
  Command ZRANGEBYSCORE = new CommandImpl("zrangebyscore", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Stores a range of members from sorted set in a key.
   * @since Redis 6.2.0
   */
  Command ZRANGESTORE = new CommandImpl("zrangestore", -5, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)), new KeyLocator(true, new BeginSearchIndex(2), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the index of a member in a sorted set ordered by ascending scores.
   * @since Redis 2.0.0
   */
  Command ZRANK = new CommandImpl("zrank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes one or more members from a sorted set. Deletes the sorted set if all members were removed.
   * @since Redis 1.2.0
   */
  Command ZREM = new CommandImpl("zrem", -3, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes members in a sorted set within a lexicographical range. Deletes the sorted set if all members were removed.
   * @since Redis 2.8.9
   */
  Command ZREMRANGEBYLEX = new CommandImpl("zremrangebylex", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes members in a sorted set within a range of indexes. Deletes the sorted set if all members were removed.
   * @since Redis 2.0.0
   */
  Command ZREMRANGEBYRANK = new CommandImpl("zremrangebyrank", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Removes members in a sorted set within a range of scores. Deletes the sorted set if all members were removed.
   * @since Redis 1.2.0
   */
  Command ZREMRANGEBYSCORE = new CommandImpl("zremrangebyscore", 4, false, false, false, new KeyLocator(false, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a range of indexes in reverse order.
   * @since Redis 1.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} argument
   */
  Command ZREVRANGE = new CommandImpl("zrevrange", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a lexicographical range in reverse order.
   * @since Redis 2.8.9
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYLEX} arguments
   */
  Command ZREVRANGEBYLEX = new CommandImpl("zrevrangebylex", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns members in a sorted set within a range of scores in reverse order.
   * @since Redis 2.2.0
   * @deprecated since Redis 6.2.0, replaced by: {@code ZRANGE} with the {@code REV} and {@code BYSCORE} arguments
   */
  Command ZREVRANGEBYSCORE = new CommandImpl("zrevrangebyscore", -4, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the index of a member in a sorted set ordered by descending scores.
   * @since Redis 2.0.0
   */
  Command ZREVRANK = new CommandImpl("zrevrank", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Iterates over members and scores of a sorted set.
   * @since Redis 2.8.0
   */
  Command ZSCAN = new CommandImpl("zscan", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the score of a member in a sorted set.
   * @since Redis 1.2.0
   */
  Command ZSCORE = new CommandImpl("zscore", 3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysRange(0, 1, 0)));
  /**
   * Returns the union of multiple sorted sets.
   * @since Redis 6.2.0
   */
  Command ZUNION = new CommandImpl("zunion", -3, true, false, false, new KeyLocator(true, new BeginSearchIndex(1), new FindKeysKeynum(0, 1, 1)));
  /**
   * Stores the union of multiple sorted sets in a key.
   * @since Redis 2.0.0
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
}
