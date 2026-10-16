package io.vertx.tests.redis.internal;

import io.vertx.tests.redis.internal.RESPTestBase.Expected;

/**
 * A suite of correct RESP2 wire-format examples.
 * <p>
 * Each constant is the exact byte sequence (as a {@code String}, using {@code \r\n} for the
 * mandatory CRLF terminators) that a server would emit on the wire for the given reply type.
 * The constants are grouped by RESP type and cover typical values as well as boundary
 * conditions defined by the specification.</p>
 * <p>
 * Every example carries an {@link Expected} annotation stating the response it must parse into,
 * so the expectation lives right next to the wire data. Large or awkward expectations (a 512-byte
 * payload, a 512-element array, etc.) are computed by a {@code static} method referenced from
 * the annotation.
 * <p>
 * RESP2 type prefixes (the first byte determines the type):
 * <ul>
 *   <li>{@code +}: Simple String</li>
 *   <li>{@code -}: Error</li>
 *   <li>{@code :}: Integer</li>
 *   <li>{@code $}: Bulk String (also encodes Null as {@code $-1\r\n})</li>
 *   <li>{@code *}: Array (also encodes Null as {@code *-1\r\n})</li>
 * </ul>
 *
 * <p>
 * Note on sizes: the specification allows Bulk Strings up to 512 MB, but the "large"
 * boundary examples here deliberately use a modest value ({@value #LARGE_PAYLOAD_SIZE} bytes)
 * so that tests remain fast and easy to constrain.</p>
 */
public final class RESP2Examples {
  /** The CRLF terminator used throughout RESP. */
  private static final String CRLF = "\r\n";

  /**
   * Sensible "large" payload size (in bytes) used for boundary examples.
   * The protocol permits far more (512 MB for Bulk Strings) but tests should stay small.
   */
  public static final int LARGE_PAYLOAD_SIZE = 512;

