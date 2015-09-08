package io.vertx.redis.impl;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.RedisCommand;

import java.nio.charset.Charset;
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

  private final NetClient client;
  private final String host;
  private final int port;

  private enum State {
    /**
     * The connection is not active. The is a stop state.
     */
    DISCONNECTED,
    /**
     * The connection is in transit, from here it can become connected or and error can occur.
     */
    CONNECTING,
    /**
     * Connection is active from here it can become an error or disconnected.
     */
    CONNECTED,
    /**
     * Connection problem
     */
    ERROR
  }

  private volatile State state = State.DISCONNECTED;
  private volatile NetSocket netSocket;
  private volatile Context context;

//  private String auth;
//  private String select;

  /**
   * Create a RedisConnection.
   * <p>
   * A Redis connection should be used for normal actions, i.e.: not for pub/sub
   *
   * @param client
   * @param host
   * @param port
   */
  public RedisConnection(NetClient client, String host, int port) {
    this.client = client;
    this.host = host;
    this.port = port;

    this.replyParser = new ReplyParser(this::handleReply);
  }

  /**
   * Create a Pub/Sub connection.
   *
   * @param client
   * @param host
   * @param port
   * @param subscriptions
   */
  public RedisConnection(NetClient client, String host, int port, RedisSubscriptions subscriptions) {
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
        context = Vertx.currentContext();

        if (asyncResult.failed()) {
          state = State.ERROR;

          Command<?> command;
          // clean up any waiting command
          while ((command = waiting.poll()) != null) {
            command.handle(Future.failedFuture(asyncResult.cause()));
          }
          // clean up any pending command
          while ((command = pending.poll()) != null) {
            command.handle(Future.failedFuture(asyncResult.cause()));
          }

          // close the socket if previously connected
          if (netSocket != null) {
            netSocket.close();
          }

          state = State.DISCONNECTED;

        } else {
          netSocket = asyncResult.result()
              .handler(replyParser)
              .closeHandler(v -> {
                state = State.ERROR;

                // should clean up queues
                Command<?> command;
                // clean up any waiting command
                while ((command = waiting.poll()) != null) {
                  command.handle(Future.failedFuture("Connection closed!"));
                }
                // clean up any pending command
                while ((command = pending.poll()) != null) {
                  command.handle(Future.failedFuture("Connection closed!"));
                }

                netSocket.close();
                state = State.DISCONNECTED;
              })
              .exceptionHandler(e -> {
                state = State.ERROR;

                // should clean up queues
                Command<?> command;
                // clean up any waiting command
                while ((command = waiting.poll()) != null) {
                  command.handle(Future.failedFuture(e));
                }
                // clean up any pending command
                while ((command = pending.poll()) != null) {
                  command.handle(Future.failedFuture(e));
                }

                netSocket.close();
                state = State.DISCONNECTED;
              });

          state = State.CONNECTED;

          Command<?> command;

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

            // write to the socket in the netSocket context
            final Command<?> _command = command;
            context.runOnContext(v -> _command.writeTo(netSocket));
          }
        }
      });
    }
  }

  synchronized void disconnect(Handler<AsyncResult<Void>> closeHandler) {
    switch (state) {
      case CONNECTED:
      case CONNECTING:
        final Command<Void> cmd = new Command<>(RedisCommand.QUIT, null, Charset.defaultCharset(), ResponseTransform.NONE, Void.class);

        cmd.handler(v -> {
          // at this we force the state to error so any incoming command will not start a connection
          state = State.ERROR;

          // should clean up queues
          Command<?> command;
          // clean up any waiting command
          while ((command = waiting.poll()) != null) {
            command.handle(Future.failedFuture("Connection closed!"));
          }
          // clean up any pending command
          while ((command = pending.poll()) != null) {
            command.handle(Future.failedFuture("Connection closed!"));
          }

          netSocket.close();
          state = State.DISCONNECTED;

          closeHandler.handle(Future.succeededFuture());
        });

        send(cmd);
        break;

      case ERROR:
        // eventually will become DISCONNECTED
      case DISCONNECTED:
        closeHandler.handle(Future.succeededFuture());
        break;
    }
  }

  /**
   * Sends a message to redis, if the connection is not active then the command is queued for processing and the
   * procedure to start a connection is started.
   * <p>
   * While this procedure is going on (CONNECTING) incomming commands are queued.
   *
   * @param command
   */
  void send(final Command<?> command) {
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
      case ERROR:
        pending.add(command);
        break;
      case DISCONNECTED:
        pending.add(command);
        connect();
        break;
    }
  }

  private void handleReply(Reply reply) {
    final Command cmd = waiting.poll();

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
      log.error("No handler waiting for message: " + reply.asType(String.class));
    }
  }
}