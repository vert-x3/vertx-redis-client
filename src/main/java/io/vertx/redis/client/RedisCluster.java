package io.vertx.redis.client;

import io.vertx.core.Future;
import io.vertx.redis.client.impl.RedisClusterImpl;

import java.util.List;

/**
 * Utilities for Redis cluster. Calling {@code create()} with an instance of {@link Redis}
 * or {@link RedisConnection} that are not Redis cluster client/connection leads to
 * an exception.
 *
 * @see #onAllNodes(Request)
 * @see #onAllMasterNodes(Request)
 * @see #groupByNodes(List)
 */
public interface RedisCluster {
  static RedisCluster create(Redis client) {
    return new RedisClusterImpl(client);
  }

  static RedisCluster create(RedisConnection connection) {
    return new RedisClusterImpl(connection);
  }

  /**
   * Runs the {@code request} against all nodes in the cluster.
   * Returns a future that completes with a list of responses, one from each
   * node, or failure when one of the operations fails. Note that in case
   * of a failure, there are no guarantees that the request was or wasn't
   * executed successfully on other Redis cluster nodes. No result order
   * is guaranteed either.
   *
   * @param request the request, must not be {@code null}
   * @return the future result, never {@code null}
   */
  Future<List<Response>> onAllNodes(Request request);

  /**
   * Runs the {@code request} against all <em>master</em> nodes in the cluster.
   * Returns a future that completes with a list of responses, one from each
   * master node, or failure when one of the operations fails. Note that in case
   * of a failure, there are no guarantees that the request was or wasn't
   * executed successfully on other Redis cluster master nodes. No result order
   * is guaranteed either.
   *
   * @param request the request, must not be {@code null}
   * @return the future result, never {@code null}
   */
  Future<List<Response>> onAllMasterNodes(Request request);

  /**
   * Groups the {@code requests} such that all requests in each inner list in the result
   * are guaranteed to be sent to the same Redis <em>master</em> node.
   * <p>
   * If the cluster client was not created with {@link RedisReplicas#NEVER} and
   * the commands are executed individually (not using {@link RedisConnection#batch(List) batch()}),
   * it is possible that the commands will be spread across different replicas
   * of the same master node.
   * <p>
   * If any of the {@code requests} don't have keys and hence are targeted at a random
   * node, all such requests shall be grouped into an extra single list for which
   * the above guarantee does not apply. If any of the {@code requests} includes multiple
   * keys that belong to different master nodes, the resulting future will fail.
   * <p>
   * Note that this method is only reliable in case the Redis cluster is in a stable
   * state. In case of resharding, failover or in general any change of cluster topology,
   * there are no guarantees on the validity of the result.
   *
   * @param requests the requests, must not be {@code null}
   * @return the requests grouped by the cluster node assignment
   */
  Future<List<List<Request>>> groupByNodes(List<Request> requests);
}
