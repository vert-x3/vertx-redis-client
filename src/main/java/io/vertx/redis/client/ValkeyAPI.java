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

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.redis.client.impl.ValkeyAPIImpl;

import java.util.List;

import static io.vertx.codegen.annotations.GenIgnore.PERMITTED_TYPE;

/**
 * <b>Auto generated</b> Valkey API client wrapper.
 * @version valkey_version:9.0.3
 */
@VertxGen
public interface ValkeyAPI {

  @GenIgnore(PERMITTED_TYPE)
  static ValkeyAPI api(Redis client) {
    return new ValkeyAPIImpl(client);
  }

  @GenIgnore(PERMITTED_TYPE)
  static ValkeyAPI api(RedisConnection connection) {
    return new ValkeyAPIImpl(connection);
  }

  void close();

  /**
   * A container for Access List Control commands.
   * @see <a href="https://valkey.io/commands/acl/">ACL</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> acl(List<String> args) {
    return send(Command.ACL, args.toArray(new String[0]));
  }

  /**
   * Appends a string to the value of a key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/append/">APPEND</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> append(String arg0, String arg1) {
    return send(Command.APPEND, arg0, arg1);
  }

  /**
   * Signals that a cluster client is following an -ASK redirect.
   * @see <a href="https://valkey.io/commands/asking/">ASKING</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> asking() {
    return send(Command.ASKING);
  }

  /**
   * Authenticates the connection.
   * @see <a href="https://valkey.io/commands/auth/">AUTH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> auth(List<String> args) {
    return send(Command.AUTH, args.toArray(new String[0]));
  }

  /**
   * Asynchronously rewrites the append-only file to disk.
   * @see <a href="https://valkey.io/commands/bgrewriteaof/">BGREWRITEAOF</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> bgrewriteaof() {
    return send(Command.BGREWRITEAOF);
  }

  /**
   * Asynchronously saves the database(s) to disk.
   * @see <a href="https://valkey.io/commands/bgsave/">BGSAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> bgsave(List<String> args) {
    return send(Command.BGSAVE, args.toArray(new String[0]));
  }

  /**
   * Counts the number of set bits (population counting) in a string.
   * @see <a href="https://valkey.io/commands/bitcount/">BITCOUNT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> bitcount(List<String> args) {
    return send(Command.BITCOUNT, args.toArray(new String[0]));
  }

  /**
   * Performs arbitrary bitfield integer operations on strings.
   * @see <a href="https://valkey.io/commands/bitfield/">BITFIELD</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> bitfield(List<String> args) {
    return send(Command.BITFIELD, args.toArray(new String[0]));
  }

  /**
   * Performs arbitrary read-only bitfield integer operations on strings.
   * @see <a href="https://valkey.io/commands/bitfield_ro/">BITFIELD_RO</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> bitfieldRo(List<String> args) {
    return send(Command.BITFIELD_RO, args.toArray(new String[0]));
  }

  /**
   * Performs bitwise operations on multiple strings, and stores the result.
   * @see <a href="https://valkey.io/commands/bitop/">BITOP</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> bitop(List<String> args) {
    return send(Command.BITOP, args.toArray(new String[0]));
  }

  /**
   * Finds the first set (1) or clear (0) bit in a string.
   * @see <a href="https://valkey.io/commands/bitpos/">BITPOS</a>
   * @since Redis 2.8.7
   */
  default Future<@Nullable Response> bitpos(List<String> args) {
    return send(Command.BITPOS, args.toArray(new String[0]));
  }

  /**
   * Pops an element from a list, pushes it to another list and returns it. Blocks until an element is available otherwise. Deletes the list if the last element was moved.
   * @see <a href="https://valkey.io/commands/blmove/">BLMOVE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> blmove(String arg0, String arg1, String arg2, String arg3, String arg4) {
    return send(Command.BLMOVE, arg0, arg1, arg2, arg3, arg4);
  }

  /**
   * Pops the first element from one of multiple lists. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/blmpop/">BLMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> blmpop(List<String> args) {
    return send(Command.BLMPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the first element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/blpop/">BLPOP</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> blpop(List<String> args) {
    return send(Command.BLPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the last element in a list. Blocks until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/brpop/">BRPOP</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> brpop(List<String> args) {
    return send(Command.BRPOP, args.toArray(new String[0]));
  }

  /**
   * Pops an element from a list, pushes it to another list and returns it. Block until an element is available otherwise. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/brpoplpush/">BRPOPLPUSH</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> brpoplpush(String arg0, String arg1, String arg2) {
    return send(Command.BRPOPLPUSH, arg0, arg1, arg2);
  }

  /**
   * Removes and returns a member by score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @see <a href="https://valkey.io/commands/bzmpop/">BZMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> bzmpop(List<String> args) {
    return send(Command.BZMPOP, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the member with the highest score from one or more sorted sets. Blocks until a member available otherwise.  Deletes the sorted set if the last element was popped.
   * @see <a href="https://valkey.io/commands/bzpopmax/">BZPOPMAX</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> bzpopmax(List<String> args) {
    return send(Command.BZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Removes and returns the member with the lowest score from one or more sorted sets. Blocks until a member is available otherwise. Deletes the sorted set if the last element was popped.
   * @see <a href="https://valkey.io/commands/bzpopmin/">BZPOPMIN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> bzpopmin(List<String> args) {
    return send(Command.BZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * A container for client connection commands.
   * @see <a href="https://valkey.io/commands/client/">CLIENT</a>
   * @since Redis 2.4.0
   */
  default Future<@Nullable Response> client(List<String> args) {
    return send(Command.CLIENT, args.toArray(new String[0]));
  }

  /**
   * A container for Cluster commands.
   * @see <a href="https://valkey.io/commands/cluster/">CLUSTER</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> cluster(List<String> args) {
    return send(Command.CLUSTER, args.toArray(new String[0]));
  }

  /**
   * Returns detailed information about all commands.
   * @see <a href="https://valkey.io/commands/command/">COMMAND</a>
   * @since Redis 2.8.13
   */
  default Future<@Nullable Response> command(List<String> args) {
    return send(Command.COMMAND, args.toArray(new String[0]));
  }

  /**
   * A container for command log commands.
   * @see <a href="https://valkey.io/commands/commandlog/">COMMANDLOG</a>
   * @since Valkey 8.1.0
   */
  default Future<@Nullable Response> commandlog(List<String> args) {
    return send(Command.COMMANDLOG, args.toArray(new String[0]));
  }

