package com.impact.lib.api.util;

public class FlipFlop<T> {

  private final T val1;
  private final T val2;
  private boolean flip;

  public FlipFlop(T val1, T val2) {
    this.val1 = val1;
    this.val2 = val2;
  }

  public T value() {
    return get();
  }

  public T get() {
    return flip ? val2 : val1;
  }

  public T flip() {
    flip = !flip;
    return get();
  }

  @Override
  public int hashCode() {
    return get().hashCode();
  }

  @Override
  public String toString() {
    return get().toString();
  }
}
