/**
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.impl;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.redis.RedisOptions;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Base class for Redis Vert.x client. Generated client would use the facilities
 * in this class to implement typed commands.
 */
class RedisConnection {

  private final Context context;

  private static final Logger log = LoggerFactory.getLogger(RedisConnection.class);

  /**
   * there are 2 queues, one for commands not yet sent over the wire to redis and another for commands already sent to
   * redis. At start up it expected that until the connection handshake is complete the pending queue will grow and once
   * the handshake completes it will be empty while the second one will be in constant movement.
   *
   * Since the client works **ALWAYS** in pipeline mode the order of adding and removing elements to the queues is
   * crucial. A command is sent only when its reply handler or handlers are added to any of the queues and the command
   * is send to the wire.
   *
   * For this reason we must **ALWAYS** synchronize the access to the queues and writes to the socket.
   */
  // pending: commands that have not yet been sent to the server
  private final Queue<Command<?>> pending = new LinkedList<>();
  // waiting: commands that have been sent but not answered
  private final Queue<Command<?>> waiting = new LinkedList<>();

  private final ReplyParser replyParser;

  private final NetClient client;
  private final RedisOptions config;

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

  private final AtomicReference<State> state = new AtomicReference<>(State.DISCONNECTED);

  private volatile NetSocket netSocket;

  /**
   * Create a RedisConnection.
   */
  public RedisConnection(Vertx vertx, RedisOptions config, RedisSubscriptions subscriptions) {
    this.context = vertx.getOrCreateContext();
    this.config = config;

    // create a netClient for the connection
    client = vertx.createNetClient(new NetClientOptions()
        .setTcpKeepAlive(config.isTcpKeepAlive())
        .setTcpNoDelay(config.isTcpNoDelay()));

    if (subscriptions != null) {
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

    } else {
      this.replyParser = new ReplyParser(this::handleReply);
    }
  }

  private void connect() {
    if (state.compareAndSet(State.DISCONNECTED, State.CONNECTING)) {
      replyParser.reset();

      client.connect(config.getPort(), config.getHost(), asyncResult -> {
        //context = vertx.getOrCreateContext();

        if (asyncResult.failed()) {
          runOnContext(v -> {
            state.set(State.ERROR);

            // clean up any waiting command
            clearQueue(waiting, asyncResult.cause());
            // clean up any pending command
            clearQueue(pending, asyncResult.cause());

            // close the socket if previously connected
            if (netSocket != null) {
              netSocket.close();
            }

            state.set(State.DISCONNECTED);
          });
        } else {
          netSocket = asyncResult.result()
              .handler(replyParser)
              .closeHandler(v -> runOnContext(v0 -> {
                state.set(State.ERROR);
                // clean up any waiting command
                clearQueue(waiting, "Connection closed");
                // clean up any pending command
                clearQueue(pending, "Connection closed");

                state.set(State.DISCONNECTED);
              }))
              .exceptionHandler(e -> runOnContext(v0 -> {
                state.set(State.ERROR);
                // clean up any waiting command
                clearQueue(waiting, e);
                // clean up any pending command
                clearQueue(pending, e);

                netSocket.close();
                state.set(State.DISCONNECTED);
              }));

          runOnContext(v -> {
            // clean up any waiting command
            clearQueue(waiting, "Connection lost");

            // handle the connection handshake
            doAuth();
          });
        }
      });
    }
  }

