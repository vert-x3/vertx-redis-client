package io.vertx.redis.impl;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.WriteStream;

import java.nio.charset.Charset;

public class Command {

    private static final byte ARGS_PREFIX = '*';
    private static final byte[] CRLF = "\r\n".getBytes();
    private static final byte BYTES_PREFIX = '$';

    private static final byte[] NEG_ONE = convert(-1);

    // Cache 256 number conversions. That should cover a huge
    // percentage of numbers passed over the wire.
    private static final int NUM_MAP_LENGTH = 256;
    private static final byte[][] numMap = new byte[NUM_MAP_LENGTH][];

    static {
        for (int i = 0; i < NUM_MAP_LENGTH; i++) {
            numMap[i] = convert(i);
        }
    }

    // Optimized for the direct to ASCII bytes case
    // About 5x faster than using Long.toString.getBytes
    private static byte[] numToBytes(long value) {
        if (value >= 0 && value < NUM_MAP_LENGTH) {
            int index = (int) value;
            return numMap[index];
        } else if (value == -1) {
            return NEG_ONE;
        }
        return convert(value);
    }

    private static byte[] convert(long value) {
        boolean negative = value < 0;
        // Checked javadoc: If the argument is equal to 10^n for integer n, then the result is n.
        // Also, if negative, leave another slot for the sign.
        long abs = Math.abs(value);
        int index = (value == 0 ? 0 : (int) Math.log10(abs)) + (negative ? 2 : 1);
        byte[] bytes = new byte[index];
        // Put the sign in the slot we saved
        if (negative) bytes[0] = '-';
        long next = abs;
        while ((next /= 10) > 0) {
            bytes[--index] = (byte) ('0' + (abs % 10));
            abs = next;
        }
        bytes[--index] = (byte) ('0' + abs);
        return bytes;
    }

    private void appendToBuffer(final Object value, final Charset encoding, final Buffer buffer) {
        buffer.appendByte(BYTES_PREFIX);
        if (value == null) {
            buffer.appendByte((byte) '0');
            buffer.appendBytes(CRLF);
            buffer.appendBytes(CRLF);
        } else {
            byte[] bytes;
            // Possible types are: String, JsonObject, JsonArray, JsonElement, Number, Boolean, byte[]

            if (value instanceof byte[]) {
                bytes = (byte[]) value;
            } else if (value instanceof Buffer) {
                bytes = ((Buffer) value).getBytes();
            } else if (value instanceof String) {
                bytes = ((String) value).getBytes(encoding);
            } else if (value instanceof Byte) {
                bytes = numToBytes((Byte) value);
            } else if (value instanceof Short) {
                bytes = numToBytes((Short) value);
            } else if (value instanceof Integer) {
                bytes = numToBytes((Integer) value);
            } else if (value instanceof Long) {
                bytes = numToBytes((Long) value);
            } else {
                bytes = value.toString().getBytes(encoding);
            }

            buffer.appendBytes(numToBytes(bytes.length));

            buffer.appendBytes(CRLF);
            buffer.appendBytes(bytes);
            buffer.appendBytes(CRLF);
        }
    }

    private final Buffer buffer;
    private int expectedReplies = 1;
    private Handler<Reply> handler;

    public Command(String command, final JsonArray args, Charset encoding) {

        int totalArgs;
        if (args == null) {
            totalArgs = 0;
        } else {
            totalArgs = args.size();
        }

        int spc = command.indexOf(' '); // there are commands which are multi word
        String extraCommand = null;

        if (spc != -1) {
            extraCommand = command.substring(spc + 1);
            command = command.substring(0, spc);
        }

        // serialize the request
        buffer = Buffer.buffer();
        buffer.appendByte(ARGS_PREFIX);
        if (extraCommand == null) {
            buffer.appendBytes(numToBytes(totalArgs + 1));
        } else {
            buffer.appendBytes(numToBytes(totalArgs + 2));
        }
        buffer.appendBytes(CRLF);
        // serialize the command
        appendToBuffer(command.getBytes(encoding), encoding, buffer);
        if (extraCommand != null) {
            appendToBuffer(extraCommand.getBytes(encoding), encoding, buffer);
        }

        // serialize arguments
        for (int i = 0; i < totalArgs; i++) {
            appendToBuffer(args.get(i), encoding, buffer);
        }
    }

    public Command setExpectedReplies(int expectedReplies) {
        this.expectedReplies = expectedReplies;
        return this;
    }

    public Command setHandler(Handler<Reply> handler) {
        this.handler = handler;
        return this;
    }

    public void writeTo(WriteStream<Buffer> writeStream) {
        writeStream.write(buffer);
    }

    public int getExpectedReplies() {
        return expectedReplies;
    }

    public Handler<Reply> getHandler() {
        return handler;
    }

}
