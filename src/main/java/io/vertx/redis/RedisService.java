package io.vertx.redis;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;

import io.vertx.core.json.*;
import io.vertx.redis.spi.RedisServiceFactory;

@VertxGen
public interface RedisService {
  static RedisService create(Vertx vertx, JsonObject config) {
    return factory.create(vertx, config);
  }

  static RedisService createEventBusProxy(Vertx vertx, String address) {
    return factory.createEventBusProxy(vertx, address);
  }

  void start(Handler<AsyncResult<Void>> handler);
  void stop(Handler<AsyncResult<Void>> handler);

  static final RedisServiceFactory factory = ServiceHelper.loadFactory(RedisServiceFactory.class);

  /**
   * Append a value to a key
   * @since 2.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void append(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Authenticate to the server
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"password","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void auth(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously rewrite the append-only file
   * @since 1.0.0
   * group: server
   *
   */
  void bgrewriteaof(Handler<AsyncResult<String>> handler);

  /**
   * Asynchronously save the dataset to disk
   * @since 1.0.0
   * group: server
   *
   */
  void bgsave(Handler<AsyncResult<String>> handler);

  /**
   * Count set bits in a string
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["start","end"],"type":["integer","integer"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void bitcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Perform bitwise operations between strings
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"operation","type":"string"},{"name":"destkey","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void bitop(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Find first bit set or clear in a string
   * @since 2.8.7
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"bit","type":"integer"},{"name":"start","type":"integer","optional":true},{"name":"end","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void bitpos(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove and get the first element in a list, or block until one is available
   * @since 2.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void blpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and get the last element in a list, or block until one is available
   * @since 2.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void brpop(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Pop a value from a list, push it to another list and return it; or block until one is available
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void brpoplpush(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Kill the connection of a client
   * @since 2.4.0
   * group: server
   *
   * @param args JsonArray [{"name":"ip:port","type":"string","optional":true},{"command":"ID","name":"client-id","type":"integer","optional":true},{"command":"TYPE","type":"enum","enum":["normal","slave","pubsub"],"optional":true},{"command":"ADDR","name":"ip:port","type":"string","optional":true},{"command":"SKIPME","name":"yes/no","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void clientKill(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the list of client connections
   * @since 2.4.0
   * group: server
   *
   */
  void clientList(Handler<AsyncResult<String>> handler);

  /**
   * Get the current connection name
   * @since 2.6.9
   * group: server
   *
   */
  void clientGetname(Handler<AsyncResult<String>> handler);

