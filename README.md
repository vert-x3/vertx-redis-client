# Vert.x Redis Service

The Vert.x Redis service provides asynchronous connectivity and  to a [Redis](http://redis.io) data-structure server.
While API calls ultimately are against the Vert.x Event Bus [Vert.x](http://vertx.io), the service provides a rich API
 that allows you to develop using a the native idioms and vernacular of the Redis runtime.

The Vert.x Redis service is developed for use with Vert.x V3 and as such, is not backward compatible with a prior Vert.x
 release. Simiarly, the underlying Redis service was developed and testing with the latest stable release of Redis,
 which at the time of this writing was verstion [2.8.19](http://redis.io/download).

Keep in mind that while the Vert.x service provides support for the complete [Redis command set](http://redis.io/commands)
, a working knowledge of the Redis runtime is strongly encouraged. Where there appears to be a discrepancy between this
 documentation and the [Redis documentation set](http://redis.io/documentation), the Redis literature takes precedence.

This project is based on original work from Paulo Lopes. The original repository for the module can be found at [https://github.com/vert-x/mod-redis](https://github.com/vert-x/mod-redis). 

##Redis Service

While it is assumed you are familar with Redis, using the Redis service is straightforward

###Creating and Starting

<pre>
	RedisService service = RedisService.create(vertx, config());

    service.start(asyncResult -> {
      if (asyncResult.succeeded()) {
        String address = config().getString("address", "vertx.redis");
        ProxyHelper.registerService(RedisService.class, vertx, service, address);
        startFuture.complete();

      } else {
        startFuture.fail(asyncResult.cause());
      }
    });	
</pre>


###Usage
<pre>
    service.set(toJsonArray(mykey, "Hello"), reply -> {
	  if(reply.succeeded()) {
        redis.get(toJsonArray(mykey), reply1 -> {
        assertTrue(reply1.succeeded());
        assertEquals("Hello", reply1.result());
        testComplete();
      });	  
	 }
    });
</pre>

If you are familar with Vert.x and Redis, the above code snippets will not be unfamiliar to you. As previously mentioned, the Redis Service supports the entire Redis Command Set allowing you full control of the Redis runtime as with any other API. 


### For Further Reading

[The Redis Community](http://redis.io)

[*Carlson, Josiah* Redis in Action](http://manning.com/carlson/) 

[Vert.x Developmet Repository](https://github.com/eclipse/vert.x)

[Vert.x User Group](https://groups.google.com/forum/?hl=en#!forum/vertx)

[Vert.x Developer Group](https://groups.google.com/forum/?fromgroups#!forum/vertx-dev)

## Online Forums
[IRC Freenode](https://freenode.net) #vertx

##Contributing
As with all Vertx related projects, contributions from the community is **always** welcomed and encouraged with many thanks.


