/**
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.op.*;

import java.util.List;
import java.util.Map;

/**
 * This Interface represents a TX
 */
@VertxGen
public interface RedisTransaction {
  /**
   * Close the client - when it is fully closed the handler will be called.
   *
   * @param handler
   */
  void close(Handler<AsyncResult<Void>> handler);

  /**
   * Append a value to a key
   *
   * @param key     Key string
   * @param value   Value to append
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: string
   */
  @Fluent
  RedisTransaction append(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Authenticate to the server
   *
   * @param password Password for authentication
   * @param handler  Handler for the result of this call.
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  RedisTransaction auth(String password, Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously rewrite the append-only file
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction bgrewriteaof(Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously save the dataset to disk
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction bgsave(Handler<AsyncResult<String>> handler);

  /**
   * Count set bits in a string
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  RedisTransaction bitcount(String key, Handler<AsyncResult<String>> handler);

  /**
   * Count set bits in a string
   *
   * @param key     Key string
   * @param start   Start index
   * @param end     End index
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  RedisTransaction bitcountRange(String key, long start, long end, Handler<AsyncResult<String>> handler);

  /**
   * Perform bitwise operations between strings
   *
   * @param operation Bitwise operation to perform
   * @param destkey   Destination key where result is stored
   * @param keys      List of keys on which to perform the operation
   * @param handler   Handler for the result of this call.
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  RedisTransaction bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Find first bit set or clear in a string
   *
   * @param key     Key string
   * @param bit     What bit value to look for - must be 1, or 0
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.7
   * group: string
   */
  @Fluent
  RedisTransaction bitpos(String key, int bit, Handler<AsyncResult<String>> handler);

  /**
   * Find first bit set or clear in a string
   * <p>
   * See also bitposRange() method, which takes start, and stop offset.
   *
   * @param key     Key string
   * @param bit     What bit value to look for - must be 1, or 0
   * @param start   Start offset
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.7
   * group: string
   */
  @Fluent
  RedisTransaction bitposFrom(String key, int bit, int start, Handler<AsyncResult<String>> handler);

  /**
   * Find first bit set or clear in a string
   * <p>
   * Note: when both start, and stop offsets are specified,
   * behaviour is slightly different than if only start is specified
   *
   * @param key     Key string
   * @param bit     What bit value to look for - must be 1, or 0
   * @param start   Start offset
   * @param stop    End offset - inclusive
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.7
   * group: string
   */
  @Fluent
  RedisTransaction bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the first element in a list, or block until one is available
   *
   * @param key     Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  RedisTransaction blpop(String key, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the first element in any of the lists, or block until one is available
   *
   * @param keys    List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  RedisTransaction blpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the last element in a list, or block until one is available
   *
   * @param key     Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  RedisTransaction brpop(String key, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the last element in any of the lists, or block until one is available
   *
   * @param keys    List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  RedisTransaction brpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   *
   * @param key     Key string identifying the source list
   * @param destkey Key string identifying the destination list
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  RedisTransaction brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Kill the connection of a client
   *
   * @param filter  Filter options
   * @param handler Handler for the result of this call.
   * @since Redis 2.4.0
   * group: server
   */
  @Fluent
  RedisTransaction clientKill(KillFilter filter, Handler<AsyncResult<String>> handler);

  /**
   * Get the list of client connections
   *
   * @since Redis 2.4.0
   * group: server
   */
  @Fluent
  RedisTransaction clientList(Handler<AsyncResult<String>> handler);

  /**
   * Get the current connection name
   *
   * @since Redis 2.6.9
   * group: server
   */
  @Fluent
  RedisTransaction clientGetname(Handler<AsyncResult<String>> handler);

  /**
   * Stop processing commands from clients for some time
   *
   * @param millis  Pause time in milliseconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.9.50
   * group: server
   */
  @Fluent
  RedisTransaction clientPause(long millis, Handler<AsyncResult<String>> handler);

  /**
   * Set the current connection name
   *
   * @param name    New name for current connection
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.9
   * group: server
   */
  @Fluent
  RedisTransaction clientSetname(String name, Handler<AsyncResult<String>> handler);

  /**
   * Assign new hash slots to receiving node.
   *
   * @param slots
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: server
   */
  @Fluent
  RedisTransaction clusterAddslots(List<String> slots, Handler<AsyncResult<String>> handler);

  /**
   * Return the number of failure reports active for a given node.
   *
   * @param nodeId
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterCountFailureReports(String nodeId, Handler<AsyncResult<String>> handler);

  /**
   * Return the number of local keys in the specified hash slot.
   *
   * @param slot
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterCountkeysinslot(long slot, Handler<AsyncResult<String>> handler);

  /**
   * Set hash slots as unbound in receiving node.
   *
   * @param slot
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterDelslots(long slot, Handler<AsyncResult<String>> handler);

  /**
   * Set hash slots as unbound in receiving node.
   *
   * @param slots
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterDelslotsMany(List<String> slots, Handler<AsyncResult<String>> handler);

  /**
   * Forces a slave to perform a manual failover of its master.
   *
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterFailover(Handler<AsyncResult<String>> handler);

  /**
   * Forces a slave to perform a manual failover of its master.
   *
   * @param options
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Remove a node from the nodes table.
   *
   * @param nodeId
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterForget(String nodeId, Handler<AsyncResult<String>> handler);

  /**
   * Return local key names in the specified hash slot.
   *
   * @param slot
   * @param count
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<String>> handler);

  /**
   * Provides info about Redis Cluster node state.
   *
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterInfo(Handler<AsyncResult<String>> handler);

  /**
   * Returns the hash slot of the specified key.
   *
   * @param key
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterKeyslot(String key, Handler<AsyncResult<String>> handler);

  /**
   * Force a node cluster to handshake with another node.
   *
   * @param ip
   * @param port
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterMeet(String ip, long port, Handler<AsyncResult<String>> handler);

  /**
   * Get Cluster config for the node.
   *
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterNodes(Handler<AsyncResult<String>> handler);

  /**
   * Reconfigure a node as a slave of the specified master node.
   *
   * @param nodeId
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterReplicate(String nodeId, Handler<AsyncResult<String>> handler);

  /**
   * Reset a Redis Cluster node.
   *
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterReset(Handler<AsyncResult<String>> handler);

  /**
   * Reset a Redis Cluster node.
   *
   * @param options
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Forces the node to save cluster state on disk.
   *
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterSaveconfig(Handler<AsyncResult<String>> handler);

  /**
   * Set the configuration epoch in a new node.
   *
   * @param epoch
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterSetConfigEpoch(long epoch, Handler<AsyncResult<String>> handler);

  /**
   * Bind an hash slot to a specific node.
   *
   * @param slot
   * @param subcommand
   * @param handler    Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<String>> handler);

  /**
   * Bind an hash slot to a specific node.
   *
   * @param slot
   * @param subcommand
   * @param nodeId
   * @param handler    Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<String>> handler);

  /**
   * List slave nodes of the specified master node.
   *
   * @param nodeId
   * @param handler Handler for the result of this call.
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  RedisTransaction clusterSlaves(String nodeId, Handler<AsyncResult<String>> handler);

  /**
   * Get array of Cluster slot to node mappings
   *
   * @since Redis 3.0.0
   * group: server
   */
  @Fluent
  RedisTransaction clusterSlots(Handler<AsyncResult<String>> handler);

  /**
   * Get array of Redis command details
   *
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  RedisTransaction command(Handler<AsyncResult<String>> handler);

  /**
   * Get total number of Redis commands
   *
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  RedisTransaction commandCount(Handler<AsyncResult<String>> handler);

  /**
   * Extract keys given a full Redis command
   *
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  RedisTransaction commandGetkeys(Handler<AsyncResult<String>> handler);

  /**
   * Get array of specific Redis command details
   *
   * @param commands List of commands to get info for
   * @param handler  Handler for the result of this call.
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  RedisTransaction commandInfo(List<String> commands, Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a configuration parameter
   *
   * @param parameter Configuration parameter
   * @param handler   Handler for the result of this call.
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  RedisTransaction configGet(String parameter, Handler<AsyncResult<String>> handler);

  /**
   * Rewrite the configuration file with the in memory configuration
   *
   * @since Redis 2.8.0
   * group: server
   */
  @Fluent
  RedisTransaction configRewrite(Handler<AsyncResult<String>> handler);

  /**
   * Set a configuration parameter to the given value
   *
   * @param parameter Configuration parameter
   * @param value     New value
   * @param handler   Handler for the result of this call.
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  RedisTransaction configSet(String parameter, String value, Handler<AsyncResult<String>> handler);

  /**
   * Reset the stats returned by INFO
   *
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  RedisTransaction configResetstat(Handler<AsyncResult<String>> handler);

  /**
   * Return the number of keys in the selected database
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction dbsize(Handler<AsyncResult<String>> handler);

  /**
   * Get debugging information about a key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction debugObject(String key, Handler<AsyncResult<String>> handler);

  /**
   * Make the server crash
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction debugSegfault(Handler<AsyncResult<String>> handler);

  /**
   * Decrement the integer value of a key by one
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction decr(String key, Handler<AsyncResult<String>> handler);

  /**
   * Decrement the integer value of a key by the given number
   *
   * @param key       Key string
   * @param decrement Value by which to decrement
   * @param handler   Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction decrby(String key, long decrement, Handler<AsyncResult<String>> handler);

  /**
   * Delete a key
   *
   * @param key     Keys to delete
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction del(String key, Handler<AsyncResult<String>> handler);

  /**
   * Delete many keys
   *
   * @param keys    List of keys to delete
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction delMany(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Discard all commands issued after MULTI
   *
   * @since Redis 2.0.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction discard(Handler<AsyncResult<String>> handler);

  /**
   * Return a serialized version of the value stored at the specified key.
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction dump(String key, Handler<AsyncResult<String>> handler);

  /**
   * Echo the given string
   *
   * @param message String to echo
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  RedisTransaction echo(String message, Handler<AsyncResult<String>> handler);

  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   * <p>
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   *
   * @param script  Lua script to evaluate
   * @param keys    List of keys
   * @param args    List of argument values
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<String>> handler);

  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   * <p>
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   *
   * @param sha1    SHA1 digest of the script cached on the server
   * @param keys    List of keys
   * @param values  List of values
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction evalsha(String sha1, List<String> keys, List<String> values, Handler<AsyncResult<String>> handler);

  /**
   * Execute all commands issued after MULTI
   *
   * @since Redis 1.2.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction exec(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Determine if a key exists
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction exists(String key, Handler<AsyncResult<String>> handler);

  /**
   * Set a key's time to live in seconds
   *
   * @param key     Key string
   * @param seconds Time to live in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction expire(String key, int seconds, Handler<AsyncResult<String>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp
   *
   * @param key     Key string
   * @param seconds Expiry time as Unix timestamp in seconds
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: generic
   */
  @Fluent
  RedisTransaction expireat(String key, long seconds, Handler<AsyncResult<String>> handler);

  /**
   * Remove all keys from all databases
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction flushall(Handler<AsyncResult<String>> handler);

  /**
   * Remove all keys from the current database
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction flushdb(Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction get(String key, Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a key - without decoding as utf-8
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction getBinary(String key, Handler<AsyncResult<String>> handler);

  /**
   * Returns the bit value at offset in the string value stored at key
   *
   * @param key     Key string
   * @param offset  Offset in bits
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  RedisTransaction getbit(String key, long offset, Handler<AsyncResult<String>> handler);

  /**
   * Get a substring of the string stored at a key
   *
   * @param key     Key string
   * @param start   Start offset
   * @param end     End offset - inclusive
   * @param handler Handler for the result of this call.
   * @since Redis 2.4.0
   * group: string
   */
  @Fluent
  RedisTransaction getrange(String key, long start, long end, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key and return its old value
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction getset(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Delete one or more hash fields
   *
   * @param key     Key string
   * @param field   Field name
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hdel(String key, String field, Handler<AsyncResult<String>> handler);

  /**
   * Delete one or more hash fields
   *
   * @param key     Key string
   * @param fields  Field names
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hdelMany(String key, List<String> fields, Handler<AsyncResult<String>> handler);

  /**
   * Determine if a hash field exists
   *
   * @param key     Key string
   * @param field   Field name
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hexists(String key, String field, Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a hash field
   *
   * @param key     Key string
   * @param field   Field name
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hget(String key, String field, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields and values in a hash
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hgetall(String key, Handler<AsyncResult<String>> handler);

  /**
   * Increment the integer value of a hash field by the given number
   *
   * @param key       Key string
   * @param field     Field name
   * @param increment Value by which to increment
   * @param handler   Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hincrby(String key, String field, long increment, Handler<AsyncResult<String>> handler);

  /**
   * Increment the float value of a hash field by the given amount
   *
   * @param key       Key string
   * @param field     Field name
   * @param increment Value by which to increment
   * @param handler   Handler for the result of this call.
   * @since Redis 2.6.0
   * group: hash
   */
  @Fluent
  RedisTransaction hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields in a hash
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hkeys(String key, Handler<AsyncResult<String>> handler);

  /**
   * Get the number of fields in a hash
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hlen(String key, Handler<AsyncResult<String>> handler);

  /**
   * Get the values of all the given hash fields
   *
   * @param key     Key string
   * @param fields  Field names
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hmget(String key, List<String> fields, Handler<AsyncResult<String>> handler);

  /**
   * Set multiple hash fields to multiple values
   *
   * @param key     Key string
   * @param values  Map of field:value pairs
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a hash field
   *
   * @param key     Key string
   * @param field   Field name
   * @param value   New value
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hset(String key, String field, String value, Handler<AsyncResult<String>> handler);

  /**
   * Set the value of a hash field, only if the field does not exist
   *
   * @param key     Key string
   * @param field   Field name
   * @param value   New value
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hsetnx(String key, String field, String value, Handler<AsyncResult<String>> handler);

  /**
   * Get all the values in a hash
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  RedisTransaction hvals(String key, Handler<AsyncResult<String>> handler);

  /**
   * Increment the integer value of a key by one
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction incr(String key, Handler<AsyncResult<String>> handler);

  /**
   * Increment the integer value of a key by the given amount
   *
   * @param key       Key string
   * @param increment Value by which to increment
   * @param handler   Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction incrby(String key, long increment, Handler<AsyncResult<String>> handler);

  /**
   * Increment the float value of a key by the given amount
   *
   * @param key       Key string
   * @param increment Value by which to increment
   * @param handler   Handler for the result of this call.
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  RedisTransaction incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler);

  /**
   * Get information and statistics about the server
   *
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction info(Handler<AsyncResult<String>> handler);

  /**
   * Get information and statistics about the server
   *
   * @param section Specific section of information to return
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction infoSection(String section, Handler<AsyncResult<String>> handler);

  /**
   * Find all keys matching the given pattern
   *
   * @param pattern Pattern to limit the keys returned
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction keys(String pattern, Handler<AsyncResult<String>> handler);

  /**
   * Get the UNIX time stamp of the last successful save to disk
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction lastsave(Handler<AsyncResult<String>> handler);

  /**
   * Get an element from a list by its index
   *
   * @param key     Key string
   * @param index   Index of list element to get
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lindex(String key, int index, Handler<AsyncResult<String>> handler);

  /**
   * Insert an element before or after another element in a list
   *
   * @param key     Key string
   * @param option  BEFORE or AFTER
   * @param pivot   Key to use as a pivot
   * @param value   Value to be inserted before or after the pivot
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  RedisTransaction linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<String>> handler);

  /**
   * Get the length of a list
   *
   * @param key     String key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction llen(String key, Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the first element in a list
   *
   * @param key     String key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lpop(String key, Handler<AsyncResult<String>> handler);

  /**
   * Prepend one or multiple values to a list
   *
   * @param key     Key string
   * @param values  Values to be added at the beginning of the list, one by one
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler);

  /**
   * Prepend one value to a list
   *
   * @param key     Key string
   * @param value   Value to be added at the beginning of the list
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lpush(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Prepend a value to a list, only if the list exists
   *
   * @param key     Key string
   * @param value   Value to add at the beginning of the list
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  RedisTransaction lpushx(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Get a range of elements from a list
   *
   * @param key     Key string
   * @param from    Start index
   * @param to      Stop index
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lrange(String key, long from, long to, Handler<AsyncResult<String>> handler);

  /**
   * Remove elements from a list
   *
   * @param key     Key string
   * @param count   Number of first found occurrences equal to $value to remove from the list
   * @param value   Value to be removed
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lrem(String key, long count, String value, Handler<AsyncResult<String>> handler);

  /**
   * Set the value of an element in a list by its index
   *
   * @param key     Key string
   * @param index   Position within list
   * @param value   New value
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction lset(String key, long index, String value, Handler<AsyncResult<String>> handler);

  /**
   * Trim a list to the specified range
   *
   * @param key     Key string
   * @param from    Start index
   * @param to      Stop index
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler);

  /**
   * Get the value of the given key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction mget(String key, Handler<AsyncResult<String>> handler);

  /**
   * Get the values of all the given keys
   *
   * @param keys    List of keys to get
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction mgetMany(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Atomically transfer a key from a Redis instance to another one.
   *
   * @param host    Destination host
   * @param port    Destination port
   * @param key     Key to migrate
   * @param destdb  Destination database index
   * @param options Migrate options
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Listen for all requests received by the server in real time
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction monitor(Handler<AsyncResult<String>> handler);

  /**
   * Move a key to another database
   *
   * @param key     Key to migrate
   * @param destdb  Destination database index
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction move(String key, int destdb, Handler<AsyncResult<String>> handler);

  /**
   * Set multiple keys to multiple values
   *
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.1
   * group: string
   */
  @Fluent
  RedisTransaction mset(JsonObject keyvals, Handler<AsyncResult<String>> handler);

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   *
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.1
   * group: string
   */
  @Fluent
  RedisTransaction msetnx(JsonObject keyvals, Handler<AsyncResult<String>> handler);

  /**
   * Mark the start of a RedisTransaction block
   *
   * @since Redis 1.2.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction multi(Handler<AsyncResult<String>> handler);

  /**
   * Inspect the internals of Redis objects
   *
   * @param key     Key string
   * @param cmd     Object sub command
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.3
   * group: generic
   */
  @Fluent
  RedisTransaction object(String key, ObjectCmd cmd, Handler<AsyncResult<String>> handler);

  /**
   * Remove the expiration from a key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: generic
   */
  @Fluent
  RedisTransaction persist(String key, Handler<AsyncResult<String>> handler);

  /**
   * Set a key's time to live in milliseconds
   *
   * @param key     String key
   * @param millis  Time to live in milliseconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction pexpire(String key, long millis, Handler<AsyncResult<String>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   *
   * @param key     Key string
   * @param millis  Expiry time as Unix timestamp in milliseconds
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction pexpireat(String key, long millis, Handler<AsyncResult<String>> handler);

  /**
   * Adds the specified element to the specified HyperLogLog.
   *
   * @param key     Key string
   * @param element Element to add
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisTransaction pfadd(String key, String element, Handler<AsyncResult<String>> handler);

  /**
   * Adds the specified elements to the specified HyperLogLog.
   *
   * @param key      Key string
   * @param elements Elementa to add
   * @param handler  Handler for the result of this call.
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisTransaction pfaddMany(String key, List<String> elements, Handler<AsyncResult<String>> handler);

  /**
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisTransaction pfcount(String key, Handler<AsyncResult<String>> handler);

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   *
   * @param keys    List of keys
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisTransaction pfcountMany(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Merge N different HyperLogLogs into a single one.
   *
   * @param destkey Destination key
   * @param keys    List of source keys
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisTransaction pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Ping the server
   *
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  RedisTransaction ping(Handler<AsyncResult<String>> handler);

  /**
   * Set the value and expiration in milliseconds of a key
   *
   * @param key     Key string
   * @param millis  Number of milliseconds until the key expires
   * @param value   New value for key
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  RedisTransaction psetex(String key, long millis, String value, Handler<AsyncResult<String>> handler);

  /**
   * Listen for messages published to channels matching the given pattern
   *
   * @param pattern Pattern string
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction psubscribe(String pattern, Handler<AsyncResult<String>> handler);

  /**
   * Listen for messages published to channels matching the given patterns
   *
   * @param patterns List of patterns
   * @param handler  Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction psubscribeMany(List<String> patterns, Handler<AsyncResult<String>> handler);

  /**
   * Lists the currently active channels - only those matching the pattern
   *
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction pubsubChannels(String pattern, Handler<AsyncResult<String>> handler);

  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   *
   * @param channels List of channels
   * @param handler  Handler for the result of this call.
   * @since Redis 2.8.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction pubsubNumsub(List<String> channels, Handler<AsyncResult<String>> handler);

  /**
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   *
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction pubsubNumpat(Handler<AsyncResult<String>> handler);

  /**
   * Get the time to live for a key in milliseconds
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction pttl(String key, Handler<AsyncResult<String>> handler);

  /**
   * Post a message to a channel
   *
   * @param channel Channel key
   * @param message Message to send to channel
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction publish(String channel, String message, Handler<AsyncResult<String>> handler);

  /**
   * Stop listening for messages posted to channels matching the given patterns
   *
   * @param patterns List of patterns to match against
   * @param handler  Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction punsubscribe(List<String> patterns, Handler<AsyncResult<String>> handler);

  /**
   * Return a random key from the keyspace
   *
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction randomkey(Handler<AsyncResult<String>> handler);

  /**
   * Rename a key
   *
   * @param key     Key string to be renamed
   * @param newkey  New key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction rename(String key, String newkey, Handler<AsyncResult<String>> handler);

  /**
   * Rename a key, only if the new key does not exist
   *
   * @param key     Key string to be renamed
   * @param newkey  New key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction renamenx(String key, String newkey, Handler<AsyncResult<String>> handler);

  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   *
   * @param key        Key string
   * @param millis     Expiry time in milliseconds to set on the key
   * @param serialized Serialized form of the key value as obtained using DUMP
   * @param handler    Handler for the result of this call.
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  RedisTransaction restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler);

  /**
   * Return the role of the instance in the context of replication
   *
   * @since Redis 2.8.12
   * group: server
   */
  @Fluent
  RedisTransaction role(Handler<AsyncResult<String>> handler);

  /**
   * Remove and get the last element in a list
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction rpop(String key, Handler<AsyncResult<String>> handler);

  /**
   * Remove the last element in a list, append it to another list and return it
   *
   * @param key     Key string identifying source list
   * @param destkey Key string identifying destination list
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: list
   */
  @Fluent
  RedisTransaction rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler);

  /**
   * Append one or multiple values to a list
   *
   * @param key     Key string
   * @param values  List of values to add to the end of the list
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction rpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler);

  /**
   * Append one or multiple values to a list
   *
   * @param key     Key string
   * @param value   Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  RedisTransaction rpush(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Append a value to a list, only if the list exists
   *
   * @param key     Key string
   * @param value   Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  RedisTransaction rpushx(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Add a member to a set
   *
   * @param key     Key string
   * @param member  Value to be added to the set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sadd(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more members to a set
   *
   * @param key     Key string
   * @param members Values to be added to the set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction saddMany(String key, List<String> members, Handler<AsyncResult<String>> handler);

  /**
   * Synchronously save the dataset to disk
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction save(Handler<AsyncResult<String>> handler);

  /**
   * Get the number of members in a set
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction scard(String key, Handler<AsyncResult<String>> handler);

  /**
   * Check existence of script in the script cache.
   *
   * @param script  SHA1 digest identifying a script in the script cache
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction scriptExists(String script, Handler<AsyncResult<String>> handler);

  /**
   * Check existence of scripts in the script cache.
   *
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction scriptExistsMany(List<String> scripts, Handler<AsyncResult<String>> handler);

  /**
   * Remove all the scripts from the script cache.
   *
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction scriptFlush(Handler<AsyncResult<String>> handler);

  /**
   * Kill the script currently in execution.
   *
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction scriptKill(Handler<AsyncResult<String>> handler);

  /**
   * Load the specified Lua script into the script cache.
   *
   * @param script  Lua script
   * @param handler Handler for the result of this call.
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  RedisTransaction scriptLoad(String script, Handler<AsyncResult<String>> handler);

  /**
   * Subtract multiple sets
   *
   * @param key     Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler);

  /**
   * Subtract multiple sets and store the resulting set in a key
   *
   * @param destkey Destination key where the result should be stored
   * @param key     Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler);

  /**
   * Change the selected database for the current connection
   *
   * @param dbindex Index identifying the new active database
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  RedisTransaction select(int dbindex, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction set(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param options Set options
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Set the binary string value of a key - without encoding as utf-8
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction setBinary(String key, Buffer value, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param options Set options
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction setBinaryWithOptions(String key, Buffer value, SetOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Sets or clears the bit at offset in the string value stored at key
   *
   * @param key     Key string
   * @param offset  Bit offset
   * @param bit     New value - must be 1 or 0
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  RedisTransaction setbit(String key, long offset, int bit, Handler<AsyncResult<String>> handler);

  /**
   * Set the value and expiration of a key
   *
   * @param key     Key string
   * @param seconds Number of seconds until the key expires
   * @param value   New value for key
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: string
   */
  @Fluent
  RedisTransaction setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler);

  /**
   * Set the value of a key, only if the key does not exist
   *
   * @param key     Key of which value to set
   * @param value   New value for the key
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  RedisTransaction setnx(String key, String value, Handler<AsyncResult<String>> handler);

  /**
   * Overwrite part of a string at key starting at the specified offset
   *
   * @param key     Key string
   * @param offset  Offset - the maximum offset that you can set is 2^29 -1 (536870911), as Redis Strings are limited to 512 megabytes
   * @param value   Value to overwrite with
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  RedisTransaction setrange(String key, int offset, String value, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sets
   *
   * @param keys    List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sinter(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sets and store the resulting set in a key
   *
   * @param destkey Key where to store the results
   * @param keys    List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sinterstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Determine if a given value is a member of a set
   *
   * @param key     Key string
   * @param member  Member to look for
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sismember(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Make the server a slave of another instance
   *
   * @param host    Host to become this server's master
   * @param port    Port of our new master
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction slaveof(String host, int port, Handler<AsyncResult<String>> handler);

  /**
   * Make this server a master
   *
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction slaveofNoone(Handler<AsyncResult<String>> handler);

  /**
   * Read the Redis slow queries log
   *
   * @param limit   Number of log entries to return. If value is less than zero all entries are returned
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.12
   * group: server
   */
  @Fluent
  RedisTransaction slowlogGet(int limit, Handler<AsyncResult<String>> handler);

  /**
   * Get the length of the Redis slow queries log
   *
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.12
   * group: server
   */
  @Fluent
  RedisTransaction slowlogLen(Handler<AsyncResult<String>> handler);

  /**
   * Reset the Redis slow queries log
   *
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.12
   * group: server
   */
  @Fluent
  RedisTransaction slowlogReset(Handler<AsyncResult<String>> handler);

  /**
   * Get all the members in a set
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction smembers(String key, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Move a member from one set to another
   *
   * @param key     Key of source set currently containing the member
   * @param destkey Key identifying the destination set
   * @param member  Member to move
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction smove(String key, String destkey, String member, Handler<AsyncResult<String>> handler);

  /**
   * Sort the elements in a list, set or sorted set
   *
   * @param key     Key string
   * @param options Sort options
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction sort(String key, SortOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Remove and return a random member from a set
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction spop(String key, Handler<AsyncResult<String>> handler);

  /**
   * Remove and return random members from a set
   *
   * @param key     Key string
   * @param count   Number of members to remove
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction spopMany(String key, int count, Handler<AsyncResult<String>> handler);

  /**
   * Get one or multiple random members from a set
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction srandmember(String key, Handler<AsyncResult<String>> handler);

  /**
   * Get one or multiple random members from a set
   *
   * @param key     Key string
   * @param count   Number of members to get
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction srandmemberCount(String key, int count, Handler<AsyncResult<String>> handler);

  /**
   * Remove one member from a set
   *
   * @param key     Key string
   * @param member  Member to remove
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction srem(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Remove one or more members from a set
   *
   * @param key     Key string
   * @param members Members to remove
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sremMany(String key, List<String> members, Handler<AsyncResult<String>> handler);

  /**
   * Get the length of the value stored in a key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  RedisTransaction strlen(String key, Handler<AsyncResult<String>> handler);

  /**
   * Listen for messages published to the given channels
   *
   * @param channel Channel to subscribe to
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction subscribe(String channel, Handler<AsyncResult<String>> handler);

  /**
   * Listen for messages published to the given channels
   *
   * @param channels List of channels to subscribe to
   * @param handler  Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction subscribeMany(List<String> channels, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sets
   *
   * @param keys    List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sunion(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sets and store the resulting set in a key
   *
   * @param destkey Destination key
   * @param keys    List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  RedisTransaction sunionstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Internal command used for replication
   *
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  RedisTransaction sync(Handler<AsyncResult<String>> handler);

  /**
   * Return the current server time
   *
   * @since Redis 2.6.0
   * group: server
   */
  @Fluent
  RedisTransaction time(Handler<AsyncResult<String>> handler);

  /**
   * Get the time to live for a key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */


  @Fluent
  RedisTransaction ttl(String key, Handler<AsyncResult<String>> handler);

  /**
   * Determine the type stored at key
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.0
   * group: generic
   */

  @Fluent
  RedisTransaction type(String key, Handler<AsyncResult<String>> handler);

  /**
   * Stop listening for messages posted to the given channels
   *
   * @param channels List of channels to subscribe to
   * @param handler  Handler for the result of this call.
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisTransaction unsubscribe(List<String> channels, Handler<AsyncResult<String>> handler);

  /**
   * Forget about all watched keys
   *
   * @since Redis 2.2.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction unwatch(Handler<AsyncResult<String>> handler);

  /**
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection.
   *
   * @param numSlaves
   * @param timeout
   * @param handler   Handler for the result of this call.
   * @since Redis 3.0.0
   * group: generic
   */
  @Fluent
  RedisTransaction wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler);

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   *
   * @param key     Key to watch
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction watch(String key, Handler<AsyncResult<String>> handler);

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   *
   * @param keys    List of keys to watch
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: RedisTransactions
   */
  @Fluent
  RedisTransaction watchMany(List<String> keys, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   *
   * @param key     Key string
   * @param score   Score used for sorting
   * @param member  New member key
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zadd(String key, double score, String member, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   *
   * @param key     Key string
   * @param members New member keys and their scores
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<String>> handler);

  /**
   * Get the number of members in a sorted set
   *
   * @param key     Key string
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zcard(String key, Handler<AsyncResult<String>> handler);

  /**
   * Count the members in a sorted set with scores within the given values
   *
   * @param key     Key string
   * @param min     Minimum score
   * @param max     Maximum score
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zcount(String key, double min, double max, Handler<AsyncResult<String>> handler);

  /**
   * Increment the score of a member in a sorted set
   *
   * @param key       Key string
   * @param increment Increment amount
   * @param member    Member key
   * @param handler   Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   *
   * @param destkey Destination key
   * @param sets    List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
   *
   * @param destkey Destination key
   * @param sets    List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Count the number of members in a sorted set between a given lexicographical range
   *
   * @param key     Key string
   * @param min     Pattern to compare against for minimum value
   * @param max     Pattern to compare against for maximum value
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zlexcount(String key, String min, String max, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by index
   *
   * @param key     Key string
   * @param start   Start index for the range
   * @param stop    Stop index for the range - inclusive
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrange(String key, long start, long stop, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by index
   *
   * @param key     Key string
   * @param start   Start index for the range
   * @param stop    Stop index for the range - inclusive
   * @param options Range options
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by lexicographical range
   *
   * @param key     Key string
   * @param min     Pattern representing a minimum allowed value
   * @param max     Pattern representing a maximum allowed value
   * @param options Limit options where limit can be specified
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by score
   *
   * @param key     Key string
   * @param min     Pattern defining a minimum value
   * @param max     Pattern defining a maximum value
   * @param options Range and limit options
   * @param handler Handler for the result of this call.
   * @since Redis 1.0.5
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Determine the index of a member in a sorted set
   *
   * @param key     Key string
   * @param member  Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrank(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Remove one member from a sorted set
   *
   * @param key     Key string
   * @param member  Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrem(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Remove one or more members from a sorted set
   *
   * @param key     Key string
   * @param members Members in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zremMany(String key, List<String> members, Handler<AsyncResult<String>> handler);

  /**
   * Remove all members in a sorted set between the given lexicographical range
   *
   * @param key     Key string
   * @param min     Pattern defining a minimum value
   * @param max     Pattern defining a maximum value
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zremrangebylex(String key, String min, String max, Handler<AsyncResult<String>> handler);

  /**
   * Remove all members in a sorted set within the given indexes
   *
   * @param key     Key string
   * @param start   Start index
   * @param stop    Stop index
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<String>> handler);

  /**
   * Remove all members in a sorted set within the given scores
   *
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zremrangebyscore(String key, String min, String max, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   *
   * @param key     Key string
   * @param start   Start index for the range
   * @param stop    Stop index for the range - inclusive
   * @param options Range options
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by score, between the given lexicographical range with scores ordered from high to low
   *
   * @param key     Key string
   * @param max     Pattern defining a maximum value
   * @param min     Pattern defining a minimum value
   * @param options Limit options
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   *
   * @param key     Key string
   * @param max     Pattern defining a maximum value
   * @param min     Pattern defining a minimum value
   * @param options Range and limit options
   * @param handler Handler for the result of this call.
   * @since Redis 2.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   *
   * @param key     Key string
   * @param member  Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zrevrank(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Get the score associated with the given member in a sorted set
   *
   * @param key     Key string
   * @param member  Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zscore(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   *
   * @param destkey Destination key
   * @param sets    List of keys identifying sorted sets
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sorted sets using weights, and store the resulting sorted set in a new key
   *
   * @param key     Destination key
   * @param sets    Map containing set-key:weight pairs
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zunionstoreWeighed(String key, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Incrementally iterate the keys space
   *
   * @param cursor  Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: generic
   */
  @Fluent
  RedisTransaction scan(String cursor, ScanOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Incrementally iterate Set elements
   *
   * @param key     Key string
   * @param cursor  Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: set
   */
  @Fluent
  RedisTransaction sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Incrementally iterate hash fields and associated values
   *
   * @param key     Key string
   * @param cursor  Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: hash
   */
  @Fluent
  RedisTransaction hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Incrementally iterate sorted sets elements and associated scores
   *
   * @param key     Key string
   * @param cursor  Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @since Redis 2.8.0
   * group: sorted_set
   */
  @Fluent
  RedisTransaction zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   *
   * @param key     Key string
   * @param longitude  longitude
   * @param latitude latitude
   * @param member member
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geoadd(String key, double longitude, double latitude, String member, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   *
   * @param key     Key string
   * @param members  list of &lt;lon, lat, member&gt;
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geoaddMany(String key, List<GeoMember> members, Handler<AsyncResult<String>> handler);

  /**
   * Return valid Geohash strings representing the position of one or more elements in a sorted set value representing
   * a geospatial index (where elements were added using GEOADD).
   *
   * @param key     Key string
   * @param member member
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geohash(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Return valid Geohash strings representing the position of one or more elements in a sorted set value representing
   * a geospatial index (where elements were added using GEOADD).
   *
   * @param key     Key string
   * @param members  list of members
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geohashMany(String key, List<String> members, Handler<AsyncResult<String>> handler);

  /**
   * Return the positions (longitude,latitude) of all the specified members of the geospatial index represented by the
   * sorted set at key.
   *
   * @param key     Key string
   * @param member member
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geopos(String key, String member, Handler<AsyncResult<String>> handler);

  /**
   * Return the positions (longitude,latitude) of all the specified members of the geospatial index represented by the
   * sorted set at key.
   *
   * @param key     Key string
   * @param members  list of members
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geoposMany(String key, List<String> members, Handler<AsyncResult<String>> handler);

  /**
   * Return the distance between two members in the geospatial index represented by the sorted set.
   *
   * @param key     Key string
   * @param member1 member 1
   * @param member2 member 2
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geodist(String key, String member1, String member2, Handler<AsyncResult<String>> handler);

  /**
   * Return the distance between two members in the geospatial index represented by the sorted set.
   *
   * @param key     Key string
   * @param member1 member 1
   * @param member2 member 2
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction geodistWithUnit(String key, String member1, String member2, GeoUnit unit, Handler<AsyncResult<String>> handler);

  /**
   * Return the members of a sorted set populated with geospatial information using GEOADD, which are within the borders
   * of the area specified with the center location and the maximum distance from the center (the radius).
   *
   * @param key     Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param radius radius
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler);

  /**
   * Return the members of a sorted set populated with geospatial information using GEOADD, which are within the borders
   * of the area specified with the center location and the maximum distance from the center (the radius).
   *
   * @param key     Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction georadiusWithOptions(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler);

  /**
   * This command is exactly like GEORADIUS with the sole difference that instead of taking, as the center of the area
   * to query, a longitude and latitude value, it takes the name of a member already existing inside the geospatial
   * index represented by the sorted set.
   *
   * @param key     Key string
   * @param member member
   * @param radius radius
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction georadiusbymember(String key, String member, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler);

  /**
   * This command is exactly like GEORADIUS with the sole difference that instead of taking, as the center of the area
   * to query, a longitude and latitude value, it takes the name of a member already existing inside the geospatial
   * index represented by the sorted set.
   *
   * @param key     Key string
   * @param member member
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options
   * @param handler Handler for the result of this call.
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  RedisTransaction georadiusbymemberWithOptions(String key, String member, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler);
}
