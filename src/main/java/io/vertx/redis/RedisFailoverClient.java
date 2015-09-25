package io.vertx.redis;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.sentinel.RedisSentinel;
import io.vertx.redis.utils.ConfigConstants;
import io.vertx.redis.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fail over client class for the redis sentinel
 *
 * <p>
 * Keeps track of redis masters and does automatic fail over to active master
 * in case of failure.
 * <p>
 *
 * Also does load balancing across redis masters using random master pickup.
 */
public class RedisFailoverClient {

    private static final Logger log = LoggerFactory.getLogger(RedisFailoverClient.class);

    private static final int REFRESH_INTERVAL_MILLIS = 1000;
    private static final int MASTERS_COMMAND_TIMEOUT_IN_MILLIS = 3000;

    private static final String IP = "ip";
    private static final String PORT = "port";

    private Map<Address, RedisClient> masterConnections = new ConcurrentHashMap<>();
    private List<RedisSentinel> sentinels = new ArrayList<>();

    private Vertx vertx;
    private JsonObject config;

    Random random = new Random();

    public RedisFailoverClient(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;

        JsonArray sentinelsArray = config.getJsonArray(ConfigConstants.SENTINELS);

        // collect the sentinel addresses from configuration
        for (int pos = 0; pos < sentinelsArray.size(); pos++) {
            JsonObject sentinel = sentinelsArray.getJsonObject(pos);
            sentinels.add(RedisSentinel.create(vertx, sentinel));
        }

        // force the update for first time and then set up refresher
        updateMastersList(handler -> {
            refresh();
        });
    }

    /**
     * Refresher where each refresh run drives the timer for next with
     * interval set to REFRESH_INTERVAL_MILLIS
     */
    private void refresh() {
        vertx.setTimer(REFRESH_INTERVAL_MILLIS, time -> {
            updateMastersList(handler -> {
                if (!handler.succeeded()) {
                    log.error("Failed to refresh master list, ex: ", handler.cause());
                }
                //set up next refresh
                refresh();
            });
        });
    }

    /**
     * Gets redis configuration for host:port
     *
     * from RedisFailoverClient config
     *
     * @param host - hostname of the redis server
     * @param port - port to connect
     */
    private JsonObject getConfig(String host, int port) {
        JsonObject jsonObject = new JsonObject();

        if (config.containsKey(ConfigConstants.ENCODING)) {
            jsonObject.put(ConfigConstants.ENCODING, config.getString(ConfigConstants.ENCODING));
        }

        if (config.containsKey(ConfigConstants.ADDRESS)) {
            jsonObject.put(ConfigConstants.ADDRESS, config.getString(ConfigConstants.ADDRESS));
        }

        if (config.containsKey(ConfigConstants.TCP_KEEP_ALIVE)) {
            jsonObject.put(ConfigConstants.TCP_KEEP_ALIVE, config.getBoolean(ConfigConstants.TCP_KEEP_ALIVE));
        }

        if (config.containsKey(ConfigConstants.TCP_NO_DELAY)) {
            jsonObject.put(ConfigConstants.TCP_NO_DELAY, config.getBoolean(ConfigConstants.TCP_NO_DELAY));
        }

        jsonObject.put(ConfigConstants.HOST, host)
                .put(ConfigConstants.PORT, port);

        return jsonObject;
    }

