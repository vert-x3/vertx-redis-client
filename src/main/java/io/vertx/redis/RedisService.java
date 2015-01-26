package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.impl.RedisServiceImpl;
import io.vertx.serviceproxy.ProxyHelper;

@VertxGen
@ProxyGen
public interface RedisService {

  static RedisService create(Vertx vertx, JsonObject config) {
    return new RedisServiceImpl(vertx, config);
  }

  static RedisService createEventBusProxy(Vertx vertx, String address) {
    return ProxyHelper.createProxy(RedisService.class, vertx, address);
  }

  @ProxyIgnore
  void start(Handler<AsyncResult<Void>> handler);

  @ProxyIgnore
  void stop(Handler<AsyncResult<Void>> handler);

  /**
   * Append a value to a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: string
   */
  @Fluent
  RedisService append(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Authenticate to the server
   *
   * @param args    JsonArray [{"name":"password","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: connection
   */
  @Fluent
  RedisService auth(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously rewrite the append-only file
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService bgrewriteaof(Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously save the dataset to disk
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService bgsave(Handler<AsyncResult<String>> handler);

  /**
   * Count set bits in a string
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":["start","end"],"type":["integer","integer"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: string
   */
  @Fluent
  RedisService bitcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Perform bitwise operations between strings
   *
   * @param args    JsonArray [{"name":"operation","type":"string"},{"name":"destkey","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: string
   */
  @Fluent
  RedisService bitop(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Find first bit set or clear in a string
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"bit","type":"integer"},{"name":"start","type":"integer","optional":true},{"name":"end","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.7
   * group: string
   */
  @Fluent
  RedisService bitpos(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove and get the first element in a list, or block until one is available
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: list
   */
  @Fluent
  RedisService blpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and get the last element in a list, or block until one is available
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: list
   */
  @Fluent
  RedisService brpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   *
   * @param args    JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: list
   */
  @Fluent
  RedisService brpoplpush(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Kill the connection of a client
   *
   * @param args    JsonArray [{"name":"ip:port","type":"string","optional":true},{"command":"ID","name":"client-id","type":"integer","optional":true},{"command":"TYPE","type":"enum","enum":["normal","slave","pubsub"],"optional":true},{"command":"ADDR","name":"ip:port","type":"string","optional":true},{"command":"SKIPME","name":"yes/no","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.4.0
   * group: server
   */
  @Fluent
  RedisService clientKill(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the list of client connections
   *
   * @since 2.4.0
   * group: server
   */
  @Fluent
  RedisService clientList(Handler<AsyncResult<String>> handler);

  /**
   * Get the current connection name
   *
   * @since 2.6.9
   * group: server
   */
  @Fluent
  RedisService clientGetname(Handler<AsyncResult<String>> handler);

  /**
   * Stop processing commands from clients for some time
   *
   * @param args    JsonArray [{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.9.50
   * group: server
   */
  @Fluent
  RedisService clientPause(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the current connection name
   *
   * @param args    JsonArray [{"name":"connection-name","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.9
   * group: server
   */
  @Fluent
  RedisService clientSetname(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get array of Cluster slot to node mappings
   *
   * @since 3.0.0
   * group: server
   */
  @Fluent
  RedisService clusterSlots(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get array of Redis command details
   *
   * @since 2.8.13
   * group: server
   */
  @Fluent
  RedisService command(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get total number of Redis commands
   *
   * @since 2.8.13
   * group: server
   */
  @Fluent
  RedisService commandCount(Handler<AsyncResult<Long>> handler);

  /**
   * Extract keys given a full Redis command
   *
   * @since 2.8.13
   * group: server
   */
  @Fluent
  RedisService commandGetkeys(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get array of specific Redis command details
   *
   * @param args    JsonArray [{"name":"command-name","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.13
   * group: server
   */
  @Fluent
  RedisService commandInfo(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the value of a configuration parameter
   *
   * @param args    JsonArray [{"name":"parameter","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: server
   */
  @Fluent
  RedisService configGet(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Rewrite the configuration file with the in memory configuration
   *
   * @since 2.8.0
   * group: server
   */
  @Fluent
  RedisService configRewrite(Handler<AsyncResult<String>> handler);

  /**
   * Set a configuration parameter to the given value
   *
   * @param args    JsonArray [{"name":"parameter","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: server
   */
  @Fluent
  RedisService configSet(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Reset the stats returned by INFO
   *
   * @since 2.0.0
   * group: server
   */
  @Fluent
  RedisService configResetstat(Handler<AsyncResult<String>> handler);

  /**
   * Return the number of keys in the selected database
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService dbsize(Handler<AsyncResult<Long>> handler);

  /**
   * Get debugging information about a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService debugObject(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Make the server crash
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService debugSegfault(Handler<AsyncResult<String>> handler);

  /**
   * Decrement the integer value of a key by one
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService decr(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Decrement the integer value of a key by the given number
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"decrement","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService decrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Delete a key
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService del(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Discard all commands issued after MULTI
   *
   * @since 2.0.0
   * group: transactions
   */
  @Fluent
  RedisService discard(Handler<AsyncResult<String>> handler);

  /**
   * Return a serialized version of the value stored at the specified key.
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService dump(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Echo the given string
   *
   * @param args    JsonArray [{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: connection
   */
  @Fluent
  RedisService echo(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Execute a Lua script server side
   *
   * @param args    JsonArray [{"name":"script","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService eval(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Execute a Lua script server side
   *
   * @param args    JsonArray [{"name":"sha1","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService evalsha(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Execute all commands issued after MULTI
   *
   * @since 1.2.0
   * group: transactions
   */
  @Fluent
  RedisService exec(Handler<AsyncResult<Void>> handler);

  /**
   * Determine if a key exists
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService exists(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set a key's time to live in seconds
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService expire(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: generic
   */
  @Fluent
  RedisService expireat(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all keys from all databases
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService flushall(Handler<AsyncResult<String>> handler);

  /**
   * Remove all keys from the current database
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService flushdb(Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService get(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Returns the bit value at offset in the string value stored at key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: string
   */
  @Fluent
  RedisService getbit(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get a substring of the string stored at a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"end","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.4.0
   * group: string
   */
  @Fluent
  RedisService getrange(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key and return its old value
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService getset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Delete one or more hash fields
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hdel(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine if a hash field exists
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hexists(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the value of a hash field
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hget(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields and values in a hash
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hgetall(JsonArray args, Handler<AsyncResult<JsonObject>> handler);

  /**
   * Increment the integer value of a hash field by the given number
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hincrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the float value of a hash field by the given amount
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: hash
   */
  @Fluent
  RedisService hincrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields in a hash
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hkeys(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the number of fields in a hash
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hlen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the values of all the given hash fields
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hmget(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Set multiple hash fields to multiple values
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":["field","value"],"type":["string","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hmset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a hash field
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hset(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value of a hash field, only if the field does not exist
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hsetnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get all the values in a hash
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: hash
   */
  @Fluent
  RedisService hvals(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Increment the integer value of a key by one
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService incr(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the integer value of a key by the given amount
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService incrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the float value of a key by the given amount
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: string
   */
  @Fluent
  RedisService incrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get information and statistics about the server
   *
   * @param args    JsonArray [{"name":"section","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService info(JsonArray args, Handler<AsyncResult<JsonObject>> handler);

  /**
   * Find all keys matching the given pattern
   *
   * @param args    JsonArray [{"name":"pattern","type":"pattern"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService keys(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the UNIX time stamp of the last successful save to disk
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService lastsave(Handler<AsyncResult<Long>> handler);

  /**
   * Get an element from a list by its index
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lindex(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Insert an element before or after another element in a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"where","type":"enum","enum":["BEFORE","AFTER"]},{"name":"pivot","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: list
   */
  @Fluent
  RedisService linsert(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the length of a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService llen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove and get the first element in a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lpop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Prepend one or multiple values to a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lpush(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Prepend a value to a list, only if the list exists
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: list
   */
  @Fluent
  RedisService lpushx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get a range of elements from a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove elements from a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lrem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value of an element in a list by its index
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService lset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Trim a list to the specified range
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService ltrim(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get the values of all the given keys
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService mget(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Atomically transfer a key from a Redis instance to another one.
   *
   * @param args    JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"},{"name":"key","type":"key"},{"name":"destination-db","type":"integer"},{"name":"timeout","type":"integer"},{"name":"copy","type":"enum","enum":["COPY"],"optional":true},{"name":"replace","type":"enum","enum":["REPLACE"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService migrate(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Listen for all requests received by the server in real time
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService monitor(Handler<AsyncResult<Void>> handler);

  /**
   * Move a key to another database
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"db","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService move(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set multiple keys to multiple values
   *
   * @param args    JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.1
   * group: string
   */
  @Fluent
  RedisService mset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   *
   * @param args    JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.1
   * group: string
   */
  @Fluent
  RedisService msetnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Mark the start of a transaction block
   *
   * @since 1.2.0
   * group: transactions
   */
  @Fluent
  RedisService multi(Handler<AsyncResult<String>> handler);

  /**
   * Inspect the internals of Redis objects
   *
   * @param args    JsonArray [{"name":"subcommand","type":"string"},{"name":"arguments","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.2.3
   * group: generic
   */
  @Fluent
  RedisService object(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Remove the expiration from a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: generic
   */
  @Fluent
  RedisService persist(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set a key's time to live in milliseconds
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService pexpire(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"milliseconds-timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService pexpireat(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Adds the specified elements to the specified HyperLogLog.
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"element","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisService pfadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisService pfcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Merge N different HyperLogLogs into a single one.
   *
   * @param args    JsonArray [{"name":"destkey","type":"key"},{"name":"sourcekey","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: hyperloglog
   */
  @Fluent
  RedisService pfmerge(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Ping the server
   *
   * @since 1.0.0
   * group: connection
   */
  @Fluent
  RedisService ping(Handler<AsyncResult<String>> handler);

  /**
   * Set the value and expiration in milliseconds of a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: string
   */
  @Fluent
  RedisService psetex(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Listen for messages published to channels matching the given patterns
   *
   * @param args    JsonArray [{"name":["pattern"],"type":["pattern"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisService psubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Inspect the state of the Pub/Sub subsystem
   *
   * @param args    JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.0
   * group: pubsub
   */
  @Fluent
  RedisService pubsub(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the time to live for a key in milliseconds
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService pttl(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Post a message to a channel
   *
   * @param args    JsonArray [{"name":"channel","type":"string"},{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisService publish(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Stop listening for messages posted to channels matching the given patterns
   *
   * @param args    JsonArray [{"name":"pattern","type":"pattern","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisService punsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Close the connection
   *
   * @since 1.0.0
   * group: connection
   */
  @Fluent
  RedisService quit(Handler<AsyncResult<String>> handler);

  /**
   * Return a random key from the keyspace
   *
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService randomkey(Handler<AsyncResult<String>> handler);

  /**
   * Rename a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService rename(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Rename a key, only if the new key does not exist
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService renamenx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"ttl","type":"integer"},{"name":"serialized-value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: generic
   */
  @Fluent
  RedisService restore(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Return the role of the instance in the context of replication
   *
   * @since 2.8.12
   * group: server
   */
  @Fluent
  RedisService role(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and get the last element in a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService rpop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Remove the last element in a list, append it to another list and return it
   *
   * @param args    JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: list
   */
  @Fluent
  RedisService rpoplpush(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Append one or multiple values to a list
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: list
   */
  @Fluent
  RedisService rpush(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Append a value to a list, only if the list exists
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: list
   */
  @Fluent
  RedisService rpushx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Add one or more members to a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Synchronously save the dataset to disk
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService save(Handler<AsyncResult<String>> handler);

  /**
   * Get the number of members in a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService scard(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Check existence of scripts in the script cache.
   *
   * @param args    JsonArray [{"name":"script","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService scriptExists(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove all the scripts from the script cache.
   *
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService scriptFlush(Handler<AsyncResult<String>> handler);

  /**
   * Kill the script currently in execution.
   *
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService scriptKill(Handler<AsyncResult<String>> handler);

  /**
   * Load the specified Lua script into the script cache.
   *
   * @param args    JsonArray [{"name":"script","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.6.0
   * group: scripting
   */
  @Fluent
  RedisService scriptLoad(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Subtract multiple sets
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sdiff(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Subtract multiple sets and store the resulting set in a key
   *
   * @param args    JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sdiffstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Change the selected database for the current connection
   *
   * @param args    JsonArray [{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: connection
   */
  @Fluent
  RedisService select(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"},{"command":"EX","name":"seconds","type":"integer","optional":true},{"command":"PX","name":"milliseconds","type":"integer","optional":true},{"name":"condition","type":"enum","enum":["NX","XX"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService set(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Sets or clears the bit at offset in the string value stored at key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: string
   */
  @Fluent
  RedisService setbit(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value and expiration of a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: string
   */
  @Fluent
  RedisService setex(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the value of a key, only if the key does not exist
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: string
   */
  @Fluent
  RedisService setnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Overwrite part of a string at key starting at the specified offset
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: string
   */
  @Fluent
  RedisService setrange(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Synchronously save the dataset to disk and then shut down the server
   *
   * @param args    JsonArray [{"name":"NOSAVE","type":"enum","enum":["NOSAVE"],"optional":true},{"name":"SAVE","type":"enum","enum":["SAVE"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService shutdown(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sets
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sinter(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Intersect multiple sets and store the resulting set in a key
   *
   * @param args    JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sinterstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine if a given value is a member of a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sismember(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Make the server a slave of another instance, or promote it as master
   *
   * @param args    JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService slaveof(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Manages the Redis slow queries log
   *
   * @param args    JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.2.12
   * group: server
   */
  @Fluent
  RedisService slowlog(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Get all the members in a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService smembers(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Move a member from one set to another
   *
   * @param args    JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService smove(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Sort the elements in a list, set or sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"command":"BY","name":"pattern","type":"pattern","optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true},{"command":"GET","name":"pattern","type":"string","optional":true,"multiple":true},{"name":"order","type":"enum","enum":["ASC","DESC"],"optional":true},{"name":"sorting","type":"enum","enum":["ALPHA"],"optional":true},{"command":"STORE","name":"destination","type":"key","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService sort(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and return a random member from a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService spop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get one or multiple random members from a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService srandmember(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove one or more members from a set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService srem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the length of the value stored in a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: string
   */
  @Fluent
  RedisService strlen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Listen for messages published to the given channels
   *
   * @param args    JsonArray [{"name":["channel"],"type":["string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisService subscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Add multiple sets
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sunion(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Add multiple sets and store the resulting set in a key
   *
   * @param args    JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: set
   */
  @Fluent
  RedisService sunionstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Internal command used for replication
   *
   * @since 1.0.0
   * group: server
   */
  @Fluent
  RedisService sync(Handler<AsyncResult<Void>> handler);

  /**
   * Return the current server time
   *
   * @since 2.6.0
   * group: server
   */
  @Fluent
  RedisService time(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the time to live for a key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService ttl(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine the type stored at key
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.0.0
   * group: generic
   */
  @Fluent
  RedisService type(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Stop listening for messages posted to the given channels
   *
   * @param args    JsonArray [{"name":"channel","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: pubsub
   */
  @Fluent
  RedisService unsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Forget about all watched keys
   *
   * @since 2.2.0
   * group: transactions
   */
  @Fluent
  RedisService unwatch(Handler<AsyncResult<String>> handler);

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   *
   * @param args    JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: transactions
   */
  @Fluent
  RedisService watch(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":["score","member"],"type":["double","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the number of members in a sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zcard(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Count the members in a sorted set with scores within the given values
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the score of a member in a sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zincrby(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   *
   * @param args    JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zinterstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Count the number of members in a sorted set between a given lexicographical range
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisService zlexcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return a range of members in a sorted set, by index
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by lexicographical range
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisService zrangebylex(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by score
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.0.5
   * group: sorted_set
   */
  @Fluent
  RedisService zrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Determine the index of a member in a sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove one or more members from a sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set between the given lexicographical range
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.8.9
   * group: sorted_set
   */
  @Fluent
  RedisService zremrangebylex(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set within the given indexes
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zremrangebyrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set within the given scores
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zremrangebyscore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrevrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"max","type":"double"},{"name":"min","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrevrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zrevrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the score associated with the given member in a sorted set
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   * @since 1.2.0
   * group: sorted_set
   */
  @Fluent
  RedisService zscore(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   *
   * @param args    JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.0.0
   * group: sorted_set
   */
  @Fluent
  RedisService zunionstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Incrementally iterate the keys space
   *
   * @param args    JsonArray [{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.0
   * group: generic
   */
  @Fluent
  RedisService scan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate Set elements
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.0
   * group: set
   */
  @Fluent
  RedisService sscan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate hash fields and associated values
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.0
   * group: hash
   */
  @Fluent
  RedisService hscan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate sorted sets elements and associated scores
   *
   * @param args    JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   * @since 2.8.0
   * group: sorted_set
   */
  @Fluent
  RedisService zscan(JsonArray args, Handler<AsyncResult<Void>> handler);

}
