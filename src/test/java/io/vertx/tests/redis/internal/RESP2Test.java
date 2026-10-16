package io.vertx.tests.redis.internal;

import io.vertx.tests.redis.internal.RESPTestBase.Expected;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Feeds every RESP2 example in {@link RESP2Examples} (valid) and {@link RESP2MalformedExamples}
 * (malformed) through the production {@code RESPParser} and asserts the intended contract:
 * <ul>
 *   <li>every <em>valid</em> example parses into exactly the response declared by its
 *       {@link Expected} annotation;</li>
 *   <li>every <em>malformed</em> example is rejected, the parser reports a failure (via its
 *       {@code fail} callback or by throwing) and does not emit a value.</li>
 * </ul>
 * <p>
 * The parser is a streaming state machine, so each vector is fed to a fresh parser as a single
 * complete buffer. A well-formed reply produces one {@code handle} callback, a stream may produce
 * multiple.</p>
 */
public class RESP2Test extends RESPTestBase {
  // ------------------------------------------------------------------------------------------
  // Suite 1: well-formed RESP2 must parse to the specified values
  // ------------------------------------------------------------------------------------------

  static List<Field> validExamples() {
    return exampleVectors(RESP2Examples.class);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("validExamples")
  public void validExample(Field field) throws Exception {
    String wire = (String) field.get(null);
    Result result = parse(wire);
    assertAgainstExpectation(field, result, "Valid");
  }

  // ------------------------------------------------------------------------------------------
  // Suite 2: malformed RESP2 must be rejected
  // ------------------------------------------------------------------------------------------

  static List<Field> malformedExamples() {
    return exampleVectors(RESP2MalformedExamples.class);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("malformedExamples")
  public void malformedExample(Field field) throws Exception {
    String wire = (String) field.get(null);
    Result result = parse(wire);
    assertAgainstExpectation(field, result, "Malformed");
  }

  // ------------------------------------------------------------------------------------------
  // Suite 3: incomplete RESP2 messages must make the parser wait (emit nothing, no error)
  // ------------------------------------------------------------------------------------------

  static List<Field> incompleteExamples() {
    return exampleVectors(RESP2IncompleteExamples.class);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("incompleteExamples")
  public void incompleteExample(Field field) throws Exception {
    String wire = (String) field.get(null);
    Result result = parse(wire);
    assertAgainstExpectation(field, result, "Incomplete");
  }
}
