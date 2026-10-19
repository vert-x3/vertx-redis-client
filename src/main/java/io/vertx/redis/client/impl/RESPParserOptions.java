package io.vertx.redis.client.impl;

/**
 * Safety limits for {@link RESPParser}.
 */
public final class RESPParserOptions {
  /**
   * Default maximum nesting depth for multi-bulk (array/map/set) replies.
   */
  public static final int DEFAULT_MAX_NESTING_DEPTH = 32;

  /**
   * Default maximum number of elements in a single multi-bulk reply.
   */
  public static final int DEFAULT_MAX_MULTI_COUNT = 65535;

  /**
   * Default maximum length of a single unterminated line (simple string, error, or type header).
   * Bounds buffer growth against a peer that never sends the {@code \r\n} terminator.
   * <p>
   * Note that if a line is terminated, it may be longer than this!
   */
  public static final int DEFAULT_MAX_LINE_LENGTH = 128 * 1024;

  /**
   * Default maximum length of a bulk (bulk string / verbatim string) payload.
   * 512 MB, the RESP2 limit for a Redis string.
   */
  public static final long DEFAULT_MAX_STRING_LENGTH = 512 * 1024 * 1024;

  private int maxNestingDepth;
  private int maxMultiCount;
  private int maxLineLength;
  private long maxStringLength;

  public RESPParserOptions() {
    maxNestingDepth = DEFAULT_MAX_NESTING_DEPTH;
    maxMultiCount = DEFAULT_MAX_MULTI_COUNT;
    maxLineLength = DEFAULT_MAX_LINE_LENGTH;
    maxStringLength = DEFAULT_MAX_STRING_LENGTH;
  }

  public RESPParserOptions(RESPParserOptions other) {
    this();
    this.maxNestingDepth = other.maxNestingDepth;
    this.maxMultiCount = other.maxMultiCount;
    this.maxLineLength = other.maxLineLength;
    this.maxStringLength = other.maxStringLength;
  }

  /**
   * @return the maximum nesting depth for multi-bulk replies
   */
  public int getMaxNestingDepth() {
    return maxNestingDepth;
  }

  /**
   * Set the maximum nesting depth for multi-bulk replies.
   *
   * @param maxNestingDepth the maximum nesting depth
   * @return fluent this
   */
  public RESPParserOptions setMaxNestingDepth(int maxNestingDepth) {
    this.maxNestingDepth = maxNestingDepth;
    return this;
  }

  /**
   * @return the maximum number of elements allowed in a single multi-bulk reply
   */
  public int getMaxMultiCount() {
    return maxMultiCount;
  }

  /**
   * Set the maximum number of elements allowed in a single multi-bulk reply.
   *
   * @param maxMultiCount the maximum multi-bulk element count
   * @return fluent this
   */
  public RESPParserOptions setMaxMultiCount(int maxMultiCount) {
    this.maxMultiCount = maxMultiCount;
    return this;
  }

  /**
   * @return the maximum length of an unterminated line before the stream is rejected
   */
  public int getMaxLineLength() {
    return maxLineLength;
  }

  /**
   * Set the maximum length of an unterminated line before the stream is rejected.
   *
   * @param maxLineLength the maximum line length
   * @return fluent this
   */
  public RESPParserOptions setMaxLineLength(int maxLineLength) {
    this.maxLineLength = maxLineLength;
    return this;
  }

  /**
   * @return the maximum length allowed for a bulk payload
   */
  public long getMaxStringLength() {
    return maxStringLength;
  }

  /**
   * Set the maximum length allowed for a bulk payload.
   *
   * @param maxStringLength the maximum bulk payload length
   * @return fluent this
   */
  public RESPParserOptions setMaxStringLength(long maxStringLength) {
    this.maxStringLength = maxStringLength;
    return this;
  }
}
