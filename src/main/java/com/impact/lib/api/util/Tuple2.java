package com.impact.lib.api.util;

import java.util.Objects;

public class Tuple2<K, V> {

  private K first;
  private V second;

  public Tuple2(K first, V second) {
    this.first = first;
    this.second = second;
  }

  public K getFirst() {
    return first;
  }

  public void setFirst(K first) {
    this.first = first;
  }

  public V getSecond() {
    return second;
  }

  public void setSecond(V second) {
    this.second = second;
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) object;
    return Objects.equals(first, tuple2.first) && Objects.equals(second, tuple2.second);
  }

  @Override
  public String toString() {
    return "Tuple2{" + "first=" + first + ", second=" + second + '}';
  }
}
