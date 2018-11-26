/*
 * Copyright 2015 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.*;

import java.nio.charset.StandardCharsets;

public final class RESPParser implements Handler<Buffer> {

  // precache OK response as it is used widely and immutable
  // we reduce the GC pressure specially for write operations
  // as REDIS on the happy flow always returns this message
  private static final Response OK = SimpleStringType.create("OK");

  // limit of integer parsing before overflowing
  private static final long MAX_INTEGER_DIV_10 = Long.MAX_VALUE / 10;
  // 512Mb the max length of a redis string
  private static final long MAX_STRING_LENGTH = 536870912;

  // the callback when a full response message has been decoded
  private final Handler<Response> onDecode;
  private final Handler<Throwable> onFailure;

  // a composite buffer to allow buffer concatenation as if it was
  // a long stream
  private final CompositeByteBuf buffer = Unpooled.compositeBuffer();
  // arrays can have nested objects so we need to keep track of the
  // nesting while parsing
  private final ArrayStack stack;

  RESPParser(RedisClient client, int maxStack) {
    this(client, client::fatal, maxStack);
  }

  RESPParser(Handler<Response> onDecode, Handler<Throwable> onFailure, int maxStack) {
    this.onDecode = onDecode;
    this.onFailure = onFailure;
    this.stack = new ArrayStack(maxStack);
  }

  // parser state machine state
  private boolean eol = true;
  private int bytesNeeded = 0;
  private boolean illegalState = false;

  private long parseInteger(int start, int end) {
    long integer = 0L;
    boolean minus = false;

    for (int i = start; i < end; i++) {
      final byte b = buffer.getByte(i);
      if (b == '-') {
        minus = true;
        continue;
      }
      // be safe, and don't swallow errors
      if (integer > MAX_INTEGER_DIV_10) {
        illegalState = true;
        onFailure.handle(ErrorType.create("ILLEGAL_STATE Redis Integer Overflow"));
        break;
      }
      integer = 10 * integer + (buffer.getByte(i) - '0');
    }

    if (minus) {
      return -integer;
    }

    return integer;
  }

  private void simpleString(int start, int length) {
    // special case OK
    if (length == 2 && buffer.getByte(start) == 'O' && buffer.getByte(start + 1) == 'K') {
      handleResponse(OK);
    } else {
      // create a message from the available content (simple strings are not binary safe)
      handleResponse(SimpleStringType.create(buffer.toString(start, length, StandardCharsets.ISO_8859_1)));
    }
  }

  private void error(int start, int length) {
    // create a message from the available content (simple strings are not binary safe)
    handleResponse(ErrorType.create(buffer.toString(start, length, StandardCharsets.ISO_8859_1)));
  }

  private void integer(int begin, int end) {
    final long integer = parseInteger(begin, end);

    if (illegalState) {
      // we can't continue
      return;
    }

    handleResponse(IntegerType.create(integer));
  }

  private void multi(int begin, int end) {
    final long integer = parseInteger(begin, end);

    if (illegalState) {
      // we can't continue
      return;
    }

    // redis arrays cannot be longer than 2^32-1 elements
    if (integer > Integer.MAX_VALUE) {
      illegalState = true;
      onFailure.handle(ErrorType.create("ILLEGAL_STATE Redis Multi cannot be larger 2^32-1 elements"));
      return;
    }

    // special cases
    if (integer < 0) {
      if (integer == -1L) {
        // this is a NULL string
        handleResponse(null);
        return;
      }
      // other negative values are not valid
      illegalState = true;
      onFailure.handle(ErrorType.create("ILLEGAL_STATE Redis Multi cannot have negative length"));
      return;
    }

    // empty arrays can be cached and require no further processing
    if (integer == 0L) {
      handleResponse(MultiType.EMPTY);
    } else {
      // safe cast
      handleResponse(MultiType.create((int) integer), true);
    }
  }

  private void bulk(int begin, int end) {
    final long integer = parseInteger(begin, end);
    if (illegalState) {
      // we can't continue
      return;
    }
    // redis strings cannot be longer than 512Mb
    if (integer > MAX_STRING_LENGTH) {
      illegalState = true;
      onFailure.handle(ErrorType.create("ILLEGAL_STATE Redis Bulk cannot be larger than 512MB"));
      return;
    }
    // special cases
    if (integer < 0) {
      if (integer == -1L) {
        // this is a NULL string
        handleResponse(null);
        return;
      }
      // other negative values are not valid
      illegalState = true;
      onFailure.handle(ErrorType.create("ILLEGAL_STATE Redis Bulk cannot have negative length"));
      return;
    }
    // safe cast
    bytesNeeded = (int) integer;
    // in this case we switch from eol parsing to fixed len parsing
    eol = false;
  }

  private boolean handleEOL() {
    // setup a rollback point
    buffer.markReaderIndex();
    // this is the begin of a message
    final byte type = buffer.readByte();

    if (type != '+' && type != '-' && type != ':' && type != '$' && type != '*') {
      illegalState = true;
      onFailure.handle(ErrorType.create("ILLEGAL_STATE Unknown RESP type " + type));
      return true;
    }

    // locate the eol and handle as a C string
    final int start = buffer.readerIndex();
    final int end = start + buffer.readableBytes();
    final int index = buffer.indexOf(start, end, (byte) '\r');
    final int length = index - start;

    if (
      // not found at all
      index == -1 ||
        // found but the message isn't fully available as we need 2 extra bytes `\r\n`
        end < index + 2) {
      buffer.resetReaderIndex();
      return true;
    }

    switch (type) {
      case '+':
        simpleString(start, length);
        break;
      case '-':
        error(start, length);
        break;
      case ':':
        integer(start, index);
        break;
      case '$':
        bulk(start, index);
        break;
      case '*':
        multi(start, index);
        break;
    }

    // clean up the buffer
    buffer.readerIndex(index + 2);
    // TODO: validate the skipped bytes to be \r\n
    return false;
  }

  private void handleFIXED() {
    // fixed length parsing
    if (bytesNeeded != 0) {
      // create a message from the available content (simple strings are not binary safe)
      ByteBuf content = buffer.readSlice(bytesNeeded);
      // clean up the buffer
      // TODO: validate the skipped bytes to be \r\n
      buffer.skipBytes(2);
      // switch back to eol parsing
      eol = true;
      handleResponse(BulkType.create(content));
    } else {
      // special case as we don't need to allocate objects for this
      // clean up the buffer
      // TODO: validate the skipped bytes to be \r\n
      buffer.skipBytes(2);
      // switch back to eol parsing
      eol = true;
      handleResponse(BulkType.EMPTY);
    }
  }

  @Override
  public void handle(Buffer chunk) {

    // add the chunk to the buffer
    buffer.addComponent(true, chunk.getByteBuf());

    while (buffer.readableBytes() >= (eol ? 3 : bytesNeeded + 2)) {
      if (eol) {
        // we need to locate the eol
        if (handleEOL() || illegalState) {
          // we must abort
          break;
        }
      } else {
        // we need to handle the fixed length
        handleFIXED();
      }
    }

    // not everything has been read, so discard what is safe to be discarded
    buffer.discardReadComponents();
  }

  private void handleResponse(Response response) {
    handleResponse(response, false);
  }

  private void handleResponse(Response response, boolean push) {
    final MultiType multi = stack.peek();
    // verify if there are multi's on the stack
    if (multi != null) {
      // add the parsed response to the multi
      multi.add(response);
      // push the given response to the stack
      if (push) {
        stack.push(response);
      } else {
        // break the chain and verify end condition
        MultiType m = multi;
        // clean up complete messages
        while (m.complete()) {
          stack.pop();

          // in case of chaining we need to take into account
          // if the stack is empty or not
          if (stack.empty()) {
            // handle the multi to the listener
            onDecode.handle(m);
            return;
          }
          // peek into the next entry
          m = stack.peek();

          if (m == null) {
            onFailure.handle(ErrorType.create("ILLEGAL_STATE Stack is empty or array is null"));
            return;
          }
        }
      }
    } else {
      if (push) {
        stack.push(response);
      } else {
        // there's nothing on the stack
        // so we can handle the response directly
        // to the listener
        onDecode.handle(response);
      }
    }
  }
}
