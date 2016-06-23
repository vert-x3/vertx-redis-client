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

package io.vertx.rxjava.redis;

import java.util.Map;
import rx.Observable;
import io.vertx.redis.op.BitOperation;
import io.vertx.redis.op.ResetOptions;
import io.vertx.redis.op.ObjectCmd;
import io.vertx.redis.op.KillFilter;
import java.util.Map;
import io.vertx.redis.op.GeoUnit;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.redis.op.RangeOptions;
import io.vertx.redis.op.GeoMember;
import io.vertx.redis.op.GeoRadiusOptions;
import io.vertx.redis.op.InsertOptions;
import io.vertx.redis.op.AggregateOptions;
import io.vertx.redis.op.SetOptions;
import io.vertx.redis.op.SortOptions;
import io.vertx.redis.op.MigrateOptions;
import io.vertx.redis.op.ScanOptions;
import io.vertx.redis.op.FailoverOptions;
import io.vertx.redis.op.SlotCmd;
import io.vertx.redis.op.RangeLimitOptions;
import io.vertx.redis.op.LimitOptions;
import io.vertx.core.json.JsonArray;
import java.util.List;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.core.Handler;

/**
 * This Interface represents a TX
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.redis.RedisTransaction original} non RX-ified interface using Vert.x codegen.
 */

public class RedisTransaction {

  final io.vertx.redis.RedisTransaction delegate;