  /**
   * A container for server configuration commands.
   * @see <a href="https://valkey.io/commands/config/">CONFIG</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> config(List<String> args) {
    return send(Command.CONFIG, args.toArray(new String[0]));
  }

  /**
   * Copies the value of a key to a new key.
   * @see <a href="https://valkey.io/commands/copy/">COPY</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> copy(List<String> args) {
    return send(Command.COPY, args.toArray(new String[0]));
  }

  /**
   * Returns the number of keys in the database.
   * @see <a href="https://valkey.io/commands/dbsize/">DBSIZE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> dbsize() {
    return send(Command.DBSIZE);
  }

  /**
   * A container for debugging commands.
   * @see <a href="https://valkey.io/commands/debug/">DEBUG</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> debug(List<String> args) {
    return send(Command.DEBUG, args.toArray(new String[0]));
  }

  /**
   * Decrements the integer value of a key by one. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/decr/">DECR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> decr(String arg0) {
    return send(Command.DECR, arg0);
  }

  /**
   * Decrements a number from the integer value of a key. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/decrby/">DECRBY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> decrby(String arg0, String arg1) {
    return send(Command.DECRBY, arg0, arg1);
  }

  /**
   * Deletes one or more keys.
   * @see <a href="https://valkey.io/commands/del/">DEL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> del(List<String> args) {
    return send(Command.DEL, args.toArray(new String[0]));
  }

  /**
   * Delete key if value matches string.
   * @see <a href="https://valkey.io/commands/delifeq/">DELIFEQ</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> delifeq(String arg0, String arg1) {
    return send(Command.DELIFEQ, arg0, arg1);
  }

  /**
   * Discards a transaction.
   * @see <a href="https://valkey.io/commands/discard/">DISCARD</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> discard() {
    return send(Command.DISCARD);
  }

  /**
   * Returns a serialized representation of the value stored at a key.
   * @see <a href="https://valkey.io/commands/dump/">DUMP</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> dump(String arg0) {
    return send(Command.DUMP, arg0);
  }

  /**
   * Returns the given string.
   * @see <a href="https://valkey.io/commands/echo/">ECHO</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> echo(String arg0) {
    return send(Command.ECHO, arg0);
  }

  /**
   * Executes a server-side Lua script.
   * @see <a href="https://valkey.io/commands/eval/">EVAL</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> eval(List<String> args) {
    return send(Command.EVAL, args.toArray(new String[0]));
  }

  /**
   * Executes a read-only server-side Lua script.
   * @see <a href="https://valkey.io/commands/eval_ro/">EVAL_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> evalRo(List<String> args) {
    return send(Command.EVAL_RO, args.toArray(new String[0]));
  }

  /**
   * Executes a server-side Lua script by SHA1 digest.
   * @see <a href="https://valkey.io/commands/evalsha/">EVALSHA</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> evalsha(List<String> args) {
    return send(Command.EVALSHA, args.toArray(new String[0]));
  }

  /**
   * Executes a read-only server-side Lua script by SHA1 digest.
   * @see <a href="https://valkey.io/commands/evalsha_ro/">EVALSHA_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> evalshaRo(List<String> args) {
    return send(Command.EVALSHA_RO, args.toArray(new String[0]));
  }

  /**
   * Executes all commands in a transaction.
   * @see <a href="https://valkey.io/commands/exec/">EXEC</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> exec() {
    return send(Command.EXEC);
  }

  /**
   * Determines whether one or more keys exist.
   * @see <a href="https://valkey.io/commands/exists/">EXISTS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> exists(List<String> args) {
    return send(Command.EXISTS, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key in seconds.
   * @see <a href="https://valkey.io/commands/expire/">EXPIRE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> expire(List<String> args) {
    return send(Command.EXPIRE, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key to a Unix timestamp.
   * @see <a href="https://valkey.io/commands/expireat/">EXPIREAT</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> expireat(List<String> args) {
    return send(Command.EXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a key as a Unix timestamp.
   * @see <a href="https://valkey.io/commands/expiretime/">EXPIRETIME</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> expiretime(String arg0) {
    return send(Command.EXPIRETIME, arg0);
  }

  /**
   * Starts a coordinated failover from a server to one of its replicas.
   * @see <a href="https://valkey.io/commands/failover/">FAILOVER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> failover(List<String> args) {
    return send(Command.FAILOVER, args.toArray(new String[0]));
  }

  /**
   * Invokes a function.
   * @see <a href="https://valkey.io/commands/fcall/">FCALL</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> fcall(List<String> args) {
    return send(Command.FCALL, args.toArray(new String[0]));
  }

  /**
   * Invokes a read-only function.
   * @see <a href="https://valkey.io/commands/fcall_ro/">FCALL_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> fcallRo(List<String> args) {
    return send(Command.FCALL_RO, args.toArray(new String[0]));
  }

  /**
   * Removes all keys from all databases.
   * @see <a href="https://valkey.io/commands/flushall/">FLUSHALL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> flushall(List<String> args) {
    return send(Command.FLUSHALL, args.toArray(new String[0]));
  }

  /**
   * Remove all keys from the current database.
   * @see <a href="https://valkey.io/commands/flushdb/">FLUSHDB</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> flushdb(List<String> args) {
    return send(Command.FLUSHDB, args.toArray(new String[0]));
  }

  /**
   * A container for function commands.
   * @see <a href="https://valkey.io/commands/function/">FUNCTION</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> function(List<String> args) {
    return send(Command.FUNCTION, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a geospatial index. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/geoadd/">GEOADD</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geoadd(List<String> args) {
    return send(Command.GEOADD, args.toArray(new String[0]));
  }

  /**
   * Returns the distance between two members of a geospatial index.
   * @see <a href="https://valkey.io/commands/geodist/">GEODIST</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geodist(List<String> args) {
    return send(Command.GEODIST, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index as geohash strings.
   * @see <a href="https://valkey.io/commands/geohash/">GEOHASH</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geohash(List<String> args) {
    return send(Command.GEOHASH, args.toArray(new String[0]));
  }

  /**
   * Returns the longitude and latitude of members from a geospatial index.
   * @see <a href="https://valkey.io/commands/geopos/">GEOPOS</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> geopos(List<String> args) {
    return send(Command.GEOPOS, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members within a distance from a coordinate, optionally stores the result.
   * @see <a href="https://valkey.io/commands/georadius/">GEORADIUS</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> georadius(List<String> args) {
    return send(Command.GEORADIUS, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index that are within a distance from a coordinate.
   * @see <a href="https://valkey.io/commands/georadius_ro/">GEORADIUS_RO</a>
   * @since Redis 3.2.10
   */
  default Future<@Nullable Response> georadiusRo(List<String> args) {
    return send(Command.GEORADIUS_RO, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members within a distance from a member, optionally stores the result.
   * @see <a href="https://valkey.io/commands/georadiusbymember/">GEORADIUSBYMEMBER</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> georadiusbymember(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER, args.toArray(new String[0]));
  }

  /**
   * Returns members from a geospatial index that are within a distance from a member.
   * @see <a href="https://valkey.io/commands/georadiusbymember_ro/">GEORADIUSBYMEMBER_RO</a>
   * @since Redis 3.2.10
   */
  default Future<@Nullable Response> georadiusbymemberRo(List<String> args) {
    return send(Command.GEORADIUSBYMEMBER_RO, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members inside an area of a box, circle, or a polygon.
   * @see <a href="https://valkey.io/commands/geosearch/">GEOSEARCH</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> geosearch(List<String> args) {
    return send(Command.GEOSEARCH, args.toArray(new String[0]));
  }

  /**
   * Queries a geospatial index for members inside an area of a box, a circle, or a polygon, optionally stores the result.
   * @see <a href="https://valkey.io/commands/geosearchstore/">GEOSEARCHSTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> geosearchstore(List<String> args) {
    return send(Command.GEOSEARCHSTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the string value of a key.
   * @see <a href="https://valkey.io/commands/get/">GET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> get(String arg0) {
    return send(Command.GET, arg0);
  }

  /**
   * Returns a bit value by offset.
   * @see <a href="https://valkey.io/commands/getbit/">GETBIT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> getbit(String arg0, String arg1) {
    return send(Command.GETBIT, arg0, arg1);
  }

  /**
   * Returns the string value of a key after deleting the key.
   * @see <a href="https://valkey.io/commands/getdel/">GETDEL</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> getdel(String arg0) {
    return send(Command.GETDEL, arg0);
  }

  /**
   * Returns the string value of a key after setting its expiration time.
   * @see <a href="https://valkey.io/commands/getex/">GETEX</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> getex(List<String> args) {
    return send(Command.GETEX, args.toArray(new String[0]));
  }

  /**
   * Returns a substring of the string stored at a key.
   * @see <a href="https://valkey.io/commands/getrange/">GETRANGE</a>
   * @since Redis 2.4.0
   */
  default Future<@Nullable Response> getrange(String arg0, String arg1, String arg2) {
    return send(Command.GETRANGE, arg0, arg1, arg2);
  }

  /**
   * Returns the previous string value of a key after setting it to a new value.
   * @see <a href="https://valkey.io/commands/getset/">GETSET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> getset(String arg0, String arg1) {
    return send(Command.GETSET, arg0, arg1);
  }

  /**
   * Deletes one or more fields and their values from a hash. Deletes the hash if no fields remain.
   * @see <a href="https://valkey.io/commands/hdel/">HDEL</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hdel(List<String> args) {
    return send(Command.HDEL, args.toArray(new String[0]));
  }

  /**
   * Handshakes with the server.
   * @see <a href="https://valkey.io/commands/hello/">HELLO</a>
   * @since Redis 6.0.0
   */
  default Future<@Nullable Response> hello(List<String> args) {
    return send(Command.HELLO, args.toArray(new String[0]));
  }

  /**
   * Determines whether a field exists in a hash.
   * @see <a href="https://valkey.io/commands/hexists/">HEXISTS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hexists(String arg0, String arg1) {
    return send(Command.HEXISTS, arg0, arg1);
  }

  /**
   * Set expiry time on hash fields.
   * @see <a href="https://valkey.io/commands/hexpire/">HEXPIRE</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hexpire(List<String> args) {
    return send(Command.HEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Set expiry time on hash fields.
   * @see <a href="https://valkey.io/commands/hexpireat/">HEXPIREAT</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hexpireat(List<String> args) {
    return send(Command.HEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns Unix timestamps in seconds since the epoch at which the given key&#x27;s field(s) will expire.
   * @see <a href="https://valkey.io/commands/hexpiretime/">HEXPIRETIME</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hexpiretime(List<String> args) {
    return send(Command.HEXPIRETIME, args.toArray(new String[0]));
  }

  /**
   * Returns the value of a field in a hash.
   * @see <a href="https://valkey.io/commands/hget/">HGET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hget(String arg0, String arg1) {
    return send(Command.HGET, arg0, arg1);
  }

  /**
   * Returns all fields and values in a hash.
   * @see <a href="https://valkey.io/commands/hgetall/">HGETALL</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hgetall(String arg0) {
    return send(Command.HGETALL, arg0);
  }

  /**
   * Get the value of one or more fields of a given hash key, and optionally set their expiration time or time-to-live (TTL).
   * @see <a href="https://valkey.io/commands/hgetex/">HGETEX</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hgetex(List<String> args) {
    return send(Command.HGETEX, args.toArray(new String[0]));
  }

  /**
   * Increments the integer value of a field in a hash by a number. Uses 0 as initial value if the field doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/hincrby/">HINCRBY</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hincrby(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBY, arg0, arg1, arg2);
  }

  /**
   * Increments the floating point value of a field by a number. Uses 0 as initial value if the field doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/hincrbyfloat/">HINCRBYFLOAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> hincrbyfloat(String arg0, String arg1, String arg2) {
    return send(Command.HINCRBYFLOAT, arg0, arg1, arg2);
  }

  /**
   * Returns all fields in a hash.
   * @see <a href="https://valkey.io/commands/hkeys/">HKEYS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hkeys(String arg0) {
    return send(Command.HKEYS, arg0);
  }

  /**
   * Returns the number of fields in a hash.
   * @see <a href="https://valkey.io/commands/hlen/">HLEN</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hlen(String arg0) {
    return send(Command.HLEN, arg0);
  }

  /**
   * Returns the values of all fields in a hash.
   * @see <a href="https://valkey.io/commands/hmget/">HMGET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hmget(List<String> args) {
    return send(Command.HMGET, args.toArray(new String[0]));
  }

  /**
   * Sets the values of multiple fields.
   * @see <a href="https://valkey.io/commands/hmset/">HMSET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hmset(List<String> args) {
    return send(Command.HMSET, args.toArray(new String[0]));
  }

  /**
   * Remove the existing expiration on a hash key&#x27;s field(s).
   * @see <a href="https://valkey.io/commands/hpersist/">HPERSIST</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hpersist(List<String> args) {
    return send(Command.HPERSIST, args.toArray(new String[0]));
  }

  /**
   * Set expiry time on hash object.
   * @see <a href="https://valkey.io/commands/hpexpire/">HPEXPIRE</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hpexpire(List<String> args) {
    return send(Command.HPEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Set expiration time on hash field.
   * @see <a href="https://valkey.io/commands/hpexpireat/">HPEXPIREAT</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hpexpireat(List<String> args) {
    return send(Command.HPEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the Unix timestamp in milliseconds since Unix epoch at which the given key&#x27;s field(s) will expire.
   * @see <a href="https://valkey.io/commands/hpexpiretime/">HPEXPIRETIME</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hpexpiretime(List<String> args) {
    return send(Command.HPEXPIRETIME, args.toArray(new String[0]));
  }

  /**
   * Returns the remaining time to live in milliseconds of a hash key&#x27;s field(s) that have an associated expiration.
   * @see <a href="https://valkey.io/commands/hpttl/">HPTTL</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hpttl(List<String> args) {
    return send(Command.HPTTL, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random fields from a hash.
   * @see <a href="https://valkey.io/commands/hrandfield/">HRANDFIELD</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> hrandfield(List<String> args) {
    return send(Command.HRANDFIELD, args.toArray(new String[0]));
  }

  /**
   * Iterates over fields and values of a hash.
   * @see <a href="https://valkey.io/commands/hscan/">HSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> hscan(List<String> args) {
    return send(Command.HSCAN, args.toArray(new String[0]));
  }

  /**
   * Creates or modifies the value of a field in a hash.
   * @see <a href="https://valkey.io/commands/hset/">HSET</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hset(List<String> args) {
    return send(Command.HSET, args.toArray(new String[0]));
  }

  /**
   * Set the value of one or more fields of a given hash key, and optionally set their expiration time.
   * @see <a href="https://valkey.io/commands/hsetex/">HSETEX</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> hsetex(List<String> args) {
    return send(Command.HSETEX, args.toArray(new String[0]));
  }

  /**
   * Sets the value of a field in a hash only when the field doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/hsetnx/">HSETNX</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hsetnx(String arg0, String arg1, String arg2) {
    return send(Command.HSETNX, arg0, arg1, arg2);
  }

  /**
   * Returns the length of the value of a field.
   * @see <a href="https://valkey.io/commands/hstrlen/">HSTRLEN</a>
   * @since Redis 3.2.0
   */
  default Future<@Nullable Response> hstrlen(String arg0, String arg1) {
    return send(Command.HSTRLEN, arg0, arg1);
  }

  /**
   * Returns the remaining time to live (in seconds) of a hash key&#x27;s field(s) that have an associated expiration.
   * @see <a href="https://valkey.io/commands/httl/">HTTL</a>
   * @since Valkey 9.0.0
   */
  default Future<@Nullable Response> httl(List<String> args) {
    return send(Command.HTTL, args.toArray(new String[0]));
  }

  /**
   * Returns all values in a hash.
   * @see <a href="https://valkey.io/commands/hvals/">HVALS</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> hvals(String arg0) {
    return send(Command.HVALS, arg0);
  }

  /**
   * Increments the integer value of a key by one. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/incr/">INCR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> incr(String arg0) {
    return send(Command.INCR, arg0);
  }

  /**
   * Increments the integer value of a key by a number. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/incrby/">INCRBY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> incrby(String arg0, String arg1) {
    return send(Command.INCRBY, arg0, arg1);
  }

  /**
   * Increment the floating point value of a key by a number. Uses 0 as initial value if the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/incrbyfloat/">INCRBYFLOAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> incrbyfloat(String arg0, String arg1) {
    return send(Command.INCRBYFLOAT, arg0, arg1);
  }

  /**
   * Returns information and statistics about the server.
   * @see <a href="https://valkey.io/commands/info/">INFO</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> info(List<String> args) {
    return send(Command.INFO, args.toArray(new String[0]));
  }

  /**
   * Returns all key names that match a pattern.
   * @see <a href="https://valkey.io/commands/keys/">KEYS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> keys(String arg0) {
    return send(Command.KEYS, arg0);
  }

  /**
   * Returns the Unix timestamp of the last successful save to disk.
   * @see <a href="https://valkey.io/commands/lastsave/">LASTSAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lastsave() {
    return send(Command.LASTSAVE);
  }

  /**
   * A container for latency diagnostics commands.
   * @see <a href="https://valkey.io/commands/latency/">LATENCY</a>
   * @since Redis 2.8.13
   */
  default Future<@Nullable Response> latency(List<String> args) {
    return send(Command.LATENCY, args.toArray(new String[0]));
  }

  /**
   * Finds the longest common substring.
   * @see <a href="https://valkey.io/commands/lcs/">LCS</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> lcs(List<String> args) {
    return send(Command.LCS, args.toArray(new String[0]));
  }

  /**
   * Returns an element from a list by its index.
   * @see <a href="https://valkey.io/commands/lindex/">LINDEX</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lindex(String arg0, String arg1) {
    return send(Command.LINDEX, arg0, arg1);
  }

  /**
   * Inserts an element before or after another element in a list.
   * @see <a href="https://valkey.io/commands/linsert/">LINSERT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> linsert(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LINSERT, arg0, arg1, arg2, arg3);
  }

  /**
   * Returns the length of a list.
   * @see <a href="https://valkey.io/commands/llen/">LLEN</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> llen(String arg0) {
    return send(Command.LLEN, arg0);
  }

  /**
   * Returns an element after popping it from one list and pushing it to another. Deletes the list if the last element was moved.
   * @see <a href="https://valkey.io/commands/lmove/">LMOVE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> lmove(String arg0, String arg1, String arg2, String arg3) {
    return send(Command.LMOVE, arg0, arg1, arg2, arg3);
  }

  /**
   * Returns multiple elements from a list after removing them. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/lmpop/">LMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> lmpop(List<String> args) {
    return send(Command.LMPOP, args.toArray(new String[0]));
  }

  /**
   * Displays computer art and the server version.
   * @see <a href="https://valkey.io/commands/lolwut/">LOLWUT</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> lolwut(List<String> args) {
    return send(Command.LOLWUT, args.toArray(new String[0]));
  }

  /**
   * Returns and removes one or more elements from the beginning of a list. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/lpop/">LPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lpop(List<String> args) {
    return send(Command.LPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the index of matching elements in a list.
   * @see <a href="https://valkey.io/commands/lpos/">LPOS</a>
   * @since Redis 6.0.6
   */
  default Future<@Nullable Response> lpos(List<String> args) {
    return send(Command.LPOS, args.toArray(new String[0]));
  }

  /**
   * Prepends one or more elements to a list. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/lpush/">LPUSH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lpush(List<String> args) {
    return send(Command.LPUSH, args.toArray(new String[0]));
  }

  /**
   * Prepends one or more elements to a list only when the list exists.
   * @see <a href="https://valkey.io/commands/lpushx/">LPUSHX</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> lpushx(List<String> args) {
    return send(Command.LPUSHX, args.toArray(new String[0]));
  }

  /**
   * Returns a range of elements from a list.
   * @see <a href="https://valkey.io/commands/lrange/">LRANGE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lrange(String arg0, String arg1, String arg2) {
    return send(Command.LRANGE, arg0, arg1, arg2);
  }

  /**
   * Removes elements from a list. Deletes the list if the last element was removed.
   * @see <a href="https://valkey.io/commands/lrem/">LREM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lrem(String arg0, String arg1, String arg2) {
    return send(Command.LREM, arg0, arg1, arg2);
  }

  /**
   * Sets the value of an element in a list by its index.
   * @see <a href="https://valkey.io/commands/lset/">LSET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> lset(String arg0, String arg1, String arg2) {
    return send(Command.LSET, arg0, arg1, arg2);
  }

  /**
   * Removes elements from both ends a list. Deletes the list if all elements were trimmed.
   * @see <a href="https://valkey.io/commands/ltrim/">LTRIM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ltrim(String arg0, String arg1, String arg2) {
    return send(Command.LTRIM, arg0, arg1, arg2);
  }

  /**
   * A container for memory diagnostics commands.
   * @see <a href="https://valkey.io/commands/memory/">MEMORY</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> memory(List<String> args) {
    return send(Command.MEMORY, args.toArray(new String[0]));
  }

  /**
   * Atomically returns the string values of one or more keys.
   * @see <a href="https://valkey.io/commands/mget/">MGET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> mget(List<String> args) {
    return send(Command.MGET, args.toArray(new String[0]));
  }

  /**
   * Atomically transfers a key from one instance to another.
   * @see <a href="https://valkey.io/commands/migrate/">MIGRATE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> migrate(List<String> args) {
    return send(Command.MIGRATE, args.toArray(new String[0]));
  }

  /**
   * A container for module commands.
   * @see <a href="https://valkey.io/commands/module/">MODULE</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> module(List<String> args) {
    return send(Command.MODULE, args.toArray(new String[0]));
  }

  /**
   * Listens for all requests received by the server in real-time.
   * @see <a href="https://valkey.io/commands/monitor/">MONITOR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> monitor() {
    return send(Command.MONITOR);
  }

  /**
   * Moves a key to another database.
   * @see <a href="https://valkey.io/commands/move/">MOVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> move(String arg0, String arg1) {
    return send(Command.MOVE, arg0, arg1);
  }

  /**
   * Atomically creates or modifies the string values of one or more keys.
   * @see <a href="https://valkey.io/commands/mset/">MSET</a>
   * @since Redis 1.0.1
   */
  default Future<@Nullable Response> mset(List<String> args) {
    return send(Command.MSET, args.toArray(new String[0]));
  }

  /**
   * Atomically modifies the string values of one or more keys only when all keys don&#x27;t exist.
   * @see <a href="https://valkey.io/commands/msetnx/">MSETNX</a>
   * @since Redis 1.0.1
   */
  default Future<@Nullable Response> msetnx(List<String> args) {
    return send(Command.MSETNX, args.toArray(new String[0]));
  }

  /**
   * Starts a transaction.
   * @see <a href="https://valkey.io/commands/multi/">MULTI</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> multi() {
    return send(Command.MULTI);
  }

  /**
   * A container for object introspection commands.
   * @see <a href="https://valkey.io/commands/object/">OBJECT</a>
   * @since Redis 2.2.3
   */
  default Future<@Nullable Response> object(List<String> args) {
    return send(Command.OBJECT, args.toArray(new String[0]));
  }

  /**
   * Removes the expiration time of a key.
   * @see <a href="https://valkey.io/commands/persist/">PERSIST</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> persist(String arg0) {
    return send(Command.PERSIST, arg0);
  }

  /**
   * Sets the expiration time of a key in milliseconds.
   * @see <a href="https://valkey.io/commands/pexpire/">PEXPIRE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pexpire(List<String> args) {
    return send(Command.PEXPIRE, args.toArray(new String[0]));
  }

  /**
   * Sets the expiration time of a key to a Unix milliseconds timestamp.
   * @see <a href="https://valkey.io/commands/pexpireat/">PEXPIREAT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pexpireat(List<String> args) {
    return send(Command.PEXPIREAT, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time of a key as a Unix milliseconds timestamp.
   * @see <a href="https://valkey.io/commands/pexpiretime/">PEXPIRETIME</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> pexpiretime(String arg0) {
    return send(Command.PEXPIRETIME, arg0);
  }

  /**
   * Adds elements to a HyperLogLog key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/pfadd/">PFADD</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfadd(List<String> args) {
    return send(Command.PFADD, args.toArray(new String[0]));
  }

  /**
   * Returns the approximated cardinality of the set(s) observed by the HyperLogLog key(s).
   * @see <a href="https://valkey.io/commands/pfcount/">PFCOUNT</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfcount(List<String> args) {
    return send(Command.PFCOUNT, args.toArray(new String[0]));
  }

  /**
   * Internal commands for debugging HyperLogLog values.
   * @see <a href="https://valkey.io/commands/pfdebug/">PFDEBUG</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfdebug(String arg0, String arg1) {
    return send(Command.PFDEBUG, arg0, arg1);
  }

  /**
   * Merges one or more HyperLogLog values into a single key.
   * @see <a href="https://valkey.io/commands/pfmerge/">PFMERGE</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfmerge(List<String> args) {
    return send(Command.PFMERGE, args.toArray(new String[0]));
  }

  /**
   * An internal command for testing HyperLogLog values.
   * @see <a href="https://valkey.io/commands/pfselftest/">PFSELFTEST</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> pfselftest() {
    return send(Command.PFSELFTEST);
  }

  /**
   * Returns the server&#x27;s liveliness response.
   * @see <a href="https://valkey.io/commands/ping/">PING</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ping(List<String> args) {
    return send(Command.PING, args.toArray(new String[0]));
  }

  /**
   * Sets both string value and expiration time in milliseconds of a key. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/psetex/">PSETEX</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> psetex(String arg0, String arg1, String arg2) {
    return send(Command.PSETEX, arg0, arg1, arg2);
  }

  /**
   * Listens for messages published to channels that match one or more patterns.
   * @see <a href="https://valkey.io/commands/psubscribe/">PSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> psubscribe(List<String> args) {
    return send(Command.PSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * An internal command used in replication.
   * @see <a href="https://valkey.io/commands/psync/">PSYNC</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> psync(List<String> args) {
    return send(Command.PSYNC, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time in milliseconds of a key.
   * @see <a href="https://valkey.io/commands/pttl/">PTTL</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> pttl(String arg0) {
    return send(Command.PTTL, arg0);
  }

  /**
   * Posts a message to a channel.
   * @see <a href="https://valkey.io/commands/publish/">PUBLISH</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> publish(String arg0, String arg1) {
    return send(Command.PUBLISH, arg0, arg1);
  }

  /**
   * A container for Pub/Sub commands.
   * @see <a href="https://valkey.io/commands/pubsub/">PUBSUB</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> pubsub(List<String> args) {
    return send(Command.PUBSUB, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages published to channels that match one or more patterns.
   * @see <a href="https://valkey.io/commands/punsubscribe/">PUNSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> punsubscribe(List<String> args) {
    return send(Command.PUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Closes the connection.
   * @see <a href="https://valkey.io/commands/quit/">QUIT</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> quit(List<String> args) {
    return send(Command.QUIT, args.toArray(new String[0]));
  }

  /**
   * Returns a random key name from the database.
   * @see <a href="https://valkey.io/commands/randomkey/">RANDOMKEY</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> randomkey() {
    return send(Command.RANDOMKEY);
  }

  /**
   * Enables read-only queries for a connection to a Valkey replica node.
   * @see <a href="https://valkey.io/commands/readonly/">READONLY</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> readonly() {
    return send(Command.READONLY);
  }

  /**
   * Enables read-write queries for a connection to a Valkey replica node.
   * @see <a href="https://valkey.io/commands/readwrite/">READWRITE</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> readwrite() {
    return send(Command.READWRITE);
  }

  /**
   * Renames a key and overwrites the destination.
   * @see <a href="https://valkey.io/commands/rename/">RENAME</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rename(String arg0, String arg1) {
    return send(Command.RENAME, arg0, arg1);
  }

  /**
   * Renames a key only when the target key name doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/renamenx/">RENAMENX</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> renamenx(String arg0, String arg1) {
    return send(Command.RENAMENX, arg0, arg1);
  }

  /**
   * An internal command for configuring the replication stream.
   * @see <a href="https://valkey.io/commands/replconf/">REPLCONF</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> replconf(List<String> args) {
    return send(Command.REPLCONF, args.toArray(new String[0]));
  }

  /**
   * Configures a server as replica of another, or promotes it to a primary.
   * @see <a href="https://valkey.io/commands/replicaof/">REPLICAOF</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> replicaof(String arg0, String arg1) {
    return send(Command.REPLICAOF, arg0, arg1);
  }

  /**
   * Resets the connection.
   * @see <a href="https://valkey.io/commands/reset/">RESET</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> reset() {
    return send(Command.RESET);
  }

  /**
   * Creates a key from the serialized representation of a value.
   * @see <a href="https://valkey.io/commands/restore/">RESTORE</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> restore(List<String> args) {
    return send(Command.RESTORE, args.toArray(new String[0]));
  }

  /**
   * An internal command for migrating keys in a cluster.
   * @see <a href="https://valkey.io/commands/restore-asking/">RESTORE-ASKING</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> restoreAsking(List<String> args) {
    return send(Command.RESTORE_ASKING, args.toArray(new String[0]));
  }

  /**
   * Returns the replication role.
   * @see <a href="https://valkey.io/commands/role/">ROLE</a>
   * @since Redis 2.8.12
   */
  default Future<@Nullable Response> role() {
    return send(Command.ROLE);
  }

  /**
   * Returns and removes one or more elements from the end of a list. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/rpop/">RPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rpop(List<String> args) {
    return send(Command.RPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the last element of a list after removing and pushing it to another list. Deletes the list if the last element was popped.
   * @see <a href="https://valkey.io/commands/rpoplpush/">RPOPLPUSH</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> rpoplpush(String arg0, String arg1) {
    return send(Command.RPOPLPUSH, arg0, arg1);
  }

  /**
   * Appends one or more elements to a list. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/rpush/">RPUSH</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> rpush(List<String> args) {
    return send(Command.RPUSH, args.toArray(new String[0]));
  }

  /**
   * Appends one or more elements to a list only when the list exists.
   * @see <a href="https://valkey.io/commands/rpushx/">RPUSHX</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> rpushx(List<String> args) {
    return send(Command.RPUSHX, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a set. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/sadd/">SADD</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sadd(List<String> args) {
    return send(Command.SADD, args.toArray(new String[0]));
  }

  /**
   * Synchronously saves the database(s) to disk.
   * @see <a href="https://valkey.io/commands/save/">SAVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> save() {
    return send(Command.SAVE);
  }

  /**
   * Iterates over the key names in the database.
   * @see <a href="https://valkey.io/commands/scan/">SCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> scan(List<String> args) {
    return send(Command.SCAN, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a set.
   * @see <a href="https://valkey.io/commands/scard/">SCARD</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> scard(String arg0) {
    return send(Command.SCARD, arg0);
  }

  /**
   * A container for Lua scripts management commands.
   * @see <a href="https://valkey.io/commands/script/">SCRIPT</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> script(List<String> args) {
    return send(Command.SCRIPT, args.toArray(new String[0]));
  }

  /**
   * Returns the difference of multiple sets.
   * @see <a href="https://valkey.io/commands/sdiff/">SDIFF</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sdiff(List<String> args) {
    return send(Command.SDIFF, args.toArray(new String[0]));
  }

  /**
   * Stores the difference of multiple sets in a key.
   * @see <a href="https://valkey.io/commands/sdiffstore/">SDIFFSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sdiffstore(List<String> args) {
    return send(Command.SDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Changes the selected database.
   * @see <a href="https://valkey.io/commands/select/">SELECT</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> select(String arg0) {
    return send(Command.SELECT, arg0);
  }

  /**
   * Sets the string value of a key, ignoring its type. The key is created if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/set/">SET</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> set(List<String> args) {
    return send(Command.SET, args.toArray(new String[0]));
  }

  /**
   * Sets or clears the bit at offset of the string value. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/setbit/">SETBIT</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> setbit(String arg0, String arg1, String arg2) {
    return send(Command.SETBIT, arg0, arg1, arg2);
  }

  /**
   * Sets the string value and expiration time of a key. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/setex/">SETEX</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> setex(String arg0, String arg1, String arg2) {
    return send(Command.SETEX, arg0, arg1, arg2);
  }

  /**
   * Set the string value of a key only when the key doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/setnx/">SETNX</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> setnx(String arg0, String arg1) {
    return send(Command.SETNX, arg0, arg1);
  }

  /**
   * Overwrites a part of a string value with another by an offset. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/setrange/">SETRANGE</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> setrange(String arg0, String arg1, String arg2) {
    return send(Command.SETRANGE, arg0, arg1, arg2);
  }

  /**
   * Synchronously saves the database(s) to disk and shuts down the server.
   * @see <a href="https://valkey.io/commands/shutdown/">SHUTDOWN</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> shutdown(List<String> args) {
    return send(Command.SHUTDOWN, args.toArray(new String[0]));
  }

  /**
   * Returns the intersect of multiple sets.
   * @see <a href="https://valkey.io/commands/sinter/">SINTER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sinter(List<String> args) {
    return send(Command.SINTER, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members of the intersect of multiple sets.
   * @see <a href="https://valkey.io/commands/sintercard/">SINTERCARD</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sintercard(List<String> args) {
    return send(Command.SINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Stores the intersect of multiple sets in a key.
   * @see <a href="https://valkey.io/commands/sinterstore/">SINTERSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sinterstore(List<String> args) {
    return send(Command.SINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Determines whether a member belongs to a set.
   * @see <a href="https://valkey.io/commands/sismember/">SISMEMBER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sismember(String arg0, String arg1) {
    return send(Command.SISMEMBER, arg0, arg1);
  }

  /**
   * Sets a server as a replica of another, or promotes it to being a primary.
   * @see <a href="https://valkey.io/commands/slaveof/">SLAVEOF</a>
   * @since Redis 1.0.0
   * @deprecated since Redis 5.0.0, replaced by: {@code REPLICAOF}
   */
  default Future<@Nullable Response> slaveof(String arg0, String arg1) {
    return send(Command.SLAVEOF, arg0, arg1);
  }

  /**
   * A container for slow log commands.
   * @see <a href="https://valkey.io/commands/slowlog/">SLOWLOG</a>
   * @since Redis 2.2.12
   */
  default Future<@Nullable Response> slowlog(List<String> args) {
    return send(Command.SLOWLOG, args.toArray(new String[0]));
  }

  /**
   * Returns all members of a set.
   * @see <a href="https://valkey.io/commands/smembers/">SMEMBERS</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> smembers(String arg0) {
    return send(Command.SMEMBERS, arg0);
  }

  /**
   * Determines whether multiple members belong to a set.
   * @see <a href="https://valkey.io/commands/smismember/">SMISMEMBER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> smismember(List<String> args) {
    return send(Command.SMISMEMBER, args.toArray(new String[0]));
  }

  /**
   * Moves a member from one set to another.
   * @see <a href="https://valkey.io/commands/smove/">SMOVE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> smove(String arg0, String arg1, String arg2) {
    return send(Command.SMOVE, arg0, arg1, arg2);
  }

  /**
   * Sorts the elements in a list, a set, or a sorted set, optionally storing the result.
   * @see <a href="https://valkey.io/commands/sort/">SORT</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sort(List<String> args) {
    return send(Command.SORT, args.toArray(new String[0]));
  }

  /**
   * Returns the sorted elements of a list, a set, or a sorted set.
   * @see <a href="https://valkey.io/commands/sort_ro/">SORT_RO</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sortRo(List<String> args) {
    return send(Command.SORT_RO, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random members from a set after removing them. Deletes the set if the last member was popped.
   * @see <a href="https://valkey.io/commands/spop/">SPOP</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> spop(List<String> args) {
    return send(Command.SPOP, args.toArray(new String[0]));
  }

  /**
   * Post a message to a shard channel.
   * @see <a href="https://valkey.io/commands/spublish/">SPUBLISH</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> spublish(String arg0, String arg1) {
    return send(Command.SPUBLISH, arg0, arg1);
  }

  /**
   * Get one or multiple random members from a set.
   * @see <a href="https://valkey.io/commands/srandmember/">SRANDMEMBER</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> srandmember(List<String> args) {
    return send(Command.SRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Removes one or more members from a set. Deletes the set if the last member was removed.
   * @see <a href="https://valkey.io/commands/srem/">SREM</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> srem(List<String> args) {
    return send(Command.SREM, args.toArray(new String[0]));
  }

  /**
   * Iterates over members of a set.
   * @see <a href="https://valkey.io/commands/sscan/">SSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> sscan(List<String> args) {
    return send(Command.SSCAN, args.toArray(new String[0]));
  }

  /**
   * Listens for messages published to shard channels.
   * @see <a href="https://valkey.io/commands/ssubscribe/">SSUBSCRIBE</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> ssubscribe(List<String> args) {
    return send(Command.SSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Returns the length of a string value.
   * @see <a href="https://valkey.io/commands/strlen/">STRLEN</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> strlen(String arg0) {
    return send(Command.STRLEN, arg0);
  }

  /**
   * Listens for messages published to channels.
   * @see <a href="https://valkey.io/commands/subscribe/">SUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> subscribe(List<String> args) {
    return send(Command.SUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Returns a substring from a string value.
   * @see <a href="https://valkey.io/commands/substr/">SUBSTR</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> substr(String arg0, String arg1, String arg2) {
    return send(Command.SUBSTR, arg0, arg1, arg2);
  }

  /**
   * Returns the union of multiple sets.
   * @see <a href="https://valkey.io/commands/sunion/">SUNION</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sunion(List<String> args) {
    return send(Command.SUNION, args.toArray(new String[0]));
  }

  /**
   * Stores the union of multiple sets in a key.
   * @see <a href="https://valkey.io/commands/sunionstore/">SUNIONSTORE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sunionstore(List<String> args) {
    return send(Command.SUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages posted to shard channels.
   * @see <a href="https://valkey.io/commands/sunsubscribe/">SUNSUBSCRIBE</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> sunsubscribe(List<String> args) {
    return send(Command.SUNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Swaps two databases.
   * @see <a href="https://valkey.io/commands/swapdb/">SWAPDB</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> swapdb(String arg0, String arg1) {
    return send(Command.SWAPDB, arg0, arg1);
  }

  /**
   * An internal command used in replication.
   * @see <a href="https://valkey.io/commands/sync/">SYNC</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> sync() {
    return send(Command.SYNC);
  }

  /**
   * Returns the server time.
   * @see <a href="https://valkey.io/commands/time/">TIME</a>
   * @since Redis 2.6.0
   */
  default Future<@Nullable Response> time() {
    return send(Command.TIME);
  }

  /**
   * Returns the number of existing keys out of those specified after updating the time they were last accessed.
   * @see <a href="https://valkey.io/commands/touch/">TOUCH</a>
   * @since Redis 3.2.1
   */
  default Future<@Nullable Response> touch(List<String> args) {
    return send(Command.TOUCH, args.toArray(new String[0]));
  }

  /**
   * Returns the expiration time in seconds of a key.
   * @see <a href="https://valkey.io/commands/ttl/">TTL</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> ttl(String arg0) {
    return send(Command.TTL, arg0);
  }

  /**
   * Determines the type of value stored at a key.
   * @see <a href="https://valkey.io/commands/type/">TYPE</a>
   * @since Redis 1.0.0
   */
  default Future<@Nullable Response> type(String arg0) {
    return send(Command.TYPE, arg0);
  }

  /**
   * Asynchronously deletes one or more keys.
   * @see <a href="https://valkey.io/commands/unlink/">UNLINK</a>
   * @since Redis 4.0.0
   */
  default Future<@Nullable Response> unlink(List<String> args) {
    return send(Command.UNLINK, args.toArray(new String[0]));
  }

  /**
   * Stops listening to messages posted to channels.
   * @see <a href="https://valkey.io/commands/unsubscribe/">UNSUBSCRIBE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> unsubscribe(List<String> args) {
    return send(Command.UNSUBSCRIBE, args.toArray(new String[0]));
  }

  /**
   * Forgets about watched keys of a transaction.
   * @see <a href="https://valkey.io/commands/unwatch/">UNWATCH</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> unwatch() {
    return send(Command.UNWATCH);
  }

  /**
   * Blocks until the asynchronous replication of all preceding write commands sent by the connection is completed.
   * @see <a href="https://valkey.io/commands/wait/">WAIT</a>
   * @since Redis 3.0.0
   */
  default Future<@Nullable Response> wait(String arg0, String arg1) {
    return send(Command.WAIT, arg0, arg1);
  }

  /**
   * Blocks until all of the preceding write commands sent by the connection are written to the append-only file of the primary and/or replicas.
   * @see <a href="https://valkey.io/commands/waitaof/">WAITAOF</a>
   * @since Redis 7.2.0
   */
  default Future<@Nullable Response> waitaof(String arg0, String arg1, String arg2) {
    return send(Command.WAITAOF, arg0, arg1, arg2);
  }

  /**
   * Monitors changes to keys to determine the execution of a transaction.
   * @see <a href="https://valkey.io/commands/watch/">WATCH</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> watch(List<String> args) {
    return send(Command.WATCH, args.toArray(new String[0]));
  }

  /**
   * Returns the number of messages that were successfully acknowledged by the consumer group member of a stream.
   * @see <a href="https://valkey.io/commands/xack/">XACK</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xack(List<String> args) {
    return send(Command.XACK, args.toArray(new String[0]));
  }

  /**
   * Appends a new message to a stream. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/xadd/">XADD</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xadd(List<String> args) {
    return send(Command.XADD, args.toArray(new String[0]));
  }

  /**
   * Changes, or acquires, ownership of messages in a consumer group, as if the messages were delivered to as consumer group member.
   * @see <a href="https://valkey.io/commands/xautoclaim/">XAUTOCLAIM</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> xautoclaim(List<String> args) {
    return send(Command.XAUTOCLAIM, args.toArray(new String[0]));
  }

  /**
   * Changes, or acquires, ownership of a message in a consumer group, as if the message was delivered a consumer group member.
   * @see <a href="https://valkey.io/commands/xclaim/">XCLAIM</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xclaim(List<String> args) {
    return send(Command.XCLAIM, args.toArray(new String[0]));
  }

  /**
   * Returns the number of messages after removing them from a stream.
   * @see <a href="https://valkey.io/commands/xdel/">XDEL</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xdel(List<String> args) {
    return send(Command.XDEL, args.toArray(new String[0]));
  }

  /**
   * A container for consumer groups commands.
   * @see <a href="https://valkey.io/commands/xgroup/">XGROUP</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xgroup(List<String> args) {
    return send(Command.XGROUP, args.toArray(new String[0]));
  }

  /**
   * A container for stream introspection commands.
   * @see <a href="https://valkey.io/commands/xinfo/">XINFO</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xinfo(List<String> args) {
    return send(Command.XINFO, args.toArray(new String[0]));
  }

  /**
   * Return the number of messages in a stream.
   * @see <a href="https://valkey.io/commands/xlen/">XLEN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xlen(String arg0) {
    return send(Command.XLEN, arg0);
  }

  /**
   * Returns the information and entries from a stream consumer group&#x27;s pending entries list.
   * @see <a href="https://valkey.io/commands/xpending/">XPENDING</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xpending(List<String> args) {
    return send(Command.XPENDING, args.toArray(new String[0]));
  }

  /**
   * Returns the messages from a stream within a range of IDs.
   * @see <a href="https://valkey.io/commands/xrange/">XRANGE</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xrange(List<String> args) {
    return send(Command.XRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns messages from multiple streams with IDs greater than the ones requested. Blocks until a message is available otherwise.
   * @see <a href="https://valkey.io/commands/xread/">XREAD</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xread(List<String> args) {
    return send(Command.XREAD, args.toArray(new String[0]));
  }

  /**
   * Returns new or historical messages from a stream for a consumer in a group. Blocks until a message is available otherwise.
   * @see <a href="https://valkey.io/commands/xreadgroup/">XREADGROUP</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xreadgroup(List<String> args) {
    return send(Command.XREADGROUP, args.toArray(new String[0]));
  }

  /**
   * Returns the messages from a stream within a range of IDs in reverse order.
   * @see <a href="https://valkey.io/commands/xrevrange/">XREVRANGE</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xrevrange(List<String> args) {
    return send(Command.XREVRANGE, args.toArray(new String[0]));
  }

  /**
   * An internal command for replicating stream values.
   * @see <a href="https://valkey.io/commands/xsetid/">XSETID</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xsetid(List<String> args) {
    return send(Command.XSETID, args.toArray(new String[0]));
  }

  /**
   * Deletes messages from the beginning of a stream.
   * @see <a href="https://valkey.io/commands/xtrim/">XTRIM</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> xtrim(List<String> args) {
    return send(Command.XTRIM, args.toArray(new String[0]));
  }

  /**
   * Adds one or more members to a sorted set, or updates their scores. Creates the key if it doesn&#x27;t exist.
   * @see <a href="https://valkey.io/commands/zadd/">ZADD</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zadd(List<String> args) {
    return send(Command.ZADD, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a sorted set.
   * @see <a href="https://valkey.io/commands/zcard/">ZCARD</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zcard(String arg0) {
    return send(Command.ZCARD, arg0);
  }

  /**
   * Returns the count of members in a sorted set that have scores within a range.
   * @see <a href="https://valkey.io/commands/zcount/">ZCOUNT</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zcount(String arg0, String arg1, String arg2) {
    return send(Command.ZCOUNT, arg0, arg1, arg2);
  }

  /**
   * Returns the difference between multiple sorted sets.
   * @see <a href="https://valkey.io/commands/zdiff/">ZDIFF</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zdiff(List<String> args) {
    return send(Command.ZDIFF, args.toArray(new String[0]));
  }

  /**
   * Stores the difference of multiple sorted sets in a key.
   * @see <a href="https://valkey.io/commands/zdiffstore/">ZDIFFSTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zdiffstore(List<String> args) {
    return send(Command.ZDIFFSTORE, args.toArray(new String[0]));
  }

  /**
   * Increments the score of a member in a sorted set.
   * @see <a href="https://valkey.io/commands/zincrby/">ZINCRBY</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zincrby(String arg0, String arg1, String arg2) {
    return send(Command.ZINCRBY, arg0, arg1, arg2);
  }

  /**
   * Returns the intersect of multiple sorted sets.
   * @see <a href="https://valkey.io/commands/zinter/">ZINTER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zinter(List<String> args) {
    return send(Command.ZINTER, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members of the intersect of multiple sorted sets.
   * @see <a href="https://valkey.io/commands/zintercard/">ZINTERCARD</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> zintercard(List<String> args) {
    return send(Command.ZINTERCARD, args.toArray(new String[0]));
  }

  /**
   * Stores the intersect of multiple sorted sets in a key.
   * @see <a href="https://valkey.io/commands/zinterstore/">ZINTERSTORE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zinterstore(List<String> args) {
    return send(Command.ZINTERSTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the number of members in a sorted set within a lexicographical range.
   * @see <a href="https://valkey.io/commands/zlexcount/">ZLEXCOUNT</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zlexcount(String arg0, String arg1, String arg2) {
    return send(Command.ZLEXCOUNT, arg0, arg1, arg2);
  }

  /**
   * Returns the highest- or lowest-scoring members from one or more sorted sets after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://valkey.io/commands/zmpop/">ZMPOP</a>
   * @since Redis 7.0.0
   */
  default Future<@Nullable Response> zmpop(List<String> args) {
    return send(Command.ZMPOP, args.toArray(new String[0]));
  }

  /**
   * Returns the score of one or more members in a sorted set.
   * @see <a href="https://valkey.io/commands/zmscore/">ZMSCORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zmscore(List<String> args) {
    return send(Command.ZMSCORE, args.toArray(new String[0]));
  }

  /**
   * Returns the highest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://valkey.io/commands/zpopmax/">ZPOPMAX</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> zpopmax(List<String> args) {
    return send(Command.ZPOPMAX, args.toArray(new String[0]));
  }

  /**
   * Returns the lowest-scoring members from a sorted set after removing them. Deletes the sorted set if the last member was popped.
   * @see <a href="https://valkey.io/commands/zpopmin/">ZPOPMIN</a>
   * @since Redis 5.0.0
   */
  default Future<@Nullable Response> zpopmin(List<String> args) {
    return send(Command.ZPOPMIN, args.toArray(new String[0]));
  }

  /**
   * Returns one or more random members from a sorted set.
   * @see <a href="https://valkey.io/commands/zrandmember/">ZRANDMEMBER</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zrandmember(List<String> args) {
    return send(Command.ZRANDMEMBER, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of indexes.
   * @see <a href="https://valkey.io/commands/zrange/">ZRANGE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zrange(List<String> args) {
    return send(Command.ZRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a lexicographical range.
   * @see <a href="https://valkey.io/commands/zrangebylex/">ZRANGEBYLEX</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zrangebylex(List<String> args) {
    return send(Command.ZRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of scores.
   * @see <a href="https://valkey.io/commands/zrangebyscore/">ZRANGEBYSCORE</a>
   * @since Redis 1.0.5
   */
  default Future<@Nullable Response> zrangebyscore(List<String> args) {
    return send(Command.ZRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Stores a range of members from sorted set in a key.
   * @see <a href="https://valkey.io/commands/zrangestore/">ZRANGESTORE</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zrangestore(List<String> args) {
    return send(Command.ZRANGESTORE, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by ascending scores.
   * @see <a href="https://valkey.io/commands/zrank/">ZRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zrank(List<String> args) {
    return send(Command.ZRANK, args.toArray(new String[0]));
  }

  /**
   * Removes one or more members from a sorted set. Deletes the sorted set if all members were removed.
   * @see <a href="https://valkey.io/commands/zrem/">ZREM</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zrem(List<String> args) {
    return send(Command.ZREM, args.toArray(new String[0]));
  }

  /**
   * Removes members in a sorted set within a lexicographical range. Deletes the sorted set if all members were removed.
   * @see <a href="https://valkey.io/commands/zremrangebylex/">ZREMRANGEBYLEX</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zremrangebylex(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYLEX, arg0, arg1, arg2);
  }

  /**
   * Removes members in a sorted set within a range of indexes. Deletes the sorted set if all members were removed.
   * @see <a href="https://valkey.io/commands/zremrangebyrank/">ZREMRANGEBYRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zremrangebyrank(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYRANK, arg0, arg1, arg2);
  }

  /**
   * Removes members in a sorted set within a range of scores. Deletes the sorted set if all members were removed.
   * @see <a href="https://valkey.io/commands/zremrangebyscore/">ZREMRANGEBYSCORE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zremrangebyscore(String arg0, String arg1, String arg2) {
    return send(Command.ZREMRANGEBYSCORE, arg0, arg1, arg2);
  }

  /**
   * Returns members in a sorted set within a range of indexes in reverse order.
   * @see <a href="https://valkey.io/commands/zrevrange/">ZREVRANGE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zrevrange(List<String> args) {
    return send(Command.ZREVRANGE, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a lexicographical range in reverse order.
   * @see <a href="https://valkey.io/commands/zrevrangebylex/">ZREVRANGEBYLEX</a>
   * @since Redis 2.8.9
   */
  default Future<@Nullable Response> zrevrangebylex(List<String> args) {
    return send(Command.ZREVRANGEBYLEX, args.toArray(new String[0]));
  }

  /**
   * Returns members in a sorted set within a range of scores in reverse order.
   * @see <a href="https://valkey.io/commands/zrevrangebyscore/">ZREVRANGEBYSCORE</a>
   * @since Redis 2.2.0
   */
  default Future<@Nullable Response> zrevrangebyscore(List<String> args) {
    return send(Command.ZREVRANGEBYSCORE, args.toArray(new String[0]));
  }

  /**
   * Returns the index of a member in a sorted set ordered by descending scores.
   * @see <a href="https://valkey.io/commands/zrevrank/">ZREVRANK</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zrevrank(List<String> args) {
    return send(Command.ZREVRANK, args.toArray(new String[0]));
  }

  /**
   * Iterates over members and scores of a sorted set.
   * @see <a href="https://valkey.io/commands/zscan/">ZSCAN</a>
   * @since Redis 2.8.0
   */
  default Future<@Nullable Response> zscan(List<String> args) {
    return send(Command.ZSCAN, args.toArray(new String[0]));
  }

  /**
   * Returns the score of a member in a sorted set.
   * @see <a href="https://valkey.io/commands/zscore/">ZSCORE</a>
   * @since Redis 1.2.0
   */
  default Future<@Nullable Response> zscore(String arg0, String arg1) {
    return send(Command.ZSCORE, arg0, arg1);
  }

  /**
   * Returns the union of multiple sorted sets.
   * @see <a href="https://valkey.io/commands/zunion/">ZUNION</a>
   * @since Redis 6.2.0
   */
  default Future<@Nullable Response> zunion(List<String> args) {
    return send(Command.ZUNION, args.toArray(new String[0]));
  }

  /**
   * Stores the union of multiple sorted sets in a key.
   * @see <a href="https://valkey.io/commands/zunionstore/">ZUNIONSTORE</a>
   * @since Redis 2.0.0
   */
  default Future<@Nullable Response> zunionstore(List<String> args) {
    return send(Command.ZUNIONSTORE, args.toArray(new String[0]));
  }

  /**
   * Send untyped command to Valkey.
   *
   * @param cmd the command
   * @param args var args
   * @return Future response.
   */
  @GenIgnore
  Future<@Nullable Response> send(Command cmd, String... args);
}
