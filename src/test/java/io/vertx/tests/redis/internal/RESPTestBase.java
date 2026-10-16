package io.vertx.tests.redis.internal;

import io.vertx.core.buffer.Buffer;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.ParserHandler;
import io.vertx.redis.client.impl.RESPParser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Shared utils for the RESP wire-format test suites.
 * <p>
 * The helpers feed each wire example through the production {@link RESPParser} as a single buffer,
 * render parsed replies, and read the {@link Expected} annotation off the example constants via
 * reflection. Subclasses only declare the parameterized tests and point them at their own example
 * classes.
 */
abstract class RESPTestBase {

  /** Collects what a single parse run produced so a test can assert on it. */
  protected static final class Result {
    final List<Response> values = new ArrayList<>();
    final List<Throwable> errors = new ArrayList<>();
  }

  /**
   * Upper bound on replies a single parse run may emit before we declare a runaway and abort.
   * No legitimate vector emits anywhere near this many top-level replies (a large aggregate is
   * still emitted as a <em>single</em> {@code MULTI} reply), so tripping this cap means the parser
   * looped. Aborting here turns a would-be OOM / hang into a clean test failure.
   */
  private static final int RUNAWAY_CAP = 100_000;

  /** All {@code public static final String} fields of the given class. */
  protected static List<Field> exampleVectors(Class<?> owner) {
    List<Field> fields = new ArrayList<>();
    for (Field field : owner.getDeclaredFields()) {
      int mods = field.getModifiers();
      if (Modifier.isPublic(mods)
          && Modifier.isStatic(mods)
          && Modifier.isFinal(mods)
          && field.getType() == String.class) {
        fields.add(field);
      }
    }
    return fields;
  }

  /** Feed one example (as a single buffer) through a fresh parser and collect the outcomes. */
  protected static Result parse(String example) {
    Result result = new Result();
    // Guard against a runaway parser: if it keeps emitting or failing with no progress,
    // throw to break its loop. A correct parser never reaches the cap, so this never trips
    // on well-behaved vectors and bounds memory instead of OOM-ing the JVM.
    int[] emissions = { 0 };
    ParserHandler handler = new ParserHandler() {
      private void inc() {
        if (++emissions[0] > RUNAWAY_CAP) {
          throw new RuntimeException("Runaway parser: emitted more than "
            + RUNAWAY_CAP + " replies for input, looks like endless loop");
        }
      }

      @Override
      public void handle(Response response) {
        inc();
        result.values.add(response);
      }

      @Override
      public void fail(Throwable t) {
        inc();
        result.errors.add(t);
      }
    };
    RESPParser parser = new RESPParser(handler);
    try {
      parser.handle(Buffer.buffer(example));
    } catch (Throwable t) {
      result.errors.add(t);
    }
    return result;
  }

  /** Render a single reply the same way for every type: {@code type:toString}. */
  protected static String render(Response response) {
    // The tests assert on a stable string projection of each reply rather than the `Response`
    // object graph. A null reply renders `"NULL"`.
    return response == null ? "NULL" : response.type() + ":" + response;
  }

  protected static List<String> renderAll(Result result) {
    List<String> responses = new ArrayList<>();
    for (Response response : result.values) {
      responses.add(render(response));
    }
    return responses;
  }

  /**
   * Read the {@link Expected} annotation, resolving either its inline {@code value} or a reference
   * to a static {@code method} that returns a {@code String[]}.
   */
  private static String[] resolveExpected(Field field, Expected expected) {
    if (expected == null) {
      return null; // absent: the example carries no expectation, so it must be rejected
    }
    if (!expected.method().isEmpty()) {
      try {
        Method method = field.getDeclaringClass().getDeclaredMethod(expected.method());
        method.setAccessible(true);
        return (String[]) method.invoke(null);
      } catch (ReflectiveOperationException e) {
        throw new AssertionError("Field " + field.getDeclaringClass() + "#" + field.getName()
          + " references method '" + expected.method() + "'", e);
      }
    }
    if (expected.value().length == 1 && Expected.UNSET.equals(expected.value()[0])) {
        throw new AssertionError("Field " + field.getDeclaringClass() + "#" + field.getName()
          + " left @Expected value at its sentinel; set value() or method()");
    }
    return expected.value(); // zero, one or more expected replies
  }

  /**
   * Apply the three annotation states to a parse result:
   * <ul>
   *   <li>no annotation (null): the input must be rejected;</li>
   *   <li>empty expectation (length 0): a <em>stall</em>, neither a value nor an error;</li>
   *   <li>non-empty expectation: exactly those replies, with no error.</li>
   * </ul>
   */
  protected static void assertAgainstExpectation(Field field, Result result, String description) {
    String name = field.getName();
    Expected expected = readExpected(field);
    String[] expectation = resolveExpected(field, expected);
    if (expectation == null) {
      // no expectation: a well-behaved parser reports a failure (via `fail()` or a throw) and emits no value
      assertNotEquals(List.of(), result.errors, description + " example " + name + " was NOT rejected: " + renderAll(result));
      assertTrue(result.values.isEmpty(),
        description + " example " + name + " emitted values AND an error: " + renderAll(result));
    } else if (expectation.length == 0) {
      // stall: neither a value nor an error is emitted, the parser is waiting for more data
      assertEquals(List.of(), result.values, "Incomplete example " + name + " unexpectedly emitted a value");
      assertEquals(List.of(), result.errors, "Incomplete example " + name + " unexpectedly reported an error");
    } else {
      assertEquals(Arrays.asList(expectation), renderAll(result), description + " example " + name + " wrong value");
      assertEquals(List.of(), result.errors, description + " example " + name + " unexpectedly reported an error");
    }
  }

  /** Read the {@link Expected} annotation from a field. Returns {@code null} if missing. */
  protected static Expected readExpected(Field field) {
    return field.getAnnotation(Expected.class);
  }

  /**
   * Declares the expected parse result of a RESP example constant, keeping the expectation next to
   * the wire-format example it describes.
   * <p>
   * The expectation is expressed as the canonical rendering used by the test: {@code TYPE:value}
   * for a parsed response (e.g. {@code "SIMPLE:OK"}, {@code "MULTI:[foo, bar]"}), or the literal
   * {@code "NULL"} for a null reply. A stream (several concatenated replies) lists one entry
   * per emitted response.
   * <p>
   * Exactly one of the two members is used:
   * </p>
   * <ul>
   *   <li>{@link #value()}: the expected responses, for the common small case</li>
   *   <li>{@link #method()}: the name of a {@code static String[]} method on the annotated field's
   *       declaring class that computes the expectation, used when the value is too large or too
   *       awkward to inline (e.g. a 512-byte payload, a 512-element array, or a payload containing a
   *       NUL byte).
   *   </li>
   * </ul>
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Expected {
    /**
     * Sentinel default for {@link #value()}. A rendering is always {@code TYPE:value} or
     * {@code NULL}, so this value can never be produced by ordinary parsing. Its presence
     * means the member was left unset; an explicit {@code @Expected({})} instead carries
     * a zero-length array, which denotes a <em>stall</em> (no emission).
     */
    String UNSET = "___ set value() or method() ___";

    /**
     * The expected responses, in emission order. Note that {@code @Expected({})} means
     * "expect nothing" (a stall), whereas leaving {@code value()} unset means fall back
     * to {@link #method()}.
     */
    String[] value() default { UNSET };

    /**
     * Name of a {@code static String[]} method on the declaring class that computes
     * the expected responses, in emission order.
     */
    String method() default "";
  }
}
