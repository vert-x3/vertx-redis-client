package io.vertx.redis.client;

import io.vertx.codegen.annotations.DataObject;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A result of {@link RedisCluster#groupByNodes(List)}.
 *
 * @see #getKeyed()
 * @see #getUnkeyed()
 */
@DataObject
public class RequestGrouping {
  private final Collection<List<Request>> keyed;
  private final List<Request> unkeyed;

  public RequestGrouping(Collection<List<Request>> keyed, List<Request> unkeyed) {
    this.keyed = Objects.requireNonNull(keyed);
    this.unkeyed = Objects.requireNonNull(unkeyed);
  }

  /**
   * Returns a collection of request groups such that all requests in each group are
   * guaranteed to be sent to the same master node.
   * <p>
   * Does not include any request that doesn't specify a key; use {@link #getUnkeyed()}
   * to get those.
   */
  public Collection<List<Request>> getKeyed() {
    return keyed;
  }

  /**
   * Returns a collection of requests that do not specify a key and would therefore
   * be executed on random node.
   */
  public List<Request> getUnkeyed() {
    return unkeyed;
  }
}
