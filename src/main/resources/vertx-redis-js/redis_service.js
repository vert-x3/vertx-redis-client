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

/** @module vertx-redis-js/redis_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JRedisService = io.vertx.redis.RedisService;

/**
 @class
*/
var RedisService = function(j_val) {

  var j_redisService = j_val;
  var that = this;

  /**

   @public
   @param handler {function} 
   */
  this.start = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["start(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**

   @public
   @param handler {function} 
   */
  this.stop = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["stop(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Append a value to a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.append = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["append(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Authenticate to the server

   @public
   @param args {todo} JsonArray [{"name":"password","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.auth = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["auth(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Asynchronously rewrite the append-only file

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.bgrewriteaof = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["bgrewriteaof(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Asynchronously save the dataset to disk

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.bgsave = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["bgsave(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Count set bits in a string

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["start","end"],"type":["integer","integer"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.bitcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["bitcount(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Perform bitwise operations between strings

   @public
   @param args {todo} JsonArray [{"name":"operation","type":"string"},{"name":"destkey","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.bitop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["bitop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Find first bit set or clear in a string

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"bit","type":"integer"},{"name":"start","type":"integer","optional":true},{"name":"end","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.bitpos = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["bitpos(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove and get the first element in a list, or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.blpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["blpop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove and get the last element in a list, or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.brpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["brpop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Pop a value from a list, push it to another list and return it; or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.brpoplpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["brpoplpush(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Kill the connection of a client

   @public
   @param args {todo} JsonArray [{"name":"ip:port","type":"string","optional":true},{"command":"ID","name":"client-id","type":"integer","optional":true},{"command":"TYPE","type":"enum","enum":["normal","slave","pubsub"],"optional":true},{"command":"ADDR","name":"ip:port","type":"string","optional":true},{"command":"SKIPME","name":"yes/no","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.clientKill = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["clientKill(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the list of client connections

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.clientList = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["clientList(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the current connection name

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.clientGetname = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["clientGetname(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Stop processing commands from clients for some time

   @public
   @param args {todo} JsonArray [{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.clientPause = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["clientPause(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the current connection name

   @public
   @param args {todo} JsonArray [{"name":"connection-name","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.clientSetname = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["clientSetname(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get array of Cluster slot to node mappings

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.clusterSlots = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["clusterSlots(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get array of Redis command details

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.command = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["command(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get total number of Redis commands

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.commandCount = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["commandCount(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Extract keys given a full Redis command

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.commandGetkeys = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["commandGetkeys(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get array of specific Redis command details

   @public
   @param args {todo} JsonArray [{"name":"command-name","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.commandInfo = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["commandInfo(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the value of a configuration parameter

   @public
   @param args {todo} JsonArray [{"name":"parameter","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.configGet = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["configGet(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Rewrite the configuration file with the in memory configuration

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.configRewrite = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["configRewrite(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set a configuration parameter to the given value

   @public
   @param args {todo} JsonArray [{"name":"parameter","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.configSet = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["configSet(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Reset the stats returned by INFO

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.configResetstat = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["configResetstat(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return the number of keys in the selected database

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.dbsize = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["dbsize(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get debugging information about a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.debugObject = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["debugObject(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Make the server crash

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.debugSegfault = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["debugSegfault(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Decrement the integer value of a key by one

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.decr = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["decr(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Decrement the integer value of a key by the given number

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"decrement","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.decrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["decrby(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Delete a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.del = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["del(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Discard all commands issued after MULTI

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.discard = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["discard(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a serialized version of the value stored at the specified key.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.dump = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["dump(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Echo the given string

   @public
   @param args {todo} JsonArray [{"name":"message","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.echo = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["echo(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Execute a Lua script server side

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.eval = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["eval(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Execute a Lua script server side

   @public
   @param args {todo} JsonArray [{"name":"sha1","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.evalsha = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["evalsha(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Execute all commands issued after MULTI

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.exec = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["exec(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine if a key exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.exists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["exists(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set a key's time to live in seconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.expire = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["expire(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the expiration for a key as a UNIX timestamp

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"timestamp","type":"posix time"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.expireat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["expireat(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all keys from all databases

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.flushall = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["flushall(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all keys from the current database

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.flushdb = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["flushdb(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the value of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.get = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["get(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Returns the bit value at offset in the string value stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.getbit = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["getbit(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get a substring of the string stored at a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"end","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.getrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["getrange(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a key and return its old value

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.getset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["getset(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Delete one or more hash fields

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hdel = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hdel(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine if a hash field exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hexists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hexists(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the value of a hash field

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hget(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get all the fields and values in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hgetall = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hgetall(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a hash field by the given number

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hincrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hincrby(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the float value of a hash field by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hincrbyfloat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hincrbyfloat(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get all the fields in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hkeys = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hkeys(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the number of fields in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hlen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hlen(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the values of all the given hash fields

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hmget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hmget(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set multiple hash fields to multiple values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["field","value"],"type":["string","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hmset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hmset(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a hash field

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hset(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the value of a hash field, only if the field does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hsetnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hsetnx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get all the values in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hvals = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hvals(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a key by one

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.incr = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["incr(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a key by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.incrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["incrby(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the float value of a key by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.incrbyfloat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["incrbyfloat(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get information and statistics about the server

   @public
   @param args {todo} JsonArray [{"name":"section","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.info = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["info(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Find all keys matching the given pattern

   @public
   @param args {todo} JsonArray [{"name":"pattern","type":"pattern"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.keys = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["keys(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the UNIX time stamp of the last successful save to disk

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.lastsave = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["lastsave(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get an element from a list by its index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lindex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lindex(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Insert an element before or after another element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"where","type":"enum","enum":["BEFORE","AFTER"]},{"name":"pivot","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.linsert = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["linsert(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the length of a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.llen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["llen(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove and get the first element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lpop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Prepend one or multiple values to a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lpush(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Prepend a value to a list, only if the list exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lpushx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lpushx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get a range of elements from a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lrange(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove elements from a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lrem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lrem(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the value of an element in a list by its index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.lset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["lset(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Trim a list to the specified range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.ltrim = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["ltrim(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the values of all the given keys

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.mget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["mget(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Atomically transfer a key from a Redis instance to another one.

   @public
   @param args {todo} JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"},{"name":"key","type":"key"},{"name":"destination-db","type":"integer"},{"name":"timeout","type":"integer"},{"name":"copy","type":"enum","enum":["COPY"],"optional":true},{"name":"replace","type":"enum","enum":["REPLACE"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.migrate = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["migrate(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Listen for all requests received by the server in real time

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.monitor = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["monitor(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Move a key to another database

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"db","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.move = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["move(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set multiple keys to multiple values

   @public
   @param args {todo} JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.mset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["mset(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set multiple keys to multiple values, only if none of the keys exist

   @public
   @param args {todo} JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.msetnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["msetnx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Mark the start of a transaction block

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.multi = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["multi(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Inspect the internals of Redis objects

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"arguments","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.object = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["object(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove the expiration from a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.persist = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["persist(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set a key's time to live in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pexpire = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pexpire(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the expiration for a key as a UNIX timestamp specified in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds-timestamp","type":"posix time"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pexpireat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pexpireat(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Adds the specified elements to the specified HyperLogLog.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"element","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pfadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pfadd(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pfcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pfcount(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Merge N different HyperLogLogs into a single one.

   @public
   @param args {todo} JsonArray [{"name":"destkey","type":"key"},{"name":"sourcekey","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pfmerge = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pfmerge(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Ping the server

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.ping = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["ping(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the value and expiration in milliseconds of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.psetex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["psetex(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Listen for messages published to channels matching the given patterns

   @public
   @param args {todo} JsonArray [{"name":["pattern"],"type":["pattern"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.psubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["psubscribe(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Inspect the state of the Pub/Sub subsystem

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pubsub = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pubsub(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the time to live for a key in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.pttl = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["pttl(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Post a message to a channel

   @public
   @param args {todo} JsonArray [{"name":"channel","type":"string"},{"name":"message","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.publish = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["publish(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Stop listening for messages posted to channels matching the given patterns

   @public
   @param args {todo} JsonArray [{"name":"pattern","type":"pattern","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.punsubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["punsubscribe(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Close the connection

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.quit = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["quit(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a random key from the keyspace

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.randomkey = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["randomkey(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Rename a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.rename = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["rename(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Rename a key, only if the new key does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.renamenx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["renamenx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Create a key using the provided serialized value, previously obtained using DUMP.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"ttl","type":"integer"},{"name":"serialized-value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.restore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["restore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return the role of the instance in the context of replication

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.role = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["role(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove and get the last element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.rpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["rpop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove the last element in a list, append it to another list and return it

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.rpoplpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["rpoplpush(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Append one or multiple values to a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.rpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["rpush(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Append a value to a list, only if the list exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.rpushx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["rpushx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Add one or more members to a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sadd(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Synchronously save the dataset to disk

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.save = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["save(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the number of members in a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.scard = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["scard(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Check existence of scripts in the script cache.

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.scriptExists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["scriptExists(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all the scripts from the script cache.

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.scriptFlush = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["scriptFlush(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Kill the script currently in execution.

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.scriptKill = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["scriptKill(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Load the specified Lua script into the script cache.

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.scriptLoad = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["scriptLoad(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Subtract multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sdiff = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sdiff(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Subtract multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sdiffstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sdiffstore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Change the selected database for the current connection

   @public
   @param args {todo} JsonArray [{"name":"index","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.select = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["select(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"},{"command":"EX","name":"seconds","type":"integer","optional":true},{"command":"PX","name":"milliseconds","type":"integer","optional":true},{"name":"condition","type":"enum","enum":["NX","XX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.set = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["set(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Sets or clears the bit at offset in the string value stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.setbit = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["setbit(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the value and expiration of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.setex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["setex(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Set the value of a key, only if the key does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.setnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["setnx(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Overwrite part of a string at key starting at the specified offset

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.setrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["setrange(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Synchronously save the dataset to disk and then shut down the server

   @public
   @param args {todo} JsonArray [{"name":"NOSAVE","type":"enum","enum":["NOSAVE"],"optional":true},{"name":"SAVE","type":"enum","enum":["SAVE"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.shutdown = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["shutdown(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sinter = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sinter(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sinterstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sinterstore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine if a given value is a member of a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sismember = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sismember(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Make the server a slave of another instance, or promote it as master

   @public
   @param args {todo} JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.slaveof = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["slaveof(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Manages the Redis slow queries log

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.slowlog = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["slowlog(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get all the members in a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.smembers = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["smembers(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Move a member from one set to another

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.smove = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["smove(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Sort the elements in a list, set or sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"command":"BY","name":"pattern","type":"pattern","optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true},{"command":"GET","name":"pattern","type":"string","optional":true,"multiple":true},{"name":"order","type":"enum","enum":["ASC","DESC"],"optional":true},{"name":"sorting","type":"enum","enum":["ALPHA"],"optional":true},{"command":"STORE","name":"destination","type":"key","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sort = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sort(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove and return a random member from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.spop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["spop(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get one or multiple random members from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.srandmember = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["srandmember(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove one or more members from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.srem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["srem(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the length of the value stored in a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.strlen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["strlen(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Listen for messages published to the given channels

   @public
   @param args {todo} JsonArray [{"name":["channel"],"type":["string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.subscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["subscribe(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Add multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sunion = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sunion(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Add multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sunionstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sunionstore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Internal command used for replication

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.sync = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["sync(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return the current server time

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.time = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["time(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the time to live for a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.ttl = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["ttl(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine the type stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.type = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["type(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Stop listening for messages posted to the given channels

   @public
   @param args {todo} JsonArray [{"name":"channel","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.unsubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["unsubscribe(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Forget about all watched keys

   @public
   @param handler {function} 
   @return {RedisService}
   */
  this.unwatch = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService["unwatch(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Watch the given keys to determine execution of the MULTI/EXEC block

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.watch = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["watch(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Add one or more members to a sorted set, or update its score if it already exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["score","member"],"type":["double","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zadd(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the number of members in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zcard = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zcard(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Count the members in a sorted set with scores within the given values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zcount(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Increment the score of a member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zincrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zincrby(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sorted sets and store the resulting sorted set in a new key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zinterstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zinterstore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Count the number of members in a sorted set between a given lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zlexcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zlexcount(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrange(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrangebylex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrangebylex(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by score

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrangebyscore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine the index of a member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrank(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove one or more members from a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrem(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set between the given lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zremrangebylex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zremrangebylex(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set within the given indexes

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zremrangebyrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zremrangebyrank(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set within the given scores

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zremrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zremrangebyscore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by index, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrevrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrevrange(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by score, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"max","type":"double"},{"name":"min","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrevrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrevrangebyscore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Determine the index of a member in a sorted set, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zrevrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zrevrank(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Get the score associated with the given member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zscore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Add multiple sorted sets and store the resulting sorted set in a new key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zunionstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zunionstore(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate the keys space

   @public
   @param args {todo} JsonArray [{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.scan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["scan(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate Set elements

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.sscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["sscan(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate hash fields and associated values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.hscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["hscan(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate sorted sets elements and associated scores

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   @return {RedisService}
   */
  this.zscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService["zscan(io.vertx.core.json.JsonArray,io.vertx.core.Handler)"](utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
      return that;
    } else utils.invalidArgs();
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_redisService;
};

/**

 @memberof module:vertx-redis-js/redis_service
 @param vertx {Vertx} 
 @param config {Object} 
 @return {RedisService}
 */
RedisService.create = function(vertx, config) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'object') {
    return new RedisService(JRedisService["create(io.vertx.core.Vertx,io.vertx.core.json.JsonObject)"](vertx._jdel, utils.convParamJsonObject(config)));
  } else utils.invalidArgs();
};

/**

 @memberof module:vertx-redis-js/redis_service
 @param vertx {Vertx} 
 @param address {string} 
 @return {RedisService}
 */
RedisService.createEventBusProxy = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return new RedisService(JRedisService["createEventBusProxy(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address));
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = RedisService;