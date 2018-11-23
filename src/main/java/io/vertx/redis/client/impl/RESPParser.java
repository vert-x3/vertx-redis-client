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

  private static final long MAX_INTEGER_DIV_10 = Long.MAX_VALUE / 10;
  // 512Mb
  private static final long MAX_STRING_LENGTH = 536870912;

  // the callback when a full response message has been decoded
  private final Handler<Response> handler;

  // a composite buffer to allow buffer concatenation as if it was
  // a long stream
  private final CompositeByteBuf buffer = Unpooled.compositeBuffer();
  // arrays can have nested objects so we need to keep track of the
  // nesting while parsing
  private final ArrayStack stack;

  RESPParser(Handler<Response> handler) {
    this(handler, 16);
  }

  RESPParser(Handler<Response> handler, int maxStack) {
    this.handler = handler;
    this.stack = new ArrayStack(maxStack);
  }

  // parser state machine state
  private boolean eol = true;
  private int bytesNeeded = 0;

  @Override
  public void handle(Buffer chunk) {

    // add the chunk to the buffer
    buffer.addComponent(true, chunk.getByteBuf());

    loop:
    while (buffer.readableBytes() >= (eol ? 3 : bytesNeeded + 2)) {
      // we need to locate the eol
      if (eol) {
        // setup a rollback point
        buffer.markReaderIndex();
        // this is the begin of a message
        final byte type = buffer.readByte();

        int start, end, index;
        String content;
        long integer;

        switch (type) {
          case '+':
          case '-':
            // locate the eol and handle as a C string
            start = buffer.readerIndex();
            end = start + buffer.readableBytes();

            index = buffer.indexOf(start, end, (byte) '\r');
            if (
              // not found at all
              index == -1 ||
                // found but the message isn't fully available as we need 2 extra bytes `\r\n`
                end < index + 2) {
              buffer.resetReaderIndex();
              break loop;
            }
            // create a message from the available content (simple strings are not binary safe)
            content = buffer.toString(start, index - start, StandardCharsets.US_ASCII);
            // clean up the buffer
            buffer.readerIndex(start + index - start + 2);
            // TODO: validate the skipped bytes to be \r\n

            switch (type) {
              case '+':
                handleResponse(SimpleStringType.create(content));
                break;
              case '-':
                handleResponse(ErrorType.create(content));
                break;
              default:
                throw new IllegalStateException();
            }
            break;
          case ':':
          case '$':
          case '*':
            // locate the eol and handle as a C string
            start = buffer.readerIndex();
            end = start + buffer.readableBytes();

            index = buffer.indexOf(start, end, (byte) '\r');
            if (
              // not found at all
              index == -1 ||
                // found but the message isn't fully available as we need 2 extra bytes `\r\n`
                end < index + 2) {
              buffer.resetReaderIndex();
              break loop;
            }
            // parse the integer value
            boolean minus = buffer.getByte(start) == '-';
            integer = 0L;

            for (int i = (minus ? start + 1 : start); i < index; i++) {
              // be safe, and don't swallow errors
              if (integer > MAX_INTEGER_DIV_10) {
                throw new ArithmeticException("Redis Integer Overflow");
              }
              integer = 10 * integer + (buffer.getByte(i) - '0');
            }
            // clean up the buffer
            buffer.readerIndex(start + index - start + 2);
            // TODO: validate the skipped bytes to be \r\n
            if (minus) {
              integer = -integer;
            }

            switch (type) {
              case ':':
                handleResponse(IntegerType.create(integer));
                break;
              case '$':
                // redis strings cannot be longer than 512Mb
                if (integer > MAX_STRING_LENGTH) {
                  throw new IllegalArgumentException("Redis Bulk cannot be larger than 512MB");
                }
                // special cases
                if (integer < 0) {
                  if (integer == -1L) {
                    // this is a NULL string
                    handleResponse(BulkType.NULL);
                    break;
                  }
                  // other negative values are not valid
                  throw new IllegalArgumentException("Redis Bulk cannot have negative length");
                }
                // safe cast
                bytesNeeded = (int) integer;
                // in this case we switch from eol parsing to fixed len parsing
                eol = false;
                break;
              case '*':
                // special cases
                // redis strings cannot be longer than 512Mb
                if (integer > Integer.MAX_VALUE) {
                  throw new IllegalArgumentException("Redis Multi cannot be larger 2GB");
                }
                if (integer < 0) {
                  if (integer == -1L) {
                    // this is a NULL array
                    handleResponse(MultiType.NULL);
                    break;
                  }
                  // other negative values are not valid
                  throw new IllegalArgumentException("Redis Multi cannot have negative length");
                }
                // empty arrays can be cached and require no further processing
                if (integer == 0L) {
                  handleResponse(MultiType.EMPTY);
                } else {
                  // safe cast
                  handleResponse(MultiType.create((int) integer), true);
                }
                break;
              default:
                throw new IllegalStateException();
            }
            break;
        }
      } else {
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
            handler.handle(m);
            return;
          }
          // peek into the next entry
          m = stack.peek();
        }
      }
    } else {
      if (push) {
        stack.push(response);
      } else {
        // there's nothing on the stack
        // so we can handle the response directly
        // to the listener
        handler.handle(response);
      }
    }
  }
}