  public RedisTransaction(io.vertx.redis.RedisTransaction delegate) {
    this.delegate = delegate;
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
   * Close the client - when it is fully closed the handler will be called.
   * @return 
   */
  public Observable<Void> closeObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    close(handler.toHandler());
    return handler;
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
   * Append a value to a key
   * @param key Key string
   * @param value Value to append
   * @return 
   */
  public Observable<String> appendObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    append(key, value, handler.toHandler());
    return handler;
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
   * Authenticate to the server
   * @param password Password for authentication
   * @return 
   */
  public Observable<String> authObservable(String password) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    auth(password, handler.toHandler());
    return handler;
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
   * Asynchronously rewrite the append-only file
   * @return 
   */
  public Observable<String> bgrewriteaofObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bgrewriteaof(handler.toHandler());
    return handler;
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
   * Asynchronously save the dataset to disk
   * @return 
   */
  public Observable<String> bgsaveObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bgsave(handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> bitcountObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitcount(key, handler.toHandler());
    return handler;
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
   * Count set bits in a string
   * @param key Key string
   * @param start Start index
   * @param end End index
   * @return 
   */
  public Observable<String> bitcountRangeObservable(String key, long start, long end) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitcountRange(key, start, end, handler.toHandler());
    return handler;
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
    delegate.bitop(operation, destkey, keys, handler);
    return this;
  }

  /**
   * Perform bitwise operations between strings
   * @param operation Bitwise operation to perform
   * @param destkey Destination key where result is stored
   * @param keys List of keys on which to perform the operation
   * @return 
   */
  public Observable<String> bitopObservable(BitOperation operation, String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitop(operation, destkey, keys, handler.toHandler());
    return handler;
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
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @return 
   */
  public Observable<String> bitposObservable(String key, int bit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitpos(key, bit, handler.toHandler());
    return handler;
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
   * See also bitposRange() method, which takes start, and stop offset.
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @return 
   */
  public Observable<String> bitposFromObservable(String key, int bit, int start) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitposFrom(key, bit, start, handler.toHandler());
    return handler;
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
   * Find first bit set or clear in a string
   * <p>
   * Note: when both start, and stop offsets are specified,
   * behaviour is slightly different than if only start is specified
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @param stop End offset - inclusive
   * @return 
   */
  public Observable<String> bitposRangeObservable(String key, int bit, int start, int stop) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitposRange(key, bit, start, stop, handler.toHandler());
    return handler;
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
   * Remove and get the first element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<String> blpopObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    blpop(key, seconds, handler.toHandler());
    return handler;
  }

  /**
   * Remove and get the first element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction blpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) { 
    delegate.blpopMany(keys, seconds, handler);
    return this;
  }

  /**
   * Remove and get the first element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<String> blpopManyObservable(List<String> keys, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    blpopMany(keys, seconds, handler.toHandler());
    return handler;
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
   * Remove and get the last element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<String> brpopObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    brpop(key, seconds, handler.toHandler());
    return handler;
  }

  /**
   * Remove and get the last element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction brpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) { 
    delegate.brpopMany(keys, seconds, handler);
    return this;
  }

  /**
   * Remove and get the last element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<String> brpopManyObservable(List<String> keys, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    brpopMany(keys, seconds, handler.toHandler());
    return handler;
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
   * Pop a value from a list, push it to another list and return it; or block until one is available
   * @param key Key string identifying the source list
   * @param destkey Key string identifying the destination list
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<String> brpoplpushObservable(String key, String destkey, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    brpoplpush(key, destkey, seconds, handler.toHandler());
    return handler;
  }

  /**
   * Kill the connection of a client
   * @param filter Filter options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clientKill(KillFilter filter, Handler<AsyncResult<String>> handler) { 
    delegate.clientKill(filter, handler);
    return this;
  }

  /**
   * Kill the connection of a client
   * @param filter Filter options
   * @return 
   */
  public Observable<String> clientKillObservable(KillFilter filter) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientKill(filter, handler.toHandler());
    return handler;
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
   * Get the list of client connections
   * @return 
   */
  public Observable<String> clientListObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientList(handler.toHandler());
    return handler;
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
   * Get the current connection name
   * @return 
   */
  public Observable<String> clientGetnameObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientGetname(handler.toHandler());
    return handler;
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
   * Stop processing commands from clients for some time
   * @param millis Pause time in milliseconds
   * @return 
   */
  public Observable<String> clientPauseObservable(long millis) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientPause(millis, handler.toHandler());
    return handler;
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
   * Set the current connection name
   * @param name New name for current connection
   * @return 
   */
  public Observable<String> clientSetnameObservable(String name) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientSetname(name, handler.toHandler());
    return handler;
  }

  /**
   * Assign new hash slots to receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterAddslots(List<String> slots, Handler<AsyncResult<String>> handler) { 
    delegate.clusterAddslots(slots, handler);
    return this;
  }

  /**
   * Assign new hash slots to receiving node.
   * @param slots 
   * @return 
   */
  public Observable<String> clusterAddslotsObservable(List<String> slots) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterAddslots(slots, handler.toHandler());
    return handler;
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
   * Return the number of failure reports active for a given node.
   * @param nodeId 
   * @return 
   */
  public Observable<String> clusterCountFailureReportsObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterCountFailureReports(nodeId, handler.toHandler());
    return handler;
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
   * Return the number of local keys in the specified hash slot.
   * @param slot 
   * @return 
   */
  public Observable<String> clusterCountkeysinslotObservable(long slot) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterCountkeysinslot(slot, handler.toHandler());
    return handler;
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
   * @param slot 
   * @return 
   */
  public Observable<String> clusterDelslotsObservable(long slot) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterDelslots(slot, handler.toHandler());
    return handler;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction clusterDelslotsMany(List<String> slots, Handler<AsyncResult<String>> handler) { 
    delegate.clusterDelslotsMany(slots, handler);
    return this;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @return 
   */
  public Observable<String> clusterDelslotsManyObservable(List<String> slots) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterDelslotsMany(slots, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> clusterFailoverObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterFailover(handler.toHandler());
    return handler;
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
   * Forces a slave to perform a manual failover of its master.
   * @param options 
   * @return 
   */
  public Observable<String> clusterFailOverWithOptionsObservable(FailoverOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterFailOverWithOptions(options, handler.toHandler());
    return handler;
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
   * Remove a node from the nodes table.
   * @param nodeId 
   * @return 
   */
  public Observable<String> clusterForgetObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterForget(nodeId, handler.toHandler());
    return handler;
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
   * Return local key names in the specified hash slot.
   * @param slot 
   * @param count 
   * @return 
   */
  public Observable<String> clusterGetkeysinslotObservable(long slot, long count) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterGetkeysinslot(slot, count, handler.toHandler());
    return handler;
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
   * Provides info about Redis Cluster node state.
   * @return 
   */
  public Observable<String> clusterInfoObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterInfo(handler.toHandler());
    return handler;
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
   * Returns the hash slot of the specified key.
   * @param key 
   * @return 
   */
  public Observable<String> clusterKeyslotObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterKeyslot(key, handler.toHandler());
    return handler;
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
   * Force a node cluster to handshake with another node.
   * @param ip 
   * @param port 
   * @return 
   */
  public Observable<String> clusterMeetObservable(String ip, long port) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterMeet(ip, port, handler.toHandler());
    return handler;
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
   * Get Cluster config for the node.
   * @return 
   */
  public Observable<String> clusterNodesObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterNodes(handler.toHandler());
    return handler;
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
   * Reconfigure a node as a slave of the specified master node.
   * @param nodeId 
   * @return 
   */
  public Observable<String> clusterReplicateObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterReplicate(nodeId, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> clusterResetObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterReset(handler.toHandler());
    return handler;
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
   * Reset a Redis Cluster node.
   * @param options 
   * @return 
   */
  public Observable<String> clusterResetWithOptionsObservable(ResetOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterResetWithOptions(options, handler.toHandler());
    return handler;
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
   * Forces the node to save cluster state on disk.
   * @return 
   */
  public Observable<String> clusterSaveconfigObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSaveconfig(handler.toHandler());
    return handler;
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
   * Set the configuration epoch in a new node.
   * @param epoch 
   * @return 
   */
  public Observable<String> clusterSetConfigEpochObservable(long epoch) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSetConfigEpoch(epoch, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> clusterSetslotObservable(long slot, SlotCmd subcommand) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSetslot(slot, subcommand, handler.toHandler());
    return handler;
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
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @param nodeId 
   * @return 
   */
  public Observable<String> clusterSetslotWithNodeObservable(long slot, SlotCmd subcommand, String nodeId) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSetslotWithNode(slot, subcommand, nodeId, handler.toHandler());
    return handler;
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
   * List slave nodes of the specified master node.
   * @param nodeId 
   * @return 
   */
  public Observable<String> clusterSlavesObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSlaves(nodeId, handler.toHandler());
    return handler;
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
   * Get array of Cluster slot to node mappings
   * @return 
   */
  public Observable<String> clusterSlotsObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSlots(handler.toHandler());
    return handler;
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
   * Get array of Redis command details
   * @return 
   */
  public Observable<String> commandObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    command(handler.toHandler());
    return handler;
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
   * Get total number of Redis commands
   * @return 
   */
  public Observable<String> commandCountObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandCount(handler.toHandler());
    return handler;
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
   * Extract keys given a full Redis command
   * @return 
   */
  public Observable<String> commandGetkeysObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandGetkeys(handler.toHandler());
    return handler;
  }

  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction commandInfo(List<String> commands, Handler<AsyncResult<String>> handler) { 
    delegate.commandInfo(commands, handler);
    return this;
  }

  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @return 
   */
  public Observable<String> commandInfoObservable(List<String> commands) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandInfo(commands, handler.toHandler());
    return handler;
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
   * Get the value of a configuration parameter
   * @param parameter Configuration parameter
   * @return 
   */
  public Observable<String> configGetObservable(String parameter) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    configGet(parameter, handler.toHandler());
    return handler;
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
   * Rewrite the configuration file with the in memory configuration
   * @return 
   */
  public Observable<String> configRewriteObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    configRewrite(handler.toHandler());
    return handler;
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
   * Set a configuration parameter to the given value
   * @param parameter Configuration parameter
   * @param value New value
   * @return 
   */
  public Observable<String> configSetObservable(String parameter, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    configSet(parameter, value, handler.toHandler());
    return handler;
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
   * Reset the stats returned by INFO
   * @return 
   */
  public Observable<String> configResetstatObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    configResetstat(handler.toHandler());
    return handler;
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
   * Return the number of keys in the selected database
   * @return 
   */
  public Observable<String> dbsizeObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    dbsize(handler.toHandler());
    return handler;
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
   * Get debugging information about a key
   * @param key Key string
   * @return 
   */
  public Observable<String> debugObjectObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    debugObject(key, handler.toHandler());
    return handler;
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
   * Make the server crash
   * @return 
   */
  public Observable<String> debugSegfaultObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    debugSegfault(handler.toHandler());
    return handler;
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
   * Decrement the integer value of a key by one
   * @param key Key string
   * @return 
   */
  public Observable<String> decrObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    decr(key, handler.toHandler());
    return handler;
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
   * Decrement the integer value of a key by the given number
   * @param key Key string
   * @param decrement Value by which to decrement
   * @return 
   */
  public Observable<String> decrbyObservable(String key, long decrement) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    decrby(key, decrement, handler.toHandler());
    return handler;
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
   * Delete a key
   * @param key Keys to delete
   * @return 
   */
  public Observable<String> delObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    del(key, handler.toHandler());
    return handler;
  }

  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction delMany(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.delMany(keys, handler);
    return this;
  }

  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @return 
   */
  public Observable<String> delManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    delMany(keys, handler.toHandler());
    return handler;
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
   * Discard all commands issued after MULTI
   * @return 
   */
  public Observable<String> discardObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    discard(handler.toHandler());
    return handler;
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
   * Return a serialized version of the value stored at the specified key.
   * @param key Key string
   * @return 
   */
  public Observable<String> dumpObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    dump(key, handler.toHandler());
    return handler;
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
   * Echo the given string
   * @param message String to echo
   * @return 
   */
  public Observable<String> echoObservable(String message) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    echo(message, handler.toHandler());
    return handler;
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
    delegate.eval(script, keys, args, handler);
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
   * @return 
   */
  public Observable<String> evalObservable(String script, List<String> keys, List<String> args) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    eval(script, keys, args, handler.toHandler());
    return handler;
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
    delegate.evalsha(sha1, keys, values, handler);
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
   * @return 
   */
  public Observable<String> evalshaObservable(String sha1, List<String> keys, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    evalsha(sha1, keys, values, handler.toHandler());
    return handler;
  }

  /**
   * Execute all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisTransaction exec(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.exec(handler);
    return this;
  }

  /**
   * Execute all commands issued after MULTI
   * @return 
   */
  public Observable<JsonArray> execObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    exec(handler.toHandler());
    return handler;
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
   * Determine if a key exists
   * @param key Key string
   * @return 
   */
  public Observable<String> existsObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    exists(key, handler.toHandler());
    return handler;
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
   * Set a key's time to live in seconds
   * @param key Key string
   * @param seconds Time to live in seconds
   * @return 
   */
  public Observable<String> expireObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    expire(key, seconds, handler.toHandler());
    return handler;
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
   * Set the expiration for a key as a UNIX timestamp
   * @param key Key string
   * @param seconds Expiry time as Unix timestamp in seconds
   * @return 
   */
  public Observable<String> expireatObservable(String key, long seconds) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    expireat(key, seconds, handler.toHandler());
    return handler;
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
   * Remove all keys from all databases
   * @return 
   */
  public Observable<String> flushallObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    flushall(handler.toHandler());
    return handler;
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
   * Remove all keys from the current database
   * @return 
   */
  public Observable<String> flushdbObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    flushdb(handler.toHandler());
    return handler;
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
   * Get the value of a key
   * @param key Key string
   * @return 
   */
  public Observable<String> getObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    get(key, handler.toHandler());
    return handler;
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
   * Get the value of a key - without decoding as utf-8
   * @param key Key string
   * @return 
   */
  public Observable<String> getBinaryObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    getBinary(key, handler.toHandler());
    return handler;
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
   * Returns the bit value at offset in the string value stored at key
   * @param key Key string
   * @param offset Offset in bits
   * @return 
   */
  public Observable<String> getbitObservable(String key, long offset) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    getbit(key, offset, handler.toHandler());
    return handler;
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
   * Get a substring of the string stored at a key
   * @param key Key string
   * @param start Start offset
   * @param end End offset - inclusive
   * @return 
   */
  public Observable<String> getrangeObservable(String key, long start, long end) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    getrange(key, start, end, handler.toHandler());
    return handler;
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
   * Set the string value of a key and return its old value
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<String> getsetObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    getset(key, value, handler.toHandler());
    return handler;
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
   * @param field Field name
   * @return 
   */
  public Observable<String> hdelObservable(String key, String field) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hdel(key, field, handler.toHandler());
    return handler;
  }

  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hdelMany(String key, List<String> fields, Handler<AsyncResult<String>> handler) { 
    delegate.hdelMany(key, fields, handler);
    return this;
  }

  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param fields Field names
   * @return 
   */
  public Observable<String> hdelManyObservable(String key, List<String> fields) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hdelMany(key, fields, handler.toHandler());
    return handler;
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
   * Determine if a hash field exists
   * @param key Key string
   * @param field Field name
   * @return 
   */
  public Observable<String> hexistsObservable(String key, String field) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hexists(key, field, handler.toHandler());
    return handler;
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
   * Get the value of a hash field
   * @param key Key string
   * @param field Field name
   * @return 
   */
  public Observable<String> hgetObservable(String key, String field) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hget(key, field, handler.toHandler());
    return handler;
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
   * Get all the fields and values in a hash
   * @param key Key string
   * @return 
   */
  public Observable<String> hgetallObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hgetall(key, handler.toHandler());
    return handler;
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
   * Increment the integer value of a hash field by the given number
   * @param key Key string
   * @param field Field name
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<String> hincrbyObservable(String key, String field, long increment) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hincrby(key, field, increment, handler.toHandler());
    return handler;
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
   * Increment the float value of a hash field by the given amount
   * @param key Key string
   * @param field Field name
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<String> hincrbyfloatObservable(String key, String field, double increment) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hincrbyfloat(key, field, increment, handler.toHandler());
    return handler;
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
   * Get all the fields in a hash
   * @param key Key string
   * @return 
   */
  public Observable<String> hkeysObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hkeys(key, handler.toHandler());
    return handler;
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
   * Get the number of fields in a hash
   * @param key Key string
   * @return 
   */
  public Observable<String> hlenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hlen(key, handler.toHandler());
    return handler;
  }

  /**
   * Get the values of all the given hash fields
   * @param key Key string
   * @param fields Field names
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hmget(String key, List<String> fields, Handler<AsyncResult<String>> handler) { 
    delegate.hmget(key, fields, handler);
    return this;
  }

  /**
   * Get the values of all the given hash fields
   * @param key Key string
   * @param fields Field names
   * @return 
   */
  public Observable<String> hmgetObservable(String key, List<String> fields) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hmget(key, fields, handler.toHandler());
    return handler;
  }

  /**
   * Set multiple hash fields to multiple values
   * @param key Key string
   * @param values Map of field:value pairs
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler) { 
    delegate.hmset(key, values, handler);
    return this;
  }

  /**
   * Set multiple hash fields to multiple values
   * @param key Key string
   * @param values Map of field:value pairs
   * @return 
   */
  public Observable<String> hmsetObservable(String key, JsonObject values) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hmset(key, values, handler.toHandler());
    return handler;
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
   * Set the string value of a hash field
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @return 
   */
  public Observable<String> hsetObservable(String key, String field, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hset(key, field, value, handler.toHandler());
    return handler;
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
   * Set the value of a hash field, only if the field does not exist
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @return 
   */
  public Observable<String> hsetnxObservable(String key, String field, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hsetnx(key, field, value, handler.toHandler());
    return handler;
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
   * Get all the values in a hash
   * @param key Key string
   * @return 
   */
  public Observable<String> hvalsObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hvals(key, handler.toHandler());
    return handler;
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
   * Increment the integer value of a key by one
   * @param key Key string
   * @return 
   */
  public Observable<String> incrObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    incr(key, handler.toHandler());
    return handler;
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
   * Increment the integer value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<String> incrbyObservable(String key, long increment) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    incrby(key, increment, handler.toHandler());
    return handler;
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
   * Increment the float value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<String> incrbyfloatObservable(String key, double increment) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    incrbyfloat(key, increment, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> infoObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    info(handler.toHandler());
    return handler;
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
   * Get information and statistics about the server
   * @param section Specific section of information to return
   * @return 
   */
  public Observable<String> infoSectionObservable(String section) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    infoSection(section, handler.toHandler());
    return handler;
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
   * Find all keys matching the given pattern
   * @param pattern Pattern to limit the keys returned
   * @return 
   */
  public Observable<String> keysObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    keys(pattern, handler.toHandler());
    return handler;
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
   * Get the UNIX time stamp of the last successful save to disk
   * @return 
   */
  public Observable<String> lastsaveObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lastsave(handler.toHandler());
    return handler;
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
   * Get an element from a list by its index
   * @param key Key string
   * @param index Index of list element to get
   * @return 
   */
  public Observable<String> lindexObservable(String key, int index) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lindex(key, index, handler.toHandler());
    return handler;
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
   * Insert an element before or after another element in a list
   * @param key Key string
   * @param option BEFORE or AFTER
   * @param pivot Key to use as a pivot
   * @param value Value to be inserted before or after the pivot
   * @return 
   */
  public Observable<String> linsertObservable(String key, InsertOptions option, String pivot, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    linsert(key, option, pivot, value, handler.toHandler());
    return handler;
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
   * Get the length of a list
   * @param key String key
   * @return 
   */
  public Observable<String> llenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    llen(key, handler.toHandler());
    return handler;
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
   * Remove and get the first element in a list
   * @param key String key
   * @return 
   */
  public Observable<String> lpopObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lpop(key, handler.toHandler());
    return handler;
  }

  /**
   * Prepend one or multiple values to a list
   * @param key Key string
   * @param values Values to be added at the beginning of the list, one by one
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction lpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) { 
    delegate.lpushMany(key, values, handler);
    return this;
  }

  /**
   * Prepend one or multiple values to a list
   * @param key Key string
   * @param values Values to be added at the beginning of the list, one by one
   * @return 
   */
  public Observable<String> lpushManyObservable(String key, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lpushMany(key, values, handler.toHandler());
    return handler;
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
   * Prepend one value to a list
   * @param key Key string
   * @param value Value to be added at the beginning of the list
   * @return 
   */
  public Observable<String> lpushObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lpush(key, value, handler.toHandler());
    return handler;
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
   * Prepend a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to add at the beginning of the list
   * @return 
   */
  public Observable<String> lpushxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lpushx(key, value, handler.toHandler());
    return handler;
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
   * Get a range of elements from a list
   * @param key Key string
   * @param from Start index
   * @param to Stop index
   * @return 
   */
  public Observable<String> lrangeObservable(String key, long from, long to) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lrange(key, from, to, handler.toHandler());
    return handler;
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
   * Remove elements from a list
   * @param key Key string
   * @param count Number of first found occurrences equal to $value to remove from the list
   * @param value Value to be removed
   * @return 
   */
  public Observable<String> lremObservable(String key, long count, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lrem(key, count, value, handler.toHandler());
    return handler;
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
   * Set the value of an element in a list by its index
   * @param key Key string
   * @param index Position within list
   * @param value New value
   * @return 
   */
  public Observable<String> lsetObservable(String key, long index, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    lset(key, index, value, handler.toHandler());
    return handler;
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
   * Trim a list to the specified range
   * @param key Key string
   * @param from Start index
   * @param to Stop index
   * @return 
   */
  public Observable<String> ltrimObservable(String key, long from, long to) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    ltrim(key, from, to, handler.toHandler());
    return handler;
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
   * Get the value of the given key
   * @param key Key string
   * @return 
   */
  public Observable<String> mgetObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    mget(key, handler.toHandler());
    return handler;
  }

  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction mgetMany(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.mgetMany(keys, handler);
    return this;
  }

  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @return 
   */
  public Observable<String> mgetManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    mgetMany(keys, handler.toHandler());
    return handler;
  }

  /**
   * Atomically transfer a key from a Redis instance to another one.
   * @param host Destination host
   * @param port Destination port
   * @param key Key to migrate
   * @param destdb Destination database index
   * @param timeout 
   * @param options Migrate options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.migrate(host, port, key, destdb, timeout, options, handler);
    return this;
  }

  /**
   * Atomically transfer a key from a Redis instance to another one.
   * @param host Destination host
   * @param port Destination port
   * @param key Key to migrate
   * @param destdb Destination database index
   * @param timeout 
   * @param options Migrate options
   * @return 
   */
  public Observable<String> migrateObservable(String host, int port, String key, int destdb, long timeout, MigrateOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    migrate(host, port, key, destdb, timeout, options, handler.toHandler());
    return handler;
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
   * Listen for all requests received by the server in real time
   * @return 
   */
  public Observable<String> monitorObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    monitor(handler.toHandler());
    return handler;
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
   * Move a key to another database
   * @param key Key to migrate
   * @param destdb Destination database index
   * @return 
   */
  public Observable<String> moveObservable(String key, int destdb) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    move(key, destdb, handler.toHandler());
    return handler;
  }

  /**
   * Set multiple keys to multiple values
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction mset(JsonObject keyvals, Handler<AsyncResult<String>> handler) { 
    delegate.mset(keyvals, handler);
    return this;
  }

  /**
   * Set multiple keys to multiple values
   * @param keyvals Key value pairs to set
   * @return 
   */
  public Observable<String> msetObservable(JsonObject keyvals) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    mset(keyvals, handler.toHandler());
    return handler;
  }

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction msetnx(JsonObject keyvals, Handler<AsyncResult<String>> handler) { 
    delegate.msetnx(keyvals, handler);
    return this;
  }

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @param keyvals Key value pairs to set
   * @return 
   */
  public Observable<String> msetnxObservable(JsonObject keyvals) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    msetnx(keyvals, handler.toHandler());
    return handler;
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
   * Mark the start of a RedisTransaction block
   * @return 
   */
  public Observable<String> multiObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    multi(handler.toHandler());
    return handler;
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
   * Inspect the internals of Redis objects
   * @param key Key string
   * @param cmd Object sub command
   * @return 
   */
  public Observable<String> objectObservable(String key, ObjectCmd cmd) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    object(key, cmd, handler.toHandler());
    return handler;
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
   * Remove the expiration from a key
   * @param key Key string
   * @return 
   */
  public Observable<String> persistObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    persist(key, handler.toHandler());
    return handler;
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
   * Set a key's time to live in milliseconds
   * @param key String key
   * @param millis Time to live in milliseconds
   * @return 
   */
  public Observable<String> pexpireObservable(String key, long millis) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pexpire(key, millis, handler.toHandler());
    return handler;
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
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @param key Key string
   * @param millis Expiry time as Unix timestamp in milliseconds
   * @return 
   */
  public Observable<String> pexpireatObservable(String key, long millis) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pexpireat(key, millis, handler.toHandler());
    return handler;
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
   * Adds the specified element to the specified HyperLogLog.
   * @param key Key string
   * @param element Element to add
   * @return 
   */
  public Observable<String> pfaddObservable(String key, String element) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfadd(key, element, handler.toHandler());
    return handler;
  }

  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @param key Key string
   * @param elements Elementa to add
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfaddMany(String key, List<String> elements, Handler<AsyncResult<String>> handler) { 
    delegate.pfaddMany(key, elements, handler);
    return this;
  }

  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @param key Key string
   * @param elements Elementa to add
   * @return 
   */
  public Observable<String> pfaddManyObservable(String key, List<String> elements) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfaddMany(key, elements, handler.toHandler());
    return handler;
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
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   * @param key Key string
   * @return 
   */
  public Observable<String> pfcountObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfcount(key, handler.toHandler());
    return handler;
  }

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfcountMany(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.pfcountMany(keys, handler);
    return this;
  }

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @return 
   */
  public Observable<String> pfcountManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfcountMany(keys, handler.toHandler());
    return handler;
  }

  /**
   * Merge N different HyperLogLogs into a single one.
   * @param destkey Destination key
   * @param keys List of source keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.pfmerge(destkey, keys, handler);
    return this;
  }

  /**
   * Merge N different HyperLogLogs into a single one.
   * @param destkey Destination key
   * @param keys List of source keys
   * @return 
   */
  public Observable<String> pfmergeObservable(String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfmerge(destkey, keys, handler.toHandler());
    return handler;
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
   * Ping the server
   * @return 
   */
  public Observable<String> pingObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    ping(handler.toHandler());
    return handler;
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
   * Set the value and expiration in milliseconds of a key
   * @param key Key string
   * @param millis Number of milliseconds until the key expires
   * @param value New value for key
   * @return 
   */
  public Observable<String> psetexObservable(String key, long millis, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psetex(key, millis, value, handler.toHandler());
    return handler;
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
   * Listen for messages published to channels matching the given pattern
   * @param pattern Pattern string
   * @return 
   */
  public Observable<String> psubscribeObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psubscribe(pattern, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction psubscribeMany(List<String> patterns, Handler<AsyncResult<String>> handler) { 
    delegate.psubscribeMany(patterns, handler);
    return this;
  }

  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @return 
   */
  public Observable<String> psubscribeManyObservable(List<String> patterns) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psubscribeMany(patterns, handler.toHandler());
    return handler;
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
   * Lists the currently active channels - only those matching the pattern
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @return 
   */
  public Observable<String> pubsubChannelsObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubChannels(pattern, handler.toHandler());
    return handler;
  }

  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction pubsubNumsub(List<String> channels, Handler<AsyncResult<String>> handler) { 
    delegate.pubsubNumsub(channels, handler);
    return this;
  }

  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @return 
   */
  public Observable<String> pubsubNumsubObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubNumsub(channels, handler.toHandler());
    return handler;
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
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   * @return 
   */
  public Observable<String> pubsubNumpatObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubNumpat(handler.toHandler());
    return handler;
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
   * Get the time to live for a key in milliseconds
   * @param key Key string
   * @return 
   */
  public Observable<String> pttlObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pttl(key, handler.toHandler());
    return handler;
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
   * Post a message to a channel
   * @param channel Channel key
   * @param message Message to send to channel
   * @return 
   */
  public Observable<String> publishObservable(String channel, String message) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    publish(channel, message, handler.toHandler());
    return handler;
  }

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction punsubscribe(List<String> patterns, Handler<AsyncResult<String>> handler) { 
    delegate.punsubscribe(patterns, handler);
    return this;
  }

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @return 
   */
  public Observable<String> punsubscribeObservable(List<String> patterns) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    punsubscribe(patterns, handler.toHandler());
    return handler;
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
   * Return a random key from the keyspace
   * @return 
   */
  public Observable<String> randomkeyObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    randomkey(handler.toHandler());
    return handler;
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
   * Rename a key
   * @param key Key string to be renamed
   * @param newkey New key string
   * @return 
   */
  public Observable<String> renameObservable(String key, String newkey) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rename(key, newkey, handler.toHandler());
    return handler;
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
   * Rename a key, only if the new key does not exist
   * @param key Key string to be renamed
   * @param newkey New key string
   * @return 
   */
  public Observable<String> renamenxObservable(String key, String newkey) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    renamenx(key, newkey, handler.toHandler());
    return handler;
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
   * Create a key using the provided serialized value, previously obtained using DUMP.
   * @param key Key string
   * @param millis Expiry time in milliseconds to set on the key
   * @param serialized Serialized form of the key value as obtained using DUMP
   * @return 
   */
  public Observable<String> restoreObservable(String key, long millis, String serialized) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    restore(key, millis, serialized, handler.toHandler());
    return handler;
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
   * Return the role of the instance in the context of replication
   * @return 
   */
  public Observable<String> roleObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    role(handler.toHandler());
    return handler;
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
   * Remove and get the last element in a list
   * @param key Key string
   * @return 
   */
  public Observable<String> rpopObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rpop(key, handler.toHandler());
    return handler;
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
   * Remove the last element in a list, append it to another list and return it
   * @param key Key string identifying source list
   * @param destkey Key string identifying destination list
   * @return 
   */
  public Observable<String> rpoplpushObservable(String key, String destkey) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rpoplpush(key, destkey, handler.toHandler());
    return handler;
  }

  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param values List of values to add to the end of the list
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction rpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) { 
    delegate.rpushMany(key, values, handler);
    return this;
  }

  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param values List of values to add to the end of the list
   * @return 
   */
  public Observable<String> rpushManyObservable(String key, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rpushMany(key, values, handler.toHandler());
    return handler;
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
   * Append one or multiple values to a list
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @return 
   */
  public Observable<String> rpushObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rpush(key, value, handler.toHandler());
    return handler;
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
   * Append a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @return 
   */
  public Observable<String> rpushxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    rpushx(key, value, handler.toHandler());
    return handler;
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
   * Add a member to a set
   * @param key Key string
   * @param member Value to be added to the set
   * @return 
   */
  public Observable<String> saddObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sadd(key, member, handler.toHandler());
    return handler;
  }

  /**
   * Add one or more members to a set
   * @param key Key string
   * @param members Values to be added to the set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction saddMany(String key, List<String> members, Handler<AsyncResult<String>> handler) { 
    delegate.saddMany(key, members, handler);
    return this;
  }

  /**
   * Add one or more members to a set
   * @param key Key string
   * @param members Values to be added to the set
   * @return 
   */
  public Observable<String> saddManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    saddMany(key, members, handler.toHandler());
    return handler;
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
   * Synchronously save the dataset to disk
   * @return 
   */
  public Observable<String> saveObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    save(handler.toHandler());
    return handler;
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
   * Get the number of members in a set
   * @param key Key string
   * @return 
   */
  public Observable<String> scardObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scard(key, handler.toHandler());
    return handler;
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
   * Check existence of script in the script cache.
   * @param script SHA1 digest identifying a script in the script cache
   * @return 
   */
  public Observable<String> scriptExistsObservable(String script) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptExists(script, handler.toHandler());
    return handler;
  }

  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scriptExistsMany(List<String> scripts, Handler<AsyncResult<String>> handler) { 
    delegate.scriptExistsMany(scripts, handler);
    return this;
  }

  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @return 
   */
  public Observable<String> scriptExistsManyObservable(List<String> scripts) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptExistsMany(scripts, handler.toHandler());
    return handler;
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
   * Remove all the scripts from the script cache.
   * @return 
   */
  public Observable<String> scriptFlushObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptFlush(handler.toHandler());
    return handler;
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
   * Kill the script currently in execution.
   * @return 
   */
  public Observable<String> scriptKillObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptKill(handler.toHandler());
    return handler;
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
   * Load the specified Lua script into the script cache.
   * @param script Lua script
   * @return 
   */
  public Observable<String> scriptLoadObservable(String script) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptLoad(script, handler.toHandler());
    return handler;
  }

