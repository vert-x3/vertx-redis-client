package io.vertx.redis.client.impl;

import io.vertx.redis.client.impl.RESPTestBase.Expected;

/**
 * A suite of <strong>malformed</strong> RESP3 wire-format examples, grouped by RESP3 type and
 * intended to drive <em>error</em> conditions in a reply parser. Unlike
 * {@link RESP2MalformedExamples} (which has to be invalid under both protocols), this suite is
 * written specifically for RESP3 framing and targets the RESP3-specific types.
 *
 * <h2>Test contract (mirrors {@link RESP2MalformedExamples})</h2>
 * <ul>
 *   <li>A vector with <strong>no</strong> {@link Expected} annotation must be <em>rejected</em>: the
 *       parser reports a failure (via {@code fail} or by throwing) and emits no value.</li>
 *   <li>A vector <em>with</em> an {@link Expected} annotation documents an <em>accepted</em>
 *       lenient behavior and asserts the shown result instead.</li>
 * </ul>
 *
 * <h2>Scope: malformed, not incomplete</h2>
 * The parser is a streaming state machine. A vector that merely lacks a terminator, or whose
 * announced count/length exceeds the bytes present (e.g. {@code ~2} followed by one element), is
 * <em>incomplete</em>. The parser waits for more data rather than reporting an error, so such
 * truncated vectors are deliberately excluded here.
 *
 * <h2>Unknown type bytes</h2>
 * RESP2 replies start with {@code + - : $ *}; RESP3 adds {@code _ # , ( = ! ~ % > |}. The
 * "unknown type byte" vectors use bytes that are type bytes in <strong>neither</strong> protocol.
 */
public final class RESP3MalformedExamples {
  // ------------------------------------------------------------------------------------------
  // Boolean (#)
  //   Spec: '#t\r\n' or '#f\r\n' only.
  // ------------------------------------------------------------------------------------------

  /** Defect: no boolean value between '#' and CRLF (reads the CR as the value byte). */
  public static final String BOOL_EMPTY = "#\r\n";

  /** Defect: a value byte that is neither 't' nor 'f'. */
  public static final String BOOL_INVALID_CHAR = "#x\r\n";

  /** Defect: uppercase 'T', RESP3 requires lowercase 't'. */
  public static final String BOOL_UPPERCASE = "#T\r\n";

  /**
   * Defect: trailing junk after the boolean value.
   * Accepted: after reading the 't' the parser skips to the next CRLF, absorbing the junk.
   */
  @Expected("BOOLEAN:true")
  public static final String BOOL_TRAILING_JUNK = "#tX\r\n";

  // ------------------------------------------------------------------------------------------
  // Double (,)
  //   Spec: ',' + a CRLF-terminated floating point value (or inf / -inf / nan).
  // ------------------------------------------------------------------------------------------

  /** Defect: not a number. */
  public static final String DOUBLE_NOT_A_NUMBER = ",abc\r\n";

  /** Defect: empty payload. */
  public static final String DOUBLE_EMPTY = ",\r\n";

  /** Defect: trailing non-numeric junk after a valid-looking number. */
  public static final String DOUBLE_TRAILING_JUNK = ",1.2x\r\n";

  /** Defect: a lone minus sign with no digits. */
  public static final String DOUBLE_SIGN_ONLY = ",-\r\n";

  /** Defect: uppercase 'INF', the infinity keyword is lowercase 'inf' only. */
  public static final String DOUBLE_UPPER_INFINITY = ",INF\r\n";

  /** Defect: more than one decimal point, not a valid double. */
  public static final String DOUBLE_MULTI_POINT = ",+1.5.0\r\n";

  // ------------------------------------------------------------------------------------------
  // Big Number (()
  //   Spec: '(' + an arbitrary-precision base-10 integer + CRLF.
  //   Invalid payloads (fractional, hex, non-numeric, empty) throw when decoded as BigInteger and
  //   are rejected.
  // ------------------------------------------------------------------------------------------

