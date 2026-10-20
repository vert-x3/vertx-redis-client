package io.vertx.redis.client.impl;

import io.vertx.redis.client.impl.RESPTestBase.Expected;

/**
 * A suite of <strong>incomplete</strong> RESP3 wire-format examples: inputs that are a valid
 * <em>prefix</em> of a reply but have not yet delivered its terminator. The parser is a streaming
 * state machine, so its correct behavior on these is to <em>wait for more data</em>. It must neither
 * emit a reply nor report an error. Each vector is therefore annotated {@code @Expected({})}
 * (an explicitly empty expectation, i.e. a stall), which {@link RESP3Test#incompleteExample} asserts
 * as "no value and no error".
 * <p>
 * These are deliberately kept separate from {@link RESP3MalformedExamples} (which must be
 * <em>rejected</em>) and {@link RESP3Examples} (which must <em>parse to a value</em>): a stall is a
 * third, distinct outcome. An incomplete input can become malformed or valid once more bytes
 * arrive; until then it is simply unfinished.
 */
public final class RESP3IncompleteExamples {
  /** Array header announcing two elements but delivering only one so far; waits for the next. */
  @Expected({})
  public static final String ARRAY_MISSING_ELEMENT = "*2\r\n+ab\r\n";

  /**
   * A valid verbatim-string header whose payload is followed by junk and no {@code \r\n}.
   */
  @Expected({})
  public static final String VERBATIM_TRAILER_JUNK = "=15\r\ntxt:Some stringXX";
}
