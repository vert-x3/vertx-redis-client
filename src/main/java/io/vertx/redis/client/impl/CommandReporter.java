package io.vertx.redis.client.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.internal.VertxInternal;
import io.vertx.core.spi.metrics.ClientMetrics;
import io.vertx.core.spi.tracing.SpanKind;
import io.vertx.core.spi.tracing.TagExtractor;
import io.vertx.core.spi.tracing.VertxTracer;
import io.vertx.core.tracing.TracingPolicy;

import java.util.function.Function;

class CommandReporter {
  enum Tags {
    // Generic
    NETWORK_PEER_ADDRESS("network.peer.address", reporter-> reporter.networkPeerAddress),
    NETWORK_PEER_PORT("network.peer.port", reporter -> reporter.networkPeerPort),
    PEER_ADDRESS("peer.address", reporter -> reporter.address),
    SERVER_ADDRESS("server.address", reporter -> reporter.serverAddress),
    SERVER_PORT("server.port", reporter -> reporter.serverPort),
    SPAN_KIND("span.kind", reporter -> "client"),

    // DB
    // See https://opentelemetry.io/docs/specs/semconv/database/redis/

    DB_USER("db.user", reporter -> reporter.user),
    DB_NAMESPACE("db.namespace", reporter -> reporter.database),
    DB_QUERY_TEXT("db.query.text", reporter -> reporter.command),
    DB_SYSTEM("db.system", reporter -> "redis");

    final String name;
    final Function<CommandReporter, String> valueFunction;

    Tags(String name, Function<CommandReporter, String> valueFunction) {
      this.name = name;
      this.valueFunction = valueFunction;
    }
  }

  private static final TagExtractor<CommandReporter> REQUEST_TAG_EXTRACTOR = new TagExtractor<>() {
    private final Tags[] TAGS = Tags.values();

    @Override
    public int len(CommandReporter obj) {
      return TAGS.length;
    }

    @Override
    public String name(CommandReporter obj, int index) {
      return TAGS[index].name;
    }

    @Override
    public String value(CommandReporter obj, int index) {
      return TAGS[index].valueFunction.apply(obj);
    }
  };

  private final VertxTracer tracer;
  private final ClientMetrics metrics;
  private final Context context;
  private final TracingPolicy tracingPolicy;
  private final String command;
  private final String address;
  private final String user;
  private final String database;
  private final String networkPeerAddress;
  private final String networkPeerPort;
  private final String serverAddress;
  private final String serverPort;

  private Object trace;
  private Object metric;

  CommandReporter(RedisConnectionInternal conn, String command) {
    VertxInternal vertx = conn.vertx();
    RedisURI uri = conn.uri();
    this.tracer = vertx.tracer();
    this.metrics = conn.metrics();
    this.context = vertx.getContext();
    this.tracingPolicy = conn.tracingPolicy();
    this.command = command;
    this.address = uri.socketAddress().toString();
    this.networkPeerAddress = conn.remoteAddress().hostAddress();
    this.networkPeerPort = String.valueOf(conn.remoteAddress().port());
    this.serverAddress = uri.socketAddress().host();
    this.serverPort = String.valueOf(uri.socketAddress().port());
    this.user = uri.user();
    // the connection doesn't track the current database, so we have to report "unknown" when tainted
    this.database = conn.isTainted() ? null : (uri.select() == null ? "0" : String.valueOf(uri.select()));
  }

  public void before() {
    if (tracer != null) {
      trace = tracer.sendRequest(context, SpanKind.RPC, tracingPolicy, this, "Command", (k, v) -> {}, REQUEST_TAG_EXTRACTOR);
    }
    if (metrics != null) {
      metric = metrics.requestBegin(command, command);
      metrics.requestEnd(metric);
    }
  }

  public void after(AsyncResult<?> ar) {
    if (tracer != null) {
      tracer.receiveResponse(context, ar.succeeded() ? ar.result() : null, trace, ar.failed() ? ar.cause() : null, TagExtractor.empty());
    }
    if (metrics != null) {
      if (ar.succeeded()) {
        metrics.responseBegin(metric, null);
        metrics.responseEnd(metric);
      } else {
        metrics.requestReset(metric);
      }
    }
  }
}
