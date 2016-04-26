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
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.redis.op.RangeOptions;
import io.vertx.redis.op.InsertOptions;
import io.vertx.redis.op.AggregateOptions;
import io.vertx.redis.op.SetOptions;
import io.vertx.redis.op.SortOptions;
import io.vertx.redis.op.MigrateOptions;
import io.vertx.redis.op.ScanOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.redis.op.FailoverOptions;
import io.vertx.redis.op.SlotCmd;
import io.vertx.redis.op.RangeLimitOptions;
import io.vertx.redis.op.LimitOptions;
import io.vertx.core.json.JsonArray;
import java.util.List;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.redis.RedisOptions;
import io.vertx.core.Handler;


public class RedisClient {

  final io.vertx.redis.RedisClient delegate;

  public RedisClient(io.vertx.redis.RedisClient delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static RedisClient create(Vertx vertx) { 
    RedisClient ret = RedisClient.newInstance(io.vertx.redis.RedisClient.create((io.vertx.core.Vertx)vertx.getDelegate()));
    return ret;
  }

  public static RedisClient create(Vertx vertx, RedisOptions config) { 
    RedisClient ret = RedisClient.newInstance(io.vertx.redis.RedisClient.create((io.vertx.core.Vertx)vertx.getDelegate(), config));
    return ret;
  }

  /**
   * Close the client - when it is fully closed the handler will be called.
   * @param handler 
   */
  public void close(Handler<AsyncResult<Void>> handler) { 
    delegate.close(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient append(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.append(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Append a value to a key
   * @param key Key string
   * @param value Value to append
   * @return 
   */
  public Observable<Long> appendObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    append(key, value, handler.toHandler());
    return handler;
  }

  /**
   * Authenticate to the server
   * @param password Password for authentication
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient auth(String password, Handler<AsyncResult<String>> handler) { 
    delegate.auth(password, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient bgrewriteaof(Handler<AsyncResult<String>> handler) { 
    delegate.bgrewriteaof(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient bgsave(Handler<AsyncResult<String>> handler) { 
    delegate.bgsave(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient bitcount(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.bitcount(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Count set bits in a string
   * @param key Key string
   * @return 
   */
  public Observable<Long> bitcountObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) { 
    delegate.bitcountRange(key, start, end, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Count set bits in a string
   * @param key Key string
   * @param start Start index
   * @param end End index
   * @return 
   */
  public Observable<Long> bitcountRangeObservable(String key, long start, long end) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) { 
    delegate.bitop(operation, destkey, keys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Perform bitwise operations between strings
   * @param operation Bitwise operation to perform
   * @param destkey Destination key where result is stored
   * @param keys List of keys on which to perform the operation
   * @return 
   */
  public Observable<Long> bitopObservable(BitOperation operation, String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) { 
    delegate.bitpos(key, bit, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Find first bit set or clear in a string
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @return 
   */
  public Observable<Long> bitposObservable(String key, int bit) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitpos(key, bit, handler.toHandler());
    return handler;
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
    delegate.bitposFrom(key, bit, start, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Find first bit set or clear in a string
   *
   * See also bitposRange() method, which takes start, and stop offset.
   * @param key Key string
   * @param bit What bit value to look for - must be 1, or 0
   * @param start Start offset
   * @return 
   */
  public Observable<Long> bitposFromObservable(String key, int bit, int start) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    bitposFrom(key, bit, start, handler.toHandler());
    return handler;
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
    delegate.bitposRange(key, bit, start, stop, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
   * @return 
   */
  public Observable<Long> bitposRangeObservable(String key, int bit, int start, int stop) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient blpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.blpop(key, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove and get the first element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<JsonArray> blpopObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient blpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.blpopMany(keys, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove and get the first element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<JsonArray> blpopManyObservable(List<String> keys, int seconds) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient brpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.brpop(key, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove and get the last element in a list, or block until one is available
   * @param key Key string identifying a list to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<JsonArray> brpopObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient brpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.brpopMany(keys, seconds, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove and get the last element in any of the lists, or block until one is available
   * @param keys List of key strings identifying lists to watch
   * @param seconds Timeout in seconds
   * @return 
   */
  public Observable<JsonArray> brpopManyObservable(List<String> keys, int seconds) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) { 
    delegate.brpoplpush(key, destkey, seconds, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient clientKill(KillFilter filter, Handler<AsyncResult<Long>> handler) { 
    delegate.clientKill(filter, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Kill the connection of a client
   * @param filter Filter options
   * @return 
   */
  public Observable<Long> clientKillObservable(KillFilter filter) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clientKill(filter, handler.toHandler());
    return handler;
  }

  /**
   * Get the list of client connections
   * @param handler 
   * @return 
   */
  public RedisClient clientList(Handler<AsyncResult<String>> handler) { 
    delegate.clientList(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient clientGetname(Handler<AsyncResult<String>> handler) { 
    delegate.clientGetname(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient clientPause(long millis, Handler<AsyncResult<String>> handler) { 
    delegate.clientPause(millis, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient clientSetname(String name, Handler<AsyncResult<String>> handler) { 
    delegate.clientSetname(name, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient clusterAddslots(List<Long> slots, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterAddslots(slots, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Assign new hash slots to receiving node.
   * @param slots 
   * @return 
   */
  public Observable<Void> clusterAddslotsObservable(List<Long> slots) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterAddslots(slots, handler.toHandler());
    return handler;
  }

  /**
   * Return the number of failure reports active for a given node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterCountFailureReports(String nodeId, Handler<AsyncResult<Long>> handler) { 
    delegate.clusterCountFailureReports(nodeId, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the number of failure reports active for a given node.
   * @param nodeId 
   * @return 
   */
  public Observable<Long> clusterCountFailureReportsObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterCountFailureReports(nodeId, handler.toHandler());
    return handler;
  }

  /**
   * Return the number of local keys in the specified hash slot.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterCountkeysinslot(long slot, Handler<AsyncResult<Long>> handler) { 
    delegate.clusterCountkeysinslot(slot, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the number of local keys in the specified hash slot.
   * @param slot 
   * @return 
   */
  public Observable<Long> clusterCountkeysinslotObservable(long slot) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterCountkeysinslot(slot, handler.toHandler());
    return handler;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slot 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterDelslots(long slot, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterDelslots(slot, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slot 
   * @return 
   */
  public Observable<Void> clusterDelslotsObservable(long slot) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterDelslots(slot, handler.toHandler());
    return handler;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterDelslotsMany(List<Long> slots, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterDelslotsMany(slots, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set hash slots as unbound in receiving node.
   * @param slots 
   * @return 
   */
  public Observable<Void> clusterDelslotsManyObservable(List<Long> slots) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterDelslotsMany(slots, handler.toHandler());
    return handler;
  }

  /**
   * Forces a slave to perform a manual failover of its master.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterFailover(Handler<AsyncResult<Void>> handler) { 
    delegate.clusterFailover(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Forces a slave to perform a manual failover of its master.
   * @return 
   */
  public Observable<Void> clusterFailoverObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterFailover(handler.toHandler());
    return handler;
  }

  /**
   * Forces a slave to perform a manual failover of its master.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterFailOverWithOptions(options, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Forces a slave to perform a manual failover of its master.
   * @param options 
   * @return 
   */
  public Observable<Void> clusterFailOverWithOptionsObservable(FailoverOptions options) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterFailOverWithOptions(options, handler.toHandler());
    return handler;
  }

  /**
   * Remove a node from the nodes table.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterForget(String nodeId, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterForget(nodeId, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove a node from the nodes table.
   * @param nodeId 
   * @return 
   */
  public Observable<Void> clusterForgetObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.clusterGetkeysinslot(slot, count, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return local key names in the specified hash slot.
   * @param slot 
   * @param count 
   * @return 
   */
  public Observable<JsonArray> clusterGetkeysinslotObservable(long slot, long count) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterGetkeysinslot(slot, count, handler.toHandler());
    return handler;
  }

  /**
   * Provides info about Redis Cluster node state.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterInfo(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.clusterInfo(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Provides info about Redis Cluster node state.
   * @return 
   */
  public Observable<JsonArray> clusterInfoObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterInfo(handler.toHandler());
    return handler;
  }

  /**
   * Returns the hash slot of the specified key.
   * @param key 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterKeyslot(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.clusterKeyslot(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Returns the hash slot of the specified key.
   * @param key 
   * @return 
   */
  public Observable<Long> clusterKeyslotObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient clusterMeet(String ip, long port, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterMeet(ip, port, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Force a node cluster to handshake with another node.
   * @param ip 
   * @param port 
   * @return 
   */
  public Observable<Void> clusterMeetObservable(String ip, long port) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterMeet(ip, port, handler.toHandler());
    return handler;
  }

  /**
   * Get Cluster config for the node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterNodes(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.clusterNodes(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get Cluster config for the node.
   * @return 
   */
  public Observable<JsonArray> clusterNodesObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterNodes(handler.toHandler());
    return handler;
  }

  /**
   * Reconfigure a node as a slave of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterReplicate(String nodeId, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterReplicate(nodeId, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Reconfigure a node as a slave of the specified master node.
   * @param nodeId 
   * @return 
   */
  public Observable<Void> clusterReplicateObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterReplicate(nodeId, handler.toHandler());
    return handler;
  }

  /**
   * Reset a Redis Cluster node.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterReset(Handler<AsyncResult<Void>> handler) { 
    delegate.clusterReset(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Reset a Redis Cluster node.
   * @return 
   */
  public Observable<Void> clusterResetObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterReset(handler.toHandler());
    return handler;
  }

  /**
   * Reset a Redis Cluster node.
   * @param options 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterResetWithOptions(options, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Reset a Redis Cluster node.
   * @param options 
   * @return 
   */
  public Observable<Void> clusterResetWithOptionsObservable(ResetOptions options) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterResetWithOptions(options, handler.toHandler());
    return handler;
  }

  /**
   * Forces the node to save cluster state on disk.
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSaveconfig(Handler<AsyncResult<Void>> handler) { 
    delegate.clusterSaveconfig(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Forces the node to save cluster state on disk.
   * @return 
   */
  public Observable<Void> clusterSaveconfigObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSaveconfig(handler.toHandler());
    return handler;
  }

  /**
   * Set the configuration epoch in a new node.
   * @param epoch 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSetConfigEpoch(long epoch, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterSetConfigEpoch(epoch, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the configuration epoch in a new node.
   * @param epoch 
   * @return 
   */
  public Observable<Void> clusterSetConfigEpochObservable(long epoch) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterSetslot(slot, subcommand, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @return 
   */
  public Observable<Void> clusterSetslotObservable(long slot, SlotCmd subcommand) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<Void>> handler) { 
    delegate.clusterSetslotWithNode(slot, subcommand, nodeId, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Bind an hash slot to a specific node.
   * @param slot 
   * @param subcommand 
   * @param nodeId 
   * @return 
   */
  public Observable<Void> clusterSetslotWithNodeObservable(long slot, SlotCmd subcommand, String nodeId) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSetslotWithNode(slot, subcommand, nodeId, handler.toHandler());
    return handler;
  }

  /**
   * List slave nodes of the specified master node.
   * @param nodeId 
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient clusterSlaves(String nodeId, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.clusterSlaves(nodeId, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * List slave nodes of the specified master node.
   * @param nodeId 
   * @return 
   */
  public Observable<JsonArray> clusterSlavesObservable(String nodeId) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSlaves(nodeId, handler.toHandler());
    return handler;
  }

  /**
   * Get array of Cluster slot to node mappings
   * @param handler 
   * @return 
   */
  public RedisClient clusterSlots(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.clusterSlots(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get array of Cluster slot to node mappings
   * @return 
   */
  public Observable<JsonArray> clusterSlotsObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    clusterSlots(handler.toHandler());
    return handler;
  }

  /**
   * Get array of Redis command details
   * @param handler 
   * @return 
   */
  public RedisClient command(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.command(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get array of Redis command details
   * @return 
   */
  public Observable<JsonArray> commandObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    command(handler.toHandler());
    return handler;
  }

  /**
   * Get total number of Redis commands
   * @param handler 
   * @return 
   */
  public RedisClient commandCount(Handler<AsyncResult<Long>> handler) { 
    delegate.commandCount(new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get total number of Redis commands
   * @return 
   */
  public Observable<Long> commandCountObservable() { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandCount(handler.toHandler());
    return handler;
  }

  /**
   * Extract keys given a full Redis command
   * @param handler 
   * @return 
   */
  public RedisClient commandGetkeys(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.commandGetkeys(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Extract keys given a full Redis command
   * @return 
   */
  public Observable<JsonArray> commandGetkeysObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandGetkeys(handler.toHandler());
    return handler;
  }

  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient commandInfo(List<String> commands, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.commandInfo(commands, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get array of specific Redis command details
   * @param commands List of commands to get info for
   * @return 
   */
  public Observable<JsonArray> commandInfoObservable(List<String> commands) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    commandInfo(commands, handler.toHandler());
    return handler;
  }

  /**
   * Get the value of a configuration parameter
   * @param parameter Configuration parameter
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient configGet(String parameter, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.configGet(parameter, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the value of a configuration parameter
   * @param parameter Configuration parameter
   * @return 
   */
  public Observable<JsonArray> configGetObservable(String parameter) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    configGet(parameter, handler.toHandler());
    return handler;
  }

  /**
   * Rewrite the configuration file with the in memory configuration
   * @param handler 
   * @return 
   */
  public RedisClient configRewrite(Handler<AsyncResult<String>> handler) { 
    delegate.configRewrite(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient configSet(String parameter, String value, Handler<AsyncResult<String>> handler) { 
    delegate.configSet(parameter, value, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient configResetstat(Handler<AsyncResult<String>> handler) { 
    delegate.configResetstat(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient dbsize(Handler<AsyncResult<Long>> handler) { 
    delegate.dbsize(new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the number of keys in the selected database
   * @return 
   */
  public Observable<Long> dbsizeObservable() { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    dbsize(handler.toHandler());
    return handler;
  }

  /**
   * Get debugging information about a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient debugObject(String key, Handler<AsyncResult<String>> handler) { 
    delegate.debugObject(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient debugSegfault(Handler<AsyncResult<String>> handler) { 
    delegate.debugSegfault(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient decr(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.decr(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Decrement the integer value of a key by one
   * @param key Key string
   * @return 
   */
  public Observable<Long> decrObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) { 
    delegate.decrby(key, decrement, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Decrement the integer value of a key by the given number
   * @param key Key string
   * @param decrement Value by which to decrement
   * @return 
   */
  public Observable<Long> decrbyObservable(String key, long decrement) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    decrby(key, decrement, handler.toHandler());
    return handler;
  }

  /**
   * Delete a key
   * @param key Keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient del(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.del(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Delete a key
   * @param key Keys to delete
   * @return 
   */
  public Observable<Long> delObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    del(key, handler.toHandler());
    return handler;
  }

  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient delMany(List<String> keys, Handler<AsyncResult<Long>> handler) { 
    delegate.delMany(keys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Delete many keys
   * @param keys List of keys to delete
   * @return 
   */
  public Observable<Long> delManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    delMany(keys, handler.toHandler());
    return handler;
  }

  /**
   * Discard all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisClient discard(Handler<AsyncResult<String>> handler) { 
    delegate.discard(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient dump(String key, Handler<AsyncResult<String>> handler) { 
    delegate.dump(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient echo(String message, Handler<AsyncResult<String>> handler) { 
    delegate.echo(message, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
   *
   * When a reply if for example a String the handler will be called with a JsonArray with a single element containing
   * the String.
   * @param script Lua script to evaluate
   * @param keys List of keys
   * @param args List of argument values
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.eval(script, keys, args, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
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
   * @param script Lua script to evaluate
   * @param keys List of keys
   * @param args List of argument values
   * @return 
   */
  public Observable<JsonArray> evalObservable(String script, List<String> keys, List<String> args) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    eval(script, keys, args, handler.toHandler());
    return handler;
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
  public RedisClient evalsha(String sha1, List<String> keys, List<String> values, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.evalsha(sha1, keys, values, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
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
   * @return 
   */
  public Observable<JsonArray> evalshaObservable(String sha1, List<String> keys, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    evalsha(sha1, keys, values, handler.toHandler());
    return handler;
  }

  /**
   * Execute all commands issued after MULTI
   * @param handler 
   * @return 
   */
  public RedisClient exec(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.exec(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient exists(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.exists(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Determine if a key exists
   * @param key Key string
   * @return 
   */
  public Observable<Long> existsObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient expire(String key, int seconds, Handler<AsyncResult<Long>> handler) { 
    delegate.expire(key, seconds, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set a key's time to live in seconds
   * @param key Key string
   * @param seconds Time to live in seconds
   * @return 
   */
  public Observable<Long> expireObservable(String key, int seconds) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient expireat(String key, long seconds, Handler<AsyncResult<Long>> handler) { 
    delegate.expireat(key, seconds, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the expiration for a key as a UNIX timestamp
   * @param key Key string
   * @param seconds Expiry time as Unix timestamp in seconds
   * @return 
   */
  public Observable<Long> expireatObservable(String key, long seconds) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    expireat(key, seconds, handler.toHandler());
    return handler;
  }

  /**
   * Remove all keys from all databases
   * @param handler 
   * @return 
   */
  public RedisClient flushall(Handler<AsyncResult<String>> handler) { 
    delegate.flushall(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient flushdb(Handler<AsyncResult<String>> handler) { 
    delegate.flushdb(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient get(String key, Handler<AsyncResult<String>> handler) { 
    delegate.get(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient getBinary(String key, Handler<AsyncResult<Buffer>> handler) { 
    delegate.getBinary(key, new Handler<AsyncResult<io.vertx.core.buffer.Buffer>>() {
      public void handle(AsyncResult<io.vertx.core.buffer.Buffer> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(Buffer.newInstance(ar.result())));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the value of a key - without decoding as utf-8
   * @param key Key string
   * @return 
   */
  public Observable<Buffer> getBinaryObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Buffer> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient getbit(String key, long offset, Handler<AsyncResult<Long>> handler) { 
    delegate.getbit(key, offset, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Returns the bit value at offset in the string value stored at key
   * @param key Key string
   * @param offset Offset in bits
   * @return 
   */
  public Observable<Long> getbitObservable(String key, long offset) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) { 
    delegate.getrange(key, start, end, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient getset(String key, String value, Handler<AsyncResult<String>> handler) { 
    delegate.getset(key, value, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient hdel(String key, String field, Handler<AsyncResult<Long>> handler) { 
    delegate.hdel(key, field, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param field Field name
   * @return 
   */
  public Observable<Long> hdelObservable(String key, String field) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) { 
    delegate.hdelMany(key, fields, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Delete one or more hash fields
   * @param key Key string
   * @param fields Field names
   * @return 
   */
  public Observable<Long> hdelManyObservable(String key, List<String> fields) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hexists(String key, String field, Handler<AsyncResult<Long>> handler) { 
    delegate.hexists(key, field, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Determine if a hash field exists
   * @param key Key string
   * @param field Field name
   * @return 
   */
  public Observable<Long> hexistsObservable(String key, String field) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hget(String key, String field, Handler<AsyncResult<String>> handler) { 
    delegate.hget(key, field, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient hgetall(String key, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.hgetall(key, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get all the fields and values in a hash
   * @param key Key string
   * @return 
   */
  public Observable<JsonObject> hgetallObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<JsonObject> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) { 
    delegate.hincrby(key, field, increment, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Increment the integer value of a hash field by the given number
   * @param key Key string
   * @param field Field name
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<Long> hincrbyObservable(String key, String field, long increment) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) { 
    delegate.hincrbyfloat(key, field, increment, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient hkeys(String key, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.hkeys(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get all the fields in a hash
   * @param key Key string
   * @return 
   */
  public Observable<JsonArray> hkeysObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hkeys(key, handler.toHandler());
    return handler;
  }

  /**
   * Get the number of fields in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hlen(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.hlen(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the number of fields in a hash
   * @param key Key string
   * @return 
   */
  public Observable<Long> hlenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hmget(String key, List<String> fields, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.hmget(key, fields, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the values of all the given hash fields
   * @param key Key string
   * @param fields Field names
   * @return 
   */
  public Observable<JsonArray> hmgetObservable(String key, List<String> fields) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler) { 
    delegate.hmset(key, values, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.hset(key, field, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the string value of a hash field
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @return 
   */
  public Observable<Long> hsetObservable(String key, String field, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.hsetnx(key, field, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the value of a hash field, only if the field does not exist
   * @param key Key string
   * @param field Field name
   * @param value New value
   * @return 
   */
  public Observable<Long> hsetnxObservable(String key, String field, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hsetnx(key, field, value, handler.toHandler());
    return handler;
  }

  /**
   * Get all the values in a hash
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient hvals(String key, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.hvals(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get all the values in a hash
   * @param key Key string
   * @return 
   */
  public Observable<JsonArray> hvalsObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    hvals(key, handler.toHandler());
    return handler;
  }

  /**
   * Increment the integer value of a key by one
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient incr(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.incr(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Increment the integer value of a key by one
   * @param key Key string
   * @return 
   */
  public Observable<Long> incrObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient incrby(String key, long increment, Handler<AsyncResult<Long>> handler) { 
    delegate.incrby(key, increment, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Increment the integer value of a key by the given amount
   * @param key Key string
   * @param increment Value by which to increment
   * @return 
   */
  public Observable<Long> incrbyObservable(String key, long increment) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) { 
    delegate.incrbyfloat(key, increment, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient info(Handler<AsyncResult<JsonObject>> handler) { 
    delegate.info(new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get information and statistics about the server
   * @return 
   */
  public Observable<JsonObject> infoObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonObject> handler = io.vertx.rx.java.RxHelper.observableFuture();
    info(handler.toHandler());
    return handler;
  }

  /**
   * Get information and statistics about the server
   * @param section Specific section of information to return
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient infoSection(String section, Handler<AsyncResult<JsonObject>> handler) { 
    delegate.infoSection(section, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get information and statistics about the server
   * @param section Specific section of information to return
   * @return 
   */
  public Observable<JsonObject> infoSectionObservable(String section) { 
    io.vertx.rx.java.ObservableFuture<JsonObject> handler = io.vertx.rx.java.RxHelper.observableFuture();
    infoSection(section, handler.toHandler());
    return handler;
  }

  /**
   * Find all keys matching the given pattern
   * @param pattern Pattern to limit the keys returned
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient keys(String pattern, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.keys(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Find all keys matching the given pattern
   * @param pattern Pattern to limit the keys returned
   * @return 
   */
  public Observable<JsonArray> keysObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    keys(pattern, handler.toHandler());
    return handler;
  }

  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @param handler 
   * @return 
   */
  public RedisClient lastsave(Handler<AsyncResult<Long>> handler) { 
    delegate.lastsave(new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @return 
   */
  public Observable<Long> lastsaveObservable() { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lindex(String key, int index, Handler<AsyncResult<String>> handler) { 
    delegate.lindex(key, index, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.linsert(key, option, pivot, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<Long> linsertObservable(String key, InsertOptions option, String pivot, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    linsert(key, option, pivot, value, handler.toHandler());
    return handler;
  }

  /**
   * Get the length of a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient llen(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.llen(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the length of a list
   * @param key String key
   * @return 
   */
  public Observable<Long> llenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    llen(key, handler.toHandler());
    return handler;
  }

  /**
   * Remove and get the first element in a list
   * @param key String key
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient lpop(String key, Handler<AsyncResult<String>> handler) { 
    delegate.lpop(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) { 
    delegate.lpushMany(key, values, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Prepend one or multiple values to a list
   * @param key Key string
   * @param values Values to be added at the beginning of the list, one by one
   * @return 
   */
  public Observable<Long> lpushManyObservable(String key, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lpush(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.lpush(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Prepend one value to a list
   * @param key Key string
   * @param value Value to be added at the beginning of the list
   * @return 
   */
  public Observable<Long> lpushObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lpushx(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.lpushx(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Prepend a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to add at the beginning of the list
   * @return 
   */
  public Observable<Long> lpushxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lrange(String key, long from, long to, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.lrange(key, from, to, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get a range of elements from a list
   * @param key Key string
   * @param from Start index
   * @param to Stop index
   * @return 
   */
  public Observable<JsonArray> lrangeObservable(String key, long from, long to) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lrem(String key, long count, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.lrem(key, count, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove elements from a list
   * @param key Key string
   * @param count Number of first found occurrences equal to $value to remove from the list
   * @param value Value to be removed
   * @return 
   */
  public Observable<Long> lremObservable(String key, long count, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient lset(String key, long index, String value, Handler<AsyncResult<String>> handler) { 
    delegate.lset(key, index, value, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) { 
    delegate.ltrim(key, from, to, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient mget(String key, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.mget(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the value of the given key
   * @param key Key string
   * @return 
   */
  public Observable<JsonArray> mgetObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    mget(key, handler.toHandler());
    return handler;
  }

  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient mgetMany(List<String> keys, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.mgetMany(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the values of all the given keys
   * @param keys List of keys to get
   * @return 
   */
  public Observable<JsonArray> mgetManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.migrate(host, port, key, destdb, timeout, options, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
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
  public RedisClient monitor(Handler<AsyncResult<Void>> handler) { 
    delegate.monitor(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Listen for all requests received by the server in real time
   * @return 
   */
  public Observable<Void> monitorObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient move(String key, int destdb, Handler<AsyncResult<Long>> handler) { 
    delegate.move(key, destdb, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Move a key to another database
   * @param key Key to migrate
   * @param destdb Destination database index
   * @return 
   */
  public Observable<Long> moveObservable(String key, int destdb) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    move(key, destdb, handler.toHandler());
    return handler;
  }

  /**
   * Set multiple keys to multiple values
   * @param keyvals Key value pairs to set
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient mset(JsonObject keyvals, Handler<AsyncResult<String>> handler) { 
    delegate.mset(keyvals, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient msetnx(JsonObject keyvals, Handler<AsyncResult<Long>> handler) { 
    delegate.msetnx(keyvals, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @param keyvals Key value pairs to set
   * @return 
   */
  public Observable<Long> msetnxObservable(JsonObject keyvals) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    msetnx(keyvals, handler.toHandler());
    return handler;
  }

  /**
   * Mark the start of a transaction block
   * @param handler 
   * @return 
   */
  public RedisClient multi(Handler<AsyncResult<String>> handler) { 
    delegate.multi(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Mark the start of a transaction block
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
  public RedisClient object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) { 
    delegate.object(key, cmd, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Inspect the internals of Redis objects
   * @param key Key string
   * @param cmd Object sub command
   * @return 
   */
  public Observable<Void> objectObservable(String key, ObjectCmd cmd) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    object(key, cmd, handler.toHandler());
    return handler;
  }

  /**
   * Remove the expiration from a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient persist(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.persist(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove the expiration from a key
   * @param key Key string
   * @return 
   */
  public Observable<Long> persistObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient pexpire(String key, long millis, Handler<AsyncResult<Long>> handler) { 
    delegate.pexpire(key, millis, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set a key's time to live in milliseconds
   * @param key String key
   * @param millis Time to live in milliseconds
   * @return 
   */
  public Observable<Long> pexpireObservable(String key, long millis) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient pexpireat(String key, long millis, Handler<AsyncResult<Long>> handler) { 
    delegate.pexpireat(key, millis, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @param key Key string
   * @param millis Expiry time as Unix timestamp in milliseconds
   * @return 
   */
  public Observable<Long> pexpireatObservable(String key, long millis) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient pfadd(String key, String element, Handler<AsyncResult<Long>> handler) { 
    delegate.pfadd(key, element, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Adds the specified element to the specified HyperLogLog.
   * @param key Key string
   * @param element Element to add
   * @return 
   */
  public Observable<Long> pfaddObservable(String key, String element) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient pfaddMany(String key, List<String> elements, Handler<AsyncResult<Long>> handler) { 
    delegate.pfaddMany(key, elements, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @param key Key string
   * @param elements Elementa to add
   * @return 
   */
  public Observable<Long> pfaddManyObservable(String key, List<String> elements) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfaddMany(key, elements, handler.toHandler());
    return handler;
  }

  /**
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfcount(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.pfcount(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the approximated cardinality of the set observed by the HyperLogLog at key.
   * @param key Key string
   * @return 
   */
  public Observable<Long> pfcountObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pfcount(key, handler.toHandler());
    return handler;
  }

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pfcountMany(List<String> keys, Handler<AsyncResult<Long>> handler) { 
    delegate.pfcountMany(keys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @param keys List of keys
   * @return 
   */
  public Observable<Long> pfcountManyObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.pfmerge(destkey, keys, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient ping(Handler<AsyncResult<String>> handler) { 
    delegate.ping(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient psetex(String key, long millis, String value, Handler<AsyncResult<Void>> handler) { 
    delegate.psetex(key, millis, value, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the value and expiration in milliseconds of a key
   * @param key Key string
   * @param millis Number of milliseconds until the key expires
   * @param value New value for key
   * @return 
   */
  public Observable<Void> psetexObservable(String key, long millis, String value) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psetex(key, millis, value, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to channels matching the given pattern
   * @param pattern Pattern string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient psubscribe(String pattern, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.psubscribe(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Listen for messages published to channels matching the given pattern
   * @param pattern Pattern string
   * @return 
   */
  public Observable<JsonArray> psubscribeObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psubscribe(pattern, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient psubscribeMany(List<String> patterns, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.psubscribeMany(patterns, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Listen for messages published to channels matching the given patterns
   * @param patterns List of patterns
   * @return 
   */
  public Observable<JsonArray> psubscribeManyObservable(List<String> patterns) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    psubscribeMany(patterns, handler.toHandler());
    return handler;
  }

  /**
   * Lists the currently active channels - only those matching the pattern
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubChannels(String pattern, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.pubsubChannels(pattern, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Lists the currently active channels - only those matching the pattern
   * @param pattern A glob-style pattern - an empty string means no pattern
   * @return 
   */
  public Observable<JsonArray> pubsubChannelsObservable(String pattern) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubChannels(pattern, handler.toHandler());
    return handler;
  }

  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubNumsub(List<String> channels, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.pubsubNumsub(channels, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
   * @param channels List of channels
   * @return 
   */
  public Observable<JsonArray> pubsubNumsubObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubNumsub(channels, handler.toHandler());
    return handler;
  }

  /**
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pubsubNumpat(Handler<AsyncResult<Long>> handler) { 
    delegate.pubsubNumpat(new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
   * @return 
   */
  public Observable<Long> pubsubNumpatObservable() { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    pubsubNumpat(handler.toHandler());
    return handler;
  }

  /**
   * Get the time to live for a key in milliseconds
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient pttl(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.pttl(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the time to live for a key in milliseconds
   * @param key Key string
   * @return 
   */
  public Observable<Long> pttlObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient publish(String channel, String message, Handler<AsyncResult<Long>> handler) { 
    delegate.publish(channel, message, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Post a message to a channel
   * @param channel Channel key
   * @param message Message to send to channel
   * @return 
   */
  public Observable<Long> publishObservable(String channel, String message) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    publish(channel, message, handler.toHandler());
    return handler;
  }

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient punsubscribe(List<String> patterns, Handler<AsyncResult<Void>> handler) { 
    delegate.punsubscribe(patterns, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @param patterns List of patterns to match against
   * @return 
   */
  public Observable<Void> punsubscribeObservable(List<String> patterns) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    punsubscribe(patterns, handler.toHandler());
    return handler;
  }

  /**
   * Return a random key from the keyspace
   * @param handler 
   * @return 
   */
  public RedisClient randomkey(Handler<AsyncResult<String>> handler) { 
    delegate.randomkey(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient rename(String key, String newkey, Handler<AsyncResult<String>> handler) { 
    delegate.rename(key, newkey, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) { 
    delegate.renamenx(key, newkey, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Rename a key, only if the new key does not exist
   * @param key Key string to be renamed
   * @param newkey New key string
   * @return 
   */
  public Observable<Long> renamenxObservable(String key, String newkey) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) { 
    delegate.restore(key, millis, serialized, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient role(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.role(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the role of the instance in the context of replication
   * @return 
   */
  public Observable<JsonArray> roleObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    role(handler.toHandler());
    return handler;
  }

  /**
   * Remove and get the last element in a list
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient rpop(String key, Handler<AsyncResult<String>> handler) { 
    delegate.rpop(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) { 
    delegate.rpoplpush(key, destkey, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) { 
    delegate.rpushMany(key, values, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param values List of values to add to the end of the list
   * @return 
   */
  public Observable<Long> rpushManyObservable(String key, List<String> values) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient rpush(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.rpush(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Append one or multiple values to a list
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @return 
   */
  public Observable<Long> rpushObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient rpushx(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.rpushx(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Append a value to a list, only if the list exists
   * @param key Key string
   * @param value Value to be added to the end of the list
   * @return 
   */
  public Observable<Long> rpushxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sadd(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.sadd(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add a member to a set
   * @param key Key string
   * @param member Value to be added to the set
   * @return 
   */
  public Observable<Long> saddObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient saddMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) { 
    delegate.saddMany(key, members, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add one or more members to a set
   * @param key Key string
   * @param members Values to be added to the set
   * @return 
   */
  public Observable<Long> saddManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    saddMany(key, members, handler.toHandler());
    return handler;
  }

  /**
   * Synchronously save the dataset to disk
   * @param handler 
   * @return 
   */
  public RedisClient save(Handler<AsyncResult<String>> handler) { 
    delegate.save(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient scard(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.scard(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the number of members in a set
   * @param key Key string
   * @return 
   */
  public Observable<Long> scardObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scard(key, handler.toHandler());
    return handler;
  }

  /**
   * Check existence of script in the script cache.
   * @param script SHA1 digest identifying a script in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scriptExists(String script, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.scriptExists(script, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Check existence of script in the script cache.
   * @param script SHA1 digest identifying a script in the script cache
   * @return 
   */
  public Observable<JsonArray> scriptExistsObservable(String script) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptExists(script, handler.toHandler());
    return handler;
  }

  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient scriptExistsMany(List<String> scripts, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.scriptExistsMany(scripts, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Check existence of scripts in the script cache.
   * @param scripts List of SHA1 digests identifying scripts in the script cache
   * @return 
   */
  public Observable<JsonArray> scriptExistsManyObservable(List<String> scripts) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    scriptExistsMany(scripts, handler.toHandler());
    return handler;
  }

  /**
   * Remove all the scripts from the script cache.
   * @param handler 
   * @return 
   */
  public RedisClient scriptFlush(Handler<AsyncResult<String>> handler) { 
    delegate.scriptFlush(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient scriptKill(Handler<AsyncResult<String>> handler) { 
    delegate.scriptKill(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient scriptLoad(String script, Handler<AsyncResult<String>> handler) { 
    delegate.scriptLoad(script, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.sdiff(key, cmpkeys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Subtract multiple sets
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @return 
   */
  public Observable<JsonArray> sdiffObservable(String key, List<String> cmpkeys) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<Long>> handler) { 
    delegate.sdiffstore(destkey, key, cmpkeys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Subtract multiple sets and store the resulting set in a key
   * @param destkey Destination key where the result should be stored
   * @param key Key identifying the set to compare with all other sets combined
   * @param cmpkeys List of keys identifying sets to subtract from the key set
   * @return 
   */
  public Observable<Long> sdiffstoreObservable(String destkey, String key, List<String> cmpkeys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sdiffstore(destkey, key, cmpkeys, handler.toHandler());
    return handler;
  }

  /**
   * Change the selected database for the current connection
   * @param dbindex Index identifying the new active database
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient select(int dbindex, Handler<AsyncResult<String>> handler) { 
    delegate.select(dbindex, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient set(String key, String value, Handler<AsyncResult<Void>> handler) { 
    delegate.set(key, value, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<Void> setObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<String>> handler) { 
    delegate.setWithOptions(key, value, options, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient setBinary(String key, Buffer value, Handler<AsyncResult<Void>> handler) { 
    delegate.setBinary(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the binary string value of a key - without encoding as utf-8
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<Void> setBinaryObservable(String key, Buffer value) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient setBinaryWithOptions(String key, Buffer value, SetOptions options, Handler<AsyncResult<Void>> handler) { 
    delegate.setBinaryWithOptions(key, (io.vertx.core.buffer.Buffer)value.getDelegate(), options, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the string value of a key
   * @param key Key of which value to set
   * @param value New value for the key
   * @param options Set options
   * @return 
   */
  public Observable<Void> setBinaryWithOptionsObservable(String key, Buffer value, SetOptions options) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) { 
    delegate.setbit(key, offset, bit, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Sets or clears the bit at offset in the string value stored at key
   * @param key Key string
   * @param offset Bit offset
   * @param bit New value - must be 1 or 0
   * @return 
   */
  public Observable<Long> setbitObservable(String key, long offset, int bit) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) { 
    delegate.setex(key, seconds, value, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient setnx(String key, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.setnx(key, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Set the value of a key, only if the key does not exist
   * @param key Key of which value to set
   * @param value New value for the key
   * @return 
   */
  public Observable<Long> setnxObservable(String key, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient setrange(String key, int offset, String value, Handler<AsyncResult<Long>> handler) { 
    delegate.setrange(key, offset, value, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Overwrite part of a string at key starting at the specified offset
   * @param key Key string
   * @param offset Offset - the maximum offset that you can set is 2^29 -1 (536870911), as Redis Strings are limited to 512 megabytes
   * @param value Value to overwrite with
   * @return 
   */
  public Observable<Long> setrangeObservable(String key, int offset, String value) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    setrange(key, offset, value, handler.toHandler());
    return handler;
  }

  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sinter(List<String> keys, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.sinter(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Intersect multiple sets
   * @param keys List of keys to perform intersection on
   * @return 
   */
  public Observable<JsonArray> sinterObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sinterstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) { 
    delegate.sinterstore(destkey, keys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Intersect multiple sets and store the resulting set in a key
   * @param destkey Key where to store the results
   * @param keys List of keys to perform intersection on
   * @return 
   */
  public Observable<Long> sinterstoreObservable(String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sismember(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.sismember(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Determine if a given value is a member of a set
   * @param key Key string
   * @param member Member to look for
   * @return 
   */
  public Observable<Long> sismemberObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient slaveof(String host, int port, Handler<AsyncResult<String>> handler) { 
    delegate.slaveof(host, port, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient slaveofNoone(Handler<AsyncResult<String>> handler) { 
    delegate.slaveofNoone(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient slowlogGet(int limit, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.slowlogGet(limit, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Read the Redis slow queries log
   * @param limit Number of log entries to return. If value is less than zero all entries are returned
   * @return 
   */
  public Observable<JsonArray> slowlogGetObservable(int limit) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogGet(limit, handler.toHandler());
    return handler;
  }

  /**
   * Get the length of the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slowlogLen(Handler<AsyncResult<Long>> handler) { 
    delegate.slowlogLen(new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the length of the Redis slow queries log
   * @return 
   */
  public Observable<Long> slowlogLenObservable() { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogLen(handler.toHandler());
    return handler;
  }

  /**
   * Reset the Redis slow queries log
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient slowlogReset(Handler<AsyncResult<Void>> handler) { 
    delegate.slowlogReset(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Reset the Redis slow queries log
   * @return 
   */
  public Observable<Void> slowlogResetObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    slowlogReset(handler.toHandler());
    return handler;
  }

  /**
   * Get all the members in a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient smembers(String key, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.smembers(key, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get all the members in a set
   * @param key Key string
   * @return 
   */
  public Observable<JsonArray> smembersObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient smove(String key, String destkey, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.smove(key, destkey, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Move a member from one set to another
   * @param key Key of source set currently containing the member
   * @param destkey Key identifying the destination set
   * @param member Member to move
   * @return 
   */
  public Observable<Long> smoveObservable(String key, String destkey, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sort(String key, SortOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.sort(key, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Sort the elements in a list, set or sorted set
   * @param key Key string
   * @param options Sort options
   * @return 
   */
  public Observable<JsonArray> sortObservable(String key, SortOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sort(key, options, handler.toHandler());
    return handler;
  }

  /**
   * Remove and return a random member from a set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient spop(String key, Handler<AsyncResult<String>> handler) { 
    delegate.spop(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient spopMany(String key, int count, Handler<AsyncResult<String>> handler) { 
    delegate.spopMany(key, count, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient srandmember(String key, Handler<AsyncResult<String>> handler) { 
    delegate.srandmember(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient srandmemberCount(String key, int count, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.srandmemberCount(key, count, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get one or multiple random members from a set
   * @param key Key string
   * @param count Number of members to get
   * @return 
   */
  public Observable<JsonArray> srandmemberCountObservable(String key, int count) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient srem(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.srem(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove one member from a set
   * @param key Key string
   * @param member Member to remove
   * @return 
   */
  public Observable<Long> sremObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) { 
    delegate.sremMany(key, members, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove one or more members from a set
   * @param key Key string
   * @param members Members to remove
   * @return 
   */
  public Observable<Long> sremManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sremMany(key, members, handler.toHandler());
    return handler;
  }

  /**
   * Get the length of the value stored in a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient strlen(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.strlen(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the length of the value stored in a key
   * @param key Key string
   * @return 
   */
  public Observable<Long> strlenObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    strlen(key, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to the given channels
   * @param channel Channel to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient subscribe(String channel, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.subscribe(channel, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Listen for messages published to the given channels
   * @param channel Channel to subscribe to
   * @return 
   */
  public Observable<JsonArray> subscribeObservable(String channel) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    subscribe(channel, handler.toHandler());
    return handler;
  }

  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient subscribeMany(List<String> channels, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.subscribeMany(channels, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Listen for messages published to the given channels
   * @param channels List of channels to subscribe to
   * @return 
   */
  public Observable<JsonArray> subscribeManyObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    subscribeMany(channels, handler.toHandler());
    return handler;
  }

  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient sunion(List<String> keys, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.sunion(keys, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add multiple sets
   * @param keys List of keys identifying sets to add up
   * @return 
   */
  public Observable<JsonArray> sunionObservable(List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sunionstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) { 
    delegate.sunionstore(destkey, keys, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add multiple sets and store the resulting set in a key
   * @param destkey Destination key
   * @param keys List of keys identifying sets to add up
   * @return 
   */
  public Observable<Long> sunionstoreObservable(String destkey, List<String> keys) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sunionstore(destkey, keys, handler.toHandler());
    return handler;
  }

  /**
   * Internal command used for replication
   * @param handler 
   * @return 
   */
  public RedisClient sync(Handler<AsyncResult<Void>> handler) { 
    delegate.sync(new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Internal command used for replication
   * @return 
   */
  public Observable<Void> syncObservable() { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    sync(handler.toHandler());
    return handler;
  }

  /**
   * Return the current server time
   * @param handler 
   * @return 
   */
  public RedisClient time(Handler<AsyncResult<JsonArray>> handler) { 
    delegate.time(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return the current server time
   * @return 
   */
  public Observable<JsonArray> timeObservable() { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    time(handler.toHandler());
    return handler;
  }

  /**
   * Get the time to live for a key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient ttl(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.ttl(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the time to live for a key
   * @param key Key string
   * @return 
   */
  public Observable<Long> ttlObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    ttl(key, handler.toHandler());
    return handler;
  }

  /**
   * Determine the type stored at key
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient type(String key, Handler<AsyncResult<String>> handler) { 
    delegate.type(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient unsubscribe(List<String> channels, Handler<AsyncResult<Void>> handler) { 
    delegate.unsubscribe(channels, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Stop listening for messages posted to the given channels
   * @param channels List of channels to subscribe to
   * @return 
   */
  public Observable<Void> unsubscribeObservable(List<String> channels) { 
    io.vertx.rx.java.ObservableFuture<Void> handler = io.vertx.rx.java.RxHelper.observableFuture();
    unsubscribe(channels, handler.toHandler());
    return handler;
  }

  /**
   * Forget about all watched keys
   * @param handler 
   * @return 
   */
  public RedisClient unwatch(Handler<AsyncResult<String>> handler) { 
    delegate.unwatch(new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) { 
    delegate.wait(numSlaves, timeout, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient watch(String key, Handler<AsyncResult<String>> handler) { 
    delegate.watch(key, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient watchMany(List<String> keys, Handler<AsyncResult<String>> handler) { 
    delegate.watchMany(keys, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.zadd(key, score, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param score Score used for sorting
   * @param member New member key
   * @return 
   */
  public Observable<Long> zaddObservable(String key, double score, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zaddMany(String key, Map<String,Double> members, Handler<AsyncResult<Long>> handler) { 
    delegate.zaddMany(key, members, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @param key Key string
   * @param members New member keys and their scores
   * @return 
   */
  public Observable<Long> zaddManyObservable(String key, Map<String,Double> members) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zaddMany(key, members, handler.toHandler());
    return handler;
  }

  /**
   * Get the number of members in a sorted set
   * @param key Key string
   * @param handler Handler for the result of this call.
   * @return 
   */
  public RedisClient zcard(String key, Handler<AsyncResult<Long>> handler) { 
    delegate.zcard(key, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Get the number of members in a sorted set
   * @param key Key string
   * @return 
   */
  public Observable<Long> zcardObservable(String key) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) { 
    delegate.zcount(key, min, max, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Count the members in a sorted set with scores within the given values
   * @param key Key string
   * @param min Minimum score
   * @param max Maximum score
   * @return 
   */
  public Observable<Long> zcountObservable(String key, double min, double max) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) { 
    delegate.zincrby(key, increment, member, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) { 
    delegate.zinterstore(destkey, sets, options, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @return 
   */
  public Observable<Long> zinterstoreObservable(String destkey, List<String> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zinterstoreWeighed(String destkey, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) { 
    delegate.zinterstoreWeighed(destkey, sets, options, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets to intersect
   * @param options Aggregation options
   * @return 
   */
  public Observable<Long> zinterstoreWeighedObservable(String destkey, Map<String,Double> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zlexcount(String key, String min, String max, Handler<AsyncResult<Long>> handler) { 
    delegate.zlexcount(key, min, max, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Count the number of members in a sorted set between a given lexicographical range
   * @param key Key string
   * @param min Pattern to compare against for minimum value
   * @param max Pattern to compare against for maximum value
   * @return 
   */
  public Observable<Long> zlexcountObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrange(String key, long start, long stop, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrange(key, start, stop, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Return a range of members in a sorted set, by index
   * @param key Key string
   * @param start Start index for the range
   * @param stop Stop index for the range - inclusive
   * @return 
   */
  public Observable<JsonArray> zrangeObservable(String key, long start, long stop) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrangeWithOptions(key, start, stop, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
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
   * @return 
   */
  public Observable<JsonArray> zrangeWithOptionsObservable(String key, long start, long stop, RangeOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrangebylex(key, min, max, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<JsonArray> zrangebylexObservable(String key, String min, String max, LimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrangebyscore(key, min, max, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<JsonArray> zrangebyscoreObservable(String key, String min, String max, RangeLimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrank(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.zrank(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Determine the index of a member in a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<Long> zrankObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrem(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.zrem(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove one member from a sorted set
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<Long> zremObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) { 
    delegate.zremMany(key, members, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove one or more members from a sorted set
   * @param key Key string
   * @param members Members in the sorted set identified by key
   * @return 
   */
  public Observable<Long> zremManyObservable(String key, List<String> members) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zremrangebylex(String key, String min, String max, Handler<AsyncResult<Long>> handler) { 
    delegate.zremrangebylex(key, min, max, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove all members in a sorted set between the given lexicographical range
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @return 
   */
  public Observable<Long> zremrangebylexObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<Long>> handler) { 
    delegate.zremrangebyrank(key, start, stop, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove all members in a sorted set within the given indexes
   * @param key Key string
   * @param start Start index
   * @param stop Stop index
   * @return 
   */
  public Observable<Long> zremrangebyrankObservable(String key, long start, long stop) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zremrangebyscore(String key, String min, String max, Handler<AsyncResult<Long>> handler) { 
    delegate.zremrangebyscore(key, min, max, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Remove all members in a sorted set within the given scores
   * @param key Key string
   * @param min Pattern defining a minimum value
   * @param max Pattern defining a maximum value
   * @return 
   */
  public Observable<Long> zremrangebyscoreObservable(String key, String min, String max) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrevrange(key, start, stop, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<JsonArray> zrevrangeObservable(String key, long start, long stop, RangeOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrevrangebylex(key, max, min, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<JsonArray> zrevrangebylexObservable(String key, String max, String min, LimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zrevrangebyscore(key, max, min, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public Observable<JsonArray> zrevrangebyscoreObservable(String key, String max, String min, RangeLimitOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zrevrank(String key, String member, Handler<AsyncResult<Long>> handler) { 
    delegate.zrevrank(key, member, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @param key Key string
   * @param member Member in the sorted set identified by key
   * @return 
   */
  public Observable<Long> zrevrankObservable(String key, String member) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zscore(String key, String member, Handler<AsyncResult<String>> handler) { 
    delegate.zscore(key, member, new Handler<AsyncResult<java.lang.String>>() {
      public void handle(AsyncResult<java.lang.String> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
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
  public RedisClient zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) { 
    delegate.zunionstore(destkey, sets, options, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * @param destkey Destination key
   * @param sets List of keys identifying sorted sets
   * @param options Aggregation options
   * @return 
   */
  public Observable<Long> zunionstoreObservable(String destkey, List<String> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zunionstoreWeighed(String key, Map<String,Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) { 
    delegate.zunionstoreWeighed(key, sets, options, new Handler<AsyncResult<java.lang.Long>>() {
      public void handle(AsyncResult<java.lang.Long> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Add multiple sorted sets using weights, and store the resulting sorted set in a new key
   * @param key Destination key
   * @param sets Map containing set-key:weight pairs
   * @param options Aggregation options
   * @return 
   */
  public Observable<Long> zunionstoreWeighedObservable(String key, Map<String,Double> sets, AggregateOptions options) { 
    io.vertx.rx.java.ObservableFuture<Long> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient scan(String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.scan(cursor, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Incrementally iterate the keys space
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<JsonArray> scanObservable(String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.sscan(key, cursor, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Incrementally iterate Set elements
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<JsonArray> sscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.hscan(key, cursor, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Incrementally iterate hash fields and associated values
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<JsonArray> hscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
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
  public RedisClient zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) { 
    delegate.zscan(key, cursor, options, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
    return this;
  }

  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @param key Key string
   * @param cursor Cursor id
   * @param options Scan options
   * @return 
   */
  public Observable<JsonArray> zscanObservable(String key, String cursor, ScanOptions options) { 
    io.vertx.rx.java.ObservableFuture<JsonArray> handler = io.vertx.rx.java.RxHelper.observableFuture();
    zscan(key, cursor, options, handler.toHandler());
    return handler;
  }


  public static RedisClient newInstance(io.vertx.redis.RedisClient arg) {
    return arg != null ? new RedisClient(arg) : null;
  }
}
