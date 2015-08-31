require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from io.vertx.redis.RedisClient
module VertxRedis
  class RedisClient
    # @private
    # @param j_del [::VertxRedis::RedisClient] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::VertxRedis::RedisClient] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [Hash{String => Object}] config 
    # @return [::VertxRedis::RedisClient]
    def self.create(vertx=nil,config=nil)
      if vertx.class.method_defined?(:j_del) && config.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoVertxRedis::RedisClient.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoVertxCoreJson::JsonObject.java_class]).call(vertx.j_del,::Vertx::Util::Utils.to_json_object(config)),::VertxRedis::RedisClient)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,config)"
    end
    #  Close the client - when it is fully closed the handler will be called.
    # @yield 
    # @return [void]
    def close
      if block_given?
        return @j_del.java_method(:close, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
      end
      raise ArgumentError, "Invalid arguments when calling close()"
    end
    #  Append a value to a key
    # @param [String] key Key string
    # @param [String] value Value to append
    # @yield Handler for the result of this call.
    # @return [self]
    def append(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:append, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling append(key,value)"
    end
    #  Authenticate to the server
    # @param [String] password Password for authentication
    # @yield Handler for the result of this call.
    # @return [self]
    def auth(password=nil)
      if password.class == String && block_given?
        @j_del.java_method(:auth, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(password,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling auth(password)"
    end
    #  Asynchronously rewrite the append-only file
    # @yield 
    # @return [self]
    def bgrewriteaof
      if block_given?
        @j_del.java_method(:bgrewriteaof, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bgrewriteaof()"
    end
    #  Asynchronously save the dataset to disk
    # @yield 
    # @return [self]
    def bgsave
      if block_given?
        @j_del.java_method(:bgsave, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bgsave()"
    end
    #  Count set bits in a string
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def bitcount(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:bitcount, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitcount(key)"
    end
    #  Count set bits in a string
    # @param [String] key Key string
    # @param [Fixnum] start Start index
    # @param [Fixnum] _end End index
    # @yield Handler for the result of this call.
    # @return [self]
    def bitcount_range(key=nil,start=nil,_end=nil)
      if key.class == String && start.class == Fixnum && _end.class == Fixnum && block_given?
        @j_del.java_method(:bitcountRange, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,_end,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitcount_range(key,start,_end)"
    end
    #  Perform bitwise operations between strings
    # @param [:AND,:OR,:XOR,:NOT] operation Bitwise operation to perform
    # @param [String] destkey Destination key where result is stored
    # @param [Array<String>] keys List of keys on which to perform the operation
    # @yield Handler for the result of this call.
    # @return [self]
    def bitop(operation=nil,destkey=nil,keys=nil)
      if operation.class == Symbol && destkey.class == String && keys.class == Array && block_given?
        @j_del.java_method(:bitop, [Java::IoVertxRedisOp::BitOperation.java_class,Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoVertxRedisOp::BitOperation.valueOf(operation),destkey,keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitop(operation,destkey,keys)"
    end
    #  Find first bit set or clear in a string
    # @param [String] key Key string
    # @param [Fixnum] bit What bit value to look for - must be 1, or 0
    # @yield Handler for the result of this call.
    # @return [self]
    def bitpos(key=nil,bit=nil)
      if key.class == String && bit.class == Fixnum && block_given?
        @j_del.java_method(:bitpos, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,bit,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitpos(key,bit)"
    end
    #  Find first bit set or clear in a string
    # 
    #  See also bitposRange() method, which takes start, and stop offset.
    # @param [String] key Key string
    # @param [Fixnum] bit What bit value to look for - must be 1, or 0
    # @param [Fixnum] start Start offset
    # @yield Handler for the result of this call.
    # @return [self]
    def bitpos_from(key=nil,bit=nil,start=nil)
      if key.class == String && bit.class == Fixnum && start.class == Fixnum && block_given?
        @j_del.java_method(:bitposFrom, [Java::java.lang.String.java_class,Java::int.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,bit,start,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitpos_from(key,bit,start)"
    end
    #  Find first bit set or clear in a string
    # 
    #  Note: when both start, and stop offsets are specified,
    #  behaviour is slightly different than if only start is specified
    # @param [String] key Key string
    # @param [Fixnum] bit What bit value to look for - must be 1, or 0
    # @param [Fixnum] start Start offset
    # @param [Fixnum] stop End offset - inclusive
    # @yield Handler for the result of this call.
    # @return [self]
    def bitpos_range(key=nil,bit=nil,start=nil,stop=nil)
      if key.class == String && bit.class == Fixnum && start.class == Fixnum && stop.class == Fixnum && block_given?
        @j_del.java_method(:bitposRange, [Java::java.lang.String.java_class,Java::int.java_class,Java::int.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,bit,start,stop,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling bitpos_range(key,bit,start,stop)"
    end
    #  Remove and get the first element in a list, or block until one is available
    # @param [String] key Key string identifying a list to watch
    # @param [Fixnum] seconds Timeout in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def blpop(key=nil,seconds=nil)
      if key.class == String && seconds.class == Fixnum && block_given?
        @j_del.java_method(:blpop, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling blpop(key,seconds)"
    end
    #  Remove and get the first element in any of the lists, or block until one is available
    # @param [Array<String>] keys List of key strings identifying lists to watch
    # @param [Fixnum] seconds Timeout in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def blpop_many(keys=nil,seconds=nil)
      if keys.class == Array && seconds.class == Fixnum && block_given?
        @j_del.java_method(:blpopMany, [Java::JavaUtil::List.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling blpop_many(keys,seconds)"
    end
    #  Remove and get the last element in a list, or block until one is available
    # @param [String] key Key string identifying a list to watch
    # @param [Fixnum] seconds Timeout in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def brpop(key=nil,seconds=nil)
      if key.class == String && seconds.class == Fixnum && block_given?
        @j_del.java_method(:brpop, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling brpop(key,seconds)"
    end
    #  Remove and get the last element in any of the lists, or block until one is available
    # @param [Array<String>] keys List of key strings identifying lists to watch
    # @param [Fixnum] seconds Timeout in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def brpop_many(keys=nil,seconds=nil)
      if keys.class == Array && seconds.class == Fixnum && block_given?
        @j_del.java_method(:brpopMany, [Java::JavaUtil::List.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling brpop_many(keys,seconds)"
    end
    #  Pop a value from a list, push it to another list and return it; or block until one is available
    # @param [String] key Key string identifying the source list
    # @param [String] destkey Key string identifying the destination list
    # @param [Fixnum] seconds Timeout in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def brpoplpush(key=nil,destkey=nil,seconds=nil)
      if key.class == String && destkey.class == String && seconds.class == Fixnum && block_given?
        @j_del.java_method(:brpoplpush, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,destkey,seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling brpoplpush(key,destkey,seconds)"
    end
    #  Kill the connection of a client
    # @param [Hash] filter Filter options
    # @yield Handler for the result of this call.
    # @return [self]
    def client_kill(filter=nil)
      if filter.class == Hash && block_given?
        @j_del.java_method(:clientKill, [Java::IoVertxRedisOp::KillFilter.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoVertxRedisOp::KillFilter.new(::Vertx::Util::Utils.to_json_object(filter)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling client_kill(filter)"
    end
    #  Get the list of client connections
    # @yield 
    # @return [self]
    def client_list
      if block_given?
        @j_del.java_method(:clientList, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling client_list()"
    end
    #  Get the current connection name
    # @yield 
    # @return [self]
    def client_getname
      if block_given?
        @j_del.java_method(:clientGetname, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling client_getname()"
    end
    #  Stop processing commands from clients for some time
    # @param [Fixnum] millis Pause time in milliseconds
    # @yield Handler for the result of this call.
    # @return [self]
    def client_pause(millis=nil)
      if millis.class == Fixnum && block_given?
        @j_del.java_method(:clientPause, [Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(millis,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling client_pause(millis)"
    end
    #  Set the current connection name
    # @param [String] name New name for current connection
    # @yield Handler for the result of this call.
    # @return [self]
    def client_setname(name=nil)
      if name.class == String && block_given?
        @j_del.java_method(:clientSetname, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(name,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling client_setname(name)"
    end
    #  Assign new hash slots to receiving node.
    # @param [Array<Fixnum>] slots 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_addslots(slots=nil)
      if slots.class == Array && block_given?
        @j_del.java_method(:clusterAddslots, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(slots.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_addslots(slots)"
    end
    #  Return the number of failure reports active for a given node.
    # @param [String] nodeId 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_count_failure_reports(nodeId=nil)
      if nodeId.class == String && block_given?
        @j_del.java_method(:clusterCountFailureReports, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(nodeId,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_count_failure_reports(nodeId)"
    end
    #  Return the number of local keys in the specified hash slot.
    # @param [Fixnum] slot 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_countkeysinslot(slot=nil)
      if slot.class == Fixnum && block_given?
        @j_del.java_method(:clusterCountkeysinslot, [Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(slot,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_countkeysinslot(slot)"
    end
    #  Set hash slots as unbound in receiving node.
    # @param [Fixnum] slot 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_delslots(slot=nil)
      if slot.class == Fixnum && block_given?
        @j_del.java_method(:clusterDelslots, [Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(slot,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_delslots(slot)"
    end
    #  Set hash slots as unbound in receiving node.
    # @param [Array<Fixnum>] slots 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_delslots_many(slots=nil)
      if slots.class == Array && block_given?
        @j_del.java_method(:clusterDelslotsMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(slots.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_delslots_many(slots)"
    end
    #  Forces a slave to perform a manual failover of its master.
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_failover
      if block_given?
        @j_del.java_method(:clusterFailover, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_failover()"
    end
    #  Forces a slave to perform a manual failover of its master.
    # @param [:FORCE,:TAKEOVER] options 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_fail_over_with_options(options=nil)
      if options.class == Symbol && block_given?
        @j_del.java_method(:clusterFailOverWithOptions, [Java::IoVertxRedisOp::FailoverOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoVertxRedisOp::FailoverOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_fail_over_with_options(options)"
    end
    #  Remove a node from the nodes table.
    # @param [String] nodeId 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_forget(nodeId=nil)
      if nodeId.class == String && block_given?
        @j_del.java_method(:clusterForget, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(nodeId,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_forget(nodeId)"
    end
    #  Return local key names in the specified hash slot.
    # @param [Fixnum] slot 
    # @param [Fixnum] count 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_getkeysinslot(slot=nil,count=nil)
      if slot.class == Fixnum && count.class == Fixnum && block_given?
        @j_del.java_method(:clusterGetkeysinslot, [Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(slot,count,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_getkeysinslot(slot,count)"
    end
    #  Provides info about Redis Cluster node state.
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_info
      if block_given?
        @j_del.java_method(:clusterInfo, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_info()"
    end
    #  Returns the hash slot of the specified key.
    # @param [String] key 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_keyslot(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:clusterKeyslot, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_keyslot(key)"
    end
    #  Force a node cluster to handshake with another node.
    # @param [String] ip 
    # @param [Fixnum] port 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_meet(ip=nil,port=nil)
      if ip.class == String && port.class == Fixnum && block_given?
        @j_del.java_method(:clusterMeet, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(ip,port,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_meet(ip,port)"
    end
    #  Get Cluster config for the node.
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_nodes
      if block_given?
        @j_del.java_method(:clusterNodes, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_nodes()"
    end
    #  Reconfigure a node as a slave of the specified master node.
    # @param [String] nodeId 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_replicate(nodeId=nil)
      if nodeId.class == String && block_given?
        @j_del.java_method(:clusterReplicate, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(nodeId,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_replicate(nodeId)"
    end
    #  Reset a Redis Cluster node.
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_reset
      if block_given?
        @j_del.java_method(:clusterReset, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_reset()"
    end
    #  Reset a Redis Cluster node.
    # @param [:HARD,:SOFT] options 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_reset_with_options(options=nil)
      if options.class == Symbol && block_given?
        @j_del.java_method(:clusterResetWithOptions, [Java::IoVertxRedisOp::ResetOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(Java::IoVertxRedisOp::ResetOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_reset_with_options(options)"
    end
    #  Forces the node to save cluster state on disk.
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_saveconfig
      if block_given?
        @j_del.java_method(:clusterSaveconfig, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_saveconfig()"
    end
    #  Set the configuration epoch in a new node.
    # @param [Fixnum] epoch 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_set_config_epoch(epoch=nil)
      if epoch.class == Fixnum && block_given?
        @j_del.java_method(:clusterSetConfigEpoch, [Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(epoch,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_set_config_epoch(epoch)"
    end
    #  Bind an hash slot to a specific node.
    # @param [Fixnum] slot 
    # @param [:IMPORTING,:MIGRATING,:STABLE,:NODE] subcommand 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_setslot(slot=nil,subcommand=nil)
      if slot.class == Fixnum && subcommand.class == Symbol && block_given?
        @j_del.java_method(:clusterSetslot, [Java::long.java_class,Java::IoVertxRedisOp::SlotCmd.java_class,Java::IoVertxCore::Handler.java_class]).call(slot,Java::IoVertxRedisOp::SlotCmd.valueOf(subcommand),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_setslot(slot,subcommand)"
    end
    #  Bind an hash slot to a specific node.
    # @param [Fixnum] slot 
    # @param [:IMPORTING,:MIGRATING,:STABLE,:NODE] subcommand 
    # @param [String] nodeId 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_setslot_with_node(slot=nil,subcommand=nil,nodeId=nil)
      if slot.class == Fixnum && subcommand.class == Symbol && nodeId.class == String && block_given?
        @j_del.java_method(:clusterSetslotWithNode, [Java::long.java_class,Java::IoVertxRedisOp::SlotCmd.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(slot,Java::IoVertxRedisOp::SlotCmd.valueOf(subcommand),nodeId,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_setslot_with_node(slot,subcommand,nodeId)"
    end
    #  List slave nodes of the specified master node.
    # @param [String] nodeId 
    # @yield Handler for the result of this call.
    # @return [self]
    def cluster_slaves(nodeId=nil)
      if nodeId.class == String && block_given?
        @j_del.java_method(:clusterSlaves, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(nodeId,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_slaves(nodeId)"
    end
    #  Get array of Cluster slot to node mappings
    # @yield 
    # @return [self]
    def cluster_slots
      if block_given?
        @j_del.java_method(:clusterSlots, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling cluster_slots()"
    end
    #  Get array of Redis command details
    # @yield 
    # @return [self]
    def command
      if block_given?
        @j_del.java_method(:command, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling command()"
    end
    #  Get total number of Redis commands
    # @yield 
    # @return [self]
    def command_count
      if block_given?
        @j_del.java_method(:commandCount, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling command_count()"
    end
    #  Extract keys given a full Redis command
    # @yield 
    # @return [self]
    def command_getkeys
      if block_given?
        @j_del.java_method(:commandGetkeys, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling command_getkeys()"
    end
    #  Get array of specific Redis command details
    # @param [Array<String>] commands List of commands to get info for
    # @yield Handler for the result of this call.
    # @return [self]
    def command_info(commands=nil)
      if commands.class == Array && block_given?
        @j_del.java_method(:commandInfo, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(commands.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling command_info(commands)"
    end
    #  Get the value of a configuration parameter
    # @param [String] parameter Configuration parameter
    # @yield Handler for the result of this call.
    # @return [self]
    def config_get(parameter=nil)
      if parameter.class == String && block_given?
        @j_del.java_method(:configGet, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(parameter,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling config_get(parameter)"
    end
    #  Rewrite the configuration file with the in memory configuration
    # @yield 
    # @return [self]
    def config_rewrite
      if block_given?
        @j_del.java_method(:configRewrite, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling config_rewrite()"
    end
    #  Set a configuration parameter to the given value
    # @param [String] parameter Configuration parameter
    # @param [String] value New value
    # @yield Handler for the result of this call.
    # @return [self]
    def config_set(parameter=nil,value=nil)
      if parameter.class == String && value.class == String && block_given?
        @j_del.java_method(:configSet, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(parameter,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling config_set(parameter,value)"
    end
    #  Reset the stats returned by INFO
    # @yield 
    # @return [self]
    def config_resetstat
      if block_given?
        @j_del.java_method(:configResetstat, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling config_resetstat()"
    end
    #  Return the number of keys in the selected database
    # @yield 
    # @return [self]
    def dbsize
      if block_given?
        @j_del.java_method(:dbsize, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling dbsize()"
    end
    #  Get debugging information about a key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def debug_object(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:debugObject, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling debug_object(key)"
    end
    #  Make the server crash
    # @yield 
    # @return [self]
    def debug_segfault
      if block_given?
        @j_del.java_method(:debugSegfault, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling debug_segfault()"
    end
    #  Decrement the integer value of a key by one
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def decr(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:decr, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling decr(key)"
    end
    #  Decrement the integer value of a key by the given number
    # @param [String] key Key string
    # @param [Fixnum] decrement Value by which to decrement
    # @yield Handler for the result of this call.
    # @return [self]
    def decrby(key=nil,decrement=nil)
      if key.class == String && decrement.class == Fixnum && block_given?
        @j_del.java_method(:decrby, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,decrement,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling decrby(key,decrement)"
    end
    #  Delete a key
    # @param [Array<String>] keys List of keys to delete
    # @yield Handler for the result of this call.
    # @return [self]
    def del(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:del, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling del(keys)"
    end
    #  Discard all commands issued after MULTI
    # @yield 
    # @return [self]
    def discard
      if block_given?
        @j_del.java_method(:discard, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling discard()"
    end
    #  Return a serialized version of the value stored at the specified key.
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def dump(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:dump, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling dump(key)"
    end
    #  Echo the given string
    # @param [String] message String to echo
    # @yield Handler for the result of this call.
    # @return [self]
    def echo(message=nil)
      if message.class == String && block_given?
        @j_del.java_method(:echo, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling echo(message)"
    end
    #  Execute a Lua script server side
    # @param [String] script Lua script to evaluate
    # @param [Array<String>] keys List of keys
    # @param [Array<String>] args List of argument values
    # @yield Handler for the result of this call.
    # @return [self]
    def eval(script=nil,keys=nil,args=nil)
      if script.class == String && keys.class == Array && args.class == Array && block_given?
        @j_del.java_method(:eval, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(script,keys.map { |element| element },args.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling eval(script,keys,args)"
    end
    #  Execute a Lua script server side
    # @param [String] sha1 SHA1 digest of the script cached on the server
    # @param [Array<String>] keys List of keys
    # @param [Array<String>] values List of values
    # @yield Handler for the result of this call.
    # @return [self]
    def evalsha(sha1=nil,keys=nil,values=nil)
      if sha1.class == String && keys.class == Array && values.class == Array && block_given?
        @j_del.java_method(:evalsha, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(sha1,keys.map { |element| element },values.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling evalsha(sha1,keys,values)"
    end
    #  Execute all commands issued after MULTI
    # @yield 
    # @return [self]
    def exec
      if block_given?
        @j_del.java_method(:exec, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling exec()"
    end
    #  Determine if a key exists
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def exists(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:exists, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling exists(key)"
    end
    #  Set a key's time to live in seconds
    # @param [String] key Key string
    # @param [Fixnum] seconds Time to live in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def expire(key=nil,seconds=nil)
      if key.class == String && seconds.class == Fixnum && block_given?
        @j_del.java_method(:expire, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling expire(key,seconds)"
    end
    #  Set the expiration for a key as a UNIX timestamp
    # @param [String] key Key string
    # @param [Fixnum] seconds Expiry time as Unix timestamp in seconds
    # @yield Handler for the result of this call.
    # @return [self]
    def expireat(key=nil,seconds=nil)
      if key.class == String && seconds.class == Fixnum && block_given?
        @j_del.java_method(:expireat, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,seconds,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling expireat(key,seconds)"
    end
    #  Remove all keys from all databases
    # @yield 
    # @return [self]
    def flushall
      if block_given?
        @j_del.java_method(:flushall, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling flushall()"
    end
    #  Remove all keys from the current database
    # @yield 
    # @return [self]
    def flushdb
      if block_given?
        @j_del.java_method(:flushdb, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling flushdb()"
    end
    #  Get the value of a key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def get(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:get, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get(key)"
    end
    #  Get the value of a key - without decoding as utf-8
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def get_binary(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:getBinary, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling get_binary(key)"
    end
    #  Returns the bit value at offset in the string value stored at key
    # @param [String] key Key string
    # @param [Fixnum] offset Offset in bits
    # @yield Handler for the result of this call.
    # @return [self]
    def getbit(key=nil,offset=nil)
      if key.class == String && offset.class == Fixnum && block_given?
        @j_del.java_method(:getbit, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,offset,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling getbit(key,offset)"
    end
    #  Get a substring of the string stored at a key
    # @param [String] key Key string
    # @param [Fixnum] start Start offset
    # @param [Fixnum] _end End offset - inclusive
    # @yield Handler for the result of this call.
    # @return [self]
    def getrange(key=nil,start=nil,_end=nil)
      if key.class == String && start.class == Fixnum && _end.class == Fixnum && block_given?
        @j_del.java_method(:getrange, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,_end,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling getrange(key,start,_end)"
    end
    #  Set the string value of a key and return its old value
    # @param [String] key Key of which value to set
    # @param [String] value New value for the key
    # @yield Handler for the result of this call.
    # @return [self]
    def getset(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:getset, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling getset(key,value)"
    end
    #  Delete one or more hash fields
    # @param [String] key Key string
    # @param [String] field Field name
    # @yield Handler for the result of this call.
    # @return [self]
    def hdel(key=nil,field=nil)
      if key.class == String && field.class == String && block_given?
        @j_del.java_method(:hdel, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hdel(key,field)"
    end
    #  Delete one or more hash fields
    # @param [String] key Key string
    # @param [Array<String>] fields Field names
    # @yield Handler for the result of this call.
    # @return [self]
    def hdel_many(key=nil,fields=nil)
      if key.class == String && fields.class == Array && block_given?
        @j_del.java_method(:hdelMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,fields.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hdel_many(key,fields)"
    end
    #  Determine if a hash field exists
    # @param [String] key Key string
    # @param [String] field Field name
    # @yield Handler for the result of this call.
    # @return [self]
    def hexists(key=nil,field=nil)
      if key.class == String && field.class == String && block_given?
        @j_del.java_method(:hexists, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hexists(key,field)"
    end
    #  Get the value of a hash field
    # @param [String] key Key string
    # @param [String] field Field name
    # @yield Handler for the result of this call.
    # @return [self]
    def hget(key=nil,field=nil)
      if key.class == String && field.class == String && block_given?
        @j_del.java_method(:hget, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hget(key,field)"
    end
    #  Get all the fields and values in a hash
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def hgetall(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:hgetall, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hgetall(key)"
    end
    #  Increment the integer value of a hash field by the given number
    # @param [String] key Key string
    # @param [String] field Field name
    # @param [Fixnum] increment Value by which to increment
    # @yield Handler for the result of this call.
    # @return [self]
    def hincrby(key=nil,field=nil,increment=nil)
      if key.class == String && field.class == String && increment.class == Fixnum && block_given?
        @j_del.java_method(:hincrby, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,increment,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hincrby(key,field,increment)"
    end
    #  Increment the float value of a hash field by the given amount
    # @param [String] key Key string
    # @param [String] field Field name
    # @param [Float] increment Value by which to increment
    # @yield Handler for the result of this call.
    # @return [self]
    def hincrbyfloat(key=nil,field=nil,increment=nil)
      if key.class == String && field.class == String && increment.class == Float && block_given?
        @j_del.java_method(:hincrbyfloat, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::double.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,::Vertx::Util::Utils.to_double(increment),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hincrbyfloat(key,field,increment)"
    end
    #  Get all the fields in a hash
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def hkeys(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:hkeys, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hkeys(key)"
    end
    #  Get the number of fields in a hash
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def hlen(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:hlen, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hlen(key)"
    end
    #  Get the values of all the given hash fields
    # @param [String] key Key string
    # @param [Array<String>] fields Field names
    # @yield Handler for the result of this call.
    # @return [self]
    def hmget(key=nil,fields=nil)
      if key.class == String && fields.class == Array && block_given?
        @j_del.java_method(:hmget, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,fields.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hmget(key,fields)"
    end
    #  Set multiple hash fields to multiple values
    # @param [String] key Key string
    # @param [Hash{String => String}] values Map of field:value pairs
    # @yield Handler for the result of this call.
    # @return [self]
    def hmset(key=nil,values=nil)
      if key.class == String && values.class == Hash && block_given?
        @j_del.java_method(:hmset, [Java::java.lang.String.java_class,Java::JavaUtil::Map.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Hash[values.map { |k,v| [k,v] }],(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hmset(key,values)"
    end
    #  Set the string value of a hash field
    # @param [String] key Key string
    # @param [String] field Field name
    # @param [String] value New value
    # @yield Handler for the result of this call.
    # @return [self]
    def hset(key=nil,field=nil,value=nil)
      if key.class == String && field.class == String && value.class == String && block_given?
        @j_del.java_method(:hset, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hset(key,field,value)"
    end
    #  Set the value of a hash field, only if the field does not exist
    # @param [String] key Key string
    # @param [String] field Field name
    # @param [String] value New value
    # @yield Handler for the result of this call.
    # @return [self]
    def hsetnx(key=nil,field=nil,value=nil)
      if key.class == String && field.class == String && value.class == String && block_given?
        @j_del.java_method(:hsetnx, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,field,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hsetnx(key,field,value)"
    end
    #  Get all the values in a hash
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def hvals(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:hvals, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hvals(key)"
    end
    #  Increment the integer value of a key by one
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def incr(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:incr, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling incr(key)"
    end
    #  Increment the integer value of a key by the given amount
    # @param [String] key Key string
    # @param [Fixnum] increment Value by which to increment
    # @yield Handler for the result of this call.
    # @return [self]
    def incrby(key=nil,increment=nil)
      if key.class == String && increment.class == Fixnum && block_given?
        @j_del.java_method(:incrby, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,increment,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling incrby(key,increment)"
    end
    #  Increment the float value of a key by the given amount
    # @param [String] key Key string
    # @param [Float] increment Value by which to increment
    # @yield Handler for the result of this call.
    # @return [self]
    def incrbyfloat(key=nil,increment=nil)
      if key.class == String && increment.class == Float && block_given?
        @j_del.java_method(:incrbyfloat, [Java::java.lang.String.java_class,Java::double.java_class,Java::IoVertxCore::Handler.java_class]).call(key,::Vertx::Util::Utils.to_double(increment),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling incrbyfloat(key,increment)"
    end
    #  Get information and statistics about the server
    # @yield Handler for the result of this call.
    # @return [self]
    def info
      if block_given?
        @j_del.java_method(:info, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling info()"
    end
    #  Get information and statistics about the server
    # @param [String] section Specific section of information to return
    # @yield Handler for the result of this call.
    # @return [self]
    def info_section(section=nil)
      if section.class == String && block_given?
        @j_del.java_method(:infoSection, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(section,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling info_section(section)"
    end
    #  Find all keys matching the given pattern
    # @param [String] pattern Pattern to limit the keys returned
    # @yield Handler for the result of this call.
    # @return [self]
    def keys(pattern=nil)
      if pattern.class == String && block_given?
        @j_del.java_method(:keys, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling keys(pattern)"
    end
    #  Get the UNIX time stamp of the last successful save to disk
    # @yield 
    # @return [self]
    def lastsave
      if block_given?
        @j_del.java_method(:lastsave, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lastsave()"
    end
    #  Get an element from a list by its index
    # @param [String] key Key string
    # @param [Fixnum] index Index of list element to get
    # @yield Handler for the result of this call.
    # @return [self]
    def lindex(key=nil,index=nil)
      if key.class == String && index.class == Fixnum && block_given?
        @j_del.java_method(:lindex, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,index,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lindex(key,index)"
    end
    #  Insert an element before or after another element in a list
    # @param [String] key Key string
    # @param [:BEFORE,:AFTER] option BEFORE or AFTER
    # @param [String] pivot Key to use as a pivot
    # @param [String] value Value to be inserted before or after the pivot
    # @yield Handler for the result of this call.
    # @return [self]
    def linsert(key=nil,option=nil,pivot=nil,value=nil)
      if key.class == String && option.class == Symbol && pivot.class == String && value.class == String && block_given?
        @j_del.java_method(:linsert, [Java::java.lang.String.java_class,Java::IoVertxRedisOp::InsertOptions.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Java::IoVertxRedisOp::InsertOptions.valueOf(option),pivot,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling linsert(key,option,pivot,value)"
    end
    #  Get the length of a list
    # @param [String] key String key
    # @yield Handler for the result of this call.
    # @return [self]
    def llen(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:llen, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling llen(key)"
    end
    #  Remove and get the first element in a list
    # @param [String] key String key
    # @yield Handler for the result of this call.
    # @return [self]
    def lpop(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:lpop, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lpop(key)"
    end
    #  Prepend one or multiple values to a list
    # @param [String] key Key string
    # @param [Array<String>] values Values to be added at the beginning of the list, one by one
    # @yield Handler for the result of this call.
    # @return [self]
    def lpush_many(key=nil,values=nil)
      if key.class == String && values.class == Array && block_given?
        @j_del.java_method(:lpushMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,values.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lpush_many(key,values)"
    end
    #  Prepend one value to a list
    # @param [String] key Key string
    # @param [String] value Value to be added at the beginning of the list
    # @yield Handler for the result of this call.
    # @return [self]
    def lpush(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:lpush, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lpush(key,value)"
    end
    #  Prepend a value to a list, only if the list exists
    # @param [String] key Key string
    # @param [String] value Value to add at the beginning of the list
    # @yield Handler for the result of this call.
    # @return [self]
    def lpushx(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:lpushx, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lpushx(key,value)"
    end
    #  Get a range of elements from a list
    # @param [String] key Key string
    # @param [Fixnum] from Start index
    # @param [Fixnum] to Stop index
    # @yield Handler for the result of this call.
    # @return [self]
    def lrange(key=nil,from=nil,to=nil)
      if key.class == String && from.class == Fixnum && to.class == Fixnum && block_given?
        @j_del.java_method(:lrange, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,from,to,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lrange(key,from,to)"
    end
    #  Remove elements from a list
    # @param [String] key Key string
    # @param [Fixnum] count Number of first found occurrences equal to $value to remove from the list
    # @param [String] value Value to be removed
    # @yield Handler for the result of this call.
    # @return [self]
    def lrem(key=nil,count=nil,value=nil)
      if key.class == String && count.class == Fixnum && value.class == String && block_given?
        @j_del.java_method(:lrem, [Java::java.lang.String.java_class,Java::long.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,count,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lrem(key,count,value)"
    end
    #  Set the value of an element in a list by its index
    # @param [String] key Key string
    # @param [Fixnum] index Position within list
    # @param [String] value New value
    # @yield Handler for the result of this call.
    # @return [self]
    def lset(key=nil,index=nil,value=nil)
      if key.class == String && index.class == Fixnum && value.class == String && block_given?
        @j_del.java_method(:lset, [Java::java.lang.String.java_class,Java::long.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,index,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling lset(key,index,value)"
    end
    #  Trim a list to the specified range
    # @param [String] key Key string
    # @param [Fixnum] from Start index
    # @param [Fixnum] to Stop index
    # @yield Handler for the result of this call.
    # @return [self]
    def ltrim(key=nil,from=nil,to=nil)
      if key.class == String && from.class == Fixnum && to.class == Fixnum && block_given?
        @j_del.java_method(:ltrim, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,from,to,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling ltrim(key,from,to)"
    end
    #  Get the value of the given key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def mget(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:mget, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling mget(key)"
    end
    #  Get the values of all the given keys
    # @param [Array<String>] keys List of keys to get
    # @yield Handler for the result of this call.
    # @return [self]
    def mget_many(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:mgetMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling mget_many(keys)"
    end
    #  Atomically transfer a key from a Redis instance to another one.
    # @param [String] host Destination host
    # @param [Fixnum] port Destination port
    # @param [String] key Key to migrate
    # @param [Fixnum] destdb Destination database index
    # @param [Fixnum] timeout 
    # @param [Hash] options Migrate options
    # @yield Handler for the result of this call.
    # @return [self]
    def migrate(host=nil,port=nil,key=nil,destdb=nil,timeout=nil,options=nil)
      if host.class == String && port.class == Fixnum && key.class == String && destdb.class == Fixnum && timeout.class == Fixnum && options.class == Hash && block_given?
        @j_del.java_method(:migrate, [Java::java.lang.String.java_class,Java::int.java_class,Java::java.lang.String.java_class,Java::int.java_class,Java::long.java_class,Java::IoVertxRedisOp::MigrateOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(host,port,key,destdb,timeout,Java::IoVertxRedisOp::MigrateOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling migrate(host,port,key,destdb,timeout,options)"
    end
    #  Listen for all requests received by the server in real time
    # @yield 
    # @return [self]
    def monitor
      if block_given?
        @j_del.java_method(:monitor, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling monitor()"
    end
    #  Move a key to another database
    # @param [String] key Key to migrate
    # @param [Fixnum] destdb Destination database index
    # @yield Handler for the result of this call.
    # @return [self]
    def move(key=nil,destdb=nil)
      if key.class == String && destdb.class == Fixnum && block_given?
        @j_del.java_method(:move, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,destdb,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling move(key,destdb)"
    end
    #  Set multiple keys to multiple values
    # @param [Hash{String => String}] keyvals Key value pairs to set
    # @yield Handler for the result of this call.
    # @return [self]
    def mset(keyvals=nil)
      if keyvals.class == Hash && block_given?
        @j_del.java_method(:mset, [Java::JavaUtil::Map.java_class,Java::IoVertxCore::Handler.java_class]).call(Hash[keyvals.map { |k,v| [k,v] }],(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling mset(keyvals)"
    end
    #  Set multiple keys to multiple values, only if none of the keys exist
    # @param [Hash{String => String}] keyvals Key value pairs to set
    # @yield Handler for the result of this call.
    # @return [self]
    def msetnx(keyvals=nil)
      if keyvals.class == Hash && block_given?
        @j_del.java_method(:msetnx, [Java::JavaUtil::Map.java_class,Java::IoVertxCore::Handler.java_class]).call(Hash[keyvals.map { |k,v| [k,v] }],(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling msetnx(keyvals)"
    end
    #  Mark the start of a transaction block
    # @yield 
    # @return [self]
    def multi
      if block_given?
        @j_del.java_method(:multi, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling multi()"
    end
    #  Inspect the internals of Redis objects
    # @param [String] key Key string
    # @param [:REFCOUNT,:ENCODING,:IDLETIME] cmd Object sub command
    # @yield Handler for the result of this call.
    # @return [self]
    def object(key=nil,cmd=nil)
      if key.class == String && cmd.class == Symbol && block_given?
        @j_del.java_method(:object, [Java::java.lang.String.java_class,Java::IoVertxRedisOp::ObjectCmd.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Java::IoVertxRedisOp::ObjectCmd.valueOf(cmd),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling object(key,cmd)"
    end
    #  Remove the expiration from a key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def persist(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:persist, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling persist(key)"
    end
    #  Set a key's time to live in milliseconds
    # @param [String] key String key
    # @param [Fixnum] millis Time to live in milliseconds
    # @yield Handler for the result of this call.
    # @return [self]
    def pexpire(key=nil,millis=nil)
      if key.class == String && millis.class == Fixnum && block_given?
        @j_del.java_method(:pexpire, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,millis,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pexpire(key,millis)"
    end
    #  Set the expiration for a key as a UNIX timestamp specified in milliseconds
    # @param [String] key Key string
    # @param [Fixnum] millis Expiry time as Unix timestamp in milliseconds
    # @yield Handler for the result of this call.
    # @return [self]
    def pexpireat(key=nil,millis=nil)
      if key.class == String && millis.class == Fixnum && block_given?
        @j_del.java_method(:pexpireat, [Java::java.lang.String.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,millis,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pexpireat(key,millis)"
    end
    #  Adds the specified element to the specified HyperLogLog.
    # @param [String] key Key string
    # @param [String] element Element to add
    # @yield Handler for the result of this call.
    # @return [self]
    def pfadd(key=nil,element=nil)
      if key.class == String && element.class == String && block_given?
        @j_del.java_method(:pfadd, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,element,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pfadd(key,element)"
    end
    #  Adds the specified elements to the specified HyperLogLog.
    # @param [String] key Key string
    # @param [Array<String>] elements Elementa to add
    # @yield Handler for the result of this call.
    # @return [self]
    def pfadd_many(key=nil,elements=nil)
      if key.class == String && elements.class == Array && block_given?
        @j_del.java_method(:pfaddMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,elements.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pfadd_many(key,elements)"
    end
    #  Return the approximated cardinality of the set observed by the HyperLogLog at key.
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def pfcount(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:pfcount, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pfcount(key)"
    end
    #  Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
    # @param [Array<String>] keys List of keys
    # @yield Handler for the result of this call.
    # @return [self]
    def pfcount_many(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:pfcountMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pfcount_many(keys)"
    end
    #  Merge N different HyperLogLogs into a single one.
    # @param [String] destkey Destination key
    # @param [Array<String>] keys List of source keys
    # @yield Handler for the result of this call.
    # @return [self]
    def pfmerge(destkey=nil,keys=nil)
      if destkey.class == String && keys.class == Array && block_given?
        @j_del.java_method(:pfmerge, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pfmerge(destkey,keys)"
    end
    #  Ping the server
    # @yield 
    # @return [self]
    def ping
      if block_given?
        @j_del.java_method(:ping, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling ping()"
    end
    #  Set the value and expiration in milliseconds of a key
    # @param [String] key Key string
    # @param [Fixnum] millis Number of milliseconds until the key expires
    # @param [String] value New value for key
    # @yield Handler for the result of this call.
    # @return [self]
    def psetex(key=nil,millis=nil,value=nil)
      if key.class == String && millis.class == Fixnum && value.class == String && block_given?
        @j_del.java_method(:psetex, [Java::java.lang.String.java_class,Java::long.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,millis,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling psetex(key,millis,value)"
    end
    #  Listen for messages published to channels matching the given pattern
    # @param [String] pattern Pattern string
    # @yield Handler for the result of this call.
    # @return [self]
    def psubscribe(pattern=nil)
      if pattern.class == String && block_given?
        @j_del.java_method(:psubscribe, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling psubscribe(pattern)"
    end
    #  Listen for messages published to channels matching the given patterns
    # @param [Array<String>] patterns List of patterns
    # @yield Handler for the result of this call.
    # @return [self]
    def psubscribe_many(patterns=nil)
      if patterns.class == Array && block_given?
        @j_del.java_method(:psubscribeMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(patterns.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling psubscribe_many(patterns)"
    end
    #  Lists the currently active channels - only those matching the pattern
    # @param [String] pattern A glob-style pattern - an empty string means no pattern
    # @yield Handler for the result of this call.
    # @return [self]
    def pubsub_channels(pattern=nil)
      if pattern.class == String && block_given?
        @j_del.java_method(:pubsubChannels, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(pattern,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pubsub_channels(pattern)"
    end
    #  Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels
    # @param [Array<String>] channels List of channels
    # @yield Handler for the result of this call.
    # @return [self]
    def pubsub_numsub(channels=nil)
      if channels.class == Array && block_given?
        @j_del.java_method(:pubsubNumsub, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(channels.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pubsub_numsub(channels)"
    end
    #  Returns the number of subscriptions to patterns (that are performed using the PSUBSCRIBE command)
    # @yield Handler for the result of this call.
    # @return [self]
    def pubsub_numpat
      if block_given?
        @j_del.java_method(:pubsubNumpat, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pubsub_numpat()"
    end
    #  Get the time to live for a key in milliseconds
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def pttl(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:pttl, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling pttl(key)"
    end
    #  Post a message to a channel
    # @param [String] channel Channel key
    # @param [String] message Message to send to channel
    # @yield Handler for the result of this call.
    # @return [self]
    def publish(channel=nil,message=nil)
      if channel.class == String && message.class == String && block_given?
        @j_del.java_method(:publish, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(channel,message,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling publish(channel,message)"
    end
    #  Stop listening for messages posted to channels matching the given patterns
    # @param [Array<String>] patterns List of patterns to match against
    # @yield Handler for the result of this call.
    # @return [self]
    def punsubscribe(patterns=nil)
      if patterns.class == Array && block_given?
        @j_del.java_method(:punsubscribe, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(patterns.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling punsubscribe(patterns)"
    end
    #  Return a random key from the keyspace
    # @yield 
    # @return [self]
    def randomkey
      if block_given?
        @j_del.java_method(:randomkey, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling randomkey()"
    end
    #  Rename a key
    # @param [String] key Key string to be renamed
    # @param [String] newkey New key string
    # @yield Handler for the result of this call.
    # @return [self]
    def rename(key=nil,newkey=nil)
      if key.class == String && newkey.class == String && block_given?
        @j_del.java_method(:rename, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,newkey,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rename(key,newkey)"
    end
    #  Rename a key, only if the new key does not exist
    # @param [String] key Key string to be renamed
    # @param [String] newkey New key string
    # @yield Handler for the result of this call.
    # @return [self]
    def renamenx(key=nil,newkey=nil)
      if key.class == String && newkey.class == String && block_given?
        @j_del.java_method(:renamenx, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,newkey,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling renamenx(key,newkey)"
    end
    #  Create a key using the provided serialized value, previously obtained using DUMP.
    # @param [String] key Key string
    # @param [Fixnum] millis Expiry time in milliseconds to set on the key
    # @param [String] serialized Serialized form of the key value as obtained using DUMP
    # @yield Handler for the result of this call.
    # @return [self]
    def restore(key=nil,millis=nil,serialized=nil)
      if key.class == String && millis.class == Fixnum && serialized.class == String && block_given?
        @j_del.java_method(:restore, [Java::java.lang.String.java_class,Java::long.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,millis,serialized,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling restore(key,millis,serialized)"
    end
    #  Return the role of the instance in the context of replication
    # @yield 
    # @return [self]
    def role
      if block_given?
        @j_del.java_method(:role, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling role()"
    end
    #  Remove and get the last element in a list
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def rpop(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:rpop, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rpop(key)"
    end
    #  Remove the last element in a list, append it to another list and return it
    # @param [String] key Key string identifying source list
    # @param [String] destkey Key string identifying destination list
    # @yield Handler for the result of this call.
    # @return [self]
    def rpoplpush(key=nil,destkey=nil)
      if key.class == String && destkey.class == String && block_given?
        @j_del.java_method(:rpoplpush, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,destkey,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rpoplpush(key,destkey)"
    end
    #  Append one or multiple values to a list
    # @param [String] key Key string
    # @param [Array<String>] values List of values to add to the end of the list
    # @yield Handler for the result of this call.
    # @return [self]
    def rpush_many(key=nil,values=nil)
      if key.class == String && values.class == Array && block_given?
        @j_del.java_method(:rpushMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,values.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rpush_many(key,values)"
    end
    #  Append one or multiple values to a list
    # @param [String] key Key string
    # @param [String] value Value to be added to the end of the list
    # @yield Handler for the result of this call.
    # @return [self]
    def rpush(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:rpush, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rpush(key,value)"
    end
    #  Append a value to a list, only if the list exists
    # @param [String] key Key string
    # @param [String] value Value to be added to the end of the list
    # @yield Handler for the result of this call.
    # @return [self]
    def rpushx(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:rpushx, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling rpushx(key,value)"
    end
    #  Add a member to a set
    # @param [String] key Key string
    # @param [String] member Value to be added to the set
    # @yield Handler for the result of this call.
    # @return [self]
    def sadd(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:sadd, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sadd(key,member)"
    end
    #  Add one or more members to a set
    # @param [String] key Key string
    # @param [Array<String>] members Values to be added to the set
    # @yield Handler for the result of this call.
    # @return [self]
    def sadd_many(key=nil,members=nil)
      if key.class == String && members.class == Array && block_given?
        @j_del.java_method(:saddMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,members.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sadd_many(key,members)"
    end
    #  Synchronously save the dataset to disk
    # @yield 
    # @return [self]
    def save
      if block_given?
        @j_del.java_method(:save, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling save()"
    end
    #  Get the number of members in a set
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def scard(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:scard, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling scard(key)"
    end
    #  Check existence of script in the script cache.
    # @param [String] script SHA1 digest identifying a script in the script cache
    # @yield Handler for the result of this call.
    # @return [self]
    def script_exists(script=nil)
      if script.class == String && block_given?
        @j_del.java_method(:scriptExists, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(script,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling script_exists(script)"
    end
    #  Check existence of scripts in the script cache.
    # @param [Array<String>] scripts List of SHA1 digests identifying scripts in the script cache
    # @yield Handler for the result of this call.
    # @return [self]
    def script_exists_many(scripts=nil)
      if scripts.class == Array && block_given?
        @j_del.java_method(:scriptExistsMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(scripts.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling script_exists_many(scripts)"
    end
    #  Remove all the scripts from the script cache.
    # @yield 
    # @return [self]
    def script_flush
      if block_given?
        @j_del.java_method(:scriptFlush, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling script_flush()"
    end
    #  Kill the script currently in execution.
    # @yield 
    # @return [self]
    def script_kill
      if block_given?
        @j_del.java_method(:scriptKill, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling script_kill()"
    end
    #  Load the specified Lua script into the script cache.
    # @param [String] script Lua script
    # @yield Handler for the result of this call.
    # @return [self]
    def script_load(script=nil)
      if script.class == String && block_given?
        @j_del.java_method(:scriptLoad, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(script,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling script_load(script)"
    end
    #  Subtract multiple sets
    # @param [String] key Key identifying the set to compare with all other sets combined
    # @param [Array<String>] cmpkeys List of keys identifying sets to subtract from the key set
    # @yield Handler for the result of this call.
    # @return [self]
    def sdiff(key=nil,cmpkeys=nil)
      if key.class == String && cmpkeys.class == Array && block_given?
        @j_del.java_method(:sdiff, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,cmpkeys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sdiff(key,cmpkeys)"
    end
    #  Subtract multiple sets and store the resulting set in a key
    # @param [String] destkey Destination key where the result should be stored
    # @param [String] key Key identifying the set to compare with all other sets combined
    # @param [Array<String>] cmpkeys List of keys identifying sets to subtract from the key set
    # @yield Handler for the result of this call.
    # @return [self]
    def sdiffstore(destkey=nil,key=nil,cmpkeys=nil)
      if destkey.class == String && key.class == String && cmpkeys.class == Array && block_given?
        @j_del.java_method(:sdiffstore, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,key,cmpkeys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sdiffstore(destkey,key,cmpkeys)"
    end
    #  Change the selected database for the current connection
    # @param [Fixnum] dbindex Index identifying the new active database
    # @yield Handler for the result of this call.
    # @return [self]
    def select(dbindex=nil)
      if dbindex.class == Fixnum && block_given?
        @j_del.java_method(:select, [Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(dbindex,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling select(dbindex)"
    end
    #  Set the string value of a key
    # @param [String] key Key of which value to set
    # @param [String] value New value for the key
    # @yield Handler for the result of this call.
    # @return [self]
    def set(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:set, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling set(key,value)"
    end
    #  Set the string value of a key
    # @param [String] key Key of which value to set
    # @param [String] value New value for the key
    # @param [Hash] options Set options
    # @yield Handler for the result of this call.
    # @return [self]
    def set_with_options(key=nil,value=nil,options=nil)
      if key.class == String && value.class == String && options.class == Hash && block_given?
        @j_del.java_method(:setWithOptions, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::SetOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,Java::IoVertxRedisOp::SetOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling set_with_options(key,value,options)"
    end
    #  Set the binary string value of a key - without encoding as utf-8
    # @param [String] key Key of which value to set
    # @param [String] value New value for the key
    # @yield Handler for the result of this call.
    # @return [self]
    def set_binary(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:setBinary, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling set_binary(key,value)"
    end
    #  Sets or clears the bit at offset in the string value stored at key
    # @param [String] key Key string
    # @param [Fixnum] offset Bit offset
    # @param [Fixnum] bit New value - must be 1 or 0
    # @yield Handler for the result of this call.
    # @return [self]
    def setbit(key=nil,offset=nil,bit=nil)
      if key.class == String && offset.class == Fixnum && bit.class == Fixnum && block_given?
        @j_del.java_method(:setbit, [Java::java.lang.String.java_class,Java::long.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,offset,bit,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling setbit(key,offset,bit)"
    end
    #  Set the value and expiration of a key
    # @param [String] key Key string
    # @param [Fixnum] seconds Number of seconds until the key expires
    # @param [String] value New value for key
    # @yield Handler for the result of this call.
    # @return [self]
    def setex(key=nil,seconds=nil,value=nil)
      if key.class == String && seconds.class == Fixnum && value.class == String && block_given?
        @j_del.java_method(:setex, [Java::java.lang.String.java_class,Java::long.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,seconds,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling setex(key,seconds,value)"
    end
    #  Set the value of a key, only if the key does not exist
    # @param [String] key Key of which value to set
    # @param [String] value New value for the key
    # @yield Handler for the result of this call.
    # @return [self]
    def setnx(key=nil,value=nil)
      if key.class == String && value.class == String && block_given?
        @j_del.java_method(:setnx, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling setnx(key,value)"
    end
    #  Overwrite part of a string at key starting at the specified offset
    # @param [String] key Key string
    # @param [Fixnum] offset Offset - the maximum offset that you can set is 2^29 -1 (536870911), as Redis Strings are limited to 512 megabytes
    # @param [String] value Value to overwrite with
    # @yield Handler for the result of this call.
    # @return [self]
    def setrange(key=nil,offset=nil,value=nil)
      if key.class == String && offset.class == Fixnum && value.class == String && block_given?
        @j_del.java_method(:setrange, [Java::java.lang.String.java_class,Java::int.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,offset,value,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling setrange(key,offset,value)"
    end
    #  Intersect multiple sets
    # @param [Array<String>] keys List of keys to perform intersection on
    # @yield Handler for the result of this call.
    # @return [self]
    def sinter(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:sinter, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sinter(keys)"
    end
    #  Intersect multiple sets and store the resulting set in a key
    # @param [String] destkey Key where to store the results
    # @param [Array<String>] keys List of keys to perform intersection on
    # @yield Handler for the result of this call.
    # @return [self]
    def sinterstore(destkey=nil,keys=nil)
      if destkey.class == String && keys.class == Array && block_given?
        @j_del.java_method(:sinterstore, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sinterstore(destkey,keys)"
    end
    #  Determine if a given value is a member of a set
    # @param [String] key Key string
    # @param [String] member Member to look for
    # @yield Handler for the result of this call.
    # @return [self]
    def sismember(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:sismember, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sismember(key,member)"
    end
    #  Make the server a slave of another instance
    # @param [String] host Host to become this server's master
    # @param [Fixnum] port Port of our new master
    # @yield Handler for the result of this call.
    # @return [self]
    def slaveof(host=nil,port=nil)
      if host.class == String && port.class == Fixnum && block_given?
        @j_del.java_method(:slaveof, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(host,port,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling slaveof(host,port)"
    end
    #  Make this server a master
    # @yield Handler for the result of this call.
    # @return [self]
    def slaveof_noone
      if block_given?
        @j_del.java_method(:slaveofNoone, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling slaveof_noone()"
    end
    #  Read the Redis slow queries log
    # @param [Fixnum] limit Number of log entries to return. If value is less than zero all entries are returned
    # @yield Handler for the result of this call.
    # @return [self]
    def slowlog_get(limit=nil)
      if limit.class == Fixnum && block_given?
        @j_del.java_method(:slowlogGet, [Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(limit,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling slowlog_get(limit)"
    end
    #  Get the length of the Redis slow queries log
    # @yield Handler for the result of this call.
    # @return [self]
    def slowlog_len
      if block_given?
        @j_del.java_method(:slowlogLen, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling slowlog_len()"
    end
    #  Reset the Redis slow queries log
    # @yield Handler for the result of this call.
    # @return [self]
    def slowlog_reset
      if block_given?
        @j_del.java_method(:slowlogReset, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling slowlog_reset()"
    end
    #  Get all the members in a set
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def smembers(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:smembers, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling smembers(key)"
    end
    #  Move a member from one set to another
    # @param [String] key Key of source set currently containing the member
    # @param [String] destkey Key identifying the destination set
    # @param [String] member Member to move
    # @yield Handler for the result of this call.
    # @return [self]
    def smove(key=nil,destkey=nil,member=nil)
      if key.class == String && destkey.class == String && member.class == String && block_given?
        @j_del.java_method(:smove, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,destkey,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling smove(key,destkey,member)"
    end
    #  Sort the elements in a list, set or sorted set
    # @param [String] key Key string
    # @param [Hash] options Sort options
    # @yield Handler for the result of this call.
    # @return [self]
    def sort(key=nil,options=nil)
      if key.class == String && options.class == Hash && block_given?
        @j_del.java_method(:sort, [Java::java.lang.String.java_class,Java::IoVertxRedisOp::SortOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Java::IoVertxRedisOp::SortOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sort(key,options)"
    end
    #  Remove and return a random member from a set
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def spop(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:spop, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling spop(key)"
    end
    #  Remove and return random members from a set
    # @param [String] key Key string
    # @param [Fixnum] count Number of members to remove
    # @yield Handler for the result of this call.
    # @return [self]
    def spop_many(key=nil,count=nil)
      if key.class == String && count.class == Fixnum && block_given?
        @j_del.java_method(:spopMany, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,count,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling spop_many(key,count)"
    end
    #  Get one or multiple random members from a set
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def srandmember(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:srandmember, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling srandmember(key)"
    end
    #  Get one or multiple random members from a set
    # @param [String] key Key string
    # @param [Fixnum] count Number of members to get
    # @yield Handler for the result of this call.
    # @return [self]
    def srandmember_count(key=nil,count=nil)
      if key.class == String && count.class == Fixnum && block_given?
        @j_del.java_method(:srandmemberCount, [Java::java.lang.String.java_class,Java::int.java_class,Java::IoVertxCore::Handler.java_class]).call(key,count,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling srandmember_count(key,count)"
    end
    #  Remove one member from a set
    # @param [String] key Key string
    # @param [String] member Member to remove
    # @yield Handler for the result of this call.
    # @return [self]
    def srem(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:srem, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling srem(key,member)"
    end
    #  Remove one or more members from a set
    # @param [String] key Key string
    # @param [Array<String>] members Members to remove
    # @yield Handler for the result of this call.
    # @return [self]
    def srem_many(key=nil,members=nil)
      if key.class == String && members.class == Array && block_given?
        @j_del.java_method(:sremMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,members.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling srem_many(key,members)"
    end
    #  Get the length of the value stored in a key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def strlen(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:strlen, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling strlen(key)"
    end
    #  Listen for messages published to the given channels
    # @param [String] channel Channel to subscribe to
    # @yield Handler for the result of this call.
    # @return [self]
    def subscribe(channel=nil)
      if channel.class == String && block_given?
        @j_del.java_method(:subscribe, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(channel,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling subscribe(channel)"
    end
    #  Listen for messages published to the given channels
    # @param [Array<String>] channels List of channels to subscribe to
    # @yield Handler for the result of this call.
    # @return [self]
    def subscribe_many(channels=nil)
      if channels.class == Array && block_given?
        @j_del.java_method(:subscribeMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(channels.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling subscribe_many(channels)"
    end
    #  Add multiple sets
    # @param [Array<String>] keys List of keys identifying sets to add up
    # @yield Handler for the result of this call.
    # @return [self]
    def sunion(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:sunion, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sunion(keys)"
    end
    #  Add multiple sets and store the resulting set in a key
    # @param [String] destkey Destination key
    # @param [Array<String>] keys List of keys identifying sets to add up
    # @yield Handler for the result of this call.
    # @return [self]
    def sunionstore(destkey=nil,keys=nil)
      if destkey.class == String && keys.class == Array && block_given?
        @j_del.java_method(:sunionstore, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sunionstore(destkey,keys)"
    end
    #  Internal command used for replication
    # @yield 
    # @return [self]
    def sync
      if block_given?
        @j_del.java_method(:sync, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sync()"
    end
    #  Return the current server time
    # @yield 
    # @return [self]
    def time
      if block_given?
        @j_del.java_method(:time, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling time()"
    end
    #  Get the time to live for a key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def ttl(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:ttl, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling ttl(key)"
    end
    #  Determine the type stored at key
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def type(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:type, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling type(key)"
    end
    #  Stop listening for messages posted to the given channels
    # @param [Array<String>] channels List of channels to subscribe to
    # @yield Handler for the result of this call.
    # @return [self]
    def unsubscribe(channels=nil)
      if channels.class == Array && block_given?
        @j_del.java_method(:unsubscribe, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(channels.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling unsubscribe(channels)"
    end
    #  Forget about all watched keys
    # @yield 
    # @return [self]
    def unwatch
      if block_given?
        @j_del.java_method(:unwatch, [Java::IoVertxCore::Handler.java_class]).call((Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling unwatch()"
    end
    #  Wait for the synchronous replication of all the write commands sent in the context of the current connection.
    # @param [Fixnum] numSlaves 
    # @param [Fixnum] timeout 
    # @yield Handler for the result of this call.
    # @return [self]
    def wait(numSlaves=nil,timeout=nil)
      if numSlaves.class == Fixnum && timeout.class == Fixnum && block_given?
        @j_del.java_method(:wait, [Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(numSlaves,timeout,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling wait(numSlaves,timeout)"
    end
    #  Watch the given keys to determine execution of the MULTI/EXEC block
    # @param [String] key Key to watch
    # @yield Handler for the result of this call.
    # @return [self]
    def watch(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:watch, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling watch(key)"
    end
    #  Watch the given keys to determine execution of the MULTI/EXEC block
    # @param [Array<String>] keys List of keys to watch
    # @yield Handler for the result of this call.
    # @return [self]
    def watch_many(keys=nil)
      if keys.class == Array && block_given?
        @j_del.java_method(:watchMany, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(keys.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling watch_many(keys)"
    end
    #  Add one or more members to a sorted set, or update its score if it already exists
    # @param [String] key Key string
    # @param [Float] score Score used for sorting
    # @param [String] member New member key
    # @yield Handler for the result of this call.
    # @return [self]
    def zadd(key=nil,score=nil,member=nil)
      if key.class == String && score.class == Float && member.class == String && block_given?
        @j_del.java_method(:zadd, [Java::java.lang.String.java_class,Java::double.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,::Vertx::Util::Utils.to_double(score),member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zadd(key,score,member)"
    end
    #  Add one or more members to a sorted set, or update its score if it already exists
    # @param [String] key Key string
    # @param [Hash{String => Float}] members New member keys and their scores
    # @yield Handler for the result of this call.
    # @return [self]
    def zadd_many(key=nil,members=nil)
      if key.class == String && members.class == Hash && block_given?
        @j_del.java_method(:zaddMany, [Java::java.lang.String.java_class,Java::JavaUtil::Map.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Hash[members.map { |k,v| [k,::Vertx::Util::Utils.to_double(v)] }],(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zadd_many(key,members)"
    end
    #  Get the number of members in a sorted set
    # @param [String] key Key string
    # @yield Handler for the result of this call.
    # @return [self]
    def zcard(key=nil)
      if key.class == String && block_given?
        @j_del.java_method(:zcard, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zcard(key)"
    end
    #  Count the members in a sorted set with scores within the given values
    # @param [String] key Key string
    # @param [Float] min Minimum score
    # @param [Float] max Maximum score
    # @yield Handler for the result of this call.
    # @return [self]
    def zcount(key=nil,min=nil,max=nil)
      if key.class == String && min.class == Float && max.class == Float && block_given?
        @j_del.java_method(:zcount, [Java::java.lang.String.java_class,Java::double.java_class,Java::double.java_class,Java::IoVertxCore::Handler.java_class]).call(key,::Vertx::Util::Utils.to_double(min),::Vertx::Util::Utils.to_double(max),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zcount(key,min,max)"
    end
    #  Increment the score of a member in a sorted set
    # @param [String] key Key string
    # @param [Float] increment Increment amount
    # @param [String] member Member key
    # @yield Handler for the result of this call.
    # @return [self]
    def zincrby(key=nil,increment=nil,member=nil)
      if key.class == String && increment.class == Float && member.class == String && block_given?
        @j_del.java_method(:zincrby, [Java::java.lang.String.java_class,Java::double.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,::Vertx::Util::Utils.to_double(increment),member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zincrby(key,increment,member)"
    end
    #  Intersect multiple sorted sets and store the resulting sorted set in a new key
    # @param [String] destkey Destination key
    # @param [Array<String>] sets List of keys identifying sorted sets to intersect
    # @param [:NONE,:SUM,:MIN,:MAX] options Aggregation options
    # @yield Handler for the result of this call.
    # @return [self]
    def zinterstore(destkey=nil,sets=nil,options=nil)
      if destkey.class == String && sets.class == Array && options.class == Symbol && block_given?
        @j_del.java_method(:zinterstore, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxRedisOp::AggregateOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,sets.map { |element| element },Java::IoVertxRedisOp::AggregateOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zinterstore(destkey,sets,options)"
    end
    #  Intersect multiple sorted sets and store the resulting sorted set in a new key using weights for scoring
    # @param [String] destkey Destination key
    # @param [Hash{String => Float}] sets List of keys identifying sorted sets to intersect
    # @param [:NONE,:SUM,:MIN,:MAX] options Aggregation options
    # @yield Handler for the result of this call.
    # @return [self]
    def zinterstore_weighed(destkey=nil,sets=nil,options=nil)
      if destkey.class == String && sets.class == Hash && options.class == Symbol && block_given?
        @j_del.java_method(:zinterstoreWeighed, [Java::java.lang.String.java_class,Java::JavaUtil::Map.java_class,Java::IoVertxRedisOp::AggregateOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,Hash[sets.map { |k,v| [k,::Vertx::Util::Utils.to_double(v)] }],Java::IoVertxRedisOp::AggregateOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zinterstore_weighed(destkey,sets,options)"
    end
    #  Count the number of members in a sorted set between a given lexicographical range
    # @param [String] key Key string
    # @param [String] min Pattern to compare against for minimum value
    # @param [String] max Pattern to compare against for maximum value
    # @yield Handler for the result of this call.
    # @return [self]
    def zlexcount(key=nil,min=nil,max=nil)
      if key.class == String && min.class == String && max.class == String && block_given?
        @j_del.java_method(:zlexcount, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,min,max,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zlexcount(key,min,max)"
    end
    #  Return a range of members in a sorted set, by index
    # @param [String] key Key string
    # @param [Fixnum] start Start index for the range
    # @param [Fixnum] stop Stop index for the range - inclusive
    # @yield Handler for the result of this call.
    # @return [self]
    def zrange(key=nil,start=nil,stop=nil)
      if key.class == String && start.class == Fixnum && stop.class == Fixnum && block_given?
        @j_del.java_method(:zrange, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,stop,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrange(key,start,stop)"
    end
    #  Return a range of members in a sorted set, by index
    # @param [String] key Key string
    # @param [Fixnum] start Start index for the range
    # @param [Fixnum] stop Stop index for the range - inclusive
    # @param [:NONE,:WITHSCORES] options Range options
    # @yield Handler for the result of this call.
    # @return [self]
    def zrange_with_options(key=nil,start=nil,stop=nil,options=nil)
      if key.class == String && start.class == Fixnum && stop.class == Fixnum && options.class == Symbol && block_given?
        @j_del.java_method(:zrangeWithOptions, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxRedisOp::RangeOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,stop,Java::IoVertxRedisOp::RangeOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrange_with_options(key,start,stop,options)"
    end
    #  Return a range of members in a sorted set, by lexicographical range
    # @param [String] key Key string
    # @param [String] min Pattern representing a minimum allowed value
    # @param [String] max Pattern representing a maximum allowed value
    # @param [Hash] options Limit options where limit can be specified
    # @yield Handler for the result of this call.
    # @return [self]
    def zrangebylex(key=nil,min=nil,max=nil,options=nil)
      if key.class == String && min.class == String && max.class == String && options.class == Hash && block_given?
        @j_del.java_method(:zrangebylex, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::LimitOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,min,max,Java::IoVertxRedisOp::LimitOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrangebylex(key,min,max,options)"
    end
    #  Return a range of members in a sorted set, by score
    # @param [String] key Key string
    # @param [String] min Pattern defining a minimum value
    # @param [String] max Pattern defining a maximum value
    # @param [Hash] options Range and limit options
    # @yield Handler for the result of this call.
    # @return [self]
    def zrangebyscore(key=nil,min=nil,max=nil,options=nil)
      if key.class == String && min.class == String && max.class == String && options.class == Hash && block_given?
        @j_del.java_method(:zrangebyscore, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::RangeLimitOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,min,max,Java::IoVertxRedisOp::RangeLimitOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrangebyscore(key,min,max,options)"
    end
    #  Determine the index of a member in a sorted set
    # @param [String] key Key string
    # @param [String] member Member in the sorted set identified by key
    # @yield Handler for the result of this call.
    # @return [self]
    def zrank(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:zrank, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrank(key,member)"
    end
    #  Remove one member from a sorted set
    # @param [String] key Key string
    # @param [String] member Member in the sorted set identified by key
    # @yield Handler for the result of this call.
    # @return [self]
    def zrem(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:zrem, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrem(key,member)"
    end
    #  Remove one or more members from a sorted set
    # @param [String] key Key string
    # @param [Array<String>] members Members in the sorted set identified by key
    # @yield Handler for the result of this call.
    # @return [self]
    def zrem_many(key=nil,members=nil)
      if key.class == String && members.class == Array && block_given?
        @j_del.java_method(:zremMany, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(key,members.map { |element| element },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrem_many(key,members)"
    end
    #  Remove all members in a sorted set between the given lexicographical range
    # @param [String] key Key string
    # @param [String] min Pattern defining a minimum value
    # @param [String] max Pattern defining a maximum value
    # @yield Handler for the result of this call.
    # @return [self]
    def zremrangebylex(key=nil,min=nil,max=nil)
      if key.class == String && min.class == String && max.class == String && block_given?
        @j_del.java_method(:zremrangebylex, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,min,max,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zremrangebylex(key,min,max)"
    end
    #  Remove all members in a sorted set within the given indexes
    # @param [String] key Key string
    # @param [Fixnum] start Start index
    # @param [Fixnum] stop Stop index
    # @yield Handler for the result of this call.
    # @return [self]
    def zremrangebyrank(key=nil,start=nil,stop=nil)
      if key.class == String && start.class == Fixnum && stop.class == Fixnum && block_given?
        @j_del.java_method(:zremrangebyrank, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,stop,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zremrangebyrank(key,start,stop)"
    end
    #  Remove all members in a sorted set within the given scores
    # @param [String] key Key string
    # @param [String] min Pattern defining a minimum value
    # @param [String] max Pattern defining a maximum value
    # @yield 
    # @return [self]
    def zremrangebyscore(key=nil,min=nil,max=nil)
      if key.class == String && min.class == String && max.class == String && block_given?
        @j_del.java_method(:zremrangebyscore, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,min,max,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zremrangebyscore(key,min,max)"
    end
    #  Return a range of members in a sorted set, by index, with scores ordered from high to low
    # @param [String] key Key string
    # @param [Fixnum] start Start index for the range
    # @param [Fixnum] stop Stop index for the range - inclusive
    # @param [:NONE,:WITHSCORES] options Range options
    # @yield Handler for the result of this call.
    # @return [self]
    def zrevrange(key=nil,start=nil,stop=nil,options=nil)
      if key.class == String && start.class == Fixnum && stop.class == Fixnum && options.class == Symbol && block_given?
        @j_del.java_method(:zrevrange, [Java::java.lang.String.java_class,Java::long.java_class,Java::long.java_class,Java::IoVertxRedisOp::RangeOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,start,stop,Java::IoVertxRedisOp::RangeOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrevrange(key,start,stop,options)"
    end
    #  Return a range of members in a sorted set, by score, between the given lexicographical range with scores ordered from high to low
    # @param [String] key Key string
    # @param [String] max Pattern defining a maximum value
    # @param [String] min Pattern defining a minimum value
    # @param [Hash] options Limit options
    # @yield Handler for the result of this call.
    # @return [self]
    def zrevrangebylex(key=nil,max=nil,min=nil,options=nil)
      if key.class == String && max.class == String && min.class == String && options.class == Hash && block_given?
        @j_del.java_method(:zrevrangebylex, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::LimitOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,max,min,Java::IoVertxRedisOp::LimitOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrevrangebylex(key,max,min,options)"
    end
    #  Return a range of members in a sorted set, by score, with scores ordered from high to low
    # @param [String] key Key string
    # @param [String] max Pattern defining a maximum value
    # @param [String] min Pattern defining a minimum value
    # @param [Hash] options Range and limit options
    # @yield Handler for the result of this call.
    # @return [self]
    def zrevrangebyscore(key=nil,max=nil,min=nil,options=nil)
      if key.class == String && max.class == String && min.class == String && options.class == Hash && block_given?
        @j_del.java_method(:zrevrangebyscore, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::RangeLimitOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,max,min,Java::IoVertxRedisOp::RangeLimitOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrevrangebyscore(key,max,min,options)"
    end
    #  Determine the index of a member in a sorted set, with scores ordered from high to low
    # @param [String] key Key string
    # @param [String] member Member in the sorted set identified by key
    # @yield Handler for the result of this call.
    # @return [self]
    def zrevrank(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:zrevrank, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zrevrank(key,member)"
    end
    #  Get the score associated with the given member in a sorted set
    # @param [String] key Key string
    # @param [String] member Member in the sorted set identified by key
    # @yield Handler for the result of this call.
    # @return [self]
    def zscore(key=nil,member=nil)
      if key.class == String && member.class == String && block_given?
        @j_del.java_method(:zscore, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(key,member,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zscore(key,member)"
    end
    #  Add multiple sorted sets and store the resulting sorted set in a new key
    # @param [String] destkey Destination key
    # @param [Array<String>] sets List of keys identifying sorted sets
    # @param [:NONE,:SUM,:MIN,:MAX] options Aggregation options
    # @yield Handler for the result of this call.
    # @return [self]
    def zunionstore(destkey=nil,sets=nil,options=nil)
      if destkey.class == String && sets.class == Array && options.class == Symbol && block_given?
        @j_del.java_method(:zunionstore, [Java::java.lang.String.java_class,Java::JavaUtil::List.java_class,Java::IoVertxRedisOp::AggregateOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(destkey,sets.map { |element| element },Java::IoVertxRedisOp::AggregateOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zunionstore(destkey,sets,options)"
    end
    #  Add multiple sorted sets using weights, and store the resulting sorted set in a new key
    # @param [String] key Destination key
    # @param [Hash{String => Float}] sets Map containing set-key:weight pairs
    # @param [:NONE,:SUM,:MIN,:MAX] options Aggregation options
    # @yield Handler for the result of this call.
    # @return [self]
    def zunionstore_weighed(key=nil,sets=nil,options=nil)
      if key.class == String && sets.class == Hash && options.class == Symbol && block_given?
        @j_del.java_method(:zunionstoreWeighed, [Java::java.lang.String.java_class,Java::JavaUtil::Map.java_class,Java::IoVertxRedisOp::AggregateOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,Hash[sets.map { |k,v| [k,::Vertx::Util::Utils.to_double(v)] }],Java::IoVertxRedisOp::AggregateOptions.valueOf(options),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zunionstore_weighed(key,sets,options)"
    end
    #  Incrementally iterate the keys space
    # @param [String] cursor Cursor id
    # @param [Hash] options Scan options
    # @yield Handler for the result of this call.
    # @return [self]
    def scan(cursor=nil,options=nil)
      if cursor.class == String && options.class == Hash && block_given?
        @j_del.java_method(:scan, [Java::java.lang.String.java_class,Java::IoVertxRedisOp::ScanOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(cursor,Java::IoVertxRedisOp::ScanOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling scan(cursor,options)"
    end
    #  Incrementally iterate Set elements
    # @param [String] key Key string
    # @param [String] cursor Cursor id
    # @param [Hash] options Scan options
    # @yield Handler for the result of this call.
    # @return [self]
    def sscan(key=nil,cursor=nil,options=nil)
      if key.class == String && cursor.class == String && options.class == Hash && block_given?
        @j_del.java_method(:sscan, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::ScanOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,cursor,Java::IoVertxRedisOp::ScanOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling sscan(key,cursor,options)"
    end
    #  Incrementally iterate hash fields and associated values
    # @param [String] key Key string
    # @param [String] cursor Cursor id
    # @param [Hash] options Scan options
    # @yield Handler for the result of this call.
    # @return [self]
    def hscan(key=nil,cursor=nil,options=nil)
      if key.class == String && cursor.class == String && options.class == Hash && block_given?
        @j_del.java_method(:hscan, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::ScanOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,cursor,Java::IoVertxRedisOp::ScanOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling hscan(key,cursor,options)"
    end
    #  Incrementally iterate sorted sets elements and associated scores
    # @param [String] key Key string
    # @param [String] cursor Cursor id
    # @param [Hash] options Scan options
    # @yield Handler for the result of this call.
    # @return [self]
    def zscan(key=nil,cursor=nil,options=nil)
      if key.class == String && cursor.class == String && options.class == Hash && block_given?
        @j_del.java_method(:zscan, [Java::java.lang.String.java_class,Java::java.lang.String.java_class,Java::IoVertxRedisOp::ScanOptions.java_class,Java::IoVertxCore::Handler.java_class]).call(key,cursor,Java::IoVertxRedisOp::ScanOptions.new(::Vertx::Util::Utils.to_json_object(options)),(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result != nil ? JSON.parse(ar.result.encode) : nil : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling zscan(key,cursor,options)"
    end
  end
end
