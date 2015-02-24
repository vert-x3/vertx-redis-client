package io.vertx.redis;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public enum BitOption {
  ONE(1),
  ZERO(0);

  private int value;

  private BitOption(int val) {
    value = val;
  }

  public int value() {
    return value;
  }
}