  /**
   * Subtract multiple sets
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler) { 
    delegate.sdiff(key, cmpkeys, handler);
    return this;
  }

  /**
   * Subtract multiple sets
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @return 
   */
  public Observable<String> sdiffObservable(String key, List<String> cmpkeys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sdiff(key, cmpkeys, handler.toHandler());
    return handler;
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
    delegate.sdiffstore(destkey, key, cmpkeys, handler);
    return this;
  }

  /**
   * Subtract multiple sets and store the resulting set in a key
   * @param destkey Destination key where the result should be stored
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @return 
   */
  public Observable<String> sdiffstoreObservable(String destkey, String key, List<String> cmpkeys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sdiffstore(destkey, key, cmpkeys, handler.toHandler());
    return handler;
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
   * Change the selected database for the current connection
   * @param dbindex Index identifying the new active database
   * @return 
   */
  public Observable<String> selectObservable(int dbindex) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    select(dbindex, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> setObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    set(key, value, handler.toHandler());
    return handler;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.setWithOptions(key, value, options, handler);
    return this;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options
   * @return 
   */
  public Observable<String> setWithOptionsObservable(String key, String value, SetOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setWithOptions(key, value, options, handler.toHandler());
    return handler;
  }

  /**
   * Set the binary string value of a key - without encoding as utf-8
   * @param key Key of which value to set
   * @param value New value for the key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setBinary(String key, Buffer value, Handler<AsyncResult<String>> handler) { 
    delegate.setBinary(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), handler);
    return this;
  }

  /**
   * Set the binary string value of a key - without encoding as utf-8
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<String> setBinaryObservable(String key, Buffer value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setBinary(key, value, handler.toHandler());
    return handler;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction setBinaryWithOptions(String key, Buffer value, SetOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.setBinaryWithOptions(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), options, handler);
    return this;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options
   * @return 
   */
  public Observable<String> setBinaryWithOptionsObservable(String key, Buffer value, SetOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setBinaryWithOptions(key, value, options, handler.toHandler());
    return handler;
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
   * Sets or clears the bit at offset in the string value stored at key
   * @param key Key string
   * @param offset Bit offset
   * @param bit New value - must be 1 or 0
   * @return 
   */
  public Observable<String> setbitObservable(String key, long offset, int bit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setbit(key, offset, bit, handler.toHandler());
    return handler;
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
   * Set the value and expiration of a key
   * @param key Key string
   * @param seconds Number of seconds until the key expires
   * @param value New value for key
   * @return 
   */
  public Observable<String> setexObservable(String key, long seconds, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setex(key, seconds, value, handler.toHandler());
    return handler;
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
   * Set the value of a key, only if the key does not exist
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<String> setnxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setnx(key, value, handler.toHandler());
    return handler;
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
   * Overwrite part of a string at key starting at the specified offset
   * @param key Key string
   * @param offset Offset - the maximum offset that you can set is 2^29 -1 (536870911), as Redis Strings are limited to 512 megabytes
   * @param value Value to overwrite with
   * @return 
   */
  public Observable<String> setrangeObservable(String key, int offset, String value) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setrange(key, offset, value, handler.toHandler());
    return handler;
  }

  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sinter(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.sinter(keys, handler);
    return this;
  }

  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @return 
   */
  public Observable<String> sinterObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sinter(keys, handler.toHandler());
    return handler;
  }

  /**
   * Intersect multiple sets and store the resulting set in a key
   * @param destkey Key where to store the results
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sinterstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.sinterstore(destkey, keys, handler);
    return this;
  }

  /**
   * Intersect multiple sets and store the resulting set in a key
   * @param destkey Key where to store the results
   * @param keys List of keys to perform intersection on
   * @return 
   */
  public Observable<String> sinterstoreObservable(String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sinterstore(destkey, keys, handler.toHandler());
    return handler;
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
   * Determine if a given value is a member of a set
   * @param key Key string
   * @param member Member to look for
   * @return 
   */
  public Observable<String> sismemberObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sismember(key, member, handler.toHandler());
    return handler;
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
   * Make the server a slave of another instance
   * @param host Host to become this server's master
   * @param port Port of our new master
   * @return 
   */
  public Observable<String> slaveofObservable(String host, int port) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slaveof(host, port, handler.toHandler());
    return handler;
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
   * Make this server a master
   * @return 
   */
  public Observable<String> slaveofNooneObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slaveofNoone(handler.toHandler());
    return handler;
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
   * Read the Redis slow queries log
   * @param limit Number of log entries to return. If value is less than zero all entries are returned
   * @return 
   */
  public Observable<String> slowlogGetObservable(int limit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogGet(limit, handler.toHandler());
    return handler;
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
   * Get the length of the Redis slow queries log
   * @return 
   */
  public Observable<String> slowlogLenObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogLen(handler.toHandler());
    return handler;
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
   * Reset the Redis slow queries log
   * @return 
   */
  public Observable<String> slowlogResetObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogReset(handler.toHandler());
    return handler;
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
   * Get all the members in a set
   * @param key Key string
   * @return 
   */
  public Observable<String> smembersObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    smembers(key, handler.toHandler());
    return handler;
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
   * Move a member from one set to another
   * @param key Key of source set currently containing the member
   * @param destkey Key identifying the destination set
   * @param member Member to move
   * @return 
   */
  public Observable<String> smoveObservable(String key, String destkey, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    smove(key, destkey, member, handler.toHandler());
    return handler;
  }

  /**
   * Sort the elements in a list, set or sorted set
   * @param key Key string
   * @param options Sort options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sort(String key, SortOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.sort(key, options, handler);
    return this;
  }

  /**
   * Sort the elements in a list, set or sorted set
   * @param key Key string
   * @param options Sort options
   * @return 
   */
  public Observable<String> sortObservable(String key, SortOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sort(key, options, handler.toHandler());
    return handler;
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
   * Remove and return a random member from a set
   * @param key Key string
   * @return 
   */
  public Observable<String> spopObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    spop(key, handler.toHandler());
    return handler;
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
   * Remove and return random members from a set
   * @param key Key string
   * @param count Number of members to remove
   * @return 
   */
  public Observable<String> spopManyObservable(String key, int count) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    spopMany(key, count, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> srandmemberObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    srandmember(key, handler.toHandler());
    return handler;
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
   * Get one or multiple random members from a set
   * @param key Key string
   * @param count Number of members to get
   * @return 
   */
  public Observable<String> srandmemberCountObservable(String key, int count) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    srandmemberCount(key, count, handler.toHandler());
    return handler;
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
   * Remove one member from a set
   * @param key Key string
   * @param member Member to remove
   * @return 
   */
  public Observable<String> sremObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    srem(key, member, handler.toHandler());
    return handler;
  }

  /**
   * Remove one or more members from a set
   * @param key Key string
   * @param members Members to remove
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) { 
    delegate.sremMany(key, members, handler);
    return this;
  }

  /**
   * Remove one or more members from a set
   * @param key Key string
   * @param members Members to remove
   * @return 
   */
  public Observable<String> sremManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sremMany(key, members, handler.toHandler());
    return handler;
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
   * Get the length of the value stored in a key
   * @param key Key string
   * @return 
   */
  public Observable<String> strlenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    strlen(key, handler.toHandler());
    return handler;
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
   * @param channel Channel to subscribe to
   * @return 
   */
  public Observable<String> subscribeObservable(String channel) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    subscribe(channel, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction subscribeMany(List<String> channels, Handler<AsyncResult<String>> handler) { 
    delegate.subscribeMany(channels, handler);
    return this;
  }

  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @return 
   */
  public Observable<String> subscribeManyObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    subscribeMany(channels, handler.toHandler());
    return handler;
  }

  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sunion(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.sunion(keys, handler);
    return this;
  }

  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @return 
   */
  public Observable<String> sunionObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sunion(keys, handler.toHandler());
    return handler;
  }

  /**
   * Add multiple sets and store the resulting set in a key
   * @param destkey Destination key
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sunionstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.sunionstore(destkey, keys, handler);
    return this;
  }

  /**
   * Add multiple sets and store the resulting set in a key
   * @param destkey Destination key
   * @param keys List of keys identifying sets to add up
   * @return 
   */
  public Observable<String> sunionstoreObservable(String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sunionstore(destkey, keys, handler.toHandler());
    return handler;
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
   * Internal command used for replication
   * @return 
   */
  public Observable<String> syncObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sync(handler.toHandler());
    return handler;
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
   * Return the current server time
   * @return 
   */
  public Observable<String> timeObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    time(handler.toHandler());
    return handler;
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
   * Get the time to live for a key
   * @param key Key string
   * @return 
   */
  public Observable<String> ttlObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    ttl(key, handler.toHandler());
    return handler;
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
   * Determine the type stored at key
   * @param key Key string
   * @return 
   */
  public Observable<String> typeObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    type(key, handler.toHandler());
    return handler;
  }

  /**
   * Stop listening for messages posted to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction unsubscribe(List<String> channels, Handler<AsyncResult<String>> handler) { 
    delegate.unsubscribe(channels, handler);
    return this;
  }

  /**
   * Stop listening for messages posted to the given channels
   * @param channels List of channels to subscribe to
   * @return 
   */
  public Observable<String> unsubscribeObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    unsubscribe(channels, handler.toHandler());
    return handler;
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
   * Forget about all watched keys
   * @return 
   */
  public Observable<String> unwatchObservable() { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    unwatch(handler.toHandler());
    return handler;
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
   * Wait for the synchronous replication of all the write commands sent in the context of the current connection.
   * @param numSlaves 
   * @param timeout 
   * @return 
   */
  public Observable<String> waitObservable(long numSlaves, long timeout) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    wait(numSlaves, timeout, handler.toHandler());
    return handler;
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
   * @param key Key to watch
   * @return 
   */
  public Observable<String> watchObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    watch(key, handler.toHandler());
    return handler;
  }

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param keys List of keys to watch
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction watchMany(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.watchMany(keys, handler);
    return this;
  }

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @param keys List of keys to watch
   * @return 
   */
  public Observable<String> watchManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    watchMany(keys, handler.toHandler());
    return handler;
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
   * @param score Score used for sorting
   * @param member New member key
   * @return 
   */
  public Observable<String> zaddObservable(String key, double score, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zadd(key, score, member, handler.toHandler());
    return handler;
  }

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param members New member keys and their scores
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zaddMany(String key, Map<String,Double> members, Handler<AsyncResult<String>> handler) { 
    delegate.zaddMany(key, members, handler);
    return this;
  }

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param members New member keys and their scores
   * @return 
   */
  public Observable<String> zaddManyObservable(String key, Map<String,Double> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zaddMany(key, members, handler.toHandler());
    return handler;
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
   * Get the number of members in a sorted set
   * @param key Key string
   * @return 
   */
  public Observable<String> zcardObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zcard(key, handler.toHandler());
    return handler;
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
   * Count the members in a sorted set with scores within the given values
   * @param key Key string
   * @param min Minimum score
   * @param max Maximum score
   * @return 
   */
  public Observable<String> zcountObservable(String key, double min, double max) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zcount(key, min, max, handler.toHandler());
    return handler;
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
   * Increment the score of a member in a sorted set
   * @param key Key string
   * @param increment Increment amount
   * @param member Member key
   * @return 
   */
  public Observable<String> zincrbyObservable(String key, double increment, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zincrby(key, increment, member, handler.toHandler());
    return handler;
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
    delegate.zinterstore(destkey, sets, options, handler);
    return this;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @return 
   */
  public Observable<String> zinterstoreObservable(String destkey, List<String> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zinterstore(destkey, sets, options, handler.toHandler());
    return handler;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zinterstoreWeighed(String destkey, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zinterstoreWeighed(destkey, sets, options, handler);
    return this;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @return 
   */
  public Observable<String> zinterstoreWeighedObservable(String destkey, Map<String,Double> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zinterstoreWeighed(destkey, sets, options, handler.toHandler());
    return handler;
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
   * Count the number of members in a sorted set between a given lexicographical range
   * @param key Key string
   * @param min Pattern to compare against for minimum value
   * @param max Pattern to compare against for maximum value
   * @return 
   */
  public Observable<String> zlexcountObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zlexcount(key, min, max, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> zrangeObservable(String key, long start, long stop) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrange(key, start, stop, handler.toHandler());
    return handler;
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
   * Return a range of members in a sorted set, by index
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @param options Range options
   * @return 
   */
  public Observable<String> zrangeWithOptionsObservable(String key, long start, long stop, RangeOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrangeWithOptions(key, start, stop, options, handler.toHandler());
    return handler;
  }

  /**
   * Return a range of members in a sorted set, by lexicographical range
   * @param key Key string
   * @param min Pattern representing a minimum allowed value
   * @param max Pattern representing a maximum allowed value
   * @param options Limit options where limit can be specified
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zrangebylex(key, min, max, options, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by lexicographical range
   * @param key Key string
   * @param min Pattern representing a minimum allowed value
   * @param max Pattern representing a maximum allowed value
   * @param options Limit options where limit can be specified
   * @return 
   */
  public Observable<String> zrangebylexObservable(String key, String min, String max, LimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrangebylex(key, min, max, options, handler.toHandler());
    return handler;
  }

  /**
   * Return a range of members in a sorted set, by score
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @param options Range and limit options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zrangebyscore(key, min, max, options, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by score
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @param options Range and limit options
   * @return 
   */
  public Observable<String> zrangebyscoreObservable(String key, String min, String max, RangeLimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrangebyscore(key, min, max, options, handler.toHandler());
    return handler;
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
   * Determine the index of a member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<String> zrankObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrank(key, member, handler.toHandler());
    return handler;
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
   * Remove one member from a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<String> zremObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrem(key, member, handler.toHandler());
    return handler;
  }

  /**
   * Remove one or more members from a sorted set
   * @param key Key string
   * @param members Members in the sorted set identified by key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) { 
    delegate.zremMany(key, members, handler);
    return this;
  }

  /**
   * Remove one or more members from a sorted set
   * @param key Key string
   * @param members Members in the sorted set identified by key
   * @return 
   */
  public Observable<String> zremManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zremMany(key, members, handler.toHandler());
    return handler;
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
   * Remove all members in a sorted set between the given lexicographical range
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @return 
   */
  public Observable<String> zremrangebylexObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zremrangebylex(key, min, max, handler.toHandler());
    return handler;
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
   * Remove all members in a sorted set within the given indexes
   * @param key Key string
   * @param start Start index
   * @param stop Stop index
   * @return 
   */
  public Observable<String> zremrangebyrankObservable(String key, long start, long stop) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zremrangebyrank(key, start, stop, handler.toHandler());
    return handler;
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
   * Remove all members in a sorted set within the given scores
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @return 
   */
  public Observable<String> zremrangebyscoreObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zremrangebyscore(key, min, max, handler.toHandler());
    return handler;
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
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @param options Range options
   * @return 
   */
  public Observable<String> zrevrangeObservable(String key, long start, long stop, RangeOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrevrange(key, start, stop, options, handler.toHandler());
    return handler;
  }

  /**
   * Return a range of members in a sorted set, by score, between the given lexicographical range with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Limit options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zrevrangebylex(key, max, min, options, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by score, between the given lexicographical range with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Limit options
   * @return 
   */
  public Observable<String> zrevrangebylexObservable(String key, String max, String min, LimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrevrangebylex(key, max, min, options, handler.toHandler());
    return handler;
  }

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Range and limit options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zrevrangebyscore(key, max, min, options, handler);
    return this;
  }

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * @param key Key string
   * @param max Pattern defining a maximum value
   * @param min Pattern defining a minimum value
   * @param options Range and limit options
   * @return 
   */
  public Observable<String> zrevrangebyscoreObservable(String key, String max, String min, RangeLimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrevrangebyscore(key, max, min, options, handler.toHandler());
    return handler;
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
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<String> zrevrankObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zrevrank(key, member, handler.toHandler());
    return handler;
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
   * Get the score associated with the given member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<String> zscoreObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zscore(key, member, handler.toHandler());
    return handler;
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
    delegate.zunionstore(destkey, sets, options, handler);
    return this;
  }

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets
   * @param options Aggregation options
   * @return 
   */
  public Observable<String> zunionstoreObservable(String destkey, List<String> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zunionstore(destkey, sets, options, handler.toHandler());
    return handler;
  }

  /**
   * Add multiple sorted sets using weights, and store the resulting sorted set in a new key
   * @param key Destination key
   * @param sets Map containing set-key:weight pairs
   * @param options Aggregation options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zunionstoreWeighed(String key, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zunionstoreWeighed(key, sets, options, handler);
    return this;
  }

  /**
   * Add multiple sorted sets using weights, and store the resulting sorted set in a new key
   * @param key Destination key
   * @param sets Map containing set-key:weight pairs
   * @param options Aggregation options
   * @return 
   */
  public Observable<String> zunionstoreWeighedObservable(String key, Map<String,Double> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zunionstoreWeighed(key, sets, options, handler.toHandler());
    return handler;
  }

  /**
   * Incrementally iterate the keys space
   * @param cursor Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction scan(String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.scan(cursor, options, handler);
    return this;
  }

  /**
   * Incrementally iterate the keys space
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<String> scanObservable(String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scan(cursor, options, handler.toHandler());
    return handler;
  }

  /**
   * Incrementally iterate Set elements
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.sscan(key, cursor, options, handler);
    return this;
  }

  /**
   * Incrementally iterate Set elements
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<String> sscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sscan(key, cursor, options, handler.toHandler());
    return handler;
  }

  /**
   * Incrementally iterate hash fields and associated values
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.hscan(key, cursor, options, handler);
    return this;
  }

  /**
   * Incrementally iterate hash fields and associated values
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<String> hscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hscan(key, cursor, options, handler.toHandler());
    return handler;
  }

  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.zscan(key, cursor, options, handler);
    return this;
  }

  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<String> zscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zscan(key, cursor, options, handler.toHandler());
    return handler;
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
   * @param longitude longitude
   * @param latitude latitude
   * @param member member
   * @return 
   */
  public Observable<String> geoaddObservable(String key, double longitude, double latitude, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geoadd(key, longitude, latitude, member, handler.toHandler());
    return handler;
  }

  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   * @param key Key string
   * @param members list of &lt;lon, lat, member&gt;
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction geoaddMany(String key, List<GeoMember> members, Handler<AsyncResult<String>> handler) { 
    delegate.geoaddMany(key, members, handler);
    return this;
  }

  /**
   * Add one or more geospatial items in the geospatial index represented using a sorted set.
   * @param key Key string
   * @param members list of &lt;lon, lat, member&gt;
   * @return 
   */
  public Observable<String> geoaddManyObservable(String key, List<GeoMember> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geoaddMany(key, members, handler.toHandler());
    return handler;
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
   * @param member member
   * @return 
   */
  public Observable<String> geohashObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geohash(key, member, handler.toHandler());
    return handler;
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
    delegate.geohashMany(key, members, handler);
    return this;
  }

  /**
   * Return valid Geohash strings representing the position of one or more elements in a sorted set value representing
   * a geospatial index (where elements were added using GEOADD).
   * @param key Key string
   * @param members list of members
   * @return 
   */
  public Observable<String> geohashManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geohashMany(key, members, handler.toHandler());
    return handler;
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
   * @param member member
   * @return 
   */
  public Observable<String> geoposObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geopos(key, member, handler.toHandler());
    return handler;
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
    delegate.geoposMany(key, members, handler);
    return this;
  }

  /**
   * Return the positions (longitude,latitude) of all the specified members of the geospatial index represented by the
   * sorted set at key.
   * @param key Key string
   * @param members list of members
   * @return 
   */
  public Observable<String> geoposManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geoposMany(key, members, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> geodistObservable(String key, String member1, String member2) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geodist(key, member1, member2, handler.toHandler());
    return handler;
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
   * Return the distance between two members in the geospatial index represented by the sorted set.
   * @param key Key string
   * @param member1 member 1
   * @param member2 member 2
   * @param unit geo unit
   * @return 
   */
  public Observable<String> geodistWithUnitObservable(String key, String member1, String member2, GeoUnit unit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    geodistWithUnit(key, member1, member2, unit, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> georadiusObservable(String key, double longitude, double latitude, double radius, GeoUnit unit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    georadius(key, longitude, latitude, radius, unit, handler.toHandler());
    return handler;
  }

  /**
   * Return the members of a sorted set populated with geospatial information using GEOADD, which are within the borders
   * of the area specified with the center location and the maximum distance from the center (the radius).
   * @param key Key string
   * @param longitude longitude
   * @param latitude latitude
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadiusWithOptions(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.georadiusWithOptions(key, longitude, latitude, radius, unit, options, handler);
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
   * @param options geo radius options
   * @return 
   */
  public Observable<String> georadiusWithOptionsObservable(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    georadiusWithOptions(key, longitude, latitude, radius, unit, options, handler.toHandler());
    return handler;
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
   * @return 
   */
  public Observable<String> georadiusbymemberObservable(String key, String member, double radius, GeoUnit unit) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    georadiusbymember(key, member, radius, unit, handler.toHandler());
    return handler;
  }

  /**
   * This command is exactly like GEORADIUS with the sole difference that instead of taking, as the center of the area
   * to query, a longitude and latitude value, it takes the name of a member already existing inside the geospatial
   * index represented by the sorted set.
   * @param key Key string
   * @param member member
   * @param radius radius
   * @param unit geo unit
   * @param options geo radius options
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisTransaction georadiusbymemberWithOptions(String key, String member, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.georadiusbymemberWithOptions(key, member, radius, unit, options, handler);
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
   * @param options geo radius options
   * @return 
   */
  public Observable<String> georadiusbymemberWithOptionsObservable(String key, String member, double radius, GeoUnit unit, GeoRadiusOptions options) { 
    io.vertx.rx.java.ObservableFuture<String> handler = io.vertx.rx.java.RxHelper.observableFuture();
    georadiusbymemberWithOptions(key, member, radius, unit, options, handler.toHandler());
    return handler;
  }


  public static RedisTransaction newInstance(io.vertx.redis.RedisTransaction arg) {
    return arg != null ? new RedisTransaction(arg) : null;
  }
}
