/**
 * = Vert.x-redis
 * :toc: left
 *
 * Vert.x-redis is redis client to be used with Vert.x.
 *
 * This module allows data to be saved, retrieved, searched for, and deleted in a Redis. Redis is an open source, BSD
 * licensed, advanced key-value store. It is often referred to as a data structure server since keys can contain
 * strings, hashes, lists, sets and sorted sets. To use this module you must have a Redis server instance running on
 * your network.
 *
 * Redis has a rich API and it can be organized in the following groups:
 *
 * * Cluster - Commands related to cluster management, note that using most of these commands you will need a redis server with version &lt;=3.0.0
 * * Connection - Commands that allow you to switch DBs, connect, disconnect and authenticate to a server.
 * * Hashes - Commands that allow operations on hashes.
 * * HyperLogLog - Commands to approximating the number of distinct elements in a multiset, a HyperLogLog.
 * * Keys - Commands to work with Keys.
 * * List - Commands to work with Lists.
 * * Pub/Sub - Commands to create queues and pub/sub clients.
 * * Scripting - Commands to run Lua Scripts in redis.
 * * Server - Commands to manage and get server configurations.
 * * Sets - Commands to work with un ordered sets.
 * * Sorted Sets - Commands to work with sorted sets.
 * * Strings - Commands to work with Strings.
 * * Transactions - Commands to handle transaction lifecycle.
 *
 * == Connecting to Redis
 *
 * In order to connect to Redis there is a config required. This config is provided in the form of {@link io.vertx.core.json.JsonObject}
 * containing the following values:
 *
 * * `host`: default is `localhost`
 * * `port`: default is `6379`
 * * `encoding`: default is `UTF-8`
 *
 * An connection example can then be:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example1}
 * ----
 *
 * == Running commands
 *
 * Given that the redis client is connected to the server, all commands are now possible to execute using this module.
 * The module offers a clean API for executing commands without the need to hand write the command itself, for example
 * if one wants to get a value of a key it can be done as:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example2}
 * ----
 *
 * In order to know more about the commands available you should look at: <a href="http://redis.io/commands">redis documentation</a>.
 *
 * == Pub/Sub mode
 *
 * Redis supports queues and pub/sub mode, when operated in this mode once a connection invokes a subscriber mode then
 * it cannot be used for running other commands than the command to leave that mode.
 *
 * To start a subscriber one would do:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example3}
 * ----
 *
 * And from another place in the code publish messages to the queue:
 *
 * [source,$lang]
 * ----
 * {@link examples.Examples#example4}
 * ----
 *
 * == Friendlier hash commands
 *
 * Most Redis commands take a single String or an Array of Strings as arguments, and replies are sent back as a single
 * String or an Array of Strings. When dealing with hash values, there are a couple of useful exceptions to this.
 *
 * === Command hgetall
 *
 * The reply from an hgetall command will be converted into a JSON Object. That way you can interact with the responses
 * using JSON syntax which is handy for the EventBus communication.
 *
 * === command mset
 *
 * Multiple values in a hash can be set by supplying an object. Note however that key and value will be coerced to
 * strings.
 *
 * <pre>
 * {
 *   keyName: "value",
 *   otherKeyName: "other value"
 * }
 * </pre>
 *
 * === command msetnx
 *
 * Multiple values in a hash can be set by supplying an object. Note however that key and value will be coerced to
 * strings.
 *
 * <pre>
 * {
 *   keyName: "value",
 *   otherKeyName: "other value"
 * }
 * </pre>
 *
 * === command hmset
 *
 * Multiple values in a hash can be set by supplying an object. Note however that key and value will be coerced to
 * strings.
 *
 * <pre>
 * {
 *   keyName: "value",
 *   otherKeyName: "other value"
 * }
 * </pre>
 *
 * === command zadd
 * Multiple values in a hash can be set by supplying an object. Note however that key and value will be coerced to
 * strings.
 *
 * <pre>
 * {
 *   score: "member",
 *   otherScore: "other member"
 * }
 * </pre>
 *
 * == Server Info
 *
 * In order to make it easier to work with the info response you don't need to parse the data yourself and the module
 * will return it in a easy to understand JSON format. The format is as follows: A JSON object for each section filled
 * with properties that belong to that section. If for some reason there is no section the properties will be visible
 * at the top level object.
 *
 * <pre>
 * {
 *   server: {
 *     redis_version: "2.5.13",
 *     redis_git_sha1: "2812b945",
 *     redis_git_dirty: "0",
 *     os: "Linux 2.6.32.16-linode28 i686",
 *     arch_bits: "32",
 *     multiplexing_api: "epoll",
 *     gcc_version: "4.4.1",
 *     process_id: "8107",
 *     ...
 *   },
 *   memory: {...},
 *   client: {...},
 *   ...
 * }
 * </pre>
 *
 */
@GenModule(name = "vertx-redis")
@Document(fileName = "index.adoc")
package io.vertx.redis;

import io.vertx.codegen.annotations.GenModule;
import io.vertx.docgen.Document;