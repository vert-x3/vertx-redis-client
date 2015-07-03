package io.vertx.redis.impl;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Base class for Redis Vert.x client. Generated client would use the facilities
 * in this class to implement typed commands.
 */
class RedisConnection {

  private static final Logger log = LoggerFactory.getLogger(RedisConnection.class);

  // there are 2 queues:
  // pending: commands that have not yet been sent to the server
  private final Queue<Command<?>> pending = new LinkedList<>();
  // waiting: commands that have been sent but not answered
  private final Queue<Command<?>> waiting = new LinkedList<>();

  private final ReplyParser replyParser;

  private final Vertx vertx;
  private final NetClient client;
  private final RedisSubscriptions subscriptions;
  private final String host;
  private final int port;

  public enum State {
    DISCONNECTED,
    CONNECTING,
    CONNECTED
  }

  private State state = State.DISCONNECTED;

  private NetSocket netSocket;
  private Context context;

  public RedisConnection(Vertx vertx, NetClient client, String host, int port, RedisSubscriptions subscriptions) {
    this.vertx = vertx;
    this.host = host;
    this.port = port;
    this.subscriptions = subscriptions;
    this.replyParser = new ReplyParser(reply -> {
      // Important to have this first - 'message' and 'pmessage' can be pushed at any moment,
      // so they must be filtered out before checking waiting queue
      if (handlePushedPubSubMessage(reply)) {
        return;
      }

      Command cmd = waiting.poll();
      if (cmd != null) {
        switch (reply.type()) {
          case '-': // Error
            cmd.handle(Future.failedFuture(reply.asType(String.class)));
            return;
          case '+':   // Status
            cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType())));
            return;
          case '$':  // Bulk
            if (cmd.responseTransform() == ResponseTransform.INFO) {
              String info = reply.asType(String.class, cmd.encoding());

              if (info == null) {
                cmd.handle(Future.succeededFuture(null));
              } else {
                String lines[] = info.split("\\r?\\n");
                JsonObject value = new JsonObject();

                JsonObject section = null;
                for (String line : lines) {
                  if (line.length() == 0) {
                    // end of section
                    section = null;
                    continue;
                  }

                  if (line.charAt(0) == '#') {
                    // begin section
                    section = new JsonObject();
                    // create a sub key with the section name
                    value.put(line.substring(2).toLowerCase(), section);
                  } else {
                    // entry in section
                    int split = line.indexOf(':');
                    if (section == null) {
                      value.put(line.substring(0, split), line.substring(split + 1));
                    } else {
                      section.put(line.substring(0, split), line.substring(split + 1));
                    }
                  }
                }
                cmd.handle(Future.succeededFuture(value));
              }
            } else {
              cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType(), cmd.encoding())));
            }
            return;
          case '*': // Multi
            if (cmd.responseTransform() == ResponseTransform.ARRAY_TO_OBJECT) {
              cmd.handle(Future.succeededFuture(reply.asType(JsonObject.class, cmd.encoding())));
            } else {
              cmd.handle(Future.succeededFuture(reply.asType(JsonArray.class, cmd.encoding())));
            }
            return;
          case ':':   // Integer
            cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType())));
            return;
          default:
            cmd.handle(Future.failedFuture("Unknown message type"));
        }
      } else {
        log.error("No handler waiting for message:" + reply.toString());
      }
    });

    this.client = client;
  }

  private synchronized void connect() {
    if (state == State.DISCONNECTED) {
      state = State.CONNECTING;

      replyParser.reset();

      client.connect(port, host, asyncResult -> {
        context = vertx.getOrCreateContext();

        if (asyncResult.failed()) {
          Command command;
          // clean up any pending command
          while ((command = pending.poll()) != null) {
            command.handle(Future.failedFuture(asyncResult.cause()));
          }
          // clean up any waiting command
          while ((command = waiting.poll()) != null) {
            command.handle(Future.failedFuture(asyncResult.cause()));
          }

          state = State.DISCONNECTED;
        } else {
          netSocket = asyncResult.result()
              .handler(replyParser)
              .closeHandler(v -> state = State.DISCONNECTED)
              .exceptionHandler(e -> state = State.DISCONNECTED);

          Command command;

          // clean up any waiting command
          while ((command = waiting.poll()) != null) {
            command.handle(Future.failedFuture("Connection lost"));
          }

          // we are connected so clean up the pending queue
          while ((command = pending.poll()) != null) {
            // The order read must match the order written, vertx guarantees
            // that this is only called from a single thread.
            for (int i = 0; i < command.getExpectedReplies(); ++i) {
              waiting.add(command);
            }
            command.writeTo(netSocket);
          }

          state = State.CONNECTED;
        }
      });
    }
  }

  synchronized void disconnect() {
    if (state != State.DISCONNECTED) {
      if (netSocket != null) {
        netSocket.close();
      }
      state = State.DISCONNECTED;
    }
  }

  // Redis 'subscribe', 'unsubscribe', 'psubscribe' and 'punsubscribe' commands can have multiple (including zero) waiting
  // See http://redis.io/topics/pubsub
  // In all cases we want to have a handler to report errors
  synchronized void send(final Command<?> command) {
    switch (state) {
      case CONNECTED:
        // The order read must match the order written, vertx guarantees
        // that this is only called from a single thread.
        for (int i = 0; i < command.getExpectedReplies(); ++i) {
          waiting.add(command);
        }
        // write to the socket in the netSocket context
        context.runOnContext(v -> command.writeTo(netSocket));
        break;
      case CONNECTING:
        pending.add(command);
        break;
      case DISCONNECTED:
        pending.add(command);
        connect();
        break;
    }
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
              vertx.getOrCreateContext().runOnContext(v ->
                  handler.handle(channel, data));
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
              vertx.getOrCreateContext().runOnContext(v ->
                  handler.handle(pattern, data));
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
