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
package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.redis.impl.RedisAPIImpl;

/**
 * <b>Auto generated</b> fluent API to interact with REDIS.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@VertxGen
public interface RedisAPI extends Redis {

  /**
   * Creates an instance of the Client.
   *
   * @param vertx vertx instance
   * @return a instance of the connector.
   */
  static RedisAPI create(Vertx vertx) {
    return new RedisAPIImpl(vertx);
  }

  /**
   * Append a value to a key
   * 
    * <b>Time complexity</b>: <i>O(1). The amortized time complexity is O(1) assuming the appended value is small and the already present value is of any size, since the dynamic string library used by Redis will double the free space available on every reallocation.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: string
   */
  @Fluent
  default RedisAPI append(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("APPEND", arguments, handler);
    return this;
  }

  /**
   * Authenticate to the server
   * 
   * <ul>
   *   <li>password - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI auth(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("AUTH", arguments, handler);
    return this;
  }

  /**
   * Asynchronously rewrite the append-only file
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI bgrewriteaof(Handler<AsyncResult<Reply>> handler) {
    send("BGREWRITEAOF", handler);
    return this;
  }

  /**
   * Asynchronously save the dataset to disk
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI bgsave(Handler<AsyncResult<Reply>> handler) {
    send("BGSAVE", handler);
    return this;
  }

  /**
   * Count set bits in a string
   * 
    * <b>Time complexity</b>: <i>O(N)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start,end <i>(optional)</i> - integer,integer</li>
   * </ul>
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  default RedisAPI bitcount(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BITCOUNT", arguments, handler);
    return this;
  }

  /**
   * Perform arbitrary bitfield integer operations on strings
   * 
    * <b>Time complexity</b>: <i>O(1) for each subcommand specified</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li><b>GET</b> type,offset <i>(optional)</i> - type,integer</li>
   *   <li><b>SET</b> type,offset,value <i>(optional)</i> - type,integer,integer</li>
   *   <li><b>INCRBY</b> type,offset,increment <i>(optional)</i> - type,integer,integer</li>
   *   <li><b>OVERFLOW</b>  <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 3.2.0
   * group: string
   */
  @Fluent
  default RedisAPI bitfield(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BITFIELD", arguments, handler);
    return this;
  }

  /**
   * Perform bitwise operations between strings
   * 
    * <b>Time complexity</b>: <i>O(N)</i>
   * 
   * <ul>
   *   <li>operation - string</li>
   *   <li>destkey - key</li>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  default RedisAPI bitop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BITOP", arguments, handler);
    return this;
  }

  /**
   * Find first bit set or clear in a string
   * 
    * <b>Time complexity</b>: <i>O(N)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>bit - integer</li>
   *   <li>start <i>(optional)</i> - integer</li>
   *   <li>end <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 2.8.7
   * group: string
   */
  @Fluent
  default RedisAPI bitpos(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BITPOS", arguments, handler);
    return this;
  }

  /**
   * Remove and get the first element in a list, or block until one is available
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  default RedisAPI blpop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BLPOP", arguments, handler);
    return this;
  }

  /**
   * Remove and get the last element in a list, or block until one is available
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 2.0.0
   * group: list
   */
  @Fluent
  default RedisAPI brpop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BRPOP", arguments, handler);
    return this;
  }

  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>source - key</li>
   *   <li>destination - key</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  default RedisAPI brpoplpush(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BRPOPLPUSH", arguments, handler);
    return this;
  }

  /**
   * Remove and return the member with the lowest score from one or more sorted sets, or block until one is available
   * 
    * <b>Time complexity</b>: <i>O(log(N)) with N being the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI bzpopmin(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BZPOPMIN", arguments, handler);
    return this;
  }

  /**
   * Remove and return the member with the highest score from one or more sorted sets, or block until one is available
   * 
    * <b>Time complexity</b>: <i>O(log(N)) with N being the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI bzpopmax(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("BZPOPMAX", arguments, handler);
    return this;
  }

  /**
   * Kill the connection of a client
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of client connections</i>
   * 
   * <ul>
   *   <li>ip:port <i>(optional)</i> - string</li>
   *   <li><b>ID</b> client-id <i>(optional)</i> - integer</li>
   *   <li><b>TYPE</b>  <i>(optional)</i> - enum</li>
   *   <li><b>ADDR</b> ip:port <i>(optional)</i> - string</li>
   *   <li><b>SKIPME</b> yes/no <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 2.4.0
   * group: server
   */
  @Fluent
  default RedisAPI clientKill(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLIENT KILL", arguments, handler);
    return this;
  }

  /**
   * Get the list of client connections
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of client connections</i>
   * 

   * 
   * @since Redis 2.4.0
   * group: server
   */
  @Fluent
  default RedisAPI clientList(Handler<AsyncResult<Reply>> handler) {
    send("CLIENT LIST", handler);
    return this;
  }

  /**
   * Get the current connection name
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.6.9
   * group: server
   */
  @Fluent
  default RedisAPI clientGetname(Handler<AsyncResult<Reply>> handler) {
    send("CLIENT GETNAME", handler);
    return this;
  }

  /**
   * Stop processing commands from clients for some time
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 2.9.50
   * group: server
   */
  @Fluent
  default RedisAPI clientPause(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLIENT PAUSE", arguments, handler);
    return this;
  }

  /**
   * Instruct the server whether to reply to commands
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>reply-mode - enum</li>
   * </ul>
   * @since Redis 3.2
   * group: server
   */
  @Fluent
  default RedisAPI clientReply(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLIENT REPLY", arguments, handler);
    return this;
  }

  /**
   * Set the current connection name
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>connection-name - string</li>
   * </ul>
   * @since Redis 2.6.9
   * group: server
   */
  @Fluent
  default RedisAPI clientSetname(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLIENT SETNAME", arguments, handler);
    return this;
  }

  /**
   * Assign new hash slots to receiving node
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of hash slot arguments</i>
   * 
   * <ul>
   *   <li>slot - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterAddslots(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER ADDSLOTS", arguments, handler);
    return this;
  }

  /**
   * Return the number of failure reports active for a given node
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of failure reports</i>
   * 
   * <ul>
   *   <li>node-id - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterCountFailureReports(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER COUNT-FAILURE-REPORTS", arguments, handler);
    return this;
  }

  /**
   * Return the number of local keys in the specified hash slot
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>slot - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterCountkeysinslot(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER COUNTKEYSINSLOT", arguments, handler);
    return this;
  }

  /**
   * Set hash slots as unbound in receiving node
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of hash slot arguments</i>
   * 
   * <ul>
   *   <li>slot - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterDelslots(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER DELSLOTS", arguments, handler);
    return this;
  }

  /**
   * Forces a slave to perform a manual failover of its master.
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>options <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterFailover(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER FAILOVER", arguments, handler);
    return this;
  }

  /**
   * Remove a node from the nodes table
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>node-id - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterForget(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER FORGET", arguments, handler);
    return this;
  }

  /**
   * Return local key names in the specified hash slot
   * 
    * <b>Time complexity</b>: <i>O(log(N)) where N is the number of requested keys</i>
   * 
   * <ul>
   *   <li>slot - integer</li>
   *   <li>count - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterGetkeysinslot(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER GETKEYSINSLOT", arguments, handler);
    return this;
  }

  /**
   * Provides info about Redis Cluster node state
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterInfo(Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER INFO", handler);
    return this;
  }

  /**
   * Returns the hash slot of the specified key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of bytes in the key</i>
   * 
   * <ul>
   *   <li>key - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterKeyslot(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER KEYSLOT", arguments, handler);
    return this;
  }

  /**
   * Force a node cluster to handshake with another node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>ip - string</li>
   *   <li>port - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterMeet(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER MEET", arguments, handler);
    return this;
  }

  /**
   * Get Cluster config for the node
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of Cluster nodes</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterNodes(Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER NODES", handler);
    return this;
  }

  /**
   * Reconfigure a node as a slave of the specified master node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>node-id - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterReplicate(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER REPLICATE", arguments, handler);
    return this;
  }

  /**
   * Reset a Redis Cluster node
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of known nodes. The command may execute a FLUSHALL as a side effect.</i>
   * 
   * <ul>
   *   <li>reset-type <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterReset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER RESET", arguments, handler);
    return this;
  }

  /**
   * Forces the node to save cluster state on disk
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterSaveconfig(Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER SAVECONFIG", handler);
    return this;
  }

  /**
   * Set the configuration epoch in a new node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>config-epoch - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterSetConfigEpoch(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER SET-CONFIG-EPOCH", arguments, handler);
    return this;
  }

  /**
   * Bind a hash slot to a specific node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>slot - integer</li>
   *   <li>subcommand - enum</li>
   *   <li>node-id <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterSetslot(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER SETSLOT", arguments, handler);
    return this;
  }

  /**
   * List slave nodes of the specified master node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>node-id - string</li>
   * </ul>
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterSlaves(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER SLAVES", arguments, handler);
    return this;
  }

  /**
   * Get array of Cluster slot to node mappings
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of Cluster nodes</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI clusterSlots(Handler<AsyncResult<Reply>> handler) {
    send("CLUSTER SLOTS", handler);
    return this;
  }

  /**
   * Get array of Redis command details
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of Redis commands</i>
   * 

   * 
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  default RedisAPI command(Handler<AsyncResult<Reply>> handler) {
    send("COMMAND", handler);
    return this;
  }

  /**
   * Get total number of Redis commands
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  default RedisAPI commandCount(Handler<AsyncResult<Reply>> handler) {
    send("COMMAND COUNT", handler);
    return this;
  }

  /**
   * Extract keys given a full Redis command
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of arguments to the command</i>
   * 

   * 
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  default RedisAPI commandGetkeys(Handler<AsyncResult<Reply>> handler) {
    send("COMMAND GETKEYS", handler);
    return this;
  }

  /**
   * Get array of specific Redis command details
   * 
    * <b>Time complexity</b>: <i>O(N) when N is number of commands to look up</i>
   * 
   * <ul>
   *   <li>command-name - string</li>
   * </ul>
   * @since Redis 2.8.13
   * group: server
   */
  @Fluent
  default RedisAPI commandInfo(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("COMMAND INFO", arguments, handler);
    return this;
  }

  /**
   * Get the value of a configuration parameter
   * 
   * <ul>
   *   <li>parameter - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  default RedisAPI configGet(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CONFIG GET", arguments, handler);
    return this;
  }

  /**
   * Rewrite the configuration file with the in memory configuration
   * 

   * 
   * @since Redis 2.8.0
   * group: server
   */
  @Fluent
  default RedisAPI configRewrite(Handler<AsyncResult<Reply>> handler) {
    send("CONFIG REWRITE", handler);
    return this;
  }

  /**
   * Set a configuration parameter to the given value
   * 
   * <ul>
   *   <li>parameter - string</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  default RedisAPI configSet(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("CONFIG SET", arguments, handler);
    return this;
  }

  /**
   * Reset the stats returned by INFO
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.0.0
   * group: server
   */
  @Fluent
  default RedisAPI configResetstat(Handler<AsyncResult<Reply>> handler) {
    send("CONFIG RESETSTAT", handler);
    return this;
  }

  /**
   * Return the number of keys in the selected database
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI dbsize(Handler<AsyncResult<Reply>> handler) {
    send("DBSIZE", handler);
    return this;
  }

  /**
   * Get debugging information about a key
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI debugObject(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("DEBUG OBJECT", arguments, handler);
    return this;
  }

  /**
   * Make the server crash
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI debugSegfault(Handler<AsyncResult<Reply>> handler) {
    send("DEBUG SEGFAULT", handler);
    return this;
  }

  /**
   * Decrement the integer value of a key by one
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI decr(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("DECR", arguments, handler);
    return this;
  }

  /**
   * Decrement the integer value of a key by the given number
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>decrement - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI decrby(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("DECRBY", arguments, handler);
    return this;
  }

  /**
   * Delete a key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of keys that will be removed. When a key to remove holds a value other than a string, the individual complexity for this key is O(M) where M is the number of elements in the list, set, sorted set or hash. Removing a single key that holds a string value is O(1).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI del(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("DEL", arguments, handler);
    return this;
  }

  /**
   * Discard all commands issued after MULTI
   * 

   * 
   * @since Redis 2.0.0
   * group: transactions
   */
  @Fluent
  default RedisAPI discard(Handler<AsyncResult<Reply>> handler) {
    send("DISCARD", handler);
    return this;
  }

  /**
   * Return a serialized version of the value stored at the specified key.
   * 
    * <b>Time complexity</b>: <i>O(1) to access the key and additional O(N*M) to serialized it, where N is the number of Redis objects composing the value and M their average size. For small string values the time complexity is thus O(1)+O(1*M) where M is small, so simply O(1).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI dump(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("DUMP", arguments, handler);
    return this;
  }

  /**
   * Echo the given string
   * 
   * <ul>
   *   <li>message - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI echo(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ECHO", arguments, handler);
    return this;
  }

  /**
   * Execute a Lua script server side
   * 
    * <b>Time complexity</b>: <i>Depends on the script that is executed.</i>
   * 
   * <ul>
   *   <li>script - string</li>
   *   <li>numkeys - integer</li>
   *   <li>key - key</li>
   *   <li>arg - string</li>
   * </ul>
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI eval(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("EVAL", arguments, handler);
    return this;
  }

  /**
   * Execute a Lua script server side
   * 
    * <b>Time complexity</b>: <i>Depends on the script that is executed.</i>
   * 
   * <ul>
   *   <li>sha1 - string</li>
   *   <li>numkeys - integer</li>
   *   <li>key - key</li>
   *   <li>arg - string</li>
   * </ul>
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI evalsha(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("EVALSHA", arguments, handler);
    return this;
  }

  /**
   * Execute all commands issued after MULTI
   * 

   * 
   * @since Redis 1.2.0
   * group: transactions
   */
  @Fluent
  default RedisAPI exec(Handler<AsyncResult<Reply>> handler) {
    send("EXEC", handler);
    return this;
  }

  /**
   * Determine if a key exists
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI exists(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("EXISTS", arguments, handler);
    return this;
  }

  /**
   * Set a key&#x27;s time to live in seconds
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>seconds - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI expire(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("EXPIRE", arguments, handler);
    return this;
  }

  /**
   * Set the expiration for a key as a UNIX timestamp
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>timestamp - posix time</li>
   * </ul>
   * @since Redis 1.2.0
   * group: generic
   */
  @Fluent
  default RedisAPI expireat(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("EXPIREAT", arguments, handler);
    return this;
  }

  /**
   * Remove all keys from all databases
   * 
   * <ul>
   *   <li>async <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI flushall(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("FLUSHALL", arguments, handler);
    return this;
  }

  /**
   * Remove all keys from the current database
   * 
   * <ul>
   *   <li>async <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI flushdb(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("FLUSHDB", arguments, handler);
    return this;
  }

  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set
   * 
    * <b>Time complexity</b>: <i>O(log(N)) for each item added, where N is the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>longitude,latitude,member - double,double,string</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI geoadd(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEOADD", arguments, handler);
    return this;
  }

  /**
   * Returns members of a geospatial index as standard geohash strings
   * 
    * <b>Time complexity</b>: <i>O(log(N)) for each member requested, where N is the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI geohash(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEOHASH", arguments, handler);
    return this;
  }

  /**
   * Returns longitude and latitude of members of a geospatial index
   * 
    * <b>Time complexity</b>: <i>O(log(N)) for each member requested, where N is the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI geopos(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEOPOS", arguments, handler);
    return this;
  }

  /**
   * Returns the distance between two members of a geospatial index
   * 
    * <b>Time complexity</b>: <i>O(log(N))</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member1 - string</li>
   *   <li>member2 - string</li>
   *   <li>unit <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI geodist(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEODIST", arguments, handler);
    return this;
  }

  /**
   * Query a sorted set representing a geospatial index to fetch members matching a given maximum distance from a point
   * 
    * <b>Time complexity</b>: <i>O(N+log(M)) where N is the number of elements inside the bounding box of the circular area delimited by center and radius and M is the number of items inside the index.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>longitude - double</li>
   *   <li>latitude - double</li>
   *   <li>radius - double</li>
   *   <li>unit - enum</li>
   *   <li>withcoord <i>(optional)</i> - enum</li>
   *   <li>withdist <i>(optional)</i> - enum</li>
   *   <li>withhash <i>(optional)</i> - enum</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   *   <li>order <i>(optional)</i> - enum</li>
   *   <li><b>STORE</b> key <i>(optional)</i> - key</li>
   *   <li><b>STOREDIST</b> key <i>(optional)</i> - key</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI georadius(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEORADIUS", arguments, handler);
    return this;
  }

  /**
   * Query a sorted set representing a geospatial index to fetch members matching a given maximum distance from a member
   * 
    * <b>Time complexity</b>: <i>O(N+log(M)) where N is the number of elements inside the bounding box of the circular area delimited by center and radius and M is the number of items inside the index.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   *   <li>radius - double</li>
   *   <li>unit - enum</li>
   *   <li>withcoord <i>(optional)</i> - enum</li>
   *   <li>withdist <i>(optional)</i> - enum</li>
   *   <li>withhash <i>(optional)</i> - enum</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   *   <li>order <i>(optional)</i> - enum</li>
   *   <li><b>STORE</b> key <i>(optional)</i> - key</li>
   *   <li><b>STOREDIST</b> key <i>(optional)</i> - key</li>
   * </ul>
   * @since Redis 3.2.0
   * group: geo
   */
  @Fluent
  default RedisAPI georadiusbymember(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GEORADIUSBYMEMBER", arguments, handler);
    return this;
  }

  /**
   * Get the value of a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI get(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GET", arguments, handler);
    return this;
  }

  /**
   * Returns the bit value at offset in the string value stored at key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>offset - integer</li>
   * </ul>
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  default RedisAPI getbit(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GETBIT", arguments, handler);
    return this;
  }

  /**
   * Get a substring of the string stored at a key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the length of the returned string. The complexity is ultimately determined by the returned length, but because creating a substring from an existing string is very cheap, it can be considered O(1) for small strings.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>end - integer</li>
   * </ul>
   * @since Redis 2.4.0
   * group: string
   */
  @Fluent
  default RedisAPI getrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GETRANGE", arguments, handler);
    return this;
  }

  /**
   * Set the string value of a key and return its old value
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI getset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("GETSET", arguments, handler);
    return this;
  }

  /**
   * Delete one or more hash fields
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of fields to be removed.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hdel(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HDEL", arguments, handler);
    return this;
  }

  /**
   * Determine if a hash field exists
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hexists(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HEXISTS", arguments, handler);
    return this;
  }

  /**
   * Get the value of a hash field
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hget(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HGET", arguments, handler);
    return this;
  }

  /**
   * Get all the fields and values in a hash
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the size of the hash.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hgetall(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HGETALL", arguments, handler);
    return this;
  }

  /**
   * Increment the integer value of a hash field by the given number
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   *   <li>increment - integer</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hincrby(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HINCRBY", arguments, handler);
    return this;
  }

  /**
   * Increment the float value of a hash field by the given amount
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   *   <li>increment - double</li>
   * </ul>
   * @since Redis 2.6.0
   * group: hash
   */
  @Fluent
  default RedisAPI hincrbyfloat(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HINCRBYFLOAT", arguments, handler);
    return this;
  }

  /**
   * Get all the fields in a hash
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the size of the hash.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hkeys(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HKEYS", arguments, handler);
    return this;
  }

  /**
   * Get the number of fields in a hash
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hlen(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HLEN", arguments, handler);
    return this;
  }

  /**
   * Get the values of all the given hash fields
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of fields being requested.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hmget(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HMGET", arguments, handler);
    return this;
  }

  /**
   * Set multiple hash fields to multiple values
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of fields being set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field,value - string,string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hmset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HMSET", arguments, handler);
    return this;
  }

  /**
   * Set the string value of a hash field
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HSET", arguments, handler);
    return this;
  }

  /**
   * Set the value of a hash field, only if the field does not exist
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hsetnx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HSETNX", arguments, handler);
    return this;
  }

  /**
   * Get the length of the value of a hash field
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>field - string</li>
   * </ul>
   * @since Redis 3.2.0
   * group: hash
   */
  @Fluent
  default RedisAPI hstrlen(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HSTRLEN", arguments, handler);
    return this;
  }

  /**
   * Get all the values in a hash
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the size of the hash.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.0.0
   * group: hash
   */
  @Fluent
  default RedisAPI hvals(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HVALS", arguments, handler);
    return this;
  }

  /**
   * Increment the integer value of a key by one
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI incr(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("INCR", arguments, handler);
    return this;
  }

  /**
   * Increment the integer value of a key by the given amount
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>increment - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI incrby(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("INCRBY", arguments, handler);
    return this;
  }

  /**
   * Increment the float value of a key by the given amount
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>increment - double</li>
   * </ul>
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  default RedisAPI incrbyfloat(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("INCRBYFLOAT", arguments, handler);
    return this;
  }

  /**
   * Get information and statistics about the server
   * 
   * <ul>
   *   <li>section <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI info(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("INFO", arguments, handler);
    return this;
  }

  /**
   * Find all keys matching the given pattern
   * 
    * <b>Time complexity</b>: <i>O(N) with N being the number of keys in the database, under the assumption that the key names in the database and the given pattern have limited length.</i>
   * 
   * <ul>
   *   <li>pattern - pattern</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI keys(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("KEYS", arguments, handler);
    return this;
  }

  /**
   * Get the UNIX time stamp of the last successful save to disk
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI lastsave(Handler<AsyncResult<Reply>> handler) {
    send("LASTSAVE", handler);
    return this;
  }

  /**
   * Get an element from a list by its index
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of elements to traverse to get to the element at index. This makes asking for the first or the last element of the list O(1).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>index - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lindex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LINDEX", arguments, handler);
    return this;
  }

  /**
   * Insert an element before or after another element in a list
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of elements to traverse before seeing the value pivot. This means that inserting somewhere on the left end on the list (head) can be considered O(1) and inserting somewhere on the right end (tail) is O(N).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>where - enum</li>
   *   <li>pivot - string</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  default RedisAPI linsert(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LINSERT", arguments, handler);
    return this;
  }

  /**
   * Get the length of a list
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI llen(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LLEN", arguments, handler);
    return this;
  }

  /**
   * Remove and get the first element in a list
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lpop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LPOP", arguments, handler);
    return this;
  }

  /**
   * Prepend one or multiple values to a list
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lpush(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LPUSH", arguments, handler);
    return this;
  }

  /**
   * Prepend a value to a list, only if the list exists
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  default RedisAPI lpushx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LPUSHX", arguments, handler);
    return this;
  }

  /**
   * Get a range of elements from a list
   * 
    * <b>Time complexity</b>: <i>O(S+N) where S is the distance of start offset from HEAD for small lists, from nearest end (HEAD or TAIL) for large lists; and N is the number of elements in the specified range.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>stop - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LRANGE", arguments, handler);
    return this;
  }

  /**
   * Remove elements from a list
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the length of the list.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>count - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lrem(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LREM", arguments, handler);
    return this;
  }

  /**
   * Set the value of an element in a list by its index
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the length of the list. Setting either the first or the last element of the list is O(1).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>index - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI lset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LSET", arguments, handler);
    return this;
  }

  /**
   * Trim a list to the specified range
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of elements to be removed by the operation.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>stop - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI ltrim(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("LTRIM", arguments, handler);
    return this;
  }

  /**
   * Outputs memory problems report
   * 

   * 
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryDoctor(Handler<AsyncResult<Reply>> handler) {
    send("MEMORY DOCTOR", handler);
    return this;
  }

  /**
   * Show helpful text about the different subcommands
   * 

   * 
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryHelp(Handler<AsyncResult<Reply>> handler) {
    send("MEMORY HELP", handler);
    return this;
  }

  /**
   * Show allocator internal stats
   * 

   * 
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryMallocStats(Handler<AsyncResult<Reply>> handler) {
    send("MEMORY MALLOC-STATS", handler);
    return this;
  }

  /**
   * Ask the allocator to release memory
   * 

   * 
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryPurge(Handler<AsyncResult<Reply>> handler) {
    send("MEMORY PURGE", handler);
    return this;
  }

  /**
   * Show memory usage details
   * 

   * 
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryStats(Handler<AsyncResult<Reply>> handler) {
    send("MEMORY STATS", handler);
    return this;
  }

  /**
   * Estimate the memory usage of a key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of samples.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li><b>SAMPLES</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 4.0.0
   * group: server
   */
  @Fluent
  default RedisAPI memoryUsage(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MEMORY USAGE", arguments, handler);
    return this;
  }

  /**
   * Get the values of all the given keys
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of keys to retrieve.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI mget(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MGET", arguments, handler);
    return this;
  }

  /**
   * Atomically transfer a key from a Redis instance to another one.
   * 
    * <b>Time complexity</b>: <i>This command actually executes a DUMP+DEL in the source instance, and a RESTORE in the target instance. See the pages of these commands for time complexity. Also an O(N) data transfer between the two instances is performed.</i>
   * 
   * <ul>
   *   <li>host - string</li>
   *   <li>port - string</li>
   *   <li>key - enum</li>
   *   <li>destination-db - integer</li>
   *   <li>timeout - integer</li>
   *   <li>copy <i>(optional)</i> - enum</li>
   *   <li>replace <i>(optional)</i> - enum</li>
   *   <li><b>KEYS</b> key <i>(optional)</i> - key</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI migrate(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MIGRATE", arguments, handler);
    return this;
  }

  /**
   * Listen for all requests received by the server in real time
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI monitor(Handler<AsyncResult<Reply>> handler) {
    send("MONITOR", handler);
    return this;
  }

  /**
   * Move a key to another database
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>db - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI move(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MOVE", arguments, handler);
    return this;
  }

  /**
   * Set multiple keys to multiple values
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of keys to set.</i>
   * 
   * <ul>
   *   <li>key,value - key,string</li>
   * </ul>
   * @since Redis 1.0.1
   * group: string
   */
  @Fluent
  default RedisAPI mset(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MSET", arguments, handler);
    return this;
  }

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of keys to set.</i>
   * 
   * <ul>
   *   <li>key,value - key,string</li>
   * </ul>
   * @since Redis 1.0.1
   * group: string
   */
  @Fluent
  default RedisAPI msetnx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("MSETNX", arguments, handler);
    return this;
  }

  /**
   * Mark the start of a transaction block
   * 

   * 
   * @since Redis 1.2.0
   * group: transactions
   */
  @Fluent
  default RedisAPI multi(Handler<AsyncResult<Reply>> handler) {
    send("MULTI", handler);
    return this;
  }

  /**
   * Inspect the internals of Redis objects
   * 
    * <b>Time complexity</b>: <i>O(1) for all the currently implemented subcommands.</i>
   * 
   * <ul>
   *   <li>subcommand - string</li>
   *   <li>arguments <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 2.2.3
   * group: generic
   */
  @Fluent
  default RedisAPI object(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("OBJECT", arguments, handler);
    return this;
  }

  /**
   * Remove the expiration from a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.2.0
   * group: generic
   */
  @Fluent
  default RedisAPI persist(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PERSIST", arguments, handler);
    return this;
  }

  /**
   * Set a key&#x27;s time to live in milliseconds
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>milliseconds - integer</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI pexpire(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PEXPIRE", arguments, handler);
    return this;
  }

  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>milliseconds-timestamp - posix time</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI pexpireat(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PEXPIREAT", arguments, handler);
    return this;
  }

  /**
   * Adds the specified elements to the specified HyperLogLog.
   * 
    * <b>Time complexity</b>: <i>O(1) to add every element.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>element - string</li>
   * </ul>
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  default RedisAPI pfadd(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PFADD", arguments, handler);
    return this;
  }

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * 
    * <b>Time complexity</b>: <i>O(1) with a very small average constant time when called with a single key. O(N) with N being the number of keys, and much bigger constant times, when called with multiple keys.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  default RedisAPI pfcount(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PFCOUNT", arguments, handler);
    return this;
  }

  /**
   * Merge N different HyperLogLogs into a single one.
   * 
    * <b>Time complexity</b>: <i>O(N) to merge N HyperLogLogs, but with high constant times.</i>
   * 
   * <ul>
   *   <li>destkey - key</li>
   *   <li>sourcekey - key</li>
   * </ul>
   * @since Redis 2.8.9
   * group: hyperloglog
   */
  @Fluent
  default RedisAPI pfmerge(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PFMERGE", arguments, handler);
    return this;
  }

  /**
   * Ping the server
   * 
   * <ul>
   *   <li>message <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI ping(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PING", arguments, handler);
    return this;
  }

  /**
   * Set the value and expiration in milliseconds of a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>milliseconds - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.6.0
   * group: string
   */
  @Fluent
  default RedisAPI psetex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PSETEX", arguments, handler);
    return this;
  }

  /**
   * Listen for messages published to channels matching the given patterns
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of patterns the client is already subscribed to.</i>
   * 
   * <ul>
   *   <li>pattern - pattern</li>
   * </ul>
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI psubscribe(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PSUBSCRIBE", arguments, handler);
    return this;
  }

  /**
   * Inspect the state of the Pub/Sub subsystem
   * 
    * <b>Time complexity</b>: <i>O(N) for the CHANNELS subcommand, where N is the number of active channels, and assuming constant time pattern matching (relatively short channels and patterns). O(N) for the NUMSUB subcommand, where N is the number of requested channels. O(1) for the NUMPAT subcommand.</i>
   * 
   * <ul>
   *   <li>subcommand - string</li>
   *   <li>argument <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 2.8.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI pubsub(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PUBSUB", arguments, handler);
    return this;
  }

  /**
   * Get the time to live for a key in milliseconds
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI pttl(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PTTL", arguments, handler);
    return this;
  }

  /**
   * Post a message to a channel
   * 
    * <b>Time complexity</b>: <i>O(N+M) where N is the number of clients subscribed to the receiving channel and M is the total number of subscribed patterns (by any client).</i>
   * 
   * <ul>
   *   <li>channel - string</li>
   *   <li>message - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI publish(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PUBLISH", arguments, handler);
    return this;
  }

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * 
    * <b>Time complexity</b>: <i>O(N+M) where N is the number of patterns the client is already subscribed and M is the number of total patterns subscribed in the system (by any client).</i>
   * 
   * <ul>
   *   <li>pattern <i>(optional)</i> - pattern</li>
   * </ul>
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI punsubscribe(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("PUNSUBSCRIBE", arguments, handler);
    return this;
  }

  /**
   * Close the connection
   * 

   * 
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI quit(Handler<AsyncResult<Reply>> handler) {
    send("QUIT", handler);
    return this;
  }

  /**
   * Return a random key from the keyspace
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI randomkey(Handler<AsyncResult<Reply>> handler) {
    send("RANDOMKEY", handler);
    return this;
  }

  /**
   * Enables read queries for a connection to a cluster slave node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI readonly(Handler<AsyncResult<Reply>> handler) {
    send("READONLY", handler);
    return this;
  }

  /**
   * Disables read queries for a connection to a cluster slave node
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 3.0.0
   * group: cluster
   */
  @Fluent
  default RedisAPI readwrite(Handler<AsyncResult<Reply>> handler) {
    send("READWRITE", handler);
    return this;
  }

  /**
   * Rename a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>newkey - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI rename(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RENAME", arguments, handler);
    return this;
  }

  /**
   * Rename a key, only if the new key does not exist
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>newkey - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI renamenx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RENAMENX", arguments, handler);
    return this;
  }

  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   * 
    * <b>Time complexity</b>: <i>O(1) to create the new key and additional O(N*M) to reconstruct the serialized value, where N is the number of Redis objects composing the value and M their average size. For small string values the time complexity is thus O(1)+O(1*M) where M is small, so simply O(1). However for sorted set values the complexity is O(N*M*log(N)) because inserting values into sorted sets is O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>ttl - integer</li>
   *   <li>serialized-value - string</li>
   *   <li>replace <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 2.6.0
   * group: generic
   */
  @Fluent
  default RedisAPI restore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RESTORE", arguments, handler);
    return this;
  }

  /**
   * Return the role of the instance in the context of replication
   * 

   * 
   * @since Redis 2.8.12
   * group: server
   */
  @Fluent
  default RedisAPI role(Handler<AsyncResult<Reply>> handler) {
    send("ROLE", handler);
    return this;
  }

  /**
   * Remove and get the last element in a list
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI rpop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RPOP", arguments, handler);
    return this;
  }

  /**
   * Remove the last element in a list, prepend it to another list and return it
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>source - key</li>
   *   <li>destination - key</li>
   * </ul>
   * @since Redis 1.2.0
   * group: list
   */
  @Fluent
  default RedisAPI rpoplpush(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RPOPLPUSH", arguments, handler);
    return this;
  }

  /**
   * Append one or multiple values to a list
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: list
   */
  @Fluent
  default RedisAPI rpush(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RPUSH", arguments, handler);
    return this;
  }

  /**
   * Append a value to a list, only if the list exists
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.2.0
   * group: list
   */
  @Fluent
  default RedisAPI rpushx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("RPUSHX", arguments, handler);
    return this;
  }

  /**
   * Add one or more members to a set
   * 
    * <b>Time complexity</b>: <i>O(1) for each element added, so O(N) to add N elements when the command is called with multiple arguments.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sadd(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SADD", arguments, handler);
    return this;
  }

  /**
   * Synchronously save the dataset to disk
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI save(Handler<AsyncResult<Reply>> handler) {
    send("SAVE", handler);
    return this;
  }

  /**
   * Get the number of members in a set
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI scard(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SCARD", arguments, handler);
    return this;
  }

  /**
   * Set the debug mode for executed scripts.
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>mode - enum</li>
   * </ul>
   * @since Redis 3.2.0
   * group: scripting
   */
  @Fluent
  default RedisAPI scriptDebug(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SCRIPT DEBUG", arguments, handler);
    return this;
  }

  /**
   * Check existence of scripts in the script cache.
   * 
    * <b>Time complexity</b>: <i>O(N) with N being the number of scripts to check (so checking a single script is an O(1) operation).</i>
   * 
   * <ul>
   *   <li>sha1 - string</li>
   * </ul>
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI scriptExists(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SCRIPT EXISTS", arguments, handler);
    return this;
  }

  /**
   * Remove all the scripts from the script cache.
   * 
    * <b>Time complexity</b>: <i>O(N) with N being the number of scripts in cache</i>
   * 

   * 
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI scriptFlush(Handler<AsyncResult<Reply>> handler) {
    send("SCRIPT FLUSH", handler);
    return this;
  }

  /**
   * Kill the script currently in execution.
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI scriptKill(Handler<AsyncResult<Reply>> handler) {
    send("SCRIPT KILL", handler);
    return this;
  }

  /**
   * Load the specified Lua script into the script cache.
   * 
    * <b>Time complexity</b>: <i>O(N) with N being the length in bytes of the script body.</i>
   * 
   * <ul>
   *   <li>script - string</li>
   * </ul>
   * @since Redis 2.6.0
   * group: scripting
   */
  @Fluent
  default RedisAPI scriptLoad(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SCRIPT LOAD", arguments, handler);
    return this;
  }

  /**
   * Subtract multiple sets
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of elements in all given sets.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sdiff(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SDIFF", arguments, handler);
    return this;
  }

  /**
   * Subtract multiple sets and store the resulting set in a key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of elements in all given sets.</i>
   * 
   * <ul>
   *   <li>destination - key</li>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sdiffstore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SDIFFSTORE", arguments, handler);
    return this;
  }

  /**
   * Change the selected database for the current connection
   * 
   * <ul>
   *   <li>index - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI select(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SELECT", arguments, handler);
    return this;
  }

  /**
   * Set the string value of a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   *   <li><b>expiration</b>  <i>(optional)</i> - enum</li>
   *   <li>condition <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI set(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SET", arguments, handler);
    return this;
  }

  /**
   * Sets or clears the bit at offset in the string value stored at key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>offset - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  default RedisAPI setbit(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SETBIT", arguments, handler);
    return this;
  }

  /**
   * Set the value and expiration of a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>seconds - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: string
   */
  @Fluent
  default RedisAPI setex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SETEX", arguments, handler);
    return this;
  }

  /**
   * Set the value of a key, only if the key does not exist
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: string
   */
  @Fluent
  default RedisAPI setnx(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SETNX", arguments, handler);
    return this;
  }

  /**
   * Overwrite part of a string at key starting at the specified offset
   * 
    * <b>Time complexity</b>: <i>O(1), not counting the time taken to copy the new string in place. Usually, this string is very small so the amortized complexity is O(1). Otherwise, complexity is O(M) with M being the length of the value argument.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>offset - integer</li>
   *   <li>value - string</li>
   * </ul>
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  default RedisAPI setrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SETRANGE", arguments, handler);
    return this;
  }

  /**
   * Synchronously save the dataset to disk and then shut down the server
   * 
   * <ul>
   *   <li>save-mode <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI shutdown(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SHUTDOWN", arguments, handler);
    return this;
  }

  /**
   * Intersect multiple sets
   * 
    * <b>Time complexity</b>: <i>O(N*M) worst case where N is the cardinality of the smallest set and M is the number of sets.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sinter(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SINTER", arguments, handler);
    return this;
  }

  /**
   * Intersect multiple sets and store the resulting set in a key
   * 
    * <b>Time complexity</b>: <i>O(N*M) worst case where N is the cardinality of the smallest set and M is the number of sets.</i>
   * 
   * <ul>
   *   <li>destination - key</li>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sinterstore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SINTERSTORE", arguments, handler);
    return this;
  }

  /**
   * Determine if a given value is a member of a set
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sismember(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SISMEMBER", arguments, handler);
    return this;
  }

  /**
   * Make the server a slave of another instance, or promote it as master
   * 
   * <ul>
   *   <li>host - string</li>
   *   <li>port - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI slaveof(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SLAVEOF", arguments, handler);
    return this;
  }

  /**
   * Manages the Redis slow queries log
   * 
   * <ul>
   *   <li>subcommand - string</li>
   *   <li>argument <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 2.2.12
   * group: server
   */
  @Fluent
  default RedisAPI slowlog(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SLOWLOG", arguments, handler);
    return this;
  }

  /**
   * Get all the members in a set
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the set cardinality.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI smembers(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SMEMBERS", arguments, handler);
    return this;
  }

  /**
   * Move a member from one set to another
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>source - key</li>
   *   <li>destination - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI smove(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SMOVE", arguments, handler);
    return this;
  }

  /**
   * Sort the elements in a list, set or sorted set
   * 
    * <b>Time complexity</b>: <i>O(N+M*log(M)) where N is the number of elements in the list or set to sort, and M the number of returned elements. When the elements are not sorted, complexity is currently O(N) as there is a copy step that will be avoided in next releases.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li><b>BY</b> pattern <i>(optional)</i> - pattern</li>
   *   <li><b>LIMIT</b> offset,count <i>(optional)</i> - integer,integer</li>
   *   <li><b>GET</b> pattern <i>(optional)</i> - string</li>
   *   <li>order <i>(optional)</i> - enum</li>
   *   <li>sorting <i>(optional)</i> - enum</li>
   *   <li><b>STORE</b> destination <i>(optional)</i> - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI sort(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SORT", arguments, handler);
    return this;
  }

  /**
   * Remove and return one or multiple random members from a set
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI spop(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SPOP", arguments, handler);
    return this;
  }

  /**
   * Get one or multiple random members from a set
   * 
    * <b>Time complexity</b>: <i>Without the count argument O(1), otherwise O(N) where N is the absolute value of the passed count.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI srandmember(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SRANDMEMBER", arguments, handler);
    return this;
  }

  /**
   * Remove one or more members from a set
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of members to be removed.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI srem(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SREM", arguments, handler);
    return this;
  }

  /**
   * Get the length of the value stored in a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.2.0
   * group: string
   */
  @Fluent
  default RedisAPI strlen(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("STRLEN", arguments, handler);
    return this;
  }

  /**
   * Listen for messages published to the given channels
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of channels to subscribe to.</i>
   * 
   * <ul>
   *   <li>channel - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI subscribe(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SUBSCRIBE", arguments, handler);
    return this;
  }

  /**
   * Add multiple sets
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of elements in all given sets.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sunion(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SUNION", arguments, handler);
    return this;
  }

  /**
   * Add multiple sets and store the resulting set in a key
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the total number of elements in all given sets.</i>
   * 
   * <ul>
   *   <li>destination - key</li>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: set
   */
  @Fluent
  default RedisAPI sunionstore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SUNIONSTORE", arguments, handler);
    return this;
  }

  /**
   * Swaps two Redis databases
   * 
   * <ul>
   *   <li>index - integer</li>
   *   <li>index - integer</li>
   * </ul>
   * @since Redis 4.0.0
   * group: connection
   */
  @Fluent
  default RedisAPI swapdb(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SWAPDB", arguments, handler);
    return this;
  }

  /**
   * Internal command used for replication
   * 

   * 
   * @since Redis 1.0.0
   * group: server
   */
  @Fluent
  default RedisAPI sync(Handler<AsyncResult<Reply>> handler) {
    send("SYNC", handler);
    return this;
  }

  /**
   * Return the current server time
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.6.0
   * group: server
   */
  @Fluent
  default RedisAPI time(Handler<AsyncResult<Reply>> handler) {
    send("TIME", handler);
    return this;
  }

  /**
   * Alters the last access time of a key(s). Returns the number of existing keys specified.
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of keys that will be touched.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 3.2.1
   * group: generic
   */
  @Fluent
  default RedisAPI touch(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("TOUCH", arguments, handler);
    return this;
  }

  /**
   * Get the time to live for a key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI ttl(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("TTL", arguments, handler);
    return this;
  }

  /**
   * Determine the type stored at key
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI type(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("TYPE", arguments, handler);
    return this;
  }

  /**
   * Stop listening for messages posted to the given channels
   * 
    * <b>Time complexity</b>: <i>O(N) where N is the number of clients already subscribed to a channel.</i>
   * 
   * <ul>
   *   <li>channel <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: pubsub
   */
  @Fluent
  default RedisAPI unsubscribe(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("UNSUBSCRIBE", arguments, handler);
    return this;
  }

  /**
   * Delete a key asynchronously in another thread. Otherwise it is just as DEL, but non blocking.
   * 
    * <b>Time complexity</b>: <i>O(1) for each key removed regardless of its size. Then the command does O(N) work in a different thread in order to reclaim memory, where N is the number of allocations the deleted objects where composed of.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 4.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI unlink(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("UNLINK", arguments, handler);
    return this;
  }

  /**
   * Forget about all watched keys
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 

   * 
   * @since Redis 2.2.0
   * group: transactions
   */
  @Fluent
  default RedisAPI unwatch(Handler<AsyncResult<Reply>> handler) {
    send("UNWATCH", handler);
    return this;
  }

  /**
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>numslaves - integer</li>
   *   <li>timeout - integer</li>
   * </ul>
   * @since Redis 3.0.0
   * group: generic
   */
  @Fluent
  default RedisAPI wait(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("WAIT", arguments, handler);
    return this;
  }

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * 
    * <b>Time complexity</b>: <i>O(1) for every key.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 2.2.0
   * group: transactions
   */
  @Fluent
  default RedisAPI watch(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("WATCH", arguments, handler);
    return this;
  }

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * 
    * <b>Time complexity</b>: <i>O(log(N)) for each item added, where N is the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>condition <i>(optional)</i> - enum</li>
   *   <li>change <i>(optional)</i> - enum</li>
   *   <li>increment <i>(optional)</i> - enum</li>
   *   <li>score,member - double,string</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zadd(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZADD", arguments, handler);
    return this;
  }

  /**
   * Get the number of members in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zcard(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZCARD", arguments, handler);
    return this;
  }

  /**
   * Count the members in a sorted set with scores within the given values
   * 
    * <b>Time complexity</b>: <i>O(log(N)) with N being the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - double</li>
   *   <li>max - double</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zcount(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZCOUNT", arguments, handler);
    return this;
  }

  /**
   * Increment the score of a member in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(log(N)) where N is the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>increment - integer</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zincrby(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZINCRBY", arguments, handler);
    return this;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * 
    * <b>Time complexity</b>: <i>O(N*K)+O(M*log(M)) worst case with N being the smallest input sorted set, K being the number of input sorted sets and M being the number of elements in the resulting sorted set.</i>
   * 
   * <ul>
   *   <li>destination - key</li>
   *   <li>numkeys - integer</li>
   *   <li>key - key</li>
   *   <li><b>WEIGHTS</b> weight <i>(optional)</i> - integer</li>
   *   <li><b>AGGREGATE</b> aggregate <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zinterstore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZINTERSTORE", arguments, handler);
    return this;
  }

  /**
   * Count the number of members in a sorted set between a given lexicographical range
   * 
    * <b>Time complexity</b>: <i>O(log(N)) with N being the number of elements in the sorted set.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - string</li>
   *   <li>max - string</li>
   * </ul>
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zlexcount(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZLEXCOUNT", arguments, handler);
    return this;
  }

  /**
   * Remove and return members with the highest scores in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(log(N)*M) with N being the number of elements in the sorted set, and M being the number of elements popped.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zpopmax(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZPOPMAX", arguments, handler);
    return this;
  }

  /**
   * Remove and return members with the lowest scores in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(log(N)*M) with N being the number of elements in the sorted set, and M being the number of elements popped.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zpopmin(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZPOPMIN", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by index
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements returned.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>stop - integer</li>
   *   <li>withscores <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZRANGE", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by lexicographical range
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with LIMIT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - string</li>
   *   <li>max - string</li>
   *   <li><b>LIMIT</b> offset,count <i>(optional)</i> - integer,integer</li>
   * </ul>
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrangebylex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZRANGEBYLEX", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by lexicographical range, ordered from higher to lower strings.
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with LIMIT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>max - string</li>
   *   <li>min - string</li>
   *   <li><b>LIMIT</b> offset,count <i>(optional)</i> - integer,integer</li>
   * </ul>
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrevrangebylex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREVRANGEBYLEX", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by score
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with LIMIT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - double</li>
   *   <li>max - double</li>
   *   <li>withscores <i>(optional)</i> - enum</li>
   *   <li><b>LIMIT</b> offset,count <i>(optional)</i> - integer,integer</li>
   * </ul>
   * @since Redis 1.0.5
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrangebyscore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZRANGEBYSCORE", arguments, handler);
    return this;
  }

  /**
   * Determine the index of a member in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(log(N))</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrank(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZRANK", arguments, handler);
    return this;
  }

  /**
   * Remove one or more members from a sorted set
   * 
    * <b>Time complexity</b>: <i>O(M*log(N)) with N being the number of elements in the sorted set and M the number of elements to be removed.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrem(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREM", arguments, handler);
    return this;
  }

  /**
   * Remove all members in a sorted set between the given lexicographical range
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements removed by the operation.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - string</li>
   *   <li>max - string</li>
   * </ul>
   * @since Redis 2.8.9
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zremrangebylex(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREMRANGEBYLEX", arguments, handler);
    return this;
  }

  /**
   * Remove all members in a sorted set within the given indexes
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements removed by the operation.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>stop - integer</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zremrangebyrank(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREMRANGEBYRANK", arguments, handler);
    return this;
  }

  /**
   * Remove all members in a sorted set within the given scores
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements removed by the operation.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>min - double</li>
   *   <li>max - double</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zremrangebyscore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREMRANGEBYSCORE", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements returned.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - integer</li>
   *   <li>stop - integer</li>
   *   <li>withscores <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrevrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREVRANGE", arguments, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with LIMIT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>max - double</li>
   *   <li>min - double</li>
   *   <li>withscores <i>(optional)</i> - enum</li>
   *   <li><b>LIMIT</b> offset,count <i>(optional)</i> - integer,integer</li>
   * </ul>
   * @since Redis 2.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrevrangebyscore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREVRANGEBYSCORE", arguments, handler);
    return this;
  }

  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * 
    * <b>Time complexity</b>: <i>O(log(N))</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zrevrank(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZREVRANK", arguments, handler);
    return this;
  }

  /**
   * Get the score associated with the given member in a sorted set
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>member - string</li>
   * </ul>
   * @since Redis 1.2.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zscore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZSCORE", arguments, handler);
    return this;
  }

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * 
    * <b>Time complexity</b>: <i>O(N)+O(M log(M)) with N being the sum of the sizes of the input sorted sets, and M being the number of elements in the resulting sorted set.</i>
   * 
   * <ul>
   *   <li>destination - key</li>
   *   <li>numkeys - integer</li>
   *   <li>key - key</li>
   *   <li><b>WEIGHTS</b> weight <i>(optional)</i> - integer</li>
   *   <li><b>AGGREGATE</b> aggregate <i>(optional)</i> - enum</li>
   * </ul>
   * @since Redis 2.0.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zunionstore(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZUNIONSTORE", arguments, handler);
    return this;
  }

  /**
   * Incrementally iterate the keys space
   * 
    * <b>Time complexity</b>: <i>O(1) for every call. O(N) for a complete iteration, including enough command calls for the cursor to return back to 0. N is the number of elements inside the collection.</i>
   * 
   * <ul>
   *   <li>cursor - integer</li>
   *   <li><b>MATCH</b> pattern <i>(optional)</i> - pattern</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 2.8.0
   * group: generic
   */
  @Fluent
  default RedisAPI scan(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SCAN", arguments, handler);
    return this;
  }

  /**
   * Incrementally iterate Set elements
   * 
    * <b>Time complexity</b>: <i>O(1) for every call. O(N) for a complete iteration, including enough command calls for the cursor to return back to 0. N is the number of elements inside the collection..</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>cursor - integer</li>
   *   <li><b>MATCH</b> pattern <i>(optional)</i> - pattern</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 2.8.0
   * group: set
   */
  @Fluent
  default RedisAPI sscan(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("SSCAN", arguments, handler);
    return this;
  }

  /**
   * Incrementally iterate hash fields and associated values
   * 
    * <b>Time complexity</b>: <i>O(1) for every call. O(N) for a complete iteration, including enough command calls for the cursor to return back to 0. N is the number of elements inside the collection..</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>cursor - integer</li>
   *   <li><b>MATCH</b> pattern <i>(optional)</i> - pattern</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 2.8.0
   * group: hash
   */
  @Fluent
  default RedisAPI hscan(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("HSCAN", arguments, handler);
    return this;
  }

  /**
   * Incrementally iterate sorted sets elements and associated scores
   * 
    * <b>Time complexity</b>: <i>O(1) for every call. O(N) for a complete iteration, including enough command calls for the cursor to return back to 0. N is the number of elements inside the collection..</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>cursor - integer</li>
   *   <li><b>MATCH</b> pattern <i>(optional)</i> - pattern</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 2.8.0
   * group: sorted_set
   */
  @Fluent
  default RedisAPI zscan(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("ZSCAN", arguments, handler);
    return this;
  }

  /**
   * Appends a new entry to a stream
   * 
    * <b>Time complexity</b>: <i>O(log(N)) with N being the number of items already into the stream.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>ID - string</li>
   *   <li>field,string - value,string</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xadd(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XADD", arguments, handler);
    return this;
  }

  /**
   * Return a range of elements in a stream, with IDs matching the specified IDs interval
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the stream and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with COUNT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>start - string</li>
   *   <li>end - string</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XRANGE", arguments, handler);
    return this;
  }

  /**
   * Return a range of elements in a stream, with IDs matching the specified IDs interval, in reverse order (from greater to smaller IDs) compared to XRANGE
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the stream and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with COUNT), you can consider it O(log(N)).</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>end - string</li>
   *   <li>start - string</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xrevrange(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XREVRANGE", arguments, handler);
    return this;
  }

  /**
   * Return the number of entires in a stream
   * 
    * <b>Time complexity</b>: <i>O(1)</i>
   * 
   * <ul>
   *   <li>key - key</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xlen(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XLEN", arguments, handler);
    return this;
  }

  /**
   * Return never seen elements in multiple streams, with IDs greater than the ones reported by the caller for each stream. Can block.
   * 
    * <b>Time complexity</b>: <i>For each stream mentioned: O(log(N)+M) with N being the number of elements in the stream and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with COUNT), you can consider it O(log(N)). On the other side, XADD will pay the O(N) time in order to serve the N clients blocked on the stream getting new data.</i>
   * 
   * <ul>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   *   <li><b>BLOCK</b> milliseconds <i>(optional)</i> - integer</li>
   *   <li>streams - enum</li>
   *   <li>key - key</li>
   *   <li>ID - string</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xread(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XREAD", arguments, handler);
    return this;
  }

  /**
   * Return new entries from a stream using a consumer group, or access the history of the pending entries for a given consumer. Can block.
   * 
    * <b>Time complexity</b>: <i>For each stream mentioned: O(log(N)+M) with N being the number of elements in the stream and M the number of elements being returned. If M is constant (e.g. always asking for the first 10 elements with COUNT), you can consider it O(log(N)). On the other side, XADD will pay the O(N) time in order to serve the N clients blocked on the stream getting new data.</i>
   * 
   * <ul>
   *   <li><b>GROUP</b> group,consumer - string,string</li>
   *   <li><b>COUNT</b> count <i>(optional)</i> - integer</li>
   *   <li><b>BLOCK</b> milliseconds <i>(optional)</i> - integer</li>
   *   <li>streams - enum</li>
   *   <li>key - key</li>
   *   <li>ID - string</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xreadgroup(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XREADGROUP", arguments, handler);
    return this;
  }

  /**
   * Return information and entries from a stream consumer group pending entries list, that are messages fetched but never acknowledged.
   * 
    * <b>Time complexity</b>: <i>O(log(N)+M) with N being the number of elements in the consumer group pending entries list, and M the number of elements being returned. When the command returns just the summary it runs in O(1) time assuming the list of consumers is small, otherwise there is additional O(N) time needed to iterate every consumer.</i>
   * 
   * <ul>
   *   <li>key - key</li>
   *   <li>group - string</li>
   *   <li>start,end,count <i>(optional)</i> - string,string,integer</li>
   *   <li>consumer <i>(optional)</i> - string</li>
   * </ul>
   * @since Redis 5.0.0
   * group: stream
   */
  @Fluent
  default RedisAPI xpending(Args arguments, Handler<AsyncResult<Reply>> handler) {
    send("XPENDING", arguments, handler);
    return this;
  }

}
