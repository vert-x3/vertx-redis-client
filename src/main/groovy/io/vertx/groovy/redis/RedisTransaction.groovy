/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.groovy.redis;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.redis.op.BitOperation
import io.vertx.redis.op.ResetOptions
import io.vertx.redis.op.ObjectCmd
import io.vertx.redis.op.KillFilter
import java.util.Map
import io.vertx.redis.op.GeoUnit
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.redis.op.RangeOptions
import io.vertx.redis.op.GeoMember
import io.vertx.redis.op.GeoRadiusOptions
import io.vertx.redis.op.InsertOptions
import io.vertx.redis.op.AggregateOptions
import io.vertx.redis.op.SetOptions
import io.vertx.redis.op.SortOptions
import io.vertx.redis.op.MigrateOptions
import io.vertx.redis.op.ScanOptions
import io.vertx.redis.op.FailoverOptions
import io.vertx.redis.op.SlotCmd
import io.vertx.redis.op.RangeLimitOptions
import io.vertx.redis.op.LimitOptions
import io.vertx.core.json.JsonArray
import java.util.List
import io.vertx.groovy.core.buffer.Buffer
import io.vertx.core.Handler
/**
 * This Interface represents a TX
*/
@CompileStatic
public class RedisTransaction {
  private final def io.vertx.redis.RedisTransaction delegate;
  public RedisTransaction(Object delegate) {
    this.delegate = (io.vertx.redis.RedisTransaction) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Close the client - when it is fully closed the handler will be called.
   * @param handler 
   */
  public void close(Handler<AsyncResult<Void>> handler) {
    delegate.close(handler);
  }
  /**
   * Append a value to a key
   * @param key Key string
   * @param value Value to append
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction append(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.append(key, value, handler);
    return this;
  }
  /**
   * Authenticate to the server
   * @param password Password for authentication
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction auth(String password, Handler<AsyncResult<String>> handler) {
    delegate.auth(password, handler);
    return this;
  }
  /**
   * Asynchronously rewrite the append-only file
   * @param handler 
   * @return 
   */
  public RedisTransaction bgrewriteaof(Handler<AsyncResult<String>> handler) {
    delegate.bgrewriteaof(handler);
    return this;
  }
  /**
   * Asynchronously save the dataset to disk
   * @param handler 
   * @return 
   */
  public RedisTransaction bgsave(Handler<AsyncResult<String>> handler) {
    delegate.bgsave(handler);
    return this;
  }
  /**
   * Count set bits in a string
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitcount(String key, Handler<AsyncResult<String>> handler) {
    delegate.bitcount(key, handler);
    return this;
  }
  /**
   * Count set bits in a string
   * @param key Key string
   * @param start Start index
   * @param end End index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitcountRange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    delegate.bitcountRange(key, start, end, handler);
    return this;
  }
  /**
   * Perform bitwise operations between strings
   * @param operation Bitwise operation to perform
   * @param destkey Destination key where result is stored
   * @param keys List of keys on which to perform the operation
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.bitop(operation, destkey, keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitpos(String key, int bit, Handler<AsyncResult<String>> handler) {
    delegate.bitpos(key, bit, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   * <p>
   * See also bitposRange() method, which takes start, and stop offset.
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitposFrom(String key, int bit, int start, Handler<AsyncResult<String>> handler) {
    delegate.bitposFrom(key, bit, start, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   * <p>
   * Note: when both start, and stop offsets are specified,
   * behaviour is slightly different than if only start is specified
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @param stop End offset - inclusive
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<String>> handler) {
    delegate.bitposRange(key, bit, start, stop, handler);
    return this;
  }
  /**
   * Remove and get the first element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction blpop(String key, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.blpop(key, seconds, handler);
    return this;
  }
  /**
   * Remove and get the first element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction blpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.blpopMany(keys != null ? (List)keys.collect({it}) : null, seconds, handler);
    return this;
  }
  /**
   * Remove and get the last element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction brpop(String key, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.brpop(key, seconds, handler);
    return this;
  }
  /**
   * Remove and get the last element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction brpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.brpopMany(keys != null ? (List)keys.collect({it}) : null, seconds, handler);
    return this;
  }
  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   * @param key Key string identifying the source list
   * @param destkey Key string identifying the destination list
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.brpoplpush(key, destkey, seconds, handler);
    return this;
  }
  /**
   * Kill the connection of a client
   * @param filter Filter options (see <a href="../../../../../../cheatsheet/KillFilter.html">KillFilter</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clientKill(Map<String, Object> filter = [:], Handler<AsyncResult<String>> handler) {
    delegate.clientKill(filter != null ? new io.vertx.redis.op.KillFilter(io.vertx.lang.groovy.InternalHelper.toJsonObject(filter)) : null, handler);
    return this;
  }
  /**
   * Get the list of client connections
   * @param handler 
   * @return 
   */
  public RedisTransaction clientList(Handler<AsyncResult<String>> handler) {
    delegate.clientList(handler);
    return this;
  }
  /**
   * Get the current connection name
   * @param handler 
   * @return 
   */
  public RedisTransaction clientGetname(Handler<AsyncResult<String>> handler) {
    delegate.clientGetname(handler);
    return this;
  }
  /**
   * Stop processing commands from clients for some time
   * @param millis Pause time in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clientPause(long millis, Handler<AsyncResult<String>> handler) {
    delegate.clientPause(millis, handler);
    return this;
  }
  /**
   * Set the current connection name
   * @param name New name for current connection
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clientSetname(String name, Handler<AsyncResult<String>> handler) {
    delegate.clientSetname(name, handler);
    return this;
  }
  /**
   * Assign new hash slots to receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterAddslots(List<String> slots, Handler<AsyncResult<String>> handler) {
    delegate.clusterAddslots(slots != null ? (List)slots.collect({it}) : null, handler);
    return this;
  }
  /**
   * Return the number of failure reports active for a given node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterCountFailureReports(String nodeId, Handler<AsyncResult<String>> handler) {
    delegate.clusterCountFailureReports(nodeId, handler);
    return this;
  }
  /**
   * Return the number of local keys in the specified hash slot.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterCountkeysinslot(long slot, Handler<AsyncResult<String>> handler) {
    delegate.clusterCountkeysinslot(slot, handler);
    return this;
  }
  /**
   * Set hash slots as unbound in receiving node.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterDelslots(long slot, Handler<AsyncResult<String>> handler) {
    delegate.clusterDelslots(slot, handler);
    return this;
  }
  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterDelslotsMany(List<String> slots, Handler<AsyncResult<String>> handler) {
    delegate.clusterDelslotsMany(slots != null ? (List)slots.collect({it}) : null, handler);
    return this;
  }
  /**
   * Forces a slave to perform a manual failover of its master.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterFailover(Handler<AsyncResult<String>> handler) {
    delegate.clusterFailover(handler);
    return this;
  }
  /**
   * Forces a slave to perform a manual failover of its master.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<String>> handler) {
    delegate.clusterFailOverWithOptions(options, handler);
    return this;
  }
  /**
   * Remove a node from the nodes table.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterForget(String nodeId, Handler<AsyncResult<String>> handler) {
    delegate.clusterForget(nodeId, handler);
    return this;
  }
  /**
   * Return local key names in the specified hash slot.
   * @param slot 
   * @param count 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<String>> handler) {
    delegate.clusterGetkeysinslot(slot, count, handler);
    return this;
  }
  /**
   * Provides info about Redis Cluster node state.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterInfo(Handler<AsyncResult<String>> handler) {
    delegate.clusterInfo(handler);
    return this;
  }
  /**
   * Returns the hash slot of the specified key.
   * @param key 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterKeyslot(String key, Handler<AsyncResult<String>> handler) {
    delegate.clusterKeyslot(key, handler);
    return this;
  }
  /**
   * Force a node cluster to handshake with another node.
   * @param ip 
   * @param port 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterMeet(String ip, long port, Handler<AsyncResult<String>> handler) {
    delegate.clusterMeet(ip, port, handler);
    return this;
  }
  /**
   * Get Cluster config for the node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterNodes(Handler<AsyncResult<String>> handler) {
    delegate.clusterNodes(handler);
    return this;
  }
  /**
   * Reconfigure a node as a slave of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterReplicate(String nodeId, Handler<AsyncResult<String>> handler) {
    delegate.clusterReplicate(nodeId, handler);
    return this;
  }
  /**
   * Reset a Redis Cluster node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterReset(Handler<AsyncResult<String>> handler) {
    delegate.clusterReset(handler);
    return this;
  }
  /**
   * Reset a Redis Cluster node.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<String>> handler) {
    delegate.clusterResetWithOptions(options, handler);
    return this;
  }
  /**
   * Forces the node to save cluster state on disk.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterSaveconfig(Handler<AsyncResult<String>> handler) {
    delegate.clusterSaveconfig(handler);
    return this;
  }
  /**
   * Set the configuration epoch in a new node.
   * @param epoch 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterSetConfigEpoch(long epoch, Handler<AsyncResult<String>> handler) {
    delegate.clusterSetConfigEpoch(epoch, handler);
    return this;
  }
  /**
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<String>> handler) {
    delegate.clusterSetslot(slot, subcommand, handler);
    return this;
  }
  /**
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<String>> handler) {
    delegate.clusterSetslotWithNode(slot, subcommand, nodeId, handler);
    return this;
  }
  /**
   * List slave nodes of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterSlaves(String nodeId, Handler<AsyncResult<String>> handler) {
    delegate.clusterSlaves(nodeId, handler);
    return this;
  }
  /**
   * Get array of Cluster slot to node mappings
   * @param handler 
   * @return 
   */
  public RedisTransaction clusterSlots(Handler<AsyncResult<String>> handler) {
    delegate.clusterSlots(handler);
    return this;
  }
  /**
   * Get array of Redis command details
   * @param handler 
   * @return 
   */
  public RedisTransaction command(Handler<AsyncResult<String>> handler) {
    delegate.command(handler);
    return this;
  }
  /**
   * Get total number of Redis commands
   * @param handler 
   * @return 
   */
  public RedisTransaction commandCount(Handler<AsyncResult<String>> handler) {
    delegate.commandCount(handler);
    return this;
  }
  /**
   * Extract keys given a full Redis command
   * @param handler 
   * @return 
   */
  public RedisTransaction commandGetkeys(Handler<AsyncResult<String>> handler) {
    delegate.commandGetkeys(handler);
    return this;
  }
  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction commandInfo(List<String> commands, Handler<AsyncResult<String>> handler) {
    delegate.commandInfo(commands != null ? (List)commands.collect({it}) : null, handler);
    return this;
  }
  /**
   * Get the value of a configuration parameter
   * @param parameter Configuration parameter
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction configGet(String parameter, Handler<AsyncResult<String>> handler) {
    delegate.configGet(parameter, handler);
    return this;
  }
  /**
   * Rewrite the configuration file with the in memory configuration
   * @param handler 
   * @return 
   */
  public RedisTransaction configRewrite(Handler<AsyncResult<String>> handler) {
    delegate.configRewrite(handler);
    return this;
  }
  /**
   * Set a configuration parameter to the given value
   * @param parameter Configuration parameter
   * @param value New value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    delegate.configSet(parameter, value, handler);
    return this;
  }
  /**
   * Reset the stats returned by INFO
   * @param handler 
   * @return 
   */
  public RedisTransaction configResetstat(Handler<AsyncResult<String>> handler) {
    delegate.configResetstat(handler);
    return this;
  }
  /**
   * Return the number of keys in the selected database
   * @param handler 
   * @return 
   */
  public RedisTransaction dbsize(Handler<AsyncResult<String>> handler) {
    delegate.dbsize(handler);
    return this;
  }
  /**
   * Get debugging information about a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction debugObject(String key, Handler<AsyncResult<String>> handler) {
    delegate.debugObject(key, handler);
    return this;
  }
  /**
   * Make the server crash
   * @param handler 
   * @return 
   */
  public RedisTransaction debugSegfault(Handler<AsyncResult<String>> handler) {
    delegate.debugSegfault(handler);
    return this;
  }
  /**
   * Decrement the integer value of a key by one
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction decr(String key, Handler<AsyncResult<String>> handler) {
    delegate.decr(key, handler);
    return this;
  }
  /**
   * Decrement the integer value of a key by the given number
   * @param key Key string
   * @param decrement Value by which to decrement
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction decrby(String key, long decrement, Handler<AsyncResult<String>> handler) {
    delegate.decrby(key, decrement, handler);
    return this;
  }
  /**
   * Delete a key
   * @param key Keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction del(String key, Handler<AsyncResult<String>> handler) {
    delegate.del(key, handler);
    return this;
  }
  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction delMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.delMany(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Discard all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisTransaction discard(Handler<AsyncResult<String>> handler) {
    delegate.discard(handler);
    return this;
  }
  /**
   * Return a serialized version of the value stored at the specified key.
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction dump(String key, Handler<AsyncResult<String>> handler) {
    delegate.dump(key, handler);
    return this;
  }
  /**
   * Echo the given string
   * @param message String to echo
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction echo(String message, Handler<AsyncResult<String>> handler) {
    delegate.echo(message, handler);
    return this;
  }
  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   * <p>
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   * @param script Lua script to evaluate
   * @param keys List of keys
   * @param args List of argument values
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<String>> handler) {
    delegate.eval(script, keys != null ? (List)keys.collect({it}) : null, args != null ? (List)args.collect({it}) : null, handler);
    return this;
  }
  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   * <p>
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   * @param sha1 SHA1 digest of the script cached on the server
   * @param keys List of keys
   * @param values List of values
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction evalsha(String sha1, List<String> keys, List<String> values, Handler<AsyncResult<String>> handler) {
    delegate.evalsha(sha1, keys != null ? (List)keys.collect({it}) : null, values != null ? (List)values.collect({it}) : null, handler);
    return this;
  }
  /**
   * Execute all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisTransaction exec(Handler<AsyncResult<List<Object>>> handler) {
    delegate.exec(handler != null ? new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture((List<Object>)InternalHelper.wrapObject(ar.result())));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    } : null);
    return this;
  }
  /**
   * Determine if a key exists
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction exists(String key, Handler<AsyncResult<String>> handler) {
    delegate.exists(key, handler);
    return this;
  }
  /**
   * Set a key's time to live in seconds
   * @param key Key string
   * @param seconds Time to live in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction expire(String key, int seconds, Handler<AsyncResult<String>> handler) {
    delegate.expire(key, seconds, handler);
    return this;
  }
  /**
   * Set the expiration for a key as a UNIX timestamp
   * @param key Key string
   * @param seconds Expiry time as Unix timestamp in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction expireat(String key, long seconds, Handler<AsyncResult<String>> handler) {
    delegate.expireat(key, seconds, handler);
    return this;
  }
  /**
   * Remove all keys from all databases
   * @param handler 
   * @return 
   */
  public RedisTransaction flushall(Handler<AsyncResult<String>> handler) {
    delegate.flushall(handler);
    return this;
  }
  /**
   * Remove all keys from the current database
   * @param handler 
   * @return 
   */
  public RedisTransaction flushdb(Handler<AsyncResult<String>> handler) {
    delegate.flushdb(handler);
    return this;
  }
  /**
   * Get the value of a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction get(String key, Handler<AsyncResult<String>> handler) {
    delegate.get(key, handler);
    return this;
  }
  /**
   * Get the value of a key - without decoding as utf-8
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction getBinary(String key, Handler<AsyncResult<String>> handler) {
    delegate.getBinary(key, handler);
    return this;
  }
  /**
   * Returns the bit value at offset in the string value stored at key
   * @param key Key string
   * @param offset Offset in bits
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction getbit(String key, long offset, Handler<AsyncResult<String>> handler) {
    delegate.getbit(key, offset, handler);
    return this;
  }
  /**
   * Get a substring of the string stored at a key
   * @param key Key string
   * @param start Start offset
   * @param end End offset - inclusive
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    delegate.getrange(key, start, end, handler);
    return this;
  }
  /**
   * Set the string value of a key and return its old value
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction getset(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.getset(key, value, handler);
    return this;
  }
  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hdel(String key, String field, Handler<AsyncResult<String>> handler) {
    delegate.hdel(key, field, handler);
    return this;
  }
  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hdelMany(String key, List<String> fields, Handler<AsyncResult<String>> handler) {
    delegate.hdelMany(key, fields != null ? (List)fields.collect({it}) : null, handler);
    return this;
  }
  /**
   * Determine if a hash field exists
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hexists(String key, String field, Handler<AsyncResult<String>> handler) {
    delegate.hexists(key, field, handler);
    return this;
  }
  /**
   * Get the value of a hash field
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hget(String key, String field, Handler<AsyncResult<String>> handler) {
    delegate.hget(key, field, handler);
    return this;
  }
  /**
   * Get all the fields and values in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hgetall(String key, Handler<AsyncResult<String>> handler) {
    delegate.hgetall(key, handler);
    return this;
  }
  /**
   * Increment the integer value of a hash field by the given number
   * @param key Key string
   * @param field Field name
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hincrby(String key, String field, long increment, Handler<AsyncResult<String>> handler) {
    delegate.hincrby(key, field, increment, handler);
    return this;
  }
  /**
   * Increment the float value of a hash field by the given amount
   * @param key Key string
   * @param field Field name
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    delegate.hincrbyfloat(key, field, increment, handler);
    return this;
  }
  /**
   * Get all the fields in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hkeys(String key, Handler<AsyncResult<String>> handler) {
    delegate.hkeys(key, handler);
    return this;
  }
  /**
   * Get the number of fields in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hlen(String key, Handler<AsyncResult<String>> handler) {
    delegate.hlen(key, handler);
    return this;
  }
  /**
   * Get the values of all the given hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hmget(String key, List<String> fields, Handler<AsyncResult<String>> handler) {
    delegate.hmget(key, fields != null ? (List)fields.collect({it}) : null, handler);
    return this;
  }
  /**
   * Set multiple hash fields to multiple values
   * @param key Key string
   * @param values Map of field:value pairs
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hmset(String key, Map<String, Object> values, Handler<AsyncResult<String>> handler) {
    delegate.hmset(key, values != null ? new io.vertx.core.json.JsonObject(values) : null, handler);
    return this;
  }
  /**
   * Set the string value of a hash field
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hset(String key, String field, String value, Handler<AsyncResult<String>> handler) {
    delegate.hset(key, field, value, handler);
    return this;
  }
  /**
   * Set the value of a hash field, only if the field does not exist
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hsetnx(String key, String field, String value, Handler<AsyncResult<String>> handler) {
    delegate.hsetnx(key, field, value, handler);
    return this;
  }
  /**
   * Get all the values in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hvals(String key, Handler<AsyncResult<String>> handler) {
    delegate.hvals(key, handler);
    return this;
  }
  /**
   * Increment the integer value of a key by one
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction incr(String key, Handler<AsyncResult<String>> handler) {
    delegate.incr(key, handler);
    return this;
  }
  /**
   * Increment the integer value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction incrby(String key, long increment, Handler<AsyncResult<String>> handler) {
    delegate.incrby(key, increment, handler);
    return this;
  }
  /**
   * Increment the float value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    delegate.incrbyfloat(key, increment, handler);
    return this;
  }
  /**
   * Get information and statistics about the server
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction info(Handler<AsyncResult<String>> handler) {
    delegate.info(handler);
    return this;
  }
  /**
   * Get information and statistics about the server
   * @param section Specific section of information to return
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction infoSection(String section, Handler<AsyncResult<String>> handler) {
    delegate.infoSection(section, handler);
    return this;
  }
  /**
   * Find all keys matching the given pattern
   * @param pattern Pattern to limit the keys returned
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction keys(String pattern, Handler<AsyncResult<String>> handler) {
    delegate.keys(pattern, handler);
    return this;
  }
  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @param handler 
   * @return 
   */
  public RedisTransaction lastsave(Handler<AsyncResult<String>> handler) {
    delegate.lastsave(handler);
    return this;
  }
  /**
   * Get an element from a list by its index
   * @param key Key string
   * @param index Index of list element to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    delegate.lindex(key, index, handler);
    return this;
  }
  /**
   * Insert an element before or after another element in a list
   * @param key Key string
   * @param option BEFORE or AFTER
   * @param pivot Key to use as a pivot
   * @param value Value to be inserted before or after the pivot
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<String>> handler) {
    delegate.linsert(key, option, pivot, value, handler);
    return this;
  }
  /**
   * Get the length of a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction llen(String key, Handler<AsyncResult<String>> handler) {
    delegate.llen(key, handler);
    return this;
  }
  /**
   * Remove and get the first element in a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lpop(String key, Handler<AsyncResult<String>> handler) {
    delegate.lpop(key, handler);
    return this;
  }
  /**
   * Prepend one or multiple values to a list
   * @param key Key string
   * @param values Values to be added at the beginning of the list, one by one
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) {
    delegate.lpushMany(key, values != null ? (List)values.collect({it}) : null, handler);
    return this;
  }
  /**
   * Prepend one value to a list
   * @param key Key string
   * @param value Value to be added at the beginning of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lpush(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.lpush(key, value, handler);
    return this;
  }
  /**
   * Prepend a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to add at the beginning of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lpushx(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.lpushx(key, value, handler);
    return this;
  }
  /**
   * Get a range of elements from a list
   * @param key Key string
   * @param from Start index
   * @param to Stop index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lrange(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    delegate.lrange(key, from, to, handler);
    return this;
  }
  /**
   * Remove elements from a list
   * @param key Key string
   * @param count Number of first found occurrences equal to $value to remove from the list
   * @param value Value to be removed
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lrem(String key, long count, String value, Handler<AsyncResult<String>> handler) {
    delegate.lrem(key, count, value, handler);
    return this;
  }
  /**
   * Set the value of an element in a list by its index
   * @param key Key string
   * @param index Position within list
   * @param value New value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
    delegate.lset(key, index, value, handler);
    return this;
  }
  /**
   * Trim a list to the specified range
   * @param key Key string
   * @param from Start index
   * @param to Stop index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    delegate.ltrim(key, from, to, handler);
    return this;
  }
  /**
   * Get the value of the given key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction mget(String key, Handler<AsyncResult<String>> handler) {
    delegate.mget(key, handler);
    return this;
  }
  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction mgetMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.mgetMany(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Atomically transfer a key from a Redis instance to another one.
   * @param host Destination host
   * @param port Destination port
   * @param key Key to migrate
   * @param destdb Destination database index
   * @param timeout 
   * @param options Migrate options (see <a href="../../../../../../cheatsheet/MigrateOptions.html">MigrateOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction migrate(String host, int port, String key, int destdb, long timeout, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.migrate(host, port, key, destdb, timeout, options != null ? new io.vertx.redis.op.MigrateOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Listen for all requests received by the server in real time
   * @param handler 
   * @return 
   */
  public RedisTransaction monitor(Handler<AsyncResult<String>> handler) {
    delegate.monitor(handler);
    return this;
  }
  /**
   * Move a key to another database
   * @param key Key to migrate
   * @param destdb Destination database index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction move(String key, int destdb, Handler<AsyncResult<String>> handler) {
    delegate.move(key, destdb, handler);
    return this;
  }
  /**
   * Set multiple keys to multiple values
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction mset(Map<String, Object> keyvals, Handler<AsyncResult<String>> handler) {
    delegate.mset(keyvals != null ? new io.vertx.core.json.JsonObject(keyvals) : null, handler);
    return this;
  }
  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction msetnx(Map<String, Object> keyvals, Handler<AsyncResult<String>> handler) {
    delegate.msetnx(keyvals != null ? new io.vertx.core.json.JsonObject(keyvals) : null, handler);
    return this;
  }
  /**
   * Mark the start of a RedisTransaction block
   * @param handler 
   * @return 
   */
  public RedisTransaction multi(Handler<AsyncResult<String>> handler) {
    delegate.multi(handler);
    return this;
  }
  /**
   * Inspect the internals of Redis objects
   * @param key Key string
   * @param cmd Object sub command
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction object(String key, ObjectCmd cmd, Handler<AsyncResult<String>> handler) {
    delegate.object(key, cmd, handler);
    return this;
  }
  /**
   * Remove the expiration from a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction persist(String key, Handler<AsyncResult<String>> handler) {
    delegate.persist(key, handler);
    return this;
  }
  /**
   * Set a key's time to live in milliseconds
   * @param key String key
   * @param millis Time to live in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pexpire(String key, long millis, Handler<AsyncResult<String>> handler) {
    delegate.pexpire(key, millis, handler);
    return this;
  }
  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @param key Key string
   * @param millis Expiry time as Unix timestamp in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pexpireat(String key, long millis, Handler<AsyncResult<String>> handler) {
    delegate.pexpireat(key, millis, handler);
    return this;
  }
  /**
   * Adds the specified element to the specified HyperLogLog.
   * @param key Key string
   * @param element Element to add
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfadd(String key, String element, Handler<AsyncResult<String>> handler) {
    delegate.pfadd(key, element, handler);
    return this;
  }
  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @param key Key string
   * @param elements Elementa to add
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfaddMany(String key, List<String> elements, Handler<AsyncResult<String>> handler) {
    delegate.pfaddMany(key, elements != null ? (List)elements.collect({it}) : null, handler);
    return this;
  }
  /**
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfcount(String key, Handler<AsyncResult<String>> handler) {
    delegate.pfcount(key, handler);
    return this;
  }
  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfcountMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.pfcountMany(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Merge N different HyperLogLogs into a single one.
   * @param destkey Destination key
   * @param keys List of source keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.pfmerge(destkey, keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Ping the server
   * @param handler 
   * @return 
   */
  public RedisTransaction ping(Handler<AsyncResult<String>> handler) {
    delegate.ping(handler);
    return this;
  }
  /**
   * Set the value and expiration in milliseconds of a key
   * @param key Key string
   * @param millis Number of milliseconds until the key expires
   * @param value New value for key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction psetex(String key, long millis, String value, Handler<AsyncResult<String>> handler) {
    delegate.psetex(key, millis, value, handler);
    return this;
  }
  /**
   * Listen for messages published to channels matching the given pattern
   * @param pattern Pattern string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction psubscribe(String pattern, Handler<AsyncResult<String>> handler) {
    delegate.psubscribe(pattern, handler);
    return this;
  }
  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction psubscribeMany(List<String> patterns, Handler<AsyncResult<String>> handler) {
    delegate.psubscribeMany(patterns != null ? (List)patterns.collect({it}) : null, handler);
    return this;
  }
  /**
   * Lists the currently active channels - only those matching the pattern
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pubsubChannels(String pattern, Handler<AsyncResult<String>> handler) {
    delegate.pubsubChannels(pattern, handler);
    return this;
  }
  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pubsubNumsub(List<String> channels, Handler<AsyncResult<String>> handler) {
    delegate.pubsubNumsub(channels != null ? (List)channels.collect({it}) : null, handler);
    return this;
  }
  /**
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pubsubNumpat(Handler<AsyncResult<String>> handler) {
    delegate.pubsubNumpat(handler);
    return this;
  }
  /**
   * Get the time to live for a key in milliseconds
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pttl(String key, Handler<AsyncResult<String>> handler) {
    delegate.pttl(key, handler);
    return this;
  }
  /**
   * Post a message to a channel
   * @param channel Channel key
   * @param message Message to send to channel
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction publish(String channel, String message, Handler<AsyncResult<String>> handler) {
    delegate.publish(channel, message, handler);
    return this;
  }
  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction punsubscribe(List<String> patterns, Handler<AsyncResult<String>> handler) {
    delegate.punsubscribe(patterns != null ? (List)patterns.collect({it}) : null, handler);
    return this;
  }
  /**
   * Return a random key from the keyspace
   * @param handler 
   * @return 
   */
  public RedisTransaction randomkey(Handler<AsyncResult<String>> handler) {
    delegate.randomkey(handler);
    return this;
  }
  /**
   * Rename a key
   * @param key Key string to be renamed
   * @param newkey New key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    delegate.rename(key, newkey, handler);
    return this;
  }
  /**
   * Rename a key, only if the new key does not exist
   * @param key Key string to be renamed
   * @param newkey New key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction renamenx(String key, String newkey, Handler<AsyncResult<String>> handler) {
    delegate.renamenx(key, newkey, handler);
    return this;
  }
  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   * @param key Key string
   * @param millis Expiry time in milliseconds to set on the key
   * @param serialized Serialized form of the key value as obtained using DUMP
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
    delegate.restore(key, millis, serialized, handler);
    return this;
  }
  /**
   * Return the role of the instance in the context of replication
   * @param handler 
   * @return 
   */
  public RedisTransaction role(Handler<AsyncResult<String>> handler) {
    delegate.role(handler);
    return this;
  }
  /**
   * Remove and get the last element in a list
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpop(String key, Handler<AsyncResult<String>> handler) {
    delegate.rpop(key, handler);
    return this;
  }
  /**
   * Remove the last element in a list, append it to another list and return it
   * @param key Key string identifying source list
   * @param destkey Key string identifying destination list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    delegate.rpoplpush(key, destkey, handler);
    return this;
  }
  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param values List of values to add to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) {
    delegate.rpushMany(key, values != null ? (List)values.collect({it}) : null, handler);
    return this;
  }
  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpush(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.rpush(key, value, handler);
    return this;
  }
  /**
   * Append a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpushx(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.rpushx(key, value, handler);
    return this;
  }
  /**
   * Add a member to a set
   * @param key Key string
   * @param member Value to be added to the set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sadd(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.sadd(key, member, handler);
    return this;
  }
  /**
   * Add one or more members to a set
   * @param key Key string
   * @param members Values to be added to the set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction saddMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
    delegate.saddMany(key, members != null ? (List)members.collect({it}) : null, handler);
    return this;
  }
  /**
   * Synchronously save the dataset to disk
   * @param handler 
   * @return 
   */
  public RedisTransaction save(Handler<AsyncResult<String>> handler) {
    delegate.save(handler);
    return this;
  }
  /**
   * Get the number of members in a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scard(String key, Handler<AsyncResult<String>> handler) {
    delegate.scard(key, handler);
    return this;
  }
  /**
   * Check existence of script in the script cache.
   * @param script SHA1 digest identifying a script in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scriptExists(String script, Handler<AsyncResult<String>> handler) {
    delegate.scriptExists(script, handler);
    return this;
  }
  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scriptExistsMany(List<String> scripts, Handler<AsyncResult<String>> handler) {
    delegate.scriptExistsMany(scripts != null ? (List)scripts.collect({it}) : null, handler);
    return this;
  }
  /**
   * Remove all the scripts from the script cache.
   * @param handler 
   * @return 
   */
  public RedisTransaction scriptFlush(Handler<AsyncResult<String>> handler) {
    delegate.scriptFlush(handler);
    return this;
  }
  /**
   * Kill the script currently in execution.
   * @param handler 
   * @return 
   */
  public RedisTransaction scriptKill(Handler<AsyncResult<String>> handler) {
    delegate.scriptKill(handler);
    return this;
  }
  /**
   * Load the specified Lua script into the script cache.
   * @param script Lua script
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scriptLoad(String script, Handler<AsyncResult<String>> handler) {
    delegate.scriptLoad(script, handler);
    return this;
  }
  /**
   * Subtract multiple sets
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler) {
    delegate.sdiff(key, cmpkeys != null ? (List)cmpkeys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Subtract multiple sets and store the resulting set in a key
   * @param destkey Destination key where the result should be stored
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler) {
    delegate.sdiffstore(destkey, key, cmpkeys != null ? (List)cmpkeys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Change the selected database for the current connection
   * @param dbindex Index identifying the new active database
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction select(int dbindex, Handler<AsyncResult<String>> handler) {
    delegate.select(dbindex, handler);
    return this;
  }
  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction set(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.set(key, value, handler);
    return this;
  }
  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options (see <a href="../../../../../../cheatsheet/SetOptions.html">SetOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setWithOptions(String key, String value, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.setWithOptions(key, value, options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Set the binary string value of a key - without encoding as utf-8
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setBinary(String key, Buffer value, Handler<AsyncResult<String>> handler) {
    delegate.setBinary(key, value != null ? (io.vertx.core.buffer.Buffer)value.getDelegate() : null, handler);
    return this;
  }
  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options (see <a href="../../../../../../cheatsheet/SetOptions.html">SetOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setBinaryWithOptions(String key, Buffer value, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.setBinaryWithOptions(key, value != null ? (io.vertx.core.buffer.Buffer)value.getDelegate() : null, options != null ? new io.vertx.redis.op.SetOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Sets or clears the bit at offset in the string value stored at key
   * @param key Key string
   * @param offset Bit offset
   * @param bit New value - must be 1 or 0
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setbit(String key, long offset, int bit, Handler<AsyncResult<String>> handler) {
    delegate.setbit(key, offset, bit, handler);
    return this;
  }
  /**
   * Set the value and expiration of a key
   * @param key Key string
   * @param seconds Number of seconds until the key expires
   * @param value New value for key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    delegate.setex(key, seconds, value, handler);
    return this;
  }
  /**
   * Set the value of a key, only if the key does not exist
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setnx(String key, String value, Handler<AsyncResult<String>> handler) {
    delegate.setnx(key, value, handler);
    return this;
  }
  /**
   * Overwrite part of a string at key starting at the specified offset
   * @param key Key string
   * @param offset Offset - the maximum offset that you can set is 2^29 -1 (536870911), as Redis Strings are limited to 512 megabytes
   * @param value Value to overwrite with
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setrange(String key, int offset, String value, Handler<AsyncResult<String>> handler) {
    delegate.setrange(key, offset, value, handler);
    return this;
  }
  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sinter(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.sinter(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Intersect multiple sets and store the resulting set in a key
   * @param destkey Key where to store the results
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sinterstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.sinterstore(destkey, keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Determine if a given value is a member of a set
   * @param key Key string
   * @param member Member to look for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sismember(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.sismember(key, member, handler);
    return this;
  }
  /**
   * Make the server a slave of another instance
   * @param host Host to become this server's master
   * @param port Port of our new master
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
    delegate.slaveof(host, port, handler);
    return this;
  }
  /**
   * Make this server a master
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction slaveofNoone(Handler<AsyncResult<String>> handler) {
    delegate.slaveofNoone(handler);
    return this;
  }
  /**
   * Read the Redis slow queries log
   * @param limit Number of log entries to return. If value is less than zero all entries are returned
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction slowlogGet(int limit, Handler<AsyncResult<String>> handler) {
    delegate.slowlogGet(limit, handler);
    return this;
  }
  /**
   * Get the length of the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction slowlogLen(Handler<AsyncResult<String>> handler) {
    delegate.slowlogLen(handler);
    return this;
  }
  /**
   * Reset the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction slowlogReset(Handler<AsyncResult<String>> handler) {
    delegate.slowlogReset(handler);
    return this;
  }
  /**
   * Get all the members in a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction smembers(String key, Handler<AsyncResult<String>> handler) {
    delegate.smembers(key, handler);
    return this;
  }
  /**
   * Move a member from one set to another
   * @param key Key of source set currently containing the member
   * @param destkey Key identifying the destination set
   * @param member Member to move
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction smove(String key, String destkey, String member, Handler<AsyncResult<String>> handler) {
    delegate.smove(key, destkey, member, handler);
    return this;
  }
  /**
   * Sort the elements in a list, set or sorted set
   * @param key Key string
   * @param options Sort options (see <a href="../../../../../../cheatsheet/SortOptions.html">SortOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sort(String key, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.sort(key, options != null ? new io.vertx.redis.op.SortOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Remove and return a random member from a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction spop(String key, Handler<AsyncResult<String>> handler) {
    delegate.spop(key, handler);
    return this;
  }
  /**
   * Remove and return random members from a set
   * @param key Key string
   * @param count Number of members to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
    delegate.spopMany(key, count, handler);
    return this;
  }
  /**
   * Get one or multiple random members from a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction srandmember(String key, Handler<AsyncResult<String>> handler) {
    delegate.srandmember(key, handler);
    return this;
  }
  /**
   * Get one or multiple random members from a set
   * @param key Key string
   * @param count Number of members to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction srandmemberCount(String key, int count, Handler<AsyncResult<String>> handler) {
    delegate.srandmemberCount(key, count, handler);
    return this;
  }
  /**
   * Remove one member from a set
   * @param key Key string
   * @param member Member to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction srem(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.srem(key, member, handler);
    return this;
  }
  /**
   * Remove one or more members from a set
   * @param key Key string
   * @param members Members to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
    delegate.sremMany(key, members != null ? (List)members.collect({it}) : null, handler);
    return this;
  }
  /**
   * Get the length of the value stored in a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction strlen(String key, Handler<AsyncResult<String>> handler) {
    delegate.strlen(key, handler);
    return this;
  }
  /**
   * Listen for messages published to the given channels
   * @param channel Channel to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction subscribe(String channel, Handler<AsyncResult<String>> handler) {
    delegate.subscribe(channel, handler);
    return this;
  }
  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction subscribeMany(List<String> channels, Handler<AsyncResult<String>> handler) {
    delegate.subscribeMany(channels != null ? (List)channels.collect({it}) : null, handler);
    return this;
  }
  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sunion(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.sunion(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Add multiple sets and store the resulting set in a key
   * @param destkey Destination key
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sunionstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.sunionstore(destkey, keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Internal command used for replication
   * @param handler 
   * @return 
   */
  public RedisTransaction sync(Handler<AsyncResult<String>> handler) {
    delegate.sync(handler);
    return this;
  }
  /**
   * Return the current server time
   * @param handler 
   * @return 
   */
  public RedisTransaction time(Handler<AsyncResult<String>> handler) {
    delegate.time(handler);
    return this;
  }
  /**
   * Get the time to live for a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction ttl(String key, Handler<AsyncResult<String>> handler) {
    delegate.ttl(key, handler);
    return this;
  }
  /**
   * Determine the type stored at key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction type(String key, Handler<AsyncResult<String>> handler) {
    delegate.type(key, handler);
    return this;
  }
  /**
   * Stop listening for messages posted to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction unsubscribe(List<String> channels, Handler<AsyncResult<String>> handler) {
    delegate.unsubscribe(channels != null ? (List)channels.collect({it}) : null, handler);
    return this;
  }
  /**
   * Forget about all watched keys
   * @param handler 
   * @return 
   */
  public RedisTransaction unwatch(Handler<AsyncResult<String>> handler) {
    delegate.unwatch(handler);
    return this;
  }
  /**
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection.
   * @param numSlaves 
   * @param timeout 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) {
    delegate.wait(numSlaves, timeout, handler);
    return this;
  }
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param key Key to watch
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction watch(String key, Handler<AsyncResult<String>> handler) {
    delegate.watch(key, handler);
    return this;
  }
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param keys List of keys to watch
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction watchMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    delegate.watchMany(keys != null ? (List)keys.collect({it}) : null, handler);
    return this;
  }
  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param score Score used for sorting
   * @param member New member key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zadd(String key, double score, String member, Handler<AsyncResult<String>> handler) {
    delegate.zadd(key, score, member, handler);
    return this;
  }
  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param members New member keys and their scores
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<String>> handler) {
    delegate.zaddMany(key, members != null ? (Map)members.collectEntries({[it.key,it.value]}) : null, handler);
    return this;
  }
  /**
   * Get the number of members in a sorted set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zcard(String key, Handler<AsyncResult<String>> handler) {
    delegate.zcard(key, handler);
    return this;
  }
  /**
   * Count the members in a sorted set with scores within the given values
   * @param key Key string
   * @param min Minimum score
   * @param max Maximum score
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zcount(String key, double min, double max, Handler<AsyncResult<String>> handler) {
    delegate.zcount(key, min, max, handler);
    return this;
  }
  /**
   * Increment the score of a member in a sorted set
   * @param key Key string
   * @param increment Increment amount
   * @param member Member key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    delegate.zincrby(key, increment, member, handler);
    return this;
  }
  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zinterstore(destkey, sets != null ? (List)sets.collect({it}) : null, options, handler);
    return this;
  }
  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zinterstoreWeighed(destkey, sets != null ? (Map)sets.collectEntries({[it.key,it.value]}) : null, options, handler);
    return this;
  }
  /**
   * Count the number of members in a sorted set between a given lexicographical range
   * @param key Key string
   * @param min Pattern to compare against for minimum value
   * @param max Pattern to compare against for maximum value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zlexcount(String key, String min, String max, Handler<AsyncResult<String>> handler) {
    delegate.zlexcount(key, min, max, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by index
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrange(String key, long start, long stop, Handler<AsyncResult<String>> handler) {
    delegate.zrange(key, start, stop, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by index
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @param options Range options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zrangeWithOptions(key, start, stop, options, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by lexicographical range
   * @param key Key string
   * @param min Pattern representing a minimum allowed value
   * @param max Pattern representing a maximum allowed value
   * @param options Limit options where limit can be specified (see <a href="../../../../../../cheatsheet/LimitOptions.html">LimitOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrangebylex(String key, String min, String max, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.zrangebylex(key, min, max, options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by score
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @param options Range and limit options (see <a href="../../../../../../cheatsheet/RangeLimitOptions.html">RangeLimitOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrangebyscore(String key, String min, String max, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.zrangebyscore(key, min, max, options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Determine the index of a member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrank(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.zrank(key, member, handler);
    return this;
  }
  /**
   * Remove one member from a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrem(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.zrem(key, member, handler);
    return this;
  }
  /**
   * Remove one or more members from a sorted set
   * @param key Key string
   * @param members Members in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
    delegate.zremMany(key, members != null ? (List)members.collect({it}) : null, handler);
    return this;
  }
  /**
   * Remove all members in a sorted set between the given lexicographical range
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zremrangebylex(String key, String min, String max, Handler<AsyncResult<String>> handler) {
    delegate.zremrangebylex(key, min, max, handler);
    return this;
  }
  /**
   * Remove all members in a sorted set within the given indexes
   * @param key Key string
   * @param start Start index
   * @param stop Stop index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<String>> handler) {
    delegate.zremrangebyrank(key, start, stop, handler);
    return this;
  }
  /**
   * Remove all members in a sorted set within the given scores
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @param handler 
   * @return 
   */
  public RedisTransaction zremrangebyscore(String key, String min, String max, Handler<AsyncResult<String>> handler) {
    delegate.zremrangebyscore(key, min, max, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @param options Range options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zrevrange(key, start, stop, options, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by score, between the given lexicographical range with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Limit options (see <a href="../../../../../../cheatsheet/LimitOptions.html">LimitOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrangebylex(String key, String max, String min, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.zrevrangebylex(key, max, min, options != null ? new io.vertx.redis.op.LimitOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Range and limit options (see <a href="../../../../../../cheatsheet/RangeLimitOptions.html">RangeLimitOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrangebyscore(String key, String max, String min, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.zrevrangebyscore(key, max, min, options != null ? new io.vertx.redis.op.RangeLimitOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrank(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.zrevrank(key, member, handler);
    return this;
  }
  /**
   * Get the score associated with the given member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zscore(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.zscore(key, member, handler);
    return this;
  }
  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zunionstore(destkey, sets != null ? (List)sets.collect({it}) : null, options, handler);
    return this;
  }
  /**
   * Add multiple sorted sets using weights, and store the resulting sorted set in a new key
   * @param key Destination key
   * @param sets Map containing set-key:weight pairs
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zunionstoreWeighed(String key, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
    delegate.zunionstoreWeighed(key, sets != null ? (Map)sets.collectEntries({[it.key,it.value]}) : null, options, handler);
    return this;
  }
  /**
   * Incrementally iterate the keys space
   * @param cursor Cursor id
   * @param options Scan options (see <a href="../../../../../../cheatsheet/ScanOptions.html">ScanOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scan(String cursor, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.scan(cursor, options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Incrementally iterate Set elements
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options (see <a href="../../../../../../cheatsheet/ScanOptions.html">ScanOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.sscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Incrementally iterate hash fields and associated values
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options (see <a href="../../../../../../cheatsheet/ScanOptions.html">ScanOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.hscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options (see <a href="../../../../../../cheatsheet/ScanOptions.html">ScanOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.zscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   * @param key Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param member member
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geoadd(String key, double longitude, double latitude, String member, Handler<AsyncResult<String>> handler) {
    delegate.geoadd(key, longitude, latitude, member, handler);
    return this;
  }
  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   * @param key Key string
   * @param members list of &lt;lon, lat, member&gt;
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geoaddMany(String key, List<Map<String, Object>> members, Handler<AsyncResult<String>> handler) {
    delegate.geoaddMany(key, members != null ? (List)members.collect({new io.vertx.redis.op.GeoMember(io.vertx.lang.groovy.InternalHelper.toJsonObject(it))}) : null, handler);
    return this;
  }
  /**
   * Return valid Geohash strings representing the position of one or more elements in a sorted set value representing
   * a geospatial index (where elements were added using GEOADD).
   * @param key Key string
   * @param member member
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geohash(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.geohash(key, member, handler);
    return this;
  }
  /**
   * Return valid Geohash strings representing the position of one or more elements in a sorted set value representing
   * a geospatial index (where elements were added using GEOADD).
   * @param key Key string
   * @param members list of members
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geohashMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
    delegate.geohashMany(key, members != null ? (List)members.collect({it}) : null, handler);
    return this;
  }
  /**
   * Return the positions (longitude,latitude) of all the specified members of the geospatial index represented by the
   * sorted set at key.
   * @param key Key string
   * @param member member
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geopos(String key, String member, Handler<AsyncResult<String>> handler) {
    delegate.geopos(key, member, handler);
    return this;
  }
  /**
   * Return the positions (longitude,latitude) of all the specified members of the geospatial index represented by the
   * sorted set at key.
   * @param key Key string
   * @param members list of members
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geoposMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
    delegate.geoposMany(key, members != null ? (List)members.collect({it}) : null, handler);
    return this;
  }
  /**
   * Return the distance between two members in the geospatial index represented by the sorted set.
   * @param key Key string
   * @param member1 member 1
   * @param member2 member 2
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geodist(String key, String member1, String member2, Handler<AsyncResult<String>> handler) {
    delegate.geodist(key, member1, member2, handler);
    return this;
  }
  /**
   * Return the distance between two members in the geospatial index represented by the sorted set.
   * @param key Key string
   * @param member1 member 1
   * @param member2 member 2
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geodistWithUnit(String key, String member1, String member2, GeoUnit unit, Handler<AsyncResult<String>> handler) {
    delegate.geodistWithUnit(key, member1, member2, unit, handler);
    return this;
  }
  /**
   * Return the members of a sorted set populated with geospatial information using GEOADD, which are within the borders
   * of the area specified with the center location and the maximum distance from the center (the radius).
   * @param key Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param radius radius
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler) {
    delegate.georadius(key, longitude, latitude, radius, unit, handler);
    return this;
  }
  /**
   * Return the members of a sorted set populated with geospatial information using GEOADD, which are within the borders
   * of the area specified with the center location and the maximum distance from the center (the radius).
   * @param key Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options (see <a href="../../../../../../cheatsheet/GeoRadiusOptions.html">GeoRadiusOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadiusWithOptions(String key, double longitude, double latitude, double radius, GeoUnit unit, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.georadiusWithOptions(key, longitude, latitude, radius, unit, options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
  /**
   * This command is exactly like GEORADIUS with the sole difference that instead of taking, as the center of the area
   * to query, a longitude and latitude value, it takes the name of a member already existing inside the geospatial
   * index represented by the sorted set.
   * @param key Key string
   * @param member member
   * @param radius radius
   * @param unit geo unit
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadiusbymember(String key, String member, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler) {
    delegate.georadiusbymember(key, member, radius, unit, handler);
    return this;
  }
  /**
   * This command is exactly like GEORADIUS with the sole difference that instead of taking, as the center of the area
   * to query, a longitude and latitude value, it takes the name of a member already existing inside the geospatial
   * index represented by the sorted set.
   * @param key Key string
   * @param member member
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options (see <a href="../../../../../../cheatsheet/GeoRadiusOptions.html">GeoRadiusOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadiusbymemberWithOptions(String key, String member, double radius, GeoUnit unit, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    delegate.georadiusbymemberWithOptions(key, member, radius, unit, options != null ? new io.vertx.redis.op.GeoRadiusOptions(io.vertx.lang.groovy.InternalHelper.toJsonObject(options)) : null, handler);
    return this;
  }
}
