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
import io.vertx.core.json.JsonArray
import io.vertx.groovy.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
@CompileStatic
public class RedisService {
  final def io.vertx.redis.RedisService delegate;
  public RedisService(io.vertx.redis.RedisService delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static RedisService create(Vertx vertx, Map<String, Object> config) {
    def ret= RedisService.FACTORY.apply(io.vertx.redis.RedisService.create((io.vertx.core.Vertx)vertx.getDelegate(), config != null ? new io.vertx.core.json.JsonObject(config) : null));
    return ret;
  }
  public static RedisService createEventBusProxy(Vertx vertx, String address) {
    def ret= RedisService.FACTORY.apply(io.vertx.redis.RedisService.createEventBusProxy((io.vertx.core.Vertx)vertx.getDelegate(), address));
    return ret;
  }
  public void start(Handler<AsyncResult<Void>> handler) {
    this.delegate.start(handler);
  }
  public void stop(Handler<AsyncResult<Void>> handler) {
    this.delegate.stop(handler);
  }
  /**
   * Append a value to a key
   * @since 2.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void append(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.append(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Authenticate to the server
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"password","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void auth(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.auth(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Asynchronously rewrite the append-only file
   * @since 1.0.0
   * group: server

   */
  public void bgrewriteaof(Handler<AsyncResult<String>> handler) {
    this.delegate.bgrewriteaof(handler);
  }
  /**
   * Asynchronously save the dataset to disk
   * @since 1.0.0
   * group: server

   */
  public void bgsave(Handler<AsyncResult<String>> handler) {
    this.delegate.bgsave(handler);
  }
  /**
   * Count set bits in a string
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["start","end"],"type":["integer","integer"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void bitcount(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitcount(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Perform bitwise operations between strings
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"operation","type":"string"},{"name":"destkey","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void bitop(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitop(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Find first bit set or clear in a string
   * @since 2.8.7
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"bit","type":"integer"},{"name":"start","type":"integer","optional":true},{"name":"end","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void bitpos(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.bitpos(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove and get the first element in a list, or block until one is available
   * @since 2.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void blpop(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.blpop(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove and get the last element in a list, or block until one is available
   * @since 2.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void brpop(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.brpop(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void brpoplpush(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.brpoplpush(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Kill the connection of a client
   * @since 2.4.0
   * group: server
   *
   * @param args JsonArray [{"name":"ip:port","type":"string","optional":true},{"command":"ID","name":"client-id","type":"integer","optional":true},{"command":"TYPE","type":"enum","enum":["normal","slave","pubsub"],"optional":true},{"command":"ADDR","name":"ip:port","type":"string","optional":true},{"command":"SKIPME","name":"yes/no","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void clientKill(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.clientKill(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the list of client connections
   * @since 2.4.0
   * group: server

   */
  public void clientList(Handler<AsyncResult<String>> handler) {
    this.delegate.clientList(handler);
  }
  /**
   * Get the current connection name
   * @since 2.6.9
   * group: server

   */
  public void clientGetname(Handler<AsyncResult<String>> handler) {
    this.delegate.clientGetname(handler);
  }
  /**
   * Stop processing commands from clients for some time
   * @since 2.9.50
   * group: server
   *
   * @param args JsonArray [{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void clientPause(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.clientPause(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the current connection name
   * @since 2.6.9
   * group: server
   *
   * @param args JsonArray [{"name":"connection-name","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void clientSetname(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.clientSetname(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get array of Cluster slot to node mappings
   * @since 3.0.0
   * group: server

   */
  public void clusterSlots(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.clusterSlots(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get array of Redis command details
   * @since 2.8.13
   * group: server

   */
  public void command(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.command(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get total number of Redis commands
   * @since 2.8.13
   * group: server

   */
  public void commandCount(Handler<AsyncResult<Long>> handler) {
    this.delegate.commandCount(handler);
  }
  /**
   * Extract keys given a full Redis command
   * @since 2.8.13
   * group: server

   */
  public void commandGetkeys(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.commandGetkeys(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get array of specific Redis command details
   * @since 2.8.13
   * group: server
   *
   * @param args JsonArray [{"name":"command-name","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void commandInfo(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.commandInfo(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get the value of a configuration parameter
   * @since 2.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"parameter","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void configGet(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.configGet(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Rewrite the configuration file with the in memory configuration
   * @since 2.8.0
   * group: server

   */
  public void configRewrite(Handler<AsyncResult<String>> handler) {
    this.delegate.configRewrite(handler);
  }
  /**
   * Set a configuration parameter to the given value
   * @since 2.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"parameter","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void configSet(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.configSet(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Reset the stats returned by INFO
   * @since 2.0.0
   * group: server

   */
  public void configResetstat(Handler<AsyncResult<String>> handler) {
    this.delegate.configResetstat(handler);
  }
  /**
   * Return the number of keys in the selected database
   * @since 1.0.0
   * group: server

   */
  public void dbsize(Handler<AsyncResult<Long>> handler) {
    this.delegate.dbsize(handler);
  }
  /**
   * Get debugging information about a key
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void debugObject(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.debugObject(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Make the server crash
   * @since 1.0.0
   * group: server

   */
  public void debugSegfault(Handler<AsyncResult<String>> handler) {
    this.delegate.debugSegfault(handler);
  }
  /**
   * Decrement the integer value of a key by one
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void decr(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.decr(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Decrement the integer value of a key by the given number
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"decrement","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void decrby(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.decrby(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Delete a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void del(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.del(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Discard all commands issued after MULTI
   * @since 2.0.0
   * group: transactions

   */
  public void discard(Handler<AsyncResult<String>> handler) {
    this.delegate.discard(handler);
  }
  /**
   * Return a serialized version of the value stored at the specified key.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void dump(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.dump(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Echo the given string
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void echo(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.echo(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Execute a Lua script server side
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void eval(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.eval(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Execute a Lua script server side
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"sha1","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void evalsha(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.evalsha(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Execute all commands issued after MULTI
   * @since 1.2.0
   * group: transactions

   */
  public void exec(Handler<AsyncResult<Void>> handler) {
    this.delegate.exec(handler);
  }
  /**
   * Determine if a key exists
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void exists(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.exists(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set a key's time to live in seconds
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void expire(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.expire(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the expiration for a key as a UNIX timestamp
   * @since 1.2.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   */
  public void expireat(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.expireat(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove all keys from all databases
   * @since 1.0.0
   * group: server

   */
  public void flushall(Handler<AsyncResult<String>> handler) {
    this.delegate.flushall(handler);
  }
  /**
   * Remove all keys from the current database
   * @since 1.0.0
   * group: server

   */
  public void flushdb(Handler<AsyncResult<String>> handler) {
    this.delegate.flushdb(handler);
  }
  /**
   * Get the value of a key
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void get(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.get(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Returns the bit value at offset in the string value stored at key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void getbit(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.getbit(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get a substring of the string stored at a key
   * @since 2.4.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"end","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void getrange(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.getrange(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the string value of a key and return its old value
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void getset(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.getset(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Delete one or more hash fields
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void hdel(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hdel(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Determine if a hash field exists
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void hexists(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hexists(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the value of a hash field
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void hget(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.hget(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get all the fields and values in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void hgetall(List<Object> args, Handler<AsyncResult<Map<String, Object>>> handler) {
    this.delegate.hgetall(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> event) {
        AsyncResult<Map<String, Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<Map<String, Object>>result(event.result()?.toMap())
        } else {
          f = InternalHelper.<Map<String, Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Increment the integer value of a hash field by the given number
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void hincrby(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hincrby(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Increment the float value of a hash field by the given amount
   * @since 2.6.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  public void hincrbyfloat(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.hincrbyfloat(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get all the fields in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void hkeys(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hkeys(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get the number of fields in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void hlen(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hlen(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the values of all the given hash fields
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void hmget(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hmget(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Set multiple hash fields to multiple values
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["field","value"],"type":["string","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void hmset(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.hmset(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the string value of a hash field
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void hset(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hset(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the value of a hash field, only if the field does not exist
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void hsetnx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.hsetnx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get all the values in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void hvals(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.hvals(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Increment the integer value of a key by one
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void incr(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.incr(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Increment the integer value of a key by the given amount
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void incrby(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.incrby(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Increment the float value of a key by the given amount
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  public void incrbyfloat(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.incrbyfloat(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get information and statistics about the server
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"section","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void info(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.info(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Find all keys matching the given pattern
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"pattern","type":"pattern"}]
   * @param handler Handler for the result of this call.
   */
  public void keys(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.keys(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @since 1.0.0
   * group: server

   */
  public void lastsave(Handler<AsyncResult<Long>> handler) {
    this.delegate.lastsave(handler);
  }
  /**
   * Get an element from a list by its index
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void lindex(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.lindex(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Insert an element before or after another element in a list
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"where","type":"enum","enum":["BEFORE","AFTER"]},{"name":"pivot","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void linsert(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.linsert(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the length of a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void llen(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.llen(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove and get the first element in a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void lpop(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.lpop(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Prepend one or multiple values to a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void lpush(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.lpush(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Prepend a value to a list, only if the list exists
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void lpushx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.lpushx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get a range of elements from a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void lrange(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.lrange(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove elements from a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void lrem(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.lrem(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the value of an element in a list by its index
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void lset(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.lset(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Trim a list to the specified range
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void ltrim(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.ltrim(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the values of all the given keys
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void mget(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.mget(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Atomically transfer a key from a Redis instance to another one.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"},{"name":"key","type":"key"},{"name":"destination-db","type":"integer"},{"name":"timeout","type":"integer"},{"name":"copy","type":"enum","enum":["COPY"],"optional":true},{"name":"replace","type":"enum","enum":["REPLACE"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void migrate(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.migrate(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Listen for all requests received by the server in real time
   * @since 1.0.0
   * group: server

   */
  public void monitor(Handler<AsyncResult<Void>> handler) {
    this.delegate.monitor(handler);
  }
  /**
   * Move a key to another database
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"db","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void move(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.move(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set multiple keys to multiple values
   * @since 1.0.1
   * group: string
   *
   * @param args JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void mset(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.mset(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @since 1.0.1
   * group: string
   *
   * @param args JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void msetnx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.msetnx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Mark the start of a transaction block
   * @since 1.2.0
   * group: transactions

   */
  public void multi(Handler<AsyncResult<String>> handler) {
    this.delegate.multi(handler);
  }
  /**
   * Inspect the internals of Redis objects
   * @since 2.2.3
   * group: generic
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"arguments","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void object(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.object(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove the expiration from a key
   * @since 2.2.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void persist(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.persist(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set a key's time to live in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void pexpire(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pexpire(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds-timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   */
  public void pexpireat(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pexpireat(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"element","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void pfadd(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfadd(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void pfcount(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pfcount(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Merge N different HyperLogLogs into a single one.
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"destkey","type":"key"},{"name":"sourcekey","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void pfmerge(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.pfmerge(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Ping the server
   * @since 1.0.0
   * group: connection

   */
  public void ping(Handler<AsyncResult<String>> handler) {
    this.delegate.ping(handler);
  }
  /**
   * Set the value and expiration in milliseconds of a key
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void psetex(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.psetex(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Listen for messages published to channels matching the given patterns
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":["pattern"],"type":["pattern"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void psubscribe(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.psubscribe(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Inspect the state of the Pub/Sub subsystem
   * @since 2.8.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void pubsub(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pubsub(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the time to live for a key in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void pttl(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.pttl(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Post a message to a channel
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"channel","type":"string"},{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void publish(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.publish(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"pattern","type":"pattern","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void punsubscribe(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.punsubscribe(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Close the connection
   * @since 1.0.0
   * group: connection

   */
  public void quit(Handler<AsyncResult<String>> handler) {
    this.delegate.quit(handler);
  }
  /**
   * Return a random key from the keyspace
   * @since 1.0.0
   * group: generic

   */
  public void randomkey(Handler<AsyncResult<String>> handler) {
    this.delegate.randomkey(handler);
  }
  /**
   * Rename a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void rename(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.rename(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Rename a key, only if the new key does not exist
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void renamenx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.renamenx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"ttl","type":"integer"},{"name":"serialized-value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void restore(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.restore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Return the role of the instance in the context of replication
   * @since 2.8.12
   * group: server

   */
  public void role(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.role(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove and get the last element in a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void rpop(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.rpop(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove the last element in a list, append it to another list and return it
   * @since 1.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void rpoplpush(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.rpoplpush(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Append one or multiple values to a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void rpush(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.rpush(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Append a value to a list, only if the list exists
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void rpushx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.rpushx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Add one or more members to a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sadd(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.sadd(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Synchronously save the dataset to disk
   * @since 1.0.0
   * group: server

   */
  public void save(Handler<AsyncResult<String>> handler) {
    this.delegate.save(handler);
  }
  /**
   * Get the number of members in a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void scard(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.scard(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Check existence of scripts in the script cache.
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void scriptExists(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.scriptExists(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove all the scripts from the script cache.
   * @since 2.6.0
   * group: scripting

   */
  public void scriptFlush(Handler<AsyncResult<String>> handler) {
    this.delegate.scriptFlush(handler);
  }
  /**
   * Kill the script currently in execution.
   * @since 2.6.0
   * group: scripting

   */
  public void scriptKill(Handler<AsyncResult<String>> handler) {
    this.delegate.scriptKill(handler);
  }
  /**
   * Load the specified Lua script into the script cache.
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void scriptLoad(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.scriptLoad(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Subtract multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sdiff(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sdiff(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Subtract multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sdiffstore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.sdiffstore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Change the selected database for the current connection
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void select(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.select(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the string value of a key
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"},{"command":"EX","name":"seconds","type":"integer","optional":true},{"command":"PX","name":"milliseconds","type":"integer","optional":true},{"name":"condition","type":"enum","enum":["NX","XX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void set(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.set(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Sets or clears the bit at offset in the string value stored at key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void setbit(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.setbit(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the value and expiration of a key
   * @since 2.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void setex(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.setex(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Set the value of a key, only if the key does not exist
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void setnx(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.setnx(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Overwrite part of a string at key starting at the specified offset
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void setrange(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.setrange(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Synchronously save the dataset to disk and then shut down the server
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"NOSAVE","type":"enum","enum":["NOSAVE"],"optional":true},{"name":"SAVE","type":"enum","enum":["SAVE"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void shutdown(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.shutdown(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Intersect multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sinter(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sinter(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Intersect multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sinterstore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.sinterstore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Determine if a given value is a member of a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void sismember(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.sismember(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Make the server a slave of another instance, or promote it as master
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void slaveof(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.slaveof(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Manages the Redis slow queries log
   * @since 2.2.12
   * group: server
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void slowlog(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.slowlog(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get all the members in a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void smembers(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.smembers(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Move a member from one set to another
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void smove(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.smove(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Sort the elements in a list, set or sorted set
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"command":"BY","name":"pattern","type":"pattern","optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true},{"command":"GET","name":"pattern","type":"string","optional":true,"multiple":true},{"name":"order","type":"enum","enum":["ASC","DESC"],"optional":true},{"name":"sorting","type":"enum","enum":["ALPHA"],"optional":true},{"command":"STORE","name":"destination","type":"key","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void sort(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sort(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove and return a random member from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void spop(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.spop(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get one or multiple random members from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void srandmember(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.srandmember(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Remove one or more members from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void srem(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.srem(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the length of the value stored in a key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void strlen(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.strlen(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Listen for messages published to the given channels
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":["channel"],"type":["string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void subscribe(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.subscribe(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Add multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sunion(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.sunion(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Add multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void sunionstore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.sunionstore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Internal command used for replication
   * @since 1.0.0
   * group: server

   */
  public void sync(Handler<AsyncResult<Void>> handler) {
    this.delegate.sync(handler);
  }
  /**
   * Return the current server time
   * @since 2.6.0
   * group: server

   */
  public void time(Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.time(new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Get the time to live for a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void ttl(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.ttl(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Determine the type stored at key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void type(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.type(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Stop listening for messages posted to the given channels
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"channel","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void unsubscribe(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.unsubscribe(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Forget about all watched keys
   * @since 2.2.0
   * group: transactions

   */
  public void unwatch(Handler<AsyncResult<String>> handler) {
    this.delegate.unwatch(handler);
  }
  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @since 2.2.0
   * group: transactions
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void watch(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.watch(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["score","member"],"type":["double","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void zadd(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zadd(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the number of members in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  public void zcard(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zcard(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Count the members in a sorted set with scores within the given values
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  public void zcount(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zcount(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Increment the score of a member in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zincrby(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.zincrby(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zinterstore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zinterstore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Count the number of members in a sorted set between a given lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zlexcount(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zlexcount(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Return a range of members in a sorted set, by index
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrange(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrange(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Return a range of members in a sorted set, by lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrangebylex(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrangebylex(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Return a range of members in a sorted set, by score
   * @since 1.0.5
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrangebyscore(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrangebyscore(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Determine the index of a member in a sorted set
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zrank(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrank(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove one or more members from a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrem(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrem(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove all members in a sorted set between the given lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zremrangebylex(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebylex(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove all members in a sorted set within the given indexes
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  public void zremrangebyrank(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebyrank(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Remove all members in a sorted set within the given scores
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  public void zremrangebyscore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zremrangebyscore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrevrange(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrevrange(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * @since 2.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"max","type":"double"},{"name":"min","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zrevrangebyscore(List<Object> args, Handler<AsyncResult<List<Object>>> handler) {
    this.delegate.zrevrangebyscore(args != null ? new io.vertx.core.json.JsonArray(args) : null, new Handler<AsyncResult<io.vertx.core.json.JsonArray>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonArray> event) {
        AsyncResult<List<Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<List<Object>>result(event.result()?.toList())
        } else {
          f = InternalHelper.<List<Object>>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zrevrank(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zrevrank(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Get the score associated with the given member in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  public void zscore(List<Object> args, Handler<AsyncResult<String>> handler) {
    this.delegate.zscore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zunionstore(List<Object> args, Handler<AsyncResult<Long>> handler) {
    this.delegate.zunionstore(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Incrementally iterate the keys space
   * @since 2.8.0
   * group: generic
   *
   * @param args JsonArray [{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void scan(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.scan(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Incrementally iterate Set elements
   * @since 2.8.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void sscan(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.sscan(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Incrementally iterate hash fields and associated values
   * @since 2.8.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void hscan(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.hscan(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }
  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @since 2.8.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  public void zscan(List<Object> args, Handler<AsyncResult<Void>> handler) {
    this.delegate.zscan(args != null ? new io.vertx.core.json.JsonArray(args) : null, handler);
  }

  static final java.util.function.Function<io.vertx.redis.RedisService, RedisService> FACTORY = io.vertx.lang.groovy.Factories.createFactory() {
    io.vertx.redis.RedisService arg -> new RedisService(arg);
  };
}
