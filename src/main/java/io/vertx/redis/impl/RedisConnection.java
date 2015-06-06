package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Base class for Redis Vert.x client. Generated client would use the facilities
 * in this class to implement typed commands.
 */
class RedisConnection implements ReplyHandler {

  private static final Logger log = LoggerFactory.getLogger(RedisConnection.class);

  private final Queue<Handler<Reply>> repliesQueue = new ConcurrentLinkedQueue<>();
  private final ReplyParser replyParser = new ReplyParser(this);
  private final NetClient client;
  private final RedisSubscriptions subscriptions;
  private final String host;
  private final int port;
  private final Handler<Void> closedHandler;

  private volatile NetSocket netSocket;

  public RedisConnection(Vertx vertx, String host, int port, RedisSubscriptions subscriptions, Handler<Void> closedHandler) {
    this.host = host;
    this.port = port;
    this.subscriptions = subscriptions;
    this.closedHandler = closedHandler;
    this.client = vertx.createNetClient(new NetClientOptions());
  }

  void connect(final Handler<AsyncResult<Void>> resultHandler) {
    replyParser.reset();

    client.connect(port, host, asyncResult -> {
      if (asyncResult.failed()) {
        resultHandler.handle(Future.failedFuture(asyncResult.cause()));
      } else {
        netSocket = asyncResult.result();
        netSocket.handler(replyParser);
        netSocket.closeHandler(closedHandler);
        resultHandler.handle(Future.succeededFuture());
      }
    });
  }

  void disconnect(Handler<AsyncResult<Void>> handler) {
    netSocket.close();
    client.close();
    handler.handle(new RedisAsyncResult<>(null, null));
  }

  // Redis 'subscribe', 'unsubscribe', 'psubscribe' and 'punsubscribe' commands can have multiple (including zero) repliesQueue
  // See http://redis.io/topics/pubsub
  // In all cases we want to have a handler to report errors
  void send(final Command command) {

    // The order read must match the order written, vertx guarantees
    // that this is only called from a single thread.
    command.writeTo(netSocket);
    for (int i = 0; i < command.getExpectedReplies(); ++i) {
      repliesQueue.offer(command.getHandler());
    }
  }

  @Override
  public void handleReply(Reply reply) {

    // Important to have this first - 'message' and 'pmessage' can be pushed at any moment,
    // so they must be filtered out before checking repliesQueue queue
    if (handlePushedPubSubMessage(reply)) {
      return;
    }

    Handler<Reply> handler = repliesQueue.poll();
    if (handler != null) {
      // handler waits for this response
      handler.handle(reply);
      return;
    }

    throw new RuntimeException("Received a non pub/sub message without reply handler waiting:" + reply.toString());
  }

  // Handle 'message' and 'pmessage' messages; returns true if the message was handled
  // Appropriate number of handlers for 'subscribe', 'unsubscribe', 'psubscribe' and 'punsubscribe' is inserted when these commands are sent
  // See http://redis.io/topics/pubsub
  boolean handlePushedPubSubMessage(Reply reply) {
    // Pub/sub messages are always multi-bulk
    if (reply.is('*')) {
      Reply[] data = (Reply[]) reply.data();
      if (data != null) {
        // message
        if (data.length == 3) {
          if (data[0].is('$') && "message".equals(data[0].asType(String.class))) {
            String channel = data[1].asType(String.class);
            MessageHandler handler = subscriptions.getChannelHandler(channel);
            if (handler != null) {
              handler.handle(channel, data);
            }
            // It is possible to get a message after removing subscription in the client but before Redis command executes,
            // so ignoring message here (consumer already is not interested in it)
            return true;
          }
        }
        // pmessage
        else if (data.length == 4) {
          if (data[0].is('$') && "pmessage".equals(data[0].asType(String.class))) {
            String pattern = data[1].asType(String.class);
            MessageHandler handler = subscriptions.getPatternHandler(pattern);
            if (handler != null) {
              handler.handle(pattern, data);
            }
            // It is possible to get a message after removing subscription in the client but before Redis command executes,
            // so ignoring message here (consumer already is not interested in it)
            return true;
          }
        }
      }
    }
    return false;
  }
}
