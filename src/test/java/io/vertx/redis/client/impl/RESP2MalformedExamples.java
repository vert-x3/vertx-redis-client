package io.vertx.redis.client.impl;

import io.vertx.redis.client.impl.RESPTestBase.Expected;

/**
 * A suite of <strong>malformed</strong> RESP2 wire-format examples, structured to mirror
 * {@link RESP2Examples} (same type grouping, same ordering). Each constant is a small or
 * "weird" corruption of an otherwise-correct RESP2 reply and is intended to drive
 * <em>error</em> conditions in a reply parser.
 *
 * <h2>Guarantee: invalid under RESP2 <em>and</em> RESP3</h2>
 * The production parser speaks RESP3, which is a strict superset of RESP2. Therefore a vector
 * is only useful as a negative test if it is rejected by <em>both</em> protocols. This is
 * ensured by construction:
 * <ul>
 *   <li>RESP3 reuses the <em>identical</em> framing rules for the five RESP2 types
 *       ({@code + - : $ *}). A corruption of those types (bad terminator, length mismatch,
 *       embedded CR/LF, illegal length, ...) is therefore invalid in RESP2 and RESP3 alike.</li>
 *   <li>"Unknown type byte" vectors deliberately use bytes that are type bytes in
 *       <strong>neither</strong> protocol. RESP2 type bytes are {@code + - : $ *}; RESP3 adds
 *       {@code _ # , ( = ! ~ % > |}. None of those RESP3-only bytes are used here.</li>
 *   <li>Lenient-parse grey areas that a permissive RESP2 parser might still accept (such as
 *       a leading {@code +} sign or leading zeros on a number) are intentionally
 *       <strong>excluded</strong>, so every vector here is unambiguously invalid.</li>
 * </ul>
 *
 * <h2>Scope</h2>
 * These are <em>reply</em> vectors (server to client). RESP2 "inline commands" are a
 * request-only feature (client to server) and are not applicable to reply parsing, so a reply
 * that does not begin with a valid type byte is simply invalid.
 *
 * <h2>Note on incomplete messages</h2>
 * The parser is a streaming state machine: a message that merely lacks its terminator, or whose
 * announced length exceeds the bytes present, is <em>incomplete</em> rather than invalid.
 * The parser waits for more data instead of reporting an error. Such truncated vectors are therefore
 * not included here.
 */
public final class RESP2MalformedExamples {
  // ------------------------------------------------------------------------------------------
  // Simple Strings (+)
  //   Spec: '+' + a string that MUST NOT contain CR or LF + CRLF.
  //   RESP3 framing is identical, so all of these are invalid in both protocols.
  // ------------------------------------------------------------------------------------------

  /**
   * Defect: payload contains an embedded lone CR (CR is forbidden).
   * Accepted: the parser keeps the embedded CR as part of the value.
   */
  @Expected("SIMPLE:he\rllo")
  public static final String SIMPLE_CR_INSIDE = "+he\rllo\r\n";

  /**
   * Defect: payload contains an embedded lone LF (LF is forbidden).
   * Accepted: the parser only terminates on CRLF, so the lone LF is kept as part of the value.
   */
  @Expected("SIMPLE:he\nllo")
  public static final String SIMPLE_LF_INSIDE = "+he\nllo\r\n";

  // ------------------------------------------------------------------------------------------
  // Errors (-)
  //   Spec: same format as a Simple String but prefixed with '-'.
  // ------------------------------------------------------------------------------------------

  /**
   * Defect: message contains an embedded lone CR.
   * Accepted: the parser keeps the embedded CR as part of the message.
   */
  @Expected("ERROR:err\rmsg")
  public static final String ERROR_CR_INSIDE = "-err\rmsg\r\n";

  // ------------------------------------------------------------------------------------------
  // Integers (:)
  //   Spec: ':' + a CRLF-terminated string representing an integer,
  //         guaranteed to fit in a signed 64-bit integer.
  // ------------------------------------------------------------------------------------------

  /** Defect: payload is not a number. */
  public static final String INTEGER_NOT_A_NUMBER = ":abc\r\n";

  /** Defect: empty payload (no integer present). */
  public static final String INTEGER_EMPTY = ":\r\n";

  /** Defect: a fractional value in the integer type (RESP has no float in this slot). */
  public static final String INTEGER_FLOAT = ":1.5\r\n";

  /** Defect: embedded space inside the numeric payload. */
  public static final String INTEGER_SPACE = ":1 2\r\n";

  /** Defect: value exceeds the signed 64-bit range the spec guarantees. */
  public static final String INTEGER_OVERFLOW = ":99999999999999999999\r\n";

  /** Defect: a lone minus sign with no digits. */
  public static final String INTEGER_SIGN_ONLY = ":-\r\n";

  /** Defect: leading space before the digits. */
  public static final String INTEGER_LEADING_SPACE = ": 1\r\n";

  /** Defect: trailing non-numeric junk after the digits. */
  public static final String INTEGER_TRAILING_JUNK = ":1x\r\n";

  /** Defect: a lone plus sign with no digits. */
  public static final String INTEGER_SIGN_PLUS = ":+\r\n";

  /** Defect: two signs in a row, neither usable as a digit. */
  public static final String INTEGER_SIGN_FLIPPED = ":+-1\r\n";

  /** Defect: an embedded space in the middle of the digits. */
  public static final String INTEGER_EMBEDDED_SPACE = ":12 34\r\n";

  // ------------------------------------------------------------------------------------------
  // Bulk Strings ($)
  //   Spec: '$' + byte-count + CRLF + <data> + CRLF.
  //         Empty is '$0\r\n\r\n'; Null is '$-1\r\n'; length is at most 512 MB.
  // ------------------------------------------------------------------------------------------

