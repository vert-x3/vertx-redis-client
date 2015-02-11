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
      j_redisService.start(function(ar) {
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
      j_redisService.stop(function(ar) {
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
   */
  this.append = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.append(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Authenticate to the server

   @public
   @param args {todo} JsonArray [{"name":"password","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.auth = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.auth(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Asynchronously rewrite the append-only file

   @public
   @param handler {function} 
   */
  this.bgrewriteaof = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.bgrewriteaof(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Asynchronously save the dataset to disk

   @public
   @param handler {function} 
   */
  this.bgsave = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.bgsave(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Count set bits in a string

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["start","end"],"type":["integer","integer"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.bitcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.bitcount(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Perform bitwise operations between strings

   @public
   @param args {todo} JsonArray [{"name":"operation","type":"string"},{"name":"destkey","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.bitop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.bitop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Find first bit set or clear in a string

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"bit","type":"integer"},{"name":"start","type":"integer","optional":true},{"name":"end","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.bitpos = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.bitpos(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove and get the first element in a list, or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.blpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.blpop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove and get the last element in a list, or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.brpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.brpop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Pop a value from a list, push it to another list and return it; or block until one is available

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.brpoplpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.brpoplpush(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Kill the connection of a client

   @public
   @param args {todo} JsonArray [{"name":"ip:port","type":"string","optional":true},{"command":"ID","name":"client-id","type":"integer","optional":true},{"command":"TYPE","type":"enum","enum":["normal","slave","pubsub"],"optional":true},{"command":"ADDR","name":"ip:port","type":"string","optional":true},{"command":"SKIPME","name":"yes/no","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.clientKill = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.clientKill(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the list of client connections

   @public
   @param handler {function} 
   */
  this.clientList = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.clientList(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the current connection name

   @public
   @param handler {function} 
   */
  this.clientGetname = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.clientGetname(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Stop processing commands from clients for some time

   @public
   @param args {todo} JsonArray [{"name":"timeout","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.clientPause = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.clientPause(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the current connection name

   @public
   @param args {todo} JsonArray [{"name":"connection-name","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.clientSetname = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.clientSetname(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get array of Cluster slot to node mappings

   @public
   @param handler {function} 
   */
  this.clusterSlots = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.clusterSlots(function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get array of Redis command details

   @public
   @param handler {function} 
   */
  this.command = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.command(function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get total number of Redis commands

   @public
   @param handler {function} 
   */
  this.commandCount = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.commandCount(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Extract keys given a full Redis command

   @public
   @param handler {function} 
   */
  this.commandGetkeys = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.commandGetkeys(function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get array of specific Redis command details

   @public
   @param args {todo} JsonArray [{"name":"command-name","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.commandInfo = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.commandInfo(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the value of a configuration parameter

   @public
   @param args {todo} JsonArray [{"name":"parameter","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.configGet = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.configGet(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Rewrite the configuration file with the in memory configuration

   @public
   @param handler {function} 
   */
  this.configRewrite = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.configRewrite(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set a configuration parameter to the given value

   @public
   @param args {todo} JsonArray [{"name":"parameter","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.configSet = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.configSet(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Reset the stats returned by INFO

   @public
   @param handler {function} 
   */
  this.configResetstat = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.configResetstat(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return the number of keys in the selected database

   @public
   @param handler {function} 
   */
  this.dbsize = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.dbsize(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get debugging information about a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.debugObject = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.debugObject(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Make the server crash

   @public
   @param handler {function} 
   */
  this.debugSegfault = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.debugSegfault(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Decrement the integer value of a key by one

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.decr = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.decr(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Decrement the integer value of a key by the given number

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"decrement","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.decrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.decrby(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Delete a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.del = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.del(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Discard all commands issued after MULTI

   @public
   @param handler {function} 
   */
  this.discard = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.discard(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a serialized version of the value stored at the specified key.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.dump = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.dump(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Echo the given string

   @public
   @param args {todo} JsonArray [{"name":"message","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.echo = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.echo(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Execute a Lua script server side

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.eval = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.eval(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Execute a Lua script server side

   @public
   @param args {todo} JsonArray [{"name":"sha1","type":"string"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"name":"arg","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.evalsha = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.evalsha(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Execute all commands issued after MULTI

   @public
   @param handler {function} 
   */
  this.exec = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.exec(function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine if a key exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.exists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.exists(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set a key's time to live in seconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.expire = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.expire(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the expiration for a key as a UNIX timestamp

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"timestamp","type":"posix time"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.expireat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.expireat(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all keys from all databases

   @public
   @param handler {function} 
   */
  this.flushall = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.flushall(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all keys from the current database

   @public
   @param handler {function} 
   */
  this.flushdb = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.flushdb(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the value of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.get = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.get(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Returns the bit value at offset in the string value stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.getbit = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.getbit(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get a substring of the string stored at a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"end","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.getrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.getrange(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a key and return its old value

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.getset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.getset(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Delete one or more hash fields

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hdel = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hdel(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine if a hash field exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hexists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hexists(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the value of a hash field

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hget(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get all the fields and values in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hgetall = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hgetall(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a hash field by the given number

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hincrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hincrby(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the float value of a hash field by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"increment","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hincrbyfloat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hincrbyfloat(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get all the fields in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hkeys = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hkeys(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the number of fields in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hlen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hlen(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the values of all the given hash fields

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hmget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hmget(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set multiple hash fields to multiple values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["field","value"],"type":["string","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hmset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hmset(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a hash field

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hset(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the value of a hash field, only if the field does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"field","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hsetnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hsetnx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get all the values in a hash

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hvals = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hvals(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a key by one

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.incr = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.incr(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the integer value of a key by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.incrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.incrby(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the float value of a key by the given amount

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.incrbyfloat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.incrbyfloat(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get information and statistics about the server

   @public
   @param args {todo} JsonArray [{"name":"section","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.info = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.info(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Find all keys matching the given pattern

   @public
   @param args {todo} JsonArray [{"name":"pattern","type":"pattern"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.keys = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.keys(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the UNIX time stamp of the last successful save to disk

   @public
   @param handler {function} 
   */
  this.lastsave = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.lastsave(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get an element from a list by its index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lindex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lindex(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Insert an element before or after another element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"where","type":"enum","enum":["BEFORE","AFTER"]},{"name":"pivot","type":"string"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.linsert = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.linsert(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the length of a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.llen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.llen(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove and get the first element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lpop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Prepend one or multiple values to a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lpush(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Prepend a value to a list, only if the list exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lpushx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lpushx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get a range of elements from a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lrange(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove elements from a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lrem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lrem(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the value of an element in a list by its index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"index","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.lset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.lset(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Trim a list to the specified range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.ltrim = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.ltrim(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the values of all the given keys

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.mget = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.mget(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Atomically transfer a key from a Redis instance to another one.

   @public
   @param args {todo} JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"},{"name":"key","type":"key"},{"name":"destination-db","type":"integer"},{"name":"timeout","type":"integer"},{"name":"copy","type":"enum","enum":["COPY"],"optional":true},{"name":"replace","type":"enum","enum":["REPLACE"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.migrate = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.migrate(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Listen for all requests received by the server in real time

   @public
   @param handler {function} 
   */
  this.monitor = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.monitor(function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Move a key to another database

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"db","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.move = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.move(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set multiple keys to multiple values

   @public
   @param args {todo} JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.mset = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.mset(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set multiple keys to multiple values, only if none of the keys exist

   @public
   @param args {todo} JsonArray [{"name":["key","value"],"type":["key","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.msetnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.msetnx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Mark the start of a transaction block

   @public
   @param handler {function} 
   */
  this.multi = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.multi(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Inspect the internals of Redis objects

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"arguments","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.object = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.object(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove the expiration from a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.persist = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.persist(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set a key's time to live in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pexpire = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pexpire(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the expiration for a key as a UNIX timestamp specified in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds-timestamp","type":"posix time"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pexpireat = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pexpireat(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Adds the specified elements to the specified HyperLogLog.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"element","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pfadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pfadd(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pfcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pfcount(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Merge N different HyperLogLogs into a single one.

   @public
   @param args {todo} JsonArray [{"name":"destkey","type":"key"},{"name":"sourcekey","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pfmerge = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pfmerge(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Ping the server

   @public
   @param handler {function} 
   */
  this.ping = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.ping(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the value and expiration in milliseconds of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"milliseconds","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.psetex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.psetex(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Listen for messages published to channels matching the given patterns

   @public
   @param args {todo} JsonArray [{"name":["pattern"],"type":["pattern"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.psubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.psubscribe(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Inspect the state of the Pub/Sub subsystem

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pubsub = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pubsub(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the time to live for a key in milliseconds

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.pttl = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.pttl(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Post a message to a channel

   @public
   @param args {todo} JsonArray [{"name":"channel","type":"string"},{"name":"message","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.publish = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.publish(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Stop listening for messages posted to channels matching the given patterns

   @public
   @param args {todo} JsonArray [{"name":"pattern","type":"pattern","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.punsubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.punsubscribe(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Close the connection

   @public
   @param handler {function} 
   */
  this.quit = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.quit(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a random key from the keyspace

   @public
   @param handler {function} 
   */
  this.randomkey = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.randomkey(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Rename a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.rename = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.rename(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Rename a key, only if the new key does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"newkey","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.renamenx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.renamenx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Create a key using the provided serialized value, previously obtained using DUMP.

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"ttl","type":"integer"},{"name":"serialized-value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.restore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.restore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return the role of the instance in the context of replication

   @public
   @param handler {function} 
   */
  this.role = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.role(function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove and get the last element in a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.rpop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.rpop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove the last element in a list, append it to another list and return it

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.rpoplpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.rpoplpush(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Append one or multiple values to a list

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.rpush = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.rpush(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Append a value to a list, only if the list exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.rpushx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.rpushx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Add one or more members to a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sadd(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Synchronously save the dataset to disk

   @public
   @param handler {function} 
   */
  this.save = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.save(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the number of members in a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.scard = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.scard(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Check existence of scripts in the script cache.

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.scriptExists = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.scriptExists(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all the scripts from the script cache.

   @public
   @param handler {function} 
   */
  this.scriptFlush = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.scriptFlush(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Kill the script currently in execution.

   @public
   @param handler {function} 
   */
  this.scriptKill = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.scriptKill(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Load the specified Lua script into the script cache.

   @public
   @param args {todo} JsonArray [{"name":"script","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.scriptLoad = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.scriptLoad(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Subtract multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sdiff = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sdiff(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Subtract multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sdiffstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sdiffstore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Change the selected database for the current connection

   @public
   @param args {todo} JsonArray [{"name":"index","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.select = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.select(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the string value of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"},{"command":"EX","name":"seconds","type":"integer","optional":true},{"command":"PX","name":"milliseconds","type":"integer","optional":true},{"name":"condition","type":"enum","enum":["NX","XX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.set = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.set(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Sets or clears the bit at offset in the string value stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.setbit = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.setbit(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the value and expiration of a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"seconds","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.setex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.setex(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Set the value of a key, only if the key does not exist

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.setnx = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.setnx(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Overwrite part of a string at key starting at the specified offset

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"offset","type":"integer"},{"name":"value","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.setrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.setrange(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Synchronously save the dataset to disk and then shut down the server

   @public
   @param args {todo} JsonArray [{"name":"NOSAVE","type":"enum","enum":["NOSAVE"],"optional":true},{"name":"SAVE","type":"enum","enum":["SAVE"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.shutdown = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.shutdown(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sinter = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sinter(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sinterstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sinterstore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine if a given value is a member of a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sismember = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sismember(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Make the server a slave of another instance, or promote it as master

   @public
   @param args {todo} JsonArray [{"name":"host","type":"string"},{"name":"port","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.slaveof = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.slaveof(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Manages the Redis slow queries log

   @public
   @param args {todo} JsonArray [{"name":"subcommand","type":"string"},{"name":"argument","type":"string","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.slowlog = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.slowlog(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get all the members in a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.smembers = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.smembers(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Move a member from one set to another

   @public
   @param args {todo} JsonArray [{"name":"source","type":"key"},{"name":"destination","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.smove = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.smove(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Sort the elements in a list, set or sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"command":"BY","name":"pattern","type":"pattern","optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true},{"command":"GET","name":"pattern","type":"string","optional":true,"multiple":true},{"name":"order","type":"enum","enum":["ASC","DESC"],"optional":true},{"name":"sorting","type":"enum","enum":["ALPHA"],"optional":true},{"command":"STORE","name":"destination","type":"key","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sort = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sort(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove and return a random member from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.spop = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.spop(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get one or multiple random members from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.srandmember = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.srandmember(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove one or more members from a set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.srem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.srem(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the length of the value stored in a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.strlen = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.strlen(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Listen for messages published to the given channels

   @public
   @param args {todo} JsonArray [{"name":["channel"],"type":["string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.subscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.subscribe(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Add multiple sets

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sunion = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sunion(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Add multiple sets and store the resulting set in a key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sunionstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sunionstore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Internal command used for replication

   @public
   @param handler {function} 
   */
  this.sync = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.sync(function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return the current server time

   @public
   @param handler {function} 
   */
  this.time = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.time(function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the time to live for a key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.ttl = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.ttl(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine the type stored at key

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.type = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.type(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Stop listening for messages posted to the given channels

   @public
   @param args {todo} JsonArray [{"name":"channel","type":"string","optional":true,"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.unsubscribe = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.unsubscribe(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Forget about all watched keys

   @public
   @param handler {function} 
   */
  this.unwatch = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_redisService.unwatch(function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Watch the given keys to determine execution of the MULTI/EXEC block

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.watch = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.watch(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Add one or more members to a sorted set, or update its score if it already exists

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":["score","member"],"type":["double","string"],"multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zadd = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zadd(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the number of members in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zcard = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zcard(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Count the members in a sorted set with scores within the given values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zcount(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Increment the score of a member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"increment","type":"integer"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zincrby = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zincrby(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Intersect multiple sorted sets and store the resulting sorted set in a new key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zinterstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zinterstore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Count the number of members in a sorted set between a given lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zlexcount = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zlexcount(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by index

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrange(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrangebylex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrangebylex(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by score

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrangebyscore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine the index of a member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrank(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove one or more members from a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string","multiple":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrem = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrem(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set between the given lexicographical range

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"string"},{"name":"max","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zremrangebylex = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zremrangebylex(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set within the given indexes

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zremrangebyrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zremrangebyrank(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Remove all members in a sorted set within the given scores

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"min","type":"double"},{"name":"max","type":"double"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zremrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zremrangebyscore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by index, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"start","type":"integer"},{"name":"stop","type":"integer"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrevrange = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrevrange(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Return a range of members in a sorted set, by score, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"max","type":"double"},{"name":"min","type":"double"},{"name":"withscores","type":"enum","enum":["WITHSCORES"],"optional":true},{"command":"LIMIT","name":["offset","count"],"type":["integer","integer"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrevrangebyscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrevrangebyscore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(utils.convReturnJson(ar.result()), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Determine the index of a member in a sorted set, with scores ordered from high to low

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zrevrank = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zrevrank(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Get the score associated with the given member in a sorted set

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"member","type":"string"}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zscore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zscore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Add multiple sorted sets and store the resulting sorted set in a new key

   @public
   @param args {todo} JsonArray [{"name":"destination","type":"key"},{"name":"numkeys","type":"integer"},{"name":"key","type":"key","multiple":true},{"command":"WEIGHTS","name":"weight","type":"integer","variadic":true,"optional":true},{"command":"AGGREGATE","name":"aggregate","type":"enum","enum":["SUM","MIN","MAX"],"optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zunionstore = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zunionstore(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(ar.result(), null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate the keys space

   @public
   @param args {todo} JsonArray [{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.scan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.scan(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate Set elements

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.sscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.sscan(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate hash fields and associated values

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.hscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.hscan(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**
   Incrementally iterate sorted sets elements and associated scores

   @public
   @param args {todo} JsonArray [{"name":"key","type":"key"},{"name":"cursor","type":"integer"},{"command":"MATCH","name":"pattern","type":"pattern","optional":true},{"command":"COUNT","name":"count","type":"integer","optional":true}] 
   @param handler {function} Handler for the result of this call. 
   */
  this.zscan = function(args, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'function') {
      j_redisService.zscan(utils.convParamJsonArray(args), function(ar) {
      if (ar.succeeded()) {
        handler(null, null);
      } else {
        handler(null, ar.cause());
      }
    });
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
    return new RedisService(JRedisService.create(vertx._jdel, utils.convParamJsonObject(config)));
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
    return new RedisService(JRedisService.createEventBusProxy(vertx._jdel, address));
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = RedisService;