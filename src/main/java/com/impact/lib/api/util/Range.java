package com.impact.lib.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Range<E extends Number & Comparable<E>> {

  @Contract("_, _ -> new")
  static <T extends Number & Comparable<T>> @NotNull Range<T> of(T min, T max) {
    return new ImmutableNumberRange<>(min, max);
  }

  default E getMin() {
    return min();
  }

  E min();

  default E setMin(E min) {
    return min(min);
  }

  E min(E min);

  default E getMax() {
    return max();
  }

  E max();

  default E setMax(E max) {
    return max(max);
  }

  E max(E max);

  boolean isIn(E number);
}
