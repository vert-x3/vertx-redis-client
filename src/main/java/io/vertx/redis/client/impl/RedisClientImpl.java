package io.vertx.redis.client.impl;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.ReferenceCountUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.impl.NetSocketInternal;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.RedisClient;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.*;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class RedisClientImpl implements RedisClient {

  public static void connect(Vertx vertx, SocketAddress address, NetClientOptions options, Handler<AsyncResult<RedisClient>> onConnect) {
    NetClient client = vertx.createNetClient(options);

    // Connect to the redis instance
    client.connect(address, ar -> {
      if (ar.succeeded()) {
        // Get the socket
        NetSocketInternal so = (NetSocketInternal) ar.result();

        // Create the client
        RedisClientImpl redis = new RedisClientImpl(so);

        // Return the memcached instance to the client
        onConnect.handle(Future.succeededFuture(redis));
      } else {
        onConnect.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  // client state
  private final NetSocketInternal so;
  // TODO: lookup the real type
  private final Deque<Handler<AsyncResult<Response>>> inflight = new ConcurrentLinkedDeque<>();


  public RedisClientImpl(final NetSocketInternal so) {
    this.so = so;
    // initialize the client, this will configure Netty's pipeline
    // an set an handler to process the decoded messages.
    final ChannelPipeline pipeline = so.channelHandlerContext().pipeline();

    // encode messages to redis
    pipeline.addFirst(new RedisEncoder());
    // decode arrays (arrays can be mixed typed)
    pipeline.addFirst(new RedisArrayAggregator());
    // decode bulks (think of UTF strings or BLOBs)
    pipeline.addFirst(new RedisBulkStringAggregator());
    // basic decoder
    pipeline.addFirst(new RedisDecoder());

    // Set the message handler to process redis message
    so.messageHandler(this::processResponse);
  }

  private Response toReply(Object msg) {
    if (msg instanceof SimpleStringRedisMessage) {
      return SimpleStringType.create(((SimpleStringRedisMessage) msg).content());
    } else if (msg instanceof ErrorRedisMessage) {
      return ErrorType.create(((ErrorRedisMessage) msg).content());
    } else if (msg instanceof IntegerRedisMessage) {
      return IntegerType.create(((IntegerRedisMessage) msg).value());
    } else if (msg instanceof FullBulkStringRedisMessage) {
      if (((FullBulkStringRedisMessage) msg).isNull()) {
        return NullType.create();
      }
      return BulkType.create(((FullBulkStringRedisMessage) msg).content());
    } else if (msg instanceof ArrayRedisMessage) {
      if (((ArrayRedisMessage) msg).isNull()) {
        return NullType.create();
      }
      final int len = ((ArrayRedisMessage) msg).children().size();
      final Response[] replies = new Response[len];

      for (int i = 0; i < len; i++) {
        replies[i] = toReply(((ArrayRedisMessage) msg).children().get(i));
      }
      return ArrayType.create(replies);
    } else {
      throw new CodecException("unknown message type: " + msg);
    }
  }

  private void processResponse(Object msg) {

    final Response reply = toReply(msg);

    // message must be a redis message
    try {
      // Get the handler that will process the response
      Handler<AsyncResult<Response>> handler = inflight.poll();

      if (reply instanceof ErrorType) {
        // Handle the message
        handler.handle(Future.failedFuture((ErrorType) reply));
      } else {
        // Handle the message
        handler.handle(Future.succeededFuture(reply));
      }
    } finally {
      // Release the referenced counted message
      ReferenceCountUtil.release(msg);
    }
  }

  @Override
  public void send(Request command, Handler<AsyncResult<Response>> onSend) {
    final RequestImpl cmd = (RequestImpl) command;
    // Write the message, the redis codec will encode the request
    // to a buffer and it will be sent
    so.writeMessage(cmd.message(), ar -> {
      if (ar.succeeded()) {
        // The message has been encoded succesfully and sent
        // we add the handler to the inflight queue
        inflight.add(onSend);
      } else {
        // The message could not be encoded or sent
        // we signal an error
        onSend.handle(Future.failedFuture(ar.cause()));
      }
    });
  }
}