    /**
     * Queries Redis sentinel servers and build or updates the
     * list of redis masters available.
     */
    private void updateMastersList(Handler<AsyncResult<Boolean>> handler) {
        log.info("Refreshing list of available masters");

        Set<Address> addresses = new HashSet<>();

        AtomicInteger count = new AtomicInteger(sentinels.size());
        try {
            // loop through each sentinel client and collect all masters
            for (RedisSentinel sentinelClient : sentinels) {

                //set up handler
                Handler<AsyncResult<JsonArray>> asyncResultHandler = jsonObjectAsyncResult -> {
                    count.decrementAndGet();
                    if (jsonObjectAsyncResult.succeeded()) {
                        JsonArray masters = jsonObjectAsyncResult.result();

                        for (int pos = 0; pos < masters.size(); pos++) {
                            JsonObject configuration = JsonUtils.convertToJsonObject(masters.getJsonArray(pos));
                            String host = configuration.getString(IP);
                            Integer port = Integer.valueOf(configuration.getString(PORT));

                            addresses.add(new Address(host, port));
                        }
                    } else {
                        log.error(jsonObjectAsyncResult.cause().getMessage());
                    }

                    // Done with all sentinel calls.
                    if (count.get() == 0) {
                        // Detect if we lost any hosts
                        for (Map.Entry<Address, RedisClient> connection : masterConnections.entrySet()) {
                            if (!addresses.contains(connection.getKey())) {
                                // remove from the list
                                log.info("Lost redis server " + String.format("%s:%s", connection.getKey().host, connection.getKey().port));
                                masterConnections.remove(connection.getKey());
                            }
                        }

                        // Detect if we saw new host
                        for (Address address : addresses) {
                            if (!masterConnections.containsKey(address)) {
                                log.info("Discovered redis server " + String.format("%s:%s", address.host, address.port));
                                masterConnections.put(address, createClient(address));
                            }
                        }

                        log.info("Number of available redis server:" + masterConnections.size() + " servers: " + getMasterAddressList());
                        handler.handle(Future.factory.completedFuture(true));
                    }
                };

                // set up timer
                final long timerId = vertx.setTimer(MASTERS_COMMAND_TIMEOUT_IN_MILLIS, t -> asyncResultHandler.handle(Future.failedFuture("Timed out on response")));

                // handle the response
                sentinelClient.masters(res -> {
                    // check if timer triggered
                    if (vertx.cancelTimer(timerId)) {
                        asyncResultHandler.handle(res);
                    }
                });
            }
        } catch (Exception ex) {
            handler.handle(Future.factory.completedFuture(ex));
        }
    }

    /**
     * Gets the redis client from list of available redis
     *+
     * @return client to redis server
     */
    public RedisClient getClient() {
        if (masterConnections.size() == 0) {
            log.error("No active redis masters found");
            return null;
        }

        // We also extend the selection policy to do round robin
        int randomIndex = random.nextInt(masterConnections.size());
        RedisClient chosen = null;

        int pos = 0;
        for (Map.Entry<Address, RedisClient> client : masterConnections.entrySet()) {
            /*
             * Set it to first client to handle the cases
             * when we lose one of the masters from the set and trying to look up
             * last element in the set.
             */
            if (client == null) {
                chosen = client.getValue();
            }

            if (pos == randomIndex) {
                chosen = client.getValue();
                break;
            }
        }

        return chosen;
    }

    private RedisClient createClient(Address address) {
        return RedisClient.create(vertx, getConfig(address.host, address.port));
    }

    private String getMasterAddressList() {
        StringJoiner joiner = new StringJoiner(",");

        for (Map.Entry<Address, RedisClient> connection : masterConnections.entrySet()) {
            joiner.add(connection.getKey().toString());
        }

        return joiner.toString();
    }

    private class Address {
        public String host;
        public int port;

        public Address(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public String toString() {
            return host + ':' + port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Address address = (Address) o;

            if (port != address.port) {
                return false;
            }
            return !(host != null ? !host.equals(address.host) : address.host != null);

        }

        @Override
        public int hashCode() {
            int result = host != null ? host.hashCode() : 0;
            result = 31 * result + port;
            return result;
        }
    }

    public void close(Handler<AsyncResult<Void>> handler) {
        AtomicInteger count = new AtomicInteger(masterConnections.size() + sentinels.size());

        // closing connections to masters
        for (Map.Entry<Address, RedisClient> client : masterConnections.entrySet()) {
            client.getValue().close(asyncResult -> {
                // best effort to close redis clients
                if (count.decrementAndGet() == 0) {
                    handler.handle(Future.factory.completedFuture());
                }
            });
        }

        // closing connections to sentinels.
        for(RedisSentinel sentinel : sentinels) {
            sentinel.close(asyncResult -> {
                // best effort to close redis sentinels
                if (count.decrementAndGet() == 0) {
                    handler.handle(Future.factory.completedFuture());
                }
            });
        }
    }
}
