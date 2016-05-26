/**
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.redis.RedisTransaction;
import io.vertx.redis.op.*;

import java.util.*;
import java.util.stream.Stream;

import static io.vertx.redis.impl.RedisCommand.*;

public final class RedisClientImpl extends AbstractRedisClient {

  RedisTransaction transaction;

  public class RedisTransactionImpl implements RedisTransaction{

    @Override
    public void close(Handler<AsyncResult<Void>> handler) {
      RedisClientImpl.this.close(handler);
    }

    @Override
    public RedisTransaction append(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(APPEND, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction auth(String password, Handler<AsyncResult<String>> handler) {
      sendString(AUTH, toPayload(password), handler);
      return this;
    }

    @Override
    public RedisTransaction bgrewriteaof(Handler<AsyncResult<String>> handler) {
      sendString(BGREWRITEAOF, null, handler);
      return this;
    }

    @Override
    public RedisTransaction bgsave(Handler<AsyncResult<String>> handler) {
      sendString(BGSAVE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction bitcount(String key, Handler<AsyncResult<String>> handler) {
      sendString(BITCOUNT, toPayload(key), handler);
      return this;
    }

    public RedisTransaction bitcountRange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
      sendString(BITCOUNT, toPayload(key, start, end), handler);
      return this;
    }

    @Override
    public RedisTransaction bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(BITOP, toPayload(operation.name(), destkey, keys), handler);
      return this;
    }

    @Override
    public RedisTransaction bitpos(String key, int bit, Handler<AsyncResult<String>> handler) {
      sendString(BITPOS, toPayload(key, bit), handler);
      return this;
    }

    @Override
    public RedisTransaction bitposFrom(String key, int bit, int start, Handler<AsyncResult<String>> handler) {
      sendString(BITPOS, toPayload(key, bit, start), handler);
      return this;
    }

    @Override
    public RedisTransaction bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<String>> handler) {
      sendString(BITPOS, toPayload(key, bit, start, stop), handler);
      return this;
    }

    @Override
    public RedisTransaction blpop(String key, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(BLPOP, toPayload(key, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction blpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(BLPOP, toPayload(keys, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction brpop(String key, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(BRPOP, toPayload(key, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction brpopMany(List<String> keys, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(BRPOP, toPayload(keys, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(BRPOPLPUSH, toPayload(key, destkey, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction clientKill(KillFilter filter, Handler<AsyncResult<String>> handler) {
      sendString(CLIENT_KILL, filter.toJsonArray().getList(), handler);
      return this;
    }

    @Override
    public RedisTransaction clientList(Handler<AsyncResult<String>> handler) {
      sendString(CLIENT_LIST, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clientGetname(Handler<AsyncResult<String>> handler) {
      sendString(CLIENT_GETNAME, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clientPause(long millis, Handler<AsyncResult<String>> handler) {
      sendString(CLIENT_PAUSE, toPayload(millis), handler);
      return this;
    }

    @Override
    public RedisTransaction clientSetname(String name, Handler<AsyncResult<String>> handler) {
      sendString(CLIENT_SETNAME, toPayload(name), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterAddslots(List<String> slots, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_ADDSLOTS, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterCountFailureReports(String nodeId, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_COUNT_FAILURE_REPORTS, toPayload(nodeId), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterCountkeysinslot(long slot, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_COUNTKEYSINSLOT, toPayload(slot), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterDelslots(long slot, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_DELSLOTS, toPayload(slot), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterDelslotsMany(List<String> slots, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_DELSLOTS, toPayload(slots), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterFailover(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_FAILOVER, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_FAILOVER, toPayload(options), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterForget(String nodeId, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_FORGET, toPayload(nodeId), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_GETKEYSINSLOT, toPayload(slot, count), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterInfo(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_INFO, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterKeyslot(String key, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_KEYSLOT, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterMeet(String ip, long port, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_MEET, toPayload(ip, port), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterNodes(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_NODES, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterReplicate(String nodeId, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_REPLICATE, toPayload(nodeId), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterReset(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_RESET, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_RESET, toPayload(options), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSaveconfig(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SAVECONFIG, null, handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSetConfigEpoch(long epoch, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SET_CONFIG_EPOCH, toPayload(epoch), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SETSLOT, toPayload(slot, subcommand), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SETSLOT, toPayload(slot, subcommand, nodeId), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSlaves(String nodeId, Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SLAVES, toPayload(nodeId), handler);
      return this;
    }

    @Override
    public RedisTransaction clusterSlots(Handler<AsyncResult<String>> handler) {
      sendString(CLUSTER_SLOTS, null, handler);
      return this;
    }

    @Override
    public RedisTransaction command(Handler<AsyncResult<String>> handler) {
      sendString(COMMAND, null, handler);
      return this;
    }

    @Override
    public RedisTransaction commandCount(Handler<AsyncResult<String>> handler) {
      sendString(COMMAND_COUNT, null, handler);
      return this;
    }

    @Override
    public RedisTransaction commandGetkeys(Handler<AsyncResult<String>> handler) {
      sendString(COMMAND_GETKEYS, null, handler);
      return this;
    }

    @Override
    public RedisTransaction commandInfo(List<String> commands, Handler<AsyncResult<String>> handler) {
      sendString(COMMAND_INFO, toPayload(commands), handler);
      return this;
    }

    @Override
    public RedisTransaction configGet(String parameter, Handler<AsyncResult<String>> handler) {
      sendString(CONFIG_GET, toPayload(parameter), handler);
      return this;
    }

    @Override
    public RedisTransaction configRewrite(Handler<AsyncResult<String>> handler) {
      sendString(CONFIG_REWRITE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
      sendString(CONFIG_SET, toPayload(parameter, value), handler);
      return this;
    }

    @Override
    public RedisTransaction configResetstat(Handler<AsyncResult<String>> handler) {
      sendString(CONFIG_RESETSTAT, null, handler);
      return this;
    }

    @Override
    public RedisTransaction dbsize(Handler<AsyncResult<String>> handler) {
      sendString(DBSIZE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction debugObject(String key, Handler<AsyncResult<String>> handler) {
      sendString(DEBUG_OBJECT, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction debugSegfault(Handler<AsyncResult<String>> handler) {
      sendString(DEBUG_SEGFAULT, null, handler);
      return this;
    }

    @Override
    public RedisTransaction decr(String key, Handler<AsyncResult<String>> handler) {
      sendString(DECR, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction decrby(String key, long decrement, Handler<AsyncResult<String>> handler) {
      sendString(DECRBY, toPayload(key, decrement), handler);
      return this;
    }

    @Override
    public RedisTransaction del(String key, Handler<AsyncResult<String>> handler) {
      sendString(DEL, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction delMany(List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(DEL, toPayload(keys), handler);
      return this;
    }

    @Override
    public RedisTransaction discard(Handler<AsyncResult<String>> handler) {
      sendString(DISCARD, null, handler);
      return this;
    }

    @Override
    public RedisTransaction dump(String key, Handler<AsyncResult<String>> handler) {
      send(DUMP, toPayload(key), String.class, true, dump -> {
        if (dump.failed()) {
          handler.handle(dump);
        } else {
          handler.handle(Future.succeededFuture(RedisEncoding.encode(dump.result())));
        }
      });
      return this;
    }

    @Override
    public RedisTransaction echo(String message, Handler<AsyncResult<String>> handler) {
      sendString(ECHO, toPayload(message), handler);
      return this;
    }

    @Override
    public RedisTransaction eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<String>> handler) {
      keys = (keys != null) ? keys : Collections.emptyList();
      args = (args != null) ? args : Collections.emptyList();
      sendString(EVAL, toPayload(script, keys.size(), keys, args), handler);
      return this;
    }

    @Override
    public RedisTransaction evalsha(String sha1, List<String> keys, List<String> args, Handler<AsyncResult<String>> handler) {
      keys = (keys != null) ? keys : Collections.emptyList();
      args = (args != null) ? args : Collections.emptyList();
      sendString(EVALSHA, toPayload(sha1, keys.size(), keys, args), handler);
      return this;
    }

    @Override
    public RedisTransaction exec(Handler<AsyncResult<JsonArray>> handler) {
      sendJsonArray(EXEC, null, handler);
      return this;
    }

    @Override
    public RedisTransaction exists(String key, Handler<AsyncResult<String>> handler) {
      sendString(EXISTS, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction expire(String key, int seconds, Handler<AsyncResult<String>> handler) {
      sendString(EXPIRE, toPayload(key, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction expireat(String key, long seconds, Handler<AsyncResult<String>> handler) {
      sendString(EXPIREAT, toPayload(key, seconds), handler);
      return this;
    }

    @Override
    public RedisTransaction flushall(Handler<AsyncResult<String>> handler) {
      sendString(FLUSHALL, null, handler);
      return this;
    }

    @Override
    public RedisTransaction flushdb(Handler<AsyncResult<String>> handler) {
      sendString(FLUSHDB, null, handler);
      return this;
    }

    @Override
    public RedisTransaction get(String key, Handler<AsyncResult<String>> handler) {
      sendString(GET, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction getBinary(String key, Handler<AsyncResult<String>> handler) {
      send(GET, toPayload(key), String.class, true, handler);
      return this;
    }

    @Override
    public RedisTransaction getbit(String key, long offset, Handler<AsyncResult<String>> handler) {
      sendString(GETBIT, toPayload(key, offset), handler);
      return this;
    }

    @Override
    public RedisTransaction getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
      sendString(GETRANGE, toPayload(key, start, end), handler);
      return this;
    }

    @Override
    public RedisTransaction getset(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(GETSET, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction hdel(String key, String field, Handler<AsyncResult<String>> handler) {
      sendString(HDEL, toPayload(key, field), handler);
      return this;
    }

    @Override
    public RedisTransaction hdelMany(String key, List<String> fields, Handler<AsyncResult<String>> handler) {
      sendString(HDEL, toPayload(key, fields), handler);
      return this;
    }

    @Override
    public RedisTransaction hexists(String key, String field, Handler<AsyncResult<String>> handler) {
      sendString(HEXISTS, toPayload(key, field), handler);
      return this;
    }

    @Override
    public RedisTransaction hget(String key, String field, Handler<AsyncResult<String>> handler) {
      sendString(HGET, toPayload(key, field), handler);
      return this;
    }

    @Override
    public RedisTransaction hgetall(String key, Handler<AsyncResult<String>> handler) {
      sendString(HGETALL, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction hincrby(String key, String field, long increment, Handler<AsyncResult<String>> handler) {
      sendString(HINCRBY, toPayload(key, field, increment), handler);
      return this;
    }

    @Override
    public RedisTransaction hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
      sendString(HINCRBYFLOAT, toPayload(key, field, increment), handler);
      return this;
    }

    @Override
    public RedisTransaction hkeys(String key, Handler<AsyncResult<String>> handler) {
      sendString(HKEYS, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction hlen(String key, Handler<AsyncResult<String>> handler) {
      sendString(HLEN, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction hmget(String key, List<String> fields, Handler<AsyncResult<String>> handler) {
      sendString(HMGET, toPayload(key, fields), handler);
      return this;
    }

    @Override
    public RedisTransaction hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler) {
      sendString(HMSET, toPayload(key, values), handler);
      return this;
    }

    @Override
    public RedisTransaction hset(String key, String field, String value, Handler<AsyncResult<String>> handler) {
      sendString(HSET, toPayload(key, field, value), handler);
      return this;
    }

    @Override
    public RedisTransaction hsetnx(String key, String field, String value, Handler<AsyncResult<String>> handler) {
      sendString(HSETNX, toPayload(key, field, value), handler);
      return this;
    }

    @Override
    public RedisTransaction hvals(String key, Handler<AsyncResult<String>> handler) {
      sendString(HVALS, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction incr(String key, Handler<AsyncResult<String>> handler) {
      sendString(INCR, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction incrby(String key, long increment, Handler<AsyncResult<String>> handler) {
      sendString(INCRBY, toPayload(key, increment), handler);
      return this;
    }

    @Override
    public RedisTransaction incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
      sendString(INCRBYFLOAT, toPayload(key, increment), handler);
      return this;
    }

    @Override
    public RedisTransaction info(Handler<AsyncResult<String>> handler) {
      sendString(INFO, Collections.emptyList(), handler);
      return this;
    }

    @Override
    public RedisTransaction infoSection(String section, Handler<AsyncResult<String>> handler) {
      sendString(INFO, toPayload(section), handler);
      return this;
    }

    @Override
    public RedisTransaction keys(String pattern, Handler<AsyncResult<String>> handler) {
      sendString(KEYS, toPayload(pattern), handler);
      return this;
    }

    @Override
    public RedisTransaction lastsave(Handler<AsyncResult<String>> handler) {
      sendString(LASTSAVE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction lindex(String key, int index, Handler<AsyncResult<String>> handler) {
      sendString(LINDEX, toPayload(key, index), handler);
      return this;
    }

    @Override
    public RedisTransaction linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<String>> handler) {
      sendString(LINSERT, toPayload(key, option.name(), pivot, value), handler);
      return this;
    }

    @Override
    public RedisTransaction llen(String key, Handler<AsyncResult<String>> handler) {
      sendString(LLEN, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction lpop(String key, Handler<AsyncResult<String>> handler) {
      sendString(LPOP, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction lpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) {
      sendString(LPUSH, toPayload(key, values), handler);
      return this;
    }

    @Override
    public RedisTransaction lpush(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(LPUSH, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction lpushx(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(LPUSHX, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction lrange(String key, long from, long to, Handler<AsyncResult<String>> handler) {
      sendString(LRANGE, toPayload(key, from, to), handler);
      return this;
    }

    @Override
    public RedisTransaction lrem(String key, long count, String value, Handler<AsyncResult<String>> handler) {
      sendString(LREM, toPayload(key, count, value), handler);
      return this;
    }

    @Override
    public RedisTransaction lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
      sendString(LSET, toPayload(key, index, value), handler);
      return this;
    }

    @Override
    public RedisTransaction ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
      sendString(LTRIM, toPayload(key, from, to), handler);
      return this;
    }

    @Override
    public RedisTransaction mget(String key, Handler<AsyncResult<String>> handler) {
      sendString(MGET, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction mgetMany(List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(MGET, toPayload(keys), handler);
      return this;
    }

    @Override
    public RedisTransaction migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) {
      sendString(MIGRATE, toPayload(host, port, key, destdb, timeout, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction monitor(Handler<AsyncResult<String>> handler) {
      sendString(MONITOR, null, handler);
      return this;
    }

    @Override
    public RedisTransaction move(String key, int destdb, Handler<AsyncResult<String>> handler) {
      sendString(MOVE, toPayload(key, destdb), handler);
      return this;
    }

    @Override
    public RedisTransaction mset(JsonObject keyvals, Handler<AsyncResult<String>> handler) {
      sendString(MSET, toPayload(keyvals), handler);
      return this;
    }

    @Override
    public RedisTransaction msetnx(JsonObject keyvals, Handler<AsyncResult<String>> handler) {
      sendString(MSETNX, toPayload(keyvals), handler);
      return this;
    }

    @Override
    public RedisTransaction multi(Handler<AsyncResult<String>> handler) {
      sendString(MULTI, null, handler);
      return this;
    }

    @Override
    public RedisTransaction object(String key, ObjectCmd cmd, Handler<AsyncResult<String>> handler) {
      sendString(OBJECT, toPayload(cmd.name(), key), handler);
      return this;
    }

    @Override
    public RedisTransaction persist(String key, Handler<AsyncResult<String>> handler) {
      sendString(PERSIST, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction pexpire(String key, long millis, Handler<AsyncResult<String>> handler) {
      sendString(PEXPIRE, toPayload(key, millis), handler);
      return this;
    }

    @Override
    public RedisTransaction pexpireat(String key, long millis, Handler<AsyncResult<String>> handler) {
      sendString(PEXPIREAT, toPayload(key, millis), handler);
      return this;
    }

    @Override
    public RedisTransaction pfadd(String key, String element, Handler<AsyncResult<String>> handler) {
      sendString(PFADD, toPayload(key, element), handler);
      return this;
    }

    @Override
    public RedisTransaction pfaddMany(String key, List<String> elements, Handler<AsyncResult<String>> handler) {
      sendString(PFADD, toPayload(key, elements), handler);
      return this;
    }

    @Override
    public RedisTransaction pfcount(String key, Handler<AsyncResult<String>> handler) {
      sendString(PFCOUNT, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction pfcountMany(List<String> key, Handler<AsyncResult<String>> handler) {
      sendString(PFCOUNT, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(PFMERGE, toPayload(destkey, keys), handler);
      return this;
    }

    @Override
    public RedisTransaction ping(Handler<AsyncResult<String>> handler) {
      sendString(PING, null, handler);
      return this;
    }

    @Override
    public RedisTransaction psetex(String key, long millis, String value, Handler<AsyncResult<String>> handler) {
      sendString(PSETEX, toPayload(key, millis, value), handler);
      return this;
    }

    @Override
    public RedisTransaction psubscribe(String pattern, Handler<AsyncResult<String>> handler) {
      sendString(PSUBSCRIBE, toPayload(pattern), handler);
      return this;
    }

    @Override
    public RedisTransaction psubscribeMany(List<String> patterns, Handler<AsyncResult<String>> handler) {
      sendString(PSUBSCRIBE, toPayload(patterns), handler);
      return this;
    }

    @Override
    public RedisTransaction pubsubChannels(String pattern, Handler<AsyncResult<String>> handler) {
      sendString(PUBSUB, toPayload("CHANNELS", pattern == null || "".equals(pattern) ? null : pattern), handler);
      return this;
    }

    public RedisTransaction pubsubNumsub(List<String> channels, Handler<AsyncResult<String>> handler) {
      sendString(PUBSUB, toPayload("NUMSUB", channels), handler);
      return this;
    }

    public RedisTransaction pubsubNumpat(Handler<AsyncResult<String>> handler) {
      sendString(PUBSUB, toPayload("NUMPAT"), handler);
      return this;
    }

    @Override
    public RedisTransaction pttl(String key, Handler<AsyncResult<String>> handler) {
      sendString(PTTL, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction publish(String channel, String message, Handler<AsyncResult<String>> handler) {
      sendString(PUBLISH, toPayload(channel, message), handler);
      return this;
    }

    @Override
    public RedisTransaction punsubscribe(List<String> patterns, Handler<AsyncResult<String>> handler) {
      sendString(PUNSUBSCRIBE, toPayload(patterns), handler);
      return this;
    }


    @Override
    public RedisTransaction randomkey(Handler<AsyncResult<String>> handler) {
      sendString(RANDOMKEY, null, handler);
      return this;
    }

    @Override
    public RedisTransaction rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
      sendString(RENAME, toPayload(key, newkey), handler);
      return this;
    }

    @Override
    public RedisTransaction renamenx(String key, String newkey, Handler<AsyncResult<String>> handler) {
      sendString(RENAMENX, toPayload(key, newkey), handler);
      return this;
    }

    @Override
    public RedisTransaction restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
      send(RESTORE, toPayload(key, millis, RedisEncoding.decode(serialized)), String.class, true, handler);
      return this;
    }

    @Override
    public RedisTransaction role(Handler<AsyncResult<String>> handler) {
      sendString(ROLE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction rpop(String key, Handler<AsyncResult<String>> handler) {
      sendString(RPOP, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
      sendString(RPOPLPUSH, toPayload(key, destkey), handler);
      return this;
    }

    @Override
    public RedisTransaction rpushMany(String key, List<String> values, Handler<AsyncResult<String>> handler) {
      sendString(RPUSH, toPayload(key, values), handler);
      return this;
    }

    @Override
    public RedisTransaction rpush(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(RPUSH, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction rpushx(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(RPUSHX, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction sadd(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(SADD, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction saddMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
      sendString(SADD, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction save(Handler<AsyncResult<String>> handler) {
      sendString(SAVE, null, handler);
      return this;
    }

    @Override
    public RedisTransaction scard(String key, Handler<AsyncResult<String>> handler) {
      sendString(SCARD, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction scriptExists(String script, Handler<AsyncResult<String>> handler) {
      sendString(SCRIPT_EXISTS, toPayload(script), handler);
      return this;
    }

    @Override
    public RedisTransaction scriptExistsMany(List<String> scripts, Handler<AsyncResult<String>> handler) {
      sendString(SCRIPT_EXISTS, toPayload(scripts), handler);
      return this;
    }

    @Override
    public RedisTransaction scriptFlush(Handler<AsyncResult<String>> handler) {
      sendString(SCRIPT_FLUSH, null, handler);
      return this;
    }

    @Override
    public RedisTransaction scriptKill(Handler<AsyncResult<String>> handler) {
      sendString(SCRIPT_KILL, null, handler);
      return this;
    }

    @Override
    public RedisTransaction scriptLoad(String script, Handler<AsyncResult<String>> handler) {
      sendString(SCRIPT_LOAD, toPayload(script), handler);
      return this;
    }

    @Override
    public RedisTransaction sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler) {
      sendString(SDIFF, toPayload(key, cmpkeys), handler);
      return this;
    }

    @Override
    public RedisTransaction sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<String>> handler) {
      sendString(SDIFFSTORE, toPayload(destkey, key, cmpkeys), handler);
      return this;
    }

    @Override
    public RedisTransaction select(int dbindex, Handler<AsyncResult<String>> handler) {
      sendString(SELECT, toPayload(dbindex), handler);
      return this;
    }

    @Override
    public RedisTransaction set(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(SET, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<String>> handler) {
      sendString(SET, toPayload(key, value, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction setBinary(String key, Buffer value, Handler<AsyncResult<String>> handler) {
      send(SET, toPayload(key, value), String.class, true, handler);
      return this;
    }

    @Override
    public RedisTransaction setBinaryWithOptions(String key, Buffer value, SetOptions options, Handler<AsyncResult<String>> handler) {
      send(SET, toPayload(key, value, options != null ? options.toJsonArray() : null), String.class, true, handler);
      return this;
    }

    @Override
    public RedisTransaction setbit(String key, long offset, int bit, Handler<AsyncResult<String>> handler) {
      sendString(SETBIT, toPayload(key, offset, bit), handler);
      return this;
    }

    @Override
    public RedisTransaction setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
      sendString(SETEX, toPayload(key, seconds, value), handler);
      return this;
    }

    @Override
    public RedisTransaction setnx(String key, String value, Handler<AsyncResult<String>> handler) {
      sendString(SETNX, toPayload(key, value), handler);
      return this;
    }

    @Override
    public RedisTransaction setrange(String key, int offset, String value, Handler<AsyncResult<String>> handler) {
      sendString(SETRANGE, toPayload(key, offset, value), handler);
      return this;
    }

    @Override
    public RedisTransaction sinter(List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(SINTER, toPayload(keys), handler);
      return this;
    }

    @Override
    public RedisTransaction sinterstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(SINTERSTORE, toPayload(destkey, keys), handler);
      return this;
    }

    @Override
    public RedisTransaction sismember(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(SISMEMBER, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
      sendString(SLAVEOF, toPayload(host, port), handler);
      return this;
    }

    @Override
    public RedisTransaction slaveofNoone(Handler<AsyncResult<String>> handler) {
      sendString(SLAVEOF, toPayload("NO", "ONE"), handler);
      return this;
    }

    @Override
    public RedisTransaction slowlogGet(int limit, Handler<AsyncResult<String>> handler) {
      sendString(SLOWLOG, toPayload("GET", limit < 0 ? null : limit), handler);
      return this;
    }

    @Override
    public RedisTransaction slowlogLen(Handler<AsyncResult<String>> handler) {
      sendString(SLOWLOG, toPayload("LEN"), handler);
      return this;
    }

    @Override
    public RedisTransaction slowlogReset(Handler<AsyncResult<String>> handler) {
      sendString(SLOWLOG, toPayload("RESET"), handler);
      return this;
    }

    @Override
    public RedisTransaction smembers(String key, Handler<AsyncResult<String>> handler) {
      sendString(SMEMBERS, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction smove(String key, String destkey, String member, Handler<AsyncResult<String>> handler) {
      sendString(SMOVE, toPayload(key, destkey, member), handler);
      return this;
    }

    @Override
    public RedisTransaction sort(String key, SortOptions options, Handler<AsyncResult<String>> handler) {
      sendString(SORT, toPayload(key, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction spop(String key, Handler<AsyncResult<String>> handler) {
      sendString(SPOP, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
      sendString(SPOP, toPayload(key, count), handler);
      return this;
    }

    @Override
    public RedisTransaction srandmember(String key, Handler<AsyncResult<String>> handler) {
      sendString(SRANDMEMBER, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction srandmemberCount(String key, int count, Handler<AsyncResult<String>> handler) {
      sendString(SRANDMEMBER, toPayload(key, count), handler);
      return this;
    }

    @Override
    public RedisTransaction srem(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(SREM, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction sremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
      sendString(SREM, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction strlen(String key, Handler<AsyncResult<String>> handler) {
      sendString(STRLEN, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction subscribe(String channel, Handler<AsyncResult<String>> handler) {
      sendString(SUBSCRIBE, toPayload(channel), handler);
      return this;
    }

    @Override
    public RedisTransaction subscribeMany(List<String> channels, Handler<AsyncResult<String>> handler) {
      sendString(SUBSCRIBE, toPayload(channels), handler);
      return this;
    }

    @Override
    public RedisTransaction sunion(List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(SUNION, toPayload(keys), handler);
      return this;
    }

    @Override
    public RedisTransaction sunionstore(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(SUNIONSTORE, toPayload(destkey, keys), handler);
      return this;
    }

    @Override
    public RedisTransaction sync(Handler<AsyncResult<String>> handler) {
      sendString(SYNC, null, handler);
      return this;
    }

    @Override
    public RedisTransaction time(Handler<AsyncResult<String>> handler) {
      sendString(TIME, null, handler);
      return this;
    }

    @Override
    public RedisTransaction ttl(String key, Handler<AsyncResult<String>> handler) {
      sendString(TTL, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction type(String key, Handler<AsyncResult<String>> handler) {
      sendString(TYPE, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction unsubscribe(List<String> channels, Handler<AsyncResult<String>> handler) {
      sendString(UNSUBSCRIBE, toPayload(channels), handler);
      return this;
    }

    @Override
    public RedisTransaction unwatch(Handler<AsyncResult<String>> handler) {
      sendString(UNWATCH, null, handler);
      return this;
    }

    @Override
    public RedisTransaction wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) {
      sendString(WAIT, toPayload(numSlaves, timeout), handler);
      return this;
    }

    @Override
    public RedisTransaction watch(String key, Handler<AsyncResult<String>> handler) {
      sendString(WATCH, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction watchMany(List<String> keys, Handler<AsyncResult<String>> handler) {
      sendString(WATCH, toPayload(keys), handler);
      return this;
    }

    @Override
    public RedisTransaction zadd(String key, double score, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZADD, toPayload(key, score, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<String>> handler) {
      // flip from <String, Double> to <Double, String> when wrapping
      Stream flipped = members.entrySet().stream().map(e -> new Object[] { e.getValue(), e.getKey() });
      sendString(ZADD, toPayload(key, flipped), handler);
      return this;
    }

    @Override
    public RedisTransaction zcard(String key, Handler<AsyncResult<String>> handler) {
      sendString(ZCARD, toPayload(key), handler);
      return this;
    }

    @Override
    public RedisTransaction zcount(String key, double min, double max, Handler<AsyncResult<String>> handler) {
      String minVal = (min == Double.NEGATIVE_INFINITY) ? "-inf" : String.valueOf(min);
      String maxVal = (max == Double.POSITIVE_INFINITY) ? "+inf" : String.valueOf(max);
      sendString(ZCOUNT, toPayload(key, minVal, maxVal), handler);
      return this;
    }

    @Override
    public RedisTransaction zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZINCRBY, toPayload(key, increment, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZINTERSTORE, toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZINTERSTORE, toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
              options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zlexcount(String key, String min, String max, Handler<AsyncResult<String>> handler) {
      sendString(ZLEXCOUNT, toPayload(key, min, max), handler);
      return this;
    }

    @Override
    public RedisTransaction zrange(String key, long start, long stop, Handler<AsyncResult<String>> handler) {
      sendString(ZRANGE, toPayload(key, start, stop), handler);
      return this;
    }

    @Override
    public RedisTransaction zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZRANGE, toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZRANGEBYLEX, toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZRANGEBYSCORE, toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrank(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZRANK, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zrem(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZREM, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zremMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
      sendString(ZREM, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction zremrangebylex(String key, String min, String max, Handler<AsyncResult<String>> handler) {
      sendString(ZREMRANGEBYLEX, toPayload(key, min, max), handler);
      return this;
    }

    @Override
    public RedisTransaction zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<String>> handler) {
      sendString(ZREMRANGEBYRANK, toPayload(key, start, stop), handler);
      return this;
    }

    @Override
    public RedisTransaction zremrangebyscore(String key, String min, String max, Handler<AsyncResult<String>> handler) {
      sendString(ZREMRANGEBYSCORE, toPayload(key, min, max), handler);
      return this;
    }

    @Override
    public RedisTransaction zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZREVRANGE, toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZREVRANGEBYLEX, toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZREVRANGEBYSCORE, toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zrevrank(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZREVRANK, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zscore(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(ZSCORE, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZUNIONSTORE, toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zunionstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZUNIONSTORE, toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
              options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction scan(String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) {
      sendString(SCAN, toPayload(cursor, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) {
      sendString(SSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) {
      sendString(HSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<String>> handler) {
      sendString(ZSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction geoadd(String key, double longitude, double latitude, String member, Handler<AsyncResult<String>> handler) {
      sendString(GEOADD, toPayload(key, longitude, latitude, member), handler);
      return this;
    }

    @Override
    public RedisTransaction geoaddMany(String key, List<GeoMember> members, Handler<AsyncResult<String>> handler) {
      sendString(GEOADD, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction geohash(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(GEOHASH, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction geohashMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
      sendString(GEOHASH, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction geopos(String key, String member, Handler<AsyncResult<String>> handler) {
      sendString(GEOPOS, toPayload(key, member), handler);
      return this;
    }

    @Override
    public RedisTransaction geoposMany(String key, List<String> members, Handler<AsyncResult<String>> handler) {
      sendString(GEOPOS, toPayload(key, members), handler);
      return this;
    }

    @Override
    public RedisTransaction geodist(String key, String member1, String member2, Handler<AsyncResult<String>> handler) {
      sendString(GEODIST, toPayload(key, member1, member2), handler);
      return this;
    }

    @Override
    public RedisTransaction geodistWithUnit(String key, String member1, String member2, GeoUnit unit, Handler<AsyncResult<String>> handler) {
      sendString(GEODIST, toPayload(key, member1, member2, unit), handler);
      return this;
    }

    @Override
    public RedisTransaction georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler) {
      sendString(GEORADIUS, toPayload(key, longitude, latitude, radius, unit), handler);
      return this;
    }

    @Override
    public RedisTransaction georadiusWithOptions(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler) {
      sendString(GEORADIUS, toPayload(key, longitude, latitude, radius, unit, options != null ? options.toJsonArray() : null), handler);
      return this;
    }

    @Override
    public RedisTransaction georadiusbymember(String key, String member, double radius, GeoUnit unit, Handler<AsyncResult<String>> handler) {
      sendString(GEORADIUSBYMEMBER, toPayload(key, member, radius, unit), handler);
      return this;
    }

    @Override
    public RedisTransaction georadiusbymemberWithOptions(String key, String member, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<String>> handler) {
      sendString(GEORADIUSBYMEMBER, toPayload(key, member, radius, unit, options != null ? options.toJsonArray() : null), handler);
      return this;
    }
  }

  public RedisClientImpl(Vertx vertx, RedisOptions config) {
    super(vertx, config);
    this.transaction = new RedisTransactionImpl();
  }

  @Override
  public RedisClient append(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(APPEND, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient auth(String password, Handler<AsyncResult<String>> handler) {
    sendString(AUTH, toPayload(password), handler);
    return this;
  } 

  @Override
  public RedisClient bgrewriteaof(Handler<AsyncResult<String>> handler) {
    sendString(BGREWRITEAOF, null, handler);
    return this;
  } 

  @Override
  public RedisClient bgsave(Handler<AsyncResult<String>> handler) {
    sendString(BGSAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient bitcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(BITCOUNT, toPayload(key), handler);
    return this;
  } 

  public RedisClient bitcountRange(String key, long start, long end, Handler<AsyncResult<Long>> handler) {
    sendLong(BITCOUNT, toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient bitop(BitOperation operation, String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(BITOP, toPayload(operation.name(), destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient bitpos(String key, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, toPayload(key, bit), handler);
    return this;
  } 

  @Override
  public RedisClient bitposFrom(String key, int bit, int start, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, toPayload(key, bit, start), handler);
    return this;
  } 

  @Override
  public RedisClient bitposRange(String key, int bit, int start, int stop, Handler<AsyncResult<Long>> handler) {
    sendLong(BITPOS, toPayload(key, bit, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient blpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BLPOP, toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient blpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BLPOP, toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpop(String key, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BRPOP, toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpopMany(List<String> keys, int seconds, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(BRPOP, toPayload(keys, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient brpoplpush(String key, String destkey, int seconds, Handler<AsyncResult<String>> handler) {
    sendString(BRPOPLPUSH, toPayload(key, destkey, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient clientKill(KillFilter filter, Handler<AsyncResult<Long>> handler) {
    sendLong(CLIENT_KILL, filter.toJsonArray().getList(), handler);
    return this;
  } 

  @Override
  public RedisClient clientList(Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_LIST, null, handler);
    return this;
  } 

  @Override
  public RedisClient clientGetname(Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_GETNAME, null, handler);
    return this;
  } 

  @Override
  public RedisClient clientPause(long millis, Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_PAUSE, toPayload(millis), handler);
    return this;
  } 

  @Override
  public RedisClient clientSetname(String name, Handler<AsyncResult<String>> handler) {
    sendString(CLIENT_SETNAME, toPayload(name), handler);
    return this;
  }

  @Override
  public RedisClient clusterAddslots(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_ADDSLOTS, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterCountFailureReports(String nodeId, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_COUNT_FAILURE_REPORTS, toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterCountkeysinslot(long slot, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_COUNTKEYSINSLOT, toPayload(slot), handler);
    return this;
  }

  @Override
  public RedisClient clusterDelslots(long slot, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_DELSLOTS, toPayload(slot), handler);
    return this;
  }

  @Override
  public RedisClient clusterDelslotsMany(List<Long> slots, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_DELSLOTS, toPayload(slots), handler);
    return this;
  }

  @Override
  public RedisClient clusterFailover(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FAILOVER, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterFailOverWithOptions(FailoverOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FAILOVER, toPayload(options), handler);
    return this;
  }

  @Override
  public RedisClient clusterForget(String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_FORGET, toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterGetkeysinslot(long slot, long count, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_GETKEYSINSLOT, toPayload(slot, count), handler);
    return this;
  }

  @Override
  public RedisClient clusterInfo(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_INFO, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterKeyslot(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(CLUSTER_KEYSLOT, toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient clusterMeet(String ip, long port, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_MEET, toPayload(ip, port), handler);
    return this;
  }

  @Override
  public RedisClient clusterNodes(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_NODES, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterReplicate(String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_REPLICATE, toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterReset(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_RESET, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterResetWithOptions(ResetOptions options, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_RESET, toPayload(options), handler);
    return this;
  }

  @Override
  public RedisClient clusterSaveconfig(Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SAVECONFIG, null, handler);
    return this;
  }

  @Override
  public RedisClient clusterSetConfigEpoch(long epoch, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SET_CONFIG_EPOCH, toPayload(epoch), handler);
    return this;
  }

  @Override
  public RedisClient clusterSetslot(long slot, SlotCmd subcommand, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SETSLOT, toPayload(slot, subcommand), handler);
    return this;
  }

  @Override
  public RedisClient clusterSetslotWithNode(long slot, SlotCmd subcommand, String nodeId, Handler<AsyncResult<Void>> handler) {
    sendVoid(CLUSTER_SETSLOT, toPayload(slot, subcommand, nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterSlaves(String nodeId, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_SLAVES, toPayload(nodeId), handler);
    return this;
  }

  @Override
  public RedisClient clusterSlots(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CLUSTER_SLOTS, null, handler);
    return this;
  } 

  @Override
  public RedisClient command(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandCount(Handler<AsyncResult<Long>> handler) {
    sendLong(COMMAND_COUNT, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandGetkeys(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND_GETKEYS, null, handler);
    return this;
  } 

  @Override
  public RedisClient commandInfo(List<String> commands, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(COMMAND_INFO, toPayload(commands), handler);
    return this;
  } 

  @Override
  public RedisClient configGet(String parameter, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(CONFIG_GET, toPayload(parameter), handler);
    return this;
  } 

  @Override
  public RedisClient configRewrite(Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_REWRITE, null, handler);
    return this;
  } 

  @Override
  public RedisClient configSet(String parameter, String value, Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_SET, toPayload(parameter, value), handler);
    return this;
  } 

  @Override
  public RedisClient configResetstat(Handler<AsyncResult<String>> handler) {
    sendString(CONFIG_RESETSTAT, null, handler);
    return this;
  } 

  @Override
  public RedisClient dbsize(Handler<AsyncResult<Long>> handler) {
    sendLong(DBSIZE, null, handler);
    return this;
  } 

  @Override
  public RedisClient debugObject(String key, Handler<AsyncResult<String>> handler) {
    sendString(DEBUG_OBJECT, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient debugSegfault(Handler<AsyncResult<String>> handler) {
    sendString(DEBUG_SEGFAULT, null, handler);
    return this;
  } 

  @Override
  public RedisClient decr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(DECR, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient decrby(String key, long decrement, Handler<AsyncResult<Long>> handler) {
    sendLong(DECRBY, toPayload(key, decrement), handler);
    return this;
  } 

  @Override
  public RedisClient del(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(DEL, toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient delMany(List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(DEL, toPayload(keys), handler);
    return this;
  }

  @Override
  public RedisClient dump(String key, Handler<AsyncResult<String>> handler) {
    send(DUMP, toPayload(key), String.class, true, dump -> {
      if (dump.failed()) {
        handler.handle(dump);
      } else {
        handler.handle(Future.succeededFuture(RedisEncoding.encode(dump.result())));
      }
    });
    return this;
  } 

  @Override
  public RedisClient echo(String message, Handler<AsyncResult<String>> handler) {
    sendString(ECHO, toPayload(message), handler);
    return this;
  } 

  @Override
  public RedisClient eval(String script, List<String> keys, List<String> args, Handler<AsyncResult<JsonArray>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    sendJsonArray(EVAL, toPayload(script, keys.size(), keys, args), handler);
    return this;
  } 

  @Override
  public RedisClient evalsha(String sha1, List<String> keys, List<String> args, Handler<AsyncResult<JsonArray>> handler) {
    keys = (keys != null) ? keys : Collections.emptyList();
    args = (args != null) ? args : Collections.emptyList();
    sendJsonArray(EVALSHA, toPayload(sha1, keys.size(), keys, args), handler);
    return this;
  }

  @Override
  public RedisClient exists(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(EXISTS, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient expire(String key, int seconds, Handler<AsyncResult<Long>> handler) {
    sendLong(EXPIRE, toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient expireat(String key, long seconds, Handler<AsyncResult<Long>> handler) {
    sendLong(EXPIREAT, toPayload(key, seconds), handler);
    return this;
  } 

  @Override
  public RedisClient flushall(Handler<AsyncResult<String>> handler) {
    sendString(FLUSHALL, null, handler);
    return this;
  } 

  @Override
  public RedisClient flushdb(Handler<AsyncResult<String>> handler) {
    sendString(FLUSHDB, null, handler);
    return this;
  } 

  @Override
  public RedisClient get(String key, Handler<AsyncResult<String>> handler) {
    sendString(GET, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient getBinary(String key, Handler<AsyncResult<Buffer>> handler) {
    send(GET, toPayload(key), Buffer.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient getbit(String key, long offset, Handler<AsyncResult<Long>> handler) {
    sendLong(GETBIT, toPayload(key, offset), handler);
    return this;
  } 

  @Override
  public RedisClient getrange(String key, long start, long end, Handler<AsyncResult<String>> handler) {
    sendString(GETRANGE, toPayload(key, start, end), handler);
    return this;
  } 

  @Override
  public RedisClient getset(String key, String value, Handler<AsyncResult<String>> handler) {
    sendString(GETSET, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient hdel(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong(HDEL, toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hdelMany(String key, List<String> fields, Handler<AsyncResult<Long>> handler) {
    sendLong(HDEL, toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hexists(String key, String field, Handler<AsyncResult<Long>> handler) {
    sendLong(HEXISTS, toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hget(String key, String field, Handler<AsyncResult<String>> handler) {
    sendString(HGET, toPayload(key, field), handler);
    return this;
  } 

  @Override
  public RedisClient hgetall(String key, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(HGETALL, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hincrby(String key, String field, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong(HINCRBY, toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hincrbyfloat(String key, String field, double increment, Handler<AsyncResult<String>> handler) {
    sendString(HINCRBYFLOAT, toPayload(key, field, increment), handler);
    return this;
  } 

  @Override
  public RedisClient hkeys(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HKEYS, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(HLEN, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient hmget(String key, List<String> fields, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HMGET, toPayload(key, fields), handler);
    return this;
  } 

  @Override
  public RedisClient hmset(String key, JsonObject values, Handler<AsyncResult<String>> handler) {
    sendString(HMSET, toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient hset(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(HSET, toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hsetnx(String key, String field, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(HSETNX, toPayload(key, field, value), handler);
    return this;
  } 

  @Override
  public RedisClient hvals(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HVALS, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incr(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(INCR, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient incrby(String key, long increment, Handler<AsyncResult<Long>> handler) {
    sendLong(INCRBY, toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient incrbyfloat(String key, double increment, Handler<AsyncResult<String>> handler) {
    sendString(INCRBYFLOAT, toPayload(key, increment), handler);
    return this;
  } 

  @Override
  public RedisClient info(Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(INFO, Collections.emptyList(), handler);
    return this;
  } 

  @Override
  public RedisClient infoSection(String section, Handler<AsyncResult<JsonObject>> handler) {
    sendJsonObject(INFO, toPayload(section), handler);
    return this;
  } 

  @Override
  public RedisClient keys(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(KEYS, toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient lastsave(Handler<AsyncResult<Long>> handler) {
    sendLong(LASTSAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient lindex(String key, int index, Handler<AsyncResult<String>> handler) {
    sendString(LINDEX, toPayload(key, index), handler);
    return this;
  } 

  @Override
  public RedisClient linsert(String key, InsertOptions option, String pivot, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LINSERT, toPayload(key, option.name(), pivot, value), handler);
    return this;
  } 

  @Override
  public RedisClient llen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(LLEN, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpop(String key, Handler<AsyncResult<String>> handler) {
    sendString(LPOP, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient lpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSH, toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient lpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSH, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LPUSHX, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient lrange(String key, long from, long to, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(LRANGE, toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient lrem(String key, long count, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(LREM, toPayload(key, count, value), handler);
    return this;
  } 

  @Override
  public RedisClient lset(String key, long index, String value, Handler<AsyncResult<String>> handler) {
    sendString(LSET, toPayload(key, index, value), handler);
    return this;
  } 

  @Override
  public RedisClient ltrim(String key, long from, long to, Handler<AsyncResult<String>> handler) {
    sendString(LTRIM, toPayload(key, from, to), handler);
    return this;
  } 

  @Override
  public RedisClient mget(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MGET, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient mgetMany(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(MGET, toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient migrate(String host, int port, String key, int destdb, long timeout, MigrateOptions options, Handler<AsyncResult<String>> handler) {
    sendString(MIGRATE, toPayload(host, port, key, destdb, timeout, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient monitor(Handler<AsyncResult<Void>> handler) {
    sendVoid(MONITOR, null, handler);
    return this;
  } 

  @Override
  public RedisClient move(String key, int destdb, Handler<AsyncResult<Long>> handler) {
    sendLong(MOVE, toPayload(key, destdb), handler);
    return this;
  } 

  @Override
  public RedisClient mset(JsonObject keyvals, Handler<AsyncResult<String>> handler) {
    sendString(MSET, toPayload(keyvals), handler);
    return this;
  } 

  @Override
  public RedisClient msetnx(JsonObject keyvals, Handler<AsyncResult<Long>> handler) {
    sendLong(MSETNX, toPayload(keyvals), handler);
    return this;
  }

  @Override
  public RedisClient object(String key, ObjectCmd cmd, Handler<AsyncResult<Void>> handler) {
    sendVoid(OBJECT, toPayload(cmd.name(), key), handler);
    return this;
  } 

  @Override
  public RedisClient persist(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PERSIST, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pexpire(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong(PEXPIRE, toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pexpireat(String key, long millis, Handler<AsyncResult<Long>> handler) {
    sendLong(PEXPIREAT, toPayload(key, millis), handler);
    return this;
  } 

  @Override
  public RedisClient pfadd(String key, String element, Handler<AsyncResult<Long>> handler) {
    sendLong(PFADD, toPayload(key, element), handler);
    return this;
  } 

  @Override
  public RedisClient pfaddMany(String key, List<String> elements, Handler<AsyncResult<Long>> handler) {
    sendLong(PFADD, toPayload(key, elements), handler);
    return this;
  } 

  @Override
  public RedisClient pfcount(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PFCOUNT, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfcountMany(List<String> key, Handler<AsyncResult<Long>> handler) {
    sendLong(PFCOUNT, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient pfmerge(String destkey, List<String> keys, Handler<AsyncResult<String>> handler) {
    sendString(PFMERGE, toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient ping(Handler<AsyncResult<String>> handler) {
    sendString(PING, null, handler);
    return this;
  } 

  @Override
  public RedisClient psetex(String key, long millis, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid(PSETEX, toPayload(key, millis, value), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribe(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PSUBSCRIBE, toPayload(pattern), handler);
    return this;
  } 

  @Override
  public RedisClient psubscribeMany(List<String> patterns, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PSUBSCRIBE, toPayload(patterns), handler);
    return this;
  } 

  @Override
  public RedisClient pubsubChannels(String pattern, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PUBSUB, toPayload("CHANNELS", pattern == null || "".equals(pattern) ? null : pattern), handler);
    return this;
  } 

  public RedisClient pubsubNumsub(List<String> channels, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(PUBSUB, toPayload("NUMSUB", channels), handler);
    return this;
  } 

  public RedisClient pubsubNumpat(Handler<AsyncResult<Long>> handler) {
    sendLong(PUBSUB, toPayload("NUMPAT"), handler);
    return this;
  } 

  @Override
  public RedisClient pttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(PTTL, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient publish(String channel, String message, Handler<AsyncResult<Long>> handler) {
    sendLong(PUBLISH, toPayload(channel, message), handler);
    return this;
  } 

  @Override
  public RedisClient punsubscribe(List<String> patterns, Handler<AsyncResult<Void>> handler) {
    sendVoid(PUNSUBSCRIBE, toPayload(patterns), handler);
    return this;
  } 


  @Override
  public RedisClient randomkey(Handler<AsyncResult<String>> handler) {
    sendString(RANDOMKEY, null, handler);
    return this;
  } 

  @Override
  public RedisClient rename(String key, String newkey, Handler<AsyncResult<String>> handler) {
    sendString(RENAME, toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient renamenx(String key, String newkey, Handler<AsyncResult<Long>> handler) {
    sendLong(RENAMENX, toPayload(key, newkey), handler);
    return this;
  } 

  @Override
  public RedisClient restore(String key, long millis, String serialized, Handler<AsyncResult<String>> handler) {
    send(RESTORE, toPayload(key, millis, RedisEncoding.decode(serialized)), String.class, true, handler);
    return this;
  } 

  @Override
  public RedisClient role(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ROLE, null, handler);
    return this;
  } 

  @Override
  public RedisClient rpop(String key, Handler<AsyncResult<String>> handler) {
    sendString(RPOP, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient rpoplpush(String key, String destkey, Handler<AsyncResult<String>> handler) {
    sendString(RPOPLPUSH, toPayload(key, destkey), handler);
    return this;
  } 

  @Override
  public RedisClient rpushMany(String key, List<String> values, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSH, toPayload(key, values), handler);
    return this;
  } 

  @Override
  public RedisClient rpush(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSH, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient rpushx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(RPUSHX, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient sadd(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SADD, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient saddMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(SADD, toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient save(Handler<AsyncResult<String>> handler) {
    sendString(SAVE, null, handler);
    return this;
  } 

  @Override
  public RedisClient scard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(SCARD, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExists(String script, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCRIPT_EXISTS, toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient scriptExistsMany(List<String> scripts, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCRIPT_EXISTS, toPayload(scripts), handler);
    return this;
  } 

  @Override
  public RedisClient scriptFlush(Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_FLUSH, null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptKill(Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_KILL, null, handler);
    return this;
  } 

  @Override
  public RedisClient scriptLoad(String script, Handler<AsyncResult<String>> handler) {
    sendString(SCRIPT_LOAD, toPayload(script), handler);
    return this;
  } 

  @Override
  public RedisClient sdiff(String key, List<String> cmpkeys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SDIFF, toPayload(key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient sdiffstore(String destkey, String key, List<String> cmpkeys, Handler<AsyncResult<Long>> handler) {
    sendLong(SDIFFSTORE, toPayload(destkey, key, cmpkeys), handler);
    return this;
  } 

  @Override
  public RedisClient select(int dbindex, Handler<AsyncResult<String>> handler) {
    sendString(SELECT, toPayload(dbindex), handler);
    return this;
  } 

  @Override
  public RedisClient set(String key, String value, Handler<AsyncResult<Void>> handler) {
    sendVoid(SET, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setWithOptions(String key, String value, SetOptions options, Handler<AsyncResult<String>> handler) {
    sendString(SET, toPayload(key, value, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient setBinary(String key, Buffer value, Handler<AsyncResult<Void>> handler) {
    send(SET, toPayload(key, value), Void.class, true, handler);
    return this;
  }

  @Override
  public RedisClient setBinaryWithOptions(String key, Buffer value, SetOptions options, Handler<AsyncResult<Void>> handler) {
    send(SET, toPayload(key, value, options != null ? options.toJsonArray() : null), Void.class, true, handler);
    return this;
  }

  @Override
  public RedisClient setbit(String key, long offset, int bit, Handler<AsyncResult<Long>> handler) {
    sendLong(SETBIT, toPayload(key, offset, bit), handler);
    return this;
  } 

  @Override
  public RedisClient setex(String key, long seconds, String value, Handler<AsyncResult<String>> handler) {
    sendString(SETEX, toPayload(key, seconds, value), handler);
    return this;
  } 

  @Override
  public RedisClient setnx(String key, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(SETNX, toPayload(key, value), handler);
    return this;
  } 

  @Override
  public RedisClient setrange(String key, int offset, String value, Handler<AsyncResult<Long>> handler) {
    sendLong(SETRANGE, toPayload(key, offset, value), handler);
    return this;
  } 

  @Override
  public RedisClient sinter(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SINTER, toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sinterstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(SINTERSTORE, toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sismember(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SISMEMBER, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient slaveof(String host, int port, Handler<AsyncResult<String>> handler) {
    sendString(SLAVEOF, toPayload(host, port), handler);
    return this;
  } 

  @Override
  public RedisClient slaveofNoone(Handler<AsyncResult<String>> handler) {
    sendString(SLAVEOF, toPayload("NO", "ONE"), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogGet(int limit, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SLOWLOG, toPayload("GET", limit < 0 ? null : limit), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogLen(Handler<AsyncResult<Long>> handler) {
    sendLong(SLOWLOG, toPayload("LEN"), handler);
    return this;
  } 

  @Override
  public RedisClient slowlogReset(Handler<AsyncResult<Void>> handler) {
    sendVoid(SLOWLOG, toPayload("RESET"), handler);
    return this;
  } 

  @Override
  public RedisClient smembers(String key, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SMEMBERS, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient smove(String key, String destkey, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SMOVE, toPayload(key, destkey, member), handler);
    return this;
  } 

  @Override
  public RedisClient sort(String key, SortOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SORT, toPayload(key, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient spop(String key, Handler<AsyncResult<String>> handler) {
    sendString(SPOP, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient spopMany(String key, int count, Handler<AsyncResult<String>> handler) {
    sendString(SPOP, toPayload(key, count), handler);
    return this;
  } 

  @Override
  public RedisClient srandmember(String key, Handler<AsyncResult<String>> handler) {
    sendString(SRANDMEMBER, toPayload(key), handler);
    return this;
  }

  @Override
  public RedisClient srandmemberCount(String key, int count, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SRANDMEMBER, toPayload(key, count), handler);
    return this;
  }

  @Override
  public RedisClient srem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(SREM, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient sremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(SREM, toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient strlen(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(STRLEN, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient subscribe(String channel, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUBSCRIBE, toPayload(channel), handler);
    return this;
  }

  @Override
  public RedisClient subscribeMany(List<String> channels, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUBSCRIBE, toPayload(channels), handler);
    return this;
  }

  @Override
  public RedisClient sunion(List<String> keys, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SUNION, toPayload(keys), handler);
    return this;
  } 

  @Override
  public RedisClient sunionstore(String destkey, List<String> keys, Handler<AsyncResult<Long>> handler) {
    sendLong(SUNIONSTORE, toPayload(destkey, keys), handler);
    return this;
  } 

  @Override
  public RedisClient sync(Handler<AsyncResult<Void>> handler) {
    sendVoid(SYNC, null, handler);
    return this;
  } 

  @Override
  public RedisClient time(Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(TIME, null, handler);
    return this;
  }

  @Override
  public RedisTransaction transaction() {
    return transaction;
  }


  @Override
  public RedisClient ttl(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(TTL, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient type(String key, Handler<AsyncResult<String>> handler) {
    sendString(TYPE, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient unsubscribe(List<String> channels, Handler<AsyncResult<Void>> handler) {
    sendVoid(UNSUBSCRIBE, toPayload(channels), handler);
    return this;
  }

  @Override
  public RedisClient wait(long numSlaves, long timeout, Handler<AsyncResult<String>> handler) {
    sendString(WAIT, toPayload(numSlaves, timeout), handler);
    return this;
  }

  @Override
  public RedisClient zadd(String key, double score, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZADD, toPayload(key, score, member), handler);
    return this;
  } 

  @Override
  public RedisClient zaddMany(String key, Map<String, Double> members, Handler<AsyncResult<Long>> handler) {
    // flip from <String, Double> to <Double, String> when wrapping
    Stream flipped = members.entrySet().stream().map(e -> new Object[] { e.getValue(), e.getKey() });
    sendLong(ZADD, toPayload(key, flipped), handler);
    return this;
  } 

  @Override
  public RedisClient zcard(String key, Handler<AsyncResult<Long>> handler) {
    sendLong(ZCARD, toPayload(key), handler);
    return this;
  } 

  @Override
  public RedisClient zcount(String key, double min, double max, Handler<AsyncResult<Long>> handler) {
    String minVal = (min == Double.NEGATIVE_INFINITY) ? "-inf" : String.valueOf(min);
    String maxVal = (max == Double.POSITIVE_INFINITY) ? "+inf" : String.valueOf(max);
    sendLong(ZCOUNT, toPayload(key, minVal, maxVal), handler);
    return this;
  } 

  @Override
  public RedisClient zincrby(String key, double increment, String member, Handler<AsyncResult<String>> handler) {
    sendString(ZINCRBY, toPayload(key, increment, member), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZINTERSTORE, toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zinterstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZINTERSTORE, toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
      options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zlexcount(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZLEXCOUNT, toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrange(String key, long start, long stop, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGE, toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zrangeWithOptions(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGE, toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebylex(String key, String min, String max, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGEBYLEX, toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrangebyscore(String key, String min, String max, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZRANGEBYSCORE, toPayload(key, min, max, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZRANK, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zrem(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREM, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zremMany(String key, List<String> members, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREM, toPayload(key, members), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebylex(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYLEX, toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyrank(String key, long start, long stop, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYRANK, toPayload(key, start, stop), handler);
    return this;
  } 

  @Override
  public RedisClient zremrangebyscore(String key, String min, String max, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREMRANGEBYSCORE, toPayload(key, min, max), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrange(String key, long start, long stop, RangeOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGE, toPayload(key, start, stop, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebylex(String key, String max, String min, LimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGEBYLEX, toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrangebyscore(String key, String max, String min, RangeLimitOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZREVRANGEBYSCORE, toPayload(key, max, min, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zrevrank(String key, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(ZREVRANK, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zscore(String key, String member, Handler<AsyncResult<String>> handler) {
    sendString(ZSCORE, toPayload(key, member), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstore(String destkey, List<String> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZUNIONSTORE, toPayload(destkey, sets.size(), sets, options != null ? options.name() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zunionstoreWeighed(String destkey, Map<String, Double> sets, AggregateOptions options, Handler<AsyncResult<Long>> handler) {
    sendLong(ZUNIONSTORE, toPayload(destkey, sets.size(), sets.keySet(), "WEIGHTS", sets.values(),
      options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient scan(String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SCAN, toPayload(cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient sscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(SSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient hscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(HSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  } 

  @Override
  public RedisClient zscan(String key, String cursor, ScanOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(ZSCAN, toPayload(key, cursor, options != null ? options.toJsonArray() : null), handler);
    return this;
  }

  @Override
  public RedisClient geoadd(String key, double longitude, double latitude, String member, Handler<AsyncResult<Long>> handler) {
    sendLong(GEOADD, toPayload(key, longitude, latitude, member), handler);
    return this;
  }

  @Override
  public RedisClient geoaddMany(String key, List<GeoMember> members, Handler<AsyncResult<Long>> handler) {
    sendLong(GEOADD, toPayload(key, members), handler);
    return this;
  }

  @Override
  public RedisClient geohash(String key, String member, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEOHASH, toPayload(key, member), handler);
    return this;
  }

  @Override
  public RedisClient geohashMany(String key, List<String> members, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEOHASH, toPayload(key, members), handler);
    return this;
  }

  @Override
  public RedisClient geopos(String key, String member, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEOPOS, toPayload(key, member), handler);
    return this;
  }

  @Override
  public RedisClient geoposMany(String key, List<String> members, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEOPOS, toPayload(key, members), handler);
    return this;
  }

  @Override
  public RedisClient geodist(String key, String member1, String member2, Handler<AsyncResult<String>> handler) {
    sendString(GEODIST, toPayload(key, member1, member2), handler);
    return this;
  }

  @Override
  public RedisClient geodistWithUnit(String key, String member1, String member2, GeoUnit unit, Handler<AsyncResult<String>> handler) {
    sendString(GEODIST, toPayload(key, member1, member2, unit), handler);
    return this;
  }

  @Override
  public RedisClient georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEORADIUS, toPayload(key, longitude, latitude, radius, unit), handler);
    return this;
  }

  @Override
  public RedisClient georadiusWithOptions(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEORADIUS, toPayload(key, longitude, latitude, radius, unit, options != null ? options.toJsonArray() : null), handler);
    return this;
  }

  @Override
  public RedisClient georadiusbymember(String key, String member, double radius, GeoUnit unit, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEORADIUSBYMEMBER, toPayload(key, member, radius, unit), handler);
    return this;
  }

  @Override
  public RedisClient georadiusbymemberWithOptions(String key, String member, double radius, GeoUnit unit, GeoRadiusOptions options, Handler<AsyncResult<JsonArray>> handler) {
    sendJsonArray(GEORADIUSBYMEMBER, toPayload(key, member, radius, unit, options != null ? options.toJsonArray() : null), handler);
    return this;
  }

  /**
   * A helper method to package method parameters into JsonArray payload.
   *
   * Null parameters are ignored.
   *
   * @param parameters Call parameters
   * @return JsonArray that can be passed to send()
   */
  @SuppressWarnings("unchecked")
  private static List<?> toPayload(Object ... parameters) {
    List<Object> result = new ArrayList<>(parameters.length);

    for (Object param: parameters) {
      // unwrap
      if (param instanceof JsonArray) {
        param = ((JsonArray) param).getList();
      }
      // unwrap
      if (param instanceof JsonObject) {
        param = ((JsonObject) param).getMap();
      }

      if (param instanceof Collection) {
        ((Collection) param).stream().filter(el -> el != null).forEach(result::add);
      } else if (param instanceof Map) {
        for (Map.Entry<?, ?> pair : ((Map<?, ?>) param).entrySet()) {
          result.add(pair.getKey());
          result.add(pair.getValue());
        }
      } else if (param instanceof Stream) {
        ((Stream) param).forEach(e -> {
          if (e instanceof Object[]) {
            for (Object item : (Object[]) e) {
              result.add(item);
            }
          } else {
            result.add(e);
          }
        });
      } else if (param instanceof Buffer) {
        result.add(((Buffer) param).getBytes());
      } else if (param != null) {
        result.add(param);
      }
    }
    return result;
  } 
}
