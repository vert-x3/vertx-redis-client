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

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.*;

import java.nio.charset.StandardCharsets;

public final class RESPParser implements Handler<Buffer> {

  private static final Logger LOG = LoggerFactory.getLogger(RESPParser.class);
  // 512Mb
  private static final long MAX_STRING_LENGTH = 536870912;

  // the callback when a full response message has been decoded
  private final ParserHandler handler;
  // a composite buffer to allow buffer concatenation as if it was
  // a long stream
  private final ReadableBuffer buffer = new ReadableBuffer();
  // arrays can have nested objects so we need to keep track of the
  // nesting while parsing
  private final ArrayStack stack;

  RESPParser(ParserHandler handler, int maxStack) {
    this.handler = handler;
    this.stack = new ArrayStack(maxStack);
  }

  // parser state machine state
  private boolean eol = true;
  private int bytesNeeded = 0;

  @Override
  public void handle(Buffer chunk) {
    // add the chunk to the buffer
    buffer.append(chunk);

    while (buffer.readableBytes() >= (eol ? 3 : bytesNeeded + 2)) {
      // setup a rollback point
      buffer.mark();

      // we need to locate the eol
      if (eol) {
        // this is the begin of a message
        final byte type = buffer.readByte();

        // locate the eol and handle as a C string
        final int start = buffer.offset();
        final int eol = buffer.findLineEnd();

        // not found at all
        if (eol == -1) {
          buffer.reset();
          break;
        }

        switch (type) {
          case '+':
            final int length = eol - start;
            // special case OK
            if (length == 2 && buffer.getByte(start) == 'O' && buffer.getByte(start + 1) == 'K') {
              handleResponse(SimpleStringType.OK);
            } else {
              handleResponse(SimpleStringType.create(buffer.readLine(eol, StandardCharsets.ISO_8859_1)));
            }
            break;
          case '-':
            handleResponse(ErrorType.create(buffer.readLine(eol, StandardCharsets.ISO_8859_1)));
            break;
          case ':':
          case '$':
          case '*':
            final long integer;

            try {
              integer = buffer.readLong(eol);
            } catch (RuntimeException e) {
              handler.fatal(e);
              return;
            }

            switch (type) {
              case ':':
                handleResponse(IntegerType.create(integer));
                break;
              case '$':
                // redis strings cannot be longer than 512Mb
                if (integer > MAX_STRING_LENGTH) {
                  handler.fatal(ErrorType.create("ILLEGAL_STATE Redis Bulk cannot be larger than 512MB"));
                  return;
                }
                // special cases
                if (integer < 0) {
                  if (integer == -1L) {
                    // this is a NULL string
                    handleResponse(null);
                    break;
                  }
                  // other negative values are not valid
                  handler.fatal(ErrorType.create("ILLEGAL_STATE Redis Bulk cannot have negative length"));
                  return;
                }
                // empty string
                if (integer == 0) {
                  // special case as we don't need to allocate objects for this
                  handleResponse(BulkType.EMPTY);
                  return;
                }
                // safe cast
                bytesNeeded = (int) integer;
                // in this case we switch from eol parsing to fixed len parsing
                this.eol = false;
                break;
              case '*':
                // special cases
                // redis multi cannot have more than 2GB elements
                if (integer > Integer.MAX_VALUE) {
                  handler.fatal(ErrorType.create("ILLEGAL_STATE Redis Multi cannot be larger 2GB elements"));
                  return;
                }
                if (integer < 0) {
                  if (integer == -1L) {
                    // this is a NULL array
                    handleResponse(null);
                    break;
                  }
                  // other negative values are not valid
                  handler.fatal(ErrorType.create("ILLEGAL_STATE Redis Multi cannot have negative length"));
                }
                // empty arrays can be cached and require no further processing
                if (integer == 0L) {
                  handleResponse(MultiType.EMPTY);
                } else {
                  // safe cast
                  handleResponse(MultiType.create((int) integer), true);
                }
                break;
            }
            break;
          default:
            // notify
            handler.fatal(ErrorType.create("ILLEGAL_STATE Unknown RESP type " + (char) type));
            return;
        }
      } else {
        // fixed length parsing && read the required bytes
        handleResponse(BulkType.create(buffer.readBytes(bytesNeeded)));

        // clean up the buffer, skip the last \r\n
        if (buffer.skip(2)) {
          // switch back to eol parsing
          bytesNeeded = 0;
          eol = true;
        } else {
          // operation failed
          buffer.reset();
        }
      }
    }
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

          if (m == null) {
            handler.fatal(ErrorType.create("ILLEGAL_STATE Multi can't be null"));
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
        handler.handle(response);
      }
    }
  }
}
