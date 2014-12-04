package io.vertx.redis.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.*;

/**
 * Base class for Redis Vertx client. Generated client would use the facilties
 * in this class to implement typed commands.
 */
public class RedisConnection implements ReplyHandler {

    private static final Logger log = LoggerFactory.getLogger(RedisConnection.class);

    private final Queue<Handler<Reply>> repliesQueue = new LinkedList<>();
    private final Queue<Command> connectingQueue = new LinkedList<>();

    // instantiate a parser for the connection
    private final ReplyParser replyParser = new ReplyParser(this);
    private final NetClient client;

    private final RedisSubscriptions subscriptions;
    private NetSocket netSocket;

    private final String host;
    private final int port;

    private static enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    private State state = State.DISCONNECTED;

    public RedisConnection(Vertx vertx, String host, int port, RedisSubscriptions subscriptions) {
        this.host = host;
        this.port = port;
        this.subscriptions = subscriptions;
        this.client = vertx.createNetClient(new NetClientOptions());
    }

    void connect(final Handler<AsyncResult<Void>> resultHandler) {
        if (state == State.DISCONNECTED) {
            state = State.CONNECTING;
            // reset the connection parser
            replyParser.reset();

            client.connect(port, host, new Handler<AsyncResult<NetSocket>>() {
                @Override
                public void handle(final AsyncResult<NetSocket> asyncResult) {
                    if (asyncResult.failed()) {
                        log.error("Net client error", asyncResult.cause());
                        // clean the reply queue
                        while (!repliesQueue.isEmpty()) {
                            repliesQueue.poll().handle(new Reply('-', "Connection closed"));
                        }
                        // clean waiting for connection queue
                        while (!connectingQueue.isEmpty()) {
                            connectingQueue.poll().getHandler().handle(new Reply('-', "Connection closed"));
                        }
                        if (resultHandler != null) {
                            resultHandler.handle(new RedisAsyncResult<>(asyncResult.cause()));
                        }
                        // make sure the socket is closed
                        if (netSocket != null) {
                            netSocket.close();
                        }
                        // update state
                        state = State.DISCONNECTED;
                    } else {
                        state = State.CONNECTED;
                        netSocket = asyncResult.result();
                        // set the data handler (the reply parser)
                        netSocket.handler(replyParser);
                        // set the exception handler
                        netSocket.exceptionHandler(e -> {
                            log.error("Socket client error", e);
                            // clean the reply queue
                            while (!repliesQueue.isEmpty()) {
                                repliesQueue.poll().handle(new Reply('-', "Connection closed"));
                            }
                            // clean waiting for connection queue
                            while (!connectingQueue.isEmpty()) {
                                connectingQueue.poll().getHandler().handle(new Reply('-', "Connection closed"));
                            }
                            // update state
                            state = State.DISCONNECTED;
                        });
                        // set the close handler
                        netSocket.closeHandler(arg0 -> {
                            log.info("Socket closed");
                            // clean the reply queue
                            while (!repliesQueue.isEmpty()) {
                                repliesQueue.poll().handle(new Reply('-', "Connection closed"));
                            }
                            // clean waiting for connection queue
                            while (!connectingQueue.isEmpty()) {
                                connectingQueue.poll().getHandler().handle(new Reply('-', "Connection closed"));
                            }
                            // update state
                            state = State.DISCONNECTED;
                        });

                        // process waiting queue (for messages that have been requested while the connection was not totally established)
                        while (!connectingQueue.isEmpty()) {
                            send(connectingQueue.poll());
                        }
                        // emit ready!
                        if (resultHandler != null) {
                            resultHandler.handle(new RedisAsyncResult<>(null, null));
                        }
                    }
                }
            });
        }
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
        switch (state) {
            case CONNECTED:
                // The order read must match the order written, vertx guarantees
                // that this is only called from a single thread.
                try {
                    command.writeTo(netSocket);
                    for (int i = 0; i < command.getExpectedReplies(); ++i) {
                        repliesQueue.offer(command.getHandler());
                    }
                } catch (RuntimeException e) {
                    // usually this means that the underlying socket is broken
                    state = State.DISCONNECTED;
                    // do not keep the client on hold forever, send an error back
                    command.getHandler().handle(new Reply('-', e.getMessage()));
                }
                break;
            case DISCONNECTED:
                log.info("Got request when disconnected. Trying to connect.");
                connect(new Handler<AsyncResult<Void>>() {
                    public void handle(AsyncResult<Void> connection) {
                        if (connection.succeeded()) {
                            send(command);
                        } else {
                            command.getHandler().handle(new Reply('-', "Unable to connect"));
                        }
                    }
                });
                break;
            case CONNECTING:
                log.debug("Got send request while connecting. Will try again in a while.");
                connectingQueue.offer(command);
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

        throw new RuntimeException("Received a non pub/sub message without reply handler waiting:"+reply.toString());
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
                        if (handler != null)
                        {
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
                        if (handler != null)
                        {
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
