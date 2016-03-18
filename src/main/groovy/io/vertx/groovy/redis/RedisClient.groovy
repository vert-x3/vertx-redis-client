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
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.redis.op.RangeOptions
import io.vertx.redis.op.InsertOptions
import io.vertx.redis.op.AggregateOptions
import io.vertx.redis.op.SetOptions
import io.vertx.redis.op.SortOptions
import io.vertx.redis.op.MigrateOptions
import io.vertx.redis.op.ScanOptions
import io.vertx.groovy.core.Vertx
import io.vertx.redis.op.FailoverOptions
import io.vertx.redis.op.SlotCmd
import io.vertx.redis.op.RangeLimitOptions
import io.vertx.redis.op.LimitOptions
import io.vertx.core.json.JsonArray
import java.util.List
import io.vertx.groovy.core.buffer.Buffer
import io.vertx.redis.RedisOptions
import io.vertx.core.Handler
@CompileStatic
public class RedisClient {
  private final def io.vertx.redis.RedisClient delegate;
  public RedisClient(Object delegate) {
    this.delegate = (io.vertx.redis.RedisClient) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static RedisClient create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(io.vertx.redis.RedisClient.create((io.vertx.core.Vertx)vertx.getDelegate()), io.vertx.groovy.redis.RedisClient.class);
    return ret;
  }
  public static RedisClient create(Vertx vertx, Map<String, Object> config) {
    def ret= InternalHelper.safeCreate(io.vertx.redis.RedisClient.create((io.vertx.core.Vertx)vertx.getDelegate(), config != null ? new io.vertx.redis.RedisOptions(new io.vertx.core.json.JsonObject(config)) : null), io.vertx.groovy.redis.RedisClient.class);
    return ret;
  }
  /**
   * Close the client - when it is fully closed the handler will be called.
   * @param handler 
   */
  public void close(Handler<AsyncResult<Void>> handler) {
    this.delegate.close(handler);
  }
  /**
   * Append a value to a key
   * @param key Key string
   * @param value Value to append
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient append(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.append(key, value, handler);
    return this;
  }
  /**
   * Authenticate to the server
   * @param password Password for authentication
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient auth(String password, Handler<AsyncResult<String>> handler) {
    this.delegate.auth(password, handler);
    return this;
  }
  /**
   * Asynchronously rewrite the append-only file
   * @param handler 
   * @return 
   */
  public RedisClient bgrewriteaof(Handler<AsyncResult<String>> handler) {
    this.delegate.bgrewriteaof(handler);
    return this;
  }
  /**
   * Asynchronously save the dataset to disk
   * @param handler 
   * @return 
   */
  public RedisClient bgsave(Handler<AsyncResult<String>> handler) {
    this.delegate.bgsave(handler);
    return this;
  }
  /**
   * Count set bits in a string
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient bitcount(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitcount(key, handler);
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
  public RedisClient bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitcountRange(key, start, end, handler);
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
  public RedisClient bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitop(operation, destkey, keys, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitpos(key, bit, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   *
   * See also bitposRange() method, which takes start, and stop offset.
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient bitposFrom(String key, int bit, int start, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitposFrom(key, bit, start, handler);
    return this;
  }
  /**
   * Find first bit set or clear in a string
   *
   * Note: when both start, and stop offsets are specified,
   * behaviour is slightly different than if only start is specified
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @param stop End offset - inclusive
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitposRange(key, bit, start, stop, handler);
    return this;
  }
  /**
   * Remove and get the first element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient blpop(String key, int seconds, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.blpop(key, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove and get the first element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient blpopMany(List<String> keys, int seconds, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.blpopMany(keys, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove and get the last element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient brpop(String key, int seconds, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.brpop(key, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove and get the last element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient brpopMany(List<String> keys, int seconds, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.brpopMany(keys, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) {
    this.delegate.brpoplpush(key, destkey, seconds, handler);
    return this;
  }
  /**
   * Kill the connection of a client
   * @param filter Filter options (see <a href="../../../../../../cheatsheet/KillFilter.html">KillFilter</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clientKill(Map<String, Object> filter = [:], Handler<AsyncResult<Long>> handler) {
    this.delegate.clientKill(filter != null ? new io.vertx.redis.op.KillFilter(new io.vertx.core.json.JsonObject(filter)) : null, handler);
    return this;
  }
  /**
   * Get the list of client connections
   * @param handler 
   * @return 
   */
  public RedisClient clientList(Handler<AsyncResult<String>> handler) {
    this.delegate.clientList(handler);
    return this;
  }
  /**
   * Get the current connection name
   * @param handler 
   * @return 
   */
  public RedisClient clientGetname(Handler<AsyncResult<String>> handler) {
    this.delegate.clientGetname(handler);
    return this;
  }
  /**
   * Stop processing commands from clients for some time
   * @param millis Pause time in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clientPause(long millis, Handler<AsyncResult<String>> handler) {
    this.delegate.clientPause(millis, handler);
    return this;
  }
  /**
   * Set the current connection name
   * @param name New name for current connection
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clientSetname(String name, Handler<AsyncResult<String>> handler) {
    this.delegate.clientSetname(name, handler);
    return this;
  }
  /**
   * Assign new hash slots to receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterAddslots(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterAddslots(slots, handler);
    return this;
  }
  /**
   * Return the number of failure reports active for a given node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterCountFailureReports(String nodeId, Handler<AsyncResult<Long>> handler) {
    this.delegate.clusterCountFailureReports(nodeId, handler);
    return this;
  }
  /**
   * Return the number of local keys in the specified hash slot.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterCountkeysinslot(long slot, Handler<AsyncResult<Long>> handler) {
    this.delegate.clusterCountkeysinslot(slot, handler);
    return this;
  }
  /**
   * Set hash slots as unbound in receiving node.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterDelslots(long slot, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterDelslots(slot, handler);
    return this;
  }
  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterDelslotsMany(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterDelslotsMany(slots, handler);
    return this;
  }
  /**
   * Forces a slave to perform a manual failover of its master.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterFailover(Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterFailover(handler);
    return this;
  }
  /**
   * Forces a slave to perform a manual failover of its master.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterFailOverWithOptions(options, handler);
    return this;
  }
  /**
   * Remove a node from the nodes table.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterForget(String nodeId, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterForget(nodeId, handler);
    return this;
  }
  /**
   * Return local key names in the specified hash slot.
   * @param slot 
   * @param count 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterGetkeysinslot(slot, count, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Provides info about Redis Cluster node state.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterInfo(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterInfo(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Returns the hash slot of the specified key.
   * @param key 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterKeyslot(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.clusterKeyslot(key, handler);
    return this;
  }
  /**
   * Force a node cluster to handshake with another node.
   * @param ip 
   * @param port 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterMeet(String ip, long port, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterMeet(ip, port, handler);
    return this;
  }
  /**
   * Get Cluster config for the node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterNodes(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterNodes(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Reconfigure a node as a slave of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterReplicate(String nodeId, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterReplicate(nodeId, handler);
    return this;
  }
  /**
   * Reset a Redis Cluster node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterReset(Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterReset(handler);
    return this;
  }
  /**
   * Reset a Redis Cluster node.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterResetWithOptions(options, handler);
    return this;
  }
  /**
   * Forces the node to save cluster state on disk.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSaveconfig(Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterSaveconfig(handler);
    return this;
  }
  /**
   * Set the configuration epoch in a new node.
   * @param epoch 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSetConfigEpoch(long epoch, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterSetConfigEpoch(epoch, handler);
    return this;
  }
  /**
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterSetslot(slot, subcommand, handler);
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
  public RedisClient clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<Void>> handler) {
    this.delegate.clusterSetslotWithNode(slot, subcommand, nodeId, handler);
    return this;
  }
  /**
   * List slave nodes of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSlaves(String nodeId, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterSlaves(nodeId, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get array of Cluster slot to node mappings
   * @param handler 
   * @return 
   */
  public RedisClient clusterSlots(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterSlots(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get array of Redis command details
   * @param handler 
   * @return 
   */
  public RedisClient command(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.command(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get total number of Redis commands
   * @param handler 
   * @return 
   */
  public RedisClient commandCount(Handler<AsyncResult<Long>> handler) {
    this.delegate.commandCount(handler);
    return this;
  }
  /**
   * Extract keys given a full Redis command
   * @param handler 
   * @return 
   */
  public RedisClient commandGetkeys(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.commandGetkeys(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient commandInfo(List<String> commands, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.commandInfo(commands, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the value of a configuration parameter
   * @param parameter Configuration parameter
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient configGet(String parameter, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.configGet(parameter, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Rewrite the configuration file with the in memory configuration
   * @param handler 
   * @return 
   */
  public RedisClient configRewrite(Handler<AsyncResult<String>> handler) {
    this.delegate.configRewrite(handler);
    return this;
  }
  /**
   * Set a configuration parameter to the given value
   * @param parameter Configuration parameter
   * @param value New value
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    this.delegate.configSet(parameter, value, handler);
    return this;
  }
  /**
   * Reset the stats returned by INFO
   * @param handler 
   * @return 
   */
  public RedisClient configResetstat(Handler<AsyncResult<String>> handler) {
    this.delegate.configResetstat(handler);
    return this;
  }
  /**
   * Return the number of keys in the selected database
   * @param handler 
   * @return 
   */
  public RedisClient dbsize(Handler<AsyncResult<Long>> handler) {
    this.delegate.dbsize(handler);
    return this;
  }
  /**
   * Get debugging information about a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient debugObject(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.debugObject(key, handler);
    return this;
  }
  /**
   * Make the server crash
   * @param handler 
   * @return 
   */
  public RedisClient debugSegfault(Handler<AsyncResult<String>> handler) {
    this.delegate.debugSegfault(handler);
    return this;
  }
  /**
   * Decrement the integer value of a key by one
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient decr(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.decr(key, handler);
    return this;
  }
  /**
   * Decrement the integer value of a key by the given number
   * @param key Key string
   * @param decrement Value by which to decrement
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    this.delegate.decrby(key, decrement, handler);
    return this;
  }
  /**
   * Delete a key
   * @param key Keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient del(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.del(key, handler);
    return this;
  }
  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient delMany(List<String> keys, Handler<AsyncResult<Long>> handler) {
    this.delegate.delMany(keys, handler);
    return this;
  }
  /**
   * Discard all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisClient discard(Handler<AsyncResult<String>> handler) {
    this.delegate.discard(handler);
    return this;
  }
  /**
   * Return a serialized version of the value stored at the specified key.
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient dump(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.dump(key, handler);
    return this;
  }
  /**
   * Echo the given string
   * @param message String to echo
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient echo(String message, Handler<AsyncResult<String>> handler) {
    this.delegate.echo(message, handler);
    return this;
  }
  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   *
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   * @param script Lua script to evaluate
   * @param keys List of keys
   * @param args List of argument values
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.eval(script, keys, args, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Execute a Lua script server side. Due to the dynamic nature of this command any response type could be returned
   * for This reason and to ensure type safety the reply is always guaranteed to be a JsonArray.
   *
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   * @param sha1 SHA1 digest of the script cached on the server
   * @param keys List of keys
   * @param values List of values
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient evalsha(String sha1, List<String> keys, List<String> values, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.evalsha(sha1, keys, values, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Execute all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisClient exec(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.exec(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Determine if a key exists
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient exists(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.exists(key, handler);
    return this;
  }
  /**
   * Set a key's time to live in seconds
   * @param key Key string
   * @param seconds Time to live in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    this.delegate.expire(key, seconds, handler);
    return this;
  }
  /**
   * Set the expiration for a key as a UNIX timestamp
   * @param key Key string
   * @param seconds Expiry time as Unix timestamp in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient expireat(String key, long seconds, Handler<AsyncResult<Long>> handler) {
    this.delegate.expireat(key, seconds, handler);
    return this;
  }
  /**
   * Remove all keys from all databases
   * @param handler 
   * @return 
   */
  public RedisClient flushall(Handler<AsyncResult<String>> handler) {
    this.delegate.flushall(handler);
    return this;
  }
  /**
   * Remove all keys from the current database
   * @param handler 
   * @return 
   */
  public RedisClient flushdb(Handler<AsyncResult<String>> handler) {
    this.delegate.flushdb(handler);
    return this;
  }
  /**
   * Get the value of a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient get(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.get(key, handler);
    return this;
  }
  /**
   * Get the value of a key - without decoding as utf-8
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient getBinary(String key, Handler<AsyncResult<Buffer>> handler) {
    this.delegate.getBinary(key, new Handler<AsyncResult<io.vertx.core.buffer.Buffer>>() {
      public void handle(AsyncResult<io.vertx.core.buffer.Buffer> event) {
        AsyncResult<Buffer> f
        if (event.succeeded()) {
          f = InternalHelper.<Buffer>result(new Buffer(event.result()))
        } else {
          f = InternalHelper.<Buffer>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Returns the bit value at offset in the string value stored at key
   * @param key Key string
   * @param offset Offset in bits
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient getbit(String key, long offset, Handler<AsyncResult<Long>> handler) {
    this.delegate.getbit(key, offset, handler);
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
  public RedisClient getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    this.delegate.getrange(key, start, end, handler);
    return this;
  }
  /**
   * Set the string value of a key and return its old value
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient getset(String key, String value, Handler<AsyncResult<String>> handler) {
    this.delegate.getset(key, value, handler);
    return this;
  }
  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hdel(String key, String field, Handler<AsyncResult<Long>> handler) {
    this.delegate.hdel(key, field, handler);
    return this;
  }
  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) {
    this.delegate.hdelMany(key, fields, handler);
    return this;
  }
  /**
   * Determine if a hash field exists
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hexists(String key, String field, Handler<AsyncResult<Long>> handler) {
    this.delegate.hexists(key, field, handler);
    return this;
  }
  /**
   * Get the value of a hash field
   * @param key Key string
   * @param field Field name
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hget(String key, String field, Handler<AsyncResult<String>> handler) {
    this.delegate.hget(key, field, handler);
    return this;
  }
  /**
   * Get all the fields and values in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hgetall(String key, Handler<AsyncResult<Map<String, Object>>> handler) {
    this.delegate.hgetall(key, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> event) {
        AsyncResult<Map<String, Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<Map<String, Object>>result((Map<String, Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Map<String, Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) {
    this.delegate.hincrby(key, field, increment, handler);
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
  public RedisClient hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    this.delegate.hincrbyfloat(key, field, increment, handler);
    return this;
  }
  /**
   * Get all the fields in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hkeys(String key, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hkeys(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the number of fields in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hlen(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.hlen(key, handler);
    return this;
  }
  /**
   * Get the values of all the given hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hmget(String key, List<String> fields, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hmget(key, fields, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Set multiple hash fields to multiple values
   * @param key Key string
   * @param values Map of field:value pairs
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hmset(String key, Map<String, Object> values, Handler<AsyncResult<String>> handler) {
    this.delegate.hmset(key, values != null ? new io.vertx.core.json.JsonObject(values) : null, handler);
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
  public RedisClient hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.hset(key, field, value, handler);
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
  public RedisClient hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.hsetnx(key, field, value, handler);
    return this;
  }
  /**
   * Get all the values in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hvals(String key, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hvals(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Increment the integer value of a key by one
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient incr(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.incr(key, handler);
    return this;
  }
  /**
   * Increment the integer value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    this.delegate.incrby(key, increment, handler);
    return this;
  }
  /**
   * Increment the float value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    this.delegate.incrbyfloat(key, increment, handler);
    return this;
  }
  /**
   * Get information and statistics about the server
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient info(Handler<AsyncResult<Map<String, Object>>> handler) {
    this.delegate.info(new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> event) {
        AsyncResult<Map<String, Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<Map<String, Object>>result((Map<String, Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Map<String, Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get information and statistics about the server
   * @param section Specific section of information to return
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient infoSection(String section, Handler<AsyncResult<Map<String, Object>>> handler) {
    this.delegate.infoSection(section, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> event) {
        AsyncResult<Map<String, Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<Map<String, Object>>result((Map<String, Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Map<String, Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Find all keys matching the given pattern
   * @param pattern Pattern to limit the keys returned
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient keys(String pattern, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.keys(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @param handler 
   * @return 
   */
  public RedisClient lastsave(Handler<AsyncResult<Long>> handler) {
    this.delegate.lastsave(handler);
    return this;
  }
  /**
   * Get an element from a list by its index
   * @param key Key string
   * @param index Index of list element to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    this.delegate.lindex(key, index, handler);
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
  public RedisClient linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.linsert(key, option, pivot, value, handler);
    return this;
  }
  /**
   * Get the length of a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient llen(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.llen(key, handler);
    return this;
  }
  /**
   * Remove and get the first element in a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lpop(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.lpop(key, handler);
    return this;
  }
  /**
   * Prepend one or multiple values to a list
   * @param key Key string
   * @param values Values to be added at the beginning of the list, one by one
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    this.delegate.lpushMany(key, values, handler);
    return this;
  }
  /**
   * Prepend one value to a list
   * @param key Key string
   * @param value Value to be added at the beginning of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.lpush(key, value, handler);
    return this;
  }
  /**
   * Prepend a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to add at the beginning of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.lpushx(key, value, handler);
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
  public RedisClient lrange(String key, long from, long to, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.lrange(key, from, to, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient lrem(String key, long count, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.lrem(key, count, value, handler);
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
  public RedisClient lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
    this.delegate.lset(key, index, value, handler);
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
  public RedisClient ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    this.delegate.ltrim(key, from, to, handler);
    return this;
  }
  /**
   * Get the value of the given key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient mget(String key, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.mget(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient mgetMany(List<String> keys, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.mgetMany(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient migrate(String host, int port, String key, int destdb, long timeout, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    this.delegate.migrate(host, port, key, destdb, timeout, options != null ? new io.vertx.redis.op.MigrateOptions(new io.vertx.core.json.JsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Listen for all requests received by the server in real time
   * @param handler 
   * @return 
   */
  public RedisClient monitor(Handler<AsyncResult<Void>> handler) {
    this.delegate.monitor(handler);
    return this;
  }
  /**
   * Move a key to another database
   * @param key Key to migrate
   * @param destdb Destination database index
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient move(String key, int destdb, Handler<AsyncResult<Long>> handler) {
    this.delegate.move(key, destdb, handler);
    return this;
  }
  /**
   * Set multiple keys to multiple values
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient mset(Map<String, Object> keyvals, Handler<AsyncResult<String>> handler) {
    this.delegate.mset(keyvals != null ? new io.vertx.core.json.JsonObject(keyvals) : null, handler);
    return this;
  }
  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient msetnx(Map<String, Object> keyvals, Handler<AsyncResult<Long>> handler) {
    this.delegate.msetnx(keyvals != null ? new io.vertx.core.json.JsonObject(keyvals) : null, handler);
    return this;
  }
  /**
   * Mark the start of a transaction block
   * @param handler 
   * @return 
   */
  public RedisClient multi(Handler<AsyncResult<String>> handler) {
    this.delegate.multi(handler);
    return this;
  }
  /**
   * Inspect the internals of Redis objects
   * @param key Key string
   * @param cmd Object sub command
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    this.delegate.object(key, cmd, handler);
    return this;
  }
  /**
   * Remove the expiration from a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient persist(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.persist(key, handler);
    return this;
  }
  /**
   * Set a key's time to live in milliseconds
   * @param key String key
   * @param millis Time to live in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pexpire(String key, long millis, Handler<AsyncResult<Long>> handler) {
    this.delegate.pexpire(key, millis, handler);
    return this;
  }
  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @param key Key string
   * @param millis Expiry time as Unix timestamp in milliseconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pexpireat(String key, long millis, Handler<AsyncResult<Long>> handler) {
    this.delegate.pexpireat(key, millis, handler);
    return this;
  }
  /**
   * Adds the specified element to the specified HyperLogLog.
   * @param key Key string
   * @param element Element to add
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfadd(String key, String element, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfadd(key, element, handler);
    return this;
  }
  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @param key Key string
   * @param elements Elementa to add
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfaddMany(String key, List<String> elements, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfaddMany(key, elements, handler);
    return this;
  }
  /**
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfcount(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfcount(key, handler);
    return this;
  }
  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfcountMany(List<String> keys, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfcountMany(keys, handler);
    return this;
  }
  /**
   * Merge N different HyperLogLogs into a single one.
   * @param destkey Destination key
   * @param keys List of source keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    this.delegate.pfmerge(destkey, keys, handler);
    return this;
  }
  /**
   * Ping the server
   * @param handler 
   * @return 
   */
  public RedisClient ping(Handler<AsyncResult<String>> handler) {
    this.delegate.ping(handler);
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
  public RedisClient psetex(String key, long millis, String value, Handler<AsyncResult<Void>> handler) {
    this.delegate.psetex(key, millis, value, handler);
    return this;
  }
  /**
   * Listen for messages published to channels matching the given pattern
   * @param pattern Pattern string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient psubscribe(String pattern, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.psubscribe(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient psubscribeMany(List<String> patterns, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.psubscribeMany(patterns, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Lists the currently active channels - only those matching the pattern
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubChannels(String pattern, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.pubsubChannels(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubNumsub(List<String> channels, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.pubsubNumsub(channels, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubNumpat(Handler<AsyncResult<Long>> handler) {
    this.delegate.pubsubNumpat(handler);
    return this;
  }
  /**
   * Get the time to live for a key in milliseconds
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pttl(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.pttl(key, handler);
    return this;
  }
  /**
   * Post a message to a channel
   * @param channel Channel key
   * @param message Message to send to channel
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient publish(String channel, String message, Handler<AsyncResult<Long>> handler) {
    this.delegate.publish(channel, message, handler);
    return this;
  }
  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient punsubscribe(List<String> patterns, Handler<AsyncResult<Void>> handler) {
    this.delegate.punsubscribe(patterns, handler);
    return this;
  }
  /**
   * Return a random key from the keyspace
   * @param handler 
   * @return 
   */
  public RedisClient randomkey(Handler<AsyncResult<String>> handler) {
    this.delegate.randomkey(handler);
    return this;
  }
  /**
   * Rename a key
   * @param key Key string to be renamed
   * @param newkey New key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    this.delegate.rename(key, newkey, handler);
    return this;
  }
  /**
   * Rename a key, only if the new key does not exist
   * @param key Key string to be renamed
   * @param newkey New key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    this.delegate.renamenx(key, newkey, handler);
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
  public RedisClient restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
    this.delegate.restore(key, millis, serialized, handler);
    return this;
  }
  /**
   * Return the role of the instance in the context of replication
   * @param handler 
   * @return 
   */
  public RedisClient role(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.role(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove and get the last element in a list
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpop(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.rpop(key, handler);
    return this;
  }
  /**
   * Remove the last element in a list, append it to another list and return it
   * @param key Key string identifying source list
   * @param destkey Key string identifying destination list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    this.delegate.rpoplpush(key, destkey, handler);
    return this;
  }
  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param values List of values to add to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    this.delegate.rpushMany(key, values, handler);
    return this;
  }
  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.rpush(key, value, handler);
    return this;
  }
  /**
   * Append a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.rpushx(key, value, handler);
    return this;
  }
  /**
   * Add a member to a set
   * @param key Key string
   * @param member Value to be added to the set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sadd(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.sadd(key, member, handler);
    return this;
  }
  /**
   * Add one or more members to a set
   * @param key Key string
   * @param members Values to be added to the set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient saddMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    this.delegate.saddMany(key, members, handler);
    return this;
  }
  /**
   * Synchronously save the dataset to disk
   * @param handler 
   * @return 
   */
  public RedisClient save(Handler<AsyncResult<String>> handler) {
    this.delegate.save(handler);
    return this;
  }
  /**
   * Get the number of members in a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scard(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.scard(key, handler);
    return this;
  }
  /**
   * Check existence of script in the script cache.
   * @param script SHA1 digest identifying a script in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scriptExists(String script, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.scriptExists(script, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scriptExistsMany(List<String> scripts, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.scriptExistsMany(scripts, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove all the scripts from the script cache.
   * @param handler 
   * @return 
   */
  public RedisClient scriptFlush(Handler<AsyncResult<String>> handler) {
    this.delegate.scriptFlush(handler);
    return this;
  }
  /**
   * Kill the script currently in execution.
   * @param handler 
   * @return 
   */
  public RedisClient scriptKill(Handler<AsyncResult<String>> handler) {
    this.delegate.scriptKill(handler);
    return this;
  }
  /**
   * Load the specified Lua script into the script cache.
   * @param script Lua script
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scriptLoad(String script, Handler<AsyncResult<String>> handler) {
    this.delegate.scriptLoad(script, handler);
    return this;
  }
  /**
   * Subtract multiple sets
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sdiff(key, cmpkeys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<Long>> handler) {
    this.delegate.sdiffstore(destkey, key, cmpkeys, handler);
    return this;
  }
  /**
   * Change the selected database for the current connection
   * @param dbindex Index identifying the new active database
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient select(int dbindex, Handler<AsyncResult<String>> handler) {
    this.delegate.select(dbindex, handler);
    return this;
  }
  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient set(String key, String value, Handler<AsyncResult<Void>> handler) {
    this.delegate.set(key, value, handler);
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
  public RedisClient setWithOptions(String key, String value, Map<String, Object> options, Handler<AsyncResult<String>> handler) {
    this.delegate.setWithOptions(key, value, options != null ? new io.vertx.redis.op.SetOptions(new io.vertx.core.json.JsonObject(options)) : null, handler);
    return this;
  }
  /**
   * Set the binary string value of a key - without encoding as utf-8
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient setBinary(String key, Buffer value, Handler<AsyncResult<Void>> handler) {
    this.delegate.setBinary(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), handler);
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
  public RedisClient setBinaryWithOptions(String key, Buffer value, Map<String, Object> options, Handler<AsyncResult<Void>> handler) {
    this.delegate.setBinaryWithOptions(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), options != null ? new io.vertx.redis.op.SetOptions(new io.vertx.core.json.JsonObject(options)) : null, handler);
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
  public RedisClient setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) {
    this.delegate.setbit(key, offset, bit, handler);
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
  public RedisClient setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    this.delegate.setex(key, seconds, value, handler);
    return this;
  }
  /**
   * Set the value of a key, only if the key does not exist
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.setnx(key, value, handler);
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
  public RedisClient setrange(String key, int offset, String value, Handler<AsyncResult<Long>> handler) {
    this.delegate.setrange(key, offset, value, handler);
    return this;
  }
  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sinter(List<String> keys, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sinter(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Intersect multiple sets and store the resulting set in a key
   * @param destkey Key where to store the results
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sinterstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    this.delegate.sinterstore(destkey, keys, handler);
    return this;
  }
  /**
   * Determine if a given value is a member of a set
   * @param key Key string
   * @param member Member to look for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sismember(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.sismember(key, member, handler);
    return this;
  }
  /**
   * Make the server a slave of another instance
   * @param host Host to become this server's master
   * @param port Port of our new master
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
    this.delegate.slaveof(host, port, handler);
    return this;
  }
  /**
   * Make this server a master
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slaveofNoone(Handler<AsyncResult<String>> handler) {
    this.delegate.slaveofNoone(handler);
    return this;
  }
  /**
   * Read the Redis slow queries log
   * @param limit Number of log entries to return. If value is less than zero all entries are returned
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slowlogGet(int limit, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.slowlogGet(limit, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the length of the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slowlogLen(Handler<AsyncResult<Long>> handler) {
    this.delegate.slowlogLen(handler);
    return this;
  }
  /**
   * Reset the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slowlogReset(Handler<AsyncResult<Void>> handler) {
    this.delegate.slowlogReset(handler);
    return this;
  }
  /**
   * Get all the members in a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient smembers(String key, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.smembers(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient smove(String key, String destkey, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.smove(key, destkey, member, handler);
    return this;
  }
  /**
   * Sort the elements in a list, set or sorted set
   * @param key Key string
   * @param options Sort options (see <a href="../../../../../../cheatsheet/SortOptions.html">SortOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sort(String key, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sort(key, options != null ? new io.vertx.redis.op.SortOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove and return a random member from a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient spop(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.spop(key, handler);
    return this;
  }
  /**
   * Remove and return random members from a set
   * @param key Key string
   * @param count Number of members to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
    this.delegate.spopMany(key, count, handler);
    return this;
  }
  /**
   * Get one or multiple random members from a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient srandmember(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.srandmember(key, handler);
    return this;
  }
  /**
   * Get one or multiple random members from a set
   * @param key Key string
   * @param count Number of members to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient srandmemberCount(String key, int count, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.srandmemberCount(key, count, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Remove one member from a set
   * @param key Key string
   * @param member Member to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient srem(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.srem(key, member, handler);
    return this;
  }
  /**
   * Remove one or more members from a set
   * @param key Key string
   * @param members Members to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    this.delegate.sremMany(key, members, handler);
    return this;
  }
  /**
   * Get the length of the value stored in a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient strlen(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.strlen(key, handler);
    return this;
  }
  /**
   * Listen for messages published to the given channels
   * @param channel Channel to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient subscribe(String channel, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.subscribe(channel, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient subscribeMany(List<String> channels, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.subscribeMany(channels, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sunion(List<String> keys, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sunion(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Add multiple sets and store the resulting set in a key
   * @param destkey Destination key
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sunionstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    this.delegate.sunionstore(destkey, keys, handler);
    return this;
  }
  /**
   * Internal command used for replication
   * @param handler 
   * @return 
   */
  public RedisClient sync(Handler<AsyncResult<Void>> handler) {
    this.delegate.sync(handler);
    return this;
  }
  /**
   * Return the current server time
   * @param handler 
   * @return 
   */
  public RedisClient time(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.time(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Get the time to live for a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient ttl(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.ttl(key, handler);
    return this;
  }
  /**
   * Determine the type stored at key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient type(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.type(key, handler);
    return this;
  }
  /**
   * Stop listening for messages posted to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient unsubscribe(List<String> channels, Handler<AsyncResult<Void>> handler) {
    this.delegate.unsubscribe(channels, handler);
    return this;
  }
  /**
   * Forget about all watched keys
   * @param handler 
   * @return 
   */
  public RedisClient unwatch(Handler<AsyncResult<String>> handler) {
    this.delegate.unwatch(handler);
    return this;
  }
  /**
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection.
   * @param numSlaves 
   * @param timeout 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) {
    this.delegate.wait(numSlaves, timeout, handler);
    return this;
  }
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param key Key to watch
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient watch(String key, Handler<AsyncResult<String>> handler) {
    this.delegate.watch(key, handler);
    return this;
  }
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param keys List of keys to watch
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient watchMany(List<String> keys, Handler<AsyncResult<String>> handler) {
    this.delegate.watchMany(keys, handler);
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
  public RedisClient zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.zadd(key, score, member, handler);
    return this;
  }
  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param members New member keys and their scores
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zaddMany(String key, Map<String,Double> members, Handler<AsyncResult<Long>> handler) {
    this.delegate.zaddMany(key, members, handler);
    return this;
  }
  /**
   * Get the number of members in a sorted set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zcard(String key, Handler<AsyncResult<Long>> handler) {
    this.delegate.zcard(key, handler);
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
  public RedisClient zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) {
    this.delegate.zcount(key, min, max, handler);
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
  public RedisClient zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    this.delegate.zincrby(key, increment, member, handler);
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
  public RedisClient zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    this.delegate.zinterstore(destkey, sets, options, handler);
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
  public RedisClient zinterstoreWeighed(String destkey, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    this.delegate.zinterstoreWeighed(destkey, sets, options, handler);
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
  public RedisClient zlexcount(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    this.delegate.zlexcount(key, min, max, handler);
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
  public RedisClient zrange(String key, long start, long stop, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrange(key, start, stop, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrangeWithOptions(key, start, stop, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zrangebylex(String key, String min, String max, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrangebylex(key, min, max, options != null ? new io.vertx.redis.op.LimitOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zrangebyscore(String key, String min, String max, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrangebyscore(key, min, max, options != null ? new io.vertx.redis.op.RangeLimitOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Determine the index of a member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrank(key, member, handler);
    return this;
  }
  /**
   * Remove one member from a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zrem(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrem(key, member, handler);
    return this;
  }
  /**
   * Remove one or more members from a sorted set
   * @param key Key string
   * @param members Members in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremMany(key, members, handler);
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
  public RedisClient zremrangebylex(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebylex(key, min, max, handler);
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
  public RedisClient zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebyrank(key, start, stop, handler);
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
  public RedisClient zremrangebyscore(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebyscore(key, min, max, handler);
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
  public RedisClient zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrevrange(key, start, stop, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zrevrangebylex(String key, String max, String min, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrevrangebylex(key, max, min, options != null ? new io.vertx.redis.op.LimitOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zrevrangebyscore(String key, String max, String min, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrevrangebyscore(key, max, min, options != null ? new io.vertx.redis.op.RangeLimitOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zrevrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrevrank(key, member, handler);
    return this;
  }
  /**
   * Get the score associated with the given member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zscore(String key, String member, Handler<AsyncResult<String>> handler) {
    this.delegate.zscore(key, member, handler);
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
  public RedisClient zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    this.delegate.zunionstore(destkey, sets, options, handler);
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
  public RedisClient zunionstoreWeighed(String key, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    this.delegate.zunionstoreWeighed(key, sets, options, handler);
    return this;
  }
  /**
   * Incrementally iterate the keys space
   * @param cursor Cursor id
   * @param options Scan options (see <a href="../../../../../../cheatsheet/ScanOptions.html">ScanOptions</a>)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scan(String cursor, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.scan(cursor, options != null ? new io.vertx.redis.op.ScanOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient sscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient hscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
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
  public RedisClient zscan(String key, String cursor, Map<String, Object> options, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zscan(key, cursor, options != null ? new io.vertx.redis.op.ScanOptions(new io.vertx.core.json.JsonObject(options)) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result((List<Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
    return this;
  }
}
