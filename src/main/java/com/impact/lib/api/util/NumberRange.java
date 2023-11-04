package com.impact.lib.api.util;

import java.util.Objects;

public final class NumberRange<E extends Number & Comparable<E>> implements Range<E> {

  private E min;
  private E max;

  public NumberRange(E min, E max) {
    this.min = min;
    this.max = max;
    checkValid();
  }

  private void checkValid() {
    if (min.compareTo(max) > 0)
      throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
  }

  // Getters
  @Override
  public E min() {
    return this.min;
  }

  // Setters
  @Override
  public E min(E min) {
    this.min = min;
    checkValid();
    return this.min;
  }

  @Override
  public E max() {
    return this.max;
  }

  @Override
  public E max(E max) {
    this.max = max;
    checkValid();
    return this.max;
  }

  @Override
  public boolean isIn(E number) {
    return min.compareTo(number) <= 0 && max.compareTo(number) >= 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NumberRange<?> that = (NumberRange<?>) o;
    return Objects.equals(min, that.min) && Objects.equals(max, that.max);
  }

  @Override
  public String toString() {
    return "[%s,%s]".formatted(min, max);
  }
}

