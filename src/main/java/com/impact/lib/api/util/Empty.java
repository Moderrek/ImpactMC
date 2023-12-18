package com.impact.lib.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Empty {
  @Contract(value = " -> new", pure = true)
  public static @NotNull Empty create() {
    return new Empty();
  }
}
