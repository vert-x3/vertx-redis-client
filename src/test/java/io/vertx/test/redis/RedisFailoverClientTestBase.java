package io.vertx.test.redis;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisFailoverClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.net.InetAddress;
import java.util.Map;

public abstract class RedisFailoverClientTestBase  extends AbstractRedisSentinelBase  {

    protected RedisFailoverClient failoverClient;

    @BeforeClass
    static public void startRedis() throws Exception {
        //determine the host address
        host = InetAddress.getLocalHost().getHostAddress();

        /*
         * Setup
         *
         * 3 Sentinels (quorum 2)
         *
         * S1, S2, S3
         *
         * 2 redis instances
         *
         * Sentinel S1 -> Redis Master M
         * Sentinel S2 -> Redis Master M
         * Sentinel S3 -> Redis Master M
         *
         * Redis Master M -> Redis Slave S
         */

        // create Redis Master M
        createRedisInstance(DEFAULT_PORT);
        instances.get(DEFAULT_PORT).start();

        // create Redis Slave S
        createSlaveRedisInstance(DEFAULT_PORT + 1, DEFAULT_PORT);
        instances.get(DEFAULT_PORT + 1).start();

        // create sentinels
        for (int i = 0; i < 3 ; i++) {
            createRedisSentinelInstance(DEFAULT_SENTINEL_PORT + i, DEFAULT_PORT);
            sentinels.get(DEFAULT_SENTINEL_PORT + i).start();
        }
    }

    @AfterClass
    static public void stopRedis() throws Exception {
        // stop sentinels
        for (Map.Entry<Integer, redis.embedded.RedisSentinel> entry : sentinels.entrySet()) {
            if (entry != null) {
                entry.getValue().stop();
            }
        }

        // stop redis instances
        for (Map.Entry<Integer, RedisServer> entry : instances.entrySet()) {
            if (entry != null) {
                entry.getValue().stop();
            }
        }
    }

    @Test
    public void testGetClient() throws Exception {
        RedisClient redisClient = failoverClient.getClient();
        redisClient.info(response -> {
            assertTrue(response.succeeded());
            assertEquals(String.valueOf(DEFAULT_PORT), response.result().getJsonObject("server").getString("tcp_port"));

            // fail the master
            instances.get(DEFAULT_PORT).stop();

            Handler<Future<Void>> blockingCodeHandler = future -> {
                // Non event loop
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    //ignoring it
                } finally {
                    future.complete();
                }
            };

            vertx.executeBlocking(blockingCodeHandler, asyncResult -> {
                // get the client to slave
                RedisClient anotherRedisClient = failoverClient.getClient();
                assertNotNull(anotherRedisClient);

                anotherRedisClient.info(response1 -> {
                    assertTrue(response1.succeeded());
                    assertEquals(String.valueOf(DEFAULT_PORT + 1), response1.result().getJsonObject("server").getString("tcp_port"));
                    testComplete();
                });
            });
        });

        await();
    }
}
