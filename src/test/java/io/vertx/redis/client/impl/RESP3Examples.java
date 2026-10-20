package io.vertx.redis.client.impl;

import io.vertx.redis.client.impl.RESPTestBase.Expected;

/**
 * A suite of correct RESP3 wire-format examples.
 * <p>
 * Each constant is the exact byte sequence (as a {@code String}, using {@code \r\n} for the
 * mandatory CRLF terminators) that a server would emit on the wire for the given reply type.
 * The constants are grouped by RESP3 type and cover typical values as well as boundary
 * conditions defined by the specification.
 * <p>
 * Every example carries an {@link Expected} annotation stating the response it must parse into,
 * so the expectation lives right next to the wire data. Large or awkward expectations (a 512-byte
 * payload, a 512-element array, etc.) are computed by a {@code static} method referenced from
 * the annotation.
 * <p>
 * RESP3 is an almost-strict superset of RESP2. The five RESP2 types are unchanged and already covered
 * by {@link RESP2Examples}; this suite concentrates on the RESP3-specific types but also restates
 * the RESP2 types (see the "Core types" section) so that the RESP3 suite is self-contained.
 * <p>
 * RESP3 type prefixes (the first byte determines the type):
 * <ul>
 *   <li>{@code +}: Simple String</li>
 *   <li>{@code -}: Error</li>
 *   <li>{@code :}: Integer</li>
 *   <li>{@code $}: Bulk String (Null bulk is {@code $-1\r\n})</li>
 *   <li>{@code *}: Array (Null array is {@code *-1\r\n})</li>
 *   <li>{@code _}: Null</li>
 *   <li>{@code #}: Boolean ({@code #t\r\n} / {@code #f\r\n})</li>
 *   <li>{@code ,}: Double</li>
 *   <li>{@code (}: Big Number (arbitrary-precision integer)</li>
 *   <li>{@code =}: Verbatim String ({@code XXX:} format prefix)</li>
 *   <li>{@code !}: Bulk Error (bulk framing, error semantics)</li>
 *   <li>{@code ~}: Set</li>
 *   <li>{@code %}: Map (count is the number of key/value PAIRS)</li>
 *   <li>{@code >}: Push</li>
 *   <li>{@code |}: Attribute (metadata that prefixes the real reply)</li>
 * </ul>
 *
 * <p>
 * Note on sizes: the RESP3 protocol permits payloads up to 512 MB, but the "large"
 * boundary examples here deliberately use a modest value ({@value #LARGE_PAYLOAD_SIZE} bytes
 * or elements) so that tests remain fast and easy to constrain.</p>
 */
public final class RESP3Examples {
  /** The CRLF terminator used throughout RESP. */
  private static final String CRLF = "\r\n";

  /**
   * Sensible "large" payload size (in bytes, or element count) used for boundary examples.
   * The protocol permits far more (512 MB for the bulk-framed types) but tests should stay small.
   */
  public static final int LARGE_PAYLOAD_SIZE = 512;