  /**
   * Defect: declared length (2) is smaller than the data, leaving trailing junk.
   * Accepted: the parser reads the declared length and then skips to the next CRLF,
   * absorbing the trailing junk (intentional leniency).
   */
  @Expected("BULK:fo")
  public static final String BULK_LEN_TOO_SHORT = "$2\r\nfoobar\r\n";

  /**
   * Defect: length is {@code -0}.
   * Accepted: the parser reads it as length 0, yielding an empty Bulk String.
   */
  @Expected("BULK:")
  public static final String BULK_LEN_NEG_ZERO = "$-0\r\n\r\n";

  /** Defect: negative length other than -1 (only -1 is the legal Null form). */
  public static final String BULK_NEGATIVE_LENGTH = "$-2\r\n";

  /** Defect: length is not a number. */
  public static final String BULK_NON_NUMERIC_LENGTH = "$abc\r\nfoo\r\n";

  /** Defect: length is a fractional value. */
  public static final String BULK_LEN_FLOAT = "$1.5\r\nfoo\r\n";

  /** Defect: length has a leading space. */
  public static final String BULK_LEN_SPACE = "$ 3\r\nfoo\r\n";

  /** Defect: length is a lone minus sign with no digits. */
  public static final String BULK_LEN_SIGN_ONLY = "$-\r\n";

  /** Defect: length is a lone plus sign with no digits. */
  public static final String BULK_LEN_SIGN_PLUS = "$+\r\n";

  /** Defect: length field is empty. */
  public static final String BULK_EMPTY_LENGTH = "$\r\nfoo\r\n";

  /** Defect: declared length exceeds the 512 MB protocol maximum (512 MB + 1). */
  public static final String BULK_OVER_LIMIT_LENGTH = "$536870913\r\n";

  /** Defect: declared length exceeds the signed 32-bit range the parser accepts. */
  public static final String BULK_LEN_OVERFLOW = "$2147483648\r\n";

  /** Defect: a lone LF embedded in the length (LF is not a valid digit). */
  public static final String BULK_LEN_LF = "$3\nfoo\r\n";

  // ------------------------------------------------------------------------------------------
  // Arrays (*)
  //   Spec: '*' + element-count + CRLF, followed by exactly that many RESP values.
  //         Empty is '*0\r\n'; Null is '*-1\r\n'.
  // ------------------------------------------------------------------------------------------

  /** Defect: negative count other than -1 (only -1 is the legal Null Array form). */
  public static final String ARRAY_NEGATIVE_COUNT = "*-2\r\n";

  /** Defect: count is not a number. */
  public static final String ARRAY_NON_NUMERIC_COUNT = "*x\r\n";

  /** Defect: count is a fractional value. */
  public static final String ARRAY_COUNT_FLOAT = "*1.5\r\n";

  /** Defect: count has a leading space. */
  public static final String ARRAY_COUNT_SPACE = "* 2\r\n:1\r\n:2\r\n";

  /** Defect: count is a lone minus sign with no digits. */
  public static final String ARRAY_COUNT_SIGN_ONLY = "*-\r\n";

  /** Defect: count is a lone plus sign with no digits. */
  public static final String ARRAY_COUNT_SIGN_PLUS = "*+3\r\n";

  /** Defect: count exceeds the signed 32-bit range the parser accepts. */
  public static final String ARRAY_COUNT_OVERFLOW = "*2147483648\r\n";

  /** Defect: count field is empty. */
  public static final String ARRAY_EMPTY_COUNT = "*\r\n";

  /** Defect: an element does not begin with a valid RESP type byte. */
  public static final String ARRAY_BAD_ELEMENT = "*1\r\nhello\r\n";

  /** Defect: an element is itself malformed (a bulk string with a bad length). */
  public static final String ARRAY_MALFORMED_ELEMENT = "*2\r\n:1\r\n$abc\r\n";

  // ------------------------------------------------------------------------------------------
  // Configured limits (NOT protocol-invalid)
  //   These vectors are legal RESP2/RESP3 but are rejected by the parser's configured safety
  //   limits (maxStack / maxMultiLength). They are the one exception to the
  //   "invalid under RESP2 and RESP3" guarantee stated in the class javadoc.
  // ------------------------------------------------------------------------------------------

  /** Defect: element count exceeds the configured maxMultiLength (default 65535). */
  public static final String ARRAY_COUNT_OVER_MAX_MULTI = "*70000\r\n";

  /** Defect: array nesting exceeds the configured maxStack depth (default 32). */
  public static final String ARRAY_NESTING_TOO_DEEP = nestedArrays(33);

  // ------------------------------------------------------------------------------------------
  // Unknown / illegal type byte
  //   RESP2 replies must start with one of: + - : $ *
  //   RESP3 additionally allows:            _ # , ( = ! ~ % > |
  //   The bytes below are type bytes in NEITHER protocol, so each is invalid in both.
  // ------------------------------------------------------------------------------------------

  /** Defect: '@' is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_AT = "@foo\r\n";

  /** Defect: '.' is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_DOT = ".123\r\n";

  /** Defect: '&' is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_AMPERSAND = "&bang\r\n";

  /** Defect: a leading digit is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_DIGIT = "0test\r\n";

  /** Defect: a leading space is not a RESP2 or RESP3 type byte. */
  public static final String TYPE_UNKNOWN_SPACE = " lead\r\n";

  /** Builds {@code depth} nested single-element array headers ({@code *1\r\n} repeated). */
  private static String nestedArrays(int depth) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < depth; i++) {
      sb.append("*1\r\n");
    }
    return sb.toString();
  }
}
