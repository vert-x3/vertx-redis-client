/*
 * Copyright 2019 Red Hat, Inc.
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
import io.vertx.redis.client.Response;
import io.vertx.redis.client.ResponseType;
import io.vertx.redis.client.impl.types.*;

public final class RESPParser implements Handler<Buffer> {

  public static final String VERSION = "3";

  // the callback when a full response message has been decoded
  private final ParserHandler handler;
  // a composite buffer to allow buffer concatenation as if it was a long stream
  private final ReadableBuffer buffer = new ReadableBuffer();
  // arrays can have nested objects so we need to keep track of the nesting while parsing
  private final ArrayStack stack;

  // maximum number of elements allowed in a single multi-bulk response
  private final int maxMultiCount;
  // maximum length of an unterminated line before the stream is rejected
  private final int maxLineLength;
  // maximum length allowed for a bulk payload
  private final long maxStringLength;

  public RESPParser(ParserHandler handler) {
    this(handler, new RESPParserOptions());
  }

  RESPParser(ParserHandler handler, int maxStack) {
    this(handler, maxStack, 65535);
  }

  RESPParser(ParserHandler handler, int maxStack, int maxMultiLength) {
    this(handler, new RESPParserOptions()
      .setMaxNestingDepth(maxStack)
      .setMaxMultiCount(maxMultiLength));
  }

  public RESPParser(ParserHandler handler, RESPParserOptions options) {
    this.handler = handler;
    this.stack = new ArrayStack(options.getMaxNestingDepth());
    this.maxMultiCount = options.getMaxMultiCount();
    this.maxLineLength = options.getMaxLineLength();
    this.maxStringLength = options.getMaxStringLength();
  }

  // parser state machine state
  private boolean eol = true;
  private int bytesNeeded = 0;
  private boolean verbatim = false;

  @Override
  public void handle(Buffer chunk) {
    // add the chunk to the buffer
    buffer.append(chunk);

    while (buffer.readableBytes() >= (eol ? 3 : bytesNeeded != -1 ? bytesNeeded + 2 : 0)) {
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
          // The line is only complete once findLineEnd() locates the CRLF. If it is still absent
          // and the buffered line already exceeds the maximum, a peer that never terminates the
          // line would grow the buffer without limit, so reject the stream.
          if (buffer.readableBytes() > maxLineLength) {
            handler.fail(ErrorType.create("ILLEGAL_STATE RESP line exceeds maximum length " + maxLineLength));
            return;
          }
          buffer.reset();
          break;
        }

        // special case for sync messages or messages that report the wrong length
        if (start == eol) {
          buffer.reset();
          break;
        }

        switch (type) {
          case '+':
            handleSimpleString(start, eol);
            break;
          case '-':
          case '!':
            handleError(eol);
            break;
          case ':':
          case ',':
          case '(':
            handleNumber(type, eol);
            break;
          case '=':
            handleBulk(eol, true);
            break;
          case '$':
            handleBulk(eol, false);
            break;
          case '*':
          case '%':
          case '~':
            handleMulti(type, eol);
            break;
          case '_':
            handleNull();
            break;
          case '#':
            handleBoolean();
            break;
          case '|':
            handleAttribute(eol);
            break;
          case '>':
            handlePush(eol);
            break;
          default:
            // notify
            handler.fail(ErrorType.create("ILLEGAL_STATE Unknown RESP type " + (char) type));
            return;
        }
      } else {
        // fixed length parsing: read the payload, but only emit once the trailing CRLF is found
        // this mirrors the line-based types, which locate EOL via findLineEnd()
        final Response response;
        if (bytesNeeded == 0) {
          // special case as we don't need to allocate objects for this
          response = BulkType.EMPTY;
        } else {
          // read the required bytes
          response = BulkType.create(buffer.readBytes(bytesNeeded), verbatim);
        }
        // clean up the buffer, skip to the last \r\n
        if (buffer.skipEOL()) {
          // switch back to eol parsing
          eol = true;
          // clear the verbatim flag only once the message is complete
          verbatim = false;
          handleResponse(response, false);
        } else {
          // trailer not present yet, rewind and wait for more data
          // note that retrying now would re-read and re-emit the payload in a loop!
          buffer.reset();
          break;
        }
      }
    }
  }

  private void handleNumber(byte type, int eol) {
    switch (type) {
      case ':':
        handleResponse(NumberType.create(buffer.readNumber(eol, ReadableBuffer.NumericType.INTEGER)), false);
        break;
      case ',':
        handleResponse(NumberType.create(buffer.readNumber(eol, ReadableBuffer.NumericType.DECIMAL)), false);
        break;
      case '(':
        handleResponse(NumberType.create(buffer.readNumber(eol, ReadableBuffer.NumericType.BIGINTEGER)), false);
        break;
      default:
        handler.fail(new NumberFormatException("Invalid REDIS format: [" + (char) type + "]"));
        break;
    }
  }

  // Reads the numeric header of a length/count type and handles only what all callers share:
  // the -1 sentinel for "NULL" and malformed negatives. The other caps are applied by callers.
  private long handleLength(int eol) {
    final long integer = buffer.readLength(eol);

    if (integer < 0) {
      if (integer == -1L) {
        // this is a NULL bulk/array
        handleResponse(null, false);
      } else {
        handler.fail(ErrorType.create("ILLEGAL_STATE Redis Multi cannot have negative length"));
      }
      return -1;
    }

    return integer;
  }

  // Reads the number of elements of aggregate/push/attribute. Note the number is not a byte length!
  private long handleCount(int eol) {
    final long count = handleLength(eol);
    if (count < 0) {
      // null emitted or already failed
      return count;
    }

    // redis multi cannot have more than 2G elements
    if (count > Integer.MAX_VALUE) {
      handler.fail(ErrorType.create("ILLEGAL_STATE Redis Multi cannot have more than 2G elements"));
      return -1;
    }

    if (count > maxMultiCount) {
      handler.fail(ErrorType.create("ILLEGAL_STATE Redis Multi element count " + count
        + " exceeds the configured maximum of " + maxMultiCount));
      return -1;
    }

    return count;
  }

  private void handlePush(int eol) {
    long len = handleCount(eol);
    if (len >= 0) {
      if (len == 0L) {
        // push always have 1 entry
        handler.fail(ErrorType.create("ILLEGAL_STATE Redis Push must have at least 1 element"));
      } else {
        handleResponse(PushType.create(len), true);
      }
    }
  }

  private void handleAttribute(int eol) {
    long len = handleCount(eol);
    if (len >= 0L) {
      if (len == 0L) {
        // push always have 1 entry
        handler.fail(ErrorType.create("ILLEGAL_STATE Redis Push must have at least 1 element"));
      } else {
        handleResponse(AttributeType.create(len), true);
      }
    }
  }

  private void handleBoolean() {
    byte value = buffer.readByte();
    switch (value) {
      case 't':
        buffer.skipEOL();
        handleResponse(BooleanType.TRUE, false);
        break;
      case 'f':
        buffer.skipEOL();
        handleResponse(BooleanType.FALSE, false);
        break;
      default:
        handler.fail(ErrorType.create("Invalid boolean value: " + ((char) value)));
    }
  }

  private void handleSimpleString(int start, int eol) {
    final int length = eol - start;
    // special case OK
    if (length == 2 && buffer.getByte(start) == 'O' && buffer.getByte(start + 1) == 'K') {
      handleResponse(SimpleStringType.OK, false);
    } else {
      handleResponse(SimpleStringType.create(buffer.readLine(eol)), false);
    }
  }

  private void handleError(int eol) {
    handleResponse(ErrorType.create(buffer.readLine(eol)), false);
  }

  private void handleBulk(int eol, boolean verbatim) {
    long len = handleLength(eol);
    if (len >= 0L) {
      if (len > maxStringLength) {
        handler.fail(ErrorType.create("ILLEGAL_STATE Redis Bulk cannot be larger than " + maxStringLength + " bytes"));
        return;
      }
      // safe cast
      bytesNeeded = (int) len;
      // in this case we switch from eol parsing to fixed len parsing
      this.eol = false;
      this.verbatim = verbatim;
    }
  }

  private void handleMulti(byte type, int eol) {

    long len = handleCount(eol);

    if (len >= 0L) {
      // empty arrays can be cached and require no further processing
      if (len == 0L) {
        handleResponse(type == '%' ? MultiType.EMPTY_MAP : MultiType.EMPTY_MULTI, false);
      } else {
        handleResponse(MultiType.create(len, type == '%'), true);
      }
    }
  }

  private void handleNull() {
    // clean up the buffer, skip to the last \r\n
    buffer.skipEOL();
    handleResponse(null, false);
  }

  private void handleResponse(Response response, boolean push) {
    final Multi multi = stack.peek();
    // verify if there are multi's on the stack
    if (multi != null) {
      // add the parsed response to the multi
      multi.add(response);
      // push the given response to the stack
      if (push) {
        stack.push(response);
      } else {
        // break the chain and verify end condition
        Multi m = multi;
        // clean up complete messages
        while (m.complete()) {
          stack.pop();

          // in case of chaining we need to take into account
          // if the stack is empty or not
          if (stack.empty()) {
            if (m.type() != ResponseType.ATTRIBUTE) {
              // handle the multi to the listener
              handler.handle(m);
            }
            return;
          }
          // peek into the next entry
          m = stack.peek();

          if (m == null) {
            handler.fail(ErrorType.create("ILLEGAL_STATE Multi can't be null"));
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