  /** A payload of {@link #LARGE_PAYLOAD_SIZE} ASCII bytes, used to build the "large" examples. */
  private static final String LARGE_PAYLOAD = repeat('x', LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Simple Strings (+)
  //   A '+' followed by a string that MUST NOT contain CR or LF, terminated by CRLF.
  // ------------------------------------------------------------------------------------------

  /** Typical: the canonical success reply. */
  @Expected("SIMPLE:OK")
  public static final String SIMPLE_OK = "+OK\r\n";

  /** Typical: reply to {@code PING}. */
  @Expected("SIMPLE:PONG")
  public static final String SIMPLE_PONG = "+PONG\r\n";

  /** Boundary: the empty Simple String (nothing between '+' and CRLF). */
  @Expected("SIMPLE:")
  public static final String SIMPLE_EMPTY = "+\r\n";

  /** Typical: a Simple String containing spaces (spaces are allowed, only CR/LF are not). */
  @Expected("SIMPLE:hello world")
  public static final String SIMPLE_WITH_SPACES = "+hello world\r\n";

  /** Typical: a Simple String containing symbols and digits. */
  @Expected("SIMPLE:v1.0.0-beta+build#42")
  public static final String SIMPLE_SYMBOLS = "+v1.0.0-beta+build#42\r\n";

  /** Boundary: a large Simple String of {@link #LARGE_PAYLOAD_SIZE} bytes. */
  @Expected(method = "expectedSimpleLarge")
  public static final String SIMPLE_LARGE = "+" + LARGE_PAYLOAD + "\r\n";

  // ------------------------------------------------------------------------------------------
  // Errors (-)
  //   Exactly like a Simple String but prefixed with '-'. The payload is the error message.
  //   The first word (up to the first space) is the conventional "Error Prefix".
  // ------------------------------------------------------------------------------------------

  /** Typical: generic {@code ERR} error with a message. */
  @Expected("ERROR:ERR unknown command 'foobar'")
  public static final String ERROR_GENERIC = "-ERR unknown command 'foobar'\r\n";

  /** Typical: a typed error prefix ({@code WRONGTYPE}) followed by a descriptive message. */
  @Expected("ERROR:WRONGTYPE Operation against a key holding the wrong kind of value")
  public static final String ERROR_WRONGTYPE = "-WRONGTYPE Operation against a key holding the wrong kind of value\r\n";

  /** Typical: error prefix only, no message body (single word, no space). */
  @Expected("ERROR:ERR")
  public static final String ERROR_PREFIX_ONLY = "-ERR\r\n";

  /** Boundary: the empty Error (nothing between '-' and CRLF). */
  @Expected("ERROR:")
  public static final String ERROR_EMPTY = "-\r\n";

  /** Typical: a common authentication error. */
  @Expected("ERROR:NOSCRIPT No matching script. Please use EVAL.")
  public static final String ERROR_NOSCRIPT = "-NOSCRIPT No matching script. Please use EVAL.\r\n";

  /** Boundary: a large Error message of {@link #LARGE_PAYLOAD_SIZE} bytes. */
  @Expected(method = "expectedErrorLarge")
  public static final String ERROR_LARGE = "-" + LARGE_PAYLOAD + "\r\n";

  // ------------------------------------------------------------------------------------------
  // Integers (:)
  //   A ':' followed by a CRLF-terminated decimal integer.
  //   Guaranteed to be within the range of a signed 64-bit integer.
  // ------------------------------------------------------------------------------------------

  /** Typical: zero (also the "false" value for commands like {@code EXISTS}). */
  @Expected("NUMBER:0")
  public static final String INTEGER_ZERO = ":0\r\n";

  /** Typical: one (also the "true" value for commands like {@code SISMEMBER}). */
  @Expected("NUMBER:1")
  public static final String INTEGER_ONE = ":1\r\n";

  /** Typical: a positive value as returned by {@code INCR}, {@code LLEN}, etc. */
  @Expected("NUMBER:1000")
  public static final String INTEGER_POSITIVE = ":1000\r\n";

  /** Typical: a negative value. */
  @Expected("NUMBER:-1")
  public static final String INTEGER_NEGATIVE = ":-1\r\n";

  /** Typical: a large-ish value such as a UNIX timestamp ({@code LASTSAVE}). */
  @Expected("NUMBER:1700000000")
  public static final String INTEGER_TIMESTAMP = ":1700000000\r\n";

  /** Boundary: the largest value a client must accept that is still "sensible" (2^53, JS-safe integer). */
  @Expected("NUMBER:9007199254740991")
  public static final String INTEGER_LARGE = ":9007199254740991\r\n";

  /** Boundary: the inclusive maximum of the signed 64-bit range ({@code Long.MAX_VALUE}). */
  @Expected("NUMBER:9223372036854775807")
  public static final String INTEGER_INT64_MAX = ":9223372036854775807\r\n";

  /** Boundary: the inclusive minimum of the signed 64-bit range ({@code Long.MIN_VALUE}). */
  @Expected("NUMBER:-9223372036854775808")
  public static final String INTEGER_INT64_MIN = ":-9223372036854775808\r\n";

  // ------------------------------------------------------------------------------------------
  // Bulk Strings ($)
  //   '$' + byte-count + CRLF + <data> + CRLF.
  //   Empty bulk string has length 0; the Null Bulk String uses length -1.
  // ------------------------------------------------------------------------------------------

  /** Typical: the string "foobar". */
  @Expected("BULK:foobar")
  public static final String BULK_FOOBAR = "$6\r\nfoobar\r\n";

  /** Typical: a single character. */
  @Expected("BULK:x")
  public static final String BULK_SINGLE_CHAR = "$1\r\nx\r\n";

  /** Boundary: the empty Bulk String (length 0, followed by a bare CRLF). */
  @Expected("BULK:")
  public static final String BULK_EMPTY = "$0\r\n\r\n";

  /** Boundary: the Null Bulk String (length -1, no data), signals non-existence. */
  @Expected("NULL")
  public static final String BULK_NULL = "$-1\r\n";

  /** Typical: a Bulk String containing spaces and punctuation. */
  @Expected("BULK:hello, world!")
  public static final String BULK_TEXT = "$13\r\nhello, world!\r\n";

  /**
   * Boundary: a binary-safe Bulk String whose payload contains CR, LF and NUL bytes.
   * The declared length (5) counts raw bytes, so embedded CRLF is data, not a terminator.
   * Payload bytes: 'a', '\r', '\n', 0x00, 'b'.
   */
  @Expected("BULK:a\r\n\u0000b")
  public static final String BULK_BINARY = "$5\r\na\r\n\u0000b\r\n";

  /**
   * Accepted leniency: the payload is followed by junk bytes ({@code XX}) before the terminator.
   * The parser locates the trailer by scanning forward for the next CRLF, so the junk is skipped
   * and the declared 3-byte payload {@code foo} is emitted intact.
   */
  @Expected("BULK:foo")
  public static final String BULK_JUNK_TRAILER = "$3\r\nfooXX\r\n";

  /** Boundary: a large Bulk String of {@link #LARGE_PAYLOAD_SIZE} bytes. */
  @Expected(method = "expectedBulkLarge")
  public static final String BULK_LARGE = "$" + LARGE_PAYLOAD_SIZE + "\r\n" + LARGE_PAYLOAD + "\r\n";

  // ------------------------------------------------------------------------------------------
  // Arrays (*)
  //   '*' + element-count + CRLF, followed by that many complete RESP values.
  //   Empty array has count 0; the Null Array uses count -1.
  // ------------------------------------------------------------------------------------------

  /** Boundary: the empty Array (count 0). */
  @Expected("MULTI:[]")
  public static final String ARRAY_EMPTY = "*0\r\n";

  /** Boundary: the Null Array (count -1). */
  @Expected("NULL")
  public static final String ARRAY_NULL = "*-1\r\n";

  /** Typical: an array of two Bulk Strings. */
  @Expected("MULTI:[foo, bar]")
  public static final String ARRAY_OF_BULK = "*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n";

  /** Typical: an array of three Integers. */
  @Expected("MULTI:[1, 2, 3]")
  public static final String ARRAY_OF_INTEGERS = "*3\r\n:1\r\n:2\r\n:3\r\n";

  /** Typical: an array of mixed types. */
  @Expected("MULTI:[1, 2, 3, 4, foobar]")
  public static final String ARRAY_MIXED = "*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n";

  /** Typical: an array containing a Null element (the middle Bulk String is Null). */
  @Expected("MULTI:[foo, null, bar]")
  public static final String ARRAY_WITH_NULL_ELEMENT = "*3\r\n$3\r\nfoo\r\n$-1\r\n$3\r\nbar\r\n";

  /** Typical: nested arrays. */
  @Expected("MULTI:[[1, 2, 3], [Foo, Bar]]")
  public static final String ARRAY_OF_ARRAYS = "*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n-Bar\r\n";

  /** Typical: the RESP request form. */
  @Expected("MULTI:[GET, key]")
  public static final String ARRAY_REQUEST_GET = "*2\r\n$3\r\nGET\r\n$3\r\nkey\r\n";

  /** Boundary: an array whose single element is itself a Null Array. */
  @Expected("MULTI:[null]")
  public static final String ARRAY_CONTAINING_NULL_ARRAY = "*1\r\n*-1\r\n";

  /** Boundary: a larger array. */
  @Expected(method = "expectedArrayLarge")
  public static final String ARRAY_LARGE = buildIntegerArray(LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Multi-reply streams (several top-level replies concatenated on one read)
  // ------------------------------------------------------------------------------------------

  /** Typical: pipelined replies. */
  @Expected({ "SIMPLE:OK", "SIMPLE:OK", "SIMPLE:OK" })
  public static final String STREAM_SIMPLE_x3 = "+OK\r\n+OK\r\n+OK\r\n";

  /** Typical: a mixed stream. */
  @Expected({ "SIMPLE:OK", "BULK:value", "NUMBER:42" })
  public static final String STREAM_MIXED = "+OK\r\n$5\r\nvalue\r\n:42\r\n";

  // ------------------------------------------------------------------------------------------
  // Expected-value computations for the large / awkward examples (referenced from @Expected)
  // ------------------------------------------------------------------------------------------

  static String[] expectedSimpleLarge() {
    return new String[] { "SIMPLE:" + LARGE_PAYLOAD };
  }

  static String[] expectedErrorLarge() {
    return new String[] { "ERROR:" + LARGE_PAYLOAD };
  }

  static String[] expectedBulkLarge() {
    return new String[] { "BULK:" + LARGE_PAYLOAD };
  }

  static String[] expectedArrayLarge() {
    StringBuilder sb = new StringBuilder("MULTI:[");
    for (int i = 0; i < LARGE_PAYLOAD_SIZE; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(i);
    }
    sb.append(']');
    return new String[] { sb.toString() };
  }

  // ------------------------------------------------------------------------------------------
  // helpers (used only to build the large boundary constants above)
  // ------------------------------------------------------------------------------------------

  private static String repeat(char c, int n) {
    char[] buf = new char[n];
    for (int i = 0; i < n; i++) {
      buf[i] = c;
    }
    return new String(buf);
  }

  private static String buildIntegerArray(int count) {
    StringBuilder sb = new StringBuilder();
    sb.append('*').append(count).append(CRLF);
    for (int i = 0; i < count; i++) {
      sb.append(':').append(i).append(CRLF);
    }
    return sb.toString();
  }
}