  void disconnect(Handler<AsyncResult<Void>> closeHandler) {
    switch (state.get()) {
      case CONNECTED:
      case CONNECTING:
        final Command<Void> cmd = new Command<>(context, RedisCommand.QUIT, null, Charset.defaultCharset(), ResponseTransform.NONE, Void.class);

        cmd.handler(v -> {
          // at this we force the state to error so any incoming command will not start a connection
          runOnContext(v0 -> {
            state.set(State.ERROR);

            // clean up any waiting command
            clearQueue(waiting, "Connection closed");
            // clean up any pending command
            clearQueue(pending, "Connection closed");

            netSocket.close();
            state.set(State.DISCONNECTED);

            closeHandler.handle(Future.succeededFuture());
          });
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
   * @param command the redis command to send
   */
  void send(final Command<?> command) {
    switch (state.get()) {
      case CONNECTED:
        // write to the socket in the netSocket context
        runOnContext(v -> {
          // The order read must match the order written, vertx guarantees
          // that this is only called from a single thread.
          for (int i = 0; i < command.getExpectedReplies(); ++i) {
            waiting.add(command);
          }

          command.writeTo(netSocket);
        });
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

  /**
   * Once a socket connection is established one needs to authenticate if there is a password
   */
  private void doAuth() {
    if (config.getAuth() != null) {
      // we need to authenticate first
      final List<Object> args = new ArrayList<>();
      args.add(config.getAuth());

      Command<String> authCmd = new Command<>(context, RedisCommand.AUTH, args, Charset.forName(config.getEncoding()), ResponseTransform.NONE, String.class).handler(auth -> {
        if (auth.failed()) {
          // clean up any waiting command
          clearQueue(pending, auth.cause());
          netSocket.close();
          state.set(State.DISCONNECTED);
        } else {
          // auth success, proceed with select
          doSelect();
        }
      });

      // write to the socket in the netSocket context
      runOnContext(v -> {
        // queue it
        waiting.add(authCmd);
        authCmd.writeTo(netSocket);
      });
    } else {
      // no auth, proceed with select
      doSelect();
    }
  }

  private void doSelect() {
    // optionally there could be a select command
    if (config.getSelect() != null) {

      final List<Object> args = new ArrayList<>();
      args.add(config.getSelect());

      Command<String> selectCmd = new Command<>(context, RedisCommand.SELECT, args, Charset.forName(config.getEncoding()), ResponseTransform.NONE, String.class).handler(select -> {
        if (select.failed()) {
          // clean up any waiting command
          clearQueue(pending, select.cause());

          netSocket.close();
          state.set(State.DISCONNECTED);
        } else {
          // select success, proceed with resend
          resendPending();
        }
      });

      // write to the socket in the netSocket context
      runOnContext(v -> {
        // queue it
        waiting.add(selectCmd);
        selectCmd.writeTo(netSocket);
      });
    } else {
      // no select, proceed with resend
      resendPending();
    }
  }

  private void resendPending() {
    runOnContext(v -> {
      Command<?> command;
      state.set(State.CONNECTED);

      // we are connected so clean up the pending queue
      while ((command = pending.poll()) != null) {
        // The order read must match the order written, vertx guarantees
        // that this is only called from a single thread.
        for (int i = 0; i < command.getExpectedReplies(); ++i) {
          waiting.add(command);
        }

        // write to the socket in the netSocket context
        command.writeTo(netSocket);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void handleReply(Reply reply) {

    runOnContext(v -> {
      final Command cmd = waiting.poll();

      if (cmd != null) {
        switch (reply.type()) {
          case '-': // Error
            cmd.handle(Future.failedFuture(reply.asType(String.class)));
            return;
          case '+':   // Status
            switch (cmd.responseTransform()) {
              case ARRAY:
                cmd.handle(Future.succeededFuture(new JsonArray().add(reply.asType(String.class))));
                break;
              default:
                cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType())));
                break;
            }
            return;
          case '$':  // Bulk
            switch (cmd.responseTransform()) {
              case ARRAY:
                cmd.handle(Future.succeededFuture(new JsonArray().add(reply.asType(String.class, cmd.encoding()))));
                break;
              case INFO:
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
                break;
              default:
                cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType(), cmd.encoding())));
                break;
            }
            return;
          case '*': // Multi
            switch (cmd.responseTransform()) {
              case HASH:
                cmd.handle(Future.succeededFuture(reply.asType(JsonObject.class, cmd.encoding())));
                break;
              default:
                cmd.handle(Future.succeededFuture(reply.asType(JsonArray.class, cmd.encoding())));
                break;
            }
            return;
          case ':':   // Integer
            switch (cmd.responseTransform()) {
              case ARRAY:
                cmd.handle(Future.succeededFuture(new JsonArray().add(reply.asType(Long.class))));
                break;
              default:
                cmd.handle(Future.succeededFuture(reply.asType(cmd.returnType())));
                break;
            }
            return;
          default:
            cmd.handle(Future.failedFuture("Unknown message type"));
        }
      } else {
        log.error("No handler waiting for message: " + reply.asType(String.class));
      }
    });
  }

  private void runOnContext(Handler<Void> handler) {
    if (Vertx.currentContext() == context) {
      handler.handle(null);
    } else {
      context.runOnContext(handler);
    }
  }

  private static void clearQueue(Queue<Command<?>> q, String message) {
    Command<?> cmd;

    // clean up any pending command
    while ((cmd = q.poll()) != null) {
      cmd.handle(Future.failedFuture(message));
    }
  }

  private static void clearQueue(Queue<Command<?>> q, Throwable cause) {
    Command<?> cmd;

    // clean up any pending command
    while ((cmd = q.poll()) != null) {
      cmd.handle(Future.failedFuture(cause));
    }
  }
}