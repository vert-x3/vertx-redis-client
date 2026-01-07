package io.vertx.redis.client.impl;

public final class Primitives {
  public static final class Int {
    public int value;

    public Int(int value) {
      this.value = value;
    }
  }

  public static final class IntList {
    private int[] array = new int[4];
    private int size = 0; // one past the last used element in `array`

    public int size() {
      return size;
    }

    public int get(int index) {
      if (index >= size) {
        throw new IndexOutOfBoundsException(index);
      }
      return array[index];
    }

    public void add(int value) {
      if (size == array.length) {
        // perhaps should check for overflow here
        int[] newArray = new int[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        this.array = newArray;
      }
      array[size] = value;
      size++;
    }
  }
}