  /** A payload of {@link #LARGE_PAYLOAD_SIZE} ASCII bytes, used to build the "large" examples. */
  private static final String LARGE_PAYLOAD = repeat('x', LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Core types (unchanged from RESP2, restated so the RESP3 suite is self-contained)
  // ------------------------------------------------------------------------------------------

  /** Simple String: the canonical PING reply. */
  @Expected("SIMPLE:PONG")
  public static final String SIMPLE_PONG = "+PONG\r\n";

  /** Error: message is the payload after the '-'. */
  @Expected("ERROR:ERR unknown command")
  public static final String ERROR_GENERIC = "-ERR unknown command\r\n";

  /** Integer. */
  @Expected("NUMBER:7")
  public static final String INTEGER_SEVEN = ":7\r\n";

  /** Bulk String. */
  @Expected("BULK:foo")
  public static final String BULK_FOO = "$3\r\nfoo\r\n";

  /** Null Bulk String ({@code $-1}). */
  @Expected("NULL")
  public static final String BULK_NULL = "$-1\r\n";

  /** Array of two Integers. */
  @Expected("MULTI:[1, 2]")
  public static final String ARRAY_OF_INTEGERS = "*2\r\n:1\r\n:2\r\n";

  /** Null Array ({@code *-1}). */
  @Expected("NULL")
  public static final String ARRAY_NULL = "*-1\r\n";

  // ------------------------------------------------------------------------------------------
  // Null (_)
  //   A '_' followed by CRLF. The RESP3 typed null; distinct from a null bulk/array length.
  // ------------------------------------------------------------------------------------------

  /** Typical: the typed Null. */
  @Expected("NULL")
  public static final String NULL_NULL = "_\r\n";

  /** Typical: typed Null and Boolean nested inside an Array. */
  @Expected("MULTI:[null, false]")
  public static final String NULL_AND_BOOL_IN_ARRAY = "*2\r\n_\r\n#f\r\n";

  // ------------------------------------------------------------------------------------------
  // Boolean (#)
  //   '#t\r\n' is true, '#f\r\n' is false. Only lowercase 't' / 'f' are valid.
  // ------------------------------------------------------------------------------------------

  /** Typical: the true value. */
  @Expected("BOOLEAN:true")
  public static final String BOOL_TRUE = "#t\r\n";

  /** Typical: the false value. */
  @Expected("BOOLEAN:false")
  public static final String BOOL_FALSE = "#f\r\n";

  // ------------------------------------------------------------------------------------------
  // Double (,)
  //   ',' + CRLF-terminated string. Includes the special forms inf, -inf and nan.
  // ------------------------------------------------------------------------------------------

  /** Typical: a plain fractional value. */
  @Expected("NUMBER:1.23")
  public static final String DOUBLE_FRACTION = ",1.23\r\n";

  /** Boundary: zero renders in double form. */
  @Expected("NUMBER:0.0")
  public static final String DOUBLE_ZERO = ",0\r\n";

  /** Typical: a negative value. */
  @Expected("NUMBER:-2.5")
  public static final String DOUBLE_NEGATIVE = ",-2.5\r\n";

  /** Typical: exponent notation normalizes to a plain decimal. */
  @Expected("NUMBER:1000.0")
  public static final String DOUBLE_EXPONENT = ",1e3\r\n";

  /** Boundary: positive infinity (spec form 'inf'). */
  @Expected("NUMBER:Infinity")
  public static final String DOUBLE_POSITIVE_INFINITY = ",inf\r\n";

  /** Boundary: negative infinity (spec form '-inf'). */
  @Expected("NUMBER:-Infinity")
  public static final String DOUBLE_NEGATIVE_INFINITY = ",-inf\r\n";

  /** Boundary: NaN (spec form 'nan'). */
  @Expected("NUMBER:NaN")
  public static final String DOUBLE_NAN = ",nan\r\n";

  // ------------------------------------------------------------------------------------------
  // Big Number (()
  //   '(' + an arbitrary-precision base-10 integer + CRLF.
  // ------------------------------------------------------------------------------------------

  /** Typical: a positive value beyond int64. */
  @Expected("NUMBER:349289032890209384209384")
  public static final String BIGNUM_POSITIVE = "(349289032890209384209384\r\n";

  /** Typical: a negative value beyond int64. */
  @Expected("NUMBER:-273158415165407483457588347989")
  public static final String BIGNUM_NEGATIVE = "(-273158415165407483457588347989\r\n";

  /** Boundary: zero. */
  @Expected("NUMBER:0")
  public static final String BIGNUM_ZERO = "(0\r\n";

  // ------------------------------------------------------------------------------------------
  // Verbatim String (=)
  //   '=' + byte-count + CRLF + <3-char format> + ':' + <data> + CRLF.
  //   Same framing as a Bulk String but the payload carries a format prefix.
  // ------------------------------------------------------------------------------------------

  /** Typical: a 'txt' verbatim string; the value drops the 'txt:' prefix. */
  @Expected("BULK:Some string")
  public static final String VERBATIM_TXT = "=15\r\ntxt:Some string\r\n";

  /** Boundary: a 'txt' verbatim string with an empty value (payload is exactly 'txt:'). */
  @Expected("BULK:")
  public static final String VERBATIM_TXT_EMPTY = "=4\r\ntxt:\r\n";

  /** Typical: a 'mdn' (markdown) verbatim string. */
  @Expected("BULK:This is a *markdown*!")
  public static final String VERBATIM_MARKDOWN = "=25\r\nmdn:This is a *markdown*!\r\n";

  /**
   * Boundary: a binary-safe verbatim string whose value (after the {@code txt:} prefix) contains
   * CR, LF and NUL bytes. The declared length (9) counts the 4-byte {@code txt:} prefix plus the
   * 5 raw value bytes 'a', '\r', '\n', 0x00, 'b'; the embedded CRLF is data, not a terminator.
   */
  @Expected("BULK:a\r\n\u0000b")
  public static final String VERBATIM_BINARY = "=9\r\ntxt:a\r\n\u0000b\r\n";

  /**
   * Boundary: a large verbatim string whose value is {@link #LARGE_PAYLOAD_SIZE} bytes (after the
   * 4-byte {@code txt:} prefix, so the declared length is {@code 4 + LARGE_PAYLOAD_SIZE}).
   */
  @Expected(method = "expectedVerbatimLarge")
  public static final String VERBATIM_LARGE = "=" + (4 + LARGE_PAYLOAD_SIZE) + CRLF + "txt:" + LARGE_PAYLOAD + CRLF;

  // ------------------------------------------------------------------------------------------
  // Bulk Error (!)
  //   '!' + byte-count + CRLF + <data> + CRLF. Bulk String framing, but the value has error
  //   semantics (like '-'); used for multi-line / binary-safe error messages (RESP3).
  // ------------------------------------------------------------------------------------------

  /** Typical: a Bulk Error carrying a plain error message. */
  @Expected("ERROR:SYNTAX invalid syntax")
  public static final String BULK_ERROR_SYNTAX = "!21\r\nSYNTAX invalid syntax\r\n";

  /** Boundary: the empty Bulk Error. */
  @Expected("ERROR:")
  public static final String BULK_ERROR_EMPTY = "!0\r\n\r\n";

  /**
   * Boundary: a binary-safe Bulk Error whose message contains CR, LF and NUL bytes. The declared
   * length (5) counts raw bytes, so the embedded CRLF is part of the message, not a terminator.
   */
  @Expected("ERROR:a\r\n\u0000b")
  public static final String BULK_ERROR_BINARY = "!5\r\na\r\n\u0000b\r\n";

  /** Boundary: a large Bulk Error of {@link #LARGE_PAYLOAD_SIZE} bytes. */
  @Expected(method = "expectedBulkErrorLarge")
  public static final String BULK_ERROR_LARGE = "!" + LARGE_PAYLOAD_SIZE + CRLF + LARGE_PAYLOAD + CRLF;

  // ------------------------------------------------------------------------------------------
  // Set (~)
  //   '~' + element-count + CRLF, followed by that many RESP values (distinct elements).
  // ------------------------------------------------------------------------------------------

  /** Boundary: the empty Set. */
  @Expected("MULTI:[]")
  public static final String SET_EMPTY = "~0\r\n";

  /** Typical: a Set of Bulk Strings (renders as an ordered sequence). */
  @Expected("MULTI:[foo, bar]")
  public static final String SET_OF_BULK = "~2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n";

  /** Typical: a heterogeneous Set (spec example). */
  @Expected("MULTI:[orange, apple, true]")
  public static final String SET_MIXED = "~3\r\n+orange\r\n+apple\r\n#t\r\n";

  /** Boundary: a large Set of {@link #LARGE_PAYLOAD_SIZE} Integers. */
  @Expected(method = "expectedSetLarge")
  public static final String SET_LARGE = buildIntegerAggregate('~', LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Map (%)
  //   '%' + <number of key/value PAIRS> + CRLF, followed by 2 * count RESP values.
  // ------------------------------------------------------------------------------------------

  /** Boundary: the empty Map. */
  @Expected("MULTI:{}")
  public static final String MAP_EMPTY = "%0\r\n";

  /** Typical: a Map of two string key/value pairs. */
  @Expected("MULTI:{key1: value1, key2: value2}")
  public static final String MAP_TWO_PAIRS =
    "%2\r\n$4\r\nkey1\r\n$6\r\nvalue1\r\n$4\r\nkey2\r\n$6\r\nvalue2\r\n";

  /** Typical: a Map whose value is an Array (spec example, simplified). */
  @Expected("MULTI:{mykey: [Hello, World]}")
  public static final String MAP_WITH_ARRAY_VALUE =
    "%1\r\n$5\r\nmykey\r\n*2\r\n$5\r\nHello\r\n$5\r\nWorld\r\n";

  /** Typical: a Map with heterogeneous value types (bulk value + integer key). */
  @Expected("MULTI:{first: item1, second: item2}")
  public static final String MAP_HETEROGENEOUS =
    "%2\r\n$5\r\nfirst\r\n$5\r\nitem1\r\n+second\r\n$5\r\nitem2\r\n";

  /** Boundary: a large Map of {@link #LARGE_PAYLOAD_SIZE} bulk key/value pairs. */
  @Expected(method = "expectedMapLarge")
  public static final String MAP_LARGE = buildMap(LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Push (>)
  //   '>' + element-count + CRLF, followed by that many RESP values. Identical framing to Array.
  // ------------------------------------------------------------------------------------------

  /** Typical: a pub/sub push (a push has at least one element). */
  @Expected("PUSH:[pubsub, message, new message]")
  public static final String PUSH_PUBSUB = ">3\r\n+pubsub\r\n+message\r\n+new message\r\n";

  /** Typical: a single-element push carrying an Integer. */
  @Expected("PUSH:[42]")
  public static final String PUSH_INTEGER = ">1\r\n:42\r\n";

  /** Boundary: a large Push of {@link #LARGE_PAYLOAD_SIZE} Integers. */
  @Expected(method = "expectedPushLarge")
  public static final String PUSH_LARGE = buildIntegerAggregate('>', LARGE_PAYLOAD_SIZE);

  // ------------------------------------------------------------------------------------------
  // Attribute (|)
  //   '|' + <number of key/value PAIRS> + CRLF, followed by 2 * count RESP values.
  //   An attribute is metadata that prefixes the real reply. The parser parses and discards it,
  //   so a reply prefixed by an attribute surfaces ONLY the following reply. A standalone attribute
  //   (with no reply after it) surfaces nothing at all - neither a value nor an error - so there is
  //   no response to assert and it is intentionally not represented here.
  // ------------------------------------------------------------------------------------------

  /**
   * Typical: a time-series attribute prefixing a Bulk String reply. The attribute ({@code ttl}) is
   * parsed and dropped, so only the {@code value} reply is surfaced.
   */
  @Expected("BULK:value")
  public static final String ATTRIBUTE_THEN_REPLY = "|1\r\n+ttl\r\n:3600\r\n$5\r\nvalue\r\n";

  // ------------------------------------------------------------------------------------------
  // Nested / composite aggregates
  // ------------------------------------------------------------------------------------------

  /** An Array containing a Map, a Set and a Double (exercises nested RESP3 types). */
  @Expected("MULTI:[{a: b}, [true], 1.5]")
  public static final String ARRAY_OF_RESP3_AGGREGATES =
    "*3\r\n%1\r\n$1\r\na\r\n$1\r\nb\r\n~1\r\n#t\r\n,1.5\r\n";

  // ------------------------------------------------------------------------------------------
  // Multi-reply streams (several top-level replies concatenated on one read)
  // ------------------------------------------------------------------------------------------

  /** A stream of scalar replies: Boolean, Double, Integer, Null. */
  @Expected({ "BOOLEAN:true", "NUMBER:1.5", "NUMBER:42", "NULL" })
  public static final String STREAM_RESP3_SCALARS = "#t\r\n,1.5\r\n:42\r\n_\r\n";

  /** A stream of a Push followed by a Bulk String reply. */
  @Expected({ "PUSH:[1]", "BULK:foo" })
  public static final String STREAM_PUSH_THEN_REPLY = ">1\r\n:1\r\n$3\r\nfoo\r\n";

  // ------------------------------------------------------------------------------------------
  // Expected-value computations for the large examples (referenced from @Expected)
  // ------------------------------------------------------------------------------------------

  static String[] expectedVerbatimLarge() {
    // the 'txt:' format prefix is stripped, leaving the raw payload as the value
    return new String[] { "BULK:" + LARGE_PAYLOAD };
  }

  static String[] expectedBulkErrorLarge() {
    return new String[] { "ERROR:" + LARGE_PAYLOAD };
  }

  static String[] expectedSetLarge() {
    return new String[] { integerSequence("MULTI:[", LARGE_PAYLOAD_SIZE) };
  }

  static String[] expectedPushLarge() {
    return new String[] { integerSequence("PUSH:[", LARGE_PAYLOAD_SIZE) };
  }

  static String[] expectedMapLarge() {
    StringBuilder sb = new StringBuilder("MULTI:{");
    for (int i = 0; i < LARGE_PAYLOAD_SIZE; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append('k').append(i).append(": v").append(i);
    }
    sb.append('}');
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

  /** {@code prefix} followed by the integers {@code 0..count-1} comma-separated, then a {@code ]}. */
  private static String integerSequence(String prefix, int count) {
    StringBuilder sb = new StringBuilder(prefix);
    for (int i = 0; i < count; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(i);
    }
    sb.append(']');
    return sb.toString();
  }

  /** An aggregate {@code type} holding {@code count} Integers {@code 0..count-1} (Set '~', Push '>'). */
  private static String buildIntegerAggregate(char type, int count) {
    StringBuilder sb = new StringBuilder();
    sb.append(type).append(count).append(CRLF);
    for (int i = 0; i < count; i++) {
      sb.append(':').append(i).append(CRLF);
    }
    return sb.toString();
  }

  /** A Map of {@code pairs} bulk key/value pairs, keys {@code k0..} and values {@code v0..}. */
  private static String buildMap(int pairs) {
    StringBuilder sb = new StringBuilder();
    sb.append('%').append(pairs).append(CRLF);
    for (int i = 0; i < pairs; i++) {
      String k = "k" + i;
      String v = "v" + i;
      sb.append('$').append(k.length()).append(CRLF).append(k).append(CRLF);
      sb.append('$').append(v.length()).append(CRLF).append(v).append(CRLF);
    }
    return sb.toString();
  }
}