  /**
   * Stop processing commands from clients for some time
   * @since 2.9.50
   * group: server
   *
   * @param args JsonArray [{"name":"timeout","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void clientPause(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the current connection name
   * @since 2.6.9
   * group: server
   *
   * @param args JsonArray [{"name":"connection-name","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void clientSetname(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get array of Cluster slot to node mappings
   * @since 3.0.0
   * group: server
   *
   */
  void clusterSlots(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get array of Redis command details
   * @since 2.8.13
   * group: server
   *
   */
  void command(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get total number of Redis commands
   * @since 2.8.13
   * group: server
   *
   */
  void commandCount(Handler<AsyncResult<Long>> handler);

  /**
   * Extract keys given a full Redis command
   * @since 2.8.13
   * group: server
   *
   */
  void commandGetkeys(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get array of specific Redis command details
   * @since 2.8.13
   * group: server
   *
   * @param args JsonArray [{"name":"command-name","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void commandInfo(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the value of a configuration parameter
   * @since 2.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"parameter","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void configGet(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Rewrite the configuration file with the in memory configuration
   * @since 2.8.0
   * group: server
   *
   */
  void configRewrite(Handler<AsyncResult<String>> handler);

  /**
   * Set a configuration parameter to the given value
   * @since 2.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"parameter","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void configSet(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Reset the stats returned by INFO
   * @since 2.0.0
   * group: server
   *
   */
  void configResetstat(Handler<AsyncResult<String>> handler);

  /**
   * Return the number of keys in the selected database
   * @since 1.0.0
   * group: server
   *
   */
  void dbsize(Handler<AsyncResult<Long>> handler);

  /**
   * Get debugging information about a key
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void debugObject(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Make the server crash
   * @since 1.0.0
   * group: server
   *
   */
  void debugSegfault(Handler<AsyncResult<String>> handler);

  /**
   * Decrement the integer value of a key by one
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void decr(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Decrement the integer value of a key by the given number
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"decrement","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void decrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Delete a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void del(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Discard all commands issued after MULTI
   * @since 2.0.0
   * group: transactions
   *
   */
  void discard(Handler<AsyncResult<String>> handler);

  /**
   * Return a serialized version of the value stored at the specified key.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void dump(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Echo the given string
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void echo(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Execute a Lua script server side
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void eval(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Execute a Lua script server side
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"sha1","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void evalsha(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Execute all commands issued after MULTI
   * @since 1.2.0
   * group: transactions
   *
   */
  void exec(Handler<AsyncResult<Void>> handler);

  /**
   * Determine if a key exists
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void exists(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set a key's time to live in seconds
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void expire(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp
   * @since 1.2.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   */
  void expireat(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all keys from all databases
   * @since 1.0.0
   * group: server
   *
   */
  void flushall(Handler<AsyncResult<String>> handler);

  /**
   * Remove all keys from the current database
   * @since 1.0.0
   * group: server
   *
   */
  void flushdb(Handler<AsyncResult<String>> handler);

  /**
   * Get the value of a key
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void get(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Returns the bit value at offset in the string value stored at key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void getbit(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get a substring of the string stored at a key
   * @since 2.4.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"end","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void getrange(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key and return its old value
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void getset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Delete one or more hash fields
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void hdel(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine if a hash field exists
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void hexists(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the value of a hash field
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void hget(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields and values in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void hgetall(JsonArray args, Handler<AsyncResult<JsonObject>> handler);

  /**
   * Increment the integer value of a hash field by the given number
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void hincrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the float value of a hash field by the given amount
   * @since 2.6.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  void hincrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get all the fields in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void hkeys(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the number of fields in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void hlen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the values of all the given hash fields
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void hmget(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Set multiple hash fields to multiple values
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["field","value"],"type":["string","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void hmset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a hash field
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void hset(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value of a hash field, only if the field does not exist
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void hsetnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get all the values in a hash
   * @since 2.0.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void hvals(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Increment the integer value of a key by one
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void incr(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the integer value of a key by the given amount
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void incrby(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the float value of a key by the given amount
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  void incrbyfloat(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get information and statistics about the server
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"section","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void info(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Find all keys matching the given pattern
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"pattern","type":"pattern"}]
   * @param handler Handler for the result of this call.
   */
  void keys(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the UNIX time stamp of the last successful save to disk
   * @since 1.0.0
   * group: server
   *
   */
  void lastsave(Handler<AsyncResult<Long>> handler);

  /**
   * Get an element from a list by its index
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void lindex(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Insert an element before or after another element in a list
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"where","type":"enum","enum":["BEFORE","AFTER"]},{"name":"pivot","type":"string"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void linsert(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the length of a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void llen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove and get the first element in a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void lpop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Prepend one or multiple values to a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void lpush(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Prepend a value to a list, only if the list exists
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void lpushx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get a range of elements from a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void lrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove elements from a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void lrem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value of an element in a list by its index
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void lset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Trim a list to the specified range
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void ltrim(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get the values of all the given keys
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void mget(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Atomically transfer a key from a Redis instance to another one.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"},{"name":"key","type":"key"},{"name":"destination-db","type":"integer"},{"name":"timeout","type":"integer"},{"name":"copy","type":"enum","enum":["COPY"],"optional":true},{"name":"replace","type":"enum","enum":["REPLACE"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void migrate(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Listen for all requests received by the server in real time
   * @since 1.0.0
   * group: server
   *
   */
  void monitor(Handler<AsyncResult<Void>> handler);

  /**
   * Move a key to another database
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"db","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void move(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set multiple keys to multiple values
   * @since 1.0.1
   * group: string
   *
   * @param args JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void mset(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set multiple keys to multiple values, only if none of the keys exist
   * @since 1.0.1
   * group: string
   *
   * @param args JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void msetnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Mark the start of a transaction block
   * @since 1.2.0
   * group: transactions
   *
   */
  void multi(Handler<AsyncResult<String>> handler);

  /**
   * Inspect the internals of Redis objects
   * @since 2.2.3
   * group: generic
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"arguments","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void object(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Remove the expiration from a key
   * @since 2.2.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void persist(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set a key's time to live in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void pexpire(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the expiration for a key as a UNIX timestamp specified in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds-timestamp","type":"posix time"}]
   * @param handler Handler for the result of this call.
   */
  void pexpireat(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Adds the specified elements to the specified HyperLogLog.
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"element","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void pfadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void pfcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Merge N different HyperLogLogs into a single one.
   * @since 2.8.9
   * group: hyperloglog
   *
   * @param args JsonArray [{"name":"destkey","type":"key"},{"name":"sourcekey","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void pfmerge(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Ping the server
   * @since 1.0.0
   * group: connection
   *
   */
  void ping(Handler<AsyncResult<String>> handler);

  /**
   * Set the value and expiration in milliseconds of a key
   * @since 2.6.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void psetex(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Listen for messages published to channels matching the given patterns
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":["pattern"],"type":["pattern"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void psubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Inspect the state of the Pub/Sub subsystem
   * @since 2.8.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void pubsub(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the time to live for a key in milliseconds
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void pttl(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Post a message to a channel
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"channel","type":"string"},{"name":"message","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void publish(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Stop listening for messages posted to channels matching the given patterns
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"pattern","type":"pattern","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void punsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Close the connection
   * @since 1.0.0
   * group: connection
   *
   */
  void quit(Handler<AsyncResult<String>> handler);

  /**
   * Return a random key from the keyspace
   * @since 1.0.0
   * group: generic
   *
   */
  void randomkey(Handler<AsyncResult<String>> handler);

  /**
   * Rename a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void rename(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Rename a key, only if the new key does not exist
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void renamenx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Create a key using the provided serialized value, previously obtained using DUMP.
   * @since 2.6.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"ttl","type":"integer"},{"name":"serialized-value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void restore(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Return the role of the instance in the context of replication
   * @since 2.8.12
   * group: server
   *
   */
  void role(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and get the last element in a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void rpop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Remove the last element in a list, append it to another list and return it
   * @since 1.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void rpoplpush(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Append one or multiple values to a list
   * @since 1.0.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void rpush(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Append a value to a list, only if the list exists
   * @since 2.2.0
   * group: list
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void rpushx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Add one or more members to a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Synchronously save the dataset to disk
   * @since 1.0.0
   * group: server
   *
   */
  void save(Handler<AsyncResult<String>> handler);

  /**
   * Get the number of members in a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void scard(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Check existence of scripts in the script cache.
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void scriptExists(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove all the scripts from the script cache.
   * @since 2.6.0
   * group: scripting
   *
   */
  void scriptFlush(Handler<AsyncResult<String>> handler);

  /**
   * Kill the script currently in execution.
   * @since 2.6.0
   * group: scripting
   *
   */
  void scriptKill(Handler<AsyncResult<String>> handler);

  /**
   * Load the specified Lua script into the script cache.
   * @since 2.6.0
   * group: scripting
   *
   * @param args JsonArray [{"name":"script","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void scriptLoad(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Subtract multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sdiff(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Subtract multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sdiffstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Change the selected database for the current connection
   * @since 1.0.0
   * group: connection
   *
   * @param args JsonArray [{"name":"index","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void select(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the string value of a key
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"},{"command":"EX","name":"seconds","type":"integer","optional":true},{"command":"PX","name":"milliseconds","type":"integer","optional":true},{"name":"condition","type":"enum","enum":["NX","XX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void set(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Sets or clears the bit at offset in the string value stored at key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void setbit(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Set the value and expiration of a key
   * @since 2.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void setex(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Set the value of a key, only if the key does not exist
   * @since 1.0.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void setnx(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Overwrite part of a string at key starting at the specified offset
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void setrange(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Synchronously save the dataset to disk and then shut down the server
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"NOSAVE","type":"enum","enum":["NOSAVE"],"optional":true},{"name":"SAVE","type":"enum","enum":["SAVE"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void shutdown(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sinter(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Intersect multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sinterstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine if a given value is a member of a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void sismember(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Make the server a slave of another instance, or promote it as master
   * @since 1.0.0
   * group: server
   *
   * @param args JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void slaveof(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Manages the Redis slow queries log
   * @since 2.2.12
   * group: server
   *
   * @param args JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void slowlog(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Get all the members in a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void smembers(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Move a member from one set to another
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void smove(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Sort the elements in a list, set or sorted set
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"command":"BY","name":"pattern","type":"pattern","optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true},{"command":"GET","name":"pattern","type":"string","optional":true,"multiple":true},{"name":"order","type":"enum","enum":["ASC","DESC"],"optional":true},{"name":"sorting","type":"enum","enum":["ALPHA"],"optional":true},{"command":"STORE","name":"destination","type":"key","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void sort(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove and return a random member from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void spop(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Get one or multiple random members from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void srandmember(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Remove one or more members from a set
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void srem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the length of the value stored in a key
   * @since 2.2.0
   * group: string
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void strlen(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Listen for messages published to the given channels
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":["channel"],"type":["string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void subscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Add multiple sets
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sunion(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Add multiple sets and store the resulting set in a key
   * @since 1.0.0
   * group: set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void sunionstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Internal command used for replication
   * @since 1.0.0
   * group: server
   *
   */
  void sync(Handler<AsyncResult<Void>> handler);

  /**
   * Return the current server time
   * @since 2.6.0
   * group: server
   *
   */
  void time(Handler<AsyncResult<JsonArray>> handler);

  /**
   * Get the time to live for a key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void ttl(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Determine the type stored at key
   * @since 1.0.0
   * group: generic
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void type(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Stop listening for messages posted to the given channels
   * @since 2.0.0
   * group: pubsub
   *
   * @param args JsonArray [{"name":"channel","type":"string","optional":true,"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void unsubscribe(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Forget about all watched keys
   * @since 2.2.0
   * group: transactions
   *
   */
  void unwatch(Handler<AsyncResult<String>> handler);

  /**
   * Watch the given keys to determine execution of the MULTI/EXEC block
   * @since 2.2.0
   * group: transactions
   *
   * @param args JsonArray [{"name":"key","type":"key","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void watch(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Add one or more members to a sorted set, or update its score if it already exists
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":["score","member"],"type":["double","string"],"multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void zadd(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the number of members in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"}]
   * @param handler Handler for the result of this call.
   */
  void zcard(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Count the members in a sorted set with scores within the given values
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  void zcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Increment the score of a member in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zincrby(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Intersect multiple sorted sets and store the resulting sorted set in a new key
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zinterstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Count the number of members in a sorted set between a given lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zlexcount(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return a range of members in a sorted set, by index
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zrangebylex(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by score
   * @since 1.0.5
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Determine the index of a member in a sorted set
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove one or more members from a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}]
   * @param handler Handler for the result of this call.
   */
  void zrem(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set between the given lexicographical range
   * @since 2.8.9
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zremrangebylex(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set within the given indexes
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}]
   * @param handler Handler for the result of this call.
   */
  void zremrangebyrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Remove all members in a sorted set within the given scores
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}]
   * @param handler Handler for the result of this call.
   */
  void zremrangebyscore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Return a range of members in a sorted set, by index, with scores ordered from high to low
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zrevrange(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Return a range of members in a sorted set, by score, with scores ordered from high to low
   * @since 2.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"max","type":"double"},{"name":"min","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zrevrangebyscore(JsonArray args, Handler<AsyncResult<JsonArray>> handler);

  /**
   * Determine the index of a member in a sorted set, with scores ordered from high to low
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zrevrank(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Get the score associated with the given member in a sorted set
   * @since 1.2.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}]
   * @param handler Handler for the result of this call.
   */
  void zscore(JsonArray args, Handler<AsyncResult<String>> handler);

  /**
   * Add multiple sorted sets and store the resulting sorted set in a new key
   * @since 2.0.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zunionstore(JsonArray args, Handler<AsyncResult<Long>> handler);

  /**
   * Incrementally iterate the keys space
   * @since 2.8.0
   * group: generic
   *
   * @param args JsonArray [{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void scan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate Set elements
   * @since 2.8.0
   * group: set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void sscan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate hash fields and associated values
   * @since 2.8.0
   * group: hash
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void hscan(JsonArray args, Handler<AsyncResult<Void>> handler);

  /**
   * Incrementally iterate sorted sets elements and associated scores
   * @since 2.8.0
   * group: sorted_set
   *
   * @param args JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}]
   * @param handler Handler for the result of this call.
   */
  void zscan(JsonArray args, Handler<AsyncResult<Void>> handler);

}
