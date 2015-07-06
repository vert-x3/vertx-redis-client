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
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Base class for Redis Vert.x client. Generated client would use the facilities
 * in this class to implement typed commands.
 */
class RedisConnection {

  private static final Logger log = LoggerFactory.getLogger(RedisConnection.class);

  // there are 2 queues:
  // pending: commands that have not yet been sent to the server
  private final Queue<Command<?>> pending = new ConcurrentLinkedQueue<>();
  // waiting: commands that have been sent but not answered
  private final Queue<Command<?>> waiting = new ConcurrentLinkedQueue<>();

  private final ReplyParser replyParser;

  private final Vertx vertx;
  private final NetClient client;
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

  private String auth;
  private String select;


  /**
   * Create a RedisConnection.
   *
   * A Redis connection should be used for normal actions, i.e.: not for pub/sub
   *
   * @param vertx
   * @param client
   * @param host
   * @param port
   */
  public RedisConnection(Vertx vertx, NetClient client, String host, int port) {
    this.vertx = vertx;
    this.client = client;
    this.host = host;
    this.port = port;

    this.replyParser = new ReplyParser(this::handleReply);
  }

  /**
   * Create a Pub/Sub connection.
   *
   * @param vertx
   * @param client
   * @param host
   * @param port
   * @param subscriptions
   */
  public RedisConnection(Vertx vertx, NetClient client, String host, int port, RedisSubscriptions subscriptions) {
    this.vertx = vertx;
    this.client = client;
    this.host = host;
    this.port = port;

    this.replyParser = new ReplyParser(reply -> {
      // Pub/sub messages are always multi-bulk
      if (reply.is('*')) {
        Reply[] data = (Reply[]) reply.data();
        if (data != null) {
          // message
          if (data.length == 3) {
            if (data[0].is('$') && "message".equals(data[0].asType(String.class))) {
              String channel = data[1].asType(String.class);
              subscriptions.handleChannel(channel, data);
              return;
            }
          }
          // pmessage
          else if (data.length == 4) {
            if (data[0].is('$') && "pmessage".equals(data[0].asType(String.class))) {
              String pattern = data[1].asType(String.class);
              subscriptions.handlePattern(pattern, data);
              return;
            }
          }
        }
      }

      // fallback to normal handler
      handleReply(reply);
    });
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
              .closeHandler(v -> {
                // should clean up queues
                Command command;
                // clean up any pending command
                while ((command = pending.poll()) != null) {
                  command.handle(Future.failedFuture("Connection closed!"));
                }
                // clean up any waiting command
                while ((command = waiting.poll()) != null) {
                  command.handle(Future.failedFuture("Connection closed!"));
                }

                state = State.DISCONNECTED;
              })
              .exceptionHandler(e -> {
                // should clean up queues
                Command command;
                // clean up any pending command
                while ((command = pending.poll()) != null) {
                  command.handle(Future.failedFuture(e));
                }
                // clean up any waiting command
                while ((command = waiting.poll()) != null) {
                  command.handle(Future.failedFuture(e));
                }

                state = State.DISCONNECTED;
              });

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

  /**
   * Sends a message to redis, if the connection is not active then the command is queued for processing and the
   * procedure to start a connection is started.
   *
   * While this procedure is going on (CONNECTING) incomming commands are queued.
   *
   * @param command
   */
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

  private void handleReply(Reply reply) {
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
  }
}