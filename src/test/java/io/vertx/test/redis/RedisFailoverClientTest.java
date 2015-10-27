package io.vertx.test.redis;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisFailoverClient;
import io.vertx.redis.utils.ConfigConstants;

import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

/**
 * This test relies on a Redis server, by default it will start and stop a Redis server
 */
public class RedisFailoverClientTest extends RedisFailoverClientTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        host = InetAddress.getLocalHost().getHostAddress();
        // create config for failover client
        JsonArray sentinels = new JsonArray();
        for (int i = 0; i < 3 ; i++) {
            JsonObject sentinel =new JsonObject();
            sentinel.put("host", host);
            sentinel.put("port", DEFAULT_SENTINEL_PORT + i);
            sentinels.add(sentinel);
        }

        JsonObject config = new JsonObject();
        config.put(ConfigConstants.SENTINELS, sentinels);

        failoverClient = new RedisFailoverClient(vertx, config);

        // sleeping for 1 second for client to refresh once.
        Thread.sleep(1000L);
    }

    @Override
    public void tearDown() throws Exception {
        // close the failover client
        CountDownLatch latch = new CountDownLatch(1);
        failoverClient.close(asyncResult -> {
            latch.countDown();
        });

        awaitLatch(latch);
        super.tearDown();
    }
}
