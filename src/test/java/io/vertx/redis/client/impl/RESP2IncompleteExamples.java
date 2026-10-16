package io.vertx.redis.client.impl;

import io.vertx.redis.client.impl.RESPTestBase.Expected;

/**
 * A suite of <strong>incomplete</strong> RESP2 wire-format examples: inputs that are a valid
 * <em>prefix</em> of a reply but have not yet delivered its terminator. The parser is a streaming
 * state machine, so its correct behavior on these is to <em>wait for more data</em>. It must neither
 * emit a reply nor report an error. Each vector is therefore annotated {@code @Expected({})}
 * (an explicitly empty expectation, i.e. a stall), which {@link RESP2Test#incompleteExample} asserts
 * as "no value and no error".
 * <p>
 * These are deliberately kept separate from {@link RESP2MalformedExamples} (which must be
 * <em>rejected</em>) and {@link RESP2Examples} (which must <em>parse to a value</em>): a stall is a
 * third, distinct outcome. An incomplete input can become malformed or valid once more bytes
 * arrive; until then it is simply unfinished.
 */
public final class RESP2IncompleteExamples {
  /** Simple string with no terminator yet; the parser waits for the {@code \r\n}. */
  @Expected({})
  public static final String SIMPLE_UNTERMINATED = "+OK";

  /**
   * An integer terminated only by a lone {@code \r}. The terminator is the {@code \r\n}
   * pair, so the bare CR is not a terminator and the parser waits for the matching LF.
   */
  @Expected({})
  public static final String INTEGER_UNTERMINATED = ":12\r";

  /**
   * A valid bulk length header whose payload is followed by junk and no {@code \r\n}.
   * This is a recoverable stall, a later {@code \r\n} completes it.
   */
  @Expected({})
  public static final String BULK_TRAILER_JUNK = "$3\r\nfooXX";
}