  /** Defect: a fractional value (big numbers are integers). */
  public static final String BIGNUM_FRACTIONAL = "(1.5\r\n";

  /** Defect: empty payload. */
  public static final String BIGNUM_EMPTY = "(\r\n";

  /** Defect: not a number. */
  public static final String BIGNUM_NOT_A_NUMBER = "(abc\r\n";

  /** Defect: hexadecimal (the spec mandates base-10 digits). */
  public static final String BIGNUM_HEX = "(0x10\r\n";

  /** Defect: an embedded space in the middle of the digits. */
  public static final String BIGNUM_EMBEDDED_SPACE = "(12 34\r\n";

  // ------------------------------------------------------------------------------------------
  // Verbatim String (=)
  //   Spec: '=' + byte-count + CRLF + <3-char format> + ':' + <data> + CRLF.
  //   The payload MUST be at least 4 bytes ("XXX:").
  // ------------------------------------------------------------------------------------------

  /** Defect: payload shorter than the mandatory "XXX:" format prefix (3 bytes). */
  public static final String VERBATIM_TOO_SHORT = "=3\r\nabc\r\n";

  // ------------------------------------------------------------------------------------------
  // Set (~)
  //   Spec: '~' + element-count + CRLF followed by that many RESP values. Same framing as arrays.
  // ------------------------------------------------------------------------------------------

  /** Defect: empty count field. */
  public static final String SET_EMPTY_COUNT = "~\r\n";

  /** Defect: count is not a number. */
  public static final String SET_NON_NUMERIC_COUNT = "~x\r\n";

  /** Defect: negative count other than -1. */
  public static final String SET_NEGATIVE_COUNT = "~-2\r\n";

  // ------------------------------------------------------------------------------------------
  // Map (%)
  //   Spec: '%' + <pair count> + CRLF followed by 2 * count RESP values. Same framing as arrays.
  // ------------------------------------------------------------------------------------------

  /** Defect: empty count field. */
  public static final String MAP_EMPTY_COUNT = "%\r\n";

  /** Defect: count is not a number. */
  public static final String MAP_NON_NUMERIC_COUNT = "%x\r\n";

  /** Defect: negative count other than -1. */
  public static final String MAP_NEGATIVE_COUNT = "%-2\r\n";

  // ------------------------------------------------------------------------------------------
  // Push (>)
  //   Spec: '>' + element-count + CRLF, count is at least 1. Same framing as arrays.
  // ------------------------------------------------------------------------------------------

  /** Defect: a Push with zero elements (a Push must carry at least one). */
  public static final String PUSH_ZERO = ">0\r\n";

  /** Defect: count is not a number. */
  public static final String PUSH_NON_NUMERIC_COUNT = ">x\r\n";

  // ------------------------------------------------------------------------------------------
  // Attribute (|)
  //   Spec: '|' + <pair count> + CRLF, count is at least 1. Same framing as arrays.
  // ------------------------------------------------------------------------------------------

  /** Defect: an Attribute with zero pairs. */
  public static final String ATTRIBUTE_ZERO = "|0\r\n";

  /** Defect: count is not a number. */
  public static final String ATTRIBUTE_NON_NUMERIC_COUNT = "|x\r\n";

  // ------------------------------------------------------------------------------------------
  // Unknown / illegal type byte
  //   RESP2 replies must start with one of: + - : $ *
  //   RESP3 additionally allows:            _ # , ( = ! ~ % > |
  //   The bytes below are type bytes in NEITHER protocol, so each is invalid in RESP3.
  // ------------------------------------------------------------------------------------------

  /** Defect: '@' is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_AT = "@foo\r\n";

  /** Defect: '?' is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_QUESTION = "?foo\r\n";

  /** Defect: a leading digit is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_DIGIT = "0test\r\n";

  /** Defect: a leading space is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_SPACE = " lead\r\n";
}
